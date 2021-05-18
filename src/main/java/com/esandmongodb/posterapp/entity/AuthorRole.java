package com.esandmongodb.posterapp.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document(collection = "user_role")
@Getter
@Setter
public class AuthorRole extends BaseCollection{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String seqName="author_role_sequence";
	@Id
	private Long id;
	
	private  Long authorId;
	
	private Long roleId;
	
}
