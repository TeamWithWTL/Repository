/**
 * 住户列表App
 */
var basePath = $("#basePath").val();
/*********分页********************************/	
	$(function(){
	console.log("初始化....");
	initDate();
	
	});
	

	
function initDate() {

	var msgIndex = top.layer.msg('数据加载中', {
		icon : 16,
		shade : [ 0.5, '#999999' ],
		time : 0
	});
	$("#table-container").load(basePath + "/app/shgl/sqmy/wgyDcList.do",{'ajaxCmd': 'table', 'pageNumber': '1','sqmyWgyId':$("#sqmyWgyId").val()}, function() {
		top.layer.close(msgIndex);
	});
}
		
//查看住户详情页面
mui("#table-container").on('tap','a',function(){
	 var dcId = this.getAttribute("name"); 
		 mui.openWindow({
	     url: basePath + '/app/shgl/sqmy/dcView.do?&dcId='+dcId, 
    	 id:'goView'
			  });
		});

//添加住户按钮
mui("#addDc").on('tap','a',function(){
	var sqmyWgyId = $("#sqmyWgyId").val();
	  mui.openWindow({
		    url: basePath + '/app/shgl/sqmy/addDcPage.do?sqmyWgyId='+sqmyWgyId, 
		    id:'addDcPage'
		  });
});
