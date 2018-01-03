<#import "/templets/commonQuery/BankCommonQueryEditType.ftl" as htmlEditType>

<#--global value-->
<#global contextPath = contextPath>
<#---->

<#--ҳ��궨��-->
<#--
�����ж�_mpf_���ж��Ƿ�Ϊ��ҳ�棬����ҳ��Ƭ��
id    ҳ��ID(��ҳ��Ϊmaster,ҳ��Ƭ�ε�ID
title ҳ�����(������Ϊ��)������ҳ��Ƭ��Ϊ�ⲿ��ı���
body  �Ƿ���ʾbody(����Ϊ�գ�Ĭ��Ϊ"true")������ҳ��Ƭ�ξ����ⲿ���Ƿ�Ҫ��ʾ
-->
<#macro page  title id="master" body="true">
    <#assign _mpf_ = RequestParameters["_mpf_"]?default("true")>
    <#if _mpf_ == "true">
        <@pageMaster title=title body=body>
            <#nested>
        </@pageMaster>
    <#else>
        <@pagelet id=id title=title body=body>
            <#nested>
        </@pagelet>
    </#if>
    <script>
	    document.body.onkeydown = function() {
            if(event.srcElement.type != "textarea" && event.srcElement.type != "text") {
                if(window.event.keyCode == 8) {
                    event.returnValue = false;
                }
            } else {
                if (event.srcElement.contentEditable == 'false') {
                    event.returnValue=false;
                }
            }
        };
		//window.history.forward(1);
	</script>
</#macro>

<#--��ҳ��궨��-->
<#--
title   ҳ�����(������Ϊ��)
body    �Ƿ���ʾbody(����Ϊ�գ�Ĭ��Ϊ"true")
-->
<#macro pageMaster title body="true">
<#assign globalinfo = statics["com.huateng.ebank.common.GlobalInfo"].getCurrentInstanceWithoutException()>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>${title}</title>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
    <META HTTP-EQUIV="Cache-Control" CONTENT="must-revalidate">
    <META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=GBK">
    <META HTTP-EQUIV="Pragma" CONTENT="no-cache">
    <link rel="stylesheet" type="text/css" href="${contextPath}/templets/lib/themes/default/extra.css">
    <style>
    body {
        visibility: hidden;
    }
    </style>
    <script language="javascript" src="${contextPath}/templets/lib/rules.js"></script>
    <#assign language=globalinfo.getLanguage()?default('')>
    <#if language=="_en_US">
        <script language="javascript" src="${contextPath}/templets/lib/strings/english.js"></script>
    <#else>
        <script language="javascript" src="${contextPath}/templets/lib/strings/chinese.js"></script>
    </#if>
    <script language="javascript" src="${contextPath}/js/richAlert.js"></script>
    <script language="javascript" src="${contextPath}/templets/lib/properties.js"></script>
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
    <script language="javascript" src="${contextPath}/js/extCommon.js"></script>

    <script language="javascript">
      //��������Ҽ�
      //document.oncontextmenu=new function(){event.returnValue=false;return false;}
    </script>
    <script language="javascript">
<!--
var _extra_library="${contextPath}/templets/lib";
var _theme_root="${contextPath}/templets/lib/themes/default";
var _application_root="${contextPath}";
var _successURL = "${contextPath}"+"/success.ftl";
var _defaultSubmitUrl=getDecodeStr("~2fextraservice~2fupdate");
var _dynamicDropDownUrl="/dynamicdropdown.ftl";
var _dynamicDropDownTreeUrl="/dynamicdropdowntree.ftl";
var _checkBrowser=true;
var _disableSystemContextMenu=false;
var _processEnterAsTab=true;
var _enableClientDebug=false;
var _supportsDatasetMultiSelect=true;
var _paramMap = new Map();//��������Ĭ��ֵ
var _window_skin = "standard";//window��Ƥ��
//-->


<#if globalinfo?exists>
var _today_date = new Date("${globalinfo.getTxdate()?string("yyyy/MM/dd")}");//��ǰ�������
<#else>
var _today_date = new Date();//��ǰ�������
</#if>
</script>
  </head>
  <#if body=="true">
  <body bgcolor="#FFFFFF" height="100%">
    <#nested>
  </body>
  <#else>
    <#nested>
  </#if>
</html>
<script language="javascript">
    //�������Ϳؼ�,ͳһ����
    var dropDownDate=createDropDown("dropDownDate");var _t=dropDownDate;_t.type="date";
    _t.cache=true;_t.fixed=false;_t.fieldMap="";_t.autoDropDown=true;_t.editable=false;
    _t.height=0;_t.tag="";_array_dropdown[_array_dropdown.length]=_t;initDropDown(_t);
    var PageState=_createPageState();
    PageState.version=-1;
    function _setElementsProperties(){};
    initDocument();
    document.onreadystatechange = function() {
        if(document.readyState=="complete"){
            initCallGetter();
        }
    };
</script>
</#macro>

<#macro pagelet id title body="true">
<#--
<#if body == "true">
<FIELDSET name='${title}' style="padding: 6px;">
    <LEGEND>${title}</LEGEND>
    <#nested>
</FIELDSET>
<#else>
    <#nested>
</#if>
-->
<#nested>
<script language="javascript">
    function documentlet_afterInit(){
        initLetCallGetter("${id}");
    }
</script>
</#macro>

<#--ͨ�ò�ѯͷģ��-->
<#--
id                      ͨ�ò�ѯid��(������Ϊ��)
init                    �Ƿ��ʼ����־(����Ϊ�գ�Ĭ��Ϊ"false"           "true"-��ʼ��;"false"-����ʼ��)
navigate��                ���������Ƿ���ʾ��־(����Ϊ�գ�Ĭ��Ϊ"true"        "true"-��ʾ;"false"-����ʾ)
mode��                     ģʽ��־(����Ϊ�գ�Ĭ��Ϊ"0"                     0-���ɽ��Dataset,1-�����ɽ��Dataset)
submitMode              �����ύ��ʽ(����Ϊ��,Ĭ��Ϊ"allchange"          all-ȫ���ύ,allchange-�ύ�޸Ĳ���,current-�ύ��ǰ�ļ�¼, selected - ֻ�ύ���б�ѡ��ļ�¼ )
parameters              �����ύ����(����Ϊ��,                          ��ʽΪ paramNm=paramVal;...)
readOnly                ֻ����־(����Ϊ�ڿ�,Ĭ��Ϊ"false"                "true"-ֻ��;"false"-��ֻ��)
masterDataset           ��dataset����(����Ϊ��,Ĭ��û����dataset)
references              ����ֶ�(����Ϊ��,                             ֻ����������maseterDataset���������Ч����ʾ����dataset��ص��ֶ�     ��ʽΪsFieldName=mFieldName1;....)
show                    �Ƿ���ʾ�м�����(����Ϊ��,Ĭ��Ϊ"true"            "true"-��ʾ;"false"-����ʾ)
insertOnEmpty           �����ݼ�ʱ�Ƿ����ռ�¼(����Ϊ��,Ĭ��Ϊ"false"    "true"-����ռ�¼;"false"-������ռ�¼)
-->
<#macro CommonQuery id init="false" navigate="true" mode="0" submitMode="allchange" parameters="" readOnly="false" masterDataset="" references="" show="true" insertOnEmpty="false">
<#assign CommonQueryConfig = statics["com.huateng.commquery.config.CommonQueryUtil"].getCommonQueryBean(id)>
<#assign type = CommonQueryConfig.getAnyValueDefault("type","call")>
<script language="javascript">
var _transDataActionURL = "${CommonQueryConfig.getAnyValue("transdataactionurl")}";
var _common_query_id = "${CommonQueryConfig.getId()}";
</script>
${CommonQueryConfig.script?default('')}

<#-- shen_antonio 20080118-->
<#if show=="false">
    <@Dataset init=init submitMode=submitMode parameters=parameters readOnly=readOnly masterDataset=masterDataset references=references insertOnEmpty=insertOnEmpty/>
    <#return>
</#if>
<#-- -->

<table id="_mainWorkTable" align="left" width="100%" style="word-break:break-all">
<#if navigate == "true">
<tr>
        <td><div id='navigate'
            <@Navigate/></div>
        </td>
    </tr>
    <tr>
        <td width="100%">
            <hr />
        </td>
	</tr>
</#if>
    <tr>
        <td>
            <#nested>
        </td>
    </tr>
</table>
<#if mode!="1">
    <@Dataset init=init submitMode=submitMode parameters=parameters readOnly=readOnly masterDataset=masterDataset references=references insertOnEmpty=insertOnEmpty/>
</#if>
<#-- yjw add  , fill the dataset -->
<#if mode=="0" && init=="false" &&  type!="form">
<#if CommonQueryResult?exists && CommonQueryResult.getCqId()==id>
<script language="javascript">
function ${id}_dataset_requestInit(){
    var _tmp = getDatasetByID("${id}_dataset");
    _tmp.disableControls();
    appendFromDataString(_tmp, "${CommonQueryConfig.toFieldString()}","${CommonQueryResult.getResultOprStr()}", true);
    _tmp.pageIndex=${CommonQueryResult.getPage().getCurrentPage()};
    _tmp.pageCount=${CommonQueryResult.getPage().getTotalPage()};
    /** set param .*/
    //converStr2DataSetParameter("${CommonQueryResult.getParamStr()}",_tmp);
    _tmp.enableControls();
    _tmp.refreshControls();
    _tmp.loadDetail();
}
</script>
</#if>
</#if>
</#macro>

<#macro CommonQueryForRequest id=CQId navigate="true" submitMode="allchange" parameters="" show="true">
<@CommonQuery id=id init="false" navigate=navigate mode="0" submitMode=submitMode parameters=parameters show=show>
        <#nested>
</@CommonQuery>
<#--
<script language="javascript">
    var _tmp = getDatasetByID("${CQId}_dataset");
    _tmp.disableControls();
    appendFromDataString(_tmp, "${CommonQueryConfig.toFieldString()}","${CommonQueryResult.getResultOprStr()}", true);
    _tmp.pageIndex=${CommonQueryResult.getPage().getCurrentPage()};
    _tmp.pageCount=${CommonQueryResult.getPage().getTotalPage()};
    /** set param .*/
    //converStr2DataSetParameter("${CommonQueryResult.getParamStr()}",_tmp);
    /* shen_antonio .*/
    _tmp.enableControls();
    _tmp.refreshControls();
    _tmp.loadDetail();
</script>
-->
</#macro>

<#macro Navigate>
    ${CommonQueryConfig.getAnyValueDefault("navigate","")}
</#macro>

<#--ͨ��Tabsģ��-->
<#--
    id                            ͨ�ò�ѯCQID(������Ϊ��)
    currentTab                    ��ǰtab��CQID(������Ϊ��)
    navigate                      ��ʾ��������־(����Ϊ�գ�Ĭ��Ϊ"true"        "true" ��ʾ;"false" ����ʾ)
    currentUrl                    ��ǰtab����URL(����Ϊ�գ�Ĭ��Ϊbutton��Ӧurl)
    width                         ���(����Ϊ��,Ĭ��Ϊ"100%")
    height                        �߶�(����Ϊ�գ�Ĭ��Ϊ"100%")
-->
<#macro CommonQueryTab id currentTab navigate="true" currentUrl="" width="100%" height="100%" >
<script language="javascript">
v_currentTab = "${currentTab}";
_init_flag = false;
</script>
<#assign CommonQueryConfig = statics["com.huateng.commquery.config.CommonQueryUtil"].getCommonQueryBean(id)>
${CommonQueryConfig.script?default('')}
<#--
<@Dataset init=init/>
-->
<#assign tabs ="" >
<#assign befDivString = "">
<#assign aftDivString = "">
<#assign operationsMap = CommonQueryConfig.getOperations()>
<#assign operationsKeys = operationsMap.keySet()>
<#assign befFlag=true>
<#assign tabsCtl="tabsCtl">
<#assign _tabsCtl = RequestParameters[tabsCtl]?default('')>
<#assign _show = true>

<#list operationsKeys as key>
    <#assign button = operationsMap.get(key)>
    <#assign desc = button.attributes["desc"]?default("")>
    <#assign tid = button.attributes["id"]?default("")>
    <#assign url  = contextPath + button.attributes["url"]?default("")>
    <#if currentUrl!="">
        <#assign url = currentUrl>
    </#if>
    <#-- �ж��Ƿ���ʾ�� tab  -->
    <#if _tabsCtl!="" && (_tabsCtl?length >= (key_index+1)) >
        <#if _tabsCtl?substring(key_index,key_index+1)=="1">
            <#assign _show = true>
        <#else>
            <#assign _show = false>
        </#if>
    </#if>

    <#if tid!=currentTab && _show==true>
        <#assign tabs = tabs + desc + "," + tid + "," + url + ";">
        <#if befFlag>
            <#assign befDivString = befDivString + "<div id=\"${id}_tabset_${tid}\"></div>">
        <#else>
            <#assign aftDivString = aftDivString + "<div id=\"${id}_tabset_${tid}\"></div>">
        </#if>
    <#elseif _show==true>
        <#assign befFlag=false>
        <#assign tabs = tabs + desc + "," + tid + "," + "" + ";">
    </#if>
</#list>
    <#if navigate == "true">
        <table  width="100%"><tr><td><@Navigate/><hr></td></tr></table>
    </#if>
    <table id="_tabsetpane_${id}_tabset" align="left" cellspacing="0" cellpadding="0" width="${width}" >
      <tr height="1">
        <td>
        <#--
          <div id="_tabdiv_${id}_tabset">
            <div id="_tabpane_${id}_tabset" style="width:100%; ">-->
              <table extra="tabset" cellspacing="0" cellpadding="0" width="100%" id="${id}_tabset" tabPlacement="top" targetFrame="_self" tabs=${tabs}></table>
            <#--</div>
          </div>-->
        </td>
      </tr>
      <tr>
        <td>
          <div id="_body_${id}_tabset" style="width:100%; height:100%; overflow-y:auto; border-left:0 gray solid; border-right:0 gray solid; border-bottom:0 gray solid">
          </div>
        </td>
      </tr>
    </table>
    ${befDivString}
    <div id="${id}_tabset_${currentTab}">
        <#nested>
    </div>
    ${aftDivString}
    <#assign tabid = RequestParameters["tabid"]?default('')>
    ${CommonQueryConfig.script?default('')}
</#macro>

<#--����DataSetģ��-->
<#--
init                      ��ʼ����־(����Ϊ��,Ĭ��Ϊ"true"                "true"-��ʼ��;"false"-����ʼ��)
submitMode                �����ύ��ʽ(����Ϊ��,Ĭ��Ϊ"allchange"          all-ȫ���ύ,allchange-�ύ�޸Ĳ���,current-�ύ��ǰ�ļ�¼, selected - ֻ�ύ���б�ѡ��ļ�¼ )
parameters                �����ύ����(����Ϊ��,                          ��ʽΪ paramNm=paramVal;...)
readOnly                  ֻ����־(����Ϊ�ڿ�,Ĭ��Ϊ"false"                "true"-ֻ��;"false"-��ֻ��)
masterDataset             ��dataset����(����Ϊ��,Ĭ��û����dataset)
references                ����ֶ�(����Ϊ��,                             ֻ����������maseterDataset���������Ч����ʾ����dataset��ص��ֶ�     ��ʽΪsFieldName=mFieldName1;....)
insertOnEmpty             �����ݼ�ʱ�Ƿ����ռ�¼(����Ϊ��,Ĭ��Ϊ"false"    "true"-����ռ�¼;"false"-������ռ�¼)
-->
<#macro Dataset init="true" submitMode="allchange" parameters="" readOnly="false" masterDataset="" references="" insertOnEmpty="false">
<#assign specStr="nextPage|everyPage|pageNm|currentPage|fieldString|recordString|recordOrigString">
<#--init request param -->
<#assign paramString = paramConver()>


<script language="javascript">
    var ${CommonQueryConfig.getId()}_dataset=createDataset("${CommonQueryConfig.getId()}_dataset","","");
    ${CommonQueryConfig.getId()}_dataset.flushData=dataset_flushData_new;
    var _t=${CommonQueryConfig.getId()}_dataset,_f;
    _t.readOnly=false;
    _t.cqId = "${CommonQueryConfig.getId()}";
    _t.async="${CommonQueryConfig.getAnyValue("async")?default("true")}";
    _t.pageSize=${CommonQueryConfig.getAnyValue("pagesize")?default("10")};
    _t.databusId="${CommonQueryConfig.getAnyValue("databusid")?default("")}";
    _t.pageIndex=1;
    _t.pageCount=1;
    _t.masterDataset="${masterDataset}";
    _t.references="${references}";
    _t.submitData="${submitMode}";
    _t.autoLoadPage=false;
    _t.autoLoadDetail=true;
    _t.downloadUrl=getDecodeStr("~2fextraservice~2fdownloaddata");
    _t.insertOnEmpty=${insertOnEmpty};
    _t.tag="";
    _t.type="result";
    _t.sessionKey="dd";
    converStr2DataSetParameter("${paramString}",_t);
    _t.setParameter("CQId","${CommonQueryConfig.getId()}","string");
    _t.setParameter("nextPage",_t.pageIndex);
    _t.setParameter("everyPage",_t.pageSize);
    _t.setParameter("_session_key",_t.sessionKey);
    _t.setParameter("databusId",_t.databusId);
    _t.init = "${init}";
    _t.initDocumentFlag = false;
    <#assign parameters = "${parameters}">
        <#if parameters!="">
            <#list parameters?split(";")  as key>
                 <#assign index =key?index_of("=")>
                 <#assign paraName =key?substring(0,index)>
                 <#assign paraValue =key?substring(index+1)>
                 _t.setParameter("${paraName}","${paraValue}");
            </#list>
        </#if>
    <#assign fieldMap = CommonQueryConfig.fields>
            <#assign fields = fieldMap.keySet()>
            <#assign field = "">
            <#assign fDesc = "",fVal = "",fStat = "">
            <#assign columnInx = 0>
            <#list fields as fId>
                <#assign field = CommonQueryConfig.getField(fId?string)>
                <#assign fDesc = getFieldDesc(CommonQueryConfig.getId(),fId)>
                <#assign fType = field.getAnyValue("datatype","string")>
                <#assign fEidtType = field.getAnyValue("edittype","text")>
                <#if fEidtType=="checkbox">
                    <#assign fType = "boolean">
                </#if>
                <#assign fSize = field.getAnyValue("size")?default('10')>
                <#assign readonly = field.getAnyValue("readonly")?default('false')>
                <#assign required = defaultStr(field.getAnyValue("require")?default("false"),"false")>
                <#assign mask = field.getAnyValue("rules")?default("null")>
                <#assign defaultValue = field.getAnyValue("defaultvalue")?default("")>
                <#assign maskErrorMessage = field.getAnyValue("errmsg")?default("")>
                <#assign translated = field.getAnyValue("translated")?default("")>
                <#assign scale = field.getAnyValue("scale")?default("")>
                <#assign toolTip = field.getAnyValue("tip")?default(fDesc)>
                <#assign translated = field.getAnyValue("translated")?default("")>
                <#assign viewField = field.getAnyValue("viewfield","")>
                <#if mask?length==0>
                    <#assign mask="null">
                 </#if>
                <#switch fEidtType>
                     <#case "select">
                     <#-- �����select �ȼ� fId���ֶ� ,ѭ�����������Ӵ�name���ֶ�,Ϊ fId +��name -->
                          _f=_t.addField("${fId}","string"); _f.label="${fDesc}";
                          _f.size="${fSize}"; _f.scale="${scale}"; _f.readOnly="${readonly}";
                          _f.required=${required}; _f.nullable=true; _f.defaultValue=getDecodeStr("${defaultValue}");
                          _f.updatable=true; _f.valueProtected=false; _f.visible=true; _f.autoGenId=false;
                          _f.tableName=""; _f.fieldName="${fId}";  _f.editorType="${fEidtType}";
                           <#-- �ж��� ��select �� CQ��ʽ������dataDic��ʽ ������� CQ��Ҫ�����Ӧ�� CQId ���Ҳ���ֱ�ӷ��룬Ҫ��initdocument����-->
                                <#assign isCQ  = "false">
                                <#assign CQid  = "">
                                <#list translated?split(":")  as key>
                                     <#if key =="CQ">
                                        <#assign isCQ  = "true">
                                        <#break>
                                    </#if>
                                </#list>
                                <#if isCQ ="true">
                                    <#list translated?split(":")  as key>
                                        <#assign CQid  = key>
                                    </#list>
                                </#if>
                                 <#if isCQ ="true">
                                     _f.tag="selectCQ";
                                     _f.viewField="${viewField}";
                                     _f.dropDown="${fId}_DropDown";
                                     _f.dropDownDataset="${CQid}_DropDownDataset";
                                   <#else>
                                     _f.tag="select";
                                     _f.viewField="";
                                      _f.dropDown="";
                                   </#if>
                          if(typeof(${mask})!="undefined" && "${mask}"!="null")_f.mask="/" + ${mask} + "/";
                          _f.maskErrorMessage="${maskErrorMessage}"; _f.toolTip="${toolTip}";
                          _f.lobDownloadURL=getDecodeStr(""); _f.lobPopupURL=getDecodeStr("");
                        <#break>
                    <#default>
                          _f=_t.addField("${fId}","${fType}"); _f.label="${fDesc}";
                          _f.size="${fSize}"; _f.scale="${scale}"; _f.readOnly="${readonly}";
                          _f.required=${required};
                           _f.nullable=true; _f.defaultValue=getDecodeStr("${defaultValue}");
                          _f.updatable=true; _f.valueProtected=false; _f.visible=true; _f.autoGenId=false;
                          _f.tableName=""; _f.fieldName="${fId}"; _f.tag=""; _f.editorType="${fEidtType}"; _f.dropDown="";
                          if(typeof(${mask})!="undefined" && "${mask}"!="null")_f.mask="/" + ${mask} + "/";
                          _f.maskErrorMessage="${maskErrorMessage}"; _f.toolTip="${toolTip}";
                          _f.lobDownloadURL=getDecodeStr(""); _f.lobPopupURL=getDecodeStr("");
                        <#break>
                </#switch>
            </#list>
<#-- ѭ������������ fId + Name �ֶ� -->
    <#assign fieldMap = CommonQueryConfig.fields>
            <#assign fields = fieldMap.keySet()>
            <#assign field = "">
            <#assign fDesc = "",fVal = "",fStat = "">
            <#assign columnInx = 0>
            <#list fields as fId>
                    <#assign field = CommonQueryConfig.getField(fId?string)>
                    <#assign fEidtType = field.getAnyValue("edittype","text")>
                    <#if fEidtType == "select" >
                            <#assign fDesc = getFieldDesc(CommonQueryConfig.getId(),fId)>
                            <#assign fType = field.getAnyValue("type","string")>
                            <#assign fSize = field.getAnyValue("size","10")>
                            <#assign readonly = field.getAnyValue("readonly","false")>
                            <#assign required = defaultStr(field.getAnyValue("require")?default("false"),"false")>
                            <#assign mask = field.getAnyValue("rules","null")>
                            <#assign maskErrorMessage = field.getAnyValue("errmsg")?default("")>
                            <#assign defaultValue = field.getAnyValue("defaultvalue")?default("")>
                            <#assign scale = field.getAnyValue("scale")?default("")>
                            <#assign toolTip = field.getAnyValue("tip")?default(fDesc)>
                            <#assign translated = field.getAnyValue("translated")?default("")>
                            <#assign viewField = field.getAnyValue("viewfield","")>
                            <#assign editType = field.getAnyValue("edittype","")>
                            <#if mask?length==0>
                                <#assign mask="null">
                            </#if>
                                <#-- �ж��� ��select �� CQ��ʽ������dataDic��ʽ ������� CQ��Ҫ�����Ӧ�� CQId-->
                                <#assign isCQ  = "false">
                                <#assign CQid  = "">
                                <#list translated?split(":")  as key>
                                     <#if key =="CQ">
                                        <#assign isCQ  = "true">
                                        <#break>
                                    </#if>
                                </#list>
                                <#if isCQ ="true">
                                    <#list translated?split(":")  as key>
                                        <#assign CQid  = key>
                                    </#list>
                                </#if>
                                  _f=_t.addField("${fId}Name","string"); _f.label="${fDesc}";
                                  _f.size="${fSize}"; _f.scale="${scale}"; _f.readOnly="${readonly}";
                                  _f.required=${required}; _f.nullable=true; _f.defaultValue=getDecodeStr("${defaultValue}");
                                  _f.updatable=true; _f.valueProtected=false; _f.visible=true; _f.autoGenId=false;
                                  _f.tableName=""; _f.fieldName="${fId}Name"; _f.tag="selectName"; _f.editorType="${editType}"; _f.dropDown="${fId}_DropDown";
                                   <#if isCQ ="true">
                                     _f.dropDownDataset="${CQid}_DropDownDataset";
                                     _f.viewField="${viewField}";
                                   <#else>
                                      _f.dropDownDataset="${fId}_DropDownDataset";
                                      _f.viewField="";
                                   </#if>
                                  if(typeof(${mask})!="undefined" && "${mask}"!="null")_f.mask="/" + ${mask} + "/";
                                  _f.maskErrorMessage="${maskErrorMessage}"; _f.toolTip="${toolTip}";
                                  _f.lobDownloadURL=getDecodeStr(""); _f.lobPopupURL=getDecodeStr("");
                    </#if>
                </#list>
                initDataset(_t);
              <#if readOnly ="true">
                _t.setReadOnly(true);
              </#if>
            <#--Ĭ��ֵ����-->
            var  _paramValues = _paramMap.keys();
            for(var i=0;i<_paramValues.length;i++){
                _t.setValue(_paramValues[i],_paramMap.get(_paramValues[i]));
            }
            _paramMap.clear();
</script>
</#macro>

<#--����DropDownDataSetģ��-->
<#macro DropDownDataSet init="true">
<#assign specStr="nextPage|everyPage|pageNm|currentPage|fieldString|recordString|recordOrigString">
<script language="javascript">
    var ${CommonQueryConfig.getId()}_dataset=createDataset("${CommonQueryConfig.getId()}_DropDownDataset","",";");
    ${CommonQueryConfig.getId()}_dataset.flushData=dataset_flushData_new;
    var ${CommonQueryConfig.getId()}_t=${CommonQueryConfig.getId()}_dataset,_f;
    ${CommonQueryConfig.getId()}_t.readOnly=false;
    ${CommonQueryConfig.getId()}_t.async="${CommonQueryConfig.getAnyValue("async")?default("true")}";
    ${CommonQueryConfig.getId()}_t.pageSize=${CommonQueryConfig.getAnyValue("pagesize")?default("10")};
    ${CommonQueryConfig.getId()}_t.pageIndex=1;
    ${CommonQueryConfig.getId()}_t.pageCount=1;
    ${CommonQueryConfig.getId()}_t.masterDataset="";
    ${CommonQueryConfig.getId()}_t.references="";
    ${CommonQueryConfig.getId()}_t.submitData="allchange";
    ${CommonQueryConfig.getId()}_t.autoLoadPage=false;
    ${CommonQueryConfig.getId()}_t.autoLoadDetail=true;
    ${CommonQueryConfig.getId()}_t.downloadUrl=getDecodeStr("~2fextraservice~2fdownloaddata");
    ${CommonQueryConfig.getId()}_t.insertOnEmpty=false;
    ${CommonQueryConfig.getId()}_t.tag="";
    ${CommonQueryConfig.getId()}_t.type="dropdown";
    ${CommonQueryConfig.getId()}_t.sessionKey="dd";
    ${CommonQueryConfig.getId()}_t.setParameter("CQId","${CommonQueryConfig.getId()}","string");
    ${CommonQueryConfig.getId()}_t.setParameter("nextPage",${CommonQueryConfig.getId()}_t.pageIndex);
    ${CommonQueryConfig.getId()}_t.setParameter("everyPage",${CommonQueryConfig.getId()}_t.pageSize);
    ${CommonQueryConfig.getId()}_t.setParameter("_session_key",${CommonQueryConfig.getId()}_t.sessionKey);
    ${CommonQueryConfig.getId()}_t.init = "${init}";
    ${CommonQueryConfig.getId()}_t.initDocumentFlag = false;
            <#assign fieldMap = CommonQueryConfig.fields>
            <#assign fields = fieldMap.keySet()>
            <#assign field = "">
            <#assign fDesc = "",fVal = "",fStat = "">
            <#assign columnInx = 0>
            <#list fields as fId>
                          _f=${CommonQueryConfig.getId()}_t.addField("${fId}","string"); _f.label="";
                          _f.size=""; _f.scale=""; _f.readOnly=false;
                          _f.required=false; _f.nullable=true; _f.defaultValue=getDecodeStr("");
                          _f.updatable=true; _f.valueProtected=false; _f.visible=true; _f.autoGenId=false;
                          _f.tableName=""; _f.fieldName="${fId}"; _f.tag=""; _f.editorType=""; _f.dropDown="";
                          _f.mask= "";
                          _f.maskErrorMessage=""; _f.toolTip="";
                          _f.lobDownloadURL=getDecodeStr(""); _f.lobPopupURL=getDecodeStr("");
            </#list>
            initDataset(${CommonQueryConfig.getId()}_t);
</script>
</#macro>

<#--����PagePilot��-->
<#--
    id                  ҳ����ͼID(������Ϊ��)
    maxpagelink         ҳ����ʾ��ͷ��־(����Ϊ��,Ĭ��Ϊ"true"            "true"-��ʾ;"false"-����ʾ)
    pageCache           ҳ�滺���־��Ĭ��Ϊ"false"  "true"-ʹ��ҳ�滺��;��ҳ���ȡ���е����ݼ�¼����ҳ����з�ҳ
-->
<#macro PagePilot id maxpagelink="9" showArrow="true" pageCache="false">
<table extra="pagepilot" id="${id}" pageCache="${pageCache}" dataset="${CommonQueryConfig.getId()}_dataset" maxPageLink="${maxpagelink}" showArrow="${showArrow}">
<tbody></tbody>
</table>
</#macro>

<#--����DataTable��-->
<#--
    id                 ҳ����ͼID(������Ϊ��)
    fieldStr           ��ʾ�ֶ�����(����Ϊ��,Ĭ��Ϊ��ʾFields�������ֶ�        ֵ��ʽΪ��field1_id,field2_id,...)
                          ֧��ÿ��field����[width]�ֶ�: ֵ��ʽ�޸�Ϊfield1_id[width],field2_id[width]...
    width              �����(����Ϊ��,Ĭ��Ϊ�ֶ�����*100)
    readonly           ֻ����־(����Ϊ��,Ĭ��Ϊ"false"                     "true"-ֻ��;"false"-�ɱ༭)
    printabled         �Ƿ��ܹ���ӡ(����Ϊ�գ�Ĭ��Ϊfalse)
    maxRow             �����ʾ����������pagecache��ʽʱ�����õ�ǰ��������ʾ���������ڲ�ʹ��pagecache��ʽʱ���벻Ҫʹ�ø��ֶ�)
-->
<#macro DataTable id   fieldStr="${CommonQueryConfig.toFieldString()}"  width="" height="" readonly="" printabled="true" maxRow="" hasFrame="false" allowClick="true" allowSort="true">
<#assign tablehead="">
<#assign tablerow="">
<#assign readonly="{readonly}"?default("true")>
<#assign fields = fieldStr?split(',')>
<#assign fCount = fields?size>
<#assign genHiddenSelect = "">
<#list fields as fId>
       <#assign _width = "">
       <#assign _fId = "">
       <#if fId?matches(".*\\[.*\\]")>
           <#assign _startIndex = fId?index_of("[")>
           <#assign _endIndex = fId?index_of("]")>
           <#assign _width = fId?substring(_startIndex+1,_endIndex)>
           <#assign _fId = fId?substring(0,_startIndex)>
       <#else>
           <#assign _fId = fId>
       </#if>
       <#assign field = CommonQueryConfig.getField(_fId)>
       <#assign fDesc = getFieldDesc(CommonQueryConfig.getId(),_fId)>
       <#if _width == "">
           <#assign fwidth = field.getAnyValue("width")?default('')>
       <#else>
           <#assign fwidth = _width>
       </#if>
<#--
       <#assign field = CommonQueryConfig.getField(fId)>
       <#assign fDesc = getFieldDesc(CommonQueryConfig.getId(),fId)>
       <#assign fwidth = field.getAnyValue("width")?default('')>
-->   
       <#assign fedittype = field.getAnyValue("edittype")?default("text")>
       <#assign datatype = field.getAnyValue("datatype")?default('string')>
<#--
       <#assign tablehead = tablehead + "\ltd name=\"" + _fId + "\" width=\"" + fwidth + "px\" label=\"" + fDesc + "\"\g" + "\l/td\g" >
       -->
       <#-- yjw 2008-05-26 -->
       <#if fedittype != "hidden">
            <#assign tablehead = tablehead + "\ltd name=\"" + _fId + "\" allowClick=\""+ allowClick +"\" allowSort=\"" + allowSort + "\" label=\"" + fDesc + "\" \g" + "\l/td\g" >
       </#if>
       <#if fedittype = "select">
                        <#assign label = field.getAnyValue("desc")?default('')>
                        <#assign width = field.getAnyValue("width")?default('')>
                        <#assign colSpan = field.getAnyValue("colspan")?default('2')>
                        <#assign vAlign = field.getAnyValue("valign")?default('center')>
                        <#assign height = field.getAnyValue("height")?default("")>
                        <#assign readonly2 = field.getAnyValue("readonly")?default("false")>
                        <#assign defaultValue = field.getAnyValue("defaultvalue")?default("")>
                        <#assign targetDataSet = CommonQueryConfig.getId() + "_dataset">
                        <#assign translated = field.getAnyValue("translated")?default('')>
                        <#assign required = field.getAnyValue("require")?default('false')>
                        <#assign viewField = field.getAnyValue("viewfield","")>
                        <#assign fieldMap = field.getAnyValue("fieldmap","")>
                        <#assign dropdowntype = defaultStr(field.getAnyValue("dropdowntype")?default("dataset"),"dataset")>
                        <#assign init = defaultStr(field.getAnyValue("init")?default("true"),"true")>
                        <#assign url = field.getAnyValue("url")?default("")>
                        <#if url = "">
                            <@htmlEditType.selectHidden lable=label id=_fId   targetDataset=targetDataSet
                                width=width height=height require=required  readOnly=readonly2 defaultValue=defaultValue
                                translated=translated  viewField=viewField fieldMap=fieldMap ddtype=dropdowntype init=init/>
                        <#elseif dropdowntype = "custom">
                            <@htmlEditType.selectDataDic lable=label id=_fId   targetDataset=targetDataSet
                                width=width height=height require=required  readOnly=readonly2 defaultValue=defaultValue
                                translated=translated  viewField=viewField fieldMap=fieldMap ddtype=dropdowntype init=init url=url notd="true"/>
                        </#if>
                        <#--
                        <@htmlEditType.selectHidden lable=label id=_fId   targetDataset=targetDataSet
                            width=width height=height require=required  readOnly=readonly2 defaultValue=defaultValue
                            translated=translated  viewField=viewField fieldMap=fieldMap ddtype=dropdowntype init=init/>
                            -->
              <#assign _id=_fId>
              <#assign _id_name=_fId+"name">
              <#assign _id_DD=_id+"_DropDown">
              <#assign tablerow = tablerow + "\ltd name=\"" + _id_name + "\" width=\"" + fwidth + "px\" dataType=\""+ datatype+"\" editorType=\"" + fedittype + "\" dropDown=\"" +_id_DD+"\"  label=\"" + fDesc + "\"  " + "\g"+ "\l/td\g" >
       <#elseif fedittype = "checkbox">
              <#assign tablerow = tablerow + "\ltd name=\"" + _fId + "\" width=\"" + fwidth + "px\" dataType=\""+ datatype+"\"  editorType=\"" + fedittype + "\" label=\"" + fDesc + "\"  " + "\g"+ "\l/td\g" >
       <#elseif fedittype != "hidden">
              <#assign tablerow = tablerow + "\ltd name=\"" + _fId + "\" width=\"" + fwidth + "px\" dataType=\""+ datatype+"\"  editorType=\"" + fedittype + "\" label=\"" + fDesc + "\"  " +   "\g"+ "\l/td\g" >
       </#if>
</#list>
<#if width == "">
    <#assign width=100*fCount>
</#if>
<#if height == "">
	<#assign height=400>
</#if>
<#if hasFrame=="true">
<#assign tableWidth=getNumber(width)>
<#assign divWidth=tableWidth?number + 17>
<div style="overflow-x:auto;overflow-y:scroll;border:1px inset;width:${divWidth}px;height:${height}px;">
</#if>
<table extra="datatable"  skipRebuild="true"  id="${id}"
            label="" printabled="${printabled}" dataset="${CommonQueryConfig.getId()}_dataset"
            readOnly="${readonly}" confirmDelete="true" editable="true"
            confirmCancel="true" showHeader="true" showIndicate="true"
            showDefinedColsOnly="true" width="${width}px" bordercolor="silver"
            cellspacing="0" cellpadding="0" rules="all" maxRow="${maxRow}" style="word-break:break-all">
            <thead>
                <tr height="20px">
                    ${tablehead}
                </tr>
            </thead>
            <tbody>
                <tr height="20px">
                    ${tablerow}
                </tr>
            </tbody>
</table>
<#if hasFrame=="true">
</div>
</#if>
</#macro>

<#--����Group��-->
<#--
id                 ҳ����ͼID(������Ϊ��)
label              group��ʾ����(������Ϊ��)
fieldStr           ��ʾ�ֶ�(������Ϊ��                  ֵ��ʽΪ��field1_id,field2_id,...)
colNm              ��ʾ����(����Ϊ��  Ĭ��Ϊ4          ֵ��ʽΪ��2*n)
printabled         �Ƿ��ܹ���ӡ(����Ϊ�գ�Ĭ��Ϊfalse)
-->
<#macro Group id label fieldStr colNm="4" showGroupLine="true" printabled="false" expand="true">
<#if showGroupLine=="true">
<FIELDSET name='${id}' style="padding: 6px;" expand="${expand}">
    <LEGEND id='${id}_line' extra="groupboxtitle">${label}</LEGEND>
</#if>
	<div style="width:100%;">
	  <div id='${id}_div' style="">
        <table width="100%" height="100%" id="${id}_Table" label="${label}" printabled="${printabled}" dataset="${CommonQueryConfig.getId()}_dataset">
        <#nested>
            <#assign fieldMap = CommonQueryConfig.fields>
            <#if fieldStr != "">
                <#assign fields = fieldStr?split(",")>
                <#assign field = "">
                <#assign fDesc = "",fVal = "",fStat = "">
                <#assign restCol = colNm>
                <#list fields as fId>
                    <#assign field = CommonQueryConfig.getField(fId)>
                    <#assign fDesc = getFieldDesc(CommonQueryConfig.getId(),fId)>
                    <#assign fTip  = field.getAnyValue("tip")?default(fDesc)>
                    <#assign status  = field.getAnyValue("status")?default("F")>
                    <#assign edittype  = field.getAnyValue("edittype")?default("")>
                    <#assign fColSpan = field.getAnyValue("colspan")?default("2")>
                    <#assign defaultValue = field.getAnyValue("defaultvalue")?default("")>
                    <#assign targetDataSet = CommonQueryConfig.getId() + "_dataset">
                    <#assign compositeFlag = field.getAnyValue("compositeflag")?default("false")>
                    <#assign compositeIndex  = field.getAnyValue("compositeindex")?default("")>
                    <#assign preLabel  = getFieldDesc(CommonQueryConfig.getId(),fId)>
                    <#assign require  = field.getAnyValue("require")?default("false")>
                    <#assign fieldId  = field.getAnyValue("id")?default("id")>
                    <#if restCol?number == colNm?number >
                            <tr id="${fieldId}_TR" fieldId="${fieldId}">
                    </#if>

                    <#if edittype!="hidden">
                            <#if compositeFlag!="true">
                                <@GroupField fId/>
                                    <#assign restCol = restCol?number - fColSpan?number>
                            <#elseif compositeFlag=="true">
                                <#if compositeIndex=="start">
                                     <td valign="center" align="right" style="width:20%" >
                                         <#if require=="true">
                                            <font color=red>*</font>
                                         </#if>
                                     <label>${preLabel}</label>
                                     </td>
                                     <td colspan=${fColSpan?number - 1}  valign="center" fieldId="${fieldId}">
                                        <#--<@SingleField fId/>${fDesc}-->
                                        <@SingleField fId/>
                                        <#assign restCol = restCol?number - 1>
                                <#elseif compositeIndex=="end">
                                        <#--<@SingleField fId/>${fDesc}-->
                                        <@SingleField fId/>
                                     </td>
                                         <#assign restCol = restCol?number - fColSpan?number +1>
                                <#else>
                                        <#--<@SingleField fId/>${fDesc}-->
                                        <@SingleField fId/>
                                </#if>
                            </#if>
                    <#else>
                            <@htmlEditType.hiddenelement id=fId value=defaultValue targetDataset=targetDataSet />
                    </#if>
                    <#if restCol?number <= 0>
                        <#assign restCol = colNm>
                        </tr>
                    </#if>
            	</#list>
            <#else><#--��group.fieldStr=""ʱ���������κ�field (ԭĬ�ϼ�������field)
                <#assign fields = fieldMap.keySet()>
                <#assign field = "">
                <#assign fDesc = "",fVal = "",fStat = "">
                <#assign restCol = colNm>
                <#list fields as fId>
                    <#assign field = CommonQueryConfig.getField(fId)>
                    <#assign fDesc = getFieldDesc(CommonQueryConfig.getId(),fId)>
                    <#assign fTip  = field.getAnyValue("tip")?default(fDesc)>
                    <#assign status  = field.getAnyValue("status")?default("F")>
                    <#assign edittype  = field.getAnyValue("edittype")?default("")>
                    <#assign fColSpan = field.getAnyValue("colspan")?default("2")>
                    <#assign defaultValue = field.getAnyValue("defaultvalue")?default("")>
                    <#assign targetDataSet = CommonQueryConfig.getId() + "_dataset">

                    <#if restCol?number == colNm?number >
                        <tr>
                    </#if>

                    <#if edittype!="hidden">
                        <@GroupField fId/>
                        <#assign restCol = restCol?number - fColSpan?number>
                    <#elseif edittype ="hidden">
                        <@htmlEditType.hiddenelement  id=fId value=defaultValue targetDataset=targetDataSet />
                    </#if>

                    <#if restCol?number <= 0>
                        <#assign restCol = colNm>
                        </tr>
                    </#if>
                </#list>-->
            </#if>

        </table>
      </div>
	</div>
<#if showGroupLine=="true">
</FIELDSET>
</#if>
</#macro>

<#--����DataTableField��-->
<#macro DataTableField fId>
</#macro>

<#--����GroupField��-->
<#macro GroupField fId>
<#assign field = CommonQueryConfig.getField(fId)>
<#assign textAlign = field.getAnyValue("textalign")?default('')>
<#assign fEidtType = field.getAnyValue("edittype")?default('text')>
<#assign label =  getFieldDesc(CommonQueryConfig.getId(),fId)>
<#assign width = field.getAnyValue("width")?default('')>
<#assign colSpan = field.getAnyValue("colspan")?default('2')>
<#assign vAlign = field.getAnyValue("valign")?default('center')>
<#assign height = field.getAnyValue("height")?default("")>
<#assign readonly = field.getAnyValue("readonly")?default("false")>
<#assign defaultValue = field.getAnyValue("defaultvalue")?default("")>
<#assign targetDataSet = CommonQueryConfig.getId() + "_dataset">
<#assign translated = field.getAnyValue("translated")?default('')>
<#assign required = field.getAnyValue("require")?default('false')>
<#assign viewField = field.getAnyValue("viewfield","")>
<#assign fieldMap = field.getAnyValue("fieldmap","")>
<#assign url = field.getAnyValue("url")?default('')>
<#-- modify by shen_antonio 20080122 -->
<#assign rowSpan = defaultStr(field.getAnyValue("rowspan")?default(1),1)>
<#assign rowSpan = rowSpan?number>
<#-- -->

<#switch fEidtType>
    <#case "text">
            <@htmlEditType.text id=fId  targetDataset=targetDataSet defaultValue=defaultValue width=width colSpan=colSpan
             rowSpan=rowSpan vAlign=vAlign textAlign=textAlign/>
        <#break>
    <#case "datalabel">
            <@htmlEditType.datalabel id=fId  targetDataset=targetDataSet defaultValue=defaultValue width=width colSpan=colSpan
             rowSpan=rowSpan vAlign=vAlign/>
        <#break>
    <#case "date">
            <@htmlEditType.date id=fId  targetDataset=targetDataSet defaultValue=defaultValue width=width colSpan=colSpan
             rowSpan=rowSpan vAlign=vAlign/>
        <#break>
    <#case "select">
        <#assign dropdowntype = defaultStr(field.getAnyValue("dropdowntype")?default("dataset"),"dataset")>
        <#assign init = defaultStr(field.getAnyValue("init")?default("true"),"true")>
        <@htmlEditType.selectDataDic lable=label id=fId   targetDataset=targetDataSet colSpan=colSpan
                    width=width height=height require=required  readOnly=readonly defaultValue=defaultValue translated=translated viewField=viewField fieldMap=fieldMap ddtype=dropdowntype init=init url=url/>
        <#break>
    <#case "textarea">
        <@htmlEditType.textarea  id=fId  targetDataset=targetDataSet defaultValue=defaultValue width=width height=height colSpan=colSpan rowSpan=rowSpan vAlign=vAlign/>
        <#break>
    <#case "checkbox">
        <@htmlEditType.checkbox  lable=label id=fId  targetDataset=targetDataSet defaultValue=defaultValue width=width colSpan=colSpan  rowSpan=rowSpan vAlign=vAlign/>
        <#break>
    <#case "dyselect">
        <@htmlEditType.selectDataDic lable=label id=fId   targetDataset=targetDataSet
                    width=width height=height require=required  readOnly=readonly defaultValue=defaultValue translated=translated viewField=viewField fieldMap=fieldMap  ddtype="dynamic"/>
        <#break>
    <#case "file">
        <@htmlEditType.file  lable=label id=fId  targetDataset=targetDataSet defaultValue=defaultValue width=width colSpan=colSpan  rowSpan=rowSpan vAlign=vAlign/>
        <#break>
      <#case "password">
        <@htmlEditType.password   id=fId  targetDataset=targetDataSet defaultValue=defaultValue width=width colSpan=colSpan  rowSpan=rowSpan vAlign=vAlign/>
        <#break>
    <#default>
        <@htmlEditType.text id=fId  targetDataset=targetDataSet defaultValue=defaultValue width=width colSpan=colSpan
             rowSpan=rowSpan vAlign=vAlign textAlign=textAlign />
    <#break>
</#switch>
</#macro>


<#--����GroupField��,������td-->
<#macro SingleField fId>
<#assign field = CommonQueryConfig.getField(fId)>
<#assign textAlign = field.getAnyValue("textalign")?default('')>
<#assign fEidtType = field.getAnyValue("edittype")?default('text')>
<#assign label =  getFieldDesc(CommonQueryConfig.getId(),fId)>
<#assign width = field.getAnyValue("width")?default('')>
<#assign colSpan = field.getAnyValue("colspan")?default('2')>
<#assign vAlign = field.getAnyValue("valign")?default('center')>
<#assign height = field.getAnyValue("height")?default("")>
<#assign readonly = field.getAnyValue("readonly")?default("false")>
<#assign defaultValue = field.getAnyValue("defaultvalue")?default("")>
<#assign targetDataSet = CommonQueryConfig.getId() + "_dataset">
<#assign translated = field.getAnyValue("translated")?default('')>
<#assign required = field.getAnyValue("require")?default('false')>
<#assign viewField = field.getAnyValue("viewfield","")>
<#assign fieldMap = field.getAnyValue("fieldmap","")>
<#assign url = field.getAnyValue("url")?default('')>
<#switch fEidtType>
    <#case "text">
            <@htmlEditType.text2 id=fId label=label  targetDataset=targetDataSet defaultValue=defaultValue width=width colSpan=colSpan
             rowSpan=rowSpan vAlign=vAlign textAlign=textAlign />
        <#break>
   <#case "datalabel">
            <@htmlEditType.datalabel2 id=fId  targetDataset=targetDataSet defaultValue=defaultValue width=width colSpan=colSpan
             rowSpan=rowSpan vAlign=vAlign/>
   <#break>
   <#case "select">
        <#assign dropdowntype = defaultStr(field.getAnyValue("dropdowntype")?default("dataset"),"dataset")>
        <#assign init = defaultStr(field.getAnyValue("init")?default("true"),"true")>
        <@htmlEditType.selectDataDic2 lable=label id=fId   targetDataset=targetDataSet
                    width=width height=height require=required  readOnly=readonly defaultValue=defaultValue translated=translated viewField=viewField fieldMap=fieldMap ddtype=dropdowntype init=init url=url/>
        <#break>
    <#case "checkbox">
        <@htmlEditType.checkbox2  lable=label id=fId  targetDataset=targetDataSet defaultValue=defaultValue width=width colSpan=colSpan  rowSpan=rowSpan vAlign=vAlign/>
        <#break>
   <#case "textarea">
        <@htmlEditType.textarea2  id=fId  targetDataset=targetDataSet defaultValue=defaultValue width=width height=height colSpan=colSpan rowSpan=rowSpan vAlign=vAlign/>
        <#break>
        <#case "dyselect">
   <@htmlEditType.selectDataDic2 lable=label id=fId   targetDataset=targetDataSet
                    width=width height=height require=required  readOnly=readonly defaultValue=defaultValue translated=translated viewField=viewField fieldMap=fieldMap  ddtype="dynamic"/>
        <#break>
    <#default>
        <@htmlEditType.text2 id=fId label=label  targetDataset=targetDataSet defaultValue=defaultValue width=width colSpan=colSpan
             rowSpan=rowSpan vAlign=vAlign textAlign=textAlign />
    <#break>

</#switch>
</#macro>

<#--����Button��-->
<#macro Button id targetDataset=CommonQueryConfig.getId()+"_dataset">
<#assign button = CommonQueryConfig.getOperationsElement(id)>
<#assign desc = button.getAnyValue("desc")?default("")>
<#assign url = button.getAnyValue("url")?default("#")>
<#assign submitDataset = button.getAnyValue("submitdataset")?default("")>
<#assign updateClass = button.getAnyValue("updateclass")?default("")>
<#assign operation = button.getAnyValue("operation")?default("")>
<#-- modfiy by shen_antonio 20080121 -->
<#assign targetFrame = button.getAnyValue("targetframe")?default("_self")>
<@htmlEditType.button id=id defaultOperation=operation desc=desc targetDataset=targetDataset url=url updateClass=updateClass submitDataset=submitDataset  targetFrame=targetFrame/>
</#macro>

<#macro ButtonGroup targetDataset=CommonQueryConfig.getId()+"_dataset">
<#assign operationsMap = CommonQueryConfig.operations>
<#assign operationsKeys = operationsMap.keySet()>
<#list operationsKeys as id>
    <#assign button = CommonQueryConfig.getOperationsElement(id)>
    <#assign desc = button.getAnyValue("desc")?default("")>
    <#assign url = button.getAnyValue("url")?default("#")>
    <#assign operation = button.getAnyValue("operation")?default("")>
    <#assign url = button.getAnyValue("url")?default("#")>
    <#assign updateClass = button.getAnyValue("updateclass")?default("")>
    <#assign submitDataset = button.getAnyValue("submitdataset")?default("")>
    <#-- modify by shen_antonio 20080121 -->
    <#assign targetFrame = button.getAnyValue("targetframe")?default("_self")>
    <@htmlEditType.button id=id defaultOperation=operation desc=desc targetDataset=targetDataset url=url updateClass=updateClass submitDataset=submitDataset targetFrame=targetFrame/>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</#list>
</#macro>


<#function defaultStr sv desv>
  <#assign restr = sv?default(desv)>
  <#if restr?trim == "">
    <#return desv>
  <#else>
    <#return sv>
  </#if>
</#function>

<#function reqParamConver>
<#assign paramString = "">
<#assign paramIds = RequestParameters?keys>
<#assign paramVal = "">
<#list paramIds as paramId>
    <#assign paramVal = RequestParameters[paramId]>
    <#--
    <#assign paramVal = encodeStr(paramVal)>
    -->
    <#if paramString!="">
        <#assign paramString = paramString + ";" + paramId + "," + paramVal>
    <#else>
        <#assign paramString = paramId + "," + paramVal>
    </#if>
</#list>
<#return paramString>
</#function>

<#function configParamConver>
<#assign paramString = "">
<#assign paramIds = CommonQueryConfig.getParameters().keySet()>
<#assign paramVal = "">
<#list paramIds as paramId>
    <#assign paramVal = CommonQueryConfig.getParameter(paramId).getAnyValue("value","")>
    <#assign paramVal = encodeStr(paramVal)>
    <#if paramString!="">
        <#assign paramString = paramString + ";" + paramId + "," + paramVal>
    <#else>
        <#assign paramString = paramId + "," + paramVal>
    </#if>
</#list>
<#return paramString>
</#function>

<#function paramConver>
<#assign cParamString = configParamConver()>
<#assign rParamString = reqParamConver()>
<#assign paramString = "">
<#if rParamString != "">
    <#assign paramString = cParamString + ";" + rParamString>
<#else>
    <#assign paramString = cParamString>
</#if>
<#return paramString>
</#function>



<#--����Interface��-->
<#--
id                               cqId (������Ϊ�գ�
label��                           Group����( ����Ϊ�գ�ȱʡΪXML������title����)
resultDateset��                   �����Dataset Ŀ�������ݼ�(����Ϊ�գ�ȱʡΪ��ͨ�ò�ѯ������ݼ�)
colNm                            ����(2�ı�����(����Ϊ�գ�ȱʡΪ4)
width��                              ��� ��ʾ���(����Ϊ�գ�ȱʡΪ"100%")
showButton                       ��ʾ��ѯ��ť��־(��Ϊ�գ�ȱʡΪ"true")
showStyle                        ��ʾ��ť��ʽ(0-��ѯ&���;1-��ѯ;2-���)
defaultOperation                 Ĭ���ύ���� (��Ϊ�գ�ȱʡasyncqrysubmitflush)
btnNm                            ��ť����(��Ϊ�գ�ȷ��ΪXML������btnNm����������ò���Ϊ�գ�ȱʡΪ'��ѯ')
-->
<#macro Interface
    id
    label=CommonQueryConfig.getAnyValueDefault("title","")
    resultDataset=CommonQueryConfig.getId()+"_dataset"
    colNm=4
    width="100%"
    showButton="true"
    defaultOperation="asyncqrysubmitflush"
    parameters=""
    btnNm=""
    showStyle="0"
    clearBtn="���"
    expand="true"
    fieldStr=""
    >
<@IntfaceDataset parameters/>
<#--
<div name="${CommonQueryConfig.getId()}_Interface_${id}_div">
-->
<FIELDSET name='${id}' style="padding: 6px;" expand="${expand}">
	<LEGEND extra="groupboxtitle">&nbsp;${label}&nbsp;</LEGEND>
	<div style="width:100%;">
	<div style="">
	<#nested>
        <#-- modify by shen_antonio 20080403 -->
        <#if btnNm=="">
            <#assign buttonNm = CommonQueryConfig.getAnyValueDefault("btnNm","��ѯ")>
        <#else>
            <#assign buttonNm = CommonQueryConfig.getAnyValueDefault("btnNm",btnNm)>
        </#if>
        <#if clearBtn!="none">
            <#assign clearNm = CommonQueryConfig.getAnyValueDefault("clearBtn",clearBtn)>
        </#if>
        <#-- -->
        <#assign elementList = CommonQueryConfig.elementList>
        <#assign elementListLength = CommonQueryConfig.elementList.size()>
        <#assign showIndex = 0>
        <table width="${width}" height="100%" id="${CommonQueryConfig.getId()}_interface_Table">
            <#assign elDesc = "",elVal = "",elStat = "">
            <#assign restCol = colNm>
            <#if fieldStr=="">
            	<#list elementList as element>
                <#assign elId = element.getAnyValue("id")>
                <#assign edittype  = element.type?default("")>
                <#assign elColSpan = element.getAnyValue("colspan")?default("2")>
                <#assign defaultValue = element.getAnyValue("default")?default("")>
                <#assign value = element.getAnyValue("value")?default("")>
                <#assign targetDataSet = CommonQueryConfig.getId() + "_interface_dataset">
                <#if restCol?number == colNm?number >
                    <tr>
                </#if>
                <#if edittype!="Hidden">
                    <#assign showIndex=showIndex + 1>
                    <@InterfaceElement element/>
                    <#assign restCol = restCol?number - elColSpan?number>
                <#else>
                    <@InterfaceElement element/>
                </#if>
                <#if restCol?number <= 0>
                    <#assign restCol = colNm>
                    <#if element_index+1 != elementListLength>
                        </tr>
                    </#if>
                </#if>
                </#list>
            <#else>
            	<#assign fields = fieldStr?split(',')>
            	<#assign elementListLength = fields?size>
            	<#list fields as fId>
            		<#assign element = CommonQueryConfig.getWhereElement(fId)>
            		<#assign elId = element.getAnyValue("id")>
                	<#assign edittype  = element.type?default("")>
                	<#assign elColSpan = element.getAnyValue("colspan")?default("2")>
                	<#assign defaultValue = element.getAnyValue("default")?default("")>
                	<#assign value = element.getAnyValue("value")?default("")>
                	<#assign targetDataSet = CommonQueryConfig.getId() + "_interface_dataset">
                    <#if restCol?number == colNm?number >
                        <tr>
                    </#if>
                    <#if edittype!="Hidden">
                        <#assign showIndex=showIndex + 1>
                        <@InterfaceElement element/>
                        <#assign restCol = restCol?number - elColSpan?number>
                    <#else>
                        <@InterfaceElement element/>
                    </#if>
                    <#if restCol?number <= 0>
                        <#assign restCol = colNm>
                        <#--
                        <#if element_index+1 != elementListLength>
                            </tr>
                        </#if>
                        -->
                    </#if>
            	</#list>
            </#if>
                <#--
                <#if showIndex <=1 && showButton == "true">
                        <td align="center" >
                         <@htmlEditType.button
                            id=CommonQueryConfig.getId()+"_interface_dataset_btnSubmit"
                            defaultOperation=defaultOperation
                            desc=buttonNm
                            targetDataset=CommonQueryConfig.getId()+"_interface_dataset"
                            url=CommonQueryConfig.getResultViewURL()
                            updateClass="#"    resultDataset = resultDataset/>
                         </td>
                         </tr>
                <#else>
                    </tr>
                </#if>
                -->
        </table>
     </div>
   </div>
</FIELDSET>
<#if showButton == "true">
            <table width="${width}" height="100%" >
            <tr><td align="center" >
            	 <#if showStyle == "0" || showStyle == "1">
                 <@htmlEditType.button
                    id=CommonQueryConfig.getId()+"_interface_dataset_btnSubmit"
                    defaultOperation=defaultOperation desc=buttonNm
                    targetDataset=CommonQueryConfig.getId()+"_interface_dataset"
                    url=CommonQueryConfig.getResultViewURL()
                    updateClass="#"  resultDataset=resultDataset/>
                 </#if>
                 <#if showStyle == "0">
                 	&nbsp;&nbsp;
                 </#if>
                 <#if showStyle == "0" || showStyle == "2">
                    <#if clearBtn!="none">
                    <@htmlEditType.button
                    id=CommonQueryConfig.getId()+"_interface_dataset_clearBtn"
                    defaultOperation="clearrecord" desc=clearNm
                    targetDataset=CommonQueryConfig.getId()+"_interface_dataset"
                    url=CommonQueryConfig.getResultViewURL()
                    updateClass="#"  resultDataset=""/>
                    </#if>
                 </#if>
                 </td>
             </tr>
            </table>
</#if>
        <#--
</div>
-->
</#macro>


<#--����Intface DataSetģ��-->
<#macro IntfaceDataset parameters="">
<#assign specStr="nextPage|everyPage|pageNm|currentPage|fieldString|recordString|recordOrigString">
<#--init request param -->
<#assign paramString = paramConver()>
<script language="javascript">
    var ${CommonQueryConfig.getId()}_interface_dataset=createDataset("${CommonQueryConfig.getId()}_interface_dataset","","");
    ${CommonQueryConfig.getId()}_interface_dataset.flushData=dataset_flushData_new;
    var _t1=${CommonQueryConfig.getId()}_interface_dataset,_f;
    _t1.readOnly=false;
    _t1.async="${CommonQueryConfig.getAnyValue("async")?default("true")}";
    _t1.pageSize=${CommonQueryConfig.getAnyValue("pagesize")?default("10")};
    _t1.databusId="${CommonQueryConfig.getAnyValue("databusid")?default("")}";
    _t1.pageIndex=1;
    _t1.pageCount=1;
    _t1.masterDataset="";
    _t1.references="";
    _t1.submitData="allchange";
    _t1.autoLoadPage=false;
    _t1.autoLoadDetail=true;
    _t1.downloadUrl=getDecodeStr("~2fextraservice~2fdownloaddata");
    _t1.insertOnEmpty=false;
    _t1.tag="";
    _t1.type="interface";
    _t1.sessionKey="dd";
    _t1.initDocumentFlag = false;
    converStr2DataSetParameter("${paramString}",_t1);
    _t1.setParameter("CQId","${CommonQueryConfig.getId()}","string");
    _t1.setParameter("nextPage",_t1.pageIndex);
    _t1.setParameter("everyPage",_t1.pageSize);
    _t1.setParameter("_session_key",_t1.sessionKey);
    _t1.setParameter("databusId",_t1.databusId);
    _t1.setParameter("_cds_","0");
        <#assign parameters = "${parameters}">
        <#if parameters!="">
            <#list parameters?split(";")  as key>
                 <#assign index =key?index_of("=")>
                 <#assign paraName =key?substring(0,index)>
                 <#assign paraValue =key?substring(index+1)>
                 _t1.setParameter("${paraName}","${paraValue}");
            </#list>
        </#if>

    <#assign elements = CommonQueryConfig.elementList>
            <#assign columnInx = 0>
            <#list elements as element>
                <#assign elId = element.getAnyValue("id")>
                <#assign elDesc = getElementDesc(CommonQueryConfig.getId(),elId)>
                <#assign elType = element.getAnyValue("datatype","string")>
                <#assign elEidtType = element.type?default("text")>
                <#if elEidtType=="checkbox">
                    <#assign elType = "boolean">
                </#if>
                <#assign elSize = element.getAnyValue("size")?default('10')>
                <#assign readonly = element.getAnyValue("readonly")?default('false')>
                <#assign required = defaultStr(element.getAnyValue("require")?default("false"),"false")>
                <#assign mask = element.getAnyValue("rules")?default("null")>
                <#assign maskErrorMessage = element.getAnyValue("errmsg")?default("")>
                <#assign scale = element.getAnyValue("scale")?default("")>
                <#assign defaultValue = element.getAnyValue("defaultvalue")?default("")>
                <#assign toolTip = element.getAnyValue("tip")?default(elDesc)>
                <#assign translated = element.getAnyValue("translated")?default("")>
                <#assign viewField = element.getAnyValue("viewfield","")>
                <#assign editType = element.getAnyValue("edittype","")>
                <#if mask?length==0>
                    <#assign mask="null">
                </#if>
                <#-- shen_antonio -->
                <#assign defaultValue = element.getAnyValue("default")?default("")>
                <#switch elEidtType>
                    <#case "Option">
                          <#-- �����select �ȼ� ��fId ���ֶ�,ѭ�����������ӡ� fId +��name���ֶ�-->
                          _f=_t1.addField("${elId}","string"); _f.label="${elDesc}";
                          _f.size="${elSize}"; _f.scale="${scale}"; _f.readOnly="${readonly}";
                          _f.required=${required}; _f.nullable=true; _f.defaultValue=getDecodeStr("${defaultValue}");
                          _f.updatable=true; _f.valueProtected=false; _f.visible=true; _f.autoGenId=false;
                          _f.tableName=""; _f.fieldName="${elId}"; _f.tag="select"; _f.editorType="${editType}"; _f.dropDown="";
                          if(typeof(${mask})!="undefined" && "${mask}"!="null")_f.mask="/" + ${mask} + "/";
                         _f.maskErrorMessage="${maskErrorMessage}"; _f.toolTip="${toolTip}";
                          _f.lobDownloadURL=getDecodeStr(""); _f.lobPopupURL=getDecodeStr("");
                            <#-- �ж��� ��select �� CQ��ʽ������dataDic��ʽ ������� CQ��Ҫ�����Ӧ�� CQId ���Ҳ���ֱ�ӷ��룬Ҫ��initdocument����-->
                                <#assign isCQ  = "false">
                                <#assign CQid  = "">
                                <#list translated?split(":")  as key>
                                     <#if key =="CQ">
                                        <#assign isCQ  = "true">
                                        <#break>
                                    </#if>
                                </#list>
                                <#if isCQ ="true">
                                    <#list translated?split(":")  as key>
                                        <#assign CQid  = key>
                                    </#list>
                                </#if>
                                 <#if isCQ ="true">
                                     _f.tag="selectCQ";
                                     _f.viewField="${viewField}";
                                     _f.dropDown="${elId}_DropDown";
                                     _f.dropDownDataset="${CQid}_DropDownDataset";
                                   <#else>
                                     _f.tag="select";
                                     _f.viewField="";
                                      _f.dropDown="";
                                   </#if>
                        <#break>
                    <#default>
                          _f=_t1.addField("${elId}","${elType}"); _f.label="${elDesc}";
                          _f.size="${elSize}"; _f.scale="${scale}"; _f.readOnly="${readonly}";
                          _f.required=${required}; _f.nullable=true; _f.defaultValue=getDecodeStr("${defaultValue}");
                          _f.updatable=true; _f.valueProtected=false; _f.visible=true; _f.autoGenId=false;
                          _f.tableName=""; _f.fieldName="${elId}"; _f.tag=""; _f.editorType="${editType}"; _f.dropDown="";
                          if(typeof(${mask})!="undefined" && "${mask}"!="null")_f.mask="/" + ${mask} + "/";
                          _f.maskErrorMessage="${maskErrorMessage}"; _f.toolTip="${toolTip}";
                          _f.lobDownloadURL=getDecodeStr(""); _f.lobPopupURL=getDecodeStr("");
                        <#break>
                </#switch>
            </#list>
<#--ѭ������������ fId + name �ֶ� -->
    <#assign elements = CommonQueryConfig.elementList>
            <#assign columnInx = 0>
            <#list elements as element>
                    <#assign elEidtType = element.type?default("text")>
                    <#if elEidtType == "Option" >
                            <#assign elId = element.getAnyValue("id")>
                            <#assign elDesc = getElementDesc(CommonQueryConfig.getId(),elId)>
                            <#assign elType = element.getAnyValue("type","string")>
                            <#assign elSize = element.getAnyValue("size","10")>
                            <#assign readonly = element.getAnyValue("readonly","false")>
                            <#assign required = defaultStr(element.getAnyValue("require")?default("false"),"false")>
                            <#assign mask = element.getAnyValue("rules","null")>
                            <#assign maskErrorMessage = element.getAnyValue("errmsg")?default("")>
                            <#assign scale = element.getAnyValue("scale")?default("")>
                            <#assign toolTip = element.getAnyValue("tip")?default(elDesc)>
                            <#if mask?length==0>
                                <#assign mask="null">
                            </#if>
                            <#-- �ж��� ��select �� CQ��ʽ������dataDic��ʽ ������� CQ��Ҫ�����Ӧ�� CQId-->
                                <#assign isCQ  = "false">
                                <#assign CQid  = "">
                                <#list translated?split(":")  as key>
                                     <#if key =="CQ">
                                        <#assign isCQ  = "true">
                                        <#break>
                                    </#if>
                                </#list>
                                <#if isCQ ="true">
                                    <#list translated?split(":")  as key>
                                        <#assign CQid  = key>
                                    </#list>
                                </#if>
                                  _f=_t1.addField("${elId}Name","string"); _f.label="${elDesc}";
                                  _f.size="${elSize}"; _f.scale="${scale}"; _f.readOnly="${readonly}";
                                  _f.required=${required}; _f.nullable=true; _f.defaultValue=getDecodeStr("");
                                  _f.updatable=true; _f.valueProtected=false; _f.visible=true; _f.autoGenId=false;
                                  _f.tableName=""; _f.fieldName="${elId}Name"; _f.tag="selectName"; _f.editorType=""; _f.dropDown="${elId}_DropDown";
                                   <#if isCQ ="true">
                                     _f.dropDownDataset="${CQid}_DropDownDataset";
                                     _f.viewField="${viewField}";
                                   <#else>
                                      _f.dropDownDataset="${elId}_DropDownDataset";
                                      _f.viewField="";
                                   </#if>
                                  if(typeof(${mask})!="undefined" && "${mask}"!="null")_f.mask="/" + ${mask} + "/";
                                  _f.maskErrorMessage="${maskErrorMessage}"; _f.toolTip="${toolTip}";
                                  _f.lobDownloadURL=getDecodeStr(""); _f.lobPopupURL=getDecodeStr("");
                    </#if>
                </#list>
    initDataset(_t1);
    <#-- shen_antonio -->
    initDefaultDataset(_t1);
//-->
</script>
</#macro>

<#--����GroupField��-->
<#macro InterfaceElement element>
<#assign elId = element.getAnyValue("id")>
<#assign textAlign = element.getAnyValue("textalign")?default('')>
<#--
<#assign textAlign = "left">
<#assign dataType = element.getAnyValue("datatype")?default('')>
<#if dataType == "currency">
	<#assign textAlign = "right">
</#if>
-->
<#assign elEidtType = element.type?default('text')>
<#assign label = getElementDesc(CommonQueryConfig.getId(),elId)>
<#assign width = element.getAnyValue("width")?default('')>
<#assign colSpan = element.getAnyValue("colspan")?default('2')>
<#assign vAlign = element.getAnyValue("valign")?default('center')>
<#assign height = element.getAnyValue("height")?default("")>
<#assign readonly = element.getAnyValue("readonly")?default("false")>
<#assign defaultValue = element.getAnyValue("defaultvalue")?default("")>
<#assign targetDataSet = CommonQueryConfig.getId() + "_interface_dataset">
<#assign translated = element.getAnyValue("translated")?default('')>
<#assign required = element.getAnyValue("require")?default('false')>
<#assign viewField = element.getAnyValue("viewfield")?default('')>
<#assign fieldMap = element.getAnyValue("fieldmap")?default('')>
<#assign url = element.getAnyValue("url")?default('')>
<#-- modify by shen_antonio 20080122 -->
<#assign rowSpan = defaultStr(element.getAnyValue("rowspan")?default(1),1)>
<#assign rowSpan = rowSpan?number>
<#-- -->
<#switch elEidtType>
    <#case "TextBox">
            <@htmlEditType.text id=elId  targetDataset=targetDataSet defaultValue=defaultValue width=width colSpan=colSpan
             rowSpan=rowSpan vAlign=vAlign textAlign=textAlign />
        <#break>
    <#case "DateTextBox">
            <@htmlEditType.date id=elId  targetDataset=targetDataSet defaultValue=defaultValue width=width colSpan=colSpan
             rowSpan=rowSpan vAlign=vAlign/>
        <#break>
    <#case "Option">
        <#assign dropdowntype = defaultStr(element.getAnyValue("dropdowntype")?default("dataset"),"dataset")>
        <#assign init = defaultStr(element.getAnyValue("init")?default("true"),"true")>
        <@htmlEditType.selectDataDic lable=label id=elId targetDataset=targetDataSet
            width=width height=height require=required readOnly=readonly defaultValue=defaultValue translated=translated viewField=viewField fieldMap=fieldMap ddtype=dropdowntype init=init url=url/>
        <#break>
    <#case "textarea">
        <@htmlEditType.textarea  id=elId  targetDataset=targetDataSet defaultValue=defaultValue width=width height=height colSpan=colSpan rowSpan=rowSpan vAlign=vAlign/>
        <#break>
    <#case "checkbox">
        <#assign preLable = element.getAnyValue("prelable")?default("")>
        <@htmlEditType.checkbox preLable=preLable lable=label id=elId  targetDataset=targetDataSet defaultValue=defaultValue width=width colSpan=colSpan  rowSpan=rowSpan vAlign=vAlign/>
        <#break>
    <#-- modify by shen_antonio 20080121 -->
    <#case "DataLabel">
        <@htmlEditType.datalabel id=elId  targetDataset=targetDataSet defaultValue=defaultValue width=width colSpan=colSpan
             rowSpan=rowSpan vAlign=vAlign/>
        <#break>
    <#-- -->
    <#default>
        <@htmlEditType.hiddenelement  id=elId value=defaultValue targetDataset=targetDataSet />
        <#break>
</#switch>
</#macro>

<#--
Window����
skin : window����Ƥ��,��Ϊ��,Ĭ��Ϊstandard
-->
<#macro WindowElement skin="standard">
<link rel="stylesheet" type="text/css" href="${contextPath}/templets/lib/themes/xwindows/skins/dhtmlxwindows_${skin}.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/templets/lib/themes/xwindows/dhtmlxwindows.css">
<script language="javascript" src="${contextPath}/templets/lib/dhtmlxcommon.js"></script>
<script language="javascript" src="${contextPath}/templets/lib/dhtmlxwindows.js"></script>
<div id="__WinsDiv" style="display:none; width:100%; height:100%; overflow-y:auto;  overflow-x:auto; border-left:0 gray solid; border-right: 0 gray solid; border-bottom:0 gray solid">
</div>
<script language="javascript">
    var dhxWins = new dhtmlXWindows();
    dhxWins.enableAutoViewport(true);
    dhxWins.vp.style.border = "#909090 0px solid";
    _window_skin = "${skin}";
    dhxWins.setSkin(_window_skin);
</script>
</#macro>

<#--
��ӡ��ť
id                               id (������Ϊ��,���ֽ��棩
title                            �ļ�����
datasetId                        ָ����ӡdatasetId
fieldStr                         field �����ö��ŷָ�
name                             ��ť��
-->
<#macro printButton id title datasetId fieldStr name="��ӡ">
<button extra="button" dataset="${datasetId}"  type="button" id="${id}" submitManager="" autoForm="dtResult" > ${name} </button>
<script language="javascript" src="${contextPath}/templets/lib/datasettoexcel.js"></script>
<script language="javascript">
    var element = document.getElementById("${id}");
    element.onclick=_printButton_onclick;
    element.dataset = "${datasetId}";
    element.url = "#";
    element.headTitle = "${title}";
    element.updateclass = "";
    element.resultDataset = "";
    element.submitDataset = "";
    element.targetFrame = "_self";
    element.fieldStr = "${fieldStr}";
</script>
</#macro>

<#--added by wangpeng 2009/09/21 BMS-1990 begin-->
<#--
����Applet
objectName                  ������������Ϊ�գ�Ĭ��ΪPrintApplet
-->
<#macro reportApplet objectName="PrintApplet">
    <OBJECT classid="clsid:8AD9C840-044E-11D1-B3E9-00805F499D93" width="0px" height="0px" style="" id="${objectName}" name="${objectName}" codebase="http://java.sun.com/update/1.4.2/jinstall-1_4-windows-i586.cab#Version=1,4,0,0">
        <PARAM NAME = CODE VALUE  = "ReporterApplet.class" >  
        <PARAM NAME = CODEBASE VALUE  = "${contextPath}/applets/report" >
        <PARAM NAME = ARCHIVE VALUE  = "jasperreports-2.0.5-applet.jar,jasperreports-2.0.5.jar" >  
        <PARAM NAME = "type" VALUE ="application/x-java-applet;version=1.2.2">  
        <PARAM NAME = "scriptable" VALUE ="true">  
    </OBJECT>  
</#macro>
<#--added by wangpeng 2009/09/21 BMS-1990 end-->

<#macro exportExcelButton id="" dataset="${CommonQueryConfig.getId()}_dataset" fieldStr="" title="" name="����EXCEL">
<button extra="button" dataset="${dataset}" type="button" id="${dataset}_${id}" submitManager="" autoForm="dtResult" > ${name} </button>
<script language="javascript" src="${contextPath}/templets/lib/datasettoexcel.js"></script>
<script language="javascript">
	var element = document.getElementById("${dataset}_${id}");
	element.onclick=_exportButton_onclick;
	element.dataset = "${dataset}";
	element.url = "#";
	element.headTitle = "${title}";
	element.updateclass = "";
	element.resultDataset = "";
	element.submitDataset = "";
	element.targetFrame = "_self";
	element.fieldStr = "${fieldStr}";
	function _exportButton_onclick() {
		if (${dataset}.length < 1) {
			alert("�б����޿������ݵ���");
			return;
		}
		var button=event.srcElement;
		var fieldStr = button.fieldStr;
		var dataset = button.dataset;
		var headTitle = button.headTitle;
		var newDatasetId = "_" + dataset + "_export";
		var orgDataset = getDatasetByID(dataset);
		//alert("dataset.pageSize=" + orgDataset.pageSize);
		//alert("dataset.pageCount=" + orgDataset.pageCount);
		if(orgDataset.pageSize * orgDataset.pageCount > 10000) {
			alert("��ǰ��ѯ�����¼���϶࣬���ʺ����嵼����\n���Ż���ѯ���������ٽ������");
			return;
		}
		_dataset_export = copyDataset(newDatasetId, dataset);
		_dataset_export.pageIndex = 1;
		_dataset_export.pageSize = 100000;
		_dataset_export.setReadOnly(true);	
		_dataset_export.setParameter('export', '1');
		_dataset_export.flushData(1);
		_dataset_export.clearData();
		delete _dataset_export;
		window.open('${contextPath}/templets/common/exportExcel.ftl?datasetid=${CommonQueryConfig.getId()}&title=' + getEncodeStr("${title}") + '&fieldStr=${fieldStr}');
	}
	</script>
</#macro>

<#--
�������ذ�ť����Ҫʵ��${id}_getPrimaryKey()��${id}_getBussType()�ű��������Ի�ȡҵ��������ҵ������
<@CommonQueryMacro.FileDownload id="dwn1" name="�����ļ�1" title="�����ļ�1" />
function dwn1_getPrimaryKey() {
	return "111111";
}
function dwn1_getBussType() {
	return "001";
}
-->
<#macro FileDownload id="" dataset="${CommonQueryConfig.getId()}_dataset" title="" name="����">
<button extra="button" dataset="${dataset}" type="button" id="${dataset}_${id}" submitManager="" autoForm="dtResult" > ${name} </button>
<iframe id="_innerIframe_${id}" style="display:none" src="${contextPath}/pages/frame/download.jsp?id=${id}">
</iframe>
<script language="javascript">
	var element = document.getElementById("${dataset}_${id}");
	element.onclick = _downloadButton_${id}_onclick;
	function _downloadButton_${id}_onclick() {
		var button = event.srcElement;
		button.blur();
		var result = fireUserEvent("${id}_onClickCheck", [button]);
        if(typeof(result) == "boolean" && !result) {
            return false;
        }
		var downloadForm = _innerIframe_${id}.document.getElementById("download_form_${id}");
		downloadForm.action = "${contextPath}/fileDownload.do?primaryKey=" + ${id}_getPrimaryKey() + "&bussType=" + ${id}_getBussType();
		funPreHook(_theme_root + "/loading.gif")
		downloadForm.submit();
		setTimeout(funPostHook, 1000);
		//document.body.removeChild($("_innerIframe_${id}"));
	}
</script>
</#macro>

<#--
�����ϴ���ť����Ҫʵ��${id}_getPrimaryKey()��${id}_getBussType()��${id}_upload_success()�ű�������
�Ի�ȡҵ��������ҵ�����ͣ��ϴ��ɹ���ҳ�����
<@CommonQueryMacro.FileUpload id="upload1" name="�ϴ�" title="�ϴ��ļ�" />
function upload1_getPrimaryKey() {
	return "111111";
}
function upload1_getBussType() {
	return "001";
}
function upload1_upload_success() {
	CustQuery_dataset.flushData(1);
}
��Ҫ����
<@CommonQueryMacro.WindowElement/>
-->
<#macro FileUpload id="" dataset="${CommonQueryConfig.getId()}_dataset" title="�ϴ��ļ�" name="�ϴ�" fileSizeLimit="3 MB" fileTypes="*.*" fileTypeDesc="�����ļ�">
<button extra="button" dataset="${dataset}" type="button" id="${dataset}_${id}" submitManager="" autoForm="dtResult" > ${name} </button>
<script type="text/javascript">
	var element = document.getElementById("${dataset}_${id}");
	element.onclick = _uploadButton_${id}_onclick;
	
	function _uploadButton_${id}_onclick() {
		var button = event.srcElement;
		button.blur();
		var result = fireUserEvent("${id}_onClickCheck", [button]);
        if(typeof(result) == "boolean" && !result) {
            return false;
        }
		var uploadURL = _application_root + "/templets/common/SWFUploader.ftl?";
		var primaryKey = ${id}_getPrimaryKey();
		var bussType = ${id}_getBussType();
		uploadURL +=  "&fileSizeLimit=" + getEncodeStr("${fileSizeLimit}");
		uploadURL +=  "&fileTypes=" + getEncodeStr("${fileTypes}");
		uploadURL +=  "&fileTypeDesc=" + getEncodeStr("${fileTypeDesc}");
		uploadURL +=  "&primaryKey=" + getEncodeStr(primaryKey);
		uploadURL +=  "&bussType=" + getEncodeStr(bussType);
		if (isUserEventDefined("${id}_upload_success")) {
			uploadURL +=  "&_SWF_ID_=${id}_upload";			
		}
		else {
			uploadURL +=  "&_SWF_ID_=";
		}
		showXDialog({id : "${id}_upload", url : uploadURL, title : "${title}", allowMove : true, allowResize : false, width : 518, height : 299});
	}
</script>
</#macro>

<#macro SimpleQuery id="" colNm="4" title="��Ϣ��ѯ" groupwidth="100%" fieldwidth="200px" dataset="${CommonQueryConfig.getId()}_dataset">
<#assign fdWidth=getNumber(fieldwidth)?number>
<link rel="stylesheet" type="text/css" href="${contextPath}/templets/lib/themes/default/extra.css">
<FIELDSET name="group" style="padding:6px; width:${groupwidth}" expand="true">
<LEGEND id="group_line" extra="groupboxtitle">&nbsp;${title}&nbsp;</LEGEND>
<div style="width:100%;">
<div id="group_div" style="">
<table id="${id}_table" style="padding:0px;">
</table>
</div>
</div>
</FIELDSET>
<script language="JavaScript">
function initCallGetter_post() {
	var table = document.getElementById("${id}_table");
	var columnsCount = ${colNm};
	
	var styleEventName = ${id}_fieldStyle;
	var formatValueEventName = ${id}_formatValue; 
	var columnSpanEventName = ${id}_getColumnSpan;
	var record = ${dataset}.firstUnit;
	
	while (record) {
		var row = table.insertRow();
		row.style.height = "20px";
		for(i = 1; i <= columnsCount && record; ) {
			fieldName = record.getString("fieldName");
			var label = document.createElement("LABEL");
			label.innerHTML = fieldName;
			label.className = "fieldlabel";
			label.style.width = "${fdWidth/2}px";
			var cell1 = row.insertCell();
			cell1.style.textAlign = "right";
			cell1.appendChild(label);
						
			fieldValue = record.getString("fieldValue");
			var editor = document.createElement("INPUT");
			editor.type = "text";
			editor.className = "editor";
			if (isUserEventDefined(formatValueEventName)) {
				fieldValue = fireUserEvent(formatValueEventName, [fieldName, fieldValue]);
			}
			editor.value = fieldValue;
			editor.readOnly = true;
			editor.style.color = "dimgray";
			editor.style.width = "${fieldwidth}";
			editor.style.height = "19px";
			editor.style.backgroundColor = "whitesmoke";
			var cell2 = row.insertCell();
			
			var align = "left";
			if (isUserEventDefined(styleEventName)) {
				align = fireUserEvent(styleEventName, [fieldName, editor]);
			}
			editor.style.textAlign = align;
			cell2.appendChild(editor);
			var colspan = 1;
			if (isUserEventDefined(columnSpanEventName)) {
				colspan = fireUserEvent(columnSpanEventName, [fieldName]);
			}
			cell2.colSpan = colspan;
			i += colspan;
			record = record.nextUnit;
		}
	}
} 
</script>
</#macro>
