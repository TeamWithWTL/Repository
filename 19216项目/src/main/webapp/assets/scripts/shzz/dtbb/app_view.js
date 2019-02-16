var basePath = $("#basePath").val();;
var pageNumber;
var isInit =false;
$(function(){
	initDtbbXq();
});
var pageCnts;

mui("#article-container").on('tap','a',function(){
	var path = this.getAttribute("name");
	downloadFile(path);
});
/**
 * app---动态播报留言--分页
 * @returns
 */
function initDtbbXq(){

	if(isInit){
		mui('#refreshContainer').pullRefresh().refresh(true);
		mui('#refreshContainer').pullRefresh().enablePullupToRefresh();
	}
	
	pageNumber = 1;
	var dtbbId = $("#dtbbId").val();

	var msgIndex = top.layer.msg('数据加载中', {
		icon : 16,
		shade : [ 0.5, '#999999' ],
		time : 0
	});
	$("#table-container").load(basePath + "/app/shzz/dtbb/initDtbbLyView.do", {
		'ajaxCmd' : 'table',
		'pageNumber' : pageNumber,
		'dtbbId' : dtbbId
	}, function() {
		top.layer.close(msgIndex);
		pageCnts =  $("#pageCnts").val();
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
	var msgIndex = top.layer.msg('数据加载中', {
		icon : 16,
		shade : [ 0.5, '#999999' ],
		time : 0
	});
	var dtbbId = $("#dtbbId").val();
	$.ajax({
		async : true,
		type : 'POST',
		url : basePath + "/app/shzz/dtbb/DtbbNextLy.do",
		data : {
			'pageNumber' : pageNumber,
			'dtbbId' : dtbbId
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
			var listObj = data.result;
			if('无数据' == listObj){
				return ;
			}
			for(var i = 0; i<listObj.length; i++){
				value = listObj[i];
				var pic = "";
				var duty = "";
				if(null != value.userPic && value.userPic != ''){
					pic = value.userPic;
				}
				if(null != value.duty && value.duty != ''){
					duty = value.duty + '-';
				}
				var html = '<li class="mui-table-view-cell mui-media">'
			        			+'<a href="javascript:;"><img class="mui-media-object mui-pull-left" src="'+pic+'">'
			        			+'<div class="mui-media-body"><span>'
			        			+duty+value.userName
			        			+'</span><p>'
			        			+value.content 
			        			+'</p><small><i class="icon ion-clock"></i>'
			        			+value.createTime
			        			+'</small></div></a></li>';
				
				mui('#table-container')[0].insertAdjacentHTML('beforeend', html);
			}
		}
	});
}


//保存留言
function doLy(id){
	var content = $("#content").val();
	if(null == content || content == ""){
		var msgIndex = top.layer.msg('留言内容为空，无法保存', {
			icon : 5,
			shade : [ 0.5, '#999999' ],
			time : 2000
		});
		return;
	}
	var msgIndex = top.layer.msg('数据加载中', {
		icon : 16,
		shade : [ 0.5, '#999999' ],
		time : 0
	});
	$.ajax({
		async : true,
		type : 'POST',
		url : basePath + "/app/shzz/dtbb/doSaveLy.do",
		data : {
			'id' : id,
			'content' : content
		},
		error : function(request) {
			top.layer.close(msgIndex);
			top.layer.msg('数据保存失败', {
				icon : 5,
				shade : [ 0.5, '#999999' ],
				time : 2000
			});
		},
		success : function(data) {
			top.layer.close(msgIndex);
			if(data == "success"){
				top.layer.msg('留言成功，需审核通过后，才可显示', {
					icon : 5,
					shade : [ 0.5, '#999999' ],
					time : 2500
				},function(){
					location.reload(true);   
				});
			}else{
				top.layer.msg('数据保存失败', {
					icon : 5,
					shade : [ 0.5, '#999999' ],
					time : 2000
				});
			}
		}
	});
}
