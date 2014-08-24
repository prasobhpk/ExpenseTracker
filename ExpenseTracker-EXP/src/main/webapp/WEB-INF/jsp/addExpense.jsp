<%@ include file="common/include.jsp" %>
<script type="text/javascript">
$(document).ready(function() 
{
	
	// prepare Options Object 
		var options = { 
			type : 'POST',	
			data:{toId:$( "#txt_to" ).val()},
		    target:     '#results', 
		    url:        'sendMessage.json', 
		    dataType:'json',
		   /* beforeSubmit: function(formArray, jqForm) {
			    			var id=$( "#txt_to" ).val();
		    	         	alert(id);
			    			if(id!=''){
			    				$( "#txt_tojj" ).val(id);
			    				 return true;
			    			}else{
			    				 return false;
			    			}
		    	     },*/
		    success:    function(data) { 
		       // alert(data.msg); 
			    $('#results').empty();
		        if(data.msg=='success'){
		        	 $('#results').html('<p>Message has been sent Successfully......</p>');
		        }else{
		        	$('#results').html('<p>Message sending failed......</p>');
		        }
		    } 
		}; 
		
		$('#newMsgForm').ajaxForm(options);
		
});
</script>
<form:form id="newMsgForm" method="post"  modelAttribute="message">
	<table class="ui-widget ui-widget-content ui-corner-all" width="300" height="180">
							<tr>
								<td>Subject :</td>
								<td>
									<form:input path="subject" id="txt_subject" cssClass="text  ui-corner-all" />
								</td>
							</tr>
							<tr>
								<td>Message :</td>
								<td>
									<form:textarea path="message" cols="30" rows="7" cssClass="text  ui-corner-all"/>
								</td>
							</tr>
							
							<tr>
								<td></td>
								<td>
									<input type="submit" value="Send" id="btn_sendMsg" class="btn ui-corner-all" />
								</td>
							</tr>
	</table>
</form:form>