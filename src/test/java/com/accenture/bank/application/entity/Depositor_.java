package com.accenture.bank.application.entity;

import java.sql.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2022-02-28T10:15:34.018+0100")
@StaticMetamodel(Depositor.class)
public class Depositor_ {
	public static volatile SingularAttribute<Depositor, Integer> idDepositor;
	public static volatile SingularAttribute<Depositor, Integer> idBank;
	public static volatile SingularAttribute<Depositor, String> codFisc;
	public static volatile SingularAttribute<Depositor, String> name;
	public static volatile SingularAttribute<Depositor, String> surname;
	public static volatile SingularAttribute<Depositor, Date> birth;
}
