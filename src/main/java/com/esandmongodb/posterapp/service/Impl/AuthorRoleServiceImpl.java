package com.esandmongodb.posterapp.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esandmongodb.posterapp.entity.AuthorRole;
import com.esandmongodb.posterapp.repository.AuthorRoleRepository;
import com.esandmongodb.posterapp.service.AuthorRoleService;
@Service
public class AuthorRoleServiceImpl implements AuthorRoleService{

	@Autowired
	private AuthorRoleRepository authorRoleRepository;
	@Override
	public AuthorRole save(AuthorRole collection) {
		return this.authorRoleRepository.save(collection);
	}

	@Override
	public boolean delete(String uuid, Long updatedBy) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existsByUuid(String uuid) {
		return this.authorRoleRepository.existsByUuid(uuid);
	}

	@Override
	public AuthorRole findByUuid(String uuid) {
		return this.authorRoleRepository.findByUuid(uuid);
	}

	@Override
	public List<AuthorRole> findAll() {
		return this.authorRoleRepository.findAll();
	}

}
