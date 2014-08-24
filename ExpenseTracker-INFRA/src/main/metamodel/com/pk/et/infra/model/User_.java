package com.pk.et.infra.model;

import com.pk.et.infra.model.Name;
import com.pk.et.infra.model.UserAuthority;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-08-24T02:11:54")
@StaticMetamodel(User.class)
public class User_ extends BaseEntity_ {

    public static volatile SingularAttribute<User, String> password;
    public static volatile SingularAttribute<User, String> salt;
    public static volatile SingularAttribute<User, Date> createdDate;
    public static volatile ListAttribute<User, UserAuthority> roles;
    public static volatile SingularAttribute<User, Boolean> credentialsNonExpired;
    public static volatile SingularAttribute<User, Name> name;
    public static volatile SingularAttribute<User, Boolean> accountNonExpired;
    public static volatile SingularAttribute<User, Long> id;
    public static volatile SingularAttribute<User, Boolean> enabled;
    public static volatile SingularAttribute<User, String> username;
    public static volatile SingularAttribute<User, Boolean> accountNonLocked;

}