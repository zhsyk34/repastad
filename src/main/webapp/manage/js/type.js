$(function() {
	//
	backFunction = type.find;
	page.init();
	//
	dialog.init();
	//
	commons.discolor();
	commons.check();
	//
	type.init();
});
var type = {
	page : function() {
		$("input[name='pageNo']").val(pageNo);
		$("input[name='pageSize']").val(pageSize);
	},
	init : function() {
		if ("delsuccess" === message) {
			alert(result.del + result.success);
		}
		if ("add" === message) {
			alert(result.add + result.success);
		}
		if ("mod" === message) {
			alert(result.mod + result.success);
		}
		if ("used" === message) {
			alert(lan.typeUsed);
		}
		
		this.save();
		this.del();
		this.edit();
		this.search();
	},
	find : function() {
		type.page();
		$("form").attr("action", "typeFind.do").submit();
	},
	save : function() {
		$("#save").click(function() {
			if($.trim($("input[name='name']").val()).length === 0){
				alert(typeName + result.cantNull);
				return;
			}
			type.page();
			$("form").attr("action", "typeMerge.do").submit();
		});
	},
	del : function() {
		$(".del").click(function() {
			$("form").find(":checkbox").prop("checked", false);
			$(this).parents("tr").find(":checkbox[name='ids']").prop("checked", true);
			pageNo = 1;
			type.page();
			if(!confirm(delsure)){
				return;
			}
			$("form").attr("action", "typeDelete.do").submit();
		});
		$("#delCheck").click(function() {
			pageNo = 1;
			type.page();
			if( $("form :checkbox:checked").length <=0){
				alert(delcheck);
				return;
			}
			if(!confirm(delsure)){
				return;
			}
			$("form").attr("action", "typeDelete.do").submit();
		});
	},
	edit : function() {
		$("#add").click(function() {
			$("input[name='id']").val("");
			$("input[name='name']").val("");
			$("#editor").text(typeAdd);
			dialog.open();
		});
		$(".mod").click(function() {
			var id = $(this).parents("tr").find("input[name='ids']").val();
			var name = $(this).parents("tr").find("td:eq(2)").html();
			$("input[name='id']").val(id);
			$("input[name='name']").val(name);
			$("#editor").text(typeMod);
			dialog.open();
		});
		$("#back").click(function() {
			dialog.close();
		});
		$("#reset").click(function() {
			$("input[name='name']").val("");
		});
	},
	search : function() {
		$("#search").click(function() {
			pageNo = 1;
			type.page();
			$("form").attr("action", "typeFind.do").submit();
		});
	}
};
