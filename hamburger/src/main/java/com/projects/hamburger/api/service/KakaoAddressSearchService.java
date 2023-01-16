package com.projects.hamburger.api.service;

import com.projects.hamburger.api.dto.KakaoAPIResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Slf4j
@Service
@RequiredArgsConstructor

public class KakaoAddressSearchService {

    private final RestTemplate restTemplate;
    private final KakaoURIBuilderService kakaoURIBuilderService;

    @Value("${kakao.rest.api.key")
    private String kakaoRestAPIKey;

    public KakaoAPIResponseDto requestAddressSearch(String address){
        if(ObjectUtils.isEmpty(address)){
            return null;
        }
        URI uri = kakaoURIBuilderService.buildUriByAddressSearch(address);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "KakaoAK" +kakaoRestAPIKey);
        HttpEntity httpEntity = new HttpEntity<>(headers);
        //kakao api
        return restTemplate.exchange(uri, HttpMethod.GET, httpEntity, KakaoAPIResponseDto.class).getBody();
    }
}
