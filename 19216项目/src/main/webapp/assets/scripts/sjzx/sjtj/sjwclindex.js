/***
 * 数据中心---事件完成率统计
 */

var basePath = $("#basePath").val();

$(function() {
	initSjwcl();
	
});

function cxAll(){
	initSjwcl();
}


/**
 * 获取仪表图数据
 * @returns
 */
function initSjwcl() {
	var msgIndex = top.layer.msg('数据加载中', {icon:16, shade:[ 0.5, '#999999' ], time:0});
	//数据中心--事件完成率统计-查询条件，社区id，服务站id(2017.12.4-xushu)
	var sqid = $("#sqid option:selected").val();	//社区ID
	var fwid = $("#ssId option:selected").val();	   //服务站ID
	
	$.ajax({
		type: "POST",
		url: basePath + "/sjzx/sjtj/initChartswcl.do",
		data : {
			"commId" : sqid,
			"ssId" : fwid
		},
		dataType: "JSON",
        success : function(mapT) {
			top.layer.close(msgIndex);
			var wcl = mapT.wcl;
			initSjlyBar(wcl);
		}
	});
}

function initSjlyBar(wcl){
	var myChart = echarts.init(document.getElementById('charts-sjwcl'));
	window.onresize = myChart.resize;
	option = {
		    tooltip : {
		        formatter: "{a} <br/>{b} : {c}%"
		    },
		    toolbox: {
		        feature: {
		        }
		    },
		    series: [
		        {
		            name: '事件完成率',
		            type: 'gauge',
		            detail: {formatter:'{value}%'},
		            data: [{value: wcl, name: '完成率'}]
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