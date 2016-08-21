<!DOCTYPE html>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%
	SimpleDateFormat format = new SimpleDateFormat("MMddyyyy");
	Calendar start = Calendar.getInstance();
	start.add(Calendar.MONTH, -12);
%>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Rafid's Sandbox</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<!-- Le styles -->
<link href="assets/css/bootstrap.css" rel="stylesheet">
<style type="text/css">
body {
	padding-top: 60px;
	padding-bottom: 40px; 
}
</style> 

<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
      <script src="assets/js/html5shiv.js"></script>
    <![endif]-->

<!-- Fav and touch icons -->
<link rel="apple-touch-icon-precomposed" sizes="144x144"
	href="assets/ico/apple-touch-icon-144-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="114x114"
	href="assets/ico/apple-touch-icon-114-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="72x72"
	href="assets/ico/apple-touch-icon-72-precomposed.png">
<link rel="apple-touch-icon-precomposed"
	href="assets/ico/apple-touch-icon-57-precomposed.png">
<link rel="shortcut icon" href="assets/ico/favicon.png">

<jsp:include page="include_bs.jsp" />

</head>

<body>

	<div class="navbar navbar-inverse navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container">
				<button type="button" class="btn btn-navbar" data-toggle="collapse"
					data-target=".nav-collapse">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="brand" href="/ds/bs.jsp">Rafid's SandBox</a>
				<div class="nav-collapse collapse">
					<ul class="nav">
						<li class="active"><a href="#">Dynamic Historical Chart</a></li>
						<!-- 
						<li><a href="#about">About</a></li>
						<li><a href="#contact">Contact</a></li>
						 -->
				</div>
				<!--/.nav-collapse -->
			</div>
		</div>
	</div>

	<div class="container">

		<!-- Main hero unit for a primary marketing message or call to action -->
		<div class="hero-unit">
			<div id="asbuffer" style="float: left;">&nbsp;&nbsp;&nbsp;</div>
			<div class="yui-skin-sam" style="float: left;">
				<div id="myAutoComplete">
					<input id="zane" type="text">
					<div id="symbolContainer_zane"></div>
				</div>
			</div>
			<div id="asbuffer" style="width: 20px; float: left;">&nbsp;</div>
			<div style="float: left;">
				&nbsp;<a id="chartBtnId" class="btn"
					href="javascript:generateChart('chartDiv')">Generate Historical
					Chart</a> &nbsp;<a id="clearBtnId" class="btn"
					href="javascript:clearCharts()">Clear Charts</a>
			</div>
			<br />
			<div id="wrapper">
				<div id="chartDiv" style="display: inline-table;"></div>
			</div>

		</div>

		<!-- Example row of columns -->
		<div class="row">
			<div class="span4" style="text-align: center">
				<h2 style="color: #CC6600">S&P 500</h2>
				<div id="sp500"></div>
				<script>
					addTicker('^GSPC', 'sp500', 'S&P 500', 300, 250)
				</script>
			</div>
			<div class="span4" style="text-align: center">
				<h2 style="color: #CC6600">Nikkei 225</h2>
				<div id="n225"></div>
				<script>
					addTicker('^n225', 'n225', 'Nikkei 225', 300, 250)
				</script>
			</div>
			<div class="span4" style="text-align: center">
				<h2 style="color: #CC6600">Nasdaq</h2>
				<div id="nasdaq"></div>
				<script>
					addTicker('^IXIC', 'nasdaq', 'Nasdaq Composite', 300, 250)
				</script>
			</div>
		</div>

		<hr>

		<footer style="text-align: center;">
			<p>Rafid Zane 2016</p>
		</footer>

	</div>
	<!-- /container -->

	<script src="assets/js/bootstrap.min.js"></script>

	<script type="text/javascript">
		YAHOO.example.BasicRemote = function() {
			var url = "/ds/idal";
			var oDS_zane = new YAHOO.util.XHRDataSource(url);

			oDS_zane.responseType = YAHOO.util.XHRDataSource.TYPE_JSON;
			oDS_zane.responseSchema = {
				resultsList : "ResultSet.Result",
				fields : [ "ticker", "name" ]
			};

			var oAC_zane = new YAHOO.widget.AutoComplete("zane",
					"symbolContainer_zane", oDS_zane);

			oAC_zane.queryDelay = .30;
			oAC_zane.delimChar = ",";
			oAC_zane.generateRequest = function(sQuery) {
				return "?ctr=as&key=SYMBOL&v1=" + sQuery
						+ "&rssize=10&dataonly=true&t="
						+ (new Date().getTime());
			};
			oAC_zane.formatResult = function(oResultData, sQuery, sResultMatch) {
				for ( var i = 0; i < oResultData.length; i++) {
					var value = oResultData[i];
					var tempQuery = sQuery;
					var start = value.toLowerCase().indexOf(
							tempQuery.toLowerCase());
					var temp = "";
					if (start > -1) {
						while (start > -1) {
							temp = temp + value.substr(0, start) + "<b>"
									+ value.substr(start, tempQuery.length)
									+ "</b>";
							value = value.substr(start + sQuery.length,
									value.length);
							start = value.toLowerCase().indexOf(
									tempQuery.toLowerCase());
						}
						temp += value;
					} else {
						temp = value;
					}
					oResultData[i] = temp;
				}

				var ticker = oResultData[0];
				var name = oResultData[1];

				var aMarkup = [ "<div >", "<div class=\"symbolResult\">",
						ticker, "</div>", "<div class=\"nameResult\">", name,
						"</div>", "</div>" ];
				return (aMarkup.join(""));
			};
			return {
				oDS_zane : oDS_zane,
				oAC_zane : oAC_zane
			};

		}();

		var dynamicCH = 250;
		var windowsize = $(window).width();
		var heroWidth = $('.hero-unit').width();
		var dynamicCW = heroWidth;

		function generateChart() {
			addTicker($('#zane').val(), 'chartDiv', 'Securities:'
					+ ($('#zane').val()), dynamicCW, dynamicCH);
		}
		function clearCharts() {
			document.getElementById('chartDiv').innerHTML = "";

		}

		if (windowsize < 440) {
			//if the window is greater than 440px wide then turn o n jScrollPane..
			$("#myAutoComplete").css("width", "10em");
			//$("#myAutoComplete").css("float", "right");
			$('#asbuffer').html("");
			$('.hero-unit').css("padding", '10px');
			$("#chartBtnId").html('Generate Chart');
			$("#clearBtnId").html('Clr');

		} else {
			dynamicCW = heroWidth;
		}
		$(window).resize(function() {
			if (windowsize < 440) {
				$("#myAutoComplete").css("width", "10em");
				$("#myAutoComplete").css("float", "right");
				$('#asbuffer').html("");
				$('.hero-unit').css("padding", '10px');
				$("#chartBtnId").html('Generate Chart');
				$("#clearBtnId").html('Clr');

				dynamicCW = $('.hero-unit').width() * .95;
				$('#asbuffer').html("");
			}

		});
	</script>
</body>
</html>
