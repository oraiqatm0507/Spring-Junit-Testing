package com.quintrix.banking.company;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/***
 * Please read about JPA repositories and utilize them here
 * @author drhin
 *
 */

@Repository
@Component("companyRepo")
public interface CompanyRepository  extends CrudRepository<Branch, Long>  {
	
	@Query(value = "SELECT * FROM BRANCH WHERE LOCATION = ?1", nativeQuery = true)
	Branch findBranchByLocation(String location);
	
	@Query(value = "SELECT * FROM BRANCH WHERE ID = ?1", nativeQuery = true)
	Branch findBranchById(long id);
	
}
