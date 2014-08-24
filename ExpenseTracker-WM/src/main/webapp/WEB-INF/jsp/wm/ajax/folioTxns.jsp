<%@ include file="../../common/include.jsp"%>
<script>
$().ready(function() {
	tableToGrid("#tbl_folio_txns");
});
</script>
<table id="tbl_folio_txns">
	<thead>
		<tr>
			<th>Instrument Name</th>
			<th>Quantity</th>
			<th>Avg. Price</th>
			<th>Total</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="txn" items="${txns}" varStatus="status">
			<tr>
				<td>${txn.instrument.name}</td>
				<td><wm:fmtUnit value="${txn.quantity}" groupingUsed="true" trimUsed="true"/></td>
				<td><wm:fmtPrice value="${txn.amount/txn.quantity}" groupingUsed="true" trimUsed="true"/></td>
				<td><wm:fmtAmount value="${txn.totalAmount}" groupingUsed="true" onlyRound="false"/></td>
			</tr>
		</c:forEach>

	</tbody>
</table>