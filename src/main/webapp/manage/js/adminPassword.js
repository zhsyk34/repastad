
$(function() {
	checkFocus();
	resetData();
	modPassword();
});

// 修改密碼
function modPassword() {
	$("#save").click(function() {
		// 提交前再次判斷，避免無焦點事件...
		checkNull();
		checkSame();
		checkOld();
		// 密碼是否爲空
		var checkPassword = $("#checkPassword").html();
		// 密碼是否一致
		var checkAgain = $("#checkAgain").html();
		// 原密碼檢查結果
		var checkOldPassword = $("#checkOldPassword").html();
		if ($.trim(checkPassword).length > 0) {
			alert(wordnull);
			return;
		}
		if (checkAgain.length > 0) {
			alert(wordDifferent);
			return;
		}
		if (checkOldPassword.length > 0) {
			alert(oldwrong);
		}
		 $("form").attr("action", basePath + "oauth/adminModWord.do").submit();
	});
}

// 重置
function resetData() {
	$("#reset").click(function() {
		$("input[name='oldpassword']").val("");
		$("input[name='password']").val("");
		$("input[name='checkword']").val("");
	});
}
// 焦點離開時判斷
function checkFocus() {
	$("input[name='oldpassword']").blur(function() {
		//checkOld();
	});
	$("input[name='password']").blur(function(){
		checkNull();
	});
	// 檢查新密碼
	$("input[name='checkword']").blur(function() {
		checkSame();
	});
}

// 檢查原密碼
function checkOld() {
	var adminId = $("input[name='adminId']").val();
	var oldpassword = $("input[name='oldpassword']").val();
	$.ajax({
		url : basePath + "json/RightManage_adminCheck.do",
		method : "post",
		dataType : "json",
		data : {
			"adminId" : adminId,
			"oldpassword" : oldpassword
		},
		success : function(data) {
			var check = data.check;
			// alert(check);
			if (!check) {
				$("#checkOldPassword").html(oldwrong);
				$("#checkOldPassword").css("color", "red");
			} else {
				$("#checkOldPassword").html("");
			}
		}
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
	var password = $("input[name='password']").val();
	// var checkword = $("input[name='checkword']").val();
	if ($.trim(password).length == 0) {
		$("#checkPassword").html(wordnull);
		$("#checkPassword").css("color", "red");
	}else{
		$("#checkPassword").html("");
	}
	// 在一致性中判斷
	/*
	 * if ($.trim(checkword).length == 0) { $("#checkAgain").html(wordnull);
	 * $("#checkAgain").css("color", "red"); }
	 */
}
