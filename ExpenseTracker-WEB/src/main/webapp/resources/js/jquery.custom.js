/**
 * @author Prasobh PK
 * Custom pluggin methods
 */
(function($) {
//Removes last row from a table
$.fn.removeRow = function(selector) {
	$(selector).each(function(){
        if($('tbody', this).length > 0){
            $('tbody tr:last', this).remove();
        }else {
            $('tr:last', this).remove();
        }
    });
};

$.fn.addRow = function(selector,row) {
	$(selector).each(function(){
		if($('tbody', this).length > 0){
            $('tbody', this).append(row);
        }else {
            $(this).append(row);
        }
    });
};

})(jQuery);