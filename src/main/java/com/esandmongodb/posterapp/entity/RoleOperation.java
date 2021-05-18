package com.esandmongodb.posterapp.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "role_operation")
public class RoleOperation extends BaseCollection {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String seqName="role_operation_sequence";
	@Id
	private Long id;
	private Long roleId;
	private Long operationId;
}
