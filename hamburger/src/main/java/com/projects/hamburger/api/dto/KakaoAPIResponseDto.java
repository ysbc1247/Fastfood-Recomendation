package com.projects.hamburger.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor

public class KakaoAPIResponseDto {

    private MetaDto metaDto;
    private List<DocumentDto> documentList;
}
