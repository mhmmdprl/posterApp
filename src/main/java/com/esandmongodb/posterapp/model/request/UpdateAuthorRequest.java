package com.esandmongodb.posterapp.model.request;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateAuthorRequest {

	@NotNull
	private String name;

	private String aboutMe;
	
	@NotNull
	@Past
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate birtOfDate;
}
