/**
 * 修改个人信息
 */

var basePath = $("#basePath").val();
var msgIndex;

function cityCascade(city, town){
	var cityVal = $("#" + city).val();
	$.ajax({
		type: "POST",
		url:basePath + '/xtgl/yhgl/qxTown.do',
		async: false,
		data:{'city':cityVal},
		error: function(request) {
			$("#" + town).html('<option value="">所在镇(街道)</option><option value="">Error...</option>')
		},
		success: function(data){
			$("#" + town).html(data);
		}
	}); 
}


/*//轮播图上传
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
			}else{
				top.layer.msg('轮播图上传失败，请联系管理员', {shade : [ 0.5, '#999999' ]});
			}
		}
	});
});*/

$(document).ready(function(){
	// 初始化表单验证
	$('#addForm').bootstrapValidator({
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        submitButtons: '#saveBtn1',
        fields: {
        	name: {
                validators: {
                    notEmpty: {
                        message: '姓名不能为空'
                    }
                },
                stringLength: {
                    min: 1,
                    max: 40,
                    message: '名称长度必须在1到40之间'
                }
            }
        }
    });
	
	// 保存
	$("#saveBtn1").click(function(){
		var bv = $("#addForm").data('bootstrapValidator');
		bv.validate();
		if(bv.isValid()){
	        msgIndex = top.layer.msg('数据保存中', {icon:16, shade:[ 0.5, '#999999' ], time:0});
			sumForm(msgIndex);
	    }
	});
});

function sumForm(msgIndex){
	$.ajax({
    	type: 'POST',
    	url: basePath + '/doSave.do?code=' + $("#accCode").val(),
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


$("#cancleBtn").click(function(){
	var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
});