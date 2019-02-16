/**
 *事件管理--上报
 */

var	basePath = $("#basePath").val();

$(function(){
	initData(1);
});

//Table数据加载
function initData(pageNumber){
	var msgIndex = top.layer.msg('数据加载中', {icon:16, shade:[ 0.5, '#999999' ], time:0});
	$("#table-container").load(basePath + "/shgl/sjgl/getPerData.do",{'ajaxCmd': 'table', 'pageNumber': pageNumber,'name':$("#name").val()}, function(){
		top.layer.close(msgIndex);
		
	});
}

//保存
$("#saveBtn").click(function(){
	var codes = '';
	// 获取被选中的数据ID
	var chkItems = $('input[name="icheck"]');
	$.each(chkItems, function(index, chkItem){
		if($(chkItem).is(":checked")){
			codes = $(chkItem).val();
		}
	});
	if(codes == ''){
		top.layer.confirm("请选择上报人员",{
			 icon:0,
			 title:"提示"
		});
		return false;
	}
    var msgIndex = top.layer.msg('数据保存中', {icon:16, shade:[ 0.5, '#999999' ], time:0});
    $.ajax({
    	type: 'POST',
    	url: basePath + '/shgl/sjgl/doReport.do',
    	data: {'id':$("#id").val(),'accCode':codes},
    	success: function(data){
    		top.layer.close(msgIndex);
    		if(data=='success'){
    			top.layer.msg('事件已上报', {
					icon : 6,
					time : 2000,
					shade : [ 0.5, '#999999' ]
				},function() {
					top.layer.close(top.layer.getFrameIndex(window.name));
				});
    		}else{
    			top.layer.msg('事件上报失败，请联系管理员', {shade : [ 0.5, '#999999' ]});
    		}
    	},
    	error: function(data){
    		top.layer.close(msgIndex);
    		top.layer.msg('程序出错，请联系管理员', {shade : [ 0.5, '#999999' ]});
    	}
    });
});

$("#cancleBtn").click(function(){
	var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
});