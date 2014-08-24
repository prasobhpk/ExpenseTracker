package com.pk.et.infra.util;


public class SearchCriteria {
	private Integer page;
	private Integer rp;
	private String sortname;
	private String sortorder;
	private String query;
	private String qtype;

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRp() {
		return rp;
	}

	public void setRp(Integer rp) {
		this.rp = rp;
	}

	public String getSortname() {
		return sortname;
	}

	public void setSortname(String sortname) {
		this.sortname = sortname;
	}

	public String getSortorder() {
		return sortorder;
	}

	public void setSortorder(String sortorder) {
		this.sortorder = sortorder;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getQtype() {
		return qtype;
	}

	public void setQtype(String qtype) {
		this.qtype = qtype;
	}

}
