package com.esandmongodb.posterapp.service;

import com.esandmongodb.posterapp.entity.RoleOperation;

public interface RoleOperationService extends BaseService<RoleOperation> {

	boolean existsByRoleIdAndOperationId(Long roleId, Long operationId);

}
