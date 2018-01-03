<#-- date with extra js
    lable ��ʾ��lable
    id  Ϊdate ��id , ��targetDataset�н����Զ����ɸ�id��Ӧ��field
    targetDataset  ��dataset��id
    defaultValue   Ĭ��ֵ
    width      text��Ŀ��,һ��Ĭ�ϲ���
    require    �Ƿ�����,����Ϊ "true"
    maxLength  ������������λ��
    readOnly   �Ƿ���ֻ����, ֻ��Ϊ"true"
 -->
<#macro date  id  targetDataset defaultValue  width  colSpan="2" rowSpan=1  vAlign="center">
        <td  valign=${vAlign} align="right" style="width: 20%" nowrap>
            <label extra="fieldlabel" id="fldlabel_${id}" dataset="${targetDataset}" dataField="${id}" ></label>
        </td>
        <td  valign="${colSpan?number - 1}" valign=${vAlign}  align="left" style="width: 20%" nowrap>
            <input type="text" extra="editor" id="editor_${id}" name="${id}" dataset="${targetDataset}" dataField="${id}" style="width:${width}">
        </td>
</#macro>

<#-- checkbox      -->
<#macro checkbox  lable id  targetDataset defaultValue width colSpan=colSpan rowSpan=1 vAlign=vAlign>
    <td  valign=${vAlign} align="right" style="width: 20%" nowrap>
            <label extra="fieldlabel" id="fldlabel_${id}" dataset="${targetDataset}" dataField="${id}" ></label>
    </td>
    <td  colspan="${colSpan?number - 1}" valign=${vAlign}  align="left" style="width: 20%" nowrap>
        <input type="checkbox" extra="editor" id="editor_${id}" dataset="${targetDataset}" dataField="${id}"  style="width:${width}">
    </td>
    <script language="javascript">
    </script>
</#macro>

<#macro checkbox2  lable id  targetDataset defaultValue width colSpan=colSpan rowSpan=1 vAlign=vAlign>
        <input type="checkbox" extra="editor" id="editor_${id}" dataset="${targetDataset}" dataField="${id}"  style="width:${width}">
    <script language="javascript">
    </script>
</#macro>

<#--textarea with extra js
    lable               ��ʾ��lable
    id                  Ϊtextarea ��id , ��targetDataset�н����Զ����ɸ�id��Ӧ��field
    targetDataset       ��dataset��id
    defaultValue        Ĭ��ֵ
    size                ָ��size�Ĵ�С֮�����
    width               text��Ŀ��,һ��Ĭ�ϲ���
    require             �Ƿ�����,����Ϊ "true"
    readOnly            �Ƿ�ֻ�� , ֻ��Ϊ"true"
    mask                У�����, ����������ʽ ,�ο�rule.js
    maskErrorMes        У�����󵯳�����У����Ϣ
 -->
<#macro textarea  id  targetDataset defaultValue  width height colSpan="2" rowSpan=1  vAlign="center">
     <td  valign=${vAlign} align="right" style="width: 20%" nowrap>
     <label extra="fieldlabel" id="fldlabel_${id}" dataset="${targetDataset}" dataField="${id}" ></label>
     </td>
     <td  colspan="${colSpan?number - 1}" valign=${vAlign} align="left" style="width: 20%" nowrap>
     <textarea extra="editor" id="${id}" dataset="${targetDataset}" dataField="${id}" style="width:${width};height:${height}"></textarea>
     </td>
</#macro>

<#macro textarea2  id  targetDataset defaultValue  width height colSpan="2" rowSpan=1  vAlign="center">
     <label extra="fieldlabel" id="fldlabel_${id}" dataset="${targetDataset}" dataField="${id}" ></label>
     <textarea extra="editor" id="${id}" dataset="${targetDataset}" dataField="${id}" style="width:${width};height:${height}"></textarea>
</#macro>

<#--text  with extra js
    lable               ��ʾ��lable
    id                  Ϊtextarea ��id , ��targetDataset�н����Զ����ɸ�id��Ӧ��field
    targetDataset       ��dataset��id
    defaultValue        Ĭ��ֵ
    maxLength           ������������λ��
    width               text��Ŀ��,һ��Ĭ�ϲ���
    dataType            ��text������,֧�ֵ������� string,int,float,double
    scale               С��λ��λ��,���textTypeΪfloat,double,����ָ����λ��
    require             �Ƿ�����,����Ϊ "true"
    readOnly            �Ƿ�ֻ�� , ֻ��Ϊ"true"
    mask                У�����, ����������ʽ ,�ο�rule.js
    maskErrorMes        У�����󵯳�����У����Ϣ
 -->
 <#--����Field-->
<#macro text id  targetDataset defaultValue  width textAlign="" colSpan=colSpan rowSpan=1  vAlign="center">
     <td  valign=${vAlign} align="right" style="width: 20%" nowrap>
            <label extra="fieldlabel" id="fldlabel_${id}" dataset="${targetDataset}" dataField="${id}" ></label>
     </td>
     <td colspan="${colSpan?number - 1}"  valign=${vAlign} align="left" style="width: 20%" nowrap>
         <#if textAlign == "">
            <input type="text" extra="editor" id="editor_${id}" name="${id}" dataset="${targetDataset}" dataField="${id}" style="width:${width};">
         <#else>
         	<input type="text" extra="editor" id="editor_${id}" name="${id}" dataset="${targetDataset}" dataField="${id}" style="width:${width};text-align:${textAlign};">
         </#if>
     </td>
</#macro>

 <#--����Field-->
<#macro text2 id label targetDataset defaultValue  width textAlign="" colSpan=colSpan rowSpan=1  vAlign="center">
	<#if textAlign == "">
		<input type="text" extra="editor" id="editor_${id}" name="${id}" dataset="${targetDataset}" dataField="${id}" style="width:${width};">
	<#else>
		<input type="text" extra="editor" id="editor_${id}" name="${id}" dataset="${targetDataset}" dataField="${id}" style="width:${width};text-align:${textAlign};">
	</#if>
</#macro>

 <#--����password-->
<#macro password id targetDataset defaultValue  width  colSpan=colSpan rowSpan=1  vAlign="center">
     <td  valign=${vAlign} align="right" style="width: 20%" nowrap>
            <label extra="fieldlabel" id="fldlabel_${id}" dataset="${targetDataset}" dataField="${id}" ></label>
     </td>
     <td colspan="${colSpan?number - 1}"  valign=${vAlign} align="left" style="width: 20%" nowrap>
            <input type="password" extra="editor" id="editor_${id}" name="${id}" dataset="${targetDataset}" dataField="${id}" style="width:${width};">
     </td>
</#macro>



 <#--����datalabel-->
<#macro datalabel id targetDataset defaultValue  width  colSpan=colSpan rowSpan=1  vAlign="center">
     <td  valign=${vAlign} align="right" style="width: 20%" nowrap>
            <label extra="fieldlabel" id="fldlabel_${id}" dataset="${targetDataset}" dataField="${id}" ></label>
     </td>
     <td colspan="${colSpan?number - 1}"  valign=${vAlign} align="left" style="width: 20%" nowrap>
            <label extra="datalabel" id="datalabel_${id}" name="${id}" dataset="${targetDataset}" dataField="${id}" ></label>
     </td>
</#macro>
 <#--����datalabel ,������td-->
<#macro datalabel2 id targetDataset defaultValue  width  colSpan=colSpan rowSpan=1  vAlign="center">
            <label extra="datalabel" id="datalabel_${id}" name="${id}" dataset="${targetDataset}" dataField="${id}" ></label>
</#macro>

<#--  with extra js
    lable  ��ʾ��lable
    id     Ϊselect ��id , Ҫ��fileMap �б��������id��ֵӳ�� , ������targetDataset�н����Զ����ɸ�id��Ӧ��field
    selsetValues   Ϊselect ������ѡ��
    targetDataset  ��dataset��id
    defaultValue   �������Ĭ��ֵ
    fileMap  ֵӳ�� ,��ʽ��"contactmanidtype=datano;contactmanidtypename=dataname"  ���б������id��ӳ��,��dataset���ֶη�=���,select��ֵ��=�ұ�
    ddsfiles ����ѡ���Ӧ��dataset��������
    field    ����ѡ���Ӧ��dataset��Ҫ��ʾ������ֵ
    width    ������Ŀ��,һ��Ĭ�ϲ���,������Ŀ�Ȼᱻѡ��ֵ�Զ��ſ�
    height   ������ĸ߶�,����ָ����ֵ,���������������ʾ���������ѡ��
    require  ������ѡ���Ƿ���� ,����Ϊ "true"
    readOnly �Ƿ��ǿɶ���
 -->
<#macro select lable id  selsetValues  targetDataset defaultValue fileMap ddsfiles field width height require readOnly colSpan=2>
    <#assign fileMapString ="${fileMap}">
    <#assign fileMapString = fileMapString+";${id}Name=${field}">
    <td  valign=center align="right" style="width: 20%" nowrap>
        <label extra="fieldlabel" id="fldlabel_${id}" dataset="${targetDataset}" dataField="${id}Name" ></label>
    </td>
    <td colspan="${colSpan?number - 1}" valign=center align="left" style="width: 20%" nowrap>
        <input type="text" extra="editor" id="editor_${id}"
                    name="${id}" dataset="${targetDataset}" dataField="${id}Name" dropDown="${id}_DropDown" style="width:${width}">
    </td>
      <script language="javascript">
      if(typeof(${id}_DropDown)=="undefined"){
          var required = "${require}";
          var ${id}_selectValues = "${selsetValues}";
          if(${id}_selectValues.length>0&&${id}_selectValues.substring(${id}_selectValues.length-1,${id}_selectValues.length)==";"){
            ${id}_selectValues  = ${id}_selectValues.substring(0,${id}_selectValues.length-1);
          }
          if(required!="true"){
                ${id}_selectValues = ";"+${id}_selectValues;
          }
      }
      if(typeof(${id}_DropDownDataset)=="undefined"){
          //dropdown dataset
          var ${id}_DropDownDataset=createDataset("${id}_DropDownDataset","",${id}_selectValues);
          var ${id}dds_t=${id}_DropDownDataset,${id}dds_f;${id}dds_t.readOnly="${readOnly}";${id}dds_t.pageSize=1000;
          ${id}dds_t.pageIndex=1;${id}dds_t.pageCount=1;${id}dds_t.masterDataset="";  ${id}dds_t.type="dropdown";
          ${id}dds_t.references="";${id}dds_t.submitData="allchange";${id}dds_t.autoLoadPage=true;${id}dds_t.autoLoadDetail=true;
          ${id}dds_t.downloadUrl=getDecodeStr("~2fextraservice~2fdownloaddata");
          ${id}dds_t.sessionKey="";${id}dds_t.insertOnEmpty=false;${id}dds_t.tag="";${id}dds_t.initDocumentFlag=false;

          if ("${ddsfiles}"){
              var temp = "${ddsfiles}";
              var temps=temp.split(",");
              for(var i=0;i<temps.length;i++){
                  ${id}dds_f=${id}dds_t.addField(temps[i],"string");${id}dds_f.label="${lable}";${id}dds_f.size=0;${id}dds_f.scale=0;${id}dds_f.readOnly=false;
                  ${id}dds_f.required=false;
                  ${id}dds_f.nullable=true;${id}dds_f.defaultValue=getDecodeStr("");${id}dds_f.updatable=true;${id}dds_f.valueProtected=false;
                  ${id}dds_f.visible=true;${id}dds_f.autoGenId=false;${id}dds_f.tableName="";
                  ${id}dds_f.fieldName=temps[i].split("=")[1];${id}dds_f.tag="";${id}dds_f.editorType="";${id}dds_f.dropDown="";${id}dds_f.mask=getDecodeStr("");
                  ${id}dds_f.maskErrorMessage=getDecodeStr("");
                  ${id}dds_f.toolTip=getDecodeStr("");${id}dds_f.lobDownloadURL=getDecodeStr("");${id}dds_f.lobPopupURL=getDecodeStr("");
                }
             }
            initDataset(${id}dds_t);

          //dropdown
          var ${id}_DropDown =createDropDown("${id}_DropDown");
          var ${id}dd_t=${id}_DropDown;${id}dd_t.type="dataset";${id}dd_t.cache=true;${id}dd_t.fixed=true;
          ${id}dd_t.fieldMap="${fileMapString}";
          ${id}dd_t.autoDropDown=true;${id}dd_t.editable=true;
          if("${height}") {${id}dd_t.height = "${height}"};
          else{ ${id}dd_t.height=0};${id}dd_t.tag="";
          ${id}dd_t.dataset="${id}_DropDownDataset";${id}dd_t.fields="${field}";${id}dd_t.showHeader=false;
          //_array_dropdown[_array_dropdown.length]=${id}dd_t;
          initDropDown(${id}dd_t);
        }else{
        }
      </script>
</#macro>
 <#--������td-->
<#macro select_2 lable id  selsetValues  targetDataset defaultValue fileMap ddsfiles field width height require readOnly>
    <#assign fileMapString ="${fileMap}">
    <#assign fileMapString = fileMapString+";${id}Name=${field}">
        <label extra="fieldlabel" id="fldlabel_${id}" dataset="${targetDataset}" dataField="${id}Name" ></label>
        <input type="text" extra="editor" id="editor_${id}"
                    name="${id}" dataset="${targetDataset}" dataField="${id}Name" dropDown="${id}_DropDown" style="width:${width}">
      <script language="javascript">
      if(typeof(${id}_DropDown)=="undefined"){
          var required = "${require}";
          var ${id}_selectValues = "${selsetValues}";
          if(${id}_selectValues.length>0&&${id}_selectValues.substring(${id}_selectValues.length-1,${id}_selectValues.length)==";"){
            ${id}_selectValues  = ${id}_selectValues.substring(0,${id}_selectValues.length-1);
          }
          if(required!="true"){
                ${id}_selectValues = ";"+${id}_selectValues;
          }
      } if(typeof(${id}_DropDownDataset)=="undefined"){
          //dropdown dataset
          var ${id}_DropDownDataset=createDataset("${id}_DropDownDataset","",${id}_selectValues);
          var ${id}dds_t=${id}_DropDownDataset,${id}dds_f;${id}dds_t.readOnly="${readOnly}";${id}dds_t.pageSize=1000;
          ${id}dds_t.pageIndex=1;${id}dds_t.pageCount=1;${id}dds_t.masterDataset="";  ${id}dds_t.type="dropdown";
          ${id}dds_t.references="";${id}dds_t.submitData="allchange";${id}dds_t.autoLoadPage=true;${id}dds_t.autoLoadDetail=true;
          ${id}dds_t.downloadUrl=getDecodeStr("~2fextraservice~2fdownloaddata");
          ${id}dds_t.sessionKey="";${id}dds_t.insertOnEmpty=false;${id}dds_t.tag="";${id}dds_t.initDocumentFlag = false;

          if ("${ddsfiles}"){
              var temp = "${ddsfiles}";
              var temps=temp.split(",");
              for(var i=0;i<temps.length;i++){
                  ${id}dds_f=${id}dds_t.addField(temps[i],"string");${id}dds_f.label="${lable}";${id}dds_f.size=0;${id}dds_f.scale=0;${id}dds_f.readOnly=false;
                  ${id}dds_f.required=false;
                  ${id}dds_f.nullable=true;${id}dds_f.defaultValue=getDecodeStr("");${id}dds_f.updatable=true;${id}dds_f.valueProtected=false;
                  ${id}dds_f.visible=true;${id}dds_f.autoGenId=false;${id}dds_f.tableName="";
                  ${id}dds_f.fieldName=temps[i].split("=")[1];${id}dds_f.tag="";${id}dds_f.editorType="";${id}dds_f.dropDown="";${id}dds_f.mask=getDecodeStr("");
                  ${id}dds_f.maskErrorMessage=getDecodeStr("");
                  ${id}dds_f.toolTip=getDecodeStr("");${id}dds_f.lobDownloadURL=getDecodeStr("");${id}dds_f.lobPopupURL=getDecodeStr("");
                }
             }
            initDataset(${id}dds_t);

          //dropdown
          var ${id}_DropDown =createDropDown("${id}_DropDown");
          var ${id}dd_t=${id}_DropDown;${id}dd_t.type="dataset";${id}dd_t.cache=true;${id}dd_t.fixed=true;
          ${id}dd_t.fieldMap="${fileMapString}";
          ${id}dd_t.autoDropDown=true;${id}dd_t.editable=true;
          if("${height}") {${id}dd_t.height = "${height}"};
          else{ ${id}dd_t.height=0};${id}dd_t.tag="";
          ${id}dd_t.dataset="${id}_DropDownDataset";${id}dd_t.fields="${field}";${id}dd_t.showHeader=false;
          //_array_dropdown[_array_dropdown.length]=${id}dd_t;
          initDropDown(${id}dd_t);
            }else{
        }
      </script>
</#macro>

<#-- �����û��Զ��� ���ɵ�select -->
<#macro select2 lable id  CQid  targetDataset defaultValue fileMap  field width height require readOnly type="dataset" colSpan=2>
    <td  valign=center align="right" style="width: 20%" nowrap>
        <label extra="fieldlabel" id="fldlabel_${id}" dataset="${targetDataset}" dataField="${id}Name" ></label>
    </td>
    <td colspan="${colSpan?number - 1}" valign=center align="left" style="width: 20%" nowrap >
        <input type="text" extra="editor" id="editor_${id}"
                    name="${id}" dataset="${targetDataset}" dataField="${id}Name" dropDown="${id}_DropDown" style="width:${width}">
    </td>
      <script language="javascript">

      //dropdown
      if(typeof(${id}_DropDown)=="undefined"){
      var ${id}_DropDown =createDropDown("${id}_DropDown");
       <#if type?string?lower_case=="dynamictree">
         <#assign ddType="dynamic">
         <#assign viewType="tree">
      <#else>
         <#assign ddType=type>
         <#assign viewType="table">
      </#if>
      var ${id}dd_t=${id}_DropDown;${id}dd_t.type="${ddType}";${id}dd_t.cache=true;${id}dd_t.fixed=true;
      ${id}dd_t.fieldMeta="";${id}dd_t.fieldMap="${fileMap}";${id}_DropDown.sessionKey = "dd";
      ${id}dd_t.autoDropDown=true;${id}dd_t.editable=true;${id}dd_t.tag="";${id}dd_t.viewType="${viewType}";
      ${id}dd_t.dataset="${CQid}_DropDownDataset";${id}dd_t.fields="${field}";${id}dd_t.showHeader=false;
      <#if height != "">
        ${id}dd_t.height=${height};
      <#else>${id}dd_t.height=0</#if>
      //_array_dropdown[_array_dropdown.length]=${id}dd_t;
      initDropDown(${id}dd_t);
      }else{
      }

        //_paramMap.put("${id}Name","${defaultValue}");
      </script>
</#macro>

<#-- �����û��Զ��� ���ɵ�select -->
<#macro selectCustom lable id  targetDataset defaultValue fileMap  field width height require readOnly type="custom" url="" notd="">
    <#if notd = "">
        <td  valign=center align="right" style="width: 20%" nowrap>
            <label extra="fieldlabel" id="fldlabel_${id}" dataset="${targetDataset}" dataField="${id}Name" ></label>
        </td>
        <td  valign=center align="left" style="width: 20%" nowrap>
            <input type="text" extra="editor" id="editor_${id}"
                    name="${id}" dataset="${targetDataset}" dataField="${id}Name" dropDown="${id}_DropDown" style="width:${width}">
        </td>
    <#else>
        <input type="hidden" extra="editor" id="editor_${id}"
                    name="${id}" dataset="${targetDataset}" dataField="${id}Name" dropDown="${id}_DropDown" style="width:${width}">
    </#if>
      <script language="javascript">
      if(typeof(${id}_DropDown)=="undefined"){
        //dropdown dataset
        var ${id}_DropDownDataset=createDataset("${id}_DropDownDataset","","");
        var ${id}dds_t=${id}_DropDownDataset,${id}dds_f;${id}dds_t.readOnly="${readOnly}";${id}dds_t.pageSize=1000;
        ${id}dds_t.pageIndex=1;${id}dds_t.pageCount=1;${id}dds_t.masterDataset="";  ${id}dds_t.type="drowdown";
        ${id}dds_t.references="";${id}dds_t.submitData="allchange";${id}dds_t.autoLoadPage=true;${id}dds_t.autoLoadDetail=true;
        ${id}dds_t.downloadUrl=getDecodeStr("~2fextraservice~2fdownloaddata");
        ${id}dds_t.sessionKey="";${id}dds_t.insertOnEmpty=false;${id}dds_t.tag="";
        initDataset(${id}dds_t);

        var ${id}_DropDown =createDropDown("${id}_DropDown");
        var ${id}dd_t=${id}_DropDown;${id}dd_t.type="${type}";${id}dd_t.cache=true;${id}dd_t.fixed=true;
        ${id}dd_t.fieldMeta="";${id}dd_t.fieldMap="${fileMap}";${id}_DropDown.sessionKey = "dd";
        ${id}dd_t.autoDropDown=true;${id}dd_t.editable=true;${id}dd_t.tag="";${id}dd_t.url="${url}";
        ${id}dd_t.targetDataset="${targetDataset}";${id}dd_t.dataset="${id}_DropDownDataset";
        ${id}dd_t.fieldId="${id}";
      <#if height != "">
        ${id}dd_t.height=${height};
      <#else>${id}dd_t.height=0</#if>
        _array_dropdown[_array_dropdown.length]=${id}dd_t;
        initDropDown(${id}dd_t);
      }else{
      }
      </script>
</#macro>

<#-- �����û��Զ��� ���ɵ�select ����TR-->
<#macro selectCustom_2 lable id  targetDataset defaultValue fileMap  field width height require readOnly type="custom" url="">
      <label extra="fieldlabel" id="fldlabel_${id}" dataset="${targetDataset}" dataField="${id}Name" ></label>
      <input type="text" extra="editor" id="editor_${id}"
                    name="${id}" dataset="${targetDataset}" dataField="${id}Name" dropDown="${id}_DropDown" style="width:${width}">
      <script language="javascript">
      if(typeof(${id}_DropDown)=="undefined"){
        //dropdown dataset
        var ${id}_DropDownDataset=createDataset("${id}_DropDownDataset","","");
        var ${id}dds_t=${id}_DropDownDataset,${id}dds_f;${id}dds_t.readOnly="${readOnly}";${id}dds_t.pageSize=1000;
        ${id}dds_t.pageIndex=1;${id}dds_t.pageCount=1;${id}dds_t.masterDataset="";  ${id}dds_t.type="drowdown";
        ${id}dds_t.references="";${id}dds_t.submitData="allchange";${id}dds_t.autoLoadPage=true;${id}dds_t.autoLoadDetail=true;
        ${id}dds_t.downloadUrl=getDecodeStr("~2fextraservice~2fdownloaddata");
        ${id}dds_t.sessionKey="";${id}dds_t.insertOnEmpty=false;${id}dds_t.tag="";
        initDataset(${id}dds_t);

        var ${id}_DropDown =createDropDown("${id}_DropDown");
        var ${id}dd_t=${id}_DropDown;${id}dd_t.type="${type}";${id}dd_t.cache=true;${id}dd_t.fixed=true;
        ${id}dd_t.fieldMeta="";${id}dd_t.fieldMap="${fileMap}";${id}_DropDown.sessionKey = "dd";
        ${id}dd_t.autoDropDown=true;${id}dd_t.editable=true;${id}dd_t.tag="";${id}dd_t.url="${url}";
        ${id}dd_t.targetDataset="${targetDataset}";${id}dd_t.dataset="${id}_DropDownDataset";
        ${id}dd_t.fieldId="${id}";
      <#if height != "">
        ${id}dd_t.height=${height};
      <#else>${id}dd_t.height=0</#if>
        _array_dropdown[_array_dropdown.length]=${id}dd_t;
        initDropDown(${id}dd_t);
      }else{
      }
      </script>
</#macro>

<#-- �����û��Զ��� ���ɵ�select ������td -->
<#macro select2_2 lable id  CQid  targetDataset defaultValue fileMap  field width height require readOnly type="dataset">
        <label extra="fieldlabel" id="fldlabel_${id}" dataset="${targetDataset}" dataField="${id}Name" ></label>
        <input type="text" extra="editor" id="editor_${id}"
                    name="${id}" dataset="${targetDataset}" dataField="${id}Name" dropDown="${id}_DropDown" style="width:${width}">
        <script language="javascript">

      //dropdown
      if(typeof(${id}_DropDown)=="undefined"){
      var ${id}_DropDown =createDropDown("${id}_DropDown");
       <#if type?string?lower_case=="dynamictree">
         <#assign ddType="dynamic">
         <#assign viewType="tree">
      <#else>
         <#assign ddType=type>
         <#assign viewType="table">
      </#if>
      var ${id}dd_t=${id}_DropDown;${id}dd_t.type="${ddType}";${id}dd_t.cache=true;${id}dd_t.fixed=true;
      ${id}dd_t.fieldMeta="";${id}dd_t.fieldMap="${fileMap}";${id}_DropDown.sessionKey = "dd";
      ${id}dd_t.autoDropDown=true;${id}dd_t.editable=true;${id}dd_t.tag="";${id}dd_t.viewType="${viewType}";
      ${id}dd_t.dataset="${CQid}_DropDownDataset";${id}dd_t.fields="${field}";${id}dd_t.showHeader=false;
      <#if height != "">
        ${id}dd_t.height=${height};
      <#else>${id}dd_t.height=0</#if>
      //_array_dropdown[_array_dropdown.length]=${id}dd_t;
      initDropDown(${id}dd_t);
      }else{
      }

        //_paramMap.put("${id}Name","${defaultValue}");
      </script>
</#macro>

<#-- �����û��Զ��� ���ɵ�select �� select ����  -->
<#macro selectGetterHidden lable id targetDataset  width height require readOnly defaultValue CQid   fileMap=""  field="" ddtype="dataset">
      <input type="hidden" extra="editor" id="editor_${id}"
                    name="${id}" dataset="${targetDataset}" dataField="${id}Name" dropDown="${id}_DropDown" style="width:${width}">
      <script language="javascript">
     //if(eval(typeof(${id}_DropDown))=="undefined"){
     if(typeof(${id}_DropDown)=="undefined"){
      //dropdown
          var ${id}_DropDown =createDropDown("${id}_DropDown");
          <#if ddtype?string?lower_case=="dynamictree">
            <#assign type="dynamic">
            <#assign viewType="tree">
          <#else>
            <#assign type=ddtype>
            <#assign viewType="table">
          </#if>
          var ${id}dd_t=${id}_DropDown;${id}dd_t.type="${type}";${id}dd_t.cache=true;${id}dd_t.fixed=true;
          ${id}dd_t.fieldMap="${fileMap}";${id}_DropDown.sessionKey = "dd";${id}dd_t.viewType="${viewType}";
          ${id}dd_t.autoDropDown=true;${id}dd_t.editable=true; ${id}dd_t.tag="";
          ${id}dd_t.dataset="${CQid}_DropDownDataset";${id}dd_t.fields="${field}";${id}dd_t.showHeader=false;
          <#if height != "">${id}dd_t.height=${height};
          <#else>${id}dd_t.height=0</#if>
          //_array_dropdown[_array_dropdown.length]=${id}dd_t;
          initDropDown(${id}dd_t);

          //_paramMap.put("${id}Name","${defaultValue}");
      }
      </script>
</#macro>

<#macro selectHidden lable id targetDataset width height require readOnly defaultValue translated viewField="" fieldMap="" ddtype="dataset" init="true">
    <#assign type = "">
    <#assign value = "">
    <#assign transAry = translated?split(":")>

    <#if transAry[0]?exists && transAry[1]?exists>
        <#assign type = transAry[0]>
        <#assign value = transAry[1]>
    </#if>
    <#if type=="CQ">
        <#assign CQid = value>
        <@CommonQueryForSelect id="${CQid}" init=init require="${require}">
            <@selectGetterHidden lable=lable id=id targetDataset="${targetDataset}"
                 width=width height=height require=require readOnly=readOnly defaultValue=defaultValue  CQid=CQid fileMap=fieldMap field=viewField ddtype=ddtype>
            </@selectGetterHidden>
        </@CommonQueryForSelect>
    <#elseif type=="LIST">
        <#assign values = value>
        <#assign fMap=id + "=" + "data">
        <@selectDataHidden lable=lable id=id  selsetValues=values targetDataset=targetDataset fileMap=fMap ddsfiles="data,dataName" defaultValue=defaultValue field="dataName"   width=width height=height require=require readOnly=readOnly />
     <#else>
        <#assign fMap=id + "=" + "data">
        <@selectDataDicHidden lable=lable id=id  translated=translated targetDataset=targetDataset fileMap=fMap ddsfiles="data,dataName" defaultValue=defaultValue field="dataName"   width=width height=height require=require readOnly=readOnly />
     </#if>
</#macro>

<#--selectHidden ��������,���ڴ������dataTable,���ǲ�����group,����dataTable��select�Ǵ��ڿɱ༭״̬����� -->
<#macro selectDataHidden lable id  selsetValues targetDataset fileMap  ddsfiles defaultValue  field width height require readOnly >
    <#assign values = selsetValues>
    <#assign fileMapString ="${fileMap}">
    <#assign fileMapString =  fileMapString + ";${id}Name=${field}">

      <input type="hidden" extra="editor" id="editor_${id}"
                    name="${id}" dataset="${targetDataset}" dataField="${id}Name" dropDown="${id}_DropDown" style="width:${width}">

      <script language="javascript">
      if(typeof(${id}_DropDown)=="undefined"){
          var ${id}_selectValues = "${values}";
          if(${id}_selectValues.length>0&&${id}_selectValues.substring(${id}_selectValues.length-1,${id}_selectValues.length)==";"){
            ${id}_selectValues  = ${id}_selectValues.substring(0,${id}_selectValues.length-1);
          }
            var required = "${require}";
          if(required!="true"){
                ${id}_selectValues = ";"+${id}_selectValues;
          }
      }
      if(typeof(${id}_DropDownDataset)=="undefined"){
          //dropdown dataset
          var ${id}_DropDownDataset=createDataset("${id}_DropDownDataset","",${id}_selectValues);
          var ${id}dds_t=${id}_DropDownDataset,${id}dds_f;${id}dds_t.readOnly="${readOnly}";${id}dds_t.pageSize=1000;
          ${id}dds_t.pageIndex=1;${id}dds_t.pageCount=1;${id}dds_t.masterDataset="";${id}dds_t.type="dropdown";
          ${id}dds_t.references="";${id}dds_t.submitData="allchange";${id}dds_t.autoLoadPage=false;${id}dds_t.autoLoadDetail=true;
          ${id}dds_t.downloadUrl=getDecodeStr("~2fextraservice~2fdownloaddata");
          ${id}dds_t.sessionKey="";${id}dds_t.insertOnEmpty=false;${id}dds_t.tag="";${id}dds_t.initDocumentFlag = false;

          if ("${ddsfiles}"){
              var temp = "${ddsfiles}";
              var temps=temp.split(",");
              for(var i=0;i<temps.length;i++){
                  ${id}dds_f=${id}dds_t.addField(temps[i],"string");${id}dds_f.label="${lable}";${id}dds_f.size=0;${id}dds_f.scale=0;${id}dds_f.readOnly=false;
                  ${id}dds_f.required=false;
                  ${id}dds_f.nullable=true;${id}dds_f.defaultValue=getDecodeStr("");${id}dds_f.updatable=true;${id}dds_f.valueProtected=false;
                  ${id}dds_f.visible=true;${id}dds_f.autoGenId=false;${id}dds_f.tableName="";
                  ${id}dds_f.fieldName=temps[i].split("=")[1];${id}dds_f.tag="";${id}dds_f.editorType="";${id}dds_f.dropDown="";${id}dds_f.mask=getDecodeStr("");
                  ${id}dds_f.maskErrorMessage=getDecodeStr("");
                  ${id}dds_f.toolTip=getDecodeStr("");${id}dds_f.lobDownloadURL=getDecodeStr("");${id}dds_f.lobPopupURL=getDecodeStr("");
                }
             }
            initDataset(${id}dds_t);

          //dropdown
          var ${id}_DropDown =createDropDown("${id}_DropDown");
          var ${id}dd_t=${id}_DropDown;${id}dd_t.type="dataset";${id}dd_t.cache=true;${id}dd_t.fixed=true;
          ${id}dd_t.fieldMap="${fileMapString}";
          ${id}dd_t.autoDropDown=true;${id}dd_t.editable=true;
          if("${height}") {${id}dd_t.height = "${height}"};
          else{ ${id}dd_t.height=0};${id}dd_t.tag="";
          ${id}dd_t.dataset="${id}_DropDownDataset";${id}dd_t.fields="${field}";${id}dd_t.showHeader=false;
          //_array_dropdown[_array_dropdown.length]=${id}dd_t;
          initDropDown(${id}dd_t);
          }else{}

      </script>

</#macro>

<#--selectDataDicHidden ��������,���ڴ������dataTable,���ǲ�����group,����dataTable��select�Ǵ��ڿɱ༭״̬����� -->
<#macro selectDataDicHidden lable id  translated targetDataset fileMap  ddsfiles defaultValue  field width height require readOnly >
    <#assign values = sysDicStr(translated)>
    <@selectDataHidden lable=lable id=id  selsetValues=values targetDataset=targetDataset fileMap=fileMap ddsfiles=ddsfiles defaultValue=defaultValue field=field  width=width height=height require=require readOnly=readOnly />
</#macro>

<#--DataDic ��������  -->
<#macro selectDataDic lable id targetDataset width height require readOnly defaultValue translated viewField="" fieldMap="" ddtype="dataset" init="true" url="" notd="" colSpan=2>
<#-- modify by shen_antonio 20080121 -->
    <#assign type = "">
    <#assign value = "">
    <#assign transAry = translated?split(":")>
    <#if transAry[0]?exists && transAry[1]?exists>
        <#assign type = transAry[0]>
        <#assign value = transAry[1]>
    </#if>

    <#if ddtype=="dataset">
        <#if type=="CQ">
            <#assign CQid = value>
            <@CommonQueryForSelect id="${CQid}" init=init require="${require}">
                <@selectGetter lable=lable id=id targetDataset="${targetDataset}"
                    width=width height=height require=require readOnly=readOnly defaultValue=defaultValue  CQid=CQid fieldmap=fieldMap filed=viewField type=ddtype>
                </@selectGetter>
            </@CommonQueryForSelect>
        <#elseif type=="LIST">
            <#assign values = value>
            <#assign fMap=id + "=" + "data">
            <@select lable=lable id=id  selsetValues=values targetDataset=targetDataset fileMap=fMap ddsfiles="data,dataName" colSpan=colSpan defaultValue=defaultValue field="dataName"   width=width height=height require=require readOnly=readOnly />
        <#else>
            <#assign values = sysDicStr(translated)>
            <#assign fMap=id + "=" + "data">
            <@select lable=lable id=id  selsetValues=values targetDataset=targetDataset fileMap=fMap ddsfiles="data,dataName" colSpan=colSpan defaultValue=defaultValue field="dataName"   width=width height=height require=require readOnly=readOnly />
        </#if>
     <#elseif ddtype=="dynamic">
        <#if type=="CQ">
            <#assign CQid = value>
            <@CommonQueryForSelect id="${CQid}" init=init require="${require}">
                <@selectGetter lable=lable id=id targetDataset="${targetDataset}" colSpan=colSpan
                    width=width height=height require=require readOnly=readOnly defaultValue=defaultValue  CQid=CQid fieldmap=fieldMap filed=viewField type=ddtype>
                </@selectGetter>
            </@CommonQueryForSelect>
        <#else>
        </#if>
     <#elseif ddtype=="dynamictree">
        <#if type=="CQ">
            <#assign CQid = value>
            <@CommonQueryForSelect id="${CQid}" init=init require="${require}">
                <@selectGetter lable=lable id=id targetDataset="${targetDataset}"
                    width=width height=height require=require readOnly=readOnly defaultValue=defaultValue  CQid=CQid fieldmap=fieldMap filed=viewField type=ddtype>
                </@selectGetter>
            </@CommonQueryForSelect>
        <#else>
        </#if>
     <#elseif ddtype=="custom">
        <@selectCustom lable=lable id=id  targetDataset=targetDataset defaultValue=defaultValue fileMap=fieldMap  field=viewField width=width height=height require=require readOnly=readOnly type=ddtype url=url notd=notd/>
     <#else>

     </#if>
</#macro>

<#--DataDic ��������  ���ɵ�select������td-->
<#macro selectDataDic2 lable id targetDataset width height require readOnly defaultValue translated viewField="" fieldMap="" ddtype="dataset" init="true" url="">
<#-- modify by shen_antonio 20080121 -->
    <#assign type = "">
    <#assign value = "">
    <#assign transAry = translated?split(":")>

    <#if transAry[0]?exists && transAry[1]?exists>
        <#assign type = transAry[0]>
        <#assign value = transAry[1]>
    </#if>

    <#if ddtype=="dataset">
        <#if type=="CQ">
            <#assign CQid = value>
            <@CommonQueryForSelect id="${CQid}" init=init require="${require}">
                <@selectGetter2 lable=lable id=id targetDataset="${targetDataset}"
                    width=width height=height require=require readOnly=readOnly defaultValue=defaultValue  CQid=CQid fieldmap=fieldMap filed=viewField type=ddtype>
                </@selectGetter2>
            </@CommonQueryForSelect>
        <#elseif type=="LIST">
            <#assign values = value>
            <#assign fMap=id + "=" + "data">
            <@select_2 lable=lable id=id  selsetValues=values targetDataset=targetDataset fileMap=fMap ddsfiles="data,dataName" defaultValue=defaultValue field="dataName"   width=width height=height require=require readOnly=readOnly />
        <#else>
            <#assign values = sysDicStr(translated)>
            <#assign fMap=id + "=" + "data">
            <@select_2 lable=lable id=id  selsetValues=values targetDataset=targetDataset fileMap=fMap ddsfiles="data,dataName" defaultValue=defaultValue field="dataName"   width=width height=height require=require readOnly=readOnly />
        </#if>
     <#elseif ddtype=="dynamic">
        <#if type=="CQ">
            <#assign CQid = value>
            <@CommonQueryForSelect id="${CQid}" init=init require="${require}">
                <@selectGetter2 lable=lable id=id targetDataset="${targetDataset}"
                width=width height=height require=require readOnly=readOnly defaultValue=defaultValue  CQid=CQid fieldmap=fieldMap filed=viewField type=ddtype>
                </@selectGetter2>
            </@CommonQueryForSelect>
        <#else>
        </#if>
     <#elseif ddtype=="dynamictree">
        <#if type=="CQ">
            <#assign CQid = value>
            <@CommonQueryForSelect id="${CQid}" init=init require="${require}">
                <@selectGetter2 lable=lable id=id targetDataset="${targetDataset}"
                    width=width height=height require=require readOnly=readOnly defaultValue=defaultValue  CQid=CQid fieldmap=fieldMap filed=viewField type=ddtype>
                </@selectGetter2>
            </@CommonQueryForSelect>
        <#else>
        </#if>
     <#elseif ddtype=="custom">
        <@selectCustom_2 lable=lable id=id  targetDataset=targetDataset defaultValue=defaultValue fileMap=fieldMap  field=viewField width=width height=height require=require readOnly=readOnly type=ddtype url=url />
     <#else>

     </#if>
</#macro>

<#--�Զ����������� , ����getter����  -->
<#macro selectGetter lable id targetDataset width height require readOnly defaultValue CQid fieldmap filed type="dataset" colSpan=2>
    <@select2 lable=lable id=id  targetDataset=targetDataset fileMap=fieldmap CQid=CQid  defaultValue=defaultValue field=filed   width=width height=height require=require readOnly=readOnly type=type colSpan=colSpan/>
</#macro>
<#--�Զ����������� , ����getter����  ������td -->
<#macro selectGetter2 lable id targetDataset width height require readOnly defaultValue CQid fieldmap filed type="dataset">
    <@select2_2 lable=lable id=id  targetDataset=targetDataset fileMap=fieldmap CQid=CQid  defaultValue=defaultValue field=filed   width=width height=height require=require readOnly=readOnly type=type/>
</#macro>



<#macro hiddenelement id value targetDataset>
<input type="hidden" extra="editor" id="editor_${id}" name="${id}"
        dataset="${targetDataset}" dataField="${id}" >
</#macro>

<#--��ť
    id
    targetDataset
    desc
    defaultOperation
    submitManager
    autoForm
    url
    updateclass
    nexturl
-->
<#-- modify by shen_antonio 20080121 -->
<#macro button id targetDataset desc defaultOperation submitManager="_submitmanager_default" autoForm="dtResult" url="" updateClass="" resultDataset="" submitDataset="" targetFrame="_self">
<button extra="button" dataset="${targetDataset}" type="button" id="${id}" defaultOperation="${defaultOperation}" submitManager="${submitManager}" autoForm="${autoForm}" > ${desc} </button>
<script language="javascript">
    var element = document.getElementById("${id}");
    element.onclick=_button_onclick_new;
    element.dataset = "${targetDataset}";
    element.url = "${url}";
    element.updateclass = "${updateClass}";
    element.resultDataset = "${resultDataset}";
    element.submitDataset = "${submitDataset}";
    element.targetFrame = "${targetFrame}";
</script>
</#macro>

<#--ͨ�ò�ѯͷģ�� �����û��Զ������ɵ�select-->
<#macro CommonQueryForSelect id init="true" require="false">
<#assign DropDownCommonQueryConfig = statics["com.huateng.commquery.config.CommonQueryUtil"].getCommonQueryBean(id)>
<@DropDownDataSet init=init require=require/>
<#nested>
</#macro>

<#--����DropDownDataSetģ��-->
<#macro DropDownDataSet init="true" require="false">
<#assign specStr="nextPage|everyPage|pageNm|currentPage|fieldString|recordString|recordOrigString">
<script language="javascript">
    var ${DropDownCommonQueryConfig.getId()}_DropDownDataset=createDataset("${DropDownCommonQueryConfig.getId()}_DropDownDataset","","");
    ${DropDownCommonQueryConfig.getId()}_DropDownDataset.flushData=dataset_flushData_new;
    var ${DropDownCommonQueryConfig.getId()}_t=${DropDownCommonQueryConfig.getId()}_DropDownDataset,_f;
    ${DropDownCommonQueryConfig.getId()}_t.readOnly=false;
    ${DropDownCommonQueryConfig.getId()}_t.pageSize=${DropDownCommonQueryConfig.getAnyValue("pagesize")?default("10")};
    ${DropDownCommonQueryConfig.getId()}_t.pageIndex=1;
    ${DropDownCommonQueryConfig.getId()}_t.pageCount=1;
    ${DropDownCommonQueryConfig.getId()}_t.masterDataset="";
    ${DropDownCommonQueryConfig.getId()}_t.references="";
    ${DropDownCommonQueryConfig.getId()}_t.submitData="allchange";
    ${DropDownCommonQueryConfig.getId()}_t.autoLoadPage=false;
    ${DropDownCommonQueryConfig.getId()}_t.autoLoadDetail=true;
    ${DropDownCommonQueryConfig.getId()}_t.downloadUrl=getDecodeStr("~2fextraservice~2fdownloaddata");
    ${DropDownCommonQueryConfig.getId()}_t.insertOnEmpty=false;
    ${DropDownCommonQueryConfig.getId()}_t.tag="";
    ${DropDownCommonQueryConfig.getId()}_t.type="dropdown";
    ${DropDownCommonQueryConfig.getId()}_t.sessionKey="dd";
    ${DropDownCommonQueryConfig.getId()}_t.setParameter("CQId","${DropDownCommonQueryConfig.getId()}","string");

    <#assign paramString = configParamConver()>
    converStr2DataSetParameter("${paramString}",${DropDownCommonQueryConfig.getId()}_t);
    ${DropDownCommonQueryConfig.getId()}_t.setParameter("nextPage",${DropDownCommonQueryConfig.getId()}_t.pageIndex);
    ${DropDownCommonQueryConfig.getId()}_t.setParameter("everyPage",${DropDownCommonQueryConfig.getId()}_t.pageSize);
    ${DropDownCommonQueryConfig.getId()}_t.setParameter("_session_key",${DropDownCommonQueryConfig.getId()}_t.sessionKey);
    ${DropDownCommonQueryConfig.getId()}_t.init = "${init}";
    ${DropDownCommonQueryConfig.getId()}_t.require = "${require}";
    ${DropDownCommonQueryConfig.getId()}_t.initDocumentFlag = false;
            <#assign fieldMap = DropDownCommonQueryConfig.fields>
            <#assign fields = fieldMap.keySet()>
            <#assign field = "">
            <#assign fDesc = "",fVal = "",fStat = "">
            <#assign columnInx = 0>
            <#list fields as fId>
                          _f=${DropDownCommonQueryConfig.getId()}_t.addField("${fId}","string"); _f.label="";
                          _f.size=""; _f.scale=""; _f.readOnly=false;
                          _f.required=false; _f.nullable=true; _f.defaultValue=getDecodeStr("");
                          _f.updatable=true; _f.valueProtected=false; _f.visible=true; _f.autoGenId=false;
                          _f.tableName=""; _f.fieldName="${fId}"; _f.tag=""; _f.editorType=""; _f.dropDown="";
                          _f.mask= "";
                          _f.maskErrorMessage=""; _f.toolTip="";
                          _f.lobDownloadURL=getDecodeStr(""); _f.lobPopupURL=getDecodeStr("");
            </#list>
            initDataset(${DropDownCommonQueryConfig.getId()}_t);
</script>
</#macro>

<#function configParamConver>
<#assign paramString = "">
<#assign paramIds = DropDownCommonQueryConfig.getParameters().keySet()>
<#assign paramVal = "">
<#list paramIds as paramId>
    <#assign paramVal = DropDownCommonQueryConfig.getParameter(paramId).getAnyValue("value","")>
    <#assign paramVal = encodeStr(paramVal)>
    <#if paramString!="">
        <#assign paramString = paramString + ";" + paramId + "," + paramVal>
    <#else>
        <#assign paramString = paramId + "," + paramVal>
    </#if>
</#list>
<#return paramString>
</#function>

