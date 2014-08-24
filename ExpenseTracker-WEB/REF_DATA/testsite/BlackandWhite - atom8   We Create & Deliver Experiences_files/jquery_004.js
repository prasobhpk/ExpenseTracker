function RepositionNav(){
	var windowHeight = $(window).height(); //get the height of the window
	var navHeight = $('#navigationMenu').height() / 2;
	var windowCenter = (windowHeight / 2); 
	var newtop = windowCenter - navHeight;
	$('#navigationMenu').css({"top": newtop}); //set the new top position of the navigation list
}


(function( $ ){
	$.fn.parallax = function(xpos, adjuster, inertia, outerHeight,backgroundPosition,  MarginLeft, MarginRight) {
			
function inView(pos, element){
	
	element.each(function(){ //for each selector, determine whether it's inview and run the move() function
		
		var element = $(this);
		var top = element.offset().top;
		
		if(outerHeight == true){
			var height = element.outerHeight(true);
		}else{
			var height = element.height();
		}
		
		//above & in view
		if(top + height >= pos && top + height - windowHeight < pos){
			move(pos, height);
		}
				
		//full view
		if(top <= pos && (top + height) >= pos && (top - windowHeight) < pos && top + height - windowHeight > pos){
			move(pos, height);
		}
		
		//below & in view
		if(top + height > pos && top - windowHeight < pos && top > pos){
			move(pos, height);
		}
	});
}		
		
		var $window = $(window);
		var windowHeight = $(window).height();
		var pos = $window.scrollTop(); //position of the scrollbar
		var $this = $(this);
		
		//setup defaults if arguments aren't specified
		if(xpos == null){xpos = "50%"}
		if(adjuster == null){adjuster = 0}
		if(inertia == null){inertia = 0.1}
		if(outerHeight == null){outerHeight = true}
		if(MarginLeft == null){MarginLeft = false}
		if(MarginRight == null){MarginRight = false}
		if(backgroundPosition == null){backgroundPosition = false}
		
		
		
		height = $this.height();
		if(backgroundPosition){
		$this.css({'backgroundPosition': newPos(xpos, outerHeight, adjuster, inertia)}); 
		
		}
		if(MarginLeft){
			$this.css({"margin-left" : newMarginLeft(xpos, outerHeight, adjuster, inertia)}); 
		}
		
		if(MarginRight){
			$this.css({"margin-right" : newMarginRight(xpos, outerHeight, adjuster, inertia)}); 
		}
		
		function newPos(xpos, windowHeight, pos, adjuster, inertia){
			return xpos + " " + Math.round((-((windowHeight + pos) - adjuster) * inertia)) + "px";
		}
		
		function newMarginLeft(xpos, windowHeight, pos, adjuster, inertia){
			return  Math.round((-((windowHeight + pos) - adjuster) * inertia)) + "px";
		}
		
		function newMarginRight(xpos, windowHeight, pos, adjuster, inertia){
			return  Math.round((((windowHeight + pos) - adjuster) * inertia)) + "px";
		}

		
		//function to be called whenever the window is scrolled or resized
		function move(pos, height){
			if(backgroundPosition){
				$this.css({'backgroundPosition': newPos(xpos, height, pos, adjuster, inertia)}); 
			}
				
			if(MarginLeft){
				$this.css({"margin-left" : newMarginLeft(xpos, height, pos, adjuster, inertia)}); 
			}
			
			if(MarginRight){
				$this.css({"margin-right" : newMarginRight(xpos, height, pos, adjuster, inertia)}); 
			}

				
		}
		
		$window.bind('scroll', function(){ //when the user is scrolling...
			var pos = $window.scrollTop(); //position of the scrollbar
			inView(pos, $this);
			
			//$('#pixels').html(pos);
		})
	}
})( jQuery );