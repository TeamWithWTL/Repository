var	basePath = $("#basePath").val();

//验证
$(document).ready(function(){
	$('#addForm').bootstrapValidator({
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
//        submitButtons: '#saveBtn',
        fields: {
        	content: {
                validators: {
                	notEmpty: {
                        message: '请填写处理意见'
                    }
                }
            }
        }
    });
});

//保存
$("#saveBtn").click(function(){
	var curRoleId = $("#curRoleId").val();
	//  如果当前处理角色 是街道办信息员 或者 接单办领导 需要选择接收人
	if($("#personlistValue").val()==""){
		top.layer.alert("请选择服务站!",{ icon:0,title:"提示"});
		return;
	}
	var bv = $("#addForm").data('bootstrapValidator');
	bv.validate();
	if(bv.isValid()){
        var msgIndex = top.layer.msg('数据保存中', {icon:16, shade:[ 0.5, '#999999' ], time:0});
        $.ajax({
        	type: 'POST',
        	url: basePath + '/shgl/sjgl/toServiceStation.do',
    		data: {'id': $("#id").val(), 'content': $("#content").val(),'ssId':$("#personlistValue").val()},
        	success: function(data){
        		top.layer.close(msgIndex);
        		if(data=='success'){
        			top.layer.msg('数据提交成功', {
    					icon : 6,
    					time : 2000,
    					shade : [ 0.5, '#999999' ]
    				},function() {
					});
        			top.layer.close(top.layer.getFrameIndex(window.name));
 
        		}else{
        			top.layer.msg('数据提交失败，请联系管理员', {shade : [ 0.5, '#999999' ]});
        		}
        	},
        	error: function(data){
        		top.layer.close(msgIndex);
        		top.layer.msg('程序出错，请联系管理员', {shade : [ 0.5, '#999999' ]});
        	}
        });
    }
});

$("#cancleBtn").click(function(){
	var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
});



//显示接收人
function showTree(){
	top.layer.open({
	    type: 2,
	    title: "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>选择人员</span>",
	    fix: false,
	    area: ['50%', '80%'],
	    content: basePath + '/shgl/sjgl/showTree.do?curRoleId='+$("#curRoleId").val(),
	    end:function(){
		  }
	  });
}
//移除接收人
function deleteMember(accCode){
	$("#"+accCode+"_member").remove();
	$("#personlistValue").val("");
}