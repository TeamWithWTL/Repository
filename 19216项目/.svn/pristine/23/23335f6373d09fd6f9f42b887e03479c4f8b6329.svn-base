var basePath = $("#basePath").val();

$(function(){
	initData(1);
});

//Table数据加载
function initData(pageNumber){
	var msgIndex = top.layer.msg('数据加载中', {icon:16, shade:[ 0.5, '#999999' ], time:0});
	$(".article-feedback").load(basePath + "/shfw/sqhd/goView.do",{'ajaxCmd': 'table', 'pageNumber': pageNumber, 'sqhdId': $("#sqhdId").val(), 'id': $("#id").val()}, function(){
		top.layer.close(msgIndex);
	});
}

//附件下载
function download(newFilename, oldFilename){
	window.location.href = basePath + '/pub/downloadbystream.do?fileName=' + newFilename + '&oldFileName=' + oldFilename;
}

//跳转留言审核页面
function goShView(id){
	top.layer.open({
		type : 2,
		title : "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>社区活动留言</span>",
		content : basePath+'/shfw/sqhd/goShView.do?id='+id,
		area: ['80%', '80%'],
		shadeClose: true, //点击遮罩关闭
		end : function() {
			initData(1);
		}
	});
}
