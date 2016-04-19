<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="util.jsp"%>
<html>
	<head>
		<title></title>
		<script type="text/javascript" language="javascript">
	var isN = /^[0-9]*$/;
	var isEN = /^[a-zA-Z0-9]*$/;
	function checkAdd(){ 
	
		//tableNo
		var arr = "";
		$(":checkbox.dinner:checked").each(function(){
			arr += $(this).val()+",";
		});
		$("#tableNo").val(arr);
		
		var terminalId = $('input[name=terminalId]').val();
		if($.trim(terminalId)==''){
			alert('${page.text1}');
			return false;
		}
		if(!isEN.test(terminalId)){
			alert('${page.text2}');
			return false;
		}
		if($('input[name=isMulti]').prop("checked")){
			var terminalEnd = $('input[name=terminalIdEnd]').val();
			if($.trim(terminalEnd)==''){
				alert('${page.text3}');
				return false;
			}
			if(!isEN.test(terminalEnd)){
				alert('${page.text4}');
				return false;
			}
			if($.trim(terminalId).length != $.trim(terminalEnd).length){
				alert('${page.text5}');
				return false;
			}
			var sArray = $.trim(terminalId).split('');
			var eArray = $.trim(terminalEnd).split('');
			var startStr = '';
			var sNum = '';
			var eNum = '';
			for(var i=0;i<sArray.length;i++){
				if(sArray[i]==eArray[i]){
					startStr += sArray[i];
				}else{
					sNum += sArray[i];
					eNum += eArray[i];
				}
			}
			if(!isN.test(sNum) || !isN.test(eNum)){
				alert('${page.text6}');
				return false;
			}
			if(parseInt(sNum,10)>parseInt(eNum,10)){
				alert('${page.text7}');
				return false;
			}
			$('input[name=startStr]').val(startStr);
		}
		
		return true;
	}
	$(document).ready(function(){
		//$('#invoiceTr').hide();
		if('${requestScope.terminal.type}'!=''){
			$('input[name=type][value=${requestScope.terminal.type}]').attr('checked',true);
			if('${requestScope.terminal.type}'=='1'){
				$('#invoiceTr').show();
			}else{
				$('#invoiceTr').hide();
			}
		}
		if('${requestScope.terminal.invoice}'!=''){
			$('input[name=invoice][value=${requestScope.terminal.invoice}]').attr('checked',true);
		}
		$('input[name=isMulti]').click(function(){
			if(this.checked){
				$('span[id=multiSpan]').show();
			}else{
				$('input[name=startStr]').val('');
				$('input[name=terminalIdEnd]').val('');
				$('span[id=multiSpan]').hide();
			}
		});
		$('input[name=type]').click(function(){
			if(this.value==1){
				$('#invoiceTr').show();
			}else{
				$('#invoiceTr').hide();
				$('input[name=invoice][value=0]').attr('checked',true);
			}
		});
		table();
		
		//
		$("#back").click(function(){
			location.href = "searchTerminal.do";
		});
		$("#reset").click(function(){
			location.href = location.href;
		});
		$("#save").click(function(){
			$("form").submit();
		});
		
		//
		$("#checkAll").on("click",function(){
			$("input.dinner").prop("checked",$(this).prop("checked"));
		});
		
	});
	
	
	function table(){
		var tableNo = $("#tableNo").val();
		var tableArr = tableNo.split(",");
		$(":checkbox.dinner").each(function(){
			var v = $(this).val();
			if($.inArray(v,tableArr)>=0){
				$(this).prop("checked",true);
			}
		});
	}
	</script>
	</head>
	<body>
		<div id="program">
			<div class="content_t">
				<h3>
					<span> ${page.text8} </span>
				</h3>
			</div>
			<div class="tables">
				<div class="schedule_add user_add">
					<form action="<%=basePath%>saveTerminal.do" method="post" name="addTerminal" onsubmit="return checkAdd();">
						<input type="hidden" name="id" value="${requestScope.terminal.id }" />
						<table width="100%">
							<tr>
								<th scope="row" width="15%">
									${page.text9}
								</th>
								<td class="s_title" width="85%">
									<input type="hidden" name="startStr" id="startStr" value="" />
									<input type="text" name="terminalId" id="terminalId" style="width: 180px;" value="${requestScope.terminal.terminalId}" />
									<c:if test="${empty requestScope.terminal.id}">
										<span id="multiSpan" style="display: none;color: #000;"> - <input type="text" name="terminalIdEnd" id="terminalIdEnd" value="" style="width: 180px;" /> </span>
										<input type="checkbox" name="isMulti" id="isMulti" value="multi" style="width: 16px;border: 0px;" />${page.text10}
									</c:if>
									<b>*<s:text name="terminal-warning"></s:text> </b>
								</td>
							</tr>
							<tr>
								<th scope="row">
									${page.text11}
								</th>
								<td class="s_title">
									<input class="radio" type="radio" name="type" id="type1" value="1" checked />
									<label for="type1">
										${page.text12}
									</label>
									<input class="radio" type="radio" name="type" id="type2" value="2" />
									<label for="type2">
										${page.text13}
									</label>
									<b>*</b>
								</td>
							</tr>
							<tr id="invoiceTr">
								<th scope="row">
									${page.text14}
								</th>
								<td class="s_title">
									<input class="radio" type="radio" name="invoice" id="invoice1" value="1" />
									<label for="invoice1">
										${page.text15}
									</label>
									<input class="radio" type="radio" name="invoice" id="invoice2" value="0" checked />
									<label for="invoice2">
										${page.text16}
									</label>
									<b>*</b>
								</td>
							</tr>
							<tr>
								<th scope="row">
									${page.text17}
								</th>
								<td class="s_title">
									<input type="location" name="location" value="${requestScope.terminal.location}" />
									<b>*</b>
								</td>
							</tr>
							<tr>
								<th scope="row">
									TeamViewer
								</th>
								<td class="s_title">
									<input name="TeamViewerId" value="${requestScope.terminal.teamViewerId}" />
									<b>*</b>
								</td>
							</tr>
							<tr>
								<th>
									<s:text name="table.list" />
									<input name="dinnerTable" id="tableNo" type="hidden" value="${requestScope.terminal.dinnerTable}" />
									<div style="padding-top: 9px;">
										<input id="checkAll" type="checkbox" />
										<s:text name="unchoose" />
									</div>
								</th>
								<td>
									<ul style="list-style: none;">
										<s:iterator value="dinnerTableList">
											<li style="float:left;width:100px;padding:3px 2px 10px 0;">
												<input class="dinner" id="<s:property value='id'/>" type="checkbox" value="<s:property value='id'/>" />
												<label for="<s:property value='id'/>">
													<s:property value="name" />
												</label>
											</li>
										</s:iterator>
									</ul>
								</td>
							</tr>
						</table>
					</form>
					<div class="button">
						<input id="back" type="image" src="sysimg/back<s:text name='language' />.jpg" />
						<input id="save" type="image" src="sysimg/submit<s:text name='language' />.jpg" />
						<input id="reset" type="image" src="sysimg/reset<s:text name='language' />.jpg" />
					</div>
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
