package com.pk.et.infra.util;

import java.util.ArrayList;
import java.util.List;

public class SearchFilter {
	private String groupOp;

	private List<SearchRule> rules = new ArrayList<SearchRule>();

	public String getGroupOp() {
		return groupOp;
	}

	public void setGroupOp(String groupOp) {
		this.groupOp = groupOp;
	}

	public List<SearchRule> getRules() {
		return rules;
	}

	public void setRules(List<SearchRule> rules) {
		this.rules = rules;
	}

}
