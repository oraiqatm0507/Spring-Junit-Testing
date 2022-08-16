package com.quintrix.banking.accounts;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component("accountsDb")
public class AccountRepo implements AccountRepoMethods {

	@Autowired
	AccountsRepository accountsRepo;
	
	
	@Override
	public Account findAccountByOwnerName(String owner) {
		String ownerName = owner;
		boolean containsComma = owner.contains(",");
		if(containsComma) {
			List<String> allNames = Arrays.asList(owner.split(","));
			String firstName = allNames.get(1).replaceAll("\\s", "");
			String lastName = allNames.get(0);
			ownerName = firstName + " " +  lastName ; 
		}
		return accountsRepo.findAccountByOwnerName(ownerName);
	}

	@Override
	public Account findAccountById(long id) {
		return accountsRepo.findById(id).get();
	}

	@Override
	public Account addAccount(Account accountToAdd) {
		return accountsRepo.save(accountToAdd);
	}

	@Override
	public boolean closeAccount(Account accountToClose) {
		Date currentDate = new Date();
		accountsRepo.updateAccountCloeAccount(currentDate, accountToClose.id);
		return false;
	}

	@Override
	public boolean updateAccount(Account updatedAccount) {
		accountsRepo.updateAccountOwnerName(updatedAccount.ownerName, updatedAccount.id);
		return false;
	
	}
	
}
