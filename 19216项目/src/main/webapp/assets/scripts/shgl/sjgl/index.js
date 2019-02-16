/**
 * 社会管理-事件管理-首页
 */	

var basePath = $("#basePath").val();

$("#apply_time").datetimepicker({
    format: "yyyy-mm-dd",
    autoclose: true,
    minView: 'month',
    language: 'zh-CN',
    clearBtn: true
});

$(function(){
	initData(1);
});

//Table数据加载
function initData(pageNumber){
	var msgIndex = top.layer.msg('数据加载中', {icon:16, shade:[ 0.5, '#999999' ], time:0});
	$("#table-container").load(basePath + "/shgl/sjgl/index.do",{'ajaxCmd': 'table', 'pageNumber': pageNumber, 'title': $("#title").val(), 'applyTime': $("#apply_time").val()}, function(){
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
	    area: ['800px', '520px'],
	    content: basePath + '/shgl/sjgl/goView.do?id='+id,
	    end:function(){
	    	initData(1);
		  }
	  });
}
//跳转查看界面
function goViewDeal(id){
	$(".btn").blur();
	top.layer.open({
	    type: 2,
	    title: "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>查看事件</span>",
	    fix: false,
	   // shadeClose: true,
	    area: ['80%', '520px'],
	    content: basePath + '/shgl/sjgl/goView.do?id='+id,
	    end:function(){
	    	initData(1);
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
	    content: basePath + '/shgl/sjgl/goDeal.do?id='+id,
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
	    content: basePath + '/shgl/sjgl/goReport.do?id='+id,
	    end:function(){
	    	initData(1);
		  }
	  });
}