/**
 * 社会组织-组织信息-新增修改js
 */

var basePath = $("#basePath").val();
 
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
    endDate: new Date()
});

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
                    message: '组织名称不能为空'
                },
                stringLength: {
                    min: 1,
                    max: 200,
                    message: '长度必须在1到200之间'
                }
            }
        },
        zzjg: {
            validators: {
                notEmpty: {
                    message: '组织机构代码不能为空'
                },
                stringLength: {
                    min: 1,
                    max: 40,
                    message: '长度必须在1到40之间'
                },
               /* regexp: {
                    regexp: /^[a-zA-Z\d]{8}\-[a-zA-Z\d]$/,
                    message: '请输入正确的组织机构代码'
                }*/
            }
        },
        shxydm: {
            validators: {
                stringLength: {
                    min: 1,
                    max: 40,
                    message: '长度必须在1到40之间'
                },
                /*regexp: {
                    regexp: /[A-Z0-9]{18}/g,
                    message: '请输入正确的组织机构代码'
                }*/
            }
        },
        phone: {
            validators: {
                notEmpty: {
                    message: '组织联系方式不能为空'
                },
                stringLength: {
                    min: 1,
                    max: 40,
                    message: '长度必须在1到40之间'
                }
            }
        }
    }
});

//初始化上传组件
$.SubFormWithAttrs.init(basePath, basePath + '/pub/uploadFile.do', 'picker', '请选择文件', '5', null, 'attrlist', 'fName');

/**
 * 保存事件
 */
$("#saveBtn").click(function(){
	var bv = $("#addForm").data('bootstrapValidator');
	bv.validate();
	if(bv.isValid()){
		$.SubFormWithAttrs.subFrom("save()");
    }
});

function save(){
	var msgIndex = top.layer.msg('数据保存中', {icon:16, shade:[ 0.5, '#999999' ], time:0});
	$.ajax({
      	type: 'POST',
      	url: basePath + '/shzz/zzxx/addzzxx.do?id='+$("#id").val(),
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
	top.layer.confirm("确定要删除所选择的附件吗？", {
		icon: 0, 
		title: "提示",
		yes: function(index){
			$.ajax({
				type: 'POST',
				url: basePath + '/shzz/zzxx/delFj.do',
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

//取消按钮事件
$("#cancleBtn").click(function(){
	var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
});

function showSs(sec,ssId){
	var _html = '<option value="">-- 所属服务站--</option>';
	$.ajax({
		type : "POST",
		url : basePath + "/shgl/wggl/getComData.do",
		data : {'comId':$(sec).val()},
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

function showData(sec,gridId){
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
			$("#"+gridId).html(_html);
		},
		error:function(){
			$("#"+gridId).html(_html);
		}
	});
}