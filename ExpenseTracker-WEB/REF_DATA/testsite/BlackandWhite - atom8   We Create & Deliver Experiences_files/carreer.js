$(document).ready(function()
{
	$("#carrerForms").submit(function()
	{
		if($('#carname').val()==""){
			$("#msgbox12").removeClass().addClass('messagebox1').text('Enter Name!!').fadeIn(1000);
			$('#carname').focus();
			return false;
		}
		if($('#carphone').val()=="" ){
			$("#msgbox12").removeClass().addClass('messagebox1').text('Enter Moblie no!!').fadeIn(1000);
			$('#carphone').focus();
			return false;
		}else{
			var carphoneno=$.trim($('#carphone').val());
			if(carphoneno!=""){
				if(validate(carphoneno)==false){
					$("#msgbox12").removeClass().addClass('messagebox1').text('It is not valid mobile number.input 10 digits number!!').fadeIn(1000);
					return false;
				}else{
					$("#msgbox12").removeClass().addClass('messagebox1').text('').fadeIn(1000);
				}
			}
		}
		if($('#caremail').val()=="" ){
			$("#msgbox12").removeClass().addClass('messagebox1').text('Enter Email!!').fadeIn(1000);
			$('#caremail').focus();
			return false;
		}else{
			var caremail_id=$.trim($('#caremail').val());
			if(caremail_id!=""){
				var caremails=caremail_id.split(',');
				for(var i=0;i<caremails.length;i++){
					if(emailCheck(caremails[i])==false){
						$("#msgbox12").removeClass().addClass('messagebox1').text('Email address seems incorrect!!').fadeIn(1000);
						return false;
					}else{
						$("#msgbox12").removeClass().addClass('messagebox1').text('').fadeIn(1000);
					}
				}
			}
		}
		if($('#cardescription').val()=="" ){
			$("#msgbox12").removeClass().addClass('messagebox1').text('Enter Description!!').fadeIn(1000);
			$('#cardescription').focus();
			return false;
		}
		if($('#filename').val()==""){
			$("#msgbox12").removeClass().addClass('messagebox1').text('Select your resume!!').fadeIn(1000);
			$('#file_upload').focus();
			return false;
		}
		$("#msgbox12").removeClass().addClass('messagebox1').text('Processing....').fadeIn(1000);
		$.post("enquiry_send.php",{ username:base64_encode(base64_encode($('#carname').val())),email:base64_encode(base64_encode($('#caremail').val())),phone:base64_encode(base64_encode($('#carphone').val())),description:base64_encode(base64_encode($('#cardescription').val())),task:'carreer',filename:base64_encode(base64_encode($('#filename').val()))} ,function(data)
        {
		  if($.trim(data)=='success'){
		  	$("#msgbox12").fadeTo(200,0.1,function(){ 
			$('#carname').val('');	
			$('#carphone').val('');	
			$('#caremail').val('');	
			$('#cardescription').val('');	
			$('#filename').val('');	
			$('#file_upload-queue').hide();
			  $(this).html('Mail has been sent successfully.').removeClass().addClass('messageboxokhome').fadeTo(900,1);
			});
		  }
		  else {
		  	$("#msgbox12").fadeTo(200,0.1,function() { 
			  $(this).html('Mail has been not sent successfully').addClass('messagebox1').fadeTo(900,1);
			});		
          }
        });
 		return false; //not to post the  form physically
	});	
});	
/*var testresults;
function emailCheck(str){
	var filter=/^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i
	if (filter.test(str)){
		testresults=true;
	}else{
		testresults=false;
	}
	return (testresults);
}
function validate(no) {
	var mobile = no;
	var pattern = /^\d{10}$/;
	if (pattern.test(mobile)) {
		return true;
	}else{ 
		return false;
	}
}

	*/