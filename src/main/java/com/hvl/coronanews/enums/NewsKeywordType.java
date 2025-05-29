package com.hvl.coronanews.enums;

public enum NewsKeywordType {
    VAKA("vaka"),
    VEFAT("vefat"),
    TABURCU("taburcu");

    private final String keyword;

    NewsKeywordType(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }
}
