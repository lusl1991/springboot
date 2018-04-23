package com.softel.springboot.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import lombok.Data;

@Data
@Component
public class RedisProperties {
	
	@Value(value = "${spring.redis.host}")
	private String host;
	
	@Value(value = "${spring.redis.port}")
	private int port;
	
	@Value(value = "${spring.redis.password}")
	private String password;
	
	@Value(value = "${spring.redis.database}")
	private int database;
	
	@Value(value = "${spring.redis.pool.max-wait}")
	private int timeout;
	
	@Value(value = "${spring.redis.pool.max-idle}")
	private int maxIdle;
	
	@Value(value = "${spring.redis.pool.min-idle}")
	private int minIdle;
	
}
