package com.hmdp.redisTest;


import redis.clients.jedis.*;



public class JedisConnectionFactory {

    private static final JedisPool jedisPool;

    static {
        //配置连接池
        JedisPoolConfig config = new JedisPoolConfig();
        //最大连接数
        config.setMaxTotal(8);
        //最大空闲连接数
        config.setMaxIdle(8);
        //最大等待时间
        config.setMaxWaitMillis(1000);
        //最小空闲连接数
        config.setMinIdle(0);
        // 创建连接池对象，参数：连接池配置、服务端ip、服务端端口、超时时间、密码
        jedisPool = new JedisPool(config, "localhost", 6379, 1000, "1234");
    }

    public static Jedis getJedis() {
        return jedisPool.getResource();
    }
}
