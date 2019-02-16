Array.prototype.contains = function (obj) {
    var i = this.length;
    while (i--) {
        if (this[i] === obj) {
            return true;
        }
    }
    return false;
}


var MECMAP_DRAWING_HANDER = "hander",     // 鼠标拖动模式  
    MECMAP_DRAWING_MARKER = "marker",     // 鼠标画点模式
    MECMAP_DRAWING_POLYLINE = "polyline",   // 鼠标画线模式
    MECMAP_DRAWING_CIRCLE = "circle",     // 鼠标画圆模式
    MECMAP_DRAWING_RECTANGLE = "rectangle",  // 鼠标画矩形模式
    MECMAP_DRAWING_POLYGON = "polygon";    // 鼠标画多边形模式
    MECMAP_DRAWING_SELECT = "select"; //鼠标选择

//鼠标经过工具栏上的提示信息
var tips = {};
tips[MECMAP_DRAWING_HANDER] = "拖动地图";
tips[MECMAP_DRAWING_MARKER] = "画点";
tips[MECMAP_DRAWING_CIRCLE] = "画圆";
tips[MECMAP_DRAWING_POLYLINE] = "画折线";
tips[MECMAP_DRAWING_POLYGON] = "画多边形";
tips[MECMAP_DRAWING_RECTANGLE] = "画矩形";
tips[MECMAP_DRAWING_SELECT] = "点击选择";


//设置工具栏当前选中的项样式
function setStyleByDrawingMode(panel, drawingType) {
    var boxs = panel.getElementsByTagName("a");
    for (var i = 0, len = boxs.length; i < len; i++) {
        var box = boxs[i];
        if (box.drawingtype == drawingType) {
            var classStr = "BMapLib_box BMapLib_" + drawingType + "_hover";
            if (i == len - 1) {
                classStr += " BMapLib_last";
            }
            box.className = classStr;
        } else {
            box.className = box.className.replace(/_hover/, "");
        }
    }
}

//设置工具栏当前选中的项的事件
function bindEventByDraingMode(drawingType) {
    MecMap.toggleControl(drawingType);
}
function _bind(e){
    setStyleByDrawingMode(this, e.srcElement.drawingtype);
    bindEventByDraingMode(e.srcElement.drawingtype);
}


var DrawingManager = {
    add: function (map_div, draw_modes) {

        if (MecMap) {
            MecMap.initDrawControls();
        } else {
            return;
        }

        if (!draw_modes.contains(MECMAP_DRAWING_HANDER)) {
            draw_modes.unshift(MECMAP_DRAWING_HANDER);
        }
        var newdiv = document.createElement("div");
        newdiv.id = "MECMapLib_Drawing_panel"
        newdiv.className = "BMapLib_Drawing_panel";
        for (var i = 0; i < draw_modes.length; i++) {

            var a_div = document.createElement("a");
            if (i == draw_modes.length - 1) {
                a_div.className = "BMapLib_box BMapLib_" + draw_modes[i] + " BMapLib_last";
            } else {
                a_div.className = "BMapLib_box BMapLib_" + draw_modes[i];
            }
            a_div.title = tips[draw_modes[i]];
            a_div.href = "javascript:void(0)";
            a_div.drawingtype = draw_modes[i];

            newdiv.appendChild(a_div);
        }
        newdiv.addEventListener("click", _bind, false);

        document.getElementById(map_div).appendChild(newdiv);

    },
    remove: function () {
        var div = document.getElementById("MECMapLib_Drawing_panel");
        if (div) {
            div.parentElement.removeChild(div);
        }
    },
    hide: function () {
        var div = document.getElementById("MECMapLib_Drawing_panel");
        if (div) {
            div.style.display = "none";
        }
    },
    show: function () {
        var div = document.getElementById("MECMapLib_Drawing_panel");
        if (div) {
            div.style.display = "";
        }
    },
    addEventListener: function (event_type, event_fun) {
        var div = document.getElementById("MECMapLib_Drawing_panel");
        if (div) {
            div.addEventListener(event_type, event_fun, false);
        }
    },
    reset: function (index) {
        var div = document.getElementById("MECMapLib_Drawing_panel");
        if (div && index && div.childNodes[index]) {
            div.childNodes[index].click();
        } else {
            div.childNodes[0].click();
        }
    }
}