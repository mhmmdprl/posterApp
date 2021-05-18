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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esandmongodb.posterapp.entity.Author;
import com.esandmongodb.posterapp.entity.Comment;
import com.esandmongodb.posterapp.filter.TokenProvider;
import com.esandmongodb.posterapp.service.AuthorService;
import com.esandmongodb.posterapp.service.CommentService;
import com.esandmongodb.posterapp.service.PostService;

@RestController
@RequestMapping("/admin")
public class AdminController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(AuthorController.class);

	@Autowired
	private AuthorService authorService;

	@Autowired
	private PostService postService;

	@Autowired
	private TokenProvider jwtTokenUtil;

	@Autowired
	private CommentService commentService;
	@PreAuthorize("hasAuthority('/admin/delete/{authorUuid}_DELETE')")
	@DeleteMapping("/delete/{authorUuid}")
	public ResponseEntity<?> deleteAuthor(@PathVariable String authorUuid, HttpServletRequest httpRequest) {
		if (this.authorService.existsByUuid(authorUuid)) {
			try {
				Long id = this.jwtTokenUtil.getUserIdFromRequest(httpRequest);
				this.authorService.delete(authorUuid, id);

			} catch (Exception e) {
				return operationFail(e, logger);
			}

		}
		return operationSuccess(this.authorService.findByUuid(authorUuid));

	}
	@PreAuthorize("hasAuthority('/admin/ban/{authorUuid}_PUT')")
	@PutMapping("/ban/{authorUuid}")
	public ResponseEntity<?> banAuthor(@PathVariable String authorUuid, HttpServletRequest httpRequest) {
		if (this.authorService.existsByUuid(authorUuid)) {
			try {
				Long id = this.jwtTokenUtil.getUserIdFromRequest(httpRequest);
				this.authorService.ban(authorUuid, id);

			} catch (Exception e) {
				return operationFail(e, logger);
			}

		}
		return operationSuccess(this.authorService.findByUuid(authorUuid));

	}
	@PreAuthorize("hasAuthority('/admin/authors_GET')")
	@GetMapping("/authors")
	public ResponseEntity<?> getAll() {
		List<Author> authors = null;
		try {
			authors = this.authorService.findAll();

		} catch (Exception e) {
			return operationFail(e, logger);
		}
		return operationSuccess(authors);
	}
	

	@PreAuthorize("hasAuthority('/admin/authorsPassive_GET')")
	@GetMapping("/authorsPassive")
	public ResponseEntity<?> getAllPassive() {
		List<Author> authors = null;
		try {
			authors = this.authorService.findAllPassive();

		} catch (Exception e) {
			return operationFail(e, logger);
		}

		return operationSuccess(authors);
	}
	@PreAuthorize("hasAuthority('/admin/banPost/{authorUuid}_PUT')")
	@PutMapping("/banPost/{uuid}")
	public ResponseEntity<?> banPost(@PathVariable String uuid, HttpServletRequest httpServletRequest) {

		try {

			this.postService.banPost(uuid, this.jwtTokenUtil.getUserIdFromRequest(httpServletRequest));
		} catch (Exception e) {
			operationFail(e, logger);
		}

		return operationSuccess(true);
	}
	@PreAuthorize("hasAuthority('/admin/banComment/{commentUuid}_PUT')")
	@PutMapping("/banComment/{commentUuid}")
	public ResponseEntity<?> banComment(@PathVariable String commentUuid, HttpServletRequest httpServletRequest) {
		Comment comment = null;
		try {
			comment = this.commentService.findByUuid(commentUuid);
			comment.setBanStatus('1');
			comment.setUpdatedBy(this.jwtTokenUtil.getUserIdFromRequest(httpServletRequest));
			comment.setUpdatedDate(new Date());
			this.commentService.save(comment);
		} catch (Exception e) {
		return operationFail(e, logger);
		}

		return operationSuccess(comment);

	}

}
