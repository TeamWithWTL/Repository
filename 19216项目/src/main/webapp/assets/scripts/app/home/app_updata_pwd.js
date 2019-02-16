/**
 * 修改密码
 */
var basePath = $("#basePath").val();

mui(document.body).on('tap','#doUpdata',function(){
	
	var oldpwd = $("#oldpwd").val();
	var newpwd = $("#newpwd").val();
	var truepwd = $("#truepwd").val();
	if (!oldpwd) {
		mui.toast('原密码不能为空',{ duration:'long', type:'div' });
		return false;
	}else if (!newpwd) {
		mui.toast('新密码不能为空',{ duration:'long', type:'div' });
		return false;
	}else if(!truepwd){
		mui.toast('确认密码不能为空',{ duration:'long', type:'div' });
		return false;
	}else {
		var msgIndex = top.layer.msg('保存中...', {
			icon : 16,
			shade : [ 0.5, '#999999' ],
			time : 3000
		});
		$.ajax({
			type : "POST",
			url : basePath + "/app/home/savePwd.do",
			data : $('#updatapwdForm').serialize(),
			success : function(data) {
				top.layer.close(msgIndex);
				switch (data) {
					case 'succ':
						mui.alert('','密码修改成功','确定',function(){
							window.location.href = basePath + "/app/home/toUserInfo.do";
						});
						break;
					case 'oldpwdno':
						mui.toast('原密码不正确',{ duration:'long', type:'div' });
						break;
					case 'newpwdno':
						mui.toast('新密码与原密码不能相同',{ duration:'long', type:'div' });
						break;
					case 'truepwdno':
						mui.toast('两次输入的密码不一致',{ duration:'long', type:'div' });
						break;
					default:
						mui.toast('用户名或密码错误',{ duration:'long', type:'div' });
						break;
				}
			},
			error : function() {}
		});
	}
	});