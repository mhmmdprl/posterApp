package com.esandmongodb.posterapp.rest;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esandmongodb.posterapp.entity.Comment;
import com.esandmongodb.posterapp.entity.Post;
import com.esandmongodb.posterapp.filter.TokenProvider;
import com.esandmongodb.posterapp.model.request.CommentRequest;
import com.esandmongodb.posterapp.service.CommentService;
import com.esandmongodb.posterapp.service.DbSequenceService;
import com.esandmongodb.posterapp.service.PostService;

@RestController
@RequestMapping("/comment")
public class CommentController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(Comment.class);
	@Autowired
	private TokenProvider jwtTokenUtil;
	@Autowired
	private PostService postService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private DbSequenceService dbSequenceService;
	@PreAuthorize("hasAuthority('/comment/save/{postUuid}_POST')")
	@PostMapping("/save/{postUuid}")
	public ResponseEntity<?> addComment(@PathVariable String postUuid, @RequestBody CommentRequest commentRequest,
			HttpServletRequest httpServletRequest) {
		Post post = null;
		Comment comment = new Comment();

		try {

			post = this.postService.findByUuid(postUuid);
			comment.setId(this.dbSequenceService.getSeq(Comment.seqName));
			comment.setAuthorId(this.jwtTokenUtil.getUserIdFromRequest(httpServletRequest));
			comment.setContent(commentRequest.getContent());
			comment.setImage(commentRequest.getImage());
			comment.setCreatedDate(new Date());
			comment.setCreatedBy(this.jwtTokenUtil.getUserIdFromRequest(httpServletRequest));
			post.addComment(comment);
			this.postService.save(post);
			this.commentService.save(comment);
		} catch (Exception e) {
			operationFail(e, logger);
		}

		return operationSuccess(comment);
	}
	@PreAuthorize("hasAuthority('/comment/delete/{postUuid}_DELETE')")
	@DeleteMapping("/delete/{postUuid}")
	public ResponseEntity<?> banComment(@PathVariable String postUuid, HttpServletRequest httpServletRequest) {
		Post post = this.postService.findByUuid(postUuid);
		Comment comment = null;
		Long authorId = this.jwtTokenUtil.getUserIdFromRequest(httpServletRequest);
		try {
			comment = this.commentService.findByAuthorIdAndPostUuid(authorId, postUuid);
			comment.setDeleted('1');
			comment.setUpdatedBy(authorId);
			comment.setUpdatedDate(new Date());
			post.removePost(post);
			this.commentService.save(comment);
		} catch (Exception e) {
			return operationFail(e, logger);
		}

		return operationSuccess(comment);

	}
	@PreAuthorize("hasAuthority('/comment/update/{commentUuid}_PUT')")
	@PutMapping("update/{commentUuid}")
	public ResponseEntity<?> updateComment(@PathVariable String commentUuid, @RequestBody CommentRequest commentRequest,
			HttpServletRequest httpServletRequest) {
		Comment comment = null;
		try {

			comment = this.commentService.findByUuid(commentUuid);
			comment.setContent(commentRequest.getContent());
			comment.setImage(commentRequest.getImage());
			comment.setUpdatedBy(this.jwtTokenUtil.getUserIdFromRequest(httpServletRequest));
			comment.setUpdatedDate(new Date());
			this.commentService.save(comment);
		} catch (Exception e) {
			return operationFail(e, logger);
		}

		return operationSuccess(comment);
	}
}
