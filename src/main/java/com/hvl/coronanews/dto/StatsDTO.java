package com.hvl.coronanews.dto;

import java.time.LocalDate;
import java.util.Date;

public class StatsDTO {
    private Date date;
    private String city;
    private int caseCount;
    private int deathCount;
    private int recoveredCount;

    public StatsDTO(Date date, String city, int caseCount, int deathCount, int recoveredCount) {
        this.date = date;
        this.city = city;
        this.caseCount = caseCount;
        this.deathCount = deathCount;
        this.recoveredCount = recoveredCount;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getCaseCount() {
        return caseCount;
    }

    public void setCaseCount(int caseCount) {
        this.caseCount = caseCount;
    }

    public int getDeathCount() {
        return deathCount;
    }

    public void setDeathCount(int deathCount) {
        this.deathCount = deathCount;
    }

    public int getRecoveredCount() {
        return recoveredCount;
    }

    public void setRecoveredCount(int recoveredCount) {
        this.recoveredCount = recoveredCount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
