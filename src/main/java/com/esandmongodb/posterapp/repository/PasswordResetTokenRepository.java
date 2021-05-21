package com.esandmongodb.posterapp.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Service;

import com.esandmongodb.posterapp.entity.PasswordResetToken;
@Service
public interface PasswordResetTokenRepository extends MongoRepository<PasswordResetToken,Long>{
	
	@Query("{'$and':[ {'deleted':'0'}, {'banStatus':'0'},{'token': ?0 } ] }")
	PasswordResetToken findByToken(String token);
	@Query("{'$and':[ {'deleted':'0'},{'authorId': ?0 } ] }")
	PasswordResetToken findByAuthorId(Long id);

}
