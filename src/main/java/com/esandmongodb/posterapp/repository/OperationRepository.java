package com.esandmongodb.posterapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ExistsQuery;
import org.springframework.data.mongodb.repository.Query;

import com.esandmongodb.posterapp.entity.Operation;
public interface OperationRepository extends BaseRepository<Operation> {

	@ExistsQuery("{'$and':[ {'deleted':'0'}, {'banStatus':'0'},{'code':?0} ] }")
	boolean existByCode(String code);
	@Query("{'$and':[ {'deleted':'0'}, {'banStatus':'0'} ]}")
	Page<Operation> findAllActiveOperations(Pageable pageable);
	Operation findByCode(String operationCode); 
}
