var basePath = $("#basePath").val();
var roleCode = $("#roleCode").val();

$(document).ready(function(){
	// 异步加载
	$.ajaxSetup ({ 
		cache: false 
		});
	initRdgz();//热点关注
	initData();//社区人口统计
	initSjtj();//事件统计
	initLbtj();//事件类别统计
	initSjly();//事件来源统计
	initTsrk();//特殊人口统计
});


/**
 * 热点关注首页-begin=========================================================================
 * @returns
 */
function initRdgz(){
	$("#table-container").load(basePath + "/home/initRdgz.do", {
		'ajaxCmd' : 'table',
	}, function() {
	});
}
//廉政要闻
function initLzyw(){
	$("#table-container").load(basePath + "/home/initLzyw.do", {
		'ajaxCmd' : 'table',
	}, function() {
	});
}
//优秀社会活动
function initYxshhd(){
	$("#tableyxsh-container").load(basePath + "/home/initYxshhd.do", {
		'ajaxCmd' : 'tableyxshhd',
	}, function() {
	});
}

function goViews(codeType,id){
	if(codeType == "廉政要闻"){
		top.layer.open({
			type : 2,
			title : "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>查看</span>",
			fix : false,
			// shadeClose: true,
			area : ['80%', '80%'],
			content : basePath + '/dflz/dzyw/goView.do?id=' + id,
		});
	}
	if(codeType == "曝光信息"){
		top.layer.open({
			type : 2,
			title : "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>查看</span>",
			fix : false,
			// shadeClose: true,
			area : ['80%', '80%'],
			content : basePath +'/dflz/bgt/goView.do?id='+id,
		});
		}
	if(codeType == "在线招募"){
		top.layer.open({
		    type: 2,
		    title: "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>查看</span>",
		    fix: false,
		   // shadeClose: true,
		    area: ['80%', '80%'],
		    content: basePath+'/shzz/zxzm/goView.do?id='+id,
		  });
	}
	if(codeType == "热点新闻"){
		top.layer.open({
		    type: 2,
		    title: "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>查看</span>",
		    fix: false,
		   // shadeClose: true,
		    area: ['80%', '80%'],
		    content: basePath+'/xtbg/rdxw/goView.do?id='+id
		  });
	}
	if(codeType == "优秀社会活动"){
		top.layer.open({
		    type: 2,
		    title: "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>查看</span>",
		    fix: false,
		   // shadeClose: true,
		    area: ['80%', '80%'],
		    content: basePath+'/shzz/dtbb/goView.do?id='+id
		  });
	}
}
/**
 * 社区人口统计-begin=========================================================================
 * @returns
 */
function initData(){
	
	/*var msgIndex = layer.msg('数据加载中...', {
		icon : 16,
		shade : [ 0.5, '#999999' ],
		time : 0
	});
*/	$.ajax({
		type:'POST',
		url:basePath+"/home/initSqrk.do",
		dataTypae:'json',
		error :function(request){
			/*layer.close(msgIndex);*/
			layer.msg('程序出错，请联系管理员', {shade : [ 0.5, '#999999' ]});
		},
		success : function(data){
			/*layer.close(msgIndex);*/
			var jsonDate = JSON.parse(data);
			if(jsonDate.code == 200){
				initSqrkBar(jsonDate.result);
			}
		}
	})
}
function initSqrkBar(data){
	// 初始化图表数据
	var myChart = echarts.init(document.getElementById('charts-rksl'));
	window.onresize = myChart.resize;
	var dataX = [];
	var dataValue = [];
	var zdyDate = [];
	for(var i = 0; i<data.length; i++){
		dataX[i] = data[i].sqName;
		dataValue[i] = data[i].sqrkCount;
		zdyDate[i] = data[i].id;
	}
	/*for(var i = 0; i<data.length; i++){
		var colorRandom =  "#"+Math.floor(Math.random()*(256*256*256-1)).toString(16);
		dataX[i] = data[i].sqName;
//		   [ { value:1 , name:'蒲姑社区' , itemStyle:{normal: {color:'#60c0dd'}}} ]
		dataArry[i]= {value: data[i].sqrkCount,name:data[i].sqName,itemStyle:{normal: {color:colorRandom}}};
		dataValue[i] = dataArry[i];
	}*/
	// 指定图表的配置项和数据
	option = {
			  baseOption: {
			        timeline: {
			            // y: 0,
			        	show: false,
			            axisType: 'category',
			            // realtime: false,
			            // loop: false,
			            autoPlay: true,
			            // currentIndex: 2,
			            playInterval: 1000,
			            // controlStyle: {
			            //     position: 'left'
			            // },
			            data: dataX 
			            },
			        
		tooltip: {
			trigger: 'axis',
			axisPointer: { // 坐标轴指示器，坐标轴触发有效
				type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
			}
		},
		grid: {
			top: '10%',
			left: '3%',
			right: '4%',
			bottom: '0',
			containLabel: true,
			y2: 140
		},
		xAxis: [{
			type: 'category',
			data:dataX,
			axisTick: {
				alignWithLabel: true
			},
			axisLabel: {
				interval: 0,
				rotate:5,//改变倾斜度
				fontSize:10 // 改变字体大小
			}
		}],
		yAxis: [{
			type: 'value'
		}],
		series: [{
			name: '人口数量',
			type: 'bar',
			barWidth: '40%',
			barMinHeight:10,
			zdyDate:zdyDate,
			label: {
				normal: {
					fontSize: '10px',
				}
			},
			 itemStyle: {
		            normal: {
		                // 随机显示
		                color:function(d){return "#"+Math.floor(Math.random()*(256*256*256-1)).toString(16);}
		              
		                // 定制显示（按顺序）
//		                color: function(params) {
//		                    var colorList = ['#C33531','#EFE42A','#64BD3D','#EE9201','#29AAE3', '#B74AE5','#0AAF9F','#E89589','#16A085','#4A235A','#C39BD3 ','#F9E79F','#BA4A00','#ECF0F1','#616A6B','#EAF2F8','#4A235A','#3498DB' ]; 
//		                    return colorList[params.dataIndex];
//		                }
		            }
		        },
			data: dataValue
		}]
		}
	
	};
	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption(option);
	myChart.on("click", eConsole);
	function eConsole(param){
		// 跳转到事件列表\  mes += '  dataIndex : ' + param.dataIndex;
//		alert(param.dataIndex);
		//alert(param.value);
		var sqName = zdyDate[param.dataIndex];
		sqrkDetail(sqName);
		//tsrkDetail(sjName);
	//	alert(sjName);
		//alert(sjName);
	}
}
//单社区人口清单表
function sqrkDetail(sqName){
//	var id='';
//	$.ajax({
//		url : basePath + '/home/findsqIdByName.do',
//		type : 'POST',
//		//dataTypae:'json',
//		data : {'sqName':sqName},
//		success : function(data){
//			id=data;
			top.layer.open({
				type : 2,
				title : "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>社区人口列表</span>",
				fix : false,
				// shadeClose: true,
				area : ['80%', '80%'],
				content : basePath + "/home/sqrkList.do?sqName="+sqName,
			})
//		}
//	});
	
	
	/*top.layer.open({
		type : 2,
		title : "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>社区人口列表</span>",
		fix : false,
		// shadeClose: true,
		area : ['80%', '80%'],
		content : basePath + "/home/sqrkList.do?sqName="+sqName,
	})*/
}
/**
 * 事件统计-begin=========================================================================
 * @returns
 */
function initSjtj(){
	/*var msgIndex = layer.msg('数据加载中...', {
		icon : 16,
		shade : [ 0.5, '#999999' ],
		time : 0
	});*/
	
	$.ajax({
		url : basePath + '/home/initSjtj.do',
		type : 'POST',
		dataTypae:'json',
		error :function(request){
		/*	layer.close(msgIndex);*/
			layer.msg('程序出错，请联系管理员', {shade : [ 0.5, '#999999' ]});
		},
		success : function(data){
		/*	layer.close(msgIndex);*/
			var jsonDate = JSON.parse(data);
			if(jsonDate.code == 200){
				initJoinBar(jsonDate.result);
			}
		}
	});
}
/**
 * 初始化饼状图
 * @param data
 * @returns
 */
function initJoinBar(data){
var myChart = echarts.init(document.getElementById('charts-sjtj'));
window.onresize = myChart.resize;
// 指定图表的配置项和数据
var dataX = [];
var dataArry =new Array();
var dataValue = [];
var zdyDate = [];
for(var i = 0; i<data.length; i++){
	var colorRandom =  "#"+Math.floor(Math.random()*(256*256*256-1)).toString(16);
	dataX[i] = data[i].name;
//	   [ { value:1 , name:'蒲姑社区' , itemStyle:{normal: {color:'#60c0dd'}}} ]
	dataArry[i]= {value: data[i].count,name:data[i].name,itemStyle:{normal: {color:colorRandom}}};
	dataValue[i] = dataArry[i];
	zdyDate[i] = data[i].id;
}
option = {
	tooltip: {
		trigger: 'item',
		formatter: "{a} <br/>{b}: {c}",
	},
	legend: {
		show:false,
		orient: 'vertical',
		x: 'left',
		y: 'center',
		data:dataX,
	},
	series: [{
		name: '访问来源',
		type: 'pie',
		radius: ['50%', '70%'],
		avoidLabelOverlap: false,
		center: ['50%', '50%'], // 饼图位置 x, y
		zdyDate:zdyDate,
		label: {
			normal: {
				show: true,
				position: 'outside',
			},
			emphasis: {
				show: true,
				textStyle: {
					fontSize: '14',
					fontWeight: 'normal'
				}
			}
		},
		labelLine: {
			normal: {
				show: true
			}
		},
		data:dataValue
	}]
};
// 使用刚指定的配置项和数据显示图表。
myChart.setOption(option);
myChart.on("click", eConsole);
function eConsole(param){
	var sjMz = zdyDate[param.dataIndex];
	sjtjMores(sjMz);
}
}
function sjtjMores(sjMz){
//	$.ajax({
//		url : basePath + '/home/sjtjJson.do',
//		type : 'POST',
//		dataType : 'json',
//		data : {
//			"sjtjName" : sjMz
//		},
//		success : function(data){
//			if(data.code == 200){
//				var sjtjId = data.sjtjId;
				top.layer.open({
					type : 2,
					title : "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>事件统计列表</span>",
					fix : false,
					// shadeClose: true,
					area : ['80%', '80%'],
					content : basePath + '/home/sjtjList.do?sjtjName='+sjMz,
				});
//			}
//		}
//	});
}
/**
 * 待办事项-begin=========================================================================
 * @returns
 */
function initDbsx(){
	/*var msgIndex = layer.msg('数据加载中...', {
		icon : 16,
		shade : [ 0.5, '#999999' ],
		time : 0
	});*/
	$("#dbsx-init").load(basePath + "/home/initDbsx.do", {
		'ajaxCmd' : 'tableDb',
	}, function() {
		/*layer.close(msgIndex);*/
		if(roleCode.indexOf("02")!=-1 || roleCode.indexOf("03")!=-1 || roleCode.indexOf("04")!=-1 || roleCode.indexOf("05")!=-1 || roleCode.indexOf("06")!=-1 || roleCode.indexOf("07")!=-1 || roleCode.indexOf("08")!=-1 || roleCode.indexOf("09")!=-1 ||roleCode.indexOf("10")!=-1 || roleCode.indexOf("12")!=-1 || roleCode.indexOf("99")!=-1){
			$("#sjLi").show();
		}
		if(roleCode.indexOf("02")!=-1 || roleCode.indexOf("03")!=-1 || roleCode.indexOf("01")!=-1 || roleCode.indexOf("12")!=-1 || roleCode.indexOf("99")!=-1){
			$("#rwLi").show();
		}
	});
}

/**
 * 特殊人口统计-begin=========================================================================
 * @returns
 */
//社区名称 服务站名称
function initTsrk() {
	/*var commId = $("#commId option:selected").val();	//社区ID
	var ssId = $("#ssId option:selected").val();	//服务站ID
*//*	var msgIndex = top.layer.msg('数据加载中', {icon:16, shade:[ 0.5, '#999999' ], time:0});*/

	$.ajax({
		type: "POST",
		url: basePath + "/home/initTsrkBar.do",
		/*data: {'commId': commId,'ssId':ssId},*/
		dataType: 'json',
		error: function(request) {
			layer.msg('程序出错，请联系管理员', {shade : [ 0.5, '#999999' ]});
		},
		success : function(data) {
		/*	top.layer.close(msgIndex);*/
			if(JSON.stringify(data) != "{}" && data.code == 200){
				initTsrkBarZ(data.result);
				//var da=data.result;
				//initBingB(data.result);
				
			}
		}
	});
}
/*******************统计图**********************/	

function initTsrkBarZ(data) {
	var dataX = [];
	var dataValue = [];
	var zdyDate = [];
	for(var i = 0; i<data.length; i++){
		dataX[i] = data[i].name;
		dataValue[i] = data[i].count;
		zdyDate[i] = data[i].id;
	}
// 初始化图表数据
	/*var listJson = data[0].listAllRK;
	var cntListJson = data[0].listAllCnt;*/
	// 基于准备好的dom，初始化echarts实例
	var myChart = echarts.init(document.getElementById('charts-tsrktjBar'));
	window.onresize = myChart.resize;
	
	// 指定图表的配置项和数据
	option = {
			tooltip: {
				trigger: 'axis',
				axisPointer: {
					type: 'shadow'
				}
			},
			grid: {
				top: '8%',
				left: '3%',
				right: '3%',
				bottom: '5%',
				containLabel: true,
			},
			xAxis: {
				axisLine: {
					show: false
				},
				splitLine: {
					show: false
				},
				axisTick:{
					show:false
				},
				type: 'value',
				boundaryGap: [0, 0.01]
			},
			yAxis: {
				axisLine: {
					show: false
				},
				splitLine: {
					show: false
				},
				splitArea: {
					show: true
				}, //保留网格区域
				axisTick:{
					show:false
				},
				type: 'category',
				data: dataX,
			},
			series: [{
				//name: '2017年',
				type: 'bar',
				barWidth: '60%', //柱图宽度
				data: dataValue,
				barMinHeight:10,
				zdyDate:zdyDate,
				itemStyle: {
					normal: {
						color: '#aacf79',	//柱状图颜色
						barBorderRadius: [10, 10, 10, 10],
					}
				},
				label: {
					normal: {
						show: true,
						position: 'right',
					}
				},
			}]
		};
	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption(option);
	myChart.on("click", eConsole);
	function eConsole(param){
		// 跳转到事件列表
		//alert(param.value);
		var tsName = zdyDate[param.dataIndex];
	//	alert(param.data);
		tsrkDetail(tsName);
	//	alert(sjName);
		//alert(sjName);
	}
}

//分类清单表格
function tsrkDetail(tsName){
//	var id='';
//	$.ajax({
//		url : basePath + '/home/findClassifyByName.do',
//		type : 'POST',
//		//dataTypae:'json',
//		data : {'tsName':tsName},
//		success : function(data){
//			id=data;
			top.layer.open({
				type : 2,
				title : "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>特殊人口列表</span>",
				fix : false,
				// shadeClose: true,
				area : ['90%', '90%'],
				content : basePath + "/home/tsrkList.do?tsId="+tsName,
			})
//		}
//	});
}
/**
 * 事件类别统计-begin=========================================================================
 */
function initLbtj() {
	/*var msgIndex = top.layer.msg('数据加载中', {icon:16, shade:[ 0.5, '#999999' ], time:0});*/

	$.ajax({
		type: "POST",
		url: basePath + "/home/initLbtj.do",
		dataType: 'json',
		error: function(request) {
			layer.msg('程序出错，请联系管理员', {shade : [ 0.5, '#999999' ]});
		},
		success : function(data) {
			/*top.layer.close(msgIndex);*/
			if(JSON.stringify(data) != "{}" && data.code == 200){
				initLbtjBar(data.result);
			}
		}
	});
}

function initLbtjBar(data){
	// 初始化图表数据
	var myChart = echarts.init(document.getElementById('charts-lbtj'));
	window.onresize = myChart.resize;
	var dataX = [];
	var dataValue = [];
	var zdyDate = [];
	for(var i = 0; i<data.length; i++){
		dataX[i] = data[i].name;
		dataValue[i] = data[i].count;
		zdyDate[i] = data[i].id;//事件类别ID
	}
	// 指定图表的配置项和数据
	option = {
			  baseOption: {
			        timeline: {
			            // y: 0,
			        	show: false,
			            axisType: 'category',
			            // realtime: false,
			            // loop: false,
			            autoPlay: true,
			            // currentIndex: 2,
			            playInterval: 1000,
			            // controlStyle: {
			            //     position: 'left'
			            // },
			            data: dataX 
			            },
			        
		tooltip: {
			trigger: 'axis',
			axisPointer: { // 坐标轴指示器，坐标轴触发有效
				type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
			}
		},
		grid: {
			top: '10%',
			left: '3%',
			right: '4%',
			bottom: '0',
			containLabel: true,
			y2: 140
		},
		xAxis: [{
			type: 'category',
			data:dataX,
			axisTick: {
				alignWithLabel: true
			},
			axisLabel: {
				interval: 0,
				rotate:5,//改变倾斜度
				fontSize:10 // 改变字体大小
			}
		}],
		yAxis: [{
			type: 'value'
		}],
		series: [{
			name: '事件数量',
			type: 'bar',
			barWidth: '40%',
			barMinHeight:10,
			label: {
				normal: {
					fontSize: '10px',
				}
			},
			 itemStyle: {
		            normal: {
		                // 随机显示
		                color:function(d){return "#"+Math.floor(Math.random()*(256*256*256-1)).toString(16);}
		              
		                // 定制显示（按顺序）
//		                color: function(params) {
//		                    var colorList = ['#C33531','#EFE42A','#64BD3D','#EE9201','#29AAE3', '#B74AE5','#0AAF9F','#E89589','#16A085','#4A235A','#C39BD3 ','#F9E79F','#BA4A00','#ECF0F1','#616A6B','#EAF2F8','#4A235A','#3498DB' ]; 
//		                    return colorList[params.dataIndex];
//		                }
		            }
		        },
			data: dataValue
		}]
		}
	
	};
	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption(option);
	myChart.on("click", eConsole);
	function eConsole(param){
		// 跳转到事件列表
		var sjName = zdyDate[param.dataIndex];
		sjlbMores(sjName);
	}
}
//统计图专用
function sjlbMores(sjName){
//	$.ajax({
//		url : basePath + '/home/sjlbJson.do',
//		type : 'POST',
//		dataType : 'json',
//		data : {
//			"sjlbName" : sjName
//		},
//		success : function(data){
//			if(data.code == 200){
//				var sjlbId = data.sjlbId;
				top.layer.open({
					type : 2,
					title : "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>事件类别</span>",
					fix : false,
					// shadeClose: true,
					area : ['80%', '80%'],
					content : basePath + "/home/sjlbList.do?sjName="+sjName,
				})
//			}
//		}
//	});
}
/**
 * 事件来源统计-begin=========================================================================
 * @returns
 */
function initSjly() {
	var msgIndex = top.layer.msg('数据加载中', {icon:16, shade:[ 0.5, '#999999' ], time:0});

	$.ajax({
		type: "POST",
		url: basePath + "/home/initSjly.do",
		dataType: 'json',
		error: function(request) {
			layer.msg('程序出错，请联系管理员', {shade : [ 0.5, '#999999' ]});
		},
		success : function(data) {
			top.layer.close(msgIndex);
			if(JSON.stringify(data) != "{}" && data.code == 200){
				initSjlyBar(data.result);
			}
		}
	});
}

function initSjlyBar(data){
	var myChart = echarts.init(document.getElementById('charts-sjly'));
	window.onresize = myChart.resize;
	var dataX = [];
	var dataArry =new Array();
	var dataValue = [];
	var	zdyDate = [];

	for(var i = 0; i<data.length; i++){
		var colorRandom =  "#"+Math.floor(Math.random()*(256*256*256-1)).toString(16);
		dataX[i] = data[i].name;
//		   [ { value:1 , name:'蒲姑社区' , itemStyle:{normal: {color:'#60c0dd'}}} ]
		dataArry[i]= {value: data[i].count,name:data[i].name,itemStyle:{normal: {color:colorRandom}}};
		dataValue[i] = dataArry[i];
		zdyDate[i] = data[i].id;
	}
	// 指定图表的配置项和数据
	option = {
		tooltip: {
			trigger: 'item',
			formatter: "{a} <br/>{b}: {c}",
		},
		legend: {
			show:false, 
			orient: 'vertical',
			x: 'left',
			y: 'center',
			data: dataX,
		},
		series: [{
			name: '访问来源',
			type: 'pie',
			radius: ['25%', '70%'],
			avoidLabelOverlap: false,
			center: ['50%', '50%'],
			roseType : 'radius',
			zdyDate:zdyDate,
			label: {
				normal: {
					show: true,
					position: 'outside',
				},
				emphasis: {
					show: true,
					textStyle: {
						fontSize: '14',
						fontWeight: 'normal'
					}
				}
			},
			labelLine: {
				normal: {
					show: true
				}
			},
			data:dataValue
		}]
	};
	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption(option);
	myChart.on("click", eConsole);
	function eConsole(param){
		// 跳转到事件列表
		var sjName = zdyDate[param.dataIndex];
		sjlyMores(sjName);
	}
}
function sjlyMores(sjlyName){
	
//	$.ajax({
//		url : basePath + '/home/sjlyJson.do',
//		type : 'POST',
//		dataType : 'json',
//		data : {
//			"sjlyName" : sjlyName
//		},
//		success : function(data){
//			if(data.code == 200){
//				var sjlyId = data.sjlyId;
				top.layer.open({
					type : 2,
					title : "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>事件来源列表</span>",
					fix : false,
					// shadeClose: true,
					area : ['80%', '80%'],
					content : basePath + '/home/sjlyList.do?sjlyName='+sjlyName,
				});
//			}
//		}
//	});
}
/**
 * 待办跳转
 */
function goDbsx(){
	var _url = basePath + "/secondMain.do?menuCode=402880cf5e0299d9015e02a9e8e40001";
	$("#mainFrame",parent.document).attr('src', _url);
}

function rdMore(){
	top.layer.open({
		type : 2,
		title : "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>新闻列表</span>",
		fix : false,
		// shadeClose: true,
		area : ['80%', '80%'],
		content : basePath + '/home/rdList.do',
	});
}

/**
 * 事件类别--更多
 * @returns
 */
function sjlbMore(all){
	top.layer.open({
		type : 2,
		title : "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>事件类别</span>",
		fix : false,
		// shadeClose: true,
		area : ['80%', '80%'],
		content : basePath + '/home/sjlb.do?all='+all,
	});
}
/**
 * 社区人口更多
 */
function sqrkMore(all){
	
	top.layer.open({
		type : 2,
		title : "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>人口统计</span>",
		fix : false,
		// shadeClose: true,
		area : ['80%', '80%'],
		content : basePath + '/home/sqrktj.do?all='+all,
	});
}

/**
 * 特殊人口更多
 */
function tsrkMore(all){
	
	top.layer.open({
		type : 2,
		title : "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>特殊人口</span>",
		fix : false,
		// shadeClose: true,
		area : ['80%', '80%'],
		content : basePath + '/home/tsrk.do?all='+all,
	});
}

/**
 * 事件统计更多
 */
function sjtjMore(all){
	top.layer.open({
		type : 2,
		title : "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>事件统计</span>",
		fix : false,
		// shadeClose: true,
		area : ['80%', '80%'],
		content : basePath + '/home/sjtj.do?all='+all,
	});
}


 

