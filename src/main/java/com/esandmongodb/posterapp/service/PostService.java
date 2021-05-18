package com.esandmongodb.posterapp.service;

import java.util.List;

import com.esandmongodb.posterapp.entity.Like;
import com.esandmongodb.posterapp.entity.Post;

public interface PostService extends BaseService<Post> {
	

	
	public boolean banPost(String uuid,Long updatedBy);
	
	List<Like> findLikesByUuid(String uuid);

	List<Post> findByAuthorId(Long userIdFromRequest);

}
