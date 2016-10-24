package com.ycii.core.utils.excel;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellRangeAddress;

import com.ycii.core.utils.string.StringUtil;

/**
 * Copyright(c) 2013 hofort. All Rights Reserved.
 * Compiler: JDK1.6.0_23
 * @author Kaylves
 * @create_date 2014-6-20 下午06:01:02
 * @version 1.0
 * @update_user Kaylves
 * @update_date 2014-6-20 下午06:01:02
 * @description Excel报表模板
 */
public final class ExcelReportTemplate {
	
	/**
	 * @author Kaylves
	 * @time 2014-6-20 下午06:04:08
	 * @param sheetName
	 * @param list
	 * @param columnNameList
	 * @param columnNameMap
	 * @return
	 * @throws Exception
	 * HSSFWorkbook
	 * @description
	 * @version 1.0
	 */
	protected HSSFWorkbook exportExcelReport(String sheetName, List<Map<String, Object>> list, 
			List<String> columnNameList,Map<String,String> columnNameMap) throws Exception {
		
		HSSFWorkbook workbook = new HSSFWorkbook();					// Create a workbook for the temporary excel file.
		HSSFSheet sheet = workbook.createSheet(); 					// Create a new sheet for this workbook.
		workbook.setSheetName(0, sheetName);						// Set sheet name for this sheet.
		int rowIndex = 0;											// Set an index for each row of this sheet.
		HSSFRow row = sheet.createRow(rowIndex++);					// Create first row to write the column names of data table.
		HSSFCell cell;
		
		/**
		 * Set title style for this sheet.
		 */
		HSSFCellStyle titleStyle = workbook.createCellStyle(); 
		titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		HSSFFont titleFont = workbook.createFont();
		titleFont.setFontName("黑体");
		titleFont.setFontHeightInPoints((short)14);
		titleStyle.setFont(titleFont);
		
		cell = row.createCell(0);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		CellRangeAddress range = new CellRangeAddress(0, 0, 0, columnNameList.size());
		sheet.addMergedRegion(range); 								// Merge cells for title row.
		cell.setCellValue(sheetName);
		cell.setCellStyle(titleStyle);
		
	    row = sheet.createRow(rowIndex++);
		
	    for (int i = 0; i < columnNameList.size(); i++) {			// Write in cell for each column name.
			cell = row.createCell(i);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(columnNameMap.get(columnNameList.get(i)));
		}
		
		for(int i = 0; i < list.size(); i++) {
			if (i!=0&&i % 65535== 0) {
				sheet = workbook.createSheet(); 
				workbook.setSheetName(0, sheetName+"i");
				rowIndex = 0;
			}
			row = sheet.createRow(i + rowIndex);					// Create each row for data table.
			Map<String, Object> record = list.get(i);
			for(int j = 0; j < columnNameList.size(); j++) {		// Write a value for each cell of each row in this sheet from data list.
				cell = row.createCell(j);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				if(record.get(columnNameList.get(j))!=null){
					cell.setCellValue(StringUtil.nullToEmpty(record.get(columnNameList.get(j))));
				}
			}
		}
		return workbook;
	}
}
