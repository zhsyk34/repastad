<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="util.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh" style="width: 100%">
  <head>
    <title></title>
	<script language="javascript" type="text/javascript">
	var basePath = '${basePath }';
		$(document).ready(function(){
			getOrderVersion();
			getCookVersion();
		});
		function getOrderVersion(){
			$.ajax({
				url:basePath+'downterminal/orderversion.txt',
				type : "post",
				data:'time='+new Date().getTime(),
				success : function(data){
					$("#ordervesion").html("${page.text1}"+data);
				}
			});
		}
		function getCookVersion(){
			$.ajax({
				url:basePath+'downterminal/cookversion.txt',
				type : "post",
				data:'time='+new Date().getTime(),
				success : function(data){
					$("#cookvesion").html("${page.text1}"+data);
				}
			});
		}
	</script>
	</head>
	<body>
		<div id="program">
            <div class="content_t"><h3><span>${page.text2}</span></h3></div>
			<div class="m_menu">
				<a href="${basePath }downterminal/FoodOrderAd.jar" style="font-weight: bold;">${page.text3}</a>
				<span id="ordervesion" style="display: inline-block;margin-left: 25px;font-weight: bold;color:green;"></span>
			</div>
			<div class="content_t"><h3><span>${page.text4}</span></h3></div>
			<div class="m_menu">
				<a href="${basePath }downterminal/FoodCookAd_Win.jar" style="font-weight: bold;">${page.text3}(windows)</a>
				<a href="${basePath }downterminal/FoodCookAd_Linux.jar" style="font-weight: bold;">${page.text3}(linux)</a>
				<span id="cookvesion" style="display: inline-block;margin-left: 25px;font-weight: bold;color:green;"></span>
			</div>
		</div>
	</body>
</html>
