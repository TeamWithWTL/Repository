/**
 * 社会组织-社会信息-首页js
 */	

var basePath = $("#basePath").val();

$(function(){
	initData(1);
});

//Table数据加载
function initData(pageNumber){
	var msgIndex = top.layer.msg('数据加载中', {icon:16, shade:[ 0.5, '#999999' ], time:0});
	$("#table-container").load(basePath + "/shzz/zzxx/index.do",{'ajaxCmd': 'table', 'pageNumber': pageNumber, 'name': $("#name").val(), 'fr_name':  $("#fr_name").val(), 'zzxz':  $("#zzxz").val()}, function(){
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
	    content: basePath + '/shzz/zzxx/goAddEdit.do',
	    end:function(){
	    	initData(1);
		  }
	  });
}

//跳转修改页面
function goUpdate(id){
	var msg ="修改界面";
	$(".btn").blur();
	top.layer.open({
	    type: 2,
	    title: "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>"+msg+"</span>",
	    fix: false,
	   // shadeClose: true,
	    area: ['80%', '80%'],
	    content: basePath+'/shzz/zzxx/goAddEdit.do?id='+id,
	    end:function(){
	    	initData(1);
		  }
	  });
}

//跳转查看页面
function goView(id){
	top.layer.open({
	    type: 2,
	    title: "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>查看界面</span>",
	    fix: false,
	   // shadeClose: true,
	    area: ['60%', '60%'],
	    content: basePath+'/shzz/zzxx/goView.do?id='+id,
	  });
}

//删除事件
function goDel(){
	var codes = '';	//要删除的ids
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
				url : basePath + "/shzz/zzxx/doDel.do",
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