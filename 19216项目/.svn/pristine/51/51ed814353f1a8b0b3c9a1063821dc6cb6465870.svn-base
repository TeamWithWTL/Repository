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
	    endDate : new Date()
	}).on("click", function() {
		//var startTime = e.date; 
		//$("#datetimepicker").datetimepicker("setEnd_date", startTime)
		$("#datetimepicker").datetimepicker("setEndDate",$("#end_date").val());
	});
	//初始化上传组件
	$.SubFormWithAttrs.init(basePath, basePath + '/pub/uploadFile.do', 'picker', '请选择文件', '5', null, 'attrlist', 'fName');
	
	//校验
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
                         message: '事件标题不能为空'
                     },
                     stringLength: {
                         min: 1,
                         max: 100,
                         message: '事件标题长度必须在1到100之间'
                     }
                 }
             },
             addr: {
                 validators: {
                 	notEmpty: {
                         message: '事件位置不能为空'
                     },
                     stringLength: {
                         min: 1,
                         max: 200,
                         message: '事件位置长度必须在1到200之间'
                     }
                 }
             },
             content: {
                 validators: {
                 	notEmpty: {
                         message: '事件详情不能为空'
                     },
                     stringLength: {
                         min: 1,
                         max: 1000,
                         message: '事件详情长度必须在1到1000之间'
                     }
                 }
             },
             lon: {
                 validators: {
                 	notEmpty: {
                         message: '经度不能为空'
                     },
                     regexp: {
                     	 regexp: /^-?((0|1?[0-7]?[0-9]?)(([.][0-9]{1,20})?)|180(([.][0]{1,20})?))$/,
                         message: '不是有效数字'
                     }
                 }
             },
             lat: {
                 validators: {
                 	notEmpty: {
                         message: '纬度不能为空'
                     },
                     regexp: {
                     	 regexp: /^-?((0|[1-8]?[0-9]?)(([.][0-9]{1,20})?)|90(([.][0]{1,20})?))$/,
                         message: '不是有效数字'
                     }
                 }
             },
             sjlx: {
                 validators: {
                 	notEmpty: {
                         message: '事件类型不能为空'
                     }
                 }
             },
             sjly: {
                 validators: {
                 	notEmpty: {
                         message: '事件来源不能为空'
                     }
                 }
             }
        }
    });
});

/**
 * 保存
 */
function save(){
	
	var title = $("#title").val();
	if(!title){
		top.layer.msg('请填写事件标题', {time: 1000, shade : [ 0.5, '#999999' ]});
		return false;
	}
	if(title.length<1 || title.length>100){
		top.layer.msg('事件标题长度必须在1到100之间', {time: 1000, shade : [ 0.5, '#999999' ]});
		return false;
	}
	var addr = $("#addr").val();
	if(!addr){
		top.layer.msg('请填写事件位置', {time: 1000, shade : [ 0.5, '#999999' ]});
		return false;
	}
	if(addr.length<1 || addr.length>200){
		top.layer.msg('事件位置长度必须在1到200之间', {time: 1000, shade : [ 0.5, '#999999' ]});
		return false;
	}
	var content = $("#content").val();
	if(!content){
		top.layer.msg('请填写事件详情', {time: 1000, shade : [ 0.5, '#999999' ]});
		return false;
	}
	if(content.length<1 || content.length>1000){
		top.layer.msg('事件详情长度必须在1到1000之间', {time: 1000, shade : [ 0.5, '#999999' ]});
		return false;
	}
	var lon = $("#lon").val();
	var lat = $("#lat").val();
	if(!lon || !lat || lon=='0' || lat=='0'){
		top.layer.msg('请选择事件经纬度', {time: 1000, shade : [ 0.5, '#999999' ]});
		return false;
	}
	var lonRegexp = /^-?((0|1?[0-7]?[0-9]?)(([.][0-9]{1,20})?)|180(([.][0]{1,20})?))$/;
	var latRegexp = /^-?((0|[1-8]?[0-9]?)(([.][0-9]{1,20})?)|90(([.][0]{1,20})?))$/;
	if(!lon.match(lonRegexp)){
		top.layer.msg('经度格式不正确', {time: 1000, shade : [ 0.5, '#999999' ]});
		return false;
	}
	if(!lat.match(latRegexp)){
		top.layer.msg('纬度格式不正确', {time: 1000, shade : [ 0.5, '#999999' ]});
		return false;
	}
	
	var filelist = $("#attrlist li").size();
	if(filelist > 0){
		$.SubFormWithAttrs.subFrom("doSave()");
		//$(".btn").attr("disabled", false);
		//$("#picker").show();
	}else{
		doSave();
	}
}

function doSave(){
	msgIndex1 = top.layer.msg(
			'数据保存中...', 
			{
				icon : 16, 
				shade : [ 0.5, '#999999' ]
			}
		);
	
	$(".btn").blur();
	/*
	var bv = $("#addForm").data('bootstrapValidator');
	bv.validate();
	if(bv.isValid()){
		
	}
	*/
	$.ajax({
		type : "POST",
		async : false,
		url : basePath+"/shgl/sjgl/savePCReport.do",
		data : {
				'title': $("#title").val(), 
				'content': $("#content").val(),
				'addr': $("#addr").val(),
				'lon': $("#lon").val(),
				'lat': $("#lat").val(),
				'sjlx': $("#sjlx").val(),
				'sjly': $("#sjly").val(),
				'fName': $("#fName").val()
				},
		error : function(request) {
			top.layer.close(msgIndex1);
			top.layer.msg('数据保存失败，请联系管理员', {shade : [ 0.5, '#999999' ]});
		},
		success : function(data) {
			top.layer.close(msgIndex1);
			var jsonData = $.parseJSON(data);
			if(jsonData.result == "success") {
				top.layer.msg('保存成功',{icon : 6,time : 2000,shade : [0.5,'#999999' ]},function(){
					//console.log(top.layer.getFrameIndex(window.name));
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
	    content: basePath + '/shgl/rwgl/showTree.do',//1党政办
	    end:function(){
		  }
	  });
}

//移除接收人
function deleteMember(accCode){
	$("#"+accCode+"_member").remove();
}

//选择地图范围
function choseEventArea(url){
	var title = $("#title").val();
	if(!title){
		top.layer.msg('请填写事件标题', {time: 1000, shade : [ 0.5, '#999999' ]});
		return false;
	}
	var addr = $("#addr").val();
	if(!addr){
		top.layer.msg('请填写事件位置', {time: 1000, shade : [ 0.5, '#999999' ]});
		return false;
	}
	var content = $("#content").val();
	if(!content){
		top.layer.msg('请填写事件详情', {time: 1000, shade : [ 0.5, '#999999' ]});
		return false;
	}
	$(".btn").blur();
	var commId = $("#commId").val();
	top.layer.open({
	    type: 2,
	    title: "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>地图绘制--" + title + "</span>",
	    fix: false,
	    area: ['80%', '80%'],
	    content: basePath + url+'?id='+commId,
	    end:function(){
	    	
		  }
	  });
}