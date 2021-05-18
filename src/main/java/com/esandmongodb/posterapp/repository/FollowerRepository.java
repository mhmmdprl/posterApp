package com.esandmongodb.posterapp.repository;

import org.springframework.data.mongodb.repository.ExistsQuery;
import org.springframework.data.mongodb.repository.Query;

import com.esandmongodb.posterapp.entity.Follower;

public interface FollowerRepository  extends BaseRepository<Follower>{
	@ExistsQuery("{'$and':[ {'toAuthorId':?0}, {'fromAuthorUuid':?1} ] }")
	boolean existsByFromAuthorUuidAndToAuthorId(Long userIdFromRequest, String uuid);
	@Query("{'$and':[ {'toAuthorId':?0}, {'fromAuthorUuid':?1} ] }")
	Follower findByFromAuthorUuidAndToAuthorId(Long userIdFromRequest, String uuid);
}
