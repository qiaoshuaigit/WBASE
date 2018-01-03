function dataset_flushData_new(pageIndex){
	try{
		var dataset=this;
		var eventName=getElementEventName(dataset, "flushDataPre");
		if (isUserEventDefined(eventName)){
			var event_result=fireUserEvent(getElementEventName(dataset, "flushDataPre"),[dataset]);
			if ( typeof(result) == "boolean" && !result ){
				return;
			}
		}
		_dataset_flushData_new(dataset, pageIndex);
		fireUserEvent(getElementEventName(dataset, "flushDataComplete"),[dataset]);
		//fireUserEvent(getElementEventName(dataset, "flushDataPost"),[dataset]);
	}
	catch (e){
		processException(e);
	}
}

function _dataset_flushData_new(dataset, pageIndex){
	dataset.disableControls();
	//useLoadingImage(_theme_root + "/loading.gif");
	try{
		dataset.clearData();
		if (dataset.sessionKey){
			var _url=_application_root+dataset.downloadUrl;
			var pageSize = dataset.pageSize;
			var _paramMap = new Object();
			_paramMap = converDateSetParameter2Map(dataset,_paramMap);
			_paramMap["nextPage"] = pageIndex;
			_paramMap["everyPage"] = pageSize;
			_paramMap["_session_key"] = dataset.sessionKey;
			if(""+dataset.async=="false"){
				DWREngine.setAsync(true);
			}else{
				DWREngine.setAsync(false);
			}
			var result=new Object();
			CommonQueryResultProcess.processAsyncBean(_paramMap,function(resultBean) {
								result.fieldStr= resultBean.fieldString;
								result.recordStr= resultBean.recordString;
								result.recordOrigStr = resultBean.recordOrigString;
								result.pageCount= resultBean.pageCount;
								result.pageIndex= pageIndex;
								result.pageSize= pageSize;
								/* modify by shen_antonio 20080121.*/
								dataset.resCd = resultBean.resCd;

								if( resultBean.resCd != '000000'){
									dataset.pageIndex=1;
									dataset.pageCount=0;
									var err = new Error(resultBean.resMsg);
									err.name = resultBean.resCd;
									throw err;
									//errAlert(resultBean.resMsg);
								}
								else{
								if (result.recordStr){
									appendFromDataString(dataset, result.fieldStr, result.recordStr, true);
								}
								dataset.pageIndex=result.pageIndex;
								dataset.pageCount=result.pageCount;
								/** set param .*/
								converStr2DataSetParameter(resultBean.parameterString,dataset);
								/* shen_antonio.*/
								var record=dataset.firstUnit;
								var i = 0;
								while (record){
									i++;
									initRecord(record, dataset);
									if( i/pageSize < 1 ) record.pageIndex = 1;
									else{
										record.pageIndex = calcPageCount(i,pageSize);
									}
									record=record.nextUnit;
								}
								if( result.pageCount == 1){
									dataset.pageCount = calcPageCount(i,pageSize);
								}
								/* .*/
								}
								/* shen_antonio .*/
								dataset.enableControls();
								dataset.refreshControls();
								dataset.loadDetail();
								/* modify by shen_antonio 20080116.*/
								fireUserEvent(getElementEventName(dataset, "flushDataPost"),[dataset]);
								/* 20080129 用于initCallGetter完成时触发.*/
								fireUserEvent(getElementEventName(dataset, "postInitCallGetter"),[dataset]);
								/* .*/

			  			}) ;
			  			delete _paramMap;
			  			DWREngine.setAsync(true);
			  			return result;
	}}catch(e){
		processException(e);
	}finally{
		dataset.enableControls();
		dataset.refreshControls();
		dataset.loadDetail();
	}
}
/* shen_antonio .*/
function _downloadData_new(dataset, pageSize, pageIndex){
	try{
		if (dataset.sessionKey){
			var _url=_application_root+dataset.downloadUrl;
			var _paramMap = new Object();
			_paramMap = converDateSetParameter2Map(dataset,_paramMap);
			_paramMap["nextPage"] = pageIndex;
			_paramMap["everyPage"] = pageSize;
			_paramMap["_session_key"] = dataset.sessionKey;
			/*
			_paramString = _paramString + ";nextPage," + pageIndex + ";everyPage," + pageSize  + ";_sessionKey," + dataset.sessionKey;
			var _paramMap = new Object();
			_paramMap["_param_str"] = _paramString;
			_paramMap["_session_key"] = dataset.sessionKey;
			*/
			var result=new Object();
			DWREngine.setAsync(false);
			CommonQueryResultProcess.processAsyncBean(_paramMap,function(resultBean) {
					result.fieldStr= resultBean.fieldString;
					result.recordStr= resultBean.recordString;
					result.recordOrigStr = resultBean.recordOrigString;
					result.pageCount= resultBean.pageCount;
					result.pageIndex= pageIndex;
					result.pageSize= pageSize;
					if( resultBean.resCd != '000000'){
						var err = new Error(resultBean.resMsg);
						err.name = resultBean.resCd;
						throw err;
					}
  			});
  			DWREngine.setAsync(true);
			return result;
		}

	}
	catch(e){
		processException(e);
	}
}
function getResultData(paramMap, pageSize, pageIndex) {
  				var resultBean = "";
  				DWREngine.setAsync(false);
  				useLoadingMessage('loading...');
  				CommonQueryResultProcess.processAsyncBean(paramMap["_param_str"],function(result) {
    				resultBean = result;
  				});
  				DWREngine.setAsync(true);
  				return resultBean;
  	}

function _clearRecord(dataset) {
	var fieldCount = dataset.fields.fieldCount;
	if(dataset.record) {
		for(i = 0; i < fieldCount; i ++) {
			dataset.record.setValue(i, null);
		}
	}
}

function _button_onclick_new(){
	try{
	var button=event.srcElement;
	button.blur();
	var result = fireUserEvent(getElementEventName(button, "onClickCheck"),[button]);
	if(typeof(result)=="boolean"&&!result){
		return false;
	}
	if (button.defaultOperation){
		switch (button.defaultOperation.toLowerCase())
		{
		case "clearrecord":
			var dataset = getDatasetByID(button.dataset);
			_clearRecord(dataset);
			break;
		case "cleardata":
			var dataset = getDatasetByID(button.dataset);
			_dataset_clearData(dataset);
			break;
		case "submitupdate":
			if (button.submitManager) {
				eval(button.submitManager + ".submit();");
			}
			break;
		case "refreshpage":
			window.open(window.location.href, "_self");
			break;
		case "submitform":
			var dataset = getDatasetByID(button.dataset);
			/*
			var datatableElement = element.datatable;
			var index = datatableElement.activeRowIndex;
			dataset.move(index-1);
			*/
			var form = document.createElement("FORM");
			form.method = "post";
			if(button.url){
				//form.action =  _application_root + _transDataActionURL;
				form.action = megerURL(_application_root,_transDataActionURL);
			}else{
				form.action = _application_root + "#";
			}
			form.style.visibility ="hidden";
			for (var i=0; i<dataset.fields.fieldCount; i++){
				form.insertAdjacentHTML("beforeEnd", "<input type=\"hidden\" name=\""+dataset.getField(i).fieldName+"\" >");
			}
			form.insertAdjacentHTML("beforeEnd","<input type=\"hidden\" name=\"_button_id\" >");
			form.insertAdjacentHTML("beforeEnd","<input type=\"hidden\" name=\"CQId\" >");
			document.body.appendChild(form);
			for (var i=0; i<dataset.fields.fieldCount; i++){
					form[dataset.getField(i).fieldName].value=dataset.getString(i);
			}
			form["_button_id"].value = button.id;
			form["CQId"].value = _common_query_id;
			form.submit();
			break;
		case "syncsubmit":
			var dataset = getDatasetByID(button.dataset);
			/* shen_antonio 20080121.*/
			var targetFrame = button.targetFrame==""?"_self":button.targetFrame;
			/* .*/
			var form = document.createElement("FORM");
			/* shen_antonio 20080121.*/
			form.target = targetFrame;
			/* .*/
			form.method = "post";
			if(button.url){
				//form.action =  _application_root + button.url;
				form.action = megerURL(_application_root,button.url);
			}else{
				form.action = _application_root + "#";
			}
			form.style.visibility ="hidden";
			for (var i=0; i<dataset.fields.fieldCount; i++){
				form.insertAdjacentHTML("beforeEnd", "<input type=\"hidden\" name=\""+dataset.getField(i).fieldName+"\" >");
			}
			document.body.appendChild(form);
			for (var i=0; i<dataset.fields.fieldCount; i++){
					form[dataset.getField(i).fieldName].value=dataset.getString(i);
			}
			/*
			form["CQId"].value = _common_query_id;
			*/
			form.submit();
			break;
		case "asysubmit":
			//if(checkRequireBeforeSubmit(button.dataset)){
				//var list = translateDataset2List(button.dataset);
					var bean = translateDataset2Bean(button);
					if(checkRequireBeforeSubmit(button)){
						var updateClass = button.updateclass;
						/* shen_antonio 20080121.*/
						var targetFrame = button.targetFrame==""?"_self":button.targetFrame;
						CommonQueryUpdateProcess.savaOrUpdate(updateClass,bean,function(result){
							var resultBean = result;
							if(resultBean.resCd == '000000'){
								try{
									var buttonReturnParam = resultBean.paramMap;
									button.returnParam=buttonReturnParam;
									var _restlt = true;
										_restlt = fireUserEvent(getElementEventName(button, "postSubmit"), [button]);
									var url =button.url;
									var path ;
									if(url!=""&&url!="#"){
										 //path =  _application_root + "/"+url;
										 path = megerURL(_application_root,url);
									}else if(url == "#"){
										 path = "#";
									}else{
										 path =  _successURL ;
									}
									 /* shen_antonio 20080121.*/
								   //window.location.href = path;
								   /* shen_antonio 20080331.*/
								   if( path!="#" ){
								   		window.open(path, targetFrame);
								   }
								}catch(e){
								}finally{
									_resetRecordState2(button);
								}
							}else{
								var err = new Error(result.resMsg);
								err.name = result.resCd;
								throw err;
								//alert(result.resMsg);
							}
						});
			}
			break;
		case "asyncqrysubmit":
			var dataset = getDatasetByID(button.dataset);
			//checkBeforeQuerySubmit add by youjinwu
			checkBeforeQuerySubmit(button);
  			var _paramMap = new Object();
  			_paramMap = converDateSet2Map(dataset);
  			_paramMap = converDateSetParameter2Map(dataset,_paramMap);
  			CommonQueryResultProcess.processAsyncBean(_paramMap,function(result) {
    			var resultBean = result;
				resultBean.url = _application_root + button.url;

				if(resultBean.resCd != '000000'){
					var err = new Error(resultBean.resMsg);
					err.name = resultBean.resCd;
					throw err;
					//errAlert(resultBean.resMsg);
				}else{

					if( resultBean.pageCount == 0 ){
						wrnAlert(constNoFoundRecode);
						return;
					}

					/** set param .*/
					converStr2DataSetParameter(resultBean.parameterString,dataset);
					var form = document.createElement("FORM");
					form.method = "post";
					if(button.url){
						//form.action =  _application_root + button.url;
						form.action = megerURL(_application_root,button.url);
					}else{
						form.action = _application_root + "#";
					}
					form.style.visibility ="hidden";
					for (var i=0; i<dataset.fields.fieldCount; i++){
						form.insertAdjacentHTML("beforeEnd", "<input type=\"hidden\" name=\""+dataset.getField(i).fieldName+"\"  value=\"" + dataset.getString(i)+ "\">");
					}
					var pId,pVal;
					var paramStr = converDateSetParameter2Str(dataset);
					form.insertAdjacentHTML("beforeEnd","<input type=\"hidden\" name=\"_paramStr_\" value=\"" + paramStr + "\">");
					form.insertAdjacentHTML("beforeEnd","<input type=\"hidden\" name=\"CQId\" value=\"" + resultBean.cqId + "\">");
					form.insertAdjacentHTML("beforeEnd","<input type=\"hidden\" name=\"fieldString\" value=\"" + resultBean.fieldString + "\">");
					form.insertAdjacentHTML("beforeEnd","<input type=\"hidden\" name=\"recordString\" value=\"" + resultBean.recordString + "\">");
					form.insertAdjacentHTML("beforeEnd","<input type=\"hidden\" name=\"recordOrigString\" value=\"" + resultBean.recordOrigString + "\">");
					form.insertAdjacentHTML("beforeEnd","<input type=\"hidden\" name=\"pageCount\" value=\"" + resultBean.pageCount + "\">");
					form.insertAdjacentHTML("beforeEnd","<input type=\"hidden\" name=\"pageIndex\" value=\"" + resultBean.pageIndex + "\">");
					form.insertAdjacentHTML("beforeEnd","<input type=\"hidden\" name=\"pageSize\" value=\"" + resultBean.pageSize + "\">");
					document.body.appendChild(form);
					form.submit();
				}
  			});
  			break;
		case "addrecord":
				checkBeforeQuerySubmit(button);
  				var dataset = getDatasetByID(button.dataset);
  				dataset.insertRecord("end");
  				fireUserEvent(getElementEventName(button, "onClick"), [button]);
  			break;
  		case "delrecord":
  				var dataset = getDatasetByID(button.dataset);
  				var count2  = dataset.getRealRecordCounts();
  				if(count2!=0){
  					dataset.deleteRecord();
  				}else{
  					//dataset.setReadOnly(true);
  				}
  				fireUserEvent(getElementEventName(button, "onClick"), [button]);
  			break;
  		case "asyncqrysubmitflush":
			var dataset = getDatasetByID(button.dataset);
			//checkBeforeQuerySubmit add by youjinwu
			checkBeforeQuerySubmit(button);
  			var resultDataset = getDatasetByID(button.resultDataset);
  			copyDateSetParameter(dataset,resultDataset);
  			DWREngine.beginBatch();
  			for (var i=0; i<dataset.fields.fieldCount; i++){
  				resultDataset.setParameter(dataset.getField(i).fieldName,dataset.getString(i));
  			}
  			funPreHook(_theme_root + "/loading.gif");
  			resultDataset.flushData(1);
  			DWREngine.endBatch();
  			//funPreHook(imageSrc);
			break;
		case "href":
		    if(button.url){
		    	/* shen_antonio 20080308.*/
				var targetFrame = button.targetFrame==""?"_self":button.targetFrame;
				/* .*/
		    	//var path = _application_root + "/"+button.url;
		    	var path = megerURL(_application_root,button.url);
		    	if( targetFrame == "_self" ){
			    	window.location.href = path;
		    	}else{
		    		window.open(path, targetFrame);
		    	}
		    }
			break;
		case "modesubmit":
			var dataset = getDatasetByID(button.dataset);
			/* shen_antonio 20080121.*/
			var targetFrame = button.targetFrame==""?"_self":button.targetFrame;
			/* .*/
			for (var i=0; i<dataset.fields.fieldCount; i++){
				//form.insertAdjacentHTML("beforeEnd", "<input type=\"hidden\" name=\""+dataset.getField(i).fieldName+"\" >");
			}
			window.showModalDialog(megerURL(_application_root,button.url),"","dialogHeight:600px; dialogWidth:600px; status:no; help:no; scroll:auto");
			break;
		};


	}
	else{
		fireUserEvent(getElementEventName(button, "onClick"), [button]);
	}
	}catch(e){
		processException(e);
	}
}

//youjinwu
//check require before submit 更新记录时的查询
function checkRequireBeforeSubmit(button){
				/* shen_antonio 20080126 .*/
				var result = fireUserEvent(getElementEventName(button, "needCheck"), [button]);
				if( typeof(result) == "boolean" && !result ){
					return true;
				}
				/* .*/
				//modify
				var updateSize = isNaN(button.submitUpdateTotalListSize)?0:button.submitUpdateTotalListSize;
				var noneSize = isNaN(button.noneListSize)?0:button.noneListSize;
				var deleteSize = isNaN(button.deleteSize)?0:button.deleteSize;
				if(updateSize==0&&noneSize==0&&deleteSize==0){
					alert(constCheckModify, "操作失败", "error");
					return false;
				}else if(updateSize!=0){
					var datasetAry = new Array();
					//提交指定的dataset
					if(button.submitDataset){
						datasetStr = button.submitDataset.split(";");
						for(var j=0;j<datasetStr.length;j++){
							var _dataSet = getDatasetByID(datasetStr[j]);
							_dataSet.type == "result"
							datasetAry[datasetAry.length] = _dataSet;
						}
					}else{
						datasetAry = getDatasets();
					}
					var errInfo= "";//校验必输
					var errInfo2="";//校验长度
					for(var k=0; k<datasetAry.length; k++){
						var dataset = datasetAry[k];
						if(dataset.type!="drowdown"&&dataset.id!="_tmp_dataset_date"){
							var fieldCount=dataset.fields.fieldCount;
							for (var i=0; i<fieldCount; i++){
							try{
								if(dataset.fields[i].tag!="selectName"&&dataset.fields[i].label!=""){
										if (!isTrue(dataset.fields[i].readOnly) && isTrue(dataset.fields[i].required) &&
											dataset.getString(i)=="" && dataset.record){
												errInfo +=   constErrFieldValueRequired.replace("%s", dataset.fields[i].label) + '\n';
												//throw constErrFieldValueRequired.replace("%s", dataset.fields[i].label);
											}
								}
								//增加字段长度校验
								if(dataset.fields[i].tag!="select" && dataset.fields[i].tag!="selectCQ" && dataset.fields[i].tag!="selectName" && dataset.fields[i].toolTip!="" ){
												if(dataset.fields[i].size!=""){
												    if (dataset.fields[i].dataType=="int" || dataset.fields[i].dataType=="double"|| dataset.fields[i].dataType=="float" || dataset.fields[i].dataType=="short" ){
												    	if(dataset.getString(i)!=""){
												    		var fieldLength = dataset.fields[i].size;
												    		if(dataset.fields[i].dataType=="double"|| dataset.fields[i].dataType=="float"&&dataset.fields[i].scale>0){
												    			fieldLength = fieldLength +1;//包含小数点
												    		}
												    		if(dataset.getString(i).length > fieldLength){
												    			errInfo2 = errInfo2 + ("[" + dataset.fields[i].toolTip + "]"+　constFieldSizeError.replace("%s", dataset.fields[i].size) ) + '\n';
												    		}
												    	}
												    	//check for chinese
												    }else if(dataset.fields[i].dataType=="" || dataset.fields[i].dataType=="string" || dataset.fields[i].dataType=="String"){
												   		 if(dataset.getString(i)!=""){
												    		if(dataset.getString(i).replace(/[^\x00-\xff]/g,'**').length > dataset.fields[i].size){
												    			errInfo2 = errInfo2 + ("[" + dataset.fields[i].toolTip  + "]"+　constFieldSizeErrorString.replace("%s", dataset.fields[i].size).replace("%s1", Math.floor(dataset.fields[i].size/2)))+ '\n';
												    		}
												    	}
												    }
											    }
								}
								}catch(e){}
							}
						}
					}
					if(errInfo!=""){
							errInfo = errInfo.replace(/\<br\>/gi,"");
							errAlert(errInfo);
							return false;
					}
					if(errInfo2!=""){
						/*
							errAlert(errInfo2);
							return false;
						*/
					}

				}
				return true;
}
//查询提交时的检查
function checkBeforeQuerySubmit(button){
	      /* shen_antonio 20080126 .*/
				var result = fireUserEvent(getElementEventName(button, "needCheck"), [button]);
				if( typeof(result) == "boolean" && !result ){
					return true;
				}
				var dataset = getDatasetByID(button.dataset);
				var errInfo= "";//校验必输
				var errInfo2="";//校验长度
					var fieldCount=dataset.fields.fieldCount;
					for (var i=0; i<fieldCount; i++){
					/* modify by shen_antonio 20080116.*/
						if( dataset.fields[i].tag && dataset.fields[i].tag == "selectName" ){
							continue;
						}
						/* modify by tanghe 查询清除后必输字段为null提示*/
						if (!isTrue(dataset.fields[i].readOnly) && isTrue(dataset.fields[i].required) &&
							dataset.getString(i)=="" && dataset.record){
								errInfo +=   constErrFieldValueRequired.replace("%s", dataset.fields[i].label) + '\n';
								//throw constErrFieldValueRequired.replace("%s", dataset.fields[i].label);
						}
						//增加字段长度校验
								if(dataset.fields[i].tag!="select" && dataset.fields[i].tag!="selectCQ" && dataset.fields[i].tag!="selectName" && dataset.fields[i].toolTip!="" ){
												if(dataset.fields[i].size!=""){
												    if (dataset.fields[i].dataType=="int" || dataset.fields[i].dataType=="double"|| dataset.fields[i].dataType=="float" || dataset.fields[i].dataType=="short" ){
												    	if(dataset.getString(i)!=""){
												    		var fieldLength = dataset.fields[i].size;
												    		if(dataset.fields[i].dataType=="double"|| dataset.fields[i].dataType=="float"&&dataset.fields[i].scale>0){
												    			fieldLength = fieldLength +1;//包含小数点
												    		}
												    		if(dataset.getString(i).length > fieldLength){
												    			errInfo2 = errInfo2 + ("[" + dataset.fields[i].toolTip + "]"+　constFieldSizeError.replace("%s", dataset.fields[i].size) ) + '\n';
												    		}
												    	}
												    	//check for chinese
												    }else if(dataset.fields[i].dataType=="" || dataset.fields[i].dataType=="string" || dataset.fields[i].dataType=="String"){
												   		 if(dataset.getString(i)!=""){
												    		if(dataset.getString(i).replace(/[^\x00-\xff]/g,'**').length > dataset.fields[i].size){
												    			errInfo2 = errInfo2 + ("[" + dataset.fields[i].toolTip  + "]"+　constFieldSizeErrorString.replace("%s", dataset.fields[i].size).replace("%s1", Math.floor(dataset.fields[i].size/2)))+ '\n';
												    		}
												    	}
												    }
											    }
								}
					}
						if( errInfo.length != 0){
							errInfo = errInfo.replace(/\<br\>/gi,"");
							throw errInfo;
						}
						/*
						if(errInfo2.length != 0){
							throw errInfo2;
						}
						*/
					return true;
}
//youojinwu
//changet dataset to list , the dataset's record is translated to  map ,and will fill the map to the list ,submit the list
function translateDataset2List(datasetId){
			var dataset = getDatasetByID(datasetId);
			var list = new Array();
			var record=dataset.firstUnit;
			var i = 0 ;
			while (record) {
				if (record.recordState != "none" && record.recordState != "discard"){
					var map = translateRecord2Map(dataset,record);
					list[i]=map;
					i++;
				}
				_resetRecordState(record);
				record=record.nextUnit;
			}
		    return list;
}

//youjinwu
function  translateRecord2Map(dataset,record){
		//定义一个空对象reply
		var reply = {};
		//定义一个对象放key
    	var key;
    	//定义一个对象放value
    	var value;
	    //遍历form,将表单key-value放入reply
	    for (var j=0; j<dataset.fields.fieldCount; j++) {
		    key = dataset.getField(j).fieldName;
		    value = record.getString(j);
		    reply[key] = value;
	    }
	    if(record.recordState=="new"){
	    	reply["recordState"]="insert";
	    }else{
	    	reply["recordState"] = record.recordState;
	    }
		return reply;
}
//yjw modify
//重新初始化record的状态,并且设置dataset加载状态为完成
function resetDataSetRecordState(){
	var datasetAry = getDatasets();
	for(var k=0; k<datasetAry.length; k++){
				var dataset = datasetAry[k];
				if( !dataset.loadCompleted ){
				dataset.loadCompleted=true;
				if(dataset.type && dataset.type == "result"){
					var record=dataset.firstUnit;
					while (record) {
						record.recordState = "none";
						record=record.nextUnit;
					}
				}
			}
	}
}

/*
*/
/* modify by shen_antonio 20080123 .*/
function initCallGetter(){
		var datasetAry = getDatasets();
		useLoadingImage(_theme_root + "/loading.gif");
		fireUserEvent("initCallGetter_pre",0);
		//DWREngine.setAsync(false);
		DWREngine.beginBatch();
		DWREngine.setOrdered(true);
			/** init result.*/
		for(var i=0; i<datasetAry.length; i++){
			var dataset = datasetAry[i];
			fireUserEvent(getElementEventName(dataset, "requestInit"),[]);
			if(dataset.type && dataset.type == "result"){
				if(dataset.init && dataset.init == "true"){
					_lastDataSetID = dataset.id;
					dataset.flushData(1);
				}
			}else{
			}
		}
		/** init dropdown.*/
		for(var i=0; i<datasetAry.length; i++){
		var dataset = datasetAry[i];
		if(dataset.type && dataset.type == "dropdown"){
			if(dataset.init && dataset.init == "true"){
				_lastDataSetID = dataset.id;
				dataset.flushData(1);
				/* shen_antonio
				if(dataset.require!="true"){
					dataset.insertRecord("begin");
				}
				*/
			}
		}else{
		}
		}
		if( typeof(_lastDataSetID) == "string" ){
			eval(_lastDataSetID+"_postInitCallGetter=function(){initDropDownValues();fireUserEvent(\"initCallGetter_post\",0);resetDataSetRecordState();}");
		}else{
			eval("initDropDownValues();fireUserEvent(\"initCallGetter_post\",0);resetDataSetRecordState();");
		}

		DWREngine.setOrdered(false);
		DWREngine.endBatch();
		//DWREngine.setAsync(true);
		//fireUserEvent("initCallGetter_post",0);
}

function initLetCallGetter(id){
		var datasetAry = getDatasets();
		useLoadingImage(_theme_root + "/loading.gif");
		fireUserEvent(id+"_initCallGetter_pre",0);
		//DWREngine.setAsync(false);
		DWREngine.beginBatch();
		DWREngine.setOrdered(true);
			/** init result.*/
		for(var i=0; i<datasetAry.length; i++){
			var dataset = datasetAry[i];
			fireUserEvent(getElementEventName(dataset, "requestInit"),[]);
			if(dataset.type && dataset.type == "result"){
				if(!dataset.loadCompleted && dataset.init && dataset.init == "true"){
					_lastDataSetID = dataset.id;
					dataset.flushData(1);
				}
			}else{
			}
		}
		/** init dropdown.*/
		for(var i=0; i<datasetAry.length; i++){
		var dataset = datasetAry[i];
		if(dataset.type && dataset.type == "dropdown"){
			if(!dataset.loadCompleted && dataset.init && dataset.init == "true"){
				_lastDataSetID = dataset.id;
				dataset.flushData(1);
				/* shen_antonio
				if(dataset.require!="true"){
					dataset.insertRecord("begin");
				}
				*/
			}
		}else{
		}
		}
		if( typeof(_lastDataSetID) == "string" ){
			eval(_lastDataSetID+"_postInitCallGetter=function(){initDropDownValues();fireUserEvent(\""+ id + "_initCallGetter_post\",0);resetDataSetRecordState();}");
		}else{
			eval("initDropDownValues();fireUserEvent(\"" + id + "_initCallGetter_post\",0);resetDataSetRecordState();");
		}

		DWREngine.setOrdered(false);
		DWREngine.endBatch();
		//DWREngine.setAsync(true);
		//fireUserEvent("initCallGetter_post",0);
}

function flushDataset(dataset){
			dataset.disableControls();
			dataset.clearData();
			var _dataset_paramMap = converDateSetParameter2Map(dataset);

			CommonQueryResultProcess.processAsyncBean(_dataset_paramMap,function(result) {
    			var resultBean = result;
				if( resultBean.resCd != '000000'){
					errAlert(resultBean.resMsg);
				}else{

				}
				appendFromDataString(dataset, result.fieldString, result.recordString, true);
				dataset.pageIndex=result.pageIndex;
				dataset.pageCount=result.pageCount;

				/** set param .*/
				converStr2DataSetParameter(result.parameterString,dataset);

				dataset.enableControls();
				dataset.refreshControls();
				dataset.loadDetail();

		});
}

function translateDataset2Bean(button){
			//上传修改,新增的记录
			var submitUpdateTotalListSize = 0;
			var deleteSize = 0;
			var noneListSize = 0;
			var beanMap = new  Object();
			var mutilBeanMap = new Object();
			var datasetAry = new Array();
			var dataBusId = "";
			var btnDataset = getDatasetByID(button.dataset);
			var btnCqId =  btnDataset.cqId;
			//提交指定的dataset
			if(button.submitDataset){
				datasetStr = button.submitDataset.split(";");
				for(var j=0;j<datasetStr.length;j++){
					var _dataSet = getDatasetByID(datasetStr[j]);
					_dataSet.type = "result";
					datasetAry[datasetAry.length] = _dataSet;
				}
			}else{
				datasetAry = getDatasets();
			}
			for(var k=0; k<datasetAry.length; k++){
				var dataset = datasetAry[k];
				var totalList = new Array();
				if(dataset.type && dataset.type == "result"){
					var record=dataset.firstUnit;
					while (record) {
						if(dataset.submitData=="allchange"){
							if (record.recordState != "none" && record.recordState != "discard"){
								var map = translateRecord2Map(dataset,record);
								totalList[totalList.length] = map;
								//删除的记录不检查是否必输
								if(record.recordState != "delete"){
									submitUpdateTotalListSize ++;
								}
								if(record.recordState == "delete"){
									deleteSize ++;
								}
							}
						}
						 /* shen_antonio 20080126 .*/
						 else if(dataset.submitData=="current"){
							if (dataset.record==record){
								var map = translateRecord2Map(dataset,record);
								totalList[totalList.length] = map;
								submitUpdateTotalListSize ++;
							}
						}else if(dataset.submitData=="selected"){
							if(record.getValue("select")){
								var map = translateRecord2Map(dataset,record);
								totalList[totalList.length] = map;
								submitUpdateTotalListSize ++;
							}
						}
						/* .*/
						else if(dataset.submitData=="all"){
							if (record.recordState != "discard"){
								var map = translateRecord2Map(dataset,record);
								totalList[totalList.length] = map;
								if (record.recordState != "none"){
									submitUpdateTotalListSize ++;
								}else{
									noneListSize ++;
								}
							}
						}else{
						}
						//_resetRecordState(record);
						record=record.nextUnit;
					}
					    var recordParMap = new Object();
						recordParMap = converDateSetParameter2Map_2(datasetAry[k]);
						var datasetid = dataset.id;
						if(dataBusId=="")dataBusId = dataset.databusId;
						/* modify by shen_antonio 20080409.*/
						//datasetid = datasetid.substring(0,(datasetid.length)-8);
						datasetid = dataset.cqId;
						/* .*/
						//  去掉 datasetid 中的 "_dataset"
						var bean = {
							id          : datasetid,
							paramMap    : recordParMap,
							totalList   : totalList,
							recodeIndex : 0
						}
						beanMap[datasetid]=bean;
				}

			}
			mutilBeanMap["databusId"] = dataBusId;
			var  multiMap ={
				updCqId      :  btnCqId ,
				updateResult :  beanMap ,
				paramMap	 :	mutilBeanMap
			}
			button.submitUpdateTotalListSize = submitUpdateTotalListSize;
			button.noneListSize = noneListSize;
			button.deleteSize = deleteSize;
		    return multiMap;
}

function _resetRecordState2(button){
		try{
			if(button.submitDataset){
					datasetStr = button.submitDataset.split(";");
					for(var j=0;j<datasetStr.length;j++){
						var _dataSet = getDatasetByID(datasetStr[j]);
						_dataSet.type == "result"
						datasetAry[datasetAry.length] = _dataSet;
					}
				}else{
					datasetAry = getDatasets();
				}
				for(var k=0; k<datasetAry.length; k++){
					var dataset = datasetAry[k];
					if(dataset.type && dataset.type == "result"){
						try{
							var record=dataset.firstUnit;
							while(record){
							      _resetRecordState(record);
								  record=record.nextUnit;
								}
						  }catch(e){
						  }finally{
						  	   dataset_setState(dataset, "none");
						  	   dataset.modified=false;

						  }
				}
			}
		}catch(e){
		}
}
function converDateSetParameter2Map_2(dataset){
	var pId,pVal;
	var paramMap2 = new Object();
	for (var i=0; i<dataset.parameters.length; i++){
		pId  = dataset.parameters[i].name;
		pVal = dataset.parameters[i].value;
		if( pVal!=null ){
			paramMap2[pId] = pVal;
		}
	}
	paramMap2["_dataset_id"] = dataset.id;
	return paramMap2;
	}

//文件上传进度条
function refreshProgress()
{
	DWREngine.setPreHook(function() {});
    DWREngine.setPostHook(function() {});
    UploadMonitor.getUploadInfo(updateProgress);
}

function updateProgress(uploadInfo)
{
    if (uploadInfo.inProgress)
    {
    	fireUserEvent("uploadFileProgress",0);
        var fileIndex = uploadInfo.fileIndex;
        var progressPercent = Math.ceil((uploadInfo.bytesRead / uploadInfo.totalSize) * 100);
        document.getElementById('progressBarText').innerHTML = 'upload in progress: ' + progressPercent + '%';
        document.getElementById('progressBarBoxContent').style.width = parseInt(progressPercent * 3.5) + 'px';
        window.setTimeout('refreshProgress()', 1000);
    }
    else
    {
    	fireUserEvent("uploadFileComplete",0);
    }

    return true;
}

function startProgress()
{
    document.getElementById('progressBar').style.display = 'block';
    document.getElementById('progressBarText').innerHTML = 'upload in progress: 0%';
    window.setTimeout("refreshProgress()", 1500);
    return true;
}


/** 载入页面片段.*/
function loanPageLet(url,paramMap,elementId){
	funPreHook(_theme_root + "/loading.gif");
	var v_div1 = document.getElementById(elementId);
	uninitLet(v_div1);
	v_div1.innerHTML = "";
    createXMLHttpRequest();
    var queryString = createQueryString();
    var url = _application_root + url + "?_mpf_=false" + queryString;
    xmlHttp.open("POST", url, true);
    xmlHttp.onreadystatechange = handleStateChange;
    xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    //xmlHttp.send(queryString);
    xmlHttp.send();

    function handleStateChange() {
    if(xmlHttp.readyState == 4) {
        if(xmlHttp.status == 200) {
        	parseResults();
        }
    }
	};
	function parseResults() {
		var v_div1 = document.getElementById(elementId);
    	v_div1.innerHTML = xmlHttp.responseText;
    	runScript(v_div1);
    	initDocumentLet(v_div1);
    	funPostHook();
	};
	function runScript(parent){
		var v_div1 = parent;
		var arg = v_div1.getElementsByTagName("SCRIPT");
		for(j = 0; j < arg.length; j++){
			CQSPACE.Eval(arg[j].innerHTML);
		}
	};
	function createXMLHttpRequest() {
    	if (window.ActiveXObject) {
        	xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
    	}
    	else if (window.XMLHttpRequest) {
        	xmlHttp = new XMLHttpRequest();
    	}
	};
	function createQueryString() {
		var pId,pVal;
		var queryString = "";
		var keyArg = paramMap.keys();

		for(var i=0; i<keyArg.length; i++){
			pId = keyArg[i];
			pVal = paramMap.get(pId);
			queryString += "&" + pId + "=" + pVal;
		}
   	 	return queryString;
	};
}
/* 载出页面片段 */
function unloadPageLet(elementId){
	funPreHook(_theme_root + "/loading.gif");
	var v_div1 = document.getElementById(elementId);
	uninitLet(v_div1);
	v_div1.innerHTML = "";
	funPostHook();
}

var CQSPACE ={}    //
CQSPACE.Eval=function(code){
 if(!!(window.attachEvent && !window.opera)){
  //ie
  if(code && "" != code) {
	  try{
		  execScript(code);
	  }catch(e){}
  }
 }else{
  //not ie
  window.eval(code);
 }
}

function showMessagePage(id, title, msg, _width, _height) {
	window.scrollTo(0, 0);
	var zone = document.createElement('div');
	zone.setAttribute('id', id);
	zone.style.backgroundColor = '#EEEEEE';
	zone.style.position = "absolute";
	zone.style.zIndex = "49";
	zone.style.left = "0px";
	zone.style.top = "0px";
	zone.style.width = (document.documentElement.clientWidth || document.body.scrollWidth) + 800;
	zone.style.height = (document.documentElement.clientHeight || document.body.scrollHeight) + 600;
	zone.style.filter = "alpha(opacity=60)";
	document.body.appendChild(zone);
	document.body.style.overflow = "hidden";

	var elementId = id + "_content";
	winZone = document.createElement('div');
	winZone.setAttribute('id', elementId);
	winZone.innerHTML = msg;
	$(__WinsDiv).appendChild(winZone);
	var dialogTop = (((document.documentElement.clientHeight || document.body.clientHeight) - _height)/2) >> 0;
	var dialogLeft = (((document.documentElement.clientWidth || document.body.clientWidth) - _width)/2) >> 0;
	win = createMessageWindow(id, dialogLeft, dialogTop, _width, _height);
	win.setText(title);
	win.attachObject(elementId, true);
	win.attachEvent("onClose",function(win) {
		document.body.removeChild($(id));
		document.body.style.overflow = "auto";
		return true;
	});

	//document.body.scroll="no";
}

/* 载入弹出窗口层 */
function loadPageWindows(id, title, url, paramMap, elementId, _width, _height) {
	window.scrollTo(0, 0);
	var zone = document.createElement('div');
	zone.setAttribute('id', 'zone');
	zone.style.backgroundColor = '#EEEEEE';
	zone.style.position = "absolute";
	zone.style.zIndex = "49";
	zone.style.left = document.documentElement.clientWidth/2;
	zone.style.top = "0px";
	zone.style.width = (document.documentElement.clientWidth || document.body.scrollWidth) + 800;
	//zone.style.height = (document.documentElement.clientHeight || document.body.clientHeight);
	zone.style.height = (document.documentElement.clientHeight || document.body.scrollHeight) + 600;
	//zone.style.width = 800;
	zone.style.filter = "alpha(opacity=60)";
	document.body.appendChild(zone);
	document.body.style.overflow = "hidden";

	winZone = document.createElement('div');
	winZone.setAttribute('id', elementId);
	$(__WinsDiv).appendChild(winZone);
	loanPageLet(url,paramMap,elementId);
	var dialogTop = (((document.documentElement.clientHeight || document.body.clientHeight) - _height)/2) >> 0;
	//var dialogTop = 30;
	var dialogLeft = (((document.documentElement.clientWidth || document.body.clientWidth) - _width)/2) >> 0;
	win = createWindows(id,dialogLeft,dialogTop, _width, _height);
	win.setText(title);
	win.attachObject(elementId,true);
	win.attachEvent("onClose",function(win) {
		var eventName=getElementEventName(winZone, "onCloseCheck");
			if (isUserEventDefined(eventName)) {
				var event_result=fireUserEvent(eventName, [win]);
				if(typeof(event_result) == "boolean") {
					if(event_result==true) {
						unloadPageLet(elementId);
						document.body.removeChild($('zone'));
					} else {
						return false;
					}
				} else {
					unloadPageLet(elementId);
					document.body.removeChild($('zone'));
				}
			} else {
				unloadPageLet(elementId);
				document.body.removeChild($('zone'));
			}
			document.body.style.overflow = "auto";
			return true;
	});
}

/* 载出弹出窗口 */
function unloadPageWindows(id){
	win = dhxWins.window(id);
	win.autoClosed = true;
	win.close();
}

/* 创建窗口 */
function createWindows(id,left, top, width, height){
	var win = dhxWins.createWindow(id, left, top, width, height);
	win.setModal(true);
	win.keepInViewport(true);
	//win.clearIcon();
	//win.denyMove();
	win.denyPark();
	//win.button("help").show();
	//win.button("stick").show();
	win.button("park").hide();
	//win.button("minmax1").hide();
	win.button("minmax2").hide();

	return win;
}

function createMessageWindow(id,left, top, width, height){
	var win = dhxWins.createWindow(id, left, top, width, height);
	win.setModal(true);
	win.keepInViewport(true);
	//win.clearIcon();
	//win.denyMove();
	win.denyPark();
	//win.button("help").show();
	//win.button("stick").show();
	win.button("park").hide();
	win.button("minmax1").hide();
	win.button("minmax2").hide();

	return win;
}

/* print button function */
function _printButton_onclick(){
	showLoadingImage(_theme_root + "/loading.gif",true);
	var button=event.srcElement;
	var fieldStr = button.fieldStr;
	var dataset = button.dataset;
	var headTitle = button.headTitle;
	var newDatasetId = "_" + dataset+"_print";
	_dataset_print = copyDataset("_" + dataset+"_print",dataset);
	_dataset_print.pageIndex = 1;
	_dataset_print.pageSize = 10000;
	_dataset_print.setReadOnly(true);
	_dataset_print.flushData(1);
	printDatasetASExcel(_dataset_print,fieldStr,headTitle);
	_dataset_print.clearData();
	delete _dataset_print;
	showLoadingImage(_theme_root + "/loading.gif",false);
}

/**added by wangpeng 2009/09/21 BMS-1990 begin**/
/* 清空已添加的report-url*/
function _printapplet_clearUrlList(){

	//var _printApplet=document.getElementById('PrintApplet');
	//_printApplet.clearUrlList(); 
}

/**添加报表url*/
function _printapplet_addReportUrl(reportId,viewUrl,printUrl){
/*
	var _printApplet=document.getElementById('PrintApplet');
	var _printPath=_application_root+"/PrintServlet?";
	if(printUrl==null){
		_printApplet.addReportUrl(_printPath+"report_id="+reportId+"&"+viewUrl);
	}else{ 
		_printApplet.addReportUrl(_printPath+"report_id="+reportId+"&"+viewUrl,_printPath+"report_id="+reportId+"&"+printUrl);
	}
//*/
	window.open(_application_root+"/jpages/draftFace/draftDisplay.jsp?report_id=RPT0001&"+viewUrl,
	"newwindow", "height=600, width=800, top=50,left=50, toolbar=no, menubar=no,  resizable=no,location=no, status=no,scrollbars=yes");
}

/**显示报表预览界面*/
function _printapplet_showViewForm(){
	//var _printApplet=document.getElementById('PrintApplet');
	//_printApplet.showViewForm();
}
/**added by wangpeng 2009/09/21 BMS-1990 end**/