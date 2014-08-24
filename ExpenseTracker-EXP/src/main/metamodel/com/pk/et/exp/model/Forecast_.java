package com.pk.et.exp.model;

import com.pk.et.exp.model.ForecastType;
import com.pk.et.exp.model.UserExpense;
import com.pk.et.infra.model.Period;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-08-24T02:12:13")
@StaticMetamodel(Forecast.class)
public class Forecast_ { 

    public static volatile SingularAttribute<Forecast, Date> date;
    public static volatile SingularAttribute<Forecast, BigDecimal> forecastAmount;
    public static volatile SingularAttribute<Forecast, Period> period;
    public static volatile SingularAttribute<Forecast, Boolean> periodic;
    public static volatile SingularAttribute<Forecast, String> description;
    public static volatile SingularAttribute<Forecast, Long> id;
    public static volatile SingularAttribute<Forecast, UserExpense> userExpense;
    public static volatile SingularAttribute<Forecast, ForecastType> forecastType;
    public static volatile SingularAttribute<Forecast, String> title;

}