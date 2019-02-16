/**
 * 系统管理-组织架构管理-重命名机构
 */
var basePath = $("#basePath").val();

// 表单验证
$(document).ready(function() {
    $('#myForm').bootstrapValidator({
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        submitButtons: '#saveBtn',
        fields: {
        	deptName: {
                validators: {
                    notEmpty: {
                        message: '机构名称不能为空'
                    },
                    stringLength: {
                        min: 1,
                        max: 50,
                        message: '机构名称长度必须在1到50之间'
                    }
                }
            }
        }
    });
});

// 保存
$("#saveBtn").click(function(){
	var bv = $("#myForm").data('bootstrapValidator');
	bv.validate();
	if(bv.isValid()){
        var msgIndex = top.layer.msg('数据保存中...', {icon:16, shade:[ 0.5, '#999999' ], time:0});
        $.ajax({
        	type: 'POST',
        	url: basePath + '/xtgl/zzjggl/doSave.do',
        	data: $("#myForm").serialize(),
        	success: function(data){
        		top.layer.close(msgIndex);
        		if(data == 'succ'){
        			top.layer.msg('数据保存成功', {
    					icon : 6,
    					time : 2000,
    					shade : [ 0.5, '#999999' ]
    				},function() {
    					top.layer.close(top.layer.getFrameIndex(window.name));
					});
        		}else{
        			top.layer.msg('操作失败，请联系管理员', {shade : [ 0.5, '#999999' ]});
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