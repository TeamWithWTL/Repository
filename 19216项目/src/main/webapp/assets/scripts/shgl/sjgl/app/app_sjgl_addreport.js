var basePath = $("#basePath").val();
var msgIndex1="";
$(function(){
	/**
	 * 保存
	 */
	jsCleanFJ();
	document.getElementById('saveBtn').addEventListener('tap',function(){
		if($("#title").val().length==0){
			top.layer.msg('请填写事件标题', {shade : [ 0.5, '#999999' ]});
			return;
		}
		if($("#addr").val().length==0){
			top.layer.msg('请填写事件位置', {shade : [ 0.5, '#999999' ]});
			return;
		}
		if($("#content").val().length==0){
			top.layer.msg('请填写事件详情', {shade : [ 0.5, '#999999' ]});
			return;
		}
		msgIndex1 = top.layer.msg('数据保存中...', {			icon : 16,			shade : [ 0.5, '#999999' ]});
		doSave();

	});

});

//调用手机端方法打开附件
function openFile() {
	toAndroid.jsOpenFile();
}
//调用手机端方法打开附件
function openFileImg() {
	toAndroid.jsOpenImage();
}
//拍照
function openCamera() {
	toAndroid.jsCallCamera();
}

//确定所选文件后执行  根据不同的附件类型显示相应的图片
function getImgs(paths) {
	   var arrs = paths.split(',');      //多个选择附件，用逗号分割
		for (var i = 0; i < arrs.length; i++) {
			var fileType=arrs[i].substring(arrs[i].lastIndexOf('.')+1);
			var fileName=arrs[i].substring(arrs[i].lastIndexOf('/')+1);
			var uuid = getNum();
			fileType = fileType.toLocaleLowerCase();
			var html ='<li id="' + uuid + '"><img  url=\'' + arrs[i] + '\' onClick="imgClick(\'' + arrs[i] + '\')"  src="'+basePath+'/assets/images/default-file.png" class="adaption"/><a href="javascript:;" aid="' + uuid + '" id="' + arrs[i] + '" onClick="tapholdHandler(this)" class="icon ion-android-cancel"></a></li>';
			$("#fjItems").prepend(html);
		}
}
function getNum(){  
    var chars = ['0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'];  
    var nums="";  
    for(var i=0;i<32;i++){  
        var id = parseInt(Math.random()*61);  
        nums+=chars[id];  
    }  
    return nums;  
}  

//查看上传图片
function imgClick(path) {
	toAndroid.jsSeeFile(path);
}

//删除上传附件
function tapholdHandler(obj) {
	top.layer.confirm("确定要删除该文件么？", {
		icon: 0, 
		title: "提示",
		yes: function(index){
			layer.close(index);
			//清除删除的img
			$(obj).parent().remove();
			//删除文件
			toAndroid.jsDeleteFj(obj.id);
			
		},
		cancel : function(index) {
			layer.close(index);
		}
	});
}


	

function doSave(){
	var u = navigator.userAgent;
	var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
	var lon = $("#lon").val();
    var lat = $("#lat").val();
    if(lon==""||lat==""){
    	top.layer.msg('未获取到您的地理位置!请重新上报！', {shade : [ 0.5, '#999999' ]});
		return;
    }
	$.ajax({
		type : "POST",
		url : basePath+"/app/sjgl/saveReport.do",
		data : {
				'title': $("#title").val(), 
				'addr': $("#addr").val(), 
				'content': $("#content").val(),
				'sjlx': $("#sjlx").val(),
				'lon':$("#lon").val(),
				'lat':$("#lat").val(),
				'fName':$("#fName").val(),
				'isAndroid':isAndroid,
				},
		error : function(request) {
			top.layer.close(msgIndex1);
			top.layer.msg('数据保存失败，请联系管理员', {shade : [ 0.5, '#999999' ]});
		},
		success : function(data) {
			var data = $.parseJSON(data);
			if(data.result == "success") {
				
				var str = '{"pid":"' + data.pid + '","table":"EventAttrs"}';
				jsUpDataFJ(str);
				top.layer.close(msgIndex1);
				top.layer.msg('保存成功',{icon : 6,time : 2000,shade : [0.5,'#999999' ]},function(){
					top.layer.close(top.layer.getFrameIndex(window.name));
				  window.location.href = $("#basePath").val() + "/app/sjgl/myReport.do";
//					location.reload(true);   
//					mui.back();
//					webview.reload();
					});
			}else{
				top.layer.msg('数据保存失败，请联系管理员', {shade : [ 0.5, '#999999' ]});
			}
		}
	});
}