//手机按键返回
//function anGoBack(){
//	mui.back();
//	return;
//}

//下载
function downloadFile(path){
	try{
	toAndroid.jsSeeFJ(path);
	}catch(err){
		console.error("pubApp_downloadFile_error!");
	}
}

//获取经纬度
function getLonLat(){
	try{
	return toAndroid.jsLocation(); 
	}catch(err){
		console.error("pubApp_getLonLat_error!");
		return "";
	}
}
function jsCleanFJ(){
	try{
		setTimeout(function(){
			toAndroid.jsCleanFJ();
		}, 100);
	}catch(err){
		console.error("pubApp_jsCleanFJ_error!");
	}
}
function jsUpDataFJ(str){
	try{
		setTimeout(function(){
			toAndroid.jsUpDataFJ(str);
		}, 500);
	}catch(err){
		console.error("pubApp_jsUpDataFJ_error!");
	}
}