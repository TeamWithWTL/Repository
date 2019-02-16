var basePath = $("#basePath").val();
//初始化开关状态
$(function (){
	jsCleanFJ();
	
	var isActive = document.getElementById("isNm").classList.contains("mui-active");  
	//alert(isActive);
	if(isActive){  
		$("#is_nm").val(1);
	}
	// alert($("#is_nm").val()); 
});
//改变开关状态
function isNm(){
	var isActive = document.getElementById("isNm").classList.contains("mui-active");  
	//alert(isActive);
	if(isActive){  
		$("#is_nm").val(1);
	 
	}else{  
		$("#is_nm").val(0);
	}  
	// alert($("#is_nm").val()); 
}

// 加载二级选项
function showData(sec){
	var _html = '<option value="">-- 所属分类--</option>';
	$.ajax({
		type : "POST",
		url : basePath +"/app/dflz/appComp/getData.do",
		data : {'code':$(sec).val()},
		success : function(data) {
			var obj = JSON.parse(data);
			if(obj.length>0){
				for(var i=0;i<obj.length;i++){
					_html+='<option value="'+obj[i].itemCode+'">'+obj[i].itemName+'</option>';
				}
			}
			$("#type").html(_html);
		},
		error:function(){
			$("#type").html(_html);
		}
	});
}

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
	var arrs = paths.split(',');
		for (var i = 0; i < arrs.length; i++) {
			var fileType=arrs[i].substring(arrs[i].lastIndexOf('.')+1);
			var fileName=arrs[i].substring(arrs[i].lastIndexOf('/')+1);
			fileType = fileType.toLocaleLowerCase();
			var html ='<li><img  onClick="imgClick(\'' + arrs[i] + '\')"   src="'+basePath+'/assets/images/default-file.png" class="adaption"/><a href="javascript:;" id="' + arrs[i] + '" onClick="tapholdHandler(this)" class="icon ion-android-cancel"></a></li>';
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

/**
 * 保存
 */
/*$("#saveBtn").click(function(){
	var title=$("#title").val();
	if(null == title || title == ""){
		var msgIndex = top.layer.msg('标题不可为空', {
			icon : 5,
			shade : [ 0.5, '#999999' ],
			time : 2000
		});
		return;
	};
	var content=$("#content").val();
	if(null == content || content == ""){
		var msgIndex = top.layer.msg('内容不可为空', {
			icon : 5,
			shade : [ 0.5, '#999999' ],
			time : 2000
		});
		return;
	};
	var type=$("#type").val();
	if(null == type || type == ""){
		var msgIndex = top.layer.msg('请选择投诉类型', {
			icon : 5,
			shade : [ 0.5, '#999999' ],
			time : 2000
		});
		return;
	};
	
	msgIndex1 = top.layer.msg('数据保存中...', {icon : 16, shade : [ 0.5, '#999999' ]});
	doSave();
});*/
	
/*function doSave(){
	var title=$("#title").val();
	if(null == title || title == ""){
		var msgIndex = top.layer.msg('标题不可为空', {
			icon : 5,
			shade : [ 0.5, '#999999' ],
			time : 2000
		});
		return;
	};
	var content=$("#content").val();
	if(null == content || content == ""){
		var msgIndex = top.layer.msg('内容不可为空', {
			icon : 5,
			shade : [ 0.5, '#999999' ],
			time : 2000
		});
		return;
	};
	var type=$("#type").val();
	if(null == type || type == ""){
		var msgIndex = top.layer.msg('请选择投诉类型', {
			icon : 5,
			shade : [ 0.5, '#999999' ],
			time : 2000
		});
		return;
	};
	
	msgIndex1 = top.layer.msg('数据保存中...', {icon : 16, shade : [ 0.5, '#999999' ]});
	
	$.ajax({
		type : "POST",
		url : basePath+"/app/dflz/appComp/save.do",
		data : {
				'title':$("#title").val(),
				'content':$("#content").val(),
				'jb_type':$("#type").val(),
				'is_nm':$("#is_nm").val(),
				'sjlx':$("#sjlx").val()
				},
		error : function(request) {
			top.layer.close(msgIndex1);
			top.layer.msg('数据保存失败，请联系管理员', {shade : [ 0.5, '#999999' ]});
		},
		success : function(data) {
			var data = $.parseJSON(data);
			if(data.result == "success") {
				var str = '{"pid":"' + data.pid + '","table":"CompAcceEntity"}';
				toAndroid.jsUpDataFJ(str);
				top.layer.close(msgIndex1);
				top.layer.msg('保存成功',{icon : 6,time : 2000,shade : [0.5,'#999999' ]},function(){
					top.layer.close(top.layer.getFrameIndex(window.name));
					mui.back();
					});
			}else{
				top.layer.msg('数据保存失败，请联系管理员', {shade : [ 0.5, '#999999' ]});
			}
			
		}
	});
}*/