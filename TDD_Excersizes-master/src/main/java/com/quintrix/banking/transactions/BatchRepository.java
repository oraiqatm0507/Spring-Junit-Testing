package com.quintrix.banking.transactions;

import java.util.Date;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Component("batchRepo")
public interface BatchRepository  extends CrudRepository<Batch, Long> {
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE BATCH c SET c.DATE = :date, c.FINALIZED = :boolVal WHERE c.ID = :id", nativeQuery = true)
	int updateBatch(
			@Param("date") Date closeDate, 
			@Param("boolVal") boolean batchFinalized,
			@Param("id") long accountId
			);
	
}
