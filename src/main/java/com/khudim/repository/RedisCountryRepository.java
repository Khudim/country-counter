package com.khudim.repository;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RedisCountryRepository {

    private final RedisTemplate<String, String> redisTemplate;

    public RedisCountryRepository(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void increment(String countryCode) {
        redisTemplate.opsForValue().increment(countryCode);
    }

    public Map<String, Long> countAll(List<String> countryCodes) {
        List<String> counters = redisTemplate.opsForValue().multiGet(countryCodes);
        if (counters == null) {
            return Collections.emptyMap();
        }
        Map<String, Long> countryCounters = new HashMap<>();
        int i = 0;
        for (String countryCode : countryCodes) {
            String counter = counters.get(i++); // counters are returned in the order of the requested keys.
            if (counter == null || counter.equals("0")) {
                continue;
            }
            countryCounters.put(countryCode, Long.parseLong(counter));
        }
        return countryCounters;
    }
}
