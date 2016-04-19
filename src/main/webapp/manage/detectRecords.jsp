<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="util.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh" style="width: 100%">
  <head>
    <title></title>
	<script src="${basePath }manage/js/date/WdatePicker.js" type="text/javascript" ></script>
	<script type="text/javascript" language="javascript">
	var basePath = '<%=basePath%>';
	</script>
	</head>
	<body>
	<div id="t_logs">
			<div class="content_t"><h3><span>${page.text1}${no}${page.text2}</span></h3></div>
			<div class="tables">
				<form action="<%=basePath%>querySomeDetectrecords.do" name="detectRecords" method="post">
				<input type="hidden" name="no" value="${no}"/>
				<div class="tlogs_search">
						<table width="100%">
							<tr>
								<th scope="row" width="13%">${page.text3}</th>
								<td class="date">
									${page.text4}<input type="text" id="d1" name="begindate" value="${begindate}" readonly="readonly"/><img width="20" height="17" src="${basePath }manage/images/date_icon2.jpg" onclick="WdatePicker({el:$dp.$('d1'),dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
									${page.text5}<input type="text" id="d2" name="enddate" value="${enddate}" readonly="readonly"/><img width="20" height="17" src="${basePath }manage/images/date_icon2.jpg" onclick="WdatePicker({el:$dp.$('d2'),dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
								</td>
							</tr>
							<tr>
								<th scope="row" width="13%">${page.text6}</th>
								<td>
									<select name="querystatus">
										<option value="" ${querystatus==''?'selected':''}>${page.text7}</option>
										<option value="runok" ${querystatus=='runok'?'selected':''}>${page.text8}</option>
										<option value="closeorborke" ${querystatus=='closeorborke'?'selected':''}>${page.text9}</option>
									</select>
								</td>
							</tr>
							<tr>
								<th scope="row" width="13%">${page.text10}</th>
								<td>
									<select name="queryline">
										<option value="" ${queryline==''?'selected':''}>${page.text7}</option>
										<option value="linein" ${queryline=='linein'?'selected':''}>${page.text11}</option>
										<option value="lineout" ${queryline=='lineout'?'selected':''}>${page.text12}</option>
									</select>
								</td>
							</tr>
						</table>
						<div class="button">
							<input type="image" alt="${page.text13}" title="${page.text13}" onclick="$('input[name=startPage]').val(1);" src="${basePath }manage/images/btn3${lansuffix }.jpg" />
							<a href="${basePath }manage/right.jsp" title="${page.text14}" class="goback"><img width="84" height="27" alt="${page.text14}" src="${basePath }manage/images/btn2${lansuffix }.jpg" /></a>
						</div>
				</div>
				<div class="logs_list">
					<table width="100%" class="discolor">
						<tr>
							<th scope="col" width="13%">${page.text15}</th>
							<th scope="col" width="28%">${page.text16}</th>
							<th scope="col" width="16%">IP</th>
							<th scope="col" width="17%">${page.text17}</th>
							<th scope="col" width="14%">${page.text6}</th>
							<th scope="col">${page.text10}</th>
						</tr>
						<c:if test="${! empty requestScope.resultList}">
						<c:forEach items="${requestScope.resultList}" var="d" varStatus="index">
						<tr>
							<td>${d.no }</td>
							<td>${d.station }</td>
							<td>${d.ip }</td>
							<td>${d.addtime }</td>
							<td>${d.status=="runok"?"":"<b>"}${d.status=="runok"?page.text8:page.text18}${d.status=="runok"?"":"</b>"}</td>
							<td>${d.line=="linein"?"":"<b>"}${d.line=="linein"?page.text11:page.text12}${d.line=="linein"?"":"</b>"}</td>
						</tr>
						</c:forEach>
						</c:if>
						<c:if test="${empty requestScope.resultList}">
							<tr>
								<td colspan="6" style="font-weight: 800;text-align: center;color:red;">${page.text19}</td>
							</tr>
						</c:if>
					</table>
				</div>
				<c:if test="${! empty requestScope.resultList}">
					<page:paper formName="detectRecords"
								startPage="${requestScope.pageBean.startPage}"
								maxPage="${requestScope.pageBean.maxPage}"
								pageSize="${requestScope.pageBean.pageSize}"
								rowCount="${requestScope.pageBean.rowCount}" 
								language="${language}"
								/>
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
