package com.accenture.bank.application.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2022-03-16T17:20:01.977+0100")
@StaticMetamodel(Transaction.class)
public class Transaction_ {
	public static volatile SingularAttribute<Transaction, Integer> idTransaction;
	public static volatile SingularAttribute<Transaction, Integer> idBankAccount;
	public static volatile SingularAttribute<Transaction, Integer> amount;
	public static volatile SingularAttribute<Transaction, String> type;
	public static volatile SingularAttribute<Transaction, Date> dateTransaction;
}
