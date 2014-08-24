package com.pk.et.exp.model;

import com.pk.et.exp.model.UserExpense;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-08-24T02:12:13")
@StaticMetamodel(ExpenseType.class)
public class ExpenseType_ { 

    public static volatile SingularAttribute<ExpenseType, Boolean> showInDashboard;
    public static volatile SingularAttribute<ExpenseType, String> description;
    public static volatile SingularAttribute<ExpenseType, Long> id;
    public static volatile SingularAttribute<ExpenseType, UserExpense> userExpense;
    public static volatile SingularAttribute<ExpenseType, String> type;

}