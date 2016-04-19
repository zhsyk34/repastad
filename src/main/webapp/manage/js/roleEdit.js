
$(function () {
	roleList();
	roleSave();
	checkCtrl();
	resetData();
	roleCheck();
});

// checkbox選擇控制
function checkCtrl() {
	$(".checkAll").click(function () {
		var flag = $(this).prop("checked");
		$(this).parents("tr").find("td:last :checkbox").prop("checked", flag);
	});
	$("#reset").click(function () {
		$(":checkbox").prop("checked", false);
	});
	$("#super").click(function () {
		$(":checkbox").prop("checked", $(this).prop("checked"));
	});
	// 顯示已擁有權限
	$.each(authorityIds, function (i, n) {
		$(":checkbox[value='" + $.trim(n) + "']").prop("checked", true);
	});
}
// 保存角色
function roleSave() {
	$("#save").click(function () {
		var roleName = $("input[name='roleName']").val();
		var namecheck = $("#roleNameCheck").html();
		if ($.trim(roleName).length == 0) {
			alert(namenull);
			return;
		}
		if (namecheck.length > 0) {
			alert(roleExist);
			return;
		}
		$("form").attr("action", basePath + "oauth/roleSave.do").submit();
	});
}

// 返回角色列表
function roleList() {
	$("#back").click(function () {
		location = basePath + "oauth/roleList.do";
	});
}
function resetData() {
	$("#reset").click(function () {
		$("input[name='roleName']").val("");
		$(":checkbox").prop("checked", false);
	});
}
function roleCheck() {
	$("input[name='roleName']").blur(function () {
		var roleName = $(this).val();
		$.ajax({url:basePath + "json/RightManage_roleNameCheck.do", method:"post", data:{"roleName":roleName}, success:function (data) {
			var exsit = data.exsit;
			if (exsit) {
				$("#roleNameCheck").html(roleExist);
				$("#roleNameCheck").css("color", "red");
			} else {
				$("#roleNameCheck").html("");
			}
		}});
	});
}

