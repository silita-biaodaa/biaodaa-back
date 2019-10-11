package com.silita.utils;

import com.silita.common.ObjectTranscoder;
import com.silita.common.RedisShardedPool;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.ShardedJedis;

/**
 * Sharded Redis工具类
 * Created by ckl on 2018/8/31.
 */
@Slf4j
public class RedisShardedPoolUtil {

    /**
     * 获取
     * @param key
     * @return
     */
    public static Object get(String key){
        ShardedJedis jedis = null;
        Object result = null;
        try {
            jedis = RedisShardedPool.getJedis();
            byte[] in = jedis.get(key.getBytes());
             result = ObjectTranscoder.deserialize(in);
        } catch (Exception e) {
            log.error("get key:{} error",key,e);
            RedisShardedPool.returnBrokenResource(jedis);
            return result;
        }finally {
            RedisShardedPool.returnResource(jedis);
        }
        return result;
    }

    /**
     * 设置值
     * @param key
     * @param value
     * @return
     */
    public static void set(String key,Object value){
        ShardedJedis jedis = null;
        try {
            jedis = RedisShardedPool.getJedis();
            byte[] serialize = ObjectTranscoder.serialize(value);
            jedis.set(key.getBytes(),serialize);;
        } catch (Exception e) {
            log.error(key,value,e);
            RedisShardedPool.returnBrokenResource(jedis);
        }finally {
            RedisShardedPool.returnResource(jedis);
        }
    }

    /**
     * 获取
     * @param key
     * @return
     */
    public static Boolean keyExist(String key){
        ShardedJedis jedis = null;
        Object result = null;
        try {
            jedis = RedisShardedPool.getJedis();
           return jedis.exists(key.getBytes());
        } catch (Exception e) {
            log.error("get key:{} error",key,e);
            RedisShardedPool.returnBrokenResource(jedis);
            return false;
        }finally {
            RedisShardedPool.returnResource(jedis);
        }
    }


    /**
     * 设置key的有效期，单位是秒
     * @param key
     * @param exTime
     * @return
     */
    public static Long expire(String key,int exTime){
        ShardedJedis jedis = null;
        Long result = null;
        try {
            jedis = RedisShardedPool.getJedis();
            result = jedis.expire(key,exTime);
        } catch (Exception e) {
            log.error("expire key:{} error",key,e);
            RedisShardedPool.returnBrokenResource(jedis);
            return result;
        }finally {
            RedisShardedPool.returnBrokenResource(jedis);
        }
        return result;
    }


    /**
     * 删除key
     * @param key
     * @return
     */
    public static Long del(String key){
        ShardedJedis jedis = null;
        Long result = null;
        try {
            jedis = RedisShardedPool.getJedis();
            result = jedis.del(key);
        } catch (Exception e) {
            log.error("del key:{} error",key,e);
            RedisShardedPool.returnBrokenResource(jedis);
            return result;
        }finally {
            RedisShardedPool.returnResource(jedis);
        }
        return result;
    }


    /**
     * 设置key value exTime的单位是秒
     * @param key
     * @param value
     * @param exTime
     * @return
     */
    public static String setEx(String key,Object value,int exTime){
        ShardedJedis jedis = null;
        String result = null;
        try {
            jedis = RedisShardedPool.getJedis();
            byte[] serialize = ObjectTranscoder.serialize(value);
            jedis.setex(key.getBytes(),exTime,serialize);
        } catch (Exception e) {
            log.error(key,value,e);
            RedisShardedPool.returnBrokenResource(jedis);
            return result;
        }finally {
            RedisShardedPool.returnResource(jedis);
        }
        return result;
    }
}
