<%@ page language="java" import="java.util.*" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ page import="com.baiyi.order.util.BeanUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<title><s:text name="title"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<base href="<%=basePath%>"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>login/login.css" />
<script type="text/javascript" src="<%=basePath %>js/jquery.js"></script>
<script type="text/javascript">
	var basePath = '<%=basePath%>';
	var write = "<s:text name='login.write'/>";
	var remind = "<s:text name='login.remind'/>";
	var validate = "<s:text name='login.validate'/>";
	$(document).ready(function(){
		$('body').height($(window).height());
		    $("html").off().on("keydown",function(event){  
		        if(event.keyCode==13){  
		            loginto();
		        }  
		    }); 
	});
	function loginto(){
		var username = $('input[name=username]').val();
		var password = $('input[name=password]').val();
		var verifycode = $('input[name=verifycode]').val();
		if($.trim(username) =='' || $.trim(password) == ''){
			alert(write);
			return false;
		}
		if(verifycode == ''){
			alert(remind);
			return false;
		}
		document.loginform.submit();
	}
	function changeSrc(){
		$('#veryImg').attr('src','<%= basePath%>login/verycode.jsp?date='+new Date());
	}
	function reset(){
		$('input[name=username]').val('');
		$('input[name=password]').val('');
		$('input[name=verifycode]').val('');
	}
</script>
</head>
<body>
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td valign="middle"><table width="958" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td width="380" align="center" background="<%=basePath %>login/images/login_02.jpg"><div id="flashcontent" style="width: 380px; height: 368px">
        </div></td>
        <td width="449" align="center" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td><img src="<%=basePath %>login/images/login_03.gif" width="449" height="67" alt=""></td>
            </tr>
          </table>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td align="center" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img id="logo" width="416" height="33" alt="" src="
                      	<s:if test='#application.loginLogo == null'>
                      		sysimg/logo/login<s:text name='language'/>.jpg
                      	</s:if>
                      	<s:else>
                      		<s:property value='#application.loginLogo'/>
                     	 </s:else>
                      "/></td>
                    </tr>
                  </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td><img src="<%=basePath %>login/images/login_07.gif" width="416" height="35" alt=""></td>
                      </tr>
                    </table>
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <form action="<%=basePath %>login.do" method="POST" name="loginform" id="loginform">
                      <tr>
                        <td><img id="title" src="<%=basePath %>login/images/login_08${lansuffix }.gif" width="128" height="156" alt=""></td>
                        <td width="187" valign="top" background="<%=basePath %>login/images/login_09.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                              <td height="19" align="left"></td>
                            </tr>
                            <tr>
                              <td height="21" align="left"><input class="login" name="username" type="text" id="admin4" size="20" value=""></td>
                            </tr>
                            <tr>
                              <td height="8" align="center"></td>
                            </tr>
                            <tr>
                              <td height="21" align="left">
                              	<input class="login" name="password" type="password" id="admin" size="20" value="">
                              </td>
                            </tr>
                            <tr>
                              <td height="7" align="center"></td>
                            </tr>
                            <tr>
                              <td height="21" align="left"><table width="148" border="0" cellspacing="0" cellpadding="0">
                                  <tr>
                                    <td width="98"><input class=login-02 name="verifycode" type="text" maxlength=4 size=10></td>
                                    <td align="right"><img id="veryImg" src="<%=basePath %>login/verycode.jsp" width="44" height="22"></td>
                                  </tr>
                              </table></td>
                            </tr>
                            <tr>
                              <td height="12" align="center"></td>
                            </tr>
                            <tr>
                              <td align="left"><table width="151" border="0" cellspacing="0" cellpadding="0">
                                  <tr>
                                    <td align="left"><a href="#"><img src="<%=basePath %>login/images/login_17${lansuffix }.gif" width="55" height="23" border="0" id="login" onclick="loginto();"></a></td>
                                    <td align="right"><a href="#"><img src="<%=basePath %>login/images/login_18${lansuffix }.gif" width="55" height="23" onclick="reset();" border="0" id="cancle"></a></td>
                                  </tr>
                              </table></td>
                            </tr>
                        </table></td>
                        <td><img src="<%=basePath %>login/images/login_10.jpg" width="101" height="156" alt=""></td>
                      </tr>
                  </form>
                  </table>
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td><img src="<%=basePath %>login/images/login_11.gif" width="416" height="77" alt=""></td>
                      </tr>
                  </table></td>
                <td width="33" align="center" valign="top"><img src="<%=basePath %>login/images/login_06.gif" width="33" height="301" alt=""></td>
              </tr>
          </table></td>
        <td align="center" background="<%=basePath %>login/images/login_04.gif">&nbsp;</td>
      </tr>
    </table>
      <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td align="center"><img src="<%=basePath %>login/images/login_12.gif" width="760" height="25" alt=""></td>
        </tr>
      </table>
      <table width="700" border="0" align="center" cellpadding="0" cellspacing="0">
      </table></td>
  </tr>
</table>
</body>
<script type="text/javascript">
	$(document).ready(function(){
		<c:if test="${! empty requestScope.message}">
		alert('${requestScope.message}');
	</c:if>
	<c:if test="${! empty param.showMessage}">
		<c:choose>
			<c:when test="${param.showMessage==1}">
				alert(validate);
			</c:when>
		</c:choose>
	</c:if>
	});
</script>
</html>