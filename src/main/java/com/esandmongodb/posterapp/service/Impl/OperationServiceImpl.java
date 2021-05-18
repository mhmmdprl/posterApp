package com.esandmongodb.posterapp.service.Impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.esandmongodb.posterapp.entity.Operation;
import com.esandmongodb.posterapp.repository.OperationRepository;
import com.esandmongodb.posterapp.service.OperationService;
@Service
public class OperationServiceImpl implements OperationService {

	@Autowired
	private OperationRepository operationRepository;
	@Autowired
	MongoOperations mongoOperations;

	@Override
	public Operation save(Operation collection) {
		return this.operationRepository.save(collection);
	}

	@Override
	public boolean delete(String uuid, Long updatedBy) {
		
		Query query=new Query(Criteria.where("uuid").is(uuid));
		Update update=new Update().set("deleted", '1').set("updatedDate", new Date()).set("updatedBy", updatedBy);
		try {
			mongoOperations.findAndModify(query, update, Operation.class);
		} catch (Exception e) {
			return false;
		}
	
		
		return true;
	}

	@Override
	public boolean existsByUuid(String uuid) {
		return this.existsByUuid(uuid);
	}

	@Override
	public Operation findByUuid(String uuid) {
		return this.findByUuid(uuid);
	}

	@Override
	public List<Operation> findAll() {
		return this.findAll();
	}

	@Override
	public Operation findById(Long operationId) {
		return this.operationRepository.findById(operationId).get();
	}

	@Override
	public boolean existByCode(String code) {
		return this.operationRepository.existByCode(code);
	}

	@Override
	public Page<Operation> findAllActiceOperations(Pageable pageable) {
		// TODO Auto-generated method stub
		return this.operationRepository.findAllActiveOperations( pageable);
	}

	@Override
	public Operation findByCode(String operationCode) {
		// TODO Auto-generated method stub
		return this.operationRepository.findByCode(operationCode);
	}


}
