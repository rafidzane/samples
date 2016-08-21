<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
 
 
    <m>AutoSuggest Chart Creation</title>
 
<style type="text/css">  
body {
	margin:0;
	padding:0;
}
</style>
 
<link rel="stylesheet" type="text/css" href="http://yui.yahooapis.com/2.8.0r4/build/fonts/fonts-min.css" />
<link rel="stylesheet" type="text/css" href="http://yui.yahooapis.com/2.8.0r4/build/autocomplete/assets/skins/sam/autocomplete.css" />
<script type="text/javascript" src="http://yui.yahooapis.com/2.8.0r4/build/yahoo-dom-event/yahoo-dom-event.js"></script>
<script type="text/javascript" src="http://yui.yahooapis.com/2.8.0r4/build/connection/connection-min.js"></script>
<script type="text/javascript" src="http://yui.yahooapis.com/2.8.0r4/build/animation/animation-min.js"></script>
<script type="text/javascript" src="http://yui.yahooapis.com/2.8.0r4/build/datasource/datasource-min.js"></script>
<script type="text/javascript" src="http://yui.yahooapis.com/2.8.0r4/build/autocomplete/autocomplete-min.js"></script>
 
 <style type="text/css"> 
label {
    color:#E76300;
    font-weight:bold;
}
#myAutoComplete {
    width:40em; 
    padding-bottom:2em;
}
</style>
 
  
</head>
 
<body class="yui-skin-sam">
 
 
<h1>Customizing Remote Requests</h1>
 
<div class="exampleIntro">
	<p>This AutoComplete implementation points to the Yahoo! Search webservice using an XHRDataSource. Since the third-party API requires certain application-specific paramaters to be passed in, the generateRequest() method has been redefined to append these special values. The <code>queryDelay</code> paramater has been increased to account for the large data payload returned by the Yahoo! Search webservice, so as to reduce throttle client-side processing.</p>
			
</div>
 
<!--BEGIN SOURCE CODE FOR EXAMPLE =============================== -->
 
<label for="myInput">Yahoo! Search:</label>
<div id="myAutoComplete">
	<input id="myInput" type="text">
	<div id="myContainer"></div>
</div>
 
<script type="text/javascript"> 
YAHOO.example.RemoteCustomRequest = function() {
    // Use an XHRDataSource
    var oDS = new YAHOO.util.XHRDataSource("http://www.rafidwahab.com:7001/ds/idal");
    // Set the responseType
    oDS.responseType = YAHOO.util.XHRDataSource.TYPE_JSON;
    // Define the schema of the JSON results
    oDS.responseSchema = {
    		  resultsList : "ResultSet.Result",    
              fields : ["ticker", "name"]   
    };
   
    // Instantiate the AutoComplete
    var oAC = new YAHOO.widget.AutoComplete("myInput", "myContainer", oDS);
    // Throttle requests sent
    oAC.queryDelay = .5;
    // The webservice needs additional parameters
    oAC.generateRequest = function(sQuery) {
        return "?rh=as&key=SYMBOL&v1=IBM&rssize=10&dataonly=true" ;
    };

    oAC.formatResult = function(oResultData, sQuery, sResultMatch) {   
        alert(oResultData);
        return oResultData;
    };   
    	    
    
    return {
        oDS: oDS,
        oAC: oAC
    };
}();
</script>
 
<!--END SOURCE CODE FOR EXAMPLE =============================== -->
 
 
<!--MyBlogLog instrumentation-->
<script type="text/javascript" src="http://track2.mybloglog.com/js/jsserv.php?mblID=2007020704011645"></script>
 
</body>
</html>
<!-- p2.ydn.re1.yahoo.com compressed/chunked Fri Mar  5 19:57:00 PST 2010 -->

