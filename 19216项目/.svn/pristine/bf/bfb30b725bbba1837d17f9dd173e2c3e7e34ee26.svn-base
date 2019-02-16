/**
 * 公共函数
 */

/****************************
 * 生成UUID
 * Len 长度
 * radix 进制
 ****************************/
function createUUID(len, radix){
	var CHARS = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
	var chars = CHARS, uuid = [], i;
	radix = radix || chars.length;
	if(len){
		for (i = 0; i < len; i++) uuid[i] = chars[0 | Math.random()*radix];
	}else{
	    var r;
	    uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
	    uuid[14] = '4';
	    for(i = 0; i < 36; i++){
	    	if(!uuid[i]){
	    		r = 0 | Math.random()*16;
	    		uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
	    	}
	    }
	}
	return uuid.join('');
}
/**************************** 
 * 截取字符串，中英文都能用 
 * str：需要截取的字符串 
 * len: 需要截取的长度 
 ****************************/
function cutstr(str, len) {
    var str_length = 0;
    var str_len = 0;
    var str_cut = new String();
    str_len = str.length;
    for (var i = 0; i < str_len; i++) {
        a = str.charAt(i);
        str_length++;
        if (escape(a).length > 4) {
            // 中文字符的长度经编码之后大于4
            str_length++;
        }
        str_cut = str_cut.concat(a);
        if (str_length >= len) {
            str_cut = str_cut.concat("...");
            return str_cut;
        }
    }
    // 如果给定字符串小于指定长度，则返回源字符串
    if (str_length < len) {
        return str;
    }
}
/**************************** 
 * 图片预览
 * url
 ****************************/
function showBigPic(url){   
   top.layer.open({
	   type: 1,
	   title: false,
	   area: ['800px','600px'],
	   zIndex:9999999999999,
	   content:  '<img style ="width:800px;height:600px;" src =  "'+url+'&showbig=1"/>' //这里content是一个DOM
	 });
}
/**************************** 
* pdf预览
* url
****************************/
function goPdf(url){
	$(".btn").blur();
	top.layer.open({
	    type: 2,
	    title: "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>预览</span>",
	    fix: false,
	   // shadeClose: true,
	    area: ['80%', '80%'],
	    content: url
	  });
}
/**************************** 
 * 全选、全不选（每次数据异步初始化完成之后都要调用此函数）
 * iCheck的函数声明必须在初始化之前进行
 * 前提是使用iCheck插件
 * 全选按钮的ID必须为checkAll
 * checkBox的name必须为icheck
 ****************************/
//function iCheckTable(){
//	// iCheck全选、全不选
//	$("#checkAll").on('ifChecked', function(event){
//		$("input[name='icheck']").iCheck('check');
//	});
//	$("#checkAll").on('ifUnchecked', function(event){
//		$("input[name='icheck']").iCheck('uncheck');
//	});
//	
//	// 初始化iCheck
//	$('.i-checks').iCheck({
//		checkboxClass: 'icheckbox_minimal-grey',
//		radioClass: 'iradio_minimal-grey',
//	    increaseArea: '20%' // optional
//	});
//}

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

function showSs(sec,ssId){
	var _html = '<option value="">-- 所属服务站--</option>';
	$.ajax({
		type : "POST",
		url : basePath + "/shgl/wggl/getComData.do",
		data : {'comId':$(sec).val()},
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

function showSsApp(sec,ssId){
	var _html = '<option value="">-- 所属服务站--</option>';
	$.ajax({
		type : "POST",
		url : basePath + "/app/shgl/wggl/getComData.do",
		data : {'comId':$(sec).val()},
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

//选择地图范围
function choseArea(id,name,url){
	$(".btn").blur();
	top.layer.open({
	    type: 2,
	    title: "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>地图绘制--"+name+"</span>",
	    fix: false,
	   // shadeClose: true,
	    area: ['80%', '80%'],
	    content: basePath + url+'?id='+id,
	    end:function(){
	    	
		  }
	  });
}

//保存地图范围
function setArea(area,areaColor,lineColor){
	var url = $("#url").val();
	var msgIndex = top.layer.msg('数据保存中', {icon:16, shade:[ 0.5, '#999999' ], time:0});
    $.ajax({
    	type: 'POST',
    	url: basePath + url,
    	data:{'id':$("#id").val(),'area':area,'areaColor':areaColor,'lineColor':lineColor},
    	success: function(data){
    		top.layer.close(msgIndex);
    		if(data=='success'){
    			top.layer.msg('数据保存成功', {
					icon : 6,
					time : 2000,
					shade : [ 0.5, '#999999' ]
				},function() {
					top.layer.close(top.layer.getFrameIndex(window.name));
				});
    		}else{
    			top.layer.msg('数据保存失败，请联系管理员', {shade : [ 0.5, '#999999' ]});
    		}
    	},
    	error: function(data){
    		top.layer.close(msgIndex);
    		top.layer.msg('程序出错，请联系管理员', {shade : [ 0.5, '#999999' ]});
    	}
    });
}

/**
 * 返回首页
 * @param url
 * @returns
 */
function goHome(url){
	window.parent.location.href = url;
}

//添加负责人div
function addOption(obj){
	var html = '';
	html = '<div class="form-group row">' + 
				'<label for="" class="col-sm-3 col-form-label"></label>' +
					'<div class="col-sm-6">' +
						'<div class="input-group">' +
							'<input class="form-control" type="text" name="manager" placeholder="姓名"/>' +
							'<input class="form-control" type="text" name="phone" placeholder="电话号码"/>' +
							'<span class="input-group-btn">' +
								'<button type="button" class="btn btn-danger" onClick="delOption(this)"><i class="fa fa-trash" aria-hidden="true"></i></button>' +
							'</span>' +
						'</div>' +
					'</div>' +
				'</div>';
		
	$("#fz_div").append(html);
}

//删除负责人div
function delOption(obj){
	$(obj).parent().parent().parent().parent().remove();
}