/**
 * 社会管理-事件管理-首页
 */	

var basePath = $("#basePath").val();

$(function(){
	//初始化日期选择控件
	$("#datetimepicker").datetimepicker({
		  
		format: 'yyyy-mm-dd',  
	    weekStart: 1,  
	    autoclose: true,  
	    startView: 2,  
	    minView: 2,  
	    forceParse: false,  
	    language: 'zh-CN',
	    clearBtn: true
	});
	
	initData(1);
	
});

//Table数据加载
function initData(pageNumber){
	var msgIndex = top.layer.msg('数据加载中', {icon:16, shade:[ 0.5, '#999999' ], time:0});
	$("#table-container").load(basePath + "/shgl/sjgl/myPubEvent.do",{'ajaxCmd': 'table', 'pageNumber': pageNumber, 'title': $("#title").val(), 'applyTime': $("#apply_time").val(), 'sjly': $("#sjly").val()}, function(){
		top.layer.close(msgIndex);
	});
}

//跳转查看界面
function goView(id){
	$(".btn").blur();
	top.layer.open({
	    type: 2,
	    title: "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>查看事件</span>",
	    fix: false,
	   // shadeClose: true,
	    area: ['90%', '80%'],
	    content: basePath + '/shgl/sjgl/eventview.do?id='+id,
	    end:function(){
//	    	initData(1);
		  }
	  });
}

//跳转处理界面
function goDeal(id){
	$(".btn").blur();
	top.layer.open({
	    type: 2,
	    title: "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>处理事件</span>",
	    fix: false,
	   // shadeClose: true,
	    area: ['800px', '450px'],
	    content: basePath + '/shgl/sjgl/eventDealPage.do?id='+id,
	    end:function(){
	    	initData(1);
		  }
	  });
}

//跳转上报界面
function report(id){
	$(".btn").blur();
	top.layer.open({
	    type: 2,
	    title: "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>上报事件</span>",
	    fix: false,
	   // shadeClose: true,
	    area: ['800px', '450px'],
	    content: basePath + '/shgl/sjgl/eventReportPage.do?id='+id,
	    end:function(){
	    	initData(1);
		  }
	  });
}

//跳转添加页面
function goAdd(msg){
	$(".btn").blur();
	top.layer.open({
	    type: 2,
	    title: "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>"+msg+"</span>",
	    fix: false,
	    area: ['80%', '80%'],
	    content: basePath + '/shgl/sjgl/addEvent.do',
	    end:function(){
	    	initData(1);
		  }
	  });
}

/**
 * 删除事件
 * @returns
 */
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
				url : basePath + "/shgl/sjgl/goDel.do",
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

