var basePath = $("#basePath").val();

$(function() {
	initData(1);
});

//Table数据加载
function initData(pageNumber){
	var msgIndex = top.layer.msg('数据加载中', {icon:16, shade:[ 0.5, '#999999' ], time:0});
	$("#table-container").load(basePath + "/shfw/sqhd/getBmjl.do",{'ajaxCmd': 'table', 'pageNumber': pageNumber, 'sqhdId': $("#sqhdId").val()}, function(){
		top.layer.close(msgIndex);
	});
}