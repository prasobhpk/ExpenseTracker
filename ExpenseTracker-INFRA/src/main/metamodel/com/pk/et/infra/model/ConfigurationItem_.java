package com.pk.et.infra.model;

import com.pk.et.infra.model.ConfigType;
import com.pk.et.infra.model.ValueType;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-08-24T02:11:54")
@StaticMetamodel(ConfigurationItem.class)
public class ConfigurationItem_ extends BaseEntity_ {

    public static volatile SingularAttribute<ConfigurationItem, Boolean> keyValuePair;
    public static volatile SingularAttribute<ConfigurationItem, String> configKey;
    public static volatile SingularAttribute<ConfigurationItem, String> defaultValue;
    public static volatile SingularAttribute<ConfigurationItem, ValueType> valueType;
    public static volatile SingularAttribute<ConfigurationItem, Long> id;
    public static volatile SingularAttribute<ConfigurationItem, ConfigType> configType;

}