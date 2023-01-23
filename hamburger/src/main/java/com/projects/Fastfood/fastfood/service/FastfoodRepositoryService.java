package com.projects.Fastfood.fastfood.service;

import com.projects.Fastfood.fastfood.entity.Fastfood;
import com.projects.Fastfood.fastfood.repository.FastfoodRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor

public class FastfoodRepositoryService {
    private final FastfoodRepository fastfoodRepository;


    // self invocation test
    public void bar(List<Fastfood> fastfoodList) {
        log.info("bar CurrentTransactionName: "+ TransactionSynchronizationManager.getCurrentTransactionName());
        foo(fastfoodList);
    }

    // self invocation test
    @Transactional
    public void foo(List<Fastfood> fastfoodList) {
        log.info("foo CurrentTransactionName: "+ TransactionSynchronizationManager.getCurrentTransactionName());
        fastfoodList.forEach(fastfood -> {
            fastfoodRepository.save(fastfood);
            throw new RuntimeException("error");
        });
    }


    // read only test
    @Transactional(readOnly = true)
    public void startReadOnlyMethod(Long id) {
        fastfoodRepository.findById(id).ifPresent(fastfood ->
                fastfood.changeStoreAddress("서울 특별시 광진구"));
    }


    @Transactional
    public List<Fastfood> saveAll(List<Fastfood> fastfoodList) {
        if(CollectionUtils.isEmpty(fastfoodList)) return Collections.emptyList();
        return fastfoodRepository.saveAll(fastfoodList);
    }
    @Transactional
    public void updateAddress(Long id, String address){
        Fastfood entity = fastfoodRepository.findById(id).orElse(null);

        if(Objects.isNull(entity)){
            log.error("cannot find id: {}", id);
            return;
        }

        entity.changeStoreAddress(address);
    }

    public void updateAddressTestForDirtyChecking(Long id, String address){
        Fastfood entity = fastfoodRepository.findById(id).orElse(null);

        if(Objects.isNull(entity)){
            log.error("cannot find id: {}", id);
            return;
        }

        entity.changeStoreAddress(address);
    }

    @Transactional(readOnly = true)
    public List<Fastfood> findAll(){
        return fastfoodRepository.findAll();
    }

}
