/**
 * 系统管理-系统菜单管理-首页
 */

var basePath = $("#basePath").val();
var treeSetting = {
	view: {
		selectedMulti: false
	},
	async: {
		autoParam: ["id"],
		enable: true,
		url: basePath + '/xtgl/xtcdgl/initMenuTree.do',
		type: 'post'
	},
	callback: {
		onAsyncSuccess: zTreeOnAsyncSuccess,
		onClick: zTreeOnClick
	}
}

$(function(){
	$.fn.zTree.init($("#menuTree"), treeSetting);
	initData(1);
});

// Table数据加载
function initData(pageNumber){
	var msgIndex = top.layer.msg('数据加载中', {icon:16, shade:[ 0.5, '#999999' ], time:0});
	$("#table-container").load(basePath + "/xtgl/xtcdgl/index.do",{'ajaxCmd': 'table', 'pageNumber': pageNumber, 'menuName': $("#menuName").val(), 'menuCode': $("#menuCode").val()}, function(){
		top.layer.close(msgIndex);
		
	});
}

function zTreeOnAsyncSuccess(event, treeId, treeNode, msg){
	//top.getFrameHeight();
}

function zTreeOnClick(event, treeId, treeNode) {
    $("#menuCode").val(treeNode.id);
    initData(1);
};

// 显示添加、编辑菜单页面
function showAddEdit(operate){
	var _title = '';
	var menuCode = '';
	if(operate == 'add'){
		_title = '添加菜单';
	}else{
		_title = '编辑菜单';
		// 判断是否选择记录
		var checkedObj = $('input[name="icheck"]:checked');
		if(checkedObj.length != 1){
			top.layer.alert('请选择操作对象', {icon:0, title:"提示"});
			return false;
		}else{
			menuCode = $(checkedObj[0]).val();
		}
	}
	top.layer.open({
		type : 2,
		title : _title,
		content : basePath + "/xtgl/xtcdgl/showAddEdit.do?menuCode=" + menuCode,
		area : [ '60%', '70%' ],
		end : function() {
			$.fn.zTree.init($("#menuTree"), treeSetting);
			initData(1);
		}
	});
}

// 删除菜单
function delMenu(){
	// 判断是否选择记录
	var menuCode = '';
	var checkedObj = $('input[name="icheck"]:checked');
	if(checkedObj.length != 1){
		top.layer.alert('请选择操作对象', {icon:0, title:"提示"});
		return false;
	}else{
		menuCode = $(checkedObj[0]).val();
	}
	// 执行删除操作
	top.layer.confirm("确定要删除所选菜单及其所有操作按钮吗？", {
		icon: 0, 
		title: "提示",
		yes: function(index){
			var msgIndex = top.layer.msg('数据删除中', {
				icon : 16,
				shade : [ 0.5, '#999999' ]
			});
			$.ajax({
				type : "POST",
				url : basePath + "/xtgl/xtcdgl/doDel.do",
				data : {'menuCode':menuCode},
				error : function(request) {
					top.layer.close(msgIndex);
	        		top.layer.msg('程序出错，请联系管理员', {shade : [ 0.5, '#999999' ]});
				},
				success : function(data) {
					top.layer.close(msgIndex);
					if(data == "1"){
						top.layer.msg('系统中依然存在使用此菜单的角色，不可删除', {shade : [ 0.5, '#999999' ]});
					}else if(data == "2"){
						top.layer.msg('所选菜单下存在依然子菜单，不可删除', {shade : [ 0.5, '#999999' ]});
					}else if (data == "succ") {
						top.layer.msg('数据删除成功', {
							icon : 6,
							time : 2000,
							shade : [ 0.5, '#999999' ]
						}, function() {
							$.fn.zTree.init($("#menuTree"), treeSetting);
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