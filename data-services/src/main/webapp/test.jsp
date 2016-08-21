<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "xhtml11.dtd">
<html debug="true">
<head>

<title>TimeSeries Chart</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.js"></script>

<script type="text/javascript">
//jQuery.noConflict();
</script>
<script type="text/javascript" src="http://www.highcharts.com/highslide/highslide-full.min.js"></script>
<script type="text/javascript" src="http://www.highcharts.com/highslide/highslide.config.js" charset="utf-8"></script>
<link rel="stylesheet" type="text/css" href="http://www.highcharts.com/highslide/highslide.css" />
<!--[if lt IE 7]>
<link rel="stylesheet" type="text/css" href="http://www.highcharts.com/highslide/highslide-ie6.css" />
<![endif]-->

<script type="text/javascript">
var example = 'compare',
	theme = 'default';
</script>

<script src="http://www.highcharts.com/demo/scripts.js"></script>
<link rel="stylesheet" href="http://www.highcharts.com/templates/yoo_symphony/css/template.css" type="text/css" />
<link rel="stylesheet" href="http://www.highcharts.com/templates/yoo_symphony/css/variations/brown.css" type="text/css" />

<script type="text/javascript">
 
<%String heightStr = request.getParameter("H") ;
heightStr = (heightStr == null) ? "300": heightStr;
String widthStr = request.getParameter("W") ;
widthStr = (widthStr == null) ? "300": widthStr;

String num = request.getParameter("n") ;
num = (num == null) ? "1": num;

%>

var namesStr = '<%=(request.getParameter("tickers") == null)?"GOOG,AAPL": request.getParameter("tickers")%>';
//var chart;
var chartH = <%=heightStr%>;
var chartW = <%=widthStr%>;
var chartN = <%=num%>;
function getData(chartIndex, tickers, names) {
	name = tickers;
	var seriesOptions = [],
		yAxisOptions = [],
		seriesCounter = 0,
		colors = Highcharts.getOptions().colors;
	if(names == null) {
		names = tickers;
	}
	$.each(tickers, function(i, ticker) {

		$.getJSON('/ds/idal?dataonly=true&s1=SEC['+ticker+']:CLOSE&sdate=09082012&edate=11082013&ctr=yahoo&output=json&usecache=false1&debug=true&jsonp1=aa'
				//'http://www.highcharts.com/samples/data/jsonp.php?filename='+ name.toLowerCase()
				//+'-c.json&callback=?'
						,	function(data) {

			seriesOptions[i] = {
				name: names[i],
				data: data
			};

			seriesCounter++;

			if (seriesCounter == names.length) {
				createChart(chartIndex, seriesOptions);
			}
		});
	});

	
}

function createChart(chartIndex, seriesOptions) {
	
	$('#container'+chartIndex).highcharts('StockChart', {
	    chart: {
	    	 height: chartH,
	    	 width: chartW
	    	 
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
	    	pointFormat: '<span style="color:{series.color}">{series.name}</span>: <b>{point.y}</b> ({point.change}%)<br/>',
	    	valueDecimals: 2
	    },
	    
	    series: seriesOptions
	});
	
}

function addTicker(newTicker){
	$('#chartDiv').append(
				"<div id=\"container"+(chartN)+"\" style=\"margin:10px; height: <%=heightStr%>px; width: <%=widthStr%>px; float:left;border:2px solid;border-radius:10px;\"></div>"
	);
	
	getData(chartN, newTicker.split(','),newTicker.split(','));
	$("#container"+chartN).show();
	
	
	chartN++;
}

function clearCharts(){ 
	$('#chartDiv').html('');

	}
</script>

 
<script src="http://code.highcharts.com/stock/highstock.js"></script>
<script src="http://code.highcharts.com/stock/modules/exporting.js"></script>

<div id="chartDiv" >
<%
for(int i =0;i< Integer.parseInt(num); i++){
%>
<div id="container<%=i%>" style="margin:10px; display: none; height: <%=heightStr%>px; width: <%=widthStr%>px; float:left;border:2px solid;border-radius:10px;"></div>
<%
}
%>
</div>

<script>
for(var i = 0; i<chartN;i++){
	getData(i,namesStr.split(','), namesStr.split(','));
	$("#container"+i).show();
	
}
</script>