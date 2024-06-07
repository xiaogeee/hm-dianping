package com.hmdp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hmdp.redisTest.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
class HmDianPingApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void testString() {
        redisTemplate.opsForValue().set("name", "王礼涛");
        String name = (String) redisTemplate.opsForValue().get("name");
        System.out.println(name);
    }

    @Autowired
    StringRedisTemplate stringRedisTemplate;
    //JSON序列化工具
    private static final ObjectMapper mapper = new ObjectMapper();
    //测试
    @Test
    void testSaveUser() throws JsonProcessingException {
        //创建对象
        User user = new User("王礼涛", 23);
        //手动序列化
        String json = mapper.writeValueAsString(user);
        //存入数据
        stringRedisTemplate.opsForValue().set("user:200", json);
        //获取数据
        String userJson = stringRedisTemplate.opsForValue().get("user:200");
        //反序列化
        User user1 = mapper.readValue(userJson, User.class);
        System.out.println(user1);
    }


}
