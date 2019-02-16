/**
 * 社情民意--增加住户App
 */
var basePath = $("#basePath").val();

$(function(){
	//1: 初始化数据
	//点击遮罩事件
	$(".mask-backdrop").click(function() {
		$(this).hide();
		$("#isPubDiv").hide();
	});

});
		//xq
	function showxq(sec,ly_id){
		var _html = '<option value="">-- 所属楼宇--</option>';
			$.ajax({
				type : "POST",
				url : basePath + "/app/shgl/sqmy/getly.do",
				data : {'xq_id':$(sec).val()},
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
		
		mui.init();
		mui('.mui-scroll-wrapper').scroll({
			indicators: false, //是否显示滚动条
			startX: 0, //初始化时滚动至x
			deceleration: 0.0004, //flick 减速系数，系数越大，滚动速度越慢，滚动距离越小，默认值0.0006
			bounce: true //是否启用回弹
		});  
	
	/**
	 *保存
	 */
		document.getElementById('saveBtn').addEventListener('tap',function(){
		msgIndex = top.layer.msg('数据保存中...',{
			icon:16,
			shade : [ 0.5 , '#999999']
		});
		doSave();
	});
		
		
	function doSave(){
	
		var name = $("#name").val();
		var xq_id = $("#xq_id").val();
		var ly_id = $("#ly_id").val();
		var unit_num = $("#unit_num").val();
		var room_num = $("#room_num").val();
		
		if(null == xq_id || xq_id == ""){
			var msgIndex = top.layer.msg('未选择所属小区,无法保存!', {
				icon : 5,
				shade : [ 0.5, '#999999' ],
				time : 2000
			});
			return;
		}
		if(null == ly_id || ly_id == ""){
			var msgIndex = top.layer.msg('未选择所属楼宇,无法保存!', {
				icon : 5,
				shade : [ 0.5, '#999999' ],
				time : 2000
			});
			return;
		}
		if(null == unit_num || unit_num == ""){
			var msgIndex = top.layer.msg('未填写单元号,无法保存!', {
				icon : 5,
				shade : [ 0.5, '#999999' ],
				time : 2000
			});
			return;
		}
		if(null == room_num || room_num == ""){
			var msgIndex = top.layer.msg('未填写室号,无法保存!', {
				icon : 5,
				shade : [ 0.5, '#999999' ],
				time : 2000
			});
			return;
		}
		
		$.ajax({
				type : "POST",
				url : basePath+"/app/shgl/sqmy/doSave.do",
				data : {
					'xq_id': xq_id, 
					'ly_id': ly_id,
					'unit_num': unit_num,
					'room_num':room_num,
					'sqmy_id' : $("#sqmy_id").val()
						},
				error : function(request) {
					top.layer.close(msgIndex);
					top.layer.msg('数据保存失败，请联系管理员!', {shade : [ 0.5, '#999999' ]});
				},
				success : function(data) {
					top.layer.close(msgIndex);
					if(data == "success") {
						top.layer.msg('保存成功!',{icon : 6,time : 2000,shade : [0.5,'#999999' ]},function(){
							top.layer.close(top.layer.getFrameIndex(window.name));
							mui.back();
							});
					}else{
						top.layer.msg('数据保存失败，请联系管理员!', {shade : [ 0.5, '#999999' ]});
					}
				}
			});
		}
	
	
	//手机按键返回
	function anGoBack(){
		mui.back();
		return;
	}
