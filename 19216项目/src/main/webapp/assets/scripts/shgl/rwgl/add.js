var basePath = $("#basePath").val();
var msgIndex1;
$(function(){
	//取消按钮
	$("#cancleBtn").click(function(){
		var index = parent.layer.getFrameIndex(window.name);
	    parent.layer.close(index);
	});
	//初始化日期选择控件
	$("#datetimepicker").datetimepicker({
		  
		format: 'yyyy-mm-dd',  
	    weekStart: 1,  
	    autoclose: true,  
	    startView: 2,  
	    minView: 2,  
	    forceParse: false,  
	    language: 'zh-CN',
	    clearBtn: true,
	    startDate : new Date()
	}).on("click", function() {
		//var startTime = e.date; 
		//$("#datetimepicker").datetimepicker("setEnd_date", startTime)
		$("#datetimepicker").datetimepicker("setEndDate",$("#end_date").val());
	});
	//初始化上传组件
	$.SubFormWithAttrs.init(basePath, basePath + '/pub/uploadFile.do', 'picker', '请选择文件', '5', null, 'attrlist', 'fName');
	
	//校验
//	$('#addForm').bootstrapValidator({
//        feedbackIcons: {
//            valid: 'glyphicon glyphicon-ok',
//            invalid: 'glyphicon glyphicon-remove',
//            validating: 'glyphicon glyphicon-refresh'
//        },
//        submitButtons: '#saveBtn',
//        fields: {
//        	 title: {
//                 validators: {
//                 	notEmpty: {
//                         message: '标题不能为空'
//                     },
//                     stringLength: {
//                         min: 1,
//                         max: 100,
//                         message: '标题长度必须在1到100之间'
//                     }
//                 }
//             },
//             endDate: {
//                 validators: {
//                 	notEmpty: {
//                         message: '完成时间不能为空'
//                     }
//                 }
//             }
//        }
//    });
});

/**
 * 保存
 */
function save(){
	var jsr_ids ="";
	var personli = $("#personlist").find("li");
	var title = $("#title").val();
	var endDate = $("#endDate").val();
	
	if(title == null || title == ''){
		top.layer.confirm("标题不能为空",{ icon:0, title:"提示"});
		return;
	}
	
	if(endDate == null || endDate == ''){
		top.layer.confirm("完成时间不能为空",{ icon:0, title:"提示"});
		return;
	}
	
	if(personli.length <=0){
		top.layer.confirm("请选择接收人",{ icon:0, title:"提示"});
		return;
	}
	
//	var bv = $("#addForm").data('bootstrapValidator');
//	bv.validate();
//	if(!bv.isValid()){
//		return;
//	}
	
	$.each(personli, function(index, person){
        var jsr_id = $(person).attr("code");
        jsr_ids +=jsr_id+";";
	});
	msgIndex1 = top.layer.msg('数据保存中...', {			icon : 16,			shade : [ 0.5, '#999999' ]});
	var filelist = $("#attrlist li").size();
	if(filelist > 0){
		$.SubFormWithAttrs.subFrom("doSave('"+jsr_ids+"')");
	}else{
		doSave(jsr_ids);
	}
	
}

function doSave(jsr_ids){
	$.ajax({
		type : "POST",
		url : basePath+"/shgl/rwgl/addRw.do",
		data : {
				'title': $("#title").val(), 
				'content': $("#content").val(),
				'finish_date':$("#endDate").val(),
				'fName':$("#fName").val(),
				'jsrIds':jsr_ids
				},
		error : function(request) {
			top.layer.close(msgIndex1);
			top.layer.msg('数据保存失败，请联系管理员', {shade : [ 0.5, '#999999' ]});
		},
		success : function(data) {
			top.layer.close(msgIndex1);
			if(data == "success") {
				top.layer.msg('保存成功',{icon : 6,time : 2000,shade : [0.5,'#999999' ]},function(){
					top.layer.close(top.layer.getFrameIndex(window.name));
					});
			}else{
				top.layer.msg('数据保存失败，请联系管理员', {shade : [ 0.5, '#999999' ]});
			}
		}
	});
}

//显示接收人
function showTree(){
	top.layer.open({
	    type: 2,
	    title: "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>选择人员</span>",
	    fix: false,
	    area: ['50%', '80%'],
	    content: basePath + '/shgl/rwgl/showTree.do',
	    end:function(){
		  }
	  });
}

//移除接收人
function deleteMember(accCode){
	$("#"+accCode+"_member").remove();
}
