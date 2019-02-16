/**
 * 社会组织-组织活动-新增修改js
 */

var basePath = $("#basePath").val();

//var curDate = new Date();
//var preDate = new Date(curDate.getTime() - 24*60*60*1000); //前一天
//var nextDate = new Date(curDate.getTime() + 24*60*60*1000); //后一天

//初始化日期选择控件
$("#datetimepicker").datetimepicker({
	format : 'yyyy-mm-dd',
	weekStart : 1,
	autoclose : true,
	startView : 2,
	minView: 'month',
	forceParse : false,
	language : 'zh-CN',
	startDate : new Date(),
	clearBtn: true
});

//初始化日期选择控件
$("#datetimepicker2").datetimepicker({
	format : 'yyyy-mm-dd',
	weekStart : 1,
	autoclose : true,
	startView : 2,
	minView: 'month',
	forceParse : false,
	language : 'zh-CN',
	startDate : new Date(),
	clearBtn: true
});

//显示社会组织
function showTree(){
	top.layer.open({
	    type: 2,
	    title: "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>选择组织</span>",
	    fix: false,
	    area: ['50%', '80%'],
	    content: basePath + '/shzz/hdgl/showTree.do',
	    end:function(){
		  }
	  });
}

//移除社会组织
function deleteZz(zzId){
	$("#"+zzId+"_num").remove();
}

//var msgIndex1 = top.layer.msg('数据加载中..', {icon:16, shade:[ 0.5, '#999999' ], time:0});

/*var zzTreeObj; // 全局Tree对象，方便父页面获取

//ZTree设置
var treeSetting = {
	check : {
		enable : true,
		chkStyle : "checkbox",
	},
	view : {
		selectedMulti : true    // 不允许同时选中多个节点
	},
	async : {
		autoParam : [ "id" ],
		enable : true,
		type : "post",
		url : basePath + "/shzz/hdgl/loadTreeData.do",
		otherParam : {
			"id"  : $("#id").val()
		}
	},
	data : {
		simpleData : {
			enable : true,
			idKey : "id",
		}
	},
	callback : {
		beforeClick : zTreeBeforeClick,
		onClick : zTreeOnClick,
		onAsyncSuccess : zTreeOnAsyncSuccess,
		onCheck: zTreeOnCheck
	}
};

//节点被点击前校验：默认所有节点都可以被点击
function zTreeBeforeClick(treeId, treeNode, clickFlag) {
	if (parent.zTreeBeforeClick
			&& typeof (parent.zTreeBeforeClick) == "function") {
		parent.zTreeBeforeClick(treeId, treeNode, clickFlag);
	} else {
		return true;
	}
}

//ZTree加载成功后触发
function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
	var treeObj = $.fn.zTree.getZTreeObj(treeId);		
	zzTreeObj = treeObj;
	if(parent.zTreeOnAsyncSuccess && typeof(parent.zTreeOnAsyncSuccess)=="function"){ 
		parent.zTreeOnAsyncSuccess(event, treeId, treeNode, msg); 
	}
	//treeObj.expandAll(true);//展开全部
	top.layer.close(msgIndex1);
}
//选中checkbox事件
function zTreeOnCheck(event, treeId, treeNode) {
	var codes='';
	var showhtml='';
	var nodes = zzTreeObj.getCheckedNodes(true);
	 $("#names").html("");
	 $("#codes").val("");
	 if(nodes.length == 0){
			return;
     }
     for(var i=0;i<nodes.length;i++){
		var name =nodes[i].name;
		var code1 = nodes[i].code;// 党员编号
		if(code1==""){
			continue;
		}
		var element = "<span id='"+code1+"_member'>"+name+"<a href='javascript:void(0)' onclick=deleteMember('"+code1+"')><i class='fa fa-minus-circle'></i></a></span>";
		codes +=code1+",";
		showhtml +=element;
     }
     $("#names").html(showhtml);
     $("#codes").val(codes);
}
//移除接收人
function deleteMember(code1){
	$("#"+code1+"_member").remove();
	var node = zzTreeObj.getNodeByParam("code", code1, null);
	zzTreeObj.checkNode(node, false, false);
	zTreeOnCheck();
}

//节点被点击时触发
function zTreeOnClick(event, treeId, treeNode) {
	// alert(" 节点id是：" + treeNode.id + ", 节点文本是：" + treeNode.name);
	if (treeNode.isParent) {// 判断是否为父节点
		var zTree = $.fn.zTree.getZTreeObj("zzTree");
		zTree.expandNode(treeNode);// 单击展开/折叠节点
	}
}

$(document).ready(
	function() {
		// 加载ZTree
		$.fn.zTree.init($("#zzTree"), treeSetting)
});*/
	 
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
                    max: 200,
                    message: '长度必须在1到200之间'
                }
            }
        }
    }
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
$.SubFormWithAttrs.init(basePath, basePath + '/pub/uploadFile.do', 'picker', '请选择文件', '5', null, 'attrlist', 'fName');

/**
 * 保存事件
 */
function subMyForm(){
	if(null == $("#title").val() || "" == $("#title").val()){
		top.layer.msg('标题不能为空', {
				icon : 5,
				time : 2000,
				shade : [ 0.5, '#999999' ]
			});
		return;
	}
	if(null == $("#startDateFmt").val() || "" == $("#startDateFmt").val()){
		top.layer.msg('请选择开始时间', {
				icon : 5,
				time : 2000,
				shade : [ 0.5, '#999999' ]
			});
		return;
	}
	
	if(null == $("#endDateFmt").val() || "" == $("#endDateFmt").val()){
		top.layer.msg('请选择结束时间', {
				icon : 5,
				time : 2000,
				shade : [ 0.5, '#999999' ]
			});
		return;
	}
	
    var msgIndex = top.layer.msg('数据保存中', {icon:16, shade:[ 0.5, '#999999' ], time:0});
    
    var zz_ids ="";
	var zzli = $("#zzlist").find("li");
	if(zzli.length <=0){
		//top.layer.confirm("请选择社会组织",{ icon:0, title:"提示"});
		top.layer.msg('请选择社会组织', {
			icon : 5,
			time : 2000,
			shade : [ 0.5, '#999999' ]
		});
		return;
	}
	$.each(zzli, function(index, zz){
        var zz_id = $(zz).attr("code");
        zz_ids +=zz_id+";";
	});
    var filesize = $("#attrlist li").size();
	if(filesize <= 0 && "" == UE.getEditor('content').getContent()){
		top.layer.msg('内容和附件必填其中一项', {
			icon : 5,
			time : 2000,
			shade : [ 0.5, '#999999' ]
		});
		return;
	}
	$.SubFormWithAttrs.subFrom("doSave('"+zz_ids+"')");
}

function doSave(zz_ids){
	var msgIndex = top.layer.msg('数据保存中', {icon:16, shade:[ 0.5, '#999999' ], time:0});
	var contentText =ue.getPlainTxt();
	$("#contentText").val(contentText);
	$.ajax({
      	type: 'POST',
      	url: basePath + '/shzz/hdgl/doSave.do?id='+$("#id").val()+'&zzIds='+zz_ids,
      	data: $("#addForm").serialize(),
      	success: function(data){
      		top.layer.close(msgIndex);
      		if(data == 'success'){
      			top.layer.msg('活动保存成功', {
  					icon : 6,
  					time : 2000,
  					shade : [ 0.5, '#999999' ]
  				},function() {
  					top.layer.close(top.layer.getFrameIndex(window.name));
					});
      		}else{
      			top.layer.msg('活动保存失败，请联系管理员', {shade : [ 0.5, '#999999' ]});
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
	top.layer.confirm("确定要删除所选择的附件吗？", {
		icon: 0, 
		title: "提示",
		yes: function(index){
			$.ajax({
				type: 'POST',
				url: basePath + '/shzz/hdgl/delFj.do',
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

//级联二级社区服务
function showEjFw(sec,type_two){
	var _html = '<option value="">二级社会活动</option>';
	$.ajax({
		type : "POST",
		url : basePath + "/xtgl/sjzdgl/getParamData1.do",
		data : {'code':$(sec).val()},
		success : function(data) {
			var obj = JSON.parse(data);
			if(obj.length>0){
				for(var i=0;i<obj.length;i++){
					_html+='<option value="'+obj[i].itemCode+'">'+obj[i].itemName+'</option>';
				}
			}
			$("#"+type_two).html(_html);
		},
		error:function(){
			$("#"+type_two).html(_html);
		}
	});
}

//取消按钮事件
$("#cancleBtn").click(function(){
	var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
});