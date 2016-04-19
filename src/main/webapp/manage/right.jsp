<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="util.jsp"%>
<html>
  <head>
    <title></title>
	<script type="text/javascript" language="javaxcript">
	var basePath = '${basePath}';
	var pageSize = 0;
	function getLineData(){
		var startPage = $('input[name=startPage]').val();
		if(typeof startPage=='undefined'){
			startPage = 0;
		}
		//獲取數據
		$.ajax({
			url:basePath+'searchTerminalDetect.do',
			type:'post',
			dataType:'json',
			data:'startPage='+startPage+'&pageSize='+pageSize+'&date='+new Date().getTime(),
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
		//alert(detect[0].id);
		var table = '<table width="100%" class="discolor">'
			+'<tr>'
				+'<th scope="col" width="13%">${page.text1}</th>'
				+'<th scope="col" width="28%">${page.text2}</th>'
				+'<th scope="col" width="17%">${page.text3}</th>'
				+'<th scope="col" width="16%">${page.text4}</th>'
				+'<th scope="col" width="14%">${page.text5}</th>'
				+'<th scope="col">${page.text6}</th>'
			+'</tr>';
		if(detect.length>0){
			for(var ti=0;ti<detect.length;ti++){
				table+='<tr>'
					+'<td><a href="javascript:void(0);" onclick="sub(\''+detect[ti].id+'\');" title="${page.text7}">'+detect[ti].id+'</a></td>'
					+'<td>'+detect[ti].location+'</td>'
					+'<td>'+detect[ti].time+'</td>'
					+'<td><b>'+(detect[ti].status=='runok'?'<span>${page.text8}</span>':'<b>${page.text9}</b>')+'</b></td>'
					+'<td><b>'+(detect[ti].online=='linein'?'<span>${page.text10}</span>':'<b>${page.text11}</b>')+'</b></td>'
					+'<td>'+(detect[ti].online=='linein'?'<a  target="_blank" href="'+basePath+detect[ti].picture+'?time='+new Date().getTime()+'">${page.text12}</a>':'<b>${page.text13}</b>')+'</td>'
					+'</tr>'
			}
		}else{
			table+='<tr><td colspan="6"><b>${page.text14}</b></td></tr>'
		}
		table+='</table> ';
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
				+(preDisabled==''?'onclick="$(\'input[name=startPage]\').val(1);getLineData();"':'')+'>${page.text15}</li>'
				+'<li class="prev" '+preDisabled
				+(preDisabled==''?'onclick="$(\'input[name=startPage]\').val('+(startPage-1)+');getLineData();"':'')+'>${page.text16}</li>'
				+'<li>|</li>'
				+'<li class="next" '+nexDisabled
				+(nexDisabled==''?'onclick="$(\'input[name=startPage]\').val('+eval(startPage+1)+');getLineData();"':'')+'>${page.text17}</li>'
				+'<li class="last" '+nexDisabled
				+(nexDisabled==''?'onclick="$(\'input[name=startPage]\').val('+maxPage+');getLineData();"':'')+'>${page.text18}</li>'
				+'<li>|</li>'
				+'<li>${page.text19}<input type="text" name="pageSize" value="'+pageSize+'" size="1"/> ${page.text20}| ${page.text21}'+maxPage+' ${page.text22}/'+rowCount+' ${page.text23}</li>'
				+'<li>|</li>'
				+'<li>'
				+'${page.text24}'
				+'<select id="pageSelect">'
					+selectButton
				+'</select>'
				+'${page.text22}'
				+'</li>'
			+'</ul>'
			+'</div>';
		if(detect.length<1){
			page = '';
		}
		$('#lineData').html(table+page);
		//parent.iframeShowHeight();
	}
	//終端監控詳細記錄
	function sub(no){
	  	window.location.href = basePath+'querySomeDetectrecords.do?no='+no;
	}
	$(document).ready(function(){
		$('#pageSelect').on("change",function(){
			$('input[name=startPage]').val(eval(this.value));
			getLineData();
		});
		$("html").off().on("keydown",function(event){  
	        if(event.keyCode==13){  
	        	pageSize = $('input[name=pageSize]').val();
	    		if(typeof pageSize=='undefined'){
	    			pageSize = 0;
	    		}
	        	getLineData();
	        }  
	    }); 
		getLineData();
		setInterval("getLineData", 1000*10);
	});
	</script>
	</head>
	<body>
		<div id="monitor">
			<div class="content_t"><h3><span>${page.text25}</span></h3></div>
			<div class="tables" id="lineData">
				
			</div>
		</div>
	</body>
	
<script type="text/javascript">
	<c:if test="${! empty requestScope.message}">
		alert('${requestScope.message}');
	</c:if>
</script>
</html>
