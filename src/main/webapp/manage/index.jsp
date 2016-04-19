<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ page import="com.baiyi.order.util.BeanUtil"%>
<%
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html>
	<head>
		<title><s:text name='title' />
		</title>
		<base href="<%=basePath%>" />
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<!--  -->
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/plugin/js/accordion.js"></script>
		<link rel="stylesheet" type="text/css" href="zhsy/css/reset.css" />
		<link rel="stylesheet" type="text/css" href="manage/css/index.css"></link>
		<script type="text/javascript" src="manage/js/index.js"></script>
		<script type="text/javascript">
			var exitSure = "<s:text name='exitSure' />";
		</script>
	</head>
	<body>
		<div id="index">
			<div id="header">
				<div id="leftHead">
					<img id="indexLogo"
						src="
						<s:if test='#application.loginLogo == null'>
							sysimg/logo/index<s:text name='language' />.jpg
						</s:if>
						<s:else>
							<s:property value='#application.loginLogo' />
						</s:else>
					">
				</div>
				<div id="rightHead">
					<ul>
						<li link="manage/right.jsp">
							<img src="sysimg/index/top1.png"></img>
						</li>
						<li link="detectPicturePage.do">
							<img src="sysimg/index/top2.png"></img>
						</li>
						<li link="adminModWord.do">
							<img src="sysimg/index/top3.png"></img>
						</li>
						<li>
							<img src="sysimg/index/top4.png"></img>
						</li>
					</ul>
				</div>
			</div>
			<div id="container">
				<div id="leftCon">
					<p id="welcome">
						<s:text name='index.welcome' />
						<span> <s:property value='#session.user.username' /> </span>
						<s:text name='index.load' />
					</p>
					<div id="accordionWrap">
						<div id="accordion">
							<p>
								<s:text name='index.data' />
							</p>
							<ul>
								<li link="searchMaterial.do">
									<s:text name='index.material' />
								</li>
								<li link="typeFind.do">
									<s:text name='index.type' />
								</li>
								<li link="tasteFind.do">
									<s:text name='index.taste' />
								</li>
								<li link="searchFood.do">
									<s:text name='index.food' />
								</li>
								<li link="searchMarquee.do">
									<s:text name='index.marquee' />
								</li>
								<li link="searchTemplate.do">
									<s:text name='index.template' />
								</li>
								<li link="tableFind.do">
									<s:text name='table.manage' />
								</li>
							</ul>

							<p>
								<s:text name='index.business' />
							</p>
							<ul>
								<li link="findRule.do">
									<s:text name='index.orderNo' />
								</li>
								<li link="searchOrder.do" id="orderDetail">
									<s:text name='index.orderList' />
								</li>
								<li link="orderCalculate.do">
									<s:text name='index.orderCal' />
								</li>
								<!-- 
								<li link="manage/invoice.jsp">
									<s:text name='index.invoice' />
								</li>
								 -->
								<li link="order/refundList.do">
									<s:text name='index.refund' />
								</li>
								<li link="manage/foodStatus.jsp">
									活動管理
								</li>
								<!-- 
								<li link="foodStatusFind.do">
									<s:text name='index.stop' />
								</li>
								<li link="findDiscount.do">
									<s:text name='index.discount' />
								</li>
								<li link="findGift.do">
									<s:text name='index.gift' />
								</li>
								 -->
							</ul>

							<p>
								<s:text name='index.equipment' />
							</p>
							<ul>
								<li link="searchTerminal.do">
									<s:text name='index.terminal' />
								</li>
								<li link="detectPicturePage.do">
									<s:text name='index.monitoring' />
								</li>
								<li link="remoteManage.do">
									<s:text name='index.remote' />
								</li>
								<li link="manage/terminalsoft.jsp">
									<s:text name='index.softDown' />
								</li>
							</ul>

							<p>
								<s:text name='index.sysManage' />
							</p>
							<ul>
								<li link="getCon.do">
									<s:text name='index.sysConfig' />
								</li>
								<!-- 
								<li link="roleList.do">
									<s:text name='index.roleManage' />
								</li>
								 -->
								<li link="adminList.do">
									<s:text name='index.userManage' />
								</li>
								<li link="adminModWord.do">
									<s:text name='index.password' />
								</li>
								<li link="logout.do" id="logout">
									<s:text name='index.exit' />
								</li>
							</ul>
						</div>
					</div>
				</div>
				<div id="rightCon">
					<iframe src="manage/right.jsp" scrolling="auto"></iframe>
				</div>
			</div>
			<div id="footer">
				<span id="version"><s:text name="index.version" />V<%=BeanUtil.serverVersion%> </span>
				<span id="serverId"><s:text name="index.number" /> <%=BeanUtil.serverId%> </span>
				<span id="date"></span>
			</div>
		</div>