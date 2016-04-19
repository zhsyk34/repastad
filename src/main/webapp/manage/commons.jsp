<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<!DOCTYPE html>
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
	<base href="<%=basePath%>" />
	<script src="js/jquery.js"></script>
	<script src="manage/js/lang/lang_<%=request.getLocale().toString()%>.js"></script>
	<script>
		var result = {
			add : "<s:text name='add'/>",
			del : "<s:text name='delete'/>",
			mod : "<s:text name='modify'/>",
			success : "<s:text name='success'/>",
			fail : "<s:text name='fail'/>",
			delsure : "<s:text name='delsure'/>",
			cantNull : "<s:text name='cantNull'/>",
		}
	</script>
</head>
