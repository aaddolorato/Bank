package com.accenture.bank.application.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2022-02-28T10:10:38.585+0100")
@StaticMetamodel(BankAccount.class)
public class BankAccount_ {
	public static volatile SingularAttribute<BankAccount, Integer> idAccount;
	public static volatile SingularAttribute<BankAccount, Integer> idDepositor;
	public static volatile SingularAttribute<BankAccount, Integer> idBank;
	public static volatile SingularAttribute<BankAccount, Integer> iban;
	public static volatile SingularAttribute<BankAccount, Integer> balance;
}
