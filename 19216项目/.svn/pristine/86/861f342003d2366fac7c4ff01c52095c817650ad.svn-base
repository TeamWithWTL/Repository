var MECPopup = {
    newdiv: null,
    newdiv_title: null,
    newdiv_content: null,
    map_div: null,
    width: 0,
    height: 0,
    top: 0,
    left: 0,
    difftop: 0,
    createpopup: function (map_div, width, height, left, top, difftop) {
        if (difftop) {
            MECPopup.difftop = difftop;
        }
        MECPopup.map_div = map_div;
        MECPopup.width = width;
        MECPopup.height = height + 60;
        MECPopup.newdiv = document.createElement("div");
        MECPopup.newdiv.id = "mec_popup_div";
        MECPopup.newdiv.style.width = width + "px";
        //MECPopup.newdiv.style.height = height + 62 + "px";
        MECPopup.newdiv.style.position = "absolute";
        MECPopup.top = (top - height - 60 + MECPopup.difftop);
        MECPopup.newdiv.style.top = MECPopup.top + "px";
        MECPopup.left = left;
        //MECPopup.left = (left - (width / 2 - 58));
        MECPopup.newdiv.style.left = MECPopup.left + "px";
        MECPopup.newdiv.style.background = "none";
        MECPopup.newdiv.style.zIndex = "20001";
        MECPopup.newdiv.style.cursor = "default";
        MECPopup.newdiv.onselectstart = function () { return false; };


        MECPopup.newdiv_title = document.createElement("div");
        MECPopup.newdiv_title.id = "mec_popup_title";
        MECPopup.newdiv_title.className = "mec_popup_title";
        //MECPopup.newdiv_title.style.width = (width - 3) + "px";
        //MECPopup.newdiv_title.style.height = "31px";
        //MECPopup.newdiv_title.style.background = "#F9F9F9";
        //MECPopup.newdiv_title.style.border = "1px solid #979797";


        //        var close_str = '<div onclick="MECPopup.closepopup();" style=" width:30px; height:30px; float:right;line-height:30px; text-align:inherit; cursor:pointer;">';
        //        close_str += '<img style=" width:18px; height:18px; margin-top:6px;" src="theme/default/img/close.png" />';
        //        close_str += '</div>';

        //        MECPopup.newdiv_title.innerHTML = close_str;
        MECPopup.newdiv.appendChild(MECPopup.newdiv_title);

        MECPopup.newdiv_content = document.createElement("div");
        MECPopup.newdiv_content.id = "mec_popup_content";
        MECPopup.newdiv_content.className = "mec_popup_content";
        //MECPopup.newdiv_content.style.width = (width - 3) + "px";
        MECPopup.newdiv_content.style.height = height + "px";
        //MECPopup.newdiv_content.style.borderLeft = "1px solid #979797";
        //MECPopup.newdiv_content.style.borderRight = "1px solid #979797";
        //MECPopup.newdiv_content.style.borderBottom = "1px solid #979797";
        //MECPopup.newdiv_content.style.background = "white";
        MECPopup.newdiv.appendChild(MECPopup.newdiv_content);


        var newdiv_bottom = document.createElement("div");
        newdiv_bottom.className = "mec_popup_bottom";
        //newdiv_bottom.style.width = "100%";
        //newdiv_bottom.style.height = "31px";

        //var bottom_str = '<img src="../Map/theme/default/img/iw_tail.png"';
        //bottom_str += 'width="58" height="31" style=" position:absolute; left: ' + (width / 2 - 58) + 'px; top: ' + (height + 30) + 'px;"';
        //bottom_str += '</div>';

        //newdiv_bottom.innerHTML = bottom_str;

        MECPopup.newdiv.appendChild(newdiv_bottom);

        return MECPopup;

    },
    showpopup: function (feature) {
        if (MECPopup.map_div && MECPopup.newdiv) {
            MECPopup.newdiv.feature = feature;
            document.getElementById(MECPopup.map_div).appendChild(MECPopup.newdiv);
        }
    }, closepopup: function () {
        if (MECPopup.newdiv) {

            //MECPopup.newdiv.remove();
            document.getElementById(MECPopup.map_div).removeChild(MECPopup.newdiv);
            this.newdiv = null;
            this.newdiv_title = null;
            this.newdiv_content = null;
        }
    }, setTitle: function (popup_title) {
        if (this.newdiv_title) {
            //var close_str = '<div onclick="MECPopup.closepopup();" style=" width:30px; height:30px; float:right;line-height:30px; text-align:inherit; cursor:pointer;">';
            //close_str += '<img style=" width:18px; height:18px; margin-top:6px;" src="../Map/theme/default/img/close.png" />';
            //close_str += '</div>';

            //标题
            var newdiv_title_name = document.createElement("span");
            newdiv_title_name.innerHTML = popup_title;
            this.newdiv_title.appendChild(newdiv_title_name);

            //关 闭按钮
            var newdiv_title_close = document.createElement("a");
            newdiv_title_close.innerHTML = "X";
            newdiv_title_close.setAttribute("href", "javascript:void(0);");
            newdiv_title_close.setAttribute("onclick", "MECPopup.closepopup();");

            this.newdiv_title.appendChild(newdiv_title_close);
            //this.newdiv_title.innerHTML = popup_title + close_str;
        }
    }, setContent: function (popup_content) {
        if (this.newdiv_content) {
            this.newdiv_content.innerHTML = popup_content;
        }
    }, setPosition: function (left, top) {
        if (this.newdiv) {
            this.newdiv.style.top = (top - this.newdiv.style.height - 62) + "px";
            //this.newdiv.style.left = (left - (this.newdiv.style.width / 2 - 58)) + "px";
            this.newdiv.style.left = left + "px";
        }
    }, reSetLocation: function () {
        if (this.newdiv && this.newdiv.feature) {

            var view_xy = MecMap.map.getViewPortPxFromLonLat(this.newdiv.feature.lonlat);
            var x = view_xy.x;
            var y = view_xy.y;
            if (this.newdiv.feature.icon) {
                y = y + this.newdiv.feature.icon.offset.y;
            }

            this.top = (Math.round(y) - this.height + MECPopup.difftop);
            this.newdiv.style.top = this.top + "px";

            this.left = (Math.round(x) );
            this.newdiv.style.left = this.left + "px";
        }
    }
}
