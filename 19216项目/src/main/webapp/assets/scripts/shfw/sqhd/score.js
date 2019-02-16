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
                    message: '积分不能为空'
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
        }
    }
});

//保存事件
function subMyForm(){
	if($("#integral").val() == null || $("#integral").val() == ""){
		top.layer.msg('积分不能为空', {
				icon : 5,
				time : 2000,
				shade : [ 0.5, '#999999' ]
			});
		return;
	}
    var msgIndex = top.layer.msg('数据保存中', {icon:16, shade:[ 0.5, '#999999' ], time:0});
    saveScore();
}

function saveScore(){
	var msgIndex = top.layer.msg('数据保存中', {icon:16, shade:[ 0.5, '#999999' ], time:0});
	$.ajax({
      	type: 'POST',
      	url: basePath + '/shfw/sqhd/saveScore.do',
      	data: $("#addForm").serialize(),
      	success: function(data){
      		top.layer.close(msgIndex);
      		if(data == 'success'){
      			top.layer.msg('设置积分成功 !', {
  					icon : 6,
  					time : 2000,
  					shade : [ 0.5, '#999999' ]
  				},function() {
  					top.layer.close(top.layer.getFrameIndex(window.name));
				});
      		}else if(data == 'ccszjf'){
      			top.layer.msg('输入积分超出活动设置的最大积分，请重新输入!', {
  					icon : 5,
  					time : 2000,
  					shade : [ 0.5, '#999999' ]
  				},function() {
  					//top.layer.close(top.layer.getFrameIndex(window.name));
				});
      		}else{
      			top.layer.msg('数据保存失败，请联系管理员!', {shade : [ 0.5, '#999999' ]});
      		}
      	},
      	error: function(data){
      		top.layer.close(msgIndex);
      		top.layer.msg('程序出错，请联系管理员!', {shade : [ 0.5, '#999999' ]});
      	}
      });
}

$("#cancleBtn").click(function(){
	var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
});