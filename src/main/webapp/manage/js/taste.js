
$(function () {
	init();
	prompt();
	edit();
	save();
	del();
	find();
});
function init() {
	// init page
	$("#page").page({pageNo:pageNo, pageSize:pageSize, dataCount:dataCount, form:$("form"), onChangePage:function (pageNo, pageSize) {
		$("form").attr("action", "tasteFind.do").submit();
	}});
	// init dialog
	dialog.init();
	$("#back").click(function () {
		dialog.close();
	});
	$("#reset").click(function () {
		$("input[name='name']").val("");
	});
	//type select evnet
	$(".combox select").on("change", function () {
		var value = $.trim($(this).find("option:selected").text());
		$("input[name='typeName']").val(value);
	});
	// init check
	commons.check();
}
function prompt() {
	if ("del" === message) {
		alert(result.del + result.success);
	}
	if ("add" === message) {
		alert(result.add + result.success);
	}
	if ("mod" === message) {
		alert(result.mod + result.success);
	}
	if ("used" === message) {
		alert(lan.tasteUsed);
	}
}
function save() {
	$("#save").click(function () {
		var name = $.trim($("input[name='name']").val());
		var typeName = $.trim($("input[name='typeName']").val());
		
		if (name.length === 0) {
			alert(lan.taste + lan.name + lan.notNull);
			return;
		} else {
			if (name.length > 8) {
				alert(lan.taste + lan.name + lan.max + lan.length + lan.be + "8");
				return;
			}
		}
		if (typeName.length === 0) {
			alert(lan.taste + lan.type + lan.notNull);
			return;
		}
		var price = parseFloat($("input[name='price']").val());
		if (isNaN(price)) {
			alert("請填寫正確的價格");
			return;
		}
		$("form").attr("action", "tasteMerge.do").submit();
	});
}
function del() {
	$(".del").click(function () {
		$("form").find(":checkbox").prop("checked", false);
		$(this).parents("tr").find(":checkbox[name='ids']").prop("checked", true);
		if (!confirm(lan.delSure)) {
			return;
		}
		$("form").attr("action", "tasteDelete.do").submit();
	});
	$("#delCheck").click(function () {
		if ($("form :checkbox:checked").length <= 0) {
			alert(lan.delCheck);
			return;
		}
		if (!confirm(lan.delSure)) {
			return;
		}
		$("form").attr("action", "tasteDelete.do").submit();
	});
}
function edit() {
	$("#add").click(function () {
		$("input[name='id']").val("");
		$("input[name='name']").val("");
		var typeName = $.trim($(".combox select option:first").text());
		$("input[name='typeName']").val(typeName);
		$(".dialog h3 div").text(lan.add + lan.taste);
		dialog.open();
	});
	$(".mod").click(function () {
		var id = $(this).parents("tr").find("input[name='ids']").val();
		var name = $.trim($(this).parents("tr").find(".name").text());
		var typeName = $.trim($(this).parents("tr").find(".typeName").text());
		var price = $.trim($(this).parents("tr").find(".price").text());
		$("input[name='id']").val(id);
		$("input[name='name']").val(name);
		$("input[name='typeName']").val(typeName);
		$("input[name='price']").val(price);
		$(".combox select").val(id);
		$(".dialog h3 div").text(lan.mod + lan.taste);
		dialog.open();
	});
}
function find() {
	$("#search").click(function () {
		$("#page").page({pageNo:1});
		$("form").attr("action", "tasteFind.do").submit();
	});
}

