var basePath = $("#basePath").val();
var pageNumber;
var isInit =false;
$(function(){
console.log("初始化....");
initDate();

});




function initDate() {
	if(isInit){
		mui('#refreshContainer').pullRefresh().refresh(true);
		mui('#refreshContainer').pullRefresh().enablePullupToRefresh();
	}
	pageNumber = 1;

//	var msgIndex = top.layer.msg('数据加载中', {
//		icon : 16,
//		shade : [ 0.5, '#999999' ],
//		time : 0
//	});
	$("#table-container").load(basePath + "/app/sjgl/myReport.do", {
		'ajaxCmd' : 'table',
		'pageNumber' : pageNumber
	}, function() {
//		top.layer.close(msgIndex);
		window.pageCnts = $("#pageCnts").val();
		window.pageSize = $("#pageSize").val();
		if(!isInit){
			isInit =true;
			//第一页加载完了 初始化拖拽
			mui.init({
				pullRefresh: {
					container: "#refreshContainer", //下拉刷新容器标识，querySelector能定位的css选择器均可，比如：id、.class等
					up: {
						height:50,//可选.默认50.触发上拉加载拖动距离
						range:'50px', //可选 默认100px,控件可下拉拖拽的范围
						contentrefresh: "正在加载...", //可选，正在加载状态时，上拉加载控件上显示的标题内容
						contentnomore: '没有更多数据了', //可选，请求完毕若没有更多数据时显示的提醒内容；
						callback: pullupRefresh
					}
				}
			});
			mui('#refreshContainer').pullRefresh().refresh(true);
			mui('#refreshContainer').pullRefresh().enablePullupToRefresh(false);
			}
	});
}
//返回界面
mui("#header").on('tap','a',function(){
	  window.location.href = $("#basePath").val() + "/app/home/index.do";
});
//添加事件按钮
mui("#addReport").on('tap','a',function(){
//	  mui.openWindow({
//		    url: basePath + '/app/sjgl/addReportPage.do', 
//		    id:'addReport'
//		  });
	  window.location.href = $("#basePath").val() + "/app/sjgl/addReportPage.do";
});

//查看
mui("#table-container").on('tap','.mui-card',function(){
	var id =  this.getAttribute("id");
	  mui.openWindow({
		    url: basePath + '/app/sjgl/reportView.do?id='+id, 
		    id:'viewReport'
		  });
});
////返回刷新当前界面
//window.addEventListener('refresh', function(e){//执行刷新
//	alert("shuaxin");
//    location.reload();
//});

/**
 * mui分页
 */
function pullupRefresh() {
	// ==================上拉加载
	console.log("pullupRefresh");
	// 获取下一页数据
	setTimeout(function() {
		getContentNext();// 具体取数据的方法
	}, 100);
}

function getContentNext(){
	pageNumber++;
	if (pageCnts < pageNumber) {
		mui('#refreshContainer').pullRefresh().endPullupToRefresh(true); 
		return;
	}
	mui('#refreshContainer').pullRefresh().endPullupToRefresh(false);
//	var msgIndex = top.layer.msg('数据加载中', {
//		icon : 16,
//		shade : [ 0.5, '#999999' ],
//		time : 0
//	});
	

	$.ajax({
				async : true,
				type : 'POST',
				url : basePath + "/app/sjgl/appGetMoreReprot.do",
				data : {
					'pageNumber' : pageNumber,
				},
				dataType : 'json',
				error : function(request) {

				},
				success : function(data) {
//					top.layer.close(msgIndex);
					var listObj = data.data;
					for(var i =0;i<listObj.length;i++){
					var	value = listObj[i];
					 var html = [];
					 html.push(	'<div class="mui-card" id="'+value.id+'">');
					 html.push(	'<div class="mui-card-header">');
					 html.push(	'<div class="mui-checkbox">');
					 html.push(	'<input name="checkbox1" value="Item 1" type="checkbox">');
					 html.push(	'</div>'+value.title+'</div>');
					 html.push(	'<div class="mui-card-content">');
					 html.push(	'<div class="mui-card-content-inner">');
					 html.push(	'<p class="mui-ellipsis-2" style="color:#666;">"'+value.content+'"</p>');
					 html.push(	'<p>"'+value.showDate+'"</p>');
					 html.push(	'</div>');
					 html.push(	'</div>');
					 html.push(	'<div class="mui-card-footer">');
					 html.push('<a href="javascript:void(0)" id="'+value.id+'"><i class="icon ion-android-list"></i> 详情</a>');
					 html.push(	'</div>');
					 html.push(	'</div>');
					$("#table-container").append(html.join(""));
					}
				}
			});
}
