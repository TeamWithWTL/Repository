/**
 * 系统主页
 */

var basePath = $("#basePath").val();



$(function(){
	$("#eventTag").click(function(){
		$("#closed-tips").click();
		goFrame('/BchSqwg/', '402880cf5e0299d9015e02a9e8e40001', this);
	});
	$("#taskTag").click(function(){
		$("#closed-tips").click();
		goFrameForTask('/BchSqwg/', '402880cf5e0299d9015e02a9e8e40001', this);
	});
	$.ajax({
		type : "POST",
		url : basePath+"/schedule.do",
		error : function(request) {
		},
		success : function(result) {
		var evnetCount  = result.eventCount;
		var rwcount  = result.rwcount;
		
		if(evnetCount>0){
			$("#schedule").show();
			$("#eventTag").show();
			$("#eventCount").html(evnetCount);
		}
		if(rwcount>0){
			$("#schedule").show();
			$("#taskTag").show();
			$("#taskCount").html(rwcount);
		}
		}
	});
});
// 回到首页
function goHome(url){
	$(".menubar ul li").removeClass('active');
	$("#homeMenu").addClass('active');
	$("#mainFrame").attr('src', url);
}
//切换任务iframe
function goFrameForTask(url, menuCode, obj){
	$(".menubar ul li").removeClass('active');
	$("#" + menuCode).addClass('active');
	$.ajax({
		type: 'POST',
		url: basePath + '/checkSecond.do',
		data: {'menuCode': menuCode},
		success: function(data){
			if(data == '1'){
				// 存在二级菜单
				var _url = basePath + "/secondMain.do?menuCode=" + menuCode+"&menuType=task";
				$("#mainFrame").attr('src', _url);
//				setTimeout(function(){
//					var iframe = window.document.getElementById("mainFrame"); 
//					iframe.contentWindow.chooseTaskTag();
//				}, 1000);
			}else{
				// 不存在二级菜单
				$("#mainFrame").attr('src', url);
			}
		},
		error: function(){
			layer.msg('程序出错，请联系管理员', {shade : [ 0.5, '#999999' ]});
		}
	});
}
// Frame页面加载
function goFrame(url, menuCode, obj){
	$(".menubar ul li").removeClass('active');
	$("#" + menuCode).addClass('active');
	$.ajax({
		type: 'POST',
		url: basePath + '/checkSecond.do',
		data: {'menuCode': menuCode},
		success: function(data){
			if(data == '1'){
				// 存在二级菜单
				var _url = basePath + "/secondMain.do?menuCode=" + menuCode;
				$("#mainFrame").attr('src', _url);
			}else{
				// 不存在二级菜单
				$("#mainFrame").attr('src', url);
			}
		},
		error: function(){
			layer.msg('程序出错，请联系管理员', {shade : [ 0.5, '#999999' ]});
		}
	});
}

// 退出登录
function logout(){
	layer.confirm("确定要退出登录吗？", {
		icon: 0, 
		title: "提示",
		yes: function(index){
			var msgIndex = layer.msg('正在退出...', {
				icon : 16,
				shade : [ 0.5, '#999999' ]
			});
			$.ajax({
				type : "POST",
				url : basePath + "/goExit.do",
				data : {},
				error : function(request) {
					layer.close(msgIndex);
	        		layer.msg('程序出错，请联系管理员', {shade : [ 0.5, '#999999' ]});
				},
				success : function(data) {
					layer.close(msgIndex);
					if(data == "succ"){
						window.location.href = basePath + "/";
					}
				}
			});
		},
		cancel : function(index) {
			layer.close(index);
		}
	});
}

// 修改密码
function goUpdatePwd(){
	$(".btn").blur();
	layer.open({
	    type: 2,
	    title: "修改密码",
	    fix: false,
	    area: ['850px', '400px'],
	    content: basePath + "/goUpdatepwd.do"
	});
}


// 修改个人信息
function goUpdate(){
	$(".btn").blur();
	layer.open({
	    type: 2,
	    title: "修改个人信息",
	    fix: false,
	    area: ['850px', '650px'],
	    content: basePath + '/goUpdate.do'
	});
}
function showBigPic(url){   
	   layer.open({
		   type: 1,
		   title: false,
		   area: ['800px','600px'],
		   zIndex:9999999999999,
		   content:  '<img style ="width:800px;height:600px;" src =  "'+url+'"/>'
	   });
	}
