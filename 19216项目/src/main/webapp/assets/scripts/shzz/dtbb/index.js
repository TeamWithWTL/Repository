/**
 * 社会组织-中心介绍-首页
 */	

var basePath = $("#basePath").val();



$(function() {
	initData(1);
});

/**
 * 动态播报-分页查询
 */
function initData(pageNumber){

	var msgIndex = top.layer.msg('数据加载中', {icon:16, shade:[ 0.5, '#999999' ], time:0});
	$("#table-container").load(
			basePath + "/shzz/dtbb/index.do",{
			'ajaxCmd': 'table', 
			'pageNumber': pageNumber,
			'title': $("#title").val(),
			'createTimes': $("#apply_time").val()
			},
			function(){
				top.layer.close(msgIndex);
			
});
}
	
/**
 * 添加页面跳转
 */

function goAdd(msg){
	$(".btn").blur();
	top.layer.open({
	    type: 2,
	    title: "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>"+msg+"</span>",
	    fix: false,
	    area: ['80%', '80%'],
	    content: basePath + '/shzz/dtbb/goAddEdit.do',
	    end:function(){
	    	initData(1);
		  }
	  });
}
//跳转查看页面
function goView(url){
	$(".btn").blur();
	top.layer.open({
	    type: 2,
	    title: "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>查看</span>",
	    fix: false,
	   // shadeClose: true,
	    area: ['80%', '80%'],
	    content: url
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
	    content: basePath+'/shzz/dtbb/goAddEdit.do?id='+id[0].split(",")[0],
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
				url : basePath + "/shzz/dtbb/doDel.do",
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
				url : basePath + "/shzz/dtbb/doAuditing.do",
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


//审核
function goAudit(id, status) {
	
	$(".btn").blur();
	top.layer
			.open({
				type : 2,
				title : "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>审核事件</span>",
				fix : false,
				// shadeClose: true,
				area : [ '80%', '80%' ],
				content : basePath + '/shzz/dtbb/goViewAudit.do?id=' + id,
				end : function() {
					initData(1);
				}
			});
};

//审核政务信息
function auditing(id) {
	$.ajax({
		type : "POST",
		url : basePath + "/shzz/dtbb/auditing.do",
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

//反审核政务信息
function antiaudit(id) {
	$.ajax({
		type : "POST",
		url : basePath + "/shzz/dtbb/antiaudit.do",
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
function goModify(url){
	$(".btn").blur();
	top.layer.open({
	    type: 2,
	    title: "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>修改</span>",
	    fix: false,
	   // shadeClose: true,
	    area: ['80%', '80%'],
	    content: url,
	    end:function(){
	    	initData(1);
		}
	  });
}

//列表删除
function goDell(id){
	$.ajax({
		type : "POST",
		url : basePath + "/shzz/dtbb/doDel.do",
		data : {
			'ids' : id
		},
		error : function(request) {
			top.layer.msg('程序出错，请稍后再试', {
				shade : [ 0.5, '#999999' ]
			});
		},
		success : function(data) {
			if (data == "success") {
				top.layer.msg('删除成功', {
					icon : 6,
					time : 2000,
					shade : [ 0.5, '#999999' ]
				}, function() {
					initData(1);
				});
			} else {
				top.layer.msg('删除失败，请联系管理员', {
					shade : [ 0.5, '#999999' ]
				});
		}
			}
	});
}

function update(id) {
	$(".btn").blur();
	top.layer
			.open({
				type : 2,
				title : "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>修改</span>",
				fix : false,
				// shadeClose: true,
				area : [ '80%', '80%' ],
				content : basePath + '/shzz/dtbb/goAddEdit.do?id=' + id,
				end : function() {
					initData(1);
				}
			});
}