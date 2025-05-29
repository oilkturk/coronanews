package com.hvl.coronanews.repository.stats;

import com.hvl.coronanews.dto.StatsDTO;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NewsStatisticsRepositoryImpl implements NewsStatisticsRepository {

    private final MongoTemplate mongoTemplate;

    public NewsStatisticsRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<StatsDTO> getDailyAggregatedStats() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.group("city", "date")
                        .sum("caseCount").as("caseCount")
                        .sum("deathCount").as("deathCount")
                        .sum("recoveredCount").as("recoveredCount"),
                Aggregation.project("caseCount", "deathCount", "recoveredCount")
                        .and("_id.city").as("city")
                        .and("_id.date").as("date"),
                Aggregation.sort(Sort.by("date").ascending())
        );

        return mongoTemplate.aggregate(aggregation, "news", StatsDTO.class).getMappedResults();
    }
}
