package com.pk.et.wm.model;

import com.pk.et.wm.model.BrokerageStructure;
import com.pk.et.wm.model.Equity;
import com.pk.et.wm.model.Portfolio;
import com.pk.et.wm.model.UserWealthContext;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-08-24T02:12:43")
@StaticMetamodel(Holding.class)
public class Holding_ { 

    public static volatile SingularAttribute<Holding, BigDecimal> brokerage;
    public static volatile SingularAttribute<Holding, BigDecimal> totalAmount;
    public static volatile SingularAttribute<Holding, BigDecimal> amount;
    public static volatile SingularAttribute<Holding, BigDecimal> quantity;
    public static volatile SingularAttribute<Holding, Portfolio> portfolio;
    public static volatile SingularAttribute<Holding, BigDecimal> price;
    public static volatile SingularAttribute<Holding, BrokerageStructure> brokerageStructure;
    public static volatile SingularAttribute<Holding, Equity> instrument;
    public static volatile SingularAttribute<Holding, Long> id;
    public static volatile SingularAttribute<Holding, UserWealthContext> wealthContext;
    public static volatile SingularAttribute<Holding, BigDecimal> profit;

}