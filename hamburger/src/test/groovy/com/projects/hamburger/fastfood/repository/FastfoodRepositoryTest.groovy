package com.projects.hamburger.fastfood.repository

import com.projects.AbstractIntegrationContainerBaseTest
import com.projects.hamburger.fastfood.entity.Fastfood
import org.springframework.beans.factory.annotation.Autowired

class FastfoodRepositoryTest extends AbstractIntegrationContainerBaseTest {
    @Autowired
    private FastfoodRepository fastfoodRepository;

    def "FastfoodRepository Save"(){
        given:
        String address = "서울 특별시 성북구 종암동"
        String name = "은혜 약국"
        double latitude = 36.11
        double longitude = 128.11

        def fastfood = Fastfood.builder()
                .storeAddress(address)
                .storeName(name)
                .latitude(latitude)
                .longitude(longitude)
                .build()

        when:
        def result = fastfoodRepository.save(fastfood)

        then:
        result.getStoreAddress() == address
        result.getStoreName() == name
        result.getLatitude() == latitude
        result.getLongitude() == longitude
    }
}
