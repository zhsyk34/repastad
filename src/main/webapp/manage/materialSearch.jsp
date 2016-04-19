<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="util.jsp"%>
<html>
	<head>
		<title></title>
		<link rel="stylesheet" type="text/css" href="zhsy/css/dialog.css" />
		<script type="text/javascript" src="zhsy/js/dialog.js"></script>
		<style type="text/css">
		.u_title{
			background: url("${basePath }manage/images/upload_tbg${lansuffix }.png") no-repeat left top;
		}
		.operation{
			margin:0;
			width: 45px;
			height: 19px;
		}
		.button{
			margin-top: 15px;
		}
		.button img{
			margin-left:12px;
			cursor: pointer;
		}
	</style>
		<script type="text/javascript" language="javascript">
	var basePath = '<%=basePath%>';
	var index=1;
	var typeStr = "jpg${page.text1}";
	//${page.text25}角色
	function deleteMaterial(id){
		var result = window.confirm('${page.text2}');
		if(result){
			$.ajax({
				url:basePath+'deleteMaterial.do',
				type:'post',
				dataType:'json',
				data:'id='+id+'&date='+new Date().getTime(),
				success:function(data){
					if(data.success){
						alert('${page.text3}');
						$('form[name=materialSearch]').submit();						
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
	}
	var movieShowStr = '<div style="width:400px;height:300px;position:relative;z-index:8;">'
		 +'<object type="application/x-shockwave-flash" data="'+basePath+'manage/vcastr3.swf" width="100%" height="100%" id="vcastr3">'
		 +'<param name="movie" value="'+basePath+'manage/vcastr3.swf">'
		 +'<param name="wmode" value="opaque">'
		 +'<param name="allowFullScreen" value="true">'
		 +'<param name="FlashVars" value="xml= {vcastr}{channel}{item}{source}+movie+{/source}{duration}{/duration}{title}{/title}{/item}{/channel}{config}<isRepeat>true</isRepeat><isChangeProgram>false</isChangeProgram><contralPanelAlpha>1</contralPanelAlpha><controlPanelMode>none</controlPanelMode>{/config}{plugIns}{logoPlugIn}{url}'+basePath+'manage/logoPlugIn.swf{/url}{logoTextAlpha}0.75{/logoTextAlpha}{logoTextFontSize}30{/logoTextFontSize}{logoTextLink}http://www.ruochigroup.com{/logoTextLink}{logoTextColor}0xffffff{/logoTextColor}{textMargin}20 20 auto auto{/textMargin}{/logoPlugIn}{/plugIns}{/vcastr}">'
		 +'</object>'
		 +'</div>';
	//${page.text26}
	function lookat(url,type){		
		$('#modal').show();
		if(type==1){
			$('#showImg').html('<img src="'+url+'" onload="setImageSize(this);" onclick="imgDivhide();"/>');
		}else{
			url = url.replace(".jpg",".flv");
			$('#showImg').html(movieShowStr.replace("+movie+",url));
			$('#showImg').width(400).height(300);
			$('#showImg').css('left',($('#material').width()-400)/2);
			$('#showImg').css('top',$('body').scrollTop()+30);
			$('#showImg').append('<span style="right:5px;top:-3px;color:red;position:absolute;font-size:30px;font-weight:800;cursor:pointer;z-index:10;" title="${page.text4}" onclick="imgDivhide();">${page.text5}</span>');
		}
		
		$('#showImg').show();
	}
	function imgDivhide(){
		$('#modal').hide();
		$('#showImg').hide();
	}
	function setImageSize(obj){
		if(obj.width>550){
			obj.width = 550;
		}
		if(obj.height>550){
			obj.height = 550;
		}
		$('#showImg').width(obj.width).height(obj.height);
		$('#showImg').css('left',($('#material').width()-obj.width)/2);
		$('#showImg').css('top',$('body').scrollTop()+30);
		$('#showImg').append('<span style="right:5px;top:-3px;color:red;position:absolute;font-size:30px;font-weight:800;cursor:pointer;" title="${page.text4}" onclick="imgDivhide();">${page.text5}</span>');
	}
	//上傳${page.text19}
	function showUpload(){
		/*
		var flag = oauth.check("roleList");
		if(!flag){
			alert(lan.unauthorized);
			return;
		}
		*/
		$('#upload').css("left",($('#material').width()-$('#upload').width())/2);
		$('#upload').show();
		$('#modal').show();
	}
	function checkFileType(obj,count){
		var type = obj.value.substring(obj.value.lastIndexOf(".")+1).toLowerCase();
		var name = obj.value.substring(obj.value.lastIndexOf("\\")+1,obj.value.lastIndexOf("."));
		if(typeof type =='undefined'){
			alert(name+"."+type+" ${page.text6}");
			return false;
		}
		$('#filepath'+count).val(obj.value);
		if(type!=''){
			if(typeStr.indexOf(type)==-1){
				$('#filepath'+count).val('');
				alert(name+"."+type+" ${page.text6}");
				return false;
			}else{
				if($('#fileName'+count).val()==''){
					$('#fileName'+count).val(name);
				}
			}
		}
	}
	function removefile(index){
		$('#addFile'+index).remove();
	}
	//${page.text31}
	function addfile(){
		var html = '<tr id="addFile'+index+'"> <td> <input  class="text" name="fileName" type="text" id="fileName'+index+'"/></td> '
					+'<td> '
					+'<input class="text" type="text" id="filepath'+index+'" name="filepath" /> '
					+'<img width="62" height="26" src="${basePath }manage/images/upload_btn1${lansuffix }.jpg" /> '
					+'<input class="file" type="file" name="materialFile" onchange="checkFileType(this,'+index+');" /> '
					+'</td>'
					+'<td> <img class="del" width="82" height="27" src="${basePath }manage/images/del_btn${lansuffix }.jpg" onclick="removefile('+index+');"/> </td> </tr>';
		$("#materialTable").append(html);
		index++;
	}
	//保存時檢查
	function checkSave(form){
		var filenum = 0;
		$('input[name=fileName]').each(function(){
			filenum++;
		});
		for(var i=0;i<filenum;i++){
			var nameVal = $('input[name=fileName]:eq('+i+')').val();
			var fileVal = $('input[name=materialFile]:eq('+i+')').val();
			if($.trim(nameVal)==''|| $.trim(fileVal)==''){
				alert('${page.text7}');
				return false;
			}
		}
		//提示上傳
		$('#uploadTip').html('${page.text8}');
		return true;
	}
	//${page.text21}
	function selectAll(name){
		$('input[name='+name+']').prop('checked',true);
	}
	//${page.text23}
	function selectCancle(name){
		$('input[name='+name+']').prop('checked',false);
	}
	//${page.text22}
	function selectReverse(name){
		$('input[name='+name+']').each(function(){
			$(this).prop('checked',!this.checked);
		});
	}
	//删除多选
	function deleteSelect(){
		var list = '';
		$("input:checked[name=mid]").each(function(){
			list+=this.value+',';
		});
		if(list == ''){
			alert('${page.text9}');
			return;
		}else {
			var result = confirm('${page.text10}');
			if(result){
				$.ajax({
					url:basePath+'deleteMaterialByIdList.do',
					type:'post',
					dataType:'json',
					data:'idList='+list+'&date='+new Date().getTime(),
					success:function(data){
						if(data.success){
							alert('${page.text3}');
							$('form[name=materialSearch]').submit();						
						}else {
							if(data.errorMsg!='' && typeof (data.errorMsg) !='undefined'){
								alert(data.errorMsg);
							}else if(data.infoInstruct!='' && typeof (data.infoInstruct) !='undefined'){
								alert(data.infoInstruct);
							}else{
								alert('${page.text11}');
							}
						}
					}
				});	
			}
		}
	}
	$(document).ready(function(){
		$("input[name=searchType]").change(function(){
			$('input[name=startPage]').val(1);
			$("form[name=materialSearch]").submit();
		});
		$("input[name=searchType][value=${searchType}]").prop("checked",true);
		
		//modify name
		$(".mod").click(function(){
			dialog.init();
			dialog.open();
			var data = $(this).parents("li").find("img.data");
			var id = $.trim(data.attr("mid"));
			var name = $.trim(data.attr("alt"));
			$("input[name='id']").val(id);
			$("input[name='name']").val(name);
			$("#back").click(function(){
				dialog.close();
			});
			$("#save").click(function(){
				$(this).parents("form").submit();
				dialog.close();
			});
		
		
		});
	});
	
	
</script>
	</head>
	<body>
		<div id="modal" style="position: fixed;"></div>
		<div id="showImg" title="${page.text12}" style="position: fixed;left: 100px;top:70px;display: none;z-index: 98;">

		</div>
		<div id="material">
			<div class="content_t">
				<h3>
					<span>${page.text13}</span>
				</h3>
			</div>
			<div class="m_menu">
				<img width="115" height="33" alt="${page.text14}" src="${basePath }manage/images/material_icon1${lansuffix }.jpg" onclick="showUpload();" />
			</div>
			<div class="content_t">
				<h3>
					<span>${page.text36}</span>
				</h3>
			</div>
			<div class="tables">
				<form action="<%=basePath%>searchMaterial.do" name="materialSearch" method="post">
					<div class="marquee_s">
						${page.text16}
						<input class="keyword" type="text" name="searchName" value="${searchName}" />
						<input class="submit" type="image" alt="${page.text17}" title="${page.text17}" onclick="$('input[name=startPage]').val(1);" src="${basePath }manage/images/btn4${lansuffix }.jpg" />
						<input class="m_radio" type="radio" name="searchType" value="0" checked="checked" />
						${page.text18}
						<input class="m_radio" type="radio" name="searchType" value="1" />
						${page.text19}
						<input class="m_radio" type="radio" name="searchType" value="2" />
						${page.text20}
					</div>
					<div class="material_list">
						<ul>
							<c:if test="${! empty requestScope.materialList}">
								<li class="m_select" style="width:95%;font-weight: 800;padding-bottom: 5px;color:#2B4850;">
									<img onclick="selectAll('mid');" width="55" height="21" alt="${page.text21}" src="${basePath }manage/images/icon1${lansuffix }.png" />
									<img onclick="selectReverse('mid');" width="55" height="21" alt="${page.text22}" src="${basePath }manage/images/icon3${lansuffix }.png" />
									<img onclick="selectCancle('mid');" width="84" height="21" alt="${page.text23}" src="${basePath }manage/images/icon4${lansuffix }.png" />
									<img alt="${page.text24}" title="${page.text24}" src="${basePath }manage/images/icon2${lansuffix }.png" onclick="deleteSelect()" />
								</li>
								<c:forEach items="${requestScope.materialList}" var="material" varStatus="index">
									<li>
										<h4 title="${material.name }">
											${material.name }
										</h4>
										<div>
											<img class="data" mid="${material.id}" width="126" height="134" alt="${material.name }" src="${basePath }${material.path }"
												onclick="lookat('${basePath }${material.path }',${material.type });" />
										</div>
										<div class="config">
											<input type="checkbox" name="mid" value="${material.id }" />
											<img class="operation" alt="${page.text25}" onclick="deleteMaterial(${material.id });" src="${basePath }manage/images/material_icon2${lansuffix }.jpg" />
											<!-- 
											<img class="operation" alt="${page.text26}" onclick="lookat('${basePath }${material.path }',${material.type });" src="${basePath }manage/images/material_icon3${lansuffix }.jpg" />
											 -->
											<img class="operation mod" src="${basePath }manage/images/material-mod${lansuffix }.jpg">
										</div>
									</li>
								</c:forEach>
							</c:if>
							<c:if test="${empty requestScope.materialList}">
								<li style="width:95%;font-weight: 800;text-align: center;color:red;">
									${page.text27}
								</li>
							</c:if>
						</ul>
						<div class="clearFloat"></div>
					</div>
					<c:if test="${! empty requestScope.materialList}">
						<page:paper formName="materialSearch" startPage="${requestScope.pageBean.startPage}" maxPage="${requestScope.pageBean.maxPage}" pageSize="${requestScope.pageBean.pageSize}"
							rowCount="${requestScope.pageBean.rowCount}" language="${language}" />
					</c:if>
				</form>
			</div>
		</div>
		<!--素材上傳窗口-->
		<div id="upload">
			<div class="u_title">
				<span id="uploadTip" style="position: absolute;top:5px;left:120px;color:red;font-size: 16px;font-weight: 800;"></span>
				<span class="u_close" title="${page.text4}" onclick="$('#upload').hide();$('#modal').hide();">${page.text5}</span>
			</div>
			<div class="upload_w">
				<form action="<%=basePath%>uploadMaterial.do" method="post" enctype="multipart/form-data" onsubmit="return checkSave(this);">
					<div style="width:100%;max-height:350px;overflow-x:hidden;overflow-y:auto ;">
						<table width="515" align="center" id="materialTable">
							<tr>
								<th scope="row" width="34%">
									${page.text28}
								</th>
								<th scope="row" width="48%">
									${page.text29}
								</th>
								<th scope="row" width="18%"></th>
							</tr>
							<tr style="text-align:center;">
								<td>
									<input class="text" type="text" id="fileName0" name="fileName" />
								</td>
								<td>
									<input class="text" type="text" id="filepath0" name="filepath" />
									<img width="62" height="26" src="${basePath }manage/images/upload_btn1${lansuffix }.jpg" />
									<input class="file" type="file" name="materialFile" onchange="checkFileType(this,0);" />

								</td>
								<td>

									<!-- <img class="del" width="76" height="26" src="${basePath }manage/images/del_btn${lansuffix }.jpg" /> -->
								</td>
							</tr>
						</table>
					</div>
					<div class="m_btn">
						<input type="image" alt="${page.text30}" title="${page.text30}" src="${basePath }manage/images/btn1${lansuffix }.jpg" />
						<img alt="${page.text31}" title="${page.text31}" src="${basePath }manage/images/mbtn_add${lansuffix }.jpg" onclick="addfile()" />
					</div>
					<div class="tips">
						${page.text32} (${page.text33})
						<br />
						${page.text34} (${page.text35})
					</div>
				</form>
			</div>
		</div>
		<!--素材上傳窗口-->
		<!--TODO-->
		<form action="modMaterial.do" method="post">
			<div class="dialog">
				<div class="title">
					<h3>
						<div>
							<s:text name="materail.edit" />
						</div>
					</h3>
				</div>
				<div>
					<table style="width:350px;">
						<tr>
							<input type="hidden" name="id">
							<td width="25%" style="text-align: center;">
								<s:text name="materail.name" />
								:
							</td>
							<td>
								<input class="keyword" name="name">
							</td>
						</tr>
					</table>
				</div>

				<div class="button">
					<img id="back" src="sysimg/back<s:text name='language' />.jpg" />
					<img id="save" src="sysimg/submit<s:text name='language' />.jpg" />
				</div>
			</div>
		</form>
	</body>

	<script type="text/javascript">
	<c:if test="${! empty requestScope.message}">
		alert('${requestScope.message}');
	</c:if>
</script>
</html>
