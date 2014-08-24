package com.pk.et.wm.batch;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.pk.et.wm.model.MarkToMarket;

public class MarkToMarketFieldSetMapper implements FieldSetMapper<MarkToMarket> {

	public MarkToMarket mapFieldSet(final FieldSet fieldSet)
			throws BindException {
		final MarkToMarket mtm = new MarkToMarket();
		mtm.setSymbol(fieldSet.readString("SYMBOL"));
		mtm.setSeries(fieldSet.readString("SERIES"));
		mtm.setOpenPrice(fieldSet.readBigDecimal("OPEN"));
		mtm.setDaysHigh(fieldSet.readBigDecimal("HIGH"));
		mtm.setDaysLow(fieldSet.readBigDecimal("LOW"));
		mtm.setClosePrice(fieldSet.readBigDecimal("CLOSE"));
		mtm.setLastTradedPrice(fieldSet.readBigDecimal("LAST"));
		mtm.setPreviousClose(fieldSet.readBigDecimal("PREVCLOSE"));
		mtm.setTotalTradedQty(fieldSet.readBigDecimal("TOTTRDQTY"));
		mtm.setTotalTradedValue(fieldSet.readBigDecimal("TOTTRDVAL"));
		mtm.setTradeDate(fieldSet.readDate("TIMESTAMP", "dd-MMM-yyyy"));
		return mtm;
	}
}
