<%@ include file="../../common/include.jsp"%>
<script>
$().ready(function() {
	tableToGrid("#tbl_folio_txns");
});
</script>
<table id="tbl_folio_txns" >
	<thead>
		<tr>
			<th>Instrument Name</th>
			<th>Quantity</th>
			<th>Average Cost Price</th>
			<th>Current Market Price</th>
			<th>Value at Average Cost Price</th>
			<th>Value at Current Market Price</th>
			<th>Unrealized Profit/Loss</th>
			<c:if test="${sessionScope.USER_CONF.SHOW_REALIZED_PROFIT_DETAILS}">
				<th>Realized Profit/Loss</th>
			</c:if>
		</tr>
	</thead>
	<tbody>
		<c:set var="mktValTotal" value="${0.00}"/>
		<c:set var="curMktValTotal" value="${0.00}"/>
		<c:set var="uPLTotal" value="${0.00}"/>
		<c:set var="rPLTotal" value="${0.00}"/>
		<c:forEach var="holding" items="${holdings}" varStatus="status">
			<c:if test="${holding.quantity>0}">
				<wm:fmtPrice value="${holding.quantity}" groupingUsed="true" trimUsed="true" var="qty"/>
				
				<c:set var="up" value="up"/>
				<c:set var="down" value="down"/>
				<c:set var="neutral" value="neutral"/>
				
				<c:set var="mktVal" value="${holding.amount}"/>
				<c:set var="curMktVal" value="${holding.quantity*priceMap[holding.instrument.symbol]}"/>
				<c:set var="mktVal_cls" value="${curMktVal>mktVal?up:(curMktVal<mktVal?down:neutral)}"/>
				
				<c:set var="profit" value="${curMktVal-mktVal}"/>
				<c:set var="profit_cls" value="${profit>0?up:(profit<0?down:neutral)}"/>
				
				<c:set var="mktValTotal" value="${mktValTotal+mktVal}"/>
				<c:set var="curMktValTotal" value="${curMktValTotal+curMktVal}"/>
				<c:set var="uPLTotal" value="${sessionScope.USER_CONF.SHOW_PROFIT_AFTER_BROKERAGE_PERFORMANCE?(uPLTotal+(profit-feeMap[holding.instrument.symbol])):(uPLTotal+profit)}"/>
				<c:set var="rPLTotal" value="${rPLTotal+holding.profit}"/>
				
				<c:set var="avgPrice" value="${holding.amount/holding.quantity}"/>
				<c:set var="mktPrice" value="${priceMap[holding.instrument.symbol]}"/>
				<c:set var="mktPrice_cls" value="${mktPrice>avgPrice?up:(mktPrice<avgPrice?down:neutral)}"/>
				
				
				<tr>
					<td><a href="<c:url value="/wm/equity?symbol=${holding.instrument.symbol}"/>">${holding.instrument.name}</a></td>
					<td><wm:fmtUnit value="${holding.quantity}" groupingUsed="true" trimUsed="true"/></td>
					<td>${avgPrice}</td>
					<td><span class="${mktPrice_cls}">${mktPrice}(<wm:fmtAmount value="${(mktPrice*100/avgPrice)-100}" groupingUsed="true" trimUsed="true"/>%)</span></td>
					<td><wm:fmtAmount value="${mktVal}" groupingUsed="true" trimUsed="true"/></td>
					<td><span class="${mktVal_cls}"><wm:fmtAmount value="${curMktVal}" groupingUsed="true" trimUsed="true"/></span></td>
					<td><span class="${profit_cls}"><wm:fmtAmount value="${sessionScope.USER_CONF.SHOW_PROFIT_AFTER_BROKERAGE_PERFORMANCE?(profit-feeMap[holding.instrument.symbol]):profit}" groupingUsed="true" trimUsed="true"/></span></td>
					<c:if test="${sessionScope.USER_CONF.SHOW_REALIZED_PROFIT_DETAILS}">
						<td><wm:fmtAmount value="${holding.profit}" groupingUsed="true" trimUsed="true"/></td>
					</c:if>
				</tr>
			</c:if>
		</c:forEach>
		<tr>
			<td>Total :</td>
			<td></td>
			<td></td>
			<td></td>
			<td><wm:fmtAmount value="${mktValTotal}" groupingUsed="true" trimUsed="true"/></td>
			<td><wm:fmtAmount value="${curMktValTotal}" groupingUsed="true" trimUsed="true"/></td>
			<td><wm:fmtAmount value="${uPLTotal}" groupingUsed="true" trimUsed="true"/></td>
			<c:if test="${sessionScope.USER_CONF.SHOW_REALIZED_PROFIT_DETAILS}">
				<td><wm:fmtAmount value="${rPLTotal}" groupingUsed="true" trimUsed="true"/></td>
			</c:if>
		</tr>

	</tbody>
</table>