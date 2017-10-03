package com.hrawat.nearby;

import android.app.Application;

import com.orhanobut.hawk.Hawk;

/**
 * Created by hrawat on 10/3/2017.
 */
public class NearbyApplication extends Application {

    public static final String TAG = NearbyApplication.class.getSimpleName();
    private static NearbyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Hawk.init(this).build();
    }

    public NearbyApplication getInstance() {
        return instance;
    }
}
