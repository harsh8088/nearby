package com.hrawat.nearby.activity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hrawat.nearby.R;
import com.hrawat.nearby.activity.adapter.ReviewAdapter;
import com.hrawat.nearby.activity.model.placeModel.PlaceDetailModel;
import com.hrawat.nearby.activity.model.placeModel.PlaceDetailResults;
import com.hrawat.nearby.network.ApiClient;
import com.hrawat.nearby.network.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hrawat on 11/3/2017.
 */
public class TabFragmentReviews extends Fragment {

    private static final String TAG = TabFragmentDetails.class.getSimpleName();
    private RecyclerView recyclerView;
    private ReviewAdapter reviewAdapter;
    private String placeId;

    public static TabFragmentReviews newInstance(String placeId) {
        TabFragmentReviews fragment = new TabFragmentReviews();
        Bundle bundle = new Bundle();
        bundle.putString("PLACE_ID", placeId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_fragment_reviews, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments().get("PLACE_ID") != null) {
            placeId = getArguments().get("PLACE_ID").toString();
        }
        initView(view);
    }

    private void initView(View view) {
        recyclerView = (view).findViewById(R.id.rv_reviews);
        reviewAdapter = new ReviewAdapter(getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(reviewAdapter);
        fetchReviews();
    }

    private void fetchReviews() {
        ApiInterface apiService =
                ApiClient.getPlacesClient().create(ApiInterface.class);
        Call<PlaceDetailResults> call = apiService.getDetailsByPlaces(placeId,
                "AIzaSyChQ0n-vud41n-_pz-nXBiDJTQrG7F0CJs");
        call.enqueue(new Callback<PlaceDetailResults>() {
            @Override
            public void onResponse(Call<PlaceDetailResults> call, Response<PlaceDetailResults> response) {
                String status = response.body().getStatus();
                switch (status) {
                    case "OK":
                        PlaceDetailModel placeDetailModel = response.body().getResult();
                        if (placeDetailModel.getReviews() != null) {
                            reviewAdapter.replaceAll(placeDetailModel.getReviews());
                            Log.d(TAG, "Number of Reviews : " + placeDetailModel.getReviews().size());
                        } else
                            reviewAdapter.clearAll();
                        break;
                    case "ZERO_RESULTS":
                        Toast.makeText(getContext(), "No such results!!!",
                                Toast.LENGTH_SHORT).show();
                        break;
                    default:
                    case "REQUEST_DENIED":
                        Log.d(TAG, "Access Denied : " + response.body().getErrorMessage());
                        break;
                }
            }

            @Override
            public void onFailure(Call<PlaceDetailResults> call, Throwable t) {
                Log.d(TAG, "Error : " + t.toString());
            }
        });
        Log.d(TAG, "fetch_hit");
    }
}