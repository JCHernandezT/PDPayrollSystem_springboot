$(document).ready(function() {

	// variables needed are defined at view-main fragment

	if (validationFailed === true) {
		$('#myModal').modal({backdrop: 'static', keyboard: false});  
		$("#myModal").modal();
	}

	$(".edit-ajax").click(function() {
		var id = $(this).find('input.elm-id').val();
		var x = formType;
		switch (formType) {
			case "districtForm": $("#myModal-content").load("/district/edit/" + id); break;
			case "operativeForm": $("#myModal-content").load("/operative/edit/" + id); break;
			case "administrativeForm": $("#myModal-content").load("/administrative/edit/" + id); break;
			case "positionForm": $("#myModal-content").load("/position/edit/" + id); break;
		}
	});
	
});