var basePath = $("#basePath").val();

//初始化日期选择控件
$("#start").datetimepicker({
	format : "yyyy-mm-dd",
	autoclose : true,
	minView : 'month',
	language : 'zh-CN',
	clearBtn: true
});

$(function() {
	initData(1);
});


//Table数据加载
function initData(pageNumber){
	var msgIndex = top.layer.msg('数据加载中', {icon:16, shade:[ 0.5, '#999999' ], time:0});
	$("#table-container").load(basePath + "/shgl/rwgl/rwhz.do",{
		'ajaxCmd': 'table', 
		'pageNumber': pageNumber, 
		'title': $("#title").val(),
		'startD' : $("#rxrq1").val()
		}, function(){
		top.layer.close(msgIndex);
	});
}

function goView(taskId){
	$(".btn").blur();
	top.layer.open({
	    type: 2,
	    title: "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>任务处理详情</span>",
	    fix: false,
	    area: ['80%', '80%'],
	    content: basePath + '/shgl/rwgl/rwhzView.do?rwId='+taskId,
	    end:function(){
	    	initData(1);
		  }
	  });
}