<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<tiles:insertAttribute name="jsInclude" ignore="true" />
<link rel="stylesheet"	href="<%=application.getContextPath()%>/resources/css/style.css" type="text/css" />
<tiles:useAttribute name="title" ignore="true" id="page_title"/>
<title><spring:message code="${page_title}"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="language" content="en" />


<script type="text/javascript">
 //List of fav stocks
 //IE corner isssue
 //$.uicornerfix('10px');
 var cmpList='';
 //Flag the controls sending more requests to the server
 var isReqAborted=false;
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
  Array.prototype.contains = function(value) {
	  var i;
	  var size=this.length;
	  for (var i = 0;i < size; i++) {
		  if (this[i] === value) {
		  	return true;
		  }
	  }
	  return false;
  };
  function EL(id){
		return document.getElementById(id);
  }
  function dispalyMessage(options){
	  if(options.msg){
		  $("#globalMsg li.brief p").html(options.msg);
	  }
	  if(options.details){
		  $("#globalMsg li.details p").html(options.details);
	  }
	  if(options.header){
		  $("#msgDialog").dialog("option",'title',options.header); 
	  }
	  if(options.type){
		  var type=options.type;
		  switch (type) {
				case 'error':
					$("#globalMsg li p").attr('class','ui-state-error ui-corner-all');
					//$("#icon").attr('class','ui-icon ui-icon-alert');
					break;
				case 'warn':
					$("#globalMsg li p").attr('class','ui-state-highlight ui-corner-all');
					//$("#icon").attr('class','ui-icon ui-icon-info');
					break;
		
				default:
					$("#globalMsg li p").attr('class','ui-corner-all');
					//$("#icon").attr('class','ui-icon ui-icon-circle-check');
					break;
			}
	  }
	  $('#msgDialog').dialog( "open" );
  }
  $().ready(function() {
	  $('input[type="text"]').addClass('ui-corner-all');
	  $('#msgDialog').dialog({
			autoOpen: false,
			resizable: false,
			show: 'slide',
			//width: 300,
			height:300,
			bgiframe: true,			
	        modal: true,

			buttons: {
				
				"Abort": function() { 
						isReqAborted=true;
						$(this).dialog("close"); 
					},
				"Ok": function() { 
					$(this).dialog("close"); 
				}
				
			}
		});	
	  
	  $('#globalMsg li span.toggle-btn').click(function () {
			var text = $(this).parent('li').children('p');

			if (text.is(':hidden')) {
				text.slideDown('200');
				$(this).first('span').removeClass('ui-icon-plus').addClass('ui-icon-minus');
			} else {
				text.slideUp('200');
				$(this).first('span').removeClass('ui-icon-minus').addClass('ui-icon-plus');	
			}
			
		});
	 // $('.ui-corner-all').corner();
	 
	 //hadle session timeout.....
		// setup the dialog
		$("#sessionTimeout_dialog").dialog({
			autoOpen: false,
			modal: true,
			width: 400,
			height: 200,
			closeOnEscape: false,
			draggable: false,
			resizable: false,
			buttons: {
				'Yes, Keep Working': function(){
					$(this).dialog('close');
				},
				'No, Logoff': function(){
					// fire whatever the configured onTimeout callback is.
					// using .call(this) keeps the default behavior of "this" being the warning
					// element (the dialog in this case) inside the callback.
					$.idleTimeout.options.onTimeout.call(this);
				}
			}
		});

		// cache a reference to the countdown element so we don't have to query the DOM for it on each ping.
		var $countdown = $("#dialog-countdown");

		// start the idle timer plugin
		$.idleTimeout('#sessionTimeout_dialog', 'div.ui-dialog-buttonpane button:first', {
			idleAfter:'${pageContext.session.maxInactiveInterval}',
			pollingInterval: 60,
			keepAliveURL: '<c:url value="/keepAlive"/>',
			serverResponseEquals: 'OK',
			onTimeout: function(){
				window.location = '<c:url value="/login"/>';
			},
			onIdle: function(){
				$(this).dialog("open");
			},
			onCountdown: function(counter){
				$countdown.html(counter); // update the counter
			}
		});

		//Set the home page
		$('#img_home').click(function(){
			var view=$(this).attr('currentView');
			var url='<c:url value="/setHome"/>';
			var params={viewName:view};
			alert(view);
			$.getJSON(url,params,function(data){
				alert(data.status);
			});
		});
		//Global ajax settings.....
		$('body').ajaxSuccess(function() {
			// clearInterval(timer);
		});
		
		var icons = {
				header: "ui-icon-circle-arrow-e",
				headerSelected: "ui-icon-circle-arrow-s"
			};
		$("#RsideAcc").accordion({
			icons: icons,
			header: "> div > h3"
			}).sortable({
			axis: "y",
			handle: "h3",
			stop: function(event, ui) {
				stop = true;
			}
		});
  });
 </script>

<script type="text/javascript">

	var outerLayout;

	/*
	*#######################
	*     ON PAGE LOAD
	*#######################
	*/
	$(document).ready( function() {
		// create the OUTER LAYOUT
		outerLayout = $("body").layout( layoutSettings_Outer );

		/*******************************
		 ***  CUSTOM LAYOUT BUTTONS  ***
		 *******************************
		 *
		 * Add SPANs to the east/west panes for customer "close" and "pin" buttons
		 *
		 * COULD have hard-coded span, div, button, image, or any element to use as a 'button'...
		 * ... but instead am adding SPANs via script - THEN attaching the layout-events to them
		 *
		 * CSS will size and position the spans, as well as set the background-images
		 */

		// BIND events to hard-coded buttons in the NORTH toolbar
		outerLayout.addToggleBtn( "#tbarToggleNorth", "north" );
		outerLayout.addOpenBtn( "#tbarOpenSouth", "south" );
		outerLayout.addCloseBtn( "#tbarCloseSouth", "south" );
		outerLayout.addPinBtn( "#tbarPinWest", "west" );
		outerLayout.addPinBtn( "#tbarPinEast", "east" );

		// save selector strings to vars so we don't have to repeat it
		// must prefix paneClass with "body > " to target ONLY the outerLayout panes
		var westSelector = "body > .ui-layout-west"; // outer-west pane
		var eastSelector = "body > .ui-layout-east"; // outer-east pane

		 // CREATE SPANs for pin-buttons - using a generic class as identifiers
		$("<span></span>").addClass("pin-button").prependTo( westSelector );
		$("<span></span>").addClass("pin-button").prependTo( eastSelector );
		// BIND events to pin-buttons to make them functional
		outerLayout.addPinBtn( westSelector +" .pin-button", "west");
		outerLayout.addPinBtn( eastSelector +" .pin-button", "east" );

		 // CREATE SPANs for close-buttons - using unique IDs as identifiers
		$("<span></span>").attr("id", "west-closer" ).prependTo( westSelector );
		$("<span></span>").attr("id", "east-closer").prependTo( eastSelector );
		// BIND layout events to close-buttons to make them functional
		outerLayout.addCloseBtn("#west-closer", "west");
		outerLayout.addCloseBtn("#east-closer", "east");


		/* Create the INNER LAYOUT - nested inside the 'center pane' of the outer layout
		 * Inner Layout is create by createInnerLayout() function - on demand
		 *
			innerLayout = $("div.pane-center").layout( layoutSettings_Inner );
		 *
		 */


		// DEMO HELPER: prevent hyperlinks from reloading page when a 'base.href' is set
		$("a").each(function () {
			var path = document.location.href;
			if (path.substr(path.length-1)=="#") path = path.substr(0,path.length-1);
			if (this.href.substr(this.href.length-1) == "#") this.href = path +"#";
		});
		
		$(".header").addClass("ui-widget-header");

	});

	/*
	*#######################
	* OUTER LAYOUT SETTINGS
	*#######################
	*
	* This configuration illustrates how extensively the layout can be customized
	* ALL SETTINGS ARE OPTIONAL - and there are more available than shown below
	*
	* These settings are set in 'sub-key format' - ALL data must be in a nested data-structures
	* All default settings (applied to all panes) go inside the defaults:{} key
	* Pane-specific settings go inside their keys: north:{}, south:{}, center:{}, etc
	*/
	var layoutSettings_Outer = {
		name: "outerLayout" // NO FUNCTIONAL USE, but could be used by custom code to 'identify' a layout
		// options.defaults apply to ALL PANES - but overridden by pane-specific settings
	,	defaults: {
			size:					"auto"
		,	minSize:				50
		,	paneClass:				"pane" 		// default = 'ui-layout-pane'
		,	resizerClass:			"resizer"	// default = 'ui-layout-resizer'
		,	togglerClass:			"toggler"	// default = 'ui-layout-toggler'
		,	buttonClass:			"button"	// default = 'ui-layout-button'
		,	contentSelector:		".content"	// inner div to auto-size so only it scrolls, not the entire pane!
		,	contentIgnoreSelector:	"span"		// 'paneSelector' for content to 'ignore' when measuring room for content
		,	togglerLength_open:		35			// WIDTH of toggler on north/south edges - HEIGHT on east/west edges
		,	togglerLength_closed:	35			// "100%" OR -1 = full height
		,	hideTogglerOnSlide:		true		// hide the toggler when pane is 'slid open'
		,	togglerTip_open:		"Close This Pane"
		,	togglerTip_closed:		"Open This Pane"
		,	resizerTip:				"Resize This Pane"
		//	effect defaults - overridden on some panes
		,	fxName:					"slide"		// none, slide, drop, scale
		,	fxSpeed_open:			750
		,	fxSpeed_close:			1500
		,	fxSettings_open:		{ easing: "easeInQuint" }
		,	fxSettings_close:		{ easing: "easeOutQuint" }
	}
	,	north: {
			spacing_open:			1			// cosmetic spacing
		,	togglerLength_open:		0			// HIDE the toggler button
		,	togglerLength_closed:	-1			// "100%" OR -1 = full width of pane
		,	resizable: 				false
		,	slidable:				false
		//	override default effect
		,	fxName:					"none"
		}
	,	south: {
			maxSize:				200
		,	spacing_closed:			0			// HIDE resizer & toggler when 'closed'
		,	slidable:				false		// REFERENCE - cannot slide if spacing_closed = 0
		,	initClosed:				true
		//	CALLBACK TESTING...
		,	onhide_start:			function () { return confirm("START South pane hide \n\n onhide_start callback \n\n Allow pane to hide?"); }
		,	onhide_end:				function () { alert("END South pane hide \n\n onhide_end callback"); }
		,	onshow_start:			function () { return confirm("START South pane show \n\n onshow_start callback \n\n Allow pane to show?"); }
		,	onshow_end:				function () { alert("END South pane show \n\n onshow_end callback"); }
		,	onopen_start:			function () { return confirm("START South pane open \n\n onopen_start callback \n\n Allow pane to open?"); }
		,	onopen_end:				function () { alert("END South pane open \n\n onopen_end callback"); }
		,	onclose_start:			function () { return confirm("START South pane close \n\n onclose_start callback \n\n Allow pane to close?"); }
		,	onclose_end:			function () { alert("END South pane close \n\n onclose_end callback"); }
		//,	onresize_start:			function () { return confirm("START South pane resize \n\n onresize_start callback \n\n Allow pane to be resized?)"); }
		,	onresize_end:			function () { alert("END South pane resize \n\n onresize_end callback \n\n NOTE: onresize_start event was skipped."); }
		}
	,	west: {
			size:					250
		,	spacing_closed:			21			// wider space when closed
		,	togglerLength_closed:	21			// make toggler 'square' - 21x21
		,	togglerAlign_closed:	"top"		// align to top of resizer
		,	togglerLength_open:		0			// NONE - using custom togglers INSIDE west-pane
		,	togglerTip_open:		"Close West Pane"
		,	togglerTip_closed:		"Open West Pane"
		,	resizerTip_open:		"Resize West Pane"
		,	slideTrigger_open:		"click" 	// default
		,	initClosed:				false
		//	add 'bounce' option to default 'slide' effect
		,	fxSettings_open:		{ easing: "easeOutBounce" }
		}
	,	east: {
			size:					200
		,	spacing_closed:			21			// wider space when closed
		,	togglerLength_closed:	21			// make toggler 'square' - 21x21
		,	togglerAlign_closed:	"top"		// align to top of resizer
		,	togglerLength_open:		0 			// NONE - using custom togglers INSIDE east-pane
		,	togglerTip_open:		"Close East Pane"
		,	togglerTip_closed:		"Open East Pane"
		,	resizerTip_open:		"Resize East Pane"
		,	slideTrigger_open:		"mouseover"
		,	initClosed:				true
		//	override default effect, speed, and settings
		,	fxName:					"drop"
		,	fxSpeed:				"normal"
		,	fxSettings:				{ easing: "" } // nullify default easing
		}
	,	center: {
			paneSelector:			"#mainContent" 			// sample: use an ID to select pane instead of a class
		,	minWidth:				200
		,	minHeight:				200
		}
	};

</script>

</head>
<body>

	<div class="ui-layout-west">
			<tiles:insertAttribute name="ui-layout-west-content" />
			<!--<div class="footer">Automatically positioned footer</div>-->
	</div>

	<div class="ui-layout-east">
		<tiles:insertAttribute name="ui-layout-east-content" />
	</div>


	<div class="ui-layout-north">
		<div style="top: 0px" align="right">
		<tiles:insertAttribute name="top_menu" />
		</div>
		<tiles:insertAttribute name="ui-layout-north-content" />
	</div>


	<div class="ui-layout-south">
		<tiles:insertAttribute name="ui-layout-south-content" />
	</div>


	<div id="mainContent">
		<c:if test="${not empty sessionScope.actionMSG}">
			<div id="actionMSG"
				class="ui-widget-content ui-corner-all ui-state-error"
				align="center">
				<span class="f14"><c:out value="${actionMSG.msg}" default="" /></span>
				<%
					session.removeAttribute("actionMSG");
				%>
			</div>
		</c:if>
		<tiles:insertAttribute name="body" />
	</div>



	<!-- Common Popups -->
<div id="msgDialog" title="Message">
<!-- 	<p id="globalMsg"> -->
<!-- 		<span id="icon" style="float:left; margin:0 7px 50px 0;"></span> -->
<!-- 		<span id="msg"></span> -->
<!-- 	</p> -->
	
	<ul id="globalMsg">
		<li class="brief">
			<h4>Message</h4>
			<span class="toggle-btn ui-icon ui-icon-plus"></span>
			<p>
<!-- 				<span id="icon" style="float:left; margin:0 7px 50px 0;"></span> -->
<!-- 				<span id="msg"></span> -->
			</p>
		</li>
		<li class="details">
			<h4>Technical Details</h4>
			<span class="toggle-btn ui-icon ui-icon-plus"></span>
			<p>
<!-- 				<span id="icon" style="float:left; margin:0 7px 50px 0;"></span> -->
<!-- 				<span id="msg"></span> -->
			</p>
		</li>
	</ul>
</div>
<!-- dialog window markup -->
<div id="sessionTimeout_dialog" title="Your session is about to expire!">
	<p>
		<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 50px 0;"></span>
		You will be logged off in <span id="dialog-countdown" style="font-weight:bold"></span> seconds.
	</p>
	
	<p>Do you want to continue your session?</p>
</div>
<c:if test="${not empty sessionScope.exceptionMSG}">
<!-- Dialog window for exceptions -->
<script>
$().ready(function() {
var options={
		msg:'<c:out value="${exceptionMSG.msg}" default=""/>',
		details:'<c:out value="${exceptionMSG.details}" default=""/>',
		type:'<c:out value="${exceptionMSG.status}" default=""/>',
		header:'Error...'
};
dispalyMessage(options);
});
</script>
<%session.removeAttribute("exceptionMSG"); %>
</c:if>

</body>
</html> 