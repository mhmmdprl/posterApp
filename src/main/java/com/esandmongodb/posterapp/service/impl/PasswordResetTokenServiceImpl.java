package com.esandmongodb.posterapp.service.impl;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esandmongodb.posterapp.entity.PasswordResetToken;
import com.esandmongodb.posterapp.repository.PasswordResetTokenRepository;
import com.esandmongodb.posterapp.service.PasswordResetTokenService;
@Service
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {

	@Autowired
	private PasswordResetTokenRepository passwordResetTokenRepository;

	@Override
	public boolean save(PasswordResetToken passwordResetToken) {
	boolean isOperation=false;
		try {
			this.passwordResetTokenRepository.save(passwordResetToken);
			isOperation=true;
		} catch (Exception e) {
			throw e;
		}

		return isOperation;
	}

	@Override
	public boolean validatePasswordResetToken(PasswordResetToken passwordResetToken) {
		if(passwordResetToken!=null) {
		
			  Calendar cal = Calendar.getInstance();
			  return passwordResetToken.getExpiryDate().before(cal.getTime());
		}
		return false;
	}

	@Override
	public PasswordResetToken findByToken(String token) {
		return this.passwordResetTokenRepository.findByToken(token);
	}

	@Override
	public PasswordResetToken findByAuthorId(Long id) {
		return this.passwordResetTokenRepository.findByAuthorId(id);
	}

}
