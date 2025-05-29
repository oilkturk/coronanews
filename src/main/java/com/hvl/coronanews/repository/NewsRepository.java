package com.hvl.coronanews.repository;

import com.hvl.coronanews.model.News;
import com.hvl.coronanews.repository.stats.NewsStatisticsRepository;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface NewsRepository extends MongoRepository<News, String>, NewsStatisticsRepository {
}
