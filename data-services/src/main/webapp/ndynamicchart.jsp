<!DOCTYPE HTML>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%><html>
<head>

<meta http-equiv="content-type" content="text/html; charset=utf-8">
<title>Rafid Wahab - Prototype Sandbox</title>

<jsp:include page="include.jsp" />

<% 
SimpleDateFormat format = new SimpleDateFormat("MMddyyyy");
Calendar start = Calendar.getInstance();
start.add(Calendar.MONTH, -12);
%>


 

</head>

<body> 
<div id="topBanner">  
		<div style="height:35px">&nbsp;</div>
		<div class="header" style="text-align: center;vertical-align: middle;">
		 Dynamic Chart Generator
		</div> 
</div> 
<div align="center"  style="margin: 20pt;font-size: 12pt;color: white">To generate your own chart, enter one or more instruments using the autocomplete below.<br>
You can also type them in manually as long as they are comma delimited (i.e NYX,BCS,ED,JPM,GS).
</div> 
<table height="60%" align="center"  >
	<tr>
		<td align="right" style="background-color: inherit;color: white">Please Enter An Instrument:</td>
		<td align="left" style="background-color: inherit;">
		<div class="yui-skin-sam" style="">
			<div id="myAutoComplete"><input id="zane" type="text">
			<div id="symbolContainer_zane"></div>
			</div>
		</div>
		</td>
		<td align="left" style="background-color: inherit;">&nbsp;&nbsp;<a href="javascript:generateChart()">Generate
		Historical Chart</a>&nbsp;&nbsp;&nbsp;<a href="javascript:clearCharts()">Clear
		Charts</a></td>
	</tr>
</table>
<div id="wrapper" style="text-align: center"> 
	<div id="chartDiv" style="display: inline-block;"></div>
</div>

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
     
    oAC_zane.queryDelay = .30;
    oAC_zane.delimChar = ",";
    oAC_zane.generateRequest = function(sQuery) {             
        return "?ctr=as&key=SYMBOL&v1="+sQuery+"&rssize=10&dataonly=true&t="+(new Date().getTime()) ;
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

function generateChart(){ 
	addTicker($('#zane').val());
} 
function clearCharts(){ 
	  document.getElementById('chartDiv').innerHTML = "";

	}
</script>