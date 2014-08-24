
<%@ include file="common/include.jsp"%>

<script type="text/javascript">
	jQuery(document).ready(function() {

		// binds form submission and fields to the validation engine

		jQuery("#regForm").validationEngine({
			//label : 'lbl'
		});

	});
</script>

<div align="center">

	<div align="left"
		style="width: 500px; margin-top: 150px; height: 250px;"
		class="ui-widget-content ui-corner-all">
		<form:form id="regForm" method="post" modelAttribute="regUser"
			style="margin: 30px 30px;">

			<table align="center">

				<tr>

					<td width="20%"><spring:message code="ui.regForm.uname" /></td>

					<td width="30%"><form:input path="username" id="txt_userName"
							cssClass="validate[required,minSize[4],maxSize[9]] text  ui-corner-all"
							cssErrorClass="error" lbl="User Name" />
					</td>

					<td width="50%"><form:errors path="username" cssClass="error" />
					</td>

				</tr>

				<tr>

					<td><spring:message code="ui.regForm.pwd" /></td>

					<td><form:password path="password" id="txt_password"
							cssClass="validate[required,minSize[4],maxSize[12]] text  ui-corner-all"
							cssErrorClass="error" />
					</td>

					<td><form:errors path="password" cssClass="error" />
					</td>

				</tr>



				<tr>

					<td><spring:message code="ui.regForm.fname" /></td>

					<td><form:input path="name.firstName" id="txt_firstName"
							cssClass="validate[required] text  ui-corner-all"
							cssErrorClass="error" />
					</td>

					<td><form:errors path="name.firstName" cssClass="error" />
					</td>

				</tr>



				<tr>

					<td><spring:message code="ui.regForm.mname" /></td>

					<td><form:input path="name.middleName" id="txt_middleName"
							cssClass="text  ui-corner-all" cssErrorClass="error" />
					</td>

					<td><form:errors path="name.middleName" cssClass="error" />
					</td>

				</tr>



				<tr>

					<td><spring:message code="ui.regForm.lname" /></td>

					<td><form:input path="name.lastName" id="txt_lastName"
							cssClass="validate[required] text  ui-corner-all"
							cssErrorClass="error" />
					</td>

					<td><form:errors path="name.lastName" cssClass="error" />
					</td>

				</tr>

				<tr>

					<td></td>

					<td><input type="submit" value="<spring:message code="ui.regForm.btn.submit" />" id="btn_sign-up"
						class="btn ui-corner-all" />
					</td>

				</tr>

			</table>

		</form:form>
	</div>

</div>

