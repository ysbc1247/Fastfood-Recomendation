package com.projects.Fastfood.controller;

import com.projects.Fastfood.fastfood.cache.FastfoodRedisTemplateService;
import com.projects.Fastfood.fastfood.dto.FastfoodDto;
import com.projects.Fastfood.fastfood.entity.Fastfood;
import com.projects.Fastfood.fastfood.service.FastfoodRepositoryService;
import com.projects.Fastfood.util.CsvUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FastfoodController {

    private final FastfoodRepositoryService fastfoodRepositoryService;
    private final FastfoodRedisTemplateService fastfoodRedisTemplateService;

    // 데이터 초기 셋팅을 위한 임시 메소드
    @GetMapping("/csv/save")
    public String saveCsv() {
        //saveCsvToDatabase();
        saveCsvToRedis();

        return "success save";
    }

    public void saveCsvToDatabase() {

        List<Fastfood> fastfoodList = loadFastfoodList();
        fastfoodRepositoryService.saveAll(fastfoodList);

    }

    public void saveCsvToRedis() {

        List<FastfoodDto> fastfoodDtoList = fastfoodRepositoryService.findAll()
                .stream().map(fastfood -> FastfoodDto.builder()
                        .id(fastfood.getId())
                        .storeName(fastfood.getStoreName())
                        .storeAddress(fastfood.getStoreAddress())
                        .latitude(fastfood.getLatitude())
                        .longitude(fastfood.getLongitude())
                        .build())
                .collect(Collectors.toList());

        fastfoodDtoList.forEach(fastfoodRedisTemplateService::save);

    }

    private List<Fastfood> loadFastfoodList() {
        return CsvUtils.convertToFastfoodDtoList()
                .stream().map(fastfoodDto ->
                        Fastfood.builder()
                                .id(fastfoodDto.getId())
                                .storeName(fastfoodDto.getStoreName())
                                .storeAddress(fastfoodDto.getStoreAddress())
                                .latitude(fastfoodDto.getLatitude())
                                .longitude(fastfoodDto.getLongitude())
                                .build())
                .collect(Collectors.toList());
    }
}