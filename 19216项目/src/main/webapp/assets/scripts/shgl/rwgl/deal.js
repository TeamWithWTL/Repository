
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

//初始化上传组件
$.SubFormWithAttrs.init(basePath, basePath + '/pub/uploadFile.do', 'picker', '请选择文件', '5', null, 'attrlist', 'fName');

function save(){
	var filelist = $("#attrlist li").size();
	if(filelist > 0){
		$.SubFormWithAttrs.subFrom("doSave()");
	}else{
		doSave();
	}
}

//保存
function doSave(){
	
	var bv = $("#addForm").data('bootstrapValidator');
	bv.validate();
	if(bv.isValid()){
        var msgIndex = top.layer.msg('数据保存中', {icon:16, shade:[ 0.5, '#999999' ], time:0});
        $.ajax({
        	type: 'POST',
        	url: basePath + '/shgl/rwgl/doFeedback.do',
    		data: {
    			'dlId' : $("#dlId").val(), 
    			'content' : $("#content").val(),
    			'fName':$("#fName").val()
    			},
        	success: function(data){
        		top.layer.close(msgIndex);
        		if(data=='success'){
        			top.layer.msg('反馈成功', {
    					icon : 6,
    					time : 2000,
    					shade : [ 0.5, '#999999' ]
    				},function() {
					});
        			top.layer.close(top.layer.getFrameIndex(window.name));
        		}else{
        			top.layer.msg('反馈失败，请联系管理员', {shade : [ 0.5, '#999999' ]});
        		}
        	},
        	error: function(data){
        		top.layer.close(msgIndex);
        		top.layer.msg('程序出错，请联系管理员', {shade : [ 0.5, '#999999' ]});
        	}
        });
    }
};

$("#cancleBtn").click(function(){
	var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
});