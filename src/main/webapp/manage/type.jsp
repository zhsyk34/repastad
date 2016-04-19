<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/manage/commons.jsp"%>
<html>
	<head>
		<title></title>
		<link rel="stylesheet" type="text/css" href="manage/css/list.css" />
		<link rel="stylesheet" type="text/css" href="zhsy/css/page.css" />
		<link rel="stylesheet" type="text/css" href="zhsy/css/dialog.css" />
		<link rel="stylesheet" type="text/css" href="manage/css/type.css" />
		<script type="text/javascript" src="manage/js/commons.js"></script>
		<script type="text/javascript" src="zhsy/js/page.js"></script>
		<script type="text/javascript" src="zhsy/js/dialog.js"></script>
		<script type="text/javascript" src="manage/js/type.js"></script>
		<script type="text/javascript">
			var pageCount = "<s:property value='pageCount'/>";
			var pageNo = "<s:property value='pageNo'/>";
			var pageSize = "<s:property value='pageSize'/>";
			var count = "<s:property value='count'/>";
			
			var message = "<s:property value='message'/>";
			var delsure = "<s:text name='delsure'/>";
			var delcheck = "<s:text name='delcheck'/>";
			
			var typeName = "<s:text name='type.name'/>";
			var typeAdd = "<s:text name='type.add'/>";
			var typeMod = "<s:text name='type.mod'/>";
			
		</script>
	</head>
	<body>
		<div class="title">
			<h3>
				<div>
					<s:text name="type.type" />
				</div>
			</h3>
		</div>
		<div class="content">
			<img id="add" src="manage/images/foodtype_add<s:text name='language' />.jpg" />
		</div>
		<div style="height: 15px;"></div>
		<div class="title">
			<h3>
				<div>
					<s:text name="type.list" />
				</div>
			</h3>
		</div>
		<form method="post">
			<div class="content discolor">
				<div class="operation">
					<ul id="left">
						<li>
							<s:text name="name" />:
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

				<table>
					<tr>
						<th width="5%">
							<input type="checkbox" id="checkParent" />
						</th>
						<th width="25%">
							<s:text name="list.number" />
						</th>
						<th width="35%">
							<s:text name="name" />
						</th>
						<th width="35%">
							<s:text name="list.edit" />
						</th>
					</tr>
					<s:iterator value="foodTypeList" status="row">
						<tr>
							<td>
								<input type="checkbox" name="ids" value="<s:property value='id'/>" />
							</td>
							<td>
								<s:property value="#row.count" />
							</td>
							<td><s:property value="typeName" /></td>
							<td>
								<img class="mod" src="sysimg/edit.gif" alt="<s:text name='edit'/>" title="<s:text name='edit'/>" />
								<img class="del" src="sysimg/del.gif" alt="<s:text name='delete'/>" title="<s:text name='delete'/>" />
							</td>
						</tr>
					</s:iterator>
				</table>
			</div>
			<div class="dialog">
				<div class="title">
					<h3>
						<div id="editor">
						</div>
					</h3>
				</div>
				<div class="edit">
					<table>
						<tr>
							<th width="30%">
								<s:text name="type.name" />:
								<input name="id" type="hidden" />
							</th>
							<td width="70%">
								<input name="name" />
							</td>
						</tr>
					</table>
				</div>
				<div class="button">
					<img id="back" src="sysimg/back<s:text name='language' />.jpg" />
					<img id="save" src="sysimg/submit<s:text name='language' />.jpg" />
					<img id="reset" src="sysimg/reset<s:text name='language' />.jpg" />
				</div>
			</div>
			<!-- pagination -->
			<input name="pageNo" type="hidden" />
			<input name="pageSize" type="hidden" />
		</form>
		<div class="page"></div>
	</body>
</html>
