package com.pk.et.wm.model;

import com.pk.et.infra.model.User;
import com.pk.et.wm.model.Equity;
import com.pk.et.wm.model.Holding;
import com.pk.et.wm.model.Portfolio;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-08-24T02:12:42")
@StaticMetamodel(UserWealthContext.class)
public class UserWealthContext_ { 

    public static volatile SetAttribute<UserWealthContext, Equity> favStocks;
    public static volatile ListAttribute<UserWealthContext, Portfolio> portfolios;
    public static volatile ListAttribute<UserWealthContext, Holding> holdings;
    public static volatile SingularAttribute<UserWealthContext, Long> id;
    public static volatile SingularAttribute<UserWealthContext, User> user;

}