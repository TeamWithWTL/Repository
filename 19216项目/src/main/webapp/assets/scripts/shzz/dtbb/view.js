var basePath = $("#basePath").val();

//附件下载
function download(newFilename, oldFilename){
	window.location.href = basePath + '/pub/downloadbystream.do?fileName=' + newFilename + '&oldFileName=' + oldFilename;
}


$(function(){
	initData(1);
});

//Table数据加载
function initData(pageNumber){
	var msgIndex = top.layer.msg('数据加载中', {icon:16, shade:[ 0.5, '#999999' ], time:0});
	$("#lyList").load(basePath + "/shzz/dtbb/goView.do",{'ajaxCmd': 'lyTable', 'pageNumber': pageNumber, 'id': $("#dtbbId").val()}, function(){
		top.layer.close(msgIndex);
	});
}

//审核事件
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
				url : basePath + "/shzz/dtbb/doDelLy.do",
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

//审核留言
function success(id){
	$.ajax({
		type : "POST",
		url : basePath + "/shzz/dtbb/successLy.do",
		data : {'id':id},
		error : function(request) {
			
			top.layer.msg('留言审核失败，请联系管理员', {shade : [ 0.5, '#999999' ]});
		},
		success : function(data) {
			
			if (data == "success") {
				top.layer.msg('留言审核成功', {
					icon : 6,
					time : 2000,
					shade : [ 0.5, '#999999' ]
				}, function() {
					initData(1);
				});
			} else {
				top.layer.msg('留言审核失败，请联系管理员', {shade : [ 0.5, '#999999' ]});
			}
		}
	});
}