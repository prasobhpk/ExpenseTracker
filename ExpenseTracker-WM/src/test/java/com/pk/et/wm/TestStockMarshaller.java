package com.pk.et.wm;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pk.et.wm.model.MarkToMarket;
import com.pk.et.wm.util.ReddiffMoneyUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/spring/ExpenseTracker-WM-context-test.xml" })
public class TestStockMarshaller {

	@Autowired(required = true)
	@Qualifier("reddiffMoneyUtil")
	private ReddiffMoneyUtil reddiffMoneyUtil;

	@Test
	public void shouldMarshallStocks() {
		final List<MarkToMarket> response = this.reddiffMoneyUtil
				.getMtms("11620084|16610041");
		assertEquals(2, response.size());
	}
}
