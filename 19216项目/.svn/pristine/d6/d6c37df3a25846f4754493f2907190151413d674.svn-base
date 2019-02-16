/**
 * 提交带附件的Form表单
 */

// 文件上传对象
var sfwo_uploader;

// 默认可上传的文件类型
var sfwo_file_type = ['png', 'jpg', 'jpeg', 'doc', 'docx', 'xls', 'xlsx', 'ppt', 'pptx', 'pdf', 'txt', 'zip', 'rar', 'mp4'];

var sfwo_listId;	// 附件存放容器ID

var sfwo_id;		// 选择按钮ID

$.SubFormWithAttrs = {
	/**
	 * 初始化上传组件
	 * @param basePath 项目根目录
	 * @param updServer Server端上传链接
	 * @param buttonName 按钮显示文字
	 * @param id 选择按钮ID
	 * @param fileNum 可上传的文件数量
	 * @param fileExtArr 可上传的文件的后缀(全部小写)，如果为空或不传则允许上传默认文件：图片文件、office文件、压缩文件、Txt文件、pdf文件
	 * @param listId 附件存放容器ID
	 * @param hiddenId 隐藏域ID，存放服务端返回的文件名（newFileName&oldFileName;......）
	 */
	init: function(basePath, updServer, id, buttonName, fileNum, fileExtArr, listId, hiddenId){
		var _multiple = parseInt(fileNum)>1 ? true : false;
		sfwo_listId = listId;
		sfwo_id = id;
		sfwo_uploader = WebUploader.create({
		    swf: basePath + '/assets/vendor/webuploader/Uploader.swf',
		    server: updServer,
		    pick: {
		    	id: "#" + id,
		    	innerHTML: buttonName,
		    	multiple: _multiple
		    },
		    fileNumLimit: fileNum
		});
		// 文件加入队列前判断
		sfwo_uploader.on( 'beforeFileQueued', function(file){
			var lastname = file.ext.toLowerCase();
			if(file.size == 0){
				top.layer.alert("空文件不可上传", {
					icon : 0,
					title : "提示"
				});
				return false;
			}else if(fileExtArr && $.inArray(lastname, fileExtArr) < 0){
				top.layer.alert("不可上传后缀为" + file.ext + "的文件", {
					icon : 0,
					title : "提示"
				});
				return false;
			}else if(!fileExtArr && $.inArray(lastname, sfwo_file_type) < 0){
				top.layer.alert("不可上传后缀为" + file.ext + "的文件", {
					icon : 0,
					title : "提示"
				});
				return false;
			}
		});
		// 显示文件队列
		sfwo_uploader.on( 'fileQueued', function( file ) {
			var lastname = file.ext.toLowerCase();
			var fileType = '';
			if($.inArray(lastname, ['png', 'jpg', 'jpeg']) >= 0){
				fileType = 'img';
				fileTypeImg = basePath+"/assets/images/icon_xt5nm097d1ymygb9/jpg.svg";
			}else if($.inArray(lastname, ['doc', 'docx']) >= 0){
				fileType = 'word';
				fileTypeImg = basePath+"/assets/images/icon_xt5nm097d1ymygb9/word.svg";
			}else if($.inArray(lastname, ['ppt', 'pptx']) >= 0){
				fileType = 'ppt';
				fileTypeImg = basePath+"/assets/images/icon_xt5nm097d1ymygb9/ppt.svg";
			}else if($.inArray(lastname, ['xls', 'xlsx']) >= 0){
				fileType = 'exl';
				fileTypeImg = basePath+"/assets/images/icon_xt5nm097d1ymygb9/excel.svg";
			}else if($.inArray(lastname, ['pdf']) >= 0){
				fileType = 'pdf';
				fileTypeImg = basePath+"/assets/images/icon_xt5nm097d1ymygb9/pdf.svg";
			}else if($.inArray(lastname, ['txt']) >= 0){
				fileType = 'txt';
				fileTypeImg = basePath+"/assets/images/icon_xt5nm097d1ymygb9/txt.svg";
			}else if($.inArray(lastname, ['mp4']) >= 0){
				fileType = 'video';
				fileTypeImg = basePath+"/assets/images/icon_xt5nm097d1ymygb9/video.svg";
			}else if($.inArray(lastname, ['mp3']) >= 0){
				fileType = 'audio';
			}else if($.inArray(lastname, ['zip', 'rar']) >= 0){
				fileType = 'zip';
				fileTypeImg = basePath+"/assets/images/icon_xt5nm097d1ymygb9/zip.svg";
			}else{
				fileType = 'default'
			}
			var _html ='<li class="col-9"  id="' + file.id + '_li"><a title="'+file.name+'"  id="' + file.id + '_a" href="javascript:void(0)">'+
			'<img src="'+fileTypeImg+'"/>' + file.name + 
			'</a><span class="colsed" id="' + file.id + '_del" onClick="delFile(\'' + file.id + '\')"></span></li>';
			$("#" + listId).append(_html);
		});
		
		// 显示上传进度
		sfwo_uploader.on( 'uploadProgress', function( file, percentage ) {
			// 删除等待上传样式
			if($("#" + file.id + "_a").hasClass("upwait")){
				$("#" + file.id + "_a").removeClass("upwait");
			}
			
			// 添加正在上传样式
			if(!$("#" + file.id + "_a").hasClass("uping")){
				$("#" + file.id + "_a").addClass("uping");
			}
			
		    $("#" + file.id + "_pro").css("width", percentage * 100 + '%');	// 修改上传进度
		});
		
		// 上传成功后处理
		sfwo_uploader.on( 'uploadSuccess', function(file, response) {
			// 删除正在上传样式
			if($("#" + file.id + "_a").hasClass("uping")){
				$("#" + file.id + "_a").removeClass("uping");
			}
			
			// 添加上传成功样式
			if(!$("#" + file.id + "_a").hasClass("updone")){
				$("#" + file.id + "_pro").css("width", "0");
				$("#" + file.id + "_a").addClass("updone");
			}
			
			// 隐藏删除按钮
			$("#" + file.id + "_del").hide();
			
		    // 将服务端返回的文件名保存到隐藏域，表单提交时用到
			var jsonData = JSON.parse(response._raw);
			if(jsonData.code == 200){
				//var tmp = jsonData.newName + "|" + jsonData.oldName + "|" + jsonData.type + ":";
				$('#' + hiddenId).val($('#' + hiddenId).val() + response._raw+">");
			}
		});
	},
	subFrom: function(funcStr){
		// 将所有按钮置为不可用
		$(".btn").attr("disabled", true);
		
		// 将选择文件按钮隐藏
		$("#" + sfwo_id).hide();
		
		// 将所有待上传的文件样式修改为待上传
		var files = sfwo_uploader.getFiles();
		$.each(files, function(index, file){
			$("#" + file.id + "_li a").addClass("upwait");
		});
		
		// 上传
		sfwo_uploader.upload();
		
		// 文件上传完成后执行提交表单的操作
		sfwo_uploader.on('uploadFinished', function(){
			eval(funcStr);
		});
	}
}

// 删除文件
function delFile(fileId){
	sfwo_uploader.removeFile(fileId);
	$('#' + fileId + "_li").remove();
}