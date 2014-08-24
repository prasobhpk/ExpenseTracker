package com.pk.et.wm.model;

import com.pk.et.wm.model.Equity;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-08-24T02:12:42")
@StaticMetamodel(MarkToMarket.class)
public class MarkToMarket_ { 

    public static volatile SingularAttribute<MarkToMarket, String> symbol;
    public static volatile SingularAttribute<MarkToMarket, BigDecimal> openPrice;
    public static volatile SingularAttribute<MarkToMarket, Equity> underlying;
    public static volatile SingularAttribute<MarkToMarket, Date> tradeDate;
    public static volatile SingularAttribute<MarkToMarket, BigDecimal> daysLow;
    public static volatile SingularAttribute<MarkToMarket, BigDecimal> previousClose;
    public static volatile SingularAttribute<MarkToMarket, BigDecimal> totalTradedValue;
    public static volatile SingularAttribute<MarkToMarket, String> series;
    public static volatile SingularAttribute<MarkToMarket, BigDecimal> totalTradedQty;
    public static volatile SingularAttribute<MarkToMarket, BigDecimal> daysHigh;
    public static volatile SingularAttribute<MarkToMarket, Long> id;
    public static volatile SingularAttribute<MarkToMarket, BigDecimal> closePrice;
    public static volatile SingularAttribute<MarkToMarket, BigDecimal> lastTradedPrice;

}