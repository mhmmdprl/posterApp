package com.esandmongodb.posterapp.repository;

import org.springframework.data.mongodb.repository.Query;

import com.esandmongodb.posterapp.entity.Following;

public interface FollowingRepository extends BaseRepository<Following> {
	@Query("{'$and':[ {'fromAuthorId':?0}, {'toAuthorUuid':?1} ] }")
	Following findByFromAuthorIdAndToAuthorUuid(Long userIdFromRequest, String uuid);
}
