package com.pk.et.wm.jaxb.adapters;

import java.math.BigDecimal;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class PriceAdapter extends XmlAdapter<String, BigDecimal> {

	@Override
	public String marshal(final BigDecimal value) throws Exception {
		return value.toString();
	}

	@Override
	public BigDecimal unmarshal(final String valStr) throws Exception {
		return BigDecimal
				.valueOf(Double.parseDouble(valStr.replaceAll(",", "")));
	}

}
