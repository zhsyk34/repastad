(function($) {
	$.fn.dialog = function(options, param) {
		if (typeof options == "string") {
			return $.fn.dialog.methods[options](this, param);
		}

		options = options || {};
		return this.each(function() {
			var state = $.data(this, "dialog");
			if (state) {
				$.extend(state.options, options);
			} else {
				$.data(this, "dialog", {
					options : $.extend({}, $.fn.dialog.defaults, options)
				});
			}
			init(this);
		});
	};

	$.fn.dialog.methods = {
		options : function(target) {
			return $.data(target[0], "dialog").options;
		},
		open : function(target) {
			this.options(target).mark.show();
			$(target).parent().show();
		},
		close : function(target) {
			this.options(target).mark.hide();
			$(target).parent().hide();
		}
	};

	$.fn.dialog.defaults = {
		title : "",
		closed : true,
		closable : false,
		width : "auto",
		height : "auto",
		left : null,
		top : null
	};

	function init(target) {
		var options = $.data(target, "dialog").options;
		// mark
		options.mark = $("<div class='mark'></div>").hide();
		$("body").append(options.mark);
		// wrap
		$(target).addClass("dialog").wrap("<div class='dialog-box'></div>");
		if (options.title || options.closable) {
			var header = $("<div class='dialog-header'></div>").insertBefore($(target));
			if (options.title) {
				header.append("<span class='dialog-title'>" + options.title + "</span>");
			}
			if (options.closable) {
				var button = $("<a class='dialog-close'>x</a>").appendTo(header);
				button.on("click.dialog", function() {
					$(target).dialog("close");
				});
			}
		}

		if (options.closed) {
			$(target).parent().hide();
		}

		$(target).css({
			width : options.width,
			height : options.height
		});
		
		var left = parseInt(($(window).width()-$(target).parent().width())/2);
		var top = parseInt(($(window).height()-$(target).parent().height())/2);
		$(target).parent().offset({
			left:options.left||left,
			top:options.top||top
		})
	}
})(jQuery);
