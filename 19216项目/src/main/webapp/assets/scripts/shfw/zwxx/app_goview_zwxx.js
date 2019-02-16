var basePath = $("#basePath").val();

//附件下载
function download(newFilename, oldFilename){
	window.location.href = basePath + '/pub/downloadbystream.do?fileName=' + newFilename + '&oldFileName=' + oldFilename;
}

//下载
function downloadFile(path){
	toAndroid.jsSeeFJ(path);
}

mui("#article-container").on('tap','a',function(){
	var path = this.getAttribute("name");
	downloadFile(path);
});