var basePath = $("#basePath").val();
function importFile(){
	var fileName = $("#path").val();
	if (fileName == "") {
		$("#chkImpMess").html("请选择要上传的文件！");
		return false;
	} else {
		var fileExtension = fileName
				.substring(fileName.lastIndexOf('.') + 1);
		if (fileExtension.toLowerCase() != "xlsx") {
			$("#chkImpMess").html("请选择后缀为xlsx的文件！");
			return false;
		}
	}

	var msgIndex = top.layer.msg('数据导入中...', {
		icon : 16,
		shade : [ 0.5, '#999999' ],
		time : 0
	});
	$.ajaxFileUpload({
		url : basePath+ '/shgl/lygl/impFile.do',
		fileElementId : 'path',
		dataType : 'json', //返回值类型 一般设置为json
		success : function(data) {
			if (data.impResult == "succ") {
				parent.layer.msg('导入成功', {
					icon : 6,
					time : 2000,
					shade : [ 0.5, '#999999' ]
				}, function() {
					parent.layer.close(parent.layer
							.getFrameIndex(window.name));
				});
			} else {
				parent.layer.msg('导入失败',{
					icon : 5,
					time : 2000,
					shade : [ 0.5, '#999999' ]
				},
				function() {
					$("#errmsg").html("<a href='"+basePath+"/pub/downloadbystream.do?fileName="+data.impResult+"&oldFileName=errMsg.txt'><font style='color:red;font-size:12px;'>点击下载错误信息</font></a>");
					//$("#upfile").val('点击选择文件');
				});
			}
			layer.close(msgIndex);
		},
		error : function(data) {
			layer.close(msgIndex);
			alert("error");
		}
	});
}