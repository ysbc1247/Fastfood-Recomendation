package com.projects.Fastfood.fastfood.service

import com.projects.AbstractIntegrationContainerBaseTest
import com.projects.Fastfood.fastfood.entity.Fastfood
import com.projects.Fastfood.fastfood.repository.FastfoodRepository
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

class FastfoodRepositoryServiceTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    FastfoodRepositoryService fastfoodRepositoryService

    @Autowired
    private FastfoodRepository fastfoodRepository;

    def setup(){
        fastfoodRepository.deleteAll()
    }

    def "FastfoodRepository update - dirty checking success"(){
        given:
        String inputAddress = "서울 특별시 성북구 종암동"
        String modifiedAddress = "서울 광진구 구의동"
        String name = "은혜 약국"

        def fastfood = Fastfood.builder()
                .storeAddress(inputAddress)
                .storeName(name)
                .build()

        when:
        def entity = fastfoodRepository.save(fastfood)
        fastfoodRepositoryService.updateAddress(entity.getId(), modifiedAddress)

        def result = fastfoodRepository.findAll()

        then:
        result.get(0).getStoreAddress() == modifiedAddress
    }

    def "FastfoodRepository update - dirty checking fail"(){
        given:
        String inputAddress = "서울 특별시 성북구 종암동"
        String modifiedAddress = "서울 광진구 구의동"
        String name = "은혜 약국"

        def fastfood = Fastfood.builder()
                .storeAddress(inputAddress)
                .storeName(name)
                .build()

        when:
        def entity = fastfoodRepository.save(fastfood)
        fastfoodRepositoryService.updateAddressTestForDirtyChecking(entity.getId(), modifiedAddress)

        def result = fastfoodRepository.findAll()

        then:
        result.get(0).getStoreAddress() == inputAddress
    }

}
