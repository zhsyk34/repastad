(function($) {

	$.fn.accordion = function(options, param) {
		if (typeof options == "string") {
			return $.fn.accordion.methods[options](this, param);
		}

		options = options || {};

		return this.each(function() {
			var state = $.data(this, "accordion");
			if (state) {
				$.extend(state.options, options);
			} else {
				$.data(this, "accordion", {
					options : $.extend({}, $.fn.accordion.defaults, options)
				});
			}
			init(this);
		});
	};

	$.fn.accordion.methods = {//TODO

	};

	$.fn.accordion.defaults = {
		width : "auto",
		height : "auto",
		fit : false,
		border : true,
		menu : "p",
		list : "ul",
		multiple : true,
		animate : true
	};

	function init(target) {
		var options = $.data(target, "accordion").options;

		$(target).addClass("accordion");
		var menu = $(target).children(options.menu).addClass("menu");
		var list = $(target).children(options.list).addClass("list");

		menu.on("click", function() {
			if (options.multiple) {
				$(this).next(".list").slideToggle(300);
			} else {
				$(this).next(".list").slideToggle(300).siblings(".list").slideUp(300);
			}
		});
	}
})(jQuery);