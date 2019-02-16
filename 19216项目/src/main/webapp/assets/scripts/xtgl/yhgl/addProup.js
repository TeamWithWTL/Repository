
var partyId = "";
var partyNamr = "";
var partyName = "";

//Tree加载成功后回调
function zTreeOnAsyncSuccess(event, treeId, treeNode, msg){
	var treeObj = window.frames['treeFrame'].window.partyProupTreeObj;
	// 展开所有节点
//	treeObj.expandAll(true);
}

// 节点被点击时触发
function zTreeOnClick(event, treeId, treeNode){
	partyId = treeNode.id;
	partyName = treeNode.name;
}

//保存
$("#saveBtnParty").click(function(){
	if (partyId==''&&partyName=='') {
		top.layer.alert("未选择部门",{
			 icon:5,
			 title:"提示"
		});
		return false;
	}
	parent.layer.getChildFrame('body').find("#deptId").val(partyId);
	parent.layer.getChildFrame('body').find("#deptName").val(partyName);
	parent.layer.getChildFrame('body').find("#noCode_13").prop({"checked":"checked","disabled":"disabled"});
	parent.layer.close(parent.layer.getFrameIndex(window.name));
});

//清空--（高帅_2017年12月12日）
$("#emptyBtnParty").click(function(){
	parent.layer.getChildFrame('body').find("#deptId").val("");
	parent.layer.getChildFrame('body').find("#deptName").val("");
	parent.layer.getChildFrame('body').find("#noCode_13").prop({"checked":"","disabled":""});
	parent.layer.close(parent.layer.getFrameIndex(window.name));
});

$("#cancleBtn").click(function(){
	var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
});