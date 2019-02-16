var basePath = $("#basePath").val();

//附件下载
function download(newFileName, oldFileName){
	window.location.href = basePath + '/pub/downloadbystream.do?fileName=' + newFileName + '&oldFileName=' + oldFileName;
}