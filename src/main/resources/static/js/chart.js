	// Load the Visualization API and the corechart package.
      google.charts.load('current', {'packages':['corechart']});

      // Set a callback to run when the Google Visualization API is loaded.
      google.charts.setOnLoadCallback(drawComboChart);
      google.charts.setOnLoadCallback(drawPieChart);
      
      // Callback that creates and populates a data table,
      // instantiates the pie chart, passes in the data and
      // draws it.
      function drawComboChart() {
    		      var data = google.visualization.arrayToDataTable([
    		        [ChArea, ChAdmin, ChOps, ChTotal],
    		        [ChPayroll, admPayroll, optPayroll, totPayroll]
      ]);

      // Set chart options        
      var options = {
        	      title : ChComboChartTitle,
        	      vAxis: {title: ChPayment, format: 'currency'},
        	      hAxis: {title: ChCurMonth},
        	      seriesType: 'bars',
        	      series: {3: {type: 'line'}},
        	      'width':400,
                  'height':300,
                  colors: ['#5C6BC0', '#26A69A', '#FF9800']
       };
      
      var formatter = new google.visualization.NumberFormat({decimalSymbol: ',',groupingSymbol: '.', negativeColor: 'red', negativeParens: true, prefix: '$ '});
      formatter.format(data, 1);
      formatter.format(data, 2);
      formatter.format(data, 3);
      
        // Instantiate and draw our chart, passing in some options.
        var chart = new google.visualization.ComboChart(document.getElementById('chart_div'));
        chart.draw(data, options);
      }
      
      function drawPieChart() {
	      var data = google.visualization.arrayToDataTable([
	        [ChArea, ChPayroll],
	        [ChAdmin, admPayroll],
	        [ChOps, optPayroll ],
		]);
		
		// Set chart options        
	        var options = {title: ChPieChartTitle,
                    width:400,
                    height:300,
                    colors: ['#5C6BC0', '#26A69A']
            };
	        
	     var formatter = new google.visualization.NumberFormat({decimalSymbol: ',',groupingSymbol: '.', negativeColor: 'red', negativeParens: true, prefix: '$ '});
	     formatter.format(data, 1);
		
		// Instantiate and draw our chart, passing in some options.
		var chart = new google.visualization.PieChart(document.getElementById('chart_div2'));
		chart.draw(data, options);
		}