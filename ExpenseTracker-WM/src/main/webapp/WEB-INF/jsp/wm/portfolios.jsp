<%@ include file="../common/include.jsp"%>
<script type="text/javascript">
$().ready(function() {
	$('#panel_foliosummary').panel({
		//'controls':$('#cntrl').html(),
		'collapsible':false,
		//collapseType:'slide-left',
		//draggable:true, 
		//trueVerticalText:true,
		//vHeight:'400px',
		width:'100%'
	});
	$('#panel_folio_details').panel({
		//'controls':$('#cntrl').html(),
		'collapsible':false,
		//collapseType:'slide-left',
		//draggable:true, 
		//trueVerticalText:true,
		//vHeight:'200px',
		'controls':'<span class="ui-icon ui-icon-calculator qTip" title="Show Dashboard" id="icon_show_details"></span>',
		width:'100%'
	});
	
	$('#panel_folio_performance').panel({
		//'controls':$('#cntrl').html(),
		'collapsible':false,
		//collapseType:'slide-left',
		//draggable:true, 
		//trueVerticalText:true,
		//vHeight:'200px',
		width:'100%'
	});
	tableToGrid("#tbl_portfolios");
	
	$("#btn_pf_add").click(function(){
		location.href ='<c:url value="/wm/portfolio"/>';
	});
	
	$("#btn_pf_view").click(function(){
		if(countChecked()){
			$("#folio_details").load('<c:url value="/wm/folioHoldings?folioId="/>'+$("input:radio[name=rd_portfolio]:checked").val());
		}else{
			alert('Please select a portfolio');
		}
		//$('#panel_folio_details').panel('controls',$('#cntrl').html());
	});
	
	$("#btn_pf_compare").click(function(){
		if(countChecked()){
			$("#folio_performance").load('<c:url value="/wm/folioPerformanceDB?folioId="/>'+$("input:radio[name=rd_portfolio]:checked").val());
		}else{
			alert('Please select a portfolio');
		}
	});
	
	var isShowingDB=false;
	$("#icon_show_details").click(function(){
		if(countChecked()){
			if(isShowingDB){
				$(this).attr('title','Show Dashoard');
				//$(this).attr('class','icon-dashboard');
				$("#folio_details").load('<c:url value="/wm/folioHoldings?folioId="/>'+$("input:radio[name=rd_portfolio]:checked").val());
				isShowingDB=false;
			}else{
				$(this).attr('title','Show Grid');
				//$(this).attr('class','ui-icon ui-icon-calculator');
				$("#folio_details").load('<c:url value="/wm/folioSummaryDB?folioId="/>'+$("input:radio[name=rd_portfolio]:checked").val());
				isShowingDB=true;
			}
		}else{
			alert('Please select a portfolio');
		}
	});
	
});
function countChecked() {
	 return $("input:radio[name=rd_portfolio]").is(':checked');
	 // var n = $("input:checked").length;
	 // $("div").text(n + (n <= 1 ? " is" : " are") + " checked!");
}

</script>
<style>
.btn_group {
	margin: 0px 0px 0px 100px;
	white-space: nowrap;
}

.btn_group input[type="button"] {
	margin: 0px 20px 0px 0px;
}

#panel_container {
	padding: 1em;
	overflow: hidden;
}

#lLeft {
	float: left;
	width: 44%;
}

#lRight {
	float: right;
	width: 54%;
}
#panel_bottom{
	padding-top: 1em;
	width: 100%;
}
.top_panel_content{
min-height: 205px;
overflow: auto;
}
.up{
color: green;
}
.down{
color: red;
}
.neutral{
color:white ;
}
</style>
<div id="panel_container">
	<div id="lLeft">
		<div id="panel_foliosummary" class="panel">
			<h3>Portfolio Summary</h3>
			<div>
				<div class="top_panel_content">
				<c:choose>
					<c:when test="${not empty summaryMap}">
							<table id="tbl_portfolios">
								<thead>
									<tr>
										<th></th>
										<th></th>
										<th>Portfolio</th>
										<th>Total</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="map" items="${summaryMap}" varStatus="status">
										<tr>
											<td></td>
											<td><input type="radio" name="rd_portfolio" value="${map.key.id}" class="rd_portfolio"></td>
											<td>${map.key.portfolioName}</td>
											<td><wm:fmtAmount value="${map.value}" groupingUsed="true" onlyRound="false" trimUsed="true"/></td>
										</tr>
									</c:forEach>
									
								</tbody>
							</table>
							<div class="btn_group" >
								<input type="button" value="View" id="btn_pf_view" class="btn ui-corner-all" />
								<input type="button" value="Add" id="btn_pf_add" class="btn ui-corner-all">
								<input type="button" value="Performance" id="btn_pf_compare" class="btn ui-corner-all" />
							</div>
				<!-- 		<div style="margin:.5em 0 0 1em;color:navy;cursor:pointer;" onClick="$('#panel4').panel('content', $('#fake_container').html());">Change this content.</div> -->
					</c:when>
					<c:otherwise>
						No Data to display
					</c:otherwise>
				</c:choose>
				</div>
			</div>
		</div>
	</div>
	<div id="lRight">
		<div id="panel_folio_details" class="panel">
			<h3>Portfolio Details</h3>
			<div>
				<div id="folio_details" class="top_panel_content">
					Select one portfolio and click view button
				</div>
			</div>
		</div>
	</div>
	<div style="clear:both"></div>
	<div id="panel_bottom">
		<div id="panel_folio_performance" class="panel">
			<h3>Portfolio Performance</h3>
			<div>
				<div id="folio_performance">
					Select one portfolio and click performance button
				</div>
			</div>
		</div>
	</div>
</div>
