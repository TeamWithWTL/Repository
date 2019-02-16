
var basePath = $("#basePath").val();

$(function(){
	initData(1);
});

//Table数据加载
function initData(pageNumber){
	var msgIndex = top.layer.msg('数据加载中', {icon:16, shade:[ 0.5, '#999999' ], time:0});
	$("#table-container").load(basePath + "/xtbg/gwcl/myreceive.do",{'ajaxCmd': 'table', 'pageNumber': pageNumber, 'title': $("#title").val()}, function(){
		top.layer.close(msgIndex);
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
//处理
function goDeal(dealId){
	top.layer.open({
	    type: 2,
	    title: "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>处理意见</span>",
	    fix: false,
	   // shadeClose: true,
	    area: ['60%', '50%'],
	    content: basePath + '/xtbg/gwcl/goDealPage.do?dealId='+dealId,
	    end:function(){
	    	initData(1);
		  }
	  });
}

