$(document).ready(function(){
//--------------------------------------------	
		$("#commentlistgf li").contextMenu({
	    menu: 'myMenu'
	    }, function(action, el, pos) {
		  var uType=$("#uType").val();
		  var parentName=$(el).attr("title");
		  var id = $(el).attr("id");
		  //alert(parentName);
		  switch (action) {
		  	case 'addToFavorites':
		  		  //alert($(el).attr("name"));
		  		  var flag = 0;
		  		  var msg='';
		  		  if($(el).attr("name")!=0){
	                var len = document.getElementById('hiddenSelectForFileMenu').options.length;
	        	    
	                for(var i=0;i<len;i++ ){
	           		  if(($(el).attr("id"))==(document.getElementById('hiddenSelectForFileMenu').options[i].value)){
	              		 flag = 1;
	              		 break;
	          		   }
	                 }
	               if(len<5){
	          	    	if(flag==1){
	          	    	 showMsg('Selected item already exist in favorites list!');
				         
	           		    }else{
				         location.href ='addToFavorites?menuId=' + id;
				         //msg='Favorite is added';
	           		   
	           		    }
	               }else{
	               // msg='Only five favorites are allowed!';
	                showMsg('Only five favorites are allowed!');
	          	      //alert('Only five favorites are allowed');
	               }
	             }else{
	            // msg='Only Reports or Xcelsius can be added to favorites!';
	             showMsg('Only Reports or Xcelsius can be added to favorites!');
				   //alert('you can add only files to favorites');	
	             }
		  		//showMsg(msg);
		  	break;

            case 'edit':
            
              location.href ='editMenu?menuId=' + id;
              //alert(removeMenu?menuId=' + id);
            
            break;
            
            case 'delete':
              var ftype=$(el).attr("name");
              var msg='';
              switch(ftype){
              case '0':
                msg='Are you sure to delete the folder?';
                break;
              case '1':
                msg='Are you sure to delete the Xcelcious?';
                break;
              case '2':
                msg='Are you sure to delete the Report?';
                break;
              }
             // alert('removeMenu?menuId=' + id);
              var dec=confirm(msg);
              if(dec)
                location.href ='removeMenu?menuId=' + id;
                           //alert(action);
            
            break;
            
            case 'newFolder':
            
              location.href ='viewAddSubMenu?menu.parentMenuId='+id+'&parentMenu='+parentName+'&menu.type=0';
            
            break;
            
            case 'newReport':
            
              location.href ='viewAddSubMenu?menu.parentMenuId='+id+'&parentMenu='+parentName+'&menu.type=2';
            
            break;
            
            case 'newDashboard':
            
              location.href ='viewAddSubMenu?menu.parentMenuId='+id+'&parentMenu='+parentName+'&menu.type=1';
            
            break;
            
		  	default:
		  	break;
		  }

	    });
		$("#commentlist li").contextMenu({
			  menu: 'myMenu'
			  }, function(action, el, pos) {
				  alert($(el).attr("id"));
		});
	//-------------------------------------------------------------------------------------------	  
});