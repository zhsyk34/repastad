(function($) {
	$.fn.drag = function(options, param) {
		if (typeof options == "string") {
			return $.fn.drag.methods[options](this, param);
		}

		options = options || {};
		return this.each(function() {
			var state = $.data(this, "drag");
			if (state) {
				$.extend(state.options, options);
			} else {
				$.data(this, "drag", {
					options : $.extend({}, $.fn.drag.defaults, options)
				});
			}
			event(this);
		});
	};
	
	$.fn.drag.defaults = {
		cursor : "default"
	};
	$.fn.drag.methods = {};

	function event(target) {
		var options = $.data(target, "drag").options;
		$(target).css({
			cursor : options.cursor
		});
		$(target).off(".drag").on("mousedown.drag", function(original) {
			original.offset = $(this).offset();
			$(document).on("mousemove.drag", function(current) {
				$(target).offset({

					left : current.clientX - original.clientX + original.offset.left,
					top : current.clientY - original.clientY + original.offset.top
				});
			}).on("mouseup.drag", function() {
				$(document).off("mousemove.drag");
				$(this).off("mouseup.drag");
			});
		});
	}
})(jQuery);