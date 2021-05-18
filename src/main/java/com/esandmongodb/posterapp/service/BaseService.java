package com.esandmongodb.posterapp.service;

import java.util.List;

public abstract interface BaseService<T> {

	T save(T collection);

	public boolean delete(String uuid, Long updatedBy);

	boolean existsByUuid(String uuid);

	T findByUuid(String uuid);

	public List<T> findAll();
	

}
