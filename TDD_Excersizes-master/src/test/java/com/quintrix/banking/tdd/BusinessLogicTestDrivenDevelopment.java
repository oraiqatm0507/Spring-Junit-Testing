package com.quintrix.banking.tdd;

import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import com.quintrix.banking.accounts.Account;
import com.quintrix.banking.accounts.AccountRepo;
import com.quintrix.banking.company.Branch;
import com.quintrix.banking.company.CompanyRepo;
import com.quintrix.banking.transactions.Transaction;
import com.quintrix.banking.transactions.TransactionType;
import com.quintrix.banking.transactions.TransactionsRepo;


import graphql.com.google.common.collect.Sets;



@SpringBootTest

public class BusinessLogicTestDrivenDevelopment {
	
	@Autowired 
	CompanyRepo companyDb;
	@Autowired
	AccountRepo accountsDb;
	@Autowired
	TransactionsRepo transactionsDb;
	
	@Test
	//canAddBranch
	public void test1_a() {
		Branch testBranch = new Branch();
		testBranch.accounts = Sets.newHashSet();
		testBranch.location = "test location";
		assert(companyDb.addBranch(testBranch) != null);
	}

	@Test
	//canQueryBranches 
	public void test1_b() {
		Branch testBranch = companyDb.findBranchByLocation("test location");
		assert(testBranch != null);
	}
	
	@Test
	//canAddAccount
	public void test2_a() {
		Account testAccount = makeAccount("General Zod");
		//  ** The following line may clash with the auto ID generation found in DataModel.java
		testAccount.id = 1;
		accountsDb.addAccount(testAccount);
	}
	
	@Test
	//canFindAccountById
	public void test2_b() {
		//  ** If auto ID generation created an issue with specifying an ID then this test will need to first locate a valid account ID before performing this search
		Account testAccount = accountsDb.findAccountById(1);
		assert(testAccount != null);
	}

	@Test
	//canFindAccountByName
	public void test2_c() {
		Account testAccount = accountsDb.findAccountByOwnerName("Zod, General");
		assert(testAccount != null);
	}
	
	@Test
	//canUpdateAccountInfo
	public void test2_d() {
		Account testAccount = accountsDb.findAccountByOwnerName("General Zod");
		testAccount.ownerName = "Batman";
		accountsDb.updateAccount(testAccount);
		testAccount = accountsDb.findAccountByOwnerName("Batman");
		assert(testAccount != null && testAccount.ownerName.equals("Batman"));
	}
	
	@Test
	//canCloseAccount
	public void test2_e() {
		Account testAccount = accountsDb.findAccountByOwnerName("Batman");
		accountsDb.closeAccount(testAccount);
		testAccount = accountsDb.findAccountByOwnerName("Batman");
		assert(testAccount.closed != null);
	}
	
	@Test
	//canStoreTwoNewAccounts
	public void test3_a() {
		Account newAccount1 = makeAccount("Thing 1");
		Account newAccount2 = makeAccount("Thing 2");
		newAccount1.currentBalance = 50.0;
		newAccount2.currentBalance = 0.0;
		accountsDb.addAccount(newAccount1);
		accountsDb.addAccount(newAccount2);
	}
	
	@Test
	//canCreateValidTransaction
	public void test3_b() throws Exception {
		Account sourceAccount = accountsDb.findAccountByOwnerName("Thing 1");
		Account destAccount = accountsDb.findAccountByOwnerName("Thing 2");
		Transaction newTransaction = new Transaction();
		newTransaction.amount = 50.0;
		newTransaction.date = new Date();
		newTransaction.sourceAccountId = sourceAccount.id;
		newTransaction.destinationAccountId = destAccount.id;
		newTransaction.type = TransactionType.Transfer;
		transactionsDb.submitNewTransaction(newTransaction);
	}
	
	@Test
	//cannotCreateInvalidTransaction
	public void test3_c() {
		Account sourceAccount = accountsDb.findAccountByOwnerName("Thing 2");
		Account destAccount = accountsDb.findAccountByOwnerName("Thing 1");
		Transaction newTransaction = new Transaction();
		newTransaction.amount = 1.0;
		newTransaction.date = new Date();
		newTransaction.sourceAccountId = sourceAccount.id;
		newTransaction.destinationAccountId = destAccount.id;
		newTransaction.type = TransactionType.Transfer;
		
		Assertions.assertThrows(Exception.class, () -> transactionsDb.submitNewTransaction(newTransaction));
	}



	@Test
	//canBatchProcessTransactions
	public void test3_d() {
		transactionsDb.startBatchProcessing();
	}
	
	@Test
	//batchProcessIsAccurate
	public void test4_a() {
		Account testAccount1 = accountsDb.findAccountByOwnerName("Thing 1");
		Account testAccount2 = accountsDb.findAccountByOwnerName("Thing 2");
		assert(testAccount1.currentBalance == 0.0 && testAccount2.currentBalance == 50.0);
	}
	
	private Account makeAccount(String owner) {
		Account testAccount = new Account();
		testAccount.currentBalance = 0.0;
		testAccount.homeBranch = companyDb.findBranchByLocation("test location");
		testAccount.opened = new Date();
		testAccount.transactions = Sets.newHashSet();
		testAccount.ownerName = owner;
		return testAccount;
	}
	
}
