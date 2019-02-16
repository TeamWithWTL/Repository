/***
 * 数据中心---事件状态统计
 */

var basePath = $("#basePath").val();

$(function(){
	var table = $("#cmd").val();
	initData(1,table);
});

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


/**
 * 查询跳转
 * @param pageNumber
 * @param flag
 */
function initData(pageNumber,table){
	var commId = $("#commId option:selected").val();
    var ssId  = $("#ssId option:selected").val();
    if(table == null){
    	var table1 = $("#cmd").val();
    	table = table1;
    }
    var msgIndex = top.layer.msg('数据加载中', {icon:16, shade:[ 0.5, '#999999' ], time:0});
	$("#table-container").load(basePath + "/sjzx/sjtj/sjzttjindex.do",{'ajaxCmd': table, 'pageNumber': pageNumber,'startTime':$("#startTime").val(),'endTime':$("#endTime").val(),'commId':commId,'ssId':ssId}, function(){
		top.layer.close(msgIndex);
	});
}

function showSs(sec,ssId){
	var _html = '<option value="">-- 所属服务站--</option>';
	$.ajax({
		type : "POST",
		url : basePath + "/shgl/wggl/getComData.do",
		data : {'comId':$(sec).val()},
		success : function(data) {
			var obj = JSON.parse(data);
			if(obj.length>0){
				for(var i=0;i<obj.length;i++){
					_html+='<option value="'+obj[i].id+'">'+obj[i].name+'</option>';
				}
			}
			$("#"+ssId).html(_html);
		},
		error:function(){
			$("#"+ssId).html(_html);
		}
	});
}



