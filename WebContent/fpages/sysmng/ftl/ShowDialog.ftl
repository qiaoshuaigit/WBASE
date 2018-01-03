<#import "/templets/commonQuery/CommonQueryTagMacro.ftl" as CommonQueryMacro>

<@CommonQueryMacro.page title="">
<#assign url = RequestParameters["url"]?default("")>
<#assign _width = RequestParameters["_width"]?default("500")>
<#assign _height = RequestParameters["_height"]?default("400")>
<@CommonQueryMacro.CommonQuery id="CommEmpty" init="false" navigate="false" submitMode="allchange">
</@CommonQueryMacro.CommonQuery>
<@CommonQueryMacro.WindowElement/>

<script language="JavaScript" for="window" event="onload">
	var id = "ShowDialog";
	var title = "";
	var url = "${url}";
	var paramMap = new Map();
	var elementId = "winZone";
	var _width = "${_width}";
	var _height = "${_height}";
	
	document.body.style.overflow = "hidden";
	winZone = document.createElement('div');
	winZone.setAttribute('id', elementId);
	$(__WinsDiv).appendChild(winZone);
	loanPageLet(url, paramMap, elementId);
	var dialogTop = (((document.documentElement.clientHeight || document.body.clientHeight) - _height)/2) >> 0;
	var dialogLeft = (((document.documentElement.clientWidth || document.body.clientWidth) - _width)/2) >> 0;
	win = createDialog(id, dialogLeft, dialogTop, _width, _height);
	win.setText(title);
	win.attachObject(elementId,true);
	win.attachEvent("onClose", function(win) {
		var eventName=getElementEventName(winZone, "onCloseCheck");
			if (isUserEventDefined(eventName)) {
				var event_result=fireUserEvent(eventName, [win]);
				if(typeof(event_result) == "boolean") {
					if(event_result==true) {
						unloadPageLet(elementId);
					} else {
						document.body.style.overflow = "auto";
						return false;
					}
				} else {
					unloadPageLet(elementId);
				}
			} else {
				unloadPageLet(elementId);
			}
			document.body.style.overflow = "auto";
			return true;
	});
</script>
<script language="JavaScript">
function createDialog(id, left, top, width, height){
	var win = dhxWins.createWindow(id, left, top, width, height);
	win.setModal(true);
	win.keepInViewport(true);
	//win.clearIcon();
	win.denyMove();
	win.denyPark();
	//win.button("help").show();
	//win.button("stick").show();
	win.button("park").hide();
	win.button("minmax1").hide();
	win.button("minmax2").hide();

	return win;
}
</script>
</@CommonQueryMacro.page>
