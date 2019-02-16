var basePath = $("#basePath").val();
var pageNumber;
var isInit =false;
$(function(){
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
	$("#table-container").load(basePath + "/app/shgl/appRwgl/myXFXq.do", {
		'ajaxCmd' : 'table',
		'pageNumber' : pageNumber,
		'taskId':$("#rwId").val()
	}, function() {
//		top.layer.close(msgIndex);
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

//查看
mui("#table-container").on('tap','.mui-card',function(){
	var id =  this.getAttribute("id");
	  mui.openWindow({
		    url: basePath + '/app/shgl/appRwgl/goView.do?id='+id, 
		    id:'goView'
		  });
});

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
			url : basePath + "/app/shgl/appRwgl/nextXFXq.do",
			data : {
				'pageNumber' : pageNumber,
				'taskId':$("#rwId").val()
			},
			dataType : 'json',
			error : function(request) {

			},
			success : function(data) {
//					top.layer.close(msgIndex);
				var listObj = data.result;
				for(var i =0;i<listObj.length;i++){
				 var value = listObj[i];
				 var htmlif = '';
				 if(value.isBack == '1'){
					 htmlif = '<span style="color: #7ebc1d"> 已反馈 </span>';
				 }else{
					 htmlif = '<span style="color: #ffa81f"> 未反馈 </span>';
				 }
				 var html = '<div class="mui-card" id="'
					 +value.rwId
					 +'"><div class="mui-card-header"><div class="mui-checkbox">'
					 +'<input name="checkbox1" value="Item 1" type="checkbox"></div>'
					 +value.titleName
					 +'</div><div class="mui-card-content"><div class="mui-card-content-inner"><p>'
					 +value.createTime
					 +'</p>'
					 +'<p>处理人：'+value.curName
					 +htmlif
					 +'</p></div></div><div class="mui-card-footer">'
					 +'<a href="javascript:void(0)" id="'+value.rwId+'"><i class="icon ion-android-list"></i> 详情</a></div></div>';
				 
				 mui('#table-container')[0].insertAdjacentHTML('beforeend', html);
				}
			}
		});
}
