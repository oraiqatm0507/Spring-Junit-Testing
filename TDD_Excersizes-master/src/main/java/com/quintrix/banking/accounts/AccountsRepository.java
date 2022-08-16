package com.quintrix.banking.accounts;



import java.util.Date;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;



@Repository
@Component("accountsRepo")
public interface AccountsRepository  extends CrudRepository<Account, Long>  {
	@Query(value = "SELECT * FROM ACCOUNT WHERE OWNER_NAME = ?1", nativeQuery = true)
	Account findAccountByOwnerName(String accountName);
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE ACCOUNT c SET c.OWNER_NAME = :name WHERE c.ID = :id", nativeQuery = true)
	int updateAccountOwnerName(@Param("name") String ownerName, @Param("id") long accountId);
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE ACCOUNT c SET c.CLOSED = :date WHERE c.ID = :id", nativeQuery = true)
	int updateAccountCloeAccount(@Param("date") Date closeDate, @Param("id") long accountId);
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE ACCOUNT c SET c.CURRENT_BALANCE = :money WHERE c.ID = :id", nativeQuery = true)
	int updateAccountCurrentBalance(@Param("money") double newAmount, @Param("id") long accountId);
	
	
	
}
