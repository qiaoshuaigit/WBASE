//check which browser the client using.
var springweb_typeIsIE = false;
var springweb_typeIsGecko = false;
var springweb_typeIsWebkit = false;

var springweb_typeIsIE6 = false;
var springweb_typeIsIE7 = false;
var springweb_typeIsIE8 = false;
var springweb_typeIsFireFox = false;
var springweb_typeIsChrome = false;
var springweb_typeIsSafari = false;

var agent = window.navigator.userAgent;

if (agent.indexOf("MSIE 6") != -1) {
    springweb_typeIsIE6 = true;
    springweb_typeIsIE = true;
}
else if (agent.indexOf("MSIE 7") != -1) {
    springweb_typeIsIE7 = true;
    springweb_typeIsIE = true;
}
else if (agent.indexOf("MSIE 8") != -1) {
    springweb_typeIsIE8 = true;
    springweb_typeIsIE = true;
}
else if (agent.indexOf("Firefox") != -1) {
    springweb_typeIsFireFox = true;
    springweb_typeIsGecko = true;
} else if (agent.indexOf("Chrome") != -1) {
    springweb_typeIsChrome = true;
    springweb_typeIsWebkit = true;
}
else if (agent.indexOf("Safari") != -1) {
    springweb_typeIsSafari = true;
    springweb_typeIsWebkit = true;
}

orgAlert = window.alert;

function richAlert(options) {
    var defaults = {
        msg : "",
        title : "提示信息",
        type : "info", //info or error
        moveable : true
    };
    var viewFlag = true;
    var thisDocument = document;
    if(document.body.clientWidth == 0) {
        thisDocument = parent.document;
        viewFlag = false;
    }
    var lastOverFlow = thisDocument.body.style.overflow;
    try {
        var params = extendMerger(defaults, options);
        if(thisDocument.body.clientWidth == 0) {
            orgAlert(params.msg);
            return;
        }
        thisDocument.body.style.overflow = "hidden";
        var divBackground = thisDocument.createElement("div");
        divBackground.style.position = "absolute";
        divBackground.style.left = "0px";
        divBackground.style.top = thisDocument.body.scrollTop +  "px";
        divBackground.style.width = (thisDocument.documentElement.clientWidth || thisDocument.body.clientWidth);
        divBackground.style.height = "100%";
        if (springweb_typeIsChrome || springweb_typeIsFireFox) {
            divBackground.style.backgroundColor = "rgba(0,0,0,0.7)";
        } else {
            divBackground.style.backgroundColor = "#EEEEEE";
            divBackground.style.filter = "alpha(opacity=60)";
        }
        divBackground.style.zIndex = "999998";
        thisDocument.body.appendChild(divBackground);

        var divDialog = thisDocument.createElement("div");
        var dialogWidth = 260;
        var dialogHeight = 120;
        var fontSize = 14;
        //build the ui components.
        var maxMsgLength = 0;
        var msgs = params.msg.replace(/\n/g, "^").split("^");
        for(var i = 0; i < msgs.length; i ++) {
           if(msgs[i].length > maxMsgLength) {
               maxMsgLength = msgs[i].length;
           }
        }
        var lineWidth = thisDocument.body.clientWidth * 0.7;
        if(fontSize * maxMsgLength < lineWidth) {
            dialogWidth = parseInt(fontSize * maxMsgLength) + 50;
        }
        else {
            dialogWidth = parseInt(lineWidth);
            dialogHeight += parseInt(((fontSize * maxMsgLength)/lineWidth) * fontSize);
        }
        divDialog.style.width = dialogWidth + "px";
        divDialog.style.height = dialogHeight + "px";
        divDialog.style.position = "absolute";
        divDialog.style.border = "1px solid #C0D7FA";
        divDialog.style.borderRight = "2px outset #DEDEDE";
        divDialog.style.borderLeft = "2px outset #DEDEDE";
        divDialog.style.left = ((thisDocument.body.clientWidth / 2) - (dialogWidth / 2)) + "px";
        divDialog.style.top = (thisDocument.body.scrollTop + (thisDocument.body.clientHeight / 2) - (dialogHeight / 2) - 20) + "px";
        divDialog.style.zIndex = "999999";

        var divHead = thisDocument.createElement("div");
        var headText = thisDocument.createElement("span");
        headText.innerText = params.title;
        headImg = thisDocument.createElement('div');
        headImg.style.backgroundImage = "url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABYAAAAUCAYAAACJfM0wAAAACXBIWXMAAAsTAAALEwEAmpwYAAAABGdBTUEAALGOfPtRkwAAACBjSFJNAAB6JQAAgIMAAPn/AACA6QAAdTAAAOpgAAA6mAAAF2+SX8VGAAAD0klEQVR42pSVfWiVVRzHP+c8z33uvXPXpu7N3TmnNdTUNHUkmPnW7ObamhUEK5qZTPQfkSgzaolBEAijfwIJooQgQoKQoIiMIjNrbL7VlmSNrW3N3bs5d992n/ucX39cpWZ3Zl84nHP4fs+H7/nnHPXJ51H+qcatc8inlp2HU5bl4913Dgbz+Se+iE3Za6UUN8Z00B3P7Zcnn5gTeHATgWdbX5N8mYa6qWe11hqtNQ11s/NCd+05Ig9tKWbbtlU0N6+kZn6C/QeO/SdcG+NhjMd0CjoDPBxZDN4okKLp0SpiI+enzYsIIoIWkX9d44aeanlV6iMVFM0uJN7TSfLSRe5eNovalZo9+97O27pxa3Gu8Y3FzXr50HGZX5li44ZqsrE+EBc34+JdG2F7QzFu6jK3kp7O6O/rZHvDXAJ+QyY6ROFdcwlVlxDvH6UyLGxaJ+zcfWTa1nY+Y98L78nSRRnW1JaSGR5keCzOlSsT+H0e82cGyUST1G+0+eb07/+v8fjoTzzeMBeVTWNl03z69SXWb2mnueUYyu/iJhRFIUPTZsPTO9+Q2wLv2vuWrFujqFkcIjEUw5qhUbbBdT1S6Szal8UKWKTH4IFVE4SLem+vsWV6aagvQZIJJuMpmCnMmeWA0iAeMtmLX74ieW2QGeYMkdUdNLe0yS3BO1rflLoNfsorLa4NxAmEFIhLyR1ZtNJ4bgI9fhIV/xa/6SaVCnN/TQ+LynrY+/z7khf8yusfS8j/B5HNM8jEXDyxCATHIPYlVaFTrF5iuKdGY5NF3ACO14srFficChrv7WB85GL+xr2/nWN7xKEwZBEf9SjwdcHV43D1e0JOgg21NmuX+9CeBa5GZV387i+k1FpWVI6wZt4FWvcdlSngF9s+koUVUdbfFyIVy6DTp3FSJ5B0FHCYiGvOdRu6uj2yGYUyCjwbnbyMEgX+ZTQtP487fmFq46H+Lh6LFOJTwyRj3QQ5Ax6IKSCbtKgqdfigPcTRwyH8ysZNW+D5wDVYExfwgqtYUKrYcudZntndLgDWaKI6vWJhn/3IxkuY0R9w3QIsbeNJBYYSRJWhrTJCgTIKnHI8XYayysEuRzlhlA6g7WL05CCVBUP8+GuA734OHrSj0SGaai+jsydBMhQ5fWAsUAo8BZ7k5rQGDxwBzHXPCBgNYx2AYnahYknpFc6O/IkdDs9Tn50KEL+6EkRjjAIDoEAAAyKA5EBK1PX3EYxRaHJ5rYRkxqZzIEx4aZlSIsKBtg9Tg4MDKJ37SRDJNb5ZKgcEya35OyPGoG2b6qoFHHqpMfjXAGZWlOiGhDgMAAAAAElFTkSuQmCC)";
        headImg.style.backgroundRepeat = "no-repeat";
        headImg.style.top = "2px";
        headImg.style.left = "2px";
        headImg.style.width = "100%";
        headImg.style.height = "25px";
        headImg.style.position = "absolute";
        headImg.width = 22;
        headImg.height = 20;
        divHead.appendChild(headImg);
        headText.style.position = "absolute";
        headText.style.left = "18px";
        headText.style.fontSize = "12px";
        headText.style.fontWeight = "bold";
        headText.style.color = "white";
        headText.style.textIndent = "10px";
        divHead.appendChild(headText);
        
        divHead.style.width = "100%";
        divHead.style.height = "25px";
        divHead.style.lineHeight = "25px";
        divHead.style.fontSize = "14px";
        divHead.style.fontWeight = "bold";
        divHead.style.borderBottom = "1px outset #8989FF";
        divHead.style.color = "white";
        divHead.style.textIndent = "10px";
        divHead.style.backgroundColor = "blue";
        if(params.type.toLowerCase() == "error") {
           // divHead.style.backgroundImage = "url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAAsCAIAAAArRUU2AAAATklEQVR4nIWLuw2AMBBDjVuQiBT2oWbRDATrnB0KQOJoqPzRe3BrHI6dcBASYREKovtK6/6DsDOX+stN+3H1YX9ciRgnYq5EWYhS2dftBIuLT4JyIrPCAAAAAElFTkSuQmCC)";
           divHead.style.filter =
            "progid:DXImageTransform.Microsoft.Gradient(gradientType=1,startColorStr=#C91E14,endColorStr=#FFFFFF)";
        }
        else {
            divHead.style.backgroundImage = "url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAAZCAIAAAB/8tMoAAAABGdBTUEAAK/INwWK6QAAABl0RVh0U29mdHdhcmUAQWRvYmUgSW1hZ2VSZWFkeXHJZTwAAABQSURBVHjaHIjRFYAgDMTag6J1/y1cxmkUC4KnH3nJC/aj4gzB1SauII3dB2oX3HxBxzPRh0BFYElQsmJJhHaTvBI3zV4Um/2PBrzI168AAwBcLhvehgR+5gAAAABJRU5ErkJggg==)";
        }
        if(params.moveable) {
            var x0=0,y0=0,x1=0,y1=0;
            var offx=3,offy=3;
            divHead.style.cursor = "move";
            divHead.onmousedown = function() {
                objEvent = null;
                if(!viewFlag) {
                    objEvent = parent.window.event;
                }
                else {
                    objEvent = event;
                }
                if(objEvent.button == 1) {
                    x0 = objEvent.clientX;
                    y0 = objEvent.clientY;
                    x1 = parseInt(divDialog.style.left);
                    y1 = parseInt(divDialog.style.top);
                    divDialog.dragging = true;
                }
            };
            divHead.onmouseup = function() {
                divDialog.dragging = false;
            };

            thisDocument.body.onmousemove = function() {
                if (!divDialog.dragging) return;
                objEvent = null;
                if(!viewFlag) {
                    objEvent = parent.window.event;
                }
                else {
                    objEvent = window.event;
                }
                e = objEvent;
                xx = x1 + e.clientX - x0;
                yy = y1 + e.clientY - y0;
                if(xx > 0 && xx < thisDocument.body.clientWidth - dialogWidth) {
                    divDialog.style.left = (x1 + e.clientX - x0) + "px";
                }
                if(yy > 0 && yy < thisDocument.body.clientHeight - dialogHeight) {
                    divDialog.style.top  = (y1 + e.clientY - y0) + "px";
                }
            };
        }

        divDialog.appendChild(divHead);
        var divContent = thisDocument.createElement("div");
        divContent.style.textAlign = "center";
        divContent.style.padding = "15px";
        divContent.style.fontSize = "12px";
        divContent.style.borderBottom = "1px outset #8989FF";

        if (springweb_typeIsIE) {
            divContent.style.height = parseInt(dialogHeight - 25) + "px";
        } else {
            divContent.style.height = parseInt(dialogHeight - 55) + "px";
        }

        divContent.style.backgroundColor = "#ffffff";
        if (springweb_typeIsIE6 || springweb_typeIsIE7 || springweb_typeIsIE8) {
            divContent.style.filter =
            "progid:DXImageTransform.Microsoft.Gradient(gradientType=1,startColorStr=#FFFFFF,endColorStr=#C2E2F8)";
        } else if (springweb_typeIsFireFox) {
            divContent.style.backgroundImage =
            "-moz-linear-gradient(left,rgba(255,255,255,1),rgba(194,226,248,1))";
        } else if (springweb_typeIsWebkit) {
            divContent.style.backgroundImage =
            "-webkit-gardient(linear,0% 0%,100% 100%,from(#FFFFFF),to(#000000))";
        }
        if(params.msg.indexOf("\n") >= 0) {
            params.msg = params.msg.replace(/\n/g, "<br />");
        }
        if(params.msg.indexOf("^") >= 0) {
        	params.msg = params.msg.replace(/\^/g, "");
        }
        var msgTable = thisDocument.createElement("table");
        var msgCell = msgTable.insertRow().insertCell();
        msgCell.innerHTML = params.msg + "<br /><br />";
        divContent.appendChild(msgTable);
        //divContent.innerHTML = msg + "<br /><br />";
        divDialog.appendChild(divContent);
        /*var closeButton = thisDocument.createElement("img");
        closeButton.setAttribute('id', "myRichAlert");
        closeButton.style.cursor = "hand";
        closeButton.setAttribute("src", _application_root + "/images/okButton.png");
        closeButton.setAttribute("alt", "确定");
        */
        var closeButton = thisDocument.createElement("input");
        closeButton.setAttribute('id', "myRichAlert");
        closeButton.type = "button";
        //closeButton.value = "确  定";
        closeButton.style.fontFamily = "verdana";
        closeButton.style.border = "darkgray 0px solid";
        closeButton.style.fontSize = "9pt";
        closeButton.style.cursor = "hand";
        closeButton.style.height = "24px";
        closeButton.style.width = "70px";
        closeButton.style.backgroundColor = "white";
        //closeButton.style.backgroundImage = "url(" + _application_root + "/templets/lib/themes/default/button.gif)";
        closeButton.style.backgroundImage = "url(data:image/png;base64,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)";
        //the click event when the dialog is closing.
        closeButton.onclick = function() {
            thisDocument.body.removeChild(divBackground);
            thisDocument.body.removeChild(divDialog);
            thisDocument.body.style.overflow = "auto";
            if(lastOverFlow && "hidden" == lastOverFlow) {
                thisDocument.body.style.overflow = lastOverFlow;
            }
        };
        divContent.appendChild(closeButton);
        //divDialog.focus();
        thisDocument.body.appendChild(divDialog);
        //closeButton.focus();
    } catch (ex) {
        orgAlert(ex.name + ":" + ex.message + "\n" + params.msg);
    }
    closeBtn = thisDocument.getElementById("myRichAlert");
    if(closeBtn) closeBtn.focus();
}

window.alert = function(msg, title, type, moveable) {
	var tmsg = "" + msg;
    var params = {
        msg : tmsg,
        title : "提示信息",
        type : "info", //info or error
        moveable : true
    };
    if(title && "" != title) {
        params.title = title;
    }
    if(type && "" != type) {
        params.type = type;
    }
    if(moveable && "" != moveable) {
        params.moveable = moveable;
    }
    richAlert(params);
};
