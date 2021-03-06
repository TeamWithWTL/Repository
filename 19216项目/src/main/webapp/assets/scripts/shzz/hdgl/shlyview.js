var basePath = $("#basePath").val();

$(function(){
	initDshData(1);
	initBtgData(1);
});

//Table待审核数据加载
function initDshData(pageNumber){
	var msgIndex = top.layer.msg('数据加载中', {icon:16, shade:[ 0.5, '#999999' ], time:0});
	$("#dshList").load(basePath + "/shzz/hdgl/goShLyView.do",{'ajaxDshCmd': 'dshTable', 'pageNumber': pageNumber, 'id': $("#id").val()}, function(){
		top.layer.close(msgIndex);
	});
}

//Table不通过数据加载
function initBtgData(pageNumber){
	var msgIndex2 = top.layer.msg('数据加载中', {icon:16, shade:[ 0.5, '#999999' ], time:0});
	$("#btgList").load(basePath + "/shzz/hdgl/goShLyView.do",{'ajaxBtgCmd': 'btgTable', 'pageNumber': pageNumber, 'id': $("#id").val()}, function(){
		top.layer.close(msgIndex2);
	});
}

/**
 * 留言审核通过
 * @param id
 * @returns
 */
function shtg(id){
	top.layer.confirm("确定通过此条留言吗？", {
		icon: 0, 
		title: "提示",
		yes: function(index){
			var msgIndex = top.layer.msg('数据审核中...', {
				icon : 16,
				shade : [ 0.5, '#999999' ]
			});
			$.ajax({
				type : "POST",
				url : basePath + "/shzz/hdgl/successLy.do",
				data : {'id' : id},
				error : function(request) {
					top.layer.close(msgIndex);
					top.layer.msg('数据审核失败，请联系管理员!', {shade : [ 0.5, '#999999' ]});
				},
				success : function(data) {
					top.layer.close(msgIndex);
					if (data == "success") {
						top.layer.msg('数据审核成功', {
							icon : 6,
							time : 2000,
							shade : [ 0.5, '#999999' ]
						}, function() {
							initDshData(1);
							initBtgData(1);
						});
					} else {
						top.layer.msg('数据审核失败，请联系管理员!', {shade : [ 0.5, '#999999' ]});
					}
				}
			});
		},
		cancel : function(index) {
			layer.close(index);
		}
	});
}

/**
 * 留言审核不通过
 * @param id
 * @returns
 */
function shbtg(id){
	top.layer.confirm("确定不通过此条留言吗？", {
		icon: 0, 
		title: "提示",
		yes: function(index){
			var msgIndex = top.layer.msg('数据审核中...', {
				icon : 16,
				shade : [ 0.5, '#999999' ]
			});
			$.ajax({
				type : "POST",
				url : basePath + "/shzz/hdgl/shLybtg.do",
				data : {'id' : id},
				error : function(request) {
					top.layer.close(msgIndex);
					top.layer.msg('数据审核失败，请联系管理员!', {shade : [ 0.5, '#999999' ]});
				},
				success : function(data) {
					top.layer.close(msgIndex);
					if (data == "success") {
						top.layer.msg('数据审核成功', {
							icon : 6,
							time : 2000,
							shade : [ 0.5, '#999999' ]
						}, function() {
							initBtgData(1);
							initDshData(1);
						});
					} else {
						top.layer.msg('数据审核失败，请联系管理员!', {shade : [ 0.5, '#999999' ]});
					}
				}
			});
		},
		cancel : function(index) {
			layer.close(index);
		}
	});
}