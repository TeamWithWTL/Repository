/**
 * 数据中心--特殊人口统计
 */
var basePath = $("#basePath").val();
$(function() {
	initData();
	
});
//社区名称 服务站名称
function initData() {
	var commId = $("#commId option:selected").val();	//社区ID
	var ssId = $("#ssId option:selected").val();	//服务站ID
	var msgIndex = top.layer.msg('数据加载中', {icon:16, shade:[ 0.5, '#999999' ], time:0});

	$.ajax({
		type: "POST",
		url: basePath + "/sjzx/tsrk/initTsrkBar.do",
		data: {'commId': commId,'ssId':ssId},
		dataType: 'json',
		error: function(request) {
			layer.msg('程序出错，请联系管理员', {shade : [ 0.5, '#999999' ]});
		},
		success : function(data) {
			top.layer.close(msgIndex);
			if(JSON.stringify(data) != "{}" && data.code == 200){
				initTsrkBarZ(data.result);
				initBingB(data.result);
			}
		}
	});
}
/*******************统计图**********************/	

function initTsrkBarZ(data) {
// 初始化图表数据
	var dataX = [];
	var dataValue = [];
	for(var i = 0; i<data.length; i++){
		dataX[i] = data[i].name;
		dataValue[i] = data[i].count;
	}
	
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
				data: dataX,//listJson,
			},
			series: [{
				//name: '2017年',
				type: 'bar',
				barWidth: '60%', //柱图宽度
				data: dataValue,//cntListJson,
				barMinHeight:10,
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
}
	

/******************特殊人口统计圆形图*******************/
function initBingB(data) {
	// 初始化图表数据
//		var listJson = data[0].listAllRK;//人口类别
//		var cntListJson = data[0].listAllCnt;	//人口数量

	var myChart = echarts.init(document.getElementById('charts-tsrktj'));
	window.onresize = myChart.resize;
	$("#charts-tsrktj").css( 'width', $("#charts-tsrktj").width() );
	var listJson = [];
	var dataArry =new Array();
	var cntListJson = [];
	for(var i = 0 ; i<data.length; i++){
		
		var colorRandom =  "#"+Math.floor(Math.random()*(256*256*256-1)).toString(16);
		listJson[i] = data[i].name;//人口分类名称

		dataArry[i]= {value: data[i].count,name:data[i].name,itemStyle:{normal: {color:colorRandom}}};
		cntListJson[i] = dataArry[i];
	}

	// 指定图表的配置项和数据
	option = {
		tooltip: {
			trigger: 'item',
			formatter: "{a} <br/>{b}: {c} ({d}%)",
		},
		legend: {
			orient: 'vertical',
			x: 'left',
			y: 'right',//left,
			data:listJson,
		},
		series: [{
			name: '访问来源',
			type: 'pie',
//			radius : '65%',
			radius: ['50%', '70%'],
			avoidLabelOverlap: false,
			center: ['55%', '50%'],
			 minAngle: 15,     // 最小角度
			label: {
				normal: {
					show: true,
					position: 'outside', //inner
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
			/*itemStyle:{ //追加数据
                normal:{ 
                    label:{ 
                       show: true, 
                       formatter: '{b} : {c} ({d}%)' 
                    }, 
                    labelLine :{show:true}
                } 
            } ,*/
			data:cntListJson,
		}]
	};
	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption(option);
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










