package com.esandmongodb.posterapp.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;

import com.esandmongodb.posterapp.entity.Author;

public  interface AuthorService  extends BaseService<Author>  {

	public Author findByUsername(String username);

	public List<Author> findAllActive();
	
	public List<Author> findAllPassive();

	public boolean existsByUsername(String username);
	

	
	public boolean ban(String uuid,Long updatedBy);
	
	public Author findById(Long id);
	
	boolean existsById(Long id);

	public List<Author> findSpecific(Query query, Pageable sortedByName);



}
