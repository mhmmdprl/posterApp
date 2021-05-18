package com.esandmongodb.posterapp.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.esandmongodb.posterapp.entity.AuthorLog;

public interface AuthorLogRepository extends MongoRepository<AuthorLog, Long> {
	<S extends AuthorLog> S save(S entity);

	Page<AuthorLog> findAll(Pageable p);

	Page<AuthorLog> findByOperationId(Pageable p, Long id);

	Page<AuthorLog> findByOperationCode(Pageable p, String operationCode);

	Page<AuthorLog> findByRequestPath(Pageable p, String requestPath);
}
