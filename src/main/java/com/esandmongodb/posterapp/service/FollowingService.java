package com.esandmongodb.posterapp.service;

import com.esandmongodb.posterapp.entity.Following;

public interface FollowingService extends BaseService<Following>{
	void hardDelete(Following following);
	Following findByFromAuthorIdAndToAuthorUuid(Long userIdFromRequest, String uuid);
}
