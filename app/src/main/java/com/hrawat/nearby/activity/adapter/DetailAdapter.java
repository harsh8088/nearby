package com.hrawat.nearby.activity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hrawat.nearby.R;
import com.hrawat.nearby.activity.model.Details;

import java.util.List;

/**
 * Created by sanjum on 9/21/2017.
 */

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.MyViewHolder> {
    private List<Details> items;
    private Context context;

    public DetailAdapter(List<Details> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public DetailAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_detais_list, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DetailAdapter.MyViewHolder holder, int position) {
        Details details = items.get(position);
        holder.tvTitle.setText(details.getTitle());
        holder.tvFoodCategory.setText(details.getFoodcategory());
        holder.tvYear.setText(details.getYear());

    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private TextView tvFoodCategory;
        private TextView tvYear;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.title);
            tvFoodCategory = itemView.findViewById(R.id.foodcategory);
            tvYear = itemView.findViewById(R.id.year);
        }
    }
}