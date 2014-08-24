package com.pk.et.bi.core;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

import com.pk.et.infra.bi.ColumnDefinition;
import com.pk.et.infra.bi.ReportDataType;

/**
 * Reporting service interface.
 */
public interface ReportingService {

	/**
	 * An interface for a {@link ReportingTask}.
	 */
	public interface ReportingTask {
		/**
		 * Set the locale
		 * 
		 * @param locale
		 *            the locale to set.
		 */
		void setLocal(final Locale locale);
	}

	/**
	 * An interface for a {@link DesignContext}
	 */
	public interface DesignContext {

		/**
		 * Save the design.
		 */
		void saveDesign();

		/**
		 * Close the design.
		 */
		void closeDesign();

		/**
		 * Create a data source.
		 * 
		 * @param dataSourceName
		 *            the data source name.
		 */
		void createDataSource(final String dataSourceName);

		/**
		 * Create a master page.
		 * 
		 * @param masterPageName
		 *            the master page name.
		 * @param orientation
		 *            the orientation (landscape or portrait).
		 * @param margin
		 *            the margin size.
		 */
		void createMasterPage(final String masterPageName, final String orientation, final String margin);

		/**
		 * Save a design in a file.
		 * 
		 * @param fileName
		 *            the file name.
		 */
		void saveAs(final String fileName);

		/**
		 * Create a report table.
		 * 
		 * @param tableName
		 *            the table name.
		 * @param columnCount
		 *            the number of columns.
		 * @param headerRow
		 *            the number of header rows.
		 * @param detailRow
		 *            the number of detail rows.
		 * @param footerRow
		 *            the number of footer rows.
		 * @return the {@link ReportTable} created.
		 */
		ReportTable createTable(final String tableName, final int columnCount, final int headerRow,
				final int detailRow, final int footerRow);

		/**
		 * Find a grid in a design by name.
		 * 
		 * @param gridName
		 *            the grid name.
		 * @return the grid found.
		 */
		ReportGrid findGridByName(final String gridName);

		/**
		 * Find a table in a design by name.
		 * 
		 * @param tableName
		 *            the table name.
		 * @return the table found.
		 */
		ReportTable findTableByName(final String tableName);

		/**
		 * Create a data set for the given column definitions list and given
		 * open and fetch code.
		 * 
		 * @param dataSourceName
		 *            the data source name.
		 * @param dataSetName
		 *            the data set name
		 * @param openCode
		 *            the open code to use.
		 * @param fetchCode
		 *            the fetch code to use.
		 * @param columnDefinitions
		 *            the column definitions in order to build the dataset.
		 * @return the {@link ReportDataSet} created.
		 */
		ReportDataSet createDataSetWithOpenCodeAndFetchCode(final String dataSourceName, final String dataSetName,
				final String openCode, final String fetchCode, final List<ColumnDefinition> columnDefinitions);

		/**
		 * Create a data set for the given column definitions list and given an
		 * event handler class.
		 * 
		 * @param dataSourceName
		 *            the data source name.
		 * @param dataSetName
		 *            the data set name.
		 * @param columnDefinitions
		 *            the column definitions in order to build the dataset.
		 * @param eventHandlerClass
		 *            the event handler class to use.
		 * @return the {@link ReportDataSet} created.
		 */
		ReportDataSet createDataSetsWithEventHandlerClass(final String dataSourceName, final String dataSetName,
				final List<ColumnDefinition> columnDefinitions, final String eventHandlerClass);

		/**
		 * Create a list data set. It contains only one element (value), the
		 * type of this element depends on the given {@link ReportDataType}.
		 * 
		 * @param dataSourceName
		 *            the data source name.
		 * @param dataSetName
		 *            the data set name.
		 * @param eventHandlerClass
		 *            the event handler class to use.
		 * @param reportDataType
		 *            the report data type to consider.
		 */
		void createListDataSetWithEventHandlerClass(final String dataSourceName, final String dataSetName,
				final String eventHandlerClass, final ReportDataType reportDataType);

		/**
		 * Create a label with the given text.
		 * 
		 * @param text
		 *            the text to put in the label.
		 * @return the label created.
		 */
		ReportItem createLabel(final String text);

		/**
		 * Create a text data which use an expression (dynamic text).
		 * 
		 * @param expression
		 *            the expression to use.
		 * @return the text data created.
		 */
		ReportItem createTextData(String expression);

		/**
		 * Create a date item.
		 * 
		 * @param columnName
		 *            the column name to consider.
		 * @param dateFormat
		 *            the date format to use.
		 * @return the date item created.
		 */
		ReportItem createDateItem(String columnName, String dateFormat);

		/**
		 * Change the master page width.
		 * 
		 * @param masterPageName
		 *            the master page name.
		 * @param masterPageWidth
		 *            the master page width.
		 */
		void changeMasterPageWidth(final String masterPageName, final Integer masterPageWidth);

		/**
		 * Create a new style with a given name.
		 * 
		 * @param styleName
		 *            the style name.
		 * @return the style created.
		 */
		ReportStyle createStyle(String styleName);

		/**
		 * Create a new style.
		 * 
		 * @return the style created.
		 */
		ReportStyle createStyle();

		/**
		 * Define the report theme.
		 * 
		 * @param name
		 *            the name of the report theme to use.
		 */
		void defineReportTheme(String name);

		/**
		 * Create a new grid.
		 * 
		 * @param columnNum
		 *            the number of column.
		 * @param rowNum
		 *            the number of row.
		 * @return the grid created.
		 */
		ReportGrid createGrid(final int columnNum, final int rowNum);

		/**
		 * Find a style in the report context by name.
		 * 
		 * @param styleName
		 *            the style name to find.
		 * @return the style found.
		 */
		ReportStyle findStyle(final String styleName);

	}

	/**
	 * An interface for a {@link ReportStyle}.
	 */
	public interface ReportStyle {

		/**
		 * Add solid border.
		 * 
		 * @param borderWidth
		 *            the border width.
		 */
		void addSolidBorder(int borderWidth);

		/**
		 * Add solid border on top of cell.
		 * 
		 * @param borderTopWidth
		 *            the border width.
		 */
		void addSolidTopBorder(int borderWidth);

		/**
		 * Add solid border on bottom of cell.
		 * 
		 * @param borderTopWidth
		 *            the border width.
		 */
		void addSolidBottomBorder(int borderWidth);

		/**
		 * Add solid border on left of cell.
		 * 
		 * @param borderTopWidth
		 *            the border width.
		 */
		void addSolidLeftBorder(int borderWidth);

		/**
		 * Add solid border on right of cell.
		 * 
		 * @param borderTopWidth
		 *            the border width.
		 */
		void addSolidRightBorder(int borderWidth);

		/**
		 * Set the font color.
		 * 
		 * @param fontColor
		 *            the font color to set.
		 */
		void setFontColor(String fontColor);

		/**
		 * Set the font weight.
		 * 
		 * @param fontWeight
		 *            the font weight to set.
		 */
		void setFontWeight(String fontWeight);

		/**
		 * Set the background color.
		 * 
		 * @param backgroundColor
		 *            the background color to set.
		 */
		void setBackgroundColor(String backgroundColor);

		/**
		 * Set the font.
		 * 
		 * @param font
		 *            the font to set.
		 */
		void setFont(String font);

		/**
		 * Set the font size.
		 * 
		 * @param fontSize
		 *            the font size to set.
		 */
		void setFontSize(final int fontSize);

		/**
		 * Set the text alignment.
		 * 
		 * @param textAlign
		 *            the text alignment to set.
		 */
		void setTextAlign(final String textAlign);

		/**
		 * Set the number format.
		 * 
		 * @param numberFormat
		 *            the number format to set.
		 */
		void setNumberFormat(String numberFormat);

		/**
		 * Set the vertical alignment.
		 * 
		 * @param verticalAlign
		 *            the vertical alignment to set.
		 */
		void setVerticalAlign(String verticalAlign);

		/**
		 * Set text wrap. By default text will be wraped.
		 * 
		 * @param wrapValue
		 */
		void setWhiteSpaceWrap(String wrapValue);
	}

	/**
	 * An interface for a {@link ReportItem}.
	 */
	public interface ReportItem {

		/**
		 * Set the onRender script.
		 * 
		 * @param scriptOnRender
		 *            the onRender script to set.
		 */
		void setOnRender(final String scriptOnRender);

		/**
		 * Sets the value of a property to a string. Use this for properties
		 * such as expressions, labels, HTML, or XML. Also use it to set the
		 * value of a choice using the internal string name of the choice. Use
		 * it to set the value of a dimension when using specified units, such
		 * as "10pt".
		 * 
		 * @param propName
		 * @param value
		 */
		void setStringProperty(final String propName, final String value);

		/**
		 * For apply a style on the {@link ReportItem}.
		 * 
		 * @param styleName
		 *            the style name to apply.
		 */
		void setStyleName(final String styleName);

		/**
		 * For apply a style on the {@link ReportItem}.
		 * 
		 * @param style
		 *            the style to apply.
		 */
		void setStyle(final ReportStyle style);
	}

	/**
	 * An interface for a {@link ReportDataSet}.
	 */
	public interface ReportDataSet {
	}

	/**
	 * An interface for a {@link ReportTableGroup}.
	 */
	public interface ReportTableGroup {

		/**
		 * Add a footer in the table group with style.
		 * 
		 * @param nbColumn
		 *            the number of columns for the footer.
		 * @param footerStyle
		 *            the style to be applied on row
		 * @param rowHeightInPixels
		 *            the row height in pixels, only positive or zero values are
		 *            applied
		 */
		void addFooter(int nbColumn, final ReportStyle footerStyle, final int rowHeightInPixels);

		/**
		 * @return the table group name.
		 */
		String getName();

		/**
		 * Add a report item in the table group footer.
		 * 
		 * @param reportItem
		 *            the report item to add.
		 * @param index
		 *            the index for insert the report item.
		 * @param style
		 *            the style for the cell.
		 */
		void addReportItemInTableGroupFooter(ReportItem reportItem, Integer index, ReportStyle style);
	}

	/**
	 * An interface for a {@link ReportTable}.
	 */
	public interface ReportTable {
		/**
		 * Bind a data set to the {@link ReportTable}
		 * 
		 * @param dataSetName
		 *            the data set name.
		 * @param reportDataSet
		 *            the report data set to bind.
		 */
		void bindDataSet(final String dataSetName, final ReportDataSet reportDataSet);

		/**
		 * Bind a list data set to the {@link ReportTable}
		 * 
		 * @param listDataSetName
		 *            the data set name.
		 */
		void bindListDataSet(String listDataSetName);

		/**
		 * Bind a column to the table (header and detail parts).
		 * 
		 * @param columnIndex
		 *            the column index.
		 * @param headerItem
		 *            a header item.
		 * @param dataItem
		 *            a data item.
		 * @param headerStyle
		 *            a style for the header.
		 * @param dataStyle
		 *            a style for the data (detail part).
		 */
		void bindColumn(final int columnIndex, final ReportItem headerItem, final ReportItem dataItem,
				final String headerStyle, final ReportStyle dataStyle);

		/**
		 * Bind a detail cell to the table.
		 * 
		 * @param detailRowIndex
		 *            the detail row index.
		 * @param columnIndex
		 *            the column index.
		 * @param dataItem
		 *            a data item.
		 * @param dataStyle
		 *            a style for the data.
		 */
		void bindDetailCell(final int detailRowIndex, final int columnIndex, final ReportItem dataItem,
				final ReportStyle dataStyle);

		/**
		 * Bind a header cell to the table.
		 * 
		 * @param columnIndex
		 *            the column index.
		 * @param headerItem
		 *            a header item.
		 * @param headerStyle
		 *            a style for the header.
		 */
		void bindHeaderCell(final int columnIndex, final ReportItem headerItem, final String headerStyle);

		/**
		 * Add the table to the design body.
		 */
		void addToBody();

		/**
		 * Sort a table with a given column.
		 * 
		 * @param columnName
		 *            the name of the column on which apply the sorting.
		 * @param sortingDirection
		 *            the sort direction.
		 */
		void sortWithAGivenColumn(String columnName, boolean isAscending);

		/**
		 * Add a group in the table.
		 * 
		 * @param columnName
		 *            the name of the column on which apply the grouping.
		 * @return the {@link ReportTableGroup} created.
		 */
		ReportTableGroup addAGroup(String columnName);

		/**
		 * Add a sum aggregation to the table (add the aggregation in the table
		 * bindings). It is for general aggregation.
		 * 
		 * @param aggregationName
		 *            the aggregation name.
		 * @param aggregationColumnName
		 *            the name of the column on which apply the aggregation.
		 */
		void addSumAggregation(final String aggregationName, final String aggregationColumnName);

		/**
		 * Add a sum aggregation to the table (add the aggregation in the table
		 * bindings). It is for aggregation on group.
		 * 
		 * @param aggregationName
		 *            the aggregation name.
		 * @param aggregationColumnName
		 *            the name of the column on which apply the aggregation.
		 * @param groupName
		 *            the group name.
		 */
		void addSumAggregationOnGroup(final String aggregationName, final String aggregationColumnName, String groupName);

		/**
		 * Set the column width.
		 * 
		 * @param columnIndex
		 *            the index of the table column to consider.
		 * @param width
		 *            the width to use.
		 */
		void setColumnWidth(final int columnIndex, final Integer width);

		/**
		 * Add a sub table into the table.
		 * 
		 * @param columnIndex
		 *            the column index for insert the sub table.
		 * @param headerItem
		 *            a header item.
		 * @param tableHeaderCellStyle
		 *            a style for the header.
		 * @param tableCellContentStyle
		 *            a style for the cell content.
		 * @param subTable
		 *            the sub table to insert.
		 */
		void addSubTable(int columnIndex, ReportItem headerItem, String tableHeaderCellStyle,
				ReportStyle tableCellContentStyle, ReportTable subTable);

		/**
		 * Add highlight rules on the table.
		 * 
		 * @param styleEven
		 *            a style for the even rows.
		 * @param styleOdd
		 *            a style for the odd rows.
		 */
		void addHighLightRules(final ReportStyle styleEven, final ReportStyle styleOdd);

		/**
		 * Set the footer style
		 * 
		 * @param footerStyle
		 *            the footer style
		 * @param footerIndex
		 *            the footer index to consider.
		 */
		void setFooterStyle(final ReportStyle footerStyle, final Integer footerIndex);

		/**
		 * Add a report item in the table footer.
		 * 
		 * @param reportItem
		 *            the report item to add.
		 * @param index
		 *            the index for insert the report item.
		 * @param style
		 *            the style for the cell.
		 * @param footerIndex
		 *            the footer index to consider.
		 */
		void addReportItemInTableFooter(ReportItem reportItem, Integer index, ReportStyle style, Integer footerIndex);

		/**
		 * Set the header height for the given index
		 * 
		 * @param indexOfHeader
		 *            the index to consider.
		 * @param height
		 *            the height to set.
		 */
		void setHeaderHeight(int indexOfHeader, int height);

		/**
		 * Set the footer height for the given index.
		 * 
		 * @param indexOfFooter
		 *            the index to consider.
		 * @param height
		 *            the height to set.
		 */
		void setFooterHeight(int indexOfFooter, int height);
	}

	/**
	 * An interface for a {@link ReportGrid}.
	 */
	public interface ReportGrid {
		/**
		 * Add a table to a {@link ReportGrid}. The location of this table will
		 * be defined by a row number and a column number.
		 * 
		 * @param table
		 *            the table to add in the grid.
		 * @param nbRows
		 *            the number of rows to consider.
		 * @param nbColumns
		 *            the number of columns to consider.
		 */
		void addTable(final ReportTable table, int nbRows, int nbColumns);

		/**
		 * Add a grid to a {@link ReportGrid}. The location of this grid will be
		 * defined by a row number and a column number.
		 * 
		 * @param grid
		 *            the grid to add in the grid.
		 * @param nbRows
		 *            the number of rows to consider.
		 * @param nbColumns
		 *            the number of columns to consider.
		 */
		void addGrid(final ReportGrid grid, final int nbRows, final int nbColumns);

		/**
		 * Add a report item to a {@link ReportGrid}. The location of this
		 * report item will be defined by a row number and a column number.
		 * 
		 * @param reportItem
		 *            the report item to add in the grid.
		 * @param nbRows
		 *            the number of rows to consider.
		 * @param nbColumns
		 *            the number of columns to consider.
		 */
		void addReportItem(final ReportItem reportItem, int nbRows, int nbColumns);

		/**
		 * Set the first column width.
		 * 
		 * @param firstColumnWidth
		 *            the first column width to set.
		 */
		void setFirstColumnWidth(Integer firstColumnWidth);

		/**
		 * Set the first line height.
		 * 
		 * @param firstLineHeight
		 *            the first line height to set.
		 */
		void setFirstLineHeight(Integer firstLineHeight);

		/**
		 * Avoid page break inside the grid.
		 */
		void avoidPageBreakInside();
	}

	/**
	 * This method will render an excel file according to a given
	 * {@link ReportingTask}
	 * 
	 * @param task
	 *            the reporting task to consider.
	 * @param fileName
	 *            the file name for the excel file generated.
	 */
	void renderExcel(final ReportingTask task, final String fileName);

	/**
	 * This method will render a pdf file according to a given
	 * {@link ReportingTask}
	 * 
	 * @param task
	 *            the reporting task to consider.
	 * @param fileName
	 *            the file name for the pdf file generated.
	 */
	void renderPdf(final ReportingTask task, final String fileName);

	
	/**
	 * Create a {@link DesignContext}.
	 * 
	 * @return the {@link DesignContext} created.
	 */
	DesignContext createDesign();

	/**
	 * Open a design using a given file name.
	 * 
	 * @param filename
	 *            the file name to open.
	 * @return the {@link DesignContext}.
	 */
	DesignContext openDesign(String filename);

	/**
	 * Create a reporting task using a given input stream.
	 * 
	 * @param stream
	 *            the input stream to use for create the {@link ReportingTask}
	 * @return the {@link ReportingTask} created.
	 */
	ReportingTask createTask(InputStream stream);

}
