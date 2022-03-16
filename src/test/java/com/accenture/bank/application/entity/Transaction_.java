package com.accenture.bank.application.entity;

import java.sql.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2022-02-28T10:17:58.933+0100")
@StaticMetamodel(Transaction.class)
public class Transaction_ {
	public static volatile SingularAttribute<Transaction, Integer> idTransaction;
	public static volatile SingularAttribute<Transaction, Integer> idBankAccount;
	public static volatile SingularAttribute<Transaction, Integer> amount;
	public static volatile SingularAttribute<Transaction, String> type;
	public static volatile SingularAttribute<Transaction, Date> dateTransaction;
}
