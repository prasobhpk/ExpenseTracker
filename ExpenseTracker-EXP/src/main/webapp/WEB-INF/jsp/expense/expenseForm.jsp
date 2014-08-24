<%@ include file="../common/include.jsp"%>

<script>
$(document).ready(function(){
	// Use a whitelist of fields to minimize unintended side effects.
	$(':text, :password, :file', '#expForm').val('');  
	// De-select any checkboxes, radios and drop-down menus
	$(':input', '#expForm').removeAttr('checked').removeAttr('selected');

	 //Apply validation
	 $("#expForm").validationEngine();
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
	$('input.txt_expDate').val('${now}');	
	//Do not delete
	/*$('input.txt_expDate').live('click', function() {
		
		$(this).datepicker(dtOptions).focus();
	});*/
	
	$('.expTypes').live('change',function(){
		 var val = $(this).children('option:selected').text();
		 var target=$(this).parent('td').next().children("input.txt_description");
		 if(val=='--Select--'){
			 target.val('');
		 }else{
			 target.val(val);
		 }
	});

	
	applyDynamic();
	
	/*$.getJSON('<c:url value="/settings/expenseType/list.json"/>', function(data) {
		var opts="";
		$.each(data, function(key,expType) { 
			  opts+='<option value="'+expType.id+'">'+expType.type+'</option>';
		});
		$('#expTypes').append(opts);
	});*/
	var position = 0;
	$(".add-last").live('click',function() {
		position++;
		
		var amountId='expenses'+position+'.expense';
		var typeId='expenses'+position+'.typeId';
		var descId='expenses'+position+'.description';
		var dtId='expenses'+position+'.expDate';
		
		var row='<tr>';
		row+='<td><input name="expenses['+position+'].expense" id="'+amountId+'" type="text" class="validate[required,custom[integer]] text  ui-corner-all txt_expense" value="<c:out value="${expCommand.expenses[0].expense}"/>"/></td>';
		
		row+='<td>';
		row+='<select name="expenses['+position+'].typeId" id="'+typeId+'" class="validate[required] expTypes">';
		row+='<option value="">--Select--</option>';
        <c:forEach var="typ" items="${expTypes}" varStatus="idx">
		row+='<option value="${typ.id}">${typ.type}</option>';
		</c:forEach>
		row+='</td>';
		
		row+='<td><input name="expenses['+position+'].description" id="'+descId+'" type="text" class="validate[required] text  ui-corner-all txt_description" /></td>';
		row+='<td><input name="expenses['+position+'].expDate" id="'+dtId+'" type="text" class="validate[required,custom[date]] text  ui-corner-all txt_expDate" value="${now}"/></td>';
		row+='<td><div class="delete-last ui-state-default ui-corner-all"><span class="ui-icon ui-icon-minus"></span></div>';
		row+='<div class="add-last ui-state-default ui-corner-all"><span class="ui-icon ui-icon-plus"></span></div></td>';
		$("#tbl_exps").append(row);
		applyDynamic();
	});
	function applyDynamic(){
		 //bind date picker
		 $('.txt_expDate').datepicker(dtOptions);
		
		
		 //Style table
		 $(".jtable thead").each(function(){
			  $(this).addClass("ui-widget-header ui-corner-all");
		  });
		  $(".jtable tr").each(function(){
		  	$(this).attr('align','center')
		  });		 
		 $(".jtable tr td").each(function(){
		  	$(this).addClass("ui-widget-content");
		  });
		  
		$('.delete-last,.add-last').hover(
			function(){
				 $(this).addClass("ui-state-hover");
			},
			function(){
				$(this).removeClass("ui-state-hover");
			}
		
		);
		  /*
		 
		  $(".jtable tr").hover(
		     function()
		     {
		      $(this).children("td").addClass("ui-state-hover");
		     },
		     function()
		     {
		      $(this).children("td").removeClass("ui-state-hover");
		     }
		    );
		 $(".jtable tr").click(function(){
		   
		   $(this).children("td").toggleClass("ui-state-highlight");
		  });
		 */
	}
	
	$('.delete-last').live('click',function(){
		if($('.jtable tr').length!=2){
			$(this).closest('tr').remove();
		}/*else{
			alert("You can't delete the first row");
		}*/
	});
});
</script>
<style>
.delete-last,.add-last{
float:left;
cursor:pointer;
cursor:hand;
margin:3px;
}

</style>
<form:form id="expForm"
	method="post" modelAttribute="expCommand" style="margin: 30px 30px;" class="jtable">
	<table align="center" id="tbl_exps" width="100%" cellpadding="0" cellspacing="0" border="0">
		<thead >
		<tr>
			<th>Amount</th>
			<th>Expense Type</th>
			<th>Description</th>
			<th>Date &nbsp;&nbsp;&nbsp;&nbsp;</th>
			<th></th>
		</tr>
		</thead>
		<tr>
			<td><form:input path="expenses[0].expense" id="expenses0.expense"
				cssClass="validate[required,custom[integer]] text  ui-corner-all txt_expense" cssErrorClass="error" /></td>
			<td>
				<form:select path="expenses[0].typeId"  cssClass="validate[required] expTypes" id="expenses0.typeId">
					<form:option value="">--Select--</form:option>
					<form:options items="${expTypes}" itemLabel="type" itemValue="id"/>
				</form:select>
				
			</td>
			<td>
			<form:input path="expenses[0].description" id="txt_description"
				cssClass="validate[required] text  ui-corner-all txt_description" cssErrorClass="error" />
			<td><form:input path="expenses[0].expDate" id="txt_expDate"
				cssClass="validate[required,custom[date]] text  ui-corner-all txt_expDate" cssErrorClass="error"/></td>
			<td>
				<div class="delete-last ui-state-default ui-corner-all"><span class="ui-icon ui-icon-minus"></span></div>
				<div class="add-last ui-state-default ui-corner-all"><span class="ui-icon ui-icon-plus"></span></div>
			</td>
		</tr>
	</table>
	<div align="center">
		<input type="submit" value="Save" id="btn_add_exp"
		class="btn ui-corner-all" />
	</div>	
	
</form:form>
