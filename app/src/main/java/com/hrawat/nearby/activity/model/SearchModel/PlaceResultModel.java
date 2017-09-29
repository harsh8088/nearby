package com.hrawat.nearby.activity.model.SearchModel;

import java.util.ArrayList;

/**
 * Created by hrawat on 9/28/2017.
 */
public class PlaceResultModel {
    private GeometryModel geometry;
    private String icon;
    private String id;
    private String name;
    private OpeningHours opening_hours;
    private ArrayList<PhotosModel> photos;
    private String place_id;
    private String rating;
    private String reference;
    private String scope;
    private ArrayList<String> types;
    private String vicinity;

    public GeometryModel getGeometry() {
        return geometry;
    }

    public String getIcon() {
        return icon;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public OpeningHours getOpening_hours() {
        return opening_hours;
    }

    public ArrayList<PhotosModel> getPhotos() {
        return photos;
    }

    public String getPlace_id() {
        return place_id;
    }

    public String getRating() {
        return rating;
    }

    public String getReference() {
        return reference;
    }

    public String getScope() {
        return scope;
    }

    public ArrayList<String> getTypes() {
        return types;
    }

    public String getVicinity() {
        return vicinity;
    }
}
