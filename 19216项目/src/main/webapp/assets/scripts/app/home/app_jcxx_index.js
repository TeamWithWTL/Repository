var basePath = $("#basePath").val();

$(document).ready(function(){
	var roles = $("#roles").val();
	if(roles.indexOf("01")!=-1){
		$("#toSqglPage").hide();
		$("#toFwzglPage").hide();
		$("#toWgglPage").show();
		$("#toXqglPage").show();
		$("#toLyglPage").show();
		$("#toJmxxPage").show();
	}else if(roles.indexOf("02")!=-1){
		$("#toFwzglPage").show();
		$("#toWgglPage").show();
		$("#toXqglPage").show();
		$("#toLyglPage").show();
		$("#toJmxxPage").show();
	}else{
		$("#toSqglPage").show();
		$("#toFwzglPage").show();
		$("#toWgglPage").show();
		$("#toXqglPage").show();
		$("#toLyglPage").show();
		$("#toJmxxPage").show();
	}
});


/*************************App社区管理--跳转***************************/
document.getElementById('toSqglPage').addEventListener('tap',function(){
	
	  mui.openWindow({
		    url: basePath + "/app/shgl/sqgl/index.do", 
		    id:'index.do'
		  });
	});
/*************************App--服务站管理--跳转***************************/
document.getElementById('toFwzglPage').addEventListener('tap',function(){
	
	  mui.openWindow({
		    url: basePath + "/app/shgl/fwzgl/index.do", 
		    id:'index.do'
		  });
	});
/*************************App--网格管理--跳转***************************/
document.getElementById('toWgglPage').addEventListener('tap',function(){
	
	  mui.openWindow({
		    url: basePath + "/app/shgl/wggl/index.do", 
		    id:'index.do'
		  });
	});
/*************************App--小区信息--跳转***************************/
document.getElementById('toXqglPage').addEventListener('tap',function(){
	
	  mui.openWindow({
		    url: basePath + "/app/shgl/xqxx/index.do", 
		    id:'index.do'
		  });
	});
/*************************App--楼宇管理--跳转***************************/
document.getElementById('toLyglPage').addEventListener('tap',function(){
	
	  mui.openWindow({
		    url: basePath + "/app/shgl/lygl/index.do", 
		    id:'index.do'
		  });
	});
/*************************App--居民信息--跳转***************************/
document.getElementById('toJmxxPage').addEventListener('tap',function(){
	
	  mui.openWindow({
		    url: basePath + "/app/shgl/jmxx/index.do", 
		    id:'index.do'
		  });
	});
