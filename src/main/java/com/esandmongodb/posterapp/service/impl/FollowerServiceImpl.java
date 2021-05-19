package com.esandmongodb.posterapp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esandmongodb.posterapp.entity.Follower;
import com.esandmongodb.posterapp.repository.FollowerRepository;
import com.esandmongodb.posterapp.service.FollowerService;

@Service
public class FollowerServiceImpl implements FollowerService {

	@Autowired
	private FollowerRepository followerRepository;

	@Override
	public Follower save(Follower collection) {
		return this.followerRepository.save(collection);
	}

	@Override
	public boolean delete(String uuid, Long updatedBy) {
		return false;
	}

	@Override
	public boolean existsByUuid(String uuid) {
		// TODO Auto-generated method stub
		return this.followerRepository.existsByUuid(uuid);
	}

	@Override
	public Follower findByUuid(String uuid) {
		// TODO Auto-generated method stub
		return this.findByUuid(uuid);
	}

	@Override
	public List<Follower> findAll() {
		// TODO Auto-generated method stub
		return this.findAll();
	}

	@Override
	public boolean existsByFromAuthorUuidAndToAuthorId(Long userIdFromRequest, String uuid) {
		return this.followerRepository.existsByFromAuthorUuidAndToAuthorId(userIdFromRequest, uuid);

	}

	@Override
	public Follower findByFromAuthorUuidAndToAuthorId(Long userIdFromRequest, String uuid) {
		return this.followerRepository.findByFromAuthorUuidAndToAuthorId(userIdFromRequest, uuid);

	}

	@Override
	public void hardDelete(Follower follower) {
		this.followerRepository.delete(follower);
		
	}

}
