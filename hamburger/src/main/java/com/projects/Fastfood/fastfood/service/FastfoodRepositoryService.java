package com.projects.Fastfood.fastfood.service;

import com.projects.Fastfood.fastfood.entity.Fastfood;
import com.projects.Fastfood.fastfood.repository.FastfoodRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor

public class FastfoodRepositoryService {
    private final FastfoodRepository fastfoodRepository;

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

}
