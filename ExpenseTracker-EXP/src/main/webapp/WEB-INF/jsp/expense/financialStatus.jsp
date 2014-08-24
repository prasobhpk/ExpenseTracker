<%@ include file="../common/include.jsp"%>
<meta charset="utf-8">
<style>
.column {
	width: 400px;
	float: left;
	padding-bottom: 50px;
}

.portlet {
	margin: 0 1em 1em 0;
}

.portlet-header {
	margin: 0.3em;
	padding-bottom: 4px;
	padding-left: 0.2em;
}

.portlet-header .ui-icon {
	float: right;
}

.portlet-content {
	padding: 0.4em;
}

.ui-sortable-placeholder {
	border: 1px dotted black;
	visibility: visible !important;
	height: 50px !important;
}

.ui-sortable-placeholder * {
	visibility: hidden;
}
</style>
<script>
	$(function() {
		
		$(".column").sortable({
			connectWith : ".column"
		});

		$(".portlet").addClass(
				"ui-widget ui-widget-content ui-helper-clearfix ui-corner-all")
				.find(".portlet-header").addClass(
						"ui-widget-header ui-corner-all").prepend(
						"<span class='ui-icon ui-icon-minusthick'></span>")
				.end().find(".portlet-content");

		$(".portlet-header .ui-icon").click(
				function() {
					$(this).toggleClass("ui-icon-minusthick").toggleClass(
							"ui-icon-plusthick");
					$(this).parents(".portlet:first").find(".portlet-content")
							.toggle();
				});

		$(".column").disableSelection();
		
		
		
	 $("#forecastForm").validationEngine();
	
	
	});
</script>


<div class="demo">
	
	<div class="column">
		<div class="portlet">
			<div class="portlet-header"><spring:message code="ui.exp.financialStatus.portlet.income.title" /></div>
			<div class="portlet-content">
				<jsp:include page="common/forecasts_inc.jsp">
					<jsp:param name="forecastType" value="Income"/>
				</jsp:include>
			</div>
		</div>
	</div>

	<div class="column">
		<div class="portlet">
			<div class="portlet-header"><spring:message code="ui.exp.financialStatus.portlet.expense.title" /></div>
			<div class="portlet-content">
				<jsp:include page="common/forecasts_inc.jsp">
					<jsp:param name="forecastType" value="Expense"/>
				</jsp:include>
			</div>
		</div>
	</div>
	<spring:message code="ui.exp.financialStatus.status" /> ${totalIncomeForecast-totalExpenseForecast}
</div>
