
$(function () {
	initTitle();
	typeList();
	typeSave();
	resetDate();
});
function initTitle() {
	var id = $("input[name='id']").val();
	if (id > 0) {
		$("title").text(typeEdit);
		$(".body_title div").html(typeEdit);
	}
}
// 返回
function typeList() {
	$("#back").click(function () {
		location = basePath + "searchFoodType.do";
	});
}
// 保存用戶
function typeSave() {
	$("#save").click(function () {
		var name = $("input[name='typeName']").val();
		if ($.trim(name).length == 0) {
			alert(typeNull);
			return;
		}
		$("form").attr("action", basePath + "saveFoodType.do").submit();
	});
}
function resetDate() {
	$("#reset").click(function () {
		$("input[name='typeName']").val("");
	});
}

