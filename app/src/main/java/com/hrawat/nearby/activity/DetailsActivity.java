package com.hrawat.nearby.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

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
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        getList();
        mAdapter = new DetailAdapter(detailsList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);

    }


    private void getList() {
        Details details = new Details("Noah", "Veg & NonVeg", "2015");
        detailsList.add(details);
        details = new Details("Noah", "Veg & NonVeg", "2015");
        detailsList.add(details);
        details = new Details("Pakwan", "Veg", "2015");
        detailsList.add(details);
        details = new Details("Nazeer", "NonVeg", "2015");
        detailsList.add(details);
        details = new Details("AlBeg", "NonVeg", "2015");
        detailsList.add(details);
        details = new Details("Nazeer", "NonVeg", "2015");
        detailsList.add(details);
        details = new Details("Up", "NonVeg", "2009");
        detailsList.add(details);
        details = new Details("Nazeer", "NonVeg", "2009");
        detailsList.add(details);
        details = new Details("Noah", "Non Veg", "2014");
        detailsList.add(details);
        details = new Details("Noah", "NonVeg", "2008");
        detailsList.add(details);
        details = new Details("Galaxy", "NonVeg", "1986");
        detailsList.add(details);
        details = new Details("Chicken Run", "Non Veg", "2000");
        detailsList.add(details);

    }
}