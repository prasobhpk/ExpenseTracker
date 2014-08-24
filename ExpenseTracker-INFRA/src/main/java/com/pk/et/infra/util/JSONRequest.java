package com.pk.et.infra.util;

public class JSONRequest {
	private boolean search;
	private int page;
	private int rows;
	private String sortField;
	private String sortOrder;
	private SearchFilter searchFilter;

	public boolean isSearch() {
		return search;
	}

	public void setSearch(boolean search) {
		this.search = search;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public SearchFilter getSearchFilter() {
		return searchFilter;
	}

	public void setSearchFilter(SearchFilter searchFilter) {
		this.searchFilter = searchFilter;
	}

}
