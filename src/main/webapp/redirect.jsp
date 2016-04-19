<%@ page language="java" import="java.util.*" pageEncoding="utf-8" isELIgnored="false"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!-- 從框架中跳轉到登錄頁面 -->
<script type="text/javascript">
	top.location.href='<%=basePath%>login/login.jsp';
</script>