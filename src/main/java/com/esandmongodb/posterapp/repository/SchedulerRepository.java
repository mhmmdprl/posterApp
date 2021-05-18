package com.esandmongodb.posterapp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.esandmongodb.posterapp.entity.TimerInfo;

public interface SchedulerRepository extends MongoRepository<TimerInfo, Long> {

}
