package com.quintrix.banking.accounts;

public interface AccountRepoMethods {
	public Account findAccountByOwnerName(String owner);
	public Account findAccountById(long id);
	public Account addAccount(Account accountToAdd);
	public boolean closeAccount(Account accountToClose);
	public boolean updateAccount(Account updatedAccount);
}
