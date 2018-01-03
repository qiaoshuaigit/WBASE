function _initPagePilot(pilot) {
	pilot.refresh=_pagePilot_refresh;
	pilot.moveToPage=_pagePilot_moveToPage;
	pilot.onKeyPress=_pagePilot_onKeyPress;

	var dataset=getElementDataset(pilot);
	if (dataset) {
	}

	pilot.refresh();
}

function refreshPagePilot(pilot) {
	pilot.refresh();
}

function _pagePilot_moveToPage(pilot) {
	var pilot = this;
	var index = -1;
	var indexPage = document.getElementById(pilot.id + "_pg");
	if(indexPage) {
		index = indexPage.value;
	}
	var dataset=getElementDataset(pilot);
	if (dataset) {
		var dsPageCount = dataset.pageCount;
		var dsPageIndex = dataset.pageIndex;
		if(index >= 1 && index <= dsPageCount && index != dsPageIndex) {
			dataset.flushData(index);
		}
		else if(index != dsPageIndex) {
			alert("您要跳转的页数[" + index + "]范围越界！");
			indexPage.value = "";
		}
	}
}

function _pagePilot_onKeyPress(pilot) {
	var pilot = this;
	if(event.keyCode == 13) {
		var indexPage = document.getElementById(pilot.id + "_pg");
		if(indexPage) {
			index = indexPage.value;
		}
		if("" != index) {
			pilot.moveToPage(pilot);
		}
	}
}

/* modify by shen_antonio 20081218.*/
function pageCacheToPage(pilot,index){
	var dataset=getElementDataset(pilot);
	dataset.moveToPage(index);
	dataset.pageIndex = index;
	refreshPagePilot(pilot);
}
/* .*/

function _pagePilot_refresh() {
	var pilot = this;
	function genLinkCell(cell, index, pageIndex) {
		if (index == dataset.pageIndex) {
			cell.innerHTML = "<b>" + index + "</b>";
		}
		else {
			/* modify by shen_antonio 20081218 for pageCache.*/
			var _pageCache = pilot.pageCache;
			if(isTrue(_pageCache)){
				cell.innerHTML = "<span onclick=\"javascript:pageCacheToPage(" + pilot.id + "," + index + ")\" style='cursor:hand'><font COLOR='blue'><u>" + index + "</u></font></span>";
			}else{
				cell.innerHTML = "<span onclick=\"javascript:" + dataset.id + ".flushData(" + index + ")\" style='cursor:hand'><font COLOR='blue'><u>" + index + "</u></font></span>";
			}
			/* .*/
		}
		if (index == 1) {
			cell.innerHTML = "页码：" + cell.innerHTML;
		}
	}

	var pilot = this;
	var dataset=getElementDataset(pilot);
	if (dataset) {
		var row=pilot.tBodies[0].rows[0];
		if (row) {
			row.removeNode(true);
		}

		var maxPageLink = getInt(pilot.maxPageLink);
		var startIndex = dataset.pageIndex - getInt(maxPageLink / 2);

		if (startIndex > (dataset.pageCount - maxPageLink + 1)) {
		  startIndex = dataset.pageCount - maxPageLink + 1;
		}
		if (startIndex < 1) {
		  startIndex = 1;
		}

		var endIndex = startIndex + maxPageLink - 1;
		if (endIndex > dataset.pageCount) {
		  endIndex = dataset.pageCount;
		}
		var hasInput = false;
		row = pilot.tBodies[0].insertRow();
		if (startIndex > 1) {
			var cell = row.insertCell();
			genLinkCell(cell, 1, dataset.pageIndex);

			if (startIndex > 2) {
				var cell = row.insertCell();
				cell.innerHTML = "...";
				/*cell.innerHTML = "...<input size=\"1\" type=\"text\" style=\"width=16px;height=15px;\" " +
					"onchange=\"" + pilot.id + ".moveToPage();\" onkeypress=\"" + pilot.id + ".onKeyPress();\" " +
					"class=\"editor\" name=\"" + pilot.id + "_pg\" value=\"\"/>...";*/
				hasInput = true;
			}
		}

		for (var i = startIndex; i <= endIndex; i++) {
			var cell = row.insertCell();
			genLinkCell(cell, i, dataset.pageIndex);
		}
		if (endIndex < dataset.pageCount) {
			if (endIndex < dataset.pageCount - 1) {
				var cell = row.insertCell();
				cell.innerHTML = "...";
				/*
				if(hasInput == false) {
					cell.innerHTML = "...<input size=\"1\" type=\"text\" style=\"width=16px;height=15px;\" " +
						"onchange=\"" + pilot.id + ".moveToPage();\" onkeypress=\"" + pilot.id + ".onKeyPress();\" " +
						"class=\"editor\" name=\"" + pilot.id + "_pg\" value=\"\"/>...";
				}
				else {
					cell.innerHTML = "...";
				}*/
				hasInput = true;
			}

			var cell = row.insertCell();
			genLinkCell(cell, dataset.pageCount, dataset.pageIndex);
		}
		if(hasInput) {
			var cell = row.insertCell();
			cell.innerHTML = " (至第 <input size=\"1\" type=\"text\" style=\"width=16px;height=15px;\" " +
					"onchange=\"" + pilot.id + ".moveToPage();\" onkeypress=\"" + pilot.id + ".onKeyPress();\" " +
					"class=\"editor\" name=\"" + pilot.id + "_pg\" value=\"\"/> 页)";
		}
	}
}