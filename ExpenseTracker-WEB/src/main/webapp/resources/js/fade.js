var numSteps = 0;
var startingRed = 0;
var startingGreen = 0;
var startingBlue = 0;
var endingRed = 0;
var endingGreen = 0;
var endingBlue = 0;
var deltaRed = 0;
var deltaGreen = 0;
var deltaBlue = 0;
var currentRed = 0;
var currentGreen = 0;
var currentBlue = 0;
var currentStep = 0;
var timerID = 0;
var didtag = "";
var forcolor = "";
function startFadeDec(startR, startG, startB, endR, endG, endB, nSteps, idtag) {
	currentRed = startingRed = parseInt(startR, 10);
	currentGreen = startingGreen = parseInt(startG, 10);
	currentBlue = startingBlue = parseInt(startB, 10);
	endingRed = parseInt(endR, 10);
	endingGreen = parseInt(endG, 10);
	endingBlue = parseInt(endB, 10);
	numSteps = parseInt(nSteps, 10);
	didtag = idtag;
	deltaRed = (endingRed - startingRed) / numSteps;
	deltaGreen = (endingGreen - startingGreen) / numSteps;
	deltaBlue = (endingBlue - startingBlue) / numSteps;
	currentStep = 0;
	forcolor = "#FFFFFF";
	fade();
}
function fade() {
	currentStep++;
	if (currentStep < 3) {
		var hexRed = decToHex(currentRed);
		var hexGreen = decToHex(currentGreen);
		var hexBlue = decToHex(currentBlue);
		var color = "#" + hexRed + "" + hexGreen + "" + hexBlue + "";
		var objid = eval("document.getElementById('" + didtag + "')");
		objid.style.background = color;
		objid.style.color = forcolor;
		if (didtag == "CurrentTime") {
			objid.style.fontWeight = "bold";
		}
		if (didtag == "index" && currentStep == 2) {
			objid.style.background = "";
		}
		currentRed = 255;
		currentGreen = 255;
		currentBlue = 255;
		forcolor = "#000000";
		setTimeout("fade1('" + didtag + "')", 3000);
	}
}
function fade1(edtag) {
	currentStep++;
	currentRed1 = 255;
	currentGreen1 = 255;
	currentBlue1 = 255;
	forcolor1 = "#000000";
	var hexRed = decToHex(currentRed1);
	var hexGreen = decToHex(currentGreen1);
	var hexBlue = decToHex(currentBlue1);
	var color = "#" + hexRed + "" + hexGreen + "" + hexBlue + "";
	var objid = eval("document.getElementById('" + edtag + "')");
	objid.style.background = "";
	objid.style.color = forcolor1;
	if (objid.id == "CurrentTime") {
		objid.style.fontWeight = "normal";
	}
	if (objid.id == "index" && currentStep == 2) {
		objid.style.background = "";
	}
}
function decToHex(decNum) {
	decNum = Math.floor(decNum);
	var decString = "" + decNum;
	for ( var i = 0; i < decString.length; i++) {
		if (decString.charAt(i) >= '0' && decString.charAt(i) <= '9') {
		} else {
			alert(decString
					+ " is not a valid decimal number because it contains "
					+ decString.charAt(i));
			return decNum;
		}
	}
	var result = decNum;
	var remainder = "";
	var hexNum = "";
	var hexAlphabet = new Array("0", "1", "2", "3", "4", "5", "6", "7", "8",
			"9", "A", "B", "C", "D", "E", "F");
	while (result > 0) {
		result = Math.floor(decNum / 16);
		remainder = decNum % 16;
		decNum = result;
		hexNum = "" + hexAlphabet[remainder] + "" + hexNum;
	}
	;
	if (hexNum.length == 1)
		hexNum = "0" + hexNum;
	else if (hexNum.length == 0)
		hexNum = "00";
	return hexNum;
}
function fadeRandom() {
	startFadeDec(sR, sG, sB, eR, eG, eB, 30);
}