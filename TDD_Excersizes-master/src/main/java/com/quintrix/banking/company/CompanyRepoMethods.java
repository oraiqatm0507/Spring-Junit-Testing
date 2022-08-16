package com.quintrix.banking.company;

public interface CompanyRepoMethods {
	public Branch addBranch(Branch newBranch);
	public Branch findBranchByLocation(String location);
	public Branch findBranchById(long id);
}
