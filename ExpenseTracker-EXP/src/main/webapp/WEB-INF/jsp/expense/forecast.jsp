<%@ include file="../common/include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script>
$(document).ready(function(){
	 //Apply validation
	 $("#forecastForm").validationEngine();
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
	$('#txt_forecast_date').val('${now}');
	$('#txt_forecast_date').datepicker(dtOptions);
	$('#txt_title').blur(function() {
		$('#txt_description').val($(this).val());
	});
});
</script>


<form:form id="forecastForm" method="post" modelAttribute="forecast"
	style="margin: 30px 30px;">
	<table class="ui-widget ui-widget-content ui-corner-all input">
		<tr>
			<td><spring:message code="ui.exp.forecastForm.title" />
				<form:hidden path="id"/>
			</td>
			<td ><form:input path="title" id="txt_title"
					cssClass="validate[required,minSize[4],maxSize[30]] text  ui-corner-all"
					lbl="Title" /></td>
			<td ></td>
		</tr>
		<tr>
			<td><spring:message code="ui.exp.forecastForm.forecastType" /></td>
			<td><form:select path="forecastType" id="txt_forecastType"
					cssClass="validate[required] ui-corner-all" lbl="Forecast Type">
					<form:option value="" label="--- Select ---" />
					<form:options items="${forecastTypes}" />
				</form:select></td>
			<td ></td>
		</tr>
		<tr>
			<td><spring:message code="ui.exp.forecastForm.date" /></td>
			<td><form:input path="date" id="txt_forecast_date"
					cssClass="validate[required,,custom[date]] text  ui-corner-all"
					lbl="Forecast Date" /></td>
			<td></td>
		</tr>
		<tr>
			<td><spring:message code="ui.exp.forecastForm.forecastAmount" /></td>
			<td><form:input path="forecastAmount" id="txt_expense"
					cssClass="validate[required,custom[integer]] text  ui-corner-all"
					lbl="Forecast Amount" /></td>
			<td></td>
		</tr>
		<tr>
			<td><spring:message code="ui.exp.forecastForm.description" /></td>
			<td><form:textarea path="description"
					id="txt_description"
					cssClass="validate[required,minSize[3]] text  ui-corner-all"
					lbl="Description" /></td>
			<td></td>
		</tr>
		<tr>
			<td><spring:message code="ui.exp.forecastForm.periodic" /></td>
			<td><form:checkbox path="periodic" id="txt_periodic"
					cssClass="ui-corner-all" lbl="Periodic" /></td>
			<td></td>
		</tr>
		<tr>
			<td><spring:message code="ui.exp.forecastForm.period" /></td>
			<td><form:select path="period" id="txt_title"
					cssClass="validate[required] ui-corner-all" lbl="Title">
					<form:option value="" label="--- Select ---" />
					<form:options items="${periods}" />
				</form:select></td>
			<td ></td>
		</tr>

		<tr>
			<td></td>
			<td><input type="submit" value="Save Forecast"
				id="btn_add_forecast" class="btn ui-corner-all" /></td>
		</tr>
	</table>
</form:form>