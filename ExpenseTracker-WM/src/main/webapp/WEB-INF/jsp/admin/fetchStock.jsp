<%@ include file="../common/include.jsp"%>
<script>
$(document).ready(function(){
	//------------------
	$('#btn_pullCmp').click(function(){
		 				$("#progress").css("visibility", "visible");
		 				var url ='<c:url value="/admin/setupStockData"/>';
		 				var params={hack:$('#hack').val()};
		 				var jqxhr = $.post(url,params, function(data) {
		 					 $("#progress").css("visibility", "hidden");
					 		  $("#msg").html(data.msg);
							  if(data.status=='success'){
								  	$("#msg").css("color","green");
						 	  }else if(data.status=='error'){
						 			 $("#msg").css("color","red");
						 	  }
	 				    });
		 				jqxhr.error(function(xhr,txt) { 
		 					$("#progress").css("visibility", "hidden");
		 					alert(txt); 
	 					});
		                					
	 });               
});
</script>
<div class="ui-widget-content" align="center" style="width: 400px;height: 200px;margin: 40px 50px 60px 300px;">
		<span id="msg"></span>
		<div id="progress" style="visibility: hidden;"><img src='<c:url value="/resources/images/ajaxLoader.gif"/>'></div>
			<table>
				
				<tr>
					<td>URL :</td>
					<td><input type="text" id="hack" value="http://money.rediff.com/companies/All/1-5000" size="50">
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
					<td><input type="button" value="Init Stock Data"
						class="btn ui-corner-all" id="btn_pullCmp"></td>
					<td></td>
				</tr>
			</table>
	</div>