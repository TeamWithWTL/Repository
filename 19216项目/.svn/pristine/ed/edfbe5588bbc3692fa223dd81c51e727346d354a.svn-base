var basePath = $("#basePath").val();

//附件下载
function download(newFilename, oldFilename){
	window.location.href = basePath + '/pub/downloadbystream.do?fileName=' + newFilename + '&oldFileName=' + oldFilename;
}


//审核
function goAudit(id, status) {
	var msgIndex = top.layer.msg('审核中...', {
		icon : 16,
		shade : [ 0.5, '#999999' ]
	});
	$.ajax({
		type : "POST",
		url : basePath + "/shzz/dtbb/doAuditing.do",
		data : {
			'id' : id,
			'status' : status
		},
		error : function(request) {
			top.layer.close(msgIndex);
			top.layer.msg('审核失败，请联系管理员', {
				shade : [ 0.5, '#999999' ]
			});
		},
		success : function(data) {
			top.layer.close(msgIndex);
			if (data == "success") {
				top.layer.msg('审核通过', {
					icon : 6,
					time : 2000,
					shade : [ 0.5, '#999999' ]
				}, function() {
					var index = parent.layer.getFrameIndex(window.name);
				    parent.layer.close(index);
				    initData(1);
				});
			} else if (data == "succes") {
				top.layer.msg('审核不通过', {
					icon : 6,
					time : 2000,
					shade : [ 0.5, '#999999' ]
				}, function() {
					var index = parent.layer.getFrameIndex(window.name);
				    parent.layer.close(index);
				    initData(1);
				});
			} else {
				top.layer.msg('审核失败，请联系管理员', {
					shade : [ 0.5, '#999999' ]
				});
			}
		}
	});
};