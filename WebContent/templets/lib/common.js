var _activeElement=null;
var _activeEditor=null;
var _activeTable=null;
var _dropdown_window=null;
var _isDropDownPage=false;

var _document_loading=false;
var _stored_element=null;
var _array_dataset=new Array();
var _tabset_list=new Array();
var _array_submitmanager=new Array();

var _oidmap=null;

var _skip_activeChanged=false;

function isFileIncluded(fileId){
	var included=false;
	eval("included=(typeof(_fileIncluded_"+fileId+")!=\"undefined\")");
	return included;
}

function getPlatform(){
	return window.clientInformation.platform;
}

function getIEVersion(){
	var index=window.clientInformation.userAgent.indexOf("MSIE");
	if (index<0){
		return "";
	}
	else{
		return window.clientInformation.userAgent.substring(index+5, index+8);
	}
}

function getRowByCell(cell){
	if(cell) {
		return cell.parentElement;
	}
}

function getTableByCell(cell){
	var row=getRowByCell(cell);
	if(row) {
		return getTableByRow(row);
	}
}

function getTableByRow(row){
	var tbody=row.parentElement;
	if (tbody) {
		if (tbody.parentElement) {
			return tbody.parentElement;
		}
		else {
			return row.table;
		}
	}
}

function getElementEventName(element, eventName){
	var result="";
	if (element.extra!="dockeditor")
		result=element.id+"_"+eventName;
	else{
		var holder=element.editorHolder;
		if (holder) result=holder.id+"_"+eventName;
	}
	return result;
}

var _user_events = new Object();

function isUserEventDefined(function_name){
	if (function_name=="") return false;
	var eventInfo=_user_events[function_name];
	if (eventInfo==null) {
		eventInfo=new Object();
		_user_events[function_name]=eventInfo;
	 	var script="eventInfo.defined=(typeof("+function_name+")!=\"undefined\");" +
	 		"if (eventInfo.defined) eventInfo.handle=" + function_name + ";";
	 	eval(script);
	}
	return eventInfo.defined;
}

function unfireUserEvent(function_name){
	var eventInfo=_user_events[function_name];
	if(eventInfo==null){
	}else{
		_user_events[function_name] = null;
	}
}

/*
function isUserEventDefined(function_name){
	if (function_name=="") return false;
	var result;
	eval("result=(typeof("+function_name+")!=\"undefined\");");
	return result;
}
*/

function fireUserEvent(function_name, param){
	var result;
	if (function_name=="") return;
	var eventInfo=_user_events[function_name];
	if (eventInfo==null) {
		if (!isUserEventDefined(function_name)) return;
		eventInfo=_user_events[function_name];
	}

	if (eventInfo!=null && eventInfo.defined) {
		result=eventInfo.handle(param[0], param[1], param[2], param[3]);
	}

/*	var paramstr="";
	for(i=0; i<param.length; i++){
		if (i==0)
		 	paramstr="param["+i+"]";
		 else
		 	paramstr=paramstr+",param["+i+"]";
	}

	if (isUserEventDefined(function_name)) {
		eval("result="+function_name+"("+paramstr+");");
	}
*/
	return result;
}

function processActiveElementChanged(activeElement){

	function isChildofTable(obj) {
		var result=null;
		var tmpObj;

		if (obj.getAttribute("extra")=="dockeditor")
			tmpObj=obj.editorHolder;
		else
			tmpObj=obj;

		if (tmpObj.getAttribute("extra")=="tablecell") result=getTableByCell(tmpObj);
		return result;
	}

	function set_activeEditor(editor){
		if (_activeEditor!=editor){
			if (_activeEditor){
				if (needUpdateEditor){
					if (_activeEditor.window==window)
						updateEditorInput(_activeEditor);
					else
						_activeEditor.window.updateEditorInput(_activeEditor);
				}
				if (typeof(hideDropDownBtn)!="undefined") hideDropDownBtn();

				switch (_activeEditor.getAttribute("extra")){
					case "editor":{
						_activeEditor.className="editor";
						break;
					}
					case "dockeditor":{
						hideDockEditor(_activeEditor);
						break;
					}
				}
				refreshElementValue(_activeEditor);
			}

			if (editor && !editor.readOnly){
				var field=getElementField(editor);

				if (editor.getAttribute("extra")=="editor" || editor.getAttribute("extra")=="dockeditor"){
					editor.className="editor_active";
					if (field){
						if (!editor.dataType) {
							editor.dataType=field.dataType;
						}
						if (!editor.editorType) {
							editor.editorType=field.editorType;
						}
					}

					if (!editor.getAttribute("dropDown") &&
						(editor.getAttribute("dataType")=="date" || editor.getAttribute("dataType")=="timestamp")){
						editor.dropDown="dropDownDate";
					}

					if ((editor.getAttribute("extra")=="editor" || editor.getAttribute("extra")=="dockEditor") && field){
						editor.maxLength=(field.size>0)?field.size:2147483647;
						if (field.size > 100 && compareText(editor.tagName, "textarea") &&
							!editor.getAttribute("dropDown")){
							_stored_element=editor;
							editor.editorType="textarea";
							setTimeout("showDockEditor(_stored_element);", 0);
						}
					}
				}

				refreshElementValue(editor);

				if (typeof(showDropDownBtn)!="undefined"){
					showDropDownBtn(editor);
				}
				var _dropdown=getEditorDropDown(editor);
				if (_dropdown){
					editor.contentEditable=(!isTrue(_dropdown.fixed));
					if (typeof(showDropDownBtn)!="undefined"){
						if (_dropdown && isTrue(_dropdown.autoDropDown)){
							setTimeout("showDropDownBox(_activeEditor);", 0);
						}
					}
				}
				else{
					editor.contentEditable=true;
				}

				if (!(_dropdown && isTrue(_dropdown.fixed)) &&
					!compareText(editor.type, "checkbox") &&
					compareText(editor.tagName, "input")) editor.select();
			}

			_activeEditor=editor;
		}
	}

	function processElementBlur(){
		var doblur=(activeElement!=_activeEditor);

		if (_activeElement){
			if (typeof(_dropdown_btn)!="undefined" && _dropdown_btn){
				doblur=doblur && (_activeElement!=_dropdown_btn) &&
					(activeElement!=_dropdown_btn);
			}

			if (typeof(_dropdown_box)!="undefined" && _dropdown_box){
				var editor=_dropdown_box.editor;
				doblur=doblur && (activeElement!=editor) &&
					(!isChild(activeElement, _dropdown_box));
			}

			if (doblur){
				if (_activeEditor && _activeEditor.dropDownVisible){
					if (typeof(hideDropDownBox)!="undefined") hideDropDownBox();
					hideStatusLabel(window);
				}
				set_activeEditor(null);
			}
		}
		else{
			doblur=false;
		}

		if (activeElement==document.body && _skip_activeChanged){
			_skip_activeChanged=false;
			return;
		}

		if ((doblur || !_activeEditor)){
			var activeTable=isChildofTable(activeElement);
			if (_activeTable!=activeTable){
				if (_activeTable){
					_activeTable.focused=false;

					var row=_activeTable.activeRow;
					if (row) refreshTableRowStyle(row);

					var eventName=getElementEventName(_activeTable, "onBlur");
					fireUserEvent(eventName, [_activeTable]);
				}

				_activeTable=activeTable;

				if (_activeTable){
					_activeTable.focused=true;

					var row=_activeTable.activeRow;
					if (row) refreshTableRowStyle(row);

					var eventName=getElementEventName(_activeTable, "onFocus");
					fireUserEvent(eventName, [_activeTable]);
				}
			}
		}
	}

	try{
		if (window.closed) return;
		if (activeElement==_activeElement) return;

		if (_activeElement){
			if (typeof(hideMenu)!="undefined"){
				if (_activeElement.getAttribute("extra")=="menuframe" ||
					_activeElement.getAttribute("extra")=="menuitem"){
					hideMenu();
				}
			}
		}

		if (activeElement){
			processElementBlur();

			switch (activeElement.getAttribute("extra")){
				case "tablecell":{
					var row=getRowByCell(activeElement);
					var table=getTableByRow(row);
					var dataset=getElementDataset(table);

					table._activeRow=row;
					table._activeCell=activeElement;
					table._activeCellIndex=activeElement.cellIndex;
					if (row.record){
						if (dataset.window==window)
							_dataset_setRecord(dataset, row.record);
						else
							dataset.window._dataset_setRecord(dataset, row.record);
					}
					setActiveTableCell(row, activeElement.cellIndex);
					table._activeRow=null;
					break;
				}
				case "editor":;
				case "dockeditor":{
					set_activeEditor(activeElement);
					break;
				}
			}
		}
		_activeElement=activeElement;
	}
	catch(e){
		processException(e);
	}
}

function _document_onpropertychange() {
	if (event.propertyName=="activeElement")
		processActiveElementChanged(document.activeElement);
}

function _document_onkeydown(){
	switch (event.keyCode){
		case 123:{
			if (_enableClientDebug && event.altKey && event.ctrlKey && event.shiftKey){
				eval(window.prompt("DEBUG", ""));
			}
			break;
		}
	}

}

function _document_oncontextmenu(){
	event.returnValue=(!isTrue(_disableSystemContextMenu));
	if (typeof(_array_menu)=="undefined") return;
	for(var i=0; i<_array_menu.length; i++){
		var strHolder=_array_menu[i].popupHolder;
		if (getValidStr(strHolder)!=""){
			var arrayHolder=strHolder.split(",");
			for(var j=0; j<arrayHolder.length; j++){
				if (arrayHolder[j]=="") continue;
				var needPopup;
				eval("needPopup=isChild(event.srcElement,"+arrayHolder[j]+")");
				if (needPopup){
					showMenu(_array_menu[i]);
					event.returnValue=false;
					return;
				}
			}
		}
	}
}

function getPriorTabElement(obj){
	var i=obj.sourceIndex-1;
	var elementCount=document.all.length
	var tmpObj=null;
	while (i<elementCount){
		tmpObj=document.all[i];
		if (tmpObj!=obj)
		{
			switch (tmpObj.tagName.toLowerCase())
			{
			case "input":
			case "textarea":
			case "button":
				if (tmpObj.tabIndex!=-1 && !tmpObj.disabled && !tmpObj.readOnly)
				{
					return tmpObj;
				}
			case "td":
				if (tmpObj.extra=="tablecell" && !tmpObj.readOnly)
				{
					return tmpObj;
				}
			}
		}
		i--;
	}
}

function getNextTabElement(obj){
	var i=obj.sourceIndex+1;
	var elementCount=document.all.length
	var tmpObj=null;
	while (i<elementCount){
		tmpObj=document.all[i];
		if (tmpObj!=obj)
		{
			switch (tmpObj.tagName.toLowerCase())
			{
			case "input":
			case "textarea":
			case "button":
				if (tmpObj.tabIndex!=-1 && !tmpObj.disabled && !tmpObj.readOnly)
				{
					return tmpObj;
				}
			case "td":
				if (tmpObj.extra=="tablecell" && !tmpObj.readOnly)
				{
					return tmpObj;
				}
			}
		}
		i++;
	}
}

//yjw add
function _control_onkeyup(){
		if (!event.srcElement.readOnly || event.srcElement.contentEditable){
		 //    backspace key and <-- key
		if( event.keyCode != 8 &&  event.keyCode!=37){
				var element=event.srcElement;
				var dataset=getElementDataset(element);
				var field = getElementField(element);
				var value = event.srcElement.value;
				if(typeof(field)!="undefined" && field.tag!="select" && field.tag!="selectCQ" && field.tag!="selectName" && field.readOnly!=true && field.size!=""){
						    if (field.dataType=="int" || field.dataType=="double"|| field.dataType=="float" || field.dataType=="short" ||field.dataType=="currency"){
						    	if(value!=""){
						    		if(value.length > field.size){
						    			value = value.substring(0,field.size);
						    		}
						    	}
						    }else if(field.dataType=="" || field.dataType=="string" || field.dataType=="String"){
						   		 if(value!=""){
						    		if(value.replace(/[^\x00-\xff]/g,'**').length > field.size){
						    					//var bakValue = event.srcElement.value;
						    					//alert(constFieldSizeError2);
						    					// event.srcElement.value
						    					value = value.substring(0,Math.floor(field.size/2)*2);
						    					if(value.replace(/[^\x00-\xff]/g,'**').length > field.size){
						    					   value =  value.substring(0,Math.floor(field.size/2));
						    					}
												event.srcElement.value =value;
						    			}
						    	 }
						   }
					}
			}
	 }
}

function _control_onkeydown() {
	element=event.srcElement;
	if (event.srcElement.readOnly || !event.srcElement.contentEditable){
		if( event.keyCode != 17 && ( event.ctlKey || event.keyCode != 67) ){
			event.returnValue=false;
			return element.editorHolder;
		}
	}

	function getCell(element){
		if (element.getAttribute("extra")=="tablecell")
			return element;
		else if (element.in_table)
			return element.editorHolder;
	}

	function processTab(element){
		var obj=null;
		if (element.extra=="dockeditor"){
			obj=element.editorHolder;
		}
		else{
			obj=element;
		}
		if (!obj) return;
		if (event.shiftKey)
			obj=getPriorTabElement(obj);
		else
			obj=getNextTabElement(obj);

		try {
			if (obj) obj.focus();
			event.returnValue=false;
			return obj;
		}
		catch (e) {
			// do nothing
		}
	}

	//element=event.srcElement;
	if (isDropdownBoxVisible()){
		if (_dropdown_window) _dropdown_window.processDropDownKeyDown(event.keyCode);
		event.returnValue=true;
	}
	else{
		var rowindex, colindex;
		switch (event.keyCode) {
			//Tab
			case 9:{
				processTab(element);
				break;
			}
			//Enter
			case 13:{
				if (_processEnterAsTab && !compareText(element.tagName, "textarea") || event.shiftKey || event.ctrlKey || event.altKey){
					var cell=getCell(element);
					if (cell && !event.shiftKey){
						var row=getRowByCell(cell);
						var table=getTableByRow(row);
						var maxIndex=checkTableCellIndex(table, 9999, 9999);
						if (row.rowIndex==maxIndex[0]){
							if (!isTrue(table.getAttribute("readOnly"))){
								var next = getNextTabElement(cell);
								if (next==null || !isChild(next, table)) {
									var dataset=getElementDataset(element);
									dataset.insertRecord("end");
									//dataset.modified=false;
									setActiveTableCell(table.activeRow, 0);
								}
								else {
									processTab(element);
								}

							}
							else {
								processTab(element);
							}
						}
						else{
							processTab(element);
						}
					}
					else{
						processTab(element);
					}
				}
				break;
			}
			//ESC
			case 27:{
				if (!element.modified){
					var dataset=getElementDataset(element);
					if (!dataset || dataset.state=="none") break;

					var cell=getCell(element);
					var table=getTableByCell(cell);
					if (cell && !isTrue(table.getAttribute("readOnly"))){
						if (isTrue(table.getAttribute("confirmCancel"))){
							if (confirm(constDatasetConfirmCancel)){
								dataset.cancelRecord();
							}
						}
						else{
							dataset.cancelRecord();
						}
					}
				}
				else{
					setElementValue(element, element.oldValue);
				}
				event.returnValue=false;
				break;
			}
			//Left
			case 37:{
				var cell=getCell(element);
				if (cell){
					if ((event.ctrlKey) || (event.altKey)){
						var table=getTableByCell(cell);
						var rowIndex=getRowByCell(cell).rowIndex;
						var cellIndex=cell.cellIndex;
						cellIndex--;
						setFocusTableCell(table, rowIndex, cellIndex);
						event.returnValue=false;
					}
				}
				break;
			}
			//Up
			case 38:{
				var cell=getCell(element);
				if (cell){
					var dataset=getElementDataset(element);
					if (dataset){
						dataset.movePrev();
						event.returnValue=false;
					}
				}
				break;
			}
			//Right
			case 39:{
				var cell=getCell(element);
				if (cell){
					if ((event.ctrlKey) || (event.altKey)){
						var table=getTableByCell(cell);
						var rowIndex=getRowByCell(cell).rowIndex;
						var cellIndex=cell.cellIndex;
						cellIndex++;
						setFocusTableCell(table, rowIndex, cellIndex);
						event.returnValue=false;
					}
				}
				break;
			}
			//Down
			case 40:{
				if (event.altKey){
					showDropDownBox(element);
				}
				else{
					var cell=getCell(element);
					if (cell){
						var table=getTableByCell(cell);
						var dataset=getElementDataset(element);
						if (dataset){
							dataset.moveNext();
							if (dataset.eof && !isTrue(table.getAttribute("readOnly")) && !isTrue(dataset.readOnly)){
								dataset.insertRecord("end");
								//dataset.modified=false;
							}
							event.returnValue=false;
						}
					}
				}
				break;
			}
			//Insert
			case 45:{
				var cell=getCell(element);
				if (cell && !isTrue(getTableByCell(cell).getAttribute("readOnly"))){
					var dataset=getElementDataset(element);
					if (!isTrue(dataset.readOnly)){
						dataset.insertRecord("before");
						//dataset.modified=false;
					}
				}
				break;
			}
			//Delete
			case 46:{
				if (event.ctrlKey){
					var cell=getCell(element);
					if (cell){
						var table=getTableByCell(cell);
						if (!isTrue(table.getAttribute("readOnly"))){
							var dataset=getElementDataset(element);
							if (!isTrue(dataset.readOnly)){
								if (isTrue(table.getAttribute("confirmDelete"))){
									if (confirm(constDatasetConfirmDelete)){
										dataset.deleteRecord();
									}
								}
								else{
									dataset.deleteRecord();
								}
							}
							event.returnValue=false;
						}
					}
				}
				break;
			}
			//Home
			case 36:{
				var cell=getCell(element);
				if (cell){
					if ((event.ctrlKey) || (event.altKey)){
						var row=getRowByCell(cell);
						setActiveTableCell(row, 0);
						event.returnValue=false;
					}
				}
				break;
			}
			//End
			case 35:{
				var cell=getCell(element);
				if (cell){
					if ((event.ctrlKey) || (event.altKey)){
						var row=getRowByCell(cell);
						setActiveTableCell(row, 99999);
						event.returnValue=false;
					}
				}
				break;
			}
			//Page Up
			case 33:{
				var cell=getCell(element);
				if (cell && !isTrue(getTableByCell(cell).getAttribute("readOnly"))){
					var dataset=getElementDataset(element);
					var pageIndex=(dataset.record)?dataset.record.pageIndex-1:1;
					dataset.moveToPage(pageIndex);
				}
				break;
			}
			//Page Down
			case 34:{
				var cell=getCell(element);
				if (cell && !isTrue(getTableByCell(cell).getAttribute("readOnly"))){
					var dataset=getElementDataset(element);
					var pageIndex=(dataset.record)?dataset.record.pageIndex+1:1;
					dataset.moveToPage(pageIndex);
				}
				break;
			}
			//F2
			case 113:;
			//F7
			case 118:{
				showDropDownBox(element);
				break;
			}
		}
	}
}

function getAbsPosition(obj, offsetObj){
	var _offsetObj=(offsetObj)?offsetObj:document.body;
	var x=obj.offsetLeft;
	var y=obj.offsetTop;
	var tmpObj=obj.offsetParent;

	while ((tmpObj!=_offsetObj) && tmpObj){
		x += tmpObj.offsetLeft - tmpObj.scrollLeft + tmpObj.clientLeft;
		y += tmpObj.offsetTop - tmpObj.scrollTop + tmpObj.clientTop;
		tmpObj=tmpObj.offsetParent;
	}
	return ([x, y]);
}

function isChild(obj, parentObj) {
	var tmpObj=obj;
	var result=false;
	if (parentObj) {
		while (tmpObj) {
			if (tmpObj==parentObj){
				result=true;
				break;
			}
			tmpObj=tmpObj.parentElement;
		}
	}
	return result;
}

function initElementDataset(element){
	var dataset=element.getAttribute("dataset");
	if (dataset) setElementDataset(element, dataset);
}

function initElement(element){
	var _extra=element.getAttribute("extra");
	var noExtra=isTrue(element.getAttribute("noExtra"));
	var initChildren=!noExtra;

	if (_extra && !noExtra){
		switch (_extra){
			case "fieldlabel":{
				if (!element.className) element.className=_extra;

				var dataset;
				var _dataset=element.getAttribute("dataset");
				if (typeof(_dataset)=="string"){
					dataset=getDatasetByID(_dataset);
				}
				else{
					dataset=_dataset;
				}
				element.dataset=dataset;
				refreshElementValue(element);
				initChildren=false;
				break;
			}
			case "columnheader":{
				if (!element.className) element.className=_extra;
				element.noWrap=true;
				element.onclick=_table_head_onclick;
				element.onmouseover=_table_head_onmouseover;
				element.onmouseout=_table_head_onmouseout;
				refreshElementValue(element);
				initChildren=false;
				break;
			}
			case "columnfooter":{
				if (!element.className) element.className=_extra;
				refreshElementValue(element);
				initChildren=false;
				break;
			}
			case "datalabel":{
				if (!element.className) element.className=_extra;
				initElementDataset(element);
				initChildren=false;
				break;
			}
			case "editor":
				if (!element.className) element.className=_extra;

				initEditor(element);
				initChildren=false;
				break;
			case "dockeditor":{
				if (!element.className) element.className="editor_active";

				initEditor(element);
				initChildren=false;
				break;
			}
			case "datatable":{
				if (_isDropDownPage || isTrue(element.isDropDownTable)){
					if (!element.className) element.className="dropdowntable";
				}
				else{
					if (!element.className) element.className="datatable";
				}

				initElementDataset(element);
				initDataTable(element, !isTrue(element.getAttribute("skipRebuild")));
				element.onkeydown=_control_onkeydown;
				break;
			}
			case "tablecell":{
				if (!element.className) element.className=_extra;
				initChildren=false;
				break;
			}
			case "datapilot":{
				if (!element.className) element.className=_extra;
				initElementDataset(element);
				initDataPilot(element);
				break;
			}
			case "pagepilot":{
				if (!element.className) element.className=_extra;
				initElementDataset(element);
				_initPagePilot(element);
				initChildren=false;
				break;
			}
			case "menubar":{
				if (!element.className) element.className=_extra;
				initMenuBar(element);
				break;
			}
			case "button":{
				if (!element.className) element.className=_extra;

				initButton(element);
				initChildren=false;
				break;
			}
			case "tree":{
				if (!element.className) element.className=_extra;
				initTree(element);
				initChildren=false;
				break;
			}
			case "tabset":{
				if (!element.className) element.className=_extra;
				initTabSet(element);
				initChildren=false;
				break;
			}
			case "groupboxtitle":{
				initGroupBoxTitle(element);
				initChildren=false;
				break;
			}
			default:{
				if (!element.className &&_extra) element.className=_extra;
				break;
			}
		}

		element.window=window;
		fireUserEvent("document_onInitElement", [element, _extra]);
	}
	return initChildren;
}

function initElements(element){
	if (compareText(element.getAttribute("extra"), "tabset")){
		_tabset_list[_tabset_list.length]=element;
	}
	else{
		if (!initElement(element)) return;
	}

	for (var i=0; i<element.children.length; i++){
		initElements(element.children[i]);
	}
}

function uninitElement(element){
	var _extra=element.getAttribute("extra");
	switch (_extra){
		case "datalabel":;
		case "editor":;
		case "dockeditor":;
		case "datatable":;
		case "tablecell":;
		case "pagepilot":;
		case "datapilot":{
			if (typeof(setElementDataset)!="undefined") setElementDataset(element, null);
			break;
		}
	}
}

function uninitElements(element){
	if (!element) {
		for(var i=0; i<_array_dataset.length; i++){
			var dataset=_array_dataset[i];
			if (dataset.window==window) dataset.setMasterDataset(null);
		}
		element=document.body;
	}
	var noExtra=isTrue(element.getAttribute("noExtra"));
	if (element && !noExtra) {
		for (var i=0; i<element.children.length; i++){
			uninitElements(element.children[i]);
		}
		uninitElement(element);
	}
}
function uninitLet(element){
    var elementId;
    uninitElements(element);
	if (!element) {
		elementId = "document";
	}else{
		elementId = element.id;
	}
	var _new_array_dataset = new Array();
	for(var i=0; i<_array_dataset.length; i++){
		var dataset=_array_dataset[i];
		if( dataset.pageElement == elementId && dataset.type!="dropdown"){
			dataset.disableControls();
			dataset.disableEvents();
			freeDataset(dataset);
			eval(dataset.id + "=undefined;");
		}else{
			_new_array_dataset[_new_array_dataset.length] = dataset;
		}
	}
	_array_dataset = _new_array_dataset;

}


function _window_onunload() {
	uninitElements();
}

function _finishInitializtion(){
	for (var i=0; i<_tabset_list.length; i++){
		initElement(_tabset_list[i]);
	}
	document.body.style.visibility="visible";
}

function initDocument(){
	if (getIEVersion()<"5.0"){
		alert(constErrUnsupportBrowser);
	}
	_document_loading=true;
	try{
		fireUserEvent("document_onInit", []);
		with (document){
			if (typeof(_setElementsProperties)!="undefined") _setElementsProperties();
			initElements(body);

			for(var i=0; i<_array_dataset.length; i++){
				var dataset=_array_dataset[i];
				if (dataset.masterDataset){
					dataset.setMasterDataset(dataset.masterDataset, dataset.references);
				}

				var event_name=getElementEventName(dataset, "onFilterRecord");
				dataset.filtered=isUserEventDefined(event_name);
				dataset.initDocumentFlag = true;
				dataset.refreshControls();
				dataset.pageElement = "document";
			}

			//setTimeout("_finishInitializtion()", 0);
			_finishInitializtion();

			language="javascript";
			onpropertychange=_document_onpropertychange;
			onkeydown=_document_onkeydown;
			oncontextmenu=_document_oncontextmenu;
		}
		if (!window.onunload) window.onunload=_window_onunload;

		if (typeof(sizeDockEditor)!="undefined") setInterval("adjustControlsSize();", 300);
		setTimeout("if (typeof(document.activeElement)!=\"unknown\") processActiveElementChanged(document.activeElement);", 0);
	}
	finally{
		_document_loading=false;
		fireUserEvent("document_afterInit", []);
	}
}

function initDocumentLet(element){
	if (getIEVersion()<"5.0"){
		alert(constErrUnsupportBrowser);
	}
	_document_loading=true;
	try{
		fireUserEvent("document_onInit", []);
		with (document){
			if (typeof(_setElementsProperties)!="undefined") _setElementsProperties();
			initElements(element);
			for(var i=0; i<_array_dataset.length; i++){
				var dataset=_array_dataset[i];
				if( dataset.initDocumentFlag == false ){
					if (dataset.masterDataset){
						dataset.setMasterDataset(dataset.masterDataset, dataset.references);
					}
					var event_name=getElementEventName(dataset, "onFilterRecord");
					dataset.filtered=isUserEventDefined(event_name);
					dataset.refreshControls();
					dataset.pageElement = element.id;
					dataset.initDocumentFlag = true;
				}

			}
		}
		if (!window.onunload) window.onunload=_window_onunload;
		if (typeof(sizeDockEditor)!="undefined") setInterval("adjustControlsSize();", 300);
		//setTimeout("if (typeof(document.activeElement)!=\"unknown\") processActiveElementChanged(document.activeElement);", 0);
	}
	finally{
		_document_loading=false;
		fireUserEvent("documentlet_afterInit", []);
	}
}

var _ad_box=null;
var _ad_interval=50;
var _ad_count=_ad_interval;

function adjustControlsSize(){
	if (typeof(sizeDockEditor)!="undefined"){
		sizeDockEditor();
		if (typeof(sizeDropDownBtn)!="undefined" && _activeEditor) sizeDropDownBtn(_activeEditor);
		if (typeof(sizeDropDownBox)!="undefined") sizeDropDownBox();
	}
}

function getElementDataset(element){
	switch (element.getAttribute("extra")){
		case "tablecell":{
			return element.dataset;
			break;
		}
		case "tablerow":{
			return element.record.dataset;
			break;
		}
		case "dockeditor":{
			var holder=element.editorHolder;
			if (holder){
				return getElementDataset(holder);
			}
			break;
		}
		default:{
			return element.getAttribute("dataset");
			break;
		}
	}
}

function getElementField(element){
	var dataset=getElementDataset(element);
	if (!dataset) return;
	return dataset.getField(element.getAttribute("dataField"));
}

function getElementValue(element){
	var eventName=getElementEventName(element, "onGetValue");
	if (isUserEventDefined(eventName)){
		var event_result=fireUserEvent(eventName, [element]);
		return event_result;
	}

	switch (element.getAttribute("extra")){
		case "editor":;
		case "dockeditor":{
			switch (element.type.toLowerCase()){
				case "checkbox":{
					return element.checked;
					break;
				}
				default:{
					var result=element.value;
					var _dropdown=getEditorDropDown(element);
					if (_dropdown){
						if (_dropdown.type=="list" && isTrue(_dropdown.mapValue)){
							var items=getDropDownItems(_dropdown);
							if (items){
								var item=items.find(["label"], [element.value]);
								if (item) result=item.getString("value");
							}
						}
					}
					return result;
					break;
				}
			}
			break;
		}

		default:{
			return element.value;
			break;
		}
	}
}

function setElementValue(element, value){
	function getEditorValue(element, value) {
		var result=getValidStr(value);
		var _dropdown=getEditorDropDown(element);
		if (_dropdown){
			if (_dropdown.type=="list" && isTrue(_dropdown.mapValue)){
				result="";
				var items=getDropDownItems(_dropdown);
				if (items){
					var item=items.find(["value"], [value]);
					if (item) result=item.getString("label");
				}
			}
		}
		return result;
	}

	switch (element.getAttribute("extra")){
		case "fieldlabel":;
		case "columnfooter":;
		case "columnheader":{
			var eventName=getElementEventName(element, "onRefresh");
			if (isUserEventDefined(eventName)){
				if (!fireUserEvent(eventName, [element, value])) break;
			}
			element.innerHTML=value;
			break;
		}

		case "datalabel":{
			if (element.oldValue==value) return;
			element.oldValue=value;

			var eventName=getElementEventName(element, "onRefresh");
			if (isUserEventDefined(eventName)){
				if (!fireUserEvent(eventName, [element, value])) break;
			}

			if (compareText(element.labelType, "image")) {
				var dataset=getElementDataset(element);
				var field=getElementField(element);
				var url=field.lobDownloadURL;
				if (url.indexOf("?")<0){
					url+="?";
				}

				var tooptip="";
				var titleField=field.name+"__title";
				if (dataset.getField(titleField)){
					tooptip="title=\""+dataset.getValue(titleField)+"\"";
				}

				url+="&lob_sessionkey=" + dataset.getValue(field.name);
				url+="&dataset_sessionKey=" + dataset.sessionKey;
				url+="&lobfield=" + field.fieldName;
				url+="&oid=" + dataset.getValue("oid");
				element.innerHTML="<img src=\"" + _application_root + url + "\" ondblclick=\"return _image_onClick();\" " +
					tooptip + " " +
					((element.width)?("width=\"" + element.width + "\""):"") + " " +
					((element.height)?("height=\"" + element.height + "\""):"") + " style=\"cursor: hand\" />";
			}
			else{
				element.innerText=value;
			}
			break;
		}

		case "editor":;
		case "dockeditor":{
			if (element.oldValue==value && !element.modified) return;
			var  dataset=getElementDataset(element);
			var eventName=getElementEventName(element, "onSetValue");
			if (isUserEventDefined(eventName)&& dataset.record && dataset.loadCompleted){
			/* modify by shen_antonio 20080526.*/
				var field=getElementField(element);
				var recodeVal = getOrigEditorValueStr(value,field.dataType);
			/* .*/
				if (!fireUserEvent(eventName, [element, recodeVal])) break;
			}

			element.keyValue=value;
			switch (element.type.toLowerCase()){
				case "checkbox":{
					element.checked=isTrue(value);
					break;
				}
				default:{
					element.value=getEditorValue(element, value);
					element.keyValue=value;
					break;
				}
			}
			break;
		}

		case "tablecell":{
			var eventName=getElementEventName(element, "onRefresh");
			if (isUserEventDefined(eventName)){
				var record=getRecordByCell(element);
				if (!fireUserEvent(eventName, [element, value, record])) break;
			}

			if (element.getAttribute("name")=="select") {
			/* modify by shen_antonio 20080306.*/
				var disableStr = "";
				var eventName1=getElementEventName(element, "isReadOnly");
				if (isUserEventDefined(eventName1)){
					var record=getRecordByCell(element);
					if(record != null){
						var isReadOnly = fireUserEvent(eventName1, [element, value, record]);
						if(isReadOnly){
							disableStr = "disabled";
						}
					}
				}
			/* .*/
				var record=getRecordByCell(element);
				if (record) {
					if (_supportsDatasetMultiSelect) {
						if (isTrue(record.getValue("select"))){
							if(disableStr=="disabled"){
								if(element.innerHTML.length==0){
									element.innerHTML="<input type=checkbox checked onclick=\"return _table_checkbox_onclick();\" style=\"height:16\"" + disableStr + ">";
								}
								/*
								else{
									element.innerHTML="<input type=checkbox onclick=\"return _table_checkbox_onclick();\" style=\"height:16\"" + disableStr + ">";
								}
								*/
							}else{
								element.innerHTML="<input type=checkbox checked  onclick=\"return _table_checkbox_onclick();\" style=\"height:16\" >";
							}

						}
						else {
							if(disableStr=="disabled"){
								if(element.innerHTML.length==0){
									element.innerHTML="<input type=checkbox  onclick=\"return _table_checkbox_onclick();\" style=\"height:16\"" + disableStr + ">";
								}
							}else{
								element.innerHTML="<input type=checkbox  onclick=\"return _table_checkbox_onclick();\" style=\"height:16\"" + disableStr + ">";
							}

						}
					}
					else {
						if (record.selected){
						 if(disableStr=="disabled"){
						 	if(element.innerHTML.length==0){
								element.innerHTML="<input type=checkbox checked onclick=\"return _table_checkbox_onclick();\" style=\"height:16\"" + disableStr + ">";
						 	}
						  }else{
						  	element.innerHTML="<input type=checkbox checked onclick=\"return _table_checkbox_onclick();\" style=\"height:16\">";
						  }
						}
						else {
							if(disableStr=="disabled"){
								if(element.innerHTML.length==0){
									element.innerHTML="<input type=checkbox onclick=\"return _table_checkbox_onclick();\" style=\"height:16\"" + disableStr + ">";
								}
							}else{
								element.innerHTML="<input type=checkbox onclick=\"return _table_checkbox_onclick();\" style=\"height:16\"" + disableStr + ">";
							}

						}
					}
				}

			}
			else {
				var tmpHTML;
				switch (element.getAttribute("editorType")){
					case "checkbox":{
						if (isTrue(value)){
							tmpHTML="<font face=Marlett size=2>a</font>";
						}
						else{
							tmpHTML="<font face=Webdings size=1 color=silver>c</font>";
						}
						element.innerHTML=tmpHTML;
						break;
					}
					case "image":{
						var record=getRecordByCell(element);
						if (record) {
							var dataset=record.dataset;
							var field=getElementField(element);
							var url=field.lobDownloadURL;
							if (url.indexOf("?")<0){
								url+="?";
							}

							var tooptip="";
							var titleField=field.name+"__title";
							if (dataset.getField(titleField)){
								tooptip="title=\""+record.getValue(titleField)+"\"";
							}

							url+="&lob_sessionkey=" + record.getValue(field.name);
							url+="&dataset_sessionKey=" + dataset.sessionKey;
							url+="&lobfield=" + field.fieldName;
							url+="&oid=" + record.getValue("oid");
							element.innerHTML="<img src=\"" + _application_root + url + "\" ondblclick=\"return _image_onClick();\" "+
								tooptip + " " +
								((element.width)?("width=\"" + element.width + "\""):"") + " style=\"cursor: hand\" />";
						}
						else{
							element.innerHTML="";
						}
						break;
					}
					default:{
						tmpHTML=getEditorValue(element, value);
						if (tmpHTML=="") tmpHTML=" ";
						element.innerText=tmpHTML;
					}
				}
			}
			break;
		}

		case "treenode":{
			var node=element.node;
			var canceled=false;
			var eventName=getElementEventName(getTableByCell(element), "onRefresh");
			if (isUserEventDefined(eventName)){
				canceled=(!fireUserEvent(eventName, [element, value, node]));
			}
			if (!canceled) element.innerHTML=value;

			if (node.selectable){
			//if (node.canSelected){
				tmpHTML="<input type=\"checkbox\" "+((node.selected)?"checked":"")+
					" onclick=\"return _tree_checkbox_onClick();\">";
				element.insertAdjacentHTML("afterBegin", tmpHTML);
				element.firstChild.node=node;
			}

			var tmpHTML="";
			if (node.icon){
				if (node.hasChild && node.expanded && node.expandedIcon)
					tmpHTML="<img src=\""+node.expandedIcon+"\" class=\"icon\">";
				else
					tmpHTML="<img src=\""+node.icon+"\" class=\"icon\">";
				element.insertAdjacentHTML("afterBegin", tmpHTML);
			}


			var record=node.data;
			var button;
			if (node.hasChild){
				var button_img=(node.expanded)?"collapse.gif":"expand.gif";
				button=document.createElement("<img id=_button_expand hideFocus=true class=\"expandbutton\" src=\""+_theme_root+"/"+button_img+"\""+
					" language=javascript onclick=\"return _tree_expendclick(this);\">");

				button.treenode=element;
				element.insertAdjacentElement("afterBegin", button);
			}
			else{
				element.insertAdjacentHTML("afterBegin", "<img id=_button_expand hideFocus=true class=\"expandbutton\" src=\""+_theme_root+"/nochild.gif\">");
			}

			tmpHTML="";
			element.button=button;
			for(var i=1; i<node.level; i++){
				tmpHTML+="&nbsp;&nbsp;&nbsp;&nbsp;"
			}
			element.insertAdjacentHTML("afterBegin", tmpHTML);
			break;
		}
		default:{
			element.value=value;
		}
	}
}

function refreshElementValue(element){
	var dataset;
	var _extra=element.getAttribute("extra");
	switch (_extra){
		case "fieldlabel":{
			var label=element.getAttribute("dataField");
			var field=getElementField(element);
			if (field){
				label=field.label;
				if (field.required){
						label="<font color=red>*</font>"+label;
				}
			}
			setElementValue(element, label);
			break;
		}

		case "columnheader":;
		case "columnfooter":{
			var label=getValidStr(element.getAttribute("label"));
			var field=getElementField(element);
			if (label==""){
				if (field){
					label=field.label;
				}
				else{
					label=getValidStr(element.getAttribute("name"));
				}
			}

			if (field){
				if (field.required && !field.readOnly && !field.dataset.readOnly){
						label="<font color=red>*</font>"+label;
				}
			}

			setElementValue(element, label);
			break;
		}

		case "tablecell":{
			var row=getRowByCell(element);
			var record=row.record;
			var dataField=element.getAttribute("dataField");

			if (record){
				//var s=record.getString(dataField);
				var s=record.getViewString(dataField);



				if (s.length>1024){
					setElementValue(element, s.substring(0, 1024) + "...");
					element.title=s;
				}
				else{
					setElementValue(element, s);
					element.title="";
				}

			}
			else
				setElementValue(element, "");
			break;
		}

		case "treenode":{
			var node=element.node;
			if (node)
				setElementValue(element, node.label);
			else
				setElementValue(element, "");
			break;
		}

		default:{
			dataset=getElementDataset(element);

			var value="";
			if (dataset){
				var fieldName=element.getAttribute("dataField");
				if (fieldName) value=dataset.getViewString(fieldName);

				setElementValue(element, value);
			}
			element.oldValue=getElementValue(element);
			element.modified=false;
			break;
		}
	}
}

function getStatusLabel(text){
	if (typeof(_status_label)=="undefined"){
		document.body.insertAdjacentHTML("beforeEnd", "<DIV id=_status_label style=\"position: absolute; visibility: hidden;"+
			" padding-left: 16px; padding-right: 16px; height: 22px; font-size: 9pt; background-color: #ffffcc; border: 1 solid silver; padding-top:3; z-index: 10000;  filter:alpha(opacity=80)\"></DIV>");
	}
	_status_label.innerHTML=text;
}

function showStatusLabel(parent_window, text, control){
	parent_window.getStatusLabel(text);
	parent_window._status_label.style.visibility="visible";
	if (control){
		var pos=getAbsPosition(control);
		locateStatusLabel(pos[0]+(control.offsetWidth-_status_label.offsetWidth)/2, pos[1]+control.offsetHeight+1);
	}
	else{
		parent_window._status_label.style.posLeft=(document.body.clientWidth - _status_label.offsetWidth) / 2;
		parent_window._status_label.style.posTop=(document.body.clientHeight - _status_label.offsetHeight) / 2;
		parent_window.document.onmousemove=null;
	}

}

function hideStatusLabel(parent_window){
	if (!parent_window.closed && parent_window._status_label){
		parent_window.document.onmousemove=null;
		parent_window._status_label.style.visibility="hidden";
	}
}

function locateStatusLabel(x, y){
	if (x==0 && y==0) return;

	var posX=document.body.clientWidth + document.body.scrollLeft - _status_label.offsetWidth;
	var posY=document.body.clientHeight + document.body.scrollTop - _status_label.offsetHeight;
	posX=(x<posX)?x:posX;
	posY=(y<posY)?y:posY;

	_status_label.style.posLeft=posX + 1;
	_status_label.style.posTop=posY + 1;
}

function isDropdownBoxVisible(){
        if (typeof(_dropdown_box)!="undefined" && _dropdown_box)
                return (_dropdown_box.style.visibility=="visible")
        else
                return false;
}

function _createParameters(){
	var parameters=new Array();
	parameters.setParameter=parameters_setParameter;
	parameters.getParameter=parameters_getParameter;
	return parameters;
}

function parameters_setParameter(name, value, dataType){
	var parameter;
	if (typeof(name)=="number"){
		var i = name;
		this[i].name=name;
		this[i].value=value;
		if (dataType){
			this[i].dataType=dataType;
		}
	}
	else{
		var count=this.length;
		var founded=false;
		for (var i=0; i<count; i++){
			if (compareText(this[i].name, name)){
				founded=true;
				break;
			}
		}
		if (!founded){
			i=count;
			this[i]=new Object();

		}
		this[i].name=name;
		this[i].value=value;
		if (dataType){
			this[i].dataType=dataType;
		}
	}
}

function parameters_getParameter(name){
	if (typeof(name)=="number"){
		return this[name].value;
	}
	else{
		var count=this.length;
		var founded=false;
		for (var i=0; i<count; i++){
			if (compareText(this[i].name, name)){
				return this[i].value;
				break;
			}
		}
		//yjw modify
		//if no found  ,return "";
		return "";
	}
}

function _createSubmitManager(id) {
	var submitManager=new Object();
	submitManager.id=id;
	submitManager.parameters=_createParameters();

	submitManager.datasets=new Array();
	submitManager.setParameter=_submitManager_setParameter;
	submitManager.getParameter=_submitManager_getParameter;
	submitManager.getParameterName=_submitManager_getParameterName;
	submitManager.getParameterCount=_submitManager_getParameterCount;
	submitManager.setDatasets=_submitManager_setDatasets;
	submitManager.submitNeeded=_submitManager_submitNeeded;
	submitManager.submit=_submitManager_submit;
	return submitManager;
}

function _submitManager_setParameter(name, value, dataType){
	this.parameters.setParameter(name, value, dataType);
}

function _submitManager_getParameter(name){
	return this.parameters.getParameter(name);
}

function _submitManager_getParameterName(index){
	return this.parameters.getParameterName(index);
}

function _submitManager_getParameterCount(){
	return this.parameters.length;
}

function _submitManager_setDatasets(datasets) {
	this.datasets = datasets;
}

function _submitManager_addDataset(submitManager, dataset) {
	submitManager.datasets[submitManager.datasets.length]=getDatasetByID(dataset);
}

function _submitManager_submitNeeded(){
	if (this.datasets.length > 0) {
		for (var i=0; i<this.datasets.length; i++){
			var dataset=this.datasets[i];
			if (!dataset) continue;
			if (!dataset.updateRecord()) return false;
			if (dataset.submitData=="all") return true;
			if (dataset.submitData=="current") return (dataset.record!=null);
			if (dataset.submitData=="selected") {
				var record=dataset.getFirstRecord();
				while (record) {
					if (isTrue(record.getValue("select"))) {
						return true;
					}
					record = record.getNextRecord();
				}
			}

			var record=dataset.firstUnit;
			while (record) {
				if (record.recordState == "insert" ||
					record.recordState == "modify" ||
					record.recordState == "delete")
				{
					return true;
				}
				record=record.nextUnit;
			}
		}
	}
	else {
		return true;
	}
}

function _submitManager_submit(){
	function _submitString(submitManager, updateString){
		if (trimStr(updateString)=="") return;
		var param=new Object();
		param.action=_application_root+submitManager.submitUrl;
		param.updatestring=updateString;
		param.pagestate=PageState.getStringCode();
		param.sourceurl=window.location.pathname;
		var updateResultXML=showModalDialog(_extra_library+"/updatedialog.html", param,
			"dialogHeight: 80px; dialogWidth: 220px; center: Yes; help: No; resizable: yes; status: No");
		if (updateResultXML) {
			var updateResult = new ActiveXObject("Msxml.DOMDocument");
			updateResult.async=false;
			updateResult.loadXML(updateResultXML);
			var rootNode = updateResult.documentElement;
			if (rootNode) {
				var succeed=isTrue(rootNode.getAttribute("succeed"));
				if (succeed) {
					submitManager.forwardPath=getDecodeStr(rootNode.getAttribute("forwardPath"));
					var stateList = rootNode.getElementsByTagName("State");
					for (var i=0; i<stateList.length; i++) {
						var stateNode = stateList.item(i);
						PageState.setString(stateNode.getAttribute("name"),
							getDecodeStr(stateNode.text));
					}

					_oidmap=new Object();
					var mapList = rootNode.getElementsByTagName("Map");
					for (var i=0; i<mapList.length; i++) {
						var map = mapList.item(i);
						_oidmap[map.getAttribute("oid")]=map.getAttribute("value");
					}

					var message = rootNode.getAttribute("message");
					if (message){
						alert(message);
					}
				}
				else {
					throw getDecodeStr(rootNode.getAttribute("errorMessage"));
				}
			}
		}
		return true;
	}

	function getSubmitForm(){
		if (typeof(_form_extra)=="undefined")
		{
			var obj=document.createElement("<form method=\"post\" id=\"_form_extra\" style=\"visibility: hidden\"></form>");
			obj.innerHTML="<input name=\"updatestring\"><input name=\"forwardPath\"><input name=\"SubmitManager.PageState\"><input name=\"sourceurl\"><input name=\"isSubmitByExtra\" value=\"true\">";
			document.body.appendChild(obj);
		}
	}

	function redirectPage(submitManager) {
		if (submitManager.forwardPath){
			var obj=document.createElement("<form method=\"post\" action=\""+submitManager.forwardPath+"\" "+
				"target=\""+((submitManager.targetFrame)?submitManager.targetFrame:"_self")+"\" style=\"visibility: hidden\"></form>");
			obj.innerHTML="<input name=\"SubmitManager.PageState\" value=\""+PageState.getStringCode()+"\">";
			document.body.appendChild(obj);
			obj.submit();
		}
	}

	var submitManager=this;
	try{
		for (var i=0; i<submitManager.datasets.length; i++){
			var dataset=submitManager.datasets[i];
			if (!dataset.updateRecord()) return true;
		}
		if (!submitManager.submitNeeded()){
			redirectPage(submitManager);
			return true;
		}

		var updateString=_getUpdateString(submitManager);
		if (compareText(submitManager.submitMode, "default"))
		{
			if (_submitString(submitManager, updateString)){
				_resetDatasetsState(submitManager);
			}
			fireUserEvent(getElementEventName(submitManager, "onSuccess"), [submitManager]);
			redirectPage(submitManager);
			return true;
		}
		else{
			getSubmitForm();
			_form_extra.action=_application_root+submitManager.submitUrl;
			_form_extra.target=(submitManager.targetFrame)?submitManager.targetFrame:"_self";
			_form_extra.updatestring.value=updateString;
			_form_extra.forwardPath.value=submitManager.forwardPath;
			_form_extra.sourceurl.value=window.location.pathname;
			_form_extra["SubmitManager.PageState"].value=PageState.getStringCode();
			_form_extra.submit();
		}
	}
	catch(e){
		var event_result=fireUserEvent(getElementEventName(submitManager, "onError"), [submitManager]);
		if (!event_result) {
			processException(e);
		}
		return false;
	}
}

function _createPageState(){
	this.version="0";
	this.REQUEST_PARAM_NAME="SubmitManager.PageState";
	this.parameter=_createParameters();
	this.getString=_PageState_getString;
	this.setString=_PageState_setString;
	this.getCount=_PageState_getCount;
	this.getStringCode=_PageState_toString;
	this.getRequestText=_PageState_getRequestText;
	return this;
}

function _PageState_getString(name){
	return this.parameter.getParameter(name);
}

function _PageState_setString(name, value){
	this.parameter.setParameter(name, value);
}

function _PageState_getCount(){
	return this.parameter.length;
}

function _PageState_toString(){
	var result="";
	var parameter=this.parameter;
	for(var i=0; i<parameter.length; i++){
		result+=(i==0)?"":";";
		result+=getEncodeStr(parameter[i].name)+"="+
			getEncodeStr(parameter[i].value);
	}
	return getEncodeStr(result);
}

function _PageState_getRequestText() {
	return this.REQUEST_PARAM_NAME+"="+this.getStringCode();
}

//-----------------------------------------------

function _image_onClick(){
	var element=event.srcElement;
	var holder = element.parentElement;
	var field=getElementField(holder);
	var dataset=field.dataset;
	var fieldname=field.name;

	var param=new Object();
	param.dataset=dataset;
	param.field=field.fieldName;
	param.image=element.src;

	var url=field.lobPopupURL;
	if (url.indexOf("?")<0){
		url+="?";
	}
	url+="&lob_sessionkey=" + dataset.getValue(fieldname);
	url+="&dataset_sessionKey=" + dataset.sessionKey;
	url+="&lobfield=" + field.fieldName;
	url+="&oid=" + dataset.getValue("oid");

	showModalDialog(_application_root + url, param,
			"dialogHeight: 400px; dialogWidth: 400px; center: Yes; help: No; resizable: yes; status: No");

	dataset.setValue(fieldname, dataset.getValue(fieldname));
}


//------------------------- yjw add  add params to tab's targetUrl  ----------------------------
// paramNames as "A,B,C" , paramValues as "a,b,c"
function addParams2TabsUrl(tabset , paramNames, paramValues){
	var _paramNames = paramNames.split(",");
	var _paramValues = paramValues.split(",");
	for(var i=0; i<tabset.cells.length; i++){
		if(typeof(tabset.cells[i].targetUrl)!="undefined"&&tabset.cells[i].targetUrl!=""){
			for(var j = 0;j<_paramNames.length;j++){
				if(j==0){
					tabset.cells[i].targetUrl = tabset.cells[i].targetUrl + "?" + _paramNames[j] + "=" + _paramValues[j];
				}else{
					tabset.cells[i].targetUrl = tabset.cells[i].targetUrl + "&" + _paramNames[j] + "=" + _paramValues[j];
				}
			}
		}
	}
}
//--------------shen_antonio-------------------------

function megerURL(applicationRoot,url){
	var v_url = url;
	if(v_url && v_url.length>0){
		var s = v_url.substring(0,1);
		if(s == '/'){
			return applicationRoot + v_url;
		}else{
			return applicationRoot + "/" + v_url;
		}
	}else{
		return applicationRoot + "/";
	}
}

//----------------shen_antonio-----------------------
function compareDate(date1,date2,month){
  var   d1Year=date1.getFullYear();
  var   d1Mohth=date1.getMonth();
  var   d1Date=date1.getDate();
  if(new Date(d1Year,d1Mohth+month,d1Date)< date2){
   	   return false;
  }else{
   		return true;
  }
}
/*----------------comprare Date---------------------
 	date1  type : string(yyyymmdd) or date
 	date2  type : string(yyyymmdd) or date
*/
function compareDateFunction(date1,date2){
	var leftDate;
	var rightDate;
	if(typeof(date1) == "object"){
		leftDate = date1;
	}else{
		leftDate = convertStr2Date(date1);
	}
	if(typeof(date2) == "object"){
		rightDate = date2;
	}else{
		rightDate = convertStr2Date(date2);
	}
	return compareDate(leftDate,rightDate,0);
}


/*-------------String Convert to Date------------------
	dateStr  type	: string(yyyymmdd)
	return type		: date
*/
function convertStr2Date(dateStr)
{
	//wrap firt four char to year
	var year = parseInt(dateStr.substring(0,4));

	//get month str
	var sMonth = dateStr.substring(4,6);

	//splice the '0' when sMonth like '01','02',,...
	if('0'==sMonth.charAt(0))
		sMonth = sMonth.substring(1,2);
	var month = parseInt(sMonth);//-1;
	//get day str

	if(dateStr.substring(6,7)=="0"){
		day = parseInt(dateStr.substring(7,8));
	}else{
		day = parseInt(dateStr.substring(6,8));
	}
	//convert to the type of Date
	return new Date(year + "/" + month + "/" + day);
}