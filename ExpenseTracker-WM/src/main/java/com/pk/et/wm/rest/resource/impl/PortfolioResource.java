package com.pk.et.wm.rest.resource.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.ext.MessageContext;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.pk.et.infra.model.User;
import com.pk.et.infra.util.ETConstants;
import com.pk.et.infra.util.Message;
import com.pk.et.wm.model.Holding;
import com.pk.et.wm.rest.resource.IPortfolioResource;
import com.pk.et.wm.service.IBrokerageStructureService;
import com.pk.et.wm.service.IHoldingService;
import com.pk.et.wm.util.ReddiffMoneyUtil;

@Service("portfolioResource")
public class PortfolioResource implements IPortfolioResource {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	@Qualifier("brokerageStructureService")
	private IBrokerageStructureService brokerageStructureService;

	@Autowired(required = true)
	@Qualifier("reddiffMoneyUtil")
	private ReddiffMoneyUtil reddiffMoneyUtil;


	@Autowired(required = true)
	@Qualifier("holdingService")
	private IHoldingService holdingService;

	@Context
	private MessageContext context;

	public Response getFolioHoldingsDataForDB(Long folioId) {
		logger.debug("Get the holding details for portfolio ==>{}",folioId);
		HttpSession session=context.getHttpServletRequest().getSession();
		User user=(User)session.getAttribute("SESSION_USER");
		@SuppressWarnings("unchecked")
		Map<String,String> conf=(Map<String,String>)session.getAttribute(ETConstants.USER_CONFIG_KEY);
		boolean iswithFee=true;
		if(conf!=null){
			String confValue=conf.get("SHOW_TOTAL_WITH_FEE_DETAILS");
			if(!Boolean.parseBoolean(confValue)){
				iswithFee=false;
			}
		}
		StringBuilder sb=new StringBuilder("<data>");
		sb.append("<variable name=\"txns\">");
		List<Holding> holdings=holdingService.getHoldingsByfolio(user, folioId);
		for(Holding holding:holdings){
			if(holding.getQuantity().compareTo(BigDecimal.ZERO)>0){
				sb.append("<row>");
				sb.append("<column>"+holding.getInstrument().getName()+"</column>");
				sb.append("<column>"+(iswithFee?holding.getTotalAmount():holding.getAmount())+"</column>");
				sb.append("</row>");
			}
		}
		sb.append("</variable>");
		sb.append("</data>");
		return Response.ok(sb.toString()).build();
	}

	public Response getFolioPerformanceForDB(Long folioId) {
		HttpSession session=context.getHttpServletRequest().getSession();
		User user=(User)session.getAttribute("SESSION_USER");
		@SuppressWarnings("unchecked")
		Map<String,String> conf=(Map<String,String>)session.getAttribute(ETConstants.USER_CONFIG_KEY);
		StringBuilder sb=new StringBuilder("<data>");
		sb.append("<variable name=\"profit\">");
		List<Holding> holdings=holdingService.getHoldingsByfolio(user, folioId);
		StringBuilder stockList=new StringBuilder("");
		for(Holding holding:holdings){
			stockList.append(holding.getInstrument().getSymbol());
			stockList.append("|");
		}
		if(stockList.length()>0){
			stockList.deleteCharAt(stockList.length()-1);
		}
		Map<String,? extends Object> response=reddiffMoneyUtil.getLiveStocksStatus(stockList.toString());
		Map<String, BigDecimal> priceMap=null;
		Message msg=(Message)response.get("message");
		if(msg.getStatus().equals(ETConstants.SUCCESS)){
			String jsonStockDetails=response.get("stockData").toString();
				// general method, same as with data binding
			   ObjectMapper mapper = new ObjectMapper();
			   // (note: can also use more specific type, like ArrayNode or ObjectNode!)
		   try {
				JsonNode rootNode = mapper.readValue(jsonStockDetails, JsonNode.class);
				priceMap=new HashMap<String, BigDecimal>();
				for(JsonNode jsonStock:rootNode.get(1)){
					priceMap.put(jsonStock.path("CompanyCode").getTextValue(), BigDecimal.valueOf(Double.parseDouble(jsonStock.path("LastTradedPrice").getTextValue())));
				}
				for(Holding holding:holdings){
					if(holding.getQuantity().compareTo(BigDecimal.ZERO)>0){
						BigDecimal mktValue=holding.getQuantity().multiply(priceMap.get(holding.getInstrument().getSymbol()));
						BigDecimal profit=null;
						if(conf!=null && Boolean.parseBoolean(conf.get("SHOW_PROFIT_AFTER_BROKERAGE_PERFORMANCE"))){
							profit=mktValue.subtract(holding.getAmount().add(holding.getFee().add(brokerageStructureService.calculateBrokerage(mktValue, holding.getBrokerageStructure()))));
						}else{
							profit=mktValue.subtract(holding.getAmount());
						}
						profit=profit.setScale(2, RoundingMode.HALF_UP).stripTrailingZeros();
						sb.append("<row>");
						sb.append("<column>"+holding.getInstrument().getName()+"</column>");
						sb.append("<column>"+profit+"</column>");
						sb.append("</row>");
					}
				}
				
			} catch (Exception e) {
				logger.debug("Error {} :: {} ",new Object[]{e.getClass(),e.getMessage()});
				e.printStackTrace();
			} 
		}else{
			sb.append("<row>");
			sb.append("<column></column>");
			sb.append("<column></column>");
			sb.append("</row>");
		}
		sb.append("</variable>");
		sb.append("</data>");
		return Response.ok(sb.toString()).build();
	}
	
	public Holding getHolding(Long folioId,Long equityId) {
		logger.debug("Get the holding for portfolio ==>{} and equity ==>{}",new Object[]{folioId,equityId});
		HttpSession session=context.getHttpServletRequest().getSession();
		User user=(User)session.getAttribute("SESSION_USER");
		Holding holding=null;
		try {
			holding=holdingService.getHoldingByfolio(user, folioId, equityId);
		} catch (Exception e) {
			System.out.println(e);
		}
		return holding;
	}

}
