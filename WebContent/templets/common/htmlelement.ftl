<#-- �ı������ -->
<#macro textinput textinputlabel id name value>
    <label>${textinputlabel}:</label>
    <input value="${value}" id="${id}" name="${name}" size="30" type="text" />
</#macro>

<#-- ��������� -->
<#macro password passwordlabel name id value size maxlength>
    <label>${passwordlabel}��</label>
    <input type="password" name=${name} value="${value}" id="${id}" size="${size}" maxlength="${maxlength}"/>
</#macro>

<#-- ��������� -->
<#macro texthidden id name value>
<input type="hidden" name="${name}" value="${value}" id="${id}" class="textinput"/></label>
</#macro>

<#-- ͼƬ��ť -->
<#macro commonbutton id href imgsrc>
    <a href="${href}"><img src="${imgsrc}" id="${id}></a>
</#macro>

<#-- ���ð�ť -->
<#macro buttonresetI buttonlabel name id>
    <input type="reset" name="${name}" value="${buttonlabel}" id="${id}" />
</#macro>

<#-- �ύ��ť -->
<#macro buttonsubmitI buttonlabel name id>
    <input type="submit" name="${name}" value="${buttonlabel}" id="${id}" />
</#macro>

<#-- ���� -->
<#macro label label>        
<font class="label">${label}</font>
<hr noshade color="#B9BEC1" size=1 width=98% align="center">
</#macro>

<#-- �ı��������� -->
<#macro textarea textarealabel id name textareaid value colnum rownum>
<div class="centerlabel" id="${id}">
${textarealabel}��
</div>
<div>
<textarea name="${name}" value="${value}" id="${textareaid}" class="textarea" cols="${colnum}" rows="${rownum}"/>
</div>
</#macro>

