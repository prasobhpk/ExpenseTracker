<%@ include file="../../common/include.jsp"%>
<script>
	$().ready(function() {
		tableToGrid("#tbl_to_grid_${param.forecastType}");
	});
</script>
<div style="height: 100%; width: 100%;">
	<table id="tbl_to_grid_${param.forecastType}">
		<thead>
			<tr>
				<th><spring:message code="ui.exp.financialStatus.portlet.title" /></th>
				<th><spring:message code="ui.exp.financialStatus.portlet.amount" /></th>
			</tr>
		</thead>
		<tbody>

			<c:set var="totalForecast" value="${0.00}" />
			<c:set var="monthlyForecasts"
				value="${param.forecastType eq 'Income'?incomeForecasts:expenseForecasts}" />

			<c:forEach items="${monthlyForecasts}" var="forecast">
				<c:set var="totalForecast"
					value="${totalForecast+forecast.forecastAmount}" />
				<tr>
					<td><a href="<c:url value="/exp/forecast/${forecast.id}"/>">${forecast.title}</a></td>
					<td><wm:fmtAmount value="${forecast.forecastAmount}"
							groupingUsed="true" trimUsed="true" /></td>
				</tr>
			</c:forEach>
			<c:choose>
				<c:when test="${param.forecastType eq 'Income'}">
					<c:set var="totalIncomeForecast" value="${totalForecast}"
						scope="request" />
				</c:when>
				<c:otherwise>
					<c:set var="totalExpenseForecast" value="${totalForecast}"
						scope="request" />
				</c:otherwise>
			</c:choose>

			<tr>
				<td><b><spring:message code="ui.exp.financialStatus.portlet.total" /></b></td>
				<td><b><wm:fmtAmount value="${totalForecast}"
							groupingUsed="true" trimUsed="true" /></b></td>
			</tr>
		</tbody>
	</table>
</div>