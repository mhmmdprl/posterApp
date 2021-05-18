package com.esandmongodb.posterapp.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "comment")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Comment  extends BaseCollection{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Transient
	public static final String seqName="comment_sequence";
	@Id
	private Long id;
	
	private String content;
	
	private String image;
	
	private Long authorId;
	
	private String postUuid;

}
