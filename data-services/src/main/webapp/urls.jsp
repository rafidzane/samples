<html>
<title>Service Urls</title>
<body>
<%
String urlTemplate = "http://localhost:7001/ds/idal?dataonly=#DATAONLY#&s1=SEC[B]:CLOSE&s2=SEC[ORCL]:CLOSE&sdate=11082008&edate=11082013&ctr=yahoo&output=json&usecache=#CACHE#&debug=#DEBUG#";
%>
<div>
<a href="<%=urlTemplate.replace("#DATAONLY#", "false").replace("#DEBUG#", "false").replace("#USECACHE#", "false")%>">Historical Block JSON Data</a>
</div>


</body>
</html>