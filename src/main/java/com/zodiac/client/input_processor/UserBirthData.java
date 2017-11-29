package com.zodiac.client.input_processor;

/**
 * class represents user input splitted into form convenient for further processing.
 */
public class UserBirthData {
    String year;
    String month;
    String day;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public UserBirthData() {
    }
}