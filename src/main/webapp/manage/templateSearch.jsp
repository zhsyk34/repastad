<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="util.jsp"%>
<html>
	<head>
		<title></title>
		<style type="text/css">
	.deleteAll{
		font-size:12px;
		color:#2B4850;
		font-weight: blod;
		cursor: pointer;
		padding: 2px;
		margin-left: 3px;
	}
	.deleteAll:HOVER {
		background: #B4DAFF;
	}
	div.operation {
	margin:0;
	padding:0;
	height: 50px;
	width: 100%;
	clear: both;
	border-bottom: 1px solid #C9C9C9;
	margin-bottom: 5px;
}
.operation img {
	cursor: pointer;
	vertical-align: middle;
	margin-left: 8px;
}

.operation .submit {
	vertical-align: middle;
	margin-left: 8px;
}

#left{
	float:left;
	margin-left: 15px;
}
#right{
	float:right;
	margin-right: 120px;
}

div.operation li {
	float: left;
	height: 40px;
	line-height: 40px;
}
	</style>
		<script type="text/javascript" language="javascript">
	var basePath = '<%=basePath%>';
	
	String.prototype.replaceAll = function(s1,s2) { 
	    return this.replace(new RegExp(s1,"gm"),s2); 
	}
	//${page.text14}角色
	function deleteTemplate(id){
		var result = window.confirm('${page.text1}');
		if(result){
			$.ajax({
				url:basePath+'json/Template_deleteTemplate.do',
				type:'post',
				dataType:'json',
				data:'id='+id+'&date='+new Date().getTime(),
				success:function(data){
					var result = data.result;
					if("del"==result){
						alert('${page.text2}');
						$('form[name=searchTemplate]').submit();						
					}
					if("used"==result){
						alert(lan.templateUsed);												
					}
				}
			});	
		}
	}
	function downTemplate(templateId){
		window.open(basePath+'terminalDownOnline.do?templateId='+templateId, 'down'+templateId, 'height=600, width=800, top=100, left=100,toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no');
	}
	//删除多选
	function deleteSelect(){
		var list = '';
		$("input:checked[name=tid]").each(function(){
			list+=this.value+',';
		});
		if(list == ''){
			alert('${page.text3}');
			return;
		}else {
			var result = confirm('${page.text4}');
			if(result){
				$.ajax({
					url:basePath+'json/Template_deleteTemplateByIdList.do',
					type:'post',
					dataType:'json',
					data:'idList='+list+'&date='+new Date().getTime(),
					success:function(data){
						var result = data.result;
						if("del"==result){
							alert('${page.text2}');
							$('form[name=searchTemplate]').submit();						
						}
						if("used"==result){
							alert(lan.templateUsed);												
						}
					}
				});	
			}
		}
	}
	$(document).ready(function(){
		$('input[name=alltid]').change(function(){
			$('input[name=tid]').attr('checked',this.checked);
		});
		$("#delCheck").click(function(){
			deleteSelect();
		});
		//
		dealCheck();
	});
	
	function dealCheck(){
		$("#checkParent").click(function () {
		$("form").find(":checkbox").prop("checked", $(this).prop("checked"));
	});
	$("#checkAll").click(function () {
		$("form").find(":checkbox").prop("checked", true);
	});
	$("#checkNone").click(function () {
		$("form").find(":checkbox").prop("checked", false);
	});
	$("#checkInvert").click(function () {
		var flag = true;
		$("form").find(":checkbox:not(:first)").each(function () {
			flag = flag && (!$(this).prop("checked"));
			$(this).prop("checked", !$(this).prop("checked"));
		});
		$("form :checkbox:first").prop("checked", flag);
	});
	$("#checkAll").click(function () {
		$("form").find(":checkbox").prop("checked", true);
	});
		
	}
</script>
	</head>
	<body>
		<div id="template">
			<div class="content_t">
				<h3>
					<span>${page.text6}</span>
				</h3>
			</div>
			<div class="m_menu">
				<a href="manage/templateEdit.jsp"> <img width="115" height="33" alt="${page.text7}" src="${basePath }manage/images/template_add${lansuffix }.jpg" /> </a>
			</div>
			<div class="content_t">
				<h3>
					<span>${page.text17}</span>
				</h3>
			</div>
			<div class="tables">
				<form action="<%=basePath%>searchTemplate.do" name="searchTemplate" method="post">
					<div class="operation">
						<ul id="left">
							<li>
								${page.text8}:
								<input class="keyword" type="text" name="searchName" value="${searchName}" />
							</li>
							<li>
								<input class="submit" type="image" onclick="$('input[name=startPage]').val(1);" src="${basePath }manage/images/btn4${lansuffix }.jpg" />
							</li>
						</ul>
						<ul id="right">
							<li>
								<img id="checkAll" src="manage/images/icon1<s:text name='language'/>.png" />
							</li>
							<li>
								<img id="checkInvert" src="manage/images/icon3<s:text name='language'/>.png" />
							</li>
							<li>
								<img id="checkNone" src="manage/images/icon4<s:text name='language'/>.png" />
							</li>
							<li>
								<img id="delCheck" src="manage/images/icon2<s:text name='language'/>.png" />
							</li>
						</ul>
					</div>
					<div class="user_list">
						<table width="100%" class="discolor">
							<s:if test="#request.templateList != null">
								<tr>
									<th scope="col" width="15%">
										<input id="checkParent" type="checkbox" name="alltid" />
									</th>
									<th scope="col" width="30%">
										${page.text10}
									</th>
									<!--  -->
									<th scope="col" width="30%">
										${page.text11}
									</th>
									<th scope="col" width="30%">
										${page.text12}
									</th>
								</tr>
								<s:iterator var="template" value="#request.templateList" status="row">
									<tr>
										<td>
											<input type="checkbox" name="tid" value="<s:property value='id'/>" />
										</td>
										<td>
											<s:property value='name' />
										</td>
										<td>
											<s:text name="formatTime">
												<s:param value="addtime"></s:param>
											</s:text>
										</td>
										<td>
											<a href="${basePath }manage/templateEdit.jsp?id=<s:property value='id'/>" title="${page.text13}"><img width="30" height="30" src="${basePath }manage/images/program_icon2.gif" /> </a>
											<a href="javascript:deleteTemplate(<s:property value='id'/>);" title="${page.text14}"><img width="30" height="30" src="${basePath }manage/images/program_icon1.gif" /> </a>
											<a href="javascript:downTemplate(<s:property value='id'/>);" title="${page.text15}"><img width="30" height="30" src="${basePath }manage/images/program_icon4.gif"> </a>
										</td>
									</tr>
								</s:iterator>
							</s:if>
							<s:else>
								<tr>
									<td colspan="4" style="font-weight: 800;text-align: center;color:red;">
										${page.text16}
									</td>
								</tr>
							</s:else>
						</table>
					</div>
					<c:if test="${! empty requestScope.templateList}">
						<page:paper formName="searchTemplate" startPage="${requestScope.pageBean.startPage}" maxPage="${requestScope.pageBean.maxPage}" pageSize="${requestScope.pageBean.pageSize}"
							rowCount="${requestScope.pageBean.rowCount}" language="${language}" />

					</c:if>
				</form>
			</div>
		</div>
	</body>

	<script type="text/javascript">
	<c:if test="${! empty requestScope.message}">
		alert('${requestScope.message}');
	</c:if>
</script>
</html>
