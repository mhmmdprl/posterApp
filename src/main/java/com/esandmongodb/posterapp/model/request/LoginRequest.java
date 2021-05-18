package com.esandmongodb.posterapp.model.request;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

	@NotBlank
	private String username;
	
	@NotBlank
	private String password;
	
	
	
}
