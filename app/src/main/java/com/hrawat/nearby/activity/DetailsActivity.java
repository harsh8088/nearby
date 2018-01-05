package com.hrawat.nearby.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.hrawat.nearby.R;
import com.hrawat.nearby.activity.adapter.NearByPagerAdapter;
import com.hrawat.nearby.activity.model.searchModel.PlaceResultModel;
import com.orhanobut.hawk.Hawk;

import static com.hrawat.nearby.activity.HomeActivity.LOCATION_LATITUDE;
import static com.hrawat.nearby.activity.HomeActivity.LOCATION_LONGITUDE;

public class DetailsActivity extends AppCompatActivity implements OnMapReadyCallback {

    public static final String BUNDLE_EXTRA_PLACE = "BUNDLE_EXTRA_PLACE";
    private PlaceResultModel placeResultModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(BUNDLE_EXTRA_PLACE)) {
            Gson gson = new Gson();
            placeResultModel = gson.fromJson(bundle.getString(BUNDLE_EXTRA_PLACE), PlaceResultModel.class);
        }
        init();
        initViewPager();
    }

    private void initViewPager() {
        final ImageView imageViewMoreDetails = findViewById(R.id.iv_more_details);
        imageViewMoreDetails.setBackground(getResources().getDrawable(R.drawable.ic_show_details));
        final LinearLayout linearLayoutMoreDetails = findViewById(R.id.ll_more_details);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Reviews"));
        tabLayout.addTab(tabLayout.newTab().setText("Photos"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final ViewPager viewPager = findViewById(R.id.pager);
        String placeResultString = new Gson().toJson(placeResultModel, PlaceResultModel.class);
        final NearByPagerAdapter adapter = new NearByPagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount(), placeResultString);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        imageViewMoreDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (linearLayoutMoreDetails.getVisibility() == View.GONE) {
                    linearLayoutMoreDetails.setVisibility(View.VISIBLE);
                    imageViewMoreDetails.setBackground(getResources().getDrawable(R.drawable.ic_hide_details));
                } else {
                    linearLayoutMoreDetails.setVisibility(View.GONE);
                    imageViewMoreDetails.setBackground(getResources().getDrawable(R.drawable.ic_show_details));
                }
            }
        });
    }

    private void init() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        TextView textViewName = (TextView) findViewById(R.id.tv_location_name);
        TextView textViewRating = (TextView) findViewById(R.id.tv_rating);
        ImageView imageViewStatus = (ImageView) findViewById(R.id.iv_status);
        if (placeResultModel != null) {
            textViewName.setText(placeResultModel.getName());
            textViewRating.setText(placeResultModel.getRating());
            if (placeResultModel.getOpening_hours() != null && !placeResultModel.getOpening_hours().getOpenNow())
                imageViewStatus.setBackground(getResources().getDrawable(R.drawable.ic_closed));
            else
                imageViewStatus.setBackground(getResources().getDrawable(R.drawable.ic_open));
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng currentLocation = new LatLng((Double) Hawk.get(LOCATION_LATITUDE),
                (Double) Hawk.get(LOCATION_LONGITUDE));
        double lat = placeResultModel.getGeometry().getLocation().getLat();
        double lng = placeResultModel.getGeometry().getLocation().getLng();
        LatLng latLng = new LatLng(lat, lng);
        googleMap.addMarker(new MarkerOptions().position(latLng).title("Destination"));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
    }
}
