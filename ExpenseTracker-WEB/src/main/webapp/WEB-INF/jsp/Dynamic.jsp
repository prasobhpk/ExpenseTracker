<%@ include file="./common/include.jsp"%>
The Query that is used for this example is Select * From OFFICES
<form id="form1" method="post">
	
	<select name="dyna1" multiple size="8">
		<option value="COUNTRY">COUNTRY</option>
		<option value="CITY">CITY</option>
		<option value="STATE">STATE</option>
		<option value="OFFICECODE">OFFICECODE</option>
		<option value="ADDRESSLINE1">ADDRESSLINE1</option>
		<option value="ADDRESSLINE2">ADDRESSLINE2</option>
		<option value="POSTALCODE">POSTALCODE</option>
		<option value="TERRITORY">TERRITORY</option>
	</select> <INPUT TYPE="SUBMIT" VALUE="Run Dynamic Report"> <INPUT
		TYPE="HIDDEN" NAME="ReportName"
		Value="DynamicTableExample.rpttemplate">
</form>