var have, list;
$(function() {
	oauth.list();
});
var oauth = {
	list : function() {
		$.ajax({
			url : basePath + "json/RightManage_findOauthList.do",
			method : "post",
			async : false,
			success : function(data) {
				// 擁有權限
				have = data.authorityIds;
				// 權限清單
				list = data.oauthList;
			}
		});
	},
	check : function(name) {
		if(!list){
			return true;
		}
		var need = list[name];
		if (!need) {
			return true;
		} else {
			if ($.inArray(need, have) >= 0) {
				return true;
			} else {
				return false;
			}
		}
	}
};
