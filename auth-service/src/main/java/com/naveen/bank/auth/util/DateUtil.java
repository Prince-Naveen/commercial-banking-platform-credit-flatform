package com.naveen.bank.auth.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DateUtil {

    private static final String DATE_PATTERN = "dd-MM-yyyy";
    private static final String DATE_TIME_PATTERN = "dd-MM-yyyy HH:mm:ss";

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern(DATE_PATTERN);

    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

    /**
     * Current Date
     */
    public static LocalDate currentDate() {
        return LocalDate.now();
    }

    /**
     * Current Date Time
     */
    public static LocalDateTime currentDateTime() {
        return LocalDateTime.now();
    }

    /**
     * LocalDateTime -> Date
     */
    public static Date toDate(LocalDateTime localDateTime) {

        return Date.from(
                localDateTime
                        .atZone(ZoneId.systemDefault())
                        .toInstant());
    }

    /**
     * Date -> LocalDateTime
     */
    public static LocalDateTime toLocalDateTime(Date date) {

        return Instant.ofEpochMilli(date.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    /**
     * Format LocalDate
     */
    public static String format(LocalDate date) {

        return date.format(DATE_FORMATTER);
    }

    /**
     * Format LocalDateTime
     */
    public static String format(LocalDateTime dateTime) {

        return dateTime.format(DATE_TIME_FORMATTER);
    }

    /**
     * Parse LocalDate
     */
    public static LocalDate parseDate(String date) {

        return LocalDate.parse(date, DATE_FORMATTER);
    }

    /**
     * Parse LocalDateTime
     */
    public static LocalDateTime parseDateTime(String dateTime) {

        return LocalDateTime.parse(
                dateTime,
                DATE_TIME_FORMATTER);
    }

    /**
     * Check Expired
     */
    public static boolean isExpired(LocalDateTime expiryDate) {

        return expiryDate.isBefore(LocalDateTime.now());
    }

}