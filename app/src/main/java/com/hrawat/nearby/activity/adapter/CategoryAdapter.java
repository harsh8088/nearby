package com.hrawat.nearby.activity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hrawat.nearby.R;
import com.hrawat.nearby.activity.model.NearByCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hrawat on 9/25/2017.
 */
public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<NearByCategory> nearByCategoryList;
    private Context context;

    public CategoryAdapter(Context context) {
        this.nearByCategoryList = new ArrayList<>();
        this.nearByCategoryList = getDummyCategories();
        this.context = context;
    }

    private ArrayList<NearByCategory> getDummyCategories() {
        ArrayList<NearByCategory> dummyCategories = new ArrayList<>();
        dummyCategories.add(new NearByCategory("", "Bars", ""));
        dummyCategories.add(new NearByCategory("", "Shopping Malls", ""));
        dummyCategories.add(new NearByCategory("", "Service Center", ""));
        dummyCategories.add(new NearByCategory("", "Grocery Store", ""));
        dummyCategories.add(new NearByCategory("", "Petrol Pumps", ""));
        dummyCategories.add(new NearByCategory("", "Restaurants", ""));
        dummyCategories.add(new NearByCategory("", "Game Parlours", ""));
        dummyCategories.add(new NearByCategory("", "Hotels", ""));
        dummyCategories.add(new NearByCategory("", "Bus Station", ""));
        dummyCategories.add(new NearByCategory("", "Railway Station", ""));
        return dummyCategories;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_nearby_category_list, null);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        NearByCategory details = nearByCategoryList.get(position);
        CategoryViewHolder viewHolder = (CategoryViewHolder) holder;
//        viewHolder.icon.(details.getIcon());
//        viewHolder.background
        viewHolder.categoryName.setText(details.getName());
    }

    @Override
    public int getItemCount() {
        return nearByCategoryList.size();
    }

    public void addAllCategories(ArrayList<NearByCategory> nearByCategories) {
        this.nearByCategoryList.clear();
        this.nearByCategoryList.addAll(nearByCategories);
        this.notifyDataSetChanged();
    }

    private class CategoryViewHolder extends RecyclerView.ViewHolder {

        private ImageView background;
        private ImageView icon;
        private TextView categoryName;

        CategoryViewHolder(View view) {
            super(view);
            background = view.findViewById(R.id.iv_category_background);
            icon = view.findViewById(R.id.iv_category_icon);
            categoryName = view.findViewById(R.id.tv_category_name);
        }
    }
}
