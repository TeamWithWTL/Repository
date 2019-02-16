/**
 * 协同办公--热点新闻审核
 */
var basePath = $("#basePath").val();

//附件下载
function download(newFileName, oldFileName){
	window.location.href = basePath + '/pub/downloadbystream.do?fileName=' + newFileName + '&oldFileName=' + oldFileName;
}

//设置热点新闻信息审核
function setSh(id,flag){
	$.ajax({
		type : "POST",
		url : basePath + "/xtbg/rdxw/setSh.do",
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
				top.layer.msg('设置成功,审核通过！', {
					icon : 6,
					time : 2000,
					shade : [ 0.5, '#999999' ]
				}, function() {
					var index = parent.layer.getFrameIndex(window.name);
				    parent.layer.close(index);
				});
			} else if(data == "pass"){
				top.layer.msg('设置成功, 审核未通过!', {
					icon : 6,
					time : 2000,
					shade : [ 0.5, '#999999' ]
				}, function() {
					var index = parent.layer.getFrameIndex(window.name);
				    parent.layer.close(index);
//				    initData(1);
				});
			} else {
				top.layer.msg('设置失败，请联系管理员!', {
					icon : 5,
					time : 2000,
					shade : [ 0.5, '#999999' ]
				});
			}
		}
	});
}