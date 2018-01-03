function RadioRender(id){
	/*初始化*/
	RadioRender._array_radio[RadioRender._array_radio.length] = this;
	
	/*私有成员*/
	var _id = id;
	var _fieldMap = undefined;
	var _dataset = undefined;
	var _radioValueField = undefined;
	var _radioViewField = undefined;
	var _viewField = undefined;
	var _valueField = undefined;
	var _targetDataset = undefined;
	var _array_radioBox = new Array();
	var _hiddenRadioBox;
	var _rowLen = 0;
	
	/*公共成员*/
	this.type;
	this.fields;
	this.table;
	this.require;
	
	this.getDataset = function (){
		if(typeof(_dataset) == "string"){
			_dataset = getDatasetByID(_dataset);
		}
		return _dataset;
	};
	this.getTargetDataset = function(){
		if(typeof(_targetDataset) == "string"){
			_targetDataset = getDatasetByID(_targetDataset);
		}
		return _targetDataset;
	};
	this.getRadioValueField = function(){
		return _radioValueField;
	};
	this.getRadioViewField = function(){
		return _radioViewField;
	};
	this.getValueField = function(){
		return _valueField;
	};
	this.getViewField = function(){
		return _viewField;
	};
	this.setDataset = function(dataset){
		_dataset = dataset;
	};
	
	this.setTargetDataset = function(dataset){
		_targetDataset = dataset;
	};
	
	this.setHiddenRadioBox = function(radioBox){
		_hiddenRadioBox=radioBox;
	};
	
	this.getRowLen = function(){
		return _rowLen;
	};
	
	this.setRowLen = function(len){
		_rowLen = len;
	};
	
	this.setFieldMap = function(fieldMap){
		_fieldMap = fieldMap;
		if(!_fieldMap) return;
		
		var maps = _fieldMap.split(';');
		if(maps.length<2) return;
		
		var valueMap = maps[0];
		var viewMap = maps[1];
		var valueFields = valueMap.split('=');
		var viewFields = viewMap.split('=');
		if(valueFields.length<2 || viewFields.length<2) return;
		
		_valueField = valueFields[0];
		_radioValueField = valueFields[1];
		_viewField = viewFields[0];
		_radioViewField = viewFields[1];
	};
	
	this.getId = function(){
		return _id;
	};
	
	this.addRadioBox = function(radioBox){
		if(radioBox.isHidden()){
			radioBox.setName(_id+"render_hidden");
			_hiddenRadioBox=radioBox;
		}else{
			_array_radioBox.push(radioBox);
			radioBox.setName(_id+"render"+_array_radioBox.length);
		}
	};
	
	this.showRadioRenderBox = function(holder){
		if(!holder) return;
		if(!_hiddenRadioBox){
			var radioBox = new RadioBox(this,true);
			this.addRadioBox(radioBox);
		}
		_hiddenRadioBox.showHiddenRadioBox(holder);
	};
	
	this.hideRadioRenderBox=function(){
		if(_hiddenRadioBox){
			_hiddenRadioBox.hide();
		};
	};
	
	this.getRadioNameValue=function(value){
		var result = "";
		var radioDataset = this.getDataset();
		var record = radioDataset.firstUnit;
		while(record){
			if(record.getValue(_radioValueField) == value){
				result = record.getValue(_radioViewField);
				break;
			}
			record = record.nextUnit;
		}
		return result;
	};
	
	this.selRadioFromRecord=function(record){
		if(record){
			var radioValue=record.getValue(_valueField);
			
			for(var i =0 ;i<_array_radioBox.length;i++){
				 var radioBox=_array_radioBox[i];
				 radioBox.selRadio(radioValue);
			}
			
			if(_hiddenRadioBox){
				_hiddenRadioBox.selRadio(radioValue);
			}
		}
	};
	
	this.sizeRadioBox = function(){
		if(_hiddenRadioBox){
			_hiddenRadioBox.size();
		}
	};
	
};

function RadioBox(radio,hidden){
	/*私有成员*/
	var _name;
	var _initialized = false;
	var _container = undefined;
	var _hidden = false;
	var _rowLen = 0;
	if(typeof(hidden) == "boolean") _hidden=hidden;
	var _radio = RadioRender.getRadio(radio);
	
	if(typeof(_radio) == "object"){
	    _rowLen = _radio.getRowLen();	
	}
	
	function _creatRadio(record){
		if(!_radio) return;
		var radioValueField = _radio.getRadioValueField();
		if(!radioValueField) return;
		var radioViewField = _radio.getRadioViewField();
		if(!radioViewField) return;
		
		var radio=document.createElement("<input type='radio' value='"+record.getValue(radioValueField)+"' name='"+_name+"'/>");
		radio.onclick=_radioOnClick;
		radio.onmouseup=_radioOnMouseUp;
		radio.label=record.getValue(radioViewField);
        return radio;	
	};
	
	function _creatRadioLabel(record){
		if(!_radio) return;
		var radioViewField = _radio.getRadioViewField();
		if(!radioViewField) return;
		
		var label=document.createElement("<front></front>");
		label.innerText=record.getValue(radioViewField);
		return label;
	};
	
	function _createBr(){
		var br=document.createElement("<br></br>");
		return br;
	}
	
	function _newHiddenRadioBox(holder){
		_container=document.createElement("<div style='diplay:none;position:absolute;'></div>");
		_container.style.zIndex=1000;
		_container.style.textAlign="left";
		document.body.appendChild(_container);
		_container.radio=_radio.getId();
	};
	
	function _radioOnClick(){
		return false;
	};
	function _radioOnMouseUp(){
		var radio=event.srcElement;
		if(_container && _container.valueHolder){
			if(radio.checked){
				radio.checked=false;
				_container.valueHolder.value="";
				_clearRadioSelected();
			}else{
				radio.checked=true;
				_container.valueHolder.value=radio.value;
				_processRadioSelected(radio);
			}
		}
	};
	function _processRadioSelected(radio){
		var dataset = _radio.getTargetDataset();
		var valueField = _radio.getValueField();
		var viewField = _radio.getViewField();
		
		if(dataset && valueField && viewField){
			dataset.setValue(valueField,radio.value);
			dataset.setValue(viewField,radio.label);
		};
	};
	function _clearRadioSelected(){
		var dataset = _radio.getTargetDataset();
		var valueField = _radio.getValueField();
		var viewField = _radio.getViewField();
		
		if(dataset && valueField && viewField){
			dataset.setValue(valueField,"");
			dataset.setValue(viewField,"");
		};
	};
	function _selRadio(value){
		if(_container){
			for(var i=0;i<_container.children.length;i++){
				var child=_container.children[i];
				if(compareText(child.tagName,'input')){
					if(child.value==value){
						child.checked=true;
					}else{
						child.checked=false;
					}
				}
			}
		}
	};
	/*公共成员*/	
	this.getName = function(){
		return _name;
	};
	
	this.setName = function(name){
		_name = name;
	};
	
	this.isHidden = function(){
		return _hidden;
	};
	
	this.setContainer = function(container){
	    _container = container;
	    if(compareText(_container.parentElement.tagName.toLowerCase(),"td")){
	    	_container.valueHolder=_container.parentElement.children[0];
	    }
	    _container.radio=_radio.getId();
	};
	
	this.init = function(){
		if(typeof(_radio) == "undefined") return;		
		if(!_name || !_container) return;
		
		_container.innerHTML = '';//clear
		_container.contentEditable = false;//
		var record,dataset,cursor=1;
		dataset = _radio.getDataset();
		record = dataset.firstUnit;
		while(record){
			_container.appendChild(_creatRadio(record));
			_container.appendChild(_creatRadioLabel(record));
			if(!_hidden && _rowLen != 0 && cursor%_rowLen == 0){
				_container.appendChild(_createBr());
			};
			record = record.nextUnit;
			cursor++;
		};
		
		_initialized = true;//
	};
	
	this.showHiddenRadioBox = function(valueHolder){
		if(!valueHolder || !_radio || !_hidden) return;
		
		if(!_container){
			_newHiddenRadioBox();
		}
		if(_initialized == false){
			this.init();
		}
		
		_container.style.posLeft=valueHolder.style.posLeft;
		_container.style.posTop=valueHolder.style.posTop;
		_container.style.width=valueHolder.offsetWidth;
		_container.style.backgroundColor='white';
		_container.style.display="";
		_container.style.border="dimgray 1px solid";
		_container.valueHolder=valueHolder;
		if(_container.offsetHeight < _container.valueHolder.offsetHeight){
			_container.style.height = _container.valueHolder.offsetHeight;
		}
		
		RadioRender._dockRadioBox = _container;
		
		var dataset = getElementDataset(_container.valueHolder);
		var valueField = _radio.getValueField();
		if(dataset && valueField){
			var radioValue = dataset.getValue(valueField);
			_selRadio(radioValue);
		};
	};
	
	this.hide = function(){
		if(_container){
			_container.style.display = "none";
			RadioRender._dockRadioBox = undefined;
		}
	};
	
	this.selRadio = _selRadio;
	
	this.size = function(){
		if(_container && _container.valueHolder){
			_container.style.width = _container.valueHolder.offsetWidth;
			if(_container.offsetHeight < _container.valueHolder.offsetHeight){
				_container.style.height = _container.valueHolder.offsetHeight;
			}
			_container.style.posLeft = _container.valueHolder.style.posLeft;
			_container.style.posTop = _container.valueHolder.style.posTop;
		}
	};
};

//-------------RadioRender静态成员--------------------/
RadioRender._array_radio = new Array();
RadioRender.getRadioById = function(id){
	for(var i=0; i<this._array_radio.length; i++){
		if (this._array_radio[i].getId()==id) return this._array_radio[i];
	}
	var result;
	return result;
};
RadioRender._dockRadioBox = undefined;
RadioRender.getRadio = function(radio){
	var result;
	if(typeof(radio) == "object"){
		result = radio;
	}
	else if(typeof(radio) == "string"){
		result = this.getRadioById(radio);
	}
	
	return result;
};
RadioRender.getRadios = function(){
	return this._array_radio;
};
RadioRender.sizeRadioBox = function(editor){
	if(RadioRender._dockRadioBox){
		var radio = this.getRadio(RadioRender._dockRadioBox.radio);
		if(radio){
			radio.sizeRadioBox();
		}
	}
};