
(function ($) {
	function init(target) {
		var $target = $(target).addClass("tabs").empty();
		var header = $("<div class='tabs-header'></div>").prependTo($target);
		$("<div class='leftScroll'></div>").appendTo(header);
		$("<div class='rightScroll'></div>").appendTo(header);
		var $tabs = $("<div class='wrap'><ul></ul></div>").appendTo(header);
		var content = $("<div class='tabs-content'></div>").appendTo($target);
	}
	function listen(target) {
		$(target).on("click", "li", function () {
			var index = $(target).find("li").index($(this));
			$.data(target, "tabs").options.select = index;
			$.fn.tabs.methods.invoke($(target));
		});
		$(target).on("mouseover", ".close", function () {
			$(this).children("img").show();
		});
		$(target).on("mouseout", ".close", function () {
			$(this).children("img").hide();
		});
		$(target).on("click", ".close img", function (event) {
			event.stopPropagation();
			var index = $(target).find("li").index($(this).parents("li"));
			$.fn.tabs.methods.del($(target), index);
		});
	}
	function setSize(target) {
		var wrap = target.find(".wrap");
		var li = wrap.find("li");
		li.each(function () {// 初始化
			$(this).width("auto");
		});
		var useable = wrap.width();// 实际可用宽度
		var need = 0;
		li.each(function () {
			need += Math.ceil($(this).width());// 需要
			useable -= Math.ceil($(this).outerWidth(true) - $(this).width());// 扣除额外开销
		});
		if (need > useable) {
			li.each(function () {
				$(this).width(Math.floor($(this).width() * useable / need));
			});
		}
	}
	$.fn.tabs = function (options, param) {
		if (typeof options == "string") {
			return $.fn.tabs.methods[options](this, param);
		}
		options = options || {};
		this.each(function () {
			var state = $.data(this, "tabs");
			if (state) {
				$.extend(state.options, options);
			} else {
				$.data(this, "tabs", {options:$.extend({}, $.fn.tabs.defaults, options), tabs:[]});
				init(this);
				listen(this);
			}
		});
	};
	$.fn.tabs.defaults = {select:-1, count:0, array:[]};
	$.fn.panel = {closable:true, cache:true};
	$.fn.tabs.methods = {options:function (target) {
		return $.data(target[0], "tabs").options;
	}, add:function (target, param) {
		var $this = this;
		return target.each(function () {
			param = $.extend({}, $.fn.panel, param || {});
			var $title = $("<li></li>").append("<span class='title'>" + param.title + "</span>").appendTo($(this).find(".wrap ul"));
			if (param.closable) {
				$("<span class='close'><img src='zhsy/img/close.png'></img><span>").appendTo($title);
			}
			var $content = $("<div class='content'></div>").html(param.content).appendTo($(this).children(".tabs-content"));
			var options = $.data(this, "tabs").options;
			options.array.push({title:$title, content:$content});
			options.count++;
			options.select = options.count - 1;
			$this.invoke($(this));
			setSize($(this));
		});
	}, invoke:function (target) {// util
		var options = $.data(target[0], "tabs").options;
		var array = options.array;
		var select = options.select;
		for (var i = 0; i < array.length; i++) {
			var $title = array[i].title;
			var $content = array[i].content;
			if (i == select) {
				$title.addClass("selected");
				$content.show();
					
				//直接修改选中li的样式 TODO CALLBACK
				var text = $.trim($title.text());
				var li = $(".accordion li:contains(" + text + ")");
				$(".accordion li").css("background-color", "");
				li.css("background-color", "#DEF6CB");
			} else {
				$title.removeClass("selected");
				$content.hide();
			}
		}
	}, check:function (target, title) {
		var options = $.data(target[0], "tabs").options;
		for (var i = 0; i < options.array.length; i++) {
			if (title == options.array[i].title.children(".title").text()) {
				return i;
			}
		}
		return -1;
	}, del:function (target, index) {
		var options = $.data(target[0], "tabs").options;
		options.array[index].content.remove();
		options.array[index].title.remove();
		options.array.splice(index, 1);
		options.count--;
		options.select = (index == 0 && options.count > 0) ? 0 : index - 1;
		this.invoke(target);
		setSize(target);
	}};
})(jQuery);

