package com.esandmongodb.posterapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.esandmongodb.posterapp.entity.AuthorLog;

public interface AuthorLogService {

	Page<AuthorLog> findAll(Pageable p);

	Page<AuthorLog> findByOperationId(Pageable p, Long id);

	Page<AuthorLog> findByOperationCode(Pageable p, String operationCode);

	Page<AuthorLog> findByRequestPath(Pageable p, String requestPath);

	void save(AuthorLog authorLog);
}
