package com.hvl.coronanews.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CityServiceTest {
    private CityService cityService;

    @BeforeEach
    void setUp() {
        cityService = new CityService();
        cityService.setCities(List.of("ANKARA", "İSTANBUL", "İZMİR"));
    }

    @Test
    void testExtractCity_validCityFound() {
        String content = "Bugün Ankara'da vaka sayısı arttı.";
        String result = cityService.detectCity(content);
        assertEquals("ANKARA", result);
    }

    @Test
    void testExtractCity_cityNameCaseInsensitive() {
        String content = "istanbul için yeni açıklama yapıldı.";
        String result = cityService.detectCity(content);
        assertEquals("İSTANBUL", result);
    }

    @Test
    void testExtractCity_cityNotFound() {
        String content = "Bu içerikte herhangi bir şehir bilgisi yok.";
        String result = cityService.detectCity(content);
        assertEquals("BİLİNMEYEN", result);
    }
}
