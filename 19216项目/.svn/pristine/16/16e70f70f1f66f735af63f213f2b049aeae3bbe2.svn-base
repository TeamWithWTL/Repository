var basePath = $("#basePath").val();

$('#addForm').bootstrapValidator({
    feedbackIcons: {
    	 valid: 'glyphicon glyphicon-ok',
         invalid: 'glyphicon glyphicon-remove',
         validating: 'glyphicon glyphicon-refresh'
    },
    submitButtons: '#saveBtn',
    fields: {
    	integral: {
            validators: {
                notEmpty: {
                    message: '兑换积分不能为空'
                },
                stringLength: {
                    min: 1,
                    max: 11,
                    message: '长度必须在1到11之间'
                },
                regexp: {
                    regexp: /^(0|[1-9]\d{0,9})$/,
                    message: '请输入正确的兑换积分'
                }
            }
        },
        content: {
        	validators: {
        		notEmpty: {
                    message: '描述不能为空'
                },
          		stringLength: {
                    min: 1,
                    max: 300,
                    message: '长度必须在1到300之间'
                }
           }
        }
    }
});

//保存事件
function subMyForm(){
	/*if($("#integral").val() == null || $("#integral").val() == ""){
		top.layer.msg('兑换积分不能为空', {
				icon : 5,
				time : 2000,
				shade : [ 0.5, '#999999' ]
			});
		return;
	}
	if($("#content").val() == null || $("#content").val() == ""){
		top.layer.msg('描述不能为空', {
				icon : 5,
				time : 2000,
				shade : [ 0.5, '#999999' ]
			});
		return;
	}*/
	var bv = $("#addForm").data('bootstrapValidator');
	bv.validate();
	if(bv.isValid()){
	    var msgIndex = top.layer.msg('数据保存中', {icon:16, shade:[ 0.5, '#999999' ], time:0});
		doSave();
	}
}

function doSave(){
	var msgIndex = top.layer.msg('数据保存中', {icon:16, shade:[ 0.5, '#999999' ], time:0});
	$.ajax({
      	type: 'POST',
      	url: basePath + '/shfw/jftj/doSave.do',
      	data: $("#addForm").serialize(),
      	success: function(data){
      		top.layer.close(msgIndex);
      		if(data == 'success'){
      			top.layer.msg('兑换成功', {
  					icon : 6,
  					time : 2000,
  					shade : [ 0.5, '#999999' ]
  				},function() {
  					top.layer.close(top.layer.getFrameIndex(window.name));
				});
      		}else if(data == 'jfbz'){
      			top.layer.msg('积分不足，无法兑换', {
  					icon : 5,
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