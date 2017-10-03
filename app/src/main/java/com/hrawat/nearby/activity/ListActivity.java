package com.hrawat.nearby.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.hrawat.nearby.R;
import com.hrawat.nearby.activity.adapter.ListAdapter;
import com.hrawat.nearby.activity.model.ListModel;
import com.hrawat.nearby.activity.model.SearchModel.PlaceResultModel;
import com.hrawat.nearby.activity.model.SearchModel.SearchResults;
import com.hrawat.nearby.network.ApiClient;
import com.hrawat.nearby.network.ApiInterface;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.hrawat.nearby.activity.HomeActivity.LOCATION_LATITUDE;
import static com.hrawat.nearby.activity.HomeActivity.LOCATION_LONGITUTE;

public class ListActivity extends AppCompatActivity {

    public static final String BUNDLE_EXTRA_CATEGORY_NAME = "BUNDLE_EXTRA_CATEGORY_NAME";
    private String TAG = this.getClass().getSimpleName();
    private ListAdapter listAdapter;
    private EditText etSearch;
    private String categoryName = "";
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.get(BUNDLE_EXTRA_CATEGORY_NAME) != null) {
            categoryName = (String) bundle.get(BUNDLE_EXTRA_CATEGORY_NAME);
        }
        init();
        if (bundle != null && bundle.containsKey(BUNDLE_EXTRA_CATEGORY_NAME)) {
            etSearch.setText(categoryName);
            searchNearby(categoryName, categoryName, "5000");
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void init() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        listAdapter = new ListAdapter(this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(listAdapter);
        etSearch = (EditText) findViewById(R.id.et_action_search);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(final Editable editable) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (editable.length() >= 3) {
                            String searchfor = editable.toString();
                            searchNearby(searchfor, searchfor, "5000");
                        } else {
                            listAdapter.clearAll();
                        }
                    }
                }, 1000);
            }
        });
    }

    public void showProgress() {
        hideProgress();
        progress = ProgressDialog.show(new ContextThemeWrapper(this,
                android.R.style.Theme_Holo_Light), "", "", true, false);
        progress.setContentView(R.layout.progress_bar);
    }

    public void hideProgress() {
        if (progress != null) {
            progress.dismiss();
        }
    }

    private void searchNearby(String searchfor, String keyword, String searchWithin) {
        showProgress();
        String LatLongString = String.format("%s,%s", Hawk.get(LOCATION_LATITUDE),
                Hawk.get(LOCATION_LONGITUTE));
        ApiInterface apiService =
                ApiClient.getPlacesClient().create(ApiInterface.class);
        Call<SearchResults> call = apiService.getNearByPlaces(LatLongString, searchWithin,
                searchfor, keyword, "AIzaSyChQ0n-vud41n-_pz-nXBiDJTQrG7F0CJs");
        call.enqueue(new Callback<SearchResults>() {
            @Override
            public void onResponse(Call<SearchResults> call, Response<SearchResults> response) {
                String status = response.body().getStatus();
                hideProgress();
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