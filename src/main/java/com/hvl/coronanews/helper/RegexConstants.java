package com.hvl.coronanews.helper;

import java.util.regex.Pattern;

public class RegexConstants {
    public static final Pattern DATE_PATTERN = Pattern.compile("(\\b\\d{2}[./-]\\d{2}[./-]\\d{4}\\b)");

    public static final Pattern NUMBER_PATTERN = Pattern.compile("(\\d+)");

    public static final Pattern SPLIT_SENTENCE_PATTERN = Pattern.compile("\\.\\s*");
}
