var basePath = $("#basePath").val();
$(function() {
	$.fn.zTree.init($("#menuTree"), treeSetting);
	var Accordion = function(el, multiple) {
		this.el = el || {};
		this.multiple = multiple || false;
		var links = this.el.find('.link');
		links.on('click', {
			el: this.el,
			multiple: this.multiple
		}, this.dropdown)
	}
	Accordion.prototype.dropdown = function(e) {
		var $el = e.data.el;
		$this = $(this),
			$next = $this.next();
		$next.slideToggle();
		$this.parent().toggleClass('open');
		if(!e.data.multiple) {
			$el.find('.submenu').not($next).slideUp().parent().removeClass('open');
		};
	}
	var accordion = new Accordion($('#accordion'), false);
	
	
	var type = $(this).data("type");
	var commId = $("#commId").val(); // 社区id
	var ssId = $("#ssId").val(); // 服务站id
	var gridId = $("#gridId").val(); // 网格id
	//默认查询优抚对象 01
	$("#table-container").load(basePath + "/wgdt/getPerData.do",
						{'ajaxCmd': 'table', 'type':'01','commId':commId,'ssId':ssId,'gridId':gridId}, function(){
		
	});
	
	//搜索特殊人口
	$("#tsrkButton").click(function(){
		var checkFlag = checkAccountRelation();
		if(!checkFlag){
			return ;
		}
		
		var commId = $("#commId").val(); // 社区id
		var ssId = $("#ssId").val(); // 服务站id
		var gridId = $("#gridId").val(); // 网格id
		var type =  $("#typeName").attr("intype");
		if(type =="name"){
			type="";
		}
		var itemName =  $("#typeName").html();
		var tsrkName =  $("#tsrkName").val();
		$("#table-container").load(basePath + "/wgdt/getPerData.do",
							{'ajaxCmd': 'table', 'type':type,'commId':commId,'ssId':ssId,'gridId':gridId,"tsrkName":tsrkName}, function(){
			
		});
		
		var obj = [];
		 $.ajax({
	    	type: 'POST',
	    	async:false,  //设置同步请求
	    	url: basePath + "/wgdt/findInmate.do",
	    	data:{'commId':commId,'ssId':ssId,'gridId':gridId,'inmateType':type,"tsrkName":tsrkName},
	    	success: function(data){
	    		var dataList = JSON.parse(data);
	    		if(dataList.length>0){
	    			for(var i=0;i<dataList.length;i++){
	    				if(dataList[i].lon != "0" && dataList[i].lat != "0"){
	    					var obj1 = new Object();
	    					obj1.Lon = dataList[i].build.lon;
	    					obj1.Lat = dataList[i].build.lat;
	    					obj1.ID = dataList[i].id;
	    					obj1.Name = dataList[i].name;
	    					obj1.card_no = dataList[i].card_no;
	    					obj1.telephone = dataList[i].telephone;
	    					obj1.buildingName = dataList[i].build.name;
	    					obj1.typeId = "7";
	    					// obj1.category = dataList[i].inmateTList[0].category;
	    					obj1.itemName = itemName;
	    					obj1.tsrktype = type;
	    					
	    					obj.push(obj1);
	    				}
	    			}
	    		}else{
	    			top.layer.msg('没有找到要查询的数据', {
    					icon : 0,
    					time : 1000,
    					shade : [ 0.5, '#999999' ]
    				});
	    			gotoArea(gridId, ssId, commId);
	    		}
	    	}
	    });
		mapFrame.clearMap();
		mapFrame.window.initMarks(obj);
		
	});
	
	
	//搜索xingming
	$("#inmateNameButton").click(function(){
		$("#tsrkList").find("a").removeClass("a_click");
		var msgIndex = top.layer.msg('数据加载中', {icon:16, shade:[ 0.5, '#999999' ], time:0});
		var checkFlag = checkAccountRelation();
		if(!checkFlag){
			top.layer.close(msgIndex);
			return ;
		}
		$("#tsrkName").val("");
		$("#typeName").html("姓名");
		$(".form-pepoer li.active").removeClass("active");
		$("#typeName").attr("intype","name");
		
		var commId = $("#commId").val(); // 社区id
		var ssId = $("#ssId").val(); // 服务站id
		var gridId = $("#gridId").val(); // 网格id
		var type =  $("#typeName").attr("intype");
		if(type =="name"){
			type ="";
		}
		var itemName =  $("#typeName").html();
		var tsrkName =  $("#inmateName").val().trim();
		if(tsrkName ==""){
			top.layer.msg('请输入查询内容', {
				icon : 0,
				time : 1000,
				shade : [ 0.5, '#999999' ]
			});
			return ;
		}
		$("#table-container").load(basePath + "/wgdt/getPerData.do",
							{'ajaxCmd': 'table', 'type':type,'commId':commId,'ssId':ssId,'gridId':gridId,"tsrkName":tsrkName}, function(){
			
		});
		
		var obj = [];
		 $.ajax({
	    	type: 'POST',
	    	async:false,  //设置同步请求
	    	url: basePath + "/wgdt/findInmate.do",
	    	data:{'commId':commId,'ssId':ssId,'gridId':gridId,'inmateType':type,"tsrkName":tsrkName},
	    	success: function(data){
	    		top.layer.close(msgIndex);
	    		var dataList = JSON.parse(data);
	    		if(dataList.length>0){
	    			for(var i=0;i<dataList.length;i++){
	    				if(dataList[i].lon != "0" && dataList[i].lat != "0"){
	    					var obj1 = new Object();
	    					obj1.Lon = dataList[i].build.lon;
	    					obj1.Lat = dataList[i].build.lat;
	    					obj1.ID = dataList[i].id;
	    					obj1.Name = dataList[i].name;
	    					obj1.card_no = dataList[i].card_no;
	    					obj1.telephone = dataList[i].telephone;
	    					obj1.buildingName = dataList[i].build.name;
	    					obj1.typeId = "7";
	    					obj1.tsrktype = "name";
	    					// obj1.category = dataList[i].inmateTList[0].category;
	    					obj1.itemName = itemName;
	    					obj.push(obj1);
	    				}
	    			}
	    		}else{
	    			top.layer.msg('没有找到要查询的数据', {
    					icon : 0,
    					time : 1000,
    					shade : [ 0.5, '#999999' ]
    				});
	    			gotoArea(gridId, ssId, commId);
	    		}
	    	}
	    });
		mapFrame.clearMap();
		mapFrame.window.initMarks(obj);
		
	});
});

var treeSetting = {
	view: {
		selectedMulti: false
	},
	data: {
		simpleData: {
			enable: true,
			idKey: "id",
			pIdKey: "pId",
			rootPId: "0"
		}
	},
	async: {
		autoParam: ["id"],
		enable: true,
		url: basePath + '/wgdt/initTree.do',
		type: 'post'
	},
	callback: {
		onAsyncSuccess: zTreeOnAsyncSuccess,
		onClick: zTreeOnClick
	}
}

function zTreeOnAsyncSuccess(event, treeId, treeNode, msg){
	//top.getFrameHeight();
}

function zTreeOnClick(event, treeId, treeNode) {
	//mapFrame.window.setAreaCenter(treeNode.id,treeNode.name,treeNode.area,treeNode.zoom);
	mapFrame.closePoPup();
	mapFrame.window.zTreeOnClick(event, treeId, treeNode);
};


function getData(url,id) {
	var obj = [];
	 $.ajax({
    	type: 'POST',
    	async:false,  //设置同步请求
    	url: basePath + url+'?id='+id,
    	success: function(data){
    		obj = JSON.parse(data);
    	}
    });
	return obj;
};
//  特殊人口点击事件
$(".map-silder-link a").click(function(){
	$("#tsrkName").val("");
	$("#inmateName").val("");
	var typeName = $(this).data("name");
	$("#typeName").html(typeName);
	$(".form-pepoer li.active").removeClass("active");
	$(this).parent().addClass("active");
	
	var type = $(this).data("type");
	var commId = $("#commId").val(); // 社区id
	var ssId = $("#ssId").val(); // 服务站id
	var gridId = $("#gridId").val(); // 网格id
	$("#typeName").attr("intype",type);
	$("#table-container").load(basePath + "/wgdt/getPerData.do",
						{'ajaxCmd': 'table', 'type':type,'commId':commId,'ssId':ssId,'gridId':gridId}, function(){
		
	});
});
//却换社区显示服务站列表
function showSsData(sec,ssId){
	var _html = '<option value="">服务站名称</option>';
	$.ajax({
		type : "POST",
		url : basePath + "/shgl/wggl/getComData2.do",
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
//切换网格列表
function showData(sec,ssId){
	var _html = '<option value="">网格名称</option>';
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

//如果搜索框中数据查询不存在, 依次(网格、服务站、社区)往上定位
function gotoArea(gridId, ssId, commId){
	// 如果搜索框中数据查询不存在 并且 网格id不为空, 定位到网格中心点
	if(gridId!=null && gridId!=""){
		gotoGrid(gridId, ssId, commId); // 定位到网格
	}else if(ssId!=null && ssId!=""){
		gotoServiceStation(gridId, ssId, commId); // 定位到服务站
	}else if(commId!=null && commId!=""){
		gotoComm(gridId, ssId, commId); // 定位到社区
	}else{
		top.layer.msg('没有找到要查询的数据', {
			icon : 0,
			time : 1000,
			shade : [ 0.5, '#999999' ]
		});
	}
}
// 定位到网格
function gotoGrid(gridId, ssId, commId){
	var gridName = "";
	var gridArea = "";
	var gridZoom = "17"; // 网格zoom：17
	$.ajax({
    	type: 'POST',
    	async:false,  //设置同步请求
    	url: basePath + "/shgl/wggl/getSingleAreaData.do",
    	data:{'id':gridId},
    	success: function(data){
    		var gridDataList = JSON.parse(data);
    		if(gridDataList.length > 0){
    			if(gridDataList[0] && gridDataList[0].length>0){
    				gridName = gridDataList[0][1];
    				gridArea = gridDataList[0][0];
    			}else{
    				// 网格不存在定位到服务站
    				gotoServiceStation(gridId, ssId, commId);
    			}
    		}else{
    			// 网格不存在定位到服务站
    			gotoServiceStation(gridId, ssId, commId);
    		}
    	}
	});
	if(gridName==null || gridName=="" || gridArea==null || gridArea==""){
		// 网格不存在定位到服务站
		gotoServiceStation(gridId, ssId, commId);
	}
	// alert("gridId:" + gridId + "# gridName:" + gridName + "# gridArea:" + gridArea + "# gridZoom:" + gridZoom);
	// 参数：id, name, area, zoom
	mapFrame.window.setAreaCenter(gridId, gridName, gridArea, gridZoom);
}

// 定位到服务站
function gotoServiceStation(gridId, ssId, commId){
	if(ssId!=null && ssId!=""){
		var ssName = "";
		var ssArea = "";
		var ssZoom = "16"; // 服务站zoom：16
		$.ajax({
	    	type: 'POST',
	    	async:false,  //设置同步请求
	    	url: basePath + "/shgl/fwzgl/getSingleAreaData.do",
	    	data:{'id':ssId},
	    	success: function(data){
	    		var ssDataList = JSON.parse(data);
	    		if(ssDataList.length > 0){
	    			if(ssDataList[0] && ssDataList[0].length>0){
	    				ssName = ssDataList[0][1];
	    				ssArea = ssDataList[0][0];
	    			}else{
	    				// 服务站不存在定位到社区
	    				gotoComm(gridId, ssId, commId);
	    			}
	    		}else{
	    			// 服务站不存在定位到社区
	    			gotoComm(gridId, ssId, commId);
	    		}
	    	}
		});
		if(ssName==null || ssName=="" || ssArea==null || ssArea==""){
			// 服务站不存在定位到社区
			gotoComm(gridId, ssId, commId);
		}
		mapFrame.window.setAreaCenter(ssId, ssName, ssArea, ssZoom);
	}else{
		// 服务站不存在定位到社区
		gotoComm(gridId, ssId, commId);
	}
}

// 定位到社区
function gotoComm(gridId, ssId, commId){
	if(commId!=null && commId!=""){
		var commName = "";
		var commArea = "";
		var commZoom = "15"; // 社区zoom：15
		$.ajax({
	    	type: 'POST',
	    	async:false,  //设置同步请求
	    	url: basePath + "/shgl/sqgl/getSingleAreaData.do",
	    	data:{'id':commId},
	    	success: function(data){
	    		var commDataList = JSON.parse(data);
	    		if(commDataList.length > 0){
	    			if(commDataList[0] && commDataList[0].length>0){
	    				commName = commDataList[0][1];
	    				commArea = commDataList[0][0];
	    			}else{
	    				// 社区地图没有绘制
	    				top.layer.msg('没有找到要查询的数据', {
							icon : 0,
							time : 1000,
							shade : [ 0.5, '#999999' ]
						});
	    			}
	    		}else{
	    			// 社区地图没有绘制
	    			top.layer.msg('没有找到要查询的数据', {
						icon : 0,
						time : 1000,
						shade : [ 0.5, '#999999' ]
					});
	    		}
	    	}
		});
		if(commName==null || commName=="" || commArea==null || commArea==""){
			// 社区地图没有绘制
			top.layer.msg('没有找到要查询的数据', {
				icon : 0,
				time : 1000,
				shade : [ 0.5, '#999999' ]
			});
		}
		mapFrame.window.setAreaCenter(commId, commName, commArea, commZoom);
	}else{
		top.layer.msg('没有找到要查询的数据', {
			icon : 0,
			time : 1000,
			shade : [ 0.5, '#999999' ]
		});
	}
}

function searchData(){
	$("#tsrkList").find("a").removeClass("a_click");
	var msgIndex = top.layer.msg('数据加载中', {icon:16, shade:[ 0.5, '#999999' ], time:0});
	var checkFlag = checkAccountRelation();
	if(!checkFlag){
		top.layer.close(msgIndex);
		return ;
	}
	
	var commId = $("#commId").val();
	var ssId = $("#ssId").val();
	var gridId = $("#gridId").val();
	var name = $("#name").val();
	var type = $(".nav-pills li a.active").data("type");
	if(type == "01"){//房查询
		if(commId==null || commId==''){
			top.layer.msg('请选择社区', {
				icon : 0,
				time : 1000,
				shade : [ 0.5, '#999999' ]
			});
			return ;
		}
		var obj = [];
		var url = $("#dataurl").val();
		 $.ajax({
	    	type: 'POST',
	    	async:false,  //设置同步请求
	    	url: basePath + "/wgdt/findHomes.do",
	    	data:{'commId':commId,'ssId':ssId,'gridId':gridId,'name':name},
	    	success: function(data){
	    		top.layer.close(msgIndex);
	    		var dataList = JSON.parse(data);
	    		if(dataList.length>0){
	    			for(var i=0;i<dataList.length;i++){
	    				if(dataList[i].lon != "0" && dataList[i].lat != "0"){
	    					var obj1 = new Object();
	    					obj1.Lon = dataList[i].lon;
	    					obj1.Lat = dataList[i].lat;
	    					obj1.ID = dataList[i].id;
	    					obj1.Name = dataList[i].name;
	    					obj1.Code = dataList[i].name;
	    					obj1.Units = dataList[i].unit_cnt;
	    					obj1.Floors = dataList[i].floor_cnt;
	    					obj1.Village = dataList[i].village.name;
	    					obj1.Addr = dataList[i].grid.serviceStation.community.name+dataList[i].grid.serviceStation.name+dataList[i].grid.name;
	    					obj1.typeId = "4";
	    					obj.push(obj1);
	    				}
	    			}
	    		}else{
    				top.layer.msg('没有找到要查询的数据', {
    					icon : 0,
    					time : 1000,
    					shade : [ 0.5, '#999999' ]
    				});
	    			gotoArea(gridId, ssId, commId);
	    		}
	    	}
	    });
		mapFrame.clearMap();
		mapFrame.window.initMarks(obj);
	}else if(type == "02"){//事件查询
		if(commId==null || commId==''){
			top.layer.msg('请选择社区', {
				icon : 0,
				time : 1000,
				shade : [ 0.5, '#999999' ]
			});
			return ;
		}
		var obj = [];
		var url = $("#dataurl").val();
		 $.ajax({
	    	type: 'POST',
	    	async:false,  //设置同步请求
	    	url: basePath + "/wgdt/findEvents2.do",
	    	data:{'commId':commId,'ssId':ssId,'gridId':gridId,'name':name},
	    	success: function(data){
	    		top.layer.close(msgIndex);
	    		var dataList = JSON.parse(data);
	    		if(dataList.length>0){
	    			for(var i=0;i<dataList.length;i++){
	    				if(dataList[i].lon != "0" && dataList[i].lat != "0"){
	    					var obj1 = new Object();
	    					obj1.Lon = dataList[i].applyLon;
	    					obj1.Lat = dataList[i].applyLat;
	    					obj1.ID = dataList[i].id;
	    					obj1.Name = dataList[i].title;
	    					obj1.Type = dataList[i].typeName;
	    					// obj1.Status = dataList[i].eventStastus.status;
	    					// obj1.Status = dataList[i].eventStastus!=null?(dataList[i].eventStastus.status!=null?dataList[i].eventStastus.status:"1"):"1";
	    					// obj1.Status = dataList[i].dealStatus!=null?dataList[i].dealStatus:"0";
	    					obj1.Status = dataList[i].isOver;
	    					obj1.Time = dataList[i].applyTimeFrm;
	    					obj1.ApplyName = dataList[i].applyName;
	    					obj1.Addr = dataList[i].applyAddr;
	    					obj1.typeId = "5";
	    					obj.push(obj1);
	    				}
	    			}
	    		}else{
	    			top.layer.msg('没有找到要查询的数据', {
    					icon : 0,
    					time : 1000,
    					shade : [ 0.5, '#999999' ]
    				});
	    			gotoArea(gridId, ssId, commId);
	    		}
	    	}
		 });
		 mapFrame.clearMap();
		 mapFrame.window.initMarks(obj);
	}else{//机构查询
		var obj = [];
		var url = $("#dataurl").val();
		 $.ajax({
	    	type: 'POST',
	    	async:false,  //设置同步请求
	    	url: basePath + "/wgdt/findGovernments.do",
	    	data:{'commId':commId,'ssId':ssId,'gridId':gridId,'name':name},
	    	success: function(data){
	    		top.layer.close(msgIndex);
	    		var dataList = JSON.parse(data);
	    		if(dataList.length>0){
	    			for(var i=0;i<dataList.length;i++){
	    				if(dataList[i].lon != "0" && dataList[i].lat != "0"){
	    					var obj1 = new Object();
	    					obj1.Lon = dataList[i].lon;
	    					obj1.Lat = dataList[i].lat;
	    					obj1.ID = dataList[i].id;
	    					obj1.Name = dataList[i].name;
	    					obj1.Code = dataList[i].name;
	    					obj1.AddName = dataList[i].addName;
	    					obj1.Description = dataList[i].description;
	    					obj1.StrativeName = dataList[i].strative_name;
	    					obj1.typeId = "6";
	    					obj.push(obj1);
	    				}
	    			}
	    		}else{
	    			top.layer.msg('没有找到要查询的数据', {
    					icon : 0,
    					time : 1000,
    					shade : [ 0.5, '#999999' ]
    				});
	    			gotoArea(gridId, ssId, commId);
	    		}
	    	}
	    });
		 mapFrame.clearMap();
		 mapFrame.window.initMarks(obj);
	}
}

// 查询特殊人口
function searchInmate(inmateType, itemName){
	$("#tsrkList").find("a").removeClass("a_click");
	$("#tsrkButton_"+inmateType).addClass("a_click");
	var msgIndex = top.layer.msg('数据加载中', {icon:16, shade:[ 0.5, '#999999' ], time:0});
	var checkFlag = checkAccountRelation();
	if(!checkFlag){
		top.layer.close(msgIndex);
		return ;
	}
	
	var commId = $("#commId").val(); // 社区id
	var ssId = $("#ssId").val(); // 服务站id
	var gridId = $("#gridId").val(); // 网格id
	
	var obj = [];
	 $.ajax({
    	type: 'POST',
    	async:false,  //设置同步请求
    	url: basePath + "/wgdt/findInmate.do",
    	data:{'commId':commId,'ssId':ssId,'gridId':gridId,'inmateType':inmateType},
    	success: function(data){
    		top.layer.close(msgIndex);
    		var dataList = JSON.parse(data);
    		if(dataList.length>0){
    			for(var i=0;i<dataList.length;i++){
    				if(dataList[i].lon != "0" && dataList[i].lat != "0"){
    					var obj1 = new Object();
    					obj1.Lon = dataList[i].build.lon;
    					obj1.Lat = dataList[i].build.lat;
    					obj1.ID = dataList[i].id;
    					obj1.Name = dataList[i].name;
    					obj1.card_no = dataList[i].card_no;
    					obj1.telephone = dataList[i].telephone;
    					obj1.buildingName = dataList[i].build.name;
    					obj1.typeId = "7";
    					// obj1.category = dataList[i].inmateTList[0].category;
    					obj1.itemName = itemName;
    					obj1.tsrktype = inmateType;
    					
    					obj.push(obj1);
    				}
    			}
    		}else{
    			top.layer.msg('没有找到要查询的数据', {
					icon : 0,
					time : 1000,
					shade : [ 0.5, '#999999' ]
				});
    			gotoArea(gridId, ssId, commId);
    		}
    	}
    });
	mapFrame.clearMap();
	mapFrame.window.initMarks(obj);
}

function  personOnlyPoin(id,lon,lat,name,card_no,telephone,buildingName){
	var tsrktype =  $("#typeName").attr("intype");
	var obj = [];
	if(lon != "0" && lat != "0"&&lon != "" && lat != ""){
		var obj1 = new Object();
		obj1.Lon = lon;
		obj1.Lat = lat;
		obj1.ID = id;
		obj1.Name = name;
		obj1.card_no = card_no;
		obj1.telephone = telephone;
		obj1.buildingName = buildingName;
		obj1.typeId = "7";
		// obj1.category = dataList[i].inmateTList[0].category;
		obj1.itemName = "";
		obj1.tsrktype = tsrktype;
		obj.push(obj1);
		mapFrame.clearMap();
		mapFrame.window.initMarks(obj);
	}else{
		top.layer.msg('此居民所在房屋没有被标注！', {shade : [ 0.5, '#999999' ]});
	}
}

// 校验相关用户的comm_id,ss_id是否为空(校验历史数据必填字段为空的情况)
function checkAccountRelation(){
	var flag = false;
	$.ajax({
    	type: 'POST',
    	async:false,  //设置同步请求
    	url: basePath + "/wgdt/checkAccountRelation.do",
    	success: function(data){
    		var dataObj = JSON.parse(data);
    		var result = dataObj[0];
    		if(result.success){
    			flag = true;
    		}else{
    			top.layer.msg(result.hisDataErr, {
					icon : 0,
					time : 1000,
					shade : [ 0.5, '#999999' ]
				});
    		}
    	}
    });
	return flag;
}