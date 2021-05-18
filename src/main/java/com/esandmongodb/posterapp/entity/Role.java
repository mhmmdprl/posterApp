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

@Document(collection = "role")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role extends BaseCollection {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Transient
	public static final String seqName="role_sequence";
	
	@Id
	private Long id;
	
	private String name;
	
	private String code;

	private String description;

	@DBRef
	private List<Operation> operations=new ArrayList<Operation>();

	
	public void addOperation(Operation operation) {
		
		this.operations.add(operation);
	}
}
