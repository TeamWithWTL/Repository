/**
 * 系统管理--数据字典--添加
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
        	code: {
                validators: {
                    notEmpty: {
                        message: '编号不能为空'
                    },
                    stringLength: {
                        min: 1,
                        max: 40,
                        message: '编号长度必须在1到40之间'
                    },
                    remote: {
                        url: basePath + '/xtgl/sjzdgl/checkCode.do',
                        message: '编号已存在',
                        delay: 3000,
                        type: 'POST'
                    },
                    regexp: {
                        regexp: /^[a-zA-Z0-9]+$/,
                        message: '编号只能包含大写、小写、数字'
                    }
                }
            },
            name: {
                validators: {
                	notEmpty: {
                        message: '名称不能为空'
                    },
                    stringLength: {
                        min: 1,
                        max: 40,
                        message: '名称长度必须在1到40之间'
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
        	url: basePath + '/xtgl/sjzdgl/doSave.do?id='+$("#code").val(),
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
