/**
 * 社会服务-社区活动积分流水js
 */
var basePath = $("#basePath").val();

$(function(){
	initData(1);
});

//初始化日期选择控件
/*$("#datetimepicker").datetimepicker({
    format: "yyyy-mm-dd",
    autoclose: true,
    minView: 'month',
    language: 'zh-CN',
    todayBtn: true
});

$("#datetimepicker2").datetimepicker({
    format: "yyyy-mm-dd",
    autoclose: true,
    minView: 'month',
    language: 'zh-CN',
    todayBtn: true
});*/

//初始化日期选择控件
$("#datetimepicker").datetimepicker({
	format : 'yyyy-mm-dd',
	weekStart : 1,
	autoclose : true,
	startView : 2,
	minView: 'month',
	forceParse : false,
	language : 'zh-CN',
	//startDate : new Date(),
	clearBtn: true
}).on("click", function() {
	$("#datetimepicker").datetimepicker("setEndDate",$("#endDate").val());
});

// 初始化日期选择控件
$("#datetimepicker2").datetimepicker({
	format : 'yyyy-mm-dd',
	weekStart : 1,
	autoclose : true,
	startView : 2,
	minView: 'month',
	forceParse : false,
	language : 'zh-CN',
	//startDate :new Date() ,
	clearBtn : true
}).on("click",function() {
	 $("#datetimepicker2").datetimepicker("setStartDate",$("#startDate").val()); 
});

$(document).on("click","#datetimepicker2",function(){  
    $('#datetimepicker2').datetimepicker('show');  
});  

$(document).on("click","#datetimepicker",function(){  
$('#datetimepicker').datetimepicker('show');  
});  

//Table数据加载
function initData(pageNumber){
	var msgIndex = top.layer.msg('数据加载中', {icon:16, shade:[ 0.5, '#999999' ], time:0});
	$("#table-container").load(basePath + "/shfw/jftj/jflsList.do",{'ajaxCmd': 'table', 'pageNumber': pageNumber, 'accCode':$("#accCode").val(), 'startDate':$("#startDate").val(), 'endDate':$("#endDate").val()}, function(){
		top.layer.close(msgIndex);
	});
}