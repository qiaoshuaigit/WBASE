var _fileIncluded_dataset=true;
var _maxAutoGenID=0;

function _getAutoGenID(){
	_maxAutoGenID++;
	return "__"+_maxAutoGenID;
}
function _downloadData(dataset, pageSize, pageIndex){
	try{
		if (dataset.sessionKey){
		var result=new Object();
		//result = _downloadData_new(dataset, pageSize, pageIndex);
		return result;
		/*
			var _url=_application_root+dataset.downloadUrl;
			//alert(_application_root);
			var _body = "sessionkey="+dataset.sessionKey;
			_body+="&pagesize="+getValidStr(pageSize);

			if (pageIndex)
				_body+="&pageindex="+getValidStr(pageIndex);
			else
				_body+="&pageindex=1";

			for(var i=0; i<dataset.parameters.length; i++){
				_body+="&paramname="+dataset.parameters[i].name+
					"&paramvalue="+getEncodeStr(dataset.parameters[i].value)+
					"&paramtype="+dataset.parameters[i].dataType;
			}




			//alert(_url);
			var XMLHttp = new ActiveXObject("Microsoft.XMLHTTP");

			XMLHttp.open("POST",_url,false);
			XMLHttp.setRequestHeader("CONTENT-TYPE","application/x-www-form-urlencoded");
			XMLHttp.send(_body);


			var xmlDoc = new ActiveXObject("Msxml2.DOMDocument");

      		xmlDoc.async = false;
      		xmlDoc.loadXML(XMLHttp.responseText);

			var XMLRoot = xmlDoc.documentElement;


			if (isTrue(XMLRoot.selectSingleNode("Succeed").text)){
				var datasetRoot=XMLRoot.selectSingleNode("Dataset");

				var result=new Object();
				result.fieldStr=getValidStr(datasetRoot.selectSingleNode("Fields").text);

				result.recordStr=getValidStr(datasetRoot.selectSingleNode("Data").text);

				result.pageCount=getInt(datasetRoot.selectSingleNode("PageSize").text);

				result.pageIndex=getInt(datasetRoot.selectSingleNode("PageIndex").text);

				result.pageCount=getInt(datasetRoot.selectSingleNode("PageCount").text);

				delete XMLHttp;
				delete xmlDoc;
				return result;

			}
			else{
				var error_text=XMLRoot.selectSingleNode("Error").text;
				delete XMLHttp;
				delete xmlDoc;
				throw constErrDownLoadFailed+"\n"+constErrDescription+":"+error_text;
			}
		}
			*/
	}
	}
	catch(e){
		processException(e);
	}
}

function getDatasetByID(ID){
	for(var i=0; i<_array_dataset.length; i++){
		if (_array_dataset[i].id==ID) {
			return _array_dataset[i];
			}
	}

	var result=null;
	eval("if (typeof("+ID+")!=\"undefined\") result="+ID+";");
	return result;
}

function getDatasets(){
	return _array_dataset;
}

function setElementDataset(element, dataset){
	var _dataset;
	if (typeof(dataset)=="string"){
		_dataset=getDatasetByID(dataset);
	}
	else{
		_dataset=dataset;
	}
	var old_dataset=element.getAttribute("dataset");

	if (old_dataset){
		var array=old_dataset.editors;
		if (array) pArray_ex_delete(array, element);
	}

	if (_dataset){
		var array=_dataset.editors;
		if (!array){
			array=new pArray();
			_dataset.editors=array;
		}

		pArray_ex_insert(array, element);
	}
	element.dataset=_dataset;
}

function _dataset_getField(fields, fieldName){
	var field=null;
	if (typeof(fieldName)=="number"){
		field=fields[fieldName];
	}
	else if (typeof(fieldName)=="string"){
		var fieldIndex=fields["_index_"+fieldName.toLowerCase()];
		if (!isNaN(fieldIndex)) field=fields[fieldIndex];
	}
	return field;
}

function dataset_getField(fieldName){
	var dataset=this;
	return _dataset_getField(dataset.fields, fieldName);
}

function appendFromDataString(dataset, fieldStr, recordStr, init){
	if (!recordStr) return;
	var fields=null;
	if (fieldStr) {
		fields=fieldStr.split(",");
	}
	var records=recordStr.split(";");
	for(var i=0; i<records.length; i++){
		var record=records[i].split(",");
		for(var j=0; j<record.length; j++){
			record[j]=getDecodeStr(record[j]);
		}
		var swapRec=new Array();
		if (fields!=null) {
			for(var j=0; j<fields.length; j++){
				var index ;
					var field = _dataset_getField(dataset.fields,fields[j].toLowerCase());
					//alert(field.tag);
					// modify by yjw , when addfield will set the tag : field.tag = "select"
					//if(field.tag=="select"){
					//	index=dataset.fields["_index_"+fields[j+1].toLowerCase()];
					//	index = index-1;
					//}else{
					//	index=dataset.fields["_index_"+fields[j].toLowerCase()];
					//}
					index=dataset.fields["_index_"+fields[j].toLowerCase()];
					//alert("index : " + index);
				swapRec[index]=record[j];
			}
			record=swapRec;
		}
		pArray_insert(dataset, "end", null, record);
		if (init) {
			initRecord(record, dataset);
		}
	}
}

function transferToDataString(dataset){
	var result="";
	var i=0;
	var record=dataset.getFirstRecord();
	while (record){
		if (i!=0) result+=";";
		for(var j=0; j<dataset.fields.fieldCount; j++){
			if (j!=0) result+=",";
			result+=getEncodeStr(record.getString(j));
		}
		record=record.getNextRecord();
		i++;
	}
	return result;
}

function createDataset(ID, fieldStr, recordStr){
	var dataset=new pArray();
	dataset.fields=new Array();
	dataset.parameters=_createParameters();
	dataset.updateItems=new Array();
	dataset.fields.fieldCount=0;
	dataset.addField=dataset_addField;
	dataset.pageSize=9999;
	dataset.pageCount=1;
	dataset.pageIndex=1;
	dataset.autoLoadPage=true;
	//yjw add ,change the state in resetDataSetRecordState() of bank.js
	dataset.loadCompleted=false;

	dataset._saveOldValue=record_saveOldValue;
	dataset._getValue=record_getValue;
	dataset._getString=record_getString;
	dataset._getViewString=record_getViewString;
	dataset._setValue=record_setValue;
	dataset._getCurValue=record_getCurValue;
	dataset._setCurValue=record_setCurValue;
	dataset._getOldValue=record_getOldValue;
	dataset._setOldValue=record_setOldValue;
	dataset._getPrevRecord=record_getPrevRecord;
	dataset._getNextRecord=record_getNextRecord;

	dataset.getField=dataset_getField;
	dataset.getValue=dataset_getValue;
	dataset.getString=dataset_getString;
	dataset.getViewString=dataset_getViewString;
	dataset.setValue=dataset_setValue;
	dataset.setValue2=dataset_setValue_2;
	dataset.getCurValue=dataset_getCurValue;
	dataset.setCurValue=dataset_setCurValue;
	dataset.getOldValue=dataset_getOldValue;
	dataset.setOldValue=dataset_setOldValue;
	dataset.disableControls=dataset_disableControls;
	dataset.enableControls=dataset_enableControls;
	dataset.disableEvents=dataset_disableEvents;
	dataset.enableEvents=dataset_enableEvents;
	dataset.refreshControls=dataset_refreshControls;
	dataset.setRecord=dataset_setRecord;
	dataset.setReadOnly=dataset_setReadOnly;
	//yjw add begin
	dataset.setAllFieldsReadOnly = dataset_setAllFieldsReadOnly;
	//yjw add end
	dataset.setFieldReadOnly=dataset_setFieldReadOnly;
	dataset.getFirstRecord=dataset_getFirstRecord;
	dataset.getLastRecord=dataset_getLastRecord;
	dataset.move=dataset_move;
	dataset.movePrev=dataset_movePrev;
	dataset.moveNext=dataset_moveNext;
	dataset.moveFirst=dataset_moveFirst;
	dataset.moveLast=dataset_moveLast;
	dataset.find=dataset_find;
	dataset.locate=dataset_locate;
	dataset.updateRecord=dataset_updateRecord;
	dataset.cancelRecord=dataset_cancelRecord;
	dataset.insertRecord=dataset_insertRecord;
	dataset.deleteRecord=dataset_deleteRecord;
	dataset.copyRecord=dataset_copyRecord;
	dataset.loadPage=dataset_loadPage;
	dataset.loadDetail=dataset_loadDetail;
	dataset.isPageLoaded=dataset_isPageLoaded;
	dataset.moveToPage=dataset_moveToPage;
	dataset.setMasterDataset=dataset_setMasterDataset;
	dataset.flushData=dataset_flushData;
	dataset.clearData=dataset_clearData;
	dataset.sort=dataset_sort;
	dataset.setParameter=dataset_setParameter;
	dataset.getParameter=dataset_getParameter;
	dataset.getParameterName=dataset_getParameterName;
	dataset.getParameterCount=dataset_getParameterCount;
	dataset.getRealRecordCounts= dataset_getRealRecordCounts;

	if (ID){
		dataset.id=ID;
		_array_dataset[_array_dataset.length]=dataset;
	}

	if (fieldStr){
		var fields=fieldStr.split(",");
		for(var i=0; i<fields.length; i++){
			dataset.addField(fields[i]);
		}
	}

	appendFromDataString(dataset, null, recordStr);

	return dataset;
}

function dataset_setParameter(name, value, dataType){
	this.parameters.setParameter(name, value, dataType);
}

function dataset_getParameter(name){
	return this.parameters.getParameter(name);
}

function dataset_getParameterName(index){
	//return this.parameters.getParameter(index);
	return this.parameters[index].name;
}

function dataset_getParameterCount(){
	var dataset=this;
	return dataset.parameters.length;
}

function dataset_addField(name, dataType){
	var dataset=this;
	try{
		if (getValidStr(name)=="")
			throw constErrEmptyFieldName;

		if (dataset.prepared)
			throw constErrAddDataField;

		var _name=name.toLowerCase();
		var field=new Object;
		var i=dataset.fields.length;
		dataset.fields["_index_"+_name]=i;
		dataset.fields[i]=field;
		dataset.fields.fieldCount++;
		field.index=i;
		field.dataset=dataset;
		field.fields=dataset.fields;
		field.name=_name;
		field.label=name;
		field.fieldName=name;
		field.dataType=dataType;

		switch (dataType){
			case "string":{
				field.editorType="text";
				field.align="left";
				field.vAlign="top";
				break;
			}

			case "byte":;
			case "short":;
			case "int":;
			case "long":;
			case "float":;
			case "double":;
			/** add by shen_antonio 20080524 for add currency type.*/
			case "currency":;
			case "bigdecimal":{
				field.editorType="text";
				field.align="right";
				field.vAlign="top";
				break;
			}

			case "boolean":{
				field.editorType="checkbox";
				field.align="middle";
				field.vAlign="middle";
				break;
			}

			case "date":{
				field.editorType="text";
				field.align="left";
				field.vAlign="top";
				field.size=10;
				break;
			}

			case "time":{
				field.editorType="text";
				field.align="left";
				field.vAlign="top";
				field.size=8;
				break;
			}

			case "timestamp":{
				field.editorType="text";
				field.align="left";
				field.vAlign="top";
				field.size=19;
				break;
			}

			default:{
				field.editorType="text";
				field.align="left";
				field.vAlign="top";
				break;
			}
		}
		return field;
	}
	catch(e){
		processException(e);
	}
}

function _addUpdateItem(dataset) {
	var item=new Object();
	dataset.updateItems[dataset.updateItems.length]=item;
	return item;
}

function initFieldArray(dataset, fields){
	var fieldCount=fields.fieldCount;
	fields.dataset=dataset;

	for(var i=0; i<fieldCount; i++){
		if (dataset.id){
			if (fields[i].id && typeof(_element_property)!="undefined"){
				var root=_element_property[fields[i].id];
				if (root){
					var property_count=root.length;
					for(var j=0; j<property_count; j++)
						eval("fields[i]."+root[j].property+"=getDecodeStr(root[j].value)");
				}
			}
		}

		fields[fieldCount+i]=new Object;
		fields[fieldCount+i].name="_cur_"+fields[i].name;
		fields[fieldCount+i].dataType=fields[i].dataType;
		fields["_index__cur_"+fields[i].name]=fieldCount+i;
		fields[fieldCount*2+i]=new Object;
		fields[fieldCount*2+i].name="_old_"+fields[i].name;
		fields[fieldCount*2+i].dataType=fields[i].dataType;
		fields["_index__old_"+fields[i].name]=fieldCount*2+i;

		fields[i].readOnly=isTrue(fields[i].readOnly);
		fireUserEvent(getElementEventName(dataset, "onInitField"),[dataset, fields[i]]);
		//fireDatasetEvent(dataset, "onInitField", [dataset, fields[i]]);
	}
}

function initRecord(record, dataset, skipSaveOld){
	record.dataset=dataset;
	record.fields=dataset.fields;
	record.recordState="none";
	record.pageIndex=dataset.pageIndex;
	record.visible=true;

	record.saveOldValue=dataset._saveOldValue;
	record.getValue=dataset._getValue;
	record.getString=dataset._getString;
	record.getViewString=dataset._getViewString;
	record.setValue=dataset._setValue;
	record.getCurValue=dataset._getCurValue;
	record.setCurValue=dataset._setCurValue;
	record.getOldValue=dataset._getOldValue;
	record.setOldValue=dataset._setOldValue;
	record.getPrevRecord=dataset._getPrevRecord;
	record.getNextRecord=dataset._getNextRecord;

	if (!skipSaveOld) record.saveOldValue();

	//
}

function initDataset(dataset){
	if (dataset.prepared) return;

	dataset.disableControlCount=1;
	dataset.disableEventCount=1;
	try{
		if (dataset.id && typeof(_element_property)!="undefined"){
			var root=_element_property[dataset.id];
			if (root){
				var property_count=root.length;
				for(var i=0; i<property_count; i++)
					eval("dataset."+root[i].property+"=getDecodeStr(root[i].value)");
			}
		}

		dataset.window=window;

		dataset.bof=true;
		dataset.eof=true;
		dataset.state="none";
		dataset.readOnly=isTrue(dataset.readOnly);
		dataset.sortFields="";
		dataset.loadedPage=new Array();
		if (dataset.pageIndex>0) dataset.loadedPage[dataset.pageIndex-1]=true;

		fireUserEvent(getElementEventName(dataset, "onInitDataset"),[dataset]);
		//fireDatasetEvent(dataset, "onInitDataset", [dataset]);
		dataset.setReadOnly(isTrue(dataset.readOnly));
		initFieldArray(dataset, dataset.fields);
		var record=dataset.firstUnit;
		while (record){
			initRecord(record, dataset);
			record=record.nextUnit;
		}
		dataset.prepared=true;
	}
	finally{
		dataset.disableControlCount=0;
		dataset.disableEventCount=0;
	}

	if (dataset.pageIndex==1 || !dataset.autoLoadPage){
		dataset.moveFirst();
	}
	else {
		dataset.setRecord(dataset.getFirstRecord());
	}

	if (!dataset.record) {
		if (dataset.insertOnEmpty && !dataset.readOnly) {
			dataset.insertRecord();
		}
	}
	fireUserEvent(getElementEventName(dataset, "afterInitDataset"),[dataset]);
}

function isFieldEditable(dataset, field){
	if (field){
		var editable=!(dataset.readOnly || field.readOnly);
		if (dataset.record){
			var recordState=dataset.record.recordState;
			editable=(editable &&
				!((recordState=="none" || recordState=="modify") && field.valueProtected));
		}
	}
	else{
		var editable=true;
	}
	return editable;
}

function _dataset_setMasterDataset(dataset, masterDataset, referencesString){
	if (dataset.masterDataset){
		var array=dataset.masterDataset.detailDatasets;
		if (array) pArray_ex_delete(array, dataset);
	}

	if (typeof(masterDataset)=="string") masterDataset=getDatasetByID(masterDataset);
	dataset.masterDataset=masterDataset;
	if (masterDataset){
		var array=masterDataset.detailDatasets;
		if (!array){
			array=new pArray();
			masterDataset.detailDatasets=array;
		}
		pArray_ex_insert(array, dataset);

		var refs=referencesString.split(";");
		var field, fieldName;
		dataset.referencesString=referencesString;
		dataset.references=new Array();
		for(var i=0; i<refs.length; i++){
			var index=refs[i].indexOf("=");
			dataset.references[i]=new Object();

			if (index>=0){
				fieldName=refs[i].substr(0, index);
			}
			else{
				fieldName=refs[i];
			}
			field=masterDataset.getField(fieldName);

			if (field){
				dataset.references[i].masterField=field.name;
				dataset.references[i].masterIndex=field.index;
			}
			else
				throw constErrCantFindMasterField.replace("%s", fieldName);

			if (index>=0){
				fieldName=refs[i].substr(index+1);
			}
			else{
				fieldName=refs[i];
			}
			field=dataset.getField(fieldName);

			if (field){
				dataset.references[i].detailField=field.name;
				dataset.references[i].detailIndex=field.index;
			}
			else{
				throw constErrCantFindDetailField.replace("%s", fieldName);
			}
		}
		delete refs;

		delete dataset.loaded_detail;
		dataset.loaded_detail=new Array;
		masterDataset.loadDetail();
	}
}

function dataset_setMasterDataset(masterDataset, referencesString){
	var dataset=this;
	try{
		_dataset_setMasterDataset(dataset, masterDataset, referencesString);
	}
	catch (e){
		processException(e);
	}
}

function _dataset_loadDetail(dataset){
	if (dataset.detailDatasets){
		var unit=dataset.detailDatasets.firstUnit;
		while (unit && unit.data){
			var detail_dataset=unit.data;

			if (dataset.record && dataset.record.recordState!="insert" &&
				dataset.record.recordState!="new"){
				try{
					validateDatasetCursor(detail_dataset);
					if (detail_dataset.bof && detail_dataset.eof) {
						var keycode_founded=false;
						if (dataset.record) {
							var keycode="";
							for(var i=0; i<detail_dataset.references.length; i++){
								keycode+=dataset.record[detail_dataset.references[i].masterIndex];
							}


							for(var i=0; i<detail_dataset.loaded_detail.length; i++){
								if (detail_dataset.loaded_detail[i]==keycode){
									keycode_founded=true;
									break;
								}
							}
						}

						if (!keycode_founded){
							var dataset_inserted=false;
							var event_result=fireDatasetEvent(detail_dataset, "beforeLoadDetail", [detail_dataset, dataset]);
							if (event_result) throw event_result;

							if (detail_dataset.references.length>0) {
								for(var i=0; i<detail_dataset.references.length; i++){
									detail_dataset.setParameter(detail_dataset.references[i].detailField,
										dataset.getValue(detail_dataset.references[i].masterIndex));
										var masterValue = dataset.getValue(detail_dataset.references[i].masterIndex);
										var dataType = dataset.getField(detail_dataset.references[i].masterIndex).dataType;
										if (dataType=="timestamp" || dataType=="date" || dataType=="time"){
											detail_dataset.setParameter(detail_dataset.references[i].detailField,
													formatDateTime(masterValue,dataType));
										}
								}

								//var result=_downloadData(detail_dataset);
								var result = _downloadData_new(detail_dataset);
								if (result && result.recordStr){
									appendFromDataString(detail_dataset, result.fieldStr, result.recordStr, true);
								}
								delete result;
							}

							detail_dataset.loaded_detail[detail_dataset.loaded_detail.length]=keycode;
						}
					}
				}
				catch (e){
					processException(e);
				}
			}


			detail_dataset.refreshControls();
			detail_dataset.moveFirst();
			unit=unit.nextUnit;
		}
	}
}

function dataset_loadDetail(){
	var dataset=this;
	try{
		_dataset_loadDetail(dataset);
	}
	catch (e){
		processException(e);
	}
}

function dataset_isPageLoaded(pageIndex){
	var dataset=this;
	return dataset.loadedPage[pageIndex-1];
}


function _dataset_loadPage(dataset, pageIndex){
	if (!dataset.autoLoadPage || pageIndex<1 || pageIndex>dataset.pageCount || dataset.isPageLoaded(pageIndex)) return;
	if (dataset.masterDataset) throw constErrLoadPageOnDetailDataset;
	if (dataset.sortFields) throw constErrLoadPageAfterSort;

	//var result=_downloadData_new(dataset, dataset.pageSize, pageIndex);
	
	var result;
	if(dataset.id == 'datasetDropDownTree')
		result=_downloadData(dataset, dataset.pageSize, pageIndex);
	else result=_downloadData_new(dataset, dataset.pageSize, pageIndex);
	
	if (result && result.recordStr){
		var tmpArray=new pArray();
		tmpArray.fields=dataset.fields;
		appendFromDataString(tmpArray, result.fieldStr, result.recordStr);
		var record=tmpArray.lastUnit;
		while (record){
			initRecord(record, dataset);
			record.pageIndex=pageIndex;
			record=record.prevUnit;
		}

		var inserted=false;
		var record=dataset.lastUnit;
		while (record){
			if (record.pageIndex<pageIndex){
				pArray_insertArray(dataset, "after", record, tmpArray);
				inserted=true;
				break;
			}
			record=record.prevUnit;
		}
		if (!inserted) pArray_insertArray(dataset, "begin", null, tmpArray);
		delete tmpArray;

		dataset.loadedPage[pageIndex-1]=true;
		dataset.refreshControls();
	}
	delete result;
}

function dataset_loadPage(pageIndex){
	try{
		var dataset=this;
		_dataset_loadPage(dataset, pageIndex);
	}
	catch (e){
		processException(e);
	}
}

function _dataset_clearData(dataset){
	dataset.disableControls();
	var autoLoadPage=dataset.autoLoadPage;
	dataset.autoLoadPage=false;
	try{
	    //yjw
		dataset.modified = false;
		if (dataset.loaded_detail) delete dataset.loaded_detail;
		if (dataset.loadedPage) delete dataset.loadedPage;
		dataset.loaded_detail=new Array();
		dataset.loadedPage=new Array();
		if (dataset.pageIndex>0) dataset.loadedPage[dataset.pageIndex-1]=true;
		pArray_clear(dataset);
		dataset.moveFirst();
	}
	finally{
		//yjw
		dataset.modified = true;
		dataset.enableControls();
		dataset.autoLoadPage=autoLoadPage;
	}
}

function dataset_clearData(){
	try{
		var dataset=this;
		_dataset_clearData(dataset);
	}
	catch (e){
		processException(e);
	}
}

function freeDataset(dataset){
	if (dataset.detailDatasets) pArray_clear(dataset.detailDatasets);
	if (dataset.editors) pArray_clear(dataset.editors);
	delete dataset.references;
	pArray_clear(dataset.fields);
	dataset.clearData();
	delete dataset;
}

function _dataset_flushData(dataset, pageIndex){
	dataset.disableControls();
	try{
		dataset.clearData();

		var result=_downloadData(dataset, dataset.pageSize, pageIndex);
		if (result){
			if (result.recordStr)
			{
				appendFromDataString(dataset, result.fieldStr, result.recordStr, true);
			}
			dataset.pageIndex=result.pageIndex;
			dataset.pageCount=result.pageCount;
		}
		delete result;
	}
	finally{
		dataset.enableControls();
		dataset.refreshControls();
		dataset.loadDetail();
	}
}

function dataset_flushData(pageIndex){
	try{
		var dataset=this;
		_dataset_flushData(dataset, pageIndex);
	}
	catch (e){
		processException(e);
	}
}

function dataset_moveToPage(pageIndex){
	try{
		var dataset=this;
		if (!dataset.isPageLoaded(pageIndex)) _dataset_loadPage(dataset, pageIndex);
		var record=dataset.getFirstRecord();
		while (record){
			if (record.pageIndex>=pageIndex){
				_dataset_setRecord(dataset, record);
				break;
			}
			record=record.getNextRecord();
		}
	}
	catch (e){
		processException(e);
	}
}

function record_saveOldValue(){
	var record=this;

	var fieldCount=record.fields.fieldCount;
	for(var i=0; i<fieldCount; i++){
		record[fieldCount+i]=record[i];
		record[fieldCount*2+i]=record[i];
	}
}

function _dataset_sort(dataset, fields){

	function quickSort(_array, _fields, _low, _high){

		function compareRecord(record, _mid_data){
			if (_fields.length>0){
				var value1, value2;
				for (var i=0; i<_fields.length; i++){
					if (_field[i].ascend){
						value1=1;
						value2=-1;
					}
					else{
						value1=-1;
						value2=1;
					}

					if (record.getValue(_fields[i].index)>_mid_data[i]){
						return value1;
					}
					else if (record.getValue(_fields[i].index)<_mid_data[i]){
						return value2;
					}
				}
			}
			else{
				if (record.recordno>_mid_data[0]){
					return 1;
				}
				else if (record.recordno<_mid_data[0]){
					return -1;
				}
			}
			return 0;
		}

		var low=_low;
		var high=_high;
		var mid=getInt((low+high)/2);
		var mid_data=new Array();

		if (_fields.length>0){
			for (var i=0; i<_fields.length; i++)
				mid_data[i]=_array[mid].getValue(_fields[i].index);
		}
		else{
			mid_data[0]=_array[mid].recordno;
		}

		do {
			while (compareRecord(_array[low], mid_data)<0) low++;
			while (compareRecord(_array[high], mid_data)>0) high--;

			if (low<=high){
				var tmp=_array[low];
				_array[low]=_array[high];
				_array[high]=tmp;

				low++;
				high--;
			}
		}while (low<=high)

		if (high>_low) quickSort(_array, _fields, _low, high);
		if (_high>low) quickSort(_array, _fields, low, _high);
	}

	var _field=new Array();
	if (fields){
		var fields_array=fields.split(",");
		for (var i=0; i<fields_array.length; i++){
			_field[i]=new Object();
			_field[i].ascend=true;

			var firstchar=fields_array[i].substring(0, 1);
			var fieldName;
			if (firstchar=="+" || firstchar=="-"){
				if (firstchar=="-") _field[i].ascend=false;
				fieldName=fields_array[i].substring(1, fields_array[i].length);
			}
			else{
				fieldName=fields_array[i];
			}

			for (var j=0; j<dataset.fields.fieldCount; j++){
				if (compareText(fieldName, dataset.fields[j].name)){
					_field[i].index=j;
					break;
				}
			}
		}
	}

	function customSort(_array, _low, _high){

		function compareRecord(record1, record2){
			var event_name=getElementEventName(dataset, "onCompareRecord");
			if (isUserEventDefined(event_name)){
				return fireUserEvent(event_name, [record1.dataset, record1, record2]);
			}
		}

		var low=_low;
		var high=_high;
		var mid_record=_array[getInt((low+high)/2)];

		do {
			while (compareRecord(_array[low], mid_record)<0) low++;
			while (compareRecord(_array[high], mid_record)>0) high--;

			if (low<=high){
				var tmp=_array[low];
				_array[low]=_array[high];
				_array[high]=tmp;

				low++;
				high--;
			}
		}while (low<=high)

		if (high>_low) customSort(_array, _low, high);
		if (_high>low) customSort(_array, low, _high);
	}

	var _field=new Array();
	if (fields){
		if (fields!="#custom"){
			var fields_array=fields.split(",");
			for (var i=0; i<fields_array.length; i++){
				_field[i]=new Object();
				_field[i].ascend=true;

				var firstchar=fields_array[i].substring(0, 1);
				var fieldName;
				if (firstchar=="+" || firstchar=="-"){
					if (firstchar=="-") _field[i].ascend=false;
					fieldName=fields_array[i].substring(1, fields_array[i].length);
				}
				else{
					fieldName=fields_array[i];
				}

				for (var j=0; j<dataset.fields.fieldCount; j++){
					if (compareText(fieldName, dataset.fields[j].name)){
						_field[i].index=j;
						break;
					}
				}
			}
		}
	}

	if (!dataset.firstUnit) return;

	var tmp_array=new Array();
	try{
		var record=dataset.firstUnit;
		var i=0;
		while (record){
			tmp_array[i++]=record;
			if (!dataset.sortFields) record.recordno=i;
			record=record.nextUnit;
		}

		dataset.sortFields=fields;
		if (fields!="#custom"){
			quickSort(tmp_array, _field, 0, tmp_array.length-1);
		}
		else{
			customSort(tmp_array, 0, tmp_array.length-1);
		}

		dataset.firstUnit=null;
		dataset.lastUnit=null;
		for (var i=0; i<tmp_array.length; i++){
			pArray_insert(dataset, "end", null, tmp_array[i]);
		}

		dataset.refreshControls();
	}
	finally{
		delete tmp_array;
		for (var i=0; i<_field.length; i++) delete _field[i];
		delete _field;
	}
}

function dataset_sort(fields){
	try{
		var dataset=this;
		_dataset_sort(dataset, fields);
	}
	catch (e){
		processException(e);
	}
}

function dataset_setReadOnly(readOnly){
	var dataset=this;
	dataset.readOnly=readOnly;
	_broadcastDatasetMsg(_notifyDatasetStateChanged, dataset);
}
//yjw add
function dataset_setAllFieldsReadOnly(readOnly){
	 var dataset=this;
	 for(var i=0;i<dataset.fields.length;i++){
	 		if (typeof(dataset.fields[i].fieldName)!="undefined"){
		  		dataset_setFieldReadOnly2(dataset,dataset.fields[i].fieldName,readOnly);
		  	}
		}
}

function dataset_setFieldReadOnly(fieldName, readOnly){
	var dataset=this;
	var field=dataset.getField(fieldName);
	if (field){
		field.readOnly=readOnly;
		if(field.tag=="select"||field.tag=="selectCQ"){
			var fieldName2 = fieldName+"Name";
			dataset_setFieldReadOnly2(dataset,fieldName2,readOnly);
		}
		_broadcastFieldMsg(_notifyFieldStateChanged, dataset, dataset.record, field);
	}
	else
		throw constErrCantFindField.replace("%s", dataset.id+"."+fieldName);
}

function dataset_setFieldReadOnly2(dataset,fieldName, readOnly){
	var field2=dataset.getField(fieldName);
	if (field2){
		field2.readOnly=readOnly;
		_broadcastFieldMsg(_notifyFieldStateChanged, dataset, dataset.record, field2);
	}
	else
		throw constErrCantFindField.replace("%s", dataset.id+"."+fieldName);
}

function fireDatasetEvent(dataset, eventName, param){
	if (dataset.disableEventCount>0) return;
	var result;
	result=fireUserEvent(getElementEventName(dataset, eventName), param);
	return result;
}

function dataset_isRecordValid(record){
	if (!record)
		return false;
	else{
		var result=(record.recordState!="delete" && record.recordState!="discard" && record.visible);
		var dataset=record.dataset;
		var masterDataset=dataset.masterDataset;

		if (result){
			if (masterDataset){
				if (!masterDataset.record) return false;
				for(var i=0; i<dataset.references.length; i++){
//					if (masterDataset.getCurValue(dataset.references[i].masterIndex)!=
//						record[dataset.references[i].detailIndex]){
//							result=false;
//							break;
//					}
                                        var masterValue = getStringValue(masterDataset.getCurValue(dataset.references[i].masterIndex));
                                        var detailValue = getStringValue(getTypedValue(record[dataset.references[i].detailIndex],dataset.getField(dataset.references[i].detailIndex).dataType));
                                        if (compareText(masterValue,detailValue)==false){
                                                result=false;
                                                break;
                                        }
				}
			}

			if (dataset.filtered && !(record==dataset.record && dataset.state!="none")){
				var event_name=getElementEventName(dataset, "onFilterRecord");
				if (isUserEventDefined(event_name)){
					if (!fireUserEvent(event_name, [dataset, record])) result=false;
				}
			}
		}
		return result;
	}
}

function dataset_setBofnEof(dataset, BofValue, EofValue){
	if (dataset.bof!=BofValue || dataset.eof!=EofValue){
		dataset.bof=BofValue;
		dataset.eof=EofValue;
		_broadcastDatasetMsg(_notifyDatasetStateChanged, dataset, dataset.record);
	}
}

function _do_dataset_setRecord(dataset, record){
	if (dataset.record!=record){
		if (dataset.record){
			_dataset_updateRecord(dataset);
		}

		if (dataset.detailDatasets){
			var unit=dataset.detailDatasets.firstUnit;
			while (unit){
				var detailDataset=unit.data;
				_dataset_updateRecord(detailDataset);
				unit=unit.nextUnit;
			}
		}

		var event_result=fireDatasetEvent(dataset, "beforeScroll", [dataset]);
		if (event_result) throw event_result;

		dataset.record=record;
		dataset.modified=false;

		if (dataset.disableControlCount<1) dataset.loadDetail();

		fireDatasetEvent(dataset, "afterScroll", [dataset]);
		_broadcastDatasetMsg(_notifyDatasetStateChanged, dataset, record);
		_broadcastDatasetMsg(_notifyDatasetCursorChanged, dataset, record);
	}
}

function _dataset_setRecord(dataset, record){
	_do_dataset_setRecord(dataset, record);
	if (record){
		dataset_setBofnEof(dataset, false, false);
		dataset_setBofnEof(dataset, false, false);
	}
}

function dataset_setRecord(record){
	try{
		_dataset_setRecord(this, record);
	}
	catch(e){
		processException(e);
	}
}

function validateDatasetCursor(dataset){
	var down_found=false, up_found=false;
	var curRecord=(dataset.record)?dataset.record:dataset.firstUnit;

	var record=curRecord;
	while (record){
		if (dataset_isRecordValid(record)){
			_do_dataset_setRecord(dataset, record);
			up_found=true;
			break;
		}
		record=_record_getPrevRecord(record);
	}

	var record=curRecord;
	while (record){
		if (dataset_isRecordValid(record)){
			_do_dataset_setRecord(dataset, record);
			down_found=true;
			break;
		}
		record=_record_getNextRecord(record);
	}

	if (!up_found && !down_found)
		_do_dataset_setRecord(dataset, null);

	dataset_setBofnEof(dataset, (!up_found), (!down_found));
}

function dataset_setState(dataset, state){
	dataset.state=state;

	_broadcastDatasetMsg(_notifyDatasetStateChanged, dataset, dataset.record);
	fireDatasetEvent(dataset, "onStateChanged", [dataset]);
}

function dataset_getState(dataset){
	return dataset.state;
}

function _record_getValue(record, fieldName){
	var dataset=record.dataset;
	var fields=record.fields;
	var fieldIndex=-1;
	var result;

	if (typeof(fieldName)=="number"){
		fieldIndex=fieldName;
	}
	else if (typeof(fieldName)=="string"){
		fieldIndex=fields["_index_"+fieldName.toLowerCase()];
	}

	var field=fields[fieldIndex];
	if (typeof(field)=="undefined"){
		throw constErrCantFindField.replace("%s", record.dataset.id+"."+fieldName);
	}

	result=getTypedValue(record[fieldIndex], field.dataType);

	var eventName=getElementEventName(dataset, "onGetValue");
	if (isUserEventDefined(eventName)){
			result=fireUserEvent(eventName, [dataset, field, result]);
	}

	return result;
}

function record_getValue(fieldName){
	try{
		return _record_getValue(this, fieldName);
	}
	catch(e){
		processException(e);
	}
}

function record_getString(fieldName){
	var record=this, field, result="";
	var field=record.dataset.getField(fieldName);
	if (field){
		var value=record.getValue(fieldName);
		switch (field.dataType){
			case "string":{
				result=getValidStr(value);
				break;
			}
			case "byte":;
			case "short":;
			case "int":;
			case "long":{
				if (!isNaN(value))  result=value+"";
				break;
			}
			case "float":;
			case "double":;
			case "currency":;
			case "bigdecimal":{
				if (!isNaN(value)) result=formatFloat(value, field.scale);
				break;
			}

			case "date":;
			case "time":;
			case "timestamp":{
				result=formatDateTime(value, field.dataType);
				break;
			}

			case "boolean":;
			default:{
				result=getValidStr(value);
				break;
			}
		}
	}
	try{
		if(typeof(field.tag)!="undefined"&& field.tag!=""&&field.tag=="selectName"&&value==""){
			//youjinwu
			result =  getFieldSelectNameValue(record,field);

		}
	}catch(e){
	}
	finally{
		return result;
	}
}
/** add by shen_antonio 20080524 for currency type.*/
function record_getViewString(fieldName){
	var record=this, field, result="";
	var field=record.dataset.getField(fieldName);
	if (field){
		var value=record.getValue(fieldName);
		switch (field.dataType){
			case "string":{
				result=getValidStr(value);
				break;
			}
			case "byte":;
			case "short":;
			case "int":;
			case "long":{
				if (!isNaN(value))  result=value+"";
				break;
			}
			/** add by shen_antonio 20080524 for add currency type.*/
			case "currency":{
				if (!isNaN(value)) {
					result=  formatFloat(value, field.scale);
					result = formatCurrency(result);
				}
				break;
			}
			case "float":;
			case "double":;
			case "bigdecimal":{
				if (!isNaN(value)) result=formatFloat(value, field.scale);
				break;
			}


			case "date":;
			case "time":;
			case "timestamp":{
				result=formatViewDateTime(value, field.dataType);
				break;
			}

			case "boolean":;
			default:{
				result=getValidStr(value);
				break;
			}
		}
	}
	try{
		if(typeof(field.tag)!="undefined"&& field.tag!=""&&field.tag=="selectName"&&value==""){
			//youjinwu
			result =  getFieldSelectNameValue(record,field);

		}
	}catch(e){
	}
	finally{
		return result;
	}
}

function record_getString_2(record,fieldName){
	var  field, result="";
	var field=record.dataset.getField(fieldName);

	if (field){
		var value=record.getValue(fieldName);
		switch (field.dataType){
			case "string":{
				result=getValidStr(value);
				break;
			}

			case "byte":;
			case "short":;
			case "int":;
			case "long":{
				if (!isNaN(value))  result=value+"";

				break;
			}
			case "float":;
			case "double":;
			case "bigdecimal":{
				if (!isNaN(value)) result=formatFloat(value, field.scale);
				break;
			}

			case "date":;
			case "time":;
			case "timestamp":{
				result=formatDateTime(value, field.dataType);
				break;
			}

			case "boolean":;
			default:{
				result=getValidStr(value);
				break;
			}
		}
	}
	return result;
}


function _record_setValue(record, fieldName, value){
	var dataset=record.dataset;
	var fields=record.fields;
	var fieldIndex=-1;

	if (typeof(fieldName)=="number"){
		fieldIndex=fieldName;
	}
	else if (typeof(fieldName)=="string"){
		fieldIndex=fields["_index_"+fieldName.toLowerCase()];
	}

	if (typeof(fields[fieldIndex])=="undefined"){
		throw constErrCantFindField.replace("%s", record.dataset.id+"."+fieldName);
	}

	var field=fields[fieldIndex];

	if (getValidStr(field.mask)!="") {
		/* shen_antonio .*/
		if((!value || value == "") && !field.required){
			//not need check
		}else{
			var valid=false;
			eval("valid="+field.mask+".test(\""+value+"\");");
			if (!valid){
				if (field.maskErrorMessage) {
					throw field.maskErrorMessage.replace("%s", value);
				}
				else{
					throw constErrInputMask.replace("%s", value);
				}
			}
		}
	}

	switch (field.dataType)
	{
		case "date":;
		case "timestamp":
			value=getValidStr(value);
			value=new Date(value.replace(/-/g, "/"));
			break;
		case "time":
			value=getValidStr(value);
			value=new Date("2000/01/01 " + value.replace(/-/g, "/"));
			break;
		case "boolean":
			value=isTrue(value);
			break;
		case "string":
			var maxLength = 0;
			if(field.scale && field.scale > 0) {
				maxLength = field.size + 1 + field.scale;
			}
			else {
				maxLength = field.size;
			}
			if("text" == field.editorType || "textarea" == field.editorType) {
				if(("" + value).bytesLength() > maxLength) {
					alert("字段[ " + field.label + " ]输入长度超长！");
					return;
				}
			}
			break;
	}
	//yjw modify , checked before the methord executing
	if(dataset.loadCompleted){
		var event_result=fireDatasetEvent(dataset, "beforeChange", [dataset, field, value]);
		if (event_result) {
			throw event_result;
		}
	}
	var eventName=getElementEventName(dataset, "onSetValue");
	//yjw modify , checked before the methord executing
	if (isUserEventDefined(eventName) && dataset.record && dataset.loadCompleted){
		value=fireUserEvent(eventName, [dataset, field, value]);
	}

	record[fieldIndex]=value;
	//yjw modify , checked before the methord executing
	dataset.modified=true;
	if(dataset.loadCompleted){
		fireDatasetEvent(dataset, "afterChange", [dataset, field]);
	}
	if (record.recordState=="none") record.recordState="modify";
	if (dataset.state=="none") dataset_setState(dataset, "modify");
	_broadcastFieldMsg(_notifyFieldDataChanged, dataset, record, field);
}

function _record_setValue_2(record, fieldName, value){

	var dataset=record.dataset;
	var fields=record.fields;
	var fieldIndex=-1;

	if (typeof(fieldName)=="number"){
		fieldIndex=fieldName;
	}
	else if (typeof(fieldName)=="string"){
		fieldIndex=fields["_index_"+fieldName.toLowerCase()];
	}

	if (typeof(fields[fieldIndex])=="undefined"){
		throw constErrCantFindField.replace("%s", record.dataset.id+"."+fieldName);
	}

	var field=fields[fieldIndex];

	if (getValidStr(field.mask)!="") {
		/* shen_antonio .*/
		if(value == "" && !field.required){
			//not need check
		}else{
		var valid=false;
		eval("valid="+field.mask+".test(\""+value+"\");");
		if (!valid){
			if (field.maskErrorMessage) {
				throw field.maskErrorMessage.replace("%s", value);
			}
			else{
				throw constErrInputMask.replace("%s", value);
			}
		}
		}
	}

	switch (field.dataType)
	{
		case "date":;
		case "timestamp":
			value=getValidStr(value);
			value=new Date(value.replace(/-/g, "/"));
			break;
		case "time":
			value=getValidStr(value);
			value=new Date("2000/01/01 " + value.replace(/-/g, "/"));
			break;
		case "boolean":
			value=isTrue(value);
			break;
	}
	//yjw modify
	if(dataset.loadCompleted){
		var event_result=fireDatasetEvent(dataset, "beforeChange", [dataset, field, value]);
		if (event_result) {
			throw event_result;
		}
	}
	var eventName=getElementEventName(dataset, "onSetValue");
		if (isUserEventDefined(eventName) && dataset.record && dataset.loadCompleted){
			value=fireUserEvent(eventName, [dataset, field, value]);
		}

	record[fieldIndex]=value;
// yjw modify
//	dataset.modified=true;

//	fireDatasetEvent(dataset, "afterChange", [dataset, field]);

//	if (record.recordState=="none") record.recordState="modify";
//	if (dataset.state=="none") dataset_setState(dataset, "modify");
//	_broadcastFieldMsg(_notifyFieldDataChanged, dataset, record, field);
}
function record_setValue(fieldName, value){
	try{
		_record_setValue(this, fieldName, value);
	}
	catch(e){
		processException(e);
	}
}

function record_getCurValue(fieldName){
	var record=this;
	if (typeof(fieldName)=="number"){
		return record.getValue(fieldName+record.fields.fieldCount);
	}
	else{
		return record.getValue("_cur_"+fieldName);
	}
}

function record_setCurValue(fieldName, value){
	var record=this;
	if (typeof(fieldName)=="number"){
		record.setValue(fieldName+record.fields.fieldCount, value);
	}
	else{
		record.setValue("_cur_"+fieldName, value);
	}
}

function record_getOldValue(fieldName){
	var record=this;
	if (typeof(fieldName)=="number"){
		return record.getValue(fieldName+record.fields.fieldCount*2);
	}
	else{
		return record.getValue("_old_"+fieldName);
	}
}

function record_setOldValue(fieldName, value){
	var record=this;
	if (typeof(fieldName)=="number"){
		record.setValue(fieldName+record.fields.fieldCount*2, value);
	}
	else{
		record.setValue("_old_"+fieldName, value);
	}
}

function dataset_getValue(fieldName){
	var dataset=this;
	if (dataset.record)
		return dataset.record.getValue(fieldName);
	else
		return "";
}

function dataset_getString(fieldName){
	var dataset=this;
	if (dataset.record)
		return dataset.record.getString(fieldName);
	else
		return "";
}

function dataset_getViewString(fieldName){
	var dataset=this;
	if (dataset.record)
		return dataset.record.getViewString(fieldName);
	else
		return "";
}

function dataset_setValue(fieldName, value){
	try{
		var dataset=this;
		if (dataset.record){
				dataset.record.setValue(fieldName, value);
			}
		else{
			throw constErrNoCurrentRecord;
			}
	}
	catch(e){
		processException(e);
	}
}
function dataset_setValue_2(fieldName, value){
	try{
		var dataset=this;
		if (dataset.record){
				dataset.record.setValue(fieldName, value);
				var field = _dataset_getField(dataset.fields,fieldName.toLowerCase());
				if(field.tag=="select"||field.tag=="selectCQ"){
					var fieldName2 = fieldName+"Name";
					if(value=""){
						dataset.record.setValue(fieldName2, "");
						return;
					}
					var field2 = _dataset_getField(dataset.fields,fieldName2.toLowerCase());
					var nameValue =  getFieldSelectNameValue(dataset.record,field2);
					dataset.record.setValue(fieldName2, nameValue);
				}else{
				}
			}
		else{
			throw constErrNoCurrentRecord;
			}
	}
	catch(e){
		processException(e);
	}
}
function dataset_getCurValue(fieldName){
	var dataset=this;
	if (typeof(fieldName)=="number"){
		return dataset.getValue(fieldName+dataset.fields.fieldCount);
	}
	else{
		return dataset.getValue("_cur_"+fieldName);
	}
}

function dataset_setCurValue(fieldName, value){
	var dataset=this;
	if (typeof(fieldName)=="number"){
		dataset.setValue(fieldName+dataset.fields.fieldCount, value);
	}
	else{
		dataset.setValue("_cur_"+fieldName, value);
	}
}

function dataset_getOldValue(fieldName){
	var dataset=this;
	if (typeof(fieldName)=="number"){
		return dataset.getValue(fieldName+dataset.fields.fieldCount*2);
	}
	else{
		return dataset.getValue("_old_"+fieldName);
	}
}

function dataset_setOldValue(fieldName, value){
	var dataset=this;
	if (typeof(fieldName)=="number"){
		dataset.setValue(fieldName+dataset.fields.fieldCount*2, value);
	}
	else{
		dataset.setValue("_old_"+fieldName, value);
	}
}

function _record_getPrevRecord(record){
	var _record=record;
	while (_record){
		_record=_record.prevUnit;
		if (dataset_isRecordValid(_record)) return _record;
	}
}

function record_getPrevRecord(){
	return _record_getPrevRecord(this);
}

function _record_getNextRecord(record){
	var _record=record;
	while (_record){
		_record=_record.nextUnit;
		if (dataset_isRecordValid(_record)) return _record;
	}
}

function record_getNextRecord(){
	return _record_getNextRecord(this);
}

function dataset_disableControls(){
	var dataset=this;
	dataset.disableControlCount=dataset.disableControlCount+1;
}

function dataset_enableControls(){
	var dataset=this;
	dataset.disableControlCount=(dataset.disableControlCount>0)?dataset.disableControlCount-1:0;
	dataset.refreshControls();

}

function dataset_disableEvents(){
	var dataset=this;
	dataset.disableEventCount=dataset.disableEventCount+1;
}

function dataset_enableEvents(){
	var dataset=this;
	dataset.disableEventCount=(dataset.disableEventCount>0)?dataset.disableEventCount-1:0;
}

function dataset_refreshControls(){
	var dataset=this;
	validateDatasetCursor(dataset);
	_broadcastDatasetMsg(_notifyDatasetRefresh, dataset);
}

function _dataset_move(dataset, count){
	var _record=dataset.record;
	if (!_record) _record=dataset.getFirstRecord();
	if (!_record) return;
	var record=_record;

	if (count>0){
		var old_pageIndex=record.pageIndex
		var eof=false;
		for(var i=0; i<count; i++){
			var pageIndex=0;

			_record=record.getNextRecord();
			if (!_record || (_record && _record.pageIndex!=old_pageIndex)){
				if (old_pageIndex<dataset.pageCount){
					if (!dataset.isPageLoaded(old_pageIndex+1)){
						if ((i+dataset.pageSize<count) && (old_pageIndex+1<dataset.pageCount)){
							i+=dataset.pageSize-1;
							_record=record;
						}
						else{
							_dataset_loadPage(dataset, old_pageIndex+1);
							_record=record.getNextRecord();
						}
					}
				}
				old_pageIndex++;
			}

			if (_record){
				record=_record;
			}
			else{
				eof=true;
				break;
			}
		}
		dataset_setBofnEof(dataset, (!dataset_isRecordValid(dataset.record)), eof);
	}
	else{
		var old_pageIndex=record.pageIndex
		var bof=false;
		for(var i=count; i<0; i++){
			var pageIndex=0;

			_record=record.getPrevRecord();
			if (!_record || (_record && _record.pageIndex!=old_pageIndex)){
				if (old_pageIndex>1){
					if (!dataset.isPageLoaded(old_pageIndex-1)){
						if ((i+dataset.pageSize<0) && (old_pageIndex>1)){
							i+=dataset.pageSize-1;
							_record=record;
						}
						else{
							_dataset_loadPage(dataset, old_pageIndex-1);
							_record=record.getPrevRecord();
						}
					}
				}
				old_pageIndex--;
			}

			if (_record){
				record=_record;
			}
			else{
				bof=true;
				break;
			}
		}
		dataset_setBofnEof(dataset, bof, (!dataset_isRecordValid(dataset.record)));
	}

	if (record) _do_dataset_setRecord(dataset, record);
}

function dataset_move(count){
	var dataset=this;
	try{
		_dataset_move(dataset, count);
	}
	catch(e){
		processException(e);
	}
}

function dataset_movePrev(){
	var dataset=this;
	try{
		_dataset_move(dataset, -1);
	}
	catch(e){
		processException(e);
	}
}

function dataset_moveNext(){
	var dataset=this;
	try{
		_dataset_move(dataset, 1);
	}
	catch(e){
		processException(e);
	}
}

function _dataset_getFirstRecord(dataset){
	var record=dataset.firstUnit;
	if (record && !dataset_isRecordValid(record)) record=record.getNextRecord();
	return record;
}

function dataset_getFirstRecord(){
	return _dataset_getFirstRecord(this);
}

function dataset_moveFirst(){
	var dataset=this;

	try{
		if (!dataset.isPageLoaded(1)) _dataset_loadPage(dataset, 1);
		_do_dataset_setRecord(dataset, dataset.getFirstRecord());
		dataset_setBofnEof(dataset, true, (!dataset_isRecordValid(dataset.record)));
	}
	catch(e){
		processException(e);
	}
}

function _dataset_getLastRecord(dataset){
	var record=dataset.lastUnit;
	if (!dataset_isRecordValid(record) && record) record=record.getPrevRecord();
	return record;
}

function dataset_getLastRecord(){
	return _dataset_getLastRecord(this);
}

function dataset_moveLast(){
	var dataset=this;

	try{
		if (!dataset.isPageLoaded(dataset.pageCount)) _dataset_loadPage(dataset, dataset.pageCount);
		_do_dataset_setRecord(dataset, dataset.getLastRecord());
		dataset_setBofnEof(dataset, (!dataset_isRecordValid(dataset.record)), true);
	}
	catch(e){
		processException(e);
	}
}

function dataset_find(fieldNames, values, startRecord){

	function isMatching(fieldNames, values, record){
		var result=true;
		for (var j=0; j<fieldNames.length && j<values.length; j++){
			if (!compareText(record.getString(fieldNames[j]), values[j])){
				result=false;
				break;
			}
		}
		return result;
	}

	if (!fieldNames || !values) return false;

	var dataset=this;
	if (!dataset.record) return;
	if (isMatching(fieldNames, values, dataset.record)) return dataset.record;

	var record=(startRecord)?startRecord:dataset.getFirstRecord();
	while (record){
		if (isMatching(fieldNames, values, record)) return record;
		record=record.getNextRecord();
	}
}

function dataset_locate(fieldName, value, startRecord){

	function isMatching(fieldName, value, record){
		var tmpValue=record.getString(fieldName);
		return (tmpValue && compareText(tmpValue.substr(0, len), value));
	}

	if (!value) return false;

	var dataset=this;
	if (!dataset.record) return;
	if (isMatching(fieldName, value, dataset.record)) return dataset.record;

	var len=value.length;
	var record=(startRecord)?startRecord:dataset.getFirstRecord();
	while (record){
		if (isMatching(fieldName, value, record)) return record;
		record=record.getNextRecord();
	}
}

function _dataset_insertRecord(dataset, mode){
	_dataset_updateRecord(dataset);
	var event_result=fireDatasetEvent(dataset, "beforeInsert", [dataset, mode]);
	if (event_result) throw event_result;

	var _masterDataset=dataset.masterDataset;
	if (_masterDataset){
			if (_masterDataset.record){
					_dataset_updateRecord(_masterDataset);
			}
	}

	var pageIndex=(dataset.record)?dataset.record.pageIndex:1;
	var newRecord=new Array();
	pArray_insert(dataset, mode, dataset.record, newRecord);
	initRecord(newRecord, dataset);
	switch (mode){
		case "begin":{
			newRecord.pageIndex=1;
			break;
		}
		case "end":{
			newRecord.pageIndex=dataset.pageCount;
			break;
		}
		default:{
			newRecord.pageIndex=pageIndex;
			break;
		}
	}

	newRecord.recordState="new";
	newRecord.recordno=9999;

	var _masterDataset=dataset.masterDataset;
	if (_masterDataset){
		if (_masterDataset.record){
			for(var i=0; i<dataset.references.length; i++){
				var fieldIndex=dataset.references[i].masterIndex;
				if (_masterDataset.getString(fieldIndex)==""){
					var field=_masterDataset.getField(fieldIndex);
					switch (field.dataType) {
					case "string":
						_masterDataset.setValue(fieldIndex, _getAutoGenID());
						break;
					case "byte":;
					case "short":;
					case "int":;
					case "long":;
					case "float":;
					case "double":;
					case "bigdecimal":;
						var maxnum=0;
						var record=_masterDataset.firstUnit;
						while (record){
							if (record.getValue(fieldIndex)>maxnum) {
								maxnum=record.getValue(fieldIndex);
							}
							record=record.nextUnit;
						}
						_masterDataset.setValue(fieldIndex, maxnum+1);
						break;
					}
				}
			}
			_dataset_updateRecord(_masterDataset);

			for(var i=0; i<dataset.references.length; i++){
				var reference=dataset.references[i];
				newRecord[reference.detailIndex]=
					_masterDataset.getValue(reference.masterIndex);
			}
		}
		else{
			throw constErrNoMasterRecord;
		}
	}
	var fieldCount=dataset.fields.fieldCount;
	for (var i=0; i<fieldCount; i++) {
		var field=dataset.fields[i];
		var defaultValue=getValidStr(field.defaultValue);
		if (defaultValue!=""){
			newRecord[i]=defaultValue;
		}
	}
	dataset_setState(dataset, "insert");
	_broadcastDatasetMsg(_notifyDatasetInsert, dataset, dataset.record, [mode, newRecord]);
	_dataset_setRecord(dataset, newRecord);
	var fieldCount=dataset.fields.fieldCount;
	for (var i=0; i<fieldCount; i++) {
		var field=dataset.fields[i];
		if (field.autoGenId){
			dataset.setValue(i, _getAutoGenID());
		}
	}
	fireDatasetEvent(dataset, "afterInsert", [dataset, mode]);
	dataset.modified=true;
}

function dataset_insertRecord(mode){
	try{
		_dataset_insertRecord(this, mode);
	}
	catch(e){
		processException(e);
	}
}

function _dataset_deleteRecord(dataset){
	if (!dataset.record) return;

	if (dataset.detailDatasets){
		var unit=dataset.detailDatasets.firstUnit;
		while (unit && unit.data){
			var detail_dataset=unit.data;
			if (detail_dataset.references.length>0) {
				_dataset_updateRecord(detail_dataset);
				detail_dataset.moveFirst();
				while (!detail_dataset.eof){
					detail_dataset.deleteRecord();
				}
			}
			detail_dataset.refreshControls();
			unit=unit.nextUnit;
		}
	}

	needUpdateEditor=false;
	try{
		if (dataset.record.recordState=="new" || dataset.record.recordState=="insert"){
			var event_result=fireDatasetEvent(dataset, "beforeDelete", [dataset]);
			if (event_result) throw event_result;

			dataset.record.recordState="discard";
		}
		else{
			var event_result=fireDatasetEvent(dataset, "beforeDelete", [dataset]);
			if (event_result) throw event_result;

			dataset.record.recordState="delete";
			_changeMasterRecordState(dataset);
		}

		dataset.modified=false;

		fireDatasetEvent(dataset, "afterDelete", [dataset]);
		dataset_setState(dataset, "none");

		_broadcastDatasetMsg(_notifyDatasetDelete, dataset, dataset.record);
		validateDatasetCursor(dataset);
	}
	finally{
		needUpdateEditor=true;
	}
}

function dataset_deleteRecord(){
	try{
		_dataset_deleteRecord(this);
	}
	catch(e){
		processException(e);
	}
}

function _dataset_updateRecord(dataset){
	if (!dataset.record) return;
	if (!dataset_isRecordValid(dataset.record)) return;
	_broadcastDatasetMsg(_notifyDatasetBeforeUpdate, dataset, dataset.record);

	if (dataset.modified){
		var fieldCount=dataset.fields.fieldCount;
		for (var i=0; i<fieldCount; i++){
			if (!isTrue(dataset.fields[i].readOnly) && isTrue(dataset.fields[i].required) &&
				dataset.getString(i)==""){
				throw constErrFieldValueRequired.replace("%s", dataset.fields[i].label);
			}
		}
		var event_result=fireDatasetEvent(dataset, "beforeUpdate", [dataset]);
		if (event_result) throw event_result;

		var detaildatasets=new Array();
		if (dataset.detailDatasets){
			var unit=dataset.detailDatasets.firstUnit;
			while (unit && unit.data){
				var detail_dataset=unit.data;
				if (detail_dataset.references.length>0) {
					var disableCount=detail_dataset.disableControlCount;
					detail_dataset.disableControlCount=1;
					try{
						var changed=false;
						_dataset_updateRecord(detail_dataset);
						detail_dataset.moveFirst();
						while (!detail_dataset.eof){
							for(var i=0; i<detail_dataset.references.length; i++){
								var detailIndex=detail_dataset.references[i].detailIndex;
								var masterIndex=detail_dataset.references[i].masterIndex;
								if (detail_dataset.getValue(detailIndex)!=dataset.getValue(masterIndex)){
									detail_dataset.setValue(detailIndex, dataset.getValue(masterIndex));
									changed=true;
								}
							}
							_dataset_updateRecord(detail_dataset);
							detail_dataset.moveNext();
						}
					}
					finally{
						detail_dataset.disableControlCount=disableCount;
					}

					if (changed){
						detaildatasets[detaildatasets.length]=detail_dataset;
					}
				}
				unit=unit.nextUnit;
			}
		}

		switch (dataset.record.recordState){
			case "none":{
				dataset.record.recordState="modify";
				_changeMasterRecordState(dataset);
				break;
			}
			case "new":{
				dataset.record.recordState="insert";
				_changeMasterRecordState(dataset);
				break;
			}
		}

		for (var i=0; i<fieldCount; i++){
			dataset.record[fieldCount+i]=dataset.record[i];
		}
		dataset.modified=false;

		fireDatasetEvent(dataset, "afterUpdate", [dataset]);
		dataset_setState(dataset, "none");

		for (var i=0;i<detaildatasets.length;i++){
			detail_dataset.refreshControls();
			validateDatasetCursor(detail_dataset);
		}
	}
	else{
		if (dataset.record.recordState=="new"){
			dataset.record.recordState="discard";
			dataset_setState(dataset, "none");
			_broadcastDatasetMsg(_notifyDatasetDelete, dataset, dataset.record);
			validateDatasetCursor(dataset);
		}
	}
}

function dataset_updateRecord(){
	try{
		_dataset_updateRecord(this);
		return true;
	}
	catch(e){
		processException(e);
		return false;
	}
}

function _dataset_cancelRecord(dataset){
	if (!dataset.record) return;

	needUpdateEditor=false;
	try{
		if (dataset.record.recordState=="new"){
			var event_result=fireDatasetEvent(dataset, "beforeCancel", [dataset]);
			if (event_result) throw event_result;

			dataset.record.recordState="discard";

			fireDatasetEvent(dataset, "afterCancel", [dataset]);

			dataset_setState(dataset, "none");
			_broadcastDatasetMsg(_notifyDatasetDelete, dataset, dataset.record);
			validateDatasetCursor(dataset);
		}
		else if (dataset.modified){
			var event_result=fireDatasetEvent(dataset, "beforeCancel", [dataset]);
			if (event_result) throw event_result;

			var fieldCount=dataset.fields.fieldCount;
			for (var i=0; i<fieldCount; i++){
				dataset.record[i]=dataset.record[fieldCount+i];
			}
			dataset.modified=false;

			fireDatasetEvent(dataset, "afterCancel", [dataset]);

			dataset_setState(dataset, "none");
			_broadcastDatasetMsg(_notifyDatasetRefreshRecord, dataset, dataset.record);
		}
	}
	finally{
		needUpdateEditor=true;
	}
}

function dataset_cancelRecord(){
	try{
		_dataset_cancelRecord(this);
	}
	catch(e){
		processException(e);
	}
}

function _dataset_copyRecord(dataset, record, fieldMap){
	if (fieldMap){
		var fieldmaps=new Array();
		var fields=fieldMap.split(";");
		var field1="", field2="";
		for(var i=0; i<fields.length; i++){
			fieldmaps[i]=new Object();
			var index=fields[i].indexOf("=");
			if (index>=0){
				field1=fields[i].substr(0, index);
				field2=fields[i].substr(index+1);
			}
			else{
				field1=fields[i];
				field2=fields[i];
			}

			var value=record.getValue(field2);
			if (typeof(value)!="undefined") dataset.setValue(field1, value);
		}
	}
	else{
		for(var i=0; i<dataset.fields.fieldCount; i++){
			var fieldName=dataset.getField(i).name;
			var field=record.dataset.getField(fieldName);
			if (field) {
				var value=record.getValue(fieldName);
				if (typeof(value)!="undefined") dataset.setValue(fieldName, value);
			}
		}
	}
}

function dataset_copyRecord(record, fieldMap){
	var dataset=this;
	_dataset_copyRecord(dataset, record, fieldMap);
}

function _broadcastDatasetMsg(proc, dataset, record, reserved){
	if (dataset.disableControlCount>0) return;
	var pArray=dataset.editors;
	if (pArray){
		var unit=pArray.firstUnit;
		while (unit && unit.data){
			proc(unit.data, dataset, record, reserved);
			unit=unit.nextUnit;
		}
	}
}

function _broadcastFieldMsg(proc, dataset, record, field, reserved){
	if (dataset.disableControlCount>0) return;
	var pArray=dataset.editors;
	if (pArray){
		var unit=pArray.firstUnit;
		while (unit && unit.data){
			proc(unit.data, dataset, record, field, reserved);
			unit=unit.nextUnit;
		}
	}
}

/*-----------------------------------------------------------------------------------------------------------------------------------------------------------------*/

function _notifyDatasetCursorChanged(element, dataset, record, reserved){
	var _window=element.window;
	switch (element.getAttribute("extra")){
		case "datatable":{
			if (!record) break;

			var maxRow=element.getAttribute("maxRow");
			if (element.tBodies[0].rows.length>=maxRow){
				var needRefresh=true;
				var firstRecord=_window.getTableFirstRecord(element);
				var lastRecord=_window.getTableLastRecord(element);

				var _record=firstRecord;
				while (_record){
					if (_record==record){
						needRefresh=false;
						break;
					}

					if (_record==lastRecord) break;
					_record=_record.nextUnit;
				}

				/*if (needRefresh && firstRecord && lastRecord){
					if (record==firstRecord.getPrevRecord()){
						_window.deleteTableRecord(element.tBodies[0].rows[element.tBodies[0].rows.length-1]);
						_window.insertTableRecord(element, "begin", null, record);
						needRefresh=false;
					}
					else if (record==lastRecord.getNextRecord()){
						_window.deleteTableRecord(element.tBodies[0].rows[0]);
						_window.insertTableRecord(element, "end", null, record);
						needRefresh=false;
					}
				}*/

				if (needRefresh){
					var counter=maxRow;
					var tmpRecord=record;

					for(var i=0; i<counter; i++){
						tmpRecord=tmpRecord.getNextRecord();
						if (!tmpRecord) break;
					}

					var startRecord=record;
					tmpRecord=record;
					counter=maxRow-i-1;
					for(var i=0; i<counter; i++){
						tmpRecord=tmpRecord.getPrevRecord();
						if (tmpRecord)
							startRecord=tmpRecord;
						else
							break;
					}

					_window.refreshTableData(element, startRecord);
				}
			}

			var row=_window.getTableRowByRecord(element, record);
			if (row){
				_window.setActiveTableRow(row);
			}
			break;
		}
		case "datalabel":{
			_window.refreshElementValue(element);
			break;
		}
		case "editor":;
		case "dockeditor":{
			_window.refreshElementValue(element);
			element.isUserInput=false;
			break;
		}
		case "datapilot":{
			_window.refreshDataPilot(element);
			break;
		}
		case "pagepilot":{
			_window.refreshPagePilot(element);
			break;
		}
	}
}

function _notifyDatasetBeforeUpdate(element, dataset, record, reserved){
	var _window=element.window;
	switch (element.getAttribute("extra")){
		case "dockeditor":{
			_window.updateEditorInput(element);
			break;
		}
	}
}

function _notifyDatasetStateChanged(element, dataset, record, reserved){
	var _window=element.window;
	switch (element.getAttribute("extra")){
		case "editor":;
		case "dockeditor":{
			var field=_window.getElementField(element);
			element.setReadOnly(!isFieldEditable(dataset, field));
			break;
		}
		case "datapilot":{
			_window.refreshDataPilot(element);
			break;
		}
		case "datatable":{
			if (element.activeRow) _window.refreshTableRowIndicate(element.activeRow);
			break;
		}
	}
}

function _notifyDatasetInsert(element, dataset, record, reserved){
	var _window=element.window;
	switch (element.getAttribute("extra")){
		case "datatable":{
			var row;
			if (record) row=_window.getTableRowByRecord(element, record);

			_window.insertTableRecord(element, reserved[0], row, reserved[1]);
			if (element.tBodies[0].rows.length>element.getAttribute("maxRow")){
				var lastRecord=_window.getTableLastRecord(element);
				if (lastRecord!=reserved[1]){
					_window.deleteTableRecord(element.tBodies[0].rows[element.tBodies[0].rows.length-1]);
				}
				else{
					_window.deleteTableRecord(element.tBodies[0].rows[0]);
				}
			}
			break;
		}
	}
}

function _notifyDatasetDelete(element, dataset, record, reserved){
	var _window=element.window;
	switch (element.getAttribute("extra")){
		case "datatable":{
			if (record){
				var row=_window.getTableRowByRecord(element, record);
				if (row){
					if (element.tBodies[0].rows.length<=element.getAttribute("maxRow")){
						var firstRecord=_window.getTableFirstRecord(element);
						var lastRecord=_window.getTableLastRecord(element);
						if (firstRecord){
							var _record=lastRecord.getNextRecord();
							if (_record){
								_window.insertTableRecord(element, "end", row, _record);
							}
							else{
								var _record=firstRecord.getPrevRecord();
								if (_record) _window.insertTableRecord(element, "begin", row, _record);
							}
						}
					}

					_window.deleteTableRecord(row);
				}
			}
			break;
		}
	}
}

function _notifyDatasetRefreshRecord(element, dataset, record, reserved){
	var _window=element.window;
	switch (element.getAttribute("extra")){
		case "datatable":{
			if (record){
				var row=_window.getTableRowByRecord(element, record);
				if (row) _window.refreshTableRecord(row);
			}
			break;
		}
		case "datalabel":;
		case "editor":;
		case "dockeditor":{
			_window.refreshElementValue(element);
			element.isUserInput=false;
			break;
		}
	}

	if (_window.isFileIncluded("editor")) _window.sizeDockEditor();
}

function _notifyDatasetRefresh(element, dataset, record, reserved){
	var _window=element.window;
	switch (element.getAttribute("extra")){
		case "datatable":{
			if (!compareText(element.style.visibility, "hidden")) {
				_window.refreshTableData(element);
			}
			else {
				element.needRefresh=true;
			}
			break;
		}
		case "datalabel":;
		case "editor":;
		case "dockeditor":{
			_window.refreshElementValue(element);
			element.isUserInput=false;
			break;
		}
		case "datapilot":{
			_window.refreshDataPilot(element);
			break;
		}
		case "pagepilot":{
			_window.refreshPagePilot(element);
			break;
		}
	}
	_notifyDatasetStateChanged(element, dataset, record, reserved);

	if (_window.isFileIncluded("editor")) _window.sizeDockEditor();
}

function _notifyFieldDataChanged(element, dataset, record, field, reserved){
	var _window=element.window;
	switch (element.getAttribute("extra")){
		case "datatable":{
			/*shen_antonio.*/
			var row=_window.getTableRowByRecord(element, record);
			if(!row){
				break;
			}
			for(var i=0; i<row.cells.length; i++){
				var cell=row.cells[i];
				if (compareText(cell.getAttribute("dataField"), field.name)){
					_window.refreshElementValue(cell);
				}
			}
			break;
		}
		case "editor":;
		case "dockeditor":{
			if (compareText(element.getAttribute("dataField"), field.name)){
				_window.refreshElementValue(element);
				element.isUserInput=false;
			}
			break;
		}
		case "datalabel":{
			if (compareText(element.getAttribute("dataField"), field.name)){
				_window.refreshElementValue(element);
			}
			break;
		}
	}

	if (_window.isFileIncluded("editor")) _window.sizeDockEditor();
}

function _notifyFieldStateChanged(element, dataset, record, field, reserved){
	switch (element.getAttribute("extra")){
		case "editor":;
		case "dockeditor":{
			var elmtField=getElementField(element);
			if (elmtField==field) {
				element.setReadOnly(!isFieldEditable(dataset, field));
			}
			break;
		}
	}
}

/*-----------------------------------------------------------------------------------------------------------------------------------------------------------------*/

function _resetRecordState(record){
	record.saveOldValue();
	if (record.recordState=="delete") {
		record.recordState="discard";
	}
	else if (record.recordState!="discard") {
		record.recordState="none";
	}
}

function _resetDatasetsState(submitManager){
	for (var i=0; i<submitManager.datasets.length; i++){
		var dataset=submitManager.datasets[i];
		dataset.disableControls();
	}
	try {
		for (var i=0; i<submitManager.datasets.length; i++){
			var dataset=submitManager.datasets[i];
			var record=dataset.firstUnit;
			while (record) {
				if (record.recordState != "none" && record.recordState != "discard"){
					var fieldCount=dataset.fields.fieldCount;
					for (var j=0; j<fieldCount; j++){
						var field=record.fields[j];
						if (field.dataType == "lob"){
							record.setValue(j, "");
						}
						if (_oidmap && record.recordState == "insert"){
							if (field.autoGenId){
								var value=_oidmap[record.getString(j)];
								if (getValidStr(value)!=""){
									dataset.setRecord(record);
									dataset.setValue(j, value);
								}
								dataset.updateRecord();
							}
						}
					}
				}
				_resetRecordState(record);
				record=record.nextUnit;
			}
		}
	}
	finally {
		for (var i=0; i<submitManager.datasets.length; i++){
			var dataset=submitManager.datasets[i];
			dataset.enableControls();
		}
	}
}

function _getUpdateString(submitManager){

	function doGetUpdateString(dataset){
		var prop="";
		prop+="id=\""+dataset.id+"\" ";
		prop+="sessionKey=\""+dataset.sessionKey+"\" ";
		if (dataset.masterDataset) {
			prop+="masterDataset=\""+dataset.masterDataset.id+"\" ";
			prop+="references=\""+dataset.referencesString+"\" ";
		}
		prop+="tag=\""+getEncodeStr(dataset.tag)+"\" ";
		var result="<Dataset "+prop+">";

		result+="<Fields>";
		for(var i=0; i<dataset.fields.fieldCount; i++){
			var field=dataset.getField(i);
			var prop="";
			prop+="name=\""+field.name+"\" ";
			prop+="dataType=\""+field.dataType+"\" ";
			prop+="nullable=\""+field.nullable+"\" ";
			prop+="updatable=\""+field.updatable+"\" ";
			prop+="valueProtected=\""+field.valueProtected+"\" ";
			prop+="fieldName=\""+field.fieldName+"\" ";
			prop+="tableName=\""+field.tableName+"\" ";
			prop+="tag=\""+getEncodeStr(field.tag)+"\" ";
			result+="<Field "+prop+"/>";
		}
		result+="</Fields>";

		result+="<Records>";
		var record=dataset.firstUnit;
		while (record){
			var needSubmit, needOldValue;
			if (dataset.submitData=="current"){
				needSubmit=(dataset.record==record);
				needOldValue=true;
			}
			else if (dataset.submitData=="selected") {
				needSubmit=isTrue(record.getValue("select"));
				needOldValue=true;
			}
			else {
				switch (record.recordState){
				case "none":
					needSubmit=(dataset.submitData=="all");
					needOldValue=false;
					break;
				case "insert":
					needSubmit=true;
					needOldValue=false;
					break;
				case "modify":
				case "delete":
					needSubmit=true;
					needOldValue=true;
					break;
				default:
					needSubmit=false;
					needOldValue=false;
					break;
				}
			}

			if (needSubmit){
				result+="<Record state=\""+record.recordState+"\">";
				result+="<data>";
				for(var i=0; i<dataset.fields.fieldCount; i++){
					result+=getEncodeStr(record.getString(i))+",";
				}
				result+="</data>";
				if (needOldValue){
					result+="<old>";
					for(var i=0; i<dataset.fields.fieldCount; i++){
						result+=getEncodeStr(record.getString(dataset.fields.fieldCount*2+i))+",";
					}
					result+="</old>";
				}
				result+="</Record>";
			}
			record=record.nextUnit;
		}
		result+="</Records>";

		result+="<UpdateItems>";
		for(var i=0; i<dataset.updateItems.length; i++){
			var item=dataset.updateItems[i];
			var prop="";
			prop+="updateMode=\""+item.updateMode+"\" ";
			prop+="dataSource=\""+item.dataSource+"\" ";
			prop+="tableName=\""+item.tableName+"\" ";
			prop+="keyFields=\""+item.keyFields+"\" ";
			prop+="updateFields=\""+item.updateFields+"\" ";
			result+="<UpdateItem "+prop+"/>";
		}
		result+="</UpdateItems>";

		result+="</Dataset>";
		return result;
	}

	var result="<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
	result+="<SubmitData updaterClass=\"" + getValidStr(submitManager.updaterClass) + "\" ";
	result+="forwardPath=\"" + submitManager.forwardPath + "\">";
	result+="<Parameters>";
	for(var i=0; i<submitManager.parameters.length; i++){
		result+="<Parameter name=\"" + submitManager.parameters[i].name + "\">" + getEncodeStr(submitManager.parameters[i].value) + "</Parameter>";
	}
	result+="</Parameters>";
	result+="<Datasets>";
	for (var i=0; i<submitManager.datasets.length; i++){
		result+=doGetUpdateString(submitManager.datasets[i]);
	}
	result+="</Datasets>";
	result+="</SubmitData>";
	return result;
}

//--------------------------------------------------

function _changeMasterRecordState(dataset){
	var masterDataset=dataset.masterDataset;
	if (masterDataset) {
		if (masterDataset.record.recordState=="none") {
			masterDataset.record.recordState="modify";
			_changeMasterRecordState(masterDataset);
		}
	}
}
//yjw
function setFieldDropDown(id,targetDataset){
	  var _id_ds = getDatasetByID(targetDataset);
	  var _field = _dataset_getField(_id_ds.fields,id);
	  _field.dropdown = id+"_DropDown";
}

function  initDropDownValues(){
		  var datasetAry = new Array();
		  datasetAry = getDatasets();
		  for(var k=0; k<datasetAry.length; k++){
			  var dataset = datasetAry[k];
			  if(!dataset.loadCompleted && dataset.type && dataset.type == "result"&&dataset.id!="_tmp_dataset_date"){
			  for(var i=0;i<dataset.fields.length;i++){
			  	if(dataset.fields[i].tag=="selectCQ"){
			  		var fieldId = dataset.fields[i].fieldName;
			  		var fieldName = fieldId+"Name";
		  			var fieldIdMap = "";
			  		var fieldNameMap = dataset.fields[i].viewField;//from getter
			  		var _dropdown_id = dataset.fields[i].dropDown;
			  		var _dropdown_dataset_id =dataset.fields[i].dropDownDataset;
			  		var _dropdown_dataset = getDatasetByID(_dropdown_dataset_id);
			  		if(_dropdown_dataset){
				  		var _dropdown = getDropDownByID(_dropdown_id);
				  		if(_dropdown!=""&&_dropdown!=null&&_dropdown.fieldMap!=""){
					  		var fieldMapStr = _dropdown.fieldMap.split(";");
					  		for(var j=0;j<fieldMapStr.length;j++){
					  			var fieldMapStr2 = fieldMapStr[j].split("=");
					  			if(fieldMapStr2[0]==dataset.fields[i].fieldName){
					  				fieldIdMap = fieldMapStr2[1];
					  				if(fieldNameMap==""){
						  					fieldNameMap = fieldIdMap+"Name"; //from  dataDic
						  			}
					  				break;
					  			}
					  		}
					  		var record=dataset.firstUnit;
							while (record){
									var fieldValue = record_getString_2(record,fieldId);
									var _record = _dropdown_dataset.firstUnit;
									while(_record){
										var fieldMapValue = record_getString_2(_record,fieldIdMap);
										if(fieldMapValue == fieldValue){
											var fieldNameMapValue = record_getString_2(_record,fieldNameMap);
											_record_setValue_2(record,fieldName,fieldNameMapValue);
											break;
										}
										_record=_record.nextUnit;
									}
								record=record.nextUnit;
							}
						}
			  		}
			  	}
			  }
		}
		/* shen_antonio 20080129 .*/
		//select 类型 增加一个空白选项
		else if( !dataset.loadCompleted && dataset.type && dataset.type == "dropdown"){
			if(dataset.init && dataset.init == "true"){
				if(dataset.require!="true")dataset.insertRecord("begin");
			}
		}
		/* .*/

	}
}

function  getFieldSelectNameValue(record,field){
				var fieldName = field.fieldName;
				var fieldNameMapValue ="";
				if(fieldName.length>4){
					var fieldNameMapValue="";
					var dataset = record.dataset;
					var length = fieldName.length;
			  		var fieldId = fieldName.substring(0,(length-4));
			  		var fieldIdMap = "";
			  		var fieldNameMap = field.viewField;//from getter
			  		var _dropdown_id = field.dropDown;
			  		var _dropdown_dataset_id =field.dropDownDataset;
			  		var _dropdown_dataset = getDatasetByID(_dropdown_dataset_id);
			  		var _dropdown = getDropDownByID(_dropdown_id);
			  		if(_dropdown_dataset){
				  		if(_dropdown.fieldMap!=""){
					  		var fieldMapStr = _dropdown.fieldMap.split(";");
					  		for(var j=0;j<fieldMapStr.length;j++){
					  			var fieldMapStr2 = fieldMapStr[j].split("=");
					  			if(fieldMapStr2[0]==fieldId){
					  				fieldIdMap = fieldMapStr2[1];
					  				if(fieldNameMap==""){
					  					fieldNameMap = fieldIdMap+"Name"; //from  dataDic
					  				}
					  				break;
					  			}
					  		}
							if (record){
									var fieldValue = record_getString_2(record,fieldId);
									if(fieldValue==""){
										return "";
									}
									var _record = _dropdown_dataset.firstUnit;
									while(_record){
										var fieldMapValue = record_getString_2(_record,fieldIdMap);
										if(fieldMapValue == fieldValue){
											 	fieldNameMapValue = record_getString_2(_record,fieldNameMap);
											    index=dataset.fields["_index_"+fieldId];
											    record[index] = fieldValue;
											    break;
										}
										_record=_record.nextUnit;
									}
									// if no match ,return code
									if(fieldNameMapValue==""){
										 fieldNameMapValue = fieldValue;
										 index=dataset.fields["_index_"+fieldId];
										 record[index] = fieldValue;
									}
							}
						}
			  		}
		  	}else{
		  	}
		  	//field.tag="";
		  	return fieldNameMapValue;
}

function dataset_getRealRecordCounts(){
	var count = 0 ;
	var dataset = this;
	if(dataset.length==0){
		return count;
	}else{
		var record=dataset.firstUnit;
		while (record) {
				if (record.recordState != "discard"){
					count ++ ;
				}
			record=record.nextUnit;
		}
	}
	return count;
}

/* shen_antonio .*/
function initDefaultDataset(dataset){
	if(dataset.getRealRecordCounts() == 0){
		dataset.insertRecord("begin");
	}
	var fieldCount = dataset.fields.fieldCount;
	for(var i=0; i<fieldCount; i++){
		var field = dataset.getField(i);
		if( field.defaultValue  && field.defaultValue != "" && dataset.getString(i) == ""){
			dataset.setValue(i,field.defaultValue);
		}
	}
}
function clearHisInputData(dataset) {
	if(dataset.getRealRecordCounts() > 0) {
		var fieldCount = dataset.fields.fieldCount;
		for(var i=0; i<fieldCount; i++){
			var field = dataset.getField(i);
			var datasetValue = dataset.getString(field.fieldName);
			var editorElement = document.getElementById("editor_" + field.fieldName);
			if(editorElement) {
				if(!datasetValue || "" == datasetValue) {
					editorElement.value = "";
				}
			}
		}
	}
	else {
		var fieldCount = dataset.fields.fieldCount;
		for(var i=0; i<fieldCount; i++){
			var field = dataset.getField(i);
			var editorElement = document.getElementById("editor_" + field.fieldName);
			if(editorElement) {
				editorElement.value = "";
			}
		}
	}
}
/* .*/

/* shen_antonio .*/
function copyDataset(disDatasetID,origDatasetID){
	var _disDataset = createDataset(disDatasetID,"","");
	_disDataset.flushData=dataset_flushData_new;
	var _origDataset = getDatasetByID(origDatasetID);
	_disDataset.fields = _origDataset.fields;
	_disDataset.parameters = _origDataset.parameters;
	_disDataset.readOnly=true;
	_disDataset.cqId = _origDataset.cqId;
	_disDataset.pageSize= _origDataset.pageSize;
	_disDataset.databusId= _origDataset.databusId;
	_disDataset.pageIndex=1;
	_disDataset.pageCount=1;
	_disDataset.masterDataset=_origDataset.masterDataset;
	_disDataset.references=_origDataset.references;
	_disDataset.submitData=_origDataset.submitData;
	_disDataset.autoLoadPage=false;
	_disDataset.autoLoadDetail=true;
	_disDataset.downloadUrl=getDecodeStr("~2fextraservice~2fdownloaddata");
	_disDataset.insertOnEmpty=_origDataset.insertOnEmpty;
	_disDataset.tag="";
	_disDataset.type="result";
	_disDataset.sessionKey="dd";
	_disDataset.init = _origDataset.init;
	initDataset(_disDataset);
	return _disDataset;
}


