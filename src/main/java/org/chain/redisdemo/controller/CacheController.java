package org.chain.redisdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/redisTest")
public class CacheController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping("/test1")
    public String test1(String key, String val) {
        stringRedisTemplate.opsForValue().set(key, val);
        return stringRedisTemplate.opsForValue().get(key);
    }

    @RequestMapping("/test2")
    @Cacheable("test2")
    public String test2() {
        System.out.println("invoke test2...");
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    @RequestMapping("/test3")
    @CachePut("test3")
    public String test3() {
        System.out.println("invoke test3...");
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    @RequestMapping("/test4")
    @CacheEvict("test4")
    public String test4() {
        System.out.println("invoke test4...");
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    @RequestMapping("/uuid")
    public String testSession(HttpSession session) {
        String uuid = (String) session.getAttribute("uuid");
        if (StringUtils.isEmpty(uuid)) {
            uuid = UUID.randomUUID().toString();
            session.setAttribute("uuid", uuid);
        }
        return uuid;
    }
}
