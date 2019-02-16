var basePath = $("#basePath").val();
$(function(){
	initModule();
	// 待办任务、事件 定时器 初始化
	countSchedule();
});
/**
 * 初始化模块
 */
function initModule(){
	var roleCode = $("#roleCode").val();
		
		if(roleCode.indexOf("14")!=-1 || roleCode.indexOf("01")!=-1 || roleCode.indexOf("99")!=-1){
			showModule("事件上报","/app/sjgl/myReport.do","icon ion-android-clipboard bg-67ccee");
		}
		if(roleCode.indexOf("13")!=-1 || roleCode.indexOf("02")!=-1 || roleCode.indexOf("03")!=-1 || roleCode.indexOf("04")!=-1 || roleCode.indexOf("05")!=-1 || roleCode.indexOf("06")!=-1 || roleCode.indexOf("07")!=-1 || roleCode.indexOf("08")!=-1 || roleCode.indexOf("09")!=-1 ||roleCode.indexOf("10")!=-1 || roleCode.indexOf("12")!=-1 || roleCode.indexOf("99")!=-1){
			showModule("事件管理","/app/sjgl/eventfunList.do","icon ion-filing bg-f6b95a");
		}
		if(roleCode.indexOf("13")!=-1 || roleCode.indexOf("02")!=-1 || roleCode.indexOf("03")!=-1 || roleCode.indexOf("01")!=-1 || roleCode.indexOf("12")!=-1 || roleCode.indexOf("99")!=-1){
			showModule("任务管理","/app/home/torwgllb.do","icon ion-android-alarm-clock bg-4fd2be");
		}
		if( roleCode.indexOf("01")!=-1 ){
			showModule("签到","Sign_In","icon icon-icon57 bg-67ccee");
			showModule("社情民意","/app/shgl/sqmy/index.do","icon ion-person-stalker bg-f6b95a");
		}
		if(roleCode.indexOf("14")!=-1|| roleCode.indexOf("99")!=-1){
			showModule("电话投诉","CALL_TELPHONE","icon ion-ios-telephone bg-a8dd99");
		}
		if(roleCode.indexOf("01")!=-1 || roleCode.indexOf("02")!=-1 || roleCode.indexOf("03")!=-1 || roleCode.indexOf("99")!=-1){
			/*showModule("基础信息","/app/home/jcxx.do","icon ion-android-clipboard bg-67ccee");*/
			showModule("基础信息","/app/home/jcxx.do","icon icon-icon_yuangongguanli bg-67ccee");
		}

	showModule("社区活动","/app/shfw/sqhd/toSqhd.do","icon ion-android-home bg-ff7360");
	showModule("组织活动","/app/shzz/appHdgl/toHdgl.do","icon ion-android-bicycle bg-67ccee");
	showModule("社区服务","/app/shfw/sqfw/toSqfw.do","icon ion-android-contacts bg-f6b95a");
	showModule("在线招募","/app/shzz/zxzm/toZxzm.do","icon ion-person-add bg-4fd2be");
	showModule("政务信息","/app/shfw/zwxx/toZwxx.do","icon ion-android-bookmark bg-a8dd99");
	showModule("中心简介","/app/shzz/zxjs/index.do","icon ion-chatbubble-working bg-67ccee");
	showModule("投诉举报","/app/dflz/appComp/toComp.do","icon ion-email bg-a8dd99");
	
//		if(roleCode.indexOf("13")!=-1 || roleCode.indexOf("99")!=-1){
//			showModule("公文处理","/app/xtbg/gwcl/myreceive.do","icon ion-android-list bg-a8dd99");
//			showModule("会议管理","/app/xtbg/appMeeting/toMeeting.do","icon ion-android-people bg-67ccee");
//		}
//		if(roleCode.indexOf("08")!=-1 || roleCode.indexOf("12")!=-1 || roleCode.indexOf("99")!=-1){
//			showModule("通讯录","/app/xtbg/txlgl/index.do","icon ion-android-call bg-67ccee");
//		}
	
	if(roleCode.indexOf("01")!=-1 || roleCode.indexOf("02")!=-1 || roleCode.indexOf("03")!=-1 || roleCode.indexOf("05")!=-1 || roleCode.indexOf("06")!=-1 || roleCode.indexOf("08")!=-1 || roleCode.indexOf("09")!=-1 || roleCode.indexOf("12")!=-1 || roleCode.indexOf("99")!=-1){
		showModule("曝光台","/app/dflz/appExpo/toExpo.do","icon ion-easel bg-ff7360");
	}
//	showModule("热点新闻","/app/xtbg/rdxw/index.do","icon ion-android-globe bg-4fd2be");
//	showModule("动态播报","/app/shzz/dtbb/toDtbb.do","icon ion-android-wifi bg-ff7360");
	showModule("廉政要闻","/app/dflz/appDzyw/toDzyw.do","icon ion-document-text bg-a8dd99");
//	showModule("通知公告","/app/xtbg/tzgg/index.do","icon ion-android-notifications bg-f6b95a");
	
}

var muduleCount = 0;
function showModule(name,url,moduleCss){
	var eventCountHtml="";
	//事件
	if(moduleCss == "icon ion-filing bg-f6b95a"){
		$.ajax({
			type : "POST",
			async: false,
			url : basePath+"/schedule.do",
			error : function(request) {
			},
			success : function(result) {
			var evnetCount  = result.eventCount;
			if(evnetCount>0){
				eventCountHtml='<span class="mui-badge mui-badge-red " id = "sjCounts">'+evnetCount+'</span>';
			}
			}
		});
	}
	//任务
	if(moduleCss == "icon ion-android-alarm-clock bg-4fd2be"){
		$.ajax({
			type : "POST",
			async: false,
			url : basePath+"/app/shgl/appRwgl/noDealCount.do",
			error : function(request) {
			},
			success : function(result) {
			var rwcount  = result.rwcount;
			if(rwcount>0){
				eventCountHtml='<span class="mui-badge mui-badge-red" id = "rwCounts">'+rwcount+'</span>';
			}
			}
		});
	}
	//社情民意
	if(moduleCss == "icon ion-person-stalker bg-f6b95a"){
		$.ajax({
			type : "POST",
			async: false,
			url : basePath+"/app/shgl/sqmy/needSee.do",
			error : function(request) {
			},
			success : function(result) {
			var count  = result.count;
			if(count>0){
				eventCountHtml='<span class="mui-badge mui-badge-red " id = "sqmyCounts">'+count+'</span>';
			}
			}
		});
	}
	var addHtml ='<li url="'+url+'" class="mui-table-view-cell mui-media mui-col-xs-3 mui-col-sm-3">'+
		'<a href="javascript:void(0)">'+
	'<span class="'+moduleCss+'">'+eventCountHtml+'</span>'+
	'<div class="mui-media-body">'+name+'</div></a></li>';
	if(muduleCount<8){
		$("#firstItem").append(addHtml);
	}
	if(muduleCount == 8){
		var secondItem = '<div class="mui-slider-item"><ul id ="secondItem" class="mui-table-view mui-grid-view mui-grid-9"></ul></div>';
		$("#module").append(secondItem);
		$("#indicator").append('<div class="mui-indicator"></div>');
		$("#secondItem").append(addHtml);
	}
	if(muduleCount>8 &&muduleCount<16){
		$("#secondItem").append(addHtml);
	}
	if(muduleCount == 16){
		var secondItem = '<div class="mui-slider-item"><ul id ="thirdItem" class="mui-table-view mui-grid-view mui-grid-9"></ul></div>';
		$("#module").append(secondItem);
		$("#indicator").append('<div class="mui-indicator"></div>');
		$("#thirdItem").append(addHtml);
	}
	if(muduleCount>16){
		$("#thirdItem").append(addHtml);
	}
	muduleCount =muduleCount+1;
	
}

/*
 * 事件和任务待办 数量刷新
 */
function countSchedule(){
	// 待办事件
	$.ajax({
		type : "POST",
		async: false,
		url : basePath+"/schedule.do",
		error : function(request) {
		},
		success : function(result) {
		var evnetCount  = result.eventCount;
		if(evnetCount>0){
			$("#sjCounts").html("");
			$("#sjCounts").html(evnetCount);
		}
		}
	});
	// 待办任务
	$.ajax({
		type : "POST",
		async: false,
		url : basePath+"/app/shgl/appRwgl/noDealCount.do",
		error : function(request) {
		},
		success : function(result) {
		var rwcount  = result.rwcount;
		if(rwcount>0){
			$("#rwCounts").html("");
			$("#rwCounts").html(rwcount);
		}
		}
	});
	setTimeout("countSchedule()",90000);
}

//查看得分详情
mui("#module").on('tap','li',function(){
	 var url = this.getAttribute("url");
	 //拨打投诉电话
	 if(url == "CALL_TELPHONE"){
		 var btnArray=['拨打','取消'];
         var phone = $("#phone").val();
         mui.confirm('是否拨打'+phone,'提示',btnArray,function(e){
             if(e.index == 0){
             	Telphone(phone);
             }
         });
         return;
	}
	 //签到
	 if(url == "Sign_In"){
	 msgIndex1 = top.layer.msg('数据保存中...', {			icon : 16,			shade : [ 0.5, '#999999' ]});
		$.ajax({
			type : "POST",
			url : basePath+"/app/signIn/doSignIn.do",
			data :{
				'lon':$("#lon").val(),
				'lat':$("#lat").val()
			},
			error : function(request) {
				top.layer.close(msgIndex1);
				top.layer.msg('签到失败，请联系管理员', {shade : [ 0.5, '#999999' ]});
			},
			success : function(data) {
				top.layer.close(msgIndex1);
				if(data == "success"){
					top.layer.msg('签到成功',{icon : 6,time : 2000,shade : [0.5,'#999999' ]},function(){
//						window.location.href = $("#basePath").val() + "/app/home/index.do";
					});
				}else if(data == "noAuthority"){
					top.layer.msg('亲，您没有权限哦！',{icon : 7,time : 2000,shade : [0.5,'#999999' ]},function(){
//						window.location.href = $("#basePath").val() + "/app/home/index.do";
					});
				}else if(data == "amN"){
					top.layer.msg('您上午已签到',{icon : 7,time : 2000,shade : [0.5,'#999999' ]},function(){
//						window.location.href = $("#basePath").val() + "/app/home/index.do";
					});
				}else if(data == "pmN"){
					top.layer.msg('您下午已签到',{icon : 7,time : 2000,shade : [0.5,'#999999' ]},function(){
//						window.location.href = $("#basePath").val() + "/app/home/index.do";
					});
				}else if(data == "noScope"){
					top.layer.msg('您不在你所负责网格范围内，无法签到',{icon : 7,time : 2000,shade : [0.5,'#999999' ]},function(){
//						window.location.href = $("#basePath").val() + "/app/home/index.do";
					});
				}else{
					top.layer.msg('签到失败，请联系管理员', {shade : [ 0.5, '#999999' ]});
				}
			}
		});
		return;
	 }
	if(url=="/app/sjgl/myReport.do"){//事件上报跳转刷新
		  window.location.href = basePath + url;
		  return;
	}
	if(url=="/app/shgl/sqmy/index.do"){//事情民意跳转刷新
		  window.location.href = basePath + url;
		  return;
	}
	if(url=="/app/home/torwgllb.do"){//任务管理跳转刷新
		  window.location.href = basePath + url;
		  return;
	}
	if(url=="/app/dflz/appComp/toComp.do"){//投诉举报跳转刷新
		  window.location.href = basePath + url;
		  return;
	}
	if(url=="/app/sjgl/eventfunList.do"){
		  window.location.href = basePath + url;
		  return;
	}
	mui.openWindow({
	    url: basePath + url, 
	    id:'moreScore'
	});
});
//电话投诉
function Telphone(phone){
 try{
		setTimeout(function(){
			toAndroid.jsCallPhone(phone);
		}, 100);
	}catch(err){
		console.error("pubApp_Telphone_error!");
	}
};