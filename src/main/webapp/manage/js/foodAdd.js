
$(function () {
	init();
	button();
});
function init() {
	//layout	
	$("div.list").width($("div.content").width() - $("div.show").width());
	/*----linsten the value------*/
	//set title by id
	var id = $("input[name='id']").val();
	if (id) {
		$(".title h3 div").text(lan.mod + lan.food);
	} else {
		$(".title h3 div").text(lan.add + lan.food);
	}
	//name	
	$("input[name='name']").on("change", function () {
		$(".name").text($.trim($(this).val()));
	});
	//price
	$("input[name='price']").on("change", function () {
		$(".price").text($.trim($(this).val()));
	});	
	//material
	$("#selectMaterial").on("click", function () {
		var dg = new J.dialog({id:"materialId", title:choosePicture, iconTitle:false, width:800, height:550, page:"searchMaterial.do?select=true&searchType=1", cover:true});
		dg.ShowDialog();
	});	
	//pircture
	var url = $(".picture img").attr("src");
	if (!url) {
		$(".picture img").hide();
	}
	//type
	if (type > 0) {
		$("select[name='type']").val(type);
	}
	// taste
	var tasteArr = taste.replace(/\s*/g, "").split(",");
	$(".taste :checkbox").each(function () {
		if ($.inArray($(this).val(), tasteArr) >= 0) {
			$(this).prop("checked", true);
		}
	});
	//taste type
	var necessary = $("#necessary").val();
	$(".necessary :checkbox").each(function () {
		var needs = necessary.replace(/,\s*$/g, "").replace(/\s*,\s*/g, ",").split(",");
		$(this).prop("checked", $.inArray($(this).val(), needs) >= 0);
	});
	$(".necessary").on("change", ":checkbox", function () {
		var arr = [];
		$(".necessary :checkbox:checked").each(function () {
			arr.push($(this).val());
		});
		$("#necessary").val(arr.join());
	});
}
function button() {
	$("#save").on("click", function () {
		//name
		var name = $.trim($("input[name='name']").val());
		if (name.length <= 0) {
			alert(lan.food + lan.name + lan.notNull);
			return false;
		}
		//price
		var lang, reg;
		if ("zh-tw" == lang) {
			reg = /^[1-9]\d*$/;
		} else {
			reg = /^[0-9]+(.[0-9]{1,2})?$/;
		}
		var price = $.trim($("input[name='price']").val());
		if (!reg.test(price)) {
			alert(lan.foodPrice);
			$("input[name='price']").focus();
			return false;
		}
	//material
		var material = parseInt($("#materialId").val());
		if (!material || material <= 0) {
			alert(lan.foodMaterial);
			return false;
		}
		$("#saveFood").submit();
	});
	$("#reset").on("click", function () {
		location = location;
	});
	$("#back").on("click", function () {
		window.location = "searchFood.do";
	});
}



//dialog插件--materialSelect.jsp中回调
function setImage(id, value, name, url) {
	$("#materialId").val(value);//id=#materialId
	$(".picture img").attr("src", url).show();
}

