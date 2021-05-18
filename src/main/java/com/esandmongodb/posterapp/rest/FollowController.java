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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esandmongodb.posterapp.entity.Author;
import com.esandmongodb.posterapp.entity.Follower;
import com.esandmongodb.posterapp.entity.Following;
import com.esandmongodb.posterapp.filter.TokenProvider;
import com.esandmongodb.posterapp.service.AuthorService;
import com.esandmongodb.posterapp.service.DbSequenceService;
import com.esandmongodb.posterapp.service.FollowerService;
import com.esandmongodb.posterapp.service.FollowingService;

@RestController
@RequestMapping("/follow")
public class FollowController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(FollowController.class);
	@Autowired
	private DbSequenceService dbSequenceService;
	@Autowired
	private AuthorService authorService;
	@Autowired
	private TokenProvider jwtUtilToken;
	@Autowired
	private FollowerService followerService;

	@Autowired
	private FollowingService followingService;
	@PreAuthorize("hasAuthority('/follow/{uuid}_POST')")
	@PostMapping("/{uuid}")
	public ResponseEntity<?> follow(@PathVariable String uuid, HttpServletRequest httpServletRequest) {
		boolean isFollow = false;
		Follower follower = null;
		Author followedAuthor = null;
		Author followingAuthor = null;
		Long followingAuthorId = this.jwtUtilToken.getUserIdFromRequest(httpServletRequest);
		try {
			if (this.authorService.existsByUuid(uuid)&&!this.followerService.existsByFromAuthorUuidAndToAuthorId(followingAuthorId, uuid)) {
				followedAuthor = this.authorService.findByUuid(uuid);
				followingAuthor = this.authorService.findById(followingAuthorId);
				follower = new Follower();
				follower.setId(this.dbSequenceService.getSeq(Follower.seqName));
				follower.setFromAuthorUuid(uuid);
				follower.setToAuthorId(followingAuthorId);
				follower.setCreatedBy(followingAuthorId);
				follower.setCreatedDate(new Date());
				followedAuthor.getFollowers().add(follower);
				this.authorService.save(followedAuthor);
				this.followerService.save(follower);

				Following following = new Following();
				following.setId(this.dbSequenceService.getSeq(Following.seqName));
				following.setFromAuthorId(followingAuthorId);
				following.setToAuthorUuid(uuid);
				following.setCreatedBy(followingAuthorId);
				following.setCreatedDate(new Date());
				followingAuthor.getFollowings().add(following);
				this.authorService.save(followingAuthor);
				this.followingService.save(following);

				isFollow = true;

			}
		} catch (Exception e) {
			operationFail(e, logger);
		}

		return operationSuccess(isFollow);
	}
	@PreAuthorize("hasAuthority('/follow/unfollow/{uuid}_DELETE')")
	@DeleteMapping("/unfollow/{uuid}")
	public ResponseEntity<?> unFollow(@PathVariable String uuid, HttpServletRequest httpServletRequest) {
		boolean isDeleted = false;
		Follower follower = null;
		Following following = null;
		Author followedAuthor = null;
		Author followingAuthor = null;
		Long id = this.jwtUtilToken.getUserIdFromRequest(httpServletRequest);
		try {
			if (this.followerService.existsByFromAuthorUuidAndToAuthorId(id, uuid)) {
				followedAuthor = this.authorService.findByUuid(uuid);
				follower = this.followerService.findByFromAuthorUuidAndToAuthorId(id, uuid);
				followedAuthor.removeFollower(follower);
				this.followerService.hardDelete(follower);
				this.authorService.save(followedAuthor);

				following = this.followingService.findByFromAuthorIdAndToAuthorUuid(id, uuid);
				followingAuthor = this.authorService.findById(id);
				followingAuthor.removeFollowings(following);
				this.authorService.save(followingAuthor);
				this.followingService.hardDelete(following);
				isDeleted = true;

			}

		} catch (Exception e) {
			operationFail(e, logger);
		}

		return operationSuccess(isDeleted);
	}
	@PreAuthorize("hasAuthority('/follow/followers_GET')")
	@GetMapping("/followers")
	public ResponseEntity<?> getFollowers(HttpServletRequest httpServletRequest) {
		List<Follower> followers = null;
		try {
			followers = this.authorService.findById(this.jwtUtilToken.getUserIdFromRequest(httpServletRequest))
					.getFollowers();

		} catch (Exception e) {
			operationFail(e, logger);
		}

		return operationSuccess(followers);
	}
	@PreAuthorize("hasAuthority('/follow/following_GET')")
	@GetMapping("/following")
	public ResponseEntity<?> getFollowing(HttpServletRequest httpServletRequest) {
		List<Following> followings = null;
		try {
			followings = this.authorService.findById(this.jwtUtilToken.getUserIdFromRequest(httpServletRequest))
					.getFollowings();

		} catch (Exception e) {
			operationFail(e, logger);
		}

		return operationSuccess(followings);
	}
}
