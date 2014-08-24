<%@ include file="../common/include.jsp"%>
<meta charset="utf-8">
<style>
.column {
	width: 300px;
	float: left;
	padding-bottom: 100px;
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
		
		$('.chk_config').change(function(){
			$.post('<c:url value="/settings/updateuserAppSettings"/>',
					{configKey:$(this).val(),configValue:$(this).is(':checked')},
					function(data)
					{
						if(data.status=='fail'){
							alert(data.status);
						}
					},
					"json"
			);
		});
		
		
	});
</script>


<div class="demo">

	<div class="column">

		<div class="portlet">
			<div class="portlet-header">Holdings Summary</div>
			<div class="portlet-content">
				<table class="ui-widget ui-widget-content ui-corner-all panel_input">
					<tr>
						<td>Show actual amount  :</td>
						<td><input type="checkbox" 
								class="chk_config" ${config.SHOW_TOTAL_WITH_FEE_SUMMARY==true?'checked=\"checked\"':''}/ value="SHOW_TOTAL_WITH_FEE_SUMMARY"></td>
					</tr>
							
				</table>
			</div>
		</div>

		<div class="portlet">
			<div class="portlet-header">Holding Details</div>
			<div class="portlet-content">
				<table class="ui-widget ui-widget-content ui-corner-all panel_input">
					<tr>
						<td>Show actual amount :</td>
						<td><input type="checkbox"  
								class="chk_config" ${config.SHOW_TOTAL_WITH_FEE_DETAILS==true?'checked=\"checked\"':''} value="SHOW_TOTAL_WITH_FEE_DETAILS"/></td>
					</tr>
					<tr>
						<td>Show realized profit :</td>
						<td><input type="checkbox"  
								class="chk_config" ${config.SHOW_REALIZED_PROFIT_DETAILS==true?'checked=\"checked\"':''} value="SHOW_REALIZED_PROFIT_DETAILS"/></td>
					</tr>
				</table>
			</div>
		</div>

	</div>

	<div class="column">

		<div class="portlet">
			<div class="portlet-header">Performance Dashboard</div>
			<div class="portlet-content">
				<table class="ui-widget ui-widget-content ui-corner-all panel_input">
					<tr>
						<td>Show actual profit  :</td>
						<td><input type="checkbox"  
								class="chk_config" ${config.SHOW_PROFIT_AFTER_BROKERAGE_PERFORMANCE==true?'checked=\"checked\"':''} value="SHOW_PROFIT_AFTER_BROKERAGE_PERFORMANCE"/></td>
					</tr>
				</table>
			</div>
		</div>

	</div>

	<div class="column">

		<div class="portlet">
			<div class="portlet-header">Links</div>
			<div class="portlet-content">Lorem ipsum dolor sit amet,
				consectetuer adipiscing elit</div>
		</div>

		<div class="portlet">
			<div class="portlet-header">Images</div>
			<div class="portlet-content">Lorem ipsum dolor sit amet,
				consectetuer adipiscing elit</div>
		</div>

	</div>

</div>
<!-- End demo -->

