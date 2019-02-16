/**
 * 会议管理--添加
 */

var basePath = $("#basePath").val();

//初始化日期选择控件
$("#datetimepicker").datetimepicker({
	format : 'yyyy-mm-dd hh:ii',
	weekStart : 1,
	autoclose : true,
	startView : 2,
	minView : 0,
	forceParse : false,
	language : 'zh-CN',
	startDate : new Date(),
	clearBtn: true
}).on("click", function() {
	
	$("#datetimepicker").datetimepicker("setEndDate",$("#end_date").val());
});

// 初始化日期选择控件
$("#datetimepicker2").datetimepicker({
	format : 'yyyy-mm-dd hh:ii',
	weekStart : 1,
	autoclose : true,
	startView : 2,
	minView : 0,
	forceParse : false,
	language : 'zh-CN',
	startDate :new Date() ,
	clearBtn: true

}).on("click",function() {
	
	 $("#datetimepicker2").datetimepicker("setStartDate",$("#start_date").val()); 
});

$(document).on("click","#datetimepicker2",function(){  
    $('#datetimepicker2').datetimepicker('show');  
});  

$(document).on("click","#datetimepicker",function(){  
$('#datetimepicker').datetimepicker('show');  
});  


$(function(){
	querySelected();//查询已选
});
//查询之前被选的人员，并初始化
 function querySelected(){
	 var jsr_ids ="";
		var personli = $("#personlist").find("li");
		
		$.each(personli, function(index, person){
	        var jsr_id = $(person).attr("code");
	        jsr_ids +=jsr_id+",";
		});
		$("#ids").val(jsr_ids);
 }
/**
 * 保存事件 提交状态为2
 */
$("#saveBtn").click(function() {
	
	var startTime = $("#start_date").val();
	var endTime = $("#end_date").val();
	if (startTime != "" && endTime != "") {
		if (new Date(startTime.replace(/\-/g, '\/')) == new Date(
				endTime.replace(/\-/g, '\/'))) { 
			top.layer.msg('开始日期和结束时期不能相等', {
				icon : 5,
				time : 1000,
				shade : [ 0.5, '#999999' ]
			});
			return false;
		}
		;

	} else {
		top.layer.msg('时间不能为空', {
			icon : 5,
			time : 1000,
			shade : [ 0.5, '#999999' ]
		});
	}
	;
	var title=$("#title").val();
	if (null == title || "" == title) {
		top.layer.msg('标题不能为空', {
			icon : 5,
			time : 1000,
			shade : [ 0.5, '#999999' ]
		});
		return false;
	}
	;
	var venue=$("#venue").val();
	if (null == venue || "" == venue) {
		top.layer.msg('地址不能为空', {
			icon : 5,
			time : 1000,
			shade : [ 0.5, '#999999' ]
		});
		return false;
	}
	;
	var content=$("#content").val();
	if (null == content || "" == content) {
		top.layer.msg('请输入会议内容', {
			icon : 5,
			time : 1000,
			shade : [ 0.5, '#999999' ]
		});
		return false;
	}
	;
	var chooseIds=$("#personlistValue").val();
	if (null == chooseIds || "" == chooseIds) {
		top.layer.msg('请选择参会人员', {
			icon : 5,
			time : 1000,
			shade : [ 0.5, '#999999' ]
		});
		return false;
	}
	;
	querySelected();//查询已选
	var msgIndex = top.layer.msg('数据保存中', {
		icon : 16,
		shade : [ 0.5, '#999999' ],
		time : 0
	});
	var id=$("#id").val();
	var title=$("#title").val();
	var venue=$("#venue").val();
	var start_date=$("#start_date").val();
	var end_date=$("#end_date").val();
	var content=$("#content").val();
	var ids=$("#ids").val();
	//debugger;
	$.ajax({
		type : 'POST',
		url : basePath+ "/xtbg/hygl/doSave.do?sub=2",
		data :{
			'id' : id,
			'title' : title,
			'venue' : venue,
			'start_date' : start_date,
			'end_date' : end_date,
			'content' : content,
			'ids' : ids
		},
		error : function(request) {
			//debugger;
			top.layer.close(msgIndex);
			top.layer.msg('程序出错，请联系管理员', {
				shade : [ 0.5, '#999999' ]
			});
		},
		success : function(data) {
			//debugger;
			top.layer.close(msgIndex);
			if (data == 'success') {
				top.layer.msg('数据保存成功', {
					icon : 6,
					time : 2000,
					shade : [ 0.5, '#999999' ]
				}, function() {
					top.layer.close(top.layer.getFrameIndex(window.name));
				})
			} else {
				top.layer.msg('数据保存失败，请联系管理员', {
					shade : [ 0.5, '#999999' ]
				})
			}
		}
	})
})



/**
 * 删除附件事件
 * 
 * @param fjId
 * @param obj
 */
function delFj(staffId, obj) {
	var id = $("#id").val();
	top.layer.confirm("确定要删除所选择的人员吗？", {
		icon : 0,
		title : "提示",
		yes : function(index) {
			$.ajax({
				type : 'POST',
				url : basePath + '/xtbg/hygl/delStaff.do',
				data : {
					'staffId' : staffId,
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

$("#cancleBtn").click(function() {
	var index = parent.layer.getFrameIndex(window.name);
	parent.layer.close(index);
});
// 参会人员列表
/*$("#add")
		.click(
				function() {
					
					top.layer
							.open({
								type : 2,
								title : "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>参会人员列表</span>",
								fix : false,
								area : [ '80%', '80%' ],
								content : basePath + '/xtbg/hygl/tree.do',
								end : function() {
								}
							});
				});*/

/**
 * 删除人员事件
 * 
 * @param staffId
 * @param obj
 */
function delStaff(staffId, obj) {
	var id = $("#id").val();
	$.ajax({
		type : 'POST',
		url : basePath + '/xtbg/hygl/delStaff.do',
		data : {
			'staffId' : staffId,
			'id' : id
		},
		success : function(data) {
			if (data == 'success') {
				$(obj).parent().remove();
			} else if (data == 'succes') {
				
				$(obj).parent().remove();
				querySelected();//查询已选
				var ids2 = $("#ids").val();
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

// 显示接收人
function showTree() {
	querySelected();//查询已选
	var ids = $("#ids").val();
	$("#personlistValue").val(ids);
	var id = $("#id").val();
	var jsrIds = $("#personlistValue").val();
	top.layer
			.open({
				type : 2,
				title : "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>选择人员</span>",
				fix : false,
				area : [ '50%', '80%' ],
				content : basePath + '/xtbg/hygl/showTree.do?jsrIds=' + jsrIds
						+ '&id=' + id,// 1党政办
				end : function() {
				}
			});
}
