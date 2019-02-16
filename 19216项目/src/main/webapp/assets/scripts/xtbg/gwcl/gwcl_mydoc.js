
var basePath = $("#basePath").val();

$(function(){
	initData(1);
});

//Table数据加载
function initData(pageNumber){
	var msgIndex = top.layer.msg('数据加载中', {icon:16, shade:[ 0.5, '#999999' ], time:0});
	$("#table-container").load(basePath + "/xtbg/gwcl/mydoc.do",{'ajaxCmd': 'table', 'pageNumber': pageNumber, 'title': $("#title").val()}, function(){
		top.layer.close(msgIndex);
	});
}


//跳转添加发文界面
function goAddFw(){
	top.layer.open({
	    type: 2,
	    title: "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>新建发文</span>",
	    fix: false,
	   // shadeClose: true,
	    area: ['70%', '80%'],
	    content: basePath + '/xtbg/gwcl/addFwDocPage.do',
	    end:function(){
	    	initData(1);
		  }
	  });
}

//跳转添加发文界面
function goAddSw(){
	top.layer.open({
	    type: 2,
	    title: "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>新建发文</span>",
	    fix: false,
	   // shadeClose: true,
	    area: ['70%', '80%'],
	    content: basePath + '/xtbg/gwcl/addSwDocPage.do',
	    end:function(){
	    	initData(1);
		  }
	  });
}
//查看
function goView(id){
	top.layer.open({
	    type: 2,
	    title: "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>查看公文</span>",
	    fix: false,
	   // shadeClose: true,
	    area: ['90%', '80%'],
	    content: basePath + '/xtbg/gwcl/goViewDoc.do?id='+id,
	    end:function(){
//	    	initData(1);
		  }
	  });
}


//删除事件
function goDel(){
	var ids='';//id+审核状态字符串
	// 获取被选中的ID
	var chkItems = $('input[name="icheck"]');
	$.each(chkItems, function(index, chkItem) {
		if ($(chkItem).is(":checked")) {
			ids += $(chkItem).val() + ';';
		}
	});
	if(ids == ''){
		top.layer.confirm("请选择要删除的记录",{
			 icon:0,
			 title:"提示"
		});
		return false;
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
				url : basePath + "/xtbg/gwcl/doDel.do",
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

//转发
function goTrans(id){
	top.layer.open({
	    type: 2,
	    title: "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>转发</span>",
	    fix: false,
	   // shadeClose: true,
	    area: ['50%', '60%'],
	    content: basePath + '/xtbg/gwcl/goTransView.do?id='+id,
	    end:function(){
//	    	initData(1);
		  }
	  });
}




