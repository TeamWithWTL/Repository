var basePath = $("#basePath").val();

//附件下载
function download(newFileName, oldFileName){
	window.location.href = basePath + '/pub/downloadbystream.do?fileName=' + newFileName + '&oldFileName=' + oldFileName;
}


$(function(){
	initData(1);
	initDataFk(1);
});

//Table数据加载
function initData(pageNumber){
	var msgIndex = top.layer.msg('数据加载中', {icon:16, shade:[ 0.5, '#999999' ], time:0});
	$("#lyList").load(basePath + "/shzz/hdgl/goView.do",{'ajaxCmd': 'lyTable', 'pageNumber': pageNumber, 'id': $("#hdglId").val()}, function(){
		top.layer.close(msgIndex);
	});
}

//Table数据加载
function initDataFk(pageNumber){
	/*var msgIndex = top.layer.msg('数据加载中', {icon:16, shade:[ 0.5, '#999999' ], time:0});*/
	$("#fkList").load(basePath + "/shzz/hdgl/goView.do",{'ajaxCmdFk': 'fkTable', 'pageNumber': pageNumber, 'id': $("#hdglId").val()}, function(){
		top.layer.close(msgIndex);
	});
}

//删除事件
function del(id){
	
	top.layer.confirm("确定要删除该留言吗？", {
		icon: 0, 
		title: "提示",
		yes: function(index){
			var msgIndex = top.layer.msg('留言删除中...', {
				icon : 16,
				shade : [ 0.5, '#999999' ]
			});
			$.ajax({
				type : "POST",
				url : basePath + "/shzz/hdgl/doDelLy.do",
				data : {'id':id},
				error : function(request) {
					top.layer.close(msgIndex);
					top.layer.msg('留言删除失败，请联系管理员', {shade : [ 0.5, '#999999' ]});
				},
				success : function(data) {
					top.layer.close(msgIndex);
					if (data == "success") {
						top.layer.msg('留言删除成功', {
							icon : 6,
							time : 2000,
							shade : [ 0.5, '#999999' ]
						}, function() {
							initData(1);
						});
					} else {
						top.layer.msg('留言删除失败，请联系管理员', {shade : [ 0.5, '#999999' ]});
					}
				}
			});
		},
		cancel : function(index) {
			layer.close(index);
		}
	});
}

//跳转留言审核页面
function goShLyView(id){
	top.layer.open({
		type : 2,
		title : "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>组织活动留言</span>",
		content : basePath+'/shzz/hdgl/goShLyView.do?id='+id,
		area: ['80%', '80%'],
		shadeClose: true, //点击遮罩关闭
		end : function() {
			initData(1);
		}
	});
}

//跳转反馈审核页面
function goShFkView(id){
	top.layer.open({
		type : 2,
		title : "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>组织活动反馈</span>",
		content : basePath+'/shzz/hdgl/goShFkView.do?id='+id,
		area: ['80%', '80%'],
		shadeClose: true, //点击遮罩关闭
		end : function() {
			initData(1);
		}
	});
}