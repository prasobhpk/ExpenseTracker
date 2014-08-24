package com.pk.et.infra.web.taglibs;


/**
 * 
 * @author Prashob PK
 *
 */
public class WMFormatAmount extends WMFormatNumber{
	private static final long serialVersionUID = 1L;
	public WMFormatAmount(){
		super();
		this.formatType=FormatType.AMOUNT;
	}

}
