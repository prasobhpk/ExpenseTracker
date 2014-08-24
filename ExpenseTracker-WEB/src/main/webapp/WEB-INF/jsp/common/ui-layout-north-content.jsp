<%@ include file="include.jsp"%>
<div class="grphics5">
	<span class="prodText">Expense Tracker</span>
</div>
<ul class="toolbar">
	<li id="tbarToggleNorth" class="first"><span></span>Toggle NORTH</li>
	<li id="tbarOpenSouth"><span></span>Open SOUTH</li>
	<li id="tbarCloseSouth"><span></span>Close SOUTH</li>
	<li id="tbarPinWest"><span></span>Pin/Unpin WEST</li>
	<li id="tbarPinEast" class="last"><span></span>Pin/Unpin EAST</li>

	<li><span><img id="img_home" alt=""
			src="<%=application.getContextPath()%>/resources/images/home.png"
			style="height: 16px; width: 16px;" currentView="${currentView}"
			title="Set as home page" class="qTip"></span> Set Home Page</li>

	<li><span></span> <select id="sel_theme"
		onchange="changeTheme(this.value);">
			<option value="-1">--Theme--</option>
			<option value="redmond">Redmond</option>
			<option value="humanity">Humanity</option>
	</select></li>
</ul>