package com.esandmongodb.posterapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.esandmongodb.posterapp.entity.Operation;

public interface OperationService extends BaseService<Operation> {

	Operation findById(Long operationId);

	boolean existByCode(String code);

	Page<Operation> findAllActiceOperations(Pageable pageable);

	Operation findByCode(String operationCode);





}
