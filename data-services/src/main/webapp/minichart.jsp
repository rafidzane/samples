<!DOCTYPE HTML>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%><html>
<head>

<meta http-equiv="content-type" content="text/html; charset=utf-8">
<style type="text/css" media="only screen and (max-device-width: 480px)">
.portrait {
	width: 300px;
	padding: 0 15px 0 5px;
}

.landscape {
	width: 460px;
	padding: 0 15px 0 5px;
}
</style>
<title>Rafid Wahab - Mini Chart</title>

<script type="text/javascript" src="scripts/jquery/jquery-1.4.2.js"></script>

<%
	SimpleDateFormat format = new SimpleDateFormat("MMddyyyy");
	Calendar start = Calendar.getInstance();
	start.add(Calendar.MONTH, -12);
%>




</head>

<body>


<table width="100%" height="100%">
	<tr height="10">
		<td>MiniChart <input id="zane" type="text" value="NYX"> <a
			href="javascript:createImage()">Create</a></td>
	</tr>
</table>
<div id="instDiv" /><script type="text/javascript"> 
 
window.addEventListener("orientationchange", function(){window.location.reload(true);}, false);

createImage();

function createImage(){  
	//alert(screen.availWidth+","+screen.availHeight);
	 var imgUrl =  '/gc/chart?ctr=yahoo&dataonly=true&sdate=<%=format.format(start.getTime())%>&edate=<%=format.format(new Date())%>&tickers='+
              document.getElementById('zane').value+'&series=CLOSE&w='+screen.availWidth/2+'&h='+screen.availHeight/2;
	 document.getElementById('instDiv').innerHTML = "<img src='"+imgUrl+"' width='"+screen.availWidth+"' height='"+screen.availHeight+"' />";
	 document.getElementById('instDiv').width=screen.width;
	 document.getElementById('instDiv').height = screen.height;
	 
}   

</script>