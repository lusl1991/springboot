package com.softel.springboot.controller;

import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.softel.springboot.redis.RedisClient;

@RestController
public class RedisController {

	@Resource
	private RedisClient redisClient;
	
	/**
	 * set
	 * @return
	 */
	@RequestMapping("/set")
    public void set(String key, String value) {
        redisClient.set(key, value);
    }
	
	/**
	 * del
	 * @return
	 */
	@RequestMapping("/del")
    public void update(String key) {
        redisClient.delete(key);
    }
	
	/**
	 * expire
	 * @return
	 */
	@RequestMapping("/expire")
    public void expire(String key) {
        redisClient.expire(key, 30);
    }
	
}
