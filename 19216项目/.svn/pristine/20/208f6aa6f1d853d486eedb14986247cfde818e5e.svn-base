var basePath;

//初始化日期选择控件
$("#start").datetimepicker({
    format: "yyyy-mm-dd",
    autoclose: true,
    minView: 'month',
    language: 'zh-CN',
    clearBtn: true
});
$("#end").datetimepicker({
    format: "yyyy-mm-dd",
    autoclose: true,
    minView: 'month',
    language: 'zh-CN',
    clearBtn: true
});

$(function() {
	basePath = $("#basePath").val();
	initData(1);
});


/***************************************************** 任务统计分页查询****************************************************/
function initData(pageNumber) {
	
	var startTime = $("#rxrq1").val();
	var endTime = $("#rxrq2").val();
	var sqId = $("#sqId option:selected").val();
	var fwzId = $("#fwzId option:selected").val();
	
	console.log("startTime"+startTime);
	console.log("endTime"+endTime);

	var msgIndex = top.layer.msg('数据加载中', {
		icon : 16,
		shade : [ 0.5, '#999999' ],
		time : 0
	});
	$("#table-container").load(basePath + "/sjzx/rwtj/initDataForRWTJ.do", {
		'ajaxCmd' : 'table',
		'pageNumber' : pageNumber,
		'startTime' : startTime,
		'endTime' : endTime,
		'sqId' : sqId,
		'fwzId' : fwzId
	}, function() {
		top.layer.close(msgIndex);
	});
}