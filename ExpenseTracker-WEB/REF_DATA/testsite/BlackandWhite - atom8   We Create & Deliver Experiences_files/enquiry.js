$(document).ready(function()
{
	$("#enquiryForm").submit(function()
	{
		if($('#name').val()==""){
			$("#msgbox1").removeClass().addClass('messagebox1').text('Enter Name!!').fadeIn(1000);
			$('#name').focus();
			return false;
		}
		if($('#phone').val()=="" ){
			$("#msgbox1").removeClass().addClass('messagebox1').text('Enter Moblie no!!').fadeIn(1000);
			$('#phone').focus();
			return false;
		}else{
			var phoneno=$.trim($('#phone').val());
			if(phoneno!=""){
				if(validate(phoneno)==false){
					$("#msgbox1").removeClass().addClass('messagebox1').text('It is not valid mobile number.input 10 digits number!!').fadeIn(1000);
					return false;
				}else{
					$("#msgbox1").removeClass().addClass('messagebox1').text('').fadeIn(1000);
				}
			}
		}
		if($('#email').val()=="" ){
			$("#msgbox1").removeClass().addClass('messagebox1').text('Enter Email!!').fadeIn(1000);
			$('#email').focus();
			return false;
		}else{
			var email_id=$.trim($('#email').val());
			if(email_id!=""){
				var emails=email_id.split(',');
				for(var i=0;i<emails.length;i++){
					if(emailCheck(emails[i])==false){
						$("#msgbox1").removeClass().addClass('messagebox1').text('Email address seems incorrect!!').fadeIn(1000);
						return false;
					}else{
						$("#msgbox1").removeClass().addClass('messagebox1').text('').fadeIn(1000);
					}
				}
			}
		}
		if($('#description').val()=="" ){
			$("#msgbox1").removeClass().addClass('messagebox1').text('Enter Description!!').fadeIn(1000);
			$('#description').focus();
			return false;
		}	
		$("#msgbox1").removeClass().addClass('messagebox1').text('Processing....').fadeIn(1000);
		$.post("enquiry_send.php",{ username:base64_encode(base64_encode($('#name').val())),email:base64_encode(base64_encode($('#email').val())),phone:base64_encode(base64_encode($('#phone').val())),description:base64_encode(base64_encode($('#description').val())),task:'enquiry'} ,function(data)
        {
//			alert(data);
		  if($.trim(data)=='success'){
		  	$("#msgbox1").fadeTo(200,0.1,function(){ 
			$('#name').val('');	
			$('#phone').val('');	
			$('#email').val('');	
			$('#description').val('');	
			  $(this).html('Mail has been sent successfully.').removeClass().addClass('messageboxokhome').fadeTo(900, 1);
			});
		  }
		  else {
		  	$("#msgbox1").fadeTo(200,0.1,function() { 
			  $(this).html('Mail has been not sent successfully').addClass('messagebox1').fadeTo(900,1);
			});		
          }
        });
 		return false; //not to post the  form physically
	});	
});	
var testresults;
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

	