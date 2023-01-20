package com.projects.Fastfood.direction.service;

import com.projects.Fastfood.api.dto.DocumentDto;
import com.projects.Fastfood.direction.entity.Direction;
import com.projects.Fastfood.fastfood.dto.FastfoodDto;
import com.projects.Fastfood.fastfood.service.FastfoodSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor

public class DirectionService {

    private static final int MAX_SEARCH_COUNT = 3;
    private static final double RADIUS_KM = 10.0;
    private final FastfoodSearchService fastfoodSearchService;

    public List<Direction> buildDirectionList(DocumentDto documentDto){
        if(Objects.isNull(documentDto)) return Collections.emptyList();
        return fastfoodSearchService.searchFastfoodDtoList()
                .stream().map(fastfoodDto ->
                    Direction.builder()
                            .inputAddress(documentDto.getStoreAddress())
                            .inputLatitude(documentDto.getLatitude())
                            .inputLongitude(documentDto.getLongitude())
                            .targetLongitude(fastfoodDto.getLongitude())
                            .targetLatitude(fastfoodDto.getLatitude())
                            .targetStoreName(fastfoodDto.getStoreName())
                            .targetAddress(fastfoodDto.getStoreAddress())
                            .distance(
                                    calculateDistance(documentDto.getLatitude(), documentDto.getLongitude(), fastfoodDto.getLatitude(), fastfoodDto.getLongitude())
                            )
                            .build())
                .filter(direction -> direction.getDistance() <= RADIUS_KM)
                .sorted(Comparator.comparing(Direction::getDistance))
                .limit(MAX_SEARCH_COUNT)
                .collect(Collectors.toList());
    }
    // Haversine formula
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);

        double earthRadius = 6371; //Kilometers
        return earthRadius * Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));
    }
}
