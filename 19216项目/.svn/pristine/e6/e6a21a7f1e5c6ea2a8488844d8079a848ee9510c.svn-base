
var basePath = $("#basePath").val();

$('#addForm').bootstrapValidator({
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        submitButtons: '#saveBtn',
        fields: {
        	title: {
                validators: {
                    notEmpty: {
                        message: '标题不能为空'
                    },
                    stringLength: {
                        min: 1,
                        max: 100,
                        message: '长度必须在1到100之间'
                    }
                }
            },
            summary: {
                validators: {
                    notEmpty: {
                        message: '摘要不能为空'
                    },
                    stringLength: {
                        min: 1,
                        max: 100,
                        message: '长度必须在1到100之间'
                    }
                }
            },
            picPath: {
                validators: {
                    notEmpty: {
                        message: '摘要不能为空'
                    }
                }
            }
        }
    });


//初始化上传组件
var ue = UE.getEditor("content", {
	toolbars : [ [ "fullscreen", "source", "simpleupload", "undo", "redo", "bold", "italic",
			"underline", "fontborder", "strikethrough", "superscript",
			"subscript", "insertunorderedlist", "selectall",
			"insertorderedlist", "justifyleft", "justifycenter",
			"justifyright", "justifyjustify", "removeformat", "rowspacingtop",
			"rowspacingbottom", "lineheight", "paragraph", "fontfamily",
			"fontsize", "horizontal", "deletetable", "insertrow", "deleterow",
			"insertcol", "deletecol", "splittocells", "splittorows",
			"splittocols", "searchreplace","insertvideo","forecolor","indent" ] ],
	autoHeightEnabled : false,
	elementPathEnabled : false,
	wordCount : true,
	autoFloatEnabled:false
});
//初始化上传组件
$.SubFormWithAttrs.init(basePath, basePath + '/pub/uploadFile.do', 'picker', '上传附件', '5', null, 'attrlist', 'fName');


/**
 * 保存事件
 */
function subMyForm(){
	if(null ==$("#title").val() || "" == $("#title").val()){
		top.layer.msg('标题不能为空', {
				icon : 5,
				time : 2000,
				shade : [ 0.5, '#999999' ]
			});
		return;
	}
	if(null ==$("#summary").val() || "" == $("#summary").val()){
		top.layer.msg('摘要不能为空', {
				icon : 5,
				time : 2000,
				shade : [ 0.5, '#999999' ]
			});
		return;
	}
	if(null ==$("#picPath").val() || "" == $("#picPath").val()){
		top.layer.msg('封面图片不能为空', {
				icon : 5,
				time : 2000,
				shade : [ 0.5, '#999999' ]
			});
		return;
	}
	/*var bv = $("#addForm").data('bootstrapValidator');
	bv.validate();
	if(bv.isValid()){*/
    var msgIndex = top.layer.msg('数据保存中', {icon:16, shade:[ 0.5, '#999999' ], time:0});
    var filesize = $("#attrlist li").size();
	if(filesize <= 0 && "" == UE.getEditor('content').getContent()){
		top.layer.msg('内容和附件必填其中一项', {
				icon : 5,
				time : 2000,
				shade : [ 0.5, '#999999' ]
			});
		return;
	}
	$.SubFormWithAttrs.subFrom("doSave()");
	//}
}
function doSave(){
	var msgIndex = top.layer.msg('数据保存中', {icon:16, shade:[ 0.5, '#999999' ], time:0});
	var contentText =ue.getPlainTxt();
	$("#contentText").val(contentText);
	$.ajax({
      	type: 'POST',
      	url: basePath + '/xtbg/rdxw/doSave.do?id='+$("#id").val(),
      	data: $("#addForm").serialize(),
      	success: function(data){
      		top.layer.close(msgIndex);
      		if(data == 'success'){
      			top.layer.msg('数据保存成功', {
  					icon : 6,
  					time : 2000,
  					shade : [ 0.5, '#999999' ]
  				},function() {
  					top.layer.close(top.layer.getFrameIndex(window.name));
					});
      		}else{
      			top.layer.msg('数据保存失败，请联系管理员', {shade : [ 0.5, '#999999' ]});
      		}
      	},
      	error: function(data){
      		top.layer.close(msgIndex);
      		top.layer.msg('程序出错，请联系管理员', {shade : [ 0.5, '#999999' ]});
      	}
      });
}

$("#cancleBtn").click(function(){
	var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
});


/**
 * 删除附件事件
 * @param fjId
 * @param obj
 */
function delFj(fjId, obj){
	top.layer.confirm("确定要删除所选择的附件吗？", {
		icon: 0, 
		title: "提示",
		yes: function(index){
			$.ajax({
				type: 'POST',
				url: basePath + '/xtbg/rdxw/delFj.do',
				data: {'fjId': fjId},
				success: function(data){
					if(data == 'success'){
						top.layer.msg('删除成功', {
							icon : 6,
							time : 2000,
							shade : [ 0.5, '#999999' ]
						},function() {
							$(obj).parent().remove();
						});
					}else{
						top.layer.msg('删除失败，请联系管理员', {shade : [ 0.5, '#999999' ]});
					}
				},
				error: function(XMLHttpRequest, textStatus, errorThrown){
					top.layer.msg('程序出错，请联系管理员', {shade : [ 0.5, '#999999' ]});
				}
			});
		}
	});
}

/*//封面图上传
layui.use('upload', function(){
	var msgIndex;
	layui.upload({
		url: basePath + '/pub/uploadFile.do',
		title:'上传图片',
		type: 'images',
		ext: 'jpg|png|JPG|PNG',
		choose: function(obj){  //上传前选择回调方法
            var flag = true;
            obj.preview(function(index, file, result){
//                console.log(file);            //file表示文件信息，result表示文件src地址
                var img = new Image();
                img.src = result; 
                img.onload = function () { //初始化夹在完成后获取上传图片宽高，判断限制上传图片的大小。
                    if(img.width ==343 && img.height ==240){
                        obj.upload(index, file); //满足条件调用上传方法
                    }else{
                        flag = false;
                        D.msg("您上传的小图大小必须是343*240尺寸！");
                        return false;
                    }
                }
                return flag;
            });
        },
		before: function(input){
			// 文件上传之前回调
			msgIndex = top.layer.msg('图片上传中...', {icon:16, shade:[ 0.5, '#999999' ], time:0});
		},
		success: function(res){
			// 上传成功后的回调
			top.layer.close(msgIndex);
			var code = res.code;
			if(code == '200'){
				$("#picPath").val(res.newName);
				$("#myImg").attr("src", basePath + '/showPic?fileName=' + res.newName);
			}else{
				top.layer.msg('轮播图上传失败，请联系管理员', {shade : [ 0.5, '#999999' ]});
			}
		}
	});
});*/

layui.use('upload',function(){
	var msgIndex;
	 var upload = layui.upload;
	 upload.render({
	     elem: '#fileBtn'
	     ,url: basePath + '/pub/uploadFile.do'
	     ,accept: 'file'
	     ,auto: false
	     ,bindAction: '#uploadBtn'
    	 ,choose: function(obj){  //上传前选择回调方法
             var flag = true;
             obj.preview(function(index, file, result){
                 console.log(file);            //file表示文件信息，result表示文件src地址
                 var img = new Image();
                 img.src = result; 
                 img.onload = function () { //初始化夹在完成后获取上传图片宽高，判断限制上传图片的大小。
                     if(img.width >=110 && img.height >=90){
                         obj.upload(index, file); //满足条件调用上传方法
                     }else{
                         flag = false;
                         top.layer.msg('您上传的小图的长宽必须大于等于110*90尺寸！', {shade : [ 0.5, '#999999' ]});
                         return false;
                     }
                 }
                 return flag;
             });
         }
	     ,before: function(input){
				// 文件上传之前回调
				msgIndex = top.layer.msg('图片上传中...', {icon:16, shade:[ 0.5, '#999999' ], time:0});
			}
	 	 ,done: function(res){
				// 上传成功后的回调
				top.layer.close(msgIndex);
				var code = res.code;
				if(code == '200'){
					$("#picPath").val(res.newName);
					$("#myImg").attr("src", basePath + '/showPic?fileName=' + res.newName);
				}else{
					top.layer.msg('轮播图上传失败，请联系管理员', {shade : [ 0.5, '#999999' ]});
				}
			}
	     });
});