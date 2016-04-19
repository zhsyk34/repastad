
$(function () {
	// 加載頁面控件
	backFunction = adminList;
	page.init();
	// 增加 刪除 修改
	adminAdd();
	adminDel();
	adminMod();
	//查詢
	adminSearch();
	//
	hideRoot();
});
function adminList() {
	var keyword = $(".keyword").val();
	$("input[name='adminName']").val(keyword);
	$("input[name='pageNo']").val(pageNo);
	$("input[name='pageSize']").val(pageSize);
	$("form").attr("action", "adminList.do").submit();
}
function adminAdd() {
	$("#add").click(function () {
		$("form").attr("action", basePath + "oauth/adminAdd.do").submit();
	});
}
function adminDel() {
	$(".del").bind("click", function () {
		if (confirm(delsure)) {
			var adminId = $(this).parents("tr").find("input.adminId").val();
			$("input[name='adminId']").val(adminId);
			$("form").attr("action", basePath + "oauth/adminDel.do").submit();
		}
	});
}
function adminMod() {
	$(".mod").click(function () {
		var adminId = $(this).parents("tr").find("input.adminId").val();
		$("input[name='adminId']").val(adminId);
		$("form").attr("action", basePath + "oauth/adminEdit.do").submit();
	});
}
function adminSearch() {
	$(".search").click(function () {
		pageNo = 1;
		adminList();
	});
}
function hideRoot() {
	$(".adminId").each(function () {
		var id = $(this).val();
		if(id == 1){
			$(this).parents("tr").find(".del").hide();
		}
	});
}

