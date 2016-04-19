<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="util.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh" style="width: 100%">
	<head>
		<title></title>
		<link href="zhsy/css/slider.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${basePath }manage/lhgdialog/lhgcore.min.js"></script>
		<script type="text/javascript" src="${basePath }manage/lhgdialog/lhgdialog.min.js?s=default"></script>
		<link rel="stylesheet" href="<%=basePath%>manage/colorpicker/js_color_picker_v2.css" />
		<script src="<%=basePath%>manage/colorpicker/color_functions.js"></script>
		<script type="text/javascript" src="<%=basePath%>manage/colorpicker/js_color_picker_v2.js"></script>
		<script type="text/javascript" src="zhsy/js/slider.js"></script>
		<script type="text/javascript" src="<%=basePath%>manage/js/marquee.js"></script>
		<style type="text/css">
	div.marquee_add table td.color input{
		background: url("${basePath}manage/images/marquee_btn4${lansuffix }.jpg") no-repeat left top;
	}
	div.marquee_add .button input.reset{
		background: url("${basePath}manage/images/marquee_btn6${lansuffix }.jpg") no-repeat left top;
	}
	.button img{
		cursor:pointer;margin-right: 11px;
	}
</style>
		<script type="text/javascript" language="javascript">
	var basePath = '${basePath}';
	String.prototype.replaceAll = function(s1,s2) { 
	    return this.replace(new RegExp(s1,"gm"),s2); 
	}
	
	$(document).ready(function(){
		var screenWidth = document.body.clientWidth;
		$(".checkDiv").css("left",(screenWidth-$(".checkDiv").width())/2+"px");
		//document.onselectstart = new Function('return false');
		$('marquee').marquee();
		var size = '${requestScope.marquee.size}';
		var colorOrImg = '${requestScope.marquee.colorOrImg}';
		if(""!=$.trim(colorOrImg)){
			$('select[name=colorOrImg] option[value='+colorOrImg+']').attr("selected", true);
		}
		if(colorOrImg !='' && colorOrImg !=1 ){
			var img = '${requestScope.ImgStr}';
			$('#bgcolorSelect').attr('disabled',true);
			$('#imgSelect').attr('disabled',false);
			readImage(img,'bgcolorDiv');
		}
		var isRss = "${requestScope.marquee.isRss}";
		$('select[name=isRss]').val(isRss);
		if(isRss == 1){
			$('#rssTR').show();
			$('#conTR').hide();
			var content = "${requestScope.marquee.m_content}".split('@rss@');
			$('#rssUrl').val(content[0]);
			$('select[name=showType]').val(content[1]);
        	$('select[name=showNumber]').val(content[2]);
        	getContent();
		}else{
			$("#martest").attr("src",$("#martest").attr("src"));
		}
		if(size!=''){
			size = size.replace(/px/,'');
			$('select[name=size] option[value='+size+']').attr("selected", true);
		}
		$('#color').change(function(){
			//$('#colorArea').css('background-color',this.value);
			//$('#jgkdls').val(this.value);
			//document.frames('martest').location.reload();
		});
		$('input[name=direction]').click(function(){
			$('#direct').val(this.value);
			document.getElementById('martest').contentWindow.location.reload();
		});
		
		$('select[name=size]').change(function(){
			//$('div > font').attr('size',this.value);
			//$('div > font').html(this.value+'${page.text39}字');
			$('#fontSize').val(this.value);
			document.getElementById('martest').contentWindow.location.reload();
		});
		var fontfamily = '${requestScope.marquee.fontfamily}';
		if("" != $.trim(fontfamily)){
			$('select option[value='+fontfamily+']').attr("selected", true);
		}
		$('select[name="fontfamily"]').change(function(){
			$('#fontFamily').val(this.value);
			document.getElementById('martest').contentWindow.location.reload();
		});
		
		var scale = 5;
	     <c:if test="${marquee.speed!=null}">
	        	scale = parseFloat('${marquee.speed}');
	        	if(scale<0 || scale>10){
	        		scale = 5;
	        	}
	     </c:if>
		
		//速度條
			$("#slidercontainer1").slider({
				percent : scale*10,
				 size: { barWidth: 200, sliderWidth: 3 },
				 onStop:function(){
				 	var percent = this.percent;
				 	$('#speed').val(percent/10);
                	document.getElementById('martest').contentWindow.location.reload();
				 }
			});
                	
		
	});

	//錯誤
	function errorPic(obj){
		obj.src="<%=basePath%>manage/images/nosubnail${lansuffix }.jpg";
	}
	//修改读取的时候生成缩略图
	function readImage(itemStr,divId){
		if(itemStr!=''){
			var itemAarray = itemStr.split(';');
			for(var i = 0;i<itemAarray.length-1;i++){
				var uriPath = itemAarray[i];
				var imgId = uriPath.substring(uriPath.lastIndexOf('<'));
				imgId = imgId.replace('<','').replace('>','');
				uriPath = uriPath.substring(0,uriPath.lastIndexOf('<'));
				if(uriPath.indexOf('flv')!=-1){
					uriPath = uriPath.replace(/flv/,'jpg');//视频缩略图
				}else {
					//uriPath = uriPath.replace(/\./,'_min.');//图片缩略图
					var ext = uriPath.substring(uriPath.lastIndexOf('.'));
					uriPath = uriPath.substring(0,uriPath.lastIndexOf('.'));
					uriPath = uriPath+'_min'+ext;
				}
				var str = '<img id="'+divId+imgId+'" src="'+basePath+uriPath.replace("_min","")+'" width="100%" height="100%" onerror="errorPic(this)"/>';
				$('#'+divId).append(str);
				$('#background').val('url('+basePath+uriPath.replace("_min","")+') no-repeat left top');
			}
		}
	}
	//选择素材后显示缩略图
	function createImage(divId,imgPath,imgId){
		var img = '<img id="'+divId+imgId+'" src="'+imgPath+'" width="50" height="50" style="padding:0px 2px 0px 2px" onerror="errorPic(this)"/>';
		$('#'+divId).append(img);
		$('#background').val('url('+imgPath.replace("_min","")+') no-repeat left top');
		document.getElementById('martest').contentWindow.location.reload();
	}
	//删除缩略图
	function deleteImage(divId,imgId){
		$('#'+divId+imgId).remove();
	}
	var basePath = "<%=basePath%>";
	//保存時檢查
	function checkSave(){
		if($.trim($('input[name=title]').val())==''){
			alert('${page.text1}');
			return false;
		}
		var isRss = $('select[name=isRss]').val();
		if(isRss == 1){
			var url = $.trim($('#rssUrl').val());
			var rssNews = getRssNews(url);
			if (!checkUrl(url) && rssNews.length<1){
	        	alert('${page.text2}');
	        	return false;
			}
			var type = $('select[name=showType]').val();
        	var number = $('select[name=showNumber]').val();
        	$('textarea[name=content]').val(url+'@rss@'+type+'@rss@'+number);
		}
		var content = $('textarea[name=content]').val();
		if($.trim(content)==''){
			alert('${page.text3}');
			return false;
		}
		return true;
	}
	//選擇顏色或圖片
	function bgOrImg(){
		var colorOrImg = $('select[name=colorOrImg]').val();
		if(colorOrImg == 1){
			$('#bgcolorSelect').attr('disabled',false);
			$('#imgSelect').attr('disabled',true);
			$('#bgcolorDiv').html('');
		}else{
			$('#bgcolorSelect').attr('disabled',true);
			$('#imgSelect').attr('disabled',false);
		}
		$('#bgcolor').val('');
		$('#background').val('');
		document.getElementById('martest').contentWindow.location.reload();
	}
	//打開圖片
	function openImg(){
		var dg = new J.dialog({ id:"bgcolor",title:"${page.text4}",loadingText:'${page.text5}',iconTitle:false,width:800, height:500,page:basePath+"searchMaterial.do?select=true&searchType=1",cover:true,left:100, top:40});
		dg.ShowDialog();
	}
	//設置圖片值
	function setImage(inputId,value,name,url){
		$(inputId).val(value);
		$('#bgcolorDiv').html('<img src="'+url+'" width="100%" height="100%"/>');
		$('#background').val('url('+url.replace("_min","")+') no-repeat left top');
		document.getElementById('martest').contentWindow.location.reload();
	}
	function changeRss(){
		var isRss = $('select[name=isRss]').val();
		if(isRss == 0){
			$('#conTR').show();
			$('#rssTR').hide();
		}else{
			$('#rssTR').show();
			$('#conTR').hide();
		}
	}

	function getContent(){
		var url = $.trim($('#rssUrl').val());
		$('#rssError').html('');
		if (!checkUrl(url)){
        	$('#rssError').html('${page.text2}');
        }else{
        	var rssNews = getRssNews(url);
        	var type = $('select[name=showType]').val();
        	var number = $('select[name=showNumber]').val();
        	if(rssNews.length>0){
				var marqueeContent = '';
				if(type=='0'){
					for(var i=0;i<number;i++){
						marqueeContent += rssNews[i].title+'<span style=\"margin:0 1em\"></span>';
					}
				}else if(type=='1'){
					for(var i=0;i<number;i++){
						marqueeContent += rssNews[i].description+'<span style=\"margin:0 1em\"></span>';
					}
				}else{
					for(var i=0;i<number;i++){
						marqueeContent += rssNews[i].title+'<span style=\"margin:0 1em\"></span>'+rssNews[i].description+'<span style=\"margin:0 1em\"></span>';
					}
				}
				$('#content').val(marqueeContent);
				$("#martest").attr("src",$("#martest").attr("src"));
			}else{
				$('#rssError').html('${page.text2}'); 
			}
        }
	}

	//檢查url地址
	function checkUrl(url){
		<%--
		var strRegex = "^http://"  
         + "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" //ftp的user@  
         + "(([0-9]{1,3}\.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184  
         + "|" // 允许IP和DOMAIN（域名） 
         + "([0-9a-z_!~*'()-]+\.)*" // 域名- www.  
         + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\." // 二级域名  
        + "[a-z]{2,6})" // first level domain- .com or .museum  
        + "(:[0-9]{1,4})?" // 端口- :80  
        + "((/?)|" // a slash isn't required if there is no file name  
        + "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";+
		
        var re=new RegExp(strRegex);
        return re.test(url);
        --%>
        return url.indexOf("http://")!=-1;
	}
	//獲取rss${page.text12}
	function getRssNews(rssUrl){
		var rssNews = new Array();
		$.ajax({
			type:"POST",
			async : false,
			url:basePath+"getRssNews.do",
			data:"rssUrl="+encodeURIComponent(rssUrl)+"&date=" + new Date().getTime(), 
			dataType:"json",
			success:function (data) {
				if(data.success){
					rssNews = data.rss;
				}
			}
		});
		return rssNews;
	}
	</script>
	</head>
	<body>
		<div id="marquee">
			<div class="content_t">
				<h3>
					<span>${page.text8}</span>
				</h3>
			</div>
			<div class="tables">
				<div class="marquee_add">
					<form action="<%=basePath%>saveMarquee.do" method="post" name="addMarquee" onsubmit="return checkSave();">
						<table width="100%">
							<tr>
								<th scope="row" width="13%">
									${page.text9}
								</th>
								<td class="title">
									<input name="title" type="text" value="${requestScope.marquee.title }" />
									<b>*</b>
								</td>
							</tr>
							<tr style="display: none;">
								<th scope="row" width="13%">
									${page.text10}
								</th>
								<td>
									<!-- 選擇 -->
									<select name="isRss" onchange="changeRss();">
										<option value="0" selected="selected">
											${page.text11}
										</option>
										<option value="1">
											RSS${page.text12}
										</option>
									</select>
								</td>
							</tr>
							<tr id="conTR">
								<th scope="row" width="13%">
									${page.text13}
								</th>
								<td>
									<textarea name="content" id="content" rows="3" cols="60" onkeypress="document.getElementById('martest').contentWindow.location.reload();">${requestScope.marquee.m_content }</textarea>
									<b>*</b>
								</td>
							</tr>
							<tr id="rssTR" style="display: none;">
								<th scope="row" width="13%">
									rss${page.text14}
								</th>
								<td height="30" align="left" valign="middle">
									<!-- ${page.text12}地址欄位 -->
									<input id="rssUrl" name="rssUrl" type="text" size="50" value="" onblur="getContent();" />
									${page.text15}
									<select name="showType" onchange="getContent();">
										<option value="0" selected="selected">
											rss${page.text16}
										</option>
										<option value="1">
											${page.text17}
										</option>
										<option value="2">
											rss${page.text18}+${page.text17}
										</option>
									</select>
									${page.text19}
									<select name="showNumber" onchange="getContent();">
										<c:forEach begin="1" end="10" step="1" var="index">
											<option value="${index }" ${index==1? 'selected="selected"':'' }>
												${index }
											</option>
										</c:forEach>
									</select>
									<span id="rssError" style="color: red;"></span>
								</td>
							</tr>
							<tr>
								<th scope="row" width="13%">
									${page.text20}
								</th>
								<!-- ${page.text20} 背景颜色-->
								<td style="line-height: 40px;">
									<select name="colorOrImg" onchange="bgOrImg();" style="display: none;">
										<option value="1">
											${page.text20}
										</option>
										<option value="2">
											${page.text21}
										</option>
									</select>
									<input type="hidden" name="bgcolor" id="bgcolor" value="${requestScope.marquee.background!=null?requestScope.marquee.background:'' }" />
									<input type="button" id="bgcolorSelect" value="${page.text22}" onclick="showColorPicker(this,document.getElementById('bgcolorArea'),'bgcolor','background');" />
									<input type="button" id="imgSelect" value="${page.text23}" onclick="openImg();" disabled="disabled" style="display: none;" />
									<span id="bgcolorDiv" style="width:40px;height: 40px;display: inline-block;"></span>
									<span id="bgcolorArea" style="display:none;width:40px;height: 40px;"></span>
								</td>
							</tr>
							<tr>
								<th scope="row" width="13%">
									${page.text24}
								</th>
								<td height="30" align="left" valign="middle">
									<!-- ${page.text17}欄位 -->
									<select name="fontfamily">
										<option value="${page.text25}">
											${page.text25}
										</option>
										<option value="${page.text26}">
											${page.text26}
										</option>
										<option value="${page.text27}">
											${page.text27}
										</option>
										<option value="${page.text27}">
											${page.text28}
										</option>
										<option value="${page.text29}">
											${page.text29}
										</option>
										<option value="${page.text30}">
											${page.text30}
										</option>
										<option value="DFHaiBaoW12-B5">
											${page.text31}
										</option>
										<option value="DFHaiBaoW9-B5">
											${page.text32}
										</option>
										<option value="DFFangYuan Std W7">
											${page.text33}
										</option>
										<option value="${page.text34}">
											${page.text35}
										</option>
										<option value="${page.text36}">
											${page.text36}
										</option>
										<option value="${page.text37}">
											${page.text37}
										</option>
									</select>
								</td>
							</tr>

							<tr>
								<th scope="row" width="13%">
									${page.text38}
								</th>
								<td>
									<select name="size">
										<option value="50">
											5${page.text39}
										</option>
										<option value="60" selected="selected">
											6${page.text39}
										</option>
										<option value="70">
											7${page.text39}
										</option>
										<option value="80">
											8${page.text39}
										</option>
										<option value="90">
											9${page.text39}
										</option>
										<option value="100">
											10${page.text39}
										</option>
										<option value="110">
											11${page.text39}
										</option>
										<option value="120">
											12${page.text39}
										</option>
										<option value="130">
											13${page.text39}
										</option>
										<option value="140">
											14${page.text39}
										</option>
										<option value="150">
											15${page.text39}
										</option>
										<option value="160">
											16${page.text39}
										</option>
										<option value="170">
											17${page.text39}
										</option>
										<option value="180">
											18${page.text39}
										</option>
										<option value="190">
											19${page.text39}
										</option>
										<option value="200">
											20${page.text39}
										</option>
										<option value="300">
											30${page.text39}
										</option>
										<option value="400">
											40${page.text39}
										</option>
										<option value="500">
											50${page.text39}
										</option>
										<option value="600">
											60${page.text39}
										</option>
										<option value="700">
											70${page.text39}
										</option>
										<option value="800">
											80${page.text39}
										</option>
									</select>
								</td>
							</tr>
							<tr>
								<th scope="row" width="13%">
									${page.text40}
								</th>
								<td class="color">
									<input type="hidden" name="color" id="color" maxlength="7" value="${requestScope.marquee.color!=null?requestScope.marquee.color:'#000000' }" />
									<input type="button" onclick="showColorPicker(this,document.getElementById('colorArea'),'color','colors');" />
									<span id="colorArea" style="width: 40px;height: 30px;display: inline-block;background-color: ${requestScope.marquee.color!=null?requestScope.marquee.color:'#000000' };"></span>
								</td>
							</tr>
							<tr>
								<th scope="row" width="13%">
									${page.text41}
								</th>
								<td class="radio">
									<input type="radio" id="leftRadio" name="direction" value="left" checked ${requestScope.marquee.direction== "left"?"checked=checked ":"" } />
									<label for="leftRadio">
										<img width="24" height="21" src="${basePath }manage/images/marquee_icon1.jpg" />
									</label>
									<input type="radio" id="rightRadio" name="direction" value="right" ${requestScope.marquee.direction== "right"?"checked=checked ":"" }/>
									<label for="rightRadio">
										<img width="24" height="21" src="${basePath }manage/images/marquee_icon2.jpg" />
									</label>
								</td>
							</tr>
							<tr>
								<th scope="row" width="13%">
									${page.text42}
								</th>
								<td>
									<div id="slidercontainer1" class="slidercontainer"></div>
								</td>
							</tr>
							<tr>
								<th scope="row" width="13%">
									${page.text43}
								</th>
								<td style="height: 135px;">
									<iframe name="martest" id="martest" src="<%=basePath%>manage/marqueshow.jsp" width="100%" height="120">
									</iframe>
								</td>
							</tr>
						</table>
						<div class="button">
							<input type="hidden" name="speed" id="speed" value="${marquee.speed!=null?marquee.speed:'5'}" />
							<input type="hidden" name="fontSize" id="fontSize" value="${marquee.size!=null?marquee.size:'60'}" />
							<input type="hidden" name="fontFamily" id="fontFamily" value="${page.text44}" />
							<input type="hidden" name="colors" id="colors" value="${marquee.color!=null?marquee.color:'#000000'}" />
							<input type="hidden" name="direct" id="direct" value="${marquee.direction!=null?marquee.direction:'left' }" />
							<input type="hidden" name="id" value="${requestScope.marquee.id}" />
							<input type="hidden" name="background" id="background" value="${marquee.background!=null?marquee.background:''}" />


							<img id="back" title="${page.text47}" src="sysimg/back<s:text name='language' />.jpg" />
					<img id="save" src="sysimg/submit<s:text name='language' />.jpg" />
					<img id="reset" src="sysimg/reset<s:text name='language' />.jpg" />
						</div>
					</form>
				</div>
			</div>
		</div>
	</body>

	<script type="text/javascript">
	$("#back").click(function(){
		location = "searchMarquee.do";
	});
	$("#save").click(function(){
		$("form").submit();
	});
	$("#reset").click(function(){
		$("input[name='title']").val("");
		$("#content").val("");
	});
	<c:if test="${! empty requestScope.message}">
		alert('${requestScope.message}');
	</c:if>
</script>
</html>
