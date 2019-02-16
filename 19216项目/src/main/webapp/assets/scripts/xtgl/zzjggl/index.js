/**
 * 系统管理-组织机构管理
 */

var basePath = $("#basePath").val();

$(function(){
	initData(1);
});

// 数据初始化
function initData(pageNumber){
	var msgIndex = top.layer.msg('数据加载中...', {icon:16, shade:[ 0.5, '#999999' ], time:0});
	var orgCode = $("#orgCode").val();
	var orgName = $("#orgName").val();
	$("#zzjg").load(basePath + "/xtgl/zzjggl/index.do",{'ajaxCmd': 'zzjg', 'pageNumber': pageNumber, 'orgCode': orgCode, 'orgName': orgName}, function(){
		top.layer.close(msgIndex);
		$("#pageNumber").val(pageNumber);
	});
}

// 节点被点击时触发
function zTreeOnClick(event, treeId, treeNode){
	$("#orgCode").val(treeNode.id);
	initData(1);
}

// 显示组织架构调整页面
$("#editZzjg").click(function(){
	top.layer.open({
		type : 2,
		title : '组织架构调整',
		content : basePath + "/xtgl/zzjggl/showEditZzjg.do",
		area : [ '30%', '80%' ],
		end : function() {
			$("#treeFrame").attr('src', basePath + '/pub/orgTree/showOrgTree.do');
			initData(1);
		}
	});
});

// 重命名
function rename(){
	var orgCode;	// 组织编码
	// 判断是否选择记录
	var checkedObj = $('input[name="icheck"]:checked');
	if(checkedObj.length == 0){
		top.layer.alert('请选择操作对象', {icon:0, title:"提示"});
		return false;
	}else if(checkedObj.length > 1){
		top.layer.alert('只能选择一个操作对象', {icon:0, title:"提示"});
		return false;
	}else{
		orgCode = $(checkedObj[0]).val();
	}
	top.layer.open({
		type : 2,
		title : "重命名",
		content : basePath + "/xtgl/zzjggl/showRename.do?orgCode=" + orgCode,
		area : [ '40%', '25%' ],
		end : function() {
			initData($("#pageNumber").val());
			$("#treeFrame").attr('src', basePath + '/pub/orgTree/showOrgTree.do');
		}
	});
}

// 删除
function dels(){
	var orgCodes = '';		// 组织编码
	var confirmMsg = '';	// 确认提示信息
	// 判断是否选择记录
	var checkedObj = $('input[name="icheck"]:checked');
	if(checkedObj.length == 0){
		top.layer.alert('请选择操作对象', {icon:0, title:"提示"});
		return false;
	}
	// 组织被选中的组织编码
	$.each(checkedObj, function(index, obj){
		orgCodes += $(obj).val() + ";";
	});
	
	top.layer.confirm('存在下级的机构将不会被删除，确定要删除所选对象吗？', {
	    title: '提示', 
	    icon: 0, 
	    yes: function(index){
	    	var msgIndex = top.layer.msg('数据删除中...', {
				icon : 16,
				shade : [ 0.5, '#999999' ]
			});
			$.ajax({
				type : "POST",
				url : basePath + "/xtgl/zzjggl/doDel.do",
				data : {'orgCodes':orgCodes},
				error : function(request) {
					top.layer.close(msgIndex);
	        		top.layer.msg('程序出错，请联系管理员', {shade : [ 0.5, '#999999' ]});
				},
				success : function(data) {
					top.layer.close(msgIndex);
					if (data == "succ") {
						top.layer.msg('操作成功', {
							icon : 6,
							time : 2000,
							shade : [ 0.5, '#999999' ]
						}, function() {
							initData($("#pageNumber").val());
							$("#treeFrame").attr('src', basePath + '/pub/orgTree/showOrgTree.do');
						});
					}else if(data == "py"){
						top.layer.msg('目前有正在进行中的评议，不能删除', {shade : [ 0.5, '#999999' ]});
					}else if(data == "mzcp"){
						top.layer.msg('目前有正在进行中的民主测评，不能删除', {shade : [ 0.5, '#999999' ]});
					}else{
						top.layer.msg('操作失败，请联系管理员', {shade : [ 0.5, '#999999' ]});
					}
				}
			});
	    },
	    cancel: function(index){
	    	top.layer.close(index);
	    }
	});
}