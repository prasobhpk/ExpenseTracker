package com.pk.et.wm.model;

import com.pk.et.wm.model.BrokerageStructure;
import com.pk.et.wm.model.Equity;
import com.pk.et.wm.model.Portfolio;
import com.pk.et.wm.model.TransactionType;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-08-24T02:12:42")
@StaticMetamodel(Transaction.class)
public class Transaction_ { 

    public static volatile SingularAttribute<Transaction, BigDecimal> brokerage;
    public static volatile SingularAttribute<Transaction, BigDecimal> amount;
    public static volatile SingularAttribute<Transaction, BigDecimal> quantity;
    public static volatile SingularAttribute<Transaction, Boolean> traded;
    public static volatile SingularAttribute<Transaction, BigDecimal> otherCharges;
    public static volatile SingularAttribute<Transaction, BrokerageStructure> brokerageStructure;
    public static volatile SingularAttribute<Transaction, Equity> instrument;
    public static volatile SingularAttribute<Transaction, Date> tradeDate;
    public static volatile SingularAttribute<Transaction, TransactionType> type;
    public static volatile SingularAttribute<Transaction, BigDecimal> totalAmount;
    public static volatile SingularAttribute<Transaction, Portfolio> portfolio;
    public static volatile SingularAttribute<Transaction, BigDecimal> price;
    public static volatile SingularAttribute<Transaction, String> exchange;
    public static volatile SingularAttribute<Transaction, Long> id;

}