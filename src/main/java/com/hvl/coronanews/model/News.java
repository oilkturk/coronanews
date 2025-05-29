package com.hvl.coronanews.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "news")
public class News {
    @Id
    private String id;

    private Date date;
    private String city;
    private int recoveredCount;
    private int deathCount;
    private int caseCount;
    private String content;

    public News(Date date, String city, int recoveredCount, int deathCount, int caseCount, String content) {
        this.date = date;
        this.city = city;
        this.recoveredCount = recoveredCount;
        this.deathCount = deathCount;
        this.caseCount = caseCount;
        this.content = content;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public int getRecoveredCount() { return recoveredCount; }
    public void setRecoveredCount(int recoveredCount) { this.recoveredCount = recoveredCount; }

    public int getDeathCount() { return deathCount; }
    public void setDeathCount(int deathCount) { this.deathCount = deathCount; }

    public int getCaseCount() { return caseCount; }
    public void setCaseCount(int caseCount) { this.caseCount = caseCount; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
