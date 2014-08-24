package com.pk.et.infra.web.taglibs;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.pk.et.infra.model.WMParams;
/**
 * 
 * @author Prashob PK
 *
 */
public abstract class WMFormatNumberSupport extends BodyTagSupport {

	private static final long serialVersionUID = 1L;
	
	// Protected state
	protected enum FormatType {
	    PRICE, AMOUNT, RATE, UNIT ,DEFAULT 
	}
	protected FormatType formatType;
	protected Object 		value; // 'value' attribute
	protected int 			fractionDigits; // 'fraction' attribute
	
	protected boolean 		valueSpecified; // status
	protected boolean 		isGroupingUsed; // 'groupingUsed' attribute
	protected boolean 		groupingUsedSpecified;
	protected boolean 		fractionDigitsSpecified;
	protected boolean 		isTrimUsed; // 'trimUsed' attribute

	// Private state
	private static final Map<String, Integer> scopeTable=new HashMap<String, Integer>();
	private String 			var; // 'var' attribute
	private int 			scope; // 'scope' attribute
	private boolean			onlyRound;//'onlyRound' attribute
	static{
		scopeTable.put("page", PageContext.PAGE_SCOPE);
		scopeTable.put("request", PageContext.REQUEST_SCOPE);
		scopeTable.put("session", PageContext.SESSION_SCOPE);
		scopeTable.put("application", PageContext.APPLICATION_SCOPE);
	}

	public WMFormatNumberSupport() {
		super();
		init();
	}

	private void init() {
		value = null;
		valueSpecified = false;
		var = null;
		groupingUsedSpecified = false;
		isTrimUsed=false;
		onlyRound=false;
		scope = PageContext.PAGE_SCOPE;
		formatType=FormatType.DEFAULT;
	}
	
    // Tag attributes
	
	// 'var' attribute
	public void setVar(String var) throws JspTagException{
		this.var = var;
	}
	
	// 'scope' attribute
	public void setScope(String scope) throws JspTagException{
		Integer val=scopeTable.get(scope);
		if(val==null){
			val=1;
		}
        this.scope = val;
	}
	// 'onlyRound' attribute
	public void setOnlyRound(boolean onlyRound) {
		this.onlyRound = onlyRound;
	}


	public int doEndTag() throws JspException {
		String formatted = null;
		Object input = null;

		// determine the input by...
		if (valueSpecified) {
			// ... reading 'value' attribute
			input = value;
		} else {
			// ... retrieving and trimming our body
			if (bodyContent != null && bodyContent.getString() != null)
				input = bodyContent.getString().trim();
		}

		if ((input == null) || input.equals("")) {
			// If value is null or empty, remove the scoped variable
			if (var != null) {
				pageContext.removeAttribute(var, scope);
			}
			return EVAL_PAGE;
		}

		/*
		 * If 'value' is a String, it is first parsed into an instance of
		 * BigDecimal
		 */
		if (input instanceof String) {
			try {
				BigDecimal val=new BigDecimal((String)input);
				/*
				 * If isTrimUsed is true, remove the trailing zeroes
				 */
				if(isTrimUsed){
					val=val.stripTrailingZeros();
				}
				input=val;
			}catch (Exception e) {
				throw new JspException("Number Format Exception :"+input, e);
			}
		}
		// Determine formatting locale
		//Locale loc = (Locale) pageContext.getSession().getAttribute("Locale");
		Locale loc = (Locale) pageContext.getRequest().getLocale();
		if(onlyRound){
			groupingUsedSpecified=true;
			isGroupingUsed=false;
			loc=new Locale("en", "US");
		}
		if (loc != null) {
			// Create formatter
			NumberFormat formatter = null;
			formatter = NumberFormat.getNumberInstance(loc);
			configureFormatter(formatter);
			formatted = formatter.format(input);
			
		} else {
			// no formatting locale available, use toString()
			formatted = input.toString();
		}
		if(isTrimUsed){
			String regex = "0*$";
			String replacement = "";
			formatted = formatted.replaceAll(regex, replacement);
			if(formatted.endsWith(".")){
				formatted=formatted.replace(".", "");
			}
		}

		if (var != null) {
			//setting the formatted value in specified(or default) scope
			pageContext.setAttribute(var, formatted, scope);
		} else {
			try {
				pageContext.getOut().print(formatted);
			} catch (IOException ioe) {
				throw new JspTagException(ioe.toString(), ioe);
			}
		}

		return EVAL_PAGE;
	}

	// Releases any resources we may have (or inherit)
	public void release() {
		init();
	}

	

	/*
	 * Applies the 'groupingUsed', 'fractionDigits' attributes to the given
	 * formatter.
	 */
	private void configureFormatter(NumberFormat formatter) {
		int fraction=0;
		boolean fractionReq=true;
		if (groupingUsedSpecified)
			formatter.setGroupingUsed(isGroupingUsed);
		if (fractionDigitsSpecified){
			fraction=fractionDigits;
		}else{
			WMParams params=(WMParams)pageContext.getServletContext().getAttribute("wmParams");
			if(params==null){
				params=new WMParams();
			}
			switch (formatType) {
					case AMOUNT:
						fraction=params.getAmountDec().intValue();
						break;
					case PRICE:
						fraction=params.getPriceDec().intValue();
						break;
						
					case UNIT:
						fraction=params.getUnitDec().intValue();
						break;
						
					case RATE:
						fraction=params.getCcyRateDec().intValue();
						break;
						/*
						 * When fmtNumber tag is used without the attribute 
						 * 'fractionDigits',no need to set the fraction
						 */
					case DEFAULT:
						fractionReq=false;
						break;
			}
		}
		if(fractionReq){
			setFraction(formatter, fraction);
		}
	}
	
	private void setFraction(NumberFormat formatter,int fraction){
		formatter.setMaximumFractionDigits(fraction);
		formatter.setMinimumFractionDigits(fraction);
	}
	
	
}
