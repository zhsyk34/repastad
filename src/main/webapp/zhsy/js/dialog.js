
var dialog = {init:function () {
	$(".dialog").after("<div class='cover'></div>");
	$(".dialog").append("<div class='close'></div>");
	$(".dialog").css({"margin-top":-$(".dialog").height(), "margin-left":-$(".dialog").width() / 2});
}, open:function () {
	$(".dialog").fadeIn(200);
	$(".cover").fadeIn(200);
}, close:function () {
	$(".dialog").fadeOut(200);
	$(".cover").fadeOut(200);
}};

