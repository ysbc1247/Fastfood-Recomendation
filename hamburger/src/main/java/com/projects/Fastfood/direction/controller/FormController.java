package com.projects.Fastfood.direction.controller;


import com.projects.Fastfood.direction.dto.InputDto;
import com.projects.Fastfood.fastfood.service.FastfoodRecommendationService;
import com.projects.Fastfood.fastfood.service.FastfoodSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@Controller

public class FormController {

    private final FastfoodRecommendationService fastfoodRecommendationService;

    @GetMapping("/")
    public String main(){
        return "main";
    }

    @PostMapping("/search")
    public ModelAndView postDirection(@ModelAttribute InputDto inputDto) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("output");
        modelAndView.addObject("outputFormList",
                fastfoodRecommendationService.recommendFastfoodList(inputDto.getAddress()));
        return modelAndView;
    }

}
