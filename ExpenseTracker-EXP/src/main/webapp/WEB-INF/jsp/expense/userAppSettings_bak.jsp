<%@ include file="../common/include.jsp"%>
<script type="text/javascript">
	$().ready(function() {
		$('#panel_folio_config').panel({
			//'controls':$('#cntrl').html(),
			'collapsible' : false,
			//collapseType:'slide-left',
			//draggable:true, 
			//trueVerticalText:true,
			//vHeight:'400px',
			width : '400px'
		});
		$('#panel_menu_config').panel({
			//'controls':$('#cntrl').html(),
			'collapsible' : false,
			//collapseType:'slide-left',
			//draggable:true, 
			//trueVerticalText:true,
			//vHeight:'200px',
			width : '500px'
		});

		$('#panel_folio_performance').panel({
			//'controls':$('#cntrl').html(),
			'collapsible' : false,
			//collapseType:'slide-left',
			//draggable:true, 
			//trueVerticalText:true,
			//vHeight:'200px',
			width : '950px'
		});
		
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
		$( ".column" ).sortable({
			connectWith: ".column"
		});

	});
</script>
<style>
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

#panel_bottom {
	padding-top: 1em;
	width: 100%;
}

.top_panel_content {
	min-height: 205px;
	overflow: auto;
}
</style>
<div id="panel_container" >
	<div id="lLeft" class="column">
		<div id="panel_folio_config" class="panel">
			<h3>Portfolio Configuration</h3>
			<div>
				<div class="top_panel_content">
						<table class="ui-widget ui-widget-content ui-corner-all panel_input">
							<tr>
								<td>Show actual amount in summary :</td>
								<td><input type="checkbox" 
										class="chk_config" ${config.SHOW_TOTAL_WITH_FEE_SUMMARY==true?'checked=\"checked\"':''}/ value="SHOW_TOTAL_WITH_FEE_SUMMARY"></td>
							</tr>
							<tr>
								<td>Show actual amount in details :</td>
								<td><input type="checkbox"  
										class="chk_config" ${config.SHOW_TOTAL_WITH_FEE_DETAILS==true?'checked=\"checked\"':''} value="SHOW_TOTAL_WITH_FEE_DETAILS"/></td>
							</tr>
							<tr>
								<td>Show actual profit in Performance :</td>
								<td><input type="checkbox"  
										class="chk_config" ${config.SHOW_PROFIT_AFTER_BROKERAGE_PERFORMANCE==true?'checked=\"checked\"':''} value="SHOW_PROFIT_AFTER_BROKERAGE_PERFORMANCE"/></td>
							</tr>
							<tr>
								<td>Show realized profit in Details :</td>
								<td><input type="checkbox"  
										class="chk_config" ${config.SHOW_REALIZED_PROFIT_DETAILS==true?'checked=\"checked\"':''} value="SHOW_REALIZED_PROFIT_DETAILS"/></td>
							</tr>
						</table>
				</div>
			</div>
		</div>
	</div>
	<div id="lRight" class="column">
		<div id="panel_menu_config" class="panel">
			<h3>Menu Configurations</h3>
			<div>
				<div class="top_panel_content"></div>
			</div>
		</div>
	</div>
	<div style="clear: both"></div>
	<div id="panel_bottom">
		<div id="panel_folio_performance" class="panel">
			<h3>Portfolio Performance</h3>
			<div>
				<div></div>
			</div>
		</div>
	</div>
</div>
