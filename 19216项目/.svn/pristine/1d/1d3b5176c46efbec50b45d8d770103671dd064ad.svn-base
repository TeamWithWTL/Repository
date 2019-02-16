 
var basePath;
$(function() {
	basePath = $("#basePath").val();
	var roleCode = $("#roleCodes").val();
	if(roleCode != null){
		$("#toUpdate").show();
	}else{
		$("#toUpdate").hide();
	}
});	

function goView(jmxxcyId){
	mui.openWindow({
	   url: basePath + "/app/shgl/jmxx/goViewBycy.do?id="+jmxxcyId+"&agoId="+$("#jmxxId").val()
	   +"&lyflag="+$("#lyflag").val()
	   +"&lyId="+$("#lyId").val()
	   +"&unitNo="+$("#unitNo").val()
	   +"&roomNo="+$("#roomNo").val(), 
	   id:'goViewBycy'
	});
 }