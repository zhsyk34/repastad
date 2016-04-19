$(function() {
	init();
	link();
});

function init() {
	$("#accordion").accordion();
	// show time	
	function setDate(){
		var date = new Date();
		var datestr = date.toLocaleDateString() + " \u661f\u671f" + "\u65e5\u4e00\u4e8c\u4e09\u56db\u4e94\u516d".charAt(date.getDay()) + " " + date.toTimeString().slice(0, 8);
		$("#date").html(datestr);
	}
	setDate();
	setInterval(setDate, 1000);
	// can't select
	$(document).on("selectstart", function() {
		return false;
	});
}

function link() {
	// left link
	$("#accordion").on("click", ".list li:not(:last)", function() {
		$("#accordion li").removeClass("active");
		$(this).addClass("active");
		var link = $(this).attr("link");
		$("#rightCon iframe").attr("src", link);
	});
	$("#accordion .list li:last").on("click", function() {
		if (confirm(exitSure)) {
			window.location = "logout.do";
		}
	});
	// right-top link
	$("#rightHead li:not(:last)").click(function() {
		var link = $(this).attr("link");
		$("#rightCon iframe").attr("src", link);
	});
	$("#rightHead li:last").click(function() {
		$("#accordion .list li:last").trigger("click");
	});
}
