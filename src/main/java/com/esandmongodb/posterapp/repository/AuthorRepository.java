package com.esandmongodb.posterapp.repository;

import org.springframework.data.mongodb.repository.Query;

import com.esandmongodb.posterapp.entity.Author;

public interface AuthorRepository extends BaseRepository<Author> {
	@Query("{ 'deleted' : '0' , 'banStatus' : '0' , 'username' :?0 }")
	Author findByUsername(String username);
	
	boolean existsByUsername(String username);

}
