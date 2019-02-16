/**
 * 社会组织--中心介绍审核页面
 */
var basePath = $("#basePath").val();

//附件下载
function download(newFilename, oldFilename){
	window.location.href = basePath + '/pub/downloadbystream.do?fileName=' + newFilename + '&oldFileName=' + oldFilename;
}
//设置中心介绍信息审核
function setSh(id,flag){
	$.ajax({
		type : "POST",
		url : basePath + "/shzz/zxjs/setSh.do",
		data : {
			'id' : id,
			'flag' : flag
		},
		error : function(request) {
			top.layer.msg('审核失败，请联系管理员', {
				shade : [ 0.5, '#999999' ]
			});
		},
		success : function(data) {
			if (data == "success") {
				top.layer.msg('设置成功！', {
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