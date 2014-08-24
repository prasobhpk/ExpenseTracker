package com.pk.et.wm;

import com.pk.et.wm.util.ReddiffMoneyUtil;

public class ReddiffMoneyUtilTest {
	ReddiffMoneyUtil util = new ReddiffMoneyUtil();

	// @Test
	public void testNseCodeMap() throws Exception {
		System.out.println(this.util.getNSECodeMap());
	}
}
