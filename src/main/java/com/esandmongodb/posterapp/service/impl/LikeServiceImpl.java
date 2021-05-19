package com.esandmongodb.posterapp.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;


import com.esandmongodb.posterapp.entity.Like;
import com.esandmongodb.posterapp.entity.Post;
import com.esandmongodb.posterapp.repository.LikeRepository;
import com.esandmongodb.posterapp.service.LikeService;

@Service
public class LikeServiceImpl implements LikeService{
	@Autowired
	private MongoOperations mongoOperations;
	
	@Autowired
	private LikeRepository likeRepository;

	@Override
	public long countByPost(Post post) {
		return this.likeRepository.countByPost(post);
	}

	@Override
	public Like save(Like collection) {
		return this.likeRepository.save(collection);
	}

	@Override
	public boolean delete(String uuid, Long updatedBy) {
		Query query = new Query(Criteria.where("uuid").is(uuid));
		Update update = new Update().set("deleted", '1').set("updatedBy", updatedBy);

		try {
			this.mongoOperations.findAndModify(query, update, Like.class);

		} catch (Exception e) {
			throw e;
		}
		return true;
	}

	@Override
	public boolean existsByUuid(String uuid) {
		
		return this.likeRepository.existsByUuid(uuid);
	}

	@Override
	public Like findByUuid(String uuid) {
		return this.likeRepository.findByUuid(uuid);
	}

	@Override
	public List<Like> findAll() {
		return this.likeRepository.findAll();
	}

	@Override
	public boolean existsByAuthorIdAndPostUuid(Long authorId, String postUuid) {
		
		return this.likeRepository.existsByAuthorIdAndPostUuid(authorId,postUuid);
	}

	@Override
	public Like findByAuthorIdAndPostUuid(Long authorId, String postUuid) {
		
		return this.likeRepository.findByAuthorIdAndPostUuid(authorId,postUuid);
	}




	
	
	
}
