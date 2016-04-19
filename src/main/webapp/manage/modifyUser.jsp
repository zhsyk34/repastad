<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="util.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh" style="width: 100%">
  <head>
    <title></title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=7" />
	<link rel="stylesheet" type="text/css" href="${basePath }manage/style/public.css" media="all" />
	<link rel="stylesheet" type="text/css" href="${basePath }manage/style/style.css" media="all" />
	<script type="text/javascript" src="${basePath }manage/js/jquery.js"></script>
	<script type="text/javascript" language="javascript">
	function checkModify(){ 
		var oldPassword = $('input[name=oldPassword]').val();
		var password = $('input[name=password]').val();
		var passwordcheck = $('input[name=passwordcheck]').val();
		if($.trim(oldPassword)==''){		
				alert('${page.text1}');
				return false;
		}
		if($.trim(password)=='' || $.trim(passwordcheck)==''){
			alert('${page.text2}');
			return false;
		}
		if(password!=passwordcheck){
			alert('${page.text3}');
			$('input[name=passwordcheck]').select();
			return false;
		}
		return true;
	}
	</script>
	</head>
	<body>
		<div id="program">
			<div class="content_t"><h3><span>${page.text4}</span></h3></div>
			<div class="tables">
				<div class="schedule_add user_add">
					<form action="<%=basePath %>updatePassword.do" method="post" name="updatePassword" onsubmit="return checkModify();">
						<table width="100%">
							<tr>
								<th scope="row">${page.text5}</th>
								<td class="s_title">
									<input type="password" name="oldPassword" /><b>*</b>
								</td>
							</tr>
							<tr>
								<th scope="row">${page.text6}</th>
								<td class="s_title">
									<input type="password" name="password" /><b>*</b>
								</td>
							</tr>
							<tr>
								<th scope="row">${page.text7}</th>
								<td class="s_title">
									<input type="password" name="passwordcheck" /><b>*</b>
								</td>
							</tr>
						</table>
						<div class="button">
							<input type="image" alt="${page.text8}" title="${page.text8}" src="${basePath }manage/images/btn1${lansuffix }.jpg" />
							<input type="reset" class="reset" title="${page.text9}" value="" />
						</div>
					</form>
				</div>
			</div>
		</div>
	</body>
	
<script type="text/javascript">
	<c:if test="${! empty requestScope.message}">
		alert('${requestScope.message}');
	</c:if>
</script>
</html>
