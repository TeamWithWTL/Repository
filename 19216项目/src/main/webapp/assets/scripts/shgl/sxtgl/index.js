/**
 * 社会管理-摄像头管理-首页
 */	

var basePath = $("#basePath").val();

$(function(){
	initData(1);
});

//Table数据加载
function initData(pageNumber){
	var msgIndex = top.layer.msg('数据加载中', {icon:16, shade:[ 0.5, '#999999' ], time:0});
	$("#table-container").load(basePath + "/shgl/sxtgl/index.do",{'ajaxCmd': 'table', 'pageNumber': pageNumber, 'name': $("#name").val(),'ssId':$("#ssId").val(),'commId':$("#commId").val(),'gridId':$("#gridId").val()}, function(){
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
	    area: ['800px', '350px'],
	    content: basePath + '/shgl/sxtgl/goAddEdit.do',
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
	    area: ['800px', '350px'],
	    content: basePath+'/shgl/sxtgl/goAddEdit.do?id='+code[0],
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
				url : basePath + "/shgl/sxtgl/goDel.do",
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

function showData(sec,ssId){
	var _html = '<option value="">-- 所属网格--</option>';
	$.ajax({
		type : "POST",
		url : basePath + "/shgl/xqxx/getData.do",
		data : {'ssId':$(sec).val()},
		success : function(data) {
			var obj = JSON.parse(data);
			if(obj.length>0){
				for(var i=0;i<obj.length;i++){
					_html+='<option value="'+obj[i].id+'">'+obj[i].name+'</option>';
				}
			}
			$("#"+ssId).html(_html);
		},
		error:function(){
			$("#"+ssId).html(_html);
		}
	});
}