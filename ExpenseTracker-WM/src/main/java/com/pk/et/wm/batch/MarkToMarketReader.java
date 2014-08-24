package com.pk.et.wm.batch;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import com.pk.et.wm.model.MarkToMarket;
import com.pk.et.wm.util.ReddiffMoneyUtil;

public class MarkToMarketReader implements ItemStreamReader<MarkToMarket> {

	@Autowired(required = true)
	@Qualifier("reddiffMoneyUtil")
	private ReddiffMoneyUtil reddiffMoneyUtil;

	// private List<Equity> stocks;
	@Value("#{wmConfig['mtm.candidates']}")
	private String mtmCandidates;

	private Queue<MarkToMarket> mtmQueue = null;

	public MarkToMarket read() throws Exception, UnexpectedInputException,
			ParseException, NonTransientResourceException {
		// TODO Auto-generated method stub
		return null;
	}

	public void open(final ExecutionContext executionContext)
			throws ItemStreamException {
		// Fetch stocks from db those need mtms

		final List<MarkToMarket> mtms = this.reddiffMoneyUtil
				.getMtms(this.mtmCandidates);
		if ((mtms != null) && !mtms.isEmpty()) {
			this.mtmQueue = new LinkedList<MarkToMarket>(mtms);
		} else {
			this.mtmQueue = new LinkedList<MarkToMarket>();
		}

	}

	public void update(final ExecutionContext executionContext)
			throws ItemStreamException {
		// TODO Auto-generated method stub

	}

	public void close() throws ItemStreamException {
		// TODO Auto-generated method stub

	}

}
