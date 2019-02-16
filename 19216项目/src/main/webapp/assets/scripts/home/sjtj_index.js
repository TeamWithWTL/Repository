var basePath = $("#basePath").val();

//初始化日期选择控件
$("#start").datetimepicker({
	format : "yyyy-mm-dd",
	autoclose : true,
	minView : 'month',
	language : 'zh-CN',
	clearBtn: true
}).on("click", function() {
	$("#start").datetimepicker("setEndDate",$("#rxrq2").val());
});
$("#end").datetimepicker({
	format : "yyyy-mm-dd",
	autoclose : true,
	minView : 'month',
	language : 'zh-CN',
	clearBtn: true
}).on("click", function() {
	$("#end").datetimepicker("setStartDate",$("#rxrq1").val());
});

$(function() {
	initData(1);
});

/**
 * 热点新闻-分页查询
 */
// Table数据加载
function initData(pageNumber) {
	var msgIndex = top.layer.msg('数据加载中', {
		icon : 16,
		shade : [ 0.5, '#999999' ],
		time : 0
	});
	$("#table-container").load(basePath + "/home/sjtjList.do", {
		'ajaxCmd' : 'table',
		'pageNumber' : pageNumber,
		'title' : $("#title").val(),
		'startDate' : $("#rxrq1").val(),
		'endDate' : $("#rxrq2").val(),
		'sjtjName':$("#sjtjName").val()
	}, function() {
		top.layer.close(msgIndex);
	});
}

// 跳转查看界面
function goView(id) {
	$(".btn").blur();
	top.layer
			.open({
				type : 2,
				title : "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>查看事件</span>",
				fix : false,
				// shadeClose: true,
				area : [ '90%', '80%' ],
				content : basePath + '/shgl/sjgl/eventview.do?id=' + id,
				end : function() {
					initData(1);
				}
			});
}