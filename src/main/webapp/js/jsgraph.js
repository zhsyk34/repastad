//画虚线
CanvasRenderingContext2D.prototype.dashedLine = function(x1, y1, x2, y2, dashLen) {
    if (dashLen == undefined) dashLen = 2;
    
    this.beginPath();
    this.moveTo(x1, y1);
    
    var dX = x2 - x1;
    var dY = y2 - y1;
    var dashes = Math.floor(Math.sqrt(dX * dX + dY * dY) / dashLen);
    var dashX = dX / dashes;
    var dashY = dY / dashes;
    
    var q = 0;
    while (q++ < dashes) {
     x1 += dashX;
     y1 += dashY;
     this[q % 2 == 0 ? 'moveTo' : 'lineTo'](x1, y1);
    }
    this[q % 2 == 0 ? 'moveTo' : 'lineTo'](x2, y2);
    
    this.stroke();
    this.closePath();
};


var jsgraph_graphs = new Array();
var jsgraph_heightSpacing = 10;

var jsgraph_bottomSpace = 13;
var jsgraph_leftSpace = 45;
var jsgraph_rightSpace = 6;
var jsgraph_textcol = "rgb(0,0,0)";
var jsgraph_linecol = "rgb(240,240,240)";
var jsgraph_keyposition = "right";
var jsgraph_barwidth = 1;
var jsgraph_showtime = 1000;
function forDight(Dight, How) {
    Dight = Math.round(Dight * Math.pow(10, How)) / Math.pow(10, How);
    return Dight;
}
// Shallow merge functon
function jsg_shallow_merge(a, b) {
    var c = {};
    for (x in a) {
        c[x] = a[x]
    }
    for (x in b) {
        c[x] = b[x]
    }
    return c;
}

function Point(x, y, colour, label) {
    this.x = x;
    this.y = y;
    this.colour = colour;
    this.label = label;
}

function Series(name, colour) {
    this.name = name;
    this.colour = colour;
    this.points = new Array();
}

function Graph(title, canvasN, type, middleval, local) {

    this.setMiddleVal = function(num) {
        this.middleval = num;
    }
    this.defaultOptions = {
        "type": "bar",
        "barOverlap": false,
        "barWidth": 1,
        "vstep": "auto",
        "vstart": "auto",
        "vfinish": "auto",
        "hstart": "auto",
        "hfinish": "auto",
        "data": null,
        "title": "",
        "xlabel": "",
        "fillColor": "",
        "canvasName": null
    }

    if (typeof(title) == 'object' && title != null) {
        this.options = jsg_shallow_merge(this.defaultOptions, title);

    } else {
        this.options = this.defaultOptions;
        this.options.title = title;
        this.options.canvasName = canvasN;
        this.options.type = type;
        this.middleval = middleval;
    }

    this.series = new Array();
    this.lastSeries = new Series('', 'red');
    this.series[this.series.length] = this.lastSeries;
    this.keypos = jsgraph_keyposition; //right
    this.start_time = new Date().getTime();

    this.addSeries = function(name, colour) {
        this.series[this.series.length] = new Series(name, colour);
        this.lastSeries = this.series[this.series.length - 1];
    }

    this.addPoint = function(x, y, label) {
        this.lastSeries.points[this.lastSeries.points.length] = new Point(x, y, this.lastSeries.colour, label);
    }

    //计算Y轴起始点
    this.vmin = function() {
        if (this.options.vstart != "auto" && !isNaN(this.options.vstart)) {
            return this.options.vstart;
        }
        var min = 1000000;
        for (q = 0; q < this.series.length; q++) {
            var ser = this.series[q];
            for (m = 0; m < ser.points.length; m++) {
                if (ser.points[m].y < min) min = ser.points[m].y;
            }
        }
        if (this.options.type == "bar" && min > 0) {
            // Hack for bar charts, this could be handled much better.
            min = 0;
        }
        return min;
    }
    //计算Y轴最高点
    this.vmax = function() {
        if (this.options.vfinish != "auto" && !isNaN(this.options.vfinish)) {
            return this.options.vfinish;
        }
        var max = -1000000;
        for (q = 0; q < this.series.length; q++) {
            var ser = this.series[q];
            for (m = 0; m < ser.points.length; m++) {
                if (ser.points[m].y > max) max = ser.points[m].y;
            }
        }
        return max;
    }
    //计算X轴起始点
    this.min = function() {
        if (this.options.hstart != "auto" && !isNaN(this.options.hstart)) {
            return this.options.hstart;
        }
        var min = 1000000;
        for (q = 0; q < this.series.length; q++) {
            var sers = this.series[q];
            for (m = 0; m < sers.points.length; m++) {
                if (sers.points[m].x < min) min = sers.points[m].x;
            }
        }
        return min;
    }
    //计算X轴最大值
    this.max = function() {
        if (this.options.hfinish != "auto" && !isNaN(this.options.hfinish)) {
            return this.options.hfinish;
        }
        var max = -1000000;
        for (q = 0; q < this.series.length; q++) {
            var ser = this.series[q];
            for (m = 0; m < ser.points.length; m++) {
                if (ser.points[m].x > max) max = ser.points[m].x;
            }
        }
        return max;
    }
    //计算X轴长度
    this.range = function() {
        var min = this.min();
        var max = this.max();
        if (max - min == 0) return 1;
        return max - min;
    }
    //计算Y轴长度
    this.vrange = function() {
        var min = this.vmin();
        var max = this.vmax();
        if (max - min == 0) return 1;
        return max - min;
    }

    this.draw = function() {
        var canvas = document.getElementById(this.options.canvasName);
        var cont;
        //判断浏览器是否支持
        if (canvas.getContext) {
            cont = canvas.getContext('2d');
            //Draw the border of the graph
        } else {
            var errorElem = document.createElement("span");
            errorElem.setAttribute("jsgraph-error");
            errorElem.innerHTML = "Your Browser Doesn't support the &lt;canvas&gt; element, You should upgrade your web browser";
            canvas.parentNode.appendChild(errorElem);
        }

        // Clear the canvas画背景色
        if (this.options.fillColor != "") {
            var origFil = cont.fillStyle;
            cont.fillStyle = this.options.fillColor;
            //canvas.width-leftSpace-rightSpace
            cont.fillRect(0, 0, canvas.width, canvas.height);
            //cont.fillStyle = "black";
            //cont.fillRect(40,18,canvas.width-leftSpace-rightSpace,canvas.height-18-bottomSpace);
            cont.fillStyle = origFil;
        } else {
            canvas.width = canvas.width;
        }
        cont.font = "11px sans-serif";
        cont.textBaseline = "top";

        var vRange = this.vrange();
        var bottomSpace = jsgraph_bottomSpace;

        if (this.options.xlabel != "") {
            bottomSpace += 13;
        }
        //垂直比例
        var vScale = 1;
        var vMin = this.vmin();
        if (this.options.type != 'line') {
            vScale = (canvas.height - 18 - bottomSpace) / this.vrange();
            //vScale = forDight(vScale,2);
        } else {
            //here modify
            var middle = forDight(this.middleval, 2);
            var low = this.vmin();
            var high = this.vmax();
            var maxvalue = 0; //间距最大值
            var minvalue = 0;
            var maxrange = 0;
            if (Math.abs(middle - high) > Math.abs(middle - low)) {
                maxrange = Math.abs(middle - high);
            } else {
                maxrange = Math.abs(middle - low);
            }
            maxrange = forDight(maxrange, 2);
            vMin = forDight(middleval - maxrange, 2);
            vMax = forDight(middleval + maxrange, 2);
            vScale = (canvas.height - 18 - bottomSpace) / (maxrange * 2);
        }
        //var pScale = vScale * Math.min((new Date().getTime()-this.start_time)/1000,1);

        var leftSpace = jsgraph_leftSpace;
        var rightSpace = jsgraph_rightSpace;

        //计算左边间距
        if (this.options.type == 'line') {
            if (leftSpace < cont.measureText(vMin + maxrange * 2).width) leftSpace = 45;
        } else {
            if (leftSpace < cont.measureText(vMin + vRange).width) leftSpace = cont.measureText(vMin + vRange).width + 5;
        }
        leftSpace = 70;
        if (this.options.type == "bar") {
            var hScale = (canvas.width - leftSpace - rightSpace) / (this.range() + 1);
        } else {
            var hScale = (canvas.width - leftSpace - rightSpace) / (this.range());
        }
        if (local == 'tw') {
            hScale = (canvas.width - leftSpace - rightSpace) / 260;
        } else if (local == 'cn') {
            hScale = (canvas.width - leftSpace - rightSpace) / 242;
        }

        var hMin = this.min();
        var spacing = jsgraph_heightSpacing;

        //画标题和标签 title & Labels
        cont.textAlign = "center";
        cont.fillStyle = jsgraph_textcol;
        cont.fillText(this.options.title, (canvas.width - rightSpace - leftSpace) / 2, 1);

        cont.textBaseline = "bottom";
        cont.fillText(this.options.xlabel, (canvas.width - rightSpace - leftSpace) / 2, canvas.height - 2);

        cont.textAlign = "left";
        //计算垂直间距
        if (this.options.vstep != "auto" && !isNaN(this.options.vstep)) {
            spacing = this.options.vstep;
        } else {
            while (vRange / spacing >= 10) {
                spacing *= 10;
            }
            while (vRange / spacing <= 2) {
                if (spacing > 1) spacing *= 0.1;
                else spacing *= 0.5;
            }
        }
        if (this.options.type == 'line') {
            //走势图
            spacing = forDight(maxrange / 3, 3);
        } else {
            //柱状
            spacing = forDight(vRange / 4, 2);
        }
        //画分割线
        if (this.options.type != 'line') {
            for (i = vMin; i <= vMin + vRange; i += spacing) {
                var y = (canvas.height - bottomSpace) - (i) * vScale + (vMin * vScale);
                cont.textBaseline = "middle";
                cont.textAlign = "right";
                cont.fillStyle = jsgraph_textcol;
                var verticalVal = forDight(i, 0);
                cont.fillText(verticalVal, leftSpace - 2, y);
                cont.fillStyle = jsgraph_linecol;
                if (i != vMin && i != vMin + vRange) {
                    cont.strokeStyle = "rgb(220,220,220)";
                    cont.beginPath();
                    cont.moveTo(leftSpace, y);
                    cont.lineTo(canvas.width - rightSpace, y);
                    cont.stroke();
                    cont.strokeStyle = "rgb(0,0,0)";
                }
            }
        } else {
      		var lineIndex = 0;
            for (var j = vMin; j <= vMax + 2; j += spacing) {
                var y = (canvas.height - bottomSpace) - (j) * vScale + (vMin * vScale);
                cont.textBaseline = "middle";
                cont.textAlign = "right";
                cont.font = "15pt Calibri";
                cont.fillStyle = jsgraph_textcol;
                j = forDight(j, 2);
                if (j < middle) {
                    cont.fillStyle = "rgb(0,255,0)";
                } else if (j > middle) {
                    cont.fillStyle = "rgb(255,0,0)";
                }
                var wucha = forDight(Math.abs(j - middle), 2);
                var verticalVal = j + '';
                if (verticalVal.substring(verticalVal.indexOf('.')).length < 3) {
                    verticalVal += '0';
                }
                if (wucha < 0.02 || wucha == 0) {
                    cont.fillText(middle + '', leftSpace - 2, y);//寫中間值
                } else {
                    cont.fillText(verticalVal + '', leftSpace - 2, y);//其他值
                }
                //画横X轴线(非红色)
                if(lineIndex==1||lineIndex==2|| lineIndex==4|| lineIndex==5 ){
                 //cont.beginPath();
                 //cont.moveTo(leftSpace, y);
                 //cont.lineTo(canvas.width - rightSpace, y);
                 //cont.stroke();
                 cont.dashedLine(leftSpace, y, canvas.width - rightSpace, y,2);
                }
                lineIndex++;
                //cont.strokeStyle = "rgb(0,0,0)";
                //画中轴横线(红色)
                if (wucha < 0.02 || wucha == 0) {
                    cont.strokeStyle = "rgb(255,0,0)";
                    cont.beginPath();
                    cont.moveTo(leftSpace, y);
                    cont.lineTo(canvas.width - rightSpace, y);
                    cont.stroke();
                    cont.strokeStyle = "rgb(0,0,0)";
                }
            }
        }
        //画水平底线 
        if (this.options.type == 'line') {
            var hdistance = 0;
            cont.textBaseline = "top";
            cont.textAlign = "center";
            cont.fillStyle = jsgraph_textcol;
            var timeArray = null;
            if (local == 'cn') {
                timeArray = ['9:30', '10:30', '11:30/13:00', '14:00', '15:00'];
                hdistance = (canvas.width - rightSpace - leftSpace) / 4;
            } else if (local == 'tw') {
                timeArray = ['9:00','10:00','11:00','12:00','13:00', '13:30'];
                hdistance =  (canvas.width - rightSpace - leftSpace) /4.5;
                //timeArray = ['', '', '', '', ''];
            }
            var startP = leftSpace;
            var testvar = '';
            cont.font = "8pt Calibri";
            for (var m = 1; m < timeArray.length + 1; m++) {
                if (m == 1) {
                    startP += 10;
                } else if (m == timeArray.length) {
                    //startP -= 10;
                }
                cont.fillText(timeArray[m - 1], startP, canvas.height - bottomSpace + 2);
                //画垂直分割线
                if (m == 1) {
                    startP -= 10;
                }
                if(m!=1 && m!=6 ){
	                cont.strokeStyle = "rgb(255,0,0)";
	                //cont.beginPath();
	                //cont.moveTo(startP, canvas.height - bottomSpace);
	                //cont.lineTo(startP,18);
	                //cont.stroke();
	                cont.dashedLine(startP, canvas.height - bottomSpace, startP, 18, 2)
	                cont.strokeStyle = "rgb(0,0,0)";
                }
				startP += hdistance;
            }
            //台股多画13:30
            if(local == 'tw'){
	            cont.fillText(timeArray[5], startP-hdistance*1.6, canvas.height - bottomSpace + 2);
            }
        }
        //连线
        if (this.options.type != 'line') {
            for (s = 0; s < this.series.length; s++) {
                var series = this.series[s];
                cont.beginPath();
                for (p = 0; p < series.points.length; p++) {
                    var curr = series.points[p];
                    //Move point into graph-space
                    var height = canvas.height;
                    var y = (canvas.height - bottomSpace) - (curr.y) * vScale + (vMin * vScale);
                    //alert(y+","+curr.y);
                    //var yp = (canvas.height-bottomSpace) - (curr.y)*pScale + (vMin*pScale);
                    var x = hScale * (curr.x - hMin) + leftSpace;

                    if (this.options.type == "bar") {
                        cont.fillStyle = curr.colour;
                        var barwidth = hScale;
                        if (this.options.barWidth != null && this.options.barWidth <= 1) {
                            barwidth *= this.options.barWidth;
                        }
                        var baroffs = ((this.options.barWidth < 1) ? ((1 - this.options.barWidth) / 2) * hScale: 0);
                        barwidth /= (this.options.barOverlap ? 1 : this.series.length);
                        var seriesWidth = (!this.options.barOverlap ? barwidth: 0);
                        var recHeight = forDight(curr.y * vScale, 2);
                        cont.fillRect((x + baroffs) + seriesWidth * s, y, barwidth, (curr.y * vScale));
                        cont.fillStyle = "rgb(0,0,0)";
                    }
                    //Add label for this point
                    cont.textBaseline = "top";
                    cont.textAlign = "center";
                    cont.fillStyle = jsgraph_textcol;
                    if (curr.label == null) cont.fillText(curr.x, x, canvas.height - bottomSpace + 2);
                    else cont.fillText(curr.label, x, canvas.height - bottomSpace + 2);
                    cont.fillStyle = jsgraph_linecol;
                }
                cont.stroke();
            }
        } else {

            for (s = 0; s < this.series.length; s++) {
                var series = this.series[s];
                cont.beginPath();
                for (p = 0; p < series.points.length; p++) {
                    var curr = series.points[p];
                    //Move point into graph-space
                    var height = canvas.height;
                    var y = (canvas.height - bottomSpace) - (curr.y) * vScale + (vMin * vScale);
                    //var yp = (canvas.height-bottomSpace) - (curr.y)*pScale + (vMin*pScale);
                    var x = hScale * (curr.x - hMin) + leftSpace;
					cont.lineWidth=3;
                    if (this.options.type == "line" || this.options.type == "scatter") {
                        if (this.options.type == "line") {
                            cont.lineTo(x, y);
                        }
                        cont.fillStyle = curr.colour;
                        //cont.fillRect(x-2,y-2,4,4);
                        cont.fillStyle = "rgb(0,0,0)";
                    }
                }
                cont.stroke();
            }
        }

        //Draw border of graph
        cont.lineWidth=1;
        cont.strokeRect(leftSpace, 18, canvas.width - leftSpace - rightSpace, canvas.height - 18 - bottomSpace);
        //alert('draw rectant');
        //cont.fillRect(leftSpace,18,canvas.width-leftSpace-rightSpace,canvas.height-18-bottomSpace);
        //If the graph isn't finish drawing, re-add a timeout
        /*if(new Date().getTime() < this.start_time+jsgraph_showtime)
		{
			setTimeout("this.draw();", 100);
		}*/
    }
    //Add graph to jsgraph_graphs list
    jsgraph_graphs[0] = this;
}

function log(str) {
    if (document.getElementById('jsgraph_out')) document.getElementById('jsgraph_out').innerHTML += str + "<br>";
}

function jsgraph_begin() {
    draw();
}

function draw() {
    for (g = 0; g < jsgraph_graphs.length; g++) {
        jsgraph_graphs[g].draw();
    }
}