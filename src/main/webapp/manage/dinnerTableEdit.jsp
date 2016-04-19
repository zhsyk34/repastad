<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/manage/util.jsp"%>
<html>
	<head>
		<script type="text/javascript" src="manage/js/commons.js"></script>
		<link rel="stylesheet" type="text/css" href="manage/css/dinnerTable.css" />
		<script type="text/javascript" src="manage/js/tableEdit.js"></script>
		<script type="text/javascript">
			var message = '<s:property value="message"/>';
			/*alert*/
			var tableName = "<s:text name='name'/>";
			var noValidate = "<s:text name='noValidate'/>";
			var cantNull = "<s:text name='cantNull'/>";
			var checkAgain = "<s:text name='checkAgain'/>";
			var terminalNo = "<s:text name='table.terminalId'/>";
			var suffix = "<s:text name='table.suffix'/>";
		</script>
		<title><s:text name="table.manage" /></title>
		<style type="text/css">
			div.content table.edit td input.text{
				width:200px;
			}
			
			
		</style>
	</head>
	<body>
		<div class="title">
			<h3>
				<div>
					<s:if test="dinnerTable != null">
						<s:text name="table.edit" />
					</s:if>
					<s:else>
						<s:text name="table.add" />
					</s:else>
				</div>
			</h3>
		</div>

		<div class="content">
			<form method="post" action="tableMerge.do">
				<table class="edit">
					<tr>
						<th width="10%">
							<s:text name="name" />
							<input type="hidden" name="id" value="<s:property value='dinnerTable.id' />" />
						</th>
						<td>
							<input class="text" name="name" value="<s:property value='dinnerTable.name' />" />
							<s:if test=" dinnerTable.id >0 ">
							</s:if>
							<s:else>
								<span id="batch"><s:text name="table.batch" /> </span>
								<span class="desc" style="display: none;"><s:text name="table.namedesc" /> </span>
							</s:else>
						</td>
					</tr>
					<tr id="suffix">
						<th>
							<s:text name="table.batch" />
							<input type="hidden" name="batch" value="0" />
						</th>
						<td>
							<s:text name="table.beginNo" />
							:
							<input id="begin" name="begin">
							&nbsp;
							<s:text name="table.endNo" />
							:
							<input id="end" name="end">
							<span class="desc"> <s:text name="table.batchDesc" /> </span>
						</td>
					</tr>
				</table>
			</form>
		</div>

		<div class="button">
			<input id="back" type="image" src="sysimg/back<s:text name='language' />.jpg" />
			<input id="save" type="image" src="sysimg/submit<s:text name='language' />.jpg" />
			<input id="reset" type="image" src="sysimg/reset<s:text name='language' />.jpg" />
		</div>
	</body>
</html>
