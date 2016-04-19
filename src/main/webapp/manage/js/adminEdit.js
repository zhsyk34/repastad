$(function() {
	initTitle();
	adminList();
	adminSave();
	initCheck();
	resetData();
	checkFocus();
});

// 增加或編輯判斷
function initTitle() {
	var adminId = $("input[name='adminId']").val();
	if (adminId == 0 || adminId.length == 0) {
		$(".body_title h3 div").html(userAdd);
		$("title").text(userAdd);
	}
}

// 返回用戶列表
function adminList() {
	$("#back").click(function() {
		location = basePath + "oauth/adminList.do";
	});
}
// 保存用戶
function adminSave() {
	$("#save").click(function() {
		// 提交前再次判斷，避免無焦點事件...
		checkName();
		checkNull();
		checkSame();
		//checkRole();
		// 用戶名
		var adminName = $("input[name='adminName']").val();
		var checkUserName = $("#checkUserName").html();
		// 密碼是否爲空
		var checkPassword = $("#checkPassword").html();
		// 密碼是否一致
		var checkAgain = $("#checkAgain").html();
		//是否選擇角色
		//var checkRoleChange = $("#checkRole").html();

		// 用戶名爲空
		if ($.trim(adminName).length == 0) {
			alert(namenull);
			return;
		}
		// 重名
		if (checkUserName.length > 0) {
			alert(userExsit);
			return;
		}
		// 密碼爲空
		if ($.trim(checkPassword).length > 0) {
			alert(wordnull);
			return;
		}
		// 密碼不一致
		if (checkAgain.length > 0) {
			alert(wordDifferent);
			return;
		}
		/*
		if (checkRoleChange.length > 0) {
			alert(unrole);
			return;
		}
		*/
		
		$("form").attr("action", basePath + "oauth/adminSave.do").submit();
	});
}

// checkbox選擇控制
function initCheck() {
	// 編輯用戶時顯示當前用戶已擁有的角色
	$.each(roleIds, function(i, n) {
		$(":radio[value='" + $.trim(n) + "']").prop("checked", true);
	});
	// 修正首個checkbox的margin
	$(":radio[name='roleIds']").css("margin-left", "0");
}
function resetData() {
	$("#reset").click(function() {
		$("input[name='adminName']").val("");
		$("input[name='password']").val("");
		$("input[name='checkword']").val("");
		$(":checkbox").prop("checked", false);
	});
}
//
function checkName() {
	var adminName = $("input[name='adminName']").val();
	if ($.trim(adminName).length == 0) {
		$("#checkUserName").html(namenull);
		$("#checkUserName").css("color", "red");
		return;
	}else{
		$("#checkUserName").html();
	}
	var adminId = $("input[name='adminId']").val();
	// 增加用戶時判斷是否重名
	$.ajax({
		url : basePath + "json/RightManage_adminCheck.do",
		method : "post",
		dataType : "json",
		data : {
			"adminId" : adminId,
			"adminName" : adminName
		},
		success : function(data) {
			var exsit = data.exsit;
			// 重名提示
			if (exsit) {
				$("#checkUserName").html(userExsit);
				$("#checkUserName").css("color", "red");
			} else {
				$("#checkUserName").html("");
			}
		}
	});
}

// 焦點離開時判斷
function checkFocus() {
	$("input[name='adminName']").blur(function() {
		checkName();
	});
	$("input[name='password']").blur(function() {
		checkNull();
	});
	$("input[name='checkword']").blur(function() {
		checkSame();
	});
}

// 檢查密碼是否一致
function checkSame() {
	var password = $("input[name='password']").val();
	var checkword = $("input[name='checkword']").val();
	if (password != checkword) {
		$("#checkAgain").html(wordDifferent);
		$("#checkAgain").css("color", "red");
	} else {
		$("#checkAgain").html("");
	}
}
// 檢查密碼是否爲空
function checkNull() {
	var adminId = $("input[name='adminId']").val();
	var password = $("input[name='password']").val();
	// 編輯時允許密碼爲空
	if (adminId <= 0 || adminId.length == 0) {
		if ($.trim(password).length == 0) {
			$("#checkPassword").html(wordnull);
			$("#checkPassword").css("color", "red");
		} else {
			$("#checkPassword").html("");
		}
	}
}

function checkRole(){
	var roleId = $(":radio[name='roleIds']:checked").val();
	if(roleId){
		$("#checkRole").html("");
	}else{
		$("#checkRole").html(unrole);
		$("#checkRole").css("color", "red");
	}
}
