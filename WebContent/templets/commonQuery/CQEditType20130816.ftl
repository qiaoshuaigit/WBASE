
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
<#macro date lable id  targetDataset defaultValue maxLength width require readOnly >
		<td  valign=center align="right" style="width: 20%" nowrap>
		<label extra="fieldlabel" id="fldlabel_${id}" dataset="${targetDataset}" dataField="${id}" ></label>
		</td>
		 <td  valign=center align="left" style="width: 20%" nowrap>
		<input type="text" extra="editor" id="editor_${id}" name="${id}" dataset="${targetDataset}" dataField="${id}" style="width:${width}">
		</td>
		<script language="javascript">

		var dropDownDate=createDropDown("dropDownDate");
		var ${id}date_t=dropDownDate;${id}date_t.type="date";
		${id}date_t.cache=true;${id}date_t.fixed=false;${id}date_t.fieldMap="";
		${id}date_t.autoDropDown=true;${id}date_t.editable=false;
		${id}date_t.height=0;${id}date_t.tag="";_array_dropdown[_array_dropdown.length]=${id}date_t;
		initDropDown(${id}date_t);

		var ${id}_t = getDatasetByID("${targetDataset}"),${id}_f="";

		${id}_f=_t.addField("${id}","date");${id}_f.label=getDecodeStr("${lable}");
		${id}_f.size="${maxLength}";${id}_f.scale=0;${id}_f.readOnly="${readOnly}";${id}_f.required="${require}";
		${id}_f.nullable=true;${id}_f.defaultValue=getDecodeStr("");
		${id}_f.updatable=true;${id}_f.valueProtected=false;${id}_f.visible=true;${id}_f.autoGenId=false;${id}_f.tableName="";
		${id}_f.fieldName="${id}";
		${id}_f.tag="";${id}_f.editorType="";${id}_f.dropDown="";${id}_f.mask=getDecodeStr("");
		${id}_f.maskErrorMessage=getDecodeStr("");${id}_f.toolTip=getDecodeStr("");${id}_f.lobDownloadURL=getDecodeStr("");
		${id}_f.lobPopupURL=getDecodeStr("");
		//_t.setValue("${id}","${defaultValue}");
		</script>
</#macro>


<#-- checkbox ����Ĳ���Ϊcheckbox��˳��,��Ҫ��һ�͵�����readonly ����Ĳ���Ϊ"0,2" , ���ȫ��Ϊreadonly ����Ϊ"all"  readOnlyIndex -->
<#macro checkbox lable checkboxpras  targetDataset defaultValues require readOnlyIndex>
	<#assign index ="">
	<#assign lableString ="">
	<#assign inputString ="">
	<#assign operationsMap = "${checkboxpras}">
	<#list operationsMap?split(";")  as key>
	     <#assign index =key?index_of(":")>
		 <#assign lableName = key?substring(0,index)>
		 <#assign id =key?substring(index+1)>
		 <#assign inputString = inputString + "<input type=\"checkbox\" extra=\"editor\" id=\"editor_${id}\" dataset=\"${targetDataset}\" dataField=\"${id}\" style=\"\" >" >
		 <#assign inputString = inputString + "<label>${lableName}</lable>" + "&nbsp&nbsp">
	</#list>
	<#if require = "true">
	 <#assign lableString ="<font color=\"red\">*</font>${lable}">
	<#else>
	  <#assign lableString ="${lable}">
	</#if>
		${lableString}
		${inputString}
    <script language="javascript">
    	var ${targetDataset}=createDataset("${targetDataset}","",",");
		var _t=${targetDataset},_f;_t.readOnly=false;_t.pageSize=100;_t.pageIndex=1;_t.pageCount=1;_t.masterDataset="";
		_t.references="";_t.submitData="allchange";_t.autoLoadPage=false;_t.autoLoadDetail=true;
		_t.downloadUrl=getDecodeStr("~2fextraservice~2fdownloaddata");
		_t.sessionKey="";
		_t.insertOnEmpty=false;_t.tag="";

		var readOnlyArraytemp =  "${readOnlyIndex}";
		var readOnlyArray = readOnlyArraytemp.split(",");
		if ("${checkboxpras}"){
	      var temp = "${checkboxpras}";
	      var checkboxpras=temp.split(";");
	      for(var i=0;i<checkboxpras.length;i++){
	      	 	_f=_t.addField(checkboxpras[i].split(":")[1],"boolean");_f.label=getDecodeStr("");
				_f.size=0;_f.scale=0;
				if("${readOnlyIndex}"=="all")
					_f.readOnly=true;
				else{
					for(var j=0;j<readOnlyArray.length;j++){
						if(i.toString()==readOnlyArray[j]){
							_f.readOnly=true;
							break;
						}else{
						    _f.readOnly=false;
						}
					}
				}
				_f.required=false;_f.nullable=true;
				_f.defaultValue="";_f.updatable=true;_f.valueProtected=false;
				_f.visible=true;_f.autoGenId=false;_f.tableName="";
				_f.fieldName=checkboxpras[i].split(":")[1];
				_f.tag="";_f.editorType="";_f.dropDown="";_f.mask=getDecodeStr("");_f.maskErrorMessage=getDecodeStr("");
				_f.toolTip=getDecodeStr("");_f.lobDownloadURL=getDecodeStr("");_f.lobPopupURL=getDecodeStr("");
	  		}
	     }
		initDataset(_t);
</script>
</#macro>


<#macro textarea lable id  targetDataset defaultValue size width  require readOnly>
	 <label extra="fieldlabel" id="fldlabel_${id}" dataset="${targetDataset}" dataField="${id}" ></label>
	 <textarea extra="editor" id="editor_${id}" dataset="${targetDataset}" dataField="${id}" style="width:${width};"></textarea>
<script language="javascript">
    	var ${targetDataset}=createDataset("${targetDataset}","",";");
		var _t=${targetDataset},_f;_t.readOnly=false;_t.pageSize=100;_t.pageIndex=1;_t.pageCount=1;_t.masterDataset="";
		_t.references="";_t.submitData="allchange";_t.autoLoadPage=false;_t.autoLoadDetail=true;
		_t.downloadUrl=getDecodeStr("~2fextraservice~2fdownloaddata");
		_t.sessionKey="";
		_t.insertOnEmpty=false;_t.tag="";

		_f=_t.addField("${id}","string");_f.label=getDecodeStr("${lable}");
		_f.size="${size}";_f.scale=0;_f.readOnly="false";_f.required="${require}";
		_f.nullable=true;_f.defaultValue=getDecodeStr("${defaultValue}");_f.updatable=true;
		_f.valueProtected=false;_f.visible=true;_f.autoGenId=false;_f.tableName="";
		_f.fieldName="${id}";_f.tag="";_f.editorType="";_f.dropDown="";
		_f.mask=getDecodeStr("");_f.maskErrorMessage=getDecodeStr("");
		_f.toolTip=getDecodeStr("");_f.lobDownloadURL=getDecodeStr("");
		_f.lobPopupURL=getDecodeStr("");

</script>

<script language="javascript">
	initDataset(_t);

</script>
</#macro>

<#--text  with extra js
	lable               ��ʾ��lable
	id  				Ϊtextarea ��id , ��targetDataset�н����Զ����ɸ�id��Ӧ��field
	targetDataset  		��dataset��id
	defaultValue   		Ĭ��ֵ
	maxLength		    ������������λ��
	width               text��Ŀ��,һ��Ĭ�ϲ���
	textType            ��text������,֧�ֵ������� string,int,float,double
	scale               С��λ��λ��,���textTypeΪfloat,double,����ָ����λ��
	require        		�Ƿ�����,����Ϊ "true"
	readOnly            �Ƿ�ֻ�� , ֻ��Ϊ"true"
	mask                У�����, ����������ʽ ,�ο�rule.js
	maskErrorMes        У�����󵯳�����У����Ϣ
 -->
<#macro text lable id targetDataset defaultValue maxLength width textType scale  require readOnly mask maskErrorMes>
	 <td  valign=center align="right" style="width: 20%" nowrap>
	 		<label extra="fieldlabel" id="fldlabel_${id}" dataset="${targetDataset}" dataField="${id}" ></label>
	 </td>
	 <td  valign=center align="left" style="width: 20%" nowrap>
	 		<input type="text" extra="editor" id="editor_${id}" name="${id}" dataset="${targetDataset}" dataField="${id}" style="width:${width};">
	 </td>
<script language="javascript">
        var ${id}_t = getDatasetByID("${targetDataset}"),${id}_f="";
		${id}_f=${id}_t.addField("${id}","${textType}");${id}_f.label=getDecodeStr("${lable}");
		${id}_f.size="${maxLength}";${id}_f.scale="${scale}";${id}_f.readOnly="${readOnly}";${id}_f.required="${require}";
		${id}_f.nullable=true;${id}_f.defaultValue=getDecodeStr("");${id}_f.updatable=true;
		${id}_f.valueProtected=false;${id}_f.visible=true;${id}_f.autoGenId=false;${id}_f.tableName="";
		${id}_f.fieldName="${id}";${id}_f.tag="";${id}_f.editorType="";${id}_f.dropDown="";
		if("${mask}") ${id}_f.mask= "/" + ${mask} + "/" ;
		else ${id}_f.mask= "" ;
		${id}_f.maskErrorMessage=getDecodeStr("${maskErrorMes}");
		${id}_f.toolTip=getDecodeStr("");${id}_f.lobDownloadURL=getDecodeStr("");
		${id}_f.lobPopupURL=getDecodeStr("");
</script>
</#macro>

<#-- select with extra js
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
<#macro select lable id  selsetValues  targetDataset defaultValue fileMap ddsfiles field width height require readOnly>
	<#assign fileMapString ="${fileMap}">
	<#assign fileMapString =  "${id}name=${field};"+fileMapString>

	<td  valign=center align="right" style="width: 20%" nowrap>
    	<label extra="fieldlabel" id="fldlabel_${id}" dataset="${targetDataset}" dataField="${id}name" ></label>
    </td>
    <td  valign=center align="left" style="width: 20%" nowrap>
    	<input type="text" extra="editor" id="editor_${id}"
					name="${id}" dataset="${targetDataset}" dataField="${id}name" dropDown="${id}_DropDown" style="width:${width}">
	</td>
	  <script language="javascript">
	  var ${id}_selectValues = "${selsetValues}";
	  if(${id}_selectValues.length>0&&${id}_selectValues.substring(${id}_selectValues.length-1,${id}_selectValues.length)==";"){
	  	${id}_selectValues  = ${id}_selectValues.substring(0,${id}_selectValues.length-1);
	  }
	  ${id}_selectValues = ";"+${id}_selectValues;

	  //dropdown dataset
	  var ${id}_DropDownDataset=createDataset("${id}_DropDownDataset","",${id}_selectValues);
	  var ${id}dds_t=${id}_DropDownDataset,${id}dds_f;${id}dds_t.readOnly="${readOnly}";${id}dds_t.pageSize=1000;
	  ${id}dds_t.pageIndex=1;${id}dds_t.pageCount=1;${id}dds_t.masterDataset="";${id}dds_t.type="dropdown";
	  ${id}dds_t.references="";${id}dds_t.submitData="allchange";${id}dds_t.autoLoadPage=false;${id}dds_t.autoLoadDetail=true;
	  ${id}dds_t.downloadUrl=getDecodeStr("~2fextraservice~2fdownloaddata");
	  ${id}dds_t.sessionKey="";${id}dds_t.insertOnEmpty=false;${id}dds_t.tag="";

	  if ("${ddsfiles}"){
	      var temp = "${ddsfiles}";
	      var temps=temp.split(",");
	      for(var i=0;i<temps.length;i++){
			  ${id}dds_f=${id}dds_t.addField(temps[i],"string");${id}dds_f.label="";${id}dds_f.size=0;${id}dds_f.scale=0;${id}dds_f.readOnly=false;
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
	  _array_dropdown[_array_dropdown.length]=${id}dd_t;
	  initDropDown(${id}dd_t);


	  var ${id}_t = getDatasetByID("${targetDataset}"),${id}_f="";

	  if ("${fileMapString}"){
	      var temp1 = "${fileMapString}";
	      var temps1=temp1.split(";");
	      for(var i=0;i<temps1.length;i++){
				  ${id}_f=${id}_t.addField(temps1[i].split("=")[0],"string");
				  ${id}_f.label=getDecodeStr("${lable}");
				  ${id}_f.size=0;${id}_f.scale=0;${id}_f.readOnly=false;
				  ${id}_f.required="${require}";
				  ${id}_f.nullable=true;${id}_f.defaultValue=getDecodeStr("");${id}_f.updatable=true;${id}_f.valueProtected=false;${id}_f.visible=true;${id}_f.autoGenId=false;
				  ${id}_f.tableName="";${id}_f.fieldName=temps1[i].split("=")[0];${id}_f.tag="";${id}_f.editorType="";${id}_f.dropDown="";${id}_f.mask=getDecodeStr("");
				  ${id}_f.maskErrorMessage=getDecodeStr("");${id}_f.toolTip=getDecodeStr("");
				  ${id}_f.lobDownloadURL=getDecodeStr("");${id}_f.lobPopupURL=getDecodeStr("");
			  }
	     }
	    _paramMap.put("${id}name","${defaultValue}");


	  </script>
</#macro>
<#-- �����û��Զ��� ���ɵ�select -->
<#macro select2 lable id  CQid  targetDataset defaultValue fileMap  field width height require readOnly>
	<td  valign=center align="right" style="width: 20%" nowrap>
    	<label extra="fieldlabel" id="fldlabel_${id}" dataset="${targetDataset}" dataField="${id}name" ></label>
    </td>
    <td  valign=center align="left" style="width: 20%" nowrap>
    	<input type="text" extra="editor" id="editor_${id}"
					name="${id}" dataset="${targetDataset}" dataField="${id}name" dropDown="${id}_DropDown" style="width:${width}">
	</td>
	  <script language="javascript">


      //dropdown
	  var ${id}_DropDown =createDropDown("${id}_DropDown");
	  var ${id}dd_t=${id}_DropDown;${id}dd_t.type="dataset";${id}dd_t.cache=true;${id}dd_t.fixed=true;
	  ${id}dd_t.fieldMap="${fileMap}";
	  ${id}dd_t.autoDropDown=true;${id}dd_t.editable=true;
	  if("${height}") {${id}dd_t.height = "${height}"};
	  else{ ${id}dd_t.height=0};${id}dd_t.tag="";
	  ${id}dd_t.dataset="${CQid}_DropDownDataset";${id}dd_t.fields="${field}";${id}dd_t.showHeader=false;
	  _array_dropdown[_array_dropdown.length]=${id}dd_t;
	  initDropDown(${id}dd_t);

  	  var ${id}_t = getDatasetByID("${targetDataset}"),${id}_f="";
	  
	  if ("${fileMap}"){
	      var temp1 = "${fileMap}";
	      var temps1=temp1.split(";");
	      for(var i=0;i<temps1.length;i++){
				  ${id}_f=${id}_t.addField(temps1[i].split("=")[0],"string");
				  ${id}_f.label=getDecodeStr("${lable}");
				  ${id}_f.size=0;${id}_f.scale=0;${id}_f.readOnly=false;
				  ${id}_f.required="${require}";
				  ${id}_f.nullable=true;${id}_f.defaultValue=getDecodeStr("");${id}_f.updatable=true;${id}_f.valueProtected=false;${id}_f.visible=true;${id}_f.autoGenId=false;
				  ${id}_f.tableName="";${id}_f.fieldName=temps1[i].split("=")[0];${id}_f.tag="";${id}_f.editorType="";${id}_f.dropDown="";${id}_f.mask=getDecodeStr("");
				  ${id}_f.maskErrorMessage=getDecodeStr("");${id}_f.toolTip=getDecodeStr("");
				  ${id}_f.lobDownloadURL=getDecodeStr("");${id}_f.lobPopupURL=getDecodeStr("");
			  }
	     }

	    //_paramMap.put("${id}name","${defaultValue}");


	  </script>
</#macro>

<#-- staticSelect with extra js
	lable ��ʾ��lable
	id  Ϊselect ��id , ��targetDataset�н����Զ����ɸ�id��Ӧ��field
	selsetValues  Ϊselect ������ѡ��,���ʽΪ"0-��;1-��",�������ѡ����"1-��"�ͻ��"1"����targetDataset��id
	targetDataset  ��dataset��id
	defaultValue  �������Ĭ��ֵ
	width  ������Ŀ��,һ��Ĭ�ϲ���,������Ŀ�Ȼᱻѡ��ֵ�Զ��ſ�
	height ������ĸ߶�,����ָ����ֵ,���������������ʾ���������ѡ��
	require  ������ѡ���Ƿ���� ,����Ϊ "true"
	readOnly �Ƿ���ֻ����, ֻ��Ϊ"true"
 -->
<#macro staticSelect lable id  selsetValues  targetDataset  defaultValue width height require readOnly defaultValue>
	<td  valign=center align="right" style="width: 20%" nowrap>
    <label extra="fieldlabel" id="fldlabel_${id}" dataset="${targetDataset}" dataField="${id}" ></label>
    </td>
    <td  valign=center align="left" style="width: 20%" nowrap>
	<input type="text" extra="editor" id="editor_${id}" dataset="${targetDataset}" dataField="${id}" dropDown="${id}_DropDown" style="width:${width}">
	 </td>
	  <script language="javascript">
      //dropdown
		var ${id}_DropDown=createDropDown("${id}_DropDown");
		var ${id}dd_t=${id}_DropDown;
		${id}dd_t.type="list";${id}dd_t.cache=true;
		${id}dd_t.fixed=true;${id}dd_t.fieldMap="";
		${id}dd_t.autoDropDown=true;
		${id}dd_t.editable=true;
		if("${height}") {${id}dd_t.height = "${height}"};
	    else{ ${id}dd_t.height=0};
	    ${id}dd_t.tag="";${id}dd_t.mapValue="false";
		${id}dd_t.items="${selsetValues}";
		${id}dd_t.fields = "dataname";
		_array_dropdown[_array_dropdown.length]=${id}dd_t;
		initDropDown(${id}dd_t);

	  // targetDataset

      var ${id}_t = getDatasetByID("${targetDataset}"),${id}_f="";
	  ${id}_f=${id}_t.addField("${id}","string");
	  ${id}_f.label=getDecodeStr("${lable}");
	  ${id}_f.size=0;${id}_f.scale=0;${id}_f.readOnly="${readOnly}";${id}_f.required="${require}";
	  ${id}_f.nullable=true;${id}_f.defaultValue=getDecodeStr("");${id}_f.updatable=true;${id}_f.valueProtected=false;${id}_f.visible=true;${id}_f.autoGenId=false;
	  ${id}_f.tableName="";${id}_f.fieldName="${id}";${id}_f.tag="";${id}_f.editorType="";${id}_f.dropDown="";${id}_f.mask=getDecodeStr("");
	  ${id}_f.maskErrorMessage=getDecodeStr("");${id}_f.toolTip=getDecodeStr("");${id}_f.lobDownloadURL=getDecodeStr("");${id}_f.lobPopupURL=getDecodeStr("");

	  </script>
</#macro>

<#--DataDic �������� ���ڽ���where�����option -->
<#macro selectDataDic lable id targetDataset width height require readOnly defaultValue translated>
<#assign values = sysDicStr(translated)>
<#assign fMap=id + "=" + "datano">
<@select lable=lable id=id  selsetValues=values targetDataset=targetDataset fileMap=fMap ddsfiles="datano,dataname" defaultValue=defaultValue field="dataname"   width=width height=height require=require readOnly=readOnly />
</#macro>

<#--�Զ����������� , ����getter����  -->
<#macro selectGetter lable id targetDataset width height require readOnly defaultValue CQid fieldmap filed>
<@select2 lable=lable id=id  targetDataset=targetDataset fileMap=fieldmap CQid=CQid  defaultValue=defaultValue field=filed   width=width height=height require=require readOnly=readOnly />
</#macro>

<#--DataDic �������� ���ڽ���filed�����select -->
<#macro selectDataDic2 lable id  translated targetDataset width height require readOnly defaultValue>
<#assign values = sysDicStr(translated)>
<#assign fMap=id + "=" + "datano">
<@select lable=lable id=id  selsetValues=values targetDataset=targetDataset fileMap=fMap ddsfiles="datano,dataname" defaultValue=defaultValue field="dataname" width=width height=height require=require readOnly=readOnly/>
</#macro>

<#macro hiddenelement id value targetDataset>
<input type="hidden" extra="editor" id="editor_${id}" name="${id}"
dataset="${targetDataset}" dataField="${id}" >
<script language="javascript">
var ${id}_t = getDatasetByID("${targetDataset}"),${id}_f="";
	  ${id}_f=${id}_t.addField("${id}","string");
	    ${id}_f.readOnly=true;
		${id}_f.required=false;
		${id}_f.nullable=false;
		${id}_f.defaultValue="${value}";
		${id}_f.updatable=true;
		${id}_f.valueProtected=true;
		${id}_f.visible=false;
		${id}_f.autoGenId=false;
		${id}_f.tableName="";
		${id}_f.fieldName="${id}";
		_paramMap.put("${id}","${value}");

</script>
</#macro>
