package com.quintrix.banking.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("comanyDb")
public class CompanyRepo implements CompanyRepoMethods {

	@Autowired
	CompanyRepository companyRepo;
	
	
	
	@Override
	public Branch addBranch(Branch newBranch) {
		return companyRepo.save(newBranch);
		
	}

	@Override
	public Branch findBranchByLocation(String location) {
		return companyRepo.findBranchByLocation(location);
		
	}

	@Override
	public Branch findBranchById(long id) {
		// TODO Auto-generated method stub
		return companyRepo.findBranchById(id);
	}

}
