var basePath = $("#basePath").val();

/**
 * 保存
 */
document.getElementById('saveBtn').addEventListener('tap', function() {
	msgIndex = top.layer.msg('数据保存中...', {
		icon : 16,
		shade : [ 0.5, '#999999' ]
	});
	doSave();
});

function doSave() {

	var xqName = $("#xqName").val();
	var vilId = $("#vilId").val();
	var ssId = $("#ssId").val();
	var xqms = $("#xqms").val();
	
	var managers = "";// 负责人
	var phones = "";// 负责人联系电话
	var fzrs = $("input[name='manager']");
	var fzrdhs = $("input[name='phone']");
	
	$.each(fzrs, function(index, fzr) {
		// 将选项中的英文;去掉
		var manager = $(fzr).val();
		managers += manager + ";"
	});

	$.each(fzrdhs, function(index, fzrdh) {
		// 将选项中的英文;去掉
		var phone = $(fzrdh).val();
		phones += phone + ";"
	});

	if (null == xqName || xqName == "") {
		var msgIndex = top.layer.msg('未填写小区名称,无法保存!', {
			icon : 5,
			shade : [ 0.5, '#999999' ],
			time : 2000
		});
		return;
	}
	if (null == xqCode || xqCode == "" || null == ssId || ssId == "") {
		var msgIndex = top.layer.msg('请选择所属区域!', {
			icon : 5,
			shade : [ 0.5, '#999999' ],
			time : 2000
		});
		return;
	}
	$.ajax({
		type : "POST",
		url : basePath + "/app/shgl/xqxx/doSave.do",
		data : {
			'name' : xqName,
			'id' : vilId,
			'ssId' : ssId,
			'managers' : managers,
			'phones' : phones,
			'comDescription' : xqms
		},
		error : function(request) {
			top.layer.close(msgIndex);
			top.layer.msg('数据保存失败，请联系管理员!', {
				shade : [ 0.5, '#999999' ]
			});
		},
		success : function(data) {
			top.layer.close(msgIndex);
			if (data == "success") {
				top.layer.msg('保存成功!', {
					icon : 6,
					time : 2000,
					shade : [ 0.5, '#999999' ]
				}, function() {
					top.layer.close(top.layer.getFrameIndex(window.name));
					mui.back();
				});
			} else {
				top.layer.msg('数据保存失败，请联系管理员!', {
					shade : [ 0.5, '#999999' ]
				});
			}
		}
	});
}
