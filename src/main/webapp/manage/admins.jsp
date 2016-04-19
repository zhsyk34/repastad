<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="util.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh" style="width: 100%">
  <head>
    <title></title>
	<script type="text/javascript" language="javascript">
	function checkAdd(){ 
		var username = $('input[name=username]').val();
		var password = $('input[name=password]').val();
		var repeatpass = $('input[name=passwordcheck]').val();
		if($.trim('${requestScope.admins.id}')==''){
			if($.trim(username)=='' || $.trim(password)=='' || $.trim(repeatpass)==''){			
				alert('${page.text1}');
				return false;
			}	
		}else if($.trim(username)==''){
			alert('${page.text2}');
			return false;
		}
		if(password!=repeatpass){
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
			<div class="content_t"><h3><span>
				${page.text4}
            </span></h3></div>
			<div class="tables">
				<div class="schedule_add user_add">
				<form action="<%=basePath %>saveAdmins.do" method="post" name="addAdmins" onsubmit="return checkAdd();">
					<input type="hidden" name="id" value="${requestScope.admins.id }" />
						<table width="100%">
							<tr>
								<th scope="row">${page.text5}</th>
								<td class="s_title">
									<input type="text" name="username" id="username" value="${requestScope.admins.username }" /><b>*</b>
								</td>
							</tr>
							<tr>
								<th scope="row">${page.text6}</th>
								<td class="s_title">
									<input type="password" name="password" value=""/><b>*</b>
								</td>
							</tr>
							<tr>
								<th scope="row">${page.text7}</th>
								<td class="s_title">
									<input type="password" name="passwordcheck" value=""/><b>*</b>
								</td>
							</tr>
						</table>
						<div class="button">
							<c:if test="${empty requestScope.admins.id }">
							<input type="image" alt="${page.text8}" title="${page.text8}" src="${basePath }manage/images/useradd_btn1${lansuffix }.jpg" />
							</c:if>     
            				<c:if test="${!empty requestScope.admins.id }">
            				<input type="image" alt="${page.text9}" title="${page.text9}" src="${basePath }manage/images/btn1${lansuffix }.jpg"  />
            				</c:if>
							<input type="reset" class="reset" title="${page.text10}" value="" />
							<img class="goback" onclick="window.location.href='${basePath }searchAdmins.do';" width="84" height="27" alt="${page.text11}" src="${basePath }manage/images/btn2${lansuffix }.jpg">
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
