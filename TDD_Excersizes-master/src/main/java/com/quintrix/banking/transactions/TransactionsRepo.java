package com.quintrix.banking.transactions;

import java.util.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.quintrix.banking.accounts.Account;
import com.quintrix.banking.accounts.AccountsRepository;

@Component("transactionsDb")
public class TransactionsRepo implements TransactionRepoMethods {

	@Autowired
	AccountsRepository accountsRepo;
	
	@Autowired
	TransactionsRepository transactionRepo;
	
	@Autowired
	BatchRepository batchRepo;
	
	
	@Override
	public Set<Transaction> getAllPendingTransactions(Date date) {
		return transactionRepo.findAllTransactions();
	}

	@Override
	public boolean submitNewTransaction(Transaction transactionToSubmit) throws Exception{
		double transactionAmount = transactionToSubmit.amount;
		long sourceId = transactionToSubmit.sourceAccountId;
		Account sourceAcc = accountsRepo.findById(sourceId).get();
		
		 try {
	         assert sourceAcc.currentBalance >= transactionAmount : "Not Enough Funds!!!!";
		     sourceAcc.currentBalance -= transactionAmount;
			 accountsRepo.updateAccountCurrentBalance(sourceAcc.currentBalance, sourceId);
			 transactionRepo.save(transactionToSubmit);
	      }
	      catch (AssertionError e) {
	         System.out.println();
	         
	         throw new Exception();
	      }
		
		return false;
	}

	@Override
	public Batch startBatchProcessing() {
		// TODO Auto-generated method stub
		Set<Transaction> allTransactions = transactionRepo.findAllTransactions();
		

		Batch newBatch = new Batch();
		batchRepo.save(newBatch);
		for(Transaction transaction: allTransactions) {
			if(transaction.type.equals(TransactionType.Transfer)) {
				makeTransfer(transaction.amount, transaction.destinationAccountId);
				transactionRepo.updateTransactionBatchId(newBatch.id, transaction.id);
			}
			newBatch.transactions.add(transaction);
			
		}
		batchRepo.updateBatch(new Date(), true, newBatch.id);

		return newBatch ;
	}
	
	
	private void makeTransfer(double amnt, long destId) {
		Account sourceAcc = accountsRepo.findById(destId).get();
		sourceAcc.currentBalance += amnt;
		accountsRepo.updateAccountCurrentBalance(sourceAcc.currentBalance, destId);
	}

}
