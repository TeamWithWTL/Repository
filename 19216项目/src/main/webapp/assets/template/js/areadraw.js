var _obj;
var _id = 0;
//初始化地图
var initmap = function () {
    if (typeof MecMap == "undefined") {
        return false;
    }

    //初始化地图
    MecMap.initMap("mapDiv");
    // MecMap.map.zoomTo(13);
    MecMap.map.setCenter(MecMap.map.getCenter(), 13); // 地图级别调成13, MecMap中默认19
    
    MecMap.initDrawMidControls();
    MecMeasure.initMeasureControls();
    MecMap.switchVecLayer();
    //注销编辑事件
    MecMap.selectMicFeatureControl.deactivate();

    //MecMap.addMoveLisenter(resetPopup);
    //鼠标点击事件
    DrawingManager.addEventListener("mousedown", clearMap);

    DrawingManager.add("mapDiv", []);
    //标注
    MecMap.registOnAddMidFeature(afterdraw);
    
}

initmap();

//无用
var loadMapByList = function (url) {
    if (!url) {
        return false;
    }

    $.getJSON(url, function (result) {
        var data = result;
        $.each(data.Data, function (index, item) {
            if (id == item.ID) {//判断当前标注是否存在
                MecMap.setMapCenter(lon, lat, 14);
                MecMap.addMidMarkerToLayer(lon, lat, 'img/station_2.png', { id: id, name: item.Name, label: item.Name });
            } else {
                DrawMarks(item);
            }
        });
    });
}
//无用
var loadMap = function (id, name, lon, lat) {
    if (!lon || !lat) {
        return false;
    }

    MecMap.setMapCenter(lon, lat, 14);
    MecMap.addMidMarkerToLayer(lon, lat, 'img/station_2.png', { id: id, name: name, label: name });
}

var deaDraw = function () {
    //注销绘面事件
    MecMap.drawControls["polygon"].deactivate();
    //注销绘点事件
    MecMap.drawControls['marker'].deactivate();
}

//绘点
var drawPoint = function () {
    deaDraw();
    MecMap.toggleControl('marker');
}
//绘面
var drawArea = function () {
    deaDraw();
    MecMap.toggleControl('polygon');
}

var typeid = $("#typeid").val();
//绘制点
function afterdraw(e) {
    var lonlat = MecMap.getCenterPolygonFeature(e.feature);
    var type = 'point';
    var _name = name;
    if (e.feature.geometry.components != undefined) {
        _name = '';
        type = 'area';
    }
   
    if (type == 'point') {
        var mark = selFeature();
        if (mark != null) {
            MecMap.removeMidAllMark();//删除原有标注
        }
        if(typeid == "8"){
        	 MecMap.addMidMarkerToLayer(lonlat.lon, lonlat.lat, 'img/triangle.png', { id: _id, label: _name });
        }else if(typeid == "9"){
        	MecMap.addMidMarkerToLayer(lonlat.lon, lonlat.lat, 'img/star.png', { id: _id, label: _name });
        }else{
        	 MecMap.addMidMarkerToLayer(lonlat.lon, lonlat.lat, 'img/station_2.png', { id: _id, label: _name });
        }
        $("#hflon").val(lonlat.lon.toFixed(6));
        $("#hflat").val(lonlat.lat.toFixed(6));
        $("#txtpoint").val(lonlat.lon.toFixed(6) + ',' + lonlat.lat.toFixed(6));
    } else {
        MecMap.removeMidFeature(MecMap.selectFea);

        e.feature.attributes = { "IsRange": true, "favColor": "red", "fillColor": "white", "fillOpacity": 0, label: _name };
        e.feature.mec_date = { label: _name, id: _id, type: type, typeId:$("#typeId").val() };
        _obj = e.feature;
        MecMap.selectFea = _obj;

        DrawingManager.reset();
		//获取坐标
		getzb();
    }
}

//绘制标记点
function DrawMarks(obj) {
//    var png = 'img/station_3.png';
//
//    var style = {
//        width: 22,
//        height: 22,
//        offsetw: 0,
//        offsety: 0,
//        labelXOffset: 0,
//        labelYOffset: -12
//    }
//
//    //添加要素
//    if (data.Lon && data.Lat) {
//        MecMap.addMarkerToLayerByCustom(data.Lon, data.Lat, png, style, { "id": data.ID, "name": data.Name, Code: data.Code, "lonlat": { "lon": data.Lon, "lat": data.Lat }, url: png, "popdata": data }, null);
//    }
	var typestr = "building";
	var imgUrl = "img/station_2.png";
	if(obj.typeId == "5"){
		typestr = "event";
	}else if(obj.typeId == "6"){
		typestr = "government";
	}else if(obj.typeId == "7"){
		console.log("obj.tsrktype:"+obj.tsrktype);
		typestr = "inmate";
		imgUrl = "img/tsrk_" + obj.tsrktype + ".png";
		if(obj.tsrktype=="name" ||obj.tsrktype==""||obj.tsrktype==undefined){
			imgUrl = "img/station_2.png";
		}
	}else if(obj.typeId == "8"){
		typestr = "sqfwz";
		imgUrl = "img/" + obj.itemName + ".png";
	}else if(obj.typeId == "9"){
		typestr = "sq";
		imgUrl = "img/" + obj.itemName + ".png";
	}
	if(obj.Lon && obj.Lat){
		MecMap.addMidMarkerToLayer(obj.Lon, obj.Lat, imgUrl, { id: obj.ID, name: obj.Name, label: obj.Name,typeId:obj.typeId });
	    //console.log(" call addMarkerToLayer1");
		if(typestr == "building" || typestr == "event" || typestr == "government"){
			MecMap.addMarkerToLayer1(obj.Lon, obj.Lat, "", { "id": obj.ID, "name": obj.Name, "lonlat": { "lon": obj.Lon, "lat": obj.Lat }, "typestr": typestr, "popdata": obj }, showpopup);
		}
	}
}

//绘制多边形
function addPolygon(_type, areas, name, _id, favColor, _color, _typeId, canShow, _cName, _sName, _perNumb, _manager, _tel ) {
    var data = { label: name, type: 'area', id: _id, typeId: _typeId };
    var _style = {
        "IsRange": true,
        "favColor": _color,
        "fillColor": favColor,
        "fillOpacity": 0.3,
        "label": name,
    };

    var zbList = new Array();
    var zbs = areas.split(';');
    $.each(zbs, function (i, item) {
        if (item == "") {
            return;
        }

        var zb = item.split(',');
        if (zb[0] != "" && zb[1] != "") {
            zbList.push(new OpenLayers.Geometry.Point(zb[0], zb[1]));
        }
    });
    var obj;
    if (_type == 0) {
        obj = MecMap.addPolygonFeature(zbList, _style, data);
    } else {
        obj = MecMap.addMidPolygonFeature(zbList, _style, data);
    }
  //社区添加点击事件
    if (_typeId == "1" && !canShow) {
        //Marker
    	var cal = calculateCenter(areas);
    	var areaobj = new Object();
    	areaobj.ID = _id;
    	areaobj.Name = name;
    	areaobj.typeId = _typeId;
    	areaobj.fwzCount = _cName;
    	areaobj.wgCount = _sName;
    	areaobj.jmCount = _perNumb;
    	areaobj.sqmanager = _manager;
    	areaobj.sqtel = _tel;
    	console.log("wg  addMarkerToLayer1");
        MecMap.addMarkerToLayer1(cal.lon, cal.lat, "", { "id": areaobj.ID, "name": areaobj.Name, "lonlat": { "lon": cal.lon, "lat": cal.lat }, "typestr": "sq", "popdata": areaobj }, showpopup);
    }
  //服务站添加点击事件
    if (_typeId == "2" && !canShow) {
        //Marker
    	var cal = calculateCenter(areas);
    	var areaobj = new Object();
    	areaobj.ID = _id;
    	areaobj.Name = name;
    	areaobj.typeId = _typeId;
    	areaobj.wgCount = _sName;
    	areaobj.jmCount = _perNumb;
    	areaobj.fzrname = _manager;
    	areaobj.telephone = _tel;
    	console.log("wg  addMarkerToLayer1");
        MecMap.addMarkerToLayer1(cal.lon, cal.lat, "", { "id": areaobj.ID, "name": areaobj.Name, "lonlat": { "lon": cal.lon, "lat": cal.lat }, "typestr": "sqfwz", "popdata": areaobj }, showpopup);
    }
    //网格添加点击事件
    if (_typeId == "3" && !canShow) {
        //Marker
    	var cal = calculateCenter(areas);
    	var areaobj = new Object();
    	areaobj.ID = _id;
    	areaobj.Name = name;
    	areaobj.typeId = _typeId;
    	areaobj.cName = _cName;
    	areaobj.sName = _sName;
    	areaobj.perNumb = _perNumb;
    	console.log("wg  addMarkerToLayer1");
        MecMap.addMarkerToLayer1(cal.lon, cal.lat, "", { "id": areaobj.ID, "name": areaobj.Name, "lonlat": { "lon": cal.lon, "lat": cal.lat }, "typestr": "area", "popdata": areaobj }, showpopup);
    }
    return obj;
}

//线段绘制多边形-无用
function DrawLines(range, _id, _color) {
    var _style = {
        IsRange: true,
        favColor: _color,
        strokeWidth: 1,
        label: ''
    };

    var data = { id: _id };
    var zbList = new Array();
    var zbs = range.split(';');
    $.each(zbs, function (i, item) {
        if (item == "") {
            return;
        }

        var zb = zbs[i].split(',');
        if (zb[0] != "" && zb[1] != "") {
            zbList.push(new OpenLayers.Geometry.Point(zb[0], zb[1]));
        }
    });

    //添加开始坐标形成回环
    zbList.push(zbList[0]);

    return MecMap.addLineFeature(zbList, _style, data, null);
}

//根据条件查找要素
var selFeature = function () {
    var marks = MecMap.markMidLayers.markers;
    for (var i = marks.length; i >= 0; i--) {
        var itemMark = marks[i];
        if (itemMark != undefined && itemMark.mec_date != undefined) {
            if (itemMark.mec_date.id == _id) {
                return itemMark;
            }
        }
    }
    return null;
}
//删除
function clearMap() {
    MecMap.endModify();
    MecMap.removeFeature(MecMap.selectFea);
    MecMap.removeMidFeature(MecMap.selectFea);

    MecMap.selectFea = null;
    _obj = null;
    deaDraw();
    MecMap.removeMidAllMark();
    MecMap.removeMidFeature();
    $("#txtpoint").val('');
}

//获取坐标
function getzb() {
    MecMap.endModify();
    MecMap.mecMidLayers.styleMap.styles.default = defaltStyle;
    var result = '';
    var features = MecMap.mecMidLayers.features;
    $.each(features, function (index, item) {
        if (item.mec_date != undefined)
            if (item.mec_date.id == id && item.mec_date.type == 'area') {
                _obj = item;
            }
    });
    if (_obj != null) {
        var rang = _obj;
        var zbs = MecMap.getFeatureVertices(_obj);
        if (zbs == undefined || zbs.length < 2) {
            _obj = MecMap.selectFeatureControl.layers[0].selectedFeatures[0];
            zbs = MecMap.getFeatureVertices(_obj);
        }
        for (var i = 0; i < zbs.length; i++) {
            result += zbs[i].x + "," + zbs[i].y + ";";
        }
        $("#txtpoint").val(result);

        return true;
    }
    return false;
}

var id = $("#id").val();
function modify() {
    MecMap.selectFeatureControl.deactivate();
    var features = MecMap.mecMidLayers.features;
    $.each(features, function (index, item) {
        if (item.mec_date.id == id && item.mec_date.type == 'area') {
            MecMap.selectFea = item;
        }
    });
    //设置地图要素显示的样式
    MecMap.mecMidLayers.styleMap.styles.default = modifyStyle;
    MecMap.beginModify();
}

var moveToSearch = function (lon, lat) {
    //alert(lon + ',' + lat);
    MecMap.setMapCenter(lon, lat, 17);
}

//#region 地名搜索

$(function () {
    $("#ddlmaptype").change(function () {
        var _type = $(this).val();

        switch (_type) {
            case '1':
                MecMap.switchVecLayer();
                break;
            case '2':
                MecMap.switchImgLayer();
                break;
            case '3':
                MecMap.switchVecImgLayer();
                break;
            default:
                break;
        }
    });
    //无用 bein
    $('#search-button').click(function () {
        searchKey();
    });

    $('#sole-input').change(function () {
        searchKey();
    });

    $('#sole-input').keyup(function () {
        searchKey();
    });

    $('#btnclear').click(function () {
        $('#sole-input').val('');
        $('#resultbox').html('');
        $('#resultbox').slideUp();
    });

    //地点搜索
    var searchKey = function () {
        var _key = $('#sole-input').val();
        if (_key == undefined || _key == null || _key == '') {
            $('#btnclear').hide();
            $('#resultbox').slideUp();
            return false;
        }

        $('#btnclear').show();
        $.ajax({
            type: 'get',
            url: 'http://124.128.48.210:9001/service/SERVICE?st=LocalSearch&city=滨州&words=' + _key,
            dataType: 'jsonp',
            jsonpCallback: "success_jsonpCallback",
            success: function (data) {
                if (data.status != 'ok') {
                    return false;
                }

                $('#resultbox').html('');
                var _html = '';
                $.each(data.result.features, function (index, item) {
                    if (index > 9) {
                        return false;
                    }
                    _html += '<li><a herf="javascript:void(0);" data-lon="' + item.lng + '" data-lat="' + item.lat + '"><i class="fa fa-map-marker">&nbsp;&nbsp;' + item.name + '</i><em>' + item.district_text.replace(new RegExp('>', 'gm'), ''); + '</em></a></li>';
                });

                $('#resultbox').html(_html);
                $('#resultbox').slideDown();
            }
        });
    }

    $('#resultbox').on('click', 'a', function () {
        moveToSearch($(this).attr('data-lon'), $(this).attr('data-lat'));
    });

    //搜索
    $(document).keyup(function (event) {
        if (event.keyCode == 13) {
            searchKey();
        }
    });
    //无用end
})

//根据地址获取坐标-无用
var getLonLat = function (_key) {
    $.ajax({
        type: 'get',
        url: 'http://124.128.48.210:9001/service/SERVICE?st=LocalSearch&city=潍坊&words=' + _key,
        dataType: 'jsonp',
        jsonpCallback: "success_jsonpCallback",
        success: function (data) {
            if (data.status != 'ok') {
                return false;
            }

            var _html = '';
            $.each(data.result.features, function (index, item) {
                if (index > 9) {
                    return false;
                }

                $("#txtpoint").val(item.lng + ',' + item.lat);
            });
        }
    });
}
//#endregion

//#region 工具函数

//随机颜色
function randomColor() {
    var rand = Math.floor(Math.random() * 0xFFFFFF).toString(16);
    if (rand.length == 6) {
        return rand;
    } else {
        return randomColor();
    }
}

/**计算中心点
 * @param rangestr
 * @returns {OpenLayers.LonLat}
 */
function calculateCenter(rangestr) {
    var range = rangestr.split(';');

    var total = range.length;
    var X = 0, Y = 0, Z = 0;
    $.each(range, function (index, lonlat) {
        if (lonlat != '') {
            var ll = lonlat.split(',');
            var lon = ll[0] * Math.PI / 180;
            var lat = ll[1] * Math.PI / 180;
            var x, y, z;
            x = Math.cos(lat) * Math.cos(lon);
            y = Math.cos(lat) * Math.sin(lon);
            z = Math.sin(lat);
            X += x;
            Y += y;
            Z += z;
        }
    });

    X = X / total;
    Y = Y / total;
    Z = Z / total;

    var Lon = Math.atan2(Y, X);
    var Hyp = Math.sqrt(X * X + Y * Y);
    var Lat = Math.atan2(Z, Hyp);

    return new OpenLayers.LonLat(Lon * 180 / Math.PI, Lat * 180 / Math.PI);
};
//标记点击事件
//id:id ;name:名称；lon/lat:坐标
function clickName(id, name, lon, lat) {
  //弹窗
  closePoPup();
  var marks = MecMap.markLayers.markers;
  $.each(marks, function (index, itemMark) {
      if (itemMark.mec_date.id == id && itemMark.mec_date.name == name) {
          MecMap.setMapCenter(lon, lat, 19);
          showpopup({ "object": itemMark });
          return false;
      }
  });
}

//地图弹窗
function showpopup(feature) {
	var canshow = false;
	var typestr = feature.object.mec_date.typestr;
	var _zoom = MecMap.map.getZoom();
//	if(_zoom >= 17){
//		canshow = true;
//	}
	if (!canshow && _zoom > 16) {
		if(typestr != null && typestr == "area"){
			canshow = true;
		}
	}
	if (!canshow && _zoom >= 13) {
		if(typestr != null && typestr == "sq"){
			canshow = true;
		}
	}
	if (!canshow && _zoom > 15 && _zoom <= 16) {
		if(typestr != null && typestr == "sqfwz"){
			canshow = true;
		}
	}
	if(typestr == "building" || typestr == "event" || typestr == "government"){
		canshow = true;
	}
//	if(typestr =="sq"|| typestr=="sqfwz"){
//		if(_zoom >= 13&&_zoom<17){
//			canshow = true;
//		}
//	}
	console.log("_zoom:"+_zoom);
    if(canshow){
	  if (MECPopup.newdiv && MECPopup.newdiv.feature == feature.object) {
		  
	  } else {
	      MECPopup.closepopup();
	
	      if (feature.object.mec_label != undefined && feature.object.mec_label.style != null) {
	          return;
	      }
//	      console.log("feature.object.lonlat:"+feature.object.lonlat);
	      var view_xy = MecMap.map.getViewPortPxFromLonLat(feature.object.lonlat);
	      var x = view_xy.x;
	      var y = view_xy.y;
	      if (feature.object.icon) {
	          y = y + feature.object.icon.offset.y;
	      }
	      x = Math.round(x);
	      y = Math.round(y);
	      MecMap.popup = MECPopup.createpopup("rcp1_map", 300, 230, x, y);
	      MecMap.popup.setTitle(feature.object.mec_date.name);
//	      console.log("feature.object.mec_date.name:"+feature.object.mec_date.name);
	      if (feature.object.mec_date.popuphtml != undefined && feature.object.mec_date.popuphtml != '') {
	          MecMap.popup.setContent(feature.object.mec_date.popuphtml);
//	          console.log("feature.object.mec_date.popuphtml:"+feature.object.mec_date.popuphtml);
	      }
	      else {
	          //alert(feature.object.mec_date.typestr);
	          var gettpl = $('#dinfo-' + feature.object.mec_date.typestr).html();
	          console.log("feature.object.mec_date.typestr:"+feature.object.mec_date.typestr);
//	          console.log("gettpl:"+gettpl);
	          laytpl(gettpl).render(feature.object.mec_date.popdata, function (html) {
	              MecMap.popup.setContent(html);
	          })
	      }
    	  MecMap.popup.showpopup(feature.object);
    	  MecMap.addMoveLisenter(resetPopup);
      }
	  var screen_width = document.documentElement.clientWidth;
	  var screen_height = document.documentElement.clientHeight;
	  if (MecMap.popup.top < 0 || MecMap.popup.left < 0 || (MecMap.popup.top + MecMap.popup.height) > screen_height || (MecMap.popup.left + MecMap.popup.width) > screen_width) {
	      //MecMap.map.panTo()
	      var lon = screen_width / 2;
	      var lat = screen_height / 2;
	      if (MecMap.popup.left < 0) {
	          lon = lon + MecMap.popup.left - 20;
	      } else if ((MecMap.popup.left + MecMap.popup.width) > screen_width) {
	          lon = lon + MecMap.popup.left + MecMap.popup.width - screen_width + 20;
	      }
	      if (MecMap.popup.top < 0) {
	          lat = lat + MecMap.popup.top - 20;
	      } else if ((MecMap.popup.top + MecMap.popup.height) > screen_height) {
	          lat = lat + MecMap.popup.top + MecMap.popup.height - screen_height + 20;
	      }
	      MecMap.map.panTo(MecMap.map.getLonLatFromViewPortPx(new OpenLayers.Pixel(lon, lat)));
	  }
   }
}

//重置弹窗
function resetPopup() {
  MECPopup.reSetLocation();
}

//关闭弹窗
function closePoPup() {
  if (MecMap.popup != null && MecMap.popup != undefined) {
      //关 闭弹窗
      MecMap.popup.closepopup();
  }
}
function clearMarks() {
    var marks = MecMap.markLayers.markers;
    for (var i = marks.length; i > 0; i--) {
        var itemMark = marks[i];
        if (itemMark != undefined && itemMark.mec_date != undefined) {
            if (itemMark.mec_date.typestr == undefined || itemMark.mec_date.typestr != "area") {
                MecMap.markLayers.removeMarker(itemMark);
            }
        }
    }
    closePoPup();
}
//查看探出详情
function showInfo(id, typeid) {
    var _url;
    var _title;
    var _width = '90%';
    var _height = '90%';
    var _scroll = 'yes';
    var basePath = $('#basePath', window.parent.document).val();
    switch (typeid) {
        case "4":
            _url = basePath + '/wgdt/goView.do';
            _title = '楼宇信息';
            break;
        case "5":
            _url = basePath + '/wgdt/goViewEvent.do';
            _title = '事件信息';
            break;
        case "7":
            _url = basePath + '/shgl/jmxx/goView.do';
            _title = '居民信息详情';
            break;
        case "8":
            _url = basePath + '/shgl/fwzgl/gofzrView.do';
            _title = '服务站负责人详情';
            break;
        default:
            break;
    }
    var _url = _url + '?id=' + id;
    top.layer.open({
        type: 2,
        area: [_width, _height],
        maxmin: true,
        title: _title,
        content: [_url, _scroll]
    });
}
//#endregion

function changeMap(mapCtl){
	switch (mapCtl) {
	    case 'map1':
	        MecMap.switchVecLayer();
	        break;
	    case 'map2':
	        MecMap.switchImgLayer();
	        break;
	    case 'map3':
	        MecMap.switchVecImgLayer();
	        break;
	    default:
	        break;
	}
	$(event.target).addClass("active").siblings().removeClass("active");
}