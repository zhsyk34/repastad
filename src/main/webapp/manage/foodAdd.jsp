<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="commons.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<link rel="stylesheet" href="css/commons.css" />
		<link rel="stylesheet" href="css/layout.css" />
		<link rel="stylesheet" href="js/plugin/css/link.css" />
		<link rel="stylesheet" href="manage/css/foodAdd.css" />
		<script src="manage/lhgdialog/lhgcore.min.js"></script>
		<script src="manage/lhgdialog/lhgdialog.min.js?s=default"></script>

		<script>
			var type = "<s:property value='food.type'/>";
			var taste = "<s:property value='food.taste'/>";
			var choosePicture = "<s:text name='picture'/>" + "<s:text name='choose' />";
		</script>
		<script src="manage/js/foodAdd.js"></script>
		<title></title>
	</head>
	<body>
		<div class="title">
			<h3>
				<div>

				</div>
			</h3>
		</div>
		<div class="content">
			<table>
				<tr>
					<td rowspan="99" style="width:300px;vertical-align: top;">
						<div class="preview">
							<img src="manage/images/food_border.png">
							<div class="picture">
								<img src="<s:property value='food.path'/>">
							</div>
							<label class="name">
								<s:property value="food.name" />
							</label>
							<div class="price">
								<s:text name="price" />
								：
								<s:if test="food != null">
									<s:text name="formatMoney">
										<s:param value="food.price" />
									</s:text>
								</s:if>
							</div>
						</div>
					</td>
					<td style="vertical-align: top;">
						<form id="saveFood" method="post" action="saveFood.do">
							<table>
								<tr>
									<th>
										<s:text name="name" />
										<input name="id" type="hidden" value="<s:property value='food.id'/>">
									</th>
									<td>
										<input type="text" name="name" value="<s:property value='food.name'/>">
									</td>
								</tr>
								<tr>
									<th>
										別名
									</th>
									<td>
										<input type="text" name="alias" value="<s:property value='food.alias'/>">
									</td>
								</tr>
								<tr>
									<th>
										簡稱
									</th>
									<td>
										<input type="text" name="shortname" value="<s:property value='food.shortname'/>">
									</td>
								</tr>
								<tr>
									<th>
										<s:text name="type" />
									</th>
									<td>
										<select name="type">
											<s:iterator value="foodTypeList">
												<option value="<s:property value='id'/>">
													<s:property value="typeName" />
												</option>
											</s:iterator>
										</select>
									</td>
								</tr>
								<tr>
									<th>
										<s:text name="price" />
									</th>
									<td>
										<input type="text" name="price" value="<s:if test="food != null"><s:text name="formatMoney2"><s:param value="food.price" /></s:text></s:if>">
									</td>
								</tr>
								<tr>
									<th>
										<s:text name="picture" />
									</th>
									<td>
										<input type="hidden" id="materialId" name="materialId" value="<s:property value='food.materialId'/>">
										<a id="selectMaterial" class="linkbutton linkbutton-primary"><s:text name="choose" /> </a>
									</td>
								</tr>
								<tr>
									<th>
										<s:text name="introduce" />
									</th>
									<td>
										<textarea name="introduce" rows="5" cols="30" style="text-align: left;"><s:property value="food.introduce" /></textarea>
									</td>
								</tr>
								<tr>
									<th>
										<s:text name="taste" />
										<input type="hidden" id="necessary" name="necessary" value="<s:property value='food.necessary' />">
									</th>
									<td class="taste">
										<s:iterator value="tasteMap">
											<ul>
												<li style="color: #f00;font-weight: 600">
													<label class="necessary">
														<input type="checkbox" value="<s:property value='key' />">
														必選調味
													</label>
												</li>
												<s:iterator value="value">
													<li>
														<label>
															<input name="tasteIds" type="checkbox" value="<s:property value='id' />">
															<s:property value="name" />
														</label>
													</li>
												</s:iterator>
											</ul>
										</s:iterator>
									</td>
								</tr>
							</table>
						</form>
					</td>
				</tr>
			</table>
		</div>
		<div class="button">
			<img id="back" src="sysimg/back<s:text name='language' />.jpg" />
			<img id="save" src="sysimg/submit<s:text name='language' />.jpg" />
			<img id="reset" src="sysimg/reset<s:text name='language' />.jpg" />
		</div>

		<div id="dialog"></div>
	</body>
</html>
