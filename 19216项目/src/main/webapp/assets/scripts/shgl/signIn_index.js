var basePath = $("#basePath").val();

//初始化日期选择控件
$("#start").datetimepicker({
	format : "yyyy-mm",
	autoclose : true,
	startView:'year',
    minView:'year',
	language : 'zh-CN',
});

$(function() {
	var roleCodes = $("#roleCode").val();
	if(roleCodes.indexOf("99") != -1){
		$("#btnShow").show();
	}else{
		$("#btnShow").hide();
	}
	initData(1);
});


//Table数据加载
function initData(pageNumber){
	var msgIndex = top.layer.msg('数据加载中', {icon:16, shade:[ 0.5, '#999999' ], time:0});
	$("#table-container").load(basePath + "/shgl/signIn/index.do",{
		'ajaxCmd': 'table', 
		'pageNumber': pageNumber,
		'ssId':$("#ssId").val(),
		'commId':$("#commId").val(),
		'gridId':$("#gridId").val(),
		'months' : $("#rxrq1").val()
		}, function(){
		top.layer.close(msgIndex);
	});
}

function showData(sec,ssId){
	if(ssId != null || ssId != ""){
		var _html = '<option value="">--所属网格--</option>';
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
}

function goView(accCode){
	$(".btn").blur();
	top.layer.open({
	    type: 2,
	    title: "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>签到详情</span>",
	    fix: false,
	    area: ['80%', '80%'],
	    content: basePath + '/shgl/signIn/signInXp.do?accCode='+accCode+"&months="+$("#rxrq1").val(),
	    end:function(){
	    	initData(1);
		  }
	  });
}

function setMonth(){
	$(".btn").blur();
	top.layer.open({
	    type: 2,
	    title: "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>设置每月签到天数</span>",
	    fix: false,
	    area: ['80%', '80%'],
	    content: basePath + '/shgl/signIn/setMonth.do',
	    end:function(){
	    	initData(1);
		  }
	  });
}