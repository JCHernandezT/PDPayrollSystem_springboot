$(document).ready(function() {

	$(".delete-ajax").click(function() {
		var id = $(this).find('input.elm2-id').val();
		var y = formType;
		switch (formType) {
			case "districtForm": $("#myDelModal-content").load("/district/delete/" + id); break;
			case "operativeForm": $("#myDelModal-content").load("/operative/delete/" + id); break;
			case "administrativeForm": $("#myDelModal-content").load("/administrative/delete/" + id); break;
			case "positionForm": $("#myDelModal-content").load("/position/delete/" + id); break;
		}
	});

});
