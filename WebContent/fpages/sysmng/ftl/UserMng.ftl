<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>

<@CommonQueryMacro.page title="�û�����">
<script language="javascript" src="${contextPath}/dwr/interface/PrivateAction.js"></script>
<@CommonQueryMacro.CommonQuery id="UserMng" init="false" navigate="true" submitMode="allchange">
<table align="center">
	<tr><td>
		<table align="center">
			<tr>
    			<td valign="top">
       				<@CommonQueryMacro.Interface id="intface" label="�������ѯ����" colNm=4 showButton="true" />
				</td>
    		</tr>
		</table>
	</td></tr>
    <tr><td>
		<table>
		<tr>
	      	<td valign="top">
				<@CommonQueryMacro.PagePilot id="ddresult" maxpagelink="3"/>
	      		<@CommonQueryMacro.DataTable id="datatable1" fieldStr="oprNo[100],userName[100],brhNo[200],teleno[100],status[60]" readonly="false"/>
	      	</td>
	    </tr>
	    <tr>
	    	<td align="center">
	    		<@CommonQueryMacro.Button id="btnAdd"/>&nbsp;&nbsp;
	      		<@CommonQueryMacro.Button id="btnDel"/>&nbsp;&nbsp;
	      		<@CommonQueryMacro.Button id="btnSaveUser"/>&nbsp;&nbsp;
	      		<@CommonQueryMacro.Button id="btnRole"/>&nbsp;&nbsp;
	      		<@CommonQueryMacro.Button id="btnResetPasswd"/>
	    	</td>
	    </tr>
		</table>
    </td></tr>
</table>
</@CommonQueryMacro.CommonQuery>
<@CommonQueryMacro.WindowElement/>

<script language="JavaScript" for="window" event="onload">
	setReadOnlyForEmptyDataset(UserMng_dataset);
	/** ����ҳ���ʼ��Ľ��� */
	setFocus("UserMng_interface", "oprNo");
	clearHisInputData(UserMng_interface_dataset);
</script>

<script language="JavaScript">
function UserMng_dataset_afterScroll(dataset) {
	setReadOnlyForEmptyDataset(UserMng_dataset);
}

function btnSaveUser_needCheck(button) {
	//return false;
}

function btnSaveUser_postSubmit(button) {
	UserMng_dataset.flushData(UserMng_dataset.pageIndex);
	var retParam = button.returnParam;
	if(retParam) {
		var resultMsg = retParam.resultMsg;
		if(resultMsg && "" != resultMsg) {
			alert(resultMsg);
		}
	}
}

function datatable1_changeTableRowEvent(table){
	var vOldOprNo = UserMng_dataset.getValue("oldOprNo");
	if('' == vOldOprNo.trim()){
		UserMng_dataset.setFieldReadOnly("oprNo",false);
	}else{
		UserMng_dataset.setFieldReadOnly("oprNo",true);
	}
}

function UserMng_dataset_afterChange(dataset,field){
	if("oprno"==field.name){
		UserMng_dataset.setValue("oldOprNo",UserMng_dataset.getValue("oprNo"));
	}
}

function btnAdd_needCheck(button) {
	if(!UserMng_dataset.record) {
		return false;
	}
	return true;
}


function btnDel_onClickCheck(button) {
	if(UserMng_dataset.record && confirm("�Ƿ�ɾ����ǰ��¼��")) {
		return true;
	}
	return false;
}

function btnRole_onClick(button) {
	var vOprId = UserMng_dataset.getValue("oprId");
	if(!isNaN(vOprId) && 0 != vOprId) {
		var paramMap = new Map();
		paramMap.put("oprId", 0 + UserMng_dataset.getValue("oprId"));
		var userName = getEncodeStr(UserMng_dataset.getValue("userName"));
		paramMap.put("userName", userName);
		loadPageWindows("userWin", "�����λ", "/fpages/sysmng/ftl/UserRoleRelation.ftl", paramMap, "winZone", 500, 400);
	}else{
		alert("�뱣���û���Ϣ���ٸ����û������λ��");
	}
}

function btnResetPasswd_onClick(button) {
	if(isNaN(UserMng_dataset.getValue("oprId")) || UserMng_dataset.getValue("oprId") == 0) {
		alert("���û������ѱ����¼��");
		return;
	}
	var paramMap = new Map();
	paramMap.put("oprId", 0 + UserMng_dataset.getValue("oprId"));
	paramMap.put("oprNo", UserMng_dataset.getValue("oprNo"));
	var userName = getEncodeStr(UserMng_dataset.getValue("userName"));
	paramMap.put("userName", userName);
	paramMap.put("brhNo", UserMng_dataset.getValue("brhNo"));
	var brhName = getEncodeStr(UserMng_dataset.getValue("brhNoName"));
	paramMap.put("brhName", brhName);
	loadPageWindows("userWin", "��������", "/fpages/sysmng/ftl/ResetPasswd.ftl", paramMap, "winZone", 400, 240);
}

//����û����Ƿ�Ψһ
function UserMng_dataset_onSetValue(dataset, field, value) {
	if("oprNo" == field.fieldName) {
		if(value && value.length != 6) {
			alert("�û��ű���Ϊ6λ���֣�");
			return;
		}
		for(i = 0; i < value.length; i ++) {
			if(value.charAt(i) < '0' || value.charAt(i) > '9') {
				alert("�û��ű���Ϊ6λ���֣�");
				return;
			}
		}
		if(value && "" != value) {
			var record = UserMng_dataset.find(["oprNo"],[value]);
			if(record){
				alert("�û����Ѵ��ڣ�");
				return;
			}else{
				if(checkDataIsOnly(value)){
					return;
				}
			}
		}
	}
	return value;
}

function checkDataIsOnly(value) {
	var rtn = false;
	dwr.engine.setAsync(false);
	PrivateAction.isHaveOpr(value,function checkResult(data) {
		if(data != 0) {
			alert("�û����Ѵ��ڣ�");
			rtn = true;
		}
	});
	dwr.engine.setAsync(true);
	return rtn;
}
</script>
</@CommonQueryMacro.page>
