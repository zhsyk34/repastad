
$(function () {
	logoCtrl();
	takeCtrl();
	deskCtrl();
	depositInit();
	payCtrl();
	alltypeCtrl();
	showorder();
	accessoryCtrl();
	buttonCtrl();
	if ("success" == message) {
		alert(modsuccess);
	}
});
function logoCtrl() {
	showDefault();
	$(".logo input:hidden").each(function () {
		var id = $(this).attr("id");
		$(this).siblings(".btn-primary").on("click", function () {
			openImgSelect(id);
		});
		$(this).siblings(".btn-success").on("click", function () {
			setDefault(id);
		});
	});
}
function showDefault() {
	$(".logo input:hidden").each(function () {
		if ($(this).val() == 0) {
			$(this).siblings(".btn-success").hide();
		} else {
			$(this).siblings(".btn-success").show();
		}
	});
}
function openImgSelect(id) {
	var dg = new J.dialog({id:id, title:picture + choose, loadingText:loading, iconTitle:false, width:800, height:550, page:"searchMaterial.do?select=true&searchType=1", cover:true});
	dg.ShowDialog();
}
function setImage(id, value, name, url) {
	$(id).val(value);
	$(id).siblings("img").attr({"src":url, "title":name});
	showDefault();
}
function setDefault(id) {
	$("#" + id).val(0);
	var src = "sysimg/logo/" + id + language + ".jpg";
	$("#" + id).siblings("img").attr("src", src);
	$("#" + id + "Default").hide();
}
function takeCtrl() {
	initTake();
	takeListener();
	function initTake() {
		if (takeId == 0) {
			$(".take :radio:first").prop("checked", true);
			$("#takeSelect").hide();
			$(".desk").hide();
		} else {
			$(".take :radio:last").prop("checked", true);
			$("#takeSelect").show();
			$("#takeSelect").val(takeId);
			$(".desk").show();
		}
	}
	function takeListener() {
		$(".take").on("change", function () {
			var flag = $(this).find(":radio:first").prop("checked");
			if (flag) {
				$("#takeSelect").hide();
				$(".desk").hide();
				takeId = 0;
				$(".desk :radio:first").prop("checked", true);
			} else {
				$("#takeSelect").show();
				$(".desk").show();
				takeId = $("#takeSelect").val();
			}
			$("input[name='takeId']").val(takeId);
		});
	}
}
function deskCtrl() {
	$(".desk :radio").eq(deskId).prop("checked", true);
}
function depositInit() {
	var lan = navigator.language || navigator.userLanguage;
	lan = lan.substr(-2, 2).toLowerCase();
	if (lan == "cn") {
		$(".tw").hide();
	} else {
		$(".cn").hide();
	}
}
function depositValidate() {
	return validateNum() && validateTotal();
	function validateNum() {
		var flag = true;
		var reg = /^\d*$/;
		var lis = $(".cn,.tw").find("li");
		lis.each(function () {
			var min = parseInt($(this).find("input[name='depMin']").val());
			var max = parseInt($(this).find("input[name='depMax']").val());
			if (isNaN(min) || isNaN(max) || min >= max) {
				flag = false;
				alert(lan.notNumber);
				return false;
			}
		});
		return flag;
	}
	function validateTotal() {
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
		var lis = ul.find("li");
		lis.each(function () {
			var max = parseInt($(this).find("input[name='depMax']").val());
			count += max;
		});
		if (count > limit) {
			alert(lan.moneyConMany);
			return false;
		}
		return true;
	}
}
function payCtrl() {
	initCheck(pay);
	payListener();
	payConfig();
	function initCheck(payArr) {
		var payIds = payArr.split(",");
		for (var i = 0; i < payIds.length; i++) {
			var v = $.trim((payIds[i]));
			$(".pay :checkbox[value='" + v + "']").prop("checked", true);
		}
	}
	function payListener() {
		$(".pay :checkbox").on("change", function () {
			var arr = [];
			$(".pay :checkbox:checked").each(function () {
				arr.push($(this).val());
			});
			$("input[name='pay']").val(arr.join());
			payConfig();
		});
	}
	function payConfig() {
		//wechat config tr
		var wechatFlag = $(".pay :checkbox[value='wechat']").prop("checked");
		wechatFlag ? $(".wechat").show() : $(".wechat").hide();
		
		//alipay config tr
		var alipayFlag = $(".pay :checkbox[value='alipay']").prop("checked");
		alipayFlag ? $(".alipay").show() : $(".alipay").hide();
	}
}
function alltypeCtrl() {
	$(":radio[name='alltype']").eq(alltype).prop("checked", true);
}
function showorder() {
	$(":checkbox[name='shoporder']").prop("checked", shoporder > 0);
	$(":checkbox[name='kitchenorder']").prop("checked", kitchenorder > 0);
}
function accessoryCtrl() {
	accessoryShow();
	$(".accessory").on("change", ":radio", function () {
		accessoryShow();
	});
	function accessoryShow() {
		var used = $(".accessory :radio:checked").val();
		used > 0 ? $(".accessory div:not(:first)").show() : $(".accessory div:not(:first)").hide();
	}
}
function buttonCtrl() {
	$("#save").on("click", function () {
		if (!depositValidate()) {
			return false;
		}
		var used = $(".accessory :radio:checked").val();
		if (used > 0) {
			var name = $.trim($("#name").val());
			if (name.length == 0) {
				alert("\u9644\u52a0\u8cbb\u540d\u7a31\u4e0d\u80fd\u70ba\u7a7a");
				return false;
			}
			var percent = $.trim(($("#percent").val()));
			if (!/^\d{1,2}$/.test(percent)) {
				alert("\u9644\u52a0\u8cbb\u6bd4\u4f8b\u5fc5\u9808\u70ba0-99\u7684\u6574\u6578");
				return false;
			}
		}
		
		//pay config
		//wechat
		if ($(".pay :checkbox[value='wechat']").prop("checked")) {
			var wx_title = $.trim($("#wx_title").val());
			var wx_mchID = $.trim($("#wx_mchID").val());
			var wx_appID = $.trim($("#wx_appID").val());
			var wx_key_status = $.trim($("#wx_key_status").val());
			var wx_key = $.trim($("#wx_key").val());
			if (wx_title.length == 0 || wx_mchID.length == 0 || wx_appID.length == 0 || (wx_key_status == 0 && wx_key.length == 0)) {
				alert("\u8acb\u586b\u5beb\u5b8c\u6574\u7684\u5fae\u4fe1\u914d\u7f6e\u4fe1\u606f");
				return;
			}
		}
		//alipay
		if ($(".pay :checkbox[value='alipay']").prop("checked")) {
			var zfb_title = $.trim($("#zfb_title").val());
			var zfb_partner = $.trim($("#zfb_partner").val());
			var zfb_appid = $.trim($("#zfb_appid").val());
			var zfb_privatekey_status = $.trim($("#zfb_privatekey_status").val());
			var zfb_privatekey = $.trim($("#zfb_privatekey").val());
			var zfb_publickey_status = $.trim($("#zfb_publickey_status").val());
			var zfb_publickey = $.trim($("#zfb_publickey").val());
			if (zfb_title.length == 0 || zfb_partner.length == 0 || zfb_appid.length == 0 || (zfb_privatekey_status == 0 && zfb_privatekey.length == 0) || (zfb_publickey_status == 0 && zfb_publickey.length == 0)) {
				alert("\u8acb\u586b\u5beb\u5b8c\u6574\u7684\u652f\u4ed8\u5bf6\u914d\u7f6e\u4fe1\u606f");
				return;
			}
		}
		$("form").attr("action", "setCon.do").submit();
	});
	$("#reset").click(function () {
		location = "getCon.do";
	});
}

