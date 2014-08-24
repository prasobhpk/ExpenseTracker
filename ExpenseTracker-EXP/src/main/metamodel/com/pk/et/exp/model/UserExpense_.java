package com.pk.et.exp.model;

import com.pk.et.exp.model.Expense;
import com.pk.et.exp.model.ExpenseType;
import com.pk.et.exp.model.Forecast;
import com.pk.et.infra.model.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-08-24T02:12:13")
@StaticMetamodel(UserExpense.class)
public class UserExpense_ { 

    public static volatile ListAttribute<UserExpense, ExpenseType> expenseTypes;
    public static volatile SingularAttribute<UserExpense, Long> id;
    public static volatile SingularAttribute<UserExpense, User> user;
    public static volatile SetAttribute<UserExpense, Expense> expenses;
    public static volatile ListAttribute<UserExpense, Forecast> forecasts;

}