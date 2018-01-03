var _fileIncluded_control=true;
var _init_flag = true;

function initDataPilot(dataPilot){
	var dataset=getElementDataset(dataPilot);
	if (!dataPilot.getAttribute("pageSize")){
		if (dataset) dataPilot.pageSize=dataset.pageSize;
	}
	var pageSize=dataPilot.getAttribute("pageSize");

	for(var i=0; i<dataPilot.tBodies[0].rows.length; i++){
		var row=dataPilot.tBodies[0].rows[i];
		row.removeNode(true);
	}

	var buttons_str=getValidStr(dataPilot.getAttribute("buttons"));
	if (buttons_str=="" || compareText(buttons_str, "default"))
		buttons_str="movefirst,prevpage,moveprev,movenext,nextpage,movelast,appendrecord,deleterecord,cancelrecord,updaterecord";
	else if (compareText(buttons_str, "readonly"))
		buttons_str="movefirst,prevpage,moveprev,movenext,nextpage,movelast";
	buttons_str=buttons_str.toLowerCase();
	var buttons=buttons_str.split(",");

	var row=dataPilot.tBodies[0].insertRow();
	row.align="center";
	for(var i=0; i<buttons.length; i++){
		btn=document.createElement("<input type=button class=button hideFocus=true style=\"height: 22px\">");
		btn.style.backgroundImage = "url("+_theme_root+"/button.gif)";
		btn.tabIndex=-1;
		btn.onmouseover=_button_onmouseover;
		btn.onmouseout=_button_onmouseout;
		btn.onclick=_datapilot_onclick;

		btn.dataset=dataPilot.getAttribute("dataset");
		btn.buttonType=buttons[i];
		btn.datapiolt=dataPilot;

		switch(buttons[i]){
			case "movefirst":{
				btn.style.fontFamily="Webdings";
				btn.value="9";
				btn.title=constDatasetMoveFirst;
				btn.style.width=30;
				break;
			}
			case "prevpage":{
				btn.style.fontFamily="Webdings";
				btn.value="7";
				btn.title=constDatasetPrevPage;
				btn.style.width=30;
				break;
			}
			case "moveprev":{
				btn.style.fontFamily="Webdings";
				btn.value="3";
				btn.title=constDatasetMovePrev;
				btn.style.width=30;
				break;
			}
			case "movenext":{
				btn.style.fontFamily="Webdings";
				btn.value="4";
				btn.title=constDatasetMoveNext;
				btn.style.width=30;
				break;
			}
			case "nextpage":{
				btn.style.fontFamily="Webdings";
				btn.value="8";
				btn.title=constDatasetNextPage;
				btn.style.width=30;
				break;
			}
			case "movelast":{
				btn.style.fontFamily="Webdings";
				btn.value=":";
				btn.title=constDatasetMoveLast;
				btn.style.width=30;
				break;
			}
			case "insertrecord":{
				btn.value=constBtnInsertRecord;
				btn.title=constDatasetInsertRecord;
				btn.style.width=45;
				break;
			}
			case "appendrecord":{
				btn.value=constBtnAppendRecord;
				btn.title=constDatasetAppendRecord;
				btn.style.width=45;
				break;
			}
			case "deleterecord":{
				btn.value=constBtnDeleteRecord;
				btn.title=constDatasetDeleteRecord;
				btn.style.width=45;
				break;
			}
			case "editrecord":{
				btn.value=constBtnEditRecord;
				btn.title=constDatasetEditRecord;
				btn.style.width=45;
				break;
			}
			case "cancelrecord":{
				btn.value=constBtnCancelRecord;;
				btn.title=constDatasetCancelRecord;
				btn.style.width=45;
				break;
			}
			case "updaterecord":{
				btn.value=constBtnUpdateRecord;
				btn.title=constDatasetUpdateRecord;
				btn.style.width=45;
				break;
			}
		}
		btn.id=dataPilot.id+"_"+btn.buttonType;
		row.insertCell().appendChild(btn);
	}

	refreshDataPilot(dataPilot);
}

function setDataPilotButtons(dataPilot, buttons){
	dataPilot.buttons=buttons;
	initDataPilot(dataPilot);
}

function refreshDataPilot(dataPilot){

	function refreshButton(btn, enable){
			btn.disabled=!enable;
			refreshButtonColor(btn);
	}

	var dataset=getElementDataset(dataPilot);

	var row=dataPilot.rows[0];
	for(var i=0; i<row.cells.length; i++){
		var btn=row.cells[i].children[0];
		switch(btn.buttonType){
			case "movefirst":;
			case "moveprev":{
				refreshButton(btn, (dataset && !dataset.bof));
				break;
			}
			case "prevpage":{
				refreshButton(btn, (dataset && dataset.record && dataset.record.pageIndex>1));
				break;
			}
			case "movenext":;
			case "movelast":{
				refreshButton(btn, (dataset && !dataset.eof));
				break;
			}
			case "nextpage":{
				refreshButton(btn, (dataset && dataset.record && dataset.record.pageIndex<dataset.pageCount));
				break;
			}
			case "insertrecord":;
			case "appendrecord":{
				refreshButton(btn, (dataset && !dataset.readOnly));
				break;
			}
			case "editrecord":{
				refreshButton(btn, (dataset && !(dataset.bof && dataset.eof) && !dataset.readOnly));
				break;
			}
			case "deleterecord":{
				refreshButton(btn, (dataset && !(dataset.bof && dataset.eof) && !dataset.readOnly));
				break;
			}
			case "cancelrecord":;
			case "updaterecord":{
				refreshButton(btn, (dataset && (dataset.state=="insert" || dataset.state=="modify") && !dataset.readOnly));
				break;
			}
		}

		fireUserEvent(getElementEventName(dataPilot, "onRefreshButton"), [dataPilot, btn, btn.buttonType, dataset]);
	}
}

function _datapilot_onclick(){
	if (event.srcElement.disabled) return;
	var datapiolt=event.srcElement.datapiolt;
	var dataset=getElementDataset(datapiolt);

	var eventName=getElementEventName(datapiolt, "onButtonClick");
	if (isUserEventDefined(eventName)){
			var event_result=fireUserEvent(eventName, [datapiolt, event.srcElement, event.srcElement.buttonType, dataset]);
			if (!event_result) return;
	}

	var pageSize=datapiolt.getAttribute("pageSize");

	switch(event.srcElement.buttonType){
		case "movefirst":{
			dataset.moveFirst();
			break;
		}
		case "prevpage":{
			var pageIndex=(dataset.record)?dataset.record.pageIndex-1:1;
			dataset.moveToPage(pageIndex);
			break;
		}
		case "moveprev":{
			dataset.movePrev();
			break;
		}
		case "movenext":{
			dataset.moveNext();
			break;
		}
		case "nextpage":{
			var pageIndex=(dataset.record)?dataset.record.pageIndex+1:1;
			dataset.moveToPage(pageIndex);
			break;
		}
		case "movelast":{
			dataset.moveLast();
			break;
		}
		case "insertrecord":{
			dataset.insertRecord("before");
			break;
		}
		case "appendrecord":{
			dataset.insertRecord("end");
			break;
		}
		case "editrecord":{
			dataset_setState(dataset, "modify");
			break;
		}
		case "deleterecord":{
			if (isTrue(datapiolt.getAttribute("confirmDelete"))){
					if (confirm(constDatasetDeleteRecord)) dataset.deleteRecord();
			}
			else
					dataset.deleteRecord();
			break;
		}
		case "cancelrecord":{
			if (isTrue(datapiolt.getAttribute("confirmCancel"))){
					if (confirm(constDatasetCancelRecord)) dataset.cancelRecord();
			}
			else
					dataset.cancelRecord();
			break;
		}
		case "updaterecord":{
			dataset.updateRecord();
			break;
		}
	}
}

function initTabSet(tabset){
	tabset._imagePrefix = _theme_root + "/tabset/" + tabset.tabPlacement + "_";
	var parentDiv=tabset.parentElement;
	parentDiv.style.width=parentDiv.offsetWidth;

	var tabs=tabset.getAttribute("tabs");
	//**************** youjinwu modify delete last";"
	if(tabs.length>0&&tabs.substring(tabs.length-1,tabs.length)==";")
	tabs  = tabs.substring(0,tabs.length-1);
	//**************** youjinwu modify end
	if (!tabs) return;
	var tabs=tabs.split(";");

	for(var i=0; i<tabset.tBodies[0].rows.length; i++){
		var row=tabset.tBodies[0].rows[i];
		row.removeNode(true);
	}

	var row=tabset.tBodies[0].insertRow();
	var cell=row.insertCell();
	cell.firstCell=true;
	cell.innerHTML="<img src=\""+tabset._imagePrefix+"start_tab.gif\">";

	var label, tabname, index;
	for(i=0; i<tabs.length; i++){
			props=tabs[i].split(",");
			cell=row.insertCell();
			cell.background=tabset._imagePrefix+"tab_button.gif";
			cell._tabIndex=i;
			label=props[0];
			tabname=props[1];
			cell.tabName=tabname;
			cell.label=label;
			cell.targetUrl=getDecodeStr(props[2]);
			//cell.targetUrl=_application_root+getDecodeStr(props[2]);


			btn=document.createElement("<DIV hideFocus=true nowrap class=tab></DIV>");
			btn.innerText=getDecodeStr(props[0]);
			btn._tabIndex=-1;
			btn.onclick=_tabset_onclick;
			btn.onmouseover=_tabset_onmouseover;
			btn.onmouseout=_tabset_onmouseout;
			btn.tab=cell;
			cell.appendChild(btn);

			cell=row.insertCell();
			if (i!=tabs.length-1){
				cell.innerHTML="<img src=\""+tabset._imagePrefix+"tab.gif\">";
			}
			else{
				cell.lastCell=true;
				cell.innerHTML="<img src=\""+tabset._imagePrefix+"end_tab.gif\">";
			}

			eval("var tabsetBody=_body_"+tabset.id+";");
			eval("if (typeof(" + tabset.id+"_"+tabname + ")!=\"undefined\") var tab="+tabset.id+"_"+tabname+";");

			if (typeof(tab)!="undefined") {
				tab.extra="tab";
				tab.style.visibility="hidden";
				_setChildTableVisibility(tab, "hidden");
				tab.style.overflow="auto";
				tab.style.position="absolute";
				tab.style.left=0;
				tab.style.top=0;
				//tab.style.width=tabsetBody.clientWidth-4;
				//tab.style.height=tabsetBody.clientHeight-4;
				tab.style.margin=2;
			}
	}
	cell=row.insertCell();
	cell.width="100%";
	cell.background=tabset._imagePrefix+"tab_blank.gif";

	/* shen_antonio .*/
	//setActiveTabIndex(tabset, getInt(tabset.getAttribute("tabIndex")));
	if(v_currentTab){
		setActiveTab(tabset,v_currentTab);
	}else{
		setActiveTabIndex(tabset, getInt(tabset.getAttribute("tabIndex")));
	}
	//setActiveTabIndex_new(tabset, getInt(tabset.getAttribute("tabIndex")));
	if (tabset.offsetWidth > parentDiv.clientWidth) {
		var buttonPane=document.createElement("<div style=\"width:30; cursor:hand; z-index:1000\"></div>");
		buttonPane.innerHTML="<img width=\"15\" height=\"15\" src=\""+_theme_root+"/tabset/scroll_button1.gif\" "+
			"onmousedown=\"_tabpane_"+tabset.id+".scrollLeft=_tabpane_"+tabset.id+".scrollLeft-50\">"+
			"<img width=\"15\" height=\"15\" src=\""+_theme_root+"/tabset/scroll_button2.gif\" "+
			"onmousedown=\"_tabpane_"+tabset.id+".scrollLeft=_tabpane_"+tabset.id+".scrollLeft+50\">";
		buttonPane.style.position="absolute";
		eval("var pos=getAbsPosition(_tabpane_"+tabset.id+");");
		buttonPane.style.left=pos[0];
		buttonPane.style.top=pos[1] + 4;
		eval("_tabdiv_"+tabset.id+".appendChild(buttonPane);");
	}

	eval("var tabsetPane=_tabsetpane_"+tabset.id+";");
	tabsetPane.tabPane=parentDiv;
	tabsetPane.onresize=tabSet_onResize;
}

function tabSet_onResize() {
	var tabsetPane=event.srcElement;
	var tabPane=tabsetPane.tabPane;
	tabPane.style.width=tabsetPane.offsetWidth;
}

function setTabs(tabset, tabs){
	tabset.tabs=tabs;
	initTabSet(tabset);
}

function _setChildTableVisibility(element, vis){
	for (var i=0; i<element.children.length; i++){
		var obj=element.children[i];
		if (compareText(obj.getAttribute("extra"), "datatable")){
			obj.style.visibility=vis;
			if (!compareText(vis, "hidden") && obj.needRefresh) {
				obj.refreshData();
			}
		}
		_setChildTableVisibility(obj, vis);
	}
}

function _setActiveTab(cell){
	try{
		var row=getRowByCell(cell);
		var tabset=getTableByRow(row);
		var selectCell=tabset.selectTab;

		if (selectCell==cell) return;
		var oldName=(selectCell)?selectCell.tabName:"";
		var newName=cell.tabName;

		var eventName=getElementEventName(tabset, "beforeTabChange");
		var event_result=fireUserEvent(eventName, [tabset, oldName, newName]);
		if (event_result) throw event_result;

		eval("var tabsetBody=_body_"+tabset.id+";");
		if (selectCell){
			var prevCell=row.cells[selectCell.cellIndex-1];
			var nextCell=row.cells[selectCell.cellIndex+1];

			selectCell.background=tabset._imagePrefix+"tab_button.gif";

			if (prevCell.firstCell)
				prevCell.firstChild.src=tabset._imagePrefix+"start_tab.gif";
			else
				prevCell.firstChild.src=tabset._imagePrefix+"tab.gif";

			if (nextCell.lastCell)
				nextCell.firstChild.src=tabset._imagePrefix+"end_tab.gif";
			else
				nextCell.firstChild.src=tabset._imagePrefix+"tab.gif";

			var tab=null;
			eval("if (typeof(" + tabset.id+"_"+oldName + ")!=\"undefined\") tab="+tabset.id+"_"+oldName+";");
			if (tab) {
				_stored_element=tab;
				_setChildTableVisibility(tab, "hidden");
				document.body.appendChild(tab);
				var s="_stored_element.style.position=\"absolute\";"+
					"_stored_element.style.visibility=\"hidden\";";
				setTimeout(s, 0);
			}
		}

		var prevCell=row.cells[cell.cellIndex-1];
		var nextCell=row.cells[cell.cellIndex+1];

		cell.background=tabset._imagePrefix+"active_tab_button.gif";

		if (prevCell.firstCell)
			prevCell.firstChild.src=tabset._imagePrefix+"active_start_tab.gif";
		else
			prevCell.firstChild.src=tabset._imagePrefix+"active_tab1.gif";

		if (nextCell.lastCell)
			nextCell.firstChild.src=tabset._imagePrefix+"active_end_tab.gif";
		else
			nextCell.firstChild.src=tabset._imagePrefix+"active_tab2.gif";

		var tab=null;
		eval("if (typeof(" + tabset.id+"_"+newName + ")!=\"undefined\") tab="+tabset.id+"_"+newName+";");
		if (tab) {
			tabsetBody.appendChild(tab);
			tab.style.position="";
			tab.style.visibility="";
			_setChildTableVisibility(tab, "");
		}

		tabset.selectTab=cell;
		tabset.selectName=cell.tabName;
		tabset.selectIndex=cell._tabIndex;
		if (cell.targetUrl){
			//20030920 TabSet的TargetUrl中不必加入tabIndex参数
			//var url=cell.targetUrl+((cell.targetUrl.indexOf("?")>=0)?"&":"?");
			//url+=tabset.id+"_tabIndex="+cell._tabIndex;
			/*shen_antonio.*/
			//alert(cell.tabName);
			//alert(cell.targetUrl);
			open(cell.targetUrl, tabset.targetFrame);
			/*
			document.getElementById(tabset.id + "_" + cell.tabName).innerHTML="<IFRAME frameborder=\"0\" scrolling=\"auto\" width=\"100%\" height=\"100%\""
			+ " src=\""+ cell.targetUrl +"\""+ "</IFRAME>";
			*/
		}

		var eventName=getElementEventName(tabset, "afterTabChange");
		fireUserEvent(eventName, [tabset, oldName, newName]);
	}
	catch(e){
		processException(e);
	}
}

function setActiveTab(table, tabname){
	if (!tabname) return;
	for(var i=0; i<table.cells.length; i++){
		if (table.cells[i].tabName==tabname){
			_setActiveTab(table.cells[i]);
			break;
		}
	}
}

function setActiveTabIndex(table, index){
	for(var i=0; i<table.cells.length; i++){
		if (table.cells[i]._tabIndex==index){
			_setActiveTab(table.cells[i]);
			if(index>10){
				temp=document.getElementById("_tabpane_"+table.id);
				temp.scrollLeft=temp.scrollLeft+500;
			}
			break;
		}
	}
}
/* shen_antonio.*/
function setActiveTabIndex_new(table, index){
	for(var i=0; i<table.cells.length; i++){
		if (table.cells[i]._tabIndex==index){
			_setActiveTab_new(table.cells[i]);
			if(index>10){
				temp=document.getElementById("_tabpane_"+table.id);
				temp.scrollLeft=temp.scrollLeft+500;
			}
			break;
		}
	}
}
function _setActiveTab_new(cell){
	try{
		var row=getRowByCell(cell);
		var tabset=getTableByRow(row);
		var selectCell=tabset.selectTab;

		if (selectCell==cell) return;
		var oldName=(selectCell)?selectCell.tabName:"";
		var newName=cell.tabName;

		var eventName=getElementEventName(tabset, "beforeTabChange");
		var event_result=fireUserEvent(eventName, [tabset, oldName, newName]);
		if (event_result) throw event_result;

		eval("var tabsetBody=_body_"+tabset.id+";");
		if (selectCell){
			var prevCell=row.cells[selectCell.cellIndex-1];
			var nextCell=row.cells[selectCell.cellIndex+1];

			selectCell.background=tabset._imagePrefix+"tab_button.gif";

			if (prevCell.firstCell)
				prevCell.firstChild.src=tabset._imagePrefix+"start_tab.gif";
			else
				prevCell.firstChild.src=tabset._imagePrefix+"tab.gif";

			if (nextCell.lastCell)
				nextCell.firstChild.src=tabset._imagePrefix+"end_tab.gif";
			else
				nextCell.firstChild.src=tabset._imagePrefix+"tab.gif";

			var tab=null;
			eval("if (typeof(" + tabset.id+"_"+oldName + ")!=\"undefined\") tab="+tabset.id+"_"+oldName+";");
			if (tab) {
				_stored_element=tab;
				_setChildTableVisibility(tab, "hidden");
				document.body.appendChild(tab);
				var s="_stored_element.style.position=\"absolute\";"+
					"_stored_element.style.visibility=\"hidden\";";
				setTimeout(s, 0);
			}
		}

		var prevCell=row.cells[cell.cellIndex-1];
		var nextCell=row.cells[cell.cellIndex+1];

		cell.background=tabset._imagePrefix+"active_tab_button.gif";

		if (prevCell.firstCell)
			prevCell.firstChild.src=tabset._imagePrefix+"active_start_tab.gif";
		else
			prevCell.firstChild.src=tabset._imagePrefix+"active_tab1.gif";

		if (nextCell.lastCell)
			nextCell.firstChild.src=tabset._imagePrefix+"active_end_tab.gif";
		else
			nextCell.firstChild.src=tabset._imagePrefix+"active_tab2.gif";

		var tab=null;
		eval("if (typeof(" + tabset.id+"_"+newName + ")!=\"undefined\") tab="+tabset.id+"_"+newName+";");
		if (tab) {
			tabsetBody.appendChild(tab);
			tab.style.position="";
			tab.style.visibility="";
			_setChildTableVisibility(tab, "");
		}

		tabset.selectTab=cell;
		tabset.selectName=cell.tabName;
		tabset.selectIndex=cell._tabIndex;

		if (cell.targetUrl){
			//20030920 TabSet的TargetUrl中不必加入tabIndex参数
			//var url=cell.targetUrl+((cell.targetUrl.indexOf("?")>=0)?"&":"?");
			//url+=tabset.id+"_tabIndex="+cell._tabIndex;
			/*shen_antonio.*/
			//open(cell.targetUrl, tabset.targetFrame);
		}

		var eventName=getElementEventName(tabset, "afterTabChange");
		fireUserEvent(eventName, [tabset, oldName, newName]);
	}
	catch(e){
		processException(e);
	}
}

function _tabset_onclick(){
	var tab=event.srcElement.tab;
	_setActiveTab(tab);
}

function _tabset_onmouseover(){
	event.srcElement.style.color="blue";
	event.srcElement.style.textDecorationUnderline=true;
}

function _tabset_onmouseout(){
	event.srcElement.style.color="black";
	event.srcElement.style.textDecorationUnderline=false;
}

function initButton(button) {
	button.hideFocus=true;
	setButtonDown(button, button.getAttribute("down"))
	button.onmousedown=_button_onmousedown;
	button.onmouseup=_button_onmouseup;
	button.onmouseover=_button_onmouseover;
	button.onmouseout=_button_onmouseout;
	if (button.onclick==null && !isTrue(button.getAttribute("defaultOperation"))) {
		button.onclick=_button_onclick;
	}
	button.title = getDecodeStr(button.toolTip);
}

function refreshButtonColor(button){
	if (isTrue(button.getAttribute("down"))){
		button.className="button_down";
		button.style.color = "white";
		button.style.backgroundImage = "url("+_theme_root+"/button_down.gif)";
	}
	else{
		button.className="button";
		button.style.color = "black";
		button.style.backgroundImage = "url("+_theme_root+"/button.gif)";
	}
}

function setButtonDown(button, down){
	button.down=isTrue(down);
	refreshButtonColor(button);
}

function _button_onmousedown(){
	var button=event.srcElement;
	fireUserEvent(getElementEventName(button, "onMouseDown"), [button]);
	var menu=button.getAttribute("menu");

	if (typeof(menu)=="string" && menu!=""){
		eval("menu="+menu);
		button.menu=menu;
	}

	if (menu){
		showButtonMenu(menu, button);
	}
}

function _button_onmouseup(){
	var button=event.srcElement;
	if (isTrue(button.getAttribute("allowPushDown"))){
		var down=button.getAttribute("down");
		setButtonDown(button, !down);
	}
	fireUserEvent(getElementEventName(button, "onMouseUp"), [button]);
}

function _button_onmouseover(){
	try{
		var button=event.srcElement;
		if (button.disabled || button.down) return;
		button.style.backgroundImage="url()";
		fireUserEvent(getElementEventName(button, "onMouseEnter"), [button]);
	}
	catch(e){
		//do nothing
	}
}

function _button_onmouseout(){
	try{
		var button=event.srcElement;
		if (button.disabled) return;
		refreshButtonColor(button);
		fireUserEvent(getElementEventName(button, "onMouseLeave"), [button]);
	}
	catch(e){
		//do nothing
	}
}

function _button_onclick(){
	var button=event.srcElement;
	if (button.defaultOperation){
		switch (button.defaultOperation.toLowerCase())
		{
		case "submitupdate":
			if (button.submitManager) {
				eval(button.submitManager + ".submit();");
			}
			break;
		case "refreshpage":
			window.open(window.location.href, "_self");
			break;
		case "submitform":
			if (button.autoForm) {
				eval("var autoForm=" + button.autoForm);
				if (autoForm) {
					eval("var dataset=" + autoForm.dataset);
					if (dataset) {
					/*
						var form=document.createElement("<form method=\""+autoForm.method+"\" action=\""+autoForm.action+"\" "+
							"style=\"visibility: hidden\"></form>");
							*/
							var form = document.createElement("FORM");
							form.method = autoForm.method;
							form.action = autoForm.action;
							form.style.visibility ="hidden";

						for (var i=0; i<dataset.fields.fieldCount; i++){
						/** shen_antonio
							form.insertAdjacentHTML("beforeEnd", "<input type=\"hidden\" name=\""+dataset.getField(i).name+"\" >");
						*/
						form.insertAdjacentHTML("beforeEnd", "<input type=\"hidden\" name=\""+dataset.getField(i).fieldName+"\" >");
						}
					}
					document.body.appendChild(form);

					for (var i=0; i<dataset.fields.fieldCount; i++){
					/** shen_antonio
						form[dataset.getField(i).name].value=dataset.getString(i);
						*/
						form[dataset.getField(i).fieldName].value=dataset.getString(i);
					}
					//document.body.appendChild(form);
					//alert(form.innerHTML);

					form.submit();
				}
			}
			break;
		}
	}
	else{
		fireUserEvent(getElementEventName(button, "onClick"), [button]);
	}
}

function initGroupBoxTitle(element){
	var expand=element.parentElement.expand;
	var imgUrl;
	var alt;
	if(expand=="true"){
		imgUrl=_theme_root+"/group_expand.gif";
		alt=constGroupBoxCollapseAlt;
	}else{
		imgUrl=_theme_root+"/group_collapse.gif";
		alt=constGroupBoxExpandAlt;
	}
	element.innerHTML+="<img id='" + element.id + "_img' expand='"+expand+"' alt='"+alt+"' src='"+imgUrl+"' onclick='_groupboxtitle_onClick(this)' style='cursor: hand;'/>";
}

function _groupboxtitle_onClick(img){
	var outerContainer=img.parentElement.parentElement.getElementsByTagName('DIV')[0];
	if(!outerContainer) return;
	var innerContainer=outerContainer.getElementsByTagName('DIV')[0];
	if(!innerContainer) return;
	
	if(img.expand=="true"){
		outerContainer.style["height"]="5px";
		outerContainer.style["width"] = outerContainer.offsetWidth;
		innerContainer.style["display"]="none";
		img.src=_theme_root+"/group_collapse.gif";
		img.alt=constGroupBoxExpandAlt;
		img.expand="false";
	}else{
		outerContainer.style["height"]="";
		outerContainer.style["width"] = "";
		innerContainer.style["display"]="";
		img.src=_theme_root+"/group_expand.gif";
		img.alt=constGroupBoxCollapseAlt;
		img.expand="true";
	}
}
