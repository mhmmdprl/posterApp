package com.esandmongodb.posterapp.service;

import com.esandmongodb.posterapp.entity.Role;

public interface RoleService extends BaseService<Role>{

	Role findById(Long id);
	
}
