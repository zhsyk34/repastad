<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="util.jsp"%>
<%
	String isSearch = request.getParameter("isSearch");
	if (isSearch == null || isSearch.length() == 0) {
		isSearch = "yes";
	}
	pageContext.setAttribute("isSearch", isSearch);
%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh">
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
	.detail{
		cursor: pointer;
		width:85px;
		 height=23px;
	}
	#export{
		vertical-align: middle;
		cursor: pointer;
	}
	
	#sort span{
		width: 70px;height: 14px;background-color: #7AC1DD;color:#FFF;display: inline-block;text-align: center;padding: 5px 0;line-height: 15px;margin: 0 5px 0px 10px;cursor: pointer;
	}
	</style>
		<script src="${basePath }manage/js/date/WdatePicker.js" type="text/javascript"></script>
		<script type="text/javascript" language="javascript">
	var basePath = '<%=basePath%>';
	String.prototype.replaceAll = function(s1,s2) { 
	    return this.replace(new RegExp(s1,"gm"),s2); 
	}
	var sortType = "<s:property value='sortType'/>";
	$(document).ready(function(){
		if(sortType == "money"){
			$("#sort span:last").css("backgroundColor","#3099DA");	
		}else{
			$("#sort span:first").css("backgroundColor","#3099DA");
		}
		$(".detail").click(function(){
			var newLink = $(this).attr("link");
			var parentWindow = $(window.parent.document);
			var li = parentWindow.find("#orderDetail");
			
			var oldLink = li.attr("link");
			li.attr("link",newLink);
			li.click();
			li.attr("link",oldLink);
		});
		//
		$("#export").on("click",function(){
			$("form").attr("action","orderExport.do").submit();
			$("form").attr("action","orderCalculate.do");
		});
	});
	function setD1D2(day){
		var d2 = new Date();
		var d1 = new Date();
		if(day==7){
			d1.setDate(d1.getDate()-7);
		}else if(day==30){
			d1.setMonth(d1.getMonth()-1);
		}
		$("#d1").val(d1.getFullYear()+"-"+(d1.getMonth()+1)+"-"+d1.getDate());
		$("#d2").val(d2.getFullYear()+"-"+(d2.getMonth()+1)+"-"+d2.getDate());
	}
	function checkSend(){
		var d1 = $("#d1").val();
		var d2 = $("#d2").val();
		if($.trim(d1)=='' && $.trim(d2)==''){
			alert("${page.text1}");
			return false;
		}
		return true;
	}
	function setSort(type){
		$("#sort span").css("backgroundColor","#7AC1DD");
		$(this).css("backgroundColor","#3099DA");
		
		$("input[name=sortType]").val(type);
		$("form[name=orderCalculate]").submit();
	}
</script>
	</head>
	<body>

		<div id="template" style="width: 100%">
			<div class="title">
				<h3>
					<div>
						<s:text name="order.count" />
					</div>
				</h3>
			</div>
			<div class="tables">
				<form action="<%=basePath%>orderCalculate.do" name="orderCalculate" method="post" onsubmit="return checkSend();">
					<input type="hidden" name="sortType" value="${sortType}" />
					<input name="exportType" type="hidden" value="2" />
					<div class="marquee_s" style="line-height: 38px;">
						<div style="height: 40px;">
							${page.text5}
							<input class="keyword" type="text" id="d1" name="begindate" value="${begindate}" readonly="readonly" />
							<img width="20" height="17" src="${basePath }manage/images/date_icon2.jpg" onclick="WdatePicker({el:$dp.$('d1'),dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'d2\')}'})" />
							${page.text6}
							<input class="keyword" type="text" id="d2" name="enddate" value="${enddate}" readonly="readonly" />
							<img width="20" height="17" src="${basePath }manage/images/date_icon2.jpg" onclick="WdatePicker({el:$dp.$('d2'),dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'d1\')}'})" />
							<span onclick="setD1D2(0);" onmouseover="$(this).css('background','#3099DA')" onmouseout="$(this).css('background','#7AC1DD')"
								style="width: 50px;height: 15px;background: #7AC1DD;color:#FFF;display: inline-block;text-align: center;padding: 5px 0;line-height: 15px;margin: 0 5px 0px 10px;cursor: pointer;">${page.text7}</span>
							<span onclick="setD1D2(7);" onmouseover="$(this).css('background','#3099DA')" onmouseout="$(this).css('background','#7AC1DD')"
								style="width: 50px;height: 15px;background: #7AC1DD;color:#FFF;display: inline-block;text-align: center;padding: 5px 0;line-height: 15px;margin: 0 5px 0px 10px;cursor: pointer;">${page.text8}</span>
							<span onclick="setD1D2(30);" onmouseover="$(this).css('background','#3099DA')" onmouseout="$(this).css('background','#7AC1DD')"
								style="width: 55px;height: 15px;background: #7AC1DD;color:#FFF;display: inline-block;text-align: center;padding: 5px 0;line-height: 15px;margin: 0 5px 0px 10px;cursor: pointer;">${page.text9}</span>
						</div>
						<div>
							${page.text10}
							<input class="keyword" type="text" name="shopId" value="${shopId}" />
							${page.text11}
							<input class="keyword" type="text" name="cookId" value="${cookId}" />
							<input class="submit" type="image" alt="${page.text12}" title="${page.text12}" src="${basePath }manage/images/btn4${lansuffix }.jpg" />
							<img src="manage/images/export${lansuffix }.png" id="export" />
						</div>
					</div>
					<div class="user_list">
						<table width="100%" class="discolor">
							<c:if test="${! empty requestScope.calcList}">
								<tr>
									<th scope="col" rowspan="2" width="10%">
										${page.text13}
									</th>
									<th scope="col" rowspan="2" width="10%">
										${page.text14}
									</th>
									<th scope="col" colspan="4" width="50%" id="sort">
										${page.text15}
										<span onclick="setSort('number');">${page.text16}</span>
										<span onclick="setSort('money');">${page.text17}</span>
									</th>
									<th scope="col" rowspan="2" width="10%">
										${page.text18}
									</th>
									<th scope="col" rowspan="2" width="10%">
										${page.text19}
									</th>
								</tr>
								<tr>
									<th scope="col" width="8%">
										${page.text20}
									</th>
									<th scope="col" width="20%">
										${page.text21}
									</th>
									<th scope="col" width="8%">
										${page.text22}
									</th>
									<th scope="col" width="14%">
										${page.text23}
									</th>
								</tr>
								<s:iterator value="#request.calcList">
									<tr>
										<td>
											<s:property value="shop" />
										</td>
										<td>
											<s:property value="cook" />
										</td>
										<td colspan="4" style="padding: 0;text-align: left;">
											<table width="100%">
												<s:iterator value="contentList" status="row">
													<tr>
														<td width="8%">
															<s:property value="#row.count" />
														</td>
														<td width="20%">
															<s:property value="name" />
														</td>
														<td width="8%">
															<s:property value="count" />
														</td>
														<td width="14%">
															<s:text name="formatMoney">
																<s:param value="money" />
															</s:text>
														</td>
													</tr>
												</s:iterator>
											</table>
										</td>
										<td>
											<s:text name="formatMoney">
												<s:param value="total" />
											</s:text>
										</td>
										<td>
											<img class="detail" alt="${page.text24}" src="${basePath }manage/images/to_list${lansuffix }.png"
												link="<%=basePath%>searchOrder.do?shopId=<s:property value='shop' />&cookId=<s:property value='cook' />&begindate=${begindate}&enddate=${enddate}" />
										</td>
									</tr>
								</s:iterator>
								<tr>
									<td colspan="2">
										${page.text25}
									</td>
									<td colspan="4" style="padding: 0;">
										<table width="100%">
											<s:set name="totalMoney" value="0" />
											<s:iterator value="#request.totalList" status="row">
												<tr>
													<td width="8%">
														<s:property value="#row.count" />
													</td>
													<td width="20%">
														<s:property value="name" />
													</td>
													<td width="8%">
														<s:property value="count" />
													</td>
													<td width="14%">
														<s:text name="formatMoney">
															<s:param value="money" />
														</s:text>
														<s:set var="totalMoney" value="%{#totalMoney + money}"></s:set>
													</td>
												</tr>
											</s:iterator>
										</table>
									</td>
									<td>
										<s:text name="formatMoney">
											<s:param value="totalMoney" />
										</s:text>
									</td>
									<td>
										<img class="detail" width="85" height="23" alt="${page.text24}" src="${basePath }manage/images/to_list${lansuffix }.png"
											link="<%=basePath%>searchOrder.do?begindate=${begindate}&enddate=${enddate}" />
									</td>
								</tr>
							</c:if>
							<c:if test="${empty requestScope.calcList}">
								<tr>
									<td colspan="8" style="font-weight: 800;text-align: center;color:red;">
										${isSearch=='no'?page.text1:page.text26}
									</td>
								</tr>
							</c:if>
						</table>
					</div>
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
