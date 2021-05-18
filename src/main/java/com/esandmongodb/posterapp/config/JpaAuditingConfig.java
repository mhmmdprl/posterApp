//package com.esandmongodb.posterapp.config;
//
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.domain.AuditorAware;
//import org.springframework.security.core.context.SecurityContextHolder;
//
//import com.esandmongodb.posterapp.service.AuthorService;
//
//@Configuration
//public class JpaAuditingConfig {
//	@Autowired
//	private AuthorService authorService;
//
//	@Bean
//	public AuditorAware<Long> auditorProvider() {
//
//		/*
//		 * if you are using spring security, you can get the currently logged username
//		 * with following code segment.
//		 * SecurityContextHolder.getContext().getAuthentication().getName()
//		 */
//		
//		
//		return () -> Optional.ofNullable(this.authorService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getId());
//	}
//}
