package com.esandmongodb.posterapp.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class GenericResponse<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected T data;
	protected String message;
	protected Integer resultCode;
	protected boolean isSuccess;
}
