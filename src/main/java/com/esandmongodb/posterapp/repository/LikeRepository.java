package com.esandmongodb.posterapp.repository;


import org.springframework.data.mongodb.repository.ExistsQuery;
import org.springframework.data.mongodb.repository.Query;

import com.esandmongodb.posterapp.entity.Like;
import com.esandmongodb.posterapp.entity.Post;

public interface LikeRepository extends BaseRepository<Like>{

	@Query(value = "{post: ?0}", count = true)
	public long countByPost(Post post);
	@ExistsQuery("{'$and':[ {'authorId':?0}, {'postUuid':?1} ] }")
	public boolean existsByAuthorIdAndPostUuid(Long authorId, String postUuid);
	@Query("{'$and':[ {'authorId':?0}, {'postUuid':?1} ] }")
	public Like findByAuthorIdAndPostUuid(Long authorId, String postUuid);
	
//	@Query("select case when count(c)> 0 then true else false end from Like l where l.authorid=:authorId and l.postuuid=:postuuid")
//	boolean existsByAuthorIdAndPostUuid(@Param("authorId") Long authorId,@Param("postUuid") String postUuid);

}
