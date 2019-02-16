/**
 * 首页
 */
var basePath;
var pageNumber;
var isInit =false;
$(function() {
	basePath = $("#basePath").val();
	initSqfwApp();
});



/**
 * app-社会服务--社区服务-分页
 */
function initSqfwApp() {
	
	if(isInit){
		mui('#refreshContainer').pullRefresh().refresh(true);
		mui('#refreshContainer').pullRefresh().enablePullupToRefresh();
	}
	
	pageNumber = 1;
	var title = $("#titleId").val();

//	var msgIndex = top.layer.msg('数据加载中', {
//		icon : 16,
//		shade : [ 0.5, '#999999' ],
//		time : 0
//	});
	$("#table-container").load(basePath + "/app/dflz/appExpo/toExpo.do", {
		'ajaxCmd' : 'table',
		'pageNumber' : pageNumber,
		'title' : title,
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
function FirstInitData() {
	initSqfwApp();
	mui('#refreshContainer').pullRefresh().refresh(true);//重置
}

/**
 * mui分页
 */
function pullupRefresh() {
	// ==================上拉加载
	// 获取下一页数据
	setTimeout(function() {
		getContentNext();// 具体取数据的方法
	}, 100);
}

function getContentNext() {

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
	
	var title = $("#titleId").val();

	$.ajax({
				async : true,
				type : 'POST',
				url : basePath + "/app/dflz/appExpo/expoNextContent.do",
				data : {
					'pageNumber' : pageNumber,
					'title' : title,
				},
				dataType : 'json',
				error : function(request) {

				},
				success : function(data) {
//					top.layer.close(msgIndex);
					var listObj = data.result;
					for(var i = 0; i<listObj.length; i++){
						value = listObj[i];
						var html ='<li class="mui-table-view-cell mui-media">'+
						'<a href="javascript:void(0)"  name="'+value.titleId+'">'+
						
						'<div class="mui-media-body">'+
							'<span class="mui-ellipsis-3">'+value.titleName+'</span>'+
							'<dt>'+value.createTime+'</dt>'+
						'</div>'+
					'</a>'+
				'</li>'
						
						mui('#table-container')[0].insertAdjacentHTML('beforeend', html);
					}
				}
			});
}

/**
 * 跳转详情
 * @param id
 */

mui("#table-container").on('tap','a',function(){
	 var zwxxId = this.getAttribute("name");
		 mui.openWindow({
			    url: basePath + "/app/dflz/appExpo/goView.do?id="+zwxxId, 
			    id:'goView'
			  });
});