$(function() {
    $('#fader img:not(:first)').hide();
   $('#fader img').css('position', 'absolute');
   
    $('#fader img').css('left', '50%');
	 $('#fader img').css('top', '0px');
    $('#fader img').each(function() {
        var img = $(this);
        $('<img>').attr('src', $(this).attr('src')).load(function() {
            img.css('margin-left', -this.width / 2 + 'px');
        });
    });

    var pause = false;
    
    function fadeNext() {
        $('#fader img').first().fadeOut().appendTo($('#fader'));
        $('#fader img').first().fadeIn();
    }

    function fadePrev() {
        $('#fader img').first().fadeOut();
        $('#fader img').last().prependTo($('#fader')).fadeIn();
    }

    function doRotate() {
        if(!pause) {
            fadeNext();
        }    
    }
    
    var rotate = setInterval(doRotate, 6000);
});
