var basePath = $("#basePath").val();
	// 附件下载
	function download(newFileName, oldFileName){
		window.location.href = basePath + '/pub/downloadbystream.do?fileName=' + newFileName + '&oldFileName=' + oldFileName;
	}
	//选择地图范围
	function choseSjArea(url,id){
		$(".btn").blur();
		top.layer.open({
		    type: 2,
		    title: "<div class='layerTitleDiv'></div><span class='layerTitleSpan'>事件位置</span>",
		    fix: false,
		   // shadeClose: true,
		    area: ['60%', '60%'],
		    content: basePath + url+'?id='+id,
		    end:function(){}
		  });
	}
