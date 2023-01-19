package com.projects.Fastfood.fastfood.repository

import com.projects.AbstractIntegrationContainerBaseTest
import com.projects.Fastfood.fastfood.entity.Fastfood
import org.springframework.beans.factory.annotation.Autowired

import java.time.LocalDateTime

class FastfoodRepositoryTest extends AbstractIntegrationContainerBaseTest {
    @Autowired
    private FastfoodRepository fastfoodRepository;

    def setup() {
        fastfoodRepository.deleteAll()
    }

    def "FastfoodRepository Save"() {
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

    def "FastfoodRepository SaveAll"() {
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
        fastfoodRepository.saveAll(Arrays.asList(fastfood))
        def result = fastfoodRepository.findAll()

        then:
        result.size() == 1
    }

    def "test for BaseTimeEntity"() {
        given:
        LocalDateTime now = LocalDateTime.now()
        String address = "서울특별시 성북구 종암동"
        String name = "은혜 약국"
        def fastfood = Fastfood.builder()
                .storeAddress(address)
                .storeName(name)
                .build()

        when:

        then:
    }
}