/***
 * 数据中心---事件来源统计
 */

var basePath = $("#basePath").val();

$(function() {
	initSjly();
	
});

function cxAll(){
	initSjly();
}


/**
 * 获取饼图数据
 * @returns
 */
function initSjly() {
	var msgIndex = top.layer.msg('数据加载中', {icon:16, shade:[ 0.5, '#999999' ], time:0});
	//数据中心--事件来源统计-查询条件，社区id，服务站id(2017.12.4-xushu)
	var sqid = $("#sqid option:selected").val();	//社区ID
	var fwid = $("#ssId option:selected").val();	   //服务站ID
	
	$.ajax({
		type: "POST",
		url: basePath + "/home/initSjly.do",
		data : {
			"sqid" : sqid,
			"fwzid" : fwid
		},
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

	for(var i = 0; i<data.length; i++){
		var colorRandom =  "#"+Math.floor(Math.random()*(256*256*256-1)).toString(16);
		dataX[i] = data[i].name;
//		   [ { value:1 , name:'蒲姑社区' , itemStyle:{normal: {color:'#60c0dd'}}} ]
		dataArry[i]= {value: data[i].count,name:data[i].name,itemStyle:{normal: {color:colorRandom}}};
		dataValue[i] = dataArry[i];
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
		var sjName = param.name;
		sjlyMores(sjName);
	}
var   currentIndex=-1;
	setInterval(function () {
	    var dataLen = option.series[0].data.length;
	    // 取消之前高亮的图形
	    myChart.dispatchAction({
	        type: 'downplay',
	        seriesIndex: 0,
	        dataIndex:  currentIndex
	    });
	     currentIndex = ( currentIndex + 1) % dataLen;
	    // 高亮当前图形
	    myChart.dispatchAction({
	        type: 'highlight',
	        seriesIndex: 0,
	        dataIndex:  currentIndex
	    });
	    // 显示 tooltip
	    myChart.dispatchAction({
	        type: 'showTip',
	        seriesIndex: 0,
	        dataIndex:  currentIndex
	    });
	}, 1000);
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