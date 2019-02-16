/**
 * 社会组织-活动统计-首页js
 */	

var basePath = $("#basePath").val();

//初始化日期选择控件
$("#start").datetimepicker({
    format: "yyyy-mm-dd",
    autoclose: true,
    minView: 'month',
    language: 'zh-CN',
    clearBtn: true
}).on("click", function() {
	$("#start").datetimepicker("setEndDate",$("#endTime").val());
});

$("#end").datetimepicker({
    format: "yyyy-mm-dd",
    autoclose: true,
    minView: 'month',
    language: 'zh-CN',
    clearBtn: true
}).on("click",function() {
	 $("#end").datetimepicker("setStartDate",$("#startTime").val()); 
});

$(function() {
	initData(1);
});

//Table数据加载
function initData(pageNumber){
	var msgIndex = top.layer.msg('数据加载中', {icon:16, shade:[ 0.5, '#999999' ], time:0});
	$("#table-container").load(basePath + "/shzz/hdtj/index.do",{'ajaxCmd': 'table', 'pageNumber': pageNumber, 'name': $("#name").val(), 'startTime':$("#startTime").val(), 'endTime':$("#endTime").val()}, function(){
		top.layer.close(msgIndex);
		
	});
}

//跳转查看反馈详情页面
function goView(zzId){
	$(".btn").blur();
	top.layer.open({
	    type: 2,
	    title: "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>活动反馈界面</span>",
	    fix: false,
	   // shadeClose: true,
	    area: ['80%', '80%'],
	    content: basePath+'/shzz/hdtj/goHdfkView.do?zzId='+zzId,
	  });
}