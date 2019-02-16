var basePath = $("#basePath").val();

$(function(){
	$("#cancleBtn").click(function(){
		var index = parent.layer.getFrameIndex(window.name);
	    parent.layer.close(index);
	});
	
});

//显示接收人
function showTree(){
	
	
	var docId = $("#docId").val();
	console.log("docId:"+docId);
	var dept ="";
	
	
	var jsr_ids ="";
	var personli = $("#personlist").find("li");
	$.each(personli, function(index, person){
        var jsr_id = $(person).attr("code");
        jsr_ids +=jsr_id+";";
	});
	top.layer.open({
	    type: 2,
	    title: "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>选择人员</span>",
	    fix: false,
	    area: ['30%', '80%'],
	    content: basePath + '/dflz/tsjb/showTree.do?docId='+docId+'&jsrIds='+jsr_ids,
	    end:function(){
		  }
	  });
}


//移除接收人
function deleteMember(accCode){
	$("#"+accCode+"_member").remove();
}



/**
 * 保存
 */
function save(){
	var jsr_ids ="";
	var personli = $("#personlist").find("li");
	//
	if(personli.length <=0){
		top.layer.confirm("请选择接收人",{ icon:0, title:"提示"});
		return;
	}
	$.each(personli, function(index, person){
        var jsr_id = $(person).attr("code");
        jsr_ids +=jsr_id+";";
	});
	
	var msgIndex1 = top.layer.msg('数据保存中...', {			icon : 16,			shade : [ 0.5, '#999999' ]});
	
	$.ajax({
		type : "POST",
		url : basePath+"/dflz/tsjb/payOut.do",
		data : {
				'cid': $("#docId").val(), 
				'pid':jsr_ids.split(";")[0]
				},
		error : function(request) {
			top.layer.close(msgIndex1);
			top.layer.msg('数据保存失败，请联系管理员', {shade : [ 0.5, '#999999' ]});
		},
		success : function(data) {
			top.layer.close(msgIndex1);
			if(data == "success") {
				top.layer.msg('保存成功',{icon : 6,time : 2000,shade : [0.5,'#999999' ]},function(){
					top.layer.close(top.layer.getFrameIndex(window.name));
					});
			}else{
				top.layer.msg('数据保存失败，请联系管理员', {shade : [ 0.5, '#999999' ]});
			}
		}
	});
	
}

