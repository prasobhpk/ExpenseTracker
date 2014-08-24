<%@ include file="../common/include.jsp" %>
<script type="text/javascript">
var holding=null;
var jsonStock=null;
var dec=2;
$(document).ready(function() 
{
	 $("#equityTxnForm").validationEngine();		
	 $("#frm_avg_sim").validationEngine();
	 var dtOptions={
				inline: true,
				dateFormat:"yy-mm-dd",
				showOn: 'button', 
				buttonImage: '<%=application.getContextPath()%>/resources/css/redmond/images/calendar.gif',
				buttonImageOnly: true,
				maxDate: +0,
				changeMonth: true,
				changeYear: true
				//showOn:'focus'
	 };
	 $('#txt_txn_date').val('${now}');
	 $('#txt_txn_date').datepicker(dtOptions);
	 
	if('${jsonStockDtls}'!=''){
		 jsonStock=$.parseJSON('${jsonStockDtls}');
		 if(jsonStock.length){
			 jsonStock=jsonStock[1][0];
			/*$.each(jsonStock,function(key,val){
				alert(key);
			});*/
			 $("#txt_txn_price").val(jsonStock.LastTradedPrice);
		 }
	} 
	$('#lst_portfolio').change(function(){
		clearDerivedFields();
		if($(this).val()!=""){
			$.getJSON(
					'<c:url value="/rest/wm/portfolio/holdingDetails"/>',
					{folioId:$(this).val(),equityId:$('#hdn_instrument_id').val()},
					function(data){
						if(data){
							holding=data;
							$("#lnk_cal_avg").css('visibility','visible');
							//$('#txt_txn_qty').val(holding.quantity);
						}
					}
			);
		}
		
	}); 
	
	$('#lst_txn_typ').change(function(){
		clearDerivedFields();
		$('#lst_broker_name').attr("selectedIndex",0);
		if($(this).val()=='SELL'){
			$("#lbl_txt_profit").css('visibility','visible');
			$("#txt_profit").css('visibility','visible');
			$('#txt_txn_qty').val(holding.quantity);
		}else{
			$("#txt_profit").css('visibility','hidden');
			$("#lbl_txt_profit").css('visibility','hidden');
		}
	}); 
	
	$('#txt_txn_qty').change(function(){
		if($(this).val()>holding.quantity){
			$('#txt_txn_qty').validationEngine('showPrompt', 'Quantity shoud not be grater than avilable quantity '+holding.quantity, 'error', true);
		}
	});
	 
	 $("#txt_txn_qty,#txt_txn_price").change(function(){
		 if($('#lst_broker_name').attr("selectedIndex")>0){
			 $("#txt_txn_fee").val(calcFee($("#txt_txn_qty").val(),$("#txt_txn_price").val()));
		 }
		 calcTotal();
	 });
	 
	 $('#txt_txn_fee').change(function(){
		 calcTotal();
	 });
	 //validate on broker change
	 $('#lst_broker_name').change(function(){
		 if($(this).val()!=""){
			 //alert($("#lst_broker_name").validationEngine('validateField', '#txt_txn_qty'));
			 //alert($("#lst_broker_name").validationEngine('validateField', '#txt_txn_price'));
			if($("#lst_broker_name").validationEngine('validateField', '#txt_txn_qty') && $("#lst_broker_name").validationEngine('validateField', '#txt_txn_price')){
				//validation failed
			}else{
				//validation success
				$("#txt_txn_fee").val(calcFee($("#txt_txn_qty").val(),$("#txt_txn_price").val()));
				calcTotal();
			}
		 }else{
			 clearDerivedFields();
			 calcTotal();
		 }
	 });
	 $('#lnk_cal_avg').click(function(){
	 	 $('#avg_dialog').css('visibility','visible');
		 $('#avg_dialog').dialog( "open" );
	 });
	 $('#avg_dialog').dialog({
			autoOpen: false,
			resizable: false,
			show: 'slide',
			width: 550,
			height:280,
			bgiframe: true,			
	        modal: true,
			open: function(event, ui) {
				if(holding){
					$("#txt_avg_qty_avail").html(holding.quantity);
					$("#txt_avg_total").html(holding.amount);
					$("#txt_avg_price").html(holding.price);
					$("#txt_sim_price").val($("#txt_txn_price").val());
				}
			},
			close:function(event,ui){
				//there is no reset() method on a jquery object.So we need to use the native approach
				document.getElementById('frm_avg_sim').reset();
			},
			buttons: {
				"Ok": function() { 
					$(this).dialog("close"); 
				}
				
			}
	 });	
	 
	 $("#txt_sim_qty,#txt_sim_price").change(function(){
		if((!$("#txt_sim_qty").validationEngine('validateField', '#txt_sim_qty') && !$("#txt_sim_price").validationEngine('validateField', '#txt_sim_price'))){
			var amt=parseFloat($("#txt_sim_qty").val()*$("#txt_sim_price").val());
			var avgPrice=(holding.amount+amt)/(holding.quantity+parseFloat($("#txt_sim_qty").val()));
			$("#txt_sim_result").html(avgPrice.toFixed(2));
			$("#txt_sim_cost").html(amt.toFixed(2));
		}
		
	});
});
function calcTotal(){
	var qty=0;
	var price=0;
	var fee=0;
	var other=0
	var total=0;
	if($("#txt_txn_qty").val()!=''){
		qty=parseFloat($("#txt_txn_qty").val());
	}
	if($("#txt_txn_price").val()!=''){
		price=parseFloat($("#txt_txn_price").val());
	}
	if($("#txt_txn_fee").val()!=''){
		fee=parseFloat($("#txt_txn_fee").val());
	}
	/*if($("#txt_txn_other").val()!=''){
		other=parseFloat($("#txt_txn_other").val());
	}*/
	var amount=qty*price;
	if(amount>0){
		total=amount+fee+other;
		total=total.toFixed(dec);
		$("#txt_txn_total").val(total);
		calcProfit(amount,fee);
	}
	return total;
}
function calcFee(qty,price){
	var index=$('#lst_broker_name').attr("selectedIndex")-1;
	var total=parseFloat(qty!=''?qty:0)*parseFloat(price!=''?price:0);
	var totalFee=0;
	if(total!=0 && !$("#lst_broker_name").validationEngine('validateField', '#lst_broker_name')){
		var brokerage=parseFloat($("#hdn_fee_"+index).attr('brokerage'));
		var minBrokerage=parseFloat($("#hdn_fee_"+index).attr('minBrokerage'));
		var serivceTax=parseFloat($("#hdn_fee_"+index).attr('serivceTax'));
		var transactionTax=parseFloat($("#hdn_fee_"+index).attr('transactionTax'));
		var otherCharges=parseFloat($("#hdn_fee_"+index).attr('otherCharges'));
		if(total*(brokerage/100)<minBrokerage){
			totalFee=minBrokerage;
		}else{
			totalFee=total*(brokerage/100);
		}
		totalFee+=totalFee*(serivceTax/100);
		totalFee+=total*(transactionTax/100);
		totalFee+=otherCharges;
		totalFee=totalFee.toFixed(dec);
	}
	return totalFee;
}
function calcProfit(amount,fee){
	if($('#lst_txn_typ').val()=='SELL' && holding!=null){
		//profit=mktValue-((holdingPrice*sellQty+feeOfThis)+brokerage)
		var mktValu=amount;
		var hldPriceVal=parseFloat($("#txt_txn_qty").val())*holding.price;
		var feeOnHldPriceVal=calcFee($("#txt_txn_qty").val(), holding.price);
		var profit=amount-((hldPriceVal+parseFloat(feeOnHldPriceVal))+fee);
		profit=profit.toFixed(dec);
		$("#txt_profit").val(profit);
	}
}
function clearDerivedFields(){
	$("#txt_txn_fee").val('');
	$("#txt_txn_total").val('');
	$("#txt_profit").val('');
}
</script>
<form:form id="equityTxnForm" method="post"  modelAttribute="txnDetails">
	<table class="ui-widget ui-widget-content ui-corner-all input">
							<tr>
								<td>Portfolio :</td>
								<td colspan="3">
									<form:select path="portfolio.id"  cssClass="validate[required]" id="lst_portfolio">
										<form:option value="">--Select--</form:option>
										<form:options items="${portfolios}" itemLabel="portfolioName" itemValue="id"/>
									</form:select>
								</td>
							</tr>
							<tr>
								<td>Instrument Name:</td>
								<td colspan="3">
									<input type="text" disabled="disabled" id="txt_instrument_name" value="${stock.name}" size="50">
									<a href="#" id="lnk_cal_avg" style="visibility:hidden;">AVG</a>
									<form:hidden path="instrument.id" id="hdn_instrument_id" value="${stock.id}"/>
								</td>
							</tr>
							<tr>
								<td>Exchange :</td>
								<td colspan="3">
									<form:select path="exchange"  cssClass="validate[required]" id="lst_txn_exchange">
										<form:option value="">--Select--</form:option>
										<form:option value="NSE">NSE</form:option>
										<form:option value="BSE">BSE</form:option>
<%-- 										<form:options items="${expTypes}" itemLabel="type" itemValue="id"/> --%>
									</form:select>
								</td>
							</tr>
							<tr>
								<td>Transaction Type :</td>
								<td>
									<form:select path="type"  cssClass="validate[required]" id="lst_txn_typ">
										<form:option value="">--Select--</form:option>
										<form:option value="BUY">BUY</form:option>
										<form:option value="SELL">SELL</form:option>
<%-- 										<form:options items="${expTypes}" itemLabel="type" itemValue="id"/> --%>
									</form:select>
								</td>

								<td>Transaction Date :</td>
								<td>
									<form:input path="tradeDate" id="txt_txn_date" cssClass="validate[required,custom[date]] text" />
								</td>
							</tr>
							<tr>
								<td>Quantity:</td>
								<td>
									<form:input path="quantity" id="txt_txn_qty" cssClass="validate[required,custom[integer]]"/>
								</td>
								<td>Price:</td>
								<td>
									<form:input path="price" id="txt_txn_price" cssClass="validate[required,custom[number]]"/>
								</td>
							</tr>
							
							<tr>
								<td>Broker Name: </td>
								<td>
									<form:select path="brokerageStructure.id"  cssClass="validate[required]" id="lst_broker_name">
										<form:option value="">--Select--</form:option>
										<form:options items="${brokList}" itemLabel="institution" itemValue="id" />
									</form:select>
									<c:forEach items="${brokList}" varStatus="status" var="item">
										<input type="hidden" id="hdn_fee_${status.index}" brokerage="${item.brokerage}" minBrokerage="${item.minBrokerage}" serivceTax="${item.serivceTax}" transactionTax="${item.transactionTax}" otherCharges="${item.otherCharges}">
									</c:forEach>
								</td>
								<td>Brokerage:</td>
								<td>
									<form:input path="brokerage" id="txt_txn_fee" cssClass="validate[required,custom[number]]"/>
								</td>
								
							</tr>
<!-- 							<tr> -->
<!-- 								<td>Other Charges:</td> -->
<!-- 								<td colspan="3"> -->
<%-- 									<form:input path="otherCharges" id="txt_txn_other"cssClass="validate[custom[number]]" /> --%>
<!-- 								</td> -->
<!-- 							</tr> -->
							<tr>
								<td>Total Amount:</td>
								<td>
									<form:input path="totalAmount" id="txt_txn_total" cassCalass="validate[custom[number]]" readonly="true"/>
								</td>
								<td><label id="lbl_txt_profit" for="txt_profit" style="visibility: hidden;">Profit:</label></td>
								<td>
									<form:input path="profit" readonly="true"  id="txt_profit" style="visibility: hidden;"/>
								</td>
							</tr>
							<tr>
								<td></td>
								<td colspan="3">
									<input type="submit" value="Submit" id="btn_eq_sbmt" class="btn ui-corner-all" />
								</td>
							</tr>
	</table>
</form:form>
<div id="avg_dialog" style="visibility: hidden;" title="${stock.name}">
	<form id="frm_avg_sim">
	<table style="height: 100%;">
		
		<tr>
			<td colspan="6"><b>Holding Details :</b></td>
		</tr>
		<tr>
			<td>Qty :</td>
			<td>
				<span id="txt_avg_qty_avail"></span>
			</td>
			<td>Total :</td>
			<td>
				<span id="txt_avg_total"></span>
			</td>
			<td>Avg Price :</td>
			<td>
				<span id="txt_avg_price"></span>
			</td>
		</tr>
		<tr>
			<td colspan="6"><b>If I Buy :</b><td>
		</tr>
		<tr>
			<td>Qty :</td>
			<td>
				<input type="text" id="txt_sim_qty" class="validate[required,custom[number]]">
			</td>
			<td>At Price :</td>
			<td colspan="3">
				<input type="text" id="txt_sim_price" class="validate[required,custom[number]]">
			</td>
		</tr>
		<tr>
			<td colspan="6"><b>Then :</b><td>
		</tr>
		<tr>
			<td>Avg Price :</td>
			<td colspan="2">
				<span id="txt_sim_result"></span>
			</td>
			<td>Total Cost :</td>
			<td colspan="2">
				<span id="txt_sim_cost"></span>
			</td>
		</tr>
	</table>
	</form>
</div>