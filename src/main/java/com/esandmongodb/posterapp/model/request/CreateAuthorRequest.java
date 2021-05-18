package com.esandmongodb.posterapp.model.request;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateAuthorRequest {

	@NotNull
	private String name;

	private String aboutMe;

	@NotNull
	private String username;

	@NotNull
	private String password;
	
	@NotNull
	@Past
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate birtOfDate;
	
	@Email
	@NotNull
	private String email;
	
	private List<Long> roleIds;
	
}
