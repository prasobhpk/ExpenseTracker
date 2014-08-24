package com.pk.et.infra.model;

import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-08-24T02:11:54")
@StaticMetamodel(AuditedObject.class)
public abstract class AuditedObject_ extends BaseEntity_ {

    public static volatile SingularAttribute<AuditedObject, Calendar> auditTimestamp;
    public static volatile SingularAttribute<AuditedObject, String> auditUser;

}