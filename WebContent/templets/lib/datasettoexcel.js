/*
 * Ĭ��ת��ʵ�ֺ����������Ҫ�������ܣ���������չ
 * ������
 *      tableID : HTML��Table����id����ֵ
 * ��ϸ�÷��μ����� TableToExcel ������
 */
var _document_;

function printDatasetASExcel(dataset,fieldStr,title){
	//funPreHook(_theme_root + "/templets/lib/themes/default/loading.gif");
	var v_dataset = dataset;
	if(typeof(v_dataset)!="object"){
		alert("not found dataset");
	}
	var htWorkSheet = new HTWorksheet();
	htWorkSheet.jXls.UserControl = true;
	var tb = new DatasetToExcel(dataset,fieldStr,title);
    tb.setFontStyle("Courier New");
    tb.setFontSize(9);
    tb.setTableBorder(2);
    tb.setColumnWidth(7);
    tb.isLineWrap(true);
	tb.appendExcelFile(htWorkSheet);
	htWorkSheet.myWorksheet.Columns.AutoFit;
	htWorkSheet.jXls.UserControl = true;
	htWorkSheet.jXls.Visible = true;
    delete htWorkSheet.jXls;
    delete htWorkSheet.myWorkbook;
    delete htWorkSheet.myWorksheet;
    delete htWorkSheet;

}


function HTWorksheet(){
	this.curRow = 0; //��ǰ�к�
	this.myWorkbook = null;//��ǰworkbook����
	this.myWorksheet = null;//��ǰworksheet����
	this.jXls = null;
    try {
        this.jXls = new ActiveXObject('Excel.Application');
    }
    catch (e) {
        alert("�޷�����Excel!\n\n" + e.message +
           "\n\n�����ȷ�����ĵ������Ѿ���װ��Excel��"+
           "��ô�����IE�İ�ȫ����(��ȫ�� - ��)��\n\n���������\n\n"+
           "���� �� Internetѡ�� �� ��ȫ �� �����ε�վ�� �� ������ȫ����(��) \n\n"+
           "Ȼ��ѱ���ַ��ӵ������ε�վ���У�������˳�ϵͳ���ٴε�½��");
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



function saveAsExcel(dataset,fieldStr,title){
  var tb = new DatasetToExcel(dataset,fieldStr,title);
  tb.setFontStyle("Courier New");
  tb.setFontSize(10);
  tb.setTableBorder(2);
  tb.setColumnWidth(7);
  tb.isLineWrap(true);
  tb.getExcelFile();
}

/*
 *  ���ܣ�HTML��Table����ת��ΪExcelͨ�ö���.
 *  ���ߣ�Jeva
 *  ʱ�䣺2006-08-09
 *  ������tableID  HTML��Table�����ID����ֵ
 *  ˵����
 *       ����Ӧ���ӵ�HTML��Table������Զ�ת�����ܹ��Զ�����������չ��Ϣ
 *       �ϲ�Excel�еĵ�Ԫ�񣬿ͻ�����Ҫ��װ��Excel
 *       ��ϸ�����ԡ���������˵���μ���Excel��Microsoft Excel Visual Basic�ο�
 *  ʾ����
 *       var tb = new TableToExcel('demoTable');
 *    tb.setFontStyle("Courier New");
 *    tb.setFontSize(10);  //�Ƽ�ȡֵ10
 *    tb.setFontColor(6);  //һ�������������
 *    tb.setBackGround(4);  //һ�������������
 *    tb.setTableBorder(2);  //�Ƽ�ȡֵ2
 *    tb.setColumnWidth(10);  //�Ƽ�ȡֵ10
 *    tb.isLineWrap(false);
 *    tb.isAutoFit(true);
 *
 *    tb.getExcelFile();
 *   ��������˵�Ԫ������Ӧ�������õ�Ԫ������Ч
 *  �汾��1.0

 *  BUG�ύ��QQ:18234348
 */
function DatasetToExcel(dataset,fieldStr,title) {
    this.tableBorder = 2; //�߿����ͣ�-1û�б߿� ��ȡ1/2/3/4
    this.backGround = 0; //������ɫ����ɫ   ��ȡ��ɫ���е���ɫ��� 1/2/3/4....
    this.fontColor = 1;  //������ɫ����ɫ
    this.fontSize = 10;  //�����С
    this.fontStyle = "����"; //��������
    this.rowHeight = -1; //�и�
    this.columnWidth = -1; //�п�
    this.lineWrap = true; //�Ƿ��Զ�����
    this.textAlign = -4108; //���ݶ��뷽ʽ   Ĭ��Ϊ����
    this.autoFit = true;  //�Ƿ�����Ӧ���
    this.dataset = dataset;
    this.title = title;//������
    this.fieldStr = fieldStr;
}

DatasetToExcel.prototype.setTableFieldStr = function (fieldStr) {
	this.fieldStr = fieldStr;
}
DatasetToExcel.prototype.setDataset = function (dataset) {
	this.dataset = dataset;
}

DatasetToExcel.prototype.setTableTitle = function (title) {
	this.title = title;
}

DatasetToExcel.prototype.setTableBorder = function (excelBorder) {
    this.tableBorder = excelBorder ;
};


DatasetToExcel.prototype.setBackGround = function (excelColor) {
    this.backGround = excelColor;
};

DatasetToExcel.prototype.setFontColor = function (excelColor) {
    this.fontColor = excelColor;
};

DatasetToExcel.prototype.setFontSize = function (excelFontSize) {
    this.fontSize = excelFontSize;
};

DatasetToExcel.prototype.setFontStyle = function (excelFont) {
    this.fontStyle = excelFont;
};

DatasetToExcel.prototype.setRowHeight = function (excelRowHeight) {
    this.rowHeight = excelRowHeight;
};

DatasetToExcel.prototype.setColumnWidth = function (excelColumnWidth) {
    this.columnWidth = excelColumnWidth;
};

DatasetToExcel.prototype.isLineWrap = function (lineWrap) {
    if (lineWrap == false || lineWrap == true) {
        this.lineWrap = lineWrap;
    }
};

DatasetToExcel.prototype.setTextAlign = function (textAlign) {
    this.textAlign = textAlign;
};

DatasetToExcel.prototype.isAutoFit = function(autoFit){
 if(autoFit == true || autoFit == false)
  this.autoFit = autoFit ;
}


//�ļ�ת��������
DatasetToExcel.prototype.getExcelFile = function () {
    var jXls, myWorkbook, myWorksheet, myHTMLTableCell, myExcelCell, myExcelCell2;
    var myCellColSpan, myCellRowSpan;

    try {
        jXls = new ActiveXObject('Excel.Application');
    }
    catch (e) {
        alert("�޷�����Excel!\n\n" + e.message +
           "\n\n�����ȷ�����ĵ������Ѿ���װ��Excel��"+
           "��ô�����IE�İ�ȫ����(��ȫ�� - ��)��\n\n���������\n\n"+
           "���� �� Internetѡ�� �� ��ȫ �� �����ε�վ�� �� ������ȫ����(��) \n\n"+
           "Ȼ��ѱ���ַ��ӵ������ε�վ���У�������˳�ϵͳ���ٴε�½��");
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
    var tabNum = 0;
    var fieldArray = new Array();
	var v_dataset = dataset;

//�����иߡ��п�
    if(this.columnWidth != -1)
     myWorksheet.Columns.ColumnWidth = this.columnWidth;
    else
     myWorksheet.Columns.ColumnWidth = 7;
    if(this.rowHeight != -1)
    	 myWorksheet.Rows.RowHeight = this.rowHeight ;
     myWorksheet.Rows.RowHeight = 20 ;

//������Ҫת����Table���󣬻�ȡ��Ӧ�С�����

    fieldArray = fieldStr.split(",");
    tabNum = fieldArray.length;
    totalRow = v_dataset.length;
    totalCol = fieldArray.length;
    /*
    for (x = 0; x < fieldArray.length; x++) {
       for (var i = 0; i < obj[x].rows[0].cells.length; i++) {
          myHTMLTableCell = obj[x].rows(0).cells(i);
          myCellColSpan = myHTMLTableCell.colSpan;
          totalCol = totalCol + myCellColSpan;
       }
    }
    */
//��ʼ����ģ����
    var excelTable = new Array();
    for (var i = 0; i <= totalRow; i++) {
        excelTable[i] = new Array();
        for (var t = 0; t <= totalCol; t++) {
            excelTable[i][t] = false;
        }
    }

//��ʼת�����
 	var z = 0;
 	var record = v_dataset.firstUnit;
    while(record){
        readRow = z + 1;
    	z++;
        readCol = 0;
        for (c = 0; c < totalCol; c++) {
        /*
            myHTMLTableCell = obj[tabNum].rows(z).cells(c);
            myCellColSpan = myHTMLTableCell.colSpan;
            myCellRowSpan = myHTMLTableCell.rowSpan;
            */
            myCellColSpan = 1;
            myCellRowSpan = 1;
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
                /*
                inputAry = myHTMLTableCell.getElementsByTagName("input");
                if(inputAry.length>0){
                	for(j=0;j<inputAry.length;j++){
                		myExcelCell.Value = "'"+inputAry[j].dataset.getString(inputAry[j].dataField);
               	 }
               	 */
               	 myExcelCell = converCellValue(myExcelCell,record,fieldArray[c]);
                for (row = readRow; row <= myCellRowSpan + readRow - 1; row++) {
                    for (col = readCol; col <= myCellColSpan + readCol - 1; col++) {
                        excelTable[row][col] = true;
                    }
                }
                readCol = readCol + myCellColSpan;
            } else {
                 myExcelCell = myWorksheet.Cells(readRow, readCol);
                 /*
              	 inputAry = myHTMLTableCell.getElementsByTagName("input");
              	 if(inputAry.length>0){
                	 for(j=0;j<inputAry.length;j++){
                 		myExcelCell.Value = "'"+inputAry[j].dataset.getString(inputAry[j].dataField);
                	}
                	*/
                 myExcelCell = converCellValue(myExcelCell,record,fieldArray[c]);
                 /*
                }else{
                	 if(myHTMLTableCell.innerText.indexOf("*")==0){
                	 	myExcelCell.Value = myHTMLTableCell.innerText.replace("*","");
                	 }else{
                	 	 myExcelCell.Value = myHTMLTableCell.innerText;
                	 }
                }
                */
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
        record=record.nextUnit;
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

DatasetToExcel.prototype.appendExcelFile = function (htWorkSheet) {
    var  readRow = 0,  readCol = 0;
    var totalRow = 0, totalCol = 0;
    var   tabNum = 0;
    var fieldArray = new Array();
	var v_dataset = this.dataset;
    myWorksheet = htWorkSheet.getMyWorksheet();
	//�����иߡ��п�
    if(this.columnWidth != -1)
     	myWorksheet.Columns.ColumnWidth = this.columnWidth;
    else
     	myWorksheet.Columns.ColumnWidth = 7;
    if(this.rowHeight != -1)
    	 myWorksheet.Rows.RowHeight = this.rowHeight ;
     myWorksheet.Rows.RowHeight = 20 ;

//������Ҫת����Table���󣬻�ȡ��Ӧ�С�����
/*
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
    */
    fieldArray = this.fieldStr.split(",");
    tabNum = fieldArray.length;
    totalRow = v_dataset.length + 1;
    totalCol = fieldArray.length;

//��ʼ����ģ����
    var excelTable = new Array();
    for (var i = 0; i <= totalRow; i++) {
        excelTable[i] = new Array();
        for (t = 0; t <= totalCol; t++) {
            excelTable[i][t] = false;
        }
    }

//��ʼת�����
	var z = 0;
 	var record = v_dataset.firstUnit;
 	//add head
    for(z = 0;z < totalRow; z++){
        readRow = z + 1;
        readCol = 0;
        for (c = 0; c < totalCol; c++) {
            myCellColSpan = 1;
            myCellRowSpan = 1;
            for (y = 1; y <= totalCol; y++) {
                if (excelTable[readRow][y] == false) {
                    readCol = y;
                    break;
                }
            }
            if( z==0 ){
            	 var v_filed = v_dataset.getField(fieldArray[c])
            	 myExcelCell = myWorksheet.Cells(readRow, readCol);
                 myExcelCell.Value = "'"+ v_filed.label;
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
            }else{
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
                /*
                inputAry = myHTMLTableCell.getElementsByTagName("input");
                if(inputAry.length>0){
                	for(j=0;j<inputAry.length;j++){
                		myExcelCell.Value = "'"+inputAry[j].dataset.getString(inputAry[j].dataField);
               	 }
               	 */
               	 myExcelCell = converCellValue(myExcelCell,record,fieldArray[c]);
                for (row = readRow; row <= myCellRowSpan + readRow - 1; row++) {
                    for (col = readCol; col <= myCellColSpan + readCol - 1; col++) {
                        excelTable[row][col] = true;
                    }
                }
                readCol = readCol + myCellColSpan;
            } else {
                 myExcelCell = myWorksheet.Cells(readRow, readCol);
                 /*
                 myExcelCell.Value = "'"+record.getViewString(fieldArray[c]);
                 .*/
                 myExcelCell = converCellValue(myExcelCell,record,fieldArray[c]);
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
      if(z>0){
     	 record=record.nextUnit;
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
		totalRow = v_dataset.length;
    	for (var z = htWorkSheet.curRow + 1; z < totalRow+1; z++) {
    		myWorksheet.Cells(z+1,totalCol).Borders(1).Weight = this.tableBorder ;
            myWorksheet.Cells(z+1,totalCol).Borders(2).Weight = this.tableBorder ;
            myWorksheet.Cells(z+1,totalCol).Borders(3).Weight = this.tableBorder ;
            myWorksheet.Cells(z+1,totalCol).Borders(4).Weight = this.tableBorder ;
    	}
    }
    htWorkSheet.curRow += readRow + 1;
};

   /**
	 * conver dataset data to xls
	 */
 function converCellValue(cell,record,fieldName){
   	var field = record.dataset.getField(fieldName);
   	switch (field.dataType){
			case "string":;
			case "byte":;
			case "short":{
			    cell.NumberFormatLocal = "@";
				cell.Value = record.getViewString(fieldName);
				break;
			}
			case "int":{
				cell.NumberFormatLocal = "@";
				cell.Value = record.getViewString(fieldName);
				break;
			}
			case "long":{
				cell.NumberFormatLocal = "@";
				cell.Value = record.getViewString(fieldName);
				break;
			}
			case "currency":{
				cell.Value = record.getViewString(fieldName);
				break;
			}
			case "float":{
				cell.Value = record.getViewString(fieldName);
				break;
			}
			case "double":{
				cell.Value = record.getViewString(fieldName);
				break;
			}
			case "bigdecimal":{
				cell.Value = record.getViewString(fieldName);
				break;
			}
			case "date":{
				cell.Value = record.getViewString(fieldName);
				break;
			}
			case "time":{
				cell.Value = record.getViewString(fieldName);
				break;
			}
			case "timestamp":{
				cell.Value = record.getViewString(fieldName);
				break;
			}
			case "boolean":{
				cell.Value = record.getViewString(fieldName);
				break;
			}
			default:{
				cell.NumberFormatLocal = "@";
				cell.Value = record.getViewString(fieldName);
				break;
			}
		}
   	return cell;
 }
 