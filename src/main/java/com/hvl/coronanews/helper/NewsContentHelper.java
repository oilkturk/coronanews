package com.hvl.coronanews.helper;

import com.hvl.coronanews.enums.NewsKeywordType;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;

import static com.hvl.coronanews.helper.RegexConstants.DATE_PATTERN;
import static com.hvl.coronanews.helper.RegexConstants.NUMBER_PATTERN;

public class NewsContentHelper {
    public static Date getNewsDate(String content) {
        Matcher m = DATE_PATTERN.matcher(content);
        if (m.find()) {
            String rawDate = m.group();
            DateTimeFormatter formatter = detectDateFormatter(rawDate);
            LocalDate parsedDate = LocalDate.parse(rawDate, formatter);
            return Date.from(parsedDate.atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        }

        return null;
    }

    private static DateTimeFormatter detectDateFormatter(String date) {
        if (date.contains(".")) {
            return DateTimeFormatter.ofPattern("dd.MM.yyyy");
        } else if (date.contains("/")) {
            return DateTimeFormatter.ofPattern("dd/MM/yyyy");
        } else if (date.contains("-")) {
            return DateTimeFormatter.ofPattern("dd-MM-yyyy");
        }
        throw new IllegalArgumentException("Desteklenmeyen tarih biçimi: " + date);
    }

    public static Map<NewsKeywordType, Integer> getCoronaData(String[] sentences) {
        Map<NewsKeywordType, Integer> stats = new EnumMap<>(NewsKeywordType.class);

        for (String sentence : sentences) {
            String normalized = sentence.trim().toLowerCase(new Locale("tr", "TR"));

            for (NewsKeywordType type : NewsKeywordType.values()) {
                if (!stats.containsKey(type) && sentenceMatchesType(normalized, type)) {
                    String cleaned = sentence.replaceAll(DATE_PATTERN.pattern(), "");

                    Matcher matcher = NUMBER_PATTERN.matcher(cleaned);
                    if (matcher.find()) {
                        stats.put(type, Integer.parseInt(matcher.group()));
                    }
                }
            }
        }

        return stats;
    }

    public static Map<String, String> maskDates(StringBuilder contentBuilder) {
        Map<String, String> dateMap = new HashMap<>();
        Matcher matcher = DATE_PATTERN.matcher(contentBuilder.toString());

        int index = 1;
        while (matcher.find()) {
            String originalDate = matcher.group();
            String tag = "TAGGED_DATE_" + index++;
            dateMap.put(tag, originalDate);
            int start = matcher.start();
            int end = matcher.end();
            contentBuilder.replace(start, end, tag);
            matcher = DATE_PATTERN.matcher(contentBuilder.toString());
        }

        return dateMap;
    }

    public static String[] unmaskDates(String[] sentences, Map<String, String> dateMap) {
        for (int i = 0; i < sentences.length; i++) {
            for (Map.Entry<String, String> entry : dateMap.entrySet()) {
                sentences[i] = sentences[i].replace(entry.getKey(), entry.getValue());
            }
        }
        return sentences;
    }

    public static boolean sentenceMatchesType(String sentence, NewsKeywordType type) {
        return sentence.toLowerCase().contains(type.getKeyword());
    }
}
