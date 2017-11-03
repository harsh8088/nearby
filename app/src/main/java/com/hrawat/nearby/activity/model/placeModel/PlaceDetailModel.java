package com.hrawat.nearby.activity.model.placeModel;

import com.hrawat.nearby.activity.model.searchModel.PhotosModel;

import java.util.ArrayList;

/**
 * Created by hrawat on 11/3/2017.
 */
public class PlaceDetailModel {

    private ArrayList<PhotosModel> photos;
    private String place_id;
    private Float rating;
    private ArrayList<ReviewModel> reviews;

    public ArrayList<PhotosModel> getPhotos() {
        return photos;
    }

    public String getPlace_id() {
        return place_id;
    }

    public Float getRating() {
        return rating;
    }

    public ArrayList<ReviewModel> getReviews() {
        return reviews;
    }
}
