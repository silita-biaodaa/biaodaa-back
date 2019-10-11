package com.silita.common;



import com.silita.utils.PropertiesUtils;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.util.Hashing;
import redis.clients.util.Sharded;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ckl on 2018/8/31.
 */
public class RedisShardedPool {

    public RedisShardedPool(){
        this.initPool();
    }

    private static ShardedJedisPool pool;
    private static Integer maxTotal = Integer.parseInt(PropertiesUtils.getProperty("redis.max.total")); //最大连接数
    private static Integer maxIdle = Integer.parseInt(PropertiesUtils.getProperty("redis.max.idle"));//在jedispool中最大的idle状态(空闲的)的jedis实例的个数
    private static Integer minIdle = Integer.parseInt(PropertiesUtils.getProperty("redis.min.idle"));////在jedispool中最小的idle状态(空闲的)的jedis实例的个数

    private static Boolean testOnBorrow = Boolean.parseBoolean(PropertiesUtils.getProperty("redis.test.borrow"));//在borrow一个jedis实例的时候，是否要进行验证操作，如果赋值true。则得到的jedis实例肯定是可以用的。
    private static Boolean testOnReturn = Boolean.parseBoolean(PropertiesUtils.getProperty("redis.test.return"));//在return一个jedis实例的时候，是否要进行验证操作，如果赋值true。则放回jedispool的jedis实例肯定是可以用的。

    private static String redis1Ip = PropertiesUtils.getProperty("redis1.ip");
    private static Integer redis1Port = Integer.parseInt(PropertiesUtils.getProperty("redis1.port"));
    private static String redis1password = PropertiesUtils.getProperty("spring.redis1.password");

    private static String redis2Ip = PropertiesUtils.getProperty("redis2.ip");
    private static Integer redis2Port = Integer.parseInt(PropertiesUtils.getProperty("redis2.port"));
    private static String redis2password = PropertiesUtils.getProperty("spring.redis1.password");




    private static void initPool(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);
        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnReturn);

        config.setBlockWhenExhausted(true);//连接耗尽的时候，是否阻塞，false会抛出异常，true阻塞直到超时。默认为true。
        //192.168.88.222:6379
        JedisShardInfo jedis1 = new JedisShardInfo(redis1Ip,redis1Port,1000*2);
        jedis1.setPassword(redis1password); //设置redis 6379密码
        //192.168.88.222:6379
        JedisShardInfo jedis2 = new JedisShardInfo(redis2Ip,redis2Port,1000*2);
        jedis2.setPassword(redis2password);//设置redis 6380密码
        List<JedisShardInfo> jedisShardInfoList = new ArrayList<JedisShardInfo>(2);
        jedisShardInfoList.add(jedis1);
        jedisShardInfoList.add(jedis2);

        //一致性Hash分片算法 基于一个圆圈~~
        pool = new ShardedJedisPool(config,jedisShardInfoList, Hashing.MURMUR_HASH, Sharded.DEFAULT_KEY_TAG_PATTERN);
    }

    static{
        initPool();
    }

    /**
     * 获取ShardedJedis实例
     * @return
     */
    public static ShardedJedis getJedis(){
        return pool.getResource();
    }

    /**
     * 关闭ShardedJedis实例,pool回收
     * @param jedis
     */
    public static void returnBrokenResource(ShardedJedis jedis){
        jedis.close();
    }

    /**
     * 关闭ShardedJedis实例,pool回收
     * @param jedis
     */
    public static void returnResource(ShardedJedis jedis){
        jedis.close();
    }
}
