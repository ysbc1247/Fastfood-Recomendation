package com.projects.Fastfood.direction.service

import com.projects.Fastfood.api.dto.DocumentDto
import com.projects.Fastfood.fastfood.dto.FastfoodDto
import com.projects.Fastfood.fastfood.service.FastfoodSearchService
import spock.lang.Specification

class DirectionServiceTest extends Specification {

    private FastfoodSearchService fastfoodSearchService = Mock()

    private DirectionService directionService = new DirectionService(fastfoodSearchService)

    private List<FastfoodDto> fastfoodList

    def setup(){
        fastfoodList = new ArrayList<>()
        fastfoodList.addAll(
                FastfoodDto.builder()
                        .id(1L)
                        .storeName("돌곶이온누리약국")
                        .storeAddress("주소1")
                        .latitude(37.61040424)
                        .longitude(127.0569046)
                        .build(),
                FastfoodDto.builder()
                        .id(2L)
                        .storeName("호수온누리약국")
                        .storeAddress("주소2")
                        .latitude(37.60894036)
                        .longitude(127.029052)
                        .build()
        )
    }
    def "buildDirectionList - check if results are sorted"(){
        given:
        def latitude1 = 37.5505
        def longitude1 = 127.0817

        def latitude2 = 37.541
        def longitude2 = 127.0766
        def result = "1.1"

        when:
        def distance = directionService.calculateDistance(latitude1, longitude1, latitude2, longitude2)

        then:
        String.format("%.1f", distance) == result
    }

    def "buildDirectionList - check if results are in 10km radius"(){
        given:
        fastfoodList.add(
                FastfoodDto.builder()
                        .id(3L)
                        .storeName("경기약국")
                        .storeAddress("주소3")
                        .latitude(37.3825107393401)
                        .longitude(127.236707811313)
                        .build())
        def storeAddress = "서울 성북구 종암로10길"
        double inputLatitude = 37.5960650456809
        double inputLongitude = 127.037033003036

        def documentDto = DocumentDto.builder()
                .storeAddress(storeAddress)
                .latitude(inputLatitude)
                .longitude(inputLongitude)
                .build()

        when:
        fastfoodSearchService.searchFastfoodDtoList() >> fastfoodList

        def results = directionService.buildDirectionList(documentDto)
        then:

        results.size() == 2
        results.get(0).targetStoreName == "호수온누리약국"
        results.get(1).targetStoreName == "돌곶이온누리약국"
        String.format("%.1f", results.get(0).distance) == "1.6"
        String.format("%.1f", results.get(1).distance) == "2.4"
    }
}
