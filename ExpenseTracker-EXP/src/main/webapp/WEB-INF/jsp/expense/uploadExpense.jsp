<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<script type="text/javascript">
$(document).ready(function(){
	
	  $('#uploadFile').html("");
	  $('#uploadFile').html('<div  id="upload_div"><span id="msg"></span><div id="progress"></div><form action="" method="post" id="upload-form"><p>Select File :<input type="file" name="file" id="xl_file"/></p><br><input id="doUpload" value="Upload" type="button"></form><div id="upResult"></div></div>');
	  $("#progress").progressbar({
				value: 100
			});
	  var form = $("#upload-form");
	  var up_div=$("#upload_div");
	  $('#uploadFile').dialog({
						autoOpen: false,
						resizable: false,
						show: 'slide',
						width: 600,
						height:300,
						bgiframe: true,			
				        modal: true,

	                   open: function(event, ui) {
					        // alert(docc._rev);
							 $("#xl_file").val("");
							 up_div.find("#progress").css("visibility", "hidden");
	                   },
						buttons: {					
								"OK": function() { 
								 $(this).dialog("close"); 							 	
								 window.location.replace(url);						      					
								},
								"Cancel": function() { 
								 $(this).dialog("close"); 												
								}	
						}
					});
	   
	 
	  
	   $('#doUpload').click(function(){
	                  
	   //alert(encodeDocId(docc._id));
	                 up_div.find("#progress").css("visibility", "visible");
	                 form.ajaxSubmit({
	                      url: 'upload',
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
						   beforeSubmit:function(data){
							  var file0=$("#fff").val();
							  var ext0=getExt(file0);
			                  if ((! (ext0 && /^(xls|xlsx)$/.test(ext0)))){  
		                          $("#msg").html('Only XLS or XLSX files are allowed').css("color","red");;  
		                          return false;  
	                           }  
							   $("#msg").html("");
						   },
						  success: function(resp) {						 	 
							  up_div.find("#progress").css("visibility", "hidden");
							  up_div.find("#doUpload").css("visibility", "hidden");
					 		  $("#upResult").html('Images uploaded ...Click Ok');
						  },
						  error:function(xhr,text){
						  alert(text);
						  }
	                });
	   });
	   
	$('#xl_upload').click(function(){
        $('#uploadFile').dialog('open');
	});
});
</script>
<div>
	<div>
		<p>
			<span>Only use the <a href="#">template</a> to upload data</span>
		</p>
	</div>
	<div>
		<input type="button" id="xl_upload" value="Upload Expenses">
	</div>
</div>

<div id="uploadFile" title="Upload Image">
<!--	<form method="post" action="upload" enctype="multipart/form-data" id="upload-form">-->
<!--		<div id="progress"></div><br><br>-->
<!--		<p>Select File :<input type="file" name="file" /></p>-->
<!--		<p><input type="submit" /></p>-->
<!--	</form>-->
</div>