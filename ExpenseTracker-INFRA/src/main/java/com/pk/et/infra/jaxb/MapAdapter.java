package com.pk.et.infra.jaxb;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import com.pk.et.infra.model.ETMapEntryType;
import com.pk.et.infra.model.ETMapType;

public final class MapAdapter extends

XmlAdapter<ETMapType, Map<String, Object>> {

	@Override
	public ETMapType marshal(Map<String, Object> arg0) throws Exception {
		ETMapType myMapType = new ETMapType();
		for (Entry<String, Object> entry : arg0.entrySet()) {
			ETMapEntryType myMapEntryType = new ETMapEntryType(entry.getKey(),
					entry.getValue());
			myMapType.getEntry().add(myMapEntryType);
		}
		return myMapType;
	}

	@Override
	public Map<String, Object> unmarshal(ETMapType arg0) throws Exception {
		Map<String, Object> hashMap = new HashMap<String, Object>();
		for (ETMapEntryType myEntryType : arg0.getEntry()) {
			hashMap.put(myEntryType.getKey(), myEntryType.getValue());
		}
		return hashMap;
	}

}
