/**
 * 社会管理-社情民意-查看网格员列表
 */	
var basePath = $("#basePath").val();

$(function(){
	initData(1);
});

//Table数据加载
function initData(pageNumber){
	var msgIndex = top.layer.msg('数据加载中', {icon:16, shade:[ 0.5, '#999999' ], time:0});
	$("#table-container").load(basePath + "/shgl/sqmy/ckwgylist.do",{'ajaxCmd': 'table', 'pageNumber': pageNumber,'id': $("#id").val() ,'name': $("#name").val()}, function(){
		top.layer.close(msgIndex);
	});
}

//跳转查看网格员调查用户列表页面
function goDcList(wgyID){
	top.layer.open({
	    type: 2,
	    title: "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>调查详情列表</span>",
	    fix: false,
	   // shadeClose: true,
	    area: ['950px', '450px'],
	    content: basePath+'/shgl/sqmy/dcList.do?wgyID='+wgyID,
	  });
}