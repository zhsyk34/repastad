<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/manage/util.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh">
	<head>
		<script type="text/javascript" src="<%=basePath%>manage/js/adminEdit.js"></script>
		<script type="text/javascript">
			var userExsit = "<s:text name='user.exist'/>";
			var userAdd = "<s:text name='user.add'/>";
			var wordDifferent = "<s:text name='user.wordifferent'/>";
			var namenull = "<s:text name='user.namenull'/>";
			var wordnull = "<s:text name='user.wordnull'/>";
			var roleIds ="<s:property value='roleIds'/>".split(",");
			var unrole = "<s:text name='user.unrole'/>";
		</script>
		<title><s:text name="user.edit" />
		</title>
	</head>
	<body>
		<div class="title">
			<h3>
				<div>
					<s:text name="user.edit" />
				</div>
			</h3>
		</div>

		<div class="content">
			<form method="post">
				<table class="edit">
					<tr>
						<th width="10%">
							<s:text name="user.name" />
						</th>
						<td>
							<input type="hidden" name="adminId" value="<s:property value='admins.id' />" />
							<input class="text" name="adminName" value="<s:property value='admins.username' />" />
							<span id="checkUserName"></span>
						</td>
					</tr>
					<tr>
						<th>
							<s:text name="user.password" />
						</th>
						<td>
							<input class="text" name="password" type="password" />
							<span id="checkPassword"></span>
						</td>
					</tr>
					<tr>
						<th>
							<s:text name="user.checkword" />
						</th>
						<td>
							<input class="text" name="checkword" type="password" />
							<span id="checkAgain"></span>
						</td>
					</tr>
					<!-- 
					<tr>
						<th>
							<s:text name="role.allot" />
						</th>
						<td>
							<s:iterator value="roleList" var="r">
								<input type="radio" value="<s:property value='#r.id' />" name="roleIds" />
								<s:property value="#r.name" />
							</s:iterator>
							<br/><span id="checkRole"></span>
						</td>
					</tr>
					 -->
				</table>
			</form>
		</div>

		<div class="button">
			<input id="back" type="image" src="<%=basePath%>sysimg/back<s:text name='language' />.jpg" />
			<input id="save" type="image" src="<%=basePath%>sysimg/submit<s:text name='language' />.jpg" />
			<input id="reset" type="image" src="<%=basePath%>sysimg/reset<s:text name='language' />.jpg" />
		</div>
	</body>
</html>
