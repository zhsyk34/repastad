<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.baiyi.order.util.BeanUtil"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>


<!DOCTYPE html>


<%
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
	String userLan = request.getLocale().toString();
	
	//	獲取頁面文字
	String name = request.getRequestURI();
	name = name.substring(name.lastIndexOf("/") + 1, name.lastIndexOf("."));
	pageContext.setAttribute("page", BeanUtil.multiLanguage.get((String) session.getAttribute("language")).get(name));
	pageContext.setAttribute("basePath",basePath );
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<base href="<%=basePath%>"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>manage/style/public.css" media="all" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>manage/style/style.css" media="all" />

<link rel="stylesheet" type="text/css" href="<%=basePath%>css/list.css" />

<script type="text/javascript" src="<%=basePath%>js/jquery.js"></script>
<script type="text/javascript" src="<%=basePath%>manage/js/right.js"></script>

<script type="text/javascript">
	var basePath = "<%=basePath%>";
</script>

<!-- pagination -->
<link rel="stylesheet" type="text/css" href="<%=basePath%>zhsy/css/page.css" />
<script type="text/javascript" src="<%=basePath%>zhsy/js/page.js"></script>
<!-- oauth -->
<script type="text/javascript" src="<%=basePath%>manage/js/oauth.js"></script>
<!-- script locale -->
<script type="text/javascript" src="<%=basePath%>manage/js/lang/lang_<%=userLan %>.js"></script>
</head>
