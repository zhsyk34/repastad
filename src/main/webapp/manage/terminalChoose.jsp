<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="util.jsp"%>
<!DOCTYPE html >
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh" style="width: 100%">
	<head>
		<title>${page.text1}</title>
		<link rel="stylesheet" type="text/css" href="${basePath }manage/style/public.css" media="all" />
		<link rel="stylesheet" type="text/css" href="${basePath }manage/style/style.css" media="all" />
		<script type="text/javascript" src="${basePath }manage/js/jquery.js"></script>
		<script type="text/javascript" src="${basePath }manage/js/right.js"></script>
		<style type="text/css">
	.bar{
		width:120px;
		height:12px;
		border: 1px solid green;
		text-align: left;
		display: none;
	}
	.bar div{
		width:0px;
		height:100%;
		background:#F87C09;
	}
	</style>
		<script type="text/javascript" language="javascript">
		var basePath = '<%=basePath%>';
		var downTerminalId = '';
		var getBarTime = 1;
		var templateId = ${requestScope.templateId };
		//滾動條${page.text13}
		function setBar(tid,percent){
			var color = '#F87C09';
			if(percent>50){
				color = 'green';
			}
			$('#'+tid+'bar div').animate({'width':percent+'%'},500).css({'background':color});
		}
		//下載${page.text13}
		function getTemplateDownBar(){
			if(downTerminalId!=''){
				$.ajax({
					url:basePath+'findDownProgress.do',
					dataType:'json',
					data:'templateId='+templateId+'&terminalId='+downTerminalId+'&date='+new Date().getTime(),
					type:'post',
					success:function(data){
						if(data.success){
							var downArray = data.downArray;
							downTerminalId ='';
							if(downArray.length>0){
								for(var i=0;i<downArray.length;i++){
									//alert(data.downArray[i].down);
									$('input[name=terminalId][value='+downArray[i].terminalId+']').prop("checked",false);
									$('input[name=terminalId][value='+downArray[i].terminalId+']').prop("disabled",true);
									if(downArray[i].cancle){
										$('#'+downArray[i].terminalId+'cancle').html('<img width="37" height="20" alt="${page.text2}" src="'+basePath+'manage/images/c_btn2${lansuffix }.jpg" />');
										$('input[name=terminalId][value='+downArray[i].terminalId+']').prop("disabled",false);
										setBar(downArray[i].terminalId,0);
										$('#'+downArray[i].terminalId+'bar').fadeOut(200);
										$('#'+downArray[i].terminalId+'text').html('');
									}else{
										if(downArray[i].isdown){
											$('#'+downArray[i].terminalId+'cancle').html('<img width="37" height="20" alt="${page.text2}" src="'+basePath+'manage/images/c_btn2${lansuffix }.jpg" />');
											setBar(downArray[i].terminalId,100);
											$('#'+downArray[i].terminalId+'bar').fadeOut(500);
											$('#'+downArray[i].terminalId+'text').html('${page.text3}');
										}else{
											$('#'+downArray[i].terminalId+'cancle').html('<img width="37" onclick="cancleDown(\''+downArray[i].terminalId+'\');" height="20" alt="${page.text2}" src="'+basePath+'manage/images/c_btn1${lansuffix }.jpg" />');
											downTerminalId += downArray[i].terminalId+';';
											$('#'+downArray[i].terminalId+'bar').fadeIn(200);
											if(downArray[i].down!=0){
												var percent = Math.floor(100 * parseInt(downArray[i].down) / parseInt(downArray[i].total));
												$('#'+downArray[i].terminalId+'text').html('${page.text4} '+percent+'%');
												setBar(downArray[i].terminalId,percent);
											}else{
												$('#'+downArray[i].terminalId+'text').html('${page.text5}%');
												setBar(downArray[i].terminalId,0);
											}
										}
									}
								}
							}
							if(getBarTime>15){
								var terminalId = '';
								$('input[name=terminalId]').each(function(){
									terminalId += this.value+";"; 
								});
								downTerminalId = terminalId;
								getBarTime = 0;
							}
							getBarTime++;
							setTimeout("getTemplateDownBar()",1000);
						}else{
							setTimeout("getAllTerminalBar()",1000*30);
						}
					} 
				});
			}else{
				setTimeout("getAllTerminalBar()",1000*30);
			}
		}
		//${page.text2}下載
		function cancleDown(tid){
			$.ajax({
				url:basePath+'cancleDownRecord.do',
				dataType:'json',
				data:'templateId='+templateId+'&terminalId='+tid+'&date='+new Date().getTime(),
				type:'post',
				success:function(data){
					if(data.success){
						$('#'+tid+'cancle').html('<img width="37" height="20" alt="${page.text2}" src="'+basePath+'manage/images/c_btn2${lansuffix }.jpg" />');
						$('input[name=terminalId][value='+tid+']').prop("disabled",false);
						$('input[name=terminalId][value='+tid+']').prop("checked",false);
						$('#'+tid+'text').html('');
						setBar(tid,0);
						$('#'+tid+'bar').hide();
					}else {
						alert("${page.text6}");
					}
				} 
			});
		}
		//獲取終端編號
		var getTerminalId = function(){
			var terminalId = '';
			$('input[name=terminalId]:checked').each(function(){
				terminalId += this.value+";"; 
			});
			if(terminalId==''){
				alert('${page.text7}');
			}
			return terminalId;
		}
		//${page.text16}下載
		function downTemplate(){
			var terminalIdList = getTerminalId();
			if(terminalIdList!='' && templateId!=''){
				$.ajax({
					url:basePath+'saveDownRecord.do',
					dataType:'json',
					data:'templateId='+templateId+'&terminalId='+terminalIdList+'&date='+new Date().getTime(),
					type:'post',
					success:function(data){
						if(data.success){
							downTerminalId = terminalIdList;
							getTemplateDownBar();
						}else {
							alert("${page.text6}");
						}
					} 
				});
			}
		}
		//獲取所有終端${page.text13}
		function getAllTerminalBar(){
			var terminalId = '';
			$('input[name=terminalId]').each(function(){
				terminalId += this.value+";"; 
			});
			downTerminalId = terminalId;
			getTemplateDownBar();
		}
		//${page.text17}
		function selectAll(name){
			$('input[name='+name+']:enabled').prop('checked',true);
		}
		//${page.text2}${page.text10}
		function selectCancle(name){
			$('input[name='+name+']:enabled').prop('checked',false);
		}
		//${page.text18}
		function selectReverse(name){
			$('input[name='+name+']:enabled').each(function(){
				$(this).prop('checked',!this.checked);
			});
		}
		$(document).ready(function(){
			getAllTerminalBar();
		});
	</script>
	</head>
	<body>
		<div id="choose">
			<div class="content_t">
				<h3>
					<span>${page.text8}</span>
				</h3>
			</div>
			<div class="tables">
				<div class="c_radio">
					<input type="hidden" name="templateId" value="${requestScope.templateId }">
					${page.text9}${requestScope.templateName }
				</div>
				<table width="100%" class="discolor">
					<tr>
						<th scope="col" width="7%">
							${page.text10}
						</th>
						<th scope="col" width="28%">
							${page.text11}
						</th>
						<th scope="col" width="28%">
							${page.text12}
						</th>
						<th scope="col" width="18%">
							${page.text13}
						</th>
						<th scope="col" width="9%">
							${page.text14}
						</th>
					</tr>
					<c:if test="${! empty requestScope.lineinList}">

						<c:forEach items="${requestScope.lineinList}" var="linein" varStatus="index">
							<tr>
								<td>
									<input type="checkbox" name="terminalId" value="${linein.terminalId }">
								</td>
								<td>
									${linein.terminalId }
								</td>
								<td class="t_left">
									${linein.location }
								</td>
								<td class="t_left">
									<div id="${linein.terminalId }text"></div>
									<div id="${linein.terminalId }bar" class="bar">
										<div></div>
									</div>
								</td>
								<td>
									<a href="javascript:void(0);" title="${page.text2}" id="${linein.terminalId }cancle"> <img width="37" height="20" alt="${page.text2}"
											src="${basePath }manage/images/c_btn2${lansuffix }.jpg" /> </a>
								</td>
								<!-- <img width="37" height="20" alt="${page.text2}" src="${basePath }manage/images/c_btn1${lansuffix }.jpg"> -->
							</tr>
						</c:forEach>
					</c:if>
					<c:if test="${empty requestScope.lineinList}">
						<tr>
							<td colspan="5" style="text-align: center;color:red;">
								${page.text15}
							</td>
						</tr>
					</c:if>
				</table>
			</div>
		</div>
		<div class="c_btn" style="clear:both;">
			<div class="button">
				<input style="float:right;margin-right:100px;" type="image" onclick="downTemplate();" alt="${page.text16}" title="${page.text16}" src="${basePath }manage/images/btn1${lansuffix }.jpg" />
			</div>
			<div class="del" style="cursor: pointer;float:left;margin-left:30px;">
				<img onclick="selectAll('terminalId');" width="55" height="21" alt="${page.text17}" src="${basePath }manage/images/icon1${lansuffix }.png" />
				<img onclick="selectReverse('terminalId');" width="55" height="21" alt="${page.text18}" src="${basePath }manage/images/icon3${lansuffix }.png" />
				<img onclick="selectCancle('terminalId');" width="84" height="21" alt="${page.text2}${page.text10}" src="${basePath }manage/images/icon4${lansuffix }.png" />
			</div>
		</div>
	</body>

	<script type="text/javascript">
	<c:if test="${! empty requestScope.message}">
		alert('${requestScope.message}');
	</c:if>
</script>
</html>
