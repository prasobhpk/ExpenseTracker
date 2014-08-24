package com.pk.et.wm.model;

import com.pk.et.wm.model.Exchanges;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-08-24T02:12:42")
@StaticMetamodel(Equity.class)
public class Equity_ extends Instrument_ {

    public static volatile SingularAttribute<Equity, Boolean> mtmCandidate;
    public static volatile SingularAttribute<Equity, String> nseCode;
    public static volatile SingularAttribute<Equity, Exchanges> exchange;

}