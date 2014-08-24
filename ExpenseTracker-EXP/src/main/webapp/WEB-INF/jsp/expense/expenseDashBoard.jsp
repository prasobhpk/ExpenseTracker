<%@page import="com.pk.et.exp.util.Util"%>
<%@ include file="../common/include.jsp"%>
<%@page import="com.pk.et.infra.util.DateUtil" %>
<style>
.ui-progressbar-value { background-image: url(<%=application.getContextPath()%>/resources/images/pbar-ani.gif); }
</style>
<script type="text/javascript">
/*
 // jQuery
$.ajax({
    url : url,
    method : 'GET',
    beforeSend : function(req) {
        req.setRequestHeader('Authorization', auth);
    }
});
 */
var fn_editSubmit=function(response,postdata){
	 //var json=response.responseText; //in my case response text form server is "{sc:true,msg:''}"
	// var result=eval("("+json+")"); //create js object from server reponse
	// return [result.sc,result.msg,null]; 
	alert(response);
}
var editOptions={
	modal:true, 
	width: 300 ,
	//closeAfterEdit:true,
	closeOnEscape: true/*, afterSubmit: fn_editSubmit*/
}

var isMenu=false;
$(document).ready(function() 
{  
	$("#jsonmap").jqGrid({ 
		url:"<c:url value='/rest/exp/expenses/list'/>", 
		editurl:"<c:url value='/rest/exp/expenses/edit'/>",
		datatype: "json", 
		userdata: "data",
		colNames:['ID','DATE','Amount','DESCRIPTION','ACTIVE','TYPE'], 
		colModel:[ 
		{name:'id',index:'id', width:55,search:false,hidden: true}, 
		{name:'expDate',index:'expDate', width:150, align:"left",search:true,stype:'text', searchoptions:{dataInit:datePick, attr:{title:'Select Date'},sopt:['eq','gt','lt']} },
		{name:'expense',index:'expense', width:90,editable:true,search:true,searchoptions:{sopt:['eq','gt','lt']},summaryType:'sum'}, 
		{name:'description',index:'description', width:100,editable:true,search:true,searchoptions:{sopt:['eq','bw']}},
		{name:'active',index:'active', width:60,editable:true,edittype:"checkbox",editoptions: {value:"true:false"},formatter:'checkbox'},
		{name:'expenseType.type',index:'expenseType.type',hidden: true, width:80,editable:false,search:true,stype:'select',
				searchoptions:{sopt:['eq'],searchhidden:true,dataUrl:"<c:url value='/rest/exp/expenseTypes/all'/>",buildSelect:buildExpTypes}}
		], 
		rowNum:10, 
		rowList:[10,20,30], 
		pager: $('#pjmap'),
		sortname: 'id', 
		viewrecords: true,
		sortorder: "desc", 
		//footerrow:true,
		jsonReader : {
		      page: "currentPage",
		      total: "totalPages",
		      records: "totalRecords",
		      root:"rows",
		      repeatitems: false,
		      id: "0"
	    },
		caption: "Expenses", 
		height: '100%' ,
		width:550,
		recordtext: "View {0} - {1} of {2}",
		grouping:true, 
		groupingView : 
			{ 
				groupField : ['expenseType.type'], 
				groupSummary : [true], 
				groupColumnShow : [true], 
				groupText : ['<b>{0}</b>'],
				groupCollapse : false, 
				groupOrder: ['asc'] 
			},
	}); 

	$("#jsonmap").jqGrid('navGrid','#pjmap',{edit:true,add:false,del:true,view:{viewtitle: "Expense"}},editOptions,{},{},{multipleSearch:true}); 
	//jQuery("#jsonmap").jqGrid('searchGrid', {multipleSearch:true} );
	function datePick(elem)
	{
	   $(elem).datepicker();
	}
	
	function buildExpTypes(data){
		var expTypes=jQuery.parseJSON(data);
		var typeOpts='<select>';
		$.each(expTypes,function(index,expType){
			typeOpts+='<option value="'+expType.type+'">'+expType.description+'</option>';
		});
		typeOpts+='</select>';
		return typeOpts;
	}
		
//------------------
	
	$( "button, input:submit").button();
	
	$("#tabs").tabs();
	
	
	


	
//change password-----------------

	// prepare Options Object 
	var options = { 
		type : 'POST',	
	    target:     '#pwd_results', 
	    url:        'changePassword.json', 
	    dataType:'json',
	    resetForm:true,
	    success:    function(data) { 
	       // alert(data.msg); 
		    $('#pwd_results').empty();
	        if(data.msg=='success'){
	        	 $('#pwd_results').html('<p>Password has been changed Successfully......</p>');
	        }else{
	        	$('#pwd_results').html("<p>Couldn't update the password......</p>");
	        }
	    } 
	}; 
	
	$('#chPwdForm').ajaxForm(options);



	//-------------------Grid Context Menu-----------------
	$('div.flexigrid tbody').live('mouseover',function(){
		prepareGridMenu();
	});
	
	
	//-----------------upload file------------------------
	
	 
	 // $('#uploadFile').html('<div  id="upload_div"><span id="msg"></span><div id="progress"></div><form action="" method="post" id="upload-form"><p>Select File :<input type="file" name="file" id="xl_file"/></p><br><input id="doUpload" value="Upload" type="button" class="btn ui-corner-all"></form><div id="upResult"></div></div>');
	  $("#progress").progressbar({
				value: 100
			});
	  var form = $("#upload-form");
	  var up_div=$("#upload_div");
	  $('#uploadFile').dialog({
						autoOpen: false,
						resizable: false,
						show: 'slide',
						width: 400,
						height:300,
						bgiframe: true,			
				        modal: true,

	                   open: function(event, ui) {
					        // alert(docc._rev);
				        	 $("#msg").html("");
							 $("#xl_file").val("");
							 up_div.find("#progress").css("visibility", "hidden");
	                   },
						buttons: {					
								"OK": function() { 
								 $(this).dialog("close"); 							 	
								},
								"Cancel": function() { 
								 $(this).dialog("close"); 												
								}	
						}
					});
	   
	 
	  
	   $('#doUpload').click(function(){
	       var typ=$('#uploadTyp').val();           
	   //alert(encodeDocId(docc._id));
	                
	                 form.ajaxSubmit({
	                      url:typ,
	                      clearForm:true,
	                      resetForm: true,
	                      dataType: 'json',
						  /*
						  beforeSubmit:function(data){
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
						   beforeSubmit:function(data){
							   $("#upResult").html("");
							  var file0=$("#xl_file").val();
							  var ext0=getExt(file0);
							  if(typ=='expenses/upload'){
				                  if ((! (ext0 && /^(xls|xlsx)$/.test(ext0)))){  
			                          $("#msg").html('Only XLS or XLSX files are allowed').css("color","red"); 
			                          return false;  
		                           }  
							  }else{
								  if ((! (ext0 && /^(csv|txt)$/.test(ext0)))){  
			                          $("#msg").html('Only CSV or TXT files are allowed').css("color","red"); 
			                          return false;  
		                           } 
							  }
		                 		 up_div.find("#progress").css("visibility", "visible");	
							   $("#msg").html("");
						   },
						  success: function(data) {	
							  //alert(resp);									  
							  //resp=resp.substring(resp.indexOf('{'),resp.indexOf('}')+1);	
							 // var data=$.parseJSON(resp);
							  //alert(data.msg);
							  up_div.find("#progress").css("visibility", "hidden");
							  //up_div.find("#doUpload").css("visibility", "hidden");
					 		  if(data.status=='success'){
							  	$("#msg").html(data.msg).css("color","green");
					 		  }else if(data.status=='error'){
					 			 $("#msg").html('Could not upload expenses!!!').css("color","red");
						 		 $("#upResult").html(data.msg);
					 		  }
						  },
						  error:function(xhr,text){
						 	 alert(text);
						  }
	                });
	   });
	   
	$('#xl_upload').click(function(){
      // $('#uploadFile').html("");
       $("#upResult").html("");
       $('#uploadFile').dialog('open');
	});
	

	
});

function prepareGridMenu(){
	if(true){
		//	alert(isMenu);
		$("div.flexigrid tbody tr").contextMenu({
			  menu: 'gridMenu'
			  }, function(action, el, pos) {
				 // alert('gsfgfdgdf');
				  gridContextMenuWork(action,el,pos);
				  
		});
		isMenu=true;
	}
}

//------------------------------------
function getExt(file){
	return (/[.]/.exec(file)) ? /[^.]+$/.exec(file.toLowerCase()) : '';
}
//-------------------------------------------------


</script>

				
			<div id="tabs" >
					<ul>
						<li><a href="#tabs-1">Dashboard</a></li>
						<li><a href="#tabs-2">Expenses</a></li>
						<li><a href="#tabs-3">Upload Data</a></li>
					</ul>
					
					
					<div id="tabs-1">
						 <OBJECT classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
							codebase="http://fpdownload.adobe.com/pub/shockwave/cabs/flash/swflash.cab#version=9,0,0,0" 
							WIDTH="700" HEIGHT="500" id="myMovieName">
							<PARAM NAME=FlashVars VALUE="URL=expenses/db&yearsURL=expenses/years&defaultYr=<%=DateUtil.getYear(null) %>">
							<PARAM NAME="movie" VALUE="<%=application.getContextPath()%>/resources/dashboards/expenses.swf"> 
							<PARAM NAME="quality" VALUE="high">
							<PARAM NAME="bgcolor" VALUE="#FFFFFF">
							<PARAM NAME="play" VALUE="true">
							<PARAM NAME="loop" VALUE="true">
							<PARAM NAME=bgcolor VALUE="#FFFFFF">
							<EMBED src="<%=application.getContextPath()%>/resources/dashboards/expenses.swf" quality=high bgcolor=#FFFFFF WIDTH="700" HEIGHT="500" 
								NAME="myMovieName" ALIGN="" TYPE="application/x-shockwave-flash" 
								play="true" loop="true" 
								FlashVars="URL=<%=Util.getPath(request)%>rest/exp/expenses/db/forTheYear&yearsURL=<%=Util.getPath(request)%>rest/exp/expenses/db/years&defaultYr=<%=DateUtil.getYear(null) %>"
								PLUGINSPAGE="http://www.adobe.com/shockwave/download/index.cgi?P1_Prod_Version=ShockwaveFlash">
							</EMBED>
						</OBJECT>
					</div>
					
					<div id="tabs-2">
						<table id="jsonmap"></table> 
						<div id="pjmap"></div> 
					</div>
					
					<div id="tabs-3">
							<div>
								<div>
									<p>
										<span>Only use the <a href="#">template</a> to upload data</span>
									</p>
								</div>
								<div>
									<table>
										<tr>
											<td>File type :</td>
											<td>
												<select id="uploadTyp">
													<option value="expenses/upload">XL/XLS</option>
													<option value="expenses/uploadCSV">CSV</option>
												</select>
											</td>
										</tr>
										<tr>
											<td colspan="2">
												<input type="button" id="xl_upload" value="Upload Expenses" class="btn ui-corner-all">
											</td>
										</tr>
									</table>
								</div>
							</div>
							
							<div id="uploadFile" title="Upload Expenses">
								<div  id="upload_div">
									<div id="progress"></div>
									<span id="msg"></span>
									<form action="" method="post" id="upload-form">
										<table>
											<tr>
												<td>Select File :</td>
												<td><input type="file" name="file" id="xl_file"/></td>
											</tr>
											<tr>
												<td></td>
												<td></td>
											</tr>
											<tr>
												<td>Description :</td>
												<td><textarea cols="30" rows="3" name="description"></textarea></td>
											</tr>
											<tr>
												<td></td>
												<td></td>
											</tr>
											<tr>
												<td colspan="2"><input id="doUpload" value="Upload" type="button" class="btn ui-corner-all"></td>
											</tr>
										</table>
										
									</form>
									<div id="upResult"></div>
								</div>
							</div>
					</div>
					
					
										
			</div>
			
