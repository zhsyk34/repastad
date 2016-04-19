<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/manage/simple.jsp"%>
<html>
	<head>
		<title></title>
		<link rel="stylesheet" href="manage/css/list.css" />
		<link rel="stylesheet" href="js/plugin/css/link.css" />
		<link rel="stylesheet" href="js/plugin/css/page.css" />
		<link rel="stylesheet" href="js/plugin/css/dialog.css" />
		<link rel="stylesheet" href="manage/css/foodStatus.css" />
		<script src="manage/js/commons.js"></script>
		<script src="js/plugin/js/page.js"></script>
		<script src="js/plugin/js/dialog.js"></script>
		<script src="manage/js/foodStatus.js"></script>

		<script src="manage/js/date/WdatePicker.js" type="text/javascript"></script>

		<style>
		
			th{height: 30px;overflow: hidden;white-space: nowrap;}
		</style>

	</head>
	<body>
		<div class="title">
			<h3>
				<div>
					活動管理
				</div>
			</h3>
		</div>

		<div class="content ">
			<div class="operation">
				<ul id="left">
					<li>
						餐點名稱：
					</li>
					<li>
						<input class="keyword" id="foodName" />
					</li>
					<li>
						終端編號：
					</li>
					<li>
						<input class="keyword" id="terminalNo" />
					</li>

					<li>
						<button class="linkbutton linkbutton-primary" id="search">
							查詢
						</button>
					</li>

					<li>
						<label>
							<input type="radio" name="pattern" value="0" checked>
							全部
						</label>
						<label>
							<input type="radio" name="pattern" value="1">
							促銷
						</label>
						<label>
							<input type="radio" name="pattern" value="2">
							贈品
						</label>
						<label>
							<input type="radio" name="pattern" value="3">
							停售
						</label>
						<!-- 
						<label>
							<input type="radio" name="pattern" value="-1">
							未活動
						</label>
						 -->
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
						<img id="batch" src="manage/images/setChoose<s:text name='language'/>.png" />
					</li>
				</ul>
			</div>

			<table class="discolor">
				<thead>
					<tr>
						<th width="3%">
							<input type="checkbox" id="checkParent" />
						</th>
						<th width="3%">
							<s:text name="list.number" />
						</th>
						<th width="5%">
							終端編號
						</th>
						<th width="6%">
							終端位置
						</th>
						<th width="6%">
							餐點名稱
						</th>
						<th width="5%">
							活動類型
						</th>
						<th width="12%">
							活動時間
						</th>
						<th width="5%">
							數量
						</th>
						<th width="5%" class="send">
							送出
						</th>
						<th width="5%" class="remain">
							剩餘
						</th>
						<th width="5%" class="unit">
							每份
						</th>
						<th width="5%" class="price">
							原價
						</th>
						<th width="5%" class="discount">
							促銷價
						</th>
						<th width="8%">
							<s:text name="edit" />
						</th>
					</tr>
				</thead>
				<tbody id="data"></tbody>
			</table>
		</div>

		<div class="dialog">
			<div class="title">
				<h3>
					<div>
						設置
					</div>
				</h3>
			</div>
			<div class="edit">
				<table>
					<tr>
						<th width="30%">
							活動類型
							<input id="id" type="hidden">
						</th>
						<td width="70%">
							<label>
								<input type="radio" name="patterns" value="1" checked>
								促销
							</label>
							<label>
								<input type="radio" name="patterns" value="2">
								赠送
							</label>
							<label>
								<input type="radio" name="patterns" value="3">
								停售
							</label>
						</td>
					</tr>
					<tr class="dialog-stoptype">
						<th>
							停售方式
						</th>
						<td>
							<label>
								<input type="radio" name="stoptype" value="1" checked>
								日期
							</label>
							<label>
								<input type="radio" name="stoptype" value="2">
								數量
							</label>
						</td>
					</tr>
					<tr class="dialog-date">
						<th>
							開始日期
						</th>
						<td>
							<input id="d1" class="keyword" name="begin" readonly="readonly" />
							<img class="date" src="manage/images/date_icon2.jpg" onclick="WdatePicker({el:$dp.$('d1'),dateFmt:'yyyy-MM-dd HH:mm',maxDate:'#F{$dp.$D(\'d2\')}'})" />
						</td>
					</tr>
					<tr class="dialog-date">
						<th>
							結束日期
						</th>
						<td>
							<input id="d2" class="keyword" name="end" readonly="readonly" />
							<img class="date" src="manage/images/date_icon2.jpg" onclick="WdatePicker({el:$dp.$('d2'),dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'d1\')}'})" />
						</td>
					</tr>
					<tr class="dialog-count">
						<th>
							活動數量
						</th>
						<td>
							<input class="keyword" id="count">
						</td>
					</tr>
					<tr class="dialog-unit">
						<th>
							每份贈送
						</th>
						<td>
							<input class="keyword" id="unit">
						</td>
					</tr>
					<tr class="dialog-distype">
						<th>
							促銷方式
						</th>
						<td>
							<label>
								<input type="radio" name="distype" value="1" checked>
								降價
							</label>
							<label>
								<input type="radio" name="distype" value="2">
								打折
							</label>
						</td>
					</tr>
					<tr class="dialog-discount">
						<th>
							促銷價
						</th>
						<td>
							<input class="keyword" id="discount">
						</td>
					</tr>
					<tr class="dialog-percent">
						<th>
							打折
						</th>
						<td>
							<input class="keyword" id="percent">
							<span style="color:#f00;padding:3px;font-weight: 600;">%</span>
						</td>
					</tr>
				</table>
			</div>
			<div class="button">
				<button class="linkbutton linkbutton-warning" id="back">
					返回
				</button>
				<button class="linkbutton linkbutton-primary" id="save">
					送出
				</button>
				<button class="linkbutton linkbutton-success" id="reset">
					重置
				</button>
			</div>
		</div>


		<div id="page"></div>
	</body>
</html>
