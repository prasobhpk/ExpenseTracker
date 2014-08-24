package com.pk.et.exp.util;

//~--- non-JDK imports --------------------------------------------------------

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.pk.et.exp.exceptions.ExcelParseException;
import com.pk.et.exp.model.Expense;
import com.pk.et.exp.model.ExpenseType;


/**
 * This java program is used to read the data from a Excel file and display them
 * on the console output.
 * 
 * @author dhanago
 */
public class POIExcelReader {
	private static final String[] months = { "January", "February", "March",
			"April", "May", "June", "July", "August", "September", "October",
			"November", "December" };
	private static final String[] cellTypes = { "Date", "Numeric", "Text" };
	private static final int CASE_DATE = 1;
	private static final int CASE_EXPENSE = 2;
	private static final int CASE_DESC = 3;
	private static final int CASE_TYPE = 4;

	/** Creates a new instance of POIExcelReader */
	public POIExcelReader() {
	}

	/**
	 * This method is used to display the Excel content to command line.
	 * 
	 * @param xlsPath
	 * @throws InvalidFormatException
	 */
	public List<Expense> readExpnsesFromExcel(InputStream inputStream)
			throws ExcelParseException {
		List<Expense> expenses = new ArrayList<Expense>();
		StringBuilder errs = new StringBuilder();
		Expense expense = null;
		boolean isFileDerty = false;
		try {

			Workbook workBook = null;
			try {
				workBook = WorkbookFactory.create(inputStream);
			} catch (InvalidFormatException e) {
				errs.append("The supported file types are .xls and .xlsx");
				throw new ExcelParseException(errs.toString());
			} catch (IllegalArgumentException e) {
				errs.append("The supported file types are .xls and .xlsx");
				throw new ExcelParseException(errs.toString());
			}
			//if Only one sheet is there(data from downloaded)
			if (workBook.getNumberOfSheets() < 12) {
				Sheet sheet = workBook.getSheetAt(0);
				Iterator<Row> rows = sheet.iterator();
				// to avoid first row that contains the column headers
				boolean b = false;
				while (rows.hasNext()) {
					Row row = rows.next();
					if (b) {
						expense = new Expense();
						// once get a row its time to iterate through cells.
						Iterator<Cell> cells = row.cellIterator();
						int columnType = 0;
						while (++columnType < 5 && cells.hasNext()) {
							Cell cell = cells.next();
							try {
								switch (columnType) {
								case CASE_DATE: {
									expense.setExpDate(cell.getDateCellValue());
									break;
								}
								case CASE_EXPENSE: {
									expense.setExpense(new Double(cell
											.getNumericCellValue()).intValue());
									break;
								}
								case CASE_DESC: {
									expense.setDescription(cell
											.getStringCellValue());
									break;
								}
								case CASE_TYPE: {
									expense.setExpenseType(new ExpenseType(cell.getStringCellValue()));
									break;
								}

								}
							} catch (IllegalStateException e) {
								errs.append("Format Error: Sheet "
										+ sheet.getSheetName() + " Row "
										+ row.getRowNum() + "  Column "
										+ cell.getColumnIndex()
										+ " ---> Should be "
										+ cellTypes[columnType - 1] + "<br/>");
								isFileDerty = true;
								// throw e;
							}

						}
						// only add if there are no format errors in the
						// excel
						if (!isFileDerty) {
							expenses.add(expense);
						}
					}
					b = true;
				}
				if (isFileDerty) {
					throw new ExcelParseException(errs.toString());
				}
			} else {
				for (String month : months) {
					Sheet sheet = workBook.getSheet(month);
					Iterator<Row> rows = sheet.iterator();
					// to avoid first row that contains the column headers
					boolean b = false;
					while (rows.hasNext()) {
						Row row = rows.next();
						if (b) {
							expense = new Expense();
							// once get a row its time to iterate through cells.
							Iterator<Cell> cells = row.cellIterator();
							int columnType = 0;
							while (++columnType < 4 && cells.hasNext()) {
								Cell cell = cells.next();
								try {
									switch (columnType) {
									case CASE_DATE: {
										expense.setExpDate(cell
												.getDateCellValue());
										break;
									}
									case CASE_EXPENSE: {
										expense.setExpense(new Double(cell
												.getNumericCellValue())
												.intValue());
										break;
									}
									case CASE_DESC: {
										expense.setDescription(cell
												.getStringCellValue());
										break;
									}
									}
								} catch (IllegalStateException e) {
									errs.append("Format Error: Sheet "
											+ sheet.getSheetName() + " Row "
											+ row.getRowNum() + "  Column "
											+ cell.getColumnIndex()
											+ " ---> Should be "
											+ cellTypes[columnType - 1]
											+ "<br/>");
									isFileDerty = true;
									// throw e;
								}

							}
							// only add if there are no format errors in the
							// excel
							if (!isFileDerty) {
								expenses.add(expense);
							}
						}
						b = true;
					}
				}
				if (isFileDerty) {
					throw new ExcelParseException(errs.toString());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		List<Expense> empty = Collections.emptyList();
		return expenses.size() > 0 ? expenses : empty;
	}

	/**
	 * Create a library of cell styles
	 */
	public static Map<String, CellStyle> createStyles(Workbook wb) {
		Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
		CellStyle style;
		Font titleFont = wb.createFont();
		titleFont.setFontHeightInPoints((short) 18);
		titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setFont(titleFont);
		styles.put("title", style);

		Font monthFont = wb.createFont();
		monthFont.setFontHeightInPoints((short) 11);
		monthFont.setColor(IndexedColors.WHITE.getIndex());
		style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setFont(monthFont);
		style.setWrapText(true);
		styles.put("header", style);

		style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_LEFT);
		style.setWrapText(true);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setRightBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		styles.put("cell", style);

		style = wb.createCellStyle();
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setRightBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		short dateFormat = HSSFDataFormat.getBuiltinFormat("m/d/yy");
		style.setDataFormat(dateFormat);
		styles.put("date", style);

		style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setDataFormat(wb.createDataFormat().getFormat("0.00"));
		styles.put("formula", style);

		style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setDataFormat(wb.createDataFormat().getFormat("0.00"));
		styles.put("formula_2", style);

		return styles;
	}

	// public static void main(String[] args) {
	// POIExcelReader poiExample = new POIExcelReader();
	// String xlsPath = "C:\\ExpenseTracker_Template.xlsx";
	// FileInputStream inputStream = null;
	// try {
	// inputStream = new FileInputStream(xlsPath);
	// } catch (FileNotFoundException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// List<Expense> expenses;
	// try {
	// expenses = poiExample.readExpnsesFromExcel(inputStream);
	// System.out.println(expenses.size());
	// for (Expense s : expenses) {
	// System.out.println(s.getExpense());
	// }
	// } catch (ExcelParseException e) {
	// System.out.println(e.getMessage());
	// }
	// }

}
