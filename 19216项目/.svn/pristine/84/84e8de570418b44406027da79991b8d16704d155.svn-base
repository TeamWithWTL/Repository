/**
 *事件管理--处理
 */

var	basePath = $("#basePath").val();
$("#doneTime").datetimepicker({
    format: "yyyy-mm-dd",
    autoclose: true,
    minView: 'month',
    language: 'zh-CN',
    clearBtn: true
}).on("changeDate",function(ev){
	$("#addForm").data("bootstrapValidator").updateStatus("doneTime",  "NOT_VALIDATED",  null );
	$("#addForm").data("bootstrapValidator").validate('doneTime');
	});

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
        	result: {
                validators: {
                	notEmpty: {
                        message: '请填写处理结果'
                    }
                }
            },
            doneTime: {
                validators: {
                	notEmpty: {
                        message: '处理日期不能为空'
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
        	url: basePath + '/shgl/sjgl/doSave.do',
        	data: $("#addForm").serialize(),
        	success: function(data){
        		top.layer.close(msgIndex);
        		if(data=='success'){
        			top.layer.msg('数据提交成功', {
    					icon : 6,
    					time : 2000,
    					shade : [ 0.5, '#999999' ]
    				},function() {
    					top.layer.close(top.layer.getFrameIndex(window.name));
					});
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