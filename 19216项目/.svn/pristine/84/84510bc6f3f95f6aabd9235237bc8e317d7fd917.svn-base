/**
 * App--社会管理--社区管理--首页_列表
 */
var basePath;
var pageNumber;
var isInit =false;
$(function() {
	basePath = $("#basePath").val();
	initData();
});

//Table数据加载
function initData(){
	
	if(isInit){
		mui('#refreshContainer').pullRefresh().refresh(true);
		mui('#refreshContainer').pullRefresh().enablePullupToRefresh();
	}
	
	pageNumber = 1;
	
	$("#table-container").load(basePath + "/app/shgl/sqgl/index.do",{
		'ajaxCmd': 'table', 
		'pageNumber': pageNumber, 
		'name': $("#name").val()
		}, function(){
			
			if($("#pageCnts").val() == undefined || $("#pageSize").val() == undefined){
				window.pageCnts = 0;
				window.pageSize = 0;
			}else{
				window.pageCnts = $("#pageCnts").val();
				window.pageSize = $("#pageSize").val();
			}
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

//跳转--详情
mui("#table-container").on('tap','#sqId',function(){
	 var sqId = this.getAttribute("name");
		 mui.openWindow({
			    url: basePath + "/app/shgl/sqgl/goView.do?id="+sqId, 
			    id:'goView'
			  });
});

/**
 * mui分页
 */
function pullupRefresh() {
	// 上拉加载--获取下一页数据
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
	
	var name = $("#name").val();

	$.ajax({
				async : true,
				type : 'POST',
				url : basePath + "/app/shgl/sqgl/sqglNextContent.do",
				data : {
					'pageNumber' : pageNumber,
					'name' : name,
				},
				dataType : 'json',
				error : function(request) {
//					top.layer.close(msgIndex);
//					top.layer.msg('数据加载失败', {
//						icon : 5,
//						shade : [ 0.5, '#999999' ],
//						time : 2000
//					});
				},
				success : function(data) {
//					top.layer.close(msgIndex);
					var listObj = data.result;
					for(var i = 0; i<listObj.length; i++){
						value = listObj[i];
						var html = '<div class="mui-card" id="sqId" name="'+value.sqId+'">'
							+'<div class="mui-card-header">社区名称:'
							+value.sqName
							+'</div><div class="mui-card-content"><div class="mui-card-content-inner"><p style="color:#666;">'
							+'已入住户数:'
							+value.sjCount+'&nbsp;&nbsp;&nbsp;添加人:'
							+value.addName+'&nbsp;&nbsp;&nbsp;添加时间:'
							+value.addTime
							+'</p></div></div>'
							+'<div class="mui-card-footer"><a href="javascript:void(0)" name=""><i class="icon ion-android-list"></i>详情</a></div></div>'
							+'<input type="hidden" id="pageCnts" value="${pageCnts}" />'
							+'<input type="hidden" id="pageSize" value="${pageSize}" />';
						
						mui('#table-container')[0].insertAdjacentHTML('beforeend', html);
					}
				}
			});
}

