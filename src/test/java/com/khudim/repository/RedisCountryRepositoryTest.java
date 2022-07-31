package com.khudim.repository;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyList;

@SpringBootTest
public class RedisCountryRepositoryTest {

    @MockBean
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private RedisCountryRepository redisCountryRepository;

    @Test
    @SuppressWarnings("unchecked")
    public void shouldGetRedisCounters() {
        ValueOperations<String, String> valueOperations = (ValueOperations<String, String>) Mockito.mock(ValueOperations.class);
        Mockito.when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        Mockito.when(valueOperations.multiGet(anyList())).thenReturn(List.of("0", "1", "2"));

        Map<String, Long> countryCounts = redisCountryRepository.countAll(List.of("it", "us", "ru"));

        assertEquals(2, countryCounts.size());
        assertNull(countryCounts.get("it"));
        assertEquals(1, countryCounts.get("us"));
        assertEquals(2, countryCounts.get("ru"));
    }
}
