/**
 * 系统二级主页
 */

var basePath = $("#basePath").val();
$(function(){
	var length = $("#accordion li:first ul").length;
	if(length>0){
		$("#secondMenuName").html($("#accordion li:first ul li:first").attr("name"));
	}else{
		$("#secondMenuName").html($("#accordion li:first").attr("name"));
	}
//	$("#accordion  li").click(function(event) {
//	    event.stopPropagation();    //  阻止事件冒泡
//		console.log("li");
//		var secondMenuName = $(this).attr("name");
//		$("#secondMenuName").html(secondMenuName);
//		$(this).parents("#accordion").find("li").removeClass("active");
//		$(this).addClass("active");
//		var _url = $(this).data("url");
//		console.log("url1:"+_url);
//		if(_url){
//			console.log("go url:"+_url);
//			goSecondFrame(basePath + "/" + _url);
//		}
//	});
//	$(".collapse a").click(function() {
//		console.log("a");
//		$(this).parents(".collapse").find("li").removeClass("active");
//		$("#accordion").find("a").removeClass("active");
//		$(this).addClass("active");
//	});
});

// Frame页面加载
function goSecondFrame(url){
	$("#secondMainFrame").attr('src', url);
}

//function chooseTaskTag(){
//	$("#402880cf5e0299d9015e02afb630000a").click();
//	$("#402880cf5e0299d9015e02b0762e000b").click();
//	$("#40286e815f993b35015f993cbf5e0000").find("a").click();
//}
function changeSecondMenu(url,name,codeId){
	console.log("li");
	$("#secondMenuName").html(name);
	$("#"+codeId).parents("#accordion").find("li").removeClass("active");
	$("#"+codeId).parents("#accordion").find("a").removeClass("active");
	$("#"+codeId).addClass("active");
	if(url){
		goSecondFrame(basePath + "/" + url);
	}
}
