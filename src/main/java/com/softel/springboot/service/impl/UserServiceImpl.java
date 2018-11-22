package com.softel.springboot.service.impl;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.softel.springboot.entity.UserEntity;
import com.softel.springboot.repository.UserRepository;
import com.softel.springboot.service.UserService;

@Service
@CacheConfig(cacheNames = "user")
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Cacheable
	@Override
	public List<UserEntity> findByExample(UserEntity userEntity){
		List<UserEntity> result = userRepository.findAll(new Specification<UserEntity>() {
			@Override
	        public Predicate toPredicate(Root<UserEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	            List<Predicate> list = new ArrayList<Predicate>();
	            
	            if (!"".equals(userEntity.getId()) && null != userEntity.getId()) {
	                list.add(cb.equal(root.get("id").as(Integer.class), userEntity.getId()));
	            }

	            if (!"".equals(userEntity.getSex()) && null != userEntity.getSex()) {
	            	list.add(cb.equal(root.get("sex").as(Integer.class), userEntity.getSex()));
	            }
	            
	            if (StringUtils.isNotEmpty(userEntity.getUsername())) {
	                list.add(cb.like(root.get("name").as(String.class), "%" + userEntity.getUsername() + "%"));
	            }
	            
	            Predicate[] p = new Predicate[list.size()];
	            return cb.and(list.toArray(p));
	        }
		});
		return result;
	}
	
	@Override
	public Page<UserEntity> cutPage(UserEntity userEntity){
		Sort.Direction direction = Sort.Direction.ASC.toString().equalsIgnoreCase(userEntity.getSord())?Sort.Direction.ASC : Sort.Direction.DESC;
		Sort sort = new Sort(direction, userEntity.getSidx());
		PageRequest pageRequest = new PageRequest(userEntity.getPage() - 1, userEntity.getSize(), sort);
		return userRepository.findAll(pageRequest);
	}

}