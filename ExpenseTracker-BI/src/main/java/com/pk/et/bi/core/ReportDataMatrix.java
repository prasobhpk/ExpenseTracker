package com.pk.et.bi.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Report matrix of data
 */
public class ReportDataMatrix implements Iterable<ReportDataMatrixRow> {

	private final List<ReportDataMatrixRow> rows = new ArrayList<ReportDataMatrixRow>();

	public Iterator<ReportDataMatrixRow> iterator() {
		final Iterator<ReportDataMatrixRow> iterator = this.rows.iterator();
		return iterator;
	}

	public void addRow(final ReportDataMatrixRow row) {
		this.rows.add(row);
	}

	public List<ReportDataMatrixRow> getRows() {
		return this.rows;
	}

	/**
	 * Sorts the values of ReportDataMatrixRow records using ordering column
	 * name and specified order.
	 * 
	 * @param orderingColumnName
	 * @param ascendingOrder
	 */
	public void sortRecords(final String orderingColumnName,
			final String secondOrderingColumnName,
			final String thridOrderingColumnName, final boolean ascendingOrder) {
		final ReportDataMatrixRowComparator reportDataMatrixRowComparator = new ReportDataMatrixRowComparator();
		reportDataMatrixRowComparator.setAscOrder(ascendingOrder);
		reportDataMatrixRowComparator.setColumnToSort(orderingColumnName);
		reportDataMatrixRowComparator
				.setSecondColumnToSort(secondOrderingColumnName);
		reportDataMatrixRowComparator
				.setThirdColumnToSort(thridOrderingColumnName);
		Collections.sort(this.rows, reportDataMatrixRowComparator);
	}
}
