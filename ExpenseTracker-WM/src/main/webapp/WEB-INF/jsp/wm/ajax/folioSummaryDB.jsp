<%@ include file="../../common/include.jsp"%>
<OBJECT classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
	codebase="http://fpdownload.adobe.com/pub/shockwave/cabs/flash/swflash.cab#version=9,0,0,0" 
	WIDTH="100%" HEIGHT="100%" id="myMovieName">
	<PARAM NAME=FlashVars VALUE="URL=<c:url value="/rest/wm/portfolio/folioSummaryDBData"/>?folioId=${folio.id}&FolioName=${folio.portfolioName}">
	<PARAM NAME="movie" VALUE="<%=application.getContextPath()%>/resources/dashboards/foliosummary.swf"> 
	<PARAM NAME="quality" VALUE="high">
	<PARAM NAME="bgcolor" VALUE="#FFFFFF">
	<PARAM NAME="play" VALUE="true">
	<PARAM NAME="loop" VALUE="true">
	<PARAM NAME=bgcolor VALUE="#FFFFFF">
	<EMBED src="<%=application.getContextPath()%>/resources/dashboards/foliosummary.swf" quality=high bgcolor=#FFFFFF WIDTH="100%" HEIGHT="100%" 
		NAME="myMovieName" ALIGN="" TYPE="application/x-shockwave-flash" 
	play="true" loop="true" 
	FlashVars="URL=<c:url value="/rest/wm/portfolio/folioSummaryDBData"/>?folioId=${folio.id}&FolioName=${folio.portfolioName}"
		PLUGINSPAGE="http://www.adobe.com/shockwave/download/index.cgi?P1_Prod_Version=ShockwaveFlash">
	</EMBED>
</OBJECT>
