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
public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ListModel> items;
    private Context context;
    private static final int TYPE_LOADING = 1;
    private static final int TYPE_LIST = TYPE_LOADING + 1;
    private static final int TYPE_EMPTY = TYPE_LIST + 1;
    private boolean isloading;

    public ListAdapter(Context context) {
        this.items = new ArrayList<>();
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_LIST:
                return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_list, parent, false));
            case TYPE_LOADING:
                return new LoadingViewHolder((LayoutInflater.from(context).inflate(R.layout.item_loading, parent, false)));
            case TYPE_EMPTY:
            default:
                return new EmptyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_empty, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            ListModel details = items.get(position);
            myViewHolder.tvName.setText(details.getName());
            myViewHolder.tvAddress.setText(details.getAddress());
        } else if (holder instanceof EmptyViewHolder) {
            EmptyViewHolder emptyViewHolder = (EmptyViewHolder) holder;
        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
        }
    }

    public void replaceAll(ArrayList<ListModel> listModels) {
        this.items.clear();
        this.items.addAll(listModels);
        this.isloading = false;
        this.notifyDataSetChanged();
    }

    public void clearAll() {
        this.isloading = false;
        this.items.clear();
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (isloading)
            return 1;
        else
            return items.size() == 0 ? 1 : items.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isloading)
            return TYPE_LOADING;
        if (items.size() == 0)
            return TYPE_EMPTY;
        else
            return TYPE_LIST;
    }

    public void startLoading() {
        this.isloading = true;
        notifyDataSetChanged();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName;
        private TextView tvAddress;

        MyViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.title);
            tvAddress = itemView.findViewById(R.id.address);
        }
    }

    private class EmptyViewHolder extends RecyclerView.ViewHolder {

        EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {

        LoadingViewHolder(View itemView) {
            super(itemView);
        }
    }
}
