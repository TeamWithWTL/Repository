//measure.html

var sketchSymbolizers = { 
    "Line": {
        strokeWidth: 2,
        strokeOpacity: 1,
        strokeColor: "red",
        strokeDashstyle: "solid" //“dot”, “dash”, “dashdot”, “longdash”, “longdashdot”, or “solid
    },
    "Polygon": {
        strokeWidth: 2,
        strokeOpacity: 1,
        strokeColor: "red",
        fillColor: "#F2D091",
        fillOpacity: 0.3
    }
};
var style = new OpenLayers.Style();
style.addRules([new OpenLayers.Rule({
    symbolizer: sketchSymbolizers
})]);
var styleMap = new OpenLayers.StyleMap({
    "default": style
});

// allow testing of specific renderers via "?renderer=Canvas", etc
var renderer = OpenLayers.Util.getParameters(window.location.href).renderer;
renderer = (renderer) ? [renderer] : OpenLayers.Layer.Vector.prototype.renderers;


var measureControls = {
        line: new OpenLayers.Control.Measure(OpenLayers.Handler.Path, {
            persist: false,
            handlerOptions: {
                layerOptions: {
                    renderers: renderer,
                    styleMap: styleMap
                }
            }
        }),
        polygon: new OpenLayers.Control.Measure(OpenLayers.Handler.Polygon, {
            persist: false,
            handlerOptions: {
                layerOptions: {
                    renderers: renderer,
                    styleMap: styleMap
                }
            }
        })
    }

    var MecMeasure = {
        initMeasureControls: function () {
            for (var key in measureControls) {
                var control = measureControls[key];
                control.events.on({
                    "measure": MecMeasure.end,
                    "measurepartial": handleMeasurements
                });
                MecMap.map.addControl(control);
            }
        },
        ctype: "",
        point: null,
        tipVector: null,
        points: new Array(),
        begin: function (element) {

            for (key in measureControls) {
                var control = measureControls[key];
                if (element == key) {
                    control.activate();
                    //control.setImmediate(true);
                    MecMeasure.ctype = element;
                } else {
                    control.deactivate();

                }
            }

            MecMeasure.points.length = 0;
            MecMap.measureMarkLayer.clearMarkers();
            MecMap.measureVectorLayer.removeAllFeatures();
            MecMap.map.div.style.cursor = "url(/widgets/map/tagimg/Line.cur),auto";
            MecMap.map.events.register('mousemove', MecMap.map, getMouseLonLat);


        },
        end: function (e) {

            
            MecMap.map.events.unregister('mousemove', MecMap.map, getMouseLonLat);
            MecMap.map.div.style.cursor = "pointer";

            var point;
            if (e.order == 1) {
                point = e.geometry.components[e.geometry.components.length - 1];
            } else {
                point = e.geometry.components[0].components[e.geometry.components[0].components.length - 2];

                tipstr = "";
            }
             
            MecMeasure.points.push(new OpenLayers.Geometry.Point(point.x, point.y));

            var size = new OpenLayers.Size(12, 12); //32, 43
            var offset = new OpenLayers.Pixel(-(size.w / 2), -(size.h / 2));

            var icon = new OpenLayers.Icon(('/widgets/map/tagimg/point.png'), size, offset);

            var mark = new OpenLayers.Marker(new OpenLayers.LonLat(point.x, point.y), icon.clone());

            MecMap.measureMarkLayer.addMarker(mark);

            

            if (MecMeasure.ctype == "line") {
                var lineFeature = new OpenLayers.Feature.Vector(
                new OpenLayers.Geometry.LineString(MecMeasure.points));
                lineFeature.attributes = {
                    label: ""
                };

                MecMap.measureVectorLayer.addFeatures(lineFeature);
            } else {

                var linearRing = new OpenLayers.Geometry.LinearRing(MecMeasure.points);
                var polygonFeature = new OpenLayers.Feature.Vector(new OpenLayers.Geometry.Polygon([linearRing]));
                polygonFeature.attributes = {
                    label: ""
                };

                MecMap.measureVectorLayer.addFeatures(polygonFeature);
            }


            var labelOffsetPoint = new OpenLayers.Geometry.Point(point.x, point.y);
            var pointVector = new OpenLayers.Feature.Vector(labelOffsetPoint);
            pointVector.attributes = {
                label: MecMeasure.tipVector.attributes.label,
                favColor: 'blue',
                labelAlign: "lm",
                labelXOffset: 15,
                labelYOffset: 0,
                labelPadding: "3px"
            };


            MecMap.measureVectorLayer.addFeatures(pointVector);


            if (MecMeasure.tipVector) {

                MecMap.measureVectorLayer.removeFeatures(MecMeasure.tipVector);

            }

            for (key in measureControls) {
                measureControls[key].deactivate();
            }

            MecMeasure.ctype = "";
        },
        clear: function () {


            MecMap.map.div.style.cursor = "pointer";
            MecMap.map.events.unregister('mousemove', MecMap.map, getMouseLonLat);

            for (key in measureControls) {
                measureControls[key].deactivate();
            }

            MecMap.measureVectorLayer.removeAllFeatures();
            MecMap.measureMarkLayer.clearMarkers();

            MecMeasure.point = null;
            MecMeasure.tipVector = null;
            MecMeasure.points.length = 0;
            MecMeasure.ctype = "";
             
        }
    }

function getMouseLonLat(e) {
    var tipstr = "请单击确定起点";
    var lonlat = MecMap.map.getLonLatFromViewPortPx(e.xy);
    if (MecMeasure.points.length > 0) {
        var parray = MecMeasure.points.concat();
        parray.push(new OpenLayers.Geometry.Point(lonlat.lon, lonlat.lat));
        if (MecMeasure.ctype == "line") {
            var lineFeature = new OpenLayers.Feature.Vector(
                new OpenLayers.Geometry.LineString(parray));

            var len = lineFeature.geometry.getGeodesicLength();
            if (len > 1000) {
                tipstr = (len / 1000).toFixed(3) + "公里";
            } else {
                tipstr = len.toFixed(3) + "米";
            }
        } else if (MecMeasure.points.length > 1) {

            var linearRing = new OpenLayers.Geometry.LinearRing(parray);
            var polygonFeature = new OpenLayers.Feature.Vector(new OpenLayers.Geometry.Polygon([linearRing]));
             
            var area = polygonFeature.geometry.getGeodesicArea();
            if (area > 1000) {
                tipstr = (area / 1000).toFixed(3) + "平方公里";
            } else {
                tipstr = area.toFixed(3) + "平方米";
            }
        } else {
            tipstr = "";
        }
        
        
    }
    
    var size = new OpenLayers.Size(12, 12); //32, 43
    var offset = new OpenLayers.Pixel(-(size.w / 2), -(size.h / 2));

    var icon = new OpenLayers.Icon(('/widgets/map/tagimg/point.png'), size, offset);

    if (MecMeasure.point) {
        MecMap.measureMarkLayer.removeMarker(MecMeasure.point);
    }

    if (MecMeasure.tipVector) {
        MecMap.measureVectorLayer.removeFeatures(MecMeasure.tipVector);
    }

    
    MecMeasure.point = new OpenLayers.Marker(lonlat, icon.clone());

    MecMap.measureMarkLayer.addMarker(MecMeasure.point);

    // Create a point feature to show the label offset options 
    var labelOffsetPoint = new OpenLayers.Geometry.Point(lonlat.lon,lonlat.lat);
    MecMeasure.tipVector = new OpenLayers.Feature.Vector(labelOffsetPoint);
    MecMeasure.tipVector.attributes = {
        label: tipstr,
        favColor: 'blue',
        labelAlign: "cm",
        labelXOffset: 50,
        labelYOffset: -35,
        labelPadding: "3px"
    };


    MecMap.measureVectorLayer.addFeatures(MecMeasure.tipVector);
     
}

function AddMeasurePoint(e) {
    var lonlat = MecMap.map.getLonLatFromViewPortPx(e.xy);
    MecMeasure.points.push(new OpenLayers.Geometry.Point(lonlat.lon, lonlat.lat));
    var size = new OpenLayers.Size(12, 12); //32, 43
    var offset = new OpenLayers.Pixel(-(size.w / 2), -(size.h / 2));

    var icon = new OpenLayers.Icon(('/widgets/map/tagimg/point.png'), size, offset);
     
    var point = new OpenLayers.Marker(lonlat, icon.clone());

    MecMap.measureMarkLayer.addMarker(point);
    addMeasureLine();
}


function addMeasureLine() {
    var len = MecMeasure.points.length;
    if (len > 1) {
        var linepoints = new Array();
        linepoints.push(new OpenLayers.Geometry.Point(MecMeasure.points[len - 2].lon, MecMeasure.points[len - 2].lat));
        linepoints.push(new OpenLayers.Geometry.Point(MecMeasure.points[len - 1].lon, MecMeasure.points[len - 1].lat));

        var lineFeature = new OpenLayers.Feature.Vector(
                new OpenLayers.Geometry.LineString(linepoints));
        lineFeature.attributes = {
            label: ""
        };

        MecMap.measureVectorLayer.addFeatures(lineFeature);
    }
}

function handleMeasurements(e) {
    var pouint;
    var tipstr = "起点";
    if (e.order == 1) {
        point = e.geometry.components[e.geometry.components.length - 1];
    } else {
        point = e.geometry.components[0].components[e.geometry.components[0].components.length - 2];
          
        tipstr = "";
    }
    //var point = e.geometry.components[e.geometry.components.length - 1].components[1];
   
    MecMeasure.points.push(new OpenLayers.Geometry.Point(point.x, point.y));

    
    if (MecMeasure.points.length > 1 && e.order == 1) {
        var lineFeature = new OpenLayers.Feature.Vector(
            new OpenLayers.Geometry.LineString(MecMeasure.points));

        var len = lineFeature.geometry.getGeodesicLength();
        if (len > 1000) {
            tipstr = (len / 1000).toFixed(3) + "公里";
        } else {
            tipstr = len.toFixed(3) + "米";
        }

    }   
     
     
    var size = new OpenLayers.Size(12, 12); //32, 43
    var offset = new OpenLayers.Pixel(-(size.w / 2), -(size.h / 2));

    var icon = new OpenLayers.Icon(('/widgets/map/tagimg/point.png'), size, offset);

    var mark = new OpenLayers.Marker( new OpenLayers.LonLat(point.x, point.y), icon.clone());

    MecMap.measureMarkLayer.addMarker(mark);

    // Create a point feature to show the label offset options 
    var labelOffsetPoint = new OpenLayers.Geometry.Point(point.x, point.y);
    var pointVector = new OpenLayers.Feature.Vector(labelOffsetPoint);
    pointVector.attributes = {
        label: tipstr,
        favColor: 'blue',
        labelAlign: "lm",
        labelXOffset: 15,
        labelYOffset: 0,
        labelPadding: "3px"
    };
    
    MecMap.measureVectorLayer.addFeatures(pointVector);

}

