package com.esandmongodb.posterapp.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "author")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Author extends BaseCollection {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Transient
	public static final String seqName = "author_sequence";
	@Id
	private Long id;

	private String name;

	private String aboutMe;
	@Indexed(unique = true)
	private String email;

	@Indexed(unique = true)
	private String username;

	private String password;
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private LocalDate birtOfDate;

	@DBRef
	@JsonIgnore
	private List<Role> roles = new ArrayList<Role>();

	@DBRef(lazy = true)
	private List<Post> posts = new ArrayList<Post>();

	@DBRef
	private List<Follower> followers = new ArrayList<Follower>();

	@DBRef
	private List<Following> followings = new ArrayList<Following>();

	public void addPost(Post post) {

		this.posts.add(post);
	}

	public void removeFollower(Follower follower) {

		this.followers.removeIf(obj -> obj.getUuid().equals(follower.getUuid()));
	}

	public void removeFollowings(Following following) {

		this.followings.removeIf(obj -> obj.getUuid().equals(following.getUuid()));
	}
}