package com.esandmongodb.posterapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.esandmongodb.posterapp.service.EmailService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Override
	public void sendSimpleMessage(String to, String subject, String text) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom("piralmuhammed@gmail.com");
			message.setTo(to);
			message.setSubject(subject);
			message.setText(text);

			javaMailSender.send(message);
		} catch (MailException e) {
			log.error("Email gönderilemedi : {}", e);
		}

	}

}
