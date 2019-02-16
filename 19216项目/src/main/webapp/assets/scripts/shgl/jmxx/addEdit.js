/**
 * 居民信息管理--添加
 */

var	basePath = $("#basePath").val();
	// 初始化日期选择控件
$("#birthday").datetimepicker({
    format: "yyyy-mm-dd",
    autoclose: true,
    minView: 'month',
    language: 'zh-CN',
    clearBtn: true
}).on("changeDate",function(ev){
	$("#addForm").data("bootstrapValidator").updateStatus("birthdayFrm",  "NOT_VALIDATED",  null );
	$("#addForm").data("bootstrapValidator").validate('birthdayFrm');
	});
//验证
$(document).ready(function(){
	//初始化户主选择是否显示
	var isHz=$("#housemaster").val();
	if (isHz=='') {
		$("#housemaster").val('0');
	}
	/*if (isHz==1) {
		$("#hm_relation").val('户主');
	}else{
		$("#hm_relation").val('');
	}*/
	
	$('#addForm').bootstrapValidator({
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
       // submitButtons: '#saveBtn',
        fields: {
            name: {
                validators: {
                	notEmpty: {
                        message: '姓名不能为空'
                    }
                }
            },
//            card_no: {
//                validators: {
//                	notEmpty: {
//                        message: '身份证号不能为空'
//                    }
//                }
//            },
       /*     birthdayFrm: {
                validators: {
                	notEmpty: {
                        message: '出生日期不能为空'
                    }
                }
            },*/
            hm_relation: {
                validators: {
                	notEmpty: {
                        message: '与户主关系不能为空'
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
		top.layer.alert("请选择所属区域",{
			 icon:5,
			 title:"提示"
		});
		return false;
	}
	var buildId = $("#buildId").val();
	var unit_no = $("#unit_no").val();
	var room_no = $("#room_no").val();
	if(buildId == '' || buildId == null || unit_no == '' || unit_no == null || room_no == '' || room_no == null){
		top.layer.confirm("请完善房屋信息",{
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
        	url: basePath + '/shgl/jmxx/doSave.do',
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
function showBuilds(sec){
	$("#buildName").val('');
	$("#buildId").val('');
	$("#unit_no").val('');
	$("#room_no").val('');
	var _html = '';
	$.ajax({
		type : "POST",
		url : basePath + "/shgl/jmxx/getData.do",
		data : {'gridId':$(sec).val()},
		success : function(data) {
			var obj = JSON.parse(data);
			if(obj.length>0){
				for(var i=0;i<obj.length;i++){
					if(i==0){
						var _html2 = '';
						for(var x=1;x<=Number(obj[i].unit_cnt);x++){
							_html2+='<option value="'+x+'">'+x+'</option>';
						}
						$("#unit_no").html(_html2);
						var _html3 = '';
						for(var y=1;y<=Number(obj[i].floor_cnt);y++){
							for(var j=1;j<=obj[i].family_cnt;j++){
								if(j<10){
									_html3+='<option value="'+y+'0'+j+'">'+y+'0'+j+'</option>';
								}else{
									_html3+='<option value="'+y+''+j+'">'+y+''+j+'</option>';
								}
							}
						}
						$("#room_no").html(_html3);
					}
					_html+='<option value="'+obj[i].id+'">'+obj[i].name+'</option>';
				}
			}else{
				$("#unit_no").html("");
				$("#room_no").html("");
			}
			$("#buildId").html(_html);
		},
		error:function(){
			$("#buildId").html(_html);
		}
	});
}
function showUnits(sec){
	var _html = '';
	$.ajax({
		type : "POST",
		url : basePath + "/shgl/jmxx/getBuildData.do",
		data : {'gridId':$(sec).val()},
		success : function(data) {
			var obj = JSON.parse(data);
			if(obj){
				var _html2 = '';
				for(var x=1;x<=Number(obj.unit_cnt);x++){
					_html2+='<option value="'+x+'">'+x+'</option>';
				}
				$("#unit_no").html(_html2);
				var _html3 = '';
				for(var y=1;y<=Number(obj.floor_cnt);y++){
					for(var j=1;j<=obj.family_cnt;j++){
						if(j<10){
							_html3+='<option value="'+y+'0'+j+'">'+y+'0'+j+'</option>';
						}else{
							_html3+='<option value="'+y+''+j+'">'+y+''+j+'</option>';
						}
					}
				}
				$("#room_no").html(_html3);
			}
		},
		error:function(){
			$("#unit_no").html(_html);
			$("#room_no").html(_html);
		}
	});
}
$(function(){
	//性别初始化
	var sex = $("#sex").val();
	if(sex != "" && sex == "F"){
		$("#sexDiv a:nth-child(1)").removeClass("selected");
		$("#sexDiv a:nth-child(2)").addClass("selected");
	}
	//房主初始化
	var housemaster = $("#housemaster").val();
	if(housemaster != "" && housemaster == "1"){
		$("#houseDiv a:nth-child(1)").addClass("selected");
		$("#houseDiv a:nth-child(2)").removeClass("selected");
	}
	//人员类型初始化
	var type = $("#type").val();
	$(".ryfl").each(function(){
		if(type.indexOf($(this).data("val")) != -1){
			$(this).find("a:nth-child(1)").addClass("selected");
			$(this).find("a:nth-child(2)").removeClass("selected");
		}
	});
});
function choseHz(sec,val){
	$(sec).addClass("selected");
	$(sec).siblings("a").removeClass("selected");
	$("#housemaster").val(val);
	var isHz=$("#housemaster").val();
	/*if (isHz==1) {
		$("#hm_relation").val('户主');
	}else{
		$("#hm_relation").val('');
	}*/
}
function choseSex(sec,sex){
	$(sec).addClass("selected");
	$(sec).siblings("a").removeClass("selected");
	$("#sex").val(sex);
}
function setType(sec,val){
	var type = $("#type").val();
	$(sec).addClass("selected");
	$(sec).siblings("a").removeClass("selected");//取消同胞a标签的选取效果
	if(type.indexOf(val+',') != -1){
		$("#type").val(type.replace(val+",",""));
	}else{
		if(type == ""){
			$("#type").val(val+",");
		}else{
			$("#type").val(type+val+",");
		}
	}
}
function checkData(sec){
	var ssId = $("#ssId").val();
	if(ssId == '' || ssId == null){
		top.layer.confirm("请选择所属服务站",{
			 icon:0,
			 title:"提示"
		});
	}
}
//楼宇选择
function choose() {
	var commId=$("#commId").val();
	//alert("commId:"+commId);
	var ssId=$("#ssId").val();
	//alert("ssId:"+ssId);
	var gridId=$("#gridId").val();
	//alert("gridId:"+gridId);
	top.layer
			.open({
				type : 2,
				title : "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>楼宇选择</span>",
				fix : false,
				// shadeClose: true,
				area : [ '90%', '90%' ],//+'&ajaxCmd=table'
				content : basePath +'/shgl/jmxx/chooseBuild.do?commId='+commId+'&ssId='+ssId+'&gridId='+gridId,
				end : function() {
					//initData(1);
				}
			});
}
