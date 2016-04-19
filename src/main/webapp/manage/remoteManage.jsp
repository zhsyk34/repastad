<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="util.jsp"%>
<html>
	<head>
		<title></title>
		<link href="<%=basePath%>manage/css/right.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="<%=basePath%>manage/js/jquery.ui.draggable.js"></script>
		<script type="text/javascript" src="<%=basePath%>manage/js/jquery.alerts.js"></script>
		<link href="<%=basePath%>manage/css/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
		<style type="text/css">
.timeDiv{
	width: 260px;
	height: 170px;
	position: absolute;left: 200px;top:120px;
	padding:5px;
	overflow:hidden;
	font-size:12px; 
	border: 1px solid #88acd9;
	background:#FFF;
	display: none;
	z-index: 3;
	text-align: center;
	line-height: 20px;
}
.timeDiv input{
 	margin: 2px;
}
.timeDiv input[type=button]{
 	width:50px;
 	height:22px;
}
#timeTitle{
	width: 100px;
	height: 20px;
	margin: 10px auto 0px;
	text-align: center;
}
.playTemplate{
	width: 450px;
	height: 400px;
	position: absolute;left: 200px;top:70px;
	padding:5px;
	overflow:hidden;
	font-size:15px; 
	border: 1px solid #88acd9;
	background:#FFF;
	display: none;
	z-index: 3;
}
.deleteTemplate{
	width: 450px;
	height: 400px;
	position: absolute;left: 200px;top:70px;
	padding:5px;
	overflow:hidden;
	font-size:15px; 
	border: 1px solid #88acd9;
	background:#FFF;
	display: none;
	z-index: 3;
}
</style>
		<script type="text/javascript" language="javascript">
	var basePath = '<%=basePath%>';
	var year = '';
	var month = '';
	var date = '';
	var hour = '';
	var minute = '';
	var second = '';
	var timeStr = '';
	var using = "${page.text51}";

	function fillNumber(number){
		if(number.length<=1){
			return '0'+number
		}else {
			return number;
		}
	}
	//顯示服務端時間
	function getServerTime(){
		$.ajax({
			url:basePath+'order/getServerTime.do',
			dataType:'json',
			data:'date='+new Date().getTime(),
			type:'post',
			success:function(data){
				if(data.success){
					year = ''+data.year;
					month= fillNumber(''+data.month);
					date = fillNumber(''+data.day);
					hour = fillNumber(''+data.hour);
					minute = fillNumber(''+data.minute);
					second = fillNumber(''+data.second);
					timeStr = year+'-'+month+'-'+date+' '+hour+':'+minute+':'+second;
					$('#nowTime').html(timeStr);
				}else {
					//alert('連接超時');
				}
			} 
		});
	}
	//${page.text37}時間
	function modifyServerTime(time){
		$.ajax({
			url:basePath+'modifyServerTime.do',
			dataType:'json',
			data:'serverTime='+time+'&date='+new Date().getTime(),
			type:'post',
			success:function(data){
				if(data.success){
					alert('${page.text1}');
				}else {
					alert('${page.text2}');
				}
			}
		});
	}
	//發送校正終端時間請求
	function sendOrder(terminalId,orderType,orderString){
		$.ajax({
			url:basePath+'sendOrder.do',
			async:false,
			dataType:'json',
			data:'terminalId='+terminalId+'&orderType='+orderType+'&orderString='+orderString+'&date='+new Date().getTime(),
			type:'post',
			success:function(data){
				if(data.success){
					alert('${page.text3}');					
				}else {
					alert('${page.text4}');
				}				
			}
		});
	}
	//檢查日期時間
	function checkDateTime(datatimeVal){
		var _reTimeReg = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/;
		if(_reTimeReg.test(datatimeVal)){
			return true;
		}else {
			return false;
		}
	}
	function checkTime(datatimeVal){
		var _reTimeReg = /^(\d{2}):(\d{2}):(\d{2})$/;
		if(_reTimeReg.test(datatimeVal)){
			return true;
		}else {
			return false;
		}
	}
	var getTerminalId=function(){
		var chooseTerminal = '';
		$("input:checked[name=id]").each(function(){
				chooseTerminal+=this.value+';';
		});
		if(chooseTerminal==''){
			alert('${page.text5}');
		}
		return chooseTerminal;
	}
	//选择校正
	function setcheckTime(){
		var tid = getTerminalId();
		if(tid!=''){
			sendOrder(tid,'timecheck','');
		}
	}
	//重啟終端
	function rebootTerminal(){
		var tid = getTerminalId();
		if(tid!=''){
			sendOrder(tid,'reboot','');
		}
	}
	
	//啓動終端
	function openTerminal(){
		var tid = getTerminalId();
		if(tid!=''){
			sendOrder(tid,'open','');
		}
	}
	//關閉終端
	function closeTerminal(){
		var tid = getTerminalId();
		if(tid!=''){
			sendOrder(tid,'close','');
		}
	}
	function shutdown(){
		var tid = getTerminalId();
		if(tid!=''){
			sendOrder(tid,'close','');
		}
	}
	

	//${page.text6}
	function setBootTime(){
		var tid = getTerminalId();
		if(tid==''){
			return;
		}
		$('#timeone').val('');
		$('#timetwo').val('');
		$('#timethree').val('');
		$("#timeTitle").html('${page.text6}');
		$("#show").show();
		$('.timeDiv').show();
		$(".timeDiv").css('left',($('body').width()-$(".timeDiv").width())/2);
		$('.timeDiv').css('top',$('body').scrollTop()+120);
		$('#bootBtn').show();
		$('#shutdownBtn').hide();
	}
	function submitBootTime(){
		var tid = getTerminalId();
		var timeone = $.trim($('#timeone').val());
		var timetwo = $.trim($('#timetwo').val());
		var timethree = $.trim($('#timethree').val());
		var time = '';
		if(timeone!=''){
			if(!checkTime(timeone)){
				alert('${page.text7}');
				$('#timeone').select();
				return;
			}
			time += timeone+';';
		}
		if(timetwo!=''){
			if(!checkTime(timetwo)){
				alert('${page.text7}');
				$('#timetwo').select();
				return;
			}
			time += timetwo+';';
		}
		if(timethree!=''){
			if(!checkTime(timethree)){
				alert('${page.text7}');
				$('#timethree').select();
				return;
			}
			time += timethree+';';
		}
		if(time==''){
			alert('${page.text8}');
			return;
		}
		$('.timeDiv').hide();
		$('#show').hide();
		sendOrder(tid,'boottime',time);
		$("form :checkbox").prop("checked",false);
		$('form[name=remoteManage]').submit();
	}

	//${page.text9}
	function setShutdownTime(){
		var tid = getTerminalId();
		if(tid==''){
			return;
		}
		$('#timeone').val('');
		$('#timetwo').val('');
		$('#timethree').val('');
		$("#timeTitle").html('${page.text9}');
		$("#show").show();
		$('.timeDiv').show();
		$(".timeDiv").css('left',($('body').width()-$(".timeDiv").width())/2);
		$('.timeDiv').css('top',$('body').scrollTop()+120);
		$('#shutdownBtn').show();
		$('#bootBtn').hide();
	}
	function submitShutdownTime(){
		var tid = getTerminalId();
		var timeone = $.trim($('#timeone').val());
		var timetwo = $.trim($('#timetwo').val());
		var timethree = $.trim($('#timethree').val());
		var time = '';
		if(timeone!=''){
			if(!checkTime(timeone)){
				alert('${page.text7}');
				$('#timeone').select();
				return;
			}
			time += timeone+';';
		}
		if(timetwo!=''){
			if(!checkTime(timetwo)){
				alert('${page.text7}');
				$('#timetwo').select();
				return;
			}
			time += timetwo+';';
		}
		if(timethree!=''){
			if(!checkTime(timethree)){
				alert('${page.text7}');
				$('#timethree').select();
				return;
			}
			time += timethree+';';
		}
		if(time==''){
			alert('${page.text7}');
			return;
		}
		$("#show").hide();
		$('.timeDiv').hide();
		sendOrder(tid,'shutdowntime',time);
		$("form :checkbox").prop("checked",false);
		$('form[name=remoteManage]').submit();
		
	}

	$(document).ready(function(){
		getServerTime();
		$("#prompt_button").click(function() {
			jPrompt('${page.text10}', timeStr, '', function(r) {
				if(r) {
					if(checkDateTime(r)){
						modifyServerTime(r);							
					}else {
						alert('${page.text11}');
					}
				}else {
				} 
			});
		});
		$("input[name=enableshutdown]").click(function(){
			if(this.checked){
				sendOrder(this.value,'enableshutdown','1');
			}else{
				sendOrder(this.value,'enableshutdown','0');
			}
			$("form :checkbox").prop("checked",false);
			$('form[name=remoteManage]').submit();
		});
		$("input[name=checkall]").click(function(){
			$("input[name=id]").prop("checked",$(this).prop("checked"));
		});
		setInterval('getServerTime()',1000);
	}); 
//獲取模板
	function findTemplate(terminalId,isDelete){
		$('#show').show();
		var $dom = $(".playTemplate");
		var templateId = $("img[name=selectTemplateBtn"+terminalId+"]").attr("play");
		if(isDelete){
			$dom = $(".deleteTemplate");
			templateId = $("img[name=deleteTemplateBtn"+terminalId+"]").attr("play");
		}
		$dom.css('left',($('body').width()-$(".deleteTemplate").width())/2);
		$dom.css('top',$('body').scrollTop()+120);
		$dom.show();
		$dom.find("#terminalName").html(terminalId);
		$dom.find("input[name=terminalId]").val(terminalId);
		$dom.find("table[id=selectTable]").html('<tr><td colspan="3" style="height:300px;">${page.text12}</td></tr>');
		$.ajax({
			url:basePath+'findTerminalDownTemplate.do',
			async:false,
			dataType:'json',
			data:'terminalId='+terminalId+'&date='+new Date().getTime(),
			type:'post',
			success:function(data){
				if(data.success){
					if(data.templateArray.length>0){
						var html = "";
						for(var i=0;i<data.templateArray.length;i++){
							html += '<tr> '
								  +' <td width="30%"><input type="'+(isDelete?'checkbox':'radio')+'" name="templatePlay" value="'+data.templateArray[i].id+'" '+(data.templateArray[i].isdown==1?'':'disabled')+'/></td>'
								  +' <td width="40%">'+data.templateArray[i].name+'</td>'
								  +' <td width="30%">'+(data.templateArray[i].isdown==1?'${page.text13}':'${page.text14}')+'</td>'
					           	  +' </tr>';
						}
						$dom.find("table[id=selectTable]").html(html);
						if(isDelete){
							$dom.find("input[name=templatePlay][value="+templateId+"]").prop("disabled",true);
							$dom.find("input[name=templatePlay][value="+templateId+"]").parent().next().next().html("${page.text15}");
						}else{
							$dom.find("input[name=templatePlay][value="+templateId+"]").prop("checked",true);
						}
					}else{
						$dom.find("table[id=selectTable]").html('<tr><td colspan="3" style="height:300px;">${page.text16}</td></tr>');
					}
				}else {
					$dom.find("table[id=selectTable]").html('<tr><td colspan="3" style="height:300px;">${page.text16}</td></tr>');
				}
			}
		});
	}
	//${page.text30}使用模板
	function savePlayTemplate(){
		var terminalId = $(".playTemplate input[name=terminalId]").val();
		var templateId = $(".playTemplate input[name=templatePlay]:checked").val();
		var templateName = $(".playTemplate input[name=templatePlay]:checked").parent("td").next().text();
		if(typeof templateId=='undefined'){
			alert("${page.text17}");
			return ;
		}
		$.ajax({
			url:basePath+'saveTerminalPlay.do',
			async:false,
			dataType:'json',
			data:'terminalId='+terminalId+'&templateId='+templateId+'&date='+new Date().getTime(),
			type:'post',
			success:function(data){
				if(data.success){
					alert("${page.text3}");
					location = location;
				}else{
					alert("${page.text4}");					
				}
			}
		});
	}
	//刪除使用模板
	function deleteTerminalTemplate(){
		var terminalId = $(".deleteTemplate input[name=terminalId]").val();
		var templateId = '';
		$(".deleteTemplate input[name=templatePlay]:checked").each(function(){
			templateId += this.value+',';
		});
		if(templateId==''){
			alert("${page.text18}");
			return ;
		}
		$.ajax({
			url:basePath+'deletetTerminalTemplate.do',
			async:false,
			dataType:'json',
			data:'terminalId='+terminalId+'&templateIdList='+templateId+'&date='+new Date().getTime(),
			type:'post',
			success:function(data){
				if(data.success){
					alert("${page.text3}");
					$('.deleteTemplate').hide();
					$('#show').hide();
				}else{
					alert("${page.text4}");					
				}
			}
		});
	}
</script>
	</head>
	<body>
		<div id="show" style="width:100%;height: 100%;position:fixed;left:0px;top:0px;background-color:#FFFFFF;z-index: 2;filter:alpha(opacity=50);opacity:0.5;display: none;">
		</div>
		<div class="timeDiv">
			<div id="timeTitle">
				${page.text6}
			</div>
			<font color="red">${page.text19}-- 13:30:00${page.text20}</font>
			<br />
			${page.text21}
			<input type="text" name="timeone" id="timeone" value="" />
			<br />
			${page.text22}
			<input type="text" name="timetwo" id="timetwo" value="" />
			<br />
			${page.text23}
			<input type="text" name="timethree" id="timethree" value="" />
			<br />
			<br />
			<input id="bootBtn" type="button" value="${page.text24}" onclick="submitBootTime();"></input>
			<input id="shutdownBtn" type="button" value="${page.text24}" style="display: none;" onclick="submitShutdownTime();"></input>
			<input type="button" value="${page.text25}" style="margin-left: 40px;" onclick="$('.timeDiv').hide();$('#show').hide();"></input>
		</div>
		<div class="playTemplate">
			<div style="width: 98%;height: 20px;border-bottom:1px solid #88acd9;margin-bottom:5px;padding:5px 5px;">
				${page.text26}
				<font color="green" id="terminalName"></font> ${page.text27}
				<input type="hidden" name="terminalId" value="" />
			</div>
			<div style="position: absolute;right: 5px;top: 5px;">
				<img onclick="savePlayTemplate();" style="cursor: pointer;" width="84" height="27" alt="${page.text28}" title="${page.text28}" src="${basePath }manage/images/btn1${lansuffix }.jpg" />
				<img onclick="$('.playTemplate').hide();$('#show').hide();" style="cursor: pointer;margin-left: 8px;" width="84" height="27" alt="${page.text29}" title="${page.text29}"
					src="${basePath }manage/images/btn2${lansuffix }.jpg" />
			</div>
			<div style="width: 100%;height:360px;overflow: auto;">
				<table width="100%" class="listtable" style="border-bottom: 0px;">
					<tr class="tableHeader">
						<td width="30%">
							${page.text30}
						</td>
						<td width="40%">
							${page.text31}
						</td>
						<td width="30%">
							${page.text32}
						</td>
					</tr>
					<tr>
						<td style="border-bottom: 0px;" width="30%">
							<input type="radio" name="templatePlay" value="0" />
						</td>
						<td style="border-bottom: 0px;" width="40%">
							${page.text33}
						</td>
						<td style="border-bottom: 0px;" width="30%"></td>
					</tr>
				</table>
				<table width="100%" class="listtable" id="selectTable">
					<tr>
						<td colspan="3" style="height:300px;">
							${page.text12}
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div class="deleteTemplate">
			<div style="width: 98%;height: 20px;border-bottom:1px solid #88acd9;margin-bottom:5px;padding:5px 5px;">
				${page.text26}
				<font color="green" id="terminalName"></font> ${page.text34}
				<input type="hidden" name="terminalId" value="" />
			</div>
			<div style="position: absolute;right: 5px;top: 5px;">
				<img onclick="deleteTerminalTemplate();" style="cursor: pointer;margin-left: 112px;" width="84" height="27" alt="${page.text28}" title="${page.text28}"
					src="${basePath }manage/images/btn1${lansuffix }.jpg" />
				<img onclick="$('.deleteTemplate').hide();$('#show').hide();" style="cursor: pointer;margin-left: 8px;" width="84" height="27" alt="${page.text29}" title="${page.text29}"
					src="${basePath }manage/images/btn2${lansuffix }.jpg" />
			</div>
			<div style="width: 100%;height:360px;overflow: auto;">
				<table width="100%" class="listtable" style="border-bottom: 0px;">
					<tr class="tableHeader">
						<td style="border-bottom: 0px;" width="30%">
							${page.text30}
						</td>
						<td style="border-bottom: 0px;" width="40%">
							${page.text31}
						</td>
						<td style="border-bottom: 0px;" width="30%">
							${page.text32}
						</td>
					</tr>
				</table>
				<table width="100%" class="listtable" id="selectTable">
					<tr>
						<td colspan="3" style="height:300px;">
							${page.text12}
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div id="program">
			<div class="content_t">
				<h3>
					<span>${page.text36}</span>
				</h3>
			</div>
			<div class="tables">
				<div class="marquee_s manager_s">
					<img style="vertical-align: bottom;" width="96" height="21" src="${basePath }manage/images/remote_title${lansuffix }.jpg" />
					<span style="font-size:16px;color:#4f6b72;" id="nowTime"></span>
					<img style="vertical-align: bottom;cursor: pointer;" id="prompt_button" title="${page.text37}" width="47" height="21" src="${basePath }manage/images/remote_btn1${lansuffix }.jpg" />
				</div>
				<c:if test="${! empty requestScope.lineinList}">
					<div class="manager">
						<img onclick="setcheckTime();" title="${page.text38}" width="64" height="27" src="${basePath }manage/images/remote_btn2${lansuffix }.jpg" style="cursor: pointer;" />
						<img onclick="rebootTerminal();" title="${page.text39}" width="89" height="27" src="${basePath }manage/images/remote_btn3${lansuffix }.jpg" style="cursor: pointer;" />
						<img onclick="shutdown();" title="關閉終端" width="89" height="27" src="${basePath }manage/images/remote_shutdown${lansuffix }.jpg" style="cursor: pointer;" />
						
						<img onclick="setBootTime();" title="${page.text6}" width="89" height="27" src="${basePath }manage/images/remote_btn4${lansuffix }.jpg" style="cursor: pointer;" />
						<img onclick="setShutdownTime();" title="${page.text9}" width="89" height="27" src="${basePath }manage/images/remote_btn5${lansuffix }.jpg" style="cursor: pointer;" />
						<img onclick="openTerminal();" title="${page.text40}" width="64" height="27" src="${basePath }manage/images/remote_btn7${lansuffix }.jpg" style="cursor: pointer;" />
						<img onclick="closeTerminal();" title="${page.text41}" width="64" height="27" src="${basePath }manage/images/remote_btn8${lansuffix }.jpg" style="cursor: pointer;" />
					</div>
				</c:if>
				<div class="manager_w">
					<form action="<%=basePath%>remoteManage.do" id="remoteManage" name="remoteManage" method="post">
						<table width="100%" class="discolor">
							<c:if test="${! empty requestScope.lineinList}">
								<tr>
								
									<th scope="col" width="3%">
										<input class="checkAll" type="checkbox" name="checkall" value="" />
									</th>
									
									<th scope="col" width="4%">
										${page.text42}
									</th>
									<th scope="col" width="14%">
										${page.text43}
									</th>
									<th scope="col" width="14%">
										TeamViewer
									</th>
									<th scope="col" width="15%">
										${page.text44}
									</th>
									<th scope="col" width="17%">
										${page.text45}
										<b>(${page.text46})</b>
									</th>
									<th scope="col" width="9%">
										${page.text47}
									</th>
									<th scope="col" width="10%">
										${page.text48}
									</th>
									<th scope="col" width="15%">
										${page.text49}
									</th>
								</tr>
								<c:forEach items="${requestScope.lineinList}" var="linein" varStatus="index">
									<tr>
										<td>
											<input type="checkbox" name="id" value="${linein.id }">
										</td>
										<td>
										
											${index.count }
										</td>
										<td>
											${linein.terminalId }
										</td>
										<td>
											${linein.teamViewerId }
										</td>
										<td>
											${linein.location }
										</td>
										<td>
											${linein.boottime}
										</td>
										<td>
											${linein.shutdowntime}
										</td>
										<td>
											<input type="checkbox" name="enableshutdown" value="${linein.id }" ${linein.enableshutdown==1? 'checked':'' }/>
										</td>
										<td>
											<c:if test="${linein.type==1}">
												<span class="template"> 
													<c:choose>
														<c:when test="${linein.templatePlay == -1}">
															<font color="red">${page.text50}</font>
														</c:when>
														<c:when test="${linein.templatePlay == 0}">${page.text51}- ${page.text52}</c:when>
														<c:otherwise>
															${page.text51}- ${linein.templateName}
														</c:otherwise>
													</c:choose> </span>
												<br />
												<img src="${basePath }manage/images/select_template${lansuffix }.png" name="selectTemplateBtn${linein.terminalId }" style="width: 66px;height:22px; margin-right:5px;cursor: pointer;"
													onclick="findTemplate('${linein.terminalId }',false);" play="${linein.templatePlay }" />
												<img src="${basePath }manage/images/delete_template${lansuffix }.png" name="deleteTemplateBtn${linein.terminalId }" style="width: 66px;height:22px;cursor: pointer;"
													onclick="findTemplate('${linein.terminalId }',true);" play="${linein.templatePlay }" />
											</c:if>
										</td>
									</tr>
								</c:forEach>
							</c:if>
							<c:if test="${empty requestScope.lineinList}">
								<tr>
									<td style="text-align: center;color:red;">
										${page.text53}
									</td>
								</tr>
							</c:if>
						</table>
						<c:if test="${! empty requestScope.lineinList}">
							<page:paper formName="remoteManage" startPage="${requestScope.pageBean.startPage}" maxPage="${requestScope.pageBean.maxPage}" pageSize="${requestScope.pageBean.pageSize}"
								rowCount="${requestScope.pageBean.rowCount}" language="${language}" />
						</c:if>
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
