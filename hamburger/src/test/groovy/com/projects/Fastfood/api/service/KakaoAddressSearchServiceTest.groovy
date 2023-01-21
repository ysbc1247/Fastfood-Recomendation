package com.projects.Fastfood.api.service
import com.projects.AbstractIntegrationContainerBaseTest
import org.springframework.beans.factory.annotation.Autowired

class KakaoAddressSearchServiceTest extends AbstractIntegrationContainerBaseTest {
    @Autowired
    private KakaoAddressSearchService kakaoAddressSearchService;

    def "if address is null search method returns null"(){
        given:
        def address = null;

        when:
        def result = kakaoAddressSearchService.requestAddressSearch(address)

        then:
        result == null
    }

    def "if address is not null search method returns valid data"(){
        given:
        def address = "서울 성북구 종암로 10길"

        when:
        def result = kakaoAddressSearchService.requestAddressSearch(address)

        then:
        result.documentList.size()>0
        result.metaDto.totalCount>0
        result.documentList.get(0).storeAddress != null

    }

    def "when inputting valid address, returns valid latitude and longitude"(){
        given:
        boolean actualResult = false

        when:
        def searchResult = kakaoAddressSearchService.requestAddressSearch(inputAddress)

        then:
        if(searchResult == null) actualResult = false
        else actualResult = searchResult.getDocumentList().size() > 0

        actualResult == expectedResult

        where:
        inputAddress                            | expectedResult
        "서울 특별시 성북구 종암동"                   | true
        "서울 성북구 종암동 91"                     | true
        "서울 대학로"                             | true
        "서울 성북구 종암동 잘못된 주소"               | false
        "광진구 구의동 251-45"                     | true
        "광진구 구의동 251-455555"                 | false
        ""                                      | false
    }

}
