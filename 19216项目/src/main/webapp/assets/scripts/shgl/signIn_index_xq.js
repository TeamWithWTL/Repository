var basePath = $("#basePath").val();

//初始化日期选择控件
$("#start").datetimepicker({
	format : "yyyy-mm",
	autoclose : true,
	startView:'year',
    minView:'year',
	language : 'zh-CN',
});

$(function() {
	initData(1);
});


//Table数据加载
function initData(pageNumber){
	var msgIndex = top.layer.msg('数据加载中', {icon:16, shade:[ 0.5, '#999999' ], time:0});
	$("#table-container").load(basePath + "/shgl/signIn/signInXp.do",{
		'ajaxCmd': 'table', 
		'pageNumber': pageNumber,
		'months' : $("#months").val(),
		'accCode' : $("#accCode").val()
		}, function(){
		top.layer.close(msgIndex);
	});
}
