package com.esandmongodb.posterapp.service.Impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import com.esandmongodb.posterapp.entity.Like;
import com.esandmongodb.posterapp.entity.Post;
import com.esandmongodb.posterapp.repository.PostRepository;
import com.esandmongodb.posterapp.service.PostService;

@Service
public class PostServiceImp implements PostService {
	@Autowired
	private PostRepository postRepository;
	@Autowired
	private MongoOperations mongoOperations;

	@Override
	public Post save(Post post) {
		return this.postRepository.save(post);
	}

	@Override
	public boolean existsByUuid(String uuid) {
		return this.postRepository.existsByUuid(uuid);
	}

	@Override
	public Post findByUuid(String uuid) {
		return this.postRepository.findByUuid(uuid);
	}

	@Override
	public List<Post> findAll() {
		return this.postRepository.findAllActive();
	}

	@Override
	public List<Post> findByAuthorId(Long authorId) {
		return this.postRepository.findByAuthorId(authorId);
	}

	@Override
	public boolean delete(String uuid, Long updatedBy) {

		Query query = new Query(Criteria.where("uuid").is(uuid));
		Update update = new Update().set("deleted", '1').set("updatedBy", updatedBy);

		try {
			this.mongoOperations.findAndModify(query, update, Post.class);

		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean banPost(String uuid, Long updatedBy) {
		Query query = new Query(Criteria.where("uuid").is(uuid));
		Update update = new Update().set("banStasus", '1').set("updatedBy", updatedBy).set("updatedDate", new Date());
		try {
			this.mongoOperations.findAndModify(query, update, Post.class);
		} catch (Exception e) {
			throw e;
		}

		return true;
	}

	@Override
	public List<Like> findLikesByUuid(String uuid) {
		
		return this.postRepository.findLikesByUuid(uuid);
	}

}
