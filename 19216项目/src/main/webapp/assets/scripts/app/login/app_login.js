var basePath = $("#basePath").val();

//cookie 初始化
$(document).ready(function(){
	if ($.cookie("rmbUser") == "true") {
        $("#rmbUser").attr("checked", true);
        $("#userName").val($.cookie("wscUsername"));
        $("#passWord").val($.cookie("wscPassword"));
    }
/*	mui("#loginForm").on('tap','#rmbUser',function(){
		if($("#rmbUser").prop("checked") == true){
    	   $("#rmbUser").prop("checked",true);
       }else{
           $("#rmbUser").prop("checked",false);
       }
	});*/
});

/**
 * app--登陆
 */
mui(document.body).on('tap','#loginGo',function(){
	
	var _uname = $("#userName").val();
	var _pwd = $("#passWord").val();
	//检验是否记住密码，是，将账号密码保存到 cookie 中，否清空 cookie	
	if ($("#rmbUser").hasClass("mui-active")) {
        var wscUsername = $("#userName").val();
        var wscPassword = $("#passWord").val();
        $.cookie("rmbUser", "true", { expires: 7 }); // 存储一个带7天期限的 cookie
        $.cookie("wscUsername", wscUsername, { expires: 7 }); // 存储一个带7天期限的 cookie
        $.cookie("wscPassword", wscPassword, { expires: 7 }); // 存储一个带7天期限的 cookie
    }
    else {
        $.cookie("rmbUser", "false", { expires: -1 });        // 删除 cookie
//        $.cookie("wscUsername", '', { expires: -1 });
        $.cookie("wscPassword", '', { expires: -1 });
    }
	
	if (!_uname) {
		mui.toast('用户名不能为空',{ duration:'long', type:'div' });
		return false;
	}else if (!_pwd) {
		mui.toast('密码不能为空',{ duration:'long', type:'div' });
		return false;
	}else {
//		var msgIndex = top.layer.msg('登陆中...', {
//			icon : 16,
//			shade : [ 0.5, '#999999' ],
//			time : 3000
//		});
		
		$.ajax({
			type : "POST",
			url : basePath + "/appLogin/login.do",
			data : $('#loginForm').serialize(),
			success : function(data) {
//				top.layer.close(msgIndex);
				switch (data) {
					case 'succ':
						window.location.href = basePath + "/app/home/index.do";
						break;
					case 'paserro':
						mui.toast('用户名或密码错误',{ duration:'long', type:'div' });
						break;
					case 'expired':
						mui.toast('账户已失效',{ duration:'long', type:'div' });
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

/**
 * 跳转到注册页面
 */
mui("#registId").on('tap','a',function(){
	  mui.openWindow({
		    url: basePath + "/appLogin/jumpToRegist.do", 
		    id:'info'
		  });
});