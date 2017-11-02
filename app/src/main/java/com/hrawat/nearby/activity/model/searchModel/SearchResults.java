package com.hrawat.nearby.activity.model.searchModel;

import java.util.ArrayList;

/**
 * Created by hrawat on 9/28/2017.
 */
public class SearchResults {

    private String status;
    private ArrayList<PlaceResultModel> results;
    private String errorMessage;

    public String getStatus() {
        return status;
    }

    public ArrayList<PlaceResultModel> getResults() {
        return results;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
