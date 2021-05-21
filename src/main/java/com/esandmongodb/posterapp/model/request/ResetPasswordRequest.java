package com.esandmongodb.posterapp.model.request;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordRequest {

	
	@NotNull
	private String newPassword;
	@NotNull
	private String token;
}
