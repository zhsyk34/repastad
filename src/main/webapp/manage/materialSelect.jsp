<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="util.jsp"%>
<%
	String checkbox = (String) request.getParameter("checkbox");
	if (checkbox != null && "Y".equals(checkbox)) {
		request.setAttribute("inputType", "checkbox");
	} else {
		checkbox = "";
		request.setAttribute("inputType", "radio");
	}
%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh" style="width: 100%;overflow-x:hidden;">
	<head>
		<title></title>
		<style>
			div.tables td.taste{overflow: hidden;color:#000;text-align: left;}
			.taste label{	
				height: 20px;
				line-height:1.5;
				margin-right: 5px;
			}
		</style>
		<script type="text/javascript" src="${basePath }manage/lhgdialog/lhgcore.min.js"></script>
		<script type="text/javascript" src="${basePath }manage/lhgdialog/lhgdialog.min.js?s=default"></script>
		<script type="text/javascript" language="javascript">
	var basePath = '<%=basePath%>';
	var DG = frameElement.lhgDG;
	
	var parentId = '#'+DG.dialogId;
	
	//移除取消按鈕
	DG.removeBtn('dgcancelBtn');
	DG.addBtn('ok','${page.text1}',function(){
		DG.cancel();
	});
	
	$(document).ready(function(){
		var parentValue = J(parentId,DG.curDoc).val();
		//加载父窗口的原有值
		if(parentValue){
			var valueArray = parentValue.split(",");
			for(var i=0;i<valueArray.length;i++){
				if(valueArray[i]!=''){
					$('input[name=materialId][value='+valueArray[i]+']').attr('checked',true);
					$('input[name=foodId][value='+valueArray[i]+']').attr('checked',true);
					$('input[name=marqueeId][value='+valueArray[i]+']').attr('checked',true);

					//sysconfig.jsp
					$('#index[value='+valueArray[i]+']').attr('checked',true);
					$('#login[value='+valueArray[i]+']').attr('checked',true);
				}
			}
		}
		//${page.text7}賦值，單個
		$('input[name=materialId]').click(function(){
			var value = $(this).val();
			if(parentId=='#picture' || parentId=='#video'){
				setValue(this);
			}else{
				DG.curWin.setImage(parentId,value,$('#materialName'+value).html(),$('#materialImg'+value).attr('src'));
			}
		});
		//
		var searchType = "${searchType}";
		$("select[name='tempType']").val(searchType);
		
		
		//食物賦值
		$('input[name=foodId]').click(function(){
			setValue(this);
		});
		//跑馬燈賦值
		$('input[name=marqueeId]').click(function(){
			setValue(this);
		});
		$('input[name=alltid]').change(function(){
			var allcheck = this.checked;
			$('input[type=checkbox][name!=alltid]').each(function(){
				if(this.checked!=allcheck){
					this.checked = allcheck;
					setValue(this);
				}
			});
		});
		
		//提交查询
		subSearch();
		function subSearch(){
			$(".marquee_s .submit").click(function(){
				var id = $(this).attr("id");
				var searchName = $("input[name='tempName']").val();
				var tempType = $("select[name='tempType']").val();
				$("input[name='searchName']").val(searchName);
				$("input[name='searchType']").val(tempType);
				$("input[name='startPage']").val(1);
				if(id=="foodsub"){
					$("form[name='foodSearch']").submit();
				}
				if(id=="materialsub"){
					$("form[name='materialSearch']").submit();
				}
				if(id=="marqueesub"){
					$("form[name='marqueeSearch']").submit();
				}
			});
		}
	});
	function setValue(obj){
		var name = $(obj).attr("name");
		var value = $(obj).val();
		if(obj.checked){
			if(name=="foodId"){
				DG.curWin.setFood(parentId,value,$('#foodName'+value).html(),$('#foodPrice'+value).html(),$('#foodImg'+value).attr('src'));
			}else if(name=="marqueeId"){
				DG.curWin.setMarquee(parentId,value,$('#marqueeName'+value).html());
			}else if(name=="materialId"){
				DG.curWin.addImage(parentId,value,$('#materialName'+value).html(),$('#materialImg'+value).attr('src'));
			}
		}else{
			var parentValue = J(parentId,DG.curDoc).val();
			parentValue = (","+parentValue).replace(","+value+",", ",").substring(1);
			J(parentId,DG.curDoc).val(parentValue);
			if(name=="foodId"){
				J(parentId+'Div'+value,DG.curDoc).remove();
				DG.curWin.setFood();
			}else if(name=="marqueeId"){
				var marContent = J(parentId+'Div',DG.curDoc).html().toLowerCase().replace($('#marqueeName'+value).html()+"<br>","").replace($('#marqueeName'+value).html()+"<br/>","");
				J(parentId+'Div',DG.curDoc).html(marContent);
			}else if(name=="materialId"){
				J(parentId+value,DG.curDoc).remove();
				
			}
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
	//${page.text8}
	function lookat(url,type){
		$('#modal').show();
		if(type==1){
			$('#showImg').html('<img src="'+url+'" onload="setImageSize(this);" onclick="imgDivhide();"/>');
		}else{
			url = url.replace(".jpg",".flv");
			$('#showImg').html(movieShowStr.replace("+movie+",url));
			$('#showImg').width(400).height(300);
			$('#showImg').css('left',($('#modal').width()-400)/2);
			$('#showImg').css('top',($('#modal').height()-300)/2);
			$('#showImg').append('<span style="right:5px;top:-3px;color:red;position:absolute;font-size:30px;font-weight:800;cursor:pointer;z-index:10;" title="${page.text2}" onclick="imgDivhide();">${page.text3}</span>');
		}
		$('#showImg').show();
	}
	function imgDivhide(){
		$('#modal').hide();
		$('#showImg').hide();
	}
	function setImageSize(obj){
		if(obj.width>760){
			obj.width = 760;
		}
		if(obj.height>420){
			obj.height = 420;
		}
		$('#showImg').width(obj.width).height(obj.height);
		$('#showImg').css('left',($('#modal').width()-obj.width)/2);
		$('#showImg').css('top',($('#modal').height()-obj.height)/2);
		$('#showImg').append('<span style="right:5px;top:-5px;color:red;position:absolute;font-size:30px;font-weight:800;cursor:pointer;" title="${page.text2}" onclick="imgDivhide();">${page.text3}</span>');
	}
	
</script>
	</head>
	<body>
		<div id="modal"></div>
		<div id="showImg" title="${page.text4}" style="position: absolute;left: 5px;top:5px;display: none;z-index: 98;">

		</div>
		<div id="program">
			<!-- search  -->
			<div class="marquee_s">
				${page.text6}:&nbsp;
				<input class="keyword" type="text" name="tempName" value="${searchName}" />
				<c:choose>
					<c:when test="${requestScope.forwardPage == 'material'}">
						<input class="submit" id="materialsub" type="image" src="${basePath }manage/images/btn4${lansuffix }.jpg" />
					</c:when>
					<c:when test="${requestScope.forwardPage == 'food'}">
						<select class="keyword" name="tempType" style="width:120px;margin-right: 15px;height: 25px;">
							<option value="0">
								<s:text name="unchoose" />
							</option>
							<c:forEach items="${requestScope.foodTypeList}" var="type" varStatus="index">
								<option value="${type.id }">
									${type.typeName }
								</option>
							</c:forEach>
						</select>
						<input class="submit" id="foodsub" type="image" src="${basePath }manage/images/btn4${lansuffix }.jpg" />
					</c:when>
					<c:when test="${requestScope.forwardPage == 'marquee'}">
						<input class="submit" id="marqueesub" type="image" src="${basePath }manage/images/btn4${lansuffix }.jpg" />
					</c:when>
				</c:choose>
			</div>
			<!--  search -->
			<div class="tables">
				<div class="user_list" style="overflow:hidden;">
					<table width="100%" class="discolor">
						<c:if test="${requestScope.forwardPage == 'material'}">
							<tr>
								<th scope="col" width="15%">
									<c:if test="${inputType=='checkbox'}">
										<input type="checkbox" name="alltid" />
									</c:if>
									${page.text5}
								</th>
								<th scope="col" width="25%">
									${page.text6}
								</th>
								<th scope="col" width="45%">
									${page.text7}
								</th>
								<th scope="col" width="15%">
									${page.text8}
								</th>
							</tr>
							<c:forEach var="material" items="${requestScope.materialList}" varStatus="index">
								<tr>
									<td>
										<input style="width: 18px;" type="${inputType }" name="materialId" id="materialId" value="${material.id }" />
									</td>
									<td id="materialName${material.id }">
										${material.name}
									</td>
									<td>
										<img id="materialImg${material.id }" src="${basePath }${material.path}" width="50" height="50" />
									</td>
									<td>
										<img width="64" height="23" onclick="lookat('${basePath }${material.path }',${material.type});" src="${basePath }manage/images/logs_icon1${lansuffix }.jpg" />
									</td>
								</tr>
							</c:forEach>
						</c:if>
						<c:if test="${requestScope.forwardPage == 'food'}">
							<tr>
								<th scope="col" width="10%">
									<c:if test="${inputType=='checkbox'}">
										<input type="checkbox" name="alltid" />
									</c:if>
								</th>
								<th scope="col" width="20%">
									${page.text6}
								</th>
								<th scope="col" width="15%">
									${page.text9}
								</th>
								<th scope="col" width="20%">
									${page.text7}
								</th>
								<th scope="col" width="35%">
									<s:text name="taste"/>
								</th>
							</tr>
							<s:iterator var="food" value="#request.foodList">
								<tr>
									<td>
										<input style="width: 18px;" type="${inputType }" name="foodId" id="foodId" value="<s:property value='id'/>" />
										<input class="foodType" type="hidden" value="<s:property value="type"/>">										
									</td>
									<td id="foodName<s:property value='id'/>">
										<s:property value='name' />
									</td>
									<!-- price format? -->
									<td id="foodPrice<s:property value='id'/>">
										<s:text name="formatMoney">
											<s:param value="price"></s:param>
										</s:text>
									</td>
									<td>
										<img style="cursor: pointer;" id="foodImg<s:property value='id'/>" src="${basePath }<s:property value='path'/>" width="50" height="50"
											onclick="lookat('${basePath }<s:property value="path"/>',1);" />
									</td>
									<td class="taste">
										<!-- food taste
										<s:iterator value="#request.foodTasteMap[id]" status="index">
											<label>												
												<s:property value="name" />
												<s:if test="!#index.last">
													<label>、</label>
												</s:if>
											</label>
										</s:iterator>
										 -->
										 <s:property value="taste"/>
									</td>
								</tr>
							</s:iterator>
						</c:if>
						<c:if test="${requestScope.forwardPage == 'marquee'}">
							<tr>
								<th scope="col" width="10%">
									<c:if test="${inputType=='checkbox'}">
										<input type="checkbox" name="alltid" />
									</c:if>
									${page.text5}
								</th>
								<th scope="col" width="20%">
									${page.text6}
								</th>
								<th scope="col" width="20%">
									${page.text10}
								</th>
							</tr>
							<c:forEach var="marquee" items="${requestScope.marqueeList}" varStatus="index">
								<tr>
									<td>
										<input style="width: 18px;" type="${inputType }" name="marqueeId" id="marqueeId" value="${marquee.id }" />
									</td>
									<td id="marqueeName${marquee.id }">
										${marquee.title}
									</td>
									<td id="marqueeContent${marquee.id }">
										<c:choose>
											<c:when test="${marquee.isRss==1 && fn:length(marquee.m_content)>50}">
		                  					${fn:substring(marquee.m_content, 0, 50)} 
		                  		</c:when>
											<c:when test="${marquee.isRss==0 && fn:length(marquee.m_content)>20}">
		                  					${fn:substring(marquee.m_content, 0, 20)} 
		                  		</c:when>
											<c:otherwise>
		                  			${marquee.m_content}
		                  		</c:otherwise>
										</c:choose>
									</td>
								</tr>
							</c:forEach>
						</c:if>
						<c:if test="${empty requestScope.materialList && empty requestScope.foodList && empty requestScope.marqueeList}">
							<tr>
								<td colspan="4">
									${page.text11}
								</td>
							</tr>
						</c:if>
					</table>
				</div>
				<c:if test="${requestScope.forwardPage == 'material'}">
					<form action="<%=basePath%>searchMaterial.do" name="materialSearch" method="post">
						<input type="hidden" name="searchName" value="${searchName}" />
						<input type="hidden" name="select" value="true" />
						<input type="hidden" name="checkbox" value="<%=checkbox%>" />
						<page:paper formName="materialSearch" startPage="${requestScope.pageBean.startPage}" maxPage="${requestScope.pageBean.maxPage}" pageSize="${requestScope.pageBean.pageSize}"
							rowCount="${requestScope.pageBean.rowCount}" language="${language}" />
					</form>
				</c:if>
				<c:if test="${requestScope.forwardPage == 'food'}">
					<form action="<%=basePath%>searchFood.do" name="foodSearch" method="post">
						<input type="hidden" name="searchName" value="${searchName}" />
						<input type="hidden" name="searchType" value="${searchType}" />
						<input type="hidden" name="select" value="true" />
						<input type="hidden" name="checkbox" value="<%=checkbox%>" />
						<page:paper formName="foodSearch" startPage="${requestScope.pageBean.startPage}" maxPage="${requestScope.pageBean.maxPage}" pageSize="${requestScope.pageBean.pageSize}"
							rowCount="${requestScope.pageBean.rowCount}" language="${language}" />
					</form>
				</c:if>
				<c:if test="${requestScope.forwardPage == 'marquee'}">
					<form action="<%=basePath%>searchMarquee.do" name="marqueeSearch" method="post">
						<input type="hidden" name="select" value="true" />
						<input type="hidden" name="checkbox" value="<%=checkbox%>" />
						<page:paper formName="marqueeSearch" startPage="${requestScope.pageBean.startPage}" maxPage="${requestScope.pageBean.maxPage}" pageSize="${requestScope.pageBean.pageSize}"
							rowCount="${requestScope.pageBean.rowCount}" language="${language}" />
					</form>
				</c:if>
			</div>
		</div>
	</body>
	<script type="text/javascript">
	<c:if test="${! empty requestScope.message}">
		alert('${requestScope.message}');
	</c:if>
</script>
</html>
