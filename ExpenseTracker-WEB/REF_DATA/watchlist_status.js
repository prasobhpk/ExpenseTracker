var compid = '17023928|17023929';
var companylist = false;
var bseltpval = "";
var nseltpval = "";
var onloadstatus = 0;
var blurflag = "false";
var oldtime = "";
enddate = new Date();

function setToProcessBSE(){
	var sleeptime = parseInt(getEl("companyhit").value) * 1000;
	st = setTimeout("updateWatchList()",sleeptime);
}
function setTimeInterval(){
	st = setTimeout("updateWatchList()",(1 * 1000));
}
function updateWatchList(call) {
	if(!companylist) {
        if(typeof document.sw_add1.compcode != 'undefined') {
            if(document.sw_add1.compcode.length > 1) {
                for(var i=0; i < document.sw_add1.compcode.length; i++) {
                    if(!companylist) {
                        companylist = document.sw_add1.compcode[i].value;
                    } else {
                        companylist = companylist + "|" + document.sw_add1.compcode[i].value;
                    }
                }
            } else {
                companylist = document.sw_add1.compcode.value;
            }
            companylist = compid + '|' + companylist;
        } else {
            companylist = compid;
        }
	}
    
    $marketstat = getEl("marketstat");
	if(typeof $marketstat != 'undefined' && $marketstat.value == 1) {
		if(call == 1) {
			var chartendtime = getEl("chartendtm").value;
			var endtime = chartendtime.split(":");
			enddate.setHours(endtime[0],endtime[1],endtime[2]);
			var ifrm = document.getElementById("stockwatchiframe");
			if(typeof ifrm !== 'undefined' && typeof ifrm.contentWindow.getStatus === 'function'){
                var dataUrl = '/money1/watchlist_status.php?companylist='+companylist;
				ifrm.contentWindow.getStatus(dataUrl,"watchListProcess"); //Call the function available in the iframe window from the parent window.
			} else {
				setTimeInterval();
			}
		} else {
			if(blurflag == "false") {
				var callflag = parseInt(getEl("urlcallflag").value);
				var timediff = parseInt(getEl("calltimediff").value);
				if((callflag==1) || (callflag==2)){
					if((callflag==1) && (timediff>0)){
						timediff--;
						getEl("calltimediff").value=timediff;
						setTimeInterval();
					}else{
						var ifrm = document.getElementById("stockwatchiframe");
						if(typeof ifrm !== 'undefined' && typeof ifrm.contentWindow.getStatus === 'function'){
                            var dataUrl = '/money1/watchlist_status.php?companylist='+companylist;
							ifrm.contentWindow.getStatus(dataUrl,"watchListProcess"); //Call the function available in the iframe window from the parent window.
						}else{
							setTimeInterval();
						}
					}
				}else{
					return;
				}
			}else{
				setTimeInterval();
			}
		}
	}
}
function trim(B) { B=B.replace(/^\s*/,"").replace(/\s*$/,""); return B; }
function watchListProcess(xhrObj){
	var json = xhrObj.responseText;
	if(json.indexOf("Error") && json != ""){
	var indexData = eval(json);
    
	for (row in indexData[1])
        {
            if (typeof indexData[1][row].CompanyCode != 'undefined') {
                if (null !== indexData[1][row].CompanyCode) {
                    (indexData[1][row].CompanyCode == 17023928)?
                        updateSensex(indexData[1][row]):(indexData[1][row].CompanyCode == 17023929)?
                                updateNifty(indexData[1][row]):updateCompany(indexData[1][row],indexData[0].gainer,indexData[0].loser);
                }
            }
        }
	}
	setToProcessBSE();
}
function updateCompany(CompanyObj,Gainer,Loser) {
	var code = "";

	if(typeof(CompanyObj.CompanyCode) == 'string') {
		code = CompanyObj.CompanyCode.replace('_','.');
	} else {
		code = CompanyObj.CompanyCode;
	}
//	if((code != "17023928") && (code != "17023929") && (code != "0")){
		var lstperdiff = CompanyObj.PercentageDiff;
		var newlstval = CompanyObj.LastTradedPrice;
		var oldltpele = getEl(code+'ltp');
		var oldltp = oldltpele.innerHTML;
		var newltp = CompanyObj.LastTradedPrice;
		getEl(code+'ltp').innerHTML = CompanyObj.LastTradedPrice;

		if(lstperdiff >= 0 && lstperdiff!=0){

			getEl(code+'ltpimage').innerHTML = "<img src=\"http://im.rediff.com/money/images/mup1.gif\" WIDTH=\"30\" HEIGHT=\"19\" alt=\"*\"/>";
			getEl(code+'NetChange').innerHTML = "<font class=\"up\">+"+CompanyObj.Change+"</font>&#160;(<font class=\"up\">+"+CompanyObj.ChangePercent+"%</font>)";

		}else if(lstperdiff <= 0 && lstperdiff!=0){

			getEl(code+'ltpimage').innerHTML =  "<img src=\"http://im.rediff.com/money/images/mdown1.gif\" WIDTH=\"30\" HEIGHT=\"19\" alt=\"*\"/>";
			getEl(code+'NetChange').innerHTML = "<font class=\"down\">"+CompanyObj.Change+"</font>&#160;(<font class=\"down\">"+CompanyObj.ChangePercent+"%</font>)";

		}else{
			getEl(code+'ltpimage').innerHTML =  "<img src=\"http://im.rediff.com/money/images/neutral2.gif\" WIDTH=\"30\" HEIGHT=\"19\" alt=\"*\"/>";
			getEl(code+'NetChange').innerHTML = "<font class=\"zero\">+"+CompanyObj.Change+"</font>&#160;(<font class=\"zero\">+"+CompanyObj.ChangePercent+"%</font>)";

		}
		getEl(code+'PrevClose').innerHTML = CompanyObj.PrevClose;
		getEl(code+'FiftyWeekHL').innerHTML = "<font class=\"fn17\">"+CompanyObj.FiftyTwoWeekHigh +" / "+CompanyObj.FiftyTwoWeekLow+"</font>";
        getEl(code+'DayHL').innerHTML = "<font class=\"fn17\">"+CompanyObj.High +" / "+CompanyObj.Low+"</font>";
        getEl(code+'Volume').innerHTML = "<font class=\"fn17\">"+CompanyObj.Volume+"</font>";

		if(oldltp != newltp){
			if(oldltp <= newltp ){
				startFadeDec(0,153,0,255,255,255,10,code+'ltp');

			}else if(oldltp >= newltp){
				startFadeDec(255,0,0,255,255,255,10,code+'ltp');
			}
			oldltp =newltp;
		}
        
        //updating the gainer & loser rows
        if(code == Gainer) {
            codeRow = getEl(code+'row');
            if ( !(codeRow.className.indexOf('redbg') == -1) ) {
                codeRow.className = codeRow.className.replace('redbg', 'greenbg');
            } else {
                if ( codeRow.className.indexOf('greenbg') == -1 ) {
                    codeRow.className += " greenbg";
                }
            }
        } else if(code == Loser) {
            codeRow = getEl(code+'row');
            if ( !(codeRow.className.indexOf('greenbg') == -1) ) {
                codeRow.className = codeRow.className.replace('greenbg', 'redbg');
            } else {
                if ( codeRow.className.indexOf('redbg') == -1 ) {
                    codeRow.className += " redbg";
                }
            }
        } else {
            codeRow = getEl(code+'row');
            codeRow.className = codeRow.className.replace('greenbg', '');
            codeRow.className = codeRow.className.replace('redbg', '');
        }
//	}
}
function updateSensex(SensexObj)
{
	var ltpnew = SensexObj.LastTradedPrice;
	if(trim(bseltpval) == ""){
		bseltpval = SensexObj.LastTradedPrice;
		onloadstatus = 1;
	}
	var perdiff = SensexObj.PercentageDiff;
	getEl('BseIndex').innerHTML = SensexObj.LastTradedPrice;
	if(perdiff >= 0 && perdiff!=0){
		getEl('BseIcon').src = "http://im.rediff.com/money/images/mup1.gif";
		getEl('BseChange').innerHTML = '+' + SensexObj.Change;
        getEl('BseChange').className = 'up';
		getEl('BseChangePercent').innerHTML = SensexObj.ChangePercent+"%";
	}else if(perdiff <= 0 && perdiff!=0){
		getEl('BseIcon').src = "http://im.rediff.com/money/images/mdown1.gif";
		getEl('BseChange').innerHTML = SensexObj.Change;
        getEl('BseChange').className = 'down';
		getEl('BseChangePercent').innerHTML = SensexObj.ChangePercent+"%";
	}else{
		getEl('BseIcon').src = "http://im.rediff.com/money/images/neutral2.gif";
		getEl('BseChange').innerHTML = SensexObj.Change;
        getEl('BseChange').className = '';
		getEl('BseChangePercent').innerHTML = SensexObj.ChangePercent+"%";
	}

	var lttime = SensexObj.LastTradedTime;
	var temp = new Array();
	temp = lttime.split(",");

	getEl('BseCurrentDate').innerHTML = temp[0]+",&#160;";
	if(oldtime==""){
		oldtime = temp[1];
	}	
	newtime = temp[1];
	getEl('BseCurrentTime').innerHTML = newtime;

	var temptime = new Array();
	temptime = newtime.split(":");
	tickerdate = new Date();
	tickerdate.setHours(temptime[0],temptime[1],temptime[2]);
	if ((IsDateGreater(tickerdate,enddate)) || (IsDateGreater(new Date(),enddate))) {
		getEl("urlcallflag").value = 0;
	}
	if(bseltpval != ltpnew){
		if(bseltpval <= ltpnew){
			startFadeDec(0,153,0,255,255,255,10,'BseIndex');
		}else if(bseltpval >= ltpnew){
			startFadeDec(255,0,0,255,255,255,10,'BseIndex');
		}
		bseltpval = ltpnew;
		onloadstatus = 0;
	}else{
		if(onloadstatus == 1){
			if((perdiff >= 0) && (perdiff != 0)){
				startFadeDec(0,153,0,255,255,255,10,'BseIndex');
			}else if((perdiff <= 0) && (perdiff != 0)){
				startFadeDec(255,0,0,255,255,255,10,'BseIndex');
			}
	}
		onloadstatus=0;
	}
	if(oldtime != newtime){
		startFadeDec(0,0,0,255,255,255,10,'BseCurrentTime');
		oldtime = newtime;
	}
}
function updateNifty(NiftyObj)
{
	var ltpnew = NiftyObj.LastTradedPrice;
	if(trim(nseltpval) == ""){
		nseltpval = NiftyObj.LastTradedPrice;
		onloadstatus = 1;
	}
	var perdiff = NiftyObj.PercentageDiff;
	getEl('NseIndex').innerHTML = NiftyObj.LastTradedPrice;
    
    
    if(perdiff >= 0 && perdiff!=0){
		getEl('NseIcon').src = "http://im.rediff.com/money/images/mup1.gif";
		getEl('NseChange').innerHTML = '+' + NiftyObj.Change;
        getEl('NseChange').className = 'up';
		getEl('NseChangePercent').innerHTML = NiftyObj.ChangePercent+"%";
	}else if(perdiff <= 0 && perdiff!=0){
		getEl('NseIcon').src = "http://im.rediff.com/money/images/mdown1.gif";
		getEl('NseChange').innerHTML = NiftyObj.Change;
        getEl('NseChange').className = 'down';
		getEl('NseChangePercent').innerHTML = NiftyObj.ChangePercent+"%";
	}else{
		getEl('NseIcon').src = "http://im.rediff.com/money/images/neutral2.gif";
		getEl('NseChange').innerHTML = NiftyObj.Change;
        getEl('NseChange').className = '';
		getEl('NseChangePercent').innerHTML = NiftyObj.ChangePercent+"%";
	}

	if(nseltpval!=ltpnew){
		if(nseltpval <= ltpnew){
			startFadeDec(0,153,0,255,255,255,10,'NseIndex');
		}else if(nseltpval >= ltpnew){				
			startFadeDec(255,0,0,255,255,255,10,'NseIndex');
		}
		nseltpval = ltpnew;
		onloadstatus=0;
	}else{
		if(onloadstatus ==1){
			if((perdiff >= 0) && (perdiff!=0)){				
				startFadeDec(0,153,0,255,255,255,10,'NseIndex');
			}else if((perdiff <= 0) && (perdiff!=0)){
				startFadeDec(255,0,0,255,255,255,10,'NseIndex');
			}
	}
		onloadstatus=0;
	}
}

if (/*@cc_on!@*/false) { // check for Internet Explorer
	document.onfocusin = focusText;
	document.onfocusout = blurText;
} else {
	window.onfocus = focusText;
	window.onblur = blurText;
}

function focusText() {
        blurflag = "false";
}
function blurText() {
        blurflag = "true"
}
function frmonblur() {
        blurflag = "true";
}
function shoWait(){}
function IsDateGreater(DateValue1, DateValue2)
{
    var MinDiff;
    Date1 = new Date(DateValue1);
    Date2 = new Date(DateValue2);
    MinDiff = Math.floor((Date1.getTime() - Date2.getTime())/(1000*60*60));
    if(MinDiff > 0)
        return true;
    else
        return false;
}