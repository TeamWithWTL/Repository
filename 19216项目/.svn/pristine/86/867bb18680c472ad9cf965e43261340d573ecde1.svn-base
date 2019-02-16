var basePath = $("#basePath").val();
var pageNumber;
var isInit =false;
var tagFlag="1";// 1待处理, 2已处理 

// 是否校验content
var checkContent = true;

//事处理类型,1 处理, 2上报
var dealType;

var eventDealId;

var curRoleId;
$(function(){
	$("#treeButton").hide();
	$("#treeList").hide();
	//step 1: 初始化数据
	initEventData();
	//点击遮罩事件
	$(".mask-backdrop").click(function() {
		$("#title").html("事件管理");
		$("#backButton").show();
		$(this).hide();
		$("#isPubDiv").hide();
		$(".inquery").removeClass("show");
		checkContent= true;
		$("#contentDiv").show();
		
	});
	//返回界面
	mui("#header").on('tap','a',function(){
		window.location.href = $("#basePath").val() + "/app/sjgl/eventfunList.do";
	});
	mui("#treeList").on('tap','a',function(){
		$("#treeList").empty();
		$("#jsrId").val("");
	});
	//事件功能点击事件：处理，下发，上报，转发  查看 
	mui("#table-container").on('tap','a',function(){
		$("#backButton").hide();
		$("#treeButton").hide();
		$("#treeList").hide();
		
		var name =  this.getAttribute("name");
		var id =  this.getAttribute("id");
		 curRoleId =  this.getAttribute("curRoleId");
		var url="";
		if(name=="view"){
			  url = basePath + '/app/sjgl/eventView.do?id='+id+'&tagFlag=1';
			  mui.openWindow({
				    url: url,
				    id:'viewReport'
				  });
		}
		if(name=="deal"){
			$("#title").html("处理");
			$(".mask-backdrop").toggle();
			$(".inquery").toggleClass("show");
			$("#suggest").html("意见:");
			
		    curRoleId =  this.getAttribute("curRoleId");
			var dealStatus =  this.getAttribute("dealStatus");

			console.log("curRoleId:"+curRoleId);
			console.log("dealStatus:"+dealStatus);
			//处理类型   处理
			dealType ="1";
			
			eventDealId =id;
			//当前处理角色只有 服务站管理员,社区管理员,街道办信息员 才有设置公开权限
			if(curRoleId == "02"||curRoleId == "03"||curRoleId == "04"){
				$("#isPubDiv").show();
			}
			//如果状态是信息员处理公开状态 隐藏 处理意见按钮
			if(dealStatus=="3"){
				checkContent = false;
				$("#contentDiv").hide();
			}
			return;
		}
		//上报
		if(name=="report"){
			$("#title").html("上报");
			$(".mask-backdrop").toggle();
			$(".inquery").toggleClass("show");
			$("#suggest").html("意见:");
			
			dealType ="2";//处理类型   上报
			eventDealId =id;
			
			//加载处理人 如果是信息员 或者 街道办领导  选择处理人员
			if(curRoleId=='04'|| curRoleId=='12'){
				$("#treeButton").show();
				$("#treeList").show();
				$("#clrLabel").html("处理人");
				loadTreeData(curRoleId);
			}
			return;
		}
		//下发
		if(name=="toservice"){
			$("#title").html("下发");
			$(".mask-backdrop").toggle();
			$(".inquery").toggleClass("show");
			$("#suggest").html("意见:");
			
			dealType ="3";//处理类型   上报  下发
			eventDealId =id;
			//加载处理人 如果是信息员 或者 街道办领导  选择处理人员
				$("#treeButton").show();
				$("#treeList").show();
				$("#clrLabel").html("服务站");
				loadTreeData(curRoleId);
			return;
		}

	});
	//选择人员
	$("#selectTreeButton").click(function(){
		$(".popup").removeAttr("style");
		$(".full-mask-backdrop").fadeOut(200);
		
		var select_Id = $('input:radio:checked').val();
		var select_name = $('input:radio:checked').attr("jsrname");
		//未选择  清空
		$("#treeList").empty();
		$("#jsrId").val("");
		if(select_Id==undefined ||select_Id ==null ||select_Id==""){
			
		}else{
			$("#jsrId").val(select_Id);
			$("#treeList").append('	<li id="'+select_Id+'">'+select_name+'<a  onClick="removeJsr(\''+select_Id+'\')" href="javascript:void(0)" name="'+select_Id+'" class="icon ion-android-cancel"></a></li>');
		}
		
	});

	//上报,处理 提交事件
	$("#dealButton").click(function(){
		var content = $("#content").val();
		var clrId = $("#jsrId").val();
		if(checkContent){
			if(null == content || content == ""){
			 top.layer.msg('意见不能为空!', {
					icon : 5,
					shade : [ 0.5, '#999999' ],
					time : 2000
				});
				return;
			}
		}
	
		var myurl = "";
		if(dealType =="1"){
			myurl=basePath + "/shgl/sjgl/eventDeal.do";
		}
		if(dealType =="2"){
			myurl=basePath + "/shgl/sjgl/eventReport.do";
			if(curRoleId=='04'|| curRoleId=='12'){
				if(null == clrId || clrId == ""){
					 top.layer.msg('请添加处理人!', {
							icon : 5,
							shade : [ 0.5, '#999999' ],
							time : 2000
						});
						return;
					}
			}
		}
		if(dealType =="3"){
			myurl=basePath + "/shgl/sjgl/toServiceStation.do";
			if(null == clrId || clrId == ""){
				 top.layer.msg('请添加处理人!', {
						icon : 5,
						shade : [ 0.5, '#999999' ],
						time : 2000
					});
					return;
				}
		}
		//判断是否公开
		var isPub ="2";
		if($("#isPub").hasClass("mui-active")){
			isPub = "1";
		}
		console.log("isPub:"+isPub);
		console.log("eventDealId:"+eventDealId);
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
				'id' : eventDealId,
				'content' : content,
				'isPub':isPub,
				'clrId':clrId,
				'ssId':clrId,
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
//加载处理人按钮
$("#Add_User").click(function(){
	
	$(".popup").css("top", "1rem");
	$(".full-mask-backdrop").fadeIn(200);
});

$("#closed-popup, #submit").click(function(){
	$(".popup").removeAttr("style");
	$(".full-mask-backdrop").fadeOut(200);
});

//mui(".mui-slider-indicator").on('tap','#eventButton',function(){ 
//	tagFlag ="1";
//	initEventData();
//	});
//mui(".mui-slider-indicator").on('tap','#recordButton',function(){ 
//	tagFlag ="2";
//	initRecordData();
//	});
//document.getElementById('eventButton').addEventListener('tap',function(){
//	tagFlag ="1";
//	initEventData();
//	});
//document.getElementById('recordButton').addEventListener('tap',function(){
//	tagFlag ="2";
//	initRecordData();
//});
//事件按钮点击
//$("#eventButton").click(function(){
////	if($(this).hasClass("mui-active")){
////		console.log("111");
////		return;
////	}
//	tagFlag ="1";
//	initEventData();
//});

//处理记录按钮点击
//$("#recordButton").click(function(){
//	tagFlag ="2";
//	initRecordData();
//});


//加载处理人列表
function loadTreeData(curRoleId){
	
//	var msgIndex = top.layer.msg('数据加载中', {
//		icon : 16,
//		shade : [ 0.5, '#999999' ],
//		time : 0
//	});
	$.ajax({
				async : false,//同步方法
				type : 'POST',
				url :basePath + "/app/sjgl/loadTreeData.do" ,
				data : {
					'curRoleId' : curRoleId,
				},
				dataType : 'json',
				error : function(request) {

				},
				success : function(listObj) {
//					top.layer.close(msgIndex);
					console.log(listObj);
					$("#checkbox-list").empty();
					for(var i =0;i<listObj.length;i++){
					 var value = listObj[i];
					 var html = [];
					 if(value.pId=='root'){
						 console.log("111");
						 html.push(	'<div class="checkbox-list">');
						 html.push(	'<div class="checkbox-list-title">');
						 html.push(	'	<div class="mui-input-row mui-radio">');
						 html.push(	'		<label>'+value.name+'</label>');
						 html.push(	'	</div>');
						 html.push(	'	</div>');
						 html.push(	'	<ul id='+value.id+' class="mui-input-group">');
						 html.push(	'	</ul>');
						 html.push(	'	</div>');
					 }
						$("#checkbox-list").append(html.join(""));
					}
					for(var i =0;i<listObj.length;i++){
						 var value = listObj[i];
//						 console.log(value.name);
						 var html = [];
						 if(value.pId!='root'){
							 html.push(	'		<div class="mui-input-row mui-radio">');
							 html.push(	'			<label>'+value.name+'</label>');
							 html.push(	'			<input name="radio" type="radio" jsrId="'+value.id+'" jsrname="'+value.name+'" value="'+value.id+'">');
							 html.push(	'		</div>');
							$("#"+value.pId).append(html.join(""));
						 }
	
						}
				}
			});
}

mui("#treeList").on('tap','a',function(){
	var select_Id = this.getAttribute("name");
	mui("input[jsrId='"+select_Id+"']").each(function(){ 
        this.checked=false; 
    }); 
//	$.each(persons, function(index, person) {
//		if ($(person).is(":checked")) {
//		    ryCode = $(person).attr("namid");
//			if(aa == ryCode){
//				mui("input[namid='"+ryCode+"']").each(function(){ 
//                    this.checked=false; 
//                }); 
//			}
//		}
//	});
});
function removeJsr(select_Id){
	mui("input[jsrname='"+select_Id+"']").each(function(){ 
        this.checked=false; 
    }); 
}
//初始化事件列表
function initEventData() {
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
	$("#table-container").load(basePath + "/app/sjgl/myEvent.do", {
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
}
function initRecordData() {
	console.log("initRecordData");
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
	$("#table-container").load(basePath + "/app/sjgl/myRecord.do", {
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
}



////返回刷新当前界面
//window.addEventListener('refresh', function(e){//执行刷新
//	alert("shuaxin");
//    location.reload();
//});

/**
 * mui分页
 */
function pullupRefresh() {
	//判断加载待办 还是 已处理 
	
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
//	var msgIndex = top.layer.msg('数据加载中', {
//		icon : 16,
//		shade : [ 0.5, '#999999' ],
//		time : 0
//	});
	
	var myurl ="";
	if(tagFlag =="1"){
		myurl = basePath + "/app/sjgl/appGetMoreEvent.do";
	}
	if(tagFlag =="2"){
		myurl = basePath + "/app/sjgl/appGetMoreRecord.do";
	}
	if(myurl == ""){
		mui('#refreshContainer').pullRefresh().endPullupToRefresh(true); 
		return;
	}
	$.ajax({
				async : true,
				type : 'POST',
				url :myurl ,
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
					 html.push(	'<div class="mui-card">');
					 html.push(	'<div class="mui-card-header">');
					 html.push(	'<div class="mui-checkbox">');
					 html.push(	'<input name="checkbox1" value="Item 1" type="checkbox">');
					 html.push(	'</div>');
					 html.push(	'<a href="javascript:void(0)">"'+value.title+'"</a>');
					 html.push(	'</div>');
					 html.push(	'<div class="mui-card-content">');
					 html.push(	'<div class="mui-card-content-inner">');
					 html.push(	'<p class="mui-ellipsis-2" style="color:#666;">"'+value.content+'"</p>');
					 html.push(	'<p>"'+value.showDate+'"</p>');
					 html.push(	'</div>');
					 html.push(	'</div>');
					 html.push(	'<div class="mui-card-footer">');
					if(tagFlag == "1"){
				
					 html.push('<a href="javascript:void(0)" id="'+value.id+'" name="deal"><i class="icon ion-loop"></i> 处理</a>');
						if(value.dealStatus != '3'&&value.dealStatus != '4'&& value.curRoleId !='08'){
					 html.push(	'<a href="javascript:void(0)" id="'+value.id+'" name="report"><i class="icon ion-arrow-up-a"></i> 上报</a>');
						}
						}
					html.push('<a href="javascript:void(0)" id="'+value.id+'" name="view"><i class="icon ion-android-list"></i> 查看</a>');
					 html.push(	'</div>');
					 html.push(	'</div>');
					$("#table-container").append(html.join(""));
					}
				}
			});
}
