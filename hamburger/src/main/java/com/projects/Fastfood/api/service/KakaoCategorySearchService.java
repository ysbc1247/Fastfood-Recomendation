package com.projects.Fastfood.api.service;


import com.projects.Fastfood.api.dto.KakaoAPIResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Slf4j
@Service
@RequiredArgsConstructor

public class KakaoCategorySearchService {

    private final KakaoURIBuilderService kakaoURIBuilderService;

    private final RestTemplate restTemplate;

    private static final String FASTFOOD_CATEGORY = "PM9";

    @Value("${kakao.rest.api.key}")
    private String kakaoRestApiKey;

    public KakaoAPIResponseDto requestFastfoodCategorySearch(double latitude, double longitude, double radius) {

        URI uri = kakaoURIBuilderService.buildUriByCategorySearch(latitude, longitude, radius, FASTFOOD_CATEGORY);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "KakaoAK "+ kakaoRestApiKey);
        HttpEntity httpEntity = new HttpEntity<>(headers);

        return restTemplate.exchange(uri, HttpMethod.GET, httpEntity, KakaoAPIResponseDto.class).getBody();
    }

}
