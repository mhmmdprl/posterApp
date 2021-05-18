package com.esandmongodb.posterapp.model.request;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleRequest {
	private String name;
	
	private String code;

	private String description;
	
	List<Long> operationIds;
}
