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
	$("#table-container").load(basePath + "/shgl/sjgl/myevent.do",{'ajaxCmd': 'table', 'pageNumber': pageNumber, 'title': $("#title").val(), 'applyTime': $("#apply_time").val()}, function(){
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
function report(id,curRoleId){
	$(".btn").blur();
	var text = "上报";
	if(curRoleId =='12'){
		text = "转发";
	}
	top.layer.open({
	    type: 2,
	    title: "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>"+text+"</span>",
	    fix: false,
	   // shadeClose: true,
	    area: ['800px', '450px'],
	    content: basePath + '/shgl/sjgl/eventReportPage.do?id='+id+'&curRoleId='+curRoleId,
	    end:function(){
	    	initData(1);
		  }
	  });
}

//社区管理员下发服务站
function toService(id,curRoleId){
	//alert(111);
	$(".btn").blur();
	top.layer.open({
	    type: 2,
	    title: "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>下发</span>",
	    fix: false,
	    area: ['800px', '450px'],
	    content: basePath + '/shgl/sjgl/toServicePage.do?id='+id+'&curRoleId='+curRoleId,
	    end:function(){
	    	initData(1);
		  }
	  });
}
