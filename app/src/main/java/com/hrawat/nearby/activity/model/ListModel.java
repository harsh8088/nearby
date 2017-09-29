package com.hrawat.nearby.activity.model;

/**
 * Created by sanjum on 9/21/2017.
 */

public class ListModel {

    private String name, address;

    public ListModel(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
}