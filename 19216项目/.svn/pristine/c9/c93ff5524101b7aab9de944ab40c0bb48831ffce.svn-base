/***
 * 数据中心---组织类型统计
 */

var basePath = $("#basePath").val();

$(function() {
	initChartsB();
	
});

function cxAll(){
	initChartsB();
}


/**
 * 获取饼图数据
 * @returns
 */
function initChartsB() {
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
        url : basePath + '/sjzx/zztj/initChartslx.do',
        data : {
        	 'sqid': commId,
			 'fwid': ssId,
			 'zzxx': zzxx
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
    var myChart = echarts.init(document.getElementById('zzlxtj'));
    window.onresize = myChart.resize;
    // 指定图表的配置项和数据
    option = {
    		 title : {
    		        text: '',
    		        x: 'center'
    		    },
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
    	            	normal: {
			                // 随机显示
			                color:function(d){return "#"+Math.floor(Math.random()*(256*256*256-1)).toString(16);}
			            }
    	            }
    	        }
    	    ]
    	};

       // 使用刚指定的配置项和数据显示图表。
      myChart.setOption(option);
      var  currentIndex=-1;
      setInterval(function () {
          var dataLen = option.series[0].data.length;
          // 取消之前高亮的图形
          myChart.dispatchAction({
              type: 'downplay',
              seriesIndex: 0,
              dataIndex: currentIndex
          });
          currentIndex = ( currentIndex + 1) % dataLen;
          // 高亮当前图形
          myChart.dispatchAction({
              type: 'highlight',
              seriesIndex: 0,
              dataIndex: currentIndex
          });
          // 显示 tooltip
          myChart.dispatchAction({
              type: 'showTip',
              seriesIndex: 0,
              dataIndex: currentIndex
          });
      }, 2000);
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