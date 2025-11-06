$(document).ready(
		
		// variables needed are defined at view-main fragment
		
		function() {
			// datatables configuration for Features List
			$('#myFeatureTable').dataTable(
					{
						// general labels
						"paging" : true,
						"ordering" : true,
						"info" : true,
						"language" : {
							"paginate" : {
								"previous" : previousLabel,
								"next" : nextLabel
							},
							"lengthMenu" : showLabel + " _MENU_ "
									+ entriesLabel,
							"search" : searchLabel,
							"info" : showingPageLabel + " _PAGE_ " + ofLabel
									+ " _PAGES_ ",
							"infoEmpty" : noRecordsLabel,
							"zeroRecords" : zeroRecordsLabel,
							"infoFiltered" : "(" + filteredFromLabel
									+ " _MAX_ " + totalEntriesLabel + ")"
						},
						// disabling ordering to edit/delete columns
						"columnDefs" : [ {
							"orderable" : false,
							"targets" : 3
						}, {
							"orderable" : false,
							"targets" : 2
						} ],
						// customising pagination groups
						"lengthMenu" : [ 10, 25, 50, 80, 100 ]
					});
			
			// datatables configuration for Officers List
			$('#myOperativesTable').dataTable(
					{
						// general labels
						"paging" : true,
						"ordering" : true,
						"info" : true,
						"language" : {
							"paginate" : {
								"previous" : previousLabel,
								"next" : nextLabel
							},
							"lengthMenu" : showLabel + " _MENU_ "
									+ entriesLabel,
							"search" : searchLabel,
							"info" : showingPageLabel + " _PAGE_ " + ofLabel
									+ " _PAGES_ ",
							"infoEmpty" : noRecordsLabel,
							"zeroRecords" : zeroRecordsLabel,
							"infoFiltered" : "(" + filteredFromLabel
									+ " _MAX_ " + totalEntriesLabel + ")"
						},
						// disabling ordering to edit/delete columns
						"columnDefs" : [ {
							"orderable" : false,
							"targets" : 7
						}, {
							"orderable" : false,
							"targets" : 6
						} ],
						// customising pagination groups
						"lengthMenu" : [ 10, 25, 50, 80, 100 ]
					});
			
			

		});