
var basePath = $("#basePath").val();

//跳转--详情
mui("#table-container").on('tap','.mui-card',function(){
	 var jmxxId = this.getAttribute("name");
	 mui.openWindow({
	    url: basePath + "/app/shgl/jmxx/goView.do?id="+jmxxId+"&lyflag=3"
	    +"&lyId="+$("#lyId").val()
	    +"&unitNo="+$("#unit_no").val()
	    +"&roomNo="+$("#room_no").val(), 
	    id:'goView'
	});
});