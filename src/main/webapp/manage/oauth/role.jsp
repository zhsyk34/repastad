<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/manage/util.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh">
	<head>
		<script type="text/javascript" src="<%=basePath%>manage/js/role.js"></script>
		<script type="text/javascript">
			pageCount = "<s:property value='jsonMap.pageCount'/>";
			pageNo = "<s:property value='pageNo'/>";
			pageSize = "<s:property value='pageSize'/>";
			var count = "<s:property value='count'/>";
			
			delsure = "<s:text name='delsure'/>";
		</script>
		<title><s:text name="role.manage" />
		</title>
	</head>
	<body>
		<div class="title">
			<h3>
				<div>
					<s:text name="role.manage" />
				</div>
			</h3>
		</div>
		<div class="content">
			<a id="add" href="javascript:void(0)"><img src="<%=basePath%>manage/images/role_add<s:text name='language' />.jpg" /> </a>
		</div>

		<div class="title">
			<h3>
				<div>
					<s:text name="role.list" />
				</div>
			</h3>
		</div>

		<div class="content discolor">
			<div>
				<s:text name="list.search" />
				:
				<input class="keyword" value="<s:property value='roleName'/>" />
				<input class="search" type="image" src="<%=basePath%>sysimg/search<s:text name='language' />.jpg" />
			</div>
			<form method="post">
				<table>
					<tr>
						<th width="10%">
							<s:text name="list.number" />
						</th>
						<th width="70%">
							<s:text name="role.name" />
						</th>
						<th width="20%">
							<s:text name="list.edit" />
						</th>
					</tr>
					<!-- roleList -->
					<s:iterator value="roleList" var="r" status="row">
						<tr>
							<td>
								<s:property value="#row.count" />
								<input class="roleId" type="hidden" value="<s:property value='#r.id'/>" />
							</td>
							<td>
								<s:property value="#r.name" />
							</td>
							<td>
								<a href="javascript:void(0)" class="mod"><img src="<%=basePath%>/sysimg/edit.gif" /> </a>
								<a href="javascript:void(0)" class="del"><img src="<%=basePath%>/sysimg/del.gif" /> </a>
							</td>
						</tr>
					</s:iterator>
				</table>
				<!-- edit-->
				<input type="hidden" name="roleId" />
				<!-- search -->
				<input type="hidden" name="roleName" />
				<!-- pagination -->
				<input name="pageNo" type="hidden" />
				<input name="pageSize" type="hidden" />
			</form>
		</div>
		<div class="page"></div>
	</body>
</html>
