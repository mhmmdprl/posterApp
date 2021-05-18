package com.esandmongodb.posterapp.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esandmongodb.posterapp.entity.Role;
import com.esandmongodb.posterapp.repository.RoleRepository;
import com.esandmongodb.posterapp.service.RoleService;
@Service
public class RoleServiceImp implements RoleService{

	@Autowired
	private RoleRepository roleRepository;
	@Override
	public Role findById(Long id) {
		return this.roleRepository.findById(id).get();
	}
	@Override
	public Role save(Role collection) {
		return this.roleRepository.save(collection);
	}
	@Override
	public boolean delete(String uuid, Long updatedBy) {
		return false;
	}
	@Override
	public boolean existsByUuid(String uuid) {
		return this.roleRepository.existsByUuid(uuid);
	}
	@Override
	public Role findByUuid(String uuid) {
		return this.roleRepository.findByUuid(uuid);
	}
	@Override
	public List<Role> findAll() {
		return this.roleRepository.findAll();
	}

}
