package com.esandmongodb.posterapp.entity;

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
@Document(collection = "author_log")
public class AuthorLog extends BaseCollection {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Transient
	public static final String seqName="author_log_sequence";
	@Id
	private Long id;

	private Long operationId;


	private String operationCode;

	private String requestMethod;

	private String requestPath;

	private Character operationControlResult;
	
	@DBRef
	private Author author;
	
}
