package com.esandmongodb.posterapp.entity;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "operation")
@AllArgsConstructor
@NoArgsConstructor
public class Operation extends BaseCollection {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Transient
	public static final String seqName = "operation_sequence";

	private Long id;

	private String path;

	private String method;

	private String description;

	private String code;
}
