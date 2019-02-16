/**
 * 修改密码
 */

var basePath = $("#basePath").val();

$(document).ready(function(){
	// 表单验证
	$('#addForm').bootstrapValidator({
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        submitButtons: '#saveBtn',
        fields: {
        	password: {
                validators: {
                    notEmpty: {
                        message: '原密码不能为空'
                    },
                    remote: {
                        url: basePath + '/checkPwd.do',
                        message: '原密码输入不正确',
                        delay: 3000,
                        type: 'POST'
                    }
                }
            },
            pwd: {
                validators: {
                	notEmpty: {
                        message: '新密码不能为空'
                    },
                    stringLength: {
                        min: 1,
                        max: 40,
                        message: '新密码长度必须在1到40之间'
                    },
                    remote: {
                        url: basePath + '/checkPwd1.do',
                        message: '新密码与原密码相同',
                        delay: 3000,
                        type: 'POST'
                    }
                }
            },
            pwd1: {
                validators: {
                	notEmpty: {
                        message: '确认密码不能为空'
                    },
                    remote: {
                        url: basePath + '/checkPwd2.do',
                        message: '确认密码与新密码不相同',
                        delay: 3000,
                        type: 'POST'
                    }
                }
            }
        }
    });
	
	//头像上传
	layui.use('upload', function(){
		var msgIndex;
		layui.upload({
			url: basePath + '/pub/uploadFile.do',
			title:'上传图片',
			type: 'images',
			ext: 'jpg|png|',
			before: function(input){
				// 文件上传之前回调
				msgIndex = top.layer.msg('图片上传中...', {icon:16, shade:[ 0.5, '#999999' ], time:0});
			},
			success: function(res){
				// 上传成功后的回调
				top.layer.close(msgIndex);
				var code = res.code;
				if(code == '200'){
					$("#picPath").val(res.newName);
					$("#myImg").attr("src", basePath + '/showPic?fileName=' + res.newName);
					$.ajax({
						type: 'POST',
			        	url: basePath + '/doUpdatePic.do',
			        	data: {
			        		'picePath':res.newName
			        		},
			        		success: function(data){
				        		top.layer.close(msgIndex);
				        		if(data=='success'){
				        			top.layer.msg('上传头像成功', {
				    					icon : 6,
				    					time : 2000,
				    					shade : [ 0.5, '#999999' ]
				    				},function() {
//				    					top.layer.close(top.layer.getFrameIndex(window.name));
									});
				        		}else{
				        			top.layer.msg('上传头像失败，请联系管理员', {shade : [ 0.5, '#999999' ]});
				        		}
				        	},
				        	error: function(data){
				        		top.layer.close(msgIndex);
				        		top.layer.msg('程序出错，请联系管理员', {shade : [ 0.5, '#999999' ]});
				        	}
					});
				}else{
					top.layer.msg('图片上传失败，请联系管理员', {shade : [ 0.5, '#999999' ]});
				}
			}
		});
	});
	
	// 保存密码
	$("#saveBtn").click(function(){
		var bv = $("#addForm").data('bootstrapValidator');
		bv.validate();
		if(bv.isValid()){
	        var msgIndex = top.layer.msg('数据保存中', {icon:16, shade:[ 0.5, '#999999' ], time:0});
	        $.ajax({
	        	type: 'POST',
	        	url: basePath + '/doUpdatepwd.do',
	        	data: {
	        		'pwd':$("#pwd").val()
	        		},
	        	success: function(data){
	        		top.layer.close(msgIndex);
	        		if(data=='success'){
	        			top.layer.msg('密码修改成功', {
	    					icon : 6,
	    					time : 2000,
	    					shade : [ 0.5, '#999999' ]
	    				},function() {
	    					top.layer.close(top.layer.getFrameIndex(window.name));
						});
	        		}else{
	        			top.layer.msg('密码修改失败，请联系管理员', {shade : [ 0.5, '#999999' ]});
	        		}
	        	},
	        	error: function(data){
	        		top.layer.close(msgIndex);
	        		top.layer.msg('程序出错，请联系管理员', {shade : [ 0.5, '#999999' ]});
	        	}
	        });
	    }
	});
});

$("#cancleBtn").click(function() {
	var index = parent.layer.getFrameIndex(window.name);
	parent.layer.close(index);
});
