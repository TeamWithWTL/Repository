/**
 * 系统管理-部门人员管理-添加修改 js
 */
var basePath = $("#basePath").val();

// 表单验证
$(document).ready(function() {
    $('#myForm').bootstrapValidator({
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        submitButtons: '#saveBtn',
        fields: {
        	accCode: {
                validators: {
                    notEmpty: {
                        message: '账号不能为空'
                    },
                    stringLength: {
                        min: 1,
                        max: 40,
                        message: '账号长度必须在1到40之间'
                    },
                    remote: {
                        url: basePath + '/xtgl/yhgl/checkCode.do',
                        message: '账号已存在',
                        delay: 3000,
                        type: 'POST'
                    },
                    regexp: {
                        regexp: /^[a-zA-Z0-9_]+$/,
                        message: '账号只能包含大写、小写、数字和下划线'
                    }
                }
            },
        	name: {
                validators: {
                    notEmpty: {
                        message: '姓名不能为空'
                    },
                    stringLength: {
                        min: 1,
                        max: 100,
                        message: '姓名长度必须在1到100之间'
                    }
                }
            },
//            mobile: {
//            	validators: {
//            		notEmpty: {
//                        message: '手机号不能为空'
//                    },
//              		stringLength: {
//                        message: '请输入11位正确手机号码'
//                    },
//              		regexp: {
//                        regexp: /^\d{0,11}$/,
//                        message: '请输入11位正确的手机号码'
//                    }
//                  }
//            },
            orderNo: {
            	validators: {
                	notEmpty: {
                        message: '排序不能为空'
                    }
                }
            },
        }
    });
});

// 保存
$("#saveBtn").click(function(){
	
	var deptId = $("#deptId").val();
	var mobile = $("#mobile").val();
	var office_tel = $("#office_tel").val();
	if(mobile != ""){
		if(!(/^\d{0,11}$/.test(mobile))){
			top.layer.alert("请输入正确11位手机号！",{
				 icon:0,
				 title:"提示"
			});
			return false;
		}
	}
	if(office_tel != ""){
		if(!(/^\d{0,11}$/.test(office_tel))){
			top.layer.alert("请输入正确11位办公电话！",{
				 icon:0,
				 title:"提示"
			});
			return false;
		}
	}
	if(deptId == '' || deptId == null){
		top.layer.alert("请先选择一个部门",{
			 icon:5,
			 title:"提示"
		});
		return false;
	}
	
	var bv = $("#myForm").data('bootstrapValidator');
	bv.validate();
	if(bv.isValid()){
        var msgIndex = top.layer.msg('数据保存中', {icon:16, shade:[ 0.5, '#999999' ], time:0});
        var roles = '';	// 选中的角色id
    	var chkItems = $('input[name="role_code"]');
    	   $.each(chkItems, function(index, chkItem){
    		if($(chkItem).is(":checked")){
    			var jsCode = $(chkItem).val();//角色code
    			roles += $(chkItem).val() + ',' ;
    		}
    	});
    	
        $.ajax({
        	type: 'POST',
        	url: basePath + '/xtgl/bmrygl/doSave.do',
        	data: {
        		'name':$("#name").val(),
        		'sex':$("#sex").val(),
        		'fzr_type':$("#fzr_type").val(),
        		'office_tel':$("#office_tel").val(),
        		'phoneNum':$("#mobile").val(),
        		'roles':roles,
        		'duty':$("#duty").val(),
        		'deptId':deptId,
        		'id':$("#id").val(),
        		'accCode':$("#accCode").val(),
        		'orderNo':$("#orderNo").val()
        	},
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
});

//选择部门
function selectParty(url){
	$(".btn").blur();
	top.layer.open({
	    type: 2,
	    title: "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>选择部门</span>",
	    fix: false,
	   // shadeClose: true,
	    area: ['40%', '80%'],
	    content: url,
	    end:function(){
	    	//校验党组（编码）
//	    	$("#addForm").data("bootstrapValidator").updateStatus("deptName",  "NOT_VALIDATED",  null);
//	    	$("#addForm").data("bootstrapValidator").validate('deptName');
		  }
	  });
}

$("#cancleBtn").click(function(){
	var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
});