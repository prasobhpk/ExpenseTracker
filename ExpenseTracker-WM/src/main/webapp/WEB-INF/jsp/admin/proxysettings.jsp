<%@ include file="../common/include.jsp"%>
<script>
$(document).ready(function(){
});
</script>
<div class="ui-widget-content" align="center" style="width: 300px;height: 200px;margin: 40px 50px 60px 300px;">
		<form action="<c:url value="/admin/proxysettings"/>" method="post" id="form_pullCmp">
			<table>
				
				<tr>
					<td>Proxy :</td>
					<td><input type="text" name="proxy" value="${proxy}">
					</td>
					<td></td>
				</tr>
				
				<tr>
					<td>&nbsp;</td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td>Port :</td>
					<td><input type="text" name="port" value="${port}">
					</td>
					<td></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td>User :</td>
					<td><input type="text" name="proxyUser" value="${proxyUser}">
					</td>
					<td></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td>Password :</td>
					<td><input type="text" name="proxyPassword" value="${proxyPassword}">
					</td>
					<td></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td>Active :</td>
					<td><input type="checkbox" name="proxyActive" ${proxyActive?"checked":""}>
					</td>
					<td></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td></td>
					<td><input type="submit" value="Save Settings"
						class="btn ui-corner-all" id="btn_pullCmp"></td>
					<td></td>
				</tr>
			</table>
		</form>
	</div>