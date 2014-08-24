<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<tiles:insertAttribute name="jsInclude" ignore="true"/>
<link rel="stylesheet" href="<%=application.getContextPath()%>/resources/css/style.css" type="text/css" />	
 <title><tiles:insertAttribute name="title" ignore="true" /></title>
 <style type="text/css">

#globalMsg {
	list-style:none;	
	font-family:arial;
	font-size:11px;
	margin:0;
	padding:0;
	width:100%;
}

	#globalMsg li {
		margin:10px;
		border-bottom:1px solid #ccc;
		position:relative;
		cursor:pointer;
	}
	
	#globalMsg h4 {
		margin:0;
		font-size:11px;
	}

	#globalMsg span.ui-icon {
		position:absolute;
		right:5px; top:0;
		float:right; 
		margin:0px 7px 50px 0;" 
		font-size:13px;
	}
	
	#globalMsg p {
		margin:5px 0;
		display:none;
	}	
	
	.headContainer {
  left: 0;
  position: fixed;
  top: 0;
  width: 100%;
  z-index: 10;
  height: 50px;
}
.midContainer {
  position: relative;
  width: 100%;
  top: 50px;
}
.bottomContainer {
background-image: url('<c:url value="/resources/images/banner_bt.png"/>');
height: 20px;
z-index: 20;
bottom: 0;
position: fixed;
}
</style>
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
</head>
<body>
<div class="headContainer">
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
								<img id="img_home" alt="" src="<%=application.getContextPath()%>/resources/images/home.png" style="height: 16px;width: 16px;" currentView="${currentView}" title="Set as home page" class="qTip">
								<select id="sel_theme" onchange="changeTheme(this.value);">
									<option value="-1">--Theme--</option>
									<option value="redmond">Redmond</option>
									<option value="humanity">Humanity</option>
								</select>
							</td>
						</tr>
					</tbody>
	</table>
</div>
<div class="midContainer">
<table cellspacing="0" cellpadding="0" border="0" bgcolor="#f6f6fb"
	style="height: 100%; width: 100%;" >
	<tbody  class="ui-widget-content">
		<!-- Head -->
		<tr>
			<td>
				
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
						<div id="Lsidebar">
							<tiles:insertAttribute name="userMenu" />
						</div>
						<div id="pageWrap" >
							<div id="contentWrap" align="left" style="width: 100%;min-height: 550px;" class="ui-widget-content ui-corner-all">
								<c:if test="${not empty sessionScope.actionMSG}">
								<div id="actionMSG" class="ui-widget-content ui-corner-all ui-state-error" align="center">
									<span class="f14"><c:out value="${actionMSG.msg}" default=""/></span>
									<%session.removeAttribute("actionMSG"); %>
								</div>
								</c:if>
								<tiles:insertAttribute name="body" />
							</div>
						</div>
						<div id="Rsidebar" style="">
							<div id="RsideAcc">
								<div>
									<h3><a href="#">Expense Forecast</a></h3>
									<div>
											content here........	
									</div>
								</div>
									
							</div>
						</div>
				</div>
			</td>
		</tr>
		
	</tbody>
</table>
</div>
<div class="bottomContainer">

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