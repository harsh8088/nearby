package com.hrawat.nearby.activity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hrawat.nearby.R;
import com.hrawat.nearby.activity.model.ListModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sanjum on 9/21/2017.
 */
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {

    private List<ListModel> items;
    private Context context;

    public ListAdapter(Context context) {
        this.items = new ArrayList<>();
        this.context = context;
    }

    @Override
    public ListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListAdapter.MyViewHolder holder, int position) {
        ListModel details = items.get(position);
        holder.tvName.setText(details.getName());
        holder.tvAddress.setText(details.getAddress());
    }

    public void replaceAll(ArrayList<ListModel> listModels) {
        this.items.clear();
        this.items.addAll(listModels);
        this.notifyDataSetChanged();
    }
    public void clearAll() {
        this.items.clear();
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName;
        private TextView tvAddress;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.title);
            tvAddress = itemView.findViewById(R.id.address);
        }
    }
}
