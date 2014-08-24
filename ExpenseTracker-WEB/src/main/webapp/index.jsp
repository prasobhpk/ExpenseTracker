<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" session="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<script type="text/javascript" src="<%=application.getContextPath()%>/resources/js/jquery.js"></script>
<script type="text/javascript" src="<%=application.getContextPath()%>/resources/js/jquery-ui.js"></script>
<script type="text/javascript" src="<%=application.getContextPath()%>/resources/js/jquery_003.js"></script>
<script type="text/javascript" src="<%=application.getContextPath()%>/resources/js/portfolio.js"></script>
<script src="<%=application.getContextPath()%>/resources/js/init.js"></script>
<script type="text/javascript">
$(function (){
	$("#web_portfolio_screen_panelContent li span a, #ui_portfolio_screen_panelContent li span a").click(function (e){
        e.preventDefault();
		window.open("<c:url value='/login'/>","_blank","toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=no, copyhistory=yes, width="+screen.width+", height="+screen.height);
	});
});
		
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ExpenseTracker: Manage your expenses &amp; track your investments</title>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<link rel="stylesheet" type="text/css" href="<%=application.getContextPath()%>/resources/css/style_bl.css">
<style>
@font-face {
	font-family: 'StRydeRegularRegular';
	src: url('fonts/stryde-regular-webfont.eot');
	src: url('fonts/stryde-regular-webfont.eot?#iefix') format('embedded-opentype'), url('fonts/stryde-regular-webfont.woff') format('woff'), url('fonts/stryde-regular-webfont.ttf') format('truetype');
	font-weight: normal;
	font-style: normal;
}
.messagebox1 {
	font-size:11px;
	color:#ff0000;
	font-weight:bold;
	float:left;
	text-align:left;
}
.messageboxokhome {
	font-size:11px;
	color:#006600;
	font-weight:bold;
	float:left;
	text-align:left;
}
</style>
<link rel="stylesheet" href="<%=application.getContextPath()%>/resources/css/queryLoader.css" type="text/css">
<link rel="stylesheet" type="text/css" href="<%=application.getContextPath()%>/resources/css/portfolio.css">
</head>
<body style="position: relative;"><div id="scroll-wrap"></div>

<div class="container" id="content" style=""> 
  <!--one--> 
  
  <div id="portfolioTop" style="height:50px;"></div>
  <div class="graphics_layer5"></div>
  <div id="portfolio">
    <div class="portfoliowrapper">
      <h1>Expense Tracker</h1>
     
      <div id="portfolio_right_panel">

					<div class="web_portfolio">
						<div style="" id="web_portfolio_screen_panel">

							<ul id="web_portfolio_screen_panelContent">
								<li class="web_portfolio_screen_panelImage"><img
									src="<%=application.getContextPath()%>/resources/images/expslides/exp_scrns_1.jpg"
									alt="sulochanamills" title=""> <span><a href="#"
										target="_blank">Login</a></span></li>
								<li class="web_portfolio_screen_panelImage"><img
									src="<%=application.getContextPath()%>/resources/images/expslides/exp_scrns_2.jpg"
									alt="wftcloud" title=""> <span><a href="#"
										target="_blank">Login</a></span></li>
								<li class="web_portfolio_screen_panelImage"><img
									src="<%=application.getContextPath()%>/resources/images/expslides/exp_scrns_3.jpg"
									alt="sulochanamills" title=""> <span><a href="#"
										target="_blank">Login</a></span></li>
								<li class="web_portfolio_screen_panelImage"><img
									src="<%=application.getContextPath()%>/resources/images/expslides/exp_scrns_4.jpg"
									alt="sulochanamills" title=""> <span><a href="#"
										target="_blank">Login</a></span></li>
								<li class="web_portfolio_screen_panelImage"><img
									src="<%=application.getContextPath()%>/resources/images/expslides/exp_scrns_5.jpg"
									alt="sulochanamills" title=""> <span><a href="#"
										target="_blank">Login</a></span></li>

								<div class="clear web_portfolio_screen_panelImage"></div>
							</ul>
						</div>

					</div>
					<div class="ui_portfolio" style="display: none;">
						<div id="ui_portfolio_screen_panel">

							<ul id="ui_portfolio_screen_panelContent">
								<li class="ui_portfolio_screen_panelImage"><img
									src="<%=application.getContextPath()%>/resources/images/expslides/wm_scrns_1.jpg"
									alt="1" title=""> <span><a href="#"
										target="_blank">Login</a></span>
								<li class="ui_portfolio_screen_panelImage"><img
									src="<%=application.getContextPath()%>/resources/images/expslides/wm_scrns_2.jpg"
									alt="2" title=""> <span><a href="#"
										target="_blank">Login</a></span></li>

								<li class="ui_portfolio_screen_panelImage"><img
									src="<%=application.getContextPath()%>/resources/images/expslides/wm_scrns_3.jpg"
									alt="2" title=""> <span><a href="#"
										target="_blank">Login</a></span></li>
								<li class="ui_portfolio_screen_panelImage"><img
									src="<%=application.getContextPath()%>/resources/images/expslides/wm_scrns_4.jpg"
									alt="2" title=""> <span><a href="#"
										target="_blank">Login</a></span></li>
								<li class="ui_portfolio_screen_panelImage"><img
									src="<%=application.getContextPath()%>/resources/images/expslides/wm_scrns_5.jpg"
									alt="2" title=""> <span><a href="#"
										target="_blank">Login</a></span></li>

								<div class="clear ui_portfolio_screen_panelImage"></div>
							</ul>
						</div>
					</div>


				</div>
      <ul id="portfolio_left_pane">
        <a href="#" id="web_portfolio_screen">
        <li class="selected">Expenses</li>
        </a>
					<a href="#" id="ui_portfolio_screen">
						<li class="">Investments</li>
					</a>
				</ul>

    </div>
  </div>
  
  <!--one end--> 
</div>
<div style="height: 25px; overflow: hidden;" class="footer_thin">
  <div class="footerwrap">
    <div class="ftrlft">Copyright © 2012 PKP | All rights reserved</div>
    <div class="ftrrht">Follow us
      <p><img src="<%=application.getContextPath()%>/resources/images/facebook.png" alt="Facebook"><img src="<%=application.getContextPath()%>/resources/images/twitter.png" alt="Twitter"><img src="<%=application.getContextPath()%>/resources/images/linkedinn.png" alt="Linked Inn"></p>
    </div>
  </div>
</div>



</body></html>