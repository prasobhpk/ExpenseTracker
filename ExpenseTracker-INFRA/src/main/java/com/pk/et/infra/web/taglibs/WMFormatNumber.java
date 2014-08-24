package com.pk.et.infra.web.taglibs;

import javax.servlet.jsp.JspTagException;
/**
 * 
 * @author Prashob PK
 *
 */
public class WMFormatNumber extends WMFormatNumberSupport {
	private static final long serialVersionUID = 1L;
	
	public WMFormatNumber(){
		super();
		this.formatType=FormatType.DEFAULT;
	}

	// 'value' attribute
	public void setValue(Object value) throws JspTagException {
		this.value = value;
		this.valueSpecified = true;
	}

	// 'groupingUsed' attribute
	public void setGroupingUsed(boolean isGroupingUsed) throws JspTagException {
		this.isGroupingUsed = isGroupingUsed;
		this.groupingUsedSpecified = true;
	}

	// 'fractionDigits' attribute
	public void setFractionDigits(int fractionDigits) throws JspTagException {
		this.fractionDigits = fractionDigits;
		this.fractionDigitsSpecified = true;
	}
	
	// 'trimUsed' attribute
	public void setTrimUsed(boolean isTrimUsed) throws JspTagException {
		this.isTrimUsed = isTrimUsed;
	}

}
