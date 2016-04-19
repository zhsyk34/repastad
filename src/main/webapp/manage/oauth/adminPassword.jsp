<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/manage/util.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh">
	<head>
		<script type="text/javascript" src="<%=basePath%>manage/js/adminPassword.js"></script>
		<script type="text/javascript">
			var wordDifferent = "<s:text name='user.wordifferent'/>";
			var namenull = "<s:text name='user.namenull'/>";
			var oldwrong = "<s:text name='user.oldwrong'/>";
			var wordnull = "<s:text name='user.wordnull'/>";
		</script>
		<title><s:text name="user.modword" /></title>
	</head>
	<body>
		<div class="title">
			<h3>
				<div>
					<s:text name="user.modword" />
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
						<td >
							<input type="hidden" name="adminId" value="<s:property value='admins.id' />" />
							<s:property value='admins.username' />
						</td>
					</tr>
					<tr>
						<th>
							<s:text name="user.oldword" />
						</th>
						<td>
							<input class="text" name="oldpassword" type="password" />
							<span id="checkOldPassword"></span>
						</td>
					</tr>
					<tr>
						<th>
							<s:text name="user.newword" />
						</th>
						<td>
							<input class="text" name="password" type="password" />
							<span id="checkPassword"></span>
						</td>
					</tr>
					<tr>
						<th>
							<s:text name="user.newcheck" />
						</th>
						<td>
							<input class="text" name="checkword" type="password" />
							<span id="checkAgain"></span>
						</td>
					</tr>
				</table>
			</form>
		</div>

		<div class="button">
			<input id="save" type="image" src="<%=basePath%>sysimg/submit<s:text name='language' />.jpg" />
			<input id="reset" type="image" src="<%=basePath %>sysimg/reset<s:text name='language' />.jpg" />
		</div>
	</body>
</html>
