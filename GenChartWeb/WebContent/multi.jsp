
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%><html>
<head>
<title>Historical Chart Image Creator</title>
<meta name="description"
	content="Parallel Processing Data Retrieval to create Historical Chart Images ">
<meta name="keywords"
	content="Historical Charts, Rafid Zane, Rafid Wahab, JFreeChart, Java, SEO, Finance, Historical Data">
<jsp:include page="metadata.jsp" />

<link rel="stylesheet" href="styles/default.css">
<%
SimpleDateFormat format = new SimpleDateFormat("MMddyyyy");
%>

</head>
<body>
<table width="100%">
	<tr class="fill_dark_grey">
		<td class="title_white">Real Time Generated Chart Images - Multi Series</td>
	</tr>
	<tr>
	  <td>
		<img border="0"
			src="/gc/chart?rh=yahoo&dataonly=true&sdate=01012009&edate=<%=format.format(new Date())%>&tickers=A,B,C,D,E,F&series=CLOSE&w=600&h=350"
			alt="Chart A,B,C,D,E,F" />

		<img border="0"
			src="/gc/chart?rh=yahoo&dataonly=true&sdate=01012009&edate=<%=format.format(new Date())%>&tickers=ED,IBM,NYX,JPM,ORCL&series=CLOSE&w=600&h=350"
			alt="Chart ED,IBM,NYX,JPM,ORCL" />

		<img border="0"
			src="/gc/chart?rh=yahoo&dataonly=true&sdate=01012009&edate=<%=format.format(new Date())%>&tickers=C,BAC,JPM,DSX,PRGN&series=CLOSE&w=600&h=350"
			alt="Chart C,BAC,JPM,DSX,PRGN" />
			
		<img border="0"
			src="/gc/chart?rh=yahoo&dataonly=true&sdate=01012009&edate=<%=format.format(new Date())%>&tickers=MSFT,ORCL,IBM,JPM,DSX,FSEAX,DOW&series=CLOSE&w=600&h=350"
			alt="Chart MSFT,ORCL,IBM,JPM,DSX,FSEAX,DOW" />


		</td>
	</tr>
</table>
</body>
</html>
