package com.esandmongodb.posterapp.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esandmongodb.posterapp.entity.Following;
import com.esandmongodb.posterapp.repository.FollowingRepository;
import com.esandmongodb.posterapp.service.FollowingService;

@Service
public class FollowingServiceImpl implements FollowingService{

	@Autowired
	private FollowingRepository followingRepository;
	@Override
	public Following save(Following collection) {
		return this.followingRepository.save(collection);
	}

	@Override
	public boolean delete(String uuid, Long updatedBy) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existsByUuid(String uuid) {
		return this.followingRepository.existsByUuid(uuid);
	}

	@Override
	public Following findByUuid(String uuid) {
		return this.followingRepository.findByUuid(uuid);
	}

	@Override
	public List<Following> findAll() {
		return this.followingRepository.findAll();
	}

	@Override
	public void hardDelete(Following following) {
		this.followingRepository.delete(following);
		
	}

	@Override
	public Following findByFromAuthorIdAndToAuthorUuid(Long userIdFromRequest, String uuid) {
		return this.followingRepository.findByFromAuthorIdAndToAuthorUuid(userIdFromRequest, uuid);
	}

}
