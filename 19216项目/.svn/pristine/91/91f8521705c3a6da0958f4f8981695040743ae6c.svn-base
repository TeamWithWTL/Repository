/**
 * 协同办公--通讯录管理
 */
var basePath = $("#basePath").val();

$(function(){
	initData(1);
});

//Table数据加载
function initData(pageNumber){
	var msgIndex = top.layer.msg('数据加载中', {icon:16, shade:[ 0.5, '#999999' ], time:0});
	var deptId = $("#deptId").val();
	$("#table-container").load(basePath + "/xtbg/txlgl/index.do",{'ajaxCmd': 'table', 'pageNumber': pageNumber,'deptId': deptId, 'name': $("#name").val(),'duty': $("#duty").val(),'phone': $("#phone").val()}, function(){
		top.layer.close(msgIndex);
		
	});
}


//节点被点击时触发
function zTreeOnClick(event, treeId, treeNode){
	$("#deptId").val(treeNode.id);
	initData(1);
}