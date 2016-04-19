<%@ page language="java" import="java.util.*" pageEncoding="utf-8" isELIgnored="false"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
  <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <script type="text/javascript" src="<%=basePath %>js/jquery.js"></script>
  	<script type="text/javascript" src="<%=basePath %>manage/js/marquee.js"></script>
  	<script type="text/javascript">
  		$(document).ready(function(){
  			var speed = parent.document.getElementById("speed").value;
  			var fontSize = parent.document.getElementById("fontSize").value;
  			var color = parent.document.getElementById("colors").value;
  			var background = parent.document.getElementById("background").value;
  			var direction = parent.document.getElementById("direct").value;
  			var fontfamily = parent.document.getElementById("fontFamily").value;
  			var content = parent.document.getElementById("content").value;
  			$('div').css('font-size', fontSize);
  			$('div').css('color', color);
  			$('div').css('font-family', fontfamily);
  			$('div').css('background', background);
  			$('#martest').html(content);
  			$('#martest').attr('scrollamount',speed);
  			$('#martest').attr('direction',direction);
  			$('marquee').marquee();
  		});
  	</script>
  </head>
  <body>
  		<div>
	  		<marquee id="martest" behavior="scroll" direction="left" scrollamount="5"></marquee>
  		</div>
  </body>
</html>
