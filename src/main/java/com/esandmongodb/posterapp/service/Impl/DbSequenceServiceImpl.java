package com.esandmongodb.posterapp.service.Impl;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.esandmongodb.posterapp.entity.util.DbSequence;
import com.esandmongodb.posterapp.service.DbSequenceService;

@Service
@Transactional
public class DbSequenceServiceImpl implements DbSequenceService{

	@Autowired
	private MongoOperations mongoOperations;
	
	@Override
	public Long getSeq(String sequenceName) {
		Query query=new Query(Criteria.where("seq").is(sequenceName));
		Update update =new Update().inc("id", 1);
		DbSequence counter=this.mongoOperations.findAndModify(query, update, new FindAndModifyOptions().upsert(true), DbSequence.class);
		return !Objects.isNull(counter) ? counter.getId() : 1L;
	}

	
	
}
