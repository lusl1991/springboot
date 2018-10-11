package com.softel.springboot.controller;

import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.softel.springboot.redis.RedisUtil;

@RestController
public class RedisController {

	@Resource
	private RedisUtil redisUtil;
	
	/**
	 * set
	 * @return
	 */
	@RequestMapping("/set")
    public void set(String key, String value) {
		redisUtil.set(key, value);
    }
	
	/**
	 * del
	 * @return
	 */
	@RequestMapping("/del")
    public void update(String key) {
		redisUtil.del(key);
    }
	
	/**
	 * expire
	 * @return
	 */
	@RequestMapping("/expire")
    public void expire(String key) {
		redisUtil.expire(key, 30);
    }
	
}
