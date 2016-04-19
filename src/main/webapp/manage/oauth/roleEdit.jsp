<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/manage/util.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh">
	<head>
		<script type="text/javascript" src="<%=basePath%>manage/js/roleEdit.js"></script>
		<script type="text/javascript">
			var roleExist = "<s:text name='role.exist'/>";
			var namenull = "<s:text name='role.namenull'/>";
			var authorityIds = "<s:property value='authorityIds'/>".split(",");
		</script>
		<title><s:text name="role.edit" /></title>
	</head>
	<body>
		<div class="title">
			<h3>
				<div>
					<s:text name="role.edit" />
				</div>
			</h3>
		</div>

		<div class="content">
			<form method="post">
				<table class="edit">
					<tr>
						<th width="10%">
							<s:text name="role.name" />
						</th>
						<td colspan="2">
							<input type="hidden" name="roleId" value="<s:property value='role.id'/>" />
							<input class="text" name="roleName" value="<s:property value='role.name'/>" />
							<span id="roleNameCheck"></span>
						</td>
					</tr>
					<tr>
						<th rowspan="20">
							<s:text name="authority.allot" />
						</th>

						<td width="18%">
							<input id="super" type="checkbox" name="checkbox" value="0" />
							<s:text name="authority.super" />
						</td>
						<td width="72%">
							<b><s:text name="authority.supernote" /> </b>
						</td>
					</tr>
					<s:iterator value="roleList" var="r">
						<tr>
							<td class="r_name">
								<input type="checkbox" class="checkAll" />
								<s:if test="language == '_cn'">
									<s:property value="#r.chName" />
								</s:if>
								<s:else>
									<s:property value="#r.twName" />
								</s:else>
							</td>
							<td class="r_name">
								<s:iterator value="jsonMap.authorityListMap[#r.id]" var="a">
									<!-- 權限 -->
									<input name="authorityIds" type="checkbox" value="<s:property value='#a.id'/>" />
									<s:if test="language == '_cn'">
										<s:property value="#a.chName" />
									</s:if>
									<s:else>
										<s:property value="#a.twName" />
									</s:else>
								</s:iterator>
							</td>
						</tr>
					</s:iterator>
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
