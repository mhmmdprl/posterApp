package com.esandmongodb.posterapp.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document(collection = "password_reset_token")
@Getter
@Setter
public class PasswordResetToken extends BaseCollection{
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Transient
    public static final int EXPIRATION = 60 * 1000 * 2;
 
	@Transient
	public static final String seqName = "password_reset_token";
    @Id
    private Long id;
 
    private String token;
 
  
    private Long authorId;
 
    private Date expiryDate;
}