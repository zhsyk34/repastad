<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/manage/commons.jsp"%>
<!DOCTYPE>
<html>
	<head>
		<title></title>
		<link rel="stylesheet" type="text/css" href="css/commons.css" />
		<link rel="stylesheet" type="text/css" href="css/layout.css" />
		<link rel="stylesheet" type="text/css" href="js/plugin/css/page.css" />
		<link rel="stylesheet" type="text/css" href="js/plugin/css/link.css" />
		<link rel="stylesheet" type="text/css" href="zhsy/css/dialog.css" />
		<link rel="stylesheet" type="text/css" href="manage/css/taste.css" />
		<script type="text/javascript" src="manage/js/commons.js"></script>
		<script type="text/javascript" src="js/plugin/js/page.js"></script>
		<script type="text/javascript" src="js/plugin/locale/plugin_<%=request.getLocale().toString()%>.js"></script>
		<script type="text/javascript" src="zhsy/js/dialog.js"></script>
		<script type="text/javascript" src="manage/js/taste.js"></script>
		<script type="text/javascript">
			var pageNo = parseInt("<s:property value='pageNo'/>");
			var pageSize = parseInt("<s:property value='pageSize'/>");
			var dataCount = parseInt("<s:property value='count'/>");		
			var message = "<s:property value='message'/>";	
		</script>
	</head>
	<body>
		<div class="title">
			<h3>
				<div>
					<s:text name="taste" />
					<s:text name="list" />
				</div>
			</h3>
		</div>
		<div class="content">
			<form method="post">
				<div class="operation">
					<ul id="left">
						<li>
							<s:text name="name" />
						</li>
						<li>
							<input class="keyword" value="<s:property value='searchName' />" name="searchName" />
						</li>
						<li>
							<s:text name="type" />
						</li>
						<li>
							<select name="typeId">
								<option value="0">
									<s:text name="all" />
								</option>
								<s:iterator value="tasteTypeList">
									<option value="<s:property value='id'/>" <s:if test="id == typeId">selected="selected"</s:if>>
										<s:property value="name" />
									</option>
								</s:iterator>
							</select>
						</li>
						<li>
							<a class="linkbutton" id="search"><s:text name="search" /> </a>
						</li>
						<li>
							<a id="add" class="linkbutton"><s:text name="add" /> </a>
						</li>
					</ul>
					<ul id="right">
						<li>
							<a class="linkbutton" id="checkAll"><s:text name="checkAll" /> </a>
						</li>
						<li>
							<a class="linkbutton" id="checkInvert"><s:text name="checkInvert" /> </a>
						</li>
						<li>
							<a class="linkbutton" id="checkNone"><s:text name="checkNone" /> </a>
						</li>
						<li>
							<a class="linkbutton" id="delCheck"><s:text name="checkDel" /> </a>
						</li>
					</ul>
				</div>
				<div class="list">
					<table>
						<tr>
							<th width="5%">
								<input type="checkbox" id="checkParent" />
							</th>
							<th width="10%">
								<s:text name="list.number" />
							</th>
							<th width="20%">
								<s:text name="name" />
							</th>
							<th width="20%">
								附加費
							</th>
							<th width="20%">
								<s:text name="type" />
							</th>
							<th width="20%">
								<s:text name="list.edit" />
							</th>
						</tr>
						<s:iterator value="tasteVOList" status="row">
							<tr>
								<td>
									<input type="checkbox" name="ids" value="<s:property value='id'/>" />
									<input type="hidden" class="type" value="<s:property value='typeId'/>">
								</td>
								<td>
									<s:property value="#row.count" />
								</td>
								<td class="name">
									<s:property value="name" />
								</td>
								<td class="price">
									<s:property value="price" />
								</td>
								<td class="typeName">
									<s:property value="typeName" />
								</td>
								<td>
									<img class="mod" src="sysimg/edit.gif" alt="<s:text name='edit'/>" title="<s:text name='edit'/>" />
									<img class="del" src="sysimg/del.gif" alt="<s:text name='delete'/>" title="<s:text name='delete'/>" />
								</td>
							</tr>
						</s:iterator>
					</table>
				</div>
				<!-- pagination -->
				<input name="pageNo" type="hidden" />
				<input name="pageSize" type="hidden" />
				<!-- dialog -->
				<div class="dialog">
					<div class="title">
						<h3>
							<div></div>
						</h3>
					</div>
					<div class="edit">
						<table>
							<tr>
								<th width="25%">
									<s:text name="name" />
									<input name="id" type="hidden" />
								</th>
								<td width="75%" class="combox">
									<input type="text" name="name" />
								</td>
							</tr>
							<tr>
								<th>
									附加費
								</th>
								<td>
									<input type="text" name="price">
								</td>
							</tr>
							<tr>
								<th>
									<s:text name="type" />
								</th>
								<td class="combox">
									<input type="text" name="typeName">
									<select>
										<s:iterator value="tasteTypeList">
											<option value="<s:property value='id'/>">
												<s:property value="name" />
											</option>
										</s:iterator>
									</select>
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
			</form>
		</div>
		<div id="page"></div>
	</body>
</html>
