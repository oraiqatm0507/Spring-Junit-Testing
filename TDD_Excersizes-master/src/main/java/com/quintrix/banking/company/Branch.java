package com.quintrix.banking.company;

import java.util.Set;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.quintrix.banking.accounts.Account;

@Entity
public class Branch {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public long id;	
	
	public String location;
	@OneToMany(targetEntity=Account.class, mappedBy="homeBranch", fetch=FetchType.EAGER)
	public Set<Account> accounts;
	
}
