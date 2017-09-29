package com.hrawat.nearby.network;

import com.hrawat.nearby.activity.model.SearchModel.SearchResults;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by hrawat on 9/28/2017.
 */
public interface ApiInterface {

    @POST("json")
    Call<SearchResults> getNearByPlaces(@Query("location") String location,
                                        @Query("radius") String radius,
                                        @Query("type") String type,
                                        @Query("keyword") String keyword,
                                        @Query("key") String apiServerKey);
}

