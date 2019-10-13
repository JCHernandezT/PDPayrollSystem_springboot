$(".to-delete").click(function(e) {
	
	var itemName = $(this).children(0).val();
	
	if (!confirm(DelMessage + " " + "\"" + itemName + "\"")) {
		e.preventDefault();
	}
});