package com.esandmongodb.posterapp.repository;

import org.springframework.data.mongodb.repository.Query;

import com.esandmongodb.posterapp.entity.Comment;

public interface CommentRepository  extends BaseRepository<Comment>{

	@Query("{'$and':[ {'authorId':?0}, {'postUuid':?1} ] }")
	public Comment findByAuthorIdAndPostUuid(Long authorId, String postUuid);
}
