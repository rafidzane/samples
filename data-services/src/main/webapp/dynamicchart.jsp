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


<table width="100%" height="100%">
	<tr height="20">
		<td colspan=3>
		<div id="topBanner">
		<div id="logoContainer">
		<h1 align="center">Dynamic Chart Generator</h1>
		</div>
		</div>
		</td>
	</tr>
	<tr height="20">
		<td colspan=3></td>
	</tr>
	<tr>
		<td colspan=3 align="center">
		<h3>To generate your own chart, enter one or more instruments
		using the autocomplete below.<br>
		You can also type them in manually as long as they are comma delimited
		(i.e NYX,BCS,ED,JPM,GS).</h3>
		</td>
	</tr>
</table>
<table height="60%" align="center" border="1" bgcolor="grey">
	<tr>
		<td align="right">Please Enter An Instrument:</td>
		<td align="left">
		<div class="yui-skin-sam">
		<div id="myAutoComplete"><input id="zane" type="text">
		<div id="symbolContainer_zane"></div>
		</div>
		</div>
		</td>
		<td align="left">&nbsp;&nbsp;<a href="javascript:createImage()">Generate
		Historical Chart</a>&nbsp;&nbsp;&nbsp;<a href="javascript:clearCharts()">Clear
		Charts</a></td>
	</tr>
</table>
<table width="100%" align="center">
	<tr valign="middle">
		<td >
		  <div id="outer" > 
		  	<div id="middle" > 
		  		<div id="inner"> 
		  			<div id="instDiv" class="imageDiv"></div>
		  		</div>
		  	</div>
		 </div>
		</td>
	</tr>
</table>



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


//$('#instDiv').ajaxStart(function() {
//	 $("#instDiv").html('<img src="images/loader.gif" alt="Loading" />');  
	 //style="text-align:center;vertical-alignment:center;" 
//});
//$('#instDiv').ajaxStop(function() {
	 //$('#instDiv').html("");
//});





function createImage(){  
	
	 var imgUrl =  '/gc/chart?ctr=yahoo&dataonly=true&sdate=<%=format.format(start.getTime())%>&edate=<%=format.format(new Date())%>&tickers='+
              document.getElementById('zane').value+'&series=CLOSE&w=500&h=200';
	 document.getElementById('instDiv').innerHTML += "<img src='"+imgUrl+"' width='500' height='250' />";
	 //$("#instDiv").html('<img src=""'+imgUrl+'" />');  
 	//alert(imgUrl);
	/*
	$.get({
  		url: imgUrl,
  		success: function(data) {  
  			alert("success");			
		 $("#instDiv").html('<img src=""'+imgUrl+'" />');  
  		}
		});
  alert(2);
	  */
}   

function clearCharts(){ 
	  document.getElementById('instDiv').innerHTML = "";

	}
</script>