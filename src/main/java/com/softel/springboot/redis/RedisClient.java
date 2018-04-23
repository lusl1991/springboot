package com.softel.springboot.redis;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;
import com.softel.springboot.util.JSONUtil;

@Component
public class RedisClient {
	
	@Autowired  
    private RedisTemplate<String, ?> redisTemplate;
	
	/**
	 * 存值
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean set(final String key, final String value) {  
        boolean result = (Boolean)redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override  
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
                connection.set(serializer.serialize(key), serializer.serialize(value));  
                return true;  
            }  
        });
        return result;  
    }  
	
	/**
	 * 取值
	 * @param key
	 * @return
	 */
	public String get(final String key){  
        String result = (String) redisTemplate.execute(new RedisCallback<String>() {  
            @Override  
            public String doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
                byte[] value =  connection.get(serializer.serialize(key));  
                return serializer.deserialize(value);  
            }  
        });  
        return result;  
    }
	
	/**
	 * 删除
	 * @param key
	 */
	public void delete(String key){
		redisTemplate.delete(key);
	}
	
	/**
	 * 设置过期时间
	 * @param key
	 * @param expire
	 * @return
	 */
	public boolean expire(final String key, long expire) {  
        return redisTemplate.expire(key, expire, TimeUnit.SECONDS);  
    }  
	
	/**
	 * 存list
	 * @param key
	 * @param list
	 * @return
	 */
    public <T> boolean setList(String key, List<T> list) {  
        String value = JSONUtil.toJson(list);  
        return set(key,value);  
    }  
  
    /**
     * 取list
     * @param key
     * @param clz
     * @return
     */
    public <T> List<T> getList(String key,Class<T> clz) {  
        String json = get(key);  
        if(json!=null){  
            List<T> list = JSONUtil.toList(json, clz);  
            return list;  
        }  
        return null;  
    }  
    
	public long lpush(final String key, Object obj) {  
        final String value = JSONUtil.toJson(obj);  
        long result = (long) redisTemplate.execute(new RedisCallback<Long>() {  
            @Override  
            public Long doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
                long count = connection.lPush(serializer.serialize(key), serializer.serialize(value));  
                return count;  
            }  
        });  
        return result;  
    }  
	
	public long rpush(final String key, Object obj) {  
        final String value = JSONUtil.toJson(obj);  
        long result = (long) redisTemplate.execute(new RedisCallback<Long>() {  
            @Override  
            public Long doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
                long count = connection.rPush(serializer.serialize(key), serializer.serialize(value));  
                return count;  
            }  
        });  
        return result;  
    }  
	
	public String lpop(final String key) {  
        String result = (String) redisTemplate.execute(new RedisCallback<String>() {  
            @Override  
            public String doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
                byte[] res =  connection.lPop(serializer.serialize(key));  
                return serializer.deserialize(res);  
            }  
        });  
        return result;  
    } 
    
}
