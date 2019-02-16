var basePath = $("#basePath").val();
var pageNumber;
var isInit =false;

var docDealId;

$(function(){
	
	initmyreceiveData();
	
	//点击遮罩事件
	$(".mask-backdrop").click(function() {
		$(this).hide();
		$("#isPubDiv").hide();
		$(".inquery").removeClass("show");
		checkContent= true;
		$("#contentDiv").show();
		
	});
	
	//功能点击  处理  查看
	mui("#table-container").on('tap','a',function(){
		var name =  this.getAttribute("name");
		var id =  this.getAttribute("id");

		var url="";
		if(name=="view"){
			  url = basePath + '/app/xtbg/gwcl/goViewDoc.do?id='+id;
			  mui.openWindow({
				    url: url,
				    id:'viewReport'
				  });
		}
		if(name=="deal"){
			
			$(".mask-backdrop").toggle();
			$(".inquery").toggleClass("show");
			
			docDealId =id;
			return;
		}
	});	
	
	//上报,处理 提交事件
	$("#dealButton").click(function(){
		var content = $("#content").val();

		if(null == content || content == ""){
		 top.layer.msg('意见不能为空!', {
				icon : 5,
				shade : [ 0.5, '#999999' ],
				time : 2000
			});
			return;
		}
		
		var myurl = "";
//		if(dealType =="1"){
			myurl=basePath + "/xtbg/gwcl/saveDeal.do";
//		}
//		if(dealType =="2"){
//			myurl=basePath + "/shgl/sjgl/eventReport.do";
//		}

//		var msgIndex = top.layer.msg('数据加载中', {
//			icon : 16,
//			shade : [ 0.5, '#999999' ],
//			time : 0
//		});
		$.ajax({
			async : true,
			type : 'POST',
			url : myurl,
			data : {
				'dealId' : docDealId,
				'content' : content,
			},
			error : function(request) {
//				top.layer.close(msgIndex);
				top.layer.msg('数据保存失败', {
					icon : 5,
					shade : [ 0.5, '#999999' ],
					time : 2000
				});
			},
			success : function(data) {
//				top.layer.close(msgIndex);
				if(data == "success"){
					top.layer.msg('处理成功！', {
						icon : 6,
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
		
	});
});

function initmyreceiveData(){

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
	$("#table-container").load(basePath + "/app/xtbg/gwcl/myreceive.do", {
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
			mui('.mui-scroll-wrapper').scroll({
				indicators: false, //是否显示滚动条
				startX: 0, //初始化时滚动至x
				deceleration: 0.0004, //flick 减速系数，系数越大，滚动速度越慢，滚动距离越小，默认值0.0006
				bounce: true //是否启用回弹
			});
			mui('#refreshContainer').pullRefresh().refresh(true);
			mui('#refreshContainer').pullRefresh().enablePullupToRefresh(false);
			}
	});
	
	
	/**
	 * mui分页
	 */
	function pullupRefresh() {
		
		// 获取下一页数据
		setTimeout(function() {
			getContentNext();// 具体取数据的方法
		}, 100);
	}

	function getContentNext(){
		pageNumber++;
		console.log("pageCnts:"+pageCnts);
		console.log("pageNumber:"+pageNumber);
		if (pageCnts < pageNumber) {
			mui('#refreshContainer').pullRefresh().endPullupToRefresh(true); 
			return;
		}
		mui('#refreshContainer').pullRefresh().endPullupToRefresh(false);
//		var msgIndex = top.layer.msg('数据加载中', {
//			icon : 16,
//			shade : [ 0.5, '#999999' ],
//			time : 0
//		});
		
		$.ajax({
					async : true,
					type : 'POST',
					url :basePath + "/app/sjgl/appGetMoreReceive.do" ,
					data : {
						'pageNumber' : pageNumber,
					},
					dataType : 'json',
					error : function(request) {

					},
					success : function(data) {
//						top.layer.close(msgIndex);
						var listObj = data.data;
						for(var i =0;i<listObj.length;i++){
						var	value = listObj[i];
						 var html = [];
						 html.push(	'<div class="mui-card">');
						 html.push(	'<div class="mui-card-header">');
						 html.push(	'<div class="mui-checkbox">');
						 html.push(	'<input name="checkbox1" value="Item 1" type="checkbox">');
						 html.push(	'</div>');
						 html.push(	'<a href="javascript:void(0)">"'+value.title+'"</a>');
						 html.push(	'</div>');
						 html.push(	'<div class="mui-card-content">');
						 html.push(	'<div class="mui-card-content-inner">');
						 html.push(	'<p>"'+value.showDate+'"</p>');
						 html.push(	'</div>');
						 html.push(	'</div>');
						 html.push(	'<div class="mui-card-footer">');
						 html.push('<a href="javascript:void(0)" id="'+value.docId+'" name="view"><i class="icon ion-android-list"></i> 查看</a>');
						 if(value.status=='0'){
							 html.push('<a href="javascript:void(0)" id="'+value.id+'" name="deal"><i class="icon ion-loop"></i> 处理</a>');
						 }
						
						 html.push(	'</div>');
						 html.push(	'</div>');
						$("#table-container").append(html.join(""));
						}
					}
				});
}
}