<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="util.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh">
	<head>
		<title></title>
		<script type="text/javascript" src="<%=basePath%>manage/js/food.js"></script>
		<script type="text/javascript" language="javascript">
			var delSure = "${page.text1}";
			var delSuccess  = "${page.text2}";
		</script>
		<style>
		#showFood{position: absolute;width:650px;height:430px;left: 5px;z-index: 98;display: none;border: 1px #201A1A solid;background: #EDEAD7;padding: 8px;overflow: hidden;}
		#foodImg{width: 420px;height:320px;padding: 0px;}
	</style>
	</head>
	<body>
		<div id="modal"></div>
		<div id="showFood">
			<div id="foodImg">
			</div>
			<div style="width: 200px;position: absolute;right: 20px;top: 20px;font-size: 20px;font-weight: 800;">
				${page.text6}
				<span id="foodName" style="color:#803D6D;"></span>
				<hr width="100%" />
				${page.text7}
				<span id="foodPrice" style="color:red;"></span>
				<hr width="100%" />
				${page.text28 }
				<br />
				<span id="foodTaste" style="color:#4E8975;font-size:16px;"></span>
			</div>
			<div style="width:100%;font-size: 20px;font-weight: 800;padding:5px;word-wrap:break-word;">
				${page.text8}
				<br />
				<span id="foodIntroduce" style="color:#2619A7;overflow: hidden;padding:5px;"></span>
			</div>
			<span style="right:5px;top:-3px;color:green;position:absolute;font-size:30px;font-weight:800;cursor:pointer;" title="${page.text9}" onclick="imgDivhide();">${page.text10}</span>
		</div>
		<div id="food">
			<div class="title">
				<h3>
					<div>
						${page.text11}
					</div>
				</h3>
			</div>

			<div class="content">
				<a href="<%=basePath%>findFood.do"><img width="115" height="33" alt="${page.text12}" src="${basePath }manage/images/food_add${lansuffix }.jpg" /> </a>
			</div>


			<div class="title">
				<h3>
					<div>
						${page.text27}
					</div>
				</h3>
			</div>

			<div class="tables">
				<form action="<%=basePath%>searchFood.do" name="foodSearch" method="post">
					<div class="marquee_s">
						${page.text6}
						<input class="keyword" type="text" name="searchName" value="${searchName}" />
						${page.text14}
						<select class="keyword" name="searchType" style="width:120px;margin-right: 15px;height: 25px;">
							<option value="0">
								${page.text15}
							</option>

							<c:forEach items="${requestScope.foodTypeList}" var="type">
								<c:choose>
									<c:when test="${type.id == searchType }">
										<option value="${type.id }" selected="selected">
											${type.typeName }
										</option>
									</c:when>
									<c:otherwise>
										<option value="${type.id }">
											${type.typeName }
										</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
						<input class="submit" type="image" alt="${page.text16}" title="${page.text16}" onclick="$('input[name=startPage]').val(1);" src="${basePath }manage/images/btn4${lansuffix }.jpg" />
					</div>
					<div class="material_list">
						<ul>
							<s:if test="#request.foodList != null">
								<li class="m_select" style="width:95%;font-weight: 800;padding-bottom: 5px;color:#2B4850;clear:both;">
									<img onclick="selectAll('fid');" width="55" height="21" alt="${page.text17}" src="${basePath }manage/images/icon1${lansuffix }.png" />
									<img onclick="selectReverse('fid');" width="55" height="21" alt="${page.text18}" src="${basePath }manage/images/icon3${lansuffix }.png" />
									<img onclick="selectCancle('fid');" width="84" height="21" alt="${page.text19}" src="${basePath }manage/images/icon4${lansuffix }.png" />
									<img alt="${page.text20}" title="${page.text20}" src="${basePath }manage/images/icon2${lansuffix }.png" onclick="deleteSelect()" />
								</li>
								<s:iterator value="#request.foodList" var="food" status="index">
									<li class="previewLi">
										<div class="preview" id="foodDiv<s:property value='id'/>">
											<div class="food">
												<img src="<s:property value='path'/>" title="<s:property value='name'/>" />
											</div>
											<img class="border" src="${basePath }manage/images/food_border.png" title="${fn:replace(food.introduce,'<br/>','\\n')}" />
											<label class="foodname">
												<s:property value='name' />
											</label>
											<span style="display:none" class="taste"> <s:iterator value="#request.foodTasteMap[#food.id]" var="taste">
													<s:property value="name" />
													<br />
												</s:iterator> </span>
											<span>${page.text21}: <span class="price"><s:text name="formatMoney">
														<s:param value="#food.price"></s:param>
													</s:text> </span> </span>
										</div>
										<div class="config">
											<input type="checkbox" name="fid" value="<s:property value='id'/>" />
											<img width="45" height="19" alt="${page.text22}" onclick="deleteFood(<s:property value='id'/>);" src="${basePath }manage/images/material_icon2${lansuffix }.jpg" />
											<a href="${basePath }findFood.do?id=<s:property value='id'/>" title="${page.text23}"><img width="45" height="19" alt="${page.text23}"
													src="${basePath }manage/images/material_edit${lansuffix }.jpg" /> </a>
											<img width="45" height="19" alt="${page.text24}" onclick="lookat(<s:property value='id'/>);" src="${basePath }manage/images/material_icon3${lansuffix }.jpg" />
										</div>
									</li>
								</s:iterator>
							</s:if>
							<s:else>
								<li style="width:95%;font-weight: 800;text-align: center;color:red;">
									${page.text25}
								</li>
							</s:else>
						</ul>
						<div class="clearFloat"></div>
					</div>
					<s:if test="#request.foodList != null">
						<page:paper formName="foodSearch" startPage="${requestScope.pageBean.startPage}" maxPage="${requestScope.pageBean.maxPage}" pageSize="${requestScope.pageBean.pageSize}"
							rowCount="${requestScope.pageBean.rowCount}" language="${language}" />
					</s:if>
				</form>
			</div>
		</div>
		<script type="text/javascript">
			//${page.text22}角色
function deleteFood(id) {
	var result = window.confirm(delSure);
	if (result) {
		$.ajax({
			url : basePath + 'deleteFood.do',
			type : 'post',
			dataType : 'json',
			data : {
				id : id
			},
			success : function(data) {
				if (data.success) {
					alert(delSuccess);
					$('form[name=foodSearch]').submit();
				} else {
					if (data.errorMsg != '' && typeof (data.errorMsg) != 'undefined') {
						alert(data.errorMsg);
					}
					if (data.infoInstruct != '' && typeof (data.infoInstruct) != 'undefined') {
						alert(data.infoInstruct);
					}
				}
			}
		});
	}
}
// ${page.text17}
function selectAll(name) {
	$('input[name=' + name + ']').prop('checked', true);
}
// ${page.text19}
function selectCancle(name) {
	$('input[name=' + name + ']').prop('checked', false);
}
// ${page.text18}
function selectReverse(name) {
	$('input[name=' + name + ']').each(function() {
		$(this).prop('checked', !this.checked);
	});
}
// 删除多选
function deleteSelect() {
	var list = '';
	$("input:checked[name=fid]").each(function() {
		list += this.value + ',';
	});
	if (list == '') {
		alert('${page.text3}');
		return;
	} else {
		var result = confirm('${page.text4}');
		if (result) {
			$.ajax({
				url : basePath + 'deleteFoodByIdList.do',
				type : 'post',
				dataType : 'json',
				data : 'idList=' + list + '&date=' + new Date().getTime(),
				success : function(data) {
					if (data.success) {
						alert('${page.text2}');
						$('form[name=foodSearch]').submit();
					} else {
						if (data.errorMsg != '' && typeof (data.errorMsg) != 'undefined') {
							alert(data.errorMsg);
						} else if (data.infoInstruct != '' && typeof (data.infoInstruct) != 'undefined') {
							alert(data.infoInstruct);
						} else {
							alert('${page.text5}');
						}
					}
				}
			});
		}
	}
}

String.prototype.replaceAll = function(s1,s2) { 
		    return this.replace(new RegExp(s1,"gm"),s2); 
		}
$(document).ready(function() {
	//$('select[name=searchType]').val('${searchType}');
	$('select[name=searchType]').change(function() {
		$('form[name=foodSearch]').submit();
	});
});
// ${page.text24}食物
function lookat(id) {
	$('#modal').show();
	$('#showFood').css('left', ($('#modal').width() - $('#showFood').width()) / 2);
	$('#showFood').css('top', document.documentElement.scrollTop + 50);
	var url = $('#foodDiv' + id + ' .food img').attr("src");
	$('#foodImg').html('<img width="420" height="320" src="' + url + '"/>');
	$('#foodName').html($('#foodDiv' + id + ' label').html());
	$('#foodTaste').html($('#foodDiv' + id + ' .taste').html());
	$('#foodPrice').html($('#foodDiv' + id + ' span.price').html());
	try {
		$('#foodIntroduce').html($('#foodDiv' + id + ' img.border').attr("title").replaceAll('\n', '<br/>'));
	} catch (e) {
	}
	$('#showFood').show();
}
function imgDivhide() {
	$('#modal').hide();
	$('#showFood').hide();
}
			
		</script>
	</body>

	<script type="text/javascript">
	<c:if test="${! empty requestScope.message}">
		alert('${requestScope.message}');
	</c:if>
</script>
</html>
