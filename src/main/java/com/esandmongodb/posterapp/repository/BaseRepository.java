package com.esandmongodb.posterapp.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;


@NoRepositoryBean
public interface BaseRepository<T> extends MongoRepository<T, Long> {

	boolean existsByUuid(String uuid);

	boolean existsById(Long id);

	
	T findByUuid(String uuid);

	
	/*
	 * Aslında sadece 'delected' ile ban ve ya hesap silinme kontrol edilebilir
	 * fakat ilerde silinmemiş ama banlanmış kullancıları diğer kullanıcılara
	 * göstermek istenebilir. Ya da aynı şekilde postlar için bu içerik
	 * yasaklanmıştır gibi bir senaryo.
	 * 
	 */
	@Query("{'$and':[ {'deleted':'0'}, {'banStatus':'0'} ] }")
	List<T> findAllActive();

	@Query("{'$or':[ {'deleted':'1'}, {'banStatus':'1'} ] }")
	List<T> findAllPassive();
}
