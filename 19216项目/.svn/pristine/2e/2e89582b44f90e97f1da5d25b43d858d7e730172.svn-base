/***
 * 数据中心---组织数量统计
 */

var basePath = $("#basePath").val();

$(function() {
	initChartsZ();
	
});

function cxAll(){
	 initChartsZ();
}

/**
 * 获取柱状图数据
 * @returns
 */
function initChartsZ() {
	var commId = $("#sqid option:selected").val();	//社区ID
	var ssId = $("#ssId option:selected").val();	   //服务站ID
	var zzxx = $("#zzxz").val();       //组织类型id
    var msgIndex = top.layer.msg('数据加载中...', {
        icon : 16,
        shade : [ 0.5, '#999999' ],
        time : 0
    });
    $.ajax({
        type : "POST",
        url:basePath+"/sjzx/zztj/initCharts.do",
		data: {
			 'sqid': commId,
			 'fwid': ssId,
			 'zzxx': zzxx
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
	var myChart = echarts.init(document.getElementById('zzsltj'));
	window.onresize = myChart.resize;
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
			            data: mapLX 
			            },
					    tooltip : {
					        trigger: 'axis',
					        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
					            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
					        }
					    },
					    grid: {
					        left: '3%',
					        right: '4%',
					        bottom: '3%',
					        containLabel: true
					    },
					    xAxis : [
					        {
					            type : 'category',
					            data : mapLX,
					            axisTick: {
					                alignWithLabel: true
					            }
					        }
					    ],
					    yAxis : [
					        {
					            type : 'value'
					        }
					    ],
					    series : [
					        {
					            name:'直接访问',
					            type:'bar',
					            barWidth: '60%',
					            label: {
									normal: {
										fontSize: '10px',
									}
								},
								 itemStyle: {
							            normal: {
							                // 随机显示
							                color:function(d){return "#"+Math.floor(Math.random()*(256*256*256-1)).toString(16);}
							            }
							        },
					            data:mapSl
					        }
					    ]
			 }	    
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
