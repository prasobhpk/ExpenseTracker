<%@ include file="../common/include.jsp"%>
<script type="text/javascript">
$(document).ready(function() 
{
	 $("#form_brokerage").validationEngine();		
});
</script>
<form:form id="form_brokerage" method="post"  modelAttribute="brokerage">
	<table class="ui-widget ui-widget-content ui-corner-all input">
							<tr>
								<td>Broker Name :</td>
								<td>
									<form:input path="institution" id="txt_broker" cssClass="validate[required] " />
								</td>
							</tr>
							<tr>
								<td>Brokerage(%) :</td>
								<td>
									<form:input path="brokerage" id="txt_fee" cssClass="validate[required,custom[number]] " />
								</td>
							</tr>
							<tr>
								<td>Min Brokerage(Rs.) :</td>
								<td>
									<form:input path="minBrokerage" id="txt_min_fee" cssClass="validate[required,custom[number]] " />
								</td>
							</tr>
							<tr>
								<td>Service Tax(%) :</td>
								<td>
									<form:input path="serivceTax" id="txt_service_tax" cssClass="validate[required,custom[number]] " />
								</td>
							</tr>
							
							<tr>
								<td>Transaction Tax(%) :</td>
								<td>
									<form:input path="transactionTax" id="txt_txn_tax" cssClass="validate[required,custom[number]] " />
								</td>
							</tr>
							
							<tr>
								<td>Other charges(%) :</td>
								<td>
									<form:input path="otherCharges" id="txt_other_chrgs" cssClass="validate[required,custom[number]] " />
								</td>
							</tr>
							
							
							
							<tr>
								<td></td>
								<td>
									<input type="submit" value="Submit" id="btn_brokerag" class="btn ui-corner-all" />
								</td>
							</tr>
	</table>
</form:form>