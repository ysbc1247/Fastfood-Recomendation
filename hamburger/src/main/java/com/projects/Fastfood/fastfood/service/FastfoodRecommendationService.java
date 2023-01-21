package com.projects.Fastfood.fastfood.service;

import com.projects.Fastfood.api.dto.DocumentDto;
import com.projects.Fastfood.api.dto.KakaoAPIResponseDto;
import com.projects.Fastfood.api.service.KakaoAddressSearchService;
import com.projects.Fastfood.direction.entity.Direction;
import com.projects.Fastfood.direction.service.DirectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor

public class FastfoodRecommendationService {

    private final KakaoAddressSearchService kakaoAddressSearchService;
    private final DirectionService directionService;

    public void recommendFastfoodList(String address){
        KakaoAPIResponseDto kakaoAPIResponseDto = kakaoAddressSearchService.requestAddressSearch(address);

        if(Objects.isNull(kakaoAPIResponseDto) || CollectionUtils.isEmpty(kakaoAPIResponseDto.getDocumentList())){
            log.error("Input address {} is not valid/null", address);
            return;
        }
        DocumentDto documentDto = kakaoAPIResponseDto.getDocumentList().get(0);
        List<Direction> directionList = directionService.buildDirectionListByCategoryApi(documentDto);

        directionService.saveAll(directionList);
    }
}
