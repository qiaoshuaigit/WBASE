var lastSpan='';
var ifCloseAll=0;
// Arrays for nodes and icons
var nodes		= new Array();
var openNodes	= new Array();
var icons		= new Array(6);

// Loads all icons that are used in the tree
function preloadIcons() {
    icons[0] = new Image();
    icons[0].src = _application_root + "/images/plus.gif";
    icons[1] = new Image();
    icons[1].src = _application_root + "/images/plusbottom.gif";
    icons[2] = new Image();
    icons[2].src = _application_root + "/images/minus.gif";
    icons[3] = new Image();
    icons[3].src = _application_root + "/images/minusbottom.gif";
    icons[4] = new Image();
    icons[4].src = _application_root + "/images/folder.gif";
    icons[5] = new Image();
    icons[5].src = _application_root + "/images/folderopen.gif";
}

// Create the tree
function createTree(arrName, startNode, openNode) {
    nodes = arrName;
    if (nodes.length > 0) {
        preloadIcons();
        if (startNode == null) startNode = 0;
        if (openNode != 0 || openNode != null) setOpenNodes(openNode);

        if (startNode !=0) {
            var nodeValues = nodes[getArrayId(startNode)].split("|");
 			document.write("<img id=\"menuHome\" src=" + _application_root + "/images/home.gif style=\"cursor:hand;\" onclick=\"closeAll(1);\" align=\"absbottom\"><br />");
        } else{
            document.write("<img id=\"menuHome\" src=" + _application_root + "/images/home.gif style=\"cursor:hand;\" onclick=\"closeAll(1);\" align=\"absbottom\"><br />");
        }
        var recursedNodes = new Array();
        addNode(startNode, recursedNodes);
    }
}

// Returns the position of a node in the array
function getArrayId(node) {
    for (i=0; i<nodes.length; i++) {
        var nodeValues = nodes[i].split("|");
        if (nodeValues[0]==node) return i;
    }
}
// Puts in array nodes that will be open
function setOpenNodes(openNode) {
    for (i=0; i<nodes.length; i++) {
        var nodeValues = nodes[i].split("|");
        if (nodeValues[0]==openNode) {
            openNodes.push(nodeValues[0]);
            setOpenNodes(nodeValues[1]);
        }
    }
}

// Checks if a node is open
function isNodeOpen(node) {
    for (i=0; i<openNodes.length; i++)
        if (openNodes[i]==node) return true;
    return false;
}

// Checks if a node has any children
function hasChildNode(parentNode) {
    for (i=0; i< nodes.length; i++) {
        var nodeValues = nodes[i].split("|");
        if (nodeValues[1] == parentNode) return true;
    }
    return false;
}

// Checks if a node is the last sibling
function lastSibling (node, parentNode) {
    var lastChild = 0;
    for (i=0; i< nodes.length; i++) {
        var nodeValues = nodes[i].split("|");
        if (nodeValues[1] == parentNode)
            lastChild = nodeValues[0];
    }
    if (lastChild==node) return true;
    return false;
}

// Adds a new node in the tree
function addNode(parentNode, recursedNodes) {
    for (var i = 0; i < nodes.length; i++) {

        var nodeValues = nodes[i].split("|");
        if (nodeValues[1] == parentNode) {

            var ls	= lastSibling(nodeValues[0], nodeValues[1]);

            var hcn	= hasChildNode(nodeValues[0]);
            var ino = isNodeOpen(nodeValues[0]);

            // Write out line & empty icons
            for (g=0; g<recursedNodes.length; g++) {
                if (recursedNodes[g] == 1) document.write("<img src=" + _application_root + "/images/line.gif align=\"absbottom\" />");
                else  document.write("<img src=" + _application_root + "/images/empty.gif align=\"absbottom\" />");
            }

            // put in array line & empty icons
            if (ls) recursedNodes.push(0);
            else recursedNodes.push(1);

            // Write out join icons
            if (hcn) {
                if (ls) {
                    //document.write("<a href=\"javascript: oc(" + nodeValues[0] + ", 1);\"><img id=\"join" + nodeValues[0] + "\" style=\"border:0px;\" src=" + _application_root + "/images/");
                    document.write("<img id=\"join" + nodeValues[0] + "\" onclick=\"oc(" + nodeValues[0] + ", 1);\" style=\"border:0px;cursor:hand;\" src=" + _application_root + "/images/");
                    if (ino) document.write("minus");
                    else document.write("plus");
                    //document.write("bottom.gif align=\"absbottom\" /></a>");
                    document.write("bottom.gif align=\"absbottom\" />");
                } else {
                    //document.write("<a href=\"javascript: oc(" + nodeValues[0] + ", 0);\"><img id=\"join" + nodeValues[0] + "\" style=\"border:0px;\" src=" + _application_root + "/images/");
                    document.write("<img id=\"join" + nodeValues[0] + "\" onclick=\"oc(" + nodeValues[0] + ", 0);\" style=\"border:0px;cursor:hand;\" src=" + _application_root + "/images/");
                    if (ino) document.write("minus");
                    else document.write("plus");
                    //document.write(".gif align=\"absbottom\" /></a>");
                    document.write(".gif align=\"absbottom\" />");
                }
            } else {
                if (ls) document.write("<img src=" + _application_root + "/images/join.gif align=\"absbottom\" />");
                else document.write("<img src=" + _application_root + "/images/joinbottom.gif align=\"absbottom\" />");
            }

            // Start link

            // Write out folder & page icons & node name
            nodeValueText = nodeValues[2];
            if(nodeValueText.indexOf("<input ") == 0) {
              	nodeValueText = "";
            }
            if (hcn) {
                document.write("<img id=\"icon" + nodeValues[0] + "\" src=" + _application_root + "/images/folder")
                if (ino) document.write("open");
                document.write(".gif align=\"absbottom\" />");
                if (ls) {
                    document.write("<span style=\"cursor:hand\" id=\"SD"+i+"\" onmousedown=\"changeMyStyle(this);\" onclick=\"treeItemClicked('"+nodeValues[0]+"','"+nodeValues[1]+"','"+nodeValueText+"');\" >"+nodeValues[2]+"</span><br />");
                } else {
                    document.write("<span style=\"cursor:hand\" id=\"SD"+i+"\" onmousedown=\"changeMyStyle(this);\" onclick=\"treeItemClicked('"+nodeValues[0]+"','"+nodeValues[1]+"','"+nodeValueText+"');\" >"+nodeValues[2]+"</span><br />");
                }
            } else {
                /*if( nodeValues[5] == "new"){
                    document.write("<img id=\"icon" + nodeValues[0] + "\" src=" + _application_root + "/images/mEditor.gif align=\"absbottom\"/>");
                }
                else if(nodeValues[5] == "list"){
                    document.write("<img id=\"icon" + nodeValues[0] + "\" src=" + _application_root + "/images/mReport.gif align=\"absbottom\"/>");
                }
                else*/
                document.write("<img id=\"icon" + nodeValues[0] + "\" src=" + _application_root + "/images/page.gif align=\"absbottom\"/>");
                document.write("<span style=\"cursor:hand\" id=\"SD"+i+"\" onmousedown=\"changeMyStyle(this)\" onclick=\"treeItemClicked('"+nodeValues[0]+"','"+nodeValues[1]+"','"+nodeValueText+"');\" >"+nodeValues[2]+"</span><br />");

            }

            // If node has children write out divs and go deeper
            if (hcn) {
                document.write("<div id=\"div" + nodeValues[0] + "\"");
                if (!ino) document.write(" style=\"display: none;\"");
                document.write(">");
                addNode(nodeValues[0], recursedNodes);
                document.write("</div>");
            }

            // remove last line or empty icon
            recursedNodes.pop();
        }
    }
}
// Opens or closes a node
function oc(node, bottom) {
    var theDiv = document.getElementById("div" + node);
    var theJoin	= document.getElementById("join" + node);
    var theIcon = document.getElementById("icon" + node);

    if (theDiv.style.display == 'none') {
        if (bottom==1) theJoin.src = icons[3].src;
        else theJoin.src = icons[2].src;
        theIcon.src = icons[5].src;
        theDiv.style.display = '';
    } else {
        if (bottom==1) theJoin.src = icons[1].src;
        else theJoin.src = icons[0].src;
        theIcon.src = icons[4].src;
        theDiv.style.display = 'none';
    }
}
// Opens or closes a node
function occ(node, bottom,needopen) {
    var theDiv = document.getElementById("div" + node);
    var theJoin	= document.getElementById("join" + node);
    var theIcon = document.getElementById("icon" + node);

    if (theDiv.style.display == 'none' && needopen == 1) {
        if (bottom==1) theJoin.src = icons[3].src;
        else theJoin.src = icons[2].src;
        theIcon.src = icons[5].src;
        theDiv.style.display = '';
    } else if(theDiv.style.display != 'none' && needopen == 0){
        if (bottom==1) theJoin.src = icons[1].src;
        else theJoin.src = icons[0].src;
        theIcon.src = icons[4].src;
        theDiv.style.display = 'none';
    }
}
function changeMyStyle(obj){
    if (lastSpan!==''){
        document.all.item(lastSpan).style.color='';
        document.all.item(lastSpan).style.backgroundColor=''
        document.all.item(lastSpan).style.borderStyle='none';
        document.all.item(lastSpan).style.borderWidth=0;
        document.all.item(lastSpan).style.borderColor='';
        //obj.style.borderStyle='none';
    }
    if (event.button==1){
        obj.style.color='#000000';
        obj.style.backgroundColor='#C6C6C6';
        obj.style.borderWidth=1;
        obj.style.borderColor='#000000';
        obj.style.borderStyle='groove';//'dotted';
    }
    lastSpan=obj.id;
}

function closeAll(needopen){
	if (ifCloseAll==0){
		needopen = 1;
		document.all.menuHome.title="收起";
		for (var i = 0; i < nodes.length; i++) {
			var nodeValues = nodes[i].split("|");
			var hcn	= hasChildNode(nodeValues[0]);
			if(hcn) occ(nodeValues[0], 1,needopen);
		}
		ifCloseAll = 1;
	}
	else{
		needopen = 0;
		document.all.menuHome.title="展开";
		for (var i = 0; i < nodes.length; i++) {
			var nodeValues = nodes[i].split("|");
			var hcn	= hasChildNode(nodeValues[0]);
			if(hcn) occ(nodeValues[0], 0,needopen);
		}
		ifCloseAll = 0;
	}
}

// Push and pop not implemented in IE(crap!)
if(!Array.prototype.push) {
    function array_push() {
        for(var i=0;i<arguments.length;i++)
            this[this.length]=arguments[i];
        return this.length;
    }
    Array.prototype.push = array_push;
}
if(!Array.prototype.pop) {
    function array_pop(){
        lastElement = this[this.length-1];
        this.length = Math.max(this.length-1,0);
        return lastElement;
    }
    Array.prototype.pop = array_pop;
}
