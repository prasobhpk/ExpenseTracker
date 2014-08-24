<%@ include file="../common/include.jsp" %>
<script type="text/javascript">
$(document).ready(function() 
{
	 $("#portfolioForm").validationEngine();		
});

</script>
<form:form id="portfolioForm" method="post"  modelAttribute="portfolio">
	<table class="ui-widget ui-widget-content ui-corner-all input">
							<tr>
								<td>Portfolio Name:</td>
								<td>
									<form:input path="portfolioName" id="txt_portfolio_name" cassCalass="validate[required]"/>
								</td>
							</tr>
							<tr>
								<td></td>
								<td colspan="2">
									<input type="submit" value="Submit" id="btn_portfolio_sbmt" class="btn ui-corner-all" />
								</td>
							</tr>
	</table>
</form:form>