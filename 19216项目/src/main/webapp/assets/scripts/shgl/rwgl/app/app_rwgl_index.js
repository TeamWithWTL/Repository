var basePath = $("#basePath").val();

$(document).ready(function(){
	var roleCodes = $("#roles").val();
	if(roleCodes.indexOf("12")!=-1){
		$("#toRwjz").show();
	}
	if(roleCodes.indexOf("12")!=-1){
		$("#toDbrw").hide();
	}
	if(roleCodes.indexOf("12")!=-1 && roleCodes.indexOf("13")!=-1){
		$("#toDbrw").hide();
	}else{
		if(roleCodes.indexOf("12")!=-1){
			$("#toDbrw").hide();
		}else{
			$("#toDbrw").show();
		}
	}
	if(roleCodes.indexOf("01")!=-1){
		$("#toMyxf").hide();
	}else{
		$("#toMyxf").show();
	}
});


/*************************App--任务管理--待办任务--跳转***************************/
document.getElementById('toDbrw').addEventListener('tap',function(){
	
	  mui.openWindow({
		    url: basePath + "/app/shgl/appRwgl/toRwgl.do", 
		    id:'toRwgl.do'
		  });
	});
/*************************App--任务管理--我的下发--跳转***************************/
document.getElementById('toMyxf').addEventListener('tap',function(){
	
	  mui.openWindow({
		    url: basePath + "/app/shgl/appRwgl/myXF.do", 
		    id:'myXF.do'
		  });
	});
/*************************App--任务管理--处理记录--跳转***************************/
document.getElementById('toCljl').addEventListener('tap',function(){
	
	  mui.openWindow({
		    url: basePath + "/app/shgl/appRwgl/toFinishRwgl.do", 
		    id:'index.do'
		  });
	});
/*************************App--任务管理--任务汇总--跳转***************************/
document.getElementById('toRwjz').addEventListener('tap',function(){
	
	  mui.openWindow({
		    url: basePath + "/app/shgl/appRwgl/toRwhz.do", 
		    id:'toRwhz.do'
		  });
	});
