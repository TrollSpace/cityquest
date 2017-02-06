package com.cpsmi.dto;

/**
 * Created by Misha on 26.01.2017.
 */
public class StatisticsDTO {

    private long time;

    private double distance;

    private int answeredQuestions;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getAnsweredQuestions() {
        return answeredQuestions;
    }

    public void setAnsweredQuestions(int answeredQuestions) {
        this.answeredQuestions = answeredQuestions;
    }
}
