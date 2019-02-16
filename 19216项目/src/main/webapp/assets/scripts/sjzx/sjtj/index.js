/***
 * 数据中心---事件统计
 */

var basePath = $("#basePath").val();

//初始化日期选择控件
$("#start").datetimepicker({
    format: "yyyy-mm-dd",
    autoclose: true,
    minView: 'month',
    language: 'zh-CN',
    clearBtn: true
}).on("click", function() {
	$("#start").datetimepicker("setEndDate",$("#endTime").val());
});
$("#end").datetimepicker({
    format: "yyyy-mm-dd",
    autoclose: true,
    minView: 'month',
    language: 'zh-CN',
    clearBtn: true
}).on("click",function() {
	 $("#end").datetimepicker("setStartDate",$("#startTime").val()); 
});

$(document).ready(function(){
    initChartsZ();
    initChartsB();
});

function cxAll(){
//	 initDataCx(1);
	 initChartsZ();
	 initChartsB();
}

/**
 * 查询跳转
 * @param pageNumber
 * @param flag
 */
function initDataCx(pageNumber, flag){
	var table = "1";
	var commId = $("#commId option:selected").val();
    var ssId  = $("#ssId option:selected").val();
	var msgIndex = top.layer.msg('数据加载中', {icon:16, shade:[ 0.5, '#999999' ], time:0});
	$("#center").load(basePath + "/sjzx/sjtj/index.do",{'ajaxCmd':table,'startTime':$("#startTime").val(),'endTime':$("#endTime").val(),'commId':commId,'ssId':ssId}, function(){
		top.layer.close(msgIndex);
	});
}


/**
 * 获取柱状图数据
 * @returns
 */
function initChartsZ() {
    var msgIndex = top.layer.msg('数据加载中...', {
        icon : 16,
        shade : [ 0.5, '#999999' ],
        time : 0
    });
    $.ajax({
        type : "POST",
        url : basePath + '/sjzx/sjtj/initCharts.do',
        data : {
            'startTime' : $("#startTime").val(),
            'endTime':$("#endTime").val(),
            'commId':$("#commId option:selected").val(),
            'ssId':$("#ssId option:selected").val()
        },
        dataType: "JSON",
        success : function(mapT) {
        	top.layer.close(msgIndex);
        	var mapLX  = mapT.listAllLX;
        	var mapSl  = mapT.listAllSL;
            initDxZ(mapLX,mapSl); // 初始化事件数量情况
        }
    });
}

/*事件数量情况  */
function initDxZ(mapLX,mapSl) {
    /// 初始化图表数据
	var myChart = echarts.init(document.getElementById('tsrktj'));
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
			data: mapLX,
		},
		series: [{
			name: '数量',
			type: 'bar',
			barWidth: '60%', //柱图宽度
			data: mapSl,
			itemStyle: {
				normal: {
					color: '#aacf79',
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

/**
 * 获取饼图数据
 * @returns
 */
function initChartsB() {
    var msgIndex = top.layer.msg('数据加载中...', {
        icon : 16,
        shade : [ 0.5, '#999999' ],
        time : 0
    });
    $.ajax({
        type : "POST",
        url : basePath + '/sjzx/sjtj/initCharts.do',
        data : {
            'startTime' : $("#startTime").val(),
            'endTime':$("#endTime").val(),
            'commId':$("#commId option:selected").val(),
            'ssId':$("#ssId option:selected").val()
        },
        dataType: "JSON",
        success : function(mapT) {
        	top.layer.close(msgIndex);
        	var mapLX  = mapT.listAllLX;
        	var mapSl  = mapT.listAllSL;
        	var dataArry =new Array();
        	var cntList = [];
        	for(var i = 0 ; i<mapT.listAllLX.length; i++){
        		dataArry[i]= {value: mapT.listAllSL[i],name:mapT.listAllLX[i]};
        		cntList[i] = dataArry[i];
        	}
        	initDxB(mapLX,cntList); // 初始化事件数量情况
        }
    });
}



/* 事件占比情况 */
function initDxB(mapLX,cntList) {
    // 初始化图表数据
    var myChart = echarts.init(document.getElementById('tsrktjbt'));
    window.onresize = myChart.resize;
    // 指定图表的配置项和数据
    option = {
    	    tooltip : {
    	        trigger: 'item',
    	        formatter: "{a} <br/>{b} : {c} ({d}%)"
    	    },
    	    legend: {
    	        orient: 'vertical',
    	        left: 'left',
    	        data: mapLX,
    	    },
    	    series : [
    	        {
    	            name: '访问来源',
    	            type: 'pie',
    	            radius : '55%',
    	            center: ['50%', '60%'],
    	            data:cntList,
    	            itemStyle: {
    	                emphasis: {
    	                    shadowBlur: 10,
    	                    shadowOffsetX: 0,
    	                    shadowColor: 'rgba(0, 0, 0, 0.5)'
    	                }
    	            }
    	        }
    	    ]
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
