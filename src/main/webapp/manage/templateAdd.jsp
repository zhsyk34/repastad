<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="util.jsp"%>
<html>
	<head>
		<title></title>

		<link rel="stylesheet" href="${basePath }manage/css/template.css" media="all" />
		<!--  -->
		<script type="text/javascript" src="${basePath }js/plugin/js/draggable.js"></script>
		<script type="text/javascript" src="${basePath }manage/lhgdialog/lhgcore.min.js"></script>
		<script type="text/javascript" src="${basePath }manage/lhgdialog/lhgdialog.min.js?s=default"></script>
		<script type="text/javascript" src="${basePath }manage/lhgdialog/lhgdialog.min.js?s=default"></script>
		

		<script>
	
	function formatPrice(num) {
		var temp = num * 10;
		if (temp % 10 == 0) {
			return temp / 10;
		}
		return num;
	}
	function saveTemplate(){
		var id = $('input[name=id]').val();
		var name = $('input[name=templateName]').val();
		var banner =  $('input[name=banner]').val();
		var titleImg =  $('input[name=titleImg]').val();
		var cakeId =  $('input[name=cakeId]').val();
		var row = parseInt($("input[name='row']").val());
		var column = parseInt($("input[name='column']").val());
		
		if(!row |!column|row<0||column<0){
			alert();
			return;
		}
		
		if($.trim(name)==''){
			alert('${page.text1}');
			return false;
		}
		if($.trim(cakeId)==''){
			alert('${page.text2}');
			return false;
		}
		var marquee = '';
		var selectPart = '';
		var video = '';
		var picture = '';
		var picTime = '';
		var type= $(":radio[name='type']:checked").val();
		var size= $(":radio[name='size']:checked").val();
		if($('input[name=isMarquee]').prop("checked")){
			selectPart += 'isMarquee,';
			marquee = $('input[name=marquee]').val();
		}
		var ctrl = $('input[name=showCtrl]:checked').attr("id");
		selectPart += ctrl+',';
		if(ctrl=='videoCtrl'){
			video = $('input[name=video]').val();
		}else if(ctrl=='pictureCtrl'){
			picture = $('input[name=picture]').val();
			picTime = $('input[name=picTime]').val();
			if(picTime==''){
				alert('${page.text3}');
				return false;
			}
			if(isNaN(picTime)){
				alert('${page.text4}');
				return false;
			}
		}
		var effect = $('select[name=effect]').val();
		
		var isExsit = false;
		$.ajax({
			url:basePath+"json/Template_validateName.do",
			type:"post",
			data:{"searchName":name},
			async: false,
			success:function(data){
				isExsit = data.exsit;
			}
		});
		
		if((!id)&&isExsit){
			alert(lan.templateExsit);
			return;
		}
		$.ajax({
			url:basePath+'saveTemplate.do',
			type:'post',
			dataType:'json',
			data:'id='+id+'&row='+row +'&column='+column+'&type='+type+'&size='+size+'&banner='+banner+'&marquee='+marquee+'&titleImg='+titleImg+'&cakeId='+cakeId+'&name='+encodeURIComponent(name)
				+'&selectPart='+selectPart+'&video='+video+'&picture='+picture+'&picTime='+picTime+'&effect='+effect+'&date='+new Date().getTime(),
			success:function(data){
				if(data.success){
					alert('${page.text5}');
					location.href = basePath+'searchTemplate.do';					
				}else {
					if(data.errorMsg!='' && typeof (data.errorMsg) !='undefined'){
						alert(data.errorMsg);
					}else if(data.infoInstruct!='' && typeof (data.infoInstruct) !='undefined'){
						alert(data.infoInstruct);
					}else{
						alert('${page.text6}');
					}
				}
			}
		});	
	}
	//delImg
	var delImg_src = "${basePath }manage/images/temp_del.gif";
	//設置圖片值
	function setImage(inputId,value,name,url){
		$(inputId).val(value);
		$(inputId+'Div').html('<img width="50" height="50" src="'+url+'" title="'+name+'"/>');
	}
	//增加圖片值
	function addImage(inputId,value,name,url){
		$(inputId).val($(inputId).val()+value+",");
		//save picture from dialog
		var str = "<div id='"+ inputId.replace("#","")+ value +"'>"
		str += "<img class='show' src='" + url+ "'/>";
		str += "<img class='mdel' picid='"+ value +"' src='" + delImg_src + "'/>";
		str += "</div>";
		$(inputId+"Div").append(str);
	}
	//保存當前食物
	function setFood(inputId,value,name,price,url){
		$(inputId).val($(inputId).val()+value+",");
		//show the foodlist
		var str = "<div id='" + inputId.replace("#","") + "Div" + value + "'>";
		str += "<img class='show' src='" + url+ "'/>";
		str += "<span class='cakename'>"+ name + "<br/></span><span class='cakeprice'>" + formatPrice(price) + "</span>";
		str += "<img class='fdel' fid='"+ value +"' src='" + delImg_src + "'/>";
		str += "</div>";
		$(inputId+'Div').append(str);
	}
	//保存跑馬燈
	function setMarquee(inputId,value,marName){
		$(inputId).val($(inputId).val()+value+",");
		$(inputId+'Div').append(marName+"<br/>");
	}
	function openImgSelect(inputid){
		//${page.text23}title
		var multi = "";
		if(inputid=="picture"){
			multi = "&checkbox=Y";
		}
		var dg = new J.dialog({id:inputid,title:"${page.text7}",loadingText:'${page.text8}',iconTitle:false,width:800, height:600,page:basePath+"searchMaterial.do?select=true&searchType=1"+multi,cover:true});
		dg.ShowDialog();
	}
	function openVideoSelect(inputid){
		var dg = new J.dialog({id:inputid,title:"${page.text9}",loadingText:'${page.text10}',iconTitle:false,width:800, height:600,page:basePath+"searchMaterial.do?select=true&searchType=2&checkbox=Y",cover:true});
		dg.ShowDialog();
	}
	function openFoodSelect(inputid){
		var dg = new J.dialog({id:inputid,title:"${page.text11}",loadingText:'${page.text12}',iconTitle:false,width:800, height:600,page:basePath+"searchFood.do?select=true&checkbox=Y",cover:true});
		dg.ShowDialog();
	}
	function openMarqueeSelect(inputid){
		var dg = new J.dialog({id:inputid,title:"${page.text13}",loadingText:'${page.text14}',iconTitle:false,width:800, height:600,page:basePath+"searchMarquee.do?select=true&checkbox=Y",cover:true});
		dg.ShowDialog();
	}
	function showCtrlFun(id){
		if(id=='numberCtrl'){
			$("#bannerTr").show();
			$("#videoTr").hide();
			$("#pictureTr").hide();
			$("#pictureTimeTr").hide();
			$("#effectTr").hide();
		}else if(id=='videoCtrl'){
			$("#bannerTr").hide();
			$("#videoTr").show();
			$("#pictureTr").hide();
			$("#pictureTimeTr").hide();
			$("#effectTr").hide();
		}else if(id=='pictureCtrl'){
			$("#bannerTr").hide();
			$("#videoTr").hide();
			$("#pictureTr").show();
			$("#pictureTimeTr").show();
			$("#effectTr").show();
		}else if(id=='isMarquee'){
			if($('input[name=isMarquee]').prop("checked")){
				$("#marqueeTr").show();
			}else{
				$("#marqueeTr").hide();
			}
		}
	}
	function editFoodList(){
		$("td").on("click",".fdel",function(){
			var id = $(this).attr("fid");
			var list = $("#cakeId").val();
			var arr = list.split(",");
			arr.splice($.inArray(id,arr),1);
			list = arr.join();
			$("#cakeId").val(list);
			$(this).parent("div").remove();
		});
	}
	function editImg(){
		$("td").on("click",".mdel",function(){
			var id = $(this).attr("picid");
			var listPic = $("#picture").val();
			var listVideo = $("#video").val();
			var arrPic = listPic.split(",");
			var arrViedo = listVideo.split(",");
			arrPic.splice($.inArray(id,arrPic),1);
			arrViedo.splice($.inArray(id,arrViedo),1);
			listPic = arrPic.join();
			listVideo = arrViedo.join();
			$("#picture").val(listPic);
			$("#video").val(listVideo);
			$(this).parent("div").remove();
		});
	}
	
	function select(){
		//init type
		var type = "${requestScope.template.type}";
		var row = "${requestScope.template.row}";
		var column = "${requestScope.template.col}";
		if(type){
			$(".type :radio[value="+type+"]").prop("checked",true);
		}
		if(row>0){
			$("input[name='row']").val(row);
		}
		if(column>0){
			$("input[name='column']").val(column);
		}
		
		//change hide by type
		function hideByType(){
			//var type = $(".type :radio:checked").val();
			var checked = $(".type :radio:checked");
			var index = $(".type :radio").index(checked);
			switch(index){
				case 0:
					$("#logo").show();
					$(".size").hide();
					$(".easy-config").hide();
					break;
				case 1:
					$("#logo").hide();
					$("#titleImg").val("");
					$(".size").show();
					$(".easy-config").hide();
					break;
				case 2:
					$(".size").hide();
					$(".easy-config").show();
					break;
			}
		}
		
		hideByType();		
		
		$(".type").on("click",":radio",function(){
			hideByType();
		});
		
		//size
		var size = "${requestScope.template.size}";
		$(":radio[name='size'][value='"+size+"']").prop("checked",true);
	}
	
	$(document).ready(function(){
		select();
		//choose foodlist
		editFoodList();
		editImg();
		//init foodlist
		$("#cakeId").val("${requestScope.template.cakeId }");
		$('input[name=showCtrl]').change(function(){
			var id = $('input[name=showCtrl]:checked').attr("id");
			showCtrlFun(id);
		});	
		//select marquee	
		$('#isMarquee').change(function(){
			if($(this).prop("checked")){
				$("#marqueeTr").show();
			}else{
				$("#marqueeTr").hide();
				$("#marquee").val("");
				$("#marqueeDiv").text("");
				
			}
		});
		
		var bannerStr = '${requestScope.template.bannerPath }';
		if(bannerStr!=''){
			var bannerArray = bannerStr.split("@");
			for(var i=0;i<bannerArray.length;i++){
				if(bannerArray[i]!=''){
					$('#bannerDiv').html('<img width="50" height="50" src="'+basePath+bannerArray[i]+'"/>');
				}
			}
		}
		var titleImgStr = '${requestScope.template.titleImgPath }';	
		if(titleImgStr!=''){
			$('#titleImgDiv').html('<img width="50" height="50" src="'+basePath+titleImgStr+'"/>');
		}
		
		var cakeStr = '${requestScope.template.cakePath }';
		if(cakeStr!=''){
			var cakeArray = cakeStr.split("@");
			for(var i=0;i<cakeArray.length;i++){
				if(cakeArray[i]!=''){
					var cake = cakeArray[i].split(";");
					//show the checked foodlist
					var moneySign = "${page.text38}";
					var str = "<div id='cakeIdDiv" + cake[0] + "'>";
					str += "<img class='show' src='" + basePath+cake[3] + "'/>";
					str += "<span class='cakename'>"+ cake[1] + "<br/></span><span class='cakeprice'>" + moneySign + formatPrice(cake[2]) + "</span>";
					str += "<img class='fdel' fid='"+ cake[0]+"' src='" + delImg_src + "'/>";
					str += "</div>";
					$('#cakeIdDiv').append(str);
				}
			}
		}
		
		var selectPart = '${requestScope.template.selectPart }';
		if(selectPart!=''){
			var selectArray = selectPart.split(",");
			$('input[name=isMarquee]').prop("checked",false);
			$("#marqueeTr").hide();
			for(var s=0;s<selectArray.length;s++){
				$("#"+selectArray[s]).click();
				showCtrlFun(selectArray[s]);
			}
		}
		if(selectPart.indexOf('videoCtrl')!=-1){
			var videoStr = '${requestScope.template.videoStr }';
			if(videoStr!=''){
				var videoArray = videoStr.split("@");
				for(var i=0;i<videoArray.length;i++){
					if(videoArray[i]!=''){
						var video = videoArray[i].split(";");
						//video
						var id = video[0];
						var url = basePath + video[1];
						var str = "<div>"
						str += "<img class='show' src='" + url+ "'/>";
						str += "<img class='mdel' picid='"+ id +"' src='" + delImg_src + "'/>";
						str += "</div>";
						$("#videoDiv").append(str);
					}
				}
			}
		}
		if(selectPart.indexOf('pictureCtrl')!=-1){
			var pictureStr = '${requestScope.template.pictureStr }';
			if(pictureStr!=''){
				var pictureArray = pictureStr.split("@");
				for(var i=0;i<pictureArray.length;i++){
					if(pictureArray[i]!=''){
						var picture = pictureArray[i].split(";");
						//picture
						var id = picture[0];
						var url = basePath + picture[1];
						var str = "<div>"
						str += "<img class='show' src='" + url+ "'/>";
						str += "<img class='mdel' picid='"+ id +"' src='" + delImg_src + "'/>";
						str += "</div>";
						$("#pictureDiv").append(str);
					}
				}
			}
		}
		$('select[name=effect]').val('${requestScope.template.effect }');
	});
	
	
	
	</script>
	<script type="text/javascript" src="${basePath }manage/js/templateDrag.js"></script>
	</head>
	<body>
		<div id="program">
			<div class="content_t">
				<h3>
					<span> ${page.text15} </span>
				</h3>
			</div>
			<div class="tables">
				<div class="edit">
					<table width="100%">
						<tr class="type">
							<th>
								模版類型
							</th>
							<td>
								<label>
									<input type="radio" name="type" checked="checked" value="s01">
									<span>s01</span>
								</label>
								<label>
									<input type="radio" name="type" value="s02">
									<span>s02</span>
								</label>
								<label>
									<input type="radio" name="type" value="e01">
									<span>e01</span>
								</label>
							</td>
						</tr>
						<tr class="easy-config">
							<th>
								模版設置
							</th>
							<td>
								默認行高：
								<input value="25" name="row">
								最大欄位數：
								<input value="2" name="column">
								<span>每欄總高度為600，建議行高設置為20-30<span>
							</td>
						</tr>
						<tr class="size">
							<th>
								模版格式
							</th>
							<td>
								<label>
									<input type="radio" name="size" checked="checked" value="3x3">
									<span>3 x 3</span>
								</label>
								<label>
									<input type="radio" name="size" value="4x4">
									<span>4 x 4</span>
								</label>
								<label>
									<input type="radio" name="size" value="5x5">
									<span>5 x 5</span>
								</label>
							</td>
						</tr>
						<tr>
							<th scope="row" width="15%">
								${page.text16}
							</th>
							<td class="text td_left">
								<input type="radio" name="showCtrl" id="numberCtrl" class="selectinput" checked="checkedCtrl" />
								<label for="numberCtrl">
									${page.text17}
								</label>
								<input type="radio" name="showCtrl" id="videoCtrl" class="selectinput" />
								<label for="videoCtrl">
									${page.text18}
								</label>
								<input type="radio" name="showCtrl" id="pictureCtrl" class="selectinput" />
								<label for="pictureCtrl">
									${page.text19}
								</label>

								<input type="checkbox" name="isMarquee" id="isMarquee" class="selectinput" checked="checked" />
								<label for="isMarquee">
									${page.text20}
								</label>
							</td>
						</tr>
						<tr>
							<th scope="row" width="15%">
								${page.text21}
							</th>
							<td class="text td_left">
								<input type="text" name="templateName" id="templateName" value="${requestScope.template.name }" />
								<b>*</b>
							</td>
						</tr>
						<tr id="bannerTr">
							<th scope="row" width="15%">
								${page.text22}
							</th>
							<td class="text td_left">
								<input type="hidden" name="banner" id="banner" value="${requestScope.template.banner }" />
								<div id="bannerDiv">
									&nbsp;
								</div>
								<a href="javascript:openImgSelect('banner');" title="${page.text23}" class="s_pic"> <img width="47" height="21" alt="${page.text23}"
										src="${basePath }manage/images/program_icon5${lansuffix }.jpg" /> </a>
							</td>
						</tr>
						<tr id="logo">
							<th scope="row" width="15%">
								${page.text24}
							</th>
							<td class="text td_left">
								<input type="hidden" name="titleImg" id="titleImg" value="${requestScope.template.titleImg }" />
								<div id="titleImgDiv">
									&nbsp;
								</div>
								<a href="javascript:openImgSelect('titleImg');" title="${page.text23}" class="s_pic"> <img width="47" height="21" alt="${page.text23}"
										src="${basePath }manage/images/program_icon5${lansuffix }.jpg" /> </a>
							</td>
						</tr>
						<tr>
							<th scope="row" width="15%">
								${page.text25}
							</th>
							<td id="template_food" style="text-align: left;padding:5px;">
								<input type="hidden" name="cakeId" id="cakeId" value="${requestScope.template.cakeId }" />
								
								<a href="javascript:openFoodSelect('cakeId');" title="${page.text23}" class="s_pic"> <img width="47" height="21" alt="${page.text23}"
										src="${basePath }manage/images/program_icon5${lansuffix }.jpg" /> </a>
								<div id="cakeIdDiv">
									
								</div>
							</td>
						</tr>
						<tr id="videoTr" style="display: none;">
							<th scope="row" width="15%">
								${page.text26}
							</th>
							<td class="text td_left">
								<input type="hidden" name="video" id="video" value="${requestScope.template.video }" />
								<div id="videoDiv" style="line-height: 0px;min-height: 40px;">
									&nbsp;
								</div>
								<a href="javascript:openVideoSelect('video');" title="${page.text23}" class="s_pic"> <img width="47" height="21" alt="${page.text23}"
										src="${basePath }manage/images/program_icon5${lansuffix }.jpg" /> </a>
							</td>
						</tr>
						<tr id="pictureTr" style="display: none;">
							<th scope="row" width="15%">
								${page.text27}
							</th>
							<td class="text td_left">
								<input type="hidden" name="picture" id="picture" value="${requestScope.template.picture }" />
								<div id="pictureDiv" style="line-height: 0px;min-height: 40px;">
									&nbsp;
								</div>
								<a href="javascript:openImgSelect('picture');" title="${page.text23}" class="s_pic"> <img width="47" height="21" alt="${page.text23}"
										src="${basePath }manage/images/program_icon5${lansuffix }.jpg" /> </a>
							</td>
						</tr>
						<tr id="pictureTimeTr" style="display: none;">
							<th scope="row" width="15%">
								${page.text28}
							</th>
							<td class="text td_left">
								<input type="text" name="picTime" id="picTime" value="${requestScope.template.picTime }" />
								<b>*</b>
							</td>
						</tr>
						<tr id="effectTr" style="display: none;">
							<th scope="row" width="15%">
								${page.text29}
							</th>
							<td class="text td_left">
								<select name="effect" id="effect">
									<option value="Random" selected="selected">
										${page.text30}
									</option>
									<option value="Alpha">
										${page.text31}
									</option>
									<option value="Circle">
										${page.text32}
									</option>
									<option value="Move">
										${page.text33}
									</option>
									<option value="Blinds">
										${page.text34}
									</option>
								</select>
							</td>
						</tr>

						<tr id="marqueeTr">
							<th scope="row" width="15%">
								${page.text35}
							</th>
							<td class="text td_left">
								<input type="hidden" name="marquee" id="marquee" value="${requestScope.template.marquee }" />
								<div id="marqueeDiv" style="background: #E1E1E1;min-height:100px;margin: 5px 0;line-height: 18px;">
									${requestScope.template.marqueeStr }
								</div>
								<a href="javascript:openMarqueeSelect('marquee')" title="${page.text23}" class="s_pic"> <img width="47" height="21" alt="${page.text23}"
										src="${basePath }manage/images/program_icon5${lansuffix }.jpg" /> </a>
								<span style="color:#f00;">*選擇多則跑馬燈時，背景及字體將默認以第一則設定為主</span>
							</td>
						</tr>
					</table>
					<div class="p_btn p_btn2">
						<input type="hidden" name="id" id="id" value="${requestScope.template.id }" />

						<img class="goback" onclick="window.location.href='${basePath }searchTemplate.do';" width="84" height="27" alt="${page.text37}" title="${page.text37}"
							src="${basePath }manage/images/btn2${lansuffix }.jpg" />

						<input type="image" alt="${page.text36}" title="${page.text36}" src="${basePath }manage/images/btn1${lansuffix }.jpg" onclick="saveTemplate();" />
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
