package com.pk.et.infra.model;

import com.pk.et.infra.model.ConfigurationItem;
import com.pk.et.infra.model.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-08-24T02:11:54")
@StaticMetamodel(Configuration.class)
public class Configuration_ extends BaseEntity_ {

    public static volatile SingularAttribute<Configuration, String> configItemValue;
    public static volatile SingularAttribute<Configuration, Long> id;
    public static volatile SingularAttribute<Configuration, User> user;
    public static volatile SingularAttribute<Configuration, ConfigurationItem> configItem;

}