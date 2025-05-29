package com.hvl.coronanews.dto;

public class NewsRequestDTO {
    private String content;

    public NewsRequestDTO(String content) {
        this.content = content;
    }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
