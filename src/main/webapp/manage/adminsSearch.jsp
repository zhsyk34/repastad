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
	<script type="text/javascript" src="${basePath }manage/js/right.js"></script>
	<script type="text/javascript" language="javascript">
	var basePath = '<%=basePath%>';
	//${page.text10}角色
	function deleteAdmins(id){
		var result = window.confirm('${page.text1}');
		if(result){
			$.ajax({
				url:basePath+'deleteAdmins.do',
				type:'post',
				dataType:'json',
				data:'id='+id+'&date='+new Date().getTime(),
				success:function(data){
					if(data.success){
						alert('${page.text2}');
						$('form[name=searchAdmins]').submit();						
					}else {
						if(data.errorMsg!='' && typeof (data.errorMsg) !='undefined'){
							alert(data.errorMsg);
						}
						if(data.infoInstruct!='' && typeof (data.infoInstruct) !='undefined'){
							alert(data.infoInstruct);
						}
					}
				}
			});	
		}
	}
</script>
	</head>
	<body>
		<div id="program">
			<div class="content_t"><h3><span>${page.text3}</span></h3></div>
			<div class="m_menu">
				<a href="${basePath }saveAdminsByPrepare.do"><img width="115" height="33" alt="${page.text4}" src="${basePath }manage/images/user_add${lansuffix }.jpg" /></a>
			</div>
			<div class="content_t"><h3><span>${page.text5}</span></h3></div>
			<div class="tables">
				<div class="user_list">
					<table width="100%" class="discolor">
						<tr>
							<th scope="col" width="20%">${page.text6}</th>
							<th scope="col" width="50%">${page.text7}</th>
							<th scope="col" width="30%">${page.text8}</th>
						</tr>
						<c:if test="${! empty requestScope.adminsList}">
                		<c:forEach var="admins" items="${requestScope.adminsList}" varStatus="index">
						<tr>
							<td>${index.count }</td>
							<td>${admins.username}</td>
							<td>
								<a href="${basePath }findAdminsById.do?id=${admins.id}" title="${page.text9}"><img width="21" height="21" src="${basePath }manage/images/edit_icon.gif" /></a>
								<a href="javascript:deleteAdmins(${admins.id});" title="${page.text10}"><img width="21" height="21" src="${basePath }manage/images/del_icon.gif" /></a>
							</td>
						</tr>
						</c:forEach>
						</c:if>
                		<c:if test="${empty requestScope.adminsList}">
               			<tr>
							<td colspan="3">${page.text11}</td>
						</tr>
                		</c:if>
					</table>
				</div>
				<c:if test="${! empty requestScope.adminsList}">
					<form action="<%=basePath%>searchAdmins.do" name="searchAdmins" method="post">
							<page:paper formName="searchAdmins"
										startPage="${requestScope.pageBean.startPage}"
										maxPage="${requestScope.pageBean.maxPage}"
										pageSize="${requestScope.pageBean.pageSize}"
										rowCount="${requestScope.pageBean.rowCount}" 
										language="${language}"
												/>
					</form>
				</c:if>
			</div>
		</div>
	</body>
	
<script type="text/javascript">
	<c:if test="${! empty requestScope.message}">
		alert('${requestScope.message}');
	</c:if>
</script>
</html>
