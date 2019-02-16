/**
 * 会议管理--添加
 */

var	basePath = $("#basePath").val();

//初始化日期选择控件
$("#datetimepicker").datetimepicker({
	format: 'yyyy-mm-dd',  
    weekStart: 1,  
    autoclose: true,  
    startView: 2,  
    minView: 2,  
    forceParse: false,  
    language: 'zh-CN',
    clearBtn: true
});

//初始化日期选择控件
$("#datetimepicker2").datetimepicker({
	format: 'yyyy-mm-dd',  
    weekStart: 1,  
    autoclose: true,  
    startView: 2,  
    minView: 2,  
    forceParse: false,  
    language: 'zh-CN',
    clearBtn: true
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
            },
            content: {
                validators: {
                	notEmpty: {
                        message: '请输入内容'
                    }
                }
            },
            start_date: {
                validators: {
                	notEmpty: {
                        message: '请输入会议开始时间'
                    }
                }
            },
            end_date: {
                validators: {
                	notEmpty: {
                        message: '请输入会议结束时间'
                    }
                }
            }
        }
    });
});

//初始化上传组件
$.SubFormWithAttrs.init(basePath, basePath + '/pub/uploadFile.do', 'picker',
		'请选择文件', '5', null, 'attrlist', 'fName');


/**
 * 保存事件 
 */
$("#saveBtn").click(function(){
	
    var msgIndex = top.layer.msg('数据保存中', {icon:16, shade:[ 0.5, '#999999' ], time:0});
    var filesize = $("#attrlist li").size();
	
	$.SubFormWithAttrs.subFrom("doSave(1)");
});

/**
 * 发布事件  提交状态为1
 */

function doSave(sub){
	var msgIndex = top.layer.msg('数据保存中', {icon:16, shade:[ 0.5, '#999999' ], time:0});
	$.ajax({
      	type: 'POST',
      	url: basePath + '/xtbg/hygl/doAcc.do?sub='+sub,
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
 * 删除参会人员事件
 * @param fjId
 * @param obj
 */
function delStaff(staffId, obj){
	
	var id = $("#id").val();

	top.layer.confirm("确定要删除所选择的人员吗？", {
		icon: 0, 
		title: "提示",
		yes: function(index){
			$.ajax({
				type: 'POST',
				url: basePath + '/xtbg/hygl/delStaff.do',
				data: {'staffId': staffId, 'id':id},
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
//参会人员列表
$("#add").click(function(){
	top.layer
	.open({
		type : 2,
		title : "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>参会人员列表</span>",
		fix : false,
		// shadeClose: true,
		area : [ '80%', '80%' ],
		content : basePath +'/xtbg/hygl/tree.do',
		end : function() {
			initData(1);
		}
	});
});

/**
 * 删除附件事件
 * 
 * @param fjId
 * @param obj
 */
function delFj(fjId, obj) {
	
	var id = $("#id").val();
	top.layer.confirm("确定要删除所选择的附件吗？", {
		icon : 0,
		title : "提示",
		yes : function(index) {
			$.ajax({
				type : 'POST',
				url : basePath + '/xtbg/hygl/delFj.do',
				data : {
					'fjId' : fjId,
					'id' : id
				},
				success : function(data) {
					if (data == 'success') {
						top.layer.msg('删除成功', {
							icon : 6,
							time : 2000,
							shade : [ 0.5, '#999999' ]
						}, function() {
							$(obj).parent().remove();
						});
					} else {
						top.layer.msg('删除失败，请联系管理员', {
							shade : [ 0.5, '#999999' ]
						});
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					top.layer.msg('程序出错，请联系管理员', {
						shade : [ 0.5, '#999999' ]
					});
				}
			});
		}
	});
}