package com.pk.et.infra.web.taglibs;
/**
 * 
 * @author Prashob PK
 *
 */
public class WMFormatPrice  extends WMFormatNumber{
	private static final long serialVersionUID = 1L;
	public WMFormatPrice(){
		super();
		this.formatType=FormatType.PRICE;
	}

}
