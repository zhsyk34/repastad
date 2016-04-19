$(function() {
	//
	backFunction = table.find;
	page.init();
	//
	commons.discolor();
	commons.check();
	//
	table.init();
});
var table = {
	page : function() {
		$("input[name='pageNo']").val(pageNo);
		$("input[name='pageSize']").val(pageSize);
	},
	init : function() {
		if ("delsuccess" === message) {
			alert(result.del + result.success);
		}
		this.del();
		this.edit();
		this.search();
	},
	find : function() {
		table.page();
		$("form").attr("action", "tableFind.do").submit();
	},
	del : function() {
		$(".del").click(function() {
			$("form").find(":checkbox").prop("checked", false);
			$(this).parents("tr").find(":checkbox[name='ids']").prop("checked", true);
			pageNo = 1;
			table.page();
			if(!confirm(delsure)){
				return;
			}
			$("form").attr("action", "tableDelete.do").submit();
		});
		$("#delCheck").click(function() {
			pageNo = 1;
			table.page();
			if( $("form :checkbox:checked").length ==0){
				alert(delcheck);
				return;
			}
			if(!confirm(delsure)){
				return;
			}
			$("form").attr("action", "tableDelete.do").submit();
		});
	},
	edit : function() {
		$(".mod").click(function() {
			var id = $(this).parents("tr").find(":checkbox[name='ids']").val();
			$("input[name='id']").val(id);
			$("form").attr("action", "tablePre.do").submit();
		});
		$("#add").click(function() {
			$("input[name='id']").val("");
			$("form").attr("action", "tablePre.do").submit();
		});
	},
	search : function() {
		$("#search").click(function() {
			pageNo = 1;
			table.page();
			$("form").attr("action", "tableFind.do").submit();
		});
	}
};






