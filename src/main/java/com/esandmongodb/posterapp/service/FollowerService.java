package com.esandmongodb.posterapp.service;

import com.esandmongodb.posterapp.entity.Follower;

public interface FollowerService extends BaseService<Follower>{

	boolean existsByFromAuthorUuidAndToAuthorId(Long userIdFromRequest, String uuid);
	Follower findByFromAuthorUuidAndToAuthorId(Long userIdFromRequest, String uuid);
	void hardDelete(Follower follower);
	
}
