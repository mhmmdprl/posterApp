package com.esandmongodb.posterapp.repository;

import org.springframework.data.mongodb.repository.ExistsQuery;

import com.esandmongodb.posterapp.entity.RoleOperation;

public interface RoleOperationRepository extends BaseRepository<RoleOperation> {
	@ExistsQuery("{'$and':[ {'roleId':?0}, {'operationId':?1}, {'deleted':'0'}, {'banStatus':'0'} ] }")
	boolean existsByRoleIdAndOperationId(Long roleId, Long operationId);
}
