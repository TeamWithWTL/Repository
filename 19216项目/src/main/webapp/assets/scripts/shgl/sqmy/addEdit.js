/**
 * 社会管理-社情民意-新增修改js
 */
var basePath = $("#basePath").val();

//var curDate = new Date();
//var preDate = new Date(curDate.getTime() - 24*60*60*1000); //前一天
//var nextDate = new Date(curDate.getTime() + 24*60*60*1000); //后一天

//初始化开始时间日期选择控件
$("#start").datetimepicker({
	format: 'yyyy-mm-dd',  
    weekStart: 1,  
    autoclose: true,  
    startView: 2, 
    minView: 2, 
    forceParse: false,  
    language: 'zh-CN' ,
    clearBtn: true,
    startDate: new Date()
}).on('hide', function(event) {  
    event.preventDefault();  
    event.stopPropagation();  
    var startTime = event.date;  
    $('#end').datetimepicker('setStartDate',startTime); 
    //验证时间，当日期选择框关闭时，执行刷新校验
    $('#addForm').data('bootstrapValidator')  
    .updateStatus('start_date', 'NOT_VALIDATED',null)  
    .validateField('start_date');  
}); 

//初始化结束时间日期选择控件
$("#end").datetimepicker({
	format: 'yyyy-mm-dd',  
    weekStart: 1,  
    autoclose: true,  
    startView: 2,  
    minView: 2,  
    forceParse: false,  
    language: 'zh-CN',
    startDate : new Date(),
    clearBtn: true
}).on('hide', function(event) {  
    event.preventDefault();  
    event.stopPropagation();  
    var endTime = event.date;  
    $("#start").datetimepicker('setEndDate',endTime);
   //验证时间，当日期选择框关闭时，执行刷新校验
    $('#addForm').data('bootstrapValidator')  
    .updateStatus('end_date', 'NOT_VALIDATED',null)  
    .validateField('end_date');
});  

$('#addForm').bootstrapValidator({
    feedbackIcons: {
        valid: 'glyphicon glyphicon-ok',
        invalid: 'glyphicon glyphicon-remove',
        validating: 'glyphicon glyphicon-refresh'
    },
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
        },
        start_date:{
        	validators: {
                notEmpty: {
                    message: '开始时间不能为空'
                }
            }
        },
        end_date:{
        	validators: {
                notEmpty: {
                    message: '结束时间不能为空'
                }
            }
        },
        dc_num: {
            validators: {
                notEmpty: {
                    message: '调查数量不能为空'
                },
                stringLength: {
                    min: 1,
                    max: 11,
                    message: '长度必须在1到11之间'
                },
                regexp: {
                    regexp: /^[0-9]*$/,
                    message: '调查数量只能为数字'
                }
            }
        },
    }
});

//初始化上传组件
$.SubFormWithAttrs.init(basePath, basePath + '/pub/uploadFile.do', 'picker', '请选择文件', '5', null, 'attrlist', 'fName');

/**
 * 保存事件
 */
function subMyForm(status){
	var bv = $("#addForm").data('bootstrapValidator');
	bv.validate();
	if(bv.isValid()){
		$.SubFormWithAttrs.subFrom("save("+status+")");
    }
}

function save(status){
		var title = $("#title").val();
		var content = $("#content").val();
		var dc_num = $("#dc_num").val();
		var start_date = $("#start_date").val();
		var end_date = $("#end_date").val();
		var fName = $("#fName").val();
		var msgIndex = top.layer.msg('数据保存中', {icon:16, shade:[ 0.5, '#999999' ], time:0});
		$.ajax({
	      	type: 'POST',
	      	url: basePath + '/shgl/sqmy/addsqmy.do',
	      	data:{
	      		id:$("#id").val(),
	      		status:status,
	      		title:title,
	      		content:content,
	      		dc_num:dc_num,
	      		start_date:start_date,
	      		end_date:end_date,
	      		fName:fName
	      	},
	      	success: function(data){
	      		top.layer.close(msgIndex);
	      		if(data == 'success1'){
	      			top.layer.msg('数据保存成功', {
	  					icon : 6,
	  					time : 2000,
	  					shade : [ 0.5, '#999999' ]
	  				},function() {
	  					top.layer.close(top.layer.getFrameIndex(window.name));
						});
	      		}else if(data == 'success2'){
	      			top.layer.msg('数据下发成功', {
	  					icon : 6,
	  					time : 2000,
	  					shade : [ 0.5, '#999999' ]
	  				},function() {
	  					top.layer.close(top.layer.getFrameIndex(window.name));
						});
	      		}else if(data == 'noCommId'){
	      			top.layer.msg('非社区管理员无法创建!', {
	  					icon : 7,
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
				url: basePath + '/shgl/sqmy/delFj.do',
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