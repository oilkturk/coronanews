package com.hvl.coronanews.service;

import com.hvl.coronanews.enums.NewsKeywordType;
import com.hvl.coronanews.model.News;
import com.hvl.coronanews.repository.NewsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class NewsServiceTest {

    private NewsRepository newsRepository;
    private CityService cityService;
    private NewsService newsService;

    @BeforeEach
    void setUp() {
        newsRepository = mock(NewsRepository.class);
        cityService = mock(CityService.class);
        newsService = new NewsService(newsRepository, cityService);
    }

    @Test
    void testCreateNews_validContent_shouldReturnNews() {
        // Arrange
        String content = "20.04.2020 tarihinde Ankara da Korona virüs salgınında yapılan testlerde 15 yeni vaka bulundu. 1 kişi korona dan vefat etti. 5 kişide taburcu oldu.";
        when(cityService.detectCity(content)).thenReturn("Ankara");
        when(newsRepository.save(any(News.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        News result = newsService.createNews(content);

        // Assert
        assertNotNull(result);
        assertEquals("Ankara", result.getCity());
        assertNotNull(result.getDate());
        assertEquals(15, result.getCaseCount());
        assertEquals(1, result.getDeathCount());
        assertEquals(5, result.getRecoveredCount());
        assertEquals(content, result.getContent());
    }

    @Test
    void testCreateNews_missingData_shouldThrow() {
        // Arrange: eksik vaka/vefat/taburcu cümlesi
        String content = "20.04.2020 tarihinde Ankara'da sadece açıklama yapıldı.";
        when(cityService.detectCity(content)).thenReturn("Ankara");

        // Act + Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            newsService.createNews(content);
        });

        // assert hata mesajı en azından bilgi içeriyor
        assertTrue(exception.getMessage().contains("bilgisi eksik"));
    }

    @Test
    void testCreateNews_noCity_shouldThrow() {
        String content = "20.04.2020 tarihinde şehir bilgisi belirtilmemiş.";
        when(cityService.detectCity(content)).thenReturn("BİLİNMEYEN");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            newsService.createNews(content);
        });

        assertEquals("Şehir bilgisi eksik.", exception.getMessage());
    }

    @Test
    void testCreateNews_noDate_shouldThrow() {
        String content = "Ankara'da 10 vaka var. 3 kişi vefat etti. 2 kişi taburcu edildi.";
        when(cityService.detectCity(content)).thenReturn("Ankara");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            newsService.createNews(content);
        });

        assertEquals("Tarih bilgisi eksik.", exception.getMessage());
    }

    @Test
    void testGetAllNews() {
        when(newsRepository.findAll()).thenReturn(List.of(new News(new Date(), "Ankara", 1, 2, 3, "test")));
        List<News> result = newsService.getAllNews();
        assertEquals(1, result.size());
        verify(newsRepository).findAll();
    }
}
