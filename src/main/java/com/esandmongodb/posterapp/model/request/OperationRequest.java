package com.esandmongodb.posterapp.model.request;

import lombok.Data;

@Data
public class OperationRequest {
	
	private String path;

	private String method;

	private String description;

	private String code;
}
