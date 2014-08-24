package com.pk.et.infra.model;

import com.pk.et.infra.model.JobStatus;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.joda.time.DateTime;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-08-24T02:11:54")
@StaticMetamodel(JobUniqueInstance.class)
public class JobUniqueInstance_ extends BaseEntity_ {

    public static volatile SingularAttribute<JobUniqueInstance, String> owner;
    public static volatile SingularAttribute<JobUniqueInstance, DateTime> endDate;
    public static volatile SingularAttribute<JobUniqueInstance, String> name;
    public static volatile SingularAttribute<JobUniqueInstance, Long> id;
    public static volatile SingularAttribute<JobUniqueInstance, DateTime> startDate;
    public static volatile SingularAttribute<JobUniqueInstance, JobStatus> status;

}