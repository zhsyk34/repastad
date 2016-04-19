<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/manage/simple.jsp"%>
<html>
	<head>
		<title>模板編輯</title>
		<link rel="stylesheet" href="manage/css/list.css" />
		<link rel="stylesheet" href="js/plugin/css/link.css" />
		<link rel="stylesheet" href="manage/css/template.css" />

		<script src="js/plugin/js/draggable.js"></script>
		<script src="manage/lhgdialog/lhgcore.min.js"></script>
		<script src="manage/lhgdialog/lhgdialog.min.js?s=default"></script>
		<script src="manage/lhgdialog/lhgdialog.min.js?s=default"></script>
		<script src="manage/js/template.js"></script>
	</head>
	<body>
		<div class="title">
			<h3>
				<div>
					模版編輯
				</div>
			</h3>
		</div>

		<div class="content ">
			<table class="discolor">
				<tbody>
					<tr class="type">
						<th width="20%">
							模板類型
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
					<tr class="content">
						<th>
							模版內容
						</th>
						<td>
							<label>
								<input type="radio" name="content" checked="checked" value="number">
								<span>取號</span>
								<!-- banner -->
							</label>
							<label>
								<input type="radio" name="content" value="video">
								<span>視訊</span>
								<!-- video -->
							</label>
							<label>
								<input type="radio" name="content" value="picture">
								<span>圖片</span>
								<!-- pictrue,pictime,effect -->
							</label>
							<label>
								<input type="checkbox" name="content" value="marquee">
								<span>跑馬燈</span>
							</label>
						</td>
					</tr>
					<tr class="rowandcol">
						<th>
							模版設置
						</th>
						<td>
							默認行高：
							<input value="25" name="row" id="row">
							最大欄位數：
							<input value="2" name="column" id="col">
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

					<tr class="title">
						<th>
							模板名稱
						</th>
						<td>
							<input>
						</td>
					</tr>
					<tr class="logo">
						<!-- titleImg -->
						<th>
							餐點Logo
							<input id="logo" type="hidden" name="titleImg">
							<button class="linkbutton linkbutton-primary">
								選擇
							</button>
						</th>
						<td>
							<div id="logoImg"></div>
						</td>
					</tr>
					<tr class="food">
						<th>
							餐點列表
							<input id="food" type="hidden" name="cakeId">
							<button class="linkbutton linkbutton-primary">
								選擇
							</button>
						</th>
						<td>

						</td>
					</tr>
					<tr class="number">
						<!-- banner -->
						<th>
							頂部圖片
							<input id="number" type="hidden" name="banner">
							<button class="linkbutton linkbutton-primary">
								選擇
							</button>
						</th>
						<td>
							<div id="numberImg"></div>
						</td>
					</tr>
					<tr class="video">
						<th>
							視訊
							<input id="video" type="hidden" name="video">
							<button class="linkbutton linkbutton-primary">
								選擇
							</button>
						</th>
						<td>
							<div id="videoDiv"></div>
						</td>
					</tr>
					<tr class="picture">
						<!-- checkbox -->
						<th>
							圖片
							<input id="picture" type="hidden" name="picture">
							<button class="linkbutton linkbutton-primary">
								選擇
							</button>
						</th>
						<td>
							<div id="pictureDiv"></div>
						</td>
					</tr>
					<tr class="interval">
						<th>
							間隔時間
						</th>
						<td>
							<input name="picTime">
						</td>
					</tr>
					<tr class="effect">
						<th>
							效果
						</th>
						<td>
							<select name="effect">
								<option value="Random" selected="selected">
									隨機
								</option>
								<option value="Alpha">
									漸變
								</option>
								<option value="Circle">
									圓形
								</option>
								<option value="Move">
									移動
								</option>
								<option value="Blinds">
									百葉窗
								</option>
							</select>
						</td>
					</tr>
					<tr class="marquee">
						<th>
							跑馬燈
							<input id="marquee" type="hidden" name="marquee">
							<button class="linkbutton linkbutton-primary">
								選擇
							</button>
						</th>
						<td>
							<div id="marqueeDiv"></div>
							<div style="color:#f00;">
								選擇多則跑馬燈時，背景及字體將默認以第一則設定為主
							</div>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="button">
			<button class="linkbutton linkbutton-warning" id="back">
				返回
			</button>
			<button class="linkbutton linkbutton-info" id="save">
				送出
			</button>
		</div>
	</body>
</html>
