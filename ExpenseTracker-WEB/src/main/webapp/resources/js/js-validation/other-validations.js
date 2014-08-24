/**
 * @author PrasobhP
 */
/*
 This file contains validations that are too specific
*/
(function($){
	if($.validationEngineLanguage == undefined || $.validationEngineLanguage.allRules == undefined )
		alert("Please include other-validations.js AFTER the translation file");
	else {
		$.validationEngineLanguage.allRules["postcodeUK"] = {
		        // UK zip codes
		        "regex": /^([A-PR-UWYZ0-9][A-HK-Y0-9][AEHMNPRTVXY0-9]?[ABEHMNPRVWXY0-9]? {1,2}[0-9][ABD-HJLN-UW-Z]{2}|GIR 0AA)$/,
				"alertText": "* Invalid postcode"
		};
		$.validationEngineLanguage.allRules["onlyLetNumSpec"] = {
				// Good for database fields
				"regex": /^[0-9a-zA-Z_-]+$/,
				"alertText": "* Only Letters, Numbers, hyphen(-) and underscore(_) allowed"
		};
		// more validations may be added after this point
	}
})(jQuery);
