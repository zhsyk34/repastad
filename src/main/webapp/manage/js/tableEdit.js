
$(function () {
	$("#back").click(function () {
		location = "tableFind.do";
	});
	$("#reset").click(function () {
		$("input[name='name']").val("");
		$("input[name='begin']").val("");
		$("input[name='end']").val("");
	});
	batch();
	save();
});
function batch() {
	function isShow() {
		var v = $("input[name='batch']").val();
		if (v == 1) {
			$("#suffix").show();
			$("#batch").css("background-color", "#3099DA");
		} else {
			$("#suffix").hide();
			$("#batch").css("background-color", "#7AC1DD");
		}
	}
	isShow();
	$("#batch").click(function () {
		var v = $("input[name='batch']").val();
		v++;
		$("input[name='batch']").val(v % 2);
		isShow();
	});
}
function save() {
	var reg = /^\d+$/;
	$("#save").click(function () {
		var batch = $("input[name='batch']").val();// 批量
		var name = $("input[name='name']").val();
		var begin = $("input[name='begin']").val();
		var end = $("input[name='end']").val();
		var nameLength = name.length;
		if (batch > 0) {
			if (!(reg.test(begin) && reg.test(end))) {
				alert(noValidate);
				return;
			} else {
				if (begin - end > 0) {
					alert(suffix);
					return;
				}
			}
			nameLength += (end + "").length;
		}
		if (nameLength == 0) {
			alert(checkAgain + tableName);
			return;
		}
		$("form").submit();
	});
}

