package com.esandmongodb.posterapp.service;

import com.esandmongodb.posterapp.entity.PasswordResetToken;

public interface PasswordResetTokenService {

	boolean save(PasswordResetToken passwordResetToken);

	boolean validatePasswordResetToken(PasswordResetToken passwordResetToken);
	
	PasswordResetToken findByToken(String token);

	PasswordResetToken findByAuthorId(Long id);
}
