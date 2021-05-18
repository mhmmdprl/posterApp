package com.esandmongodb.posterapp.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esandmongodb.posterapp.entity.Comment;
import com.esandmongodb.posterapp.repository.CommentRepository;
import com.esandmongodb.posterapp.service.CommentService;
@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;
	
	@Override
	public Comment save(Comment collection) {
		return this.commentRepository.save(collection);
	}

	@Override
	public boolean delete(String uuid, Long updatedBy) {
		return this.delete(uuid, updatedBy);
	}

	@Override
	public boolean existsByUuid(String uuid) {
		return this.commentRepository.existsByUuid(uuid);
	}

	@Override
	public Comment findByUuid(String uuid) {
		return this.commentRepository.findByUuid(uuid);
	}

	@Override
	public List<Comment> findAll() {
		return this.commentRepository.findAll();
	}

	@Override
	public Comment findByAuthorIdAndPostUuid(Long authorId, String postUuid) {
		return this.commentRepository.findByAuthorIdAndPostUuid(authorId, postUuid);
	}

}
