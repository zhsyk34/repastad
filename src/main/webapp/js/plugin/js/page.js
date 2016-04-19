(function($) {
	$.fn.page = function(options) {
		if (typeof options == "string") {
			switch (options) {
			case "options":
				return $.data(this[0], "page").options;
			}
		}
		options = options || {};
		return this.each(function() {
			var state = $.data(this, "page");
			if (state) {
				$.extend(state.options, options);
			} else {
				$.data(this, "page", {
					options : $.extend({}, $.fn.page.defaults, options)
				});
			}
			initHtml(this);
			listener(this);
		});
	};

	$.fn.page.defaults = {
		pageNo : 1,
		pageSize : 10,
		dataCount : 0,
		form : null,
		onChangePage : function(pageNo, pageSize) {
		}
	};

	function initHtml(target) {
		var options = $.data(target, "page").options;
		var pager = $(target).empty().addClass("page");
		var text = $.fn.page.defaults.text;

		var tr = $("<tr></tr>");
		var table = $("<table></table>").append(tr);
		pager.append(table);

		// pageCtrl
		$("<td class='first'><div></div></td>").appendTo(tr);
		$("<td class='first'><span>" + text.first + "</span></td>").appendTo(tr);
		$("<td class='previous'><div></div></td>").appendTo(tr);
		$("<td class='previous'><span>" + text.previous + "</span></td>").appendTo(tr);
		$("<td class='separator'><div></div></td>").appendTo(tr);
		$("<td class='next'><div></div></td>").appendTo(tr);
		$("<td class='next'><span>" + text.next + "</span></td>").appendTo(tr);
		$("<td class='last'><div></div></td>").appendTo(tr);
		$("<td class='last'><span>" + text.last + "</span></td>").appendTo(tr);
		$("<td class='separator'><div></div></td>").appendTo(tr);

		// pageSize
		var pageSizeStr = "<span>" + text.sizePre + "</span>" + "<input class='pageSize'>" + "<span>" + text.sizeSuf + "</span>";
		$("<td></td>").append(pageSizeStr).appendTo(tr);
		$("<td class='separator'><div></div></td>").appendTo(tr);

		// pageCount
		var pageCountStr = "<span>" + text.pageCountPre + "</span>" + "<span class='pageCount'></span>" + "<span>" + text.pageCountSuf + "</span>";
		$("<td></td>").append(pageCountStr).appendTo(tr);
		// dataCount
		var dataCountStr = "<span>" + text.dataCountPre + "</span>" + "<span class='dataCount'></span>" + "<span>" + text.dataCountSuf + "</span>";
		$("<td></td>").append(dataCountStr).appendTo(tr);
		$("<td class='separator'><div></div></td>").appendTo(tr);

		// pageNo
		var pageNoStr = "<span>" + text.pageNoPre + "</span>" + "<select class='pageNo'></select>" + "<span>" + text.pageNoSuf + "</span>";
		$("<td></td>").append(pageNoStr).appendTo(tr);

		// commit with form
		if (options.form) {
			$(options.form).find("input[name='pageNo']").length || $("input[name='pageNo']").appendTo($(options.form));
			$(options.form).find("input[name='pageSize']").length || $("input[name='pageSize']").appendTo($(options.form));
		}
		initData(target);
		initCss(target);
	}

	function initCss(target) {
		var options = $.data(target, "page").options;
		var pageNo = options.pageNo;
		var pageCount = options.pageCount;

		if (pageNo <= 1) {
			$(".first ,.previous").addClass("disabled");
		} else {
			$(".first ,.previous").removeClass("disabled");
		}

		if (pageNo >= pageCount) {
			$(".next ,.last").addClass("disabled");
		} else {
			$(".next ,.last").removeClass("disabled");
		}
	}
	function initData(target) {
		var options = $.data(target, "page").options;
		var pageNo = options.pageNo;
		var pageSize = options.pageSize;
		var dataCount = options.dataCount;
		var pageCount = Math.ceil(dataCount / pageSize);
		options.pageCount = pageCount;

		$(target).find(".dataCount").text(dataCount);
		$(target).find(".pageCount").text(pageCount);
		$(target).find(".pageSize").val(pageSize);

		$(options.form).find("input[name='pageNo']").val(pageNo);
		$(options.form).find("input[name='pageSize']").val(pageSize);

		var pageNoStr = "";
		for (var i = 1; i <= pageCount; i = i + 1) {
			if (pageNo === i) {
				pageNoStr += "<option selected='selected' value='" + i + "'>" + i + "</option>";
			} else {
				pageNoStr += "<option value='" + i + "'>" + i + "</option>";
			}
		}
		$(target).find(".pageNo").html(pageNoStr);
	}
	function listener(target) {
		var options = $.data(target, "page").options;

		// turn pageNo listener
		$(target).find("td").off(".page").on("click.page", function() {// TODO
			if ($(this).hasClass("disabled")) {
				return;
			} else if ($(this).hasClass("first")) {
				options.pageNo = 1;
			} else if ($(this).hasClass("previous")) {
				options.pageNo = options.pageNo <= 1 ? 1 : options.pageNo - 1;
			} else if ($(this).hasClass("next")) {
				options.pageNo = options.pageNo < options.pageCount ? options.pageNo + 1 : options.pageCount;
			} else if ($(this).hasClass("last")) {
				options.pageNo = options.pageCount;
			} else {
				return;
			}
			initCss(target);
			initData(target);
			options.onChangePage.call(this, options.pageNo, options.pageSize);
		});

		// change pageNo istener
		$(target).find(".pageSize").off(".page").on("keydown.page", function(e) {
			if (e.keyCode == 13) {
				options.pageNo = 1;
				options.pageSize = parseInt($(this).val()) || options.pageSize;
				initCss(target);
				initData(target);
				options.onChangePage.call(this, options.pageNo, options.pageSize);
			}
		});
		// pageSize istener
		$(target).find(".pageNo").off(".page").on("change.page", function(e) {
			options.pageNo = parseInt($(this).val());
			initCss(target);
			initData(target);
			options.onChangePage.call(this, options.pageNo, options.pageSize);
		});
	}	
	
	$.fn.page.defaults.text = {
		first : "首頁",
		previous : "上一頁",
		next : "下一頁",
		last : "末頁",
		sizePre : "每頁",
		sizeSuf : "筆資料",
		pageCountPre : "共",
		pageCountSuf : "頁",
		dataCountPre : "/",
		dataCountSuf : "筆記錄",
		pageNoPre : "第",
		pageNoSuf : "頁"
	};
})(jQuery);
