<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/manage/util.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" >
	<head>
		<script type="text/javascript" src="<%=basePath%>manage/js/admin.js"></script>
		<script type="text/javascript">
			pageCount = '<s:property value="jsonMap.pageCount"/>';
			pageNo = '<s:property value="pageNo"/>';
			pageSize = '<s:property value="pageSize"/>';
			var count = "<s:property value='count'/>";
			
			delsure = "<s:text name='delsure'/>";
		</script>
		<title><s:text name="user.manage" /></title>
	</head>
	<body>
		<div class="title">
			<h3>
				<div>
					<s:text name="user.manage" />
				</div>
			</h3>
		</div>
		<div class="content">
			<a id="add" href="javascript:void(0)"><img src="<%=basePath%>manage/images/user_add<s:text name='language' />.jpg" > </a>
		</div>

		<div class="title">
			<h3>
				<div>
					<s:text name="user.list" />
				</div>
			</h3>
		</div>

		<div class="content discolor">
			<div>
				<s:text name="list.search" />
				:
				<input class="keyword" value="<s:property value='adminName' />"/>
				<input class="search" type="image" src="<%=basePath%>sysimg/search<s:text name='language' />.jpg" />
			</div>
			<form method="post">
				<table>
					<tr>
						<th width="10%">
							<s:text name="list.number" />
						</th>
						<th width="70%">
							<s:text name="user.name" />
						</th>
						<th width="20%">
							<s:text name="list.edit" />
						</th>
					</tr>
					<!-- adminList -->
					<s:iterator value="adminsList" var="a" status="row">
						<tr>
							<td>
								<s:property value="#row.count" />
								<input class="adminId" type="hidden" value="<s:property value='#a.id' />" />
							</td>
							<td>
								<s:property value="#a.username" />
							</td>
							<td>
								<a href="javascript:void(0)" class="mod"><img src="<%=basePath%>/sysimg/edit.gif" /> </a>
								<a href="javascript:void(0)" class="del"><img src="<%=basePath%>/sysimg/del.gif" /> </a>
							</td>
						</tr>
					</s:iterator>
				</table>
				<!-- edit-->
				<input type="hidden" name="adminId" />
				<!-- search -->
				<input type="hidden" name="adminName" />
				<!-- pagination -->
				<input name="pageNo" type="hidden" />
				<input name="pageSize" type="hidden" />
			</form>
		</div>
		<div class="page"></div>
	</body>
</html>
