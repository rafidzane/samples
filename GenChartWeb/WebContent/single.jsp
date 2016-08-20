<html>
<head>
    <title>Historical Chart Image Creator</title>
    <meta name="description" content="Parallel Processing Data Retrieval to create Historical Chart Images ">
    <meta name="keywords" content="Historical Charts, Rafid Zane, Rafid Wahab, JFreeChart, Java, SEO, Finance, Historical Data">
    <jsp:include page="metadata.jsp" /> 
    
    <link rel="stylesheet" href="styles/default.css">
      
</head>
<body>
<table width ="100%" >
	<tr class="fill_dark_grey">
		<td class="title_white" >Real Time Generated Chart Images - Single Series</td>
	</tr>
	<tr><td class="title_white">
<%
String h = request.getParameter("h") == null? "200": request.getParameter("h");
String w = request.getParameter("w") == null? "600": request.getParameter("w");
for(int i = 65; i< 91;i++){
	 
%>	

 <img border="0" src="/gc/chart?rh=yahoo&dataonly=true&sdate=01012008&edate=08302016&tickers=<%=((char)i)%>&series=CLOSE&w=<%=w%>&h==<%=h%>" 
     alt="Chart <%=(char)i%>" />

	
<%	
}
%>
</td>
</tr>
</table>
</body>
</html>
