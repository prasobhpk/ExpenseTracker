package com.pk.et.exp.model;

import com.pk.et.exp.model.ExpenseType;
import com.pk.et.exp.model.UserExpense;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-08-24T02:12:13")
@StaticMetamodel(Expense.class)
public class Expense_ { 

    public static volatile SingularAttribute<Expense, ExpenseType> expenseType;
    public static volatile SingularAttribute<Expense, Boolean> active;
    public static volatile SingularAttribute<Expense, String> description;
    public static volatile SingularAttribute<Expense, Long> id;
    public static volatile SingularAttribute<Expense, UserExpense> userExpense;
    public static volatile SingularAttribute<Expense, Integer> expense;
    public static volatile SingularAttribute<Expense, Date> expDate;

}