var basePath = $("#basePath").val();

//附件下载
function download(newFileName, oldFileName){
	window.location.href = basePath + '/pub/downloadbystream.do?fileName=' + newFileName + '&oldFileName=' + oldFileName;
}

$(function() {
	initData(1);
});

//Table数据加载
function initData(pageNumber){
	var msgIndex = top.layer.msg('数据加载中', {icon:16, shade:[ 0.5, '#999999' ], time:0});
	$("#table-container").load(basePath + "/shzz/hdtj/goHdfkView.do",{'ajaxCmd': 'table', 'pageNumber': pageNumber, 'zzId': $("#zzId").val()}, function(){
		top.layer.close(msgIndex);
	});
}

function goView(id){
	$(".btn").blur();
	top.layer.open({
	    type: 2,
	    title: "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>活动反馈详情界面</span>",
	    fix: false,
	   // shadeClose: true,
	    area: ['80%', '80%'],
	    content: basePath+'/shzz/hdtj/goView.do?id='+id,
	  });
}