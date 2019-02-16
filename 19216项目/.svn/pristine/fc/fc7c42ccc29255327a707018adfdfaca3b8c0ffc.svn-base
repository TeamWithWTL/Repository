/**
 * 社会管理-服务站管理-首页
 */	

var basePath = $("#basePath").val();

$(function(){
	initData(1);
});

//Table数据加载
function initData(pageNumber){
	var msgIndex = top.layer.msg('数据加载中', {icon:16, shade:[ 0.5, '#999999' ], time:0});
	$("#table-container").load(basePath + "/shgl/fwzgl/index.do",{'ajaxCmd': 'table', 'pageNumber': pageNumber, 'name': $("#name").val(),'commId':$("#commId").val(), 'manager':$("#manager").val(), 'phone':$("#phone").val()}, function(){
		top.layer.close(msgIndex);
		
	});
}

//跳转添加界面
function goAdd(msg){
	$(".btn").blur();
	top.layer.open({
	    type: 2,
	    title: "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>"+msg+"</span>",
	    fix: false,
	   // shadeClose: true,
	    area: ['80%', '80%'],
	    content: basePath + '/shgl/fwzgl/goAddEdit.do',
	    end:function(){
	    	initData(1);
		  }
	  });
}

//修改界面
function goUpdate(msg){
	var codes = '';	// 要修改的id
	// 获取被选中的数据ID
	var chkItems = $('input[name="icheck"]');
	$.each(chkItems, function(index, chkItem){
		if($(chkItem).is(":checked")){
			codes += $(chkItem).val() + ';' ;
		}
	});
	var code = codes.split(";");
	if(codes == ''){
		top.layer.confirm("请选择操作对象",{
			 icon:0,
			 title:"提示"
		});
		return false;
	}
	if(code.length>2){
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
	    content: basePath+'/shgl/fwzgl/goAddEdit.do?id='+code[0],
	    end:function(){
	    	initData(1);
		  }
	  });
}
//删除
function goDel(){
	var codes = '';	// 要删除的id
	// 获取被选中的数据ID
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
	goDelDq(codes);
}

//单条修改
function goUpdateDq(id){
	$(".btn").blur();
	top.layer.open({
	    type: 2,
	    title: "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>修改界面</span>",
	    fix: false,
	   // shadeClose: true,
	    area: ['80%', '80%'],
	    content: basePath+'/shgl/fwzgl/goAddEdit.do?id='+id,
	    end:function(){
	    	initData(1);
		  }
	  });
}

//单条删除
function goDelDq(codes){
	top.layer.confirm("确定要删除所选记录吗？", {
		icon: 0, 
		title: "提示",
		yes: function(index){
			var msgIndex = top.layer.msg('数据删除中...', {
				icon : 16,
				shade : [ 0.5, '#999999' ]
			});
			$.ajax({
				type : "POST",
				url : basePath + "/shgl/fwzgl/goDel.do",
				data : {'ids':codes},
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

function goImpFile(){
	$(".btn").blur();
	top.layer.open({
	    type: 2,
	    title: "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>导入</span>",
	    fix: false,
	   // shadeClose: true,
	    area: ['50%', '40%'],
	    content: basePath+'/shgl/fwzgl/shouImportView.do',
	    end:function(){
	    	initData(1);
		  }
	  });
}

function goExport(){
	var commId = $("#commId").val();
	//alert(commId);
	var name = $("#name").val();
	//alert(name);
	//debugger;
	top.layer.confirm("确定要导出信息吗？", {
		icon : 0,
		title : "提示",
		yes : function(index) {
			var msgIndex = top.layer.msg('数据导出中...', {
				icon : 16,
				shade : [ 0.5, '#999999' ]
			});
			name=escape(encodeURIComponent(name));
			window.location.href = basePath + "/shgl/fwzgl/export.do?commId=" 
			+ commId + '&flag=export'
			+ '&name='+name;
		},
		cancel : function(index) {
			layer.close(index);
		}
	});

	/*window.location.href =  basePath+'/shgl/fwzgl/export.do?flag=export';*/
}