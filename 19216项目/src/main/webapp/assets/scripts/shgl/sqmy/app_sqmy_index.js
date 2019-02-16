/**
 * 社情民意App
 */
var basePath = $("#basePath").val();
var pageNumber;
var isInit =false;

$(function(){
	initData();
});
//Table数据加载
function initData(){
	
	$("#table-container").load(basePath + "/app/shgl/sqmy/index.do",{
		'ajaxCmd': 'table', 
		'pageNumber': pageNumber
		}, function(){
		});
}


//返回界面
mui("#header").on('tap','a',function(){
	  window.location.href = basePath + "/app/home/index.do";
});

//点击进入调查列表页面
mui("#table-container").on('tap','a',function(){
	 var buttontype = this.getAttribute("buttontype");
	 var namevalue = this.getAttribute("name");
	 var wgyid = this.getAttribute("wgyid");
//	 top.layer.msg(buttontype, {shade : [ 0.5, '#999999' ]});
	 
	 if(buttontype =='view'){
//		 top.layer.msg(wgyid, {shade : [ 0.5, '#999999' ]});
//		 mui.openWindow({
//			    url: basePath+'/app/shgl/sqmy/gosqmyxq.do?&id='+namevalue,
//			    id:'goView'
//			  });
		 window.location.href = basePath + '/app/shgl/sqmy/gosqmyxq.do?wgyid='+wgyid+'&id='+ namevalue;
	 }
	 if(buttontype =='check'){
//		 top.layer.msg(wgyid, {shade : [ 0.5, '#999999' ]});
//		 mui.openWindow({
//			    url: basePath+'/app/shgl/sqmy/wgyDcList.do?sqmyWgyId='+namevalue,
//			    id:'goView'
//			  });
		 window.location.href = basePath + '/app/shgl/sqmy/wgyDcList.do?sqmyWgyId='+namevalue;
		 }
});


////点击查看社情民意详情
//mui("#table-container").on('tap','span',function(){
//	 var sqmy_id = this.getAttribute("name");
//	 var wgyid = this.getAttribute("wgyid");
//	 top.layer.msg(wgyid, {shade : [ 0.5, '#999999' ]});
//	 window.location.href = basePath + '/app/shgl/sqmy/gosqmyxq.do?wgyid='+wgyid+'&id='+ sqmy_id;
//});

