<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/manage/commons.jsp"%>
<html>
	<head>
		<title><s:text name="index.discount" /></title>
		<link rel="stylesheet" type="text/css" href="manage/css/list.css" />
		<link rel="stylesheet" type="text/css" href="zhsy/css/page.css" />
		<link rel="stylesheet" type="text/css" href="zhsy/css/dialog.css" />
		<link rel="stylesheet" type="text/css" href="manage/css/promotionFree.css" />
		<script type="text/javascript" src="manage/js/commons.js"></script>
		<script type="text/javascript" src="zhsy/js/page.js"></script>
		<script type="text/javascript" src="zhsy/js/dialog.js"></script>
		<script type="text/javascript" src="manage/js/discount.js"></script>

		<script src="manage/js/date/WdatePicker.js" type="text/javascript"></script>
		<script type="text/javascript">
			var pageCount = "<s:property value='pageCount'/>";
			var pageNo = "<s:property value='pageNo'/>";
			var pageSize = "<s:property value='pageSize'/>";
			var count = "<s:property value='count'/>";
		</script>

	</head>
	<body>
		<div class="title">
			<h3>
				<div>
					<s:text name="index.discount" />
				</div>
			</h3>
		</div>

		<div class="content ">
			<form method="post">
				<div class="operation">
					<ul id="left">
						<li>
							<s:text name="status.food" />
							:
						</li>
						<li>
							<input class="keyword" value="<s:property value='foodName'/>" name="foodName" />
						</li>
						<li>
							<s:text name="status.terminal" />
							:
						</li>
						<li>
							<input class="keyword" value="<s:property value='terminalNo'/>" name="terminalNo" />
						</li>

						<li>
							<img id="search" src="sysimg/search<s:text name='language' />.jpg" />
						</li>

						<li>
							<label>
								<input type="radio" name="status" value="all" <s:if test="status == 'all'">checked="checked"</s:if> />
								<s:text name="promotion.all" />
							</label>
							<label>
								<input type="radio" name="status" value="is" <s:if test="status == 'is'">checked="checked"</s:if> />
								<s:text name="promotion.discount" />
							</label>
							<label>
								<input type="radio" name="status" value="not" <s:if test="status == 'not'">checked="checked"</s:if> />
								<s:text name="promotion.notDiscount" />
							</label>
						</li>
					</ul>
					<div class="clear"></div>
					<ul id="left">
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
							<img id="delCheck" src="manage/images/restore<s:text name='language'/>.png" />
						</li>
						<li>
							<img id="batchSave" src="manage/images/setChoose<s:text name='language'/>.png" />
						</li>

					</ul>
				</div>

				<table class="discolor">
					<tr>
						<th width="5%">
							<input type="checkbox" id="checkParent" />
						</th>
						<th width="5%">
							<s:text name="list.number" />
						</th>
						<th width="10%">
							<s:text name="promotion.food" />
						</th>
						<th width="10%">
							終端編號
						<!-- 
							<s:text name="promotion.terminal" /> -->
						</th>
						<th width="10%">
							終端位置
						</th>
						<th width="30%">
							<s:text name="promotion.time" />
						</th>
						<th width="8%">
							<s:text name="promotion.original" />
						</th>
						<th width="8%">
							<s:text name="promotion.price" />
						</th>
						<th width="14%">
							<s:text name="edit" />
						</th>
					</tr>
					<s:iterator value="foodPromotionList" status="row">
						<tr>
							<td>
								<input type="checkbox" name="ids" value="<s:property value='id'/>" />
							</td>
							<td>
								<s:property value="#row.count" />
							</td>
							<td>
								<input type="checkbox" class="foodId" name="foodIds" value="<s:property value='foodId'/>" style="display: none;" />
								<span class="food"> <s:property value="foodName" /> <img src="<s:property value='path' />" /> </span>
							</td>
							<td>
								<input type="checkbox" class="terminalId" name="terminalIds" value="<s:property value='terminalId'/>" style="display: none;" />
								<s:property value="terminalNo" />
							</td>
							<td>
								<s:property value="location"/>
							</td>
							<td>
								<s:if test="pattern == 2">
									<s:text name="from" />
									<span class="beginTime"> <s:text name="formatTime1">
											<s:param value="begin" />
										</s:text> </span>
									<s:text name="to" />
									<span class="endTime"> <s:text name="formatTime1">
											<s:param value="end" />
									</span>
									</s:text>
								</s:if>

							</td>
							<td>
								<s:text name="formatMoney">
									<s:param value="OriginalPrice" />
								</s:text>
								<span style="display: none;" class="percent"> <s:property value="percent" /> </span>
							</td>
							<td class="price">
								<s:if test="pattern == 2">
									<s:text name="formatMoney">
										<s:param value="cheapPrice" />
									</s:text>
								</s:if>
							</td>
							<td>
								<s:if test="pattern == 2">
									<span class="del"><s:text name="restore" /> </span>
									<span class="mod"><s:text name="modify" /> </span>
								</s:if>
								<s:else>
									<span class="config"><s:text name="setting" /> </span>
								</s:else>
							</td>
						</tr>
					</s:iterator>
				</table>
				<!-- pagination -->
				<input name="pageNo" type="hidden" />
				<input name="pageSize" type="hidden" />

				<div class="dialog">
					<div class="title">
						<h3>
							<div>
								<s:text name="promotion.freeEdit" />
							</div>
						</h3>
					</div>
					<div>
						<table style="width:500px;">
							<tr>
								<td width="20%">
									<s:text name="promotion.type" />
								</td>
								<td>
									<input type="radio" name="type" value="1">
									<s:text name="promotion.cut" />
									<input type="radio" name="type" value="2">
									<s:text name="promotion.less" />
								</td>
							</tr>
							<tr class="type1">
								<td>
									<s:text name="promotion.cheapPrice" />
								</td>
								<td>
									<input class="keyword" name="price">
								</td>
							</tr>
							<tr class="type2">
								<td>
									<s:text name="promotion.percent" />
								</td>
								<td>
									<input class="keyword" name="percent" style="width:120px;">
									<span style="margin-left:5px;color:#FF0000;font-weight: 700;">%</span>
								</td>
							</tr>
							<tr>
								<td>
									<s:text name="promotion.time" />
									<s:text name="from" />
								</td>
								<td class="edit">
									<input id="d1" class="keyword" name="begin" readonly="readonly" />
									<img class="date" src="manage/images/date_icon2.jpg" onclick="WdatePicker({el:$dp.$('d1'),dateFmt:'yyyy-MM-dd HH:mm',maxDate:'#F{$dp.$D(\'d2\')}'})" />
								</td>
							</tr>
							<tr>
								<td style="text-align: center;">
									<s:text name="to" />
								</td>
								<td class="edit">
									<input id="d2" class="keyword" name="end" readonly="readonly" />
									<img class="date" src="manage/images/date_icon2.jpg" onclick="WdatePicker({el:$dp.$('d2'),dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'d1\')||\'%y-%M-%d %H:%m\'}'})" />
								</td>
							</tr>
						</table>
					</div>

					<div class="button">
						<img id="back" src="sysimg/back<s:text name='language' />.jpg" />
						<img id="save" src="sysimg/submit<s:text name='language' />.jpg" />
					</div>
				</div>
			</form>
		</div>


		<div class="page"></div>
	</body>
</html>
