
$(function () {
	init();
	find();
	add();
	mod();
	save();
	del();
});
function list() {
	$("input[name='pageNo']").val(pageNo);
	$("input[name='pageSize']").val(pageSize);
	$("form").attr("action", "findDiscount.do").submit();
}
function find() {
	$("#search").click(function () {
		pageNo = 1;
		list();
	});
	$(":radio[name='status']").on("change", function () {
		pageNo = 1;
		list();
	});
}
function add() {
	$(".config").click(function () {
		$("form").find(":checkbox").prop("checked", false);
		$(this).parents("tr").find(":checkbox").prop("checked", true);
		
		$("#d1").val("");
		$("#d2").val("");
		$("input[name='price']").val("");
		$("input[name='percent']").val("");
		initDialog();
		dialog.open();
		$("form").attr("action", "saveDiscount.do");
	});
}
function mod() {
	$(".mod").click(function () {
		$("form").find(":checkbox").prop("checked", false);
		$(this).parents("tr").find(":checkbox").prop("checked", true);
		var beginTime = $.trim($(this).parents("tr").find(".beginTime").text());
		var endTime = $.trim($(this).parents("tr").find(".endTime").text());
		var price = $(this).parents("tr").find(".price").text();
		var percent = $.trim($(this).parents("tr").find(".percent").text());
		price = price.replace(/\$|￥|\s/g, "");
		$("#d1").val(beginTime);
		$("#d2").val(endTime);
		$("input[name='price']").val(price);
		$("input[name='percent']").val(percent);
		initDialog();
		dialog.open();
		$("form").attr("action", "saveDiscount.do");
	});
	$("#batchSave").click(function () {
		if ($("form :checkbox:checked").length <= 0) {
			alert(lan.modCheck);
			return;
		}
		$("#d1").val("");
		$("#d2").val("");
		$("input[name='price']").val("");
		$("input[name='percent']").val("");
		initDialog();
		dialog.open();
		$("form").attr("action", "saveDiscount.do");
	});
}
function save() {
	var dateReg = /\d{4}-\d{2}-\d{2}\s+\d{2}:\d{2}/;
	$("#save").click(function () {
		var price = +($("input[name='price']").val());
		var percent = +($("input[name='percent']").val());
		var d1 = $.trim($("#d1").val());
		var d2 = $.trim($("#d2").val());
		if ((isNaN(price) || price <= 0) && (isNaN(percent) || percent <= 0) || percent >= 100) {
			alert(lan.notNumber);
			return;
		}
		if ((!dateReg.test(d1)) || (!dateReg.test(d2))) {
			alert(lan.notDate);
			return;
		}
		$("form").submit();
		dialog.close();
	});
}
function del() {
	$(".del").click(function () {
		$("form").find(":checkbox").prop("checked", false);
		$(this).parents("tr").find(":checkbox").prop("checked", true);
		if (!confirm(lan.restoreSure)) {
			return;
		}
		$("form").attr("action", "deleteDiscount.do").submit();
	});
	$("#delCheck").click(function () {
		if ($("form :checkbox:checked").length <= 0) {
			alert(lan.restoreCheck);
			return;
		}
		if (!confirm(lan.restoreSure)) {
			return;
		}
		$("form").attr("action", "deleteDiscount.do").submit();
	});
}
function init() {
	$("input[name='pageNo']").val(pageNo);
	$("input[name='pageSize']").val(pageSize);
	$(":checkbox[name='ids']").on("click", function () {
		$(this).parents("tr").find(":checkbox").prop("checked", $(this).prop("checked"));
	});
	//
	dialog.init();
	page.init();
	backFunction = list;
	commons.discolor();
	commons.check();	
	//
	$("#back").click(function () {
		dialog.close();
	});
	//
	$(".food").on("mouseover", function () {
		var img = $(this).children("img");
		img.addClass().show();
	});
	$(".food").on("mouseout", function () {
		$(this).children("img").hide();
	});
	initDialog();
}

function initDialog(){
	$(":radio[name='type']:first").prop("checked", true);
	$(".type2").hide();
	$(".type1").show();
	$(":radio[name='type']").off().on("change", function () {
		var type = $(":radio[name='type']:checked").val();
		if (type == 1) {
			$(".type2").hide();
			$(".type1").show();
		} else {
			$(".type1").hide();
			$(".type2").show();
		}
	});
}

