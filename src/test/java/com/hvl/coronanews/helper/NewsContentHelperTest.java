package com.hvl.coronanews.helper;

import com.hvl.coronanews.enums.NewsKeywordType;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class NewsContentHelperTest {

    @Test
    void testGetNewsDate_ddMMyyyy() {
        String content = "20.04.2020 tarihinde önemli gelişmeler oldu.";
        Date date = NewsContentHelper.getNewsDate(content);

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        cal.setTime(date);

        assertEquals(2020, cal.get(Calendar.YEAR));
        assertEquals(Calendar.APRIL, cal.get(Calendar.MONTH)); // Nisan = 3
        assertEquals(20, cal.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    void testGetCoronaData_missingKeyword() {
        String[] sentences = {
                "10 kişi hastaneden ayrıldı."
        };

        Map<NewsKeywordType, Integer> stats = NewsContentHelper.getCoronaData(sentences);

        assertTrue(stats.isEmpty()); // anahtar kelimeler eşleşmez
    }

    @Test
    void testMaskAndUnmaskDates() {
        StringBuilder content = new StringBuilder("Bugün 21.05.2024 ve yarın 22.05.2024 tarihleri arasında işlemler yapılacak.");
        Map<String, String> masked = NewsContentHelper.maskDates(content);

        assertTrue(content.toString().contains("TAGGED_DATE_1"));
        assertTrue(masked.containsKey("TAGGED_DATE_1"));

        String[] sentences = { content.toString() };
        String[] unmasked = NewsContentHelper.unmaskDates(sentences, masked);

        assertTrue(unmasked[0].contains("21.05.2024"));
        assertTrue(unmasked[0].contains("22.05.2024"));
    }

    @Test
    void testSentenceMatchesType() {
        String sentence = "Bugün 15 kişi taburcu oldu.";
        boolean result = NewsContentHelper.sentenceMatchesType(sentence, NewsKeywordType.TABURCU);

        assertTrue(result);
    }

    @Test
    void testSentenceMatchesType_caseInsensitive() {
        String sentence = "Bugün 15 kişi TABURCU edildi.";
        boolean result = NewsContentHelper.sentenceMatchesType(sentence, NewsKeywordType.TABURCU);

        assertTrue(result);
    }

    @Test
    void testGetNewsDate_invalidFormat_shouldReturnNull() {
        String content = "Bugün bir tarih yok.";
        Date result = NewsContentHelper.getNewsDate(content);

        assertNull(result);
    }

    @Test
    void testGetNewsDate_with_slash() {
        String content = "Tarih: 21/05/2024 tarihinde bildirildi.";
        Date date = NewsContentHelper.getNewsDate(content);

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        cal.setTime(date);

        assertEquals(2024, cal.get(Calendar.YEAR));
        assertEquals(Calendar.MAY, cal.get(Calendar.MONTH));
        assertEquals(21, cal.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    void testGetNewsDate_with_dash() {
        String content = "Olay 21-05-2024 tarihinde oldu.";
        Date date = NewsContentHelper.getNewsDate(content);

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        cal.setTime(date);

        assertEquals(2024, cal.get(Calendar.YEAR));
        assertEquals(Calendar.MAY, cal.get(Calendar.MONTH));
        assertEquals(21, cal.get(Calendar.DAY_OF_MONTH));
    }
}
