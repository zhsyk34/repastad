<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ include file="/manage/commons.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title></title>
		<link rel="stylesheet" href="css/commons.css" />
		<link rel="stylesheet" href="css/layout.css" />
		<link rel="stylesheet" href="js/plugin/css/page.css" />
		<link rel="stylesheet" href="js/plugin/css/link.css" />
		<link rel="stylesheet" href="js/plugin/css/dialog.css" />
		<link rel="stylesheet" href="manage/css/rule.css" />
		<!--  -->
		<script src="js/plugin/js/page.js"></script>
		<script src="js/plugin/js/dialog.js"></script>
		<script src="js/plugin/locale/plugin_<%=request.getLocale().toString()%>.js"></script>
		<script src="manage/js/commons.js"></script>
		<script src="manage/js/rule.js"></script>
		<script>
			var pageNo = parseInt("<s:property value='pageNo'/>");
			var pageSize = parseInt("<s:property value='pageSize'/>");
			var dataCount = parseInt("<s:property value='dataCount'/>");		
			var message = "<s:property value='message'/>";	
		</script>
	</head>
	<body>
		<div class="title">
			<h3>
				<div>
					<s:text name="orderRule" />
				</div>
			</h3>
		</div>
		<div class="content">
			<form method="post">
				<div class="operation">
					<ul id="left">
						<li>
							<a id="add" class="linkbutton linkbutton-primary"><s:text name="orderRuleAdd" /> </a>
						</li>
						<li id="desc">
							<s:text name="orderRuleDesc" />
						</li>
					</ul>
					<ul id="right">
						<li>
							<a class="linkbutton linkbutton-info" id="checkAll"><s:text name="checkAll" /> </a>
						</li>
						<li>
							<a class="linkbutton linkbutton-info" id="checkInvert"><s:text name="checkInvert" /> </a>
						</li>
						<li>
							<a class="linkbutton linkbutton-info" id="checkNone"><s:text name="checkNone" /> </a>
						</li>
						<li>
							<a class="linkbutton linkbutton-danger" id="delCheck"><s:text name="checkDel" /> </a>
						</li>
					</ul>
				</div>
				<div class="list">
					<table>
						<tr>
							<th width="5%">
								<input type="checkbox" id="checkParent" />
							</th>
							<th width="5%">
								<s:text name="list.number" />
							</th>
							<th width="45%">
								<s:text name="orderRuleConfig" />
							</th>
							<th width="20%">
								<s:text name="orderRuleisUsed" />
							</th>
							<th width="25%">
								<s:text name="list.edit" />
							</th>
						</tr>
						<s:iterator value="list" status="row">
							<tr>
								<td>
									<input type="checkbox" name="ids" value="<s:property value='id'/>" />
								</td>
								<td>
									<s:property value="#row.count" />
								</td>
								<td class="rule">
									<label>
										<s:text name="orderRuleBegin" />
										<span class="startNo"><s:property value="startNo" /> </span>
									</label>
									<label>
										<s:text name="orderRuleWith" />
										<s:text name="orderRuleTotal" />
										<span class="noNumber"><s:property value="noNumber" /> </span>
										<s:text name="orderRuleBit" />
									</label>
									<label>
										<s:text name="orderRuleStart" />
										<span class="minNumber"><s:property value="minNumber" /> </span>
									</label>
								</td>
								<td class="use">
									<input value="<s:property value='used'/>" type="hidden">
									<s:if test="used == 1">
										<span class="used"><s:text name="orderRuleused" /> </span>
										<a class="linkbutton linkbutton-disabled"><s:text name="orderRuleunused" /> </a>
									</s:if>
									<s:else>
										<span class="unused"><s:text name="orderRuleunused" /> </span>
										<a class="linkbutton linkbutton-success"><s:text name="orderRuleused" /> </a>
									</s:else>
								</td>
								<td>
									<img class="mod" src="sysimg/edit.gif" alt="<s:text name='edit'/>" title="<s:text name='edit'/>" />
									<img class="del" src="sysimg/del.gif" alt="<s:text name='delete'/>" title="<s:text name='delete'/>" />
								</td>
							</tr>
						</s:iterator>
					</table>
				</div>
				<!-- dialog -->
				<div class="dialog">
					<div class="title">
						<h3>
							<div></div>
						</h3>
					</div>
					<div class="edit">
						<table>
							<tr>
								<td>
									<input name="id" type="hidden">
									<input name="used" type="hidden">
									<s:text name="orderRuleBegin" />
									<input name="startNo" type="text">
									<s:text name="orderRuleWith" />
									<s:text name="orderRuleTotal" />
									<input name="noNumber" type="text">
									<s:text name="orderRuleBit" />
									<s:text name="orderRuleStart" />
									<input name="minNumber" type="text">
								</td>
							</tr>
						</table>
					</div>
					<div class="button">
						<img id="back" src="sysimg/back<s:text name='language' />.jpg" />
						<img id="save" src="sysimg/submit<s:text name='language' />.jpg" />
						<img id="reset" src="sysimg/reset<s:text name='language' />.jpg" />
					</div>
				</div>
				<!-- pagination -->
				<input name="pageNo" type="hidden" />
				<input name="pageSize" type="hidden" />
			</form>
		</div>
		<div id="page"></div>
	</body>
</html>
