var basePath = $("#basePath").val();

//附件下载
function download(newFilename, oldFilename){
	window.location.href = basePath + '/pub/downloadbystream.do?fileName=' + newFilename + '&oldFileName=' + oldFilename;
}

/**
 * 发布事件  提交状态为1
 */
function issue(id){
	var startDate=$("#startDate").val();//new Date(startDate.replace(/\-/g, "\/"))
	var start=new Date(startDate.replace(/\-/g, "\/"));
	if (start < new Date) {
		top.layer.msg('会议开始时间已经过去，请重新指定会议时间再发布', {
			icon : 5,
			time : 1000,
			shade : [ 0.5, '#999999' ]
		});
		return false;
	}
	;
	
	var msgIndex = top.layer.msg('发布中...', {
		icon : 16,
		shade : [ 0.5, '#999999' ]
	});
	$.ajax({
		type : "POST",
		url : basePath + "/xtbg/hygl/issue.do",
		data : {
			'id' : id
		},
		error : function(request) {
			top.layer.close(msgIndex);
			top.layer.msg('发布失败，请联系管理员', {
				shade : [ 0.5, '#999999' ]
			});
		},
		success : function(data) {
			top.layer.close(msgIndex);
			if (data == "success") {
				top.layer.msg('成功发布', {
					icon : 6,
					time : 2000,
					shade : [ 0.5, '#999999' ]
				}, function() {
					var index = parent.layer.getFrameIndex(window.name);
				    parent.layer.close(index);
					initData(1);
				});
			} else {
				top.layer.msg('设置失败，请联系管理员', {
					shade : [ 0.5, '#999999' ]
				});
			}
		}
	});
	
}