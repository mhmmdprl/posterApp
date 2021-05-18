package com.esandmongodb.posterapp.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.esandmongodb.posterapp.entity.AuthorLog;
import com.esandmongodb.posterapp.repository.AuthorLogRepository;
import com.esandmongodb.posterapp.service.AuthorLogService;
@Service
public class AuthorLogServiceImpl implements AuthorLogService {


	@Autowired
	private AuthorLogRepository authorLogRepository;
	@Override
	public Page<AuthorLog> findAll(Pageable p) {
		return this.authorLogRepository.findAll(p);
	}

	@Override
	public Page<AuthorLog> findByOperationId(Pageable p, Long id) {
		return this.authorLogRepository.findByOperationId(p, id);
	}

	@Override
	public Page<AuthorLog> findByOperationCode(Pageable p, String operationCode) {
		return this.authorLogRepository.findByOperationCode(p, operationCode);
	}

	@Override
	public Page<AuthorLog> findByRequestPath(Pageable p, String requestPath) {
		return this.authorLogRepository.findByRequestPath(p, requestPath);
	}

	@Override
	public void save(AuthorLog authorLog) {
	this.authorLogRepository.save(authorLog);
		
	}

}
