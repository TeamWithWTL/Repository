/**
 * 社会组织-通知公告-首页
 */	

var basePath = $("#basePath").val();



$(function() {
	initData(1);
});

/**
 * 通知公告-分页查询
 */
function initData(pageNumber) {
	var msgIndex = top.layer.msg('数据加载中', {
		icon : 16,
		shade : [ 0.5, '#999999' ],
		time : 0
	});
	$("#table-container").load(basePath + "/xtbg/tzgg/index.do", {
		'ajaxCmd' : 'table',
		'pageNumber' : pageNumber,
		'title' : $("#title").val(),
		'applyTime' : $("#apply_time").val()
	}, function() {
		top.layer.close(msgIndex);

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
	    content: basePath + '/xtbg/tzgg/goAddEdit.do',
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
	var status = id[0].split(",")[0];
	if(status == '0'){
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
	    content: basePath+'/xtbg/tzgg/goAddEdit.do?id='+id[0].split(",")[0],
	    end:function(){
	    	initData(1);
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
				content : basePath + '/xtbg/tzgg/goAddEdit.do?id=' + id,
				end : function() {
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
				url : basePath + "/xtbg/tzgg/doDel.do",
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


/**
 * 转发
 * @param msg
 */
function forward(msg){
	var ids = '';	// 要转发的id
	// 获取被选中的ID
	var chkItems = $('input[name="icheck"]');
	$.each(chkItems, function(index, chkItem){
		if($(chkItem).is(":checked")){
			ids += $(chkItem).val() + ';' ;
		}
	});
	var id = ids.split(";");
	if(id == ''){
		top.layer.confirm("请选择要转发的记录",{
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
	top.layer
	.open({
		type : 2,
		title : "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>"+msg+"</span>",
		fix : false,
		// shadeClose: true,
		area : [ '60%', '60%' ],
		content : basePath +'/xtbg/tzgg/tree.do?id='+id,
		end : function() {
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
				url : basePath + "/xtbg/tzgg/doDel.do",
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

