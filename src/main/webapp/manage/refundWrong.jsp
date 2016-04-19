<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="util.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>refund</title>
		<link rel="stylesheet" type="text/css" href="manage/css/refundWrong.css" />
		<script src="manage/js/date/WdatePicker.js" type="text/javascript"></script>
		<script type="text/javascript" src="manage/js/refundWrong.js"></script>
		<script type="text/javascript">
	var pageCount = '<s:property value="pageCount"/>';
	var pageNo = '<s:property value="pageNo"/>';
	var pageSize = '<s:property value="pageSize"/>';
	var count = "<s:property value='count'/>";	
	
	var isGet = "<s:property value='isGet' />";
	var reason = "<s:property value='reason' />";
	var type = "<s:property value='type' />";
	var adminId = "<s:property value='adminId' />";
	/*--------------*/
	var notDeal = "<s:text name='refund.notDeal' />";
	var haveDeal = "<s:text name='refund.haveDeal' />";
	var nowDeal = "<s:text name='refund.nowDeal' />";
	var noneDeal = "<s:text name='refund.noneDeal' />";
	var reDeal = "<s:text name='refund.reDeal' />";
	
	var modSure = "<s:text name='refund.modSure' />";
	var modSuccess = "<s:text name='refund.modSuccess' />";
</script>
	</head>
	<body>
		<div class="title">
			<h3>
				<div>
					<s:text name="refund.query" />
				</div>
			</h3>
		</div>

		<div class="content">
			<!-- search -->
			<div id="search">
				<form action="order/refundList.do" method="post">
					<table>
						<!-- position -->
						<tr>
							<td>
								<s:text name="refund.beginTime" />
							</td>
							<td>
								<input id="d1" class="keyword" name="beginTime" value="<s:property value='beginTime' />" />
								<img width="20" height="17" src="manage/images/date_icon2.jpg" onclick="WdatePicker({el:$dp.$('d1'),dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'d2\')}'})" />
							</td>
							<td>
								<s:text name="refund.endTime" />
							</td>
							<td>
								<input id="d2" class="keyword" name="endTime" value="<s:property value='endTime' />" />
								<img width="20" height="17" src="manage/images/date_icon2.jpg" onclick="WdatePicker({el:$dp.$('d2'),dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'d1\')}'})" />
							</td>
							<!-- button -->

							<td id="button" colspan="2" rowspan="2" style="text-align: center;">
								<img id="reset" src="sysimg/reset<s:text name='language' />.jpg" />
								<br />
								<img class="search" src="sysimg/search<s:text name='language' />.jpg" />
							</td>
						</tr>
						<!-- time -->
						<tr>
							<td width="9%">
								<s:text name="refund.terminal" />
							</td>
							<td width="24%">
								<input class="keyword" name="terminalId" value="<s:property value='terminalId' />" />
							</td>
							<td width="9%">
								<s:text name="refund.sno" />
							</td>
							<td width="24%">
								<input class="keyword" name="sno" value="<s:property value='sno' />" />
							</td>
						</tr>
						<!-- selector -->
						<tr>
							<!-- user -->
							<td width="9%">
								<s:text name="refund.admin" />

							</td>
							<td width="25%">
								<select name="adminId" class="keyword">
									<option value="0">
										<s:text name="unchoose" />
									</option>
									<s:iterator value="adminList">
										<option value="<s:property value='id'/>">
											<s:property value="username" />
										</option>
									</s:iterator>
								</select>
							</td>
							<!-- status -->
							<td>
								<s:text name="refund.isGet" />
							</td>
							<td>
								<select name="isGet" class="keyword">
									<option value="0">
										<s:text name="unchoose" />
									</option>
									<option value="1">
										<s:text name="refund.notDeal" />
									</option>
									<option value="2">
										<s:text name="refund.haveDeal" />
									</option>
								</select>
							</td>

						</tr>
					</table>
					<!-- pagination -->
					<input name="pageNo" type="hidden" value="<s:property value='pageNo'/>" />
					<input name="pageSize" type="hidden" value="<s:property value='pageSize'/>" />
				</form>
			</div>
			<!-- data list -->
			<table class="discolor">
				<tr>
					<td>
						<s:text name="number" />
					</td>
					<td>
						<s:text name="refund.sno" />
					</td>
					<td>
						<s:text name="refund.terminal" />
					</td>
					
					<td>
						<s:text name="refund.order" />
					</td>
					 
					<td>
						<s:text name="refund.reason" />
					</td>
					<td>
						<s:text name="refund.type" />
					</td>
					<td>
						<s:text name="refund.amount" />
					</td>
					<td>
						<s:text name="refund.happen" />
					</td>
					<td>
						<s:text name="refund.isGet" />
					</td>
					<td>
						<s:text name="refund.admin" />
					</td>
				</tr>
				<s:iterator value="refundWrongList" var="refundWrong" status="row">
					<tr>
						<td>
							<input class="id" type="hidden" value="<s:property value='id' />" />
							<s:property value="#row.count" />
						</td>
						<td>
							<s:property value="sno" />
						</td>
						<td>
							<s:property value="terminalId" />
						</td>
						
						<td>
							<s:if test="type == 1">
								<s:property value="orderId" />
							</s:if>							
						</td>
						 
						<td>
							<s:if test="reason == 1">
								<s:text name="refund.machine" />
							</s:if>
							<s:else>
								<s:text name="refund.balance" />
							</s:else>
						</td>
						<td>
							<s:if test="type == 1">
								<s:text name="refund.change" />
							</s:if>
							<s:else>
								<s:text name="refund.back" />
							</s:else>
						</td>
						<td>
							<s:text name="formatMoney">
								<s:param value="amount" />
							</s:text>
						</td>
						<td>
							<s:text name="formatTime">
								<s:param value="happenTime" />
							</s:text>
						</td>
						<td class="dealStatus">
							<div>
								<s:if test="isGet == 2">
									<span class="haveDeal"><s:text name="refund.haveDeal" /><span class="dealTime"><s:text name="formatTime">
												<s:param value="dealTime" />
											</s:text>
									</span>
									<br />
										<label class="noneDeal">
											<s:text name="refund.noneDeal" />
										</label>
										<label class="reDeal">
											<s:text name="refund.reDeal" />
										</label>
									</span>
								</s:if>
								<s:else>
									<span class="noDeal"><s:text name="refund.notDeal" />
										<label class="nowDeal">
											<s:text name="refund.nowDeal" />
										</label>
									</span>
								</s:else>
							</div>
						</td>
						<td>
							<s:iterator value="adminList" var="user">
								<s:if test="id == #refundWrong.adminId">
									<s:property value="username" />
								</s:if>
							</s:iterator>
						</td>
					</tr>
				</s:iterator>
			</table>
		</div>
		<div class="page"></div>
	</body>
</html>
