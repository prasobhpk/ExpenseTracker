package com.pk.et.wm.model;

import com.pk.et.wm.model.Transaction;
import com.pk.et.wm.model.UserWealthContext;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-08-24T02:12:42")
@StaticMetamodel(Portfolio.class)
public class Portfolio_ { 

    public static volatile SingularAttribute<Portfolio, Long> id;
    public static volatile SingularAttribute<Portfolio, UserWealthContext> wealthContext;
    public static volatile ListAttribute<Portfolio, Transaction> transactions;
    public static volatile SingularAttribute<Portfolio, String> portfolioName;

}