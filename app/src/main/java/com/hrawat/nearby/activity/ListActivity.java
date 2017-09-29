package com.hrawat.nearby.activity;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.hrawat.nearby.R;
import com.hrawat.nearby.activity.adapter.ListAdapter;
import com.hrawat.nearby.activity.model.ListModel;
import com.hrawat.nearby.activity.model.SearchModel.PlaceResultModel;
import com.hrawat.nearby.activity.model.SearchModel.SearchResults;
import com.hrawat.nearby.network.ApiClient;
import com.hrawat.nearby.network.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListActivity extends AppCompatActivity {

    private String TAG = this.getClass().getSimpleName();
    private List<ListModel> listModels = new ArrayList<>();
    private ListAdapter listAdapter;
    private EditText etSearch;
    protected Location mLastLocation;
    private FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        listAdapter = new ListAdapter(this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(listAdapter);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        etSearch = (EditText) findViewById(R.id.et_action_search);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() >= 3) {
                    String searchfor = editable.toString();
                    searchNearby(String.format("%s,%s", mLastLocation.getLatitude(),
                            mLastLocation.getLongitude()), searchfor, searchfor, "5000");
                }
            }
        });
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getLastLocation();
    }

    /**
     * Provides a simple way of getting a device's location and is well suited for
     * applications that do not require a fine-grained location and that do not need location
     * updates. Gets the best and most recent location currently available, which may be null
     * in rare cases when a location is not available.
     * <p>
     * Note: this method should be called after location permission has been granted.
     */
    @SuppressWarnings("MissingPermission")
    private void getLastLocation() {
        mFusedLocationClient.getLastLocation()
                .addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            mLastLocation = task.getResult();
                        } else {
                            Log.w(TAG, "getLastLocation:exception", task.getException());
                            Toast.makeText(ListActivity.this, "no_location_detected",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void searchNearby(String LatLongString, String searchfor, String keyword, String searchWithin) {
        ApiInterface apiService =
                ApiClient.getPlacesClient().create(ApiInterface.class);
        Call<SearchResults> call = apiService.getNearByPlaces(LatLongString, searchWithin,
                searchfor, keyword, "AIzaSyChQ0n-vud41n-_pz-nXBiDJTQrG7F0CJs");
        call.enqueue(new Callback<SearchResults>() {
            @Override
            public void onResponse(Call<SearchResults> call, Response<SearchResults> response) {
                String status = response.body().getStatus();
                switch (status) {
                    case "OK":
                        ArrayList<PlaceResultModel> places = response.body().getResults();
                        ArrayList<ListModel> listModels = new ArrayList<>();
                        for (PlaceResultModel placeResultModel : places) {
                            listModels.add(new ListModel(placeResultModel.getName(),
                                    placeResultModel.getVicinity()));
                        }
                        listAdapter.replaceAll(listModels);
                        Log.d(TAG, "Number of Places : " + places.size());
                        Toast.makeText(ListActivity.this, "Number of Places Found : " + places.size(),
                                Toast.LENGTH_SHORT).show();
                        break;
                    case "ZERO_RESULTS":
                        Toast.makeText(ListActivity.this, "No such results!!!",
                                Toast.LENGTH_SHORT).show();
                        break;
                    default:
                    case "REQUEST_DENIED":
                        Log.d(TAG, "Access Denied : " + response.body().getErrorMessage());
                        break;
                }
            }

            @Override
            public void onFailure(Call<SearchResults> call, Throwable t) {
                Log.d(TAG, "Error : " + t.toString());
            }
        });
    }

}