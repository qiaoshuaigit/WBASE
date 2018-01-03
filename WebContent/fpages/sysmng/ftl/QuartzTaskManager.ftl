<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>

<@CommonQueryMacro.page title="��ʱ/��ѵ�������">
<script language="javascript" src="${contextPath}/dwr/interface/PrivateAction.js"></script>
<@CommonQueryMacro.CommonQueryTab id="YW_NAVIGATE" navigate="false" currentTab="quartzTaskTab">
<@CommonQueryMacro.CommonQuery id="QuartzTaskManager" init="true" navigate="true" submitMode="allchange">
<table align="center">
    <tr>
    	 <td valign="top">
             <@CommonQueryMacro.PagePilot id="ddresult" maxpagelink="3"/>
             <@CommonQueryMacro.DataTable id="datatable" fieldStr="taskName[200],triggerType[80],taskDefine[140],status[60],nextExecuteTime[185]" />
         </td>
    </tr>
    <tr>
        <td align="center">
             <@CommonQueryMacro.Button id="saveBtn"/>&nbsp;&nbsp;
             <@CommonQueryMacro.Button id="pausedBtn"/>&nbsp;&nbsp;
             <@CommonQueryMacro.Button id="resumeBtn"/>&nbsp;&nbsp;
             <@CommonQueryMacro.Button id="refreshBtn"/>
        </td>
    </tr>
    <tr>
    	<td>
Spring CronTriggerBean ��ʱ/��ѵ��������˵��
<div style="overflow-x:auto;overflow-y:scroll;width:680px;height:240px;border-top:1 gray solid; border-left:1 gray solid; border-right:1 gray solid; border-bottom:1 gray solid">
<pre>
��� ˵��  �Ƿ����  ������д��ֵ         �����ͨ���  
 1   ��    ��        0-59                 , - * / 
 2   ��    ��        0-59                 , - * / 
 3   Сʱ  ��        0-23                 , - * / 
 4   ��    ��        1-31                 , - * ? / L W 
 5   ��    ��        1-12 or JAN-DEC      , - * / 
 6   ��    ��        1-7 or SUN-SAT       , - * ? / L #  
 7   ��    ��        empty �� 1970-2099   , - * / 

ͨ���˵��:
* ��ʾ����ֵ. ����:�ڷֵ��ֶ������� "*",��ʾÿһ���Ӷ��ᴥ����
? ��ʾ��ָ��ֵ��ʹ�õĳ���Ϊ����Ҫ���ĵ�ǰ��������ֶε�ֵ������:Ҫ��ÿ�µ�10�Ŵ���
  һ�������������������ܼ���������Ҫ��λ�õ��Ǹ��ֶ�����Ϊ"?" ��������Ϊ 0 0 0 10 * ?
- ��ʾ���䡣���� ��Сʱ������ "10-12",��ʾ 10,11,12�㶼�ᴥ����
, ��ʾָ�����ֵ�����������ֶ������� "MON,WED,FRI" ��ʾ��һ�����������崥��
/ ���ڵ�����������������������"5/15" ��ʾ��5�뿪ʼ��ÿ��15�봥��(5,20,35,50)�� ������
  ��������'1/3'��ʾÿ��1�ſ�ʼ��ÿ�����촥��һ�Ρ�
L ��ʾ������˼�������ֶ������ϣ���ʾ���µ����һ��(���ݵ�ǰ�·ݣ�����Ƕ��»�������
  �Ƿ�������[leap]), �����ֶ��ϱ�ʾ���������൱��"7"��"SAT"�������"L"ǰ�������֣���
  ��ʾ�����ݵ����һ�������������ֶ�������"6L"�����ĸ�ʽ,���ʾ���������һ��������" 
W ��ʾ��ָ�����ڵ�����Ǹ�������(��һ������). ���������ֶ�������"15W"����ʾ��ÿ��15��
  ������Ǹ������մ��������15���������������������������(14��)����, ���15������δ��
  �������������һ(16��)����.���15�������ڹ�����(��һ������)������ڸ��촥�������ָ
  ����ʽΪ "1W",�����ʾÿ��1����������Ĺ����մ��������1����������������3������һ
  ������(ע��"W"ǰֻ�����þ��������,����������"-").
# ���(��ʾÿ�µĵڼ����ܼ�)�����������ֶ�������"6#3"��ʾ��ÿ�µĵ���������.ע�����ָ
  ��"#5",���õ�����û���������򲻻ᴥ��������(����ĸ�׽ں͸��׽��ٺ��ʲ�����) ��

С��ʾ��
'L'�� 'W'����һ���ʹ�á���������ֶ�������"LW",���ʾ�ڱ��µ����һ�������մ�����
���ֶε����ã���ʹ��Ӣ����ĸ�ǲ����ִ�Сд�ģ���MON ��mon��ͬ��
        
����ʾ��:
�� ��    ʱ    ��  �� ��        ��
0  0     12    *   *  ?                    ÿ��12�㴥�� 
0  15    10    ?   *  *                    ÿ��10��15�ִ��� 
0  15    10    *   *  ?                    ÿ��10��15�ִ���  
0  15    10    *   *  ?         *          ÿ��10��15�ִ���  
0  15    10    *   *  ?         2005       2005��ÿ��10��15�ִ��� 
0  *     14    *   *  ?                    ÿ������� 2�㵽2��59��ÿ�ִ��� 
0  0/5   14    *   *  ?                    ÿ������� 2�㵽2��59��(���㿪ʼ��ÿ��5�ִ���)  
0  0/5   14,18 *   *  ?                    ÿ������� 2�㵽2��59�֡�18�㵽18��59��(���㿪ʼ��ÿ��5�ִ���) 
0  0-5   14    *   *  ?                    ÿ������� 2�㵽2��05��ÿ�ִ��� 
0  10,44 14    ?   3  WED                  3�·�ÿ��������� 2��10�ֺ�2��44�ִ��� 
0  15    10    ?   *  MON-FRI              ����һ������ÿ�������10��15�ִ��� 
0  15    10    15  *  ?                    ÿ��15������10��15�ִ��� 
0  15    10    L   *  ?                    ÿ�����һ���10��15�ִ��� 
0  15    10    ?   *  6L                   ÿ�����һ�ܵ��������10��15�ִ��� 
0  15    10    ?   *  6L        2002-2005  ��2002�굽2005��ÿ�����һ�ܵ��������10��15�ִ��� 
0  15    10    ?   *  6#3                  ÿ�µĵ����ܵ������忪ʼ���� 
0  0     12    1/5 *  ?                    ÿ�µĵ�һ�����翪ʼÿ��5�촥��һ�� 
0  11    11    11  11 ?                    ÿ���11��11�� 11��11�ִ���
</pre>
</div>
    	</td>
    </tr>
</table>
</@CommonQueryMacro.CommonQuery>

<script language="JavaScript">
function saveBtn_postSubmit(button) {
	QuartzTaskManager_dataset.flushData(QuartzTaskManager_dataset.pageIndex);
}
function pausedBtn_needCheck(button) {
	return false;
}
function pausedBtn_onClick(button) {
	dwr.engine.setAsync(false);
	PrivateAction.pausedQuartzTask(QuartzTaskManager_dataset.getString("beanId"));
	dwr.engine.setAsync(true);
	QuartzTaskManager_dataset.flushData(QuartzTaskManager_dataset.pageIndex);
}
function resumeBtn_needCheck(button) {
	return false;
}
function resumeBtn_onClick(button) {
	dwr.engine.setAsync(false);
	PrivateAction.resumeQuartzTask(QuartzTaskManager_dataset.getString("beanId"));
	dwr.engine.setAsync(true);
	QuartzTaskManager_dataset.flushData(QuartzTaskManager_dataset.pageIndex);
}
function refreshBtn_needCheck(button) {
	return false;
}
function refreshBtn_onClick(button) {
	QuartzTaskManager_dataset.flushData(QuartzTaskManager_dataset.pageIndex);
}
</script>
</@CommonQueryMacro.CommonQueryTab>
</@CommonQueryMacro.page>
