package com.quintrix.banking.transactions;

import java.util.Date;
import java.util.Set;

public interface TransactionRepoMethods {
	public Set<Transaction> getAllPendingTransactions(Date date);
	public boolean submitNewTransaction(Transaction transactionToSubmit) throws Exception;
	public Batch startBatchProcessing();
}
