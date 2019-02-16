/**
 * 系统登录
 */

// 防止登录页被内嵌到iframe
if (window != top) {
	top.location.href = location.href;
}

var basePath = $("#basePath").val();

// 更换验证码
function changeCode(){
	$("#codeImage").attr('src', basePath + '/codeImage?' + Math.random());
}

// 回车登录
$(document).keyup(function(event) {
	if (event.keyCode == 13) {
		if ($('body').find('.layui-layer-shade').length > 0) {
			layer.closeAll();
			return false;
		}
		$("#loginBtn").trigger("click");
	}
});

// 登录事件
$('#loginBtn').click(function() {
	var _uname = $("#signin-acccode").val();
	var _pwd = $("#signin-password").val();
	var _code = $("#authCode").val();
	if (!_uname) {
		layer.alert('请输入用户名！', {
			icon : 0,
			title : "提示"
		});
		return false;
	}else if (!_pwd) {
		layer.alert('请输入密码！', {
			icon : 0,
			title : "提示"
		});
		return false;
	}else if (!_code) {
		layer.alert('请输入验证码！', {
			icon : 0,
			title : "提示"
		});
		return false;
	}else {
		var loadi = layer.msg('正在验证，请稍后...', {
			icon : 16,
			time : 0,
			shade : [ 0.5, '#999999' ]
		});
		$.ajax({
			type : "POST",
			url : basePath + "/login/checkLogin.do",
			data : $('#loginForm').serialize(),
			success : function(data) {
				layer.close(loadi);
				switch (data) {
					case 'succ':
						window.location.href = basePath + "/main.do";
						break;
					case 'expired':
						layer.alert('登录账户已失效！', {
							title: '提示',
							icon: 5
						}, function(index){
							layer.close(index);
							changeCode();
							$("#authCode").val('');
						});
						break;
					case 'authCodeErr':
						layer.alert('验证码输入错误，请重试！', {
							title: '提示',
							icon: 5
						}, function(index){
							layer.close(index);
							changeCode();
							$("#authCode").val('');
						});
						break;
					case 'noPower':
						layer.alert('您无权限登录系统！', {
							title: '提示',
							icon: 5
						}, function(index){
							layer.close(index);
						});
						break;
					case 'noCode':
						layer.alert('缓存已清理，请重新输入验证码！', {
							title: '提示',
							icon: 5
						}, function(index){
							layer.close(index);
							changeCode();
							$("#authCode").val('');
						});
						break;
					default:
						layer.alert('用户名或密码错误，请重试！', {
							title: '提示',
							icon: 5
						}, function(index){
							layer.close(index);
							changeCode();
							$("#authCode").val('');
						});
						break;
				}
			},
			error : function() {
				layer.close(loadi);
				layer.alert('程序出现错误，请稍后再试！', {
					icon : 5,
					title : "提示"
				}, function(index){
					layer.close(index);
					changeCode();
					$("#authCode").val('');
				});
			}
		});
	}
});
