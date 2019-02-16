/**
 * 社会服务-社区服务审核js
 */	

var basePath = $("#basePath").val();

//设置社区服务审核状态
function setSh(id,flag){
	$.ajax({
		type : "POST",
		url : basePath + "/shfw/sqfw/setSh.do",
		data : {
			'id' : id,
			'flag' : flag
		},
		error : function(request) {
			top.layer.msg('程序出错，请稍后再试', {
				shade : [ 0.5, '#999999' ]
			});
		},
		success : function(data) {
			if (data == "success") {
				top.layer.msg('设置成功', {
					icon : 6,
					time : 2000,
					shade : [ 0.5, '#999999' ]
				}, function() {
					var index = parent.layer.getFrameIndex(window.name);
				    parent.layer.close(index);
				});
			} else {
				top.layer.msg('设置失败，请联系管理员', {
					shade : [ 0.5, '#999999' ]
				});
			}
		}
	});
}