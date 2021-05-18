package com.esandmongodb.posterapp.service;

import com.esandmongodb.posterapp.entity.Comment;

public interface CommentService extends BaseService<Comment> {
	public Comment findByAuthorIdAndPostUuid(Long authorId, String postUuid);
}
