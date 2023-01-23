package com.projects.Fastfood.fastfood.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.Fastfood.fastfood.dto.FastfoodDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class FastfoodRedisTemplateService {

    private static final String CACHE_KEY = "FASTFOOD";

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    private HashOperations<String, String, String> hashOperations;

    @PostConstruct
    public void init() {
        this.hashOperations = redisTemplate.opsForHash();
    }

    public void save(FastfoodDto fastfoodDto) {
        if(Objects.isNull(fastfoodDto) || Objects.isNull(fastfoodDto.getId())) {
            log.error("Required Values must not be null");
            return;
        }

        try {
            hashOperations.put(CACHE_KEY,
                    fastfoodDto.getId().toString(),
                    serializeFastfoodDto(fastfoodDto));
            log.info("[FastfoodRedisTemplateService save success] id: {}", fastfoodDto.getId());
        } catch (Exception e) {
            log.error("[FastfoodRedisTemplateService save error] {}", e.getMessage());
        }
    }

    public List<FastfoodDto> findAll() {

        try {
            List<FastfoodDto> list = new ArrayList<>();
            for (String value : hashOperations.entries(CACHE_KEY).values()) {
                FastfoodDto fastfoodDto = deserializeFastfoodDto(value);
                list.add(fastfoodDto);
            }
            return list;

        } catch (Exception e) {
            log.error("[FastfoodRedisTemplateService findAll error]: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    public void delete(Long id) {
        hashOperations.delete(CACHE_KEY, String.valueOf(id));
        log.info("[FastfoodRedisTemplateService delete]: {} ", id);
    }

    private String serializeFastfoodDto(FastfoodDto fastfoodDto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(fastfoodDto);
    }

    private FastfoodDto deserializeFastfoodDto(String value) throws JsonProcessingException {
        return objectMapper.readValue(value, FastfoodDto.class);
    }
}
