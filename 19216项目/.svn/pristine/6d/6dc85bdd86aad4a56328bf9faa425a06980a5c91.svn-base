/**
 * 住户列表App
 */

var pageNumber;
var isInit =false;
var basePath = $("#basePath").val();
/*********分页********************************/	


	$(function(){
	console.log("初始化....");
	initDate();
	
	});
	
	//添加住户按钮
	mui("#addZhuHu").on('tap','a',function(){
		  mui.openWindow({
			    url: basePath + '/app/shgl/sqmy/addZhuHuPage.do?sqmy_id='+$("#sqmy_id").val(), 
			    id:'addZhuHu'
			  });
	});
		
function initDate() {
	if(isInit){
		mui('#refreshContainer').pullRefresh().refresh(true);
		mui('#refreshContainer').pullRefresh().enablePullupToRefresh();
	}
	pageNumber = 1;

	var msgIndex = top.layer.msg('数据加载中', {
		icon : 16,
		shade : [ 0.5, '#999999' ],
		time : 0
	});
	$("#table-container").load(basePath + "/app/shgl/sqmy/zhList.do",{'ajaxCmd': 'table', 'pageNumber': pageNumber,'sqmy_id':$("#sqmy_id").val()}, function() {
		top.layer.close(msgIndex);
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

/**
 * mui分页
 */
function pullupRefresh() {
	// ==================上拉加载
	console.log("pullupRefresh");
	// 获取下一页数据
	setTimeout(function() {
		getList();// 具体取数据的方法
	}, 100);
}



function getList() {

	pageNumber++;
	if (pageCnts < pageNumber) {
		mui('#refreshContainer').pullRefresh().endPullupToRefresh(true); 
		return;
	}
	mui('#refreshContainer').pullRefresh().endPullupToRefresh(false);
	
	var msgIndex = top.layer.msg('数据加载中', {
		icon : 16,
		shade : [ 0.5, '#999999' ],
		time : 0
	});
	

	$.ajax({
				async : true,
				type : 'POST',
				url : basePath + "/app/shgl/sqmy/getList.do",
				data : {
					'pageNumber' : pageNumber,
					'sqmy_id':$("#sqmy_id").val()
				},
				dataType : 'json',
				error : function(request) {
					top.layer.close(msgIndex);
					top.layer.msg('数据加载失败', {
						icon : 5,
						shade : [ 0.5, '#999999' ],
						time : 2000
					});

				},
				success : function(data) {
					top.layer.close(msgIndex);
					var listObj = data.data;//data
					for(var i = 0; i<listObj.length; i++){
						value = listObj[i];
						var html = '<div class="mui-card">'
							+'<div class="mui-card-header">'
							+'小区名称:'+value.xqname
							+'</div><div class="mui-card-content"><div class="mui-card-content-inner"><p style="color:#666;">'
							+'楼宇:'+value.lyname +'&nbsp; &nbsp;&nbsp;'
							+'单元号:'+value.unit_num +'&nbsp; &nbsp;&nbsp;'
							+'室号:'+value.room_num
							+'</p></div></div><div class="mui-card-footer">'
							+'<a href="javascript:void(0)" name="'+value.id+'"><i class="icon ion-android-list"></i>'
							+'详情' 
							+'</a></div></div>';
						mui('#table-container')[0].insertAdjacentHTML('beforeend', html);
					}
				}
			});
}
		
//查看住户详情页面
mui("#table-container").on('tap','a',function(){
	 var zh_id = this.getAttribute("name"); 
		 mui.openWindow({
	     url: basePath + '/app/shgl/sqmy/zhdcList.do?&zh_id='+zh_id, 
    	 id:'goView'
			  });
		}); 


//手机按键返回
function anGoBack(){
	mui.back();
	return;
}
