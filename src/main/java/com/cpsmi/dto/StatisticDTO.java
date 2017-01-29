package com.cpsmi.dto;

/**
 * Created by Misha on 26.01.2017.
 */
public class StatisticDTO {

    public long questTime;

    public double distance;

    public int questCounter;

    public long getQuestTime() {
        return questTime;
    }

    public void setQuestTime(long questTime) {
        this.questTime = questTime;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getQuestCounter() {
        return questCounter;
    }

    public void setQuestCounter(int questCounter) {
        this.questCounter = questCounter;
    }
}
