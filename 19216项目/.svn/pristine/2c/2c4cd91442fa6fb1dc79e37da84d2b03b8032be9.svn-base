/**
 * 党风廉政-曝光台-首页
 */

var basePath = $("#basePath").val();

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
 

$(function() {
	initData(1);
});

// Table数据加载
function initData(pageNumber) {
	var msgIndex = top.layer.msg('数据加载中', {
		icon : 16,
		shade : [ 0.5, '#999999' ],
		time : 0
	});
	$("#table-container").load(basePath + "/dflz/bgt/index.do", {
		'ajaxCmd' : 'table',
		'pageNumber' : pageNumber,
		'title' : $("#title").val(),
		'applyTime' : $("#apply_time").val()
	}, function() {
		top.layer.close(msgIndex);

	});
}
//跳转修改页面
function goUpdate(msg) {
	var id = ''; // 要修改的id
	var status='';//审核状态
	var idsStatus='';//id+审核状态字符串
	// 获取被选中的ID
	var chkItems = $('input[name="icheck"]');
	$.each(chkItems, function(index, chkItem) {
		if ($(chkItem).is(":checked")) {
			idsStatus += $(chkItem).val() + ';';
		}
	});
	var 	idsSt = idsStatus.split(";");//分离id+审核状态
	
	if (idsSt == '') {
		top.layer.confirm("请选择要修改的记录", {
			icon : 0,
			title : "提示"
		});
		return false;
	}
	if (idsSt.length > 2) {
		top.layer.confirm("只能选择一条记录", {
			icon : 0,
			title : "提示"
		});
		return false;
	}
	id=idsSt[0].split("*")[0];
	status=idsSt[0].split("*")[1];
	
	if (status == '1') {
		top.layer.confirm("已发布的数据不能修改", {
			icon : 0,
			title : "提示"
	});
		return false;
	}

	$(".btn").blur();
	top.layer
			.open({
				type : 2,
				title : "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>"
						+ msg + "</span>",
				fix : false,
				// shadeClose: true,
				area : [ '80%', '80%' ],
				content : basePath + '/dflz/bgt/goAddEdit.do?id='
						+ id,
				end : function() {
					initData(1);
				}
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
	    area: ['80%', '80%'],
	    content: basePath + '/dflz/bgt/goAddEdit.do',
	    end:function(){
	    	initData(1);
		  }
	  });
}
// 跳转查看界面
function goView(id) {
	$(".btn").blur();
	top.layer
			.open({
				type : 2,
				title : "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>查看</span>",
				fix : false,
				// shadeClose: true,
				area : [ '80%', '80%' ],
				content : basePath +'/dflz/bgt/goView.do?id='+id,
				end : function() {
					//initData(1);
				}
			});
}

//删除事件
function goDel(){
	var id = ''; // 要修改的id
	var status='';//审核状态
	var idsStatus='';//id+审核状态字符串
	// 获取被选中的ID
	var chkItems = $('input[name="icheck"]');
	$.each(chkItems, function(index, chkItem) {
		if ($(chkItem).is(":checked")) {
			idsStatus += $(chkItem).val() + ';';
		}
	});
	var idStatus=idsStatus.split(";")
	//重新拼接id 的字符串组
	var ids='';
	
	for (var int = 0; int < idStatus.length; int++) {
		ids+=idStatus[int].split("*")[0]+";";
		
	}
	
	if(ids == ''){
		top.layer.confirm("请选择要删除的记录",{
			 icon:0,
			 title:"提示"
		});
		return false;
	}

	for (var i = 0; i < idStatus.length; i++) {
		if(idStatus[i] == ""){
			break;
		}
		var statu = idStatus[i];
		
		if(statu.split("*")[1] == '1'){
			top.layer.confirm("已发布的数据不能删除",{
				 icon:0,
				 title:"提示"
			});
			return false;
		}
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
				url : basePath + "/dflz/bgt/doDel.do",
				data : {'id':ids},
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


//审核
function goAudit(id, status) {
	
	$(".btn").blur();
	top.layer
			.open({
				type : 2,
				title : "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>审核</span>",
				fix : false,
				// shadeClose: true,
				area : [ '80%', '80%' ],
				content : basePath + '/dflz/bgt/goViewAudit.do?id=' + id,
				end : function() {
					initData(1);
				}
			});
};
//修改
function update(id) {
	$(".btn").blur();
	top.layer
			.open({
				type : 2,
				title : "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>修改</span>",
				fix : false,
				// shadeClose: true,
				area : [ '80%', '80%' ],
				content : basePath + '/dflz/bgt/goAddEdit.do?id=' + id,
				end : function() {
					initData(1);
				}
			});
}

