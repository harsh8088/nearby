package com.hrawat.nearby.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.hrawat.nearby.R;
import com.hrawat.nearby.activity.model.SearchModel.PlaceResultModel;
import com.orhanobut.hawk.Hawk;

import static com.hrawat.nearby.activity.HomeActivity.LOCATION_LATITUDE;
import static com.hrawat.nearby.activity.HomeActivity.LOCATION_LONGITUTE;

public class DetailsActivity extends AppCompatActivity implements OnMapReadyCallback {

    public static final String BUNDLE_EXTRA_PLACE = "BUNDLE_EXTRA_PLACE";
    private PlaceResultModel placeResultModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(BUNDLE_EXTRA_PLACE)) {
            Gson gson = new Gson();
            placeResultModel = gson.fromJson(bundle.getString(BUNDLE_EXTRA_PLACE), PlaceResultModel.class);
        }
        init();
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
                (Double) Hawk.get(LOCATION_LONGITUTE));
        // Add a marker in current location and move the camera
        googleMap.addMarker(new MarkerOptions().position(currentLocation).title("You are here"));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
        double lat = placeResultModel.getGeometry().getLocation().getLat();
        double lng = placeResultModel.getGeometry().getLocation().getLng();
        LatLng latLng = new LatLng(lat, lng);
        googleMap.addMarker(new MarkerOptions().position(latLng).title("Destination"));

    }
}
