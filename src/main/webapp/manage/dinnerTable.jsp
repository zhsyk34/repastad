<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/manage/commons.jsp"%>
<html>
	<head>
		<title></title>
		<script type="text/javascript" src="manage/js/commons.js"></script>
		<link rel="stylesheet" type="text/css" href="manage/css/list.css" />
		<link rel="stylesheet" type="text/css" href="zhsy/css/page.css" />
		<script type="text/javascript" src="zhsy/js/page.js"></script>

		<link rel="stylesheet" type="text/css" href="manage/css/dinnerTable.css" />
		<script type="text/javascript" src="manage/js/dinnerTable.js"></script>
		<script type="text/javascript">
			var pageCount = '<s:property value="pageCount"/>';
			var pageNo = '<s:property value="pageNo"/>';
			var pageSize = '<s:property value="pageSize"/>';
			var count = "<s:property value='count'/>";	
			
			
			var message = "<s:property value='message'/>";
			var delsure = "<s:text name='delsure'/>";
			var delcheck = "<s:text name='delcheck'/>";
		</script>
	</head>
	<body>
		<div class="title">
			<h3>
				<div>
					<s:text name="table.manage" />
				</div>
			</h3>
		</div>
		<div class="content">
			<img id="add" src="manage/images/tableAdd<s:text name='language' />.jpg" />
		</div>
		<div style="height:15px;"></div>

		<div class="title">
			<h3>
				<div>
					<s:text name="table.list" />
				</div>
			</h3>
		</div>
		<div class="content">
			<form method="post">
				<div class="operation">
					<ul id="left">
						<li>
							<s:text name="name" />
							:
						</li>
						<li>
							<input class="keyword" value="<s:property value='searchName' />" name="searchName" />
						</li>
						<li>
							<img id="search" src="sysimg/search<s:text name='language' />.jpg" />
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
				<table class="discolor">
					<tr>
						<th style="width:5%">
							<input type="checkbox" id="checkParent" />
						</th>
						<th style="width:10%">
							<s:text name="number" />
						</th>
						<th style="width:30%">
							<s:text name="name" />
						</th>
						<th style="width:20%">
							<s:text name="edit" />
						</th>
					</tr>
					<s:iterator value="dinnerTableList" status="row">
						<tr>
							<td>
								<input name="ids" type="checkbox" value="<s:property value='id' />" />
							</td>
							<td>
								<s:property value="#row.count" />
							</td>
							<td>
								<s:property value="name" />
							</td>
							<td>
								<img class="mod" src="sysimg/edit.gif" alt="<s:text name='modify'/>" title="<s:text name='modify'/>" />
								<img class="del" src="sysimg/del.gif" alt="<s:text name='delete'/>" title="<s:text name='delete'/>" />
							</td>
						</tr>
					</s:iterator>
				</table>
				<input type="hidden" name="id" />
				<input name="pageNo" type="hidden" value="<s:property value='pageNo'/>" />
				<input name="pageSize" type="hidden" value="<s:property value='pageSize'/>" />
			</form>
		</div>
		<div class="page"></div>
	</body>
</html>
