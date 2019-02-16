/**
 * 党风廉政-投诉举报-首页
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
	$("#table-container").load(basePath + "/dflz/tsjb/index.do", {
		'ajaxCmd' : 'table',
		'pageNumber' : pageNumber,
		'title' : $("#title").val(),
		'applyTime' : $("#apply_time").val(),
		'status':$("#status").val()
	}, function() {
		top.layer.close(msgIndex);

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
				content : basePath +'/dflz/tsjb/goView.do?id='+id,
				end : function() {
					//initData(1);
				}
			});
}

// 删除事件
function goDel(){
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
			top.layer.confirm("已审核的数据不能删除",{
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
				url : basePath + "/dflz/tsjb/doDel.do",
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
};
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
				content : basePath + '/dflz/tsjb/goViewAudit.do?id=' + id,
				end : function() {
					initData(1);
				}
			});
};
	/**
	 * 转发
	 * @param msg
	 */
//转发
function forward(id){
	top.layer.open({
	    type: 2,
	    title: "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>转发</span>",
	    fix: false,
	   // shadeClose: true,
	    area: ['50%', '60%'],
	    content: basePath + '/dflz/tsjb/goTransView.do?id='+id,
	    end:function(){
	    	initData(1);
		  }
	  });
}
	/*function forward(id){
		//alert(id);
		top.layer
		.open({
			type : 2,
			title : "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>转发</span>",
			fix : false,
			// shadeClose: true,
			area : [ '80%', '80%' ],
			content : basePath +'/dflz/tsjb/tree.do?id='+id,
			end : function() {
				initData(1);
			}
		});
	}*/
	
	
	
	
	//处理
	function goHandle(id){
		
		top.layer.open({
		    type: 2,
		    title: "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>处理意见</span>",
		    fix: false,
		   // shadeClose: true,
		    area: ['60%', '50%'],
		    content: basePath + '/dflz/tsjb/goDealPage.do?id='+id,
		    end:function(){
		    	initData(1);
			  }
		  });
	}
	