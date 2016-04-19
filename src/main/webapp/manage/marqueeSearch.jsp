<%@ page language="java"  pageEncoding="UTF-8"%>
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
}
.operation img {
	cursor: pointer;
	vertical-align: middle;
	margin-left: 8px;
}
#left{
	float:left;
	margin-left: 15px;
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
	//${page.text23}角色
	function deleteMarquee(id){
		var result = window.confirm('${page.text1}');
		if(result){
			$.ajax({
				url:basePath+'deleteMarquee.do',
				type:'post',
				dataType:'json',
				data:'id='+id+'&date='+new Date().getTime(),
				success:function(data){
					if(data.success){
						alert('${page.text2}');
						$('form[name=searchMarquee]').submit();						
					}else {
						if(data.errorMsg!='' && typeof (data.errorMsg) !='undefined'){
							alert(data.errorMsg);
						}
						if(data.infoInstruct!='' && typeof (data.infoInstruct) !='undefined'){
							alert(data.infoInstruct);
						}
					}
				}
			});	
		}
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
					url:basePath+'deleteMarqueeByIdList.do',
					type:'post',
					dataType:'json',
					data:'idList='+list+'&date='+new Date().getTime(),
					success:function(data){
						if(data.success){
							alert('${page.text2}');
							$('form[name=searchMarquee]').submit();						
						}else {
							if(data.errorMsg!='' && typeof (data.errorMsg) !='undefined'){
								alert(data.errorMsg);
							}else if(data.infoInstruct!='' && typeof (data.infoInstruct) !='undefined'){
								alert(data.infoInstruct);
							}else{
								alert('${page.text5}');
							}
						}
					}
				});	
			}
		}
	}
	$(document).ready(function(){
	dealCheck();
	$("#delCheck").click(function(){
			deleteSelect();
		});
		$("#delctrl").append(' <div class="del">'
				+' <a href="javascript:selectAll(\'tid\');" title="${page.text6}"><img width="55" height="21" alt="${page.text6}" src="'+basePath+'manage/images/icon1${lansuffix }.png" /></a>'
				+' <a href="javascript:selectReverse(\'tid\');" title="${page.text7}"><img width="55" height="21" alt="${page.text7}" src="'+basePath+'manage/images/icon3${lansuffix }.png" /></a>'
				+' <a href="javascript:selectCancle(\'tid\');" title="${page.text8}"><img width="84" height="21" alt="${page.text8}" src="'+basePath+'manage/images/icon4${lansuffix }.png" /></a>'
				+' <a href="javascript:deleteSelect();" title="${page.text9}"><img width="85" height="21" alt="${page.text9}" src="'+basePath+'manage/images/icon2${lansuffix }.png" /></a>'
			+' </div>');
			
			
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
		<div id="marquee">
			<div class="content_t"><h3><span>${page.text10}</span></h3></div>
			<div class="m_menu">
				<a href="<%=basePath%>saveMarqueeByPrepare.do"><img width="133" height="34" alt="${page.text11}" src="${basePath }manage/images/marquee_btn1${lansuffix }.jpg" /></a>
			</div>
			<div class="content_t"><h3><span>${page.text29}</span></h3></div>
			<div class="tables">
				<form action="<%=basePath%>searchMarquee.do" name="searchMarquee" method="post">
					<div class="marquee_s">
						${page.text13}<input class="keyword" type="text" name="title" value="${title }"/>
						${page.text14}<input class="keyword" type="text" name="content" value="${content }"/>
						<input class="submit" type="image" alt="${page.text15}" title="${page.text15}" onclick="$('input[name=startPage]').val(1);" src="${basePath }manage/images/btn4${lansuffix }.jpg" />
					</div>
					<div class="operation">
					<ul id="left" style="list-style: none">
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
				<div class="marquee_list">
					<table width="100%" class="discolor">
						<tr>
							<th scope="col" width="5%"><input id="checkParent" type="checkbox"/></th>
							<th scope="col" width="15%">${page.text17}</th>
							<th scope="col" width="32%">${page.text18}</th>
							<th scope="col" width="6%">${page.text19}</th>
							<th scope="col" width="9%">${page.text20}</th>
							<th scope="col" width="7%">${page.text21}</th>
							<th scope="col" width="5%">${page.text22}</th>
							<th scope="col" width="5%">${page.text23}</th>
						</tr>
						<c:if test="${! empty requestScope.marqueeList}">
                		<c:forEach var="marquee" items="${requestScope.marqueeList}" varStatus="index">
						<tr>
							<td><input type="checkbox" name="tid" value="${marquee.id}"/></td>
							<td>${marquee.title}</td>
							<td>
								<c:choose>
		                  		<c:when test="${marquee.isRss==1 && fn:length(marquee.m_content)>50}">
		                  					${fn:substring(marquee.m_content, 0, 50)} 
		                  		</c:when>
		                  		<c:when test="${marquee.isRss==0 && fn:length(marquee.m_content)>20}">
		                  					${fn:substring(marquee.m_content, 0, 20)} 
		                  		</c:when>
		                  		<c:otherwise>
		                  			${marquee.m_content}
		                  		</c:otherwise>
		                  		</c:choose>
							</td>
							<td>
							<c:choose>
	                  			<c:when test="${marquee.direction=='left'}">${page.text24}</c:when>
	                  			<c:when test="${marquee.direction=='right'}">${page.text25}</c:when>
	                  			<c:when test="${marquee.direction=='down'}">${page.text26}</c:when>
	                  			<c:when test="${marquee.direction=='up'}">${page.text27}</c:when>
	                  		</c:choose>
							 </td>
							<td>${marquee.size}${page.text28}</td>
							<td><div style="background-color: ${marquee.color};"></div></td>
							<td><a href="<%=basePath%>findMarquee.do?id=${marquee.id}" title="${page.text22}"><img width="21" height="21" alt="${page.text22}" src="${basePath }manage/images/edit_icon.gif" /></a></td>
							<td><a href="javascript:deleteMarquee(${marquee.id});" title="${page.text23}"><img width="21" height="21" alt="${page.text23}" src="${basePath }manage/images/del_icon.gif" /></a></td>
						</tr>
						</c:forEach>
						</c:if>
					</table>
				</div>
				<c:if test="${! empty requestScope.marqueeList}">
							<page:paper formName="searchMarquee"
										startPage="${requestScope.pageBean.startPage}"
										maxPage="${requestScope.pageBean.maxPage}"
										pageSize="${requestScope.pageBean.pageSize}"
										rowCount="${requestScope.pageBean.rowCount}" 
										language="${language}"
												/>
					
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
