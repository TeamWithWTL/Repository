var basePath = $("#basePath").val();

$(document).ready(function(){
	var radioCode = $('input:radio[name="zcCode"]:checked').val();
	$('input:radio[name="zcCode"]').each(function(){
		$("#jmpho").show();
		$("#cardCode").show();
		$(this).click(function(){
			var radioCode = $('input:radio[name="zcCode"]:checked').val();
			if(radioCode == 2){//法人注册
				$("#frCode").show();
				$("#userCode").show();
				$("#jmpho").hide();
				$("#frpho").show();
				$("#cardCode").hide();
			}else{
				$("#frCode").hide();
				$("#userCode").hide();
				$("#jmpho").show();
				$("#frpho").hide();
				$("#cardCode").show();
			}
		});
	});
});

mui(document.body).on('tap','#RegistGo',function(){
	
	var _uname = $("#userName").val();
	var _pwd = $("#passWord").val();
	var confirmPwd = $("#confirmPwd").val();
	var firstName = $("#firstName").val();
	var idCard = $("#idCard").val();
	var phoneNum = $("#phoneNum").val();
	var phoneNum2 = $("#phoneNum2").val();
	var frString = $("#frString").val();
	
	var radioCode = $('input:radio[name="zcCode"]:checked').val();
	// 居民注册
	if(radioCode == 1){
		if(!phoneNum2){
			mui.toast('账号不能为空',{ duration:'long', type:'div' });
			return false;
		}else if(!firstName){
			mui.toast('姓名不能为空',{ duration:'long', type:'div' });
		}else if(!idCard || !(/^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{4}$/.test(idCard))){//检验身份证号是否为空，格式是否正确
			mui.toast('身份证不能为空或格式不正确',{ duration:'long', type:'div' });
			return false;
		}else if(!_pwd || !(/^.{6,18}$/.test(_pwd))) {
			mui.toast('密码不能为空或密码必须在6~18之间',{ duration:'long', type:'div' });
			return false;
		}else if(!confirmPwd || confirmPwd != _pwd){
			mui.toast('密码不一致，请再一次输入',{ duration:'long', type:'div' });
			return false;
		}else{
			$.ajax({
				type : "POST",
				url : basePath + "/appLogin/regist.do",
				data : $('#registForm').serialize(),
				success : function(data) {
					switch (data) {
						case 'succ':
							mui.alert('','注册成功','确定',function(){
								window.location.href = basePath + "/appLogin/toLogin.do";
							});
							break;
						case 'paserro':
							mui.toast('用户名已存在',{ duration:'long', type:'div' });
							break;
						case 'noCard':
							mui.alert('千乘社区15726296312\n乐安社区8196620\n蒲姑社区8135291\n新城社区8196052', '身份证不存在请联系社区管理人员', function() {});
							break;
						default:
							mui.toast('用户名或密码错误',{ duration:'long', type:'div' });
							break;
					}
				},
				error : function() {}
			});
		}
	}else{
		if(!frString){
			mui.toast('组织机构代码不能为空',{ duration:'long', type:'div' });
			return false;
		}else if (!_uname) {
			mui.toast('账号不能为空',{ duration:'long', type:'div' });
			return false;
		}else if(!firstName){
			mui.toast('姓名不能为空',{ duration:'long', type:'div' });
		}else if(!phoneNum || !(/^\d{0,11}$/.test(phoneNum))){//检验手机号是否为空，格式是否正确
			mui.toast('手机号不能为空或格式不正确',{ duration:'long', type:'div' });
			return false;
		}else if(!_pwd || !(/^.{6,18}$/.test(_pwd))) {
			mui.toast('密码不能为空或密码必须在6~18之间',{ duration:'long', type:'div' });
			return false;
		}else if(!confirmPwd || confirmPwd != _pwd){
			mui.toast('密码不一致，请再一次输入',{ duration:'long', type:'div' });
			return false;
		}else{
			$.ajax({
				type : "POST",
				url : basePath + "/appLogin/regist.do",
				data : $('#registForm').serialize(),
				success : function(data) {
					switch (data) {
						case 'succ':
							mui.alert('','注册成功','确定',function(){
								window.location.href = basePath + "/appLogin/toLogin.do";
							});
							break;
						case 'paserro':
							mui.toast('账号已存在',{ duration:'long', type:'div' });
							break;
						case 'frerror':
							mui.toast('组织机构代码错误',{ duration:'long', type:'div' });
							break;
						case 'frerroron':
							mui.toast('法人已绑定',{ duration:'long', type:'div' });
							break;
						default:
							mui.toast('用户名或密码错误',{ duration:'long', type:'div' });
							break;
					}
				},
				error : function() {}
			});
		}
	}
});

/**
 * 跳转到协议界面
 */
//mui("#goXy").on('tap','a',function(){
//	  mui.openWindow({
//		    url: basePath + "/appLogin/jumpToXy.do", 
//		    id:'info'
//		  });
//	});