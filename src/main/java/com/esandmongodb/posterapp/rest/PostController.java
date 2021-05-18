package com.esandmongodb.posterapp.rest;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esandmongodb.posterapp.entity.Author;
import com.esandmongodb.posterapp.entity.Like;
import com.esandmongodb.posterapp.entity.Post;
import com.esandmongodb.posterapp.filter.TokenProvider;
import com.esandmongodb.posterapp.model.request.PostRequest;
import com.esandmongodb.posterapp.service.AuthorService;
import com.esandmongodb.posterapp.service.DbSequenceService;
import com.esandmongodb.posterapp.service.PostService;

@RestController
@RequestMapping("/posts")
public class PostController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(AuthorController.class);
	@Autowired
	private DbSequenceService dbSequenceService;

	@Autowired
	private PostService postService;
	@Autowired
	private AuthorService authorService;
	@Autowired
	private TokenProvider jwtTokenUtil;
	@PreAuthorize("hasAuthority('/posts/addPost_POST')")
	@PostMapping("/addPost")
	public ResponseEntity<?> addPost(@RequestBody PostRequest postRequest, HttpServletRequest httpServletRequest) {

		Post post = new Post();
		try {
			Long authorId = this.jwtTokenUtil.getUserIdFromRequest(httpServletRequest);
			Author author = this.authorService.findById(authorId);
			post.setCreatedBy(authorId);
			post.setCreatedDate(new Date());
			post.setImage(postRequest.getImage());
			post.setContent(postRequest.getContent());
			post.setId(this.dbSequenceService.getSeq(Post.seq));
			post.setAuthorId(authorId);
			author.addPost(post);
			this.authorService.save(author);
			this.postService.save(post);
		} catch (Exception e) {
			return operationFail(e, logger);
		}

		return operationSuccess(post);

	}
	@PreAuthorize("hasAuthority('/posts_GET')")
	@GetMapping
	public ResponseEntity<?> getPosts(HttpServletRequest httpServletRequest) {

		List<Post> posts = null;
		try {

			posts = this.postService.findAll();

		} catch (Exception e) {
			operationFail(e, logger);
		}
		return operationSuccess(posts);

	}
	@PreAuthorize("hasAuthority('/posts/deletePost/{uuid}_DELETE')")
	@DeleteMapping("/deletePost/{uuid}")
	public ResponseEntity<?> deletePost(@PathVariable String uuid, HttpServletRequest httpServletRequest) {
		if (this.postService.existsByUuid(uuid))
			try {

				this.postService.delete(uuid, this.jwtTokenUtil.getUserIdFromRequest(httpServletRequest));

			} catch (Exception e) {
				return operationFail(e, logger);
			}

		return operationSuccess(true);

	}
	@PreAuthorize("hasAuthority('/posts/updatePost/{uuid}_PUT')")
	@PutMapping("/updatePost/{uuid}")
	public ResponseEntity<?> updatePost(@PathVariable String uuid, @RequestBody PostRequest postRequest,
			HttpServletRequest httpServletRequest) {
		Post post = null;
		
		if (this.postService.existsByUuid(uuid)) {

			try {
				post = this.postService.findByUuid(uuid);
				post.setContent(postRequest.getContent());
				post.setImage(postRequest.getImage());
				post.setUpdatedBy(this.jwtTokenUtil.getUserIdFromRequest(httpServletRequest));
				post.setUpdatedDate(new Date());
				this.postService.save(post);

			} catch (Exception e) {
				return operationFail(e, logger);
			}

		} else {
			return operationFail(new Exception(uuid), logger);
		}

		return operationSuccess(post);
	}
	@PreAuthorize("hasAuthority('/posts/myposts}_GET')")
	@GetMapping("/myposts")
	public ResponseEntity<?> getMyPosts(HttpServletRequest httpServletRequest) {
		List<Post> posts = null;
//		List<Comment> comments = null;
		try {

			posts = this.postService.findByAuthorId(this.jwtTokenUtil.getUserIdFromRequest(httpServletRequest));
//			for (Post post : posts) {
//				comments = post.getComments();
//				for (Comment comment : comments) {
//					if (comment.getDeleted() == '1' || comment.getBanStatus() == '1') {
//
//						comments.remove(comment);
//					}
//				}
//				post.setComments(comments);
//				post.setCommentCount((long) comments.size());
//			}
		} catch (Exception e) {
			operationFail(e, logger);
		}

		return operationSuccess(posts);
	}
	@PreAuthorize("hasAuthority('/posts/likes/{uuid}_GET')")
	@GetMapping("/likes/{uuid}")
	public ResponseEntity<?> getPostLikes(@PathVariable String uuid) {
		List<Like> likes = null;
		try {

			likes = this.postService.findLikesByUuid(uuid);

		} catch (Exception e) {

			return operationFail(e, logger);
		}

		return operationSuccess(likes);
	}
}
