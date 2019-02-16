/**
 * 首页
 */
var basePath;
var pageNumber;
var isInit =false;
$(function() {
	
	basePath = $("#basePath").val();
	initEventData();
	//initRecordData();
	$("#backId").toggle();
	
	//点击遮罩事件
	$(".mask-backdrop").click(function() {
		$(this).hide();
		$("#backId").toggle();
		//$("#isPubDiv").hide();
		$(".inquery").removeClass("show");
		checkContent= true;
		$("#contentDiv").show();
		
	});
});

/**
 * app-任务管理--待处理任务-（高帅_2018-1-4——修改）
 */
function initEventData() {
	
	if(isInit){
		mui('#refreshContainer').pullRefresh().refresh(true);
		mui('#refreshContainer').pullRefresh().enablePullupToRefresh();
	}
	
	pageNumber = 1;
	//var title = $("#titleId").val();

//	var msgIndex = top.layer.msg('数据加载中', {
//		icon : 16,
//		shade : [ 0.5, '#999999' ],
//		time : 0
//	});
	$("#table-container").load(basePath + "/app/shgl/appRwgl/toRwgl.do", {
		'ajaxCmd' : 'table',
		'pageNumber' : pageNumber
		/*'title' : title,*/
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
//	var msgIndex = top.layer.msg('数据加载中', {
//		icon : 16,
//		shade : [ 0.5, '#999999' ],
//		time : 0
//	});
	
	$.ajax({
				async : true,
				type : 'POST',
				url : basePath + "/app/shgl/appRwgl/rwglNextContent.do",
				data : {
					'pageNumber' : pageNumber
				//	'title' : title,
				},
				dataType : 'json',
				error : function(request) {
				},
				success : function(data) {
//					top.layer.close(msgIndex);
					var listObj = data.result;
					var isWgy = data.isWgys;
					for(var i = 0; i<listObj.length; i++){
						
						value = listObj[i];
						var htmlQx = '';
						
						if(value.isBack == '1'){
							htmlQx = '<a id="details" href="javascript:void(0)" name="'
								+value.titleId+'"><i class="icon ion-android-list"></i> 详情</a>';
						}else{
							if(value.isBack == '2' && value.isDown == "2"){
								htmlQx = '<a id="fk" href="javascript:void(0)" name="'
									+value.titleId+'"><i class="icon ion-android-list"></i> 反馈</a>';
								if(isWgy == 'no'){
									htmlQx = htmlQx+'<a id="issue" href="javascript:void(0)" name="'
										+value.titleId+'"><i class="icon ion-android-list"></i> 下发</a>';
								}
								htmlQx = htmlQx+'<a id="details" href="javascript:void(0)" name="'
									+value.titleId+'"><i class="icon ion-android-list"></i> 详情</a>';
							}else{
								htmlQx = htmlQx+'<a id="fk" href="javascript:void(0)" name="'
									+value.titleId+'"><i class="icon ion-android-list"></i> 反馈</a>';
								htmlQx = htmlQx+'<a id="details" href="javascript:void(0)" name="'
									+value.titleId+'"><i class="icon ion-android-list"></i> 详情</a>';
							}
						}
						
						var html ='<div class="mui-card">'+
						'<div class="mui-card-header"><div class="mui-checkbox">'
						+'<input name="checkbox1" value="Item 1" type="checkbox"></div><p>'
						+value.titleName+'</p><p>'
						+value.createTime+'</p></div><div class="mui-card-footer" id="container">'
						+htmlQx+'</div></div>';
						
						mui('#table-container')[0].insertAdjacentHTML('beforeend', html);
					}
				}
			});
}

//跳转下发
mui("#table-container").on('tap','#issue',function(){
	
	 var rwglId = this.getAttribute("name");
	 
		 mui.openWindow({
			    url: basePath + "/app/shgl/appRwgl/goIssue.do?rwglId="+rwglId, 
			    id:'goIssue'
			  });
});

/**
* 跳转详情
* @param id
*/
mui("#table-container").on('tap','#details',function(){
	 var rwclId = this.getAttribute("name");
		 mui.openWindow({
			    url: basePath + "/app/shgl/appRwgl/goView.do?id="+rwclId, 
			    id:'goView'
			  });
});


//调用手机端方法打开附件
function openFile() {
	toAndroid.jsOpenFile();
}
//调用手机端方法打开附件
//function openFileImg() {
//	toAndroid.jsOpenImage();
//}
//拍照
function openCamera() {
	toAndroid.jsCallCamera();
}

//确定所选文件后执行  根据不同的附件类型显示相应的图片
function getImgs(paths) {
	   var arrs = paths.split(',');      //多个选择附件，用逗号分割
		for (var i = 0; i < arrs.length; i++) {
			var fileType=arrs[i].substring(arrs[i].lastIndexOf('.')+1);
			var fileName=arrs[i].substring(arrs[i].lastIndexOf('/')+1);
			fileType = fileType.toLocaleLowerCase();
			var html ='<li><img  onClick="imgClick(\'' + arrs[i] + '\')"  src="'+basePath+'/assets/images/default-file.png" class="adaption"/><a href="javascript:;" id="' + arrs[i] + '" onClick="tapholdHandler(this)" class="icon ion-android-cancel"></a></li>';
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

var fkId='';
/**
 * 反馈div显示
 */
mui("#table-container").on('tap','#fk',function(){
	$(".mask-backdrop").toggle();
	$(".inquery").toggleClass("show");
	$("#backId").toggle();
	fkId = this.getAttribute("name");
})

$("#fkButton").click(function(){
	
	var content = $("#contentFk").val();
	
		if(null == content || content == ""){
		 top.layer.msg('意见不能为空!', {
				icon : 5,
				shade : [ 0.5, '#999999' ],
				time : 2000
			});
			return;
		}
	
	
//	var msgIndex = top.layer.msg('数据加载中', {
//		icon : 16,
//		shade : [ 0.5, '#999999' ],
//		time : 0
//	});
	$.ajax({
		async : true,
		type : 'POST',
		url : basePath + "/app/shgl/appRwgl/goFk.do",
		data : {
			'id' : fkId,
			'content' : content,
			'fName':$("#fName").val()
		},
		error : function(request) {
//			top.layer.close(msgIndex);
			top.layer.msg('数据保存失败', {
				icon : 5,
				shade : [ 0.5, '#999999' ],
				time : 2000
			});
		},
		success : function(data) {
//			top.layer.close(msgIndex);
			var data = $.parseJSON(data);
			if(data.result == "success"){
				var str = '{"pid":"' + data.pid + '","table":"RwclAttrEntity"}';
//				setTimeout(function(){
				jsUpDataFJ(str);
//				}, 500);
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
