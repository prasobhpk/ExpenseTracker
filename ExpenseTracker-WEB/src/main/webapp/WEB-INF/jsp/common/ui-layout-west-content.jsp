<%@ include file="include.jsp"%>
<script type="text/javascript">
//------------------
$(document).ready(function() 
{  
var icons = {
		header: "ui-icon-circle-arrow-e",
		headerSelected: "ui-icon-circle-arrow-s"
	};
$("#accordion").accordion({
	icons: icons,
	header: "> div > h3"
	}).sortable({
	axis: "y",
	handle: "h3",
	stop: function(event, ui) {
		stop = true;
	}
});
$('#lnk_upload').live('click',function(){
	$("#tabs").tabs( "select" , 2);
});

//Menu--------

$("#secondpane p.menu_head").mouseover(function(){
     $(this).css({backgroundImage:"url('<c:url value="/resources/images/fina.gif"/>')"}).next("div.menu_body").slideDown(500).siblings("div.menu_body").slideUp("slow");
     $(this).siblings().css({backgroundImage:"url('<c:url value="/resources/images/fina.gif"/>')"});
});
//-----------------qtip--------------
// Select all elements that are to share the same tooltip
   var elems = $('.qTip')
 
   // Store our title attribute and remove it so we don't get browser tooltips showing up
   .each(function(i) {
      $.attr(this, 'tooltip', $.attr(this, 'title'));
   })
   .removeAttr('title');
 
   // Create the tooltip on a dummy div since we're sharing it between targets
   $('<div />').qtip(
   {
      content: ' ', // Can use any content here :)
      position: {
         target: 'event', // Use the triggering element as the positioning target
         effect: false   // Disable default 'slide' positioning animation
      },
      show: {
         target: elems
      },
      hide: {
         target: elems
      },
      style: {
      classes: 'ui-tooltip-rounded ui-tooltip-shadow'
 	  },
      events: {
         show: function(event, api) {
            // Update the content of the tooltip on each show
            var target = $(event.originalEvent.target);
 
            if(target.length) {
               api.set('content.text', target.attr('tooltip'));
            }
         }
      }
   });
});

</script>
			
<div class="header">Menu/Search</div>
<div class="content">
	<div id="accordion">
		<sec:authorize access="hasAnyRole('ROLE_USER','ROLE_ADMIN')">
			<sec:authorize access="hasRole('ROLE_USER')">
				<c:if test="${showLiveBox}">
						<script type="text/javascript">
							//------------------
							$(document).ready(function() 
							{  
							
							
							$('#pane2').jScrollPane({showArrows:true, scrollbarWidth: 15, arrowSize: 16});
							//$('#ser').keypress(function(e){
							$('#ser').keyup(function(e){
									//alert(e.which);
									var rows='';
									if(e.which==13){
										$.getJSON('<c:url value="/rest/wm/stocks/search"/>', {cmpName:this.value},function(data) {
											$.each(data.data, function() { 
									 					 rows+='<li id="'+this.symbol+'" >';
									 					 //rows+='<cite>'+this.name+':</cite><br>';
									 					// rows+='<span class="time">'+this.dob+'</span><br>';
									 					 rows+='<span>'+this.name+'</span>';
									 					 rows+='</li>';
													});
													$('#commentlist').empty();
													$('#commentlist').html(rows);
													$('#pane2').jScrollPane({showArrows:true, scrollbarWidth: 15, arrowSize: 16});
													$("#commentlist li").contextMenu({
														  menu: 'myMenu'
														  }, function(action, el, pos) {
															  contextMenuWork(action,el,pos);
															  
													});
										});
									}
							});
							
							$("#commentlist li").each(function (i) {
								i = i+1;
								$(this).prepend('<span class="commentnumber"> #'+i+'</span>');
							});
							
							/*$(document).bind("contextmenu", function(e){
								e.preventDefault();
							});*/
							
							});
							//--------------------
							function contextMenuWork(action, el, pos) {
								var cmpCode=$(el).attr("id");
							    switch (action) {
							        case "fav":
							            {
							        	var cmpIds=cmpList.split('|');	
							        	if(cmpIds.contains(cmpCode)){
							        		alert('Stock already exists in your favourite list');
							        	}else{
							        		location.href ='<c:url value="/wm/watchlist/add?id="/>'+ cmpCode;
							        	}
							            		   
							                break;
							            }
							        case "buy":
							            {
							        		location.href ='<c:url value="/wm/equity?symbol="/>'+ cmpCode;
							                break;
							            }
							
							        case "sell":
							            {
							                alert('Edit Clicked');
							                break;
							            }
							        case "deleteSelected":
							            {
							                alert('Multiple Delete Clicked');
							                break;
							            }
							    }
							} 
							</script>
						
							<div>
								<h3><a href="#">Market Live</a></h3>
								<div style="overflow: hidden">
									Search Stocks :<br>
									<input type="text" id="ser" class="text ui-widget-content ui-corner-all" value="Enter Stock name" onfocus="this.value=''" onblur="this.value='Enter Stock name'">
									<div  class="holder osX">
										<div id="pane2" class="scroll-pane">
											<ol id="commentlist">
																				
											</ol>
										</div>
									</div>
								</div>
							</div>
									
									
							<ul id="myMenu" class="contextMenu">
	
								<li class="favorite"><a href="#fav">Add To List</a></li>
								<li class="cut separator"><a href="#buy">Buy</a></li>
								<li class="copy"><a href="#sell">Sell</a></li>
<!-- 										<li class="paste"><a href="#paste">Paste</a></li> -->
<!-- 										<li class="delete"><a href="#delete">Delete</a></li> -->
<!-- 										<li class="quit separator"><a href="#quit">Quit</a></li> -->
	
						   </ul>	
					</c:if>
				</sec:authorize>

				<div>
					<h3><a href="#">Menu</a></h3>
					<div >
			                <div class="menu_list" id="secondpane" style="height: 200px">
				                  <sec:authorize access="hasRole('ROLE_USER')">
					                  <p class="menu_head">Expenses</p>
					                  <div class="menu_body">
					                  	 <a href="<c:url value="/exp"/>" title="Add new Expense" class="qTip">Add Expense</a>
					                  	 <a href="<c:url value="/exp/forecast"/>" title="Forecast your expense" class="qTip">Add Forecast</a> 
					                  	 <a href="<c:url value="/exp/forecast/status"/>" title="Check your financial status for current month" class="qTip">Financial Status</a>
					                  	 <a href="#" id="lnk_upload">Upload Expenses </a>
					                  	 <a href="<c:url value="/exp/export"/>">Download Expense</a>
				                  	  </div>
				                  	  
					                 <p class="menu_head">Dash Boards</p>
					                  <div class="menu_body">
					                  	 <a href="<c:url value="/exp/expenseDashBoard"/>">Expense Dashboard</a>
				                  	  </div>
				                  	  
				                  	  <p class="menu_head">Settings</p>
					                  <div class="menu_body">
					                 	 <a href="<c:url value="/settings/userAppSettings"/>">App Settings</a>
					                  	 <a href="<c:url value="/settings/expenseType"/>">Expense Types</a>
					                  	 <a href="#">Password</a> 
					                  	 <a href="<c:url value="/settings/reportTest"/>">Report</a>
				                  	  </div>
				                  	  <p class="menu_head">Investments</p>
					                  <div class="menu_body">
					                  	 <a href="<c:url value="/wm/portfolios"/>">Portfolios</a>
					                  	 <a href="<c:url value="/wm/watchlist"/>">Watch List</a>
				                  	  </div>
			                  	  </sec:authorize>
			                  	  
								<sec:authorize access="hasRole('ROLE_ADMIN')">
				                      <p class="menu_head">System Settings</p>
						              <div class="menu_body">
						             	 <a href="<c:url value="/admin/proxysettings"/>">Proxy Settings</a>
						          	  </div>
				           	  
					           	 	 <p class="menu_head">Data Setup</p>
						             <div class="menu_body">
						             	 <a href="<c:url value="/admin/setupStockData"/>">Stock Data</a>
						             	 <a href="<c:url value="/admin/brokerage"/>">Brokerage Structure</a>
						           	  </div>
						           	  
								</sec:authorize>					                  	  
			                </div>
						
					</div>
				</div>
					
		</sec:authorize>
	</div>
</div>	