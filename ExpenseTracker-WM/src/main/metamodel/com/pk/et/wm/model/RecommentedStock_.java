package com.pk.et.wm.model;

import com.pk.et.infra.model.User;
import com.pk.et.wm.model.Equity;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-08-24T02:12:42")
@StaticMetamodel(RecommentedStock.class)
public class RecommentedStock_ { 

    public static volatile SingularAttribute<RecommentedStock, BigDecimal> buyPrice;
    public static volatile SingularAttribute<RecommentedStock, BigDecimal> targetPrice;
    public static volatile SingularAttribute<RecommentedStock, BigDecimal> longtermBuyPrice;
    public static volatile SingularAttribute<RecommentedStock, String> description;
    public static volatile SingularAttribute<RecommentedStock, Long> id;
    public static volatile SingularAttribute<RecommentedStock, Equity> stock;
    public static volatile SingularAttribute<RecommentedStock, User> user;

}