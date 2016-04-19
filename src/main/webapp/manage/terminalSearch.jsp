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
	
	table.money{
		width:100%;
	}
	div.tables table table.money td{
		padding:0;
		border:0;
		height: 12px;
		padding:2px 0;	
		overflow: hidden;	
	}
	.min ,.max{
		display: none;
	}
	.current{
		color: black;
	};
	</style>
		<script type="text/javascript" language="javascript">
	var basePath = '<%=basePath%>';
	String.prototype.replaceAll = function(s1,s2) { 
	    return this.replace(new RegExp(s1,"gm"),s2); 
	}
	//${page.text26}角色
	function deleteTerminal(id){
		var result = window.confirm('${page.text1}');
		if(result){
			$.ajax({
				url:basePath+'deleteTerminal.do',
				type:'post',
				dataType:'json',
				data:'id='+id+'&date='+new Date().getTime(),
				success:function(data){
					if(data.success){
						alert('${page.text2}');
						$('form[name=searchTerminal]').submit();						
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
	//全選
	function selectAll(name){
		$('input[name='+name+']').attr('checked',true);
	}
	//取消選擇
	function selectCancle(name){
		$('input[name='+name+']').attr('checked',false);
	}
	//反選
	function selectReverse(name){
		$('input[name='+name+']').each(function(){
			$(this).attr('checked',!this.checked);
		});
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
					url:basePath+'deleteTerminalByIdList.do',
					type:'post',
					dataType:'json',
					data:'idList='+list+'&date='+new Date().getTime(),
					success:function(data){
						if(data.success){
							alert('${page.text2}');
							$('form[name=searchTerminal]').submit();						
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
		$('input[name=searchType][value=${searchType}]').attr('checked',true);
		$('input[name=searchType]').change(function(){
			$('form[name=searchTerminal]').submit();
		});
		$('input[name=alltid]').change(function(){
			$('input[name=tid]').attr('checked',this.checked);
		});
		moneyColor();
	});
	
	function moneyColor(){
		$(".current").each(function(){
			var current = parseInt($(this).text());
			var max = parseInt($(this).parents(".money tr").find(".max").html());
			var min = parseInt($(this).parents(".money tr").find(".min").html());
			if(current > max){
				$(this).css("color","red");
			}else if(current < min){
				$(this).css("color","green");
			}
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
				<a href="<%=basePath%>saveTerminalByPre.do"> <img width="115" height="33" alt="${page.text7}" src="${basePath }manage/images/terminal_add${lansuffix }.jpg" /> </a>
			</div>
			<div class="content_t">
				<h3>
					<span>${page.text8}</span>
				</h3>
			</div>
			<div class="tables">
				<form action="<%=basePath%>searchTerminal.do" name="searchTerminal" method="post">
					<div class="marquee_s">
						${page.text9}
						<input class="keyword" type="text" name="searchName" value="${searchName}" />
						<input class="submit" type="image" alt="${page.text10}" title="${page.text10}" onclick="$('input[name=startPage]').val(1);" src="${basePath }manage/images/btn4${lansuffix }.jpg" />
						<input class="m_radio" type="radio" name="searchType" value="0" />
						${page.text11}
						<input class="m_radio" type="radio" name="searchType" value="1" />
						${page.text12}
						<input class="m_radio" type="radio" name="searchType" value="2" />
						${page.text13}
					</div>
					<div class="user_list">
						<table width="100%" class="discolor">
							<c:if test="${! empty requestScope.terminalList}">
								<tr>
									<th scope="col" width="10%" rowspan="2">
										${page.text16}
									</th>
									<th scope="col" width="10%" rowspan="2">
										TeamViewer
									</th>
									<th scope="col" width="10%" rowspan="2">
										${page.text17}
									</th>
									<th scope="col" width="10%" rowspan="2">
										${page.text18}
									</th>
									<th scope="col" width="10%" rowspan="2">
										${page.text19}
									</th>
									<th scope="col" width="10%" rowspan="2">
										${page.text20}
									</th>
									<th scope="col" width="10%" colspan="2">
										<s:text name="terminal.out" />
									</th>
									<th scope="col" width="10%" colspan="2">
										<s:text name="terminal.in" />
									</th>
									<th scope="col" width="10%" colspan="2">
										<s:text name="terminal.coin" />
									</th>
									<th scope="col" width="10%" rowspan="2">
										${page.text22}
									</th>
								</tr>
								<tr>
									<th>
										<s:text name="terminal.face" />
									</th>
									<th>
										<s:text name="terminal.stock" />
									</th>
									<th>
										<s:text name="terminal.face" />
									</th>
									<th>
										<s:text name="terminal.stock" />
									</th>
									<th>
										<s:text name="terminal.face" />
									</th>
									<th>
										<s:text name="terminal.stock" />
									</th>
								</tr>
								<c:forEach var="terminal" items="${requestScope.terminalList}" varStatus="index">
									<tr>
										<td>
											${terminal.terminalId}
											<c:set var="key" value="${terminal.id }"></c:set>
											<input type="hidden" name="tid" value="${terminal.id}" />
										</td>
										<td>
											${terminal.teamViewerId}
										</td>
										<td>
											<c:if test="${terminal.type==1}">${page.text12}</c:if>
											<c:if test="${terminal.type==2}">${page.text13}</c:if>
										</td>
										<td>
											${terminal.location}
										</td>
										<td>
											<c:if test="${!(terminal.version eq null)}">
												<c:if test="${terminal.type==1}">
													<font color="${requestScope.orderversion != terminal.version?'red':'green'}"> ${terminal.version} </font>
												</c:if>
												<c:if test="${terminal.type==2}">
													<font color="${requestScope.cookversion != terminal.version?'red':'green'}"> ${terminal.version} </font>
												</c:if>
											</c:if>
										</td>
										<td>
											<c:if test="${terminal.invoice==0}">
												<font color="red">${page.text23}</font>
											</c:if>
											<c:if test="${terminal.invoice==1}">
												<font color="green">${page.text24}</font>
											</c:if>
										</td>

										<td colspan="2">
											<table class="money">
												<c:forEach items="${ndMap[key]}" var="md">
													<tr>
														<td width="50%">
															${md.name}
															<span class="min">${md.min}</span>
															<span class="max">${md.max}</span>
														</td>
														<td width="50%" class="current">
															${md.current}
														</td>
													</tr>
												</c:forEach>
											</table>
										</td>

										<td colspan="2">
											<table class="money">
												<c:forEach items="${nvMap[key]}" var="md">
													<tr>
														<td width="50%">
															${md.name}
															<span class="min">${md.min}</span>
															<span class="max">${md.max}</span>
														</td>
														<td width="50%" class="current">
															${md.current}
														</td>
													</tr>
												</c:forEach>
											</table>
										</td>

										<td colspan="2">
											<table class="money">
												<c:forEach items="${hopMap[key]}" var="md">
													<tr>
														<td width="50%">
															${md.name}
															<span class="min">${md.min}</span>
															<span class="max">${md.max}</span>
														</td>
														<td width="50%" class="current">
															${md.current}
														</td>
													</tr>
												</c:forEach>
											</table>
										</td>





										<td>
											<a href="${basePath }findTerminal.do?id=${terminal.id}" title="${page.text25}"><img width="30" height="30" src="${basePath }manage/images/program_icon2.gif" /> </a>
											<a href="javascript:deleteTerminal(${terminal.id});" title="${page.text26}"><img width="30" height="30" src="${basePath }manage/images/program_icon1.gif" /> </a>
										</td>
									</tr>
								</c:forEach>
							</c:if>
							<c:if test="${empty requestScope.terminalList}">
								<tr>
									<td colspan="8" style="font-weight: 800;text-align: center;color:red;">
										${page.text27}
									</td>
								</tr>
							</c:if>
						</table>
					</div>
					<c:if test="${! empty requestScope.terminalList}">
						<page:paper formName="searchTerminal" startPage="${requestScope.pageBean.startPage}" maxPage="${requestScope.pageBean.maxPage}" pageSize="${requestScope.pageBean.pageSize}"
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
