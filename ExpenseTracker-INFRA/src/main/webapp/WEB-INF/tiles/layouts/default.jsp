<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<tiles:insertAttribute name="jsInclude" ignore="true"/>
<link rel="stylesheet" href="<%=application.getContextPath()%>/resources/css/style.css" type="text/css" />	
 <title><tiles:insertAttribute name="title" ignore="true" /></title>
 <script type="text/javascript">
  function createCookie(name,value,days) {
		if (days) {
			var date = new Date();
			date.setTime(date.getTime()+(days*24*60*60*1000));
			var expires = "; expires="+date.toGMTString();
		}
		else var expires = "";
		document.cookie = name+"="+value+expires+"; path=/";
	}
  function changeTheme(theme){
	  //alert(theme);
	  if(theme!=-1){
		  createCookie('org.springframework.web.servlet.theme.CookieThemeResolver.THEME',theme);
	 	  location.reload(true);	
	  }
  }

 </script>
</head>
<body>
<table cellspacing="0" cellpadding="0" border="0" bgcolor="#f6f6fb"
	style="height: 100%; width: 100%;" >
	<tbody  class="ui-widget-content">
		<!-- Head -->
		<tr>
			<td>
				<table cellspacing="0" cellpadding="0" border="0" bgcolor="#f6f6fb"
					style="height: 100%; width: 100%;">
					<tbody>
						<tr class="grphics5">
							<td colspan="3"
								class="bannerCustomLeft" ><span class="prodText">Expense Tracker</span></td>
							<td align="right"  valign="top" class="bannerCustomRight">
								<tiles:insertAttribute name="top_menu" />
							</td>
						</tr>
						<tr>
							<td colspan="4"	style="background-image: url(<%=application.getContextPath()%>/resources/images/right.png);" height="20" align="right">
							</td>
						</tr>
					</tbody>
				</table>
			</td>
		</tr>
		<!-- mainWrap -->
		<tr>
			<td>
				<div class="mainWrap" >
						<noscript>
							<div class="ui-widget-content ui-corner-all ui-state-error" align="center">
								<b>This page is trying to run JavaScript and your browser either does not support JavaScript or you may have turned-off JavaScript. If you have disabled JavaScript on your computer, please turn on JavaScript. Thank you</b>
							</div>
						</noscript>
						<tiles:insertAttribute name="userMenu" />
						<div id="pageWrap">
							<tiles:insertAttribute name="body" />
						</div>
				</div>
			</td>
		</tr>
		<tr>
			<td style="background-image: url('<c:url value="/resources/images/banner_bt.png"/>');"
				height="20"></td>
		</tr>
	</tbody>
</table>
</body>
</html>