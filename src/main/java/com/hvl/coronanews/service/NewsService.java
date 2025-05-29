package com.hvl.coronanews.service;

import com.hvl.coronanews.enums.NewsKeywordType;
import com.hvl.coronanews.helper.NewsContentHelper;
import com.hvl.coronanews.model.News;
import com.hvl.coronanews.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.hvl.coronanews.helper.RegexConstants.SPLIT_SENTENCE_PATTERN;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private CityService cityService;

    public NewsService(NewsRepository newsRepository, CityService cityService) {
        this.newsRepository = newsRepository;
        this.cityService = cityService;
    }

    public News createNews(String content) {
        StringBuilder contentBuilder = new StringBuilder(content);
        Map<String, String> maskedDates = NewsContentHelper.maskDates(contentBuilder);

        String[] maskedSentences = contentBuilder.toString().split(SPLIT_SENTENCE_PATTERN.pattern());
        String[] sentences = NewsContentHelper.unmaskDates(maskedSentences, maskedDates);

        String city = cityService.detectCity(content);
        Date date = NewsContentHelper.getNewsDate(content);

        Map<NewsKeywordType, Integer> stats = NewsContentHelper.getCoronaData(sentences);

        if (city.equals("BİLİNMEYEN")) {
            throw new IllegalArgumentException("Şehir bilgisi eksik.");
        }
        if (date == null) {
            throw new IllegalArgumentException("Tarih bilgisi eksik.");
        }
        for (NewsKeywordType type : NewsKeywordType.values()) {
            if (!stats.containsKey(type)) {
                throw new IllegalArgumentException(type.name() + " bilgisi eksik.");
            }
        }

        News news = new News(
                date,
                city,
                stats.get(NewsKeywordType.TABURCU),
                stats.get(NewsKeywordType.VEFAT),
                stats.get(NewsKeywordType.VAKA),
                content
        );
        return newsRepository.save(news);
    }

    public List<News> getAllNews() {
        return newsRepository.findAll();
    }
}
