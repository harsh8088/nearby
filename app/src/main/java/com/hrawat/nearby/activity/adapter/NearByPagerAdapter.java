package com.hrawat.nearby.activity.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.google.gson.Gson;
import com.hrawat.nearby.activity.fragment.TabFragmentDetails;
import com.hrawat.nearby.activity.fragment.TabFragmentPhotos;
import com.hrawat.nearby.activity.fragment.TabFragmentReviews;
import com.hrawat.nearby.activity.model.searchModel.PlaceResultModel;

/**
 * Created by hrawat on 11/3/2017.
 */
public class NearByPagerAdapter extends FragmentStatePagerAdapter {

    private int mNumOfTabs;
    private PlaceResultModel placeResultModel;

    public NearByPagerAdapter(FragmentManager fm, int NumOfTabs, String placeModelString) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        placeResultModel = new Gson().fromJson(placeModelString, PlaceResultModel.class);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return TabFragmentDetails.newInstance(placeResultModel.getPlace_id());
            case 1:
                return TabFragmentReviews.newInstance(placeResultModel.getPlace_id());
            case 2:
                return TabFragmentPhotos.newInstance(placeResultModel.getPlace_id());
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}