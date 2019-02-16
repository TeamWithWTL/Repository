/**
 * 系统管理--参数项管理--添加
 */

var	basePath = $("#basePath").val();

//验证
$(document).ready(function(){
	$('#addForm').bootstrapValidator({
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        submitButtons: '#saveBtn',
        fields: {
        	itemCode: {
                validators: {
                    notEmpty: {
                        message: '参数项编号不能为空'
                    },
                    stringLength: {
                        min: 1,
                        max: 40,
                        message: '参数项编号长度必须在1到40之间'
                    },
                    remote: {
                        url: basePath + '/xtgl/sjzdgl/checkDescCode.do?sjzdCode='+$("#code").val(),
                        message: '参数项编号已存在',
                        delay: 3000,
                        type: 'POST'
                    },
                    regexp: {
                        regexp: /^[a-zA-Z0-9]+$/,
                        message: '参数项编号只能包含大写、小写、数字'
                    }
                }
            },
            itemName: {
                validators: {
                	notEmpty: {
                        message: '参数项名称不能为空'
                    },
                    stringLength: {
                        min: 1,
                        max: 40,
                        message: '参数项名称长度必须在1到40之间'
                    }
                }
            }
        }
    });
});

//保存
$("#saveBtn").click(function(){
	var bv = $("#addForm").data('bootstrapValidator');
	bv.validate();
	if(bv.isValid()){
        var msgIndex = top.layer.msg('数据保存中', {icon:16, shade:[ 0.5, '#999999' ], time:0});
        $.ajax({
        	type: 'POST',
        	url: basePath + '/xtgl/sjzdgl/doDescSave.do?descId='+$("#id").val(),
        	data: $("#addForm").serialize(),
        	success: function(data){
        		top.layer.close(msgIndex);
        		if(data=='success'){
        			top.layer.msg('数据保存成功', {
    					icon : 6,
    					time : 2000,
    					shade : [ 0.5, '#999999' ]
    				},function() {
    					top.layer.close(top.layer.getFrameIndex(window.name));
					});
        		}else{
        			top.layer.msg('数据保存失败，请联系管理员', {shade : [ 0.5, '#999999' ]});
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