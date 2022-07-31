package com.khudim.service;

import com.khudim.repository.RedisCountryRepository;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@Service
public class CountryService {

    private final RedisCountryRepository redisCountryRepository;

    public CountryService(RedisCountryRepository redisCountryRepository) {
        this.redisCountryRepository = redisCountryRepository;
    }

    public void increment(String countryCode) {
        redisCountryRepository.increment(countryCode);
    }

    public Map<String, Long> countAll(List<String> countryCodes) {
        return redisCountryRepository.countAll(countryCodes);
    }
}
