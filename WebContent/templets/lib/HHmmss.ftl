<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>

<#-- session id -->
<#assign sessionkey = RequestParameters["sessionkey"]>
<#-- dropdown viewField -->
<#assign viewField = RequestParameters["viewField"]>
<#-- dropdown fieldmap -->
<#assign fieldMapStr = RequestParameters["fieldMapStr"]>
<#-- dropdown dataset parameter -->
<#assign paramStr = RequestParameters["paramStr"]>
<#-- dropdown target dataset field value -->
<#assign targetFieldStr = RequestParameters["targetFieldStr"]>
<#-- dropdown fieldId -->
<#assign fieldId = RequestParameters["fieldId"]>

<@CommonQueryMacro.page title="tmSelect" body="false">
<div id="_dropdown_div" align=left>
	<TABLE id="CalendarTable" class="calendar" align=center width=100% cellspacing=0 cellpadding=1 rule=all>
	<TR class="title" valign=top>
		<TD align=center>
		<TABLE WIDTH=100% CELLSPACING=1 CELLPADDING=0>
			<TR>
				<TD width=1><select id="_calender_hh" style="width: 40px"></select></TD>
				<TD width=1><select id="_calender_mm" style="width: 40px"></select></TD>
				<TD width=1><select id="_calender_ss" style="width: 40px"></select></TD>
			</TR>
		</TABLE>
		</TD>
	</TR>
	<TR class="footer">
		<TD align=center>
			<TABLE><TR><TD>
			<INPUT extra=button type=button id="button_now" value="" onclick="_new_calendar_now_onclick()">
			</TD><TD>
			<INPUT extra=button type=button id="selectBtn" value="确定" onclick="_selectCalenderTime()">
			</TD><TD>
			<INPUT extra=button type=button id="clearBtn" value="清除" onclick="_clearCalenderTime()">
			</TD></TR></TABLE>
		</TD>
	</TR>
</TABLE>
</div>
<script language="javascript">

var _sessionkey = "${sessionkey}";
var _fieldMap = "${fieldMapStr}";
var _paramMap = converStr2Map("${paramStr}");
var _targetFieldStrMap = converStr2Map("${targetFieldStr}");
var _fieldId = "${fieldId}";

init();

function init(){
    _newTimeHHmmss = new timeHHmmss(_fieldId, "000000");
    var _hhSelect = document.getElementById("_calender_hh");
    for(var i = 0; i < 24; i ++) {
        var value = padZero(i, 2);
        _hhSelect.add(new Option(value, value));
    }
    var _mmSelect = document.getElementById("_calender_mm");
    var _ssSelect = document.getElementById("_calender_ss");
    for(var i = 0; i < 60; i ++) {
        var value = padZero(i, 2);
        _mmSelect.add(new Option(value, value));
        _ssSelect.add(new Option(value, value));
    }
    initElements(CalendarTable);
    document.getElementById("button_now").value= "现在";
    initDropDownBox("custom");
    initHHmmssDataset();
    initValue();
}

function initValue() {
    var fieldVal = _targetFieldStrMap[_fieldId];
    if(typeof(fieldVal)!="undefined" && fieldVal.length==6 && !isNumber(fieldVal)){
        newChangeTime(parseInt(fieldVal.substr(0,2)),parseInt(fieldVal.substr(2,2)),parseInt(fieldVal.substr(4,2)));
    }
    else {
        _new_calendar_now_onclick();
    }
}

function _new_calendar_now_onclick() {
    var _now = new Date();
    newChangeTime(_now.getHours(), _now.getMinutes(), _now.getSeconds());
}

function newChangeTime(hh, mm, ss) {
    if(!hh || "" == hh) hh = 0;
    if(!mm || "" == mm) mm = 0;
    if(!ss || "" == ss) ss = 0;
    _newTimeHHmmss.HH = hh;
    _newTimeHHmmss.mm = mm;
    _newTimeHHmmss.ss = ss;
    _new_setCalendarActiveCell();
}

function initHHmmssDataset() {
    _HHmmssds = createDataset("HHmmssds","HHmmss","");
    initDataset(_HHmmssds);
    _HHmmssds.insertRecord("end");
}
	
function timeHHmmss(ID, value) {
    this.HH = value.substr(0, 2);
    this.mm = value.substr(2, 2);
    this.ss = value.substr(4, 2);
}

function dropDown_onGetRecord(){
    return _HHmmssds.getFirstRecord();
}

function padZero(num, length) {
    num = String(num);
    length = parseInt(length) || 2;
    while (num.length < length)
        num = "0" + num;
    return num;
}

function _new_setCalendarActiveCell(){
    if(_newTimeHHmmss.HH < 10) {
        _calender_hh.value=padZero(_newTimeHHmmss.HH, 2);
    }
    else {
    	_calender_hh.value=_newTimeHHmmss.HH;
    }
    if(_newTimeHHmmss.mm < 10) {
    	_calender_mm.value=padZero(_newTimeHHmmss.mm, 2);
    }
    else {
    	_calender_mm.value=_newTimeHHmmss.mm;
    }
    if(_newTimeHHmmss.ss < 10) {
    	_calender_ss.value=padZero(_newTimeHHmmss.ss, 2);
    }
    else {
    	_calender_ss.value=_newTimeHHmmss.ss;
    }
}

function _selectCalenderTime(){
    var _HH = _calender_hh.value;
    var _mm = _calender_mm.value;
    var _ss = _calender_ss.value;
    if(_HH < 0 || _HH > 23) {
    	_HH = 0;
    	_calender_hh.value = _HH;
    }
    if(_mm < 0 || _mm > 59) {
    	_mm = 0;
    	_calender_mm.value = _mm;
    }
    if(_ss < 0 || _ss > 59) {
    	_ss = 0;
    	_calender_ss.value = _ss;
    }
    _HH = padZero(_calender_hh.value, 2);
    _mm = padZero(_calender_mm.value, 2);
    _ss = padZero(_calender_ss.value, 2);
    
    _HHmmssds.setValue("HHmmss", _HH + _mm + _ss);
    _dropdown_onclick();
}

function _clearCalenderTime() {
    _HHmmssds.setValue("HHmmss", "");
    _dropdown_onclick();
}
</script>
</@CommonQueryMacro.page>