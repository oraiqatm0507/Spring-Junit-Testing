package com.quintrix.banking.transactions;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class Batch   {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public long id;	
	
	public boolean finalized;
	
	@OneToMany(targetEntity=Transaction.class, fetch=FetchType.EAGER)
	public Set<Transaction> transactions = new HashSet<Transaction>();
	public Date date;
	
	public boolean isFinal() {
		return finalized;
	}
	
	
}
