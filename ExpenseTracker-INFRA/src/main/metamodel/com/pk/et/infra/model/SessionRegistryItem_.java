package com.pk.et.infra.model;

import com.pk.et.infra.model.User;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-08-24T02:11:54")
@StaticMetamodel(SessionRegistryItem.class)
public class SessionRegistryItem_ extends BaseEntity_ {

    public static volatile SingularAttribute<SessionRegistryItem, User> principal;
    public static volatile SingularAttribute<SessionRegistryItem, Boolean> expired;
    public static volatile SingularAttribute<SessionRegistryItem, Date> lastRequest;
    public static volatile SingularAttribute<SessionRegistryItem, Long> id;
    public static volatile SingularAttribute<SessionRegistryItem, String> sessionId;

}