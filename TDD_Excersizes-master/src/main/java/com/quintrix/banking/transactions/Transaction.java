package com.quintrix.banking.transactions;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name="TRANSACTIONS")
public class Transaction  {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public long id;	
	
	public long sourceAccountId;
	public long destinationAccountId;
	@OneToOne
	public Batch batch;
	public double amount;
	public Date date;
	public String comments;
	@Enumerated(EnumType.STRING)
	public TransactionType type;
	
	
	
	public boolean isPendingTransaction() {
		return batch == null || !batch.isFinal();
	}
}
