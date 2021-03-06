package com.hrawat.nearby.activity.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.SharedElementCallback;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hrawat.nearby.R;
import com.hrawat.nearby.activity.DetailsActivity;
import com.hrawat.nearby.activity.adapter.PhotosAdapter;
import com.hrawat.nearby.activity.model.placeModel.PlaceDetailModel;
import com.hrawat.nearby.activity.model.placeModel.PlaceDetailResults;
import com.hrawat.nearby.network.ApiClient;
import com.hrawat.nearby.network.ApiInterface;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hrawat on 11/3/2017.
 */
public class TabFragmentPhotos extends Fragment {

    private static final String TAG = TabFragmentPhotos.class.getSimpleName();
    private PhotosAdapter photoAdapter;
    private String placeId;
    private RecyclerView photoRecyclerView;

    public static TabFragmentPhotos newInstance(String placeId) {
        TabFragmentPhotos fragment = new TabFragmentPhotos();
        Bundle bundle = new Bundle();
        bundle.putString("PLACE_ID", placeId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_fragment_photos, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments().get("PLACE_ID") != null)
            placeId = getArguments().get("PLACE_ID").toString();
        initView(view);
        setExitSharedElementCallback(
                new SharedElementCallback() {
                    @Override
                    public void onMapSharedElements(
                            List<String> names, Map<String, View> sharedElements) {
                        // Locate the ViewHolder for the clicked position.
                        RecyclerView.ViewHolder selectedViewHolder = photoRecyclerView
                                .findViewHolderForAdapterPosition(DetailsActivity.currentPosition);
                        if (selectedViewHolder == null || selectedViewHolder.itemView == null) {
                            return;
                        }
                        // Map the first shared element name to the child ImageView.
                        sharedElements
                                .put(names.get(0),
                                        selectedViewHolder.itemView.findViewById(R.id.iv_photo));
                    }
                });
    }

    private void initView(View view) {
        photoRecyclerView = (view).findViewById(R.id.rv_photos);
        photoAdapter = new PhotosAdapter(getContext());
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        photoRecyclerView.setLayoutManager(mLayoutManager);
        photoRecyclerView.setAdapter(photoAdapter);
        photoAdapter.startLoading();
        fetchPhotos();
        photoAdapter.setClickListener(new PhotosAdapter.ClickListener() {
            @Override
            public void onPhotoClick(String photoUrl) {
                showImageDialog(photoUrl);
            }
        });
    }

    private void fetchPhotos() {
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
                        if (placeDetailModel.getPhotos() != null) {
                            photoAdapter.replaceAll(placeDetailModel.getPhotos());
                            Log.d(TAG, "Number of Reviews : " + placeDetailModel.getReviews().size());
                        } else
                            photoAdapter.clearAll();
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
    }

    private void showImageDialog(String photoUrl) {
        final Dialog dialog = new Dialog(getContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_image);
        ImageView ivPhoto = dialog.findViewById(R.id.iv_photo);
        Glide.with(getContext()).load(photoUrl).into(ivPhoto);
        final ImageView imageExit = dialog.findViewById(R.id.iv_close);
        imageExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}