package com.hmdp.redisTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.Jedis;

import java.util.Map;

public class test1 {
    private Jedis jedis;
    @BeforeEach
    void setup(){
        //建立连接
        //jedis = new Jedis("192.168.150.101", 6379);
        jedis = JedisConnectionFactory.getJedis();
        //设置密码
        jedis.auth("1234");
        //选择库
        jedis.select(0);
    }


    //测试
    @Test
    void testString() {
        // 存入数据
        String result = jedis.set("name", "王礼涛");
        System.out.println(result);
        //获取权限
        String name = jedis.get("name");
        System.out.println(name);
    }

    //测试hash数据
    @Test
    void testHash() {
        // 存入数据
        jedis.hset("user:1", "name", "王礼涛");
        jedis.hset("user:1", "age", "23");
        //获取数据
        Map<String, String> map = jedis.hgetAll("user:1");
        System.out.println(map);
    }



    //释放资源
    @AfterEach
    void tearDown(){
        if (jedis != null) {
            jedis.close();
        }
    }

}
