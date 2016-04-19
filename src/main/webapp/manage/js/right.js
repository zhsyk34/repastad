
$(function () {
	/*隔行变色*/
	$(".discolor tr").on({mouseover:function () {
		$(this).css("background-color", "#C0F4FF");
	}, mouseout:function () {
		$(this).css("background-color", "#F0F5FB");
	}});
	/*全選*/
	$(".del a:first").toggle(function (e) {
		$(":checkbox").prop("checked", true);
		e.preventDefault();
	}, function (e) {
		$(":checkbox").removeAttr("checked");
		e.preventDefault();
	});
	/*全選2*/
	var checkboxAll = $(".checkAll");
	if (checkboxAll.length > 1) {
		checkAll(checkboxAll.eq(0), ".terminal_w");
		checkAll(checkboxAll.eq(1), ".program_w");
	} else {
		checkAll(checkboxAll, ".terminal_w");
	}
	/*終端節目下載*/
	$("#terminaldown").click(function () {
		$(".discolor").eq(0).show();
		$(".discolor").eq(1).hide();
	});
	$("#groupdown").click(function () {
		$(".discolor").eq(0).hide();
		$(".discolor").eq(1).show();
	});
});
/*全選*/
function checkAll(element, site) {
	element.click(function () {
		if (element.attr("checked") == "checked") {
			$(site + " input[name='id']").not(":disabled").attr("checked", true);
		} else {
			$(site + " input[name='id']").removeAttr("checked");
		}
	});
}

