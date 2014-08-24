package com.pk.et.exp.web.views;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.pk.et.exp.model.Expense;
import com.pk.et.exp.util.POIExcelReader;


//@Component("excelExpenses")
public class ExcelExpenseView extends AbstractExcelView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model,
            HSSFWorkbook workbook, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	@SuppressWarnings("unchecked")
		List<Expense> expenses=(List<Expense>)model.get("expenseData");
        // create a wordsheet
    	Workbook wb=workbook;
    	Map<String, CellStyle> styles = POIExcelReader.createStyles(wb);

        Sheet sheet = wb.createSheet("Expense Report");
        sheet.setColumnWidth(2, 15000);

        Row row=null;
        Cell cell=null;
        //Header row
        row = sheet.createRow(0);
        row.setHeightInPoints(40);

        
        cell=row.createCell(0);
        cell.setCellValue("Date");
        cell.setCellStyle(styles.get("header"));
        
        cell=row.createCell(1);
        cell.setCellValue("Expense");
        cell.setCellStyle(styles.get("header"));
        
        cell=row.createCell(2);
        cell.setCellValue("Description");
        cell.setCellStyle(styles.get("header"));
        
        cell=row.createCell(3);
        cell.setCellValue("Type");
        cell.setCellStyle(styles.get("header"));
        
        int rowNum = 1;
        for (Expense exp:expenses) {
            // Data row
            row = sheet.createRow(rowNum++);
            
            //Date Cell
            cell=row.createCell(0);
            cell.setCellValue(exp.getExpDate());
            cell.setCellStyle(styles.get("date"));
           
            //Expense Cell
            cell=row.createCell(1);
            cell.setCellValue(exp.getExpense());
            cell.setCellStyle(styles.get("cell"));
            
            //Description Cell
            cell=row.createCell(2);
            cell.setCellValue(exp.getDescription());
            cell.setCellStyle(styles.get("cell"));
            
            //Type Cell
            cell=row.createCell(3);
            cell.setCellValue(exp.getExpenseType().getType());
            cell.setCellStyle(styles.get("cell"));
        }

    }

}

