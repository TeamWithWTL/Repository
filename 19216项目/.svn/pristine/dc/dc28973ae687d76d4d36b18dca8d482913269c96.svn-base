/**
 * 曝光台--添加
 */

var	basePath = $("#basePath").val();

//验证
$(document).ready(function(){
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
                        max: 50,
                        message: '名称长度必须在1到50之间'
                    }
                }
            }
        }
    });
});

var ue = UE.getEditor("content", {
	toolbars : [ [ "fullscreen", "source", "undo", "redo", "bold", "italic",
			"underline", "fontborder", "strikethrough", "superscript",
			"subscript", "insertunorderedlist", "selectall",
			"insertorderedlist", "justifyleft", "justifycenter",
			"justifyright", "justifyjustify", "removeformat", "rowspacingtop",
			"rowspacingbottom", "lineheight", "paragraph", "fontfamily",
			"fontsize", "horizontal", "deletetable", "insertrow", "deleterow",
			"insertcol", "deletecol", "splittocells", "splittorows",
			"splittocols", "searchreplace", "simpleupload", "insertvideo" ,"forecolor","indent"] ],
	autoHeightEnabled : false,
	elementPathEnabled : false,
	wordCount : true,
	autoFloatEnabled:false
});

//初始化上传组件
$.SubFormWithAttrs.init(basePath,basePath + '/pub/uploadFile.do','picker','请选择文件','5',null,'attrlist','fName');

/**
 * 保存事件
 */
//$("#saveBtn").click(function(){
function subMyForm(){
	/*var bv = $("#addForm").data('bootstrapValidator');
	if(bv.isValid()){
	    var msgIndex = top.layer.msg('数据保存中', {icon:16, shade:[ 0.5, '#999999' ], time:0});
		$.SubFormWithAttrs.subFrom("doSave()");
	}*/
	if(null ==$("#title").val() || "" == $("#title").val()){
		top.layer.msg('标题不能为空', {
				icon : 5,
				time : 2000,
				shade : [ 0.5, '#999999' ]
			});
		return;
	}
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
}	
//});

function doSave(){
    //var title = $("#title").val();
    var id= $("#id").val();
	var msgIndex = top.layer.msg('数据保存中', {icon:16, shade:[ 0.5, '#999999' ], time:0});
	var contentText =ue.getPlainTxt();
	$("#contentText").val(contentText);
	$.ajax({
      	type: 'POST',
      	url: basePath + '/dflz/bgt/doSave.do?id='+id,
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

/**
 * 删除附件事件
 * @param fjId
 * @param obj
 */
function delFj(fjId, obj){
	var id = $("#id").val();
	top.layer.confirm("确定要删除所选择的附件吗？", {
		icon: 0, 
		title: "提示",
		yes: function(index){
			$.ajax({
				type: 'POST',
				url: basePath + '/dflz/bgt/delFj.do',
				data: {'fjId': fjId, 'id':id},
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


$("#cancleBtn").click(function(){
	var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
});


