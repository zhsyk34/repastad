$(function() {
	checkCtrl();
	page();
	dialog();
	search();
	find();
	merge();
	remove();
});

function checkCtrl() {
	$("#checkParent").click(function() {
		$("table.discolor").find(":checkbox").prop("checked", $(this).prop("checked"));
	});
	$("#checkAll").click(function() {
		$("table.discolor").find(":checkbox").prop("checked", true);
	});
	$("#checkNone").click(function() {
		$("table.discolor").find(":checkbox").prop("checked", false);
	});
	$("#checkInvert").click(function() {
		var flag = true;
		$("table.discolor").find(":checkbox:not(:first)").each(function() {
			flag = flag && (!$(this).prop("checked"));
			$(this).prop("checked", !$(this).prop("checked"));
		});
		$("table.discolor :checkbox:first").prop("checked", flag);
	});
}

function dialog() {
	$(".dialog").dialog({
		closed: true,
		width: 450
	});
	$(".dialog :radio").on("change", function() {
		listenRadio();
	});

	function listenRadio() {
		var trs = $(".dialog table").find("tr").show();
		var pattern = parseInt($(".dialog :radio[name='patterns']:checked").val());
		var distype = parseInt($(".dialog :radio[name='distype']:checked").val());
		var stoptype = parseInt($(".dialog :radio[name='stoptype']:checked").val());

		switch (pattern) {
			case 1:
				trs.filter(".dialog-stoptype,.dialog-unit").hide();
				switch (distype) {
					case 1:
						trs.filter(".dialog-percent").hide();
						break;
					case 2:
						trs.filter(".dialog-discount").hide();
						break;
				}
				break;
			case 2:
				trs.filter(".dialog-stoptype,.dialog-distype,.dialog-percent,.dialog-discount").hide();
				break;
			case 3:
				trs.filter(".dialog-distype,.dialog-percent,.dialog-discount,.dialog-unit").hide();
				switch (stoptype) {
					case 1:
						trs.filter(".dialog-count").hide();
						break;
					case 2:
						trs.filter(".dialog-date").hide();
						break;
				}
				break;
		}
	}
	// back
	$("#back").click(function() {
		$(".dialog").dialog("close");
	});
	// add
	$("#add").on("click", function() {
		$("#editor").modal("title", "");
		$("#editor").data("row", null);
		loadDialog();
		$("#editor").modal("open");
	});
	// update
	$("#data").on("click", ".update", function() {
		$("#data").find(":checkbox").prop("checked", false);
		$(this).parents("tr").find(":checkbox").prop("checked", true);
		var row = $(this).parents("tr").data("row");
		$(".dialog").data("row", row);
		loadDialog();
		$(".dialog").dialog("open");
	});
	// batch
	$("#batch").on("click", function() {
		var checkboxs = $("#data").find(":checkbox:checked");
		if (checkboxs.length == 0) {
			alert("請選擇要編輯的數據");
			return;
		}
		$(".dialog").data("row", null);
		loadDialog();
		$(".dialog").dialog("open");
	});
	// reset
	$("#reset").on("click", function() {
		loadDialog();
	});

	function loadDialog() {
		// clear-init
		$(".dialog tr").find(":radio:first").prop("checked", true);
		$(".dialog").find("input:not(:radio,:checkbox)").val("");

		var trs = $(".dialog table").find("tr").show();
		var row = $(".dialog").data("row");

		if (row && row.id) {
			var pattern = row.pattern;
			$(".dialog :radio[name='patterns']").eq(pattern - 1).prop("checked", true);
			$("#id").val(row.id);
			$("#d1").val(parseDate(row.begin));
			$("#d2").val(parseDate(row.end));
			$("#count").val(row.count ? row.count : "");
			$("#unit").val(row.unit ? row.unit : 1);
			$("#discount").val(row.discount ? row.discount : "");
			$("#percent").val(row.percent ? row.percent : "");

			// 促销方式
			if (row.percent) {
				$(".dialog :radio[name='distype']:last").prop("checked", true);
			}
			// 停售方式
			if (row.count > 0) {
				$(".dialog :radio[name='stoptype']:last").prop("checked", true);
			}
		}
		listenRadio();
	}
}

function merge() {
	$("#save").on("click", function() {
		var id = parseInt($("#id").val());

		var pattern = parseInt($(".dialog :radio[name='patterns']:checked").val());
		var begin = $.trim($("#d1").val());
		var end = $.trim($("#d2").val());
		var count = parseInt($("#count").val());
		var unit = parseInt($("#unit").val());
		var discount = parseInt($("#discount").val());
		var percent = parseInt($("#percent").val());

		var params = {
			pattern: pattern
		}
		var reg = /^\+?[1-9][0-9]*$/;

		switch (pattern) {
			case 1: // 促销
				if (begin.length == 0 || end.length == 0) {
					alert("日期不能為空");
					return;
				}

				if (!reg.test(count)) {
					alert("請填寫正確的數量");
					return;
				}
				params.begin = begin;
				params.end = end;
				params.count = count;

				var distype = parseInt($(".dialog :radio[name='distype']:checked").val());
				switch (distype) {
					case 1: // 价格
						if (!reg.test(discount)) {
							alert("促銷價必須大於0");
							return;
						}
						params.discount = discount;

						break;
					case 2: // 折扣
						if (!reg.test(percent) || percent > 100) {
							alert("折扣百分比必須為1-100之間的整數");
							return;
						}
						params.percent = percent;
						break;
				}
				break;
			case 2: // 赠品
				if (begin.length == 0 || end.length == 0) {
					alert("日期不能為空");
					return;
				}

				if (!reg.test(count)) {
					alert("請填寫正確的數量");
					return;
				}

				if (!reg.test(unit)) {
					alert("每份贈送數量必須為大於0的整數");
					return;
				}

				params.begin = begin;
				params.end = end;
				params.count = count;
				params.unit = unit;
				break;
			case 3:
				var stoptype = parseInt($(".dialog :radio[name='stoptype']:checked").val());
				switch (stoptype) {
					case 1: // 日期
						if (begin.length == 0 || end.length == 0) {
							alert("日期不能為空");
							return;
						}
						params.begin = begin;
						params.end = end;
						break;
					case 2: // 数量
						if (!reg.test(count)) {
							alert("請填寫正確的數量");
							return;
						}
						params.count = count;
						break;
				}
				break;
		}

		var foodIds = [],
			terminalIds = [];

		var trs = $("#data").find(":checkbox:checked").parents("tr");
		trs.each(function() {
			var row = $(this).data("row");
			var foodId = row.foodId;
			var terminalId = row.terminalId;
			foodIds.push(foodId);
			terminalIds.push(terminalId);
		});

		params.foodIds = foodIds;
		params.terminalIds = terminalIds;

		$.ajax({
			url: "json/FoodStatus_merge.do",
			traditional: true,
			async: false,
			data: params,
			success: function(data) {
				$(".dialog").dialog("close");
				if (data.result == "success") {
					find();
					alert("編輯成功");
				}
			}
		});
	});
}

function remove() {
	$("#data").on("click", ".del", function() {
		var id = parseInt($(this).parents("tr").data("row").id);
		del(id ? [id] : []);
	});
	$("#delCheck").on("click", function() {
		var ids = [];
		$("#data :checkbox:checked").each(function() {
			var id = parseInt($(this).parents("tr").data("row").id);
			if (id) {
				ids.push(id);
			}
		});
		del(ids);
	});

	function del(ids) {
		if (ids.length == 0) {
			alert("請選擇要恢復的數據");
			return;
		}
		if (!confirm("是否要恢復所選數據？")) {
			return;
		}
		$.ajax({
			url: "json/FoodStatus_delete.do",
			traditional: true,
			async: false,
			data: {
				ids: ids
			},
			success: function(data) {
				if (data.result == "delete") {
					alert("數據已恢復！");
				}
				$("#page").page({
					pageNo: 1
				});
				find();
			}
		});
	}
}

function search() {
	$("#search").on("click", function() {
		$("#page").page({
			pageNo: 1
		});
		find();
	});
	$(".operation :radio").on("change", function() {
		$("#page").page({
			pageNo: 1
		});
		find();
	});
}

function find() {
	var foodName = $.trim($("#foodName").val());
	var terminalNo = $.trim($("#terminalNo").val());
	var options = $("#page").page("options");
	var pattern = parseInt($(".operation :radio:checked").val());
	$.ajax({
		url: "json/FoodStatus_find.do",
		async: false,
		data: {
			foodName: foodName,
			terminalNo: terminalNo,
			pattern: pattern,
			pageNo: options.pageNo,
			pageSize: options.pageSize,
			time:new Date().getTime()
		},
		success: function(data) {
			load(data);
			page(data);
		}
	});
	$("th,td").show();
	switch (pattern) {
		case 0:
			break;
		case 1:
			$(".unit").hide();
			break;
		case 2:
			$(".price,.discount").hide();
			break;
		case 3:
			$(".price,.discount,.unit").hide();
			break;
		case 4:
			break;
	}

	function load(data) {
		$("#data").empty();
		$("#checkParent").prop("checked", false);

		if (!data.list) {
			return;
		}

		var str = "<tr>";
		str += "<td><input type='checkbox' class='id'></td>";
		str += "<td class='index'></td>";
		str += "<td class='terminalNo'></td>";
		str += "<td class='terminalLocation'></td>";
		str += "<td class='foodName'></td>";
		str += "<td class='active'></td>";
		str += "<td class='time'></td>";

		str += "<td class='count'></td>";
		str += "<td class='send'></td>";
		str += "<td class='remain'></td>";

		// gift
		str += "<td class='unit'></td>";

		// discount
		str += "<td class='price'></td>";
		str += "<td class='discount'></td>";

		str += "<td class='edit'><button class='linkbutton linkbutton-info update'>設置</button></td>";
		str += "</tr>";

		$.each(data.list, function(index, row) {
			var tr = $(str);
			tr.data("row", row);
			tr.find(".id").val(row.id);
			tr.find(".index").text(index + 1);
			tr.find(".terminalNo").text(row.terminalNo);
			tr.find(".terminalLocation").text(row.terminalLocation);
			tr.find(".foodName").text(row.foodName);
			tr.find(".price").text(row.price);

			var active = "";

			if (row.pattern) {
				if (row.begin && row.end) {
					tr.find(".time").html("<div>從:" + parseDate(row.begin) + "</div><div>到:" + parseDate(row.end) + "</div>");
				}
				if (row.count) {
					tr.find(".count").text(row.count);
					tr.find(".send").text(row.send);
					tr.find(".remain").text(Math.max(row.count - row.send,0));
				}

				switch (row.pattern) {
					case 1:
						active = "促銷";
						tr.find(".discount").text(row.discount);						
						break;
					case 2:
						active = "贈送";
						tr.find(".unit").text(row.unit);
						break;
					case 3:
						active = "停售";
						break;
				}
				tr.find(".active").text(active);
				tr.find(".edit").append("<button class='linkbutton linkbutton-danger del'>恢復</button>");
			}
			$("#data").append(tr);
		});
	}
}

function page(data) {
	var options = {
		onChangePage: function() {
			find();
		}
	};
	if (data) {
		if (data.pageNo) {
			options.pageNo = data.pageNo;
		}
		if (data.pageSize) {
			options.pageSize = data.pageSize;
		}
		if (data.dataCount >= 0) {
			options.dataCount = data.dataCount;
		}
	}
	$("#page").page(options);
}

function parseDate(str) {
	if (str) {
		return str.replace("T", " ");
	}
	return "";
}