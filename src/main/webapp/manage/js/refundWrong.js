$(function() {
	// 加載頁面控件
	backFunction = refundList;
	page.init();
	initSelect();
	resetSelect();
	refundMod();
	refundSearch();
});
function refundList() {
	$("input[name='pageNo']").val(pageNo);
	$("input[name='pageSize']").val(pageSize);
	$("form").submit();
}

function refundSearch(){
	$("img.search").click(function(){
		pageNo = 1;
		refundList();
	});
}

function initSelect() {
	$("select[name='isGet']").val(isGet);
	$("select[name='adminId']").val(adminId);
}

function resetSelect(){
	$("#reset").click(function(){
		$(".keyword").val("");
		$("select[name='isGet']").val(0);
	$("select[name='adminId']").val(0);
		
	});
}
function refundMod() {
	$(".dealStatus").on("click",".nowDeal",function(){
		var div = $(this).parents("td.dealStatus").find("div");
		var userTd = $(this).parents("td").next("td");
		var id = $(this).parents("tr").find(".id").val();
		var status = 2;
		mod(id,status,div,userTd);
	
	});
	$(".dealStatus").on("click",".noneDeal",function(){
		var userTd = $(this).parents("td").next("td");
		var div = $(this).parents("td.dealStatus").find("div");
		var id = $(this).parents("tr").find(".id").val();
		var status = 1;
		mod(id,status,div,userTd);
	
	});
	$(".dealStatus").on("click",".reDeal",function(){
		var div = $(this).parents("td.dealStatus").find("div");
		var userTd = $(this).parents("td").next("td");
		var id = $(this).parents("tr").find(".id").val();
		var status = 2;
		mod(id,status,div,userTd);
	});
	
	
	function mod(id,status,div,userTd){
		var flag = confirm(modSure);
		if(!flag){return;}
		$.ajax({
			url : "json/RefundWrong_mod.do",
			data : {
				id : id,
				dealStatus : status
			},
			dataType : "json",
			method : "post",
			success : function(data) {
				var str = "";
				if (data.result) {
					if (status == 1) {
						str = "<span class='noDeal'>" + notDeal + "<label class='nowDeal'>"+nowDeal+"</label></span>";
					} else {
						str = "<span class='haveDeal'>" + haveDeal + "<span class='dealTime'>" + data.dateStr + "<br/><label class='noneDeal'>"+noneDeal+"</label><label class='reDeal'>"+reDeal+"</label></span>";
					}
					div.html(str);
					userTd.html(data.username);
					alert(modSuccess);
				}
			}
		});
	}
}
