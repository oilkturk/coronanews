package com.hvl.coronanews.controller;

import com.hvl.coronanews.dto.StatsDTO;
import com.hvl.coronanews.repository.NewsRepository;
import com.hvl.coronanews.service.CityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/corona/stats")
public class StatsController {
    private final NewsRepository newsRepository;

    private final CityService cityService;

    public StatsController(NewsRepository newsRepository, CityService cityService) {
        this.newsRepository = newsRepository;
        this.cityService = cityService;
    }

    @GetMapping("/daily")
    public List<StatsDTO> getDailyStats() {
        return newsRepository.getDailyAggregatedStats();
    }

    @GetMapping("/cities")
    public ResponseEntity<List<String>> getCities() {
        return ResponseEntity.ok(cityService.getAllCities());
    }
}
