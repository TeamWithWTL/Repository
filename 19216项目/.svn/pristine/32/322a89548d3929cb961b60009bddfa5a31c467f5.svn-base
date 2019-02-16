/**
 * 社会服务-政务信息-首页js
 */	

var basePath = $("#basePath").val();

$(function(){
	initData(1);
});

//Table数据加载
function initData(pageNumber){
	var msgIndex = top.layer.msg('数据加载中', {icon:16, shade:[ 0.5, '#999999' ], time:0});
	$("#table-container").load(basePath + "/shfw/zwxx/index.do",{'ajaxCmd': 'table', 'pageNumber': pageNumber, 'title': $("#title").val()}, function(){
		top.layer.close(msgIndex);
	});
}

//跳转添加页面
function goAdd(msg){
	$(".btn").blur();
	top.layer.open({
	    type: 2,
	    title: "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>"+msg+"</span>",
	    fix: false,
	   // shadeClose: true,
	    area: ['80%', '80%'],
	    content: basePath + '/shfw/zwxx/goAddEdit.do',
	    end:function(){
	    	initData(1);
		  }
	  });
}

//跳转修改页面
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
		top.layer.confirm("请选择要修改的记录",{
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
	
	var status = id[0].split(",")[1];
	if(status == '1'){
		top.layer.confirm("已审核通过的数据不能修改",{
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
	    content: basePath+'/shfw/zwxx/goAddEdit.do?id='+id[0].split(",")[0],
	    end:function(){
	    	initData(1);
		  }
	  });
}

//审核事件
function goAuditing(){
	var codes = '';	//要审核的id+status
	var ids = '';	//要审核的id
	// 获取被选中的ID
	var chkItems = $('input[name="icheck"]');
	$.each(chkItems, function(index, chkItem){
		if($(chkItem).is(":checked")){
			codes += $(chkItem).val() + ';' ;
		}
	});
	if(codes == ''){
		top.layer.confirm("请选择要审核的记录",{
			 icon:0,
			 title:"提示"
		});
		return false;
	}
	var code = codes.split(";");
	for(var i=0; i<code.length; i++){
		if(code[i] == ""){
			break;
		}
		ids += code[i].split(",")[0] + ';' ;
	}
	
	top.layer.confirm("确定要审核所选记录吗？", {
		icon: 0, 
		title: "提示",
		yes: function(index){
			var msgIndex = top.layer.msg('数据审核中...', {
				icon : 16,
				shade : [ 0.5, '#999999' ]
			});
			$.ajax({
				type : "POST",
				url : basePath + "/shfw/zwxx/doAuditing.do",
				data : {'ids':ids},
				error : function(request) {
					top.layer.close(msgIndex);
					top.layer.msg('数据审核失败，请联系管理员', {shade : [ 0.5, '#999999' ]});
				},
				success : function(data) {
					top.layer.close(msgIndex);
					if (data == "success") {
						top.layer.msg('数据审核成功', {
							icon : 6,
							time : 2000,
							shade : [ 0.5, '#999999' ]
						}, function() {
							initData(1);
						});
					} else {
						top.layer.msg('数据审核失败，请联系管理员', {shade : [ 0.5, '#999999' ]});
					}
				}
			});
		},
		cancel : function(index) {
			layer.close(index);
		}
	});
}

//跳转查看页面
function goView(id){
	$(".btn").blur();
	top.layer.open({
	    type: 2,
	    title: "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>查看界面</span>",
	    fix: false,
	   // shadeClose: true,
	    area: ['80%', '80%'],
	    content: basePath+'/shfw/zwxx/goView.do?id='+id,
	  });
}

//跳转审核页面
function goSh(id){
	$(".btn").blur();
	top.layer.open({
	    type: 2,
	    title: "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>审核界面</span>",
	    fix: false,
	   // shadeClose: true,
	    area: ['80%', '80%'],
	    content: basePath+'/shfw/zwxx/goSh.do?id='+id,
	    end:function(){
	    	initData(1);
		}
	  });
}


//删除事件
function goDel(){
	var codes = '';	//要删除的id+status
	var ids = '';	//要删除的id
	// 获取被选中的ID
	var chkItems = $('input[name="icheck"]');
	$.each(chkItems, function(index, chkItem){
		if($(chkItem).is(":checked")){
			codes += $(chkItem).val() + ';' ;
		}
	});
	if(codes == ''){
		top.layer.confirm("请选择要删除的记录",{
			 icon:0,
			 title:"提示"
		});
		return false;
	}
	var code = codes.split(";");
	for(var i=0; i<code.length; i++){
		if(code[i] == ""){
			break;
		}
		if(code[i].split(",")[1] == '1'){
			top.layer.confirm("已审核通过的数据不能删除",{
				 icon:0,
				 title:"提示"
			});
			return false;
		}
		ids += code[i].split(",")[0] + ';' ;
	}
	
	top.layer.confirm("确定要删除选择的记录吗？", {
		icon: 0, 
		title: "提示",
		yes: function(index){
			var msgIndex = top.layer.msg('数据删除中...', {
				icon : 16,
				shade : [ 0.5, '#999999' ]
			});
			$.ajax({
				type : "POST",
				url : basePath + "/shfw/zwxx/doDel.do",
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

//审核政务信息
function auditing(id) {
	$.ajax({
		type : "POST",
		url : basePath + "/shfw/zwxx/auditing.do",
		data : {
			'id' : id
		},
		error : function(request) {
			top.layer.msg('程序出错，请稍后再试', {
				shade : [ 0.5, '#999999' ]
			});
		},
		success : function(data) {
			if (data == "success") {
				top.layer.msg('审核成功', {
					icon : 6,
					time : 2000,
					shade : [ 0.5, '#999999' ]
				}, function() {
					initData(1);
				});
			} else {
				top.layer.msg('审核失败，请联系管理员', {
					shade : [ 0.5, '#999999' ]
				});
			}
		}
	});
}

//反审核政务信息
function antiaudit(id) {
	$.ajax({
		type : "POST",
		url : basePath + "/shfw/zwxx/antiaudit.do",
		data : {
			'id' : id
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

//跳转修改页面
function goModify(id){
	$(".btn").blur();
	top.layer.open({
	    type: 2,
	    title: "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>修改界面</span>",
	    fix: false,
	   // shadeClose: true,
	    area: ['80%', '80%'],
	    content: basePath+'/shfw/zwxx/goAddEdit.do?id='+id,
	    end:function(){
	    	initData(1);
		}
	  });
}