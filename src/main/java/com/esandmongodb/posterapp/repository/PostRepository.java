package com.esandmongodb.posterapp.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.Query;
//import com.esandmongodb.posterapp.entity.Author;
import com.esandmongodb.posterapp.entity.Like;
import com.esandmongodb.posterapp.entity.Post;

public interface PostRepository extends BaseRepository<Post> {
//	@Query("{'$and':[ {'deleted':'0'}, {'banStatus':'0'} ] }")
//	List<Post> findByAuthor(Author author);
	
	@Query("{'$and':[ {'deleted':'0'}, {'banStatus':'0'} ] }")
	List<Like> findLikesByUuid(String uuid);
	@Query("{'$and':[ {'deleted':'0'}, {'banStatus':'0'} ] }")
	List<Post> findByAuthorId(Long authorId);
	

}
