var basePath = $("#basePath").val();
var roleCode = $("#roleCode").val();

$(document).ready(function(){
	$("#jfId").show();
	if(roleCode.indexOf("14") !=-1){
		$("#updataXx").show();
	}
});

/*************************首页退出登录*******************************/
document.getElementById('loginApp').addEventListener('tap',function(){
	  mui.confirm('确认退出当前账号吗？','',['是','否'],function(e){
		  if(e.index == 1){
			  
		  }else{
		  mui.openWindow({
			    url: basePath + "/appLogin/toLogin.do", 
			    id:'toLogin.do'
			  });
		  }
	  },'div');
	});
/*************************首页-个人中心-修改密码*******************************/
document.getElementById('updataPwd').addEventListener('tap',function(){
		  mui.openWindow({
			    url: basePath + "/app/home/toUpdataPwd.do", 
			    id:'toLogin.do'
			  });
	});
/*************************首页-个人中心-修改信息*******************************/
document.getElementById('updataXx').addEventListener('tap',function(){
		  mui.openWindow({
			    url: basePath + "/app/home/toUpdataXx.do", 
			    id:'toUpdataXx.do'
			  });
	});