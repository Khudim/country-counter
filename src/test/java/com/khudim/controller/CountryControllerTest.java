package com.khudim.controller;

import com.khudim.service.CountryService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.anyList;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CountryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RedisTemplate<String, Long> redisTemplate;

    @MockBean
    private CountryService countryService;

    @Test
    public void shouldAddCounter() throws Exception {
        mockMvc.perform(patch("/country/counter/us/increment"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldNotIncrement() throws Exception {
        mockMvc.perform(patch("/country/counter/qwe/increment"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Country code = qwe, not supported."));
    }

    @Test
    public void shouldReturnCount() throws Exception {
        Mockito.when(countryService.countAll(anyList())).thenReturn(Map.of("ru", 1L));

        mockMvc.perform(get("/country/counter/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ru", equalTo(1)));
    }
}
