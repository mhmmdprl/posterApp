package com.esandmongodb.posterapp.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Post extends BaseCollection {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Transient
	public static final String seq = "post_sequence";

	@Id
	private Long id;

	private String content;

	private String image;

	@DBRef
	private List<Comment> comments = new ArrayList<Comment>();
	@DBRef
	private List<Like> likes = new ArrayList<Like>();
	private Long authorId;
	@Transient
	private Long commentCount;
	@Transient
	private Long likeCount;

	public void addComment(Comment comment) {

		this.comments.add(comment);
	}
	public void removePost(Post post) {
		
		this.comments.removeIf(obj->obj.getUuid().equals(post.getUuid()));
	}

	public void addLikes(Like like) {

		this.likes.add(like);
	}

	public void removeLikes(Like like) {
		this.likes.removeIf(obj->obj.getUuid().equals(like.getUuid()));
		
	}

}
