package com.softel.springboot.repository;

import com.softel.springboot.base.BaseRepository;
import com.softel.springboot.entity.UserEntity;

public interface UserRepository extends BaseRepository<UserEntity, Integer> {

	UserEntity findByUsername(String username);
	
}
