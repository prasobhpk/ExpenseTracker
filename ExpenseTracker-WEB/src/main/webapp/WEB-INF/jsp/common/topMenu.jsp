<%@ include file="include.jsp"%>
<style type="text/css">
.topnav .mid {
	background: url('<c:url value="/resources/images/inner_topnavbg.gif"/>');
	text-align: center;
	font-weight: bold;
	height: 100%;
}

.topnav a:link {
	text-decoration: none;
	color: #000000;
	font-weight: bold
}

.topnav a:visited {
	text-decoration: none;
	color: #000000;
	font-weight: bold
}

.topnav a:hover {
	text-decoration: none;
	color: #005476;
	font-weight: bold
}

.topnav a:active {
	text-decoration: none;
	color: #000000;
	font-weight: bold
}

.topnav {
	position: fixed;
	right: 0;
	top: 0;
	width: auto;
	text-align: right;
}
</style>

<table class="topnav" cellspacing="0" cellpadding="0" border="0">
	<tr>
		<td><img height="32"
			src='<c:url value="/resources/images/inner_topnavlft.gif"/>'></td>
		<td class="mid"><span>Welcome <sec:authentication
					property="principal.username" /></span> <span>|</span> <span><a
				href="<c:url value="/home"/>">home</a></span> <span>|</span> <span><a
				href="<c:url value="/et_logout"/>">Log Out</a></span> <span>|</span></td>
		<td><img height="32"
			src="<c:url value="/resources/images/inner_topnavrt.gif"/>"></td>
	</tr>
</table>

