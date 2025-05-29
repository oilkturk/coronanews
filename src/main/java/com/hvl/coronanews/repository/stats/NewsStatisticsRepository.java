package com.hvl.coronanews.repository.stats;

import com.hvl.coronanews.dto.StatsDTO;

import java.util.List;

public interface NewsStatisticsRepository {
    List<StatsDTO> getDailyAggregatedStats();
}
