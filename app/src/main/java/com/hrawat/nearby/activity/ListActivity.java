package com.hrawat.nearby.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hrawat.nearby.R;
import com.hrawat.nearby.activity.adapter.ListAdapter;
import com.hrawat.nearby.activity.model.FilterModel;
import com.hrawat.nearby.activity.model.ListModel;
import com.hrawat.nearby.activity.model.searchModel.PlaceResultModel;
import com.hrawat.nearby.activity.model.searchModel.SearchResults;
import com.hrawat.nearby.network.ApiClient;
import com.hrawat.nearby.network.ApiInterface;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.hrawat.nearby.activity.DetailsActivity.BUNDLE_EXTRA_PLACE;
import static com.hrawat.nearby.activity.HomeActivity.LOCATION_LATITUDE;
import static com.hrawat.nearby.activity.HomeActivity.LOCATION_LONGITUDE;

public class ListActivity extends AppCompatActivity {

    public static final String BUNDLE_EXTRA_CATEGORY_NAME = "BUNDLE_EXTRA_CATEGORY_NAME";
    private String TAG = this.getClass().getSimpleName();
    private ListAdapter listAdapter;
    private EditText etSearch;
    private String categoryName = "";
    private RecyclerView recyclerViewList;

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
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void init() {
        recyclerViewList = (RecyclerView) findViewById(R.id.recycler_view);
        listAdapter = new ListAdapter(this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerViewList.setLayoutManager(mLayoutManager);
        recyclerViewList.setAdapter(listAdapter);
        etSearch = (EditText) findViewById(R.id.et_action_search);
        ImageView imageFilter = (ImageView) findViewById(R.id.iv_filter_search);
        imageFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFilterDialog();
            }
        });
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0)
                    listAdapter.clearAll();
            }

            @Override
            public void afterTextChanged(final Editable editable) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (editable.length() >= 3) {
                            String searchfor = editable.toString();
                            searchNearby(searchfor, searchfor, "5000");
                        }
                    }
                }, 1500);
            }
        });
        listAdapter.setClickListener(new ListAdapter.ClickListener() {
            @Override
            public void onListClick(ListAdapter adapter, int position) {
                Intent intent = new Intent(ListActivity.this, DetailsActivity.class);
                if (listAdapter.getPlace(position) != null)
                    intent.putExtra(BUNDLE_EXTRA_PLACE, listAdapter.getPlace(position));
                startActivity(intent);
            }
        });
    }

    private void runLayoutAnimation(final RecyclerView recyclerView) {
        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down);
        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }

    private void showFilterDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setTitle("Filter by Distance");
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.filter_dialog);
        final TextView textSeekBar = dialog.findViewById(R.id.tv_seek_bar_max);
        Button btnApply = dialog.findViewById(R.id.btn_apply);
        Button btnCancel = dialog.findViewById(R.id.btn_cancel);
        SeekBar seekBar = dialog.findViewById(R.id.seekbar_distance);
        dialog.show();
        if (Hawk.contains("FILTER")) {
            FilterModel filterModel = Hawk.get("FILTER");
            if (filterModel.isApplied()) {
                int distance = Integer.valueOf(filterModel.getDistance());
                distance = distance / 1000;
                textSeekBar.setText(String.valueOf(distance));
                seekBar.setProgress(distance);
            } else {
                textSeekBar.setText("5");
                seekBar.setProgress(5);
            }
        }
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (i >= 1)
                    textSeekBar.setText(String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int distance = Integer.valueOf(textSeekBar.getText().toString());
                distance = distance * 1000;
                FilterModel filterModel = new FilterModel(String.valueOf(distance), true);
                Hawk.put("FILTER", filterModel);
                if (!etSearch.getText().toString().isEmpty())
                    searchNearby(etSearch.getText().toString(), etSearch.getText().toString(),
                            String.valueOf(distance));
                dialog.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void searchNearby(String searchfor, String keyword, String searchWithin) {
        if (Hawk.contains("FILTER")) {
            FilterModel filterModel = Hawk.get("FILTER");
            if (filterModel != null && filterModel.isApplied()) {
                int distance = Integer.valueOf(filterModel.getDistance());
                searchWithin = String.valueOf(distance);
            }
        }
        listAdapter.startLoading();
        String LatLongString = String.format("%s,%s", Hawk.get(LOCATION_LATITUDE),
                Hawk.get(LOCATION_LONGITUDE));
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
                        listAdapter.replaceAll(listModels, places);
                        runLayoutAnimation(recyclerViewList);
                        Log.d(TAG, "Number of Places : " + places.size());
                        break;
                    case "ZERO_RESULTS":
                        listAdapter.clearAll();
                        Toast.makeText(ListActivity.this, "No such results!!!",
                                Toast.LENGTH_SHORT).show();
                        break;
                    default:
                    case "REQUEST_DENIED":
                        listAdapter.clearAll();
                        Log.d(TAG, "Access Denied : " + response.body().getErrorMessage());
                        break;
                }
            }

            @Override
            public void onFailure(Call<SearchResults> call, Throwable t) {
                listAdapter.replaceAll(new ArrayList<ListModel>(), new ArrayList<PlaceResultModel>());
                runLayoutAnimation(recyclerViewList);
                Log.d(TAG, "Error : " + t.toString());
            }
        });
    }
}