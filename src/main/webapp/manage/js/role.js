
$(function () {
	// 加載頁面控件
	backFunction = roleList;
	page.init();
	// 增加 刪除 修改
	roleAdd();
	roleDel();
	roleMod();
	//查詢
	roleSearch();
});
function roleList() {
	var keyword = $(".keyword").val();
	$("input[name='roleName']").val(keyword);
	$("input[name='pageNo']").val(pageNo);
	$("input[name='pageSize']").val(pageSize);
	$("form").attr("action", "roleList.do").submit();
}
function roleAdd() {
	$("#add").click(function () {
		$("form").attr("action", basePath + "oauth/roleAdd.do").submit();
	});
}
function roleDel() {
	$(".del").click(function () {
		if (confirm(delsure)) {
			var roleId = $(this).parents("tr").find("input.roleId").val();
			$("input[name='roleId']").val(roleId);
			$("form").attr("action", basePath + "oauth/roleDel.do").submit();
		}
	});
}
function roleMod() {
	$(".mod").click(function () {
		var roleId = $(this).parents("tr").find("input.roleId").val();
		$("input[name='roleId']").val(roleId);
		$("form").attr("action", basePath + "oauth/roleEdit.do").submit();
	});
}
function roleSearch() {
	$(".search").click(function () {
		pageNo = 1;
		roleList();
	});
}

