/**
 * 社会管理-网格管理-首页
 */	

var basePath = $("#basePath").val();

$(function(){
	initData(1);
});

//Table数据加载
function initData(pageNumber){
	var msgIndex = top.layer.msg('数据加载中', {icon:16, shade:[ 0.5, '#999999' ], time:0});
	$("#table-container").load(basePath + "/shgl/jmxx/chooseBuild.do",{'ajaxCmd': 'table', 'pageNumber': pageNumber,'ssId':$("#ssId").val(),'commId':$("#commId").val(),'gridId':$("#gridId").val()}, function(){
		top.layer.close(msgIndex);
		
	});
}

function choose(){
	var codes='';
	// 获取被选中的数据ID
	var chkItems = $('input[name="icheck"]');
	
	$.each(chkItems, function(index, chkItem){
		if($(chkItem).is(":checked")){
			codes = $(chkItem).val();
		}
	});
	//alert(codes);
	if(codes== ''){
		top.layer.confirm("请选择楼宇",{
			 icon:0,
			 title:"提示"
		});
		return false;
	}
	var buildId=codes.split("*")[0];
	var buildName=codes.split("*")[1];
	//alert(buildId+"  "+buildName );
	parent.layer.getChildFrame('body').find("#buildId").val(buildId);
	parent.layer.getChildFrame('body').find("#buildName").val(buildName);
	var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
}


