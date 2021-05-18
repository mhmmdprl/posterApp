package com.esandmongodb.posterapp.service;


import com.esandmongodb.posterapp.entity.Like;
import com.esandmongodb.posterapp.entity.Post;

public interface LikeService extends BaseService<Like>  {

	public long countByPost(Post post);
	public boolean existsByAuthorIdAndPostUuid(Long authorId ,String postUuid);
	public Like findByAuthorIdAndPostUuid(Long authorId, String postUuid);
}
