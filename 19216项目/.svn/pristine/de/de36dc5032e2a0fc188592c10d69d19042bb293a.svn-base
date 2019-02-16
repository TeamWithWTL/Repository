/**
 * 系统管理-角色管理-权限分配
 */

var basePath = $("#basePath").val();

//全选、取消全选
function selectAll(obj){
	var chked = $(obj).is(":checked");
	var checkboxes = $("input[name='icheck']");
	if(chked){
		$.each(checkboxes, function(index, box){
			var dis = $(box).attr("disabled");
			if(!dis){
				$(box).prop("checked", true);
			}
		});
	}else{
		$("input[name='icheck']").attr("checked", false);
	}
}

$(function(){
	var _checkid = 0, _checkpid = 0, _checkbtnid = 0;
	// 初始化icheck事件
    var initICheckEvent = function () {
        $('.i-checks[name="menu"]').on('click', function (event) {
            _checkid = $(this).data('id');
            _checkpid = $(this).data('pid');
            _checkbtnid = 0;
        });
        $('.i-checks[name="menu"]').on('click', function (event) {
            var _checkstatus = $(this).prop('checked');
            var _pid = $(this).data('pid');
            var _id = $(this).data('id');
            var flag = !_checkstatus ? false : true;
            //父级检测
            if (_pid > 0) {
                //选中
                if (_checkstatus) {
                    $('.i-checks[name="menu"][data-id="' + _pid + '"]').prop("checked",flag);
                }
                //取消选中
                else {
                    var _length = $('.i-checks[name="menu"][data-pid="' + _pid + '"]').length;
                    var _clength = $('.i-checks[name="menu"][data-pid="' + _pid + '"]:not(:checked)').length;
                    if (_clength >= _length) {
                        $('.i-checks[name="menu"][data-id="' + _pid + '"]').prop("checked",flag);
                    }
                }
            }

            //子级检测
            if (_checkid == _id || _checkpid == 0) {
                $('.i-checks[name="menu"][data-pid="' + _id + '"]').prop("checked",flag);
            }

            //按钮
            if (_checkbtnid == 0) {
            	$('.i-checks[name="btn"][data-menuid="' + _id + '"]').prop("checked",flag);
            	$('.i-checks[name="btn"][data-pid="' + _id + '"]').prop("checked",flag);
            }
        });

        $('.i-checks[name="btn"]').on('click', function (event) {
            //Click事件
            var _checkstatus = $(this).prop('checked');
            var _menuid = $(this).data('menuid');

            var pmenu = $('.i-checks[name="menu"][data-id="' + _menuid + '"]');
            _checkid = $(pmenu).data('id');
            _checkpid = $(pmenu).data('pid');
            _checkbtnid = _menuid;
            //选中
            if (_checkstatus) {
                pmenu.prop("checked",true);
            }
            //取消选中
            else {
                var _length = $('.i-checks[name="btn"][data-menuid="' + _menuid + '"]').length;
                var _clength = $('.i-checks[name="btn"][data-menuid="' + _menuid + '"]:not(:checked)').length;
             
                if (_clength >= _length) {
                	//add by jiangkai
//                	 pmenu.prop("checked",false);
                }
            }
        });
    }
	
    // 注册iCheck事件
	initICheckEvent();
    
 	// 全选
	$("#selectAll").on('click', function(){
		$('.i-checks').prop("checked",true);
	});
	
	// 清空
	$("#clrSeletc").on('click', function(){
		$('.i-checks').prop("checked",false);
	});
	
	// 保存权限
	$("#save").on('click', function(){
		// 获取被选择的权限
		var allCheckedMenu = $("input[name='menu']:checked");
		if(allCheckedMenu.length == 0){
			top.layer.alert('权限不可为空', {icon:0, title:"提示"});
			return false;
		}else{
			var rights = '';
			$.each(allCheckedMenu, function(i, obj){
				var menuCode = $(obj).data("id");
				// 菜单下的功能按钮
				var btns = $("input[name='btn'][data-menuid='" + menuCode + "']:checked");
				var btnStr = '';
				$.each(btns, function(j, btn){
					btnStr += $(btn).data("id") + ",";
				});
				rights += menuCode + "-" + btnStr + ";";
			});
			// 执行清理操作
			top.layer.confirm("确定要为角色分配所选权限吗？", {
				icon: 0, 
				title: "提示",
				yes: function(index){
					var msgIndex = top.layer.msg('权限分配中', {
						icon : 16,
						shade : [ 0.5, '#999999' ]
					});
					$.ajax({
						type : "POST",
						url : basePath + "/xtgl/jsgl/assignRights.do",
						data : {'roleCode':$("#roleCode").val(), 'rights': rights},
						error : function(request) {
							top.layer.close(msgIndex);
			        		top.layer.msg('程序出错，请联系管理员', {shade : [ 0.5, '#999999' ]});
						},
						success : function(data) {
							top.layer.close(msgIndex);
							if(data == "99"){
								top.layer.msg('超级管理员权限不允许分配', {shade : [ 0.5, '#999999' ]});
								return false;
							}else if (data == "succ") {
								top.layer.msg('权限分配成功', {
									icon : 6,
									time : 2000,
									shade : [ 0.5, '#999999' ]
								}, function(){
									top.layer.close(top.layer.getFrameIndex(window.name));
								});
							}else{
								top.layer.msg('权限分配失败，请联系管理员', {shade : [ 0.5, '#999999' ]});
							}
						}
					});
				},
				cancel : function(index) {
					top.layer.close(index);
				}
			});
		}
	});
});