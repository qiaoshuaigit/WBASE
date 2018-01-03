/**
 * 提供企业组织机构代码合法性验证的功能, 企业组织机构代码编码规则参考国标GB 11714-1997.
 * 
 * @param orgCode 组织机构代码.
 * @return <code>true</code>如果验证通过, 否则返回<code>false</code>.
 * @author	Richard Chen
 * @version	1.0
 * @since	1.0
 */
function isValidateOrgCode(orgCode) {
	var orgCodePattern = /^[A-Z0-9]{8}-[0-9X]{1}$/;
	var table = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	if (orgCode.match(orgCodePattern) == null) {
		return false;
	}	
	var ORG_CODE_FACTOR_TABLE = [ 3, 7, 9, 10, 5, 8, 4, 2 ];
	var elements = new Array(10);
	for (var length = 0 ; length < 10 ; length++) {
		elements[length] = orgCode.charAt(length);
	}
	var sum = 0;
	for (var i = 0, body = elements.length - 2; i < body; i++) {
		if (elements[i] <= '9') {
			sum += (parseInt(elements[i])) * ORG_CODE_FACTOR_TABLE[i];
		} else {
			sum += (table.indexOf(elements[i]) + 10) * ORG_CODE_FACTOR_TABLE[i];
		}
	}
	var result = 11 - (sum % 11);
	var verify = elements[elements.length -1];
	if ((result == 10 && verify == "X") || (result == 11 && verify == "0")) {
		return true;
	}
	if (verify <= '9') {
	    if(result == parseInt(verify)) {
		    return true;
		}
	}
	return false;
}