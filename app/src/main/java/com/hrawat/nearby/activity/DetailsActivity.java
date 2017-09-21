package com.hrawat.nearby.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.hrawat.nearby.R;
import com.hrawat.nearby.activity.adapter.DetailAdapter;
import com.hrawat.nearby.activity.model.Details;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    private List<Details> detailsList = new ArrayList<>();
    private DetailAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        getList();
        mAdapter = new DetailAdapter(detailsList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    private void getList() {
        Details details = new Details("Noah", "Veg & NonVeg", "Sec 49");
        detailsList.add(details);
        details = new Details("Noah", "Veg & NonVeg", "Sec 82");
        detailsList.add(details);
        details = new Details("Pakwan", "Veg", "Sec 49 , B block");
        detailsList.add(details);
        details = new Details("Nazeer", "NonVeg", "Sec 49");
        detailsList.add(details);
        details = new Details("AlBeg", "NonVeg", "Sec 55 , A block");
        detailsList.add(details);
        details = new Details("Nazeer", "NonVeg", "Sec 49");
        detailsList.add(details);
        details = new Details("Up", "NonVeg", "Sec 49");
        detailsList.add(details);
        details = new Details("Nazeer", "NonVeg", "Sec 49");
        detailsList.add(details);
        details = new Details("Noah", "Non Veg", "Sec 49");
        detailsList.add(details);
        details = new Details("Noah", "NonVeg", "Sec 49");
        detailsList.add(details);
        details = new Details("Galaxy", "NonVeg", "Sec 49");
        detailsList.add(details);
        details = new Details("Chicken Run", "Non Veg", "NSEZ");
        detailsList.add(details);

    }


}