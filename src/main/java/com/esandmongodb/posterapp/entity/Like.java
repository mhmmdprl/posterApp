package com.esandmongodb.posterapp.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "like")
public class Like extends BaseCollection {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Transient
	public static final String seqName="like_sequence";
	@Id
	private Long id;

	private Long authorId;

	private String postUuid;

	
}
