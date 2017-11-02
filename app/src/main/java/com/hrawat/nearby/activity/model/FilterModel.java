package com.hrawat.nearby.activity.model;

/**
 * Created by hrawat on 10/4/2017.
 */
public class FilterModel {
    private String distance;
    private boolean isApplied;

    public FilterModel(String distance, boolean isApplied) {
        this.distance = distance;
        this.isApplied = isApplied;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public boolean isApplied() {
        return isApplied;
    }

    public void setApplied(boolean applied) {
        isApplied = applied;
    }
}
