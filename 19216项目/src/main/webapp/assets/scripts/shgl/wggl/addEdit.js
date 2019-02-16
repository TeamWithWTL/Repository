/**
 * 服务站管理--添加
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
            name: {
                validators: {
                	notEmpty: {
                        message: '网格名称不能为空'
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
	var commId = $("#commId").val();
	if(commId == '' || commId == null){
		top.layer.alert("请选择所属社区",{
			 icon:0,
			 title:"提示"
		});
		return false;
	}
	var ssId = $("#ssId").val();
	if(ssId == '' || ssId == null){
		top.layer.alert("请选择所属服务站",{
			 icon:0,
			 title:"提示"
		});
		return false;
	}
	var managers = "";//负责人
	var phones = "";//负责人联系电话
	var fzrs = $("input[name='manager']");
	var fzrdhs = $("input[name='phone']");
	$.each(fzrs, function(index, fzr){
		// 将选项中的英文;去掉
		var manager = $(fzr).val();
		managers += manager + ";"
	});
	
	$.each(fzrdhs, function(index, fzrdh){
		// 将选项中的英文;去掉
		var phone = $(fzrdh).val();
		phones += phone + ";"
	});
	$("#managers").val(managers);
	$("#phones").val(phones);
	
	var phoneNum = phones.replace(";","");
	if(phoneNum != ""){
		if(!(/^\d{0,11}$/.test(phoneNum))){
			top.layer.alert("请输入正确11位手机号！",{
				 icon:0,
				 title:"提示"
			});
			return false;
		}
	}
	
	var bv = $("#addForm").data('bootstrapValidator');
	bv.validate();
	if(bv.isValid()){
        var msgIndex = top.layer.msg('数据保存中', {icon:16, shade:[ 0.5, '#999999' ], time:0});
        $.ajax({
        	type: 'POST',
        	url: basePath + '/shgl/wggl/doSave.do',
        	data: $("#addForm").serialize(),
        	success: function(data){
        		top.layer.close(msgIndex);
        		if(data == 'exists'){
        			top.layer.msg('数据已存在,请到列表中查看', {shade : [ 0.5, '#999999' ]},function() {
    					top.layer.close(top.layer.getFrameIndex(window.name));
					});
        		}else if(data=='success'){
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
