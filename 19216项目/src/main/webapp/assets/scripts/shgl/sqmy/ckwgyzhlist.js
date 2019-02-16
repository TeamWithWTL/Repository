/**
 * 社会管理-社情民意-查看网格员调查住户列表
 */	
var basePath = $("#basePath").val();

$(function(){
	initData(1);
});

//Table数据加载
function initData(pageNumber){
	var vId = $("#vId option:selected").val();
	var unit_num = $("#unit_num option:selected").val();
	var msgIndex = top.layer.msg('数据加载中', {icon:16, shade:[ 0.5, '#999999' ], time:0});
	$("#table-container").load(basePath + "/shgl/sqmy/ckwgyzhlist.do",{'ajaxCmd': 'table', 'pageNumber': pageNumber,'wgyID': $("#wgyID").val() ,'rqmy_id': $("#rqmy_id").val(),'xq_id': vId,'ly_id': $("#ly_id").val(),'unit_num': unit_num }, function(){
		top.layer.close(msgIndex);
	});
}

function showxq(sec,ly_id){
	var _html = '<option value="">-- 所属楼宇--</option>';
	$.ajax({
		type : "POST",
		url : basePath + "/shgl/sqmy/getly.do",
		data : {'vId':$(sec).val()},
		success : function(data) {
			var obj = JSON.parse(data);
			if(obj.length>0){
				for(var i=0;i<obj.length;i++){
					_html+='<option value="'+obj[i].id+'">'+obj[i].name+'</option>';
				}
			}
			$("#"+ly_id).html(_html);
		},
		error:function(){
			$("#"+ly_id).html(_html);
		}
	});
}

function showly(sec,unit_num){
	var _html = '<option value="">-- 所属单元--</option>';
	$.ajax({
		type : "POST",
		url : basePath + "/shgl/sqmy/getdy.do",
		data : {'ly':$(sec).val()},
		success : function(data) {
			var obj = JSON.parse(data);
			if(obj.length>0){
				for(var i=0;i<obj.length;i++){
					_html+='<option value="'+obj[i].unit_no+'">'+obj[i].unit_no+'</option>';
				}
			}
			$("#"+unit_num).html(_html);
		},
		error:function(){
			$("#"+unit_num).html(_html);
		}
	});
}

//跳转查看网格员调查用户人员列表页面
function goView(sqmyzhid){
	top.layer.open({
	    type: 2,
	    title: "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>查看调查住户详情</span>",
	    fix: false,
	   // shadeClose: true,
	    area: ['950px', '450px'],
	    content: basePath+'/shgl/sqmy/ckwgyzhrylist.do?zh_id='+sqmyzhid,
	  });
}