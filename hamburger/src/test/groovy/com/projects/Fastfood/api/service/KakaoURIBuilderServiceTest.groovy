package com.projects.Fastfood.api.service

import spock.lang.Specification

import java.nio.charset.StandardCharsets

class KakaoURIBuilderServiceTest extends Specification {
    KakaoURIBuilderService kakaoURIBuilderService;

    def setup(){
        kakaoURIBuilderService = new KakaoURIBuilderService();
    }

    def "buildURIByAddressSearch"(){
        given:
        String address = "서울 성북구"
        def charset = StandardCharsets.UTF_8

        when:
        def uri = kakaoURIBuilderService.buildUriByAddressSearch(address)
        def decodedResult = URLDecoder.decode(uri.toString(), charset)

        then:
        decodedResult == "https://dapi.kakao.com/v2/local/search/address.json?query=서울 성북구"
    }
}
