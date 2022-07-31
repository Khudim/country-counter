package com.khudim.controller;

import com.khudim.exception.BusinessLogicException;
import com.khudim.service.CountryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/country")
public class CountryController {

    private final CountryService countryService;
    private final Set<String> uniqueCodes;
    private final List<String> orderedCodes;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
        String[] countries = Locale.getISOCountries(); // ISO 3166-1 alpha-2, ru, us...
        orderedCodes = Arrays.asList(countries);
        uniqueCodes = new HashSet<>(orderedCodes);
    }

    @GetMapping("/counter")
    public ResponseEntity<Map<String, Long>> count() {
        Map<String, Long> countryCounters = countryService.countAll(orderedCodes);
        return ResponseEntity.ok(countryCounters);
    }

    @PatchMapping("/counter/{countryCode}/increment")
    public void incrementCounterByCode(@PathVariable String countryCode) {
        String code = countryCode.toUpperCase();
        if (!uniqueCodes.contains(code)) {
            throw BusinessLogicException.countryCodeNotSupported(countryCode);
        }
        countryService.increment(code);
    }
}
