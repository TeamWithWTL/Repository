/**
 * 系统管理-组织架构管理-组织架构调整
 */

var basePath = $("#basePath").val();

// Tree加载成功后回调
function zTreeOnAsyncSuccess(event, treeId, treeNode, msg){
	// 绑定添加节点事件
	$("#addNode").bind("click", addNode);
	// 绑定重命名节点事件
	$("#renameNode").bind("click", renameNode);
	// 绑定删除节点事件
	$("#removeNode").bind("click", removeNode);
}

// 移动节点前处理
function zTreeBeforeDrag(treeId, treeNodes){
	return true;
}

// 拖拽释放之后结束前执行
function zTreeBeforeDrop(treeId, treeNodes, targetNode, moveType){  
	var oldPid = treeNodes[0].pId;
	var targetPid = targetNode.pId;
	if(oldPid!=targetPid){
		parent.layer.confirm("只能在同目录下移动位置", {
			icon: 0, 
			title: "提示"
		});
		return false;
	}
}

// 新增节点
function addNode(){
	var treeObj = window.frames['treeFrame'].window.orgTreeObj;	// 获取zTree对象
	var selectedNodes = treeObj.getSelectedNodes();	// 获取被选中的节点
	if(!selectedNodes || selectedNodes.length <= 0){
		top.layer.alert('请先选择一个组织结构', {title: '提示', icon: 0});
		return false;
	}
	var _nodeName = "新建组织结构";
	var treeNode = selectedNodes[0];	// 被选中的节点
	treeNode = treeObj.addNodes(treeNode, {id:createNodeId(), pId:treeNode.id, name:_nodeName});
	treeObj.editName(treeNode[0]);
}

// 重命名节点
function renameNode(){
	var treeObj = window.frames['treeFrame'].window.orgTreeObj;	// 获取zTree对象
	var nodes = treeObj.getSelectedNodes();
	if(!nodes || nodes.length <= 0){
		top.layer.alert('请先选择一个组织结构', {title: '提示', icon: 0});
		return false;
	}
	treeNode = nodes[0];
	treeObj.editName(treeNode);
};

// 删除节点
function removeNode(e){
	var treeObj = window.frames['treeFrame'].window.orgTreeObj;	// 获取zTree对象
	var nodes = treeObj.getSelectedNodes();
	if(!nodes || nodes.length <= 0){
		top.layer.alert('请先选择一个组织结构', {title: '提示', icon: 0});
		return false;
	}
	
	// 存在下级组织机构不可删除
	if(nodes[0].isParent){
		top.layer.alert('该机构存在下级，不可删除', {title: '提示', icon: 0});
		return false;
	}
	
	top.layer.confirm("确定要删除所选组织结构吗？", {
		icon: 0, 
		title: "提示",
		yes: function(index){
			$.ajax({
				type: 'POST',
				url: basePath + '/xtgl/zzjggl/doDel.do',
				data: {'orgCodes': nodes[0].id + ";"},
				success: function(data){
					if(data == 'succ'){
						top.layer.msg('操作成功', {
							icon : 6,
							time : 2000,
							shade : [ 0.5, '#999999' ]
						},function() {
							// 删除所选节点
							treeObj.removeNode(nodes[0]);
							top.layer.close(index);
						});
					}else if(data == "py"){
						top.layer.msg('目前有正在进行中的评议，不能删除', {shade : [ 0.5, '#999999' ]}, function(){
							top.layer.close(index);
						});
					}else if(data == "mzcp"){
						top.layer.msg('目前有正在进行中的民主测评，不能删除', {shade : [ 0.5, '#999999' ]}, function(){
							top.layer.close(index);
						});
					}else{
						top.layer.msg('操作失败，请联系管理员', {shade : [ 0.5, '#999999' ]}, function(){
							top.layer.close(index);
						});
					}
				},
				error: function(){
					top.layer.msg('程序出错，请联系管理员', {shade : [ 0.5, '#999999' ]},function(){
						top.layer.close(index);
					});
				}
			});
		}
	});
	
};

// 保存修改
$("#saveTree").click(function(){
	var treeObj = window.frames['treeFrame'].window.orgTreeObj;	// 获取zTree对象
	var nodes = treeObj.getNodes();
	var allNodes = treeObj.transformToArray(nodes);
	var str = "";
	$.each(allNodes, function(index, node){
		var id = node.id;
		var pid = node.pId
		var name = node.name;
		str += id + ',' + pid + ',' + name + ';';
	});
	var msgIndex = top.layer.msg('数据保存中...', {icon:16, shade:[ 0.5, '#999999' ], time:0});
	$.ajax({
		type: 'POST',
		url: basePath + '/xtgl/zzjggl/saveOrg.do',
		data: {'orgStr': str},
		success: function(data){
			top.layer.close(msgIndex);
			if(data == 'succ'){
				top.layer.msg('操作成功', {
					icon : 6,
					time : 2000,
					shade : [ 0.5, '#999999' ]
				}, function() {
					top.layer.close(top.layer.getFrameIndex(window.name));
				});
			}else{
				top.layer.msg('操作失败，请联系管理员', {shade : [ 0.5, '#999999' ]});
			}
		},
		error: function(){
			top.layer.msg('程序出错，请联系管理员', {shade : [ 0.5, '#999999' ]});
		}
	});
});

/** 随机生成新增节点的ID */
function createNodeId(){
	var oDate = new Date();	// 实例一个时间对象
	return oDate.getFullYear() + "" + (oDate.getMonth()+1) + "" + oDate.getDate() + "" + oDate.getHours() + "" + oDate.getMinutes() + "" + oDate.getSeconds() + "" + createUUID(18, 16);
}