<%@ include file="include.jsp"%>
<script>
	$().ready(function() {
		var dtOptions={
			inline: true,
			dateFormat:'${initParam.jq_dateFormat}',
			showOn: 'button', 
			buttonImage: '<%=application.getContextPath()%>/resources/css/redmond/images/calendar.gif',
			buttonImageOnly : true,
			minDate : +0,
			changeMonth : true,
			changeYear : true
			//showOn:'focus'
			};
	$('#txt_forecastMonth').val('${now}');
	$('#txt_forecastMonth').datepicker(dtOptions);
	$("#statusForm").validationEngine();
	});
</script>
<div class="header">Alerts</div>
<div class="subhead">I'm a subheader</div>
<div class="content">
	<div id="RsideAcc">
		<div>
			<h3>
				<a href="#">Expense Forecast</a>
			</h3>
			<div>
			
				<form action='<c:url value="/exp/forecast/status"/>' id="statusForm">
					<spring:message code="ui.exp.financialStatus.forecastMonth" /><br>
					<input  id="txt_forecastMonth" type="text" name="month"	class="validate[custom[date]] text  ui-corner-all"	/><br>
					<input type="submit" value="Find Forecast"	id="btn_load_forecast" class="btn ui-corner-all" />
		
				</form>
				
			</div>
		</div>
	</div>
</div>
<div class="footer">I'm a footer</div>