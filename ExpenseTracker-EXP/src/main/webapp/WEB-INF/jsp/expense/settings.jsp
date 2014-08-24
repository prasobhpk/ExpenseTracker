<%@ include file="../common/include.jsp"%>
<script type="text/javascript">
$(function(){ 
	$('#tabs').tabs();
	
	$("#json_expTypes").jqGrid({ 
		url:"<c:url value='/rest/exp/expenseTypes/list'/>",
		editurl:"<c:url value='/rest/exp/expenseTypes/edit'/>",
		datatype: "json", 
		userdata: "data",
		colNames:['ID','Type','Description','Show In DB',' '], 
		colModel:[ 
		{name:'id',index:'id', width:55,hidden: true}, 
		{name:'type',index:'type', width:100, align:"left",editable:true},
		{name:'description',index:'description', width:150,editable:true}, 
		{name:'showInDashboard',index:'showInDashboard', width:60,editable:true,edittype:"checkbox",editoptions: {value:"true:false"}},
		{name: 'myac', width:80, fixed:true, sortable:false, resize:false, formatter:'actions', formatoptions:{keys:true}}
		], 
		rowNum:10, 
		rowList:[10,20,30], 
		pager: $('#pjmap'),
		sortname: 'id', 
		viewrecords: true,
		sortorder: "desc", 
		jsonReader : {
		      page: "currentPage",
		      total: "totalPages",
		      records: "totalRecords",
		      root:"rows",
		      repeatitems: false,
		      id: "0"
	    },
		caption: "Expenses Types", 
		height: '100%' ,
		width:500,
		recordtext: "View {0} - {1} of {2}"
	}); 

	$("#json_expTypes").jqGrid('navGrid','#pjmap',{edit:false,add:false,del:false,search:false}); 
	
	//------------------
	 $('#btn_pullCmp').click(function(){
		 				 $("#progress").css("visibility", "visible");
		                 $('#form_pullCmp').ajaxSubmit({
		                      url: '<c:url value="/rediffMoney/companies/pullfromsite"/>',
							  /*beforeSubmit:function(data){
								  var k=$('#upload-form *').fieldValue();
								  alert(k.join('::'));
								  return false;
							  },
							  
							   beforeSerialize: function(form, opts) {
		                         $.each(opts,function(i,data){alert(opts.url)});
								 $.each(form[0],function(i,data){
									alert(i +' : '+data.value)
								  });
								alert(form[0][0].name);
		                       }, */
							  success: function(data) {						 	 
								  $("#progress").css("visibility", "hidden");
						 		  $("#msg").html(data.msg);
								  if(data.status=='success'){
									  	$("#msg").css("color","green");
							 	  }else if(data.status=='error'){
							 			 $("#msg").css("color","red");
							 	  }
							  },
							  error:function(xhr,text){
							  alert(text);
							  }
		                });
		   });
});
</script>
<div id="tabs">
	<ul>
		<li><a href="#tabs-1">New Type</a></li>
		<li><a href="#tabs-2">ExpenseTypes</a></li>
	</ul>
	
	
	<div id="tabs-1">
		<div>
                <div id="type_results"></div>
				<form:form id="typeForm" method="post" action='expenseType' modelAttribute="type">
				<table class="ui-widget ui-widget-content ui-corner-all" width="400" height="180">
					<tr>
						<td>Expense Type :</td>
						<td><form:input path="type" id="txt_pwd" class="text  ui-corner-all" cssErrorClass="error"/></td>
						<td><form:errors path="type" cssClass="error" /></td>
					</tr>
					<tr>
						<td>Description :</td>
						<td><form:textarea  path="description" id="txt_pwd" class="text  ui-corner-all" cols="20" rows="5"/></td>
						<td></td>
					</tr>
					<tr>
						<td>Show In DashBoard</td>
						<td><form:checkbox path="showInDashboard" label="Yes" value="true" checked="checked"/></td>
						<td></td>
					</tr>
					<tr>
						<td></td>
						<td><input type="submit" value="Save" class="btn ui-corner-all" ></td>
						<td></td>
					</tr>
				</table>
				</form:form>             
				
			</div>
	</div>
	
	<div id="tabs-2">
		<table id="json_expTypes"></table> 
		<div id="pjmap"></div> 
	</div>
	
</div>
