<%@ include file="../common/include.jsp"%>

<script>
$(function() {
	var dates = $( "#from, #to" ).datepicker({
		defaultDate: "+1w",
		changeMonth: true,
		numberOfMonths: 1,
		inline: true,
		//dateFormat:'<c:out  value="${dateFormat}"/>',
		dateFormat:'dd-mm-yy',
		showOn: 'button', 
		buttonImage: '<c:url value="/resources/css/redmond/images/calendar.gif"/>',
		buttonImageOnly: true,
		maxDate: +0,
		changeYear: true,
		yearRange:'<c:out  value="${yearRange}"/>',
		onSelect: function( selectedDate ) {
			var option = this.id == "from" ? "minDate" : "maxDate",
				instance = $( this ).data( "datepicker" );
				date = $.datepicker.parseDate(
					instance.settings.dateFormat ||
					$.datepicker._defaults.dateFormat,
					selectedDate, instance.settings );
			dates.not( this ).datepicker( "option", option, date );
		}
	});
});

</script>
	<form action="<c:url value="/exp/download"/>" method="post" style="margin: 30px 30px;">
		<table align="center">
			<tr>
				<td width="20%">From :</td>
				<td width="50%">
					<input type="text" id="from" name="from"/>
				</td>
				<td width="30%"></td>
			</tr>
			<tr>
				<td>To :</td>
				<td width="30%">
					<input type="text" id="to"	name="to"/>
				</td>
				<td width="30%"></td>
			</tr>
			
			<tr>
				<td></td>
				<td><input type="submit" value="Export" id="btn_add_exp"
					class="btn ui-corner-all" /></td>
			</tr>
		</table>
	</form>
