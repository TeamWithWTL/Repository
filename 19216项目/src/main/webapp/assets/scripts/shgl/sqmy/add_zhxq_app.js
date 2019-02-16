/**
 * 
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
/**
 * 滚动
 */
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
	var db_status ="2"; //否 是否享受低保
	var yfdx_status = "2";	//是否优抚对象 1是2否
	if($("#db_status").hasClass("mui-active")){
		db_status = "1";
	}	
	if($("#yfdx_status").hasClass("mui-active")){
		yfdx_status = "1";
	}
	
	var name = $("#name").val();
	if(null == name || name == ""){
		var msgIndex = top.layer.msg('未填写姓名,无法保存', {
			icon : 5,
			shade : [ 0.5, '#999999' ],
			time : 2000
		});
		return;
	}
	
	
	$.ajax({
			type : "POST",
			url : basePath+"/app/shgl/sqmy/doZhxqSave.do",
			data : {
				'relation':$("#relation").val(),
				'name':name,
				'sex':$("#sex").val(),
				'birth_date': $("#birth_date").val(), 
				'card_id': $("#card_id").val(),
				'zzmm': $("#zzmm").val(),
				'nation':$("#nation").val(),
				'origin':$("#origin").val(),
				'hkxz':$("#hkxz").val(),
				'grjgzk':$("#grjgzk").val(),
				'hkszd': $("#hkszd").val(), 
				'ry_type': $("#ry_type").val(),
				'whcd': $("#whcd").val(),
				'school':$("#school").val(),
				'rd_date':$("#rd_date").val(),
				'hy_status':$("#hy_status").val(),
				'jh_date':$("#jh_date").val(),
				'by_status': $("#by_status").val(), 
				'jk_status': $("#jk_status").val(),
				'duty': $("#duty").val(),
				'dept':$("#dept").val(),
				'db_status':$("#db_status").val(),
				'db_status':db_status,
				'yfdx_status':yfdx_status,
				'syry_type':$("#syry_type").val(),
				'sy_date': $("#sy_date").val(),
				'jsgldw':$("#jsgldw").val(),
				'wsjz':$("#wsjz").val(),
				'yb_type':$("#yb_type").val(),
				'zf_type':$("#zf_type").val(),
				'zf_size':$("#zf_size").val(),
				'zf_owner':$("#zf_owner").val(),
				'zfsy_type':$("#zfsy_type").val(),
				'jtfw_type':$("#jtfw_type").val(),
				'remark':$("#remark").val(),
				'zh_id' : $("#zh_id").val(),
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


