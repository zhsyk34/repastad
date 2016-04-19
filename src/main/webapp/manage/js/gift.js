
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
	$("form").attr("action", "findGift.do").submit();
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
		$("input[name='unit']").val("1");
		$("input[name='total']").val("");
		dialog.open();
		$("form").attr("action", "saveGift.do");
	});
}
function mod() {
	$(".mod").click(function () {
		$("form").find(":checkbox").prop("checked", false);
		$(this).parents("tr").find(":checkbox").prop("checked", true);
		var beginTime = $.trim($(this).parents("tr").find(".beginTime").text());
		var endTime = $.trim($(this).parents("tr").find(".endTime").text());
		var unit = parseInt($(this).parents("tr").find(".unit").text());
		var total = parseInt($(this).parents("tr").find(".total").text());
		$("#d1").val(beginTime);
		$("#d2").val(endTime);
		$("input[name='unit']").val(unit);
		$("input[name='total']").val(total);
		dialog.open();
		$("form").attr("action", "saveGift.do");
	});
	$("#batchSave").click(function () {
		if ($("form :checkbox:checked").length <= 0) {
			alert(lan.modCheck);
			return;
		}
		$("#d1").val("");
		$("#d2").val("");
		$("input[name='unit']").val("1");
		$("input[name='total']").val("");
		dialog.open();
		$("form").attr("action", "saveGift.do");
	});
}
function save() {
	var dateReg = /\d{4}-\d{2}-\d{2}\s+\d{2}:\d{2}/;
	var numReg = /^[1-9]+\d*$/;
	$("#save").click(function () {
		
		//unit and total
		var unit = $("input[name='unit']").val();
		var total = $("input[name='total']").val();
		if ((!numReg.test(unit)) || (!numReg.test(total))) {
			alert(lan.notNumber);
			return;
		}
		//total and send
		var isSend = true;
		var ids = $(":checkbox[name='ids']:checked");
		ids.each(function () {
			var send = $.trim($(this).parents("tr").find(".send").text());
			if (total -send<0) {
				isSend = false;
				alert(lan.haveSend);
				return false;
			}
		});
		if(!isSend){
			return;
		}
		
		var d1 = $.trim($("#d1").val());
		var d2 = $.trim($("#d2").val());
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
		$("form").attr("action", "deleteGift.do").submit();
	});
	$("#delCheck").click(function () {
		if ($("form :checkbox:checked").length <= 0) {
			alert(lan.restoreCheck);
			return;
		}
		if (!confirm(lan.restoreSure)) {
			return;
		}
		$("form").attr("action", "deleteGift.do").submit();
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
}

