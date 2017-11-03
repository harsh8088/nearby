package com.hrawat.nearby.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hrawat on 9/28/2017.
 */
public class ApiClient {

    private static final String BASE_URL = "http://api.themoviedb.org/3/";
    //    private static final String PLACE_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/";
    private static final String PLACE_URL = "https://maps.googleapis.com/maps/api/place/";
    private static final String PLACE_DETAILS = "https://maps.googleapis.com/maps/api/place/details/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getPlacesClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(PLACE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
