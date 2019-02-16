/**
 * 系统管理-角色管理-首页
 */

var basePath = $("#basePath").val();

$(function(){
	initData(1);
});

// Table数据加载
function initData(pageNumber){
	var msgIndex = top.layer.msg('数据加载中', {icon:16, shade:[ 0.5, '#999999' ], time:0});
	$("#table-container").load(basePath + "/xtgl/jsgl/index.do",{'ajaxCmd': 'table', 'pageNumber': pageNumber, 'roleName': $("#roleName").val(), 'roleCode': $("#roleCode").val(), 'cdCode':$("#cdCode").val()}, function(){
		top.layer.close(msgIndex);
		
	});
}

// 显示添加、编辑角色页面
function showAddEdit(operate){
	var _title = '';
	var roleCode = '';
	if(operate == 'add'){
		_title = '添加角色';
	}else{
		_title = '编辑角色';
		// 判断是否选择记录
		var checkedObj = $('input[name="icheck"]:checked');
		if(checkedObj.length != 1){
			top.layer.alert('请选择操作对象', {icon:0, title:"提示"});
			return false;
		}else{
			roleCode = $(checkedObj[0]).val();
		}
	}
	top.layer.open({
		type : 2,
		title : _title,
		content : basePath + "/xtgl/jsgl/showAddEdit.do?roleCode=" + roleCode,
		area : [ '60%', '70%' ],
		end : function() {
			initData(1);
		}
	});
}

// 删除角色
function delRole(){
	// 判断是否选择记录
	var roleCode = '';
	var checkedObj = $('input[name="icheck"]:checked');
	if(checkedObj.length != 1){
		top.layer.alert('请选择操作对象', {icon:0, title:"提示"});
		return false;
	}else{
		roleCode = $(checkedObj[0]).val();
	}
	// 执行删除操作
	top.layer.confirm("确定要删除所选记录吗？", {
		icon: 0, 
		title: "提示",
		yes: function(index){
			var msgIndex = top.layer.msg('数据删除中', {
				icon : 16,
				shade : [ 0.5, '#999999' ]
			});
			$.ajax({
				type : "POST",
				url : basePath + "/xtgl/jsgl/doDel.do",
				data : {'roleCode':roleCode},
				error : function(request) {
					top.layer.close(msgIndex);
	        		top.layer.msg('程序出错，请联系管理员', {shade : [ 0.5, '#999999' ]});
				},
				success : function(data) {
					top.layer.close(msgIndex);
					if(data == "99"){
						top.layer.msg('超级管理员角色不允许删除', {shade : [ 0.5, '#999999' ]});
					}else if(data == "1"){
						top.layer.msg('系统中依然存在使用此角色的用户，不可删除', {shade : [ 0.5, '#999999' ]});
					}else if (data == "succ") {
						top.layer.msg('数据删除成功', {
							icon : 6,
							time : 2000,
							shade : [ 0.5, '#999999' ]
						}, function() {
							initData(1);
						});
					}else{
						top.layer.msg('数据删除失败，请联系管理员', {shade : [ 0.5, '#999999' ]});
					}
				}
			});
		},
		cancel : function(index) {
			top.layer.close(index);
		}
	});
}

// 权限清理
function clrRights(){
	// 判断是否选择记录
	var roleCode = '';
	var checkedObj = $('input[name="icheck"]:checked');
	if(checkedObj.length != 1){
		top.layer.alert('请选择操作对象', {icon:0, title:"提示"});
		return false;
	}else{
		roleCode = $(checkedObj[0]).val();
	}
	// 执行清理操作
	top.layer.confirm("确定要清理所选角色的权限吗？", {
		icon: 0, 
		title: "提示",
		yes: function(index){
			var msgIndex = top.layer.msg('权限清理中', {
				icon : 16,
				shade : [ 0.5, '#999999' ]
			});
			$.ajax({
				type : "POST",
				url : basePath + "/xtgl/jsgl/clrRights.do",
				data : {'roleCode':roleCode},
				error : function(request) {
					top.layer.close(msgIndex);
	        		top.layer.msg('程序出错，请联系管理员', {shade : [ 0.5, '#999999' ]});
				},
				success : function(data) {
					top.layer.close(msgIndex);
					if(data == "99"){
						top.layer.msg('超级管理员权限不允许清理', {shade : [ 0.5, '#999999' ]});
					}else if (data == "succ") {
						top.layer.msg('权限清理成功', {
							icon : 6,
							time : 2000,
							shade : [ 0.5, '#999999' ]
						});
					}else{
						top.layer.msg('权限清理失败，请联系管理员', {shade : [ 0.5, '#999999' ]});
					}
				}
			});
		},
		cancel : function(index) {
			top.layer.close(index);
		}
	});
}

// 权限分配
function assignRights(){
	// 判断是否选择记录
	var roleCode = '';
	var checkedObj = $('input[name="icheck"]:checked');
	if(checkedObj.length != 1){
		top.layer.alert('请选择操作对象', {icon:0, title:"提示"});
		return false;
	}else{
		roleCode = $(checkedObj[0]).val();
	}
	top.layer.open({
		type : 2,
		title : '权限分配',
		content : basePath + "/xtgl/jsgl/showAssignRights.do?roleCode=" + roleCode,
		area : [ '80%', '90%' ]
	});
}