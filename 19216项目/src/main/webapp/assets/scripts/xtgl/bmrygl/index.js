/**
 * 系统管理-部门人员管理
 */

var basePath = $("#basePath").val();

$(function(){
	initData(1);
});

// 数据初始化
function initData(pageNumber){
	var msgIndex = top.layer.msg('数据加载中', {icon:16, shade:[ 0.5, '#999999' ], time:0});
	var name = $("#name").val();
	var deptId = $("#deptId").val();
	var roleCode = $("#roleCode option:selected").val();
	$("#bmry").load(basePath + "/xtgl/bmrygl/index.do",{
		'ajaxCmd': 'table', 
		'pageNumber': pageNumber, 
		'name': name, 
		'deptId': deptId,
		'roleCode':roleCode
		}, function(){
		top.layer.close(msgIndex);
	});
}

// ZTree加载完成后回调
function zTreeOnAsyncSuccess(event, treeId, treeNode, msg){
	var treeObj = window.frames['treeFrame'].window.deptTreeObj;
	// 展开所有节点
	treeObj.expandAll(true);
}

// 节点被点击时触发
function zTreeOnClick(event, treeId, treeNode){
	$("#deptId").val(treeNode.id);
	initData(1);
}

/**
 * 跳转添加页面
 * @param msg
 */
function goAdd(msg){
	$(".btn").blur();
//	var deptId = $("#deptId").val();
//	if(deptId == '' || deptId == null ){
//		top.layer.alert('请先选择一个部门', {title: '提示', icon: 0});
//		return;
//	}
	top.layer.open({
	    type: 2,
	    title: "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>"+msg+"</span>",
	    fix: false,
	   //shadeClose: true,
	    area: ['80%', '80%'],
	    content: basePath + '/xtgl/bmrygl/goAddEdit.do',
	    end:function(){
	    	initData(1);
		  }
	  });
}

/**
 * 跳转修改页面
 * @param msg
 */
function goUpdate(msg){
	var ids = '';	//要修改的id
	//获取被选中的ID
	var chkItems = $('input[name="icheck"]');
	$.each(chkItems, function(index, chkItem){
		if($(chkItem).is(":checked")){
			ids += $(chkItem).val() + ';' ;
		}
	});
	var id = ids.split(";");
	if(ids == ''){
		top.layer.confirm("请选择要修改的部门人员",{
			 icon:0,
			 title:"提示"
		});
		return false;
	}
	if(id.length > 2){
		top.layer.confirm("只能选择一条记录",{
			 icon:0,
			 title:"提示"
		});
		return false;
	}
	
	$(".btn").blur();
	top.layer.open({
	    type: 2,
	    title: "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>"+msg+"</span>",
	    fix: false,
	   // shadeClose: true,
	    area: ['80%', '80%'],
	    content: basePath+'/xtgl/bmrygl/goAddEdit.do?id='+id[0].split(",")[0],
	    end:function(){
	    	initData(1);
		  }
	  });
}

/**
 * 修改--列表
 * @param code
 * @returns
 */
function goUpdateDq(code){
	$(".btn").blur();
	top.layer.open({
	    type: 2,
	    title: "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>修改界面</span>",
	    fix: false,
	   // shadeClose: true,
	    area: ['950px', '460px'],
	    content: basePath+'/xtgl/bmrygl/goAddEdit.do?id='+code,
	    end:function(){
	    	initData(1);
		  }
	  });
}

/**
 * 删除事件
 */
function goDel(){
	var ids = '';	//要删除的id
	// 获取被选中的ID
	var chkItems = $('input[name="icheck"]');
	$.each(chkItems, function(index, chkItem){
		if($(chkItem).is(":checked")){
			ids += $(chkItem).val() + ';' ;
		}
	});
	if(ids == ''){
		top.layer.confirm("请选择要删除的人员",{
			 icon:0,
			 title:"提示"
		});
		return false;
	}
	
	top.layer.confirm("确定要删除选择的人员吗？", {
		icon: 0, 
		title: "提示",
		yes: function(index){
			var msgIndex = top.layer.msg('数据删除中...', {
				icon : 16,
				shade : [ 0.5, '#999999' ]
			});
			$.ajax({
				type : "POST",
				url : basePath + "/xtgl/bmrygl/doDel.do",
				data : {'ids':ids},
				error : function(request) {
					top.layer.close(msgIndex);
					top.layer.msg('数据删除失败，请联系管理员', {shade : [ 0.5, '#999999' ]});
				},
				success : function(data) {
					top.layer.close(msgIndex);
					if (data == "success") {
						top.layer.msg('数据删除成功', {
							icon : 6,
							time : 2000,
							shade : [ 0.5, '#999999' ]
						}, function() {
							initData(1);
						});
					} else {
						top.layer.msg('数据删除失败，请联系管理员', {shade : [ 0.5, '#999999' ]});
					}
				}
			});
		},
		cancel : function(index) {
			layer.close(index);
		}
	});
}

//查看界面
function goView(url){
	$(".btn").blur();
	top.layer.open({
	    type: 2,
	    title: "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>查看界面</span>",
	    fix: false,
	    area: ['950px', '450px'],
	    content: url
	  });
}

//密码重置
function resetKey(){
	var codes = '';	// 要重置的acccode
	var defaultPwd = $("#defaultPwd").val();//系统默认初始密码
	// 获取被选中的用户ID
	var chkItems = $('input[name="icheck"]');
	$.each(chkItems, function(index, chkItem){
		if($(chkItem).is(":checked")){
			codes += $(chkItem).val() + ';' ;
		}
	});
	if(codes == ''){
		top.layer.confirm("请选择操作对象",{
			 icon:0,
			 title:"提示"
		});
		return false;
	}
	top.layer.confirm("确定要将密码重置为："+defaultPwd+"吗？", {
		icon: 0, 
		title: "提示",
		yes: function(index){
			var msgIndex = top.layer.msg('密码重置中...', {
				icon : 16,
				shade : [ 0.5, '#999999' ]
			});
			$.ajax({
				type : "POST",
				url : basePath + "/xtgl/yhgl/resetKey.do",
				data : {'ids':codes},
				error : function(request) {
					top.layer.close(msgIndex);
					top.layer.msg('程序出错，请稍后再试', {shade : [ 0.5, '#999999' ]});
				},
				success : function(data) {
					top.layer.close(msgIndex);
					if (data == "success") {
						top.layer.msg('密码重置成功', {
							icon : 6,
							time : 2000,
							shade : [ 0.5, '#999999' ]
						}, function() {
							initData(1);
						});
					} else {
						top.layer.msg('密码重置失败，请联系管理员', {shade : [ 0.5, '#999999' ]});
					}
				}
			});
		},
		cancel : function(index) {
			layer.close(index);
		}
	});
}

/**
 * 密码重置--列表
 * @param code
 * @returns
 */
function resetKeyDq(code){
	var defaultPwd = $("#defaultPwd").val();//系统默认初始密码
	top.layer.confirm("确定要将密码重置为："+defaultPwd+"吗？", {
		icon: 0, 
		title: "提示",
		yes: function(index){
			var msgIndex = top.layer.msg('密码重置中...', {
				icon : 16,
				shade : [ 0.5, '#999999' ]
			});
			$.ajax({
				type : "POST",
				url : basePath + "/xtgl/yhgl/resetKey.do",
				data : {'ids':code},
				error : function(request) {
					top.layer.close(msgIndex);
					top.layer.msg('程序出错，请稍后再试', {shade : [ 0.5, '#999999' ]});
				},
				success : function(data) {
					top.layer.close(msgIndex);
					if (data == "success") {
						top.layer.msg('密码重置成功', {
							icon : 6,
							time : 2000,
							shade : [ 0.5, '#999999' ]
						}, function() {
							initData(1);
						});
					} else {
						top.layer.msg('密码重置失败，请联系管理员', {shade : [ 0.5, '#999999' ]});
					}
				}
			});
		},
		cancel : function(index) {
			layer.close(index);
		}
	});
}

//设置用户有效无效
function Sz(accCode,validFlag){
	$.ajax({
		type : "POST",
		url : basePath + "/xtgl/yhgl/ValidAcc.do",
		data : {
			'accCode' : accCode,
			'validFlag' : validFlag
		},
		error : function(request) {
			top.layer.msg('程序出错，请稍后再试', {
				shade : [ 0.5, '#999999' ]
			});
		},
		success : function(data) {
			if (data == "success") {
				top.layer.msg('设置成功', {
					icon : 6,
					time : 2000,
					shade : [ 0.5, '#999999' ]
				}, function() {
					initData(1);
				});
			} else {
				top.layer.msg('设置失败，请联系管理员', {
					shade : [ 0.5, '#999999' ]
				});
			}
		}
	});
}