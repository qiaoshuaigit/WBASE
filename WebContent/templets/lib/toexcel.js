/*
 * 默认转换实现函数，如果需要其他功能，需自行扩展
 * 参数：
 *      tableID : HTML中Table对象id属性值
 * 详细用法参见以下 TableToExcel 对象定义
 */
var _document_;

function printASExcelFrame(myDocument, callWindow){
	//funPreHook(_theme_root + "/templets/lib/themes/default/loading.gif");
	_document_ = myDocument;
	_window_ = callWindow;
	if(!callWindow){
		_window_ = window;
	}
	var tableAry = _document_.getElementsByTagName("table");
	var tb = null;
	var tableID = null;
	var tableAry2 = new Array();
	for(var i=0;i<tableAry.length;i++){
		if(tableAry[i].printabled && isTrue(tableAry[i].printabled)){
			 tableAry2[tableAry2.length] = tableAry[i].id;
		}
	}
	if( tableAry2.length == 0 ){
		alert("目前无可供打印的内容");
		return;
	}
	var htWorkSheet = new HTWorksheet();
	htWorkSheet.jXls.UserControl = true;
	for(var i=0;i<tableAry2.length;i++){
		 var tb = new TableToExcel(tableAry2[i]);
  		 tb.setFontStyle("Courier New");
         tb.setFontSize(9);
         tb.setTableBorder(2);
         tb.setColumnWidth(7);
         tb.isLineWrap(true);
		 tb.appendExcelFile(htWorkSheet);
	}
	htWorkSheet.myWorksheet.Columns.AutoFit;
	htWorkSheet.jXls.UserControl = true;
	htWorkSheet.jXls.Visible = true;
    delete htWorkSheet.jXls;
    delete htWorkSheet.myWorkbook;
    delete htWorkSheet.myWorksheet;
    delete htWorkSheet;

}

function printAsExcel(){
	printASExcelFrame(document);
}

function HTWorksheet(){
	this.curRow = 0; //当前行号
	this.myWorkbook = null;//当前workbook对象
	this.myWorksheet = null;//当前worksheet对象
	this.jXls = null;
    try {
        this.jXls = new ActiveXObject('Excel.Application');
    }
    catch (e) {
        alert("无法启动Excel!\n\n" + e.message +
           "\n\n如果您确信您的电脑中已经安装了Excel，"+
           "那么请调整IE的安全级别(安全级 - 低)。\n\n具体操作：\n\n"+
           "工具 → Internet选项 → 安全 → 受信任的站点 → 拉动安全级至(低) \n\n"+
           "然后把本网址添加到受信任的站点中，保存后退出系统，再次登陆。");
        return false;
    }


    this.myWorkbook = this.jXls.Workbooks.Add();
    this.jXls.DisplayAlerts = false;
    this.myWorkbook.Worksheets(3).Delete();
    this.myWorkbook.Worksheets(2).Delete();
    this.jXls.DisplayAlerts = true;
    this.myWorksheet = this.myWorkbook.ActiveSheet;
    this.myWorksheet.PageSetup.LeftMargin = 2/0.035;
    this.myWorksheet.PageSetup.RightMargin = 0;
}
	HTWorksheet.prototype.setCurRow = function (curRow) {
		this.curRow = curRow;
	};
	HTWorksheet.prototype.getCurRow = function () {
		return this.curRow;
	};
	HTWorksheet.prototype.setMyWorkbook = function (myWorkbook) {
		this.myWorkbook = myWorkbook;
	};
	HTWorksheet.prototype.getMyWorkbook = function () {
		return this.myWorkbook;
	};
	HTWorksheet.prototype.setMyWorksheet = function (myWorksheet) {
		this.myWorksheet = myWorksheet;
	};
	HTWorksheet.prototype.getMyWorksheet = function () {
		return this.myWorksheet;
	};



function saveAsExcel(tableID){
  var tb = new TableToExcel(tableID);
  tb.setFontStyle("Courier New");
  tb.setFontSize(10);
  tb.setTableBorder(2);
  tb.setColumnWidth(7);
  tb.isLineWrap(true);
  tb.getExcelFile();
}

/*
 *  功能：HTML中Table对象转换为Excel通用对象.
 *  作者：Jeva
 *  时间：2006-08-09
 *  参数：tableID  HTML中Table对象的ID属性值
 *  说明：
 *       能适应复杂的HTML中Table对象的自动转换，能够自动根据行列扩展信息
 *       合并Excel中的单元格，客户端需要安装有Excel
 *       详细的属性、方法引用说明参见：Excel的Microsoft Excel Visual Basic参考
 *  示范：
 *       var tb = new TableToExcel('demoTable');
 *    tb.setFontStyle("Courier New");
 *    tb.setFontSize(10);  //推荐取值10
 *    tb.setFontColor(6);  //一般情况不用设置
 *    tb.setBackGround(4);  //一般情况不用设置
 *    tb.setTableBorder(2);  //推荐取值2
 *    tb.setColumnWidth(10);  //推荐取值10
 *    tb.isLineWrap(false);
 *    tb.isAutoFit(true);
 *
 *    tb.getExcelFile();
 *   如果设置了单元格自适应，则设置单元格宽度无效
 *  版本：1.0

 *  BUG提交：QQ:18234348
 */
function TableToExcel(tableID) {
    this.tableBorder = 2; //边框类型，-1没有边框 可取1/2/3/4
    this.backGround = 0; //背景颜色：白色   可取调色板中的颜色编号 1/2/3/4....
    this.fontColor = 1;  //字体颜色：黑色
    this.fontSize = 10;  //字体大小
    this.fontStyle = "宋体"; //字体类型
    this.rowHeight = -1; //行高
    this.columnWidth = -1; //列宽
    this.lineWrap = true; //是否自动换行
    this.textAlign = -4108; //内容对齐方式   默认为居中
    this.autoFit = true;  //是否自适应宽度
    this.tableID = tableID;
    this.title = "";//表格标题
}
TableToExcel.prototype.setTableTitle = function (title) {
	this.title = title;
}

TableToExcel.prototype.setTableBorder = function (excelBorder) {
    this.tableBorder = excelBorder ;
};


TableToExcel.prototype.setBackGround = function (excelColor) {
    this.backGround = excelColor;
};

TableToExcel.prototype.setFontColor = function (excelColor) {
    this.fontColor = excelColor;
};

TableToExcel.prototype.setFontSize = function (excelFontSize) {
    this.fontSize = excelFontSize;
};

TableToExcel.prototype.setFontStyle = function (excelFont) {
    this.fontStyle = excelFont;
};

TableToExcel.prototype.setRowHeight = function (excelRowHeight) {
    this.rowHeight = excelRowHeight;
};

TableToExcel.prototype.setColumnWidth = function (excelColumnWidth) {
    this.columnWidth = excelColumnWidth;
};

TableToExcel.prototype.isLineWrap = function (lineWrap) {
    if (lineWrap == false || lineWrap == true) {
        this.lineWrap = lineWrap;
    }
};

TableToExcel.prototype.setTextAlign = function (textAlign) {
    this.textAlign = textAlign;
};

TableToExcel.prototype.isAutoFit = function(autoFit){
 if(autoFit == true || autoFit == false)
  this.autoFit = autoFit ;
}


//文件转换主函数
TableToExcel.prototype.getExcelFile = function () {
    var jXls, myWorkbook, myWorksheet, myHTMLTableCell, myExcelCell, myExcelCell2;
    var myCellColSpan, myCellRowSpan;

    try {
        jXls = new ActiveXObject('Excel.Application');
    }
    catch (e) {
        alert("无法启动Excel!\n\n" + e.message +
           "\n\n如果您确信您的电脑中已经安装了Excel，"+
           "那么请调整IE的安全级别(安全级 - 低)。\n\n具体操作：\n\n"+
           "工具 → Internet选项 → 安全 → 受信任的站点 → 拉动安全级至(低) \n\n"+
           "然后把本网址添加到受信任的站点中，保存后退出系统，再次登陆。");
        return false;
    }

    jXls.Visible = false;
    myWorkbook = jXls.Workbooks.Add();
    jXls.DisplayAlerts = false;
    myWorkbook.Worksheets(3).Delete();
    myWorkbook.Worksheets(2).Delete();
    jXls.DisplayAlerts = true;
    myWorksheet = myWorkbook.ActiveSheet;
    var  readRow = 0,  readCol = 0;
    var totalRow = 0, totalCol = 0;
    var   tabNum = 0;

//设置行高、列宽
    if(this.columnWidth != -1)
     myWorksheet.Columns.ColumnWidth = this.columnWidth;
    else
     myWorksheet.Columns.ColumnWidth = 7;
    if(this.rowHeight != -1)
    	 myWorksheet.Rows.RowHeight = this.rowHeight ;
     myWorksheet.Rows.RowHeight = 20 ;

//搜索需要转换的Table对象，获取对应行、列数
    var obj = _document_.all.tags("table");
    for (x = 0; x < obj.length; x++) {
        if (obj[x].id == this.tableID) {
            tabNum = x;
            totalRow = obj[x].rows.length;
            for (var i = 0; i < obj[x].rows[0].cells.length; i++) {
                myHTMLTableCell = obj[x].rows(0).cells(i);
                myCellColSpan = myHTMLTableCell.colSpan;
                totalCol = totalCol + myCellColSpan;
            }
        }
    }
//开始构件模拟表格
    var excelTable = new Array();
    for (var i = 0; i <= totalRow; i++) {
        excelTable[i] = new Array();
        for (var t = 0; t <= totalCol; t++) {
            excelTable[i][t] = false;
        }
    }

//开始转换表格
	this.title = obj[tabNum].label;
    for (z = 0; z < obj[tabNum].rows.length; z++) {
        readRow = z + 1;
        readCol = 0;
        for (c = 0; c < obj[tabNum].rows(z).cells.length; c++) {
            myHTMLTableCell = obj[tabNum].rows(z).cells(c);
            myCellColSpan = myHTMLTableCell.colSpan;
            myCellRowSpan = myHTMLTableCell.rowSpan;
            for (y = 1; y <= totalCol; y++) {
                if (excelTable[readRow][y] == false) {
                    readCol = y;
                    break;
                }
            }
            if (myCellColSpan * myCellRowSpan > 1) {
                myExcelCell = myWorksheet.Cells(readRow, readCol);
                myExcelCell2 = myWorksheet.Cells(readRow + myCellRowSpan - 1, readCol + myCellColSpan - 1);
                myWorksheet.Range(myExcelCell, myExcelCell2).Merge();
                myExcelCell.HorizontalAlignment = this.textAlign;
                myExcelCell.Font.Size = this.fontSize;
                myExcelCell.Font.Name = this.fontStyle;
                myExcelCell.wrapText = this.lineWrap;
                myExcelCell.Interior.ColorIndex = this.backGround;
                myExcelCell.Font.ColorIndex = this.fontColor;
                if(this.tableBorder != -1){
                 myWorksheet.Range(myExcelCell, myExcelCell2).Borders(1).Weight = this.tableBorder ;
                 myWorksheet.Range(myExcelCell, myExcelCell2).Borders(2).Weight = this.tableBorder ;
                 myWorksheet.Range(myExcelCell, myExcelCell2).Borders(3).Weight = this.tableBorder ;
                 myWorksheet.Range(myExcelCell, myExcelCell2).Borders(4).Weight = this.tableBorder ;
                }
                inputAry = myHTMLTableCell.getElementsByTagName("input");
                if(inputAry.length>0){
                	for(j=0;j<inputAry.length;j++){
                		myExcelCell.Value = "'"+inputAry[j].dataset.getString(inputAry[j].dataField);
               	 }
               	}else{
               		 if(myHTMLTableCell.innerText.indexOf("*")==0){
               		 	 alert(myHTMLTableCell.innerText);
                	 	 myExcelCell.Value = myHTMLTableCell.innerText.replace("*","");
                	 }else{
                	 	 myExcelCell.Value = myHTMLTableCell.innerText;
                	 }
                }
                for (row = readRow; row <= myCellRowSpan + readRow - 1; row++) {
                    for (col = readCol; col <= myCellColSpan + readCol - 1; col++) {
                        excelTable[row][col] = true;
                    }
                }

                readCol = readCol + myCellColSpan;
            } else {
                 myExcelCell = myWorksheet.Cells(readRow, readCol);
              	 inputAry = myHTMLTableCell.getElementsByTagName("input");
              	 if(inputAry.length>0){
                	 for(j=0;j<inputAry.length;j++){
                 		myExcelCell.Value = "'"+inputAry[j].dataset.getString(inputAry[j].dataField);
                	}
                }else{
                	 if(myHTMLTableCell.innerText.indexOf("*")==0){
                	 	myExcelCell.Value = myHTMLTableCell.innerText.replace("*","");
                	 }else{
                	 	 myExcelCell.Value = myHTMLTableCell.innerText;
                	 }
                }
                myExcelCell.HorizontalAlignment = this.textAlign;
                myExcelCell.Font.Size = this.fontSize;
                myExcelCell.Font.Name = this.fontStyle;
                myExcelCell.wrapText = this.lineWrap;
                myExcelCell.Interior.ColorIndex = this.backGround;
                myExcelCell.Font.ColorIndex = this.fontColor;
                if(this.tableBorder != -1){
                 myExcelCell.Borders(1).Weight = this.tableBorder ;
                 myExcelCell.Borders(2).Weight = this.tableBorder ;
                 myExcelCell.Borders(3).Weight = this.tableBorder ;
                 myExcelCell.Borders(4).Weight = this.tableBorder ;
                }
                excelTable[readRow][readCol] = true;
                readCol = readCol + 1;
            }
        }
    }
    if(this.autoFit == true)
     myWorksheet.Columns.AutoFit;

    _result = myWorksheet.rows(1).Insert();
    if(_result){
    	for(i=0;i<totalCol;i++){
    		curCol = i+1;
    		curCell = myWorksheet.Cells(1, curCol);
    		if(i>0){
    			preCell = myWorksheet.Cells(1, curCol-1);
    			myWorksheet.Range(preCell, curCell).Merge();
    		}
    	}
    	titlCell = myWorksheet.Cells(1,1);
    	titlCell.HorizontalAlignment = this.textAlign;
        titlCell.Font.Size = this.fontSize * 2;
        titlCell.Font.Name = this.fontStyle;
        titlCell.wrapText = this.lineWrap;
        titlCell.Interior.ColorIndex = this.backGround;
        titlCell.Font.ColorIndex = this.fontColor;
    	titlCell.value=this.title;
    	excelTable[1][1] = true;

		totalRow = obj[tabNum].rows.length;
    	for (z = 1; z < totalRow+1; z++) {
    		myWorksheet.Cells(z+1,totalCol).Borders(1).Weight = this.tableBorder ;
            myWorksheet.Cells(z+1,totalCol).Borders(2).Weight = this.tableBorder ;
            myWorksheet.Cells(z+1,totalCol).Borders(3).Weight = this.tableBorder ;
            myWorksheet.Cells(z+1,totalCol).Borders(4).Weight = this.tableBorder ;
    	}
    }


    jXls.UserControl = true;
    jXls.visible = true;
    jXls = null;
    myWorkbook = null;
    myWorksheet = null;
};

TableToExcel.prototype.appendExcelFile = function (htWorkSheet) {
    var  readRow = 0,  readCol = 0;
    var totalRow = 0, totalCol = 0;
    var   tabNum = 0;
    myWorksheet = htWorkSheet.getMyWorksheet();
	//设置行高、列宽
    if(this.columnWidth != -1)
     	myWorksheet.Columns.ColumnWidth = this.columnWidth;
    else
     	myWorksheet.Columns.ColumnWidth = 7;
    if(this.rowHeight != -1)
    	 myWorksheet.Rows.RowHeight = this.rowHeight ;
     myWorksheet.Rows.RowHeight = 20 ;

//搜索需要转换的Table对象，获取对应行、列数
    var obj = _document_.all.tags("table");
    for (var x = 0; x < obj.length; x++) {
        if (obj[x].id == this.tableID) {
            tabNum = x;
            totalRow = obj[x].rows.length;
            for (i = 0; i < obj[x].rows[0].cells.length; i++) {
                myHTMLTableCell = obj[x].rows(0).cells(i);
                myCellColSpan = myHTMLTableCell.colSpan;
                totalCol = totalCol + myCellColSpan;
            }
        }
    }
//开始构件模拟表格
    var excelTable = new Array();
    for (var i = 0; i <= totalRow; i++) {
        excelTable[i] = new Array();
        for (t = 0; t <= totalCol; t++) {
            excelTable[i][t] = false;
        }
    }

//开始转换表格
	this.title = obj[tabNum].label;
    for (z = 0; z < obj[tabNum].rows.length; z++) {
        readRow = z + 1;
        readCol = 0;
        for (c = 0; c < obj[tabNum].rows(z).cells.length; c++) {
            myHTMLTableCell = obj[tabNum].rows(z).cells(c);
            myCellColSpan = myHTMLTableCell.colSpan;
            myCellRowSpan = myHTMLTableCell.rowSpan;
            for (var y = 1; y <= totalCol; y++) {
                if (excelTable[readRow][y] == false) {
                    readCol = y;
                    break;
                }
            }
            if (myCellColSpan * myCellRowSpan > 1) {
                myExcelCell = myWorksheet.Cells(readRow + htWorkSheet.curRow , readCol);
                myExcelCell2 = myWorksheet.Cells(readRow + htWorkSheet.curRow + myCellRowSpan - 1, readCol + myCellColSpan - 1);
                myWorksheet.Range(myExcelCell, myExcelCell2).Merge();
                myExcelCell.HorizontalAlignment = this.textAlign;
                myExcelCell.Font.Size = this.fontSize;
                myExcelCell.Font.Name = this.fontStyle;
                myExcelCell.wrapText = this.lineWrap;
                myExcelCell.Interior.ColorIndex = this.backGround;
                myExcelCell.Font.ColorIndex = this.fontColor;
                if(this.tableBorder != -1){
                 myWorksheet.Range(myExcelCell, myExcelCell2).Borders(1).Weight = this.tableBorder ;
                 myWorksheet.Range(myExcelCell, myExcelCell2).Borders(2).Weight = this.tableBorder ;
                 myWorksheet.Range(myExcelCell, myExcelCell2).Borders(3).Weight = this.tableBorder ;
                 myWorksheet.Range(myExcelCell, myExcelCell2).Borders(4).Weight = this.tableBorder ;
                }
                var inputAry = myHTMLTableCell.getElementsByTagName("input");
                if(inputAry.length>0){
                	for(var j=0;j<inputAry.length;j++){
                		myExcelCell.Value = "'"+inputAry[j].dataset.getString(inputAry[j].dataField);
               	 }
               	}else{
                	if(myHTMLTableCell.innerText.indexOf("*")==0){
                	 	myExcelCell.Value = "'"+myHTMLTableCell.innerText.replace("*","");
                	 }else{
                	 	 myExcelCell.Value = "'"+myHTMLTableCell.innerText;

                	 }
                }
                for (row = readRow; row <= myCellRowSpan + readRow - 1; row++) {
                    for (col = readCol; col <= myCellColSpan + readCol - 1; col++) {
                        excelTable[row][col] = true;
                    }
                }
                readCol = readCol + myCellColSpan;
            } else {
                 myExcelCell = myWorksheet.Cells( readRow + htWorkSheet.curRow, readCol );
              	 var inputAry = myHTMLTableCell.getElementsByTagName("input");
              	 if(inputAry.length>0){
                	 for(var j=0;j<inputAry.length;j++){
                 		myExcelCell.Value = "'"+inputAry[j].dataset.getString(inputAry[j].dataField);
                	}
                }else{
                	if(myHTMLTableCell.innerText.indexOf("*")==0){
                	 	myExcelCell.Value = "'"+myHTMLTableCell.innerText.replace("*","");
                	 	myExcelCell.Font.Bold = true;
                	 }else{
                	 	myExcelCell.Value = "'"+myHTMLTableCell.innerText;
                	 	myExcelCell.Font.Bold = true;
                	 }
                }
                myExcelCell.HorizontalAlignment = this.textAlign;
                myExcelCell.Font.Size = this.fontSize;
                myExcelCell.Font.Name = this.fontStyle;
                myExcelCell.wrapText = this.lineWrap;
                myExcelCell.Interior.ColorIndex = this.backGround;
                myExcelCell.Font.ColorIndex = this.fontColor;
                if(this.tableBorder != -1){
                 myExcelCell.Borders(1).Weight = this.tableBorder ;
                 myExcelCell.Borders(2).Weight = this.tableBorder ;
                 myExcelCell.Borders(3).Weight = this.tableBorder ;
                 myExcelCell.Borders(4).Weight = this.tableBorder ;
                }
                excelTable[readRow][readCol] = true;
                readCol = readCol + 1;
            }
        }
    }
    //if(this.autoFit == true)


    _result = myWorksheet.rows(htWorkSheet.curRow + 1).Insert();
    if(_result){
    	for(var i=0;i<totalCol;i++){
    		curCol = i+1;
    		curCell = myWorksheet.Cells(htWorkSheet.curRow + 1, curCol);
    		if(i>0){
    			preCell = myWorksheet.Cells(htWorkSheet.curRow + 1, curCol-1);
    			myWorksheet.Range(preCell, curCell).Merge();
    		}
    	}
    	titlCell = myWorksheet.Cells(htWorkSheet.curRow + 1,1);
    	titlCell.HorizontalAlignment = this.textAlign;
        titlCell.Font.Size = this.fontSize * 2;
        titlCell.Font.Name = this.fontStyle;
        titlCell.wrapText = this.lineWrap;
        titlCell.Interior.ColorIndex = this.backGround;
        titlCell.Font.ColorIndex = this.fontColor;
    	titlCell.value=this.title;
    	excelTable[1][1] = true;
		totalRow = obj[tabNum].rows.length;
    	for (var z = htWorkSheet.curRow + 1; z < totalRow+1; z++) {
    		myWorksheet.Cells(z+1,totalCol).Borders(1).Weight = this.tableBorder ;
            myWorksheet.Cells(z+1,totalCol).Borders(2).Weight = this.tableBorder ;
            myWorksheet.Cells(z+1,totalCol).Borders(3).Weight = this.tableBorder ;
            myWorksheet.Cells(z+1,totalCol).Borders(4).Weight = this.tableBorder ;
    	}
    }
    htWorkSheet.curRow += readRow + 1;
};