var basePath = $("#basePath").val();
var flag = $("#flag").val();
var agoId = $("#agoId").val();

$(function(){
	var type = $("#type").val();
	$(".ryfl").each(function(){
		if(type.indexOf($(this).data("val")) != -1){
			$(this).addClass("mui-active");
		}
	});
	if($(".housemaster").data("val") == "1"){
		$(".housemaster").addClass("mui-active");
	}
})

mui("#header").on('tap','a',function(){
	if(flag == "1"){
		//返回到居民信息详情
		window.location.href = basePath + "/app/shgl/jmxx/goView.do?id="+$("#id").val()
		+"&lyflag="+$("#lyflag").val()
		+"&lyId="+$("#lyId").val()
		+"&unitNo="+$("#unitNo").val()
		+"&roomNo="+$("#roomNo").val();
	}else{
		//返回到家庭成员详情
		window.location.href = basePath + "/app/shgl/jmxx/goViewBycy.do?id="+$("#id").val()
		+"&agoId="+agoId
		+"&lyflag="+$("#lyflag").val()
		+"&lyId="+$("#lyId").val()
		+"&unitNo="+$("#unitNo").val()
		+"&roomNo="+$("#roomNo").val();
	}
});

//手机按键返回
function anGoBack(){
	if(flag == "1"){
		//返回到居民信息详情
		window.location.href = basePath + "/app/shgl/jmxx/goView.do?id="+$("#id").val()
		+"&lyflag="+$("#lyflag").val()
		+"&lyId="+$("#lyId").val()
		+"&unitNo="+$("#unitNo").val()
		+"&roomNo="+$("#roomNo").val();
	}else{
		//返回到家庭成员详情
		window.location.href = basePath + "/app/shgl/jmxx/goViewBycy.do?id="+$("#id").val()
		+"&agoId="+agoId
		+"&lyflag="+$("#lyflag").val()
		+"&lyId="+$("#lyId").val()
		+"&unitNo="+$("#unitNo").val()
		+"&roomNo="+$("#roomNo").val();
	}
	return;
}


//保存
function saveDo(){
	
	var housemaster = "";//是否是户主
	if($(".housemaster").data("val") == "1"){
		housemaster = $(".housemaster").data("val");
	}else{
		housemaster = $(".housemaster").data("val");
	}
	$(".ryfl").each(function(){
		var type = $("#type").val();//人员分类
		if($(this).hasClass('mui-active')){
			var aa = $(this).data("val");
			if(type.indexOf($(this).data("val")) != -1){
				$("#type").val(type.replace($(this).data("val")+",",""));
				$("#type").val($(this).data("val")+",");
			}else{
				if(type == ""){
					$("#type").val($(this).data("val")+",");
				}else{
					$("#type").val(type+$(this).data("val")+",");
				}
			}
		}
	});
	var gridId = $("#gridId").val();
	var id = $("#id").val();
	var name = $("#name").val();
	var sex = $("#sex option:selected").val();
	var birthdayFrm = $("#birthdayFrm").val();
	var card_no = $("#card_no").val();
	var nation = $("#nation option:selected").val();//民族
	var telephone = $("#telephone").val();
	var house_register = $("#house_register").val();//籍 贯
	var political = $("#political option:selected").val();//政治面貌
	var marriage = $("#marriage option:selected").val();//婚姻状态 
	var work_unit = $("#work_unit").val();//工作处所
	var car_no = $("#car_no").val();//车牌号
	var education = $("#education option:selected").val();//文化程度
	var hm_relation = $("#hm_relation").val();//与户主关系
	
	
	if(gridId == '' || gridId == null){
		top.layer.alert("请选择所在区域",{
			 icon:5,
			 title:"提示"
		});
		return false;
	}
//	var buildId = $("#buildId").val();
//	if(buildId == '' || buildId == null){
//		top.layer.confirm("请选择房屋信息",{
//			 icon:0,
//			 title:"提示"
//		});
//		return false;
//	}
        var msgIndex = top.layer.msg('数据保存中', {icon:16, shade:[ 0.5, '#999999' ], time:0});
        $.ajax({
        	type: 'POST',
        	url: basePath + '/app/shgl/jmxx/doSave.do',
        	data: {
        		'name':name,
        		'sex':sex,
        		'birthdayFrm':birthdayFrm,
        		'card_no':card_no,
        		'nation':nation,
        		'telephone':telephone,
        		'house_register':house_register,
        		'political':political,
        		'marriage':marriage,
        		'work_unit':work_unit,
        		'car_no':car_no,
        		'education':education,
        		'hm_relation':hm_relation,
        		'housemaster':housemaster,
        		'types':$("#type").val(),
        		'id':id,
        		'commId':$("#commId option:selected").val(),
        		'ssId':$("#ssId option:selected").val(),
        		'gridId':$("#gridId option:selected").val(),
        		'buildId' : $("#buildId").attr('name'),
        		'unit_no' : $("#unit_no").val(),
        		'room_no' : $("#room_no").val()
        	},
        	success: function(data){
        		top.layer.close(msgIndex);
        		if(data == 'exists'){
        			top.layer.msg('数据已存在,请到列表中查看', {shade : [ 0.5, '#999999' ]},function() {
    					top.layer.close(top.layer.getFrameIndex(window.name));
					});
        		}else if(data=='success'){
        			top.layer.msg('数据保存成功', {
    					icon : 6,
    					time : 2000,
    					shade : [ 0.5, '#999999' ]
    				},function() {
    					top.layer.close(top.layer.getFrameIndex(window.name));
    					if(flag == "1"){
    						//返回到居民信息详情
    						window.location.href = basePath + "/app/shgl/jmxx/goView.do?id="+id
    						+"&lyflag="+$("#lyflag").val()
    						+"&lyId="+$("#lyId").val()
				    		+"&unitNo="+$("#unitNo").val()
							+"&roomNo="+$("#roomNo").val();
    					}else{
    						//返回到家庭成员详情
    						window.location.href = basePath + "/app/shgl/jmxx/goViewBycy.do?id="+id
    						+"&agoId="+agoId
    						+"&lyflag="+$("#lyflag").val()
    						+"&lyId="+$("#lyId").val()
				    		+"&unitNo="+$("#unitNo").val()
							+"&roomNo="+$("#roomNo").val();
    					}
    					
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
};


