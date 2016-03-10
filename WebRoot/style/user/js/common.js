$(function(){
	$("#tabMenu li").click(function(){
		$(this).addClass("on").siblings().removeClass("on");
		var index = $(this).index();
		$(".infoBox .comSection").eq(index).show().siblings().hide();
	});
	$(".close").click(function(){
		$(this).parents("li").remove();
	});
	//权限锁定js
	$(".suo").click(function(){
		var flag = $(this).attr("src")=="img/common/suo02.png";
		if(flag){
			$(this).attr("src","img/common/suo01.png");
		}
		else {
			$(this).attr("src","img/common/suo02.png");
		}
	});
	$("#header .hUl li:has(ul)").hover(function(){
		$(this).find(".userOperating").slideDown();
	},function(){
		$(this).find(".userOperating").slideUp();
	});
	$("#arrowImg").click(function(){
		var icon = $(this).attr("src") =="../img/common/arrow-left.png";
		if(icon){
			//$("#sideBar").hide();
			$("#sideBar").slideLeftHide(400);
			$(this).attr("src","../img/common/arrow-right.png").animate({"left":"0px"},400);
			$("body").animate({"padding-left":"0px"},400);
		}
		else
		{
			//$("#sideBar").show();
			$("#sideBar").slideLeftShow(400);
			//$(this).attr("src","img/common/arrow-left.png").css("left","24px");
			//$("body").css("padding-left","60px");
			$(this).attr("src","../img/common/arrow-left.png").animate({"left":"24px"},400);
			$("body").animate({"padding-left":"60px"},400);
		}
	});
	jQuery.fn.slideLeftHide = function( speed, callback ) {  
        this.animate({  
            width : "hide",  
            paddingLeft : "hide",  
            paddingRight : "hide",  
            marginLeft : "hide",  
            marginRight : "hide"  
        }, speed, callback );  
    };  
    jQuery.fn.slideLeftShow = function( speed, callback ) {  
        this.animate({  
            width : "show",  
            paddingLeft : "show",  
            paddingRight : "show",  
            marginLeft : "show",  
            marginRight : "show"  
        }, speed, callback );  
    };  

});