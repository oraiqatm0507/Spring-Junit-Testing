package com.quintrix.banking.transactions;

import java.util.Date;
import java.util.Set;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.quintrix.banking.company.Branch;

/***
 * Please read about JPA repositories and utilize them here
 * @author drhin
 *
 */

@Repository
@Component("transactionRepo")
public interface TransactionsRepository  extends CrudRepository<Transaction, Long>  {
	
	@Query(value = "SELECT * FROM TRANSACTIONS ", nativeQuery = true)
	Set<Transaction> findAllTransactions();
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE TRANSACTIONS c SET c.BATCH_ID = :batch WHERE c.ID = :id", nativeQuery = true)
	int updateTransactionBatchId(@Param("batch") long batchId, @Param("id") long transactionId);
	
	
	
	
}
