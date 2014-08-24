package com.pk.et.infra.web.taglibs;
/**
 * 
 * @author Prashob PK
 *
 */
public class WMFormatRate  extends WMFormatNumber{
	private static final long serialVersionUID = 1L;
	public WMFormatRate(){
		super();
		this.formatType=FormatType.RATE;
	}

}
