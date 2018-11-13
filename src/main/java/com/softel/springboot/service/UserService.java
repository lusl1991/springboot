package com.softel.springboot.service;

import java.util.List;
import org.springframework.data.domain.Page;
import com.softel.springboot.entity.UserEntity;

public interface UserService {
	
	public List<UserEntity> findByExample(UserEntity userEntity);
	
	public Page<UserEntity> cutPage(UserEntity userEntity);
	
}