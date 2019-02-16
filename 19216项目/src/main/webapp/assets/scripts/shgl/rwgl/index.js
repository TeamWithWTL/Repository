var basePath = $("#basePath").val();
$(function() {
	initData(1);
});

//Table数据加载
function initData(pageNumber){
	var msgIndex = top.layer.msg('数据加载中', {icon:16, shade:[ 0.5, '#999999' ], time:0});
	$("#table-container").load(basePath + "/shgl/rwgl/myDeal.do",{'ajaxCmd': 'table', 'pageNumber': pageNumber, 'title': $("#title").val()}, function(){
		top.layer.close(msgIndex);
	});
}

/**
 * 跳转到查看界面
 * @param msg
 * @returns
 */
function goView(taskId,clId){
	$(".btn").blur();
	top.layer.open({
	    type: 2,
	    title: "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>任务处理详情</span>",
	    fix: false,
	    area: ['80%', '80%'],
	    content: basePath + '/shgl/rwgl/clView.do?taskId='+taskId+'&clId='+clId,
	    end:function(){
	    	initData(1);
		  }
	  });
}
