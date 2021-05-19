package com.esandmongodb.posterapp.rest;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.esandmongodb.posterapp.entity.Comment;
import com.esandmongodb.posterapp.entity.Like;
import com.esandmongodb.posterapp.entity.Post;
import com.esandmongodb.posterapp.filter.TokenProvider;
import com.esandmongodb.posterapp.service.DbSequenceService;
import com.esandmongodb.posterapp.service.LikeService;
import com.esandmongodb.posterapp.service.PostService;

@RestController
@RequestMapping("/like")
public class LikeController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(Comment.class);
	@Autowired
	private TokenProvider jwtTokenUtil;
	@Autowired
	private PostService postService;

	@Autowired
	private LikeService likeService;
	@Autowired
	private DbSequenceService dbSequenceService;
	@PostMapping("/{postUuid}")
	public ResponseEntity<?> likePost(@PathVariable String postUuid, HttpServletRequest httpServletRequest) {
		Post post=this.postService.findByUuid(postUuid);
		
		Like changedLikeStatus=null;
		Long authorId = this.jwtTokenUtil.getUserIdFromRequest(httpServletRequest);
		try {
			if (this.likeService.existsByAuthorIdAndPostUuid(authorId, postUuid)) {
				changedLikeStatus = this.likeService.findByAuthorIdAndPostUuid(authorId, postUuid);
				if (changedLikeStatus.getDeleted() == '0') {
					post.removeLikes(changedLikeStatus);
					changedLikeStatus.setUpdatedBy(authorId);
					changedLikeStatus.setUpdatedDate(new Date());
					changedLikeStatus.setDeleted('1');
					this.likeService.save(changedLikeStatus);
					
					this.postService.save(post);
				} else {
					
					
					changedLikeStatus.setDeleted('0');
					post.addLikes(changedLikeStatus);
					this.likeService.save(changedLikeStatus);
					this.postService.save(post);

				}

			} else {
				changedLikeStatus = new Like();
				changedLikeStatus.setAuthorId(authorId);
				changedLikeStatus.setPostUuid(postUuid);
				changedLikeStatus.setId(this.dbSequenceService.getSeq(Like.seqName));
				post.addLikes(changedLikeStatus);
				this.postService.save(post);
				this.likeService.save(changedLikeStatus);
			}

		} catch (Exception e) {
			return operationFail(e, logger);
		}

		return operationSuccess(changedLikeStatus);
	}

}
