function errAlert(e){
/*
	if(str == null || str == ""){
		cAlert('操作失败','平台异常','red');
	}else{
		cAlert('操作失败',str,'red');
	}
*/
	switch (typeof(e)){
		case "string":{
				if (e == null || e == "")
					cAlert('操作失败','平台异常','red');
				else
					cAlert('操作失败',e,'red');
			break;
		}
		case "object":{
			if( e == null )
				cAlert('操作失败','平台异常','red');
			else
				cAlert('操作失败',e.description,'red');
			break;
		}
		default:{
			cAlert('操作失败','平台异常','red');
			break;
		}
	}
}
function wrnAlert(str){
	cAlert('操作提示',str,'#336699');
}

function cAlert(titleStr,str,bc){
/*
  var param = new Object();
  param.title = titleStr;
  param.info = str;
  param.bc = bc;
  window.showModalDialog(_extra_library+"/alterDialog.html", param,"status:false;dialogWidth:300px;dialogHeight:100px;edge:Raised; enter: Yes; help: No; resizable: Yes; status: No");
  */
  window.alert(str, titleStr, "error");
}


function sAlert(titleStr,str,bc){
			var msgw,msgh,bordercolor;
			msgw=400;
			msgh=100;
			titleheight=25
			//bordercolor="#336699";
			bordercolor=bc;
			titlecolor="#99CCFF";

			var sWidth,sHeight;
			sWidth=document.body.offsetWidth;
			sHeight=screen.height;

			var bgOjb = document.getElementById("bgDiv");
			if( bgOjb && typeof(bgOjb) == "object"){
				var msgTxt = document.getElementById("msgTxt");
				if( msgTxt ){
					msgTxt.innerHTML = msgTxt.innerHTML + "<br\>" + str;
					return;
				}
			}
			var bgObj=document.createElement("div");
			bgObj.setAttribute('id','bgDiv');
			bgObj.style.position="absolute";
			bgObj.style.top="0";
			bgObj.style.background="#777";
			bgObj.style.filter="progid:DXImageTransform.Microsoft.Alpha(style=3,opacity=25,finishOpacity=75";
			bgObj.style.opacity="0.6";
			bgObj.style.left="0";
			bgObj.style.width=sWidth + "px";
			bgObj.style.height=sHeight + "px";
			bgObj.style.zIndex = "10000";
			document.body.appendChild(bgObj);
			var msgObj=document.createElement("div")
			msgObj.setAttribute("id","msgDiv");
			msgObj.setAttribute("align","center");
			msgObj.style.background="white";
			msgObj.style.border="1px solid " + bordercolor;
	    	msgObj.style.position = "absolute";
            msgObj.style.left = "50%";
            msgObj.style.top = "50%";
            msgObj.style.font="12px/1.6em Verdana, Geneva, Arial, Helvetica, sans-serif";
            msgObj.style.marginLeft = "-225px" ;
            msgObj.style.marginTop = -75+document.documentElement.scrollTop+"px";
            msgObj.style.width = msgw + "px";
            msgObj.style.height =msgh + "px";
            msgObj.style.textAlign = "center";
            msgObj.style.lineHeight ="25px";
            msgObj.style.zIndex = "10001";

		   var title=document.createElement("h4");
		   title.setAttribute("id","msgTitle");
		   title.setAttribute("align","right");
		   title.style.margin="0";
		   title.style.padding="3px";
		   title.style.background=bordercolor;
		   title.style.filter="progid:DXImageTransform.Microsoft.Alpha(startX=20, startY=20, finishX=100, finishY=100,style=1,opacity=75,finishOpacity=100);";
		   title.style.opacity="0.75";
		   title.style.border="1px solid " + bordercolor;
		   title.style.height="18px";
		   title.style.font="12px Verdana, Geneva, Arial, Helvetica, sans-serif";
		   title.style.color="white";
		   title.style.cursor="pointer";
		   title.innerHTML= "<center>" + titleStr + "</center>" +  "关闭";
		   title.setAttribute("title",titleStr);
		   title.onclick=function(){
		        document.body.removeChild(bgObj);
                document.getElementById("msgDiv").removeChild(title);
                document.body.removeChild(msgObj);
                }
		   document.body.appendChild(msgObj);
		   document.getElementById("msgDiv").appendChild(title);
		   var txt=document.createElement("p");
		   txt.style.margin="1em 0"
		   txt.setAttribute("id","msgTxt");
		   txt.innerHTML=str;
           document.getElementById("msgDiv").appendChild(txt);
            }