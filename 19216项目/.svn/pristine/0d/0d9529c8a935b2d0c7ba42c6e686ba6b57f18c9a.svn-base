/**
 * 系统管理-系统菜单管理-添加、编辑菜单
 */
var basePath = $("#basePath").val();

$('#icons').on('change', function(e) { 
	$("#icoUrl").val('fa ' + e.icon);
});

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
        	menuName: {
                validators: {
                    notEmpty: {
                        message: '菜单名称不能为空'
                    },
                    stringLength: {
                        min: 1,
                        max: 20,
                        message: '菜单名称长度必须在1到20之间'
                    }
                }
            },
            aliasName: {
                validators: {
                    notEmpty: {
                        message: '菜单别名不能为空'
                    },
                    stringLength: {
                        min: 1,
                        max: 70,
                        message: '菜单别名长度必须在1到70之间'
                    }
                }
            },
            orderNo: {
                validators: {
                	notEmpty: {
                        message: '序号不能为空'
                    },
                    stringLength: {
                        min: 1,
                        max: 6,
                        message: '序号长度必须在1到11之间'
                    },
                    regexp: {
                        regexp: "^[1-9][0-9]*$",
                        message: '序号必须为大于0的正整数'
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
        var msgIndex = top.layer.msg('数据保存中', {icon:16, shade:[ 0.5, '#999999' ], time:0});
        $.ajax({
        	type: 'POST',
        	url: basePath + '/xtgl/xtcdgl/doSave.do',
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