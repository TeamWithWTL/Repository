var basePath = $("#basePath").val();




/*************************首页--个人中心*******************************/
document.getElementById('userInfoId').addEventListener('tap',function(){
	
	 mui.openWindow({
		    url: basePath + "/app/home/toUserInfo.do", 
		    id:'toUserInfo.do'
		  });
	});
/*************************App--热点新闻更多--跳转***************************/
document.getElementById('gotoRdxw').addEventListener('tap',function(){
	
	  mui.openWindow({
		    url: basePath + "/app/xtbg/rdxw/index.do", 
		    id:'index.do'
		  });
	});
/********************************app-热点新闻跳转--详情*******************************************/
mui("#rdxwLi").on('tap','a',function(){
	 var id = this.getAttribute("id");
		 mui.openWindow({
			    url: basePath + "/app/xtbg/rdxw/goView.do?id="+id, 
			    id:'goView'
			  });
});
mui("#slideBox").on('tap','a',function(){
	 var id = this.getAttribute("name");
		 mui.openWindow({
			    url: basePath + "/app/xtbg/rdxw/goView.do?id="+id, 
			    id:'goView'
			  });
});
/*************************App--廉政要闻更多--跳转***************************/
//document.getElementById('gotoLzyw').addEventListener('tap',function(){
//	
//	  mui.openWindow({
//		    url: basePath + "/app/dflz/appDzyw/toDzyw.do", 
//		    id:'toDzyw.do'
//		  });
//	});
/********************************app-廉政要闻跳转--详情*******************************************/
//mui("#lzywLi").on('tap','a',function(){
//	 var id = this.getAttribute("id");
//		 mui.openWindow({
//			    url: basePath + "/app/dflz/appDzyw/goView.do?id="+id, 
//			    id:'goView'
//			  });
//});
