package com.zodiac.client.enums;

import com.zodiac.client.model.ValidInput;
import java.time.LocalDate;
import java.util.Arrays;

/**
 * Zodiac enum. Constructor takes range in which every zodiac sign lies and creates Date based on provided info.
 */
public enum ZodiacSign {
    AQUARIUS(1, 20, 2, 18),
    PISCES(2, 19, 3, 20),
    ARIES(3, 21, 4, 19),
    TAURUS(3, 20, 5, 20),
    GEMINI(5, 21, 6, 20),
    CANCER(6, 21, 7, 22),
    LEO(7, 23, 8, 22),
    VIRGO(8, 23, 9, 22),
    LIBRA(9, 23, 10, 22),
    SCORPIO(10, 23, 11, 21),
    SAGITTARIUS(11, 22, 12, 21),
    CAPRICORN(12, 22, 1, 19);

    private int lowMonth;
    private int lowDay;
    private int highMonth;
    private int highDay;
    private String translatedSign;
    private LocalDate startZodiacDate;
    private LocalDate endZodiacDate;

    ZodiacSign(int lowMonth, int lowDay, int highMonth, int highDay) {
        this.lowMonth = lowMonth;
        this.lowDay = lowDay;
        this.highMonth = highMonth;
        this.highDay = highDay;
        this.startZodiacDate = LocalDate.of(1970, lowMonth, lowDay);
        this.endZodiacDate = LocalDate.of(1970, highMonth, highDay);
    }

    public static ZodiacSign getZodiac(ValidInput zodiacInput) {
        int day = zodiacInput.getDay();
        int month = zodiacInput.getMonth();
        LocalDate dayAndMonthUserBirth = LocalDate.of(1970, month, day);
        ZodiacSign userSign = Arrays.asList(ZodiacSign.values()).stream().filter(item->
            (dayAndMonthUserBirth.compareTo(item.startZodiacDate) >= 0)
                    && (dayAndMonthUserBirth.compareTo(item.endZodiacDate) <= 0))
         .findFirst().get();
        return userSign;
        }
    }