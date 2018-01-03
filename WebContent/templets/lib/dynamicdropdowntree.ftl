<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>
<html>
  <head>
    <title>dySelect</title>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
    <META HTTP-EQUIV="Cache-Control" CONTENT="must-revalidate">
    <META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=GBK">
    <META HTTP-EQUIV="Pragma" CONTENT="no-cache">
    <META HTTP-EQUIV="funccode:needlog" CONTENT="20200:false">
    <link rel="stylesheet" type="text/css" href="${contextPath}/templets/lib/themes/default/extra.css">
    <style>
    body {
        visibility: hidden;
    }
    </style>
    <script language="javascript" src="${contextPath}/templets/lib/rules.js"></script>
    <script language="javascript" src="${contextPath}/templets/lib/strings/chinese.js"></script>
    <script language="javascript" src="${contextPath}/templets/lib/basic.js"></script>
    <script language="javascript" src="${contextPath}/templets/lib/common.js"></script>
    <script language="javascript" src="${contextPath}/templets/lib/control.js"></script>
    <script language="javascript" src="${contextPath}/templets/lib/dataset.js"></script>
    <script language="javascript" src="${contextPath}/templets/lib/editor.js"></script>
    <script language="javascript" src="${contextPath}/templets/lib/table.js"></script>
    <script language="javascript" src="${contextPath}/templets/lib/dropdown.js"></script>
    <script language="javascript" src="${contextPath}/templets/lib/tree.js"></script>
    <script language="javascript" src="${contextPath}/templets/lib/menu.js"></script>
    <script language="javascript" src="${contextPath}/templets/lib/pagepilot.js"></script>
    <script language="javascript" src="${contextPath}/templets/lib/salert.js"></script>
    <script language="javascript" src="${contextPath}/templets/lib/bank.js"></script>
    <script type='text/javascript' src='${contextPath}/dwr/engine.js'> </script>
    <script type='text/javascript' src='${contextPath}/dwr/util.js'> </script>
    <script language="javascript" src="${contextPath}/templets/lib/dwr.js"></script>
    <script type='text/javascript' src='${contextPath}/dwr/interface/CommonQueryResultProcess.js'> </script>
    <script type='text/javascript' src='${contextPath}/dwr/interface/CommonQueryUpdateProcess.js'> </script>
    <script type='text/javascript' src='${contextPath}/dwr/interface/UploadMonitor.js'> </script>
    <script language="javascript">
      //屏蔽鼠标右键
      //document.oncontextmenu=new function(){event.returnValue=false;return false;}
    </script>
    <script language="javascript">
<!--
var _extra_library="${contextPath}/templets/lib";
var _theme_root="${contextPath}/templets/lib/themes/default";
var _application_root="${contextPath}";
var _successURL = "${contextPath}"+"/success.jsp";
var _defaultSubmitUrl=getDecodeStr("~2fextraservice~2fupdate");
var _dynamicDropDownUrl="/dynamicdropdown.ftl";
var _dynamicDropDownTreeUrl="/dynamicdropdowntree.ftl";
var _checkBrowser=true;
var _disableSystemContextMenu=false;
var _processEnterAsTab=true;
var _enableClientDebug=false;
var _supportsDatasetMultiSelect=true;
var _paramMap = new Map();//用于设置默认值
//-->
</script>
  </head>
<#--
<@CommonQueryMacro.page title="dySelect" body="false">
-->
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
          <td nowrap>&nbsp;值:&nbsp;<input extra="editor" type="text" id="editor_value" value="" size="16">&nbsp;</td>
          <td align="left" width="100%">
            <button extra=button id=buttonRequery onclick="return buttonRequery_onclick();">筛选</button>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</#if>
  <tr>
    <td bgcolor="#e0e0e0">
    <#--
      <table border="0" width="100%"  cellspacing="0" cellpadding="0" style="border-right: gray 1px solid; font-size: 9pt; border-left: gray 1px solid; border-top: gray 1px solid; border-collapse: collapse">
        <tr>
          <td>
            <table extra="datapilot" id="pilotDropDown" dataset="datasetDropDownTree" buttons="movefirst,prevpage,nextpage,movelast" confirmDelete="true" confirmCancel="true" cellspacing="1" cellpadding="0"></table>

          </td>
          <td align=right nowrap>
            <label id=label_pageno></label>
          </td>
        </tr>
      </table>
      -->
      <table extra="tree" id="dytree" dataField="name" supportRightClick="true" width="100%" height="100%" bordercolor="silver" cellspacing="0" cellpadding="2" rules="all" onkeydown="return _tree_onkeydown(this)">
        <tbody><tr height="16px" onmousedown="return _tree_onmousedown(this)">
            <td extra="treenode" nowrap></td>
              </tr>
        </tbody>
      </table>
    </td>
  </tr>
  <#--
  <tr>
    <td>
      <table extra="datatable" skipRebuild="true" id="tableDropDown" dataset="datasetDropDownTree" readOnly="true" editable="true" confirmDelete="true" confirmCancel="true" maxRow="${DropDownCommonQueryConfig.getAnyValue("pagesize")?default("10")}" width="100%"  height="100%" bordercolor="silver" cellspacing="0" cellpadding="2" rules="all">
        <tbody><tr height="20px">
            <td name="${RequestParameters["viewField"]}" dataType="string" editorType="" >
        </td>
      </tr>
      </tbody>
      </table>
    </td>
  </tr>
  -->
</table>
</div>
</body>
<@DropDownDataSet/>
<#--
</@CommonQueryMacro.page>
-->
</html>
<script language="javascript">
<#--
    //日期类型控件,统一处理
    var dropDownDate=createDropDown("dropDownDate");var _t=dropDownDate;_t.type="date";
    _t.cache=true;_t.fixed=false;_t.fieldMap="";_t.autoDropDown=true;_t.editable=false;
    _t.height=0;_t.tag="";_array_dropdown[_array_dropdown.length]=_t;initDropDown(_t);
    var PageState=_createPageState();
    PageState.version=-1;
    function _setElementsProperties(){};
    //initDocument();
//-->
</script>

<script language="javascript">
initDropDownBox("dynamic");
var selectedNode = null;
var emptyRecord = null;

function window_onload(){
  datasetDropDownTree_afterScroll(datasetDropDownTree);
}

dytree.onclick=function(){
    //_dropdown_onclick();
}

function dytree_beforeExpandNode(tree, node) {
  if (isTrue(node.finished)){
    return;
  }
  else{
     var dataset = datasetDropDownTree;
     var v_node_record = node.tag;
     if(!v_node_record){
        if(!isTrue(dataset.require)){
            var newNode = tree.addNode(node);
            newNode.label = "";
            newNode.finished = true;
            newNode.hasChild = false;
            newNode.canSelected = true;
        }
        dataset.setParameter("_level_",0);
        dataset.flushData(0);
     }else{
        for(var i=0; i < v_node_record.fields.fieldCount; i++){
            fId  = v_node_record.fields[i].fieldName;
            fVal = v_node_record.getString(i);
            dataset.setParameter(fId,fVal);
        }
        dataset.setParameter("_level_",node.level);
        dataset.disableControls();
        dataset.clearData();
        dataset.flushData(0);
     }
     dataset.moveFirst();
     while (!dataset.eof){
        var newNode = tree.addNode(node);
        newNode.tag = dataset.record;
        newNode.label = dataset.getString("${RequestParameters["viewField"]}");
        newNode.icon = _theme_root + "/folder.gif";
        newNode.expandedIcon = _theme_root + "/exfolder.gif";
        var v_hasChild_ = dataset.getValue("_hasChild_");
        if(isTrue(v_hasChild_)){
            newNode.hasChild = true;
        }else{
        	newNode.icon = _theme_root + "/leaf.gif";
            newNode.hasChild = false;
        }
        var v_canSelected_ = dataset.getValue("_canSelected_");
        if(isTrue(v_canSelected_)){
            //newNode.icon = _theme_root + "/leaf.gif";
            newNode.canSelected = true;
        }else{
            newNode.canSelected = false;
        }
        dataset.moveNext();
    }
    node.finished = true;
  }
}
function dytree_afterNodeChanged(tree,node){
    var dataset = datasetDropDownTree;
    if(!node.canSelected){
        return -1;
    }else{
        //dataset.disableControls();
        //dataset.clearData();
        //pArray_insert(dataset, "end", dataset.record, node.tag);
        if(!node.tag){
            if(emptyRecord==null){
                dataset.insertRecord("end");
                emptyRecord = dataset.record;
            }else{
                dataset.setRecord(emptyRecord);
            }
        }else{
            dataset.setRecord(node.tag);
        }
        _dropdown_onclick();

    }

}

function dytree_onInitTreeNode(tree,node){
    return;
}


function datasetDropDownTree_afterScroll(dataset){
  if (typeof(label_pageno)=="object" && dataset.record){
    label_pageno.innerHTML = "&nbsp" + dataset.record.pageIndex + "/" + dataset.pageCount + "&nbsp";
  }
}

function buttonRequery_onclick(){
  with (datasetDropDownTree) {
    setParameter("value", "%" + editor_value.value + "%");
    flushData(pageIndex);
    moveFirst();
  }
}
</script>



<#macro DropDownDataSet>
<script language="javascript">
<!--
    var datasetDropDownTree=createDataset("datasetDropDownTree","","");
    datasetDropDownTree.flushData=dataset_flushData_new;
    var _t=datasetDropDownTree,_f;
    _t.readOnly=true;
    _t.pageSize=${DropDownCommonQueryConfig.getAnyValue("pagesize")?default("10")};
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

