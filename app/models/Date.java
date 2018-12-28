package models;


import java.time.YearMonth;


public class Date {
    private int year = 0;
    private int month = 0;
    private int day = 0;
    private int hour = 0;
    private int minutes = 0;


    public Date(int year, int month, int day, int hour, int minutes) {
        if (year < YearMonth.now().getYear()) {
            throw new NullPointerException("Please enter a valid year");
        }

        if (month > 12) {
            throw new IllegalArgumentException("Please enter a valid month");
        }

        if (day > 30) {
            throw new IllegalArgumentException("Please enter a valid month");
        }
        if (hour > 24) {
            throw new IllegalArgumentException("Please enter a valid no of Hours");
        }

        if (minutes > 60) {
            throw new IllegalArgumentException("Please enter a valid no of minutes");
        }


        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minutes = minutes;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        if (year < YearMonth.now().getYear()) {
            throw new NullPointerException("Please enter a valid year");
        }
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        if (month > 12) {
            throw new IllegalArgumentException("Please enter a valid month");
        }
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        if (day > 31) {
            throw new IllegalArgumentException("Please enter a valid month");
        }

        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        if (hour > 24) {
            throw new IllegalArgumentException("Please enter a valid no of Hours");
        }
        this.hour = hour;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        if (minutes > 60) {
            throw new IllegalArgumentException("Please enter a valid no of minutes");
        }
        this.minutes = minutes;
    }


}
