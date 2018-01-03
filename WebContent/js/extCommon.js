_topBrhClass_ = "1";

/**
 * 上传文件
 * 参数：primaryKey 上传文件对应表记录的主键(业务关联信息)
 *      bussType 业务类型(区分不同业务的上传文件，需与后台定义一致)
 *      fileSizeLimit 文件大小上限，默认为"3 MB"
 *      fileTypes 文件类型，默认为"*.*"
 *      fileTypeDesc 文件类型描述，默认为"所有文件"
 */
function openUploadDialog(primaryKey, bussType, fileSizeLimit, fileTypes, fileTypeDesc) {
	if(!primaryKey || "" == primaryKey) {
		alert("上传文件对应表记录的主键不能为空");
		return;
	}
	if(!bussType || "" == bussType) {
		alert("上传文件对应的业务类型不能为空");
		return;
	}
	if(!fileSizeLimit || "" == fileSizeLimit) {
		fileSizeLimit = "3 MB";
	}
	if(!fileTypes || "" == fileTypes) {
		fileTypes = "*.*";
	}
	if(!fileTypeDesc || "" == fileTypeDesc) {
		fileTypeDesc = "所有文件";
	}
	fileSizeLimit = getEncodeStr(fileSizeLimit);
	fileTypes = getEncodeStr(fileTypes);
	fileTypeDesc = getEncodeStr(fileTypeDesc);
	var param = "?primaryKey=" + primaryKey + "&bussType=" + bussType + "&fileSizeLimit=" +
		fileSizeLimit + "&fileTypes=" + fileTypes + "&fileTypeDesc=" + fileTypeDesc;
	window.showModalDialog(_application_root + "/templets/common/SWFUploader.ftl" + param,
		"上传文件", "dialogHeight=260px;dialogWidth=504px;center=yes;toolbar=no;menubar=no;" +
		"scrollbars=no;resizable=no;location=no;status=no;edge:raised;unadorned:yes");
}

function setFocus(tableName, fieldName) {
	var tableElement = document.getElementById(tableName + "_Table");
	if(!tableElement) return;
	var editorName = "editor_" + fieldName;
	var fields = tableElement.getElementsByTagName("INPUT");
	
	for(i = 0; fields && i < fields.length; i ++) {
		if(editorName == fields[i].id) {
			fields[i].focus();
			return;
		}
	}
}

function setFocusToFirstCellOfNewRowForDataTabel(datatable) {
	if(!datatable) {
		return;
	}
	var row = datatable.rows[datatable.activeRowIndex];
	if(row) {
		row.cells[1].focus();
	}
}

function setReadOnlyForEmptyDataset(dataset) {
	if(!dataset) return;
	if(!dataset.record) {
		dataset.setReadOnly(true);
	}
	else {
		dataset.setReadOnly(false);
	}
}

function getRuntimeDesc(runtime) {
    var runtimeDesc = runtime;
    if(runtime == "90") runtimeDesc = "90-每日";
    else if(runtime == "93") runtimeDesc = "93-月末";
    else if(runtime == "94") runtimeDesc = "94-季末";
    else if(runtime == "95") runtimeDesc = "95-半年";
    else if(runtime == "96") runtimeDesc = "96-年末";
    else if(runtime == "99") runtimeDesc = "99-不执行";
    else if(runtime == "41") runtimeDesc = "41-星期一";
    else if(runtime == "42") runtimeDesc = "42-星期二";
    else if(runtime == "43") runtimeDesc = "43-星期三";
    else if(runtime == "44") runtimeDesc = "44-星期四";
    else if(runtime == "45") runtimeDesc = "45-星期五";
    else if(runtime == "46") runtimeDesc = "46-星期六";
    else if(runtime == "47") runtimeDesc = "47-星期七";
    else if(runtime >= 1 && runtime <= 31) {
    	runtimeDesc = runtime + "-指定日";
    }
	return runtimeDesc;
}

function extendMerger() {
    var options, name, src, copy, copyIsArray, clone,
        target = arguments[0] || {},
        i = 1,
        length = arguments.length,
        deep = false;

    if(typeof target === "boolean") {
        deep = target;
        target = arguments[1] || {};
		i = 2;
	}

    if(typeof target !== "object" && !(typeof target !== "function")) {
        target = {};
    }

    if(length === i) {
        target = this;
        --i;
    }

    for(; i < length; i++) {
        if((options = arguments[i]) != null) {
            for(name in options) {
                src = target[name];
                copy = options[name];
                if(target === copy) {
                    continue;
                }

                if(copy !== undefined) {
                    target[name] = copy;
                }
            }
        }
    }

	return target;
}

function showXDialog(options) {
    var defaults = {
        id : "XDialog",
        url : "",
        title : "XDialog",
        width : 800,
        height : 600,
        allowMove : true,
        allowPark : true,
        allowResize : false
    };
    window.scrollTo(0, 0);
    var params = extendMerger(defaults, options);
	var elementId = params.id + "_window";
	var zone = document.createElement('div');
	zone.setAttribute('id', elementId);
	zone.style.backgroundColor = '#EEEEEE';
	zone.style.position = "absolute";
	zone.style.zIndex = "49";
	zone.style.left = document.documentElement.clientWidth/2;
	zone.style.top = "0px";
	//zone.style.width = (document.documentElement.clientWidth || document.body.clientWidth);
	zone.style.width = (document.documentElement.clientWidth || document.body.scrollWidth) + 800;
	//zone.style.height = (document.documentElement.clientHeight || document.body.clientHeight);
	zone.style.height = (document.documentElement.clientHeight || document.body.scrollHeight) + 600;
	zone.style.filter = "alpha(opacity=60)";
	document.body.appendChild(zone);
	document.body.style.overflow = "hidden";
	
	var dialogTop = (((document.documentElement.clientHeight || document.body.clientHeight) - params.height)/2) >> 0;
	var dialogLeft = (((document.documentElement.clientWidth || document.body.clientWidth) - params.width)/2) >> 0;
	win = dhxWins.createWindow(params.id, dialogLeft, dialogTop, params.width, params.height);
	win.setModal(true);
	win.keepInViewport(true);
	if(params.allowMove) {
	    win.allowMove();
	}
	else {
	    win.denyMove();
	}
	if(params.allowPark) {
	    win.allowPark();
	}
	else {
	    win.denyPark();
	}
	if(params.allowResize) {
	    win.allowResize();
	}
	else {
	    win.denyResize();
	}
	win.button("help").hide();
	win.button("stick").hide();
	win.button("minmax1").hide();
	win.button("minmax2").hide();
	win.setText(params.title);
	win.attachURL(params.url);
	win.attachEvent("onClose",function(win) {
		var eventName=getElementEventName(zone, "onCloseCheck");
			if (isUserEventDefined(eventName)) {
				var event_result=fireUserEvent(eventName, [win]);
				if(typeof(event_result) == "boolean") {
					if(event_result == true) {
						document.body.removeChild($(elementId));
					} else {
						return false;
					}
				} else {
					document.body.removeChild($(elementId));
				}
			} else {
				document.body.removeChild($(elementId));
			}
			document.body.style.overflow = "auto";
			return true;
	});
	return win;
}

function XDocParams() {
	this.buffer = "";
	this.xdoc   = "";
	this.addParam = function(key, value) {
		if(this.buffer.length == 0) {
			this.buffer = key + "=" + value;
		}
		else {
			this.buffer += ("!!" + key + "=" + value);
		}
	}
	this.setXDoc = function(xdoc) {
		this.xdoc = "xdoc=" + xdoc;
	}
	/**
	 * 静态XDoc模板参数
	 */
	this.toStaticXDocParams = function() {
		return "params=" + getEncodeStr("xdoc?xdocType=1!!" + this.buffer + "!!" + this.xdoc);
	}
	/**
	 * 动态XDoc模板参数
	 */
	this.toDynamicXDocParams = function() {
		return "params=" + getEncodeStr("xdoc?xdocType=2!!" + this.buffer + "!!" + this.xdoc);
	}
}

