$(function() {
	
	logoCtrl();
	takeCtrl();
	deskCtrl();
	unifyNoCtrl();
	depositShow();

	//
	reset();
	save();
	//
	if ("success" == message) {
		alert(modsuccess);
	}
});

// logo 操作
function logoCtrl() {
	// 初始化頁面時判斷是否顯示默認按鈕 id + #
	showDefault("#index");
	showDefault("#login");
	$("#loginChoose").click(function() {
		openImgSelect("login");
	});
	$("#indexChoose").click(function() {
		openImgSelect("index");
	});
	$("#loginDefault").click(function() {
		setDefault("login");
	});
	$("#indexDefault").click(function() {
		setDefault("index");
	});
}
/*----------------------*/
// 1.1是否顯示默認按鈕
function showDefault(id) {
	if ($(id).val() > 0) {
		$(id + "Default").css("display", "block");
	} else {
		$(id + "Default").css("display", "none");
	}
}
// 1.2打開選擇界面
function openImgSelect(id) {
	var dg = new J.dialog({
		id : id,
		title : picture + choose,
		loadingText : loading,
		iconTitle : false,
		width : 800,
		height : 550,
		page : basePath + "searchMaterial.do?select=true&searchType=1",
		cover : true
	});
	dg.ShowDialog();
}
// 1.3顯示選擇圖片 此處獲取的id帶#號
function setImage(id, value, name, url) {
	$(id).val(value);
	$(id + "Div").find("img").attr({
		"src" : url,
		"title" : name
	});
	// 默認 按鈕 處理
	showDefault(id);
}
// 1.4設爲默認
function setDefault(id) {
	$("#" + id + "Default").hide();
	$("#" + id).val(0);
	var src = basePath + "sysimg/logo/" + id + language + ".jpg";
	$("#" + id + "Div").html("<img src='" + src + "'/>");
}
/*----------------------*/
function takeCtrl() {
	initTake();
	handleTake();
	// 2.1取餐方式 初始化
	function initTake() {
		if (takeValue == 0) {
			$(".take :radio:first").prop("checked", true);
			$("#takeSelect").hide();
			$("#deskByTake").hide();
		} else {
			$(".take :radio:last").prop("checked", true);
			$("#takeSelect").show();
			$("#takeSelect").val(takeValue);// 内用外帶
			$("#deskByTake").show();
		}
	}

	// 2.2取餐方式選擇
	function handleTake() {
		$(".take").change(function() {
			var flag = $(this).find(":radio:first").prop("checked");
			if (flag) {
				$("#takeSelect").hide();
				takeValue = 0;
				$("#deskByTake").hide();
				$(".desk :radio:first").prop("checked", true);
			} else {
				$("#takeSelect").show();
				takeValue = $("#takeSelect").val();
				$("#deskByTake").show();
			}
		});
	}
}
//
function deskCtrl() {
	$(".desk :radio").eq(deskValue).prop("checked", true);
}

// 編號
function unifyNoCtrl() {
	$(".unifyNo :radio").eq(unifyNoValue).prop("checked", true);
}

function depositShow() {
	$("input[readonly]").css({"background-color":"#EBEBE4","border":"1px solid #7F9DB9"});
	$("input[readonly]").on("focus",function(){
		$(this).blur();
	});
	$("input[readonly]").on("mouseover",function(){
		$(this).css({"cursor":"default"});
	});
	var lan = navigator.language||navigator.userLanguage;
	lan  = lan.substr(-2,2).toLowerCase();
	if(lan == "tw"){
		$("#currency option:first").prop("selected",true);
		$(".cn").hide();
	}else{
		$("#currency option:last").prop("selected",true);
		$(".tw").hide();
	}
	/*
	$("#currency").on("change",function(){
		if($("#currency option:first").prop("selected")){
			$(".cn").hide();$(".tw").show();
		}else{
			$(".tw").hide();$(".cn").show();
		}
	});
	*/
}
function deposit() {
	$(".depWarn").html("");
	var reg = /^\d*$/;
	var li = $("#deposit").find("li");
	for (var i = 0; i < li.length; i++) {
		var minInp = li.eq(i).find("input[name='depMin']");
		var maxInp = li.eq(i).find("input[name='depMax']");
		if (minInp.length > 0 && maxInp.length > 0) {
		
			var min = +($.trim(minInp.val()));
			var max = +($.trim(maxInp.val()));
			if (!reg.test(min)) {
				alert("現金存量應爲數字");
				return false;
			}
			if (!reg.test(max)) {
				alert("現金存量應爲數字");
				return false;
			}
			
			
			if (min > max || min < 0) {
				alert("現金存量的最大值不能小於最小值");
				return false;
			}
		}
	}

	if (!totalCheck($(".nd").eq(0), 600)) {
		return false;
	}
	if (!totalCheck($(".nd").eq(1), 600)) {
		return false;
	}
	if (!totalCheck($(".nv").eq(0), 500)) {
		return false;
	}
	if (!totalCheck($(".nv").eq(1), 500)) {
		return false;
	}
	if (!totalCheck($(".hop").eq(0), 1000)) {
		return false;
	}
	if (!totalCheck($(".hop").eq(1), 1000)) {
		return false;
	}
	return true;

}

function totalCheck(ul, limit) {
	var count = 0;
	var li = ul.find("li:not(.depWarn)");
	for (var i = 0; i < li.length; i++) {
		var maxInp = li.eq(i).find("input[name='depMax']");
		if (maxInp.length > 0) {
			var max = maxInp.val();
			count = (+count) + (+max);
		}

	}
	var warnLi = ul.children(".depWarn");
	if (count > limit) {
		alert(lan.moneyConMany);
		return false;
	}
	return true;
}

// 提交數據
function save() {
	$("#save").click(function() {
		$("input[name='indexId']").val($("#index").val());
		$("input[name='loginId']").val($("#login").val());
		$("input[name='takeId']").val(takeValue);
		$("input[name='unifyNo']").val($(":radio[name='unifyNoValue']:checked").val());
		$("input[name='deskId']").val($(":radio[name='deskType']:checked").val());

		//
		if (!deposit()) {
			return;
		}
		//
		var percent = parseInt($("#percent").val());
		console.log(percent);
		if(isNaN(percent)){
			alert("附加費比例必須為正整數");
		}
		//$("form").attr("action", basePath + "setCon.do").submit();
	});
}
// 重置
function reset() {
	$("#reset").click(function() {
		location = basePath + "getCon.do";
	});
}
