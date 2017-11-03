package com.hrawat.nearby.network;

import com.hrawat.nearby.activity.model.placeModel.PlaceDetailResults;
import com.hrawat.nearby.activity.model.searchModel.SearchResults;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by hrawat on 9/28/2017.
 */
public interface ApiInterface {

    @POST("nearbysearch/json")
    Call<SearchResults> getNearByPlaces(@Query("location") String location,
                                        @Query("radius") String radius,
                                        @Query("type") String type,
                                        @Query("keyword") String keyword,
                                        @Query("key") String apiServerKey);

    @POST("details/json")
    Call<PlaceDetailResults> getDetailsByPlaces(@Query("placeid") String placeId,
                                                @Query("key") String apiServerKey);
}

