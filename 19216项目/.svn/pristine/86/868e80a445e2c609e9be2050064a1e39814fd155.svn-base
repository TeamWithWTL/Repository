/**
 * 系统管理--用户管理--添加
 */

var	basePath = $("#basePath").val();

function cityCascade(city, town){
	var cityVal = $("#" + city).val();
	$.ajax({
		type: "POST",
		url:basePath + '/xtgl/yhgl/qxTown.do',
		async: false,
		data:{'city':cityVal},
		error: function(request) {
			$("#" + town).html('<option value="">所在镇(街道)</option><option value="">Error...</option>')
		},
		success: function(data){
			$("#" + town).html(data);
		}
	}); 
}

//验证
$(document).ready(function(){
	$('#addForm').bootstrapValidator({
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        //submitButtons: '#saveBtn',
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
                        message: '名称不能为空'
                    },
                    stringLength: {
                        min: 1,
                        max: 40,
                        message: '名称长度必须在1到40之间'
                    }
                }
            },
            role_code: {
            	validators: {
                	notEmpty: {
                        message: '角色不能为空'
                    }
                }
            },
//            deptName: {
//            	validators: {
//                	notEmpty: {
//                        message: '部门不能为空'
//                    }
//                }
//            },
//            phone: {
//            	validators: {
//            		notEmpty: {
//                        message: '手机号不能为空'
//                    },
//              		regexp: {
//                        regexp: /^\d{0,11}$/,
//                        message: '请输入正确的11位手机号码'
//                    }
//                  }
//            },
//            cardNo: {
//            	validators: {
//                	notEmpty: {
//                        message: '身份证号码不能为空'
//                    },
//                    stringLength: {
//                        min: 15,
//                        max: 18,
//                        message: '请输入15或18位身份证号码'
//                    },
//                    regexp: {
//                        regexp: /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/,
//                        message: '请输入正确的身份证号码'
//                    }
//                }
//            },
            duty: {
            	 validators: {
                     stringLength: {
                         min: 1,
                         max: 100,
                         message: '职务 长度必须在1到100之间'
                     }
                 }
            },
//            office_tel: {
//            	validators: {
//                     stringLength: {
//                         min: 1,
//                         max: 40,
//                         message: '办公室电话长度必须在1到40之间'
//                     },
//                     regexp: {
//                         regexp: /^([0][1-9][0-9]*)$/,
//                         message: '请输入正确的格式 如 0101234567'
//                     }
//                 }
//            },
//           appSerial: {
//        	   validators: {
//                   stringLength: {
//                       min: 1,
//                       max: 60,
//                       message: '手机串长度必须在1到60之间'
//                   }
//               }
//            }
        }
    });
});

//保存
$("#saveBtn").click(function(){
	var bv = $("#addForm").data('bootstrapValidator');
	var phone = $("#phone").val();
	var office_tel = $("#office_tel").val();
	if(phone != ""){
		if(!(/^\d{0,11}$/.test(phone))){
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
	bv.validate();
	if(bv.isValid()){
		var zh_type = "1";
		var bsCode = "";
		var commId = $("#commId option:selected").val();//社区ID
		var ssId = $("#ssId option:selected").val();//服务站ID
		var gridId = $("#gridId option:selected").val();//网格ID
//		var deptId = $("#deptId").val();//部门id
        var msgIndex = top.layer.msg('数据保存中', {icon:16, shade:[ 0.5, '#999999' ], time:0});
        var roles = '';	// 选中的角色id
    	// 获取被选中的用户ID
    	var chkItems = $('input[name="role_code"]');
    	   $.each(chkItems, function(index, chkItem){
    		if($(chkItem).is(":checked")){
    			var jsCode = $(chkItem).val();//角色code
    			if(jsCode == "01"){//网格员
    				if(commId == "" || ssId == "" || gridId == ""){
    					bsCode = "2";
    				}
    			}
    			if(jsCode == "02"){//服务站
    				if(commId == "" || ssId == ""){
    					bsCode = "3";
    				}
    			}
    			if(jsCode == "03"){//社区
    				if(commId == ""){
    					bsCode = "4";
    				}
    			}
    			//角色是部门成员的必须选择部门
//    			if(jsCode == "13"){
//    				if(deptId == ""){
//    					bsCode = "1";
//    				}
//    			}
    			roles += $(chkItem).val() + ',' ;
    		}
    	});
    	if(bsCode == "2"){
    		top.layer.confirm("请选择所属网格！",{
	   			 icon:7,
	   			 title:"提示"
	   		});
	   		return false;
    	}
    	if(bsCode == "3"){
    		top.layer.confirm("请选择所属服务站！",{
	   			 icon:7,
	   			 title:"提示"
	   		});
	   		return false;
    	}
    	if(bsCode == "4"){
    		top.layer.confirm("请选择所属社区！",{
	   			 icon:7,
	   			 title:"提示"
	   		});
	   		return false;
    	}
//    	if(bsCode == "1"){
//    		top.layer.confirm("亲！请选择部门！",{
//	   			 icon:7,
//	   			 title:"提示"
//	   		});
//	   		return false;
//    	}
        $.ajax({
        	type: 'POST',
        	url: basePath + '/xtgl/yhgl/doSave.do?code='+$("#accCode").val()+'&roles='+roles+'&zh_type='+zh_type,
        	data: $("#addForm").serialize(),
        	success: function(data){
        		top.layer.close(msgIndex);
        		if(data=='success'){
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



//选择党组
//function selectParty(url){
//	$(".btn").blur();
//	top.layer.open({
//	    type: 2,
//	    title: "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>选择部门</span>",
//	    fix: false,
//	   // shadeClose: true,
//	    area: ['40%', '80%'],
//	    content: url,
//	    end:function(){
	    	//校验党组（编码）
//	    	$("#addForm").data("bootstrapValidator").updateStatus("deptName",  "NOT_VALIDATED",  null);
//	    	$("#addForm").data("bootstrapValidator").validate('deptName');
//		  }
//	  });
//}

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
		},
		error:function(){
			$("#"+ssId).html(_html);
		}
	});
}