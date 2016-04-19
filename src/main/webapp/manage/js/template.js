// 区间
Array.prototype.range = function(n, r) {
	var arr = this.unique();
	arr.unshift(-Infinity);
	arr.push(Infinity);

	var result = {};
	for (var i = 0; i < arr.length - 1; i++) {
		if (n > arr[i] && n <= arr[i + 1]) {
			result.min = arr[i];
			result.max = arr[i + 1];

			if (r && (r > 0) && (n > arr[i] + r)) {
				result.min = arr[i + 1];
			}
			return result;
		}
	}
}

// 排序+去重
Array.prototype.unique = function() {
	this.sort(function(x, y) {
		return x - y;
	});

	var result = [ this[0] ];
	for (var i = 1; i < this.length; i++) {
		if (this[i] > result[result.length - 1]) {
			result.push(this[i]);
		}
	}
	return result;
}
$(function() {
	init();
	show();
	openDialog();

	listenRemove();
	drag();

	$("#back").click(function() {
		location = "searchTemplate.do";
	});
	$("#save").on("click", function() {
		save();
	});
});

var foodMap, typeMap, materialMap, marqueeMap;//

function init() {
	var id = parseInt(getParam("id"));
	var url = "json/Template_loadTemplate.do";
	if (id) {
		url += "?id=" + id;
	}
	$.ajax({
		url : url,
		async : false,
		type : "json",
		success : function(data) {
			load(data);
		}
	});

	function load(data) {
		if (!data) {
			return;
		}
		var template = data.template;

		materialMap = data.materialMap;
		marqueeMap = data.marqueeMap;
		typeMap = data.typeMap;
		foodMap = data.foodMap;

		if (template) {
			// type
			var type = template.type;
			$(":radio[name=type][value=" + type + "]").prop("checked", true);
			if (type == "s02") {
				var size = template.size;
				$(":radio[name=size][value=" + size + "]").prop("checked", true);
			}
			if (type == "e01") {
				$("input[name=row]").val(template.row);
				$("input[name=col]").val(template.col);
			}
			// content
			var contentStr = template.selectPart;
			if (contentStr) {
				$("tr.content :checkbox").prop("checked", contentStr.indexOf("isMarquee") != -1);

				if (contentStr.indexOf("numberCtrl") != -1) {
					$("tr.content :radio:first").prop("checked", true);
				} else if (contentStr.indexOf("videoCtrl") != -1) {
					$("tr.content :radio").eq(1).prop("checked", true);
				} else {
					$("tr.content :radio:last").prop("checked", true);
				}

			}
			// title
			$(".title input").val(template.name);
			// logo-titleImg
			if (template.titleImg) {
				var logoId = parseInt(template.titleImg);
				$("#logo").val(logoId);
				$("#logoImg").html("<img src=" + materialMap[logoId] + ">");
			}
			// number
			if (template.banner) {
				var numberId = parseInt(template.banner);
				$("#number").val(numberId);
				$("#numberImg").html("<img src=" + materialMap[numberId] + ">");
			}
			// vido
			if (template.video) {
				var videoStr = template.video;
				$("#video").val(videoStr);
				showVideo(videoStr);
			}
			// picture
			if (template.picture) {
				var pictrueStr = template.picture;
				$("#picture").val(pictrueStr);
				showPicture(pictrueStr);
			}
			if (template.picTime) {
				$(".interval input").val(template.picTime);
			}
			if (template.effect) {
				$(".effect select").val(template.effect);
			}
			// marquee
			if (template.marquee) {
				var marqueeStr = template.marquee;
				$("#marquee").val(marqueeStr);
				showMarquee(marqueeStr);
			}
			// food
			var foodStr = template.cakeId;
			$("#food").val(foodStr);
			showFood();
		}
	}
}
function showMarquee(marqueeStr) {
	$("#marqueeDiv").empty();
	var marqueeArr = strToArr(marqueeStr);
	for (var i = 0; i < marqueeArr.length; i++) {
		var marqueeId = marqueeArr[i];
		$("#marqueeDiv").append(marqueeMap[marqueeId] + "<br/>");
	}
}

function showVideo(videoStr) {
	$("#videoDiv").empty();
	var videoArr = strToArr(videoStr);
	for (var i = 0; i < videoArr.length; i++) {
		var videoId = videoArr[i];
		var div = $("<div id='video" + videoId + "'></div>").appendTo($("#videoDiv"));
		div.data("id", videoId);
		div.append("<img src=" + (materialMap[videoId]||"").replace(/.flv/i, ".jpg") + ">");
		div.append("<div class='close'></div>");
	}
}
function showPicture(pictrueStr) {
	$("#pictureDiv").empty();
	var pictureArr = strToArr(pictrueStr);
	for (var i = 0; i < pictureArr.length; i++) {
		var pictureId = pictureArr[i];
		var div = $("<div id='picture" + pictureId + "'></div>").appendTo($("#pictureDiv"));
		div.data("id", pictureId);
		div.append("<img src=" + materialMap[pictureId] + ">");
		div.append("<div class='close'></div>");
	}
}
function showFood() {
	var td = $(".food td").empty();
	var foodStr = $("#food").val();
	var typeArr = [];
	var typeFoodList = {};

	var foodArr = strToArr(foodStr);
	for (var i = 0; i < foodArr.length; i++) {
		var id = foodArr[i];
		var food = foodMap[id];
		if (!food) {
			continue;
		}
		var type = food.type;
		var index = $.inArray(type, typeArr);
		if (index == -1) {
			typeArr.push(type);
		}
		if (!typeFoodList[type]) {
			typeFoodList[type] = [];
		}
		typeFoodList[type].push(food);
	}

	for (var i = 0; i < typeArr.length; i++) {
		var type = typeArr[i];
		var wrap = $("<div class='wrap'><div class='food-type'>" + typeMap[type] + "</div></div>").appendTo(td);
		var foods = typeFoodList[type];
		for (var j = 0; j < foods.length; j++) {
			var food = foods[j];
			var div = $("<div class='inner'></div>").appendTo(wrap);
			div.data("id", food.id);
			var mid = food.materialId;
			var path = materialMap[mid];
			div.append("<img class='img' src='" + path + "'>");
			div.append("<div class='name'>" + food.name + "</div>");
			div.append("<div class='price'>" + food.price + "</div>");
			div.append("<div class='close'></div>");
		}
	}
	drag();
}

function listenRemove() {
	$(".picture").on("click", ".close", function() {
		var div = $(this).parent();
		var id = div.data("id");
		div.remove();
		$("#picture").val(delInStr($("#picture").val(), id));
	});

	$(".video").on("click", ".close", function() {
		var div = $(this).parent();
		var id = div.data("id");
		div.remove();
		$("#video").val(delInStr($("#video").val(), id));
	});

	$(".food").on("click", ".close", function() {
		var div = $(this).parent();
		var id = div.data("id");
		$("#food").val(delInStr($("#food").val(), id));
		showFood();
	});
}

function delInStr(str, id) {
	id = "" + id;
	var arr = str.split(",");
	var index = $.inArray(id, arr);
	if (index != -1) {
		arr.splice(index, 1);
	}
	str = arr.join();
	return str;
}
function strToArr(str) {
	var result = [];
	if (str) {
		var arr = str.split(",");
		if (arr) {
			for (var i = 0; i < arr.length; i++) {
				var int = parseInt(arr[i]);
				if (int) {
					result.push(int);
				}
			}
		}
	}
	return result;
}

function show() {
	showCtrl();
	$(":radio,:checkbox").on("change", function() {
		showCtrl();
	});

	function showCtrl() {
		// type
		var type = $("tr.type").find(":radio:checked").val();
		switch (type) {
		case "s01":
			$(".rowandcol,.size").hide();
			$(".logo").show();
			break;
		case "s02":
			$(".rowandcol,.logo").hide();
			$(".size").show();
			break;
		case "e01":
			$(".size").hide();
			$(".rowandcol,.logo").show();
			break;
		}
		// content
		var content = $("tr.content").find(":radio:checked").val();
		var marquee = $("tr.content :checkbox").prop("checked");

		switch (content) {
		case "number":
			$(".number").show();
			$(".video,.picture,.interval,.effect").hide();
			break;
		case "video":
			$(".video").show();
			$(".number,.picture,.interval,.effect").hide();
			break;
		case "picture":
			$(".picture,.interval,.effect").show();
			$(".number,.video").hide();
			break;
		}
		marquee ? $(".marquee").show() : $(".marquee").hide();
	}
}

function openDialog() {
	function openSelect(id, title, url) {
		var dg = new J.dialog({
			id : id,
			title : title,
			loadingText : "loading...",
			iconTitle : false,
			width : 800,
			height : 600,
			page : url,
			cover : true
		});
		dg.ShowDialog();
	}
	$(".logo button").on("click", function() {
		openSelect("logo", "選擇圖片", "searchMaterial.do?select=true&searchType=1");
	});
	$(".food button").on("click", function() {
		openSelect("food", "選擇圖片", "searchFood.do?select=true&checkbox=Y");
	});
	$(".number button").on("click", function() {
		openSelect("number", "選擇圖片", "searchMaterial.do?select=true&searchType=1");
	});
	$(".video button").on("click", function() {
		openSelect("video", "選擇視訊", "searchMaterial.do?select=true&searchType=2&checkbox=Y");
	});
	$(".picture button").on("click", function() {
		openSelect("picture", "選擇圖片", "searchMaterial.do?select=true&searchType=1&checkbox=Y");
	});
	$(".marquee button").on("click", function() {
		openSelect("marquee", "選擇跑馬燈", "searchMarquee.do?select=true&checkbox=Y");
	});
}

//
function getParam(key) {
	var reg = new RegExp("(^|&)" + key + "=([^&]*)(&|$)", "i");
	var value = window.location.search.substr(1).match(reg);
	if (value) {
		return (value[2]);
	}
	return null;
}

// for-dialog
function setImage(id, value, name, url) {
	$(id).val(value);
	$(id + "Img").html("<img src='" + url + "'>");
}
function setMarquee(id, value, name) {
	$(id).val($(id).val() + value + ",");
	$(id + "Div").append(name + "<br/>");
}
function setFood(id, value, name, price, url) {
	if (id) {
		$(id).val($(id).val() + value + ",");
	}
	showFood();
}
function addImage(id, value, name, url) {
	var str = $(id).val() + value + ",";
	$(id).val(str);
	if (id == "#picture") {
		showPicture(str);
	}
	if (id == "#video") {
		showVideo(str);
	}
}

function drag() {
	var inner = $(".food").find(".inner");
	if (inner.length <= 1) {
		return;
	}

	inner.draggable({
		proxy : "clone",
		cursor : "default",
		onStopDrag : function() {
			locate($(this), $(".food .inner").not(this));
		},
		onDrag : function(e) {// TODO
			// var container = $(".container");
			// var target = $(this).draggable("proxy");
			//
			// var edge = -10;
			//
			// var t = target.offset().top - container.offset().top;
			// var b = container.offset().top + container.outerHeight() -
			// target.offset().top + target.outerHeight();
			//
			// var l = target.offset().left - container.offset().left;
			// var r = container.offset().left + container.outerWidth() -
			// target.offset().left - target.outerWidth();
		}
	});
}

// 重新定位
function locate(target, list) {
	// 原来位置
	var ox = parseInt(target.offset().left), oy = parseInt(target.offset().top);
	var h = parseInt(target.outerHeight());
	var cx, cy;// 插入位置
	var index = 0;// cx索引

	// 计算cy
	var varr = [];
	list.each(function() {
		var p = $(this).offset();
		varr.push(parseInt(p.top));
	});
	cy = varr.range(oy, h).min;
	if (Number.NEGATIVE_INFINITY === cy) {
		cy = varr.range(oy, h).max;
	}

	var ylist = list.filter(function() {
		var top = parseInt($(this).offset().top);
		return top == cy;
	});

	// 定位cx
	var harr = [];
	ylist.each(function() {
		var p = $(this).offset();
		harr.push(parseInt(p.left));
	});
	var cx = harr.range(ox).min;
	index = $.inArray(cx, harr);

	if (index == -1) {
		target.insertBefore(ylist.eq(0));
	} else {
		target.insertAfter(ylist.eq(index));
	}

	var foodStr = "";
	$(".inner").each(function() {
		var id = $(this).data("id");
		foodStr += id + ",";
	});

	$("#food").val(foodStr);
	showFood();
}

function save() {

	var id = parseInt(getParam("id"));
	// type
	var type = $("tr.type").find(":radio:checked").val();
	// content
	var content = $("tr.content").find(":radio:checked").val();
	// marquee
	var ismarquee = $("tr.content :checkbox").prop("checked");

	var cakeId = $("#food").val();
	var name = $.trim($(".title input").val());

	if (!name) {
		alert("模板名稱不能為空");
		return;
	}
	if (!cakeId) {
		alert("餐點不能為空");
		return;
	}

	var data = {
		type : type,
		name : name,
		cakeId : cakeId
	};
	if (id) {
		data.id = id;
	}
	// ----------type-------------

	var titleImg = $.trim($("#logo").val());

	var size = $("tr.size").find(":radio:checked").val();

	var row = parseInt($("#row").val());
	var col = parseInt($("#col").val());

	// -------content----------
	var selectPart = "";
	// number
	var banner = $("#number").val();
	// picture
	var effect = $(".effect select").val();
	var picTime = parseInt($(".interval input").val());
	var picture = $("#picture").val();
	// video
	var video = $("#video").val();
	var marquee = $("#marquee").val();

	if (ismarquee) {
		if (!marquee) {
			alert("請選擇跑馬燈");
			return;
		}
		selectPart += "isMarquee,";
		data.marquee = marquee;
	}

	switch (type) {
	case "s01":
		if (!titleImg) {
			alert("logo不能為空");
			return;
		}
		data.titleImg = titleImg;
		break;
	case "s02":
		data.size = size;
		break;
	case "e01":
		if (!row || row <= 0) {
			alert("行高必須為正整數");
			return;
		}
		if (!col || col <= 0) {
			alert("欄位數必須為正整數");
			return;
		}
		data.row = row;
		data.col = col;
		break;
	}

	switch (content) {
	case "number":
		if (!banner) {
			alert("請選擇頂部圖片素材");
			return;
		}
		selectPart += "numberCtrl";
		data.banner = banner;
		break;
	case "video":
		if (!video) {
			alert("請選擇視頻素材");
			return;
		}
		selectPart += "videoCtrl";
		data.video = video;
		break;
	case "picture":
		if (!picTime || picTime <= 0) {
			alert("時間間隔必須為正整數");
			return;
		}
		if (!picture) {
			alert("圖片不能為空");
			return;
		}
		if (!effect) {
			alert("請選擇效果");
			return;
		}
		selectPart += "pictureCtrl";
		data.picture = picture;
		data.picTime = picTime;
		data.effect = effect;
		break;
	}
	data.selectPart = selectPart;

	$.ajax({
		url : "saveTemplate.do",
		type : "post",
		dataType : "json",
		data : data,
		success : function(data) {
			if (data.success) {
				alert("編輯成功");
				location = "searchTemplate.do";
			} else {
				alert("出錯了");
			}
		}
	});
}