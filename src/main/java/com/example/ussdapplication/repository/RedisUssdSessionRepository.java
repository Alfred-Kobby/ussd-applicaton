package com.example.ussdapplication.repository;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.example.ussdapplication.model.UssdSession;
import com.example.ussdapplication.utility.ApplicationProperties;
import com.example.ussdapplication.utility.JsonUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;



import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class RedisUssdSessionRepository implements RedisRepository<UssdSession>{
    Integer timeout;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    ApplicationProperties applicationProperties;
    @Override
    public void put(UssdSession ussdSession) {
        log.info("ussdsession.getObjectKey: {}, ussdsession.getKey: {}", ussdSession.getObjectKey(), ussdSession.getKey());
        log.info("ussdsession {}", ussdSession);
		redisTemplate.opsForHash().put(ussdSession.getObjectKey(), ussdSession.getKey(), JsonUtility.toJson(ussdSession));
        redisTemplate.opsForValue().set(ussdSession.getKey(), JsonUtility.toJson(ussdSession));
        Boolean expire = redisTemplate.expire(ussdSession.getKey(), applicationProperties.getUssdsessionTimeout(), TimeUnit.MINUTES);
        log.info("Expire {}", expire);
        log.info("=======Expire period========{}", redisTemplate.getExpire(ussdSession.getKey()));

    }

    public UssdSession fragment(String json){
        UssdSession ussdSession = null;
        try {
            if(json!=null) {
                ussdSession = JsonUtility.fromJson(json, UssdSession.class);

            }else {
                return null;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ussdSession;
    }

    @Override
    public UssdSession get(UssdSession key) {
//		 return (UssdSession) redisTemplate.opsForHash().get(key.getObjectKey(),
//				key.getKey());
        String json = redisTemplate.opsForValue().get(key.getKey());
        return fragment(json);
    }

    @Override
    public void delete(UssdSession key) {
        redisTemplate.opsForValue().getOperations().delete(key.getKey());
//		redisTemplate.delete(key.getKey());
    }

    @Override
    public List<UssdSession> getObjects() {
        List<UssdSession> ussdSessions = new ArrayList<UssdSession>();
        for (Object ussdSession : redisTemplate.opsForHash().values(UssdSession.OBJECT_KEY) ){
            ussdSessions.add((UssdSession)ussdSession);
        }
        return ussdSessions;
    }

    public UssdSession getShow(String key) {
//		 return (UssdSession) redisTemplate.opsForHash().get(key.getObjectKey(),
//				key.getKey());
        String json = redisTemplate.opsForValue().get(key);
        return fragment(json);
    }
}
