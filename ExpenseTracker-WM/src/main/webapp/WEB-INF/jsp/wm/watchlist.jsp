<%@ include file="../common/include.jsp"%>
<script type="text/javascript" src="<c:url value="/resources/js/jquery.tablesorter.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/js/fade.js"/>"></script>
<style>
/* tables */
table.tablesorter {
	font-family: arial;
	background-color: #CDCDCD;
	margin: 10px 0pt 15px;
	font-size: 8pt;
	width: 100%;
	text-align: left;
}

table.tablesorter thead tr th,table.tablesorter tfoot tr th {
	background-color: #e6EEEE;
	border: 1px solid #FFF;
	font-size: 8pt;
	padding: 4px;
}

table.tablesorter thead tr .header {
	background-image: url('<c:url value="/resources/images/bg.gif"/>');
	background-repeat: no-repeat;
	background-position: center right;
	cursor: pointer;
}

table.tablesorter tbody td {
	color: #3D3D3D;
	padding: 4px;
	background-color: #FFF;
	vertical-align: top;
}

table.tablesorter tbody tr.odd td {
	background-color: #F0F0F6;
}

table.tablesorter thead tr .headerSortUp {
	background-image: url('<c:url value="/resources/images/asc.gif"/>');
}

table.tablesorter thead tr .headerSortDown {
	background-image: url('<c:url value="/resources/images/desc.gif"/>');
}

table.tablesorter thead tr .headerSortDown,table.tablesorter thead tr .headerSortUp
	{
	background-color: #8dbdd8;
}
</style>
<script type="text/javascript">
	cmpList = '${cmpListStr}';
	$().ready(function() {
		//$("#myTable").css("visibility", "hidden");
		$("#tbl_marketWatch").tablesorter();
		//tableToGrid("#tbl_marketWatch");
		if(cmpList!=''){
			updateFavList();
			//Frequently update the watchlist only if the market is open
			if(marketOpen()){
				setInterval(updateFavList,5000);
			}
			$("#tbl_marketWatch tbody tr").contextMenu({
				  menu: 'marketWatchMenu'
				  }, function(action, el, pos) {
					  marketWatchMenuWork(action,el,pos);
					  
			});
		}
	});
	function marketOpen(){
		var now=new Date();
		if(now.getHours()>9 && now.getHours()<16 && now.getDay()!=0 && now.getDay()!=6){
			return true;
		}else{
			return false;
		}
	}
	function updateFavList(){
		if(!isReqAborted){
			var url="<%=application.getContextPath()%>/wm/watchlist/liveStatus.json";
			var params={companylist:cmpList};
			$.getJSON(url,params,updateList);
		}
	}
	function updateList(data){
		var message=data.message;
		data=eval(data.stockData);
		if(message.status!='fail'){
			var gl=data[0];
			var stocks=data[1];
			 $.each(stocks, function(i,stock){
				 updateStock(stock);
			 });
		}else{
			var options={
					msg:message.msg,
					details:message.details,
					type:'error',
					header:'Error...'
			};
			dispalyMessage(options);
		}

	}
	function updateStock(stock){

		var code = "";

		if(typeof(stock.CompanyCode) == 'string') {
			code = stock.CompanyCode.replace('_','.');
		} else {
			code = stock.CompanyCode;
		}
//		if((code != "17023928") && (code != "17023929") && (code != "0")){
			var lstperdiff = stock.PercentageDiff;
			var newlstval = stock.LastTradedPrice;
			var oldltpele = EL(code+'ltp');
			var oldltp = oldltpele.innerHTML;
			var newltp = stock.LastTradedPrice;
			EL(code+'ltp').innerHTML=stock.LastTradedPrice;

			if(lstperdiff >= 0 && lstperdiff!=0){

				EL(code+'ltpimage').innerHTML='<img src="<c:url value="/resources/images/mup1.gif"/>" WIDTH="30" HEIGHT="19" alt="*"/>';
				EL(code+'NetChange').innerHTML="<font class=\"up\">+"+stock.Change+"</font>&#160;(<font class=\"up\">+"+stock.ChangePercent+"%</font>)";

			}else if(lstperdiff <= 0 && lstperdiff!=0){

				EL(code+'ltpimage').innerHTML='<img src="<c:url value="/resources/images/mdown1.gif"/>" WIDTH="30" HEIGHT="19" alt="*"/>';
				EL(code+'NetChange').innerHTML= "<font class=\"down\">"+stock.Change+"</font>&#160;(<font class=\"down\">"+stock.ChangePercent+"%</font>)";

			}else{
				EL(code+'ltpimage').innerHTML='<img src="<c:url value="/resources/images/neutral2.gif"/>" WIDTH="30" HEIGHT="19" alt="*"/>';
				EL(code+'NetChange').innerHTML="<font class=\"zero\">+"+stock.Change+"</font>&#160;(<font class=\"zero\">+"+stock.ChangePercent+"%</font>)";

			}
			EL(code+'PrevClose').innerHTML= stock.PrevClose;
			EL(code+'FiftyWeekHL').innerHTML= "<font class=\"fn17\">"+stock.FiftyTwoWeekHigh +" / "+stock.FiftyTwoWeekLow+"</font>";
	        EL(code+'DayHL').innerHTML= "<font class=\"fn17\">"+stock.High +" / "+stock.Low+"</font>";
	        EL(code+'Volume').innerHTML= "<font class=\"fn17\">"+stock.Volume+"</font>";

			if(oldltp != newltp){
				if(oldltp <= newltp ){
					startFadeDec(0,153,0,255,255,255,10,code+'ltp');

				}else if(oldltp >= newltp){
					startFadeDec(255,0,0,255,255,255,10,code+'ltp');
				}
				oldltp =newltp;
			}
	        /*
	        //updating the gainer & loser rows
	        if(code == Gainer) {
	            codeRow = $(code+'row');
	            if ( !(codeRow.className.indexOf('redbg') == -1) ) {
	                codeRow.className = codeRow.className.replace('redbg', 'greenbg');
	            } else {
	                if ( codeRow.className.indexOf('greenbg') == -1 ) {
	                    codeRow.className += " greenbg";
	                }
	            }
	        } else if(code == Loser) {
	            codeRow = $(code+'row');
	            if ( !(codeRow.className.indexOf('greenbg') == -1) ) {
	                codeRow.className = codeRow.className.replace('greenbg', 'redbg');
	            } else {
	                if ( codeRow.className.indexOf('redbg') == -1 ) {
	                    codeRow.className += " redbg";
	                }
	            }
	        } else {
	            codeRow = $(code+'row');
	            codeRow.className = codeRow.className.replace('greenbg', '');
	            codeRow.className = codeRow.className.replace('redbg', '');
	        }*/
//		}

	}
	function marketWatchMenuWork(action, el, pos) {
		var cmpCode=$(el).attr("id");
	    switch (action) {
	      	    case "delete":
	            {
	                location.href ='<c:url value="/wm/watchlist/delete?id="/>'+ cmpCode;
	                break;
	            }
	      	  case "transact":
	            {
	        		location.href ='<c:url value="/wm/equity?symbol="/>'+ cmpCode;
	                break;
	            }
	
	    }
	} 
	
	
</script>
<table id="tbl_marketWatch" class="tablesorter">
	<thead>
		<tr>
			<th>Company</th>
			<th>Last Traded Price</th>
			<th>Change</th>
			<th>Prev. Close</th>
			<th>Day H/L</th>
			<th>52wk H/L</th>
			<th>Volume</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="fav" items="${cmpList}" varStatus="status">
			<tr id="${fav.symbol}">
				<td>${fav.name}</td>
				<td>
					<span id="${fav.symbol}ltp"></span>
					<span id="${fav.symbol}ltpimage" style="float: right;">
						<img width="30" height="19" alt="*" src="<c:url value="/resources/images/mup1.gif"/>">
					</span>
				</td>
				<td id="${fav.symbol}NetChange"></td>
				<td id="${fav.symbol}PrevClose"></td>
				<td id="${fav.symbol}DayHL"></td>
				<td id="${fav.symbol}FiftyWeekHL"></td>
				<td id="${fav.symbol}Volume"></td>
			</tr>
		</c:forEach>
		
	</tbody>
</table>
<ul id="marketWatchMenu" class="contextMenu">
	<li class="favorite"><a href="#transact">Buy/Sell</a></li>
	<li class="delete  separator"><a href="#delete">Delete</a></li>
</ul>	