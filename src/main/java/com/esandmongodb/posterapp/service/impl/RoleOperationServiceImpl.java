package com.esandmongodb.posterapp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esandmongodb.posterapp.entity.RoleOperation;
import com.esandmongodb.posterapp.repository.RoleOperationRepository;
import com.esandmongodb.posterapp.service.RoleOperationService;
@Service
public class RoleOperationServiceImpl implements RoleOperationService {

	@Autowired
	private RoleOperationRepository roleOperationRepository;
	@Override
	public RoleOperation save(RoleOperation collection) {
		return this.roleOperationRepository.save(collection);
	}

	@Override
	public boolean delete(String uuid, Long updatedBy) {
		return false;
	}

	@Override
	public boolean existsByUuid(String uuid) {
		return this.roleOperationRepository.existsByUuid(null);
	}

	@Override
	public RoleOperation findByUuid(String uuid) {
		return this.roleOperationRepository.findByUuid(uuid);
	}

	@Override
	public List<RoleOperation> findAll() {
		return this.roleOperationRepository.findAll();
	}

	@Override
	public boolean existsByRoleIdAndOperationId(Long roleId, Long operationId) {
		return this.roleOperationRepository.existsByRoleIdAndOperationId(roleId, operationId);
	}

}
