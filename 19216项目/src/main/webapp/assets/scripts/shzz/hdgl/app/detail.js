var basePath;
var pageNumber;
var isInit =false;
$(function() {
	basePath = $("#basePath").val();
	initDtbbXq();
	
	$("#backId").show();
	
	//点击遮罩事件
	$(".mask-backdrop").click(function() {
		$(this).hide();
		$("#backId").toggle();
		$(".inquery").removeClass("show");
	});
});
var pageCnts;

/**
 * app--组织活动留言--分页
 * @returns
 */
function initDtbbXq(){

	if(isInit){
		mui('#refreshContainer').pullRefresh().refresh(true);
		mui('#refreshContainer').pullRefresh().enablePullupToRefresh();
	}
	
	pageNumber = 1;
	var hdglId = $("#id").val();
	var msgIndex = top.layer.msg('数据加载中', {
		icon : 16,
		shade : [ 0.5, '#999999' ],
		time : 0
	});
	$("#table-container").load(basePath + "/app/shzz/appHdgl/initHdglLyView.do", {
		'ajaxCmd' : 'table',
		'pageNumber' : pageNumber,
		'hdglId' : hdglId
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

//下载
function downloadFile(path){
	toAndroid.jsSeeFJ(path);
}

//点击下载跳转
mui("#article-container").on('tap','.outside',function(){
	var path = this.getAttribute("name");
	//alert(path);
	downloadFile(path);
});

mui("#insideClick").on('tap','.inside',function(){
	var path = this.getAttribute("name");
	//alert(path);
	downloadFile(path);
});

/*function down(path) {
	alert(path);
	downloadFile(path);
}*/

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
	var msgIndex = top.layer.msg('数据加载中', {
		icon : 16,
		shade : [ 0.5, '#999999' ],
		time : 0
	});
	var dtbbId = $("#dtbbId").val();
	$.ajax({
		async : true,
		type : 'POST',
		url : basePath + "/app/shzz/appHdgl/HdglNextLy.do",
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
				var html = '<li class="mui-table-view-cell mui-media">'
		        			+'<a href="javascript:;"><img class="mui-media-object mui-pull-left" src="'+value.userPic+'">'
		        			+'<div class="mui-media-body"><span>'
		        			+value.duty+'-'+value.userName
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
function doSave(id){
	var content = $("#contentLy").val();
	if(null == content || content == ""){
		var msgIndex = top.layer.msg('留言内容为空，无法保存', {
			icon : 5,
			shade : [ 0.5, '#999999' ],
			time : 2000
		});
		return;
	}
	$.ajax({
		async : true,
		type : 'POST',
		url : basePath + "/app/shzz/appHdgl/doSaveLy.do",
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
					icon : 6,
					shade : [ 0.5, '#999999' ],
					time : 2500
				},function(){
					location.reload(true);   
				});
			}else if(data == "yly"){
				top.layer.msg('您已留言，无需再次留言', {
					icon : 5,
					shade : [ 0.5, '#999999' ],
					time : 2000
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

/****************************************附件上传*********************************************/

//调用手机端方法打开附件
function openFile() {
	toAndroid.jsOpenFile();
}

//调用手机端方法打开附件---手机图库
function openFileImg(){
	toAndroid.jsOpenImage();
}

//拍照
function openCamera() {
	toAndroid.jsCallCamera();
}

//确定所选文件后执行  根据不同的附件类型显示相应的图片
function getImgs(paths) {
	var arrs = paths.split(',');
		for (var i = 0; i < arrs.length; i++) {
			var fileType=arrs[i].substring(arrs[i].lastIndexOf('.')+1);
			var fileName=arrs[i].substring(arrs[i].lastIndexOf('/')+1);
			fileType = fileType.toLocaleLowerCase();
			var html='<li><img  onClick="imgClick(\'' + arrs[i] + '\')"   src="'+basePath+'/assets/images/default-file.png" class="adaption"/><a href="javascript:;" id="' + arrs[i] + '" onClick="tapholdHandler(this)" class="icon ion-android-cancel"></a></li>';
			$("#fjItems").prepend(html);
		}
}


//查看上传图片
function imgClick(path) {
	toAndroid.jsSeeFile(path);
}

//删除上传附件
function tapholdHandler(obj) {
	top.layer.confirm("确定要删除该文件么？", {
		icon: 0, 
		title: "提示",
		yes: function(index){
			layer.close(index);
			//清除删除的img
			$(obj).parent().remove();
			//删除文件
			toAndroid.jsDeleteFj(obj.id);
			
		},
		cancel : function(index) {
			layer.close(index);
		}
	});
}

//保存反馈
function doSav(id){
	var content = $("#contentFk").val();
	if(null == content || content == ""){
		var msgIndex = top.layer.msg('反馈内容为空，无法保存', {
			icon : 5,
			shade : [ 0.5, '#999999' ],
			time : 2000
		});
		return;
	}
	/*var zzId=$("#zzId").val();
	if(null == zzId || zzId == ""){
		var msgIndex = top.layer.msg('请选择组织，无法保存', {
			icon : 5,
			shade : [ 0.5, '#999999' ],
			time : 2000
		});
		return;
	}*/
	msgIndex1 = top.layer.msg('数据保存中...', {icon : 16,shade : [ 0.5, '#999999' ]});
	$.ajax({
		type : "POST",
		url : basePath+"/app/shzz/appHdgl/saveFk.do",
		data : {
			'id':id,
			'content':content
			//'zzId':$("#zzId").val()
		},
		error : function(request) {
			top.layer.close(msgIndex1);
			top.layer.msg('数据保存失败，请联系管理员', {shade : [ 0.5, '#999999' ]});
		},
		success : function(data) {
			var data = $.parseJSON(data);
			if(data.result == "success") {
				var str = '{"pid":"' + data.pid + '","table":"HdglFkAttrEntity"}';
				jsUpDataFJ(str);
				top.layer.close(msgIndex1);
				top.layer.msg('反馈成功，需审核通过后，才可显示',{icon : 6,time : 2000,shade : [0.5,'#999999' ]},function(){
					top.layer.close(top.layer.getFrameIndex(window.name));
					mui.back();
				});
			}else if(data.result == "bnfk"){
				top.layer.msg('您不能对该组织活动做反馈', {
					icon : 5,
					shade : [ 0.5, '#999999' ],
					time : 2000
				});
			}else{
				top.layer.msg('数据保存失败，请联系管理员', {shade : [ 0.5, '#999999' ]});
			}
			
		}
	});
}