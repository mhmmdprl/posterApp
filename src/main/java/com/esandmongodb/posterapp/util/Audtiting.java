package com.esandmongodb.posterapp.util;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.esandmongodb.posterapp.service.AuthorService;

@Component("securityAuditorAware")
public class Audtiting implements AuditorAware<Long> {
	@Autowired
	private AuthorService authorService;

	@Override
	public Optional<Long> getCurrentAuditor() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		if (username.equals("anonymousUser")) {
			return Optional.ofNullable(0l);
		}
		else
		return Optional.ofNullable(this.authorService.findByUsername(username).getId());
	}

}
