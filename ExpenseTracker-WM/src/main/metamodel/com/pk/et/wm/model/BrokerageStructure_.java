package com.pk.et.wm.model;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-08-24T02:12:43")
@StaticMetamodel(BrokerageStructure.class)
public class BrokerageStructure_ { 

    public static volatile SingularAttribute<BrokerageStructure, BigDecimal> brokerage;
    public static volatile SingularAttribute<BrokerageStructure, BigDecimal> transactionTax;
    public static volatile SingularAttribute<BrokerageStructure, String> institution;
    public static volatile SingularAttribute<BrokerageStructure, BigDecimal> otherCharges;
    public static volatile SingularAttribute<BrokerageStructure, Long> id;
    public static volatile SingularAttribute<BrokerageStructure, BigDecimal> minBrokerage;
    public static volatile SingularAttribute<BrokerageStructure, BigDecimal> serivceTax;

}