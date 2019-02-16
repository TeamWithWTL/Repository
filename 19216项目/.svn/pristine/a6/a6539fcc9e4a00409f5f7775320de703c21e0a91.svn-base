/**
 * 服务站管理--添加
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
            name: {
                validators: {
                	notEmpty: {
                        message: '楼宇名称不能为空'
                    },
                    stringLength: {
                        min: 1,
                        max: 40,
                        message: '名称长度必须在1到40之间'
                    }
                }
            },
            lon: {
                validators: {
                	notEmpty: {
                        message: '经度不能为空'
                    },
                    regexp: {
                        // regexp: /^[0-9]+$/,
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
                        // regexp: /^[0-9]+$/,
                    	regexp: /^-?((0|[1-8]?[0-9]?)(([.][0-9]{1,20})?)|90(([.][0]{1,20})?))$/,
                        message: '不是有效数字'
                    }
                }
            },
            unit_cnt: {
                validators: {
                	notEmpty: {
                        message: '单元数不能为空'
                    },
                    regexp: {
                        regexp: /^[0-9]+$/,
                        message: '不是有效整数'
                    }
                }
            },
            floor_cnt: {
                validators: {
                	notEmpty: {
                        message: '楼层数不能为空'
                    },
                    regexp: {
                        regexp: /^[0-9]+$/,
                        message: '不是有效数字'
                    }
                }
            },
            family_cnt: {
                validators: {
                	notEmpty: {
                        message: '每层户数不能为空'
                    },
                    regexp: {
                        regexp: /^[0-9]+$/,
                        message: '不是有效数字'
                    }
                }
            }
        }
    });
});

//保存
$("#saveBtn").click(function(){
	var gridId = $("#gridId").val();
	if(gridId == '' || gridId == null){
		top.layer.confirm("请选择所属区域",{
			 icon:0,
			 title:"提示"
		});
		return false;
	}
	var vId = $("#vId").val();
	if(vId == '' || vId == null){
		top.layer.confirm("请选择所属小区",{
			 icon:0,
			 title:"提示"
		});
		return false;
	}
	var bv = $("#addForm").data('bootstrapValidator');
	bv.validate();
	if(bv.isValid()){
        var msgIndex = top.layer.msg('数据保存中', {icon:16, shade:[ 0.5, '#999999' ], time:0});
        $.ajax({
        	type: 'POST',
        	url: basePath + '/shgl/lygl/doSave.do',
        	data: $("#addForm").serialize(),
        	success: function(data){
        		top.layer.close(msgIndex);
        		if(data == 'exists'){
        			top.layer.msg('数据已存在,请到列表中查看', {shade : [ 0.5, '#999999' ]},function() {
    					top.layer.close(top.layer.getFrameIndex(window.name));
					});
        		}else if(data=='success'){
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
});

$("#cancleBtn").click(function(){
	var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
});

function showData(sec,ssId){
	var _html = '<option value="">-- 所属网格--</option>';
	$.ajax({
		type : "POST",
		url : basePath + "/shgl/xqxx/getData.do",
		data : {'ssId':$(sec).val()},
		success : function(data) {
			var obj = JSON.parse(data);
			if(obj.length>0){
				for(var i=0;i<obj.length;i++){
					_html+='<option value="'+obj[i].id+'">'+obj[i].name+'</option>';
				}
			}
			$("#"+ssId).html(_html);
			showXqData(sec,"vId");
		},
		error:function(){
			$("#"+ssId).html(_html);
		}
	});
}

function showXqData(sec,ssId){
	var _html = '<option value="">-- 所属小区--</option>';
	$.ajax({
		type : "POST",
		url : basePath + "/shgl/lygl/getData.do",
		data : {'ssId':$(sec).val()},
		success : function(data) {
			var obj = JSON.parse(data);
			if(obj.length>0){
				for(var i=0;i<obj.length;i++){
					_html+='<option value="'+obj[i].id+'">'+obj[i].name+'</option>';
				}
			}
			$("#"+ssId).html(_html);
		},
		error:function(){
			$("#"+ssId).html(_html);
		}
	});
}
//选择地图范围
function choseLyArea(url){
	var name = $("#name").val();
	if(!name){
		top.layer.msg('请填写楼宇名称', {shade : [ 0.5, '#999999' ]});
		return false;
	}
	var gridId = $("#gridId").val();
	if(!gridId){
		top.layer.msg('请选择所属网格', {shade : [ 0.5, '#999999' ]});
		return false;
	}
	$(".btn").blur();
	top.layer.open({
	    type: 2,
	    title: "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>地图绘制--"+name+"</span>",
	    fix: false,
	   // shadeClose: true,
	    area: ['80%', '80%'],
	    content: basePath + url+'?id='+gridId,
	    end:function(){
	    	var bv = $("#addForm").data('bootstrapValidator');
	    	bv.validate();
	    	$("#addForm").data('bootstrapValidator').validateField('lon');
	    	$("#addForm").data('bootstrapValidator').validateField('lat');
		  }
	  });
}
