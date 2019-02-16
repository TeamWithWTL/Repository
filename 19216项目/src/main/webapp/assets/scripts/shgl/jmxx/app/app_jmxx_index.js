/**
 * App--社会管理--居民信息--首页_列表
 */
var basePath;
var pageNumber;
var isInit =false;
$(function() {
	basePath = $("#basePath").val();
	initData();
	var roleCodeId = $("#roleCode").val();
	if(roleCodeId.indexOf("01")!=-1){
		$("#roleCodeId").hide();
	}else{
		$("#roleCodeId").show();
	}
});

//Table数据加载
function initData(){
	
	if(isInit){
		mui('#refreshContainer').pullRefresh().refresh(true);
		mui('#refreshContainer').pullRefresh().enablePullupToRefresh();
	}
	
	pageNumber = 1;
	
	$("#table-container").load(basePath + "/app/shgl/jmxx/index.do",{
		'ajaxCmd': 'table', 
		'pageNumber': pageNumber, 
		'name': $("#name").val(),
		'commId' : $("#jmxxCode1 option:selected").val(),
		'ssId' : $("#ssId option:selected").val(),
		'gridId' : $("#gridId option:selected").val(),
		'xqId' : $("#xqId option:selected").val()
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
			$(".mask-backdrop").hide();
			$(".inquery").removeClass("show");
	});
}

//跳转--详情
mui("#table-container").on('tap','.mui-card',function(){
	 var jmxxId = this.getAttribute("name");
		 mui.openWindow({
			    url: basePath + "/app/shgl/jmxx/goView.do?id="+jmxxId, 
			    id:'goView'
			  });
});

//获取网格列表
function showData(sec,ssId,xqId){
	if(ssId != null || ssId != ""){
		var _html = '<option value="">--所属网格--</option>';
		$.ajax({
			type : "POST",
			url : basePath + "/shgl/xqxx/getData.do",
			data : {'ssId':$(sec).val()},
			success : function(data) {
				var obj = JSON.parse(data);
				if(obj.length>0){
					for(var i=0;i<obj.length;i++){
						_html+='<option value="'+obj[i].id+'">'+obj[i].name+'</option>';
					}
				}
				$("#"+ssId).html(_html);
			},
			error:function(){
				$("#"+ssId).html(_html);
			}
		});
	}
	if(xqId != null || xqId != ""){
		var _htmlxq = '<option value="">--所属小区--</option>';
		$.ajax({
			type : "POST",
			url : basePath + "/shgl/xqxx/getXqData.do",
			data : {'ssId':$(sec).val()},
			success : function(data) {
				var obj = JSON.parse(data);
				if(obj.length>0){
					for(var i=0;i<obj.length;i++){
						_htmlxq+='<option value="'+obj[i].id+'">'+obj[i].name+'</option>';
					}
				}
				$("#"+xqId).html(_htmlxq);
			},
			error:function(){
				$("#"+xqId).html(_htmlxq);
			}
		});
	}
}



//mui分页
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
	var ssId = $("#ssId option:selected").val();
	var commId = $("#lyCode1 option:selected").val();
	var gridId = $("#gridId option:selected").val();
	var xqId = $("#xqId option:selected").val()

	$.ajax({
				async : true,
				type : 'POST',
				url : basePath + "/app/shgl/jmxx/jmxxNextContent.do",
				data : {
					'pageNumber' : pageNumber,
					'name' : name,
					'commId' : commId,
					'serId' : ssId,
					'gridId' : gridId,
					'xqId' : xqId
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
						var sex = value.sex == 'E'?'男':'女';
						var html = '<div class="mui-card" id="jmxxId" name="'+value.id+'">'
							+'<div class="mui-card-header">姓名：'
							+value.jmxxName
							+'</div><div class="mui-card-content"><div class="mui-card-content-inner"><p style="color:#666;">性别：'
							+sex+'&nbsp;&nbsp;&nbsp;出生日期：'
							+value.addTime
							+'</p></div></div>'
							+'<div class="mui-card-footer"><a href="javascript:void(0)" name=""><i class="icon ion-android-list"></i>详情</a></div></div>';
						
						mui('#qkId')[0].insertAdjacentHTML('beforeend', html);
					}
				}
			});
}