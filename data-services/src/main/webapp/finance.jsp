<!DOCTYPE HTML>

<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%><html>
<head>


<meta http-equiv="content-type" content="text/html; charset=utf-8">
<title>HTML5 Equity Charting</title>


<link rel="stylesheet" type="text/css" href="styles/services.css" />
<link type="text/css" rel="stylesheet" href="styles/demos.css" />
<link type="text/css" rel="stylesheet" href="styles/applicationStyles.css" />


<link rel="stylesheet" type="text/css" href="scripts/yahoo/fonts-min.css" />
<script type="text/javascript" src="scripts/yahoo/yahoo-dom-event.js"></script>
<script type="text/javascript" src="scripts/yahoo/connection-min.js"></script>
<script type="text/javascript" src="scripts/yahoo/animation-min.js"></script>
<script type="text/javascript" src="scripts/yahoo/datasource-min.js"></script>
<script type="text/javascript" src="scripts/yahoo/autocomplete-min.js"></script>


<script type="text/javascript" src="scripts/jquery/jquery-1.4.2.js"></script>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7; IE=EmulateIE9"> 

<!--[if IE]>
      <script type="text/javascript" src="scripts/excanvas.js"></script>
      
    <![endif]-->

<script type="text/javascript"
  				src="scripts/dygraph-combined.js"></script>

</head>

<body>


<table width="100%"  height="100%" >
	<tr height="20">
		<td colspan=3>
			<div id="topBanner">
				<div id="logoContainer">
					<h1 align="center">HTML 5 Equity Chart</h1>
				</div>
			</div>
		</td>
	</tr>
	<tr height="20">
		<td colspan=3>
		</td>
	</tr>
	<tr>
		<td colspan=3 align="center"><h3></h3></td>
	</tr>
</table>
<table width="100%"  height="100%"  border="1" bgcolor="grey">
	<tr>
		<td align="right"  >Enter An Instrument:</td>
		<td width="120" align="left" >
			<div class="yui-skin-sam">
				<div id="myAutoComplete"><input id="zane" type="text">
					<div id="symbolContainer_zane"></div>
				</div>
			</div>
		</td>
		<td align="left">&nbsp;&nbsp;<a href="javascript:createImage()">Plot Data
		</a>&nbsp;&nbsp;&nbsp;<a href="javascript:clearCharts()">Reset</a> </td>
	</tr>
</table>
<table width="100%">
	<tr height="20">
		<td align="center">&nbsp;<div id="instDiv"></div>
		</td>
	</tr>
	<tr>
		<td align="center">
		<div id="graphdiv4"
  			style="width:900px; height:400px;"></div>
		</td>
	</tr>
</table>

 

<script type="text/javascript">
var ticker = "NYX,BAC,ED,ORCL,JPM";
<%
Calendar cal = Calendar.getInstance();

Date endDate = cal.getTime();
cal.add(Calendar.MONTH,-6);
Date startDate = cal.getTime(); 
SimpleDateFormat format = new SimpleDateFormat("MMddyyyy");
%>
function drawChart(){

  var securities = ticker.split(",");
  var tickerExp = "";
  for(var i=0;i<securities.length;i++){
	  var individualSec = jQuery.trim(securities[i]);
	if(individualSec != null && individualSec != ""){
		tickerExp += "&s"+(i+1)+"=SEC["+individualSec+"]:CLOSE&"
	}
  }
   
  g4 = new Dygraph(
    document.getElementById("graphdiv4"),
    "/ds/idal?ctr=yahoo&dataonly=true&sdate=<%=format.format(startDate)%>&edate=<%=format.format(endDate)%>"+tickerExp,
    
    { 
        labelsDiv: document.getElementById("instDiv")
    	
    }
  ); 
}
drawChart();
</script> 

<script type="text/javascript"> 
YAHOO.example.BasicRemote = function() {
    var url = "/ds/idal";    
    var oDS_zane = new YAHOO.util.XHRDataSource(url);
     
    oDS_zane.responseType = YAHOO.util.XHRDataSource.TYPE_JSON;
    oDS_zane.responseSchema = {   
             resultsList : "ResultSet.Result",    
             fields : ["ticker", "name"]   
    };   
 
     var oAC_zane = new YAHOO.widget.AutoComplete("zane", "symbolContainer_zane", oDS_zane);
     
    oAC_zane.queryDelay = .20;
    oAC_zane.delimChar = ",";
    oAC_zane.generateRequest = function(sQuery) {             
        return "?ctr=as&key=SYMBOL&v1="+sQuery+"&rssize=10&dataonly=true" ;
    };
     oAC_zane.formatResult = function(oResultData, sQuery, sResultMatch) {			 
        for(var i = 0;i< oResultData.length ;i++){
					var value = oResultData[i]; 
					var tempQuery = sQuery;					
					var start = value.toLowerCase().indexOf(tempQuery.toLowerCase());
					var temp = "";
					if(start > -1){
						while(start > -1){							
							temp = temp + value.substr(0,start)+"<b>"+value.substr(start,tempQuery.length)+"</b>";
							value = value.substr(start+sQuery.length,value.length);
							start = value.toLowerCase().indexOf(tempQuery.toLowerCase());							 							
						}
						temp += value;
					}else{
						temp = value;
					}								
					oResultData[i] = temp;					
 	 	 	  }
 	   	      
    	  var ticker = oResultData[0];   
 	   	  var name = oResultData[1];
    	  
    	  var aMarkup = ["<div >",
    	      "<div class=\"symbolResult\">",
    	      ticker,
    	      "</div>",
    	      "<div class=\"nameResult\">",
    	      name,    	      
    	      "</div>",
    	      "</div>"];
    	  return (aMarkup.join(""));
    	};
    return {
        oDS_zane: oDS_zane,
        oAC_zane: oAC_zane
    };    


}();


function createImage(){ 
  ticker = document.getElementById('zane').value;
  drawChart();
}

function clearCharts(){ 
	drawChart();
}
</script>
<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-12514618-1']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

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