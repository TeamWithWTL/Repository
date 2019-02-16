OpenLayers.INCHES_PER_UNIT["千米"] = OpenLayers.INCHES_PER_UNIT["km"];
OpenLayers.INCHES_PER_UNIT["米"] = OpenLayers.INCHES_PER_UNIT["m"];
OpenLayers.INCHES_PER_UNIT["英里"] = OpenLayers.INCHES_PER_UNIT["mi"];
OpenLayers.INCHES_PER_UNIT["英寸"] = OpenLayers.INCHES_PER_UNIT["ft"];

//地图要素显示样式，可显示lable
var defaltStyle = new OpenLayers.Style({
    strokeColor: "${favColor}",
    strokeOpacity: 1,
    strokeWidth: "${strokeWidth}",
    fillColor: "${fillColor}",
    fillOpacity: "${fillOpacity}",
    cursor: "pointer",
    label: "${label}",
    fontColor: "red",
    fontSize: "12px",
    fontFamily: "Courier New, monospace",
    fontWeight: "bold",
    labelAlign: "${labelAlign}",
    labelXOffset: "${labelXOffset}",
    labelYOffset: "${labelYOffset}",
    labelOutlineColor: "white",
    labelOutlineWidth: 3,
    labelBackgroundColor: "white",
    labelBorderColor: "#D42136",
    labelBorderSize: "0.3px"
});

var modifyStyle = new OpenLayers.Style({
    strokeColor: "${favColor}",
    strokeOpacity: 1,
    strokeWidth: "${strokeWidth}",
    fillColor: "${fillColor}",
    fillOpacity: "${fillOpacity}",
    cursor: "pointer"
});

var MecMap = {
    map: null,
    markers: null,
    popup: null,
    centerLon: 118.12223099821,
    centerLat: 37.153542191932,
    centerZoom: 15,
    strokeWidth: 2,
    fillColor: "#fff",
    fillOpacity: 0,
    tog: "",
    SDPubMap: null,
    SdRasterPubMap: null,
    sdrasterpubmapdj: null,
    selectMicFeatureControl: null,//普通
    selectFeatureControl: null,//微调
    modifyFeatureControl: null,//微调
    mecLayers: new OpenLayers.Layer.Vector("Vector", {
        styleMap: new OpenLayers.StyleMap({
            "default": new OpenLayers.Style({
                strokeColor: "${favColor}",
                strokeOpacity: 1,
                strokeWidth: 2,
                strokeDashstyle: "longdashdot",
                fillColor: "${fillColor}",
                fillOpacity: "${fillOpacity}",
                cursor: "pointer",
                label: "${label}",
                fontColor: "red",
                fontSize: "12px",
                fontFamily: "Courier New, monospace",
                fontWeight: "bold",
                labelAlign: "${labelAlign}",
                labelXOffset: "${labelXOffset}",
                labelYOffset: "${labelYOffset}",
                labelOutlineColor: "white",
                labelOutlineWidth: 3,
//                labelBackgroundColor: "white",
//                labelBorderColor: "#D42136",
//                labelBorderSize: "0.3px"
            }),
            "temporary": new OpenLayers.Style({
                strokeColor: "#ff0000",
                strokeOpacity: 0.5,
                strokeWidth: 2,
                fillColor: "white",
                fillOpacity: 0

            }),
            "select": new OpenLayers.Style({
                strokeColor: "#0033ff",
                strokeOpacity: 0.5,
                strokeWidth: 8,
                fillColor: "#0033ff",
                fillOpacity: 0,
                graphicZIndex: 2,
                cursor: "pointer"
            })
        })
    }),
    mecMidLayers: new OpenLayers.Layer.Vector("Vector", {//修改图层
        styleMap: new OpenLayers.StyleMap({
            "default": new OpenLayers.Style({
                strokeColor: "${favColor}",
                strokeOpacity: 1,
                strokeWidth: 1,
                fillColor: "${fillColor}",
                fillOpacity: "${fillOpacity}",
                cursor: "pointer",
            }),
            "temporary": new OpenLayers.Style({
                strokeColor: "#ff0000",
                strokeOpacity: 0.5,
                strokeWidth: 2,
                fillColor: "white",
                fillOpacity: 0

            }),
            "select": new OpenLayers.Style({
                strokeColor: "${favColor}",
                strokeOpacity: 0.5,
                strokeWidth: 3,
                fillColor: "${fillColor}",
                fillOpacity: 0,
                graphicZIndex: 2,
                cursor: "pointer"
            })
        })
    }),
    measureVectorLayer: new OpenLayers.Layer.Vector("Vector", {//测量
        styleMap: new OpenLayers.StyleMap({
            "default": new OpenLayers.Style({
                strokeColor: "red",
                strokeOpacity: 1,
                strokeWidth: 2,
                fillColor: "#F2D091",
                fillOpacity: 0.3,
                cursor: "pointer",
                label: "${label}",
                fontColor: "black",
                fontSize: "12px",
                fontFamily: "Courier New, monospace",
                labelAlign: "${labelAlign}",
                labelXOffset: "${labelXOffset}",
                labelYOffset: "${labelYOffset}",
                labelOutlineColor: "white",
                labelOutlineWidth: 3,
                labelBackgroundColor: "white",
                labelBorderColor: "red",
                labelBorderSize: "1px"
            }),
            "temporary": new OpenLayers.Style({
                strokeColor: "red",
                strokeOpacity: 1,
                strokeWidth: 2,
                fillColor: "#FA7B78",
                fillOpacity: 0.3
            })
        })
    }),
    pathLayers: new OpenLayers.Layer.Vector("Vector"),
    markLayers: new OpenLayers.Layer.Markers("Markers"),
    markMidLayers: new OpenLayers.Layer.Markers("Markers"),//微调
    measureMarkLayer: new OpenLayers.Layer.Markers("Markers"),
    //初始化绘制工具条
    drawControls: null,
    drawMidControls: null,//微调绘制工具条
    initDrawControls: function () {
        this.drawControls = {
            marker: new OpenLayers.Control.DrawFeature(this.mecLayers, OpenLayers.Handler.Point),
            polyline: new OpenLayers.Control.DrawFeature(this.mecLayers, OpenLayers.Handler.Path),
            polygon: new OpenLayers.Control.DrawFeature(this.mecLayers, OpenLayers.Handler.Polygon),
            circle: new OpenLayers.Control.DrawFeature(this.mecLayers, OpenLayers.Handler.RegularPolygon, {
                handlerOptions: {
                    sides: 40,
                    irregular: false
                }
            }),
            rectangle: new OpenLayers.Control.DrawFeature(this.mecLayers, OpenLayers.Handler.RegularPolygon, {
                handlerOptions: {
                    sides: 4,
                    irregular: true
                }
            }),
            select: new OpenLayers.Control.SelectFeature(this.mecLayers, {
                clickout: true,
                toggle: false,
                multiple: false,
                hover: false,
                toggleKey: "ctrlKey",
                // ctrl key removes from selection
                multipleKey: "shiftKey",
                // shift key adds to selection
                box: false
            })
        }

        for (var key in this.drawControls) {
            this.map.addControl(this.drawControls[key]);
        }

    },
    initDrawMidControls: function () {//微调绘制
        this.drawMidControls = {
            marker: new OpenLayers.Control.DrawFeature(this.mecMidLayers, OpenLayers.Handler.Point),
            polyline: new OpenLayers.Control.DrawFeature(this.mecMidLayers, OpenLayers.Handler.Path),
            polygon: new OpenLayers.Control.DrawFeature(this.mecMidLayers, OpenLayers.Handler.Polygon),
            circle: new OpenLayers.Control.DrawFeature(this.mecMidLayers, OpenLayers.Handler.RegularPolygon, {
                handlerOptions: {
                    sides: 40,
                    irregular: false
                }
            }),
            rectangle: new OpenLayers.Control.DrawFeature(this.mecMidLayers, OpenLayers.Handler.RegularPolygon, {
                handlerOptions: {
                    sides: 4,
                    irregular: true
                }
            }),
            select: new OpenLayers.Control.SelectFeature(this.mecMidLayers, {
            	clickout: true,
                toggle: false,
                multiple: false,
                hover: false,
                toggleKey: "ctrlKey",
                // ctrl key removes from selection
                multipleKey: "shiftKey",
                // shift key adds to selection
                box: false
            })
        }

        for (var key in this.drawMidControls) {
            this.map.addControl(this.drawMidControls[key]);
        }
    },

    //初始化地图
    initMap: function (div) {
        // 使用指定的文档元素创建地图 
        this.map = new OpenLayers.Map(div, {
            numZoomLevels: 19
        });

        // 创建一个 OpenStreeMap raster layer
        // 把这个图层添加到map中
        this.SDPubMap = new OpenLayers.Layer.WMTS({
            name: "山东电子地图",
            url: "http://www.sdmap.gov.cn/tileservice/SDPubMap?service=WMTS",
            layer: "山东电子地图",
            format: "image/png",
            style: "Default",
            matrixSet: "tianditu2013",
            isBaseLayer: true,

            visibility: true
        });

        this.SdRasterPubMap = new OpenLayers.Layer.WMTS({
            name: "山东影像地图",
            url: "http://www.sdmap.gov.cn/tileservice/SdRasterPubMap",
            layer: "山东影像地图",
            format: "image/png",
            style: "Default",
            matrixSet: "tianditu2013",
            isBaseLayer: false,

            visibility: false
        });

        this.sdrasterpubmapdj = new OpenLayers.Layer.WMTS({
            name: "山东影像注记地图",
            url: "http://www.sdmap.gov.cn/tileservice/sdrasterpubmapdj",
            layer: "山东影像注记地图",
            format: "image/png",
            style: "Default",
            matrixSet: "tianditu2013",
            isBaseLayer: false,

            visibility: false
        });

        this.map.addLayers([this.SDPubMap, this.SdRasterPubMap, this.sdrasterpubmapdj, this.pathLayers, this.measureVectorLayer, this.measureMarkLayer, this.mecLayers, this.mecMidLayers, this.markMidLayers,this.markLayers]);
        //注销浏览器右键事件
        this.map.div.oncontextmenu = function () {
            return false;
        };

        //this.map.setCenter(new OpenLayers.LonLat(this.centerLon, this.centerLat), this.centerZoom);
       // this.map.setCenter(new OpenLayers.LonLat("118.12223099821", "37.153542191932"), "16");
        this.map.addControl(new OpenLayers.Control.MousePosition());
        this.map.addControl(new OpenLayers.Control.ScaleLine({
            topOutUnits: "千米",
            topInUnits: "米",
            bottomOutUnits: "英里",
            bottomInUnits: "英寸"
            //如果底部单位为空 则不显示比例尺下部分
        }));
        MecMap.map.removeControl(MecMap.map.controls[1]);

        //添加选择控制器
        this.selectMicFeatureControl = new OpenLayers.Control.SelectFeature([MecMap.mecLayers], {
            onSelect: onFeatureMicSelect,
            onUnselect: onFeatureMicUnselect,

            clickout: true,
            toggle: false,
            multiple: false,
            hover: false,
            toggleKey: "ctrlKey",
            // ctrl key removes from selection
            multipleKey: "shiftKey",
            // shift key adds to selection
            box: false
        });

        //实现鼠标在要素上拖动地图的时候，可以正常拖动
        this.selectMicFeatureControl.handlers.feature.stopDown = false;
        this.map.addControl(this.selectMicFeatureControl);
        this.selectMicFeatureControl.activate();


        //添加选择控制器
        this.selectFeatureControl = new OpenLayers.Control.SelectFeature([MecMap.mecMidLayers], {
            onSelect: onFeatureSelect,
            onUnselect: onFeatureUnselect,

            clickout: true,
            toggle: false,
            multiple: false,
            hover: false,
            toggleKey: "ctrlKey",
            // ctrl key removes from selection
            multipleKey: "shiftKey",
            // shift key adds to selection
            box: false
        });

        //实现鼠标在要素上拖动地图的时候，可以正常拖动
        this.selectFeatureControl.handlers.feature.stopDown = false;
        this.map.addControl(this.selectFeatureControl);
        this.selectFeatureControl.deactivate();

        //添加图平微调控制器
        this.modifyFeatureControl = new OpenLayers.Control.ModifyFeature(this.mecMidLayers);
        this.map.addControl(this.modifyFeatureControl);
        this.modifyFeatureControl.virtualStyle = {
            fillColor: "#00CD00",
            fillOpacity: 0.8,
            pointRadius: 6,
            pointerEvents: "visiblePainted",
            strokeColor: "#008B00",
            strokeDashstyle: "solid",
            strokeLinecap: "round",
            strokeOpacity: 0.8,
            strokeWidth: 1
        };

        this.modifyFeatureControl.mode |= OpenLayers.Control.ModifyFeature.ROTATE;
        this.modifyFeatureControl.mode |= OpenLayers.Control.ModifyFeature.DRAG;
        
        this.map.events.register("zoomend", this.map, this.onMapZoom);
        this.map.setCenter(new OpenLayers.LonLat(this.centerLon, this.centerLat), this.centerZoom);
    },
    //激活工具条的某个操作
    toggleControl: function (tog) {
        MecMap.tog = "";
        if (tog != "hander") {
            if (tog != "select") {
                MecMap.map.div.style.cursor = "crosshair";
                MecMap.tog = tog;
            } else {
                MecMap.map.div.style.cursor = "pointer";
            }

            for (key in this.drawControls) {
                var control = this.drawControls[key];
                if (tog == key) {
                    control.activate();
                } else {
                    control.deactivate();
                }
            }
        } else {
            MecMap.map.div.style.cursor = "pointer";

            for (key in this.drawControls) {
                var control = this.drawControls[key];
                control.deactivate();
            }
        }

    },
    //设置地图样式
    setMapStyle: function (isFill, fillColor, fillOpacity, strokeColor, strokeOpacity, strokeWeight) {
        MecMap.mecLayers.style = {
            fill: isFill,
            fillColor: fillColor,
            fillOpacity: fillOpacity,
            strokeColor: strokeColor,
            strokeOpacity: strokeOpacity,
            strokeWeight: strokeWeight
        }
    },
    //重置地图样式
    resetMapStyle: function () {
        MecMap.mecLayers.style = {
            fill: true,
            fillColor: "Orange",
            fillOpacity: 0.3,
            strokeColor: "Orange",
            strokeOpacity: 0.5,
            strokeWeight: 1
        }
    },
    //清空所有图形
    removeAllFeatures: function () {
        this.mecLayers.removeAllFeatures();
    },
    removeMark: function (mark) {
        this.markLayers.removeMarker(mark);
    },
    removeAllMark: function () {
        this.markLayers.clearMarkers();
    },
    //移除一个用户所添加的图形
    removeFeature: function (features) {
        this.mecLayers.removeFeatures(features);
    },
    //获取用户选中的图形
    getSelectFeatures: function () {
        return this.mecLayers.selectedFeatures;
    },
    //注册图形草图画完，将要生成正式图形前的监听事件
    registOnAddFeature: function (onAddFeature) {
        this.mecLayers.events.register('sketchcomplete', this.mecLayers, onAddFeature); //featureadded
    },
  //添加标注
    addMarkerToLayer1: function (lon, lat, imageurl, markerdate, mousedownevent, labelfeature) {
    	MecMap.selectColor = '';
        //坐标点
        var lonlat = new OpenLayers.LonLat(lon, lat);
        //图片大小
        var size = new OpenLayers.Size(16, 16); //32, 43
        var offset = new OpenLayers.Pixel(-(size.w / 2), -size.h);
        //图片对象
        var icon = new OpenLayers.Icon((imageurl ? imageurl : '../template/img/r-point.png'), size, offset);
        var marker = new OpenLayers.Marker(lonlat, icon.clone());
        if (mousedownevent) {
            //marker.events.register('mousedown', marker, mousedownevent);
            marker.events.register('mouseover', marker, mousedownevent);
        }
        marker.mec_date = markerdate;
        //marker.mec_event = mousedownevent;
        marker.icon.imageDiv.className = "markerImageHover";
        this.markLayers.addMarker(marker);

        // Create a point feature to show the label offset options
        if (labelfeature) {
            marker.mec_label = labelfeature;
        }
        return marker;
    },
    //添加标注
    addMarkerToLayer: function (lon, lat, imageurl, markerdate, mousedownevent) {
        var lonlat = new OpenLayers.LonLat(lon, lat);
        var width = 35, height = 35;
        var size = new OpenLayers.Size(width, height); //32, 43

        var offset = new OpenLayers.Pixel(-(size.w / 2), -size.h);
        var icon = new OpenLayers.Icon((imageurl ? imageurl : '/Images/map/marker.png'), size, offset);
        var marker = new OpenLayers.Marker(lonlat, icon.clone());

        if (mousedownevent) {//鼠标点击事件
            marker.events.register('mousedown', marker, mousedownevent);
        }

        marker.mec_date = markerdate;
        //marker.icon.imageDiv.className = "markerImageHover";
        this.markLayers.addMarker(marker);

        // Create a point feature to show the label offset options
        var labelOffsetPoint = new OpenLayers.Geometry.Point(lon, lat);
        var labelOffsetFeature = new OpenLayers.Feature.Vector(labelOffsetPoint);

        labelOffsetFeature.attributes = {
            label: markerdate.name,
            fontSize: "4px",
            labelAlign: "cm",
            labelXOffset: 0,
            labelYOffset: 4
        };
        labelOffsetFeature.mec_date = markerdate;

        this.mecLayers.addFeatures(labelOffsetFeature);
    },
    //添加标注,自定义样式
    addMarkerToLayerByCustom: function (lon, lat, imageurl, style, markerdate, mousedownevent) {
        var lonlat = new OpenLayers.LonLat(lon, lat);

        var width = style.width ? style.width : 22;
        var height = style.height ? style.height : 22;
        var size = new OpenLayers.Size(width, height);

        var offsetw = style.offsetw ? style.offsetw : -(size.w / 2);
        var offseth = style.offseth ? style.offseth : -size.h;
        var offset = new OpenLayers.Pixel(offsetw, offseth);

        var icon = new OpenLayers.Icon((imageurl ? imageurl : '/Images/map/marker.png'), size, offset);

        var marker = new OpenLayers.Marker(lonlat, icon.clone());

        if (mousedownevent) {//鼠标点击事件
            marker.events.register('mousedown', marker, mousedownevent);
        }

        marker.mec_date = markerdate;
        //marker.icon.imageDiv.className = "markerImageHover";
        this.markLayers.addMarker(marker);

        // Create a point feature to show the label offset options
        if (!style.isLabelShow) {
            return false;
        }
        var labelOffsetPoint = new OpenLayers.Geometry.Point(lon, lat);
        var labelOffsetFeature = new OpenLayers.Feature.Vector(labelOffsetPoint);

        labelOffsetFeature.attributes = {
            label: markerdate.name,
            fontSize: style.fontsize ? style.fontsize : '4px',
            fontWeight: "normal",
            labelAlign: "cm",
            labelXOffset: style.labelXOffset ? style.labelXOffset : 0,
            labelYOffset: style.labelYOffset ? style.labelYOffset : 0,
        };
        labelOffsetFeature.mec_date = markerdate;
        this.mecLayers.addFeatures(labelOffsetFeature);
    },
    //添加多边形
    addPolygonFeature: function (pointList, attributes, featuredate, mousedownevent) {
        if (pointList && pointList.length > 0) {
            pointList.push(pointList[0]);
        }

        var linearRing = new OpenLayers.Geometry.LinearRing(pointList);
        var polygonFeature = new OpenLayers.Feature.Vector(new OpenLayers.Geometry.Polygon([linearRing]));
        polygonFeature.attributes = attributes;
        if (mousedownevent) {
            polygonFeature.events.register('mousedown', polygonFeature, mousedownevent);
        }
        polygonFeature.mec_date = featuredate;
        this.mecLayers.addFeatures(polygonFeature);
        return polygonFeature;
    },
    //添加线
    addLineFeature: function (pointList, attributes, featuredate, mousedownevent) {
        var lineFeature = new OpenLayers.Feature.Vector(new OpenLayers.Geometry.LineString(pointList));
        lineFeature.attributes = attributes;
        if (mousedownevent) {
            lineFeature.events.register('mousedown', lineFeature, mousedownevent);
        }
        lineFeature.mec_date = featuredate;
        this.mecLayers.addFeatures(lineFeature);
        return lineFeature;
    },
    //获取所画图形的坐标
    getFeatureVertices: function (feature) {

        return feature.geometry.getVertices();
    },
    //获取所画图形的面积 以平方米为单位
    getFeatureArea: function (feature) {
        //平方米为单位
        return feature.geometry.getGeodesicArea().toFixed(2);
    },
    //获取所画图形的长度 以米为单位
    getFeatureDistance: function (feature) {
        //米为单位
        return feature.geometry.getGeodesicLength().toFixed(2);
    },
    addMoveLisenter: function (moveend_event) {
        this.map.events.register('move', this.map, moveend_event);
    },
    setMapCenter: function (lon, lat) {
        this.map.setCenter(new OpenLayers.LonLat(lon, lat));
    },
    setMapCenter: function (lon, lat, zoom) {
        this.map.setCenter(new OpenLayers.LonLat(lon, lat), zoom);
    },
    //矢量二维地图
    switchVecLayer: function () {

        MecMap.SDPubMap.setVisibility(true);
        MecMap.SdRasterPubMap.setVisibility(false);
        MecMap.sdrasterpubmapdj.setVisibility(false);
    },
    //卫星地图
    switchImgLayer: function () {

        MecMap.SDPubMap.setVisibility(false);
        MecMap.SdRasterPubMap.setVisibility(true);
        MecMap.sdrasterpubmapdj.setVisibility(false);

    },
    //混合地图
    switchVecImgLayer: function () {
        MecMap.SDPubMap.setVisibility(false);
        MecMap.SdRasterPubMap.setVisibility(true);
        MecMap.sdrasterpubmapdj.setVisibility(true);
    },
    //获取所绘制的图形的中心点
    getCenterPolygonFeature: function (feature) {
        return feature.geometry.getBounds().getCenterLonLat();
    },
    //启用微调功能
    beginModify: function () {
        if (MecMap.selectFea) {
            MecMap.modifyFeatureControl.selectFeature(MecMap.selectFea);
            MecMap.modifyFeatureControl.activate();
        }

    },
    //关闭微调功能
    endModify: function () {
        if (MecMap.modifyFeatureControl.feature) {
            MecMap.modifyFeatureControl.unselectFeature(MecMap.modifyFeatureControl.feature);
        }
        MecMap.modifyFeatureControl.deactivate();
    },
    //注册图形草图画完，将要生成正式图形前的监听事件(微调)
    registOnAddMidFeature: function (onAddFeature) {
        this.mecLayers.events.register('sketchcomplete', this.mecMidLayers, onAddFeature); //featureadded
    },
    //添加编辑标注
    addMidMarkerToLayer: function (lon, lat, imageurl, markerdate, mousedownevent) {
        var lonlat = new OpenLayers.LonLat(lon, lat);
        var size = new OpenLayers.Size(20, 20); //32, 43

        var offset = new OpenLayers.Pixel(-(size.w / 2), -size.h);
        var icon = new OpenLayers.Icon((imageurl ? imageurl : '/Images/map/marker.png'), size, offset);
        var marker = new OpenLayers.Marker(lonlat, icon.clone());

        if (mousedownevent) {//鼠标点击事件
            marker.events.register('mousedown', marker, mousedownevent);
        }
        marker.label = markerdate.label ? markerdate.label : '';
        marker.mec_date = markerdate;
        //marker.icon.imageDiv.className = "markerImageHover";
        this.markMidLayers.addMarker(marker);
//        var labelOffsetPoint = new OpenLayers.Geometry.Point(lon, lat);
//        var labelOffsetFeature = new OpenLayers.Feature.Vector(labelOffsetPoint);
//
//        labelOffsetFeature.attributes = {
//            label: markerdate.name,
//            fontSize: "4px",
//            labelAlign: "cm",
//            width: 22,
//            height: 22,
//            labelXOffset: 0,
//            labelYOffset: -9
//        };
//        labelOffsetFeature.mec_date = markerdate;
//
//        this.mecLayers.addFeatures(labelOffsetFeature);
    },
    //添加编辑多边形
    addMidPolygonFeature: function (pointList, attributes, featuredate, mousedownevent) {
        if (pointList && pointList.length > 0) {
            pointList.push(pointList[0]);
        }

        var linearRing = new OpenLayers.Geometry.LinearRing(pointList);
        var polygonFeature = new OpenLayers.Feature.Vector(new OpenLayers.Geometry.Polygon([linearRing]));
        polygonFeature.attributes = attributes;
        if (mousedownevent) {
            polygonFeature.events.register('mousedown', polygonFeature, mousedownevent);
        }
        polygonFeature.mec_date = featuredate;
        this.mecMidLayers.addFeatures(polygonFeature);
        return polygonFeature;
    },
    //清空所有图形
    removeMidAllFeatures: function () {
        this.mecMidLayers.removeAllFeatures();
    },
    removeMidMark: function (mark) {
        this.markMidLayers.removeMarker(mark);
    },
    removeMidAllMark: function () {
        this.markMidLayers.clearMarkers();
    },
    //移除一个用户所添加的图形
    removeMidFeature: function (features) {
        this.mecMidLayers.removeFeatures(features);
    },
    //地图层级变化监听
    onMapZoom: function () {
        var _zoom = MecMap.map.getZoom();
        //alert(_zoom);
        //最小缩放等级 小于此等级地图范围不足
        if (_zoom < 9) {
            MecMap.map.setCenter(MecMap.map.getCenter(), 9);
        }
        //#region 根据层级控制区域显示隐藏 算法烂，想法不成熟前不要修改
        var flag = false;
        if (_zoom >= 17) {
            var features = MecMap.mecLayers.features;
            var features1 = MecMap.mecMidLayers.features;
            var marks = MecMap.markMidLayers.markers;
            $.each(features, function (index, itemfeat) {
                itemfeat.style = null;
                if (itemfeat.mec_date != null && itemfeat.mec_date.typeId != null && (itemfeat.mec_date.typeId == "1"  || itemfeat.mec_date.typeId == "2")) {
                	itemfeat.attributes.fillOpacity = 0;
                }
            });
            $.each(features1, function (index, itemfeat2) {
                itemfeat2.style = null;
                if (itemfeat2.mec_date != null && itemfeat2.mec_date.typeId != null && (itemfeat2.mec_date.typeId == "1"  || itemfeat2.mec_date.typeId == "2")) {
                	itemfeat2.attributes.fillOpacity = 0;
                }
            });
            $.each(marks, function (index, itemfeat1) {
                if (itemfeat1.mec_date != null && itemfeat1.mec_date.typeId != null && (itemfeat1.mec_date.typeId == "8"  || itemfeat1.mec_date.typeId == "9")) {
                	itemfeat1.display(false);
                }
            });
            flag = true;
            MecMap.mecLayers.redraw();
            MecMap.mecMidLayers.redraw();
        }
        if (!flag && _zoom >= 16) {
            var features = MecMap.mecLayers.features;
            var features1 = MecMap.mecMidLayers.features;
            var marks = MecMap.markMidLayers.markers;
            $.each(features, function (index, itemfeat) {
                itemfeat.style = null;
                if (itemfeat.mec_date != null && itemfeat.mec_date.typeId != null && itemfeat.mec_date.typeId == "1") {
                	itemfeat.attributes.fillOpacity = 0;
                }else if(itemfeat.mec_date != null && itemfeat.mec_date.typeId != null && itemfeat.mec_date.typeId == "2"){
                	itemfeat.style = null;
                    itemfeat.attributes.fillOpacity = 0.5;
                }else{
                	itemfeat.style = { display: 'none' };
                }
            });
            $.each(features1, function (index, itemfeat2) {
                itemfeat2.style = null;
                if (itemfeat2.mec_date != null && itemfeat2.mec_date.typeId != null && itemfeat2.mec_date.typeId == "1") {
                	itemfeat2.attributes.fillOpacity = 0;
                }else if(itemfeat2.mec_date != null && itemfeat2.mec_date.typeId != null && itemfeat2.mec_date.typeId == "2"){
                	itemfeat2.style = null;
                    itemfeat2.attributes.fillOpacity = 0.5;
                }else{
                	itemfeat2.style = { display: 'none' };
                }
            });
            $.each(marks, function (index, itemfeat1) {
                if (itemfeat1.mec_date != null && itemfeat1.mec_date.typeId != null) {
                	if(itemfeat1.mec_date.typeId == "9"){
                		itemfeat1.display(false);
                	}else if(itemfeat1.mec_date.typeId == "8" || itemfeat1.mec_date.typeId == "4" || itemfeat1.mec_date.typeId == "5" || itemfeat1.mec_date.typeId == "6"){
                		itemfeat1.display(true);
                	}
                }
            });
            flag = true;
            MecMap.mecLayers.redraw();
            MecMap.mecMidLayers.redraw();
        }
        if (!flag && _zoom >= 13) {
            var features = MecMap.mecLayers.features;
            var features1 = MecMap.mecMidLayers.features;
            var marks = MecMap.markMidLayers.markers;
            $.each(features, function (index, itemfeat) {
            	if (itemfeat.mec_date != null && itemfeat.mec_date.typeId != null && itemfeat.mec_date.typeId == "1") {
            		//alert(itemfeat.attributes.fontColor);
            		itemfeat.style = null;
                    itemfeat.attributes.fillOpacity = 0.5;
                } else {
                    itemfeat.style = { display: 'none' };
                }
            });
            $.each(features1, function (index, itemfeat2) {
            	if (itemfeat2.mec_date != null && itemfeat2.mec_date.typeId != null && itemfeat2.mec_date.typeId == "1") {
            		itemfeat2.style = null;
                    itemfeat2.attributes.fillOpacity = 0.5;
                } else {
                    itemfeat2.style = { display: 'none' };
                }
            });
            $.each(marks, function (index, itemfeat1) {
                if (itemfeat1.mec_date != null && itemfeat1.mec_date.typeId != null) {
                	if(itemfeat1.mec_date.typeId == "8"){
                		itemfeat1.display(false);
                	}else if(itemfeat1.mec_date.typeId == "9" || itemfeat1.mec_date.typeId == "4" || itemfeat1.mec_date.typeId == "5" || itemfeat1.mec_date.typeId == "6"){
                		itemfeat1.display(true);
                	}
                }
            });
            flag = true;
            MecMap.mecLayers.redraw();
            MecMap.mecMidLayers.redraw();
        }
        
        //小于13级隐藏所有元素
        if (!flag && _zoom < 13) {
            var features = MecMap.mecLayers.features;
            var features1 = MecMap.mecMidLayers.features;
            var marks = MecMap.markMidLayers.markers;
            $.each(features, function (index, itemfeat) {
                itemfeat.style = { display: 'none' };
            });
            $.each(features1, function (index, itemfeat2) {
                itemfeat2.style = { display: 'none' };
            });
            $.each(marks, function (index, itemfeat1) {
        		itemfeat1.display(false);
            });
            flag = true;

            MecMap.mecLayers.redraw();
            MecMap.mecMidLayers.redraw();
            MECPopup.closepopup();
        }

        //小于16级隐藏服务站
        if (!flag && _zoom < 16) {
            var features = MecMap.mecLayers.features;
            var features1 = MecMap.mecMidLayers.features;
            $.each(features, function (index, itemfeat) {
                if (itemfeat.mec_date != null && itemfeat.mec_date.typeId != null && itemfeat.mec_date.typeId == "2") {
                    itemfeat.style = { display: 'none' };
                } else {
                    itemfeat.attributes.fillOpacity = 0.5;
                }
            });
            $.each(features1, function (index, itemfeat2) {
                if (itemfeat2.mec_date != null && itemfeat2.mec_date.typeId != null && itemfeat2.mec_date.typeId == "2") {
                    itemfeat2.style = { display: 'none' };
                } else {
                    itemfeat2.attributes.fillOpacity = 0.5;
                }
            });
            flag = true;
            MecMap.mecLayers.redraw();
            MecMap.mecMidLayers.redraw();
        }
      //小于17级隐藏网格
        if (!flag && _zoom < 17) {
            var features = MecMap.mecLayers.features;
            var features1 = MecMap.mecMidLayers.features;
            $.each(features, function (index, itemfeat) {
                if (itemfeat.mec_date != null && itemfeat.mec_date.typeId != null && itemfeat.mec_date.typeId == "3") {
                    itemfeat.style = { display: 'none' };
                } else {
                    itemfeat.attributes.fillOpacity = 0.5;
                }
            });
            $.each(features1, function (index, itemfeat2) {
                if (itemfeat2.mec_date != null && itemfeat2.mec_date.typeId != null && itemfeat2.mec_date.typeId == "3") {
                    itemfeat2.style = { display: 'none' };
                } else {
                    itemfeat2.attributes.fillOpacity = 0.5;
                }
            });
            flag = true;
            MecMap.mecLayers.redraw();
            MecMap.mecMidLayers.redraw();
        }
    }
}

function resetPopup() {
    MECPopup.reSetLocation();
}

//绘制完成后调用的此方法
function onAddFeature(event) {
    event.feature.attributes = {
        "label": "111"
    };
    alert(event.feature.geometry.getVertices());
    //feature.geometry.getVertices()
}

function getLabel(feature) {
    return (feature.label != undefined ? feature.label : "");
}

function handleMeasurements(geometry, length, area, units) {
    var out = "";
    if (geometry.CLASS_NAME == "OpenLayers.Geometry.LineString") {
        out += "length: " + length.toFixed(3) + " " + units;
    } else {
        out += "perimeter: " + length.toFixed(3) + " " + units + "<br />";
        out += "area: " + area.toFixed(3) + " " + units + "<sup>2</" + "sup>";
    }
    alert(out);
}

//meclayer中featuer选中事件
function onFeatureMicSelect(feature) {
    var menu = document.getElementById("menu_div");
    if (menu) {
        menu.style.visibility = "visible";
    }
    //MecMap.selectFea = feature;
    //getDrive(feature.mec_date.id);
}

//meclayer中featuer不选中事件
function onFeatureMicUnselect(e) {
    var menu = document.getElementById("menu_div");
    if (menu) {
        menu.style.visibility = "hidden";
    }
    //MecMap.selectFea = null;
}

//meclayer中featuer选中事件
function onFeatureSelect(feature) {
    var menu = document.getElementById("menu_div");
    if (menu) {
        menu.style.visibility = "visible";
    }
    MecMap.selectFea = feature;
}

//meclayer中featuer不选中事件
function onFeatureUnselect(e) {
    var menu = document.getElementById("menu_div");
    if (menu) {
        menu.style.visibility = "hidden";
    }
    MecMap.selectFea = null;
}
