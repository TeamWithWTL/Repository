/**
 * 系统管理-操作按钮管理-首页
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

$(function(){
	$.fn.zTree.init($("#menuTree"), treeSetting);
	initData(1);
});

// Table数据加载
function initData(pageNumber){
	var msgIndex = top.layer.msg('数据加载中', {icon:16, shade:[ 0.5, '#999999' ], time:0});
	$("#table-container").load(basePath + "/xtgl/czangl/index.do",{'ajaxCmd': 'table', 'pageNumber': pageNumber, 'metName': $("#metName").val(), 'menuCode': $("#menuCode").val(), cdCode:$("#cdCode").val()}, function(){
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

// 显示添加、编辑操作按钮页面
function showAddEdit(operate){
	var _title = '';
	var metCode = '';
	if(operate == 'add'){
		_title = '添加按钮';
	}else{
		_title = '编辑按钮';
		// 判断是否选择记录
		var checkedObj = $('input[name="icheck"]:checked');
		if(checkedObj.length != 1){
			top.layer.alert('请选择操作对象', {icon:0, title:"提示"});
			return false;
		}else{
			metCode = $(checkedObj[0]).val();
		}
	}
	top.layer.open({
		type : 2,
		title : _title,
		content : basePath + "/xtgl/czangl/showAddEdit.do?metCode=" + metCode,
		area : [ '60%', '70%' ],
		end : function() {
			initData(1);
		}
	});
}

// 删除按钮
function delBtn(){
	// 判断是否选择记录
	var metCode = '';
	var menuCode = '';
	var checkedObj = $('input[name="icheck"]:checked');
	if(checkedObj.length != 1){
		top.layer.alert('请选择操作对象', {icon:0, title:"提示"});
		return false;
	}else{
		metCode = $(checkedObj[0]).val().split(";")[0];
		menuCode = $(checkedObj[0]).val().split(";")[1];
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
				url : basePath + "/xtgl/czangl/doDel.do",
				data : {'metCode':metCode, 'menuCode': menuCode},
				error : function(request) {
					top.layer.close(msgIndex);
	        		top.layer.msg('程序出错，请联系管理员', {shade : [ 0.5, '#999999' ]});
				},
				success : function(data) {
					top.layer.close(msgIndex);
					if(data == "1"){
						top.layer.msg('系统中依然存在使用此按钮的角色，不可删除', {shade : [ 0.5, '#999999' ]});
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