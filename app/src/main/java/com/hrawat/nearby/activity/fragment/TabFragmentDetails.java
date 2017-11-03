package com.hrawat.nearby.activity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hrawat.nearby.R;

/**
 * Created by hrawat on 11/3/2017.
 */
public class TabFragmentDetails extends Fragment {

    private String placeId;

    public static TabFragmentDetails newInstance(String placeId) {
        TabFragmentDetails fragment = new TabFragmentDetails();
        Bundle bundle = new Bundle();
        bundle.putString("PLACE_ID", placeId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_fragment_details, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments().get("PLACE_ID") != null)
            placeId = getArguments().get("PLACE_ID").toString();
        initView(view);
    }

    private void initView(View view) {
    }
}