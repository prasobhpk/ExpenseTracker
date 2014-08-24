<%@ include file="/WEB-INF/jsp/common/include.jsp" %>
<script type="text/javascript" src="<%=application.getContextPath()%>/resources/js/jquery.js"></script>
<link type="text/css" href='<%=application.getContextPath()%>/<spring:theme code="css"/>'
	rel="stylesheet" />
<script type="text/javascript" src="<%=application.getContextPath()%>/resources/js/jquery-ui.js"></script>
<script type="text/javascript" src="<%=application.getContextPath()%>/resources/js/jquery.validate.js"></script>
<script type="text/javascript" src="<%=application.getContextPath()%>/resources/js/sha2.js"></script>
<script type="text/javascript">
$(document).ready(function() 
{
	var randomSalt="<%=session.getId()%>";
	//$( "button, input:submit").button();
	 $('#loginForm').validate({
			errorElement: "em",
			errorPlacement: function(error, element) {
				error.appendTo( element.parent("td").next("td") );
			},
			/*success: function(label) {
				label.text("ok!").addClass("success");
			},*/
			rules: {
				j_username: {
					required:true
					},
					j_password: {
					required:true
					}
			},
			messages: {
				j_username: {
					required: 'User Name is required!'
				},
				j_password: {
					required: 'Password is required!'
				}
		    }
	});  

	 $("#loginForm").submit(function(){
		if($(this).valid()){
			try {
				//var token=make_base_auth($("#j_username").val(),$("#j_password").val());
				//$.cookie('auth_token',token);
				var pwdHash=SHA256($("#j_password").val());
				$("#j_password").val(SHA256(pwdHash+"{"+randomSalt+"}"));
				return true;
				
			} catch (e) {
				alert(e);
			}
		return false;
		}
	 });
	 
	 function make_base_auth(user, password) {
		  var tok = user + ':' + password;
		  var hash = Base64.encode(tok);
		  return "Basic " + hash;
	 }
	// $(window).unload( function () { alert("Bye now!"); } );
	
});
</script>
		<div id="loginDiv"  class="ui-widget-content ui-corner-all">
			 <%-- this form-login-page form is also used as the
         form-error-page to ask for a login again.
         --%>
		    <c:if test="${not empty param.login_error}">
		      <font color="red">Your login attempt was not successful, try again.<br/>
		        Reason: <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>.
		      </font>
		    </c:if>
			<form action="<c:url value='/et_login'/>" method="post" id="loginForm" style="margin: 30px 30px;">
				<table>
					<tr>
						<td>User Name</td>
						<td><input id="j_username" type='text' name='j_username'></td>
						<td></td>
					</tr>
					<tr>
						<td>Password</td>
						<td><input id="j_password" type='password' name='j_password'></td>
						<td></td>
					</tr>
					<tr>
						<td></td>
						<td><input type="submit" value="Login" class="btn">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="regForm" id="create-userdd">Sign up?</a></td>
						<td></td>
					</tr>
				</table>
			</form>
		</div>
