
var commons = {isEmpty:function (str) {
	if (str === undefined || str === null || $.trim(str).length === 0) {
		return true;
	}
	return false;
}, discolor:function () {
	$("tr").on({mouseover:function () {
		$(this).css("background-color", "#D0F3F1");
	}, mouseout:function () {
		$(this).css("background-color", "#F0F5FB");
	}});
}, check:function () {
	$("#checkParent").click(function () {
		$("form").find(":checkbox").prop("checked", $(this).prop("checked"));
	});
	$("#checkAll").click(function () {
		$("form").find(":checkbox").prop("checked", true);
	});
	$("#checkNone").click(function () {
		$("form").find(":checkbox").prop("checked", false);
	});
	$("#checkInvert").click(function () {
		var flag = true;
		$("form").find(":checkbox:not(:first)").each(function () {
			flag = flag && (!$(this).prop("checked"));
			$(this).prop("checked", !$(this).prop("checked"));
		});
		$("form :checkbox:first").prop("checked", flag);
	});
	$("#checkAll").click(function () {
		$("form").find(":checkbox").prop("checked", true);
	});
}};

