/**
 * 数据中心---人口数量
 */
var basePath = $("#basePath").val();
$(function() {
	initData();
	
});
//社区名称 服务站名称
function initData(){
	var commId = $("#commId option:selected").val();	//社区ID
	var ssId = $("#ssId option:selected").val();	//服务站ID
	var msgIndex = layer.msg('数据加载中...', {
		icon : 16,
		shade : [ 0.5, '#999999' ],
		time : 0
	});
	$.ajax({
		type:'POST',
		url:basePath+"/sjzx/rksl/initSqrk.do",
		data: {'commId': commId,'ssId':ssId},
		dataTypae:'json',
		error :function(request){
			layer.close(msgIndex);
			layer.msg('程序出错，请联系管理员', {shade : [ 0.5, '#999999' ]});
		},
		success : function(data){
			layer.close(msgIndex);
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
	for(var i = 0; i<data.length; i++){
		dataX[i] = data[i].sqName;
		dataValue[i] = data[i].sqrkCount;
	}
	// 指定图表的配置项和数据
	option = {
			  baseOption: {
			        timeline: {
			            // y: 0,
			            axisType: 'category',
			            // realtime: false,
			            show: false,
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
			bottom: '10%',
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
}


