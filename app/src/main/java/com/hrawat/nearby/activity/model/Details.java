package com.hrawat.nearby.activity.model;

/**
 * Created by sanjum on 9/21/2017.
 */

public class Details {

    private String title, foodcategory, year;

    public Details(String title, String foodcategory, String year) {
        this.title = title;
        this.foodcategory = foodcategory;
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getFoodcategory() {
        return foodcategory;
    }

    public void setFoodcategory(String foodcategory) {
        this.foodcategory = foodcategory;
    }
}