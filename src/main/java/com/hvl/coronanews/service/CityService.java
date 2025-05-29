package com.hvl.coronanews.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.*;

@Service
public class CityService {
    private List<String> cities;

    public CityService() {
        this.cities = new ArrayList<>();
    }

    @PostConstruct
    public void loadCities() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream is = getClass().getClassLoader().getResourceAsStream("data/cities.json");
            this.cities = mapper.readValue(is, new TypeReference<>() {});
        } catch (Exception e) {
            throw new RuntimeException("Şehir listesi yüklenemedi", e);
        }
    }

    public String detectCity(String content) {
        String lower = content.toLowerCase(new Locale("tr", "TR"));
        return cities.stream()
                .filter(city -> lower.contains(city.toLowerCase(new Locale("tr", "TR"))))
                .findFirst()
                .orElse("BİLİNMEYEN");
    }

    public void setCities(List<String> cities) {
        this.cities.clear();
        this.cities.addAll(cities);
    }

    public List<String> getAllCities() {
        return cities;
    }
}
