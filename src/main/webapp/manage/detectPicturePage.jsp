<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="util.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh" style="width: 100%">
	<head>
		<title></title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<style>
		.marquee_s{padding:2px;margin:0;border:0;}
		#setId{
			border:0;
		}
		div.tables table th{
			border:0;
		}
		
		</style>
		<script type="text/javascript" language="javaxcript">
	var pageSize = 0;
	var timer = null;
	
	function redraw(){
		clearInterval(timer);
		getLineData();
		//刷新频率
		timer = setInterval(getLineData, 10000);
	}
	function getLineData(){
		var startPage = $('input[name=startPage]').val();
		if(typeof startPage=='undefined'){
			startPage = 0;
		}
		var terminalId = $('input[name=terminalId]').val();
		var queryline = $('#queryline').val();
		//獲取數據
		$.ajax({
			url:basePath+'searchTerminalDetectPage.do',
			type:'post',
			dataType:'json',
			data:'startPage='+startPage+'&pageSize='+pageSize+'&terminalId='+terminalId+'&queryline='+queryline+'&date='+new Date().getTime(),
			success:function(data){
				if(data.success){
					pageSize = 0;
					setData(data);					
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
	function setData(result){
		var detect = result.detect;
		var startPage = result.startPage;
		var pageSize = result.pageSize;
		var rowCount = result.rowCount;
		var maxPage = result.maxPage;
		var table = '<div class="list">'
			+'<ul>';
		if(detect.length>0){
			for(var ti=0;ti<detect.length;ti++){
				table+='<li>'
					+(detect[ti].online=='linein'?'<a class="'+detect[ti].type+'" target="_blank" href="'+basePath+detect[ti].picture+'?time='+new Date().getTime()+'" title="'+detect[ti].location+'"><img class="'+detect[ti].type+'" src="'+basePath+detect[ti].picture+'?time='+new Date().getTime()+'" /></a>':'<a href="javascript:void(0);" title="'+detect[ti].location+'">${page.text1}</a>')
					+'<div>${page.text2}<span class="yellow">'+(detect[ti].online=='linein'?'${page.text3}':'${page.text4}')+'</span></div>'
					+'<div>${page.text5}<span class="red">'+(detect[ti].status=='runok'?'${page.text6}':'${page.text7}')+'</span></div>'
					+'<div>${page.text8}<span>'+detect[ti].id+'</span></div>'
					+'<div>${page.text9}<span title="'+detect[ti].location+'">'+detect[ti].location+'</span></div>'
					+'</li>'
			}
		}else{
			table+='<li style="width:100%;height:65px;font-size:32px;color:red;text-align:center;line-height:50px;">${page.text10}</li>';
		}
		table+='</ul> </div> ';
		var preDisabled = "";
		var nexDisabled = "";
		if (startPage >= maxPage){
			nexDisabled = "style=\"color: gray;cursor:auto;\"";
		}
		if (startPage <= 1){
			preDisabled = "style=\"color: gray;cursor:auto;\"";
		}
		var selectButton = "";
		for (var i = 1; i <= maxPage; i++) {
			if (i == startPage){
				selectButton+='<option value=' + i + ' selected>' + i + '</option>';
			}else{
				selectButton+='<option value=' + i + '>' + i + '</option>';
			}
		}
		var page = '<div id="page">'
			+'<input type="hidden" name="startPage" value="'+startPage+'"/>'
			+'<ul>'
				+'<li class="first" '+preDisabled
				+(preDisabled==''?'onclick="$(\'input[name=startPage]\').val(1);getLineData();"':'')+'>${page.text11}</li>'
				+'<li class="prev" '+preDisabled
				+(preDisabled==''?'onclick="$(\'input[name=startPage]\').val('+(startPage-1)+');getLineData();"':'')+'>${page.text12}</li>'
				+'<li>|</li>'
				+'<li class="next" '+nexDisabled
				+(nexDisabled==''?'onclick="$(\'input[name=startPage]\').val('+eval(startPage+1)+');getLineData();"':'')+'>${page.text13}</li>'
				+'<li class="last" '+nexDisabled
				+(nexDisabled==''?'onclick="$(\'input[name=startPage]\').val('+maxPage+');getLineData();"':'')+'>${page.text14}</li>'
				+'<li>|</li>'
				+'<li>${page.text15}<input type="text" name="pageSize" value="'+pageSize+'" size="1"/> ${page.text16}| ${page.text17}'+maxPage+' ${page.text18}/'+rowCount+' ${page.text19}</li>'
				+'<li>|</li>'
				+'<li>'
				+'${page.text20}'
				+'<select id="pageSelect">'
					+selectButton
				+'</select>'
				+'${page.text18}'
				+'</li>'
			+'</ul>'
			+'</div>';
		if(detect.length<1){
			page = '';
		}
		$('#detectpage').html(table+page);
		//parent.iframeShowHeight();
	}
	$(function(){
		redraw();
		$('#pageSelect').on("change",function(){
			$('input[name=startPage]').val(eval(this.value));
			redraw();
		});
		$('#queryline').on("change",function(){
			$('input[name=startPage]').val(1);
			redraw();
		});
		
		$("html").off().on("keydown",function(event){  
	        if(event.keyCode==13){
	        	$('input[name=startPage]').val(1);
	    		pageSize = $('input[name=pageSize]').val();
	    		if(typeof pageSize=='undefined'){
	    			pageSize = 0;
	    		}  
	        	redraw();
	        }  
	    }); 
	    $("#setId").click(function(){
	    	var tempId = $("#tempId").val();
	    	$('input[name=terminalId]').val(tempId);
	    	redraw();
	    });
	    
	});
	</script>
	</head>
	<body>
		<div id="monitor">
			<div class="content_t">
				<h3>
					<span>${page.text21}</span>
				</h3>
			</div>
			<div class="tables" id="lineData">
				<div class="tlogs_search">
					<table width="100%">
						<tr>
							<th scope="row" width="20%">
								${page.text22}
							</th>
							<td>
								<select id="queryline" name="queryline">
									<option value="" selected>
										${page.text23}
									</option>
									<option value="linein">
										${page.text3}
									</option>
									<option value="lineout">
										${page.text4}
									</option>
								</select>
							</td>
							<th scope="row" width="20%">
								${page.text24}
							</th>
							<td>
								<span class="marquee_s">
									<input type="hidden" name="terminalId" value="${terminalId}" />
									<input class="keyword" type="text" id="tempId"/>
									<input id="setId" class="submit" type="image" src="${basePath }manage/images/btn4${lansuffix }.jpg" />
								</span>
							</td>
						</tr>
					</table>
				</div>
				<div id="detectpage">
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
