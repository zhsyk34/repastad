$(function() {
	init();
	prompt();
	save();
	del();
	edit();
});
function init() {
	// init page
	$("#page").page({
		pageNo : pageNo,
		pageSize : pageSize,
		dataCount : dataCount,
		form : $("form"),
		onChangePage : function(pageNo, pageSize) {
			$("form").attr("action", "findRule.do").submit();
		}
	});
	// init dialog
	$(".dialog").dialog({
		width:450
	});
	$("#back").click(function() {
		$(".dialog").dialog("close");
	});
	$("#reset").click(function() {
		$(".dialog input").val("");
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
	
}
function save() {
	$("#save").click(function() {
		
		
		var startNo = $("input[name='startNo']").val();
		var noNumber = $("input[name='noNumber']").val();
		var minNumber = $("input[name='minNumber']").val();
		
		
		
		if (startNo.length === 0) {
			alert(lan.startNo+ lan.notNull);
			return;
		} 
		if (isNaN(noNumber) || noNumber<=0) {
			alert(lan.beginNo);
			return;
		}
		if (isNaN(minNumber) || minNumber<=0) {
			alert(lan.bitNo);
			return;
		}
		$("form").attr("action", "saveRule.do").submit();
	});
}
function del() {
	$(".del").click(function() {
		$("form").find(":checkbox").prop("checked", false);
		$(this).parents("tr").find(":checkbox[name='ids']").prop("checked", true);
		if (!confirm(lan.delSure)) {
			return;
		}
		$("form").attr("action", "delRule.do").submit();
	});
	$("#delCheck").click(function() {
		if ($("form :checkbox:checked").length <= 0) {
			alert(lan.delCheck);
			return;
		}
		if (!confirm(lan.delSure)) {
			return;
		}
		$("form").attr("action", "delRule.do").submit();
	});
}
function edit() {
	$("#add").click(function() {
		$(".dialog input").val("");
		$(".dialog h3 div").text(lan.add + lan.orderRule);
		$(".dialog").dialog("open");
	});
	$(".mod").click(function() {
		var id = $(this).parents("tr").find("input[name='ids']").val();
		var startNo = $.trim($(this).parents("tr").find(".startNo").text());
		var noNumber = parseInt($(this).parents("tr").find(".noNumber").text());
		var minNumber = parseInt($(this).parents("tr").find(".minNumber").text());
		$("input[name='id']").val(id);
		$("input[name='startNo']").val(startNo);
		$("input[name='noNumber']").val(noNumber);
		$("input[name='minNumber']").val(minNumber);
		$(".dialog h3 div").text(lan.mod + lan.orderRule);
		$(".dialog").dialog("open");
	});
	
	$(".use a").on("click",function(){
	var id = $(this).parents("tr").find("input[name='ids']").val();
		$("input[name='id']").val(id);
		
		
		var usedInp = $(this).siblings("input");
		var used = (parseInt(usedInp.val()) + 1 )% 2;	
		
		$("input[name='used']").val(used);
		
		$("form").attr("action", "saveRule.do").submit();
	});
}



