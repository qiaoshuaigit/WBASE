var x0=0,y0=0,x1=0,y1=0;
var offx=6,offy=6;
var moveable=false;
var hover='orange',normal='#336699';//color;
var index=10000;//z-index;

function startDrag(obj)
{
	if(event.button==1)
	{
		obj.setCapture();

		var win = obj.parentNode;
		var sha = win.nextSibling;

		x0 = event.clientX;
		y0 = event.clientY;
		x1 = parseInt(win.style.left);
		y1 = parseInt(win.style.top);

		normal = obj.style.backgroundColor;

		obj.style.backgroundColor = hover;
		win.style.borderColor = hover;
		obj.nextSibling.style.color = hover;
		sha.style.left = x1 + offx;
		sha.style.top  = y1 + offy;
		moveable = true;
	}
}

function drag(obj)
{
	if(moveable)
	{
		var win = obj.parentNode;
		var sha = win.nextSibling;
		win.style.left = x1 + event.clientX - x0;
		win.style.top  = y1 + event.clientY - y0;
		sha.style.left = parseInt(win.style.left) + offx;
		sha.style.top  = parseInt(win.style.top) + offy;
	}
}

function stopDrag(obj)
{
	if(moveable)
	{
		var win = obj.parentNode;
		var sha = win.nextSibling;
		var msg = obj.nextSibling;
		win.style.borderColor     = normal;
		obj.style.backgroundColor = normal;
		msg.style.color           = normal;
		sha.style.left = obj.parentNode.style.left;
		sha.style.top  = obj.parentNode.style.top;
		obj.releaseCapture();
		moveable = false;
	}
}

function getFocus(obj)
{
	if(obj.style.zIndex!=index)
	{
		index = index + 2;
		var idx = index;
		obj.style.zIndex=idx;
		obj.nextSibling.style.zIndex=idx-1;
	}
}

function min(obj)
{
	var win = obj.parentNode.parentNode;
	var sha = win.nextSibling;
	var tit = obj.parentNode;
	var msg = tit.nextSibling;
	var flg = msg.style.display=="none";
	if(flg)
	{
		win.style.height  = parseInt(msg.style.height) + parseInt(tit.style.height) + 2*2;
		sha.style.height  = win.style.height;
		msg.style.display = "block";
		obj.innerHTML = "0";
	}
	else
	{
		win.style.height  = parseInt(tit.style.height) + 2*2;
		sha.style.height  = win.style.height;
		obj.innerHTML = "2";
		msg.style.display = "none";
	}
}

function xWin(id,w,h,l,t,tit,msg){
	index = index+2;
	this.id      = id;
	this.width   = w;
	this.height  = h;
	this.left    = l;
	this.top     = t;
	this.zIndex  = index;
	this.title   = tit;
	this.message = (msg==""||msg==null)?"system error":msg;
	this.obj     = null;
	this.build  = _build;
	this.reload = _reload;
	this.createBg = _createXMsgBg;
	this.removeBg = _removeXMsgBg;
	var xMsgDiv = document.getElementById("xMsg" + this.id);

	this.removeBg();
	if(!xMsgDiv){
		this.build();
	}else{
		this.reload();
	}
	this.createBg();
}

function _reload(){
	ShowHide(this.id,"none");
	var titleSpan = document.getElementById("titleSpan" + this.id);
	var xMsgMsg = document.getElementById("xMsgMsg" + this.id);
    titleSpan.childNodes[0].nodeValue = this.title;
    xMsgMsg.childNodes[0].nodeValue = this.message;
	ShowHide(this.id,"");
}

function _build(){
	var str = ""
		+ "<div id=xMsg" + this.id + " "
		+ "style='"
		+ "z-index:" + this.zIndex + ";"
		+ "width:" + this.width + ";"
		+ "height:" + this.height + ";"
		+ "left:" + this.left + ";"
		+ "top:" + this.top + ";"
		+ "background-color:" + normal + ";"
		+ "color:" + normal + ";"
		+ "font-size:8pt;"
		+ "font-family:Tahoma;"
		+ "position:absolute;"
		+ "cursor:default;"
		+ "border:2px solid " + normal + ";"
		+ "' "
		+ "onmousedown='getFocus(this)'>"
			+ "<div "
			+ "style='"
			+ "background-color:" + normal + ";"
			+ "width:" + (this.width-2*2) + ";"
			+ "height:20;"
			+ "color:white;"
			+ "' "
			+ "onmousedown='startDrag(this)' "
			+ "onmouseup='stopDrag(this)' "
			+ "onmousemove='drag(this)' "
			+ "ondblclick='min(this.childNodes[1])' "
			+ ">"
				+ "<span id=titleSpan" + this.id + " style='width:" + (this.width-2*12-4) + ";padding-left:3px;'>" + this.title + "</span>"
				+ "<span style='width:12;border-width:0px;color:white;font-family:webdings;' onclick='min(this)'>0</span>"
				+ "<span style='width:12;border-width:0px;color:white;font-family:webdings;' onclick='ShowHide(\""+this.id+"\",null)'>r</span>"
			+ "</div>"
				+ "<div id=xMsgMsg" + this.id + " "
				+ "style='"
				+ "width:100%;"
				+ "height:" + (this.height-20-4) + ";"
				+ "background-color:white;"
				+ "line-height:14px;"
				+ "word-break:break-all;"
				+ "padding:3px;"
				+ "'>" + this.message + "</div>"
		+ "</div>";
		/*
		+ "<div id=xMsg" + this.id + "bg style='"
		+ "width:" + this.width + ";"
		+ "height:" + this.height + ";"
		+ "top:" + this.top + ";"
		+ "left:" + this.left + ";"
		+ "z-index:" + (this.zIndex-1) + ";"
		+ "position:absolute;"
		+ "background-color:black;"
		+ "filter:alpha(opacity=40);"
		+ "'></div>";
		*/
		document.body.insertAdjacentHTML("beforeEnd",str);
}

function _createXMsgBg(){
	var sWidth=document.body.offsetWidth;
	var sHeight=screen.height;
	var XMsgBg=document.createElement("div");
	XMsgBg.setAttribute('id',"xMsg" + this.id + "bg");
	XMsgBg.style.position="absolute";
	XMsgBg.style.top="0";
	XMsgBg.style.background="#777";
	XMsgBg.style.filter="progid:DXImageTransform.Microsoft.Alpha(style=3,opacity=25,finishOpacity=75";
	XMsgBg.style.opacity="0.6";
	XMsgBg.style.left="0";
	XMsgBg.style.width=sWidth + "px";
	XMsgBg.style.height=sHeight + "px";
	XMsgBg.style.zIndex = this.zIndex-1;
	document.body.appendChild(XMsgBg);
}

function _removeXMsgBg(){
	var XMsgBg = document.getElementById("xMsg" + this.id + "bg");
	if(XMsgBg){
		document.body.removeChild(XMsgBg);
	}
}

function ShowHide(id,dis){
	var bdisplay = (dis==null)?((document.getElementById("xMsg"+id).style.display=="")?"none":""):dis
	document.getElementById("xMsg"+id).style.display = bdisplay;
	if(bdisplay == "none"){
		var XMsgBg = document.getElementById("xMsg" + this.id + "bg");
		if(XMsgBg){
			document.body.removeChild(XMsgBg);
		}
	}else{
			var sWidth=document.body.offsetWidth;
			var sHeight=screen.height;
			var XMsgBg=document.createElement("div");
			XMsgBg.setAttribute('id',"xMsg" + this.id + "bg");
			XMsgBg.style.position="absolute";
	XMsgBg.style.top="0";
	XMsgBg.style.background="#777";
	XMsgBg.style.filter="progid:DXImageTransform.Microsoft.Alpha(style=3,opacity=25,finishOpacity=75";
	XMsgBg.style.opacity="0.6";
	XMsgBg.style.left="0";
	XMsgBg.style.width=sWidth + "px";
	XMsgBg.style.height=sHeight + "px";
	XMsgBg.style.zIndex = this.zIndex-1;
	document.body.appendChild(XMsgBg);
	}
	//document.getElementById("xMsg"+id+"bg").style.display = bdisplay;
}
function openXMsg(index,title,message){
	var a = new xWin(index,400,100,350,160,title,message);
}