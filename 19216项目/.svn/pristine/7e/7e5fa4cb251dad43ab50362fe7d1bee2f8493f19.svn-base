/**
 * 修改密码
 */
var basePath = $("#basePath").val();


//获取网格列表
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

function doSave(){
	$.ajax({
		type : "POST",
		url : basePath + "/app/home/doSaveXx.do",
		data : {
			'commId':$("#wgCode1 option:selected").val(),
			'ssId':$("#ssId option:selected").val(),
			'gridId':$("#gridId option:selected").val()
			},
		success : function(data) {
			if(data == "upSuc"){
				top.layer.msg('数据修改成功', {
					icon : 6,
					time : 2000,
					shade : [ 0.5, '#999999' ]
				},function() {
					top.layer.close(top.layer.getFrameIndex(window.name));
					window.location.reload();
				});
			}else if(data == "success"){
				top.layer.msg('数据保存成功', {
					icon : 6,
					time : 2000,
					shade : [ 0.5, '#999999' ]
				},function() {
					top.layer.close(top.layer.getFrameIndex(window.name));
					window.location.reload();
				});
			}else{
				top.layer.msg('程序出错，请联系管理员', {shade : [ 0.5, '#999999' ]});
				window.location.reload();
			}
		},
		error:function(){
			top.layer.msg('程序出错，请联系管理员', {shade : [ 0.5, '#999999' ]});
			window.location.reload();
		}
	});
}