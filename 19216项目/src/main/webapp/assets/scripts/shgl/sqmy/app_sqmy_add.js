var basePath = $("#basePath").val();
var msgIndex1="";
$(function(){
	jsCleanFJ();
	/**
	 * 保存
	 */
	document.getElementById('saveBtn').addEventListener('tap',function(){
		if($("#title").val().length==0){
			top.layer.msg('请填写标题', {shade : [ 0.5, '#999999' ]});
			return;
		}
		if($("#content").val().length==0){
			top.layer.msg('请填写调查详情', {shade : [ 0.5, '#999999' ]});
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
			fileType = fileType.toLocaleLowerCase();
			var html ='<li><img  onClick="imgClick(\'' + arrs[i] + '\')"  src="'+basePath+'/assets/images/default-file.png" class="adaption"/><a href="javascript:;" id="' + arrs[i] + '" onClick="tapholdHandler(this)" class="icon ion-android-cancel"></a></li>';
			$("#fjItems").prepend(html);
		}
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
	$.ajax({
		type : "POST",
		url : basePath+"/app/shgl/sqmy/saveDcInfo.do",
		data : {
				'sqmyWgyId':$("#sqmyWgyId").val(),
				'title': $("#title").val(), 
				'content': $("#content").val(),
				},
		error : function(request) {
			top.layer.close(msgIndex1);
			top.layer.msg('数据保存失败，请联系管理员', {shade : [ 0.5, '#999999' ]});
		},
		success : function(result) {
			var data = $.parseJSON(result);
			if(data.result == "success") {
				var str = '{"pid":"' + data.pid + '","table":"ShglSqmyDcAttrs"}';
				jsUpDataFJ(str);
				top.layer.close(msgIndex1);
				top.layer.msg('保存成功',{icon : 6,time : 2000,shade : [0.5,'#999999' ]},function(){
					top.layer.close(top.layer.getFrameIndex(window.name));
					 var sqmyWgyId =$("#sqmyWgyId").val();
//					 top.layer.msg(sqmyWgyId, {shade : [ 0.5, '#999999' ]});
					  window.location.href =$("#basePath").val()+ '/app/shgl/sqmy/wgyDcList.do?sqmyWgyId='+sqmyWgyId;
					});
			}else{
				top.layer.msg('数据保存失败，请联系管理员', {shade : [ 0.5, '#999999' ]});
			}
		}
	});
}