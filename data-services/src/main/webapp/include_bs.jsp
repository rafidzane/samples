<link rel="stylesheet" type="text/css" href="styles/services.css" />
<link type="text/css" rel="stylesheet" href="styles/applicationStyles.css" />

<link rel="stylesheet" type="text/css" href="scripts/yahoo/fonts-min.css" />
<script type="text/javascript" src="scripts/yahoo/yahoo-dom-event.js"></script>
<script type="text/javascript" src="scripts/yahoo/connection-min.js"></script>
<script type="text/javascript" src="scripts/yahoo/animation-min.js"></script>
<script type="text/javascript" src="scripts/yahoo/datasource-min.js"></script>
<script type="text/javascript" src="scripts/yahoo/autocomplete-min.js"></script>

<!--<script type="text/javascript" src="scripts/jquery/jquery-1.4.2.js"></script> -->
<script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>

<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.js"></script>
<script type="text/javascript" src="http://www.highcharts.com/highslide/highslide-full.min.js"></script>
<script type="text/javascript" src="http://www.highcharts.com/highslide/highslide.config.js" charset="utf-8"></script>
<script src="http://www.highcharts.com/demo/scripts.js"></script>
<script src="http://code.highcharts.com/stock/highstock.js"></script>
<script src="http://code.highcharts.com/stock/modules/exporting.js"></script>




<style type="text/css"> 
		
		#chartDiv {text-align: center; }  
		 
</style>
 
<script type="text/javascript">
 
<%String heightStr = request.getParameter("H") ;
heightStr = (heightStr == null) ? "300": heightStr;
String widthStr = request.getParameter("W") ;
widthStr = (widthStr == null) ? "500": widthStr;

String num = request.getParameter("n") ;
num = (num == null) ? "1": num;

%>

var namesStr = '<%=(request.getParameter("tickers") == null)?"GOOG,AAPL": request.getParameter("tickers")%>';
//var chart;
var chartH = <%=heightStr%>;
var chartW = <%=widthStr%>;
var chartN = <%=num%>;
function setUpChartTemplate(chartIndex, tickers, names, width, height) {
	name = tickers;
	var seriesOptions = [],
		yAxisOptions = [],
		seriesCounter = 0,
		colors = Highcharts.getOptions().colors;
	if(names == null) {
		names = tickers;
	}
	$.each(tickers, function(i, ticker) {

		$.getJSON('/ds/idal?dataonly=true&s1=SEC['+ticker.trim()+']:CLOSE&sdate=09082012&edate=12312016&ctr=yahoo&output=json&debug=true&jsonp1=aa&t='+(new Date().getTime())
				//'http://www.highcharts.com/samples/data/jsonp.php?filename='+ name.toLowerCase()
				//+'-c.json&callback=?'
						,	function(data) {

			seriesOptions[i] = {
				name: names[i],
				data: data
			};

			seriesCounter++;

			if (seriesCounter == names.length) {
				createChart(chartIndex, seriesOptions, width, height);
			}
		});
	});

	
}

function createChart(chartIndex, seriesOptions, chartTitle, chartW, chartH) {
	var title = seriesOptions[0].name;
	for (var i = 1; i < seriesOptions.length; i++){
		title += ','+seriesOptions[i].name;	
	}
    
	$('#container'+chartIndex).highcharts('StockChart', {
	    chart: {
	    	 height: chartH,
	    	 width: chartW
	    	 
	    },
	    title: {
	    	text: chartTitle,
    	    floating: false,
        	align: 'center',
        	x: 0,
        	y: 10
    	},
	    rangeSelector: {
	        selected: 5,
	        enabled: false
	        
	    },
	    
	    navigator : {
			enabled : false
		},
		scrollbar: {
	    	enabled: false
	    },
	    exporting: {
	        enabled: false
	    }, 
	    credits: {
	        enabled: false
	    },
	    

	    yAxis: {
	    	labels: {
	    		formatter: function() {
	    			return (this.value > 0 ? '' : '') + this.value + '';
	    		}
	    	},
	    	plotLines: [{
	    		value: 0,
	    		width: 2,
	    		color: 'silver'
	    	}]
	    },
	    
	    plotOptions: {
	    	series: {
	    		//compare: 'percent'
	    		animation: {
    				duration: 100
    			}
	    	}
	    },
	    
	    tooltip: {
	    	pointFormat: '<span style="color:{series.color}">{series.name}</span>: <b>{point.y}</b><br/>',
	    	valueDecimals: 2
	    },
	    
	    series: seriesOptions
	});  
	
}

function addTicker(newTicker, divName, title, width, height){
	//console.log('\''+divName+'\'');
	//console.log('done');
	$('#'+divName).append(
				"<div align=\"center\" id=\"container"+(chartN)+"\" style=\"margin:10px; height: "+height+"px; width: "+width+"px; float:left;border:2px solid;border-radius:10px;\"></div>"
	);
	
	setUpChartTemplate(chartN, newTicker.split(','),newTicker.split(','), title, width, height);
	$("#container"+chartN).show();
	
	
	chartN++;
}

function clearCharts(){ 
	$('#chartDiv').html('');

	}
</script>
   
<script type="text/javascript">
var sc_project=7271626; 
var sc_invisible=1; 
var sc_security="9f44141a"; 
</script>
<script type="text/javascript"
src="http://www.statcounter.com/counter/counter.js"></script>
<noscript><div class="statcounter"><a title="drupal stats"
href="http://statcounter.com/drupal/" target="_blank"><img
class="statcounter"
src="http://c.statcounter.com/7271626/0/9f44141a/1/"
alt="drupal stats"></a></div></noscript> 