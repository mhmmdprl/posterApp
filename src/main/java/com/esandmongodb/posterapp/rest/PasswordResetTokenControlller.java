package com.esandmongodb.posterapp.rest;

import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.esandmongodb.posterapp.entity.Author;
import com.esandmongodb.posterapp.entity.PasswordResetToken;
import com.esandmongodb.posterapp.model.request.ResetPasswordRequest;
import com.esandmongodb.posterapp.service.AuthorService;
import com.esandmongodb.posterapp.service.DbSequenceService;
import com.esandmongodb.posterapp.service.EmailService;
import com.esandmongodb.posterapp.service.PasswordResetTokenService;

@RestController
@RequestMapping
public class PasswordResetTokenControlller extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(PasswordResetTokenControlller.class);

	@Autowired
	private DbSequenceService dbSequenceService;
	@Autowired
	private AuthorService authorService;

	@Autowired
	private EmailService emailService;
	@Autowired
	private PasswordResetTokenService passwordResetService;
	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;

	@PostMapping("/resetPassword")
	public ResponseEntity<?> resetPassword(final HttpServletRequest request,
			@RequestParam("email") final String email) {

		final Author author = authorService.findByEmail(email);
		PasswordResetToken passwordResetToken = null;
		PasswordResetToken isExistPasswordResetToken = null;
		String token = null;
		String url = "http://" + request.getServerName() + ":" + request.getServerPort() + "/changePassword?token=";
		boolean isOperation = false;

		try {
			if (author != null) {

				isExistPasswordResetToken=this.passwordResetService.findByAuthorId(author.getId());
				if(isExistPasswordResetToken!=null) {
					isExistPasswordResetToken.setDeleted('1');
					this.passwordResetService.save(isExistPasswordResetToken);
				}
				passwordResetToken = new PasswordResetToken();
				
				token = UUID.randomUUID().toString();
				passwordResetToken.setId(this.dbSequenceService.getSeq(PasswordResetToken.seqName));
				passwordResetToken.setToken(token);
				passwordResetToken.setExpiryDate(new Date(System.currentTimeMillis() + PasswordResetToken.EXPIRATION));
				passwordResetToken.setAuthorId(author.getId());
				this.emailService.sendSimpleMessage(author.getEmail(), "ResetPassword",
						"Şifre sıfırlamak için linke tıklayınız : " + url + token);
				this.passwordResetService.save(passwordResetToken);
				isOperation = true;

			} else {
				return operationFail(null, logger, "Invalid email");
			}
		} catch (Exception e) {
			return operationFail(e, logger);
		}
		return operationSuccess(isOperation);
	}

	@PostMapping("/changePassword")
	public ResponseEntity<?> changePassword(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest) {
		Author author = null;
		boolean isOperation = false;
		try {
			PasswordResetToken passwordResetToken = this.passwordResetService
					.findByToken(resetPasswordRequest.getToken());
			if (this.passwordResetService.validatePasswordResetToken(passwordResetToken)) {
				author = this.authorService.findById(passwordResetToken.getAuthorId());
				author.setPassword(this.bcryptPasswordEncoder.encode(resetPasswordRequest.getNewPassword()));
				passwordResetToken.setDeleted('1');
				this.passwordResetService.save(passwordResetToken);
				this.authorService.save(author);
				isOperation = true;
			} else {
				return operationFail(null, logger, "Invalid token");
			}
		} catch (Exception e) {
			operationFail(e, logger);
		}

		return operationSuccess(isOperation);
	}

}
