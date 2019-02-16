/**
 * 系统管理-部门管理首页js
 */

var basePath = $("#basePath").val();

$(function(){
	initData(1);
});

// 数据初始化
function initData(pageNumber){
	var msgIndex = top.layer.msg('数据加载中...', {icon:16, shade:[ 0.5, '#999999' ], time:0});
	var deptId = $("#deptId").val();
	var deptName = $("#deptName").val();
	$("#dept").load(basePath + "/xtgl/dept/index.do",{'ajaxCmd': 'dept', 'pageNumber': pageNumber, 'deptId': deptId, 'deptName': deptName}, function(){
		top.layer.close(msgIndex);
		$("#pageNumber").val(pageNumber);
	});
}

//节点被点击时触发
function zTreeOnClick(event, treeId, treeNode){
	$("#deptId").val(treeNode.id);
	initData(1);
}

//显示部门结构调整页面
$("#editDept").click(function(){
	top.layer.open({
		type : 2,
		title : '组织架构调整',
		content : basePath + "/xtgl/dept/showEditDept.do",
		area : [ '30%', '80%' ],
		end : function() {
			$("#treeFrame").attr('src', basePath + '/pub/deptTree/showDeptTree.do');
			initData(1);
		}
	});
});

//重命名
function rename(){
	var deptId;	// 组织编码
	// 判断是否选择记录
	var checkedObj = $('input[name="icheck"]:checked');
	if(checkedObj.length == 0){
		top.layer.alert('请选择部门', {icon:0, title:"提示"});
		return false;
	}else if(checkedObj.length > 1){
		top.layer.alert('只能选择一个部门', {icon:0, title:"提示"});
		return false;
	}else{
		deptId = $(checkedObj[0]).val();
	}
	top.layer.open({
		type : 2,
		title : "重命名",
		content : basePath + "/xtgl/dept/showRename.do?deptId=" + deptId,
		area : [ '40%', '25%' ],
		end : function() {
			initData($("#pageNumber").val());
			$("#treeFrame").attr('src', basePath + '/pub/deptTree/showDeptTree.do');
		}
	});
}

// 删除
function dels(){
	var deptIds = '';		//部门编码
	var confirmMsg = '';	//确认提示信息
	// 判断是否选择记录
	var checkedObj = $('input[name="icheck"]:checked');
	if(checkedObj.length == 0){
		top.layer.alert('请选择要删除的部门', {icon:0, title:"提示"});
		return false;
	}
	// 组织被选中的组织编码
	$.each(checkedObj, function(index, obj){
		deptIds += $(obj).val() + ";";
	});
	
	top.layer.confirm('存在下级的部门将不会被删除，确定要删除所选部门吗？', {
	    title: '提示', 
	    icon: 0, 
	    yes: function(index){
	    	var msgIndex = top.layer.msg('数据删除中...', {
				icon : 16,
				shade : [ 0.5, '#999999' ]
			});
			$.ajax({
				type : "POST",
				url : basePath + "/xtgl/dept/doDel.do",
				data : {'deptIds':deptIds},
				error : function(request) {
					top.layer.close(msgIndex);
	        		top.layer.msg('程序出错，请联系管理员', {shade : [ 0.5, '#999999' ]});
				},
				success : function(data) {
					top.layer.close(msgIndex);
					if (data == "succ") {
						top.layer.msg('删除成功', {
							icon : 6,
							time : 2000,
							shade : [ 0.5, '#999999' ]
						}, function() {
							initData($("#pageNumber").val());
							$("#treeFrame").attr('src', basePath + '/pub/deptTree/showDeptTree.do');
						});
					}else{
						top.layer.msg('删除失败，请联系管理员', {shade : [ 0.5, '#999999' ]});
					}
				}
			});
	    },
	    cancel: function(index){
	    	top.layer.close(index);
	    }
	});
}