package com.pk.et.infra.util;

import java.io.Serializable;
import java.util.List;

public class JQResponse<T> implements Serializable{
	private static final long serialVersionUID = ETConstants.etVersion;
	
	private long currentPage;
	private long totalRecords;
	private long totalPages;
	private List<T> rows;

	public JQResponse(){
		super();
	}
	public JQResponse(long currentPage, long totalRecords, long totalPages,
			List<T> rows) {
		super();
		this.currentPage = currentPage;
		this.totalRecords = totalRecords;
		this.totalPages = totalPages;
		this.rows = rows;
	}

	public long getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(long currentPage) {
		this.currentPage = currentPage;
	}

	public long getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(long totalRecords) {
		this.totalRecords = totalRecords;
	}

	public long getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(long totalPages) {
		this.totalPages = totalPages;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

}
