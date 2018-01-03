<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>
<@CommonQueryMacro.page title="dySelect" body="false">
<#assign DropDownCommonQueryConfig = statics["com.huateng.commquery.config.CommonQueryUtil"].getCommonQueryBean(RequestParameters["CQId"])>
<body language="javascript" onload="return window_onload()">
<div id="_dropdown_div">
<table width="100%" cellspacing="0" cellpadding="0"  height="100%">
<#assign flag = DropDownCommonQueryConfig.getParameters().containsKey("value")>
<#if flag>
  <tr>
    <td bgcolor="#e0e0e0">
      <table border="0" width="100%" cellspacing="0" cellpadding="1" style="border-right: gray 1px solid; font-size: 9pt; border-left: gray 1px solid; border-top: gray 1px solid; border-collapse: collapse">
        <tr>
          <td nowrap>&nbsp;Öµ:&nbsp;<input extra="editor" type="text" id="editor_value" value="" size="16">&nbsp;</td>
          <td align="left" width="100%">
            <button extra=button id=buttonRequery onclick="return buttonRequery_onclick();">É¸Ñ¡</button>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</#if>
  <tr>
    <td bgcolor="#e0e0e0">
      <table border="0" width="100%"  cellspacing="0" cellpadding="0" style="border-right: gray 1px solid; font-size: 9pt; border-left: gray 1px solid; border-top: gray 1px solid; border-collapse: collapse">
        <tr>
          <td>
            <table extra="datapilot" id="pilotDropDown" dataset="datasetDropDown" buttons="movefirst,prevpage,nextpage,movelast" confirmDelete="true" confirmCancel="true" cellspacing="1" cellpadding="0"></table>
          </td>
		  <td align="left">
            <button extra=button onclick="dropDownClear();clearEditorValue();">Çå¿Õ</button>
          </td>
          <td align=right nowrap>
            <label id=label_pageno></label>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td>
      <table extra="datatable" skipRebuild="true" isDropDownTable="true" id="tableDropDown" dataset="datasetDropDown" readOnly="true" editable="true" confirmDelete="true" confirmCancel="true" maxRow="${DropDownCommonQueryConfig.getAnyValue("pagesize")?default("10")}" width="100%"  height="100%" bordercolor="silver" cellspacing="0" cellpadding="2" rules="all">
        <tbody><tr height="20px">
            <td name="${RequestParameters["viewField"]}" dataType="string" editorType="" >
        </td>
      </tr>
      </tbody>
      </table>
    </td>
  </tr>
</table>
</div>
</body>
<@DropDownDataSet/>

</@CommonQueryMacro.page>
<script language="JavaScript" for="window" event="onload">
<#if flag>
	editor_value.focus();
	editor_value.onkeydown = function() {
		if(13 == window.event.keyCode) {
			buttonRequery_onclick();
			buttonRequery.focus();
			//event.returnValue = false;
		}
	}
</#if>
</script>
<script language="javascript">
initDropDownBox("dynamic");
<#if flag == false>
  datasetDropDown.flushData(1);
</#if>
count = calcPageCount(datasetDropDown.length,datasetDropDown.pageSize);
    for(i=1;i<=count;i++){
        datasetDropDown.loadedPage[i-1] = true;
    }

function window_onload(){
  datasetDropDown_afterScroll(datasetDropDown);
}

function datasetDropDown_afterScroll(dataset){
  if (typeof(label_pageno)=="object" && dataset.record){
    label_pageno.innerHTML = "&nbsp" + dataset.record.pageIndex + "/" + dataset.pageCount + "&nbsp";
  }
  else {
  	label_pageno.innerHTML = "&nbsp1/1&nbsp";
  }
}

function buttonRequery_onclick(){
  with (datasetDropDown) {
    var filterValue = editor_value.value;
	if("" != filterValue) {
		filterValue = filterValue.trim();
		filterValue = filterValue.replace(/\s+/g, "%");
	}
    setParameter("value", "%" + filterValue + "%");
    flushData(pageIndex);
    count = calcPageCount(datasetDropDown.length,datasetDropDown.pageSize);
    for(i=1;i<=count;i++){
        datasetDropDown.loadedPage[i-1] = true;
    }
    moveFirst();
  }
}

function clearEditorValue() {
  <#if flag>
    editor_value.value = "";
  </#if>
}
</script>



<#macro DropDownDataSet>
<script language="javascript">
<!--
    var datasetDropDown=createDataset("datasetDropDown","","");
    datasetDropDown.flushData=dataset_flushData_new;
    var _t=datasetDropDown,_f;
    _t.readOnly=true;
    _t.pageSize=${DropDownCommonQueryConfig.getAnyValue("pagesize")?default("10")};
    _t.async="${DropDownCommonQueryConfig.getAnyValue("async")?default("true")}";
    _t.masterDataset="";
    _t.references="null";
    _t.submitData="allchange";
    _t.autoLoadPage=true;
    _t.autoLoadDetail=true;
    _t.downloadUrl=getDecodeStr("~2fextraservice~2fdownloaddata");
    _t.insertOnEmpty=false;
    _t.tag="";
    _t.type="dropdown";
    _t.sessionKey="dd";
    <#assign paramStr = RequestParameters["paramstr"]>
    converStr2DataSetParameter( "${paramStr}", _t );
    _t.pageIndex=_t.getParameter("nextPage");
    _t.pageCount=1;
    _t.init = "${RequestParameters["init"]}";
    _t.require = "${RequestParameters["require"]}";

    <#assign fieldMap = DropDownCommonQueryConfig.fields>
    <#assign fields = fieldMap.keySet()>
    <#assign field = "">
    <#assign fDesc = "",fVal = "",fStat = "">
    <#assign columnInx = 0>
    <#list fields as fId>
            _f=_t.addField("${fId}","string"); _f.label="${fId}";
            _f.size=0;_f.scale=0; _f.readOnly=true;
            _f.required=false; _f.nullable=true; _f.defaultValue=getDecodeStr("");
            _f.updatable=true; _f.valueProtected=false; _f.visible=true; _f.autoGenId=false;
            _f.tableName=""; _f.fieldName="${fId}"; _f.tag=""; _f.editorType=""; _f.dropDown="";
            _f.mask= "";_f.maskErrorMessage=""; _f.toolTip="";_f.lobDownloadURL=getDecodeStr("");
            _f.lobPopupURL=getDecodeStr("");
    </#list>
    initDataset(_t);
//-->
</script>
</#macro>
