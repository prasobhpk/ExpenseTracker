package com.pk.et.infra.model;

import java.util.ArrayList;
import java.util.List;

public class ETMapType {
	private List<ETMapEntryType> entry = new ArrayList<ETMapEntryType>();

	public List<ETMapEntryType> getEntry() {
		return entry;
	}

	public void setEntry(List<ETMapEntryType> entry) {
		this.entry = entry;
	}
	
}
