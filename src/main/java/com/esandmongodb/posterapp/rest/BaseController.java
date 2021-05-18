package com.esandmongodb.posterapp.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.esandmongodb.posterapp.filter.TokenProvider;
import com.esandmongodb.posterapp.model.GenericResponse;
import org.slf4j.Logger;



public class BaseController {

	
	
	@Autowired
	public TokenProvider tokenProvider;
	
	public  ResponseEntity<?> operationSuccess(Object data){
		GenericResponse<Object> response = new GenericResponse<Object>();
		response.setData(data);
		response.setSuccess(true);
		response.setResultCode(HttpStatus.OK.value());
		return new ResponseEntity<>(response, HttpStatus.OK);
		
	}
	
	public  ResponseEntity<?> operationFail(Exception e,Logger logger){
		GenericResponse<Object> response = new GenericResponse<Object>();
		logger.error("Hata :"+e.getMessage());
		response.setData(null);
		response.setSuccess(false);
		response.setMessage(e.getMessage());
		response.setResultCode(HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	public  ResponseEntity<?> operationFail(Exception e,Logger logger,String message){
		GenericResponse<Object> response = new GenericResponse<Object>();
		logger.error("Hata :"+message);
		response.setData(null);
		response.setSuccess(false);
		response.setMessage(message);
		response.setResultCode(HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
}
