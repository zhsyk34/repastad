(function() {
	/*
	 * bar:控件,slider:拖動條,completed:進度條;
	 */
	var Slider = function(element, option) {
		this.element = element;
		this.defaults = {
			enable : true,
			percent : 50,// 百分比
			size : {
				barWidth : 400,
				sliderWidth : 10
			},
			barClass : "defaultBar",
			completedClass : "defaultCompleted",
			sliderClass : "defaultSlider",
			sliderHover : "defaultHover",
			onStop : function() {
				/* 怎麽取得外層對象？ */
			}
		};
		this.option = $.extend({}, this.defaults, option);
	};

	Slider.prototype = {
		init : function() {
			this.html();
			if (this.option.enable) {
				this.drag();
			}
			return this;
		},
		html : function() {
			var bar = $("<div><div></div><div></div></div>").attr("class", this.option.barClass).css("width", this.option.size.barWidth).appendTo(this.element);
			var completed = bar.find("div:first").attr("class", this.option.completedClass);
			var slider = bar.find("div:last").attr("class", this.option.sliderClass).css("width", this.option.size.sliderWidth);
			// 邊界
			var limit = {
				min : 0,
				max : bar.width() - slider.outerWidth()
			};
			$.extend(this.option, {
				bar : bar,
				completed : completed,
				slider : slider,
				limit : limit
			});
			// 定位
			this.locate(limit.max * this.option.percent / 100);
		},
		drag : function() {
			var start;// 原來位置
			var current;// 當前位置
			var offset;// 偏移值

			/* 傳遞對象 */
			var $this = this;
			var $bar = $this.option.bar;
			var $slider = $this.option.slider;
			var $completed = $this.option.completed;

			$slider.on("mousedown", function(e) {
				e = e || window.event;
				start = e.clientX;
				offset = $slider.position().left;
				$(document).on("mouseup", function() {
					$(this).off("mousemove");
					$this.option.onStop();
				});
				$(document).on("mousemove", function(e) {
					e = e || window.event;
					current = e.clientX - start + offset;
					current = current < 0 ? 0 : (current > $this.option.limit.max ? $this.option.limit.max : current);

					$this.option.percent = parseInt(current * 100 / $this.option.limit.max);
					$this.locate(current);
				});
			});
		},
		locate : function(current) {
			this.option.slider.css("left", current);
			this.option.completed.css("width", current);
		}
	};

	$.fn.slider = function(option) {
		return new Slider(this, option).init();
	};

})();