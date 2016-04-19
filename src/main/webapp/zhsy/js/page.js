/*
 * 翻頁控件
 * 2015-06-08
 */
//頁面大小選擇列表
var pageSizeList = [ 5, 10, 15, 20, 30 ];
// 當前頁碼
var pageNo = 1;
// 當前頁面大小
var pageSize = pageSizeList[0];
// 總頁面數
var pageCount = 5;
var count = 0;
// 調用ctrl()時的回調函數----->在頁面中定義
// 使用ajax方式：ajaxFunction()
// 使用action方式：ationFunction()
var backFunction;

// 提供簡繁體版本
var pageLanguage = {
	"zh-CN" : [ "首页", "上一页", "下一页", "末页", "每页", "笔资料", "共", "页", "条记录", "第" ],
	"zh-TW" : [ "首頁", "上一頁", "下一頁", "末頁", "每頁", "筆資料", "共", "頁", "條記錄", "第" ]
};
// 默認語言
var currentLanguage = "zh-TW";

// 翻頁控件
var page = {
	// 數據由後臺action獲取填充
	init : function() {
		//
		pageNo = parseInt(pageNo);
		pageCount = parseInt(pageCount);
		// 靜態加載...
		page.html();
		// 根據後臺數據中的pageNo pageSize pageCount 動態加載...
		page.count();
		page.css();
		// 操作控件
		page.ctrl();
	},
	// 數據通過ajax訪問后獲取填充
	ajaxInit : function() {
		// 靜態加載部分 html sizeList
		page.html();
		page.sizeList();
		// 獲取數據 數據同時返回pageCount
		// 根據回調函數加載數據 返回pageCount后 動態加載 css count
		// 首次加載頁面時直接調用
		backFunction();
		// 操作控件
		page.ctrl();
	},
	// 頁面初始化
	html : function() {
		var locale = window.navigator.language;
		if ("zh-CN" == locale) {
			currentLanguage = locale;
		}
		var language = pageLanguage[currentLanguage];
		var str = "<ul>";
		str += "<li class='first'>" + language[0] + "</li>";
		str += "<li class='prev'>" + language[1] + "</li>";
		str += "<li>|</li>";
		str += "<li class='next'>" + language[2] + "</li>";
		str += "<li class='last'>" + language[3] + "</li>";
		str += "<li>|</li>";
		str += "<li>" + language[4] + "<input id='pageSize' value=" + pageSize + ">" + language[5] + "</li>";
		str += "<li>|</li>";
		str += "<li>" + language[6] + pageCount + language[7] + "  /  " + count + language[8] + "</li>";
		str += "<li>|</li>";
		str += "<li>" + language[9] + "<select id='pageNo'></select>" + language[7] + "</li>";

		$(".page").html(str);
	},
	// pageNo pageSize選擇
	ctrl : function() {
		$(".first:not(.disfirst)").click(function() {
			pageNo = 1;
			if (backFunction) {
				backFunction();
			}
		});
		$(".prev:not(.disprev)").click(function() {
			pageNo--;
			if (pageNo < 1) {
				pageNo = 1;
			}
			if (backFunction) {
				backFunction();
			}
		});
		$(".next:not(.disnext)").click(function() {
			pageNo++;
			if (pageNo > pageCount) {
				pageNo = pageCount;
			}
			if (backFunction) {
				backFunction();
			}
		});
		$(".last:not(.dislast)").click(function() {
			pageNo = pageCount;
			if (backFunction) {
				backFunction();
			}
		});
		$("#pageNo").change(function() {
			pageNo = parseInt($(this).val());
			if (backFunction) {
				backFunction();
			}
		});
		$("#pageSize").change(function() {
			pageNo = 1;
			pageSize = parseInt($(this).val());
			if (backFunction) {
				backFunction();
			}
		});
	},
	// 根據pageCount初始化組件，并選中當前頁pageNo
	count : function() {
		var str = "";
		for (var i = 1; i <= pageCount; i++) {
			if (i == pageNo) {
				// 選中當前頁
				str += "<option selected='selected'>" + i + "</option>";
			} else {
				str += "<option>" + i + "</option>";
			}
		}
		$("#pageNo").html(str);
		$("#pageCount").html(pageCount);
	},
	// 樣式切換
	css : function() {		
		if (pageNo <= 1) {
			$(".first").removeClass("actfirst");
			$(".first").addClass("disfirst");
			$(".prev").removeClass("actprev");
			$(".prev").addClass("disprev");
		} else {
			$(".first").removeClass("disfirst");
			$(".first").addClass("actfirst");
			$(".prev").removeClass("disprev");
			$(".prev").addClass("actprev");
		}
		if (pageNo >= pageCount) {
			$(".next").removeClass("actnext");
			$(".next").addClass("disnext");
			$(".last").removeClass("actlast");
			$(".last").addClass("dislast");
		} else {
			$(".next").removeClass("disnext");
			$(".next").addClass("actnext");
			$(".last").removeClass("dislast");
			$(".last").addClass("actlast");
		}
	}
};
