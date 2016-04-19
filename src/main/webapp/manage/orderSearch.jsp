<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="util.jsp"%>
<%
	String isSearch = request.getParameter("isSearch");
	if (isSearch == null || isSearch.length() == 0) {
		isSearch = "yes";
	}
	pageContext.setAttribute("isSearch", isSearch);
%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh" style="width: 100%">
	<head>
		<title><s:text name="order.detail" /></title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
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
	#export{
	cursor: pointer;
		vertical-align: middle;
	}
	</style>
		<script src="${basePath }manage/js/date/WdatePicker.js" type="text/javascript"></script>
		<script type="text/javascript" language="javascript">
	var basePath = '<%=basePath%>';
	String.prototype.replaceAll = function(s1,s2) { 
	    return this.replace(new RegExp(s1,"gm"),s2); 
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
		$("input:checked[name=oid]").each(function(){
			list+=this.value+',';
		});
		if(list == ''){
			alert('${page.text1}');
			return;
		}else {
			var result = confirm('${page.text2}');
			if(result){
				$.ajax({
					url:basePath+'deleteOrderByIdList.do',
					type:'post',
					dataType:'json',
					data:'idList='+list+'&date='+new Date().getTime(),
					success:function(data){
						if(data.success){
							alert('${page.text3}');
							$('form[name=searchOrder]').submit();						
						}else {
							if(data.errorMsg!='' && typeof (data.errorMsg) !='undefined'){
								alert(data.errorMsg);
							}else if(data.infoInstruct!='' && typeof (data.infoInstruct) !='undefined'){
								alert(data.infoInstruct);
							}else{
								alert('${page.text4}');
							}
						}
					}
				});	
			}
		}
	}
	
	function exportData(){
		$("#export").on("click",function(){
			$("form").attr("action","orderExport.do").submit();
			$("form").attr("action","searchOrder.do");
		});
	}
	$(document).ready(function(){
		$('input[name=alloid]').change(function(){
			$('input[name=oid]').attr('checked',this.checked);
		});
		//
		exportData();
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
			alert("${page.text5}");
			return false;
		}
		return true;
	}
	
</script>
	</head>
	<body>
		<div id="template">
			<div class="title">
				<h3>
					<div>
						<s:text name="order.detail" />
					</div>
				</h3>
			</div>
			<div class="tables">
				<form action="<%=basePath%>searchOrder.do" name="searchOrder" method="post" onsubmit="return checkSend();">
					<input name="exportType" type="hidden" value="1" />
					<div class="marquee_s" style="line-height: 38px;">
						<div style="height: 40px;">
							${page.text9}
							<input class="keyword" type="text" id="d1" name="begindate" value="${begindate}" readonly="readonly" />
							<img width="20" height="17" src="${basePath }manage/images/date_icon2.jpg" onclick="WdatePicker({el:$dp.$('d1'),dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'d2\')}'})" />
							${page.text10}
							<input class="keyword" type="text" id="d2" name="enddate" value="${enddate}" readonly="readonly" />
							<img width="20" height="17" src="${basePath }manage/images/date_icon2.jpg" onclick="WdatePicker({el:$dp.$('d2'),dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'d1\')}'})" />
							<span onclick="setD1D2(0);" onmouseover="$(this).css('background','#3099DA')" onmouseout="$(this).css('background','#7AC1DD')"
								style="width: 50px;height: 15px;background: #7AC1DD;color:#FFF;display: inline-block;text-align: center;padding: 5px 0;line-height: 15px;margin: 0 5px 0px 10px;cursor: pointer;">${page.text11}</span>
							<span onclick="setD1D2(7);" onmouseover="$(this).css('background','#3099DA')" onmouseout="$(this).css('background','#7AC1DD')"
								style="width: 50px;height: 15px;background: #7AC1DD;color:#FFF;display: inline-block;text-align: center;padding: 5px 0;line-height: 15px;margin: 0 5px 0px 10px;cursor: pointer;">${page.text12}</span>
							<span onclick="setD1D2(30);" onmouseover="$(this).css('background','#3099DA')" onmouseout="$(this).css('background','#7AC1DD')"
								style="width: 55px;height: 15px;background: #7AC1DD;color:#FFF;display: inline-block;text-align: center;padding: 5px 0;line-height: 15px;margin: 0 5px 0px 10px;cursor: pointer;">${page.text13}</span>
						</div>
						<div>
							${page.text14}
							<input class="keyword" type="text" name="shopId" value="${shopId}"/>
							${page.text15}
							<input class="keyword" type="text" name="cookId" value="${cookId}" />
							${page.text20}
							<input class="keyword"  name="orderId" value="${orderId}" />
							<input type="image" alt="${page.text16}" title="${page.text16}" onclick="$('input[name=startPage]').val(1);" src="${basePath }manage/images/btn4${lansuffix }.jpg" />
							<img src="manage/images/export${lansuffix }.png" id="export" />
						</div>
					</div>
					<div class="user_list">
						<table width="100%" class="discolor">
							<s:if test="#request.list != null">
								<tr>
									<th scope="col" width="10%" style="display: none">
										<input type="checkbox" name="alloid" />
										<span onclick="deleteSelect();" class="deleteAll">${page.text17}</span>
									</th>
									<th scope="col" width="10%">
										${page.text18}
									</th>
									<th scope="col" width="10%">
										${page.text19}
									</th>
									<th scope="col" width="15%">
										訂單編號
									</th>
									<th scope="col" width="20%">
										${page.text21}
									</th>
									<th scope="col" width="10%">
										${page.text22}
									</th>
									<th>
										${page.text26}
									</th>
									<th>
										${page.text27}
									</th>
									<th scope="col" width="15%">
										${page.text23}
									</th>
								</tr>
								<s:iterator var="order" value="#request.list" status="index">
									<tr>
										<td style="display: none">
											<input type="checkbox" name="oid" value="<s:property value='id'/>" />
										</td>
										<td>
											<s:property value="shop" />
										</td>
										<td>
											<s:property value="cook" />
										</td>
										<td>
											<s:property value="id" />
										</td>
										<td style="text-align: left">
											<s:iterator value="contentList" var="content" status="row">
												<s:property value="#row.count" />.
												<s:property value="name" />:
											
												<s:text name="formatMoney2">
													<s:param value="price" />
												</s:text>
												*
													<s:property value="count" />
												=
												<s:text name="formatMoney2">
													<s:param value="money" />
												</s:text>
												<br />
											</s:iterator>
										</td>
										<td>
											<s:text name="formatMoney">
												<s:param value="total"></s:param>
											</s:text>
										</td>
										<td>
											<s:text name="formatMoney">
												<s:param value="income"></s:param>
											</s:text>
										</td>
										<td>
											<s:text name="formatMoney">
												<s:param value="pay"></s:param>
											</s:text>
										</td>
										<td>
											<s:text name="formatTime">
												<s:param value="clock"></s:param>
											</s:text>
										</td>
									</tr>
								</s:iterator>
							</s:if>
							<s:else>
								<tr>
									<td colspan="7" style="font-weight: 800;text-align: center;color:red;">
										${isSearch=='no'?page.text5:page.text24}
									</td>
								</tr>
							</s:else>
						</table>
					</div>
					<s:if test="#request.list != null">
						<page:paper formName="searchOrder" startPage="${requestScope.pageBean.startPage}" maxPage="${requestScope.pageBean.maxPage}" pageSize="${requestScope.pageBean.pageSize}"
							rowCount="${requestScope.pageBean.rowCount}" language="${language}" />
					</s:if>
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
