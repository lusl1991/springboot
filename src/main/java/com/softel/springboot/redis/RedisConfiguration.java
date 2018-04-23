package com.softel.springboot.redis;

import java.lang.reflect.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableCaching
public class RedisConfiguration extends CachingConfigurerSupport {
	
	private static Logger logger = LoggerFactory.getLogger(RedisConfiguration.class);
	
	@Autowired
	private RedisProperties redisProperties;
	
    /**
     * redis连接的基础设置
     * @Description:
     * @return
     * @throws Exception 
     */
    @Bean
    public JedisConnectionFactory getConnectionFactory() throws Exception {
    	JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
    	jedisConnectionFactory.setHostName(redisProperties.getHost());
    	jedisConnectionFactory.setPort(redisProperties.getPort());
    	jedisConnectionFactory.setPassword(redisProperties.getPassword());
    	//存储的库
    	jedisConnectionFactory.setDatabase(redisProperties.getDatabase());
    	//设置连接超时时间
    	jedisConnectionFactory.setTimeout(redisProperties.getTimeout());
    	jedisConnectionFactory.setUsePool(true);
    	jedisConnectionFactory.setPoolConfig(initPoolConfig());
    	jedisConnectionFactory.afterPropertiesSet();
    	try {
    		jedisConnectionFactory.getConnection();
		} catch (Exception e) {
			logger.error("redis服务器[{}:{}]连接异常！", redisProperties.getHost(), redisProperties.getPort());
			throw new Exception();
		}
    	logger.info("redis服务器[{}:{}]连接成功！", redisProperties.getHost(), redisProperties.getPort());
    	return jedisConnectionFactory;
    }
    
    @Bean
    public JedisPoolConfig initPoolConfig(){
    	JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(redisProperties.getMaxIdle());
        jedisPoolConfig.setMinIdle(redisProperties.getMinIdle());
        return jedisPoolConfig;
    }
    
	/**
	 * 采用RedisCacheManager作为缓存处理器
	 * @param redisTemplate
	 * @return
	 */
	@Bean
	public CacheManager cacheManager(RedisTemplate<?, ?> redisTemplate){
		return new RedisCacheManager(redisTemplate);
	}

	/**
	 * 自定义生成key规则
	 */
	@Override
	public KeyGenerator keyGenerator() {
		return new KeyGenerator() {
			@Override
			public Object generate(Object target, Method method, Object... params) {
				StringBuffer stringBuffer = new StringBuffer();
				stringBuffer.append(target.getClass().getName());
				stringBuffer.append(".").append(method.getName());
				for(Object object : params){
					stringBuffer.append(object.toString());
				}
				logger.info("调用redis缓存：key=[{}]", stringBuffer.toString());
				return stringBuffer.toString();
			}
		};
	}
	
	/**
	 * redis模板，存储关键字是字符串，值是Jdk序列化
	 * @Description:
	 * @param factory
	 * @return
	 */
	@Bean
	public RedisTemplate<?,?> redisTemplate(RedisConnectionFactory factory) {
		RedisTemplate<?,?> redisTemplate = new RedisTemplate<>();
	    redisTemplate.setConnectionFactory(factory);
	      
	    //key序列化方式;但是如果方法上有Long等非String类型的话，会报类型转换错误；
	    RedisSerializer<String> redisSerializer = new StringRedisSerializer();//Long类型不可以会出现异常信息;
	    redisTemplate.setKeySerializer(redisSerializer);
	    redisTemplate.setHashKeySerializer(redisSerializer);

	    //JdkSerializationRedisSerializer序列化方式;
	    JdkSerializationRedisSerializer jdkRedisSerializer=new JdkSerializationRedisSerializer();
	    redisTemplate.setValueSerializer(jdkRedisSerializer);
	    redisTemplate.setHashValueSerializer(jdkRedisSerializer);
	    redisTemplate.afterPropertiesSet();
	      
	    return redisTemplate; 
	}


	/**
	 * redis数据操作异常处理
	 * 这里的处理：在日志中打印出错误信息，但是放行
	 * 保证redis服务器出现连接等问题的时候不影响程序的正常运行，使得能够出问题时不用缓存
	 * @return
	 */
	@Bean
	@Override
	public CacheErrorHandler errorHandler() {
	    CacheErrorHandler cacheErrorHandler = new CacheErrorHandler() {
	        @Override
	        public void handleCacheGetError(RuntimeException e, Cache cache, Object key) {
	            logger.error("redis异常：key=[{}]",key,e);
	        }

	        @Override
	        public void handleCachePutError(RuntimeException e, Cache cache, Object key, Object value) {
	            logger.error("redis异常：key=[{}]",key,e);
	        }

	        @Override
	        public void handleCacheEvictError(RuntimeException e, Cache cache, Object key)    {
	            logger.error("redis异常：key=[{}]",key,e);
	        }

	        @Override
	        public void handleCacheClearError(RuntimeException e, Cache cache) {
	            logger.error("redis异常：",e);
	        }
	    };
	    return cacheErrorHandler;
	}
	
}
