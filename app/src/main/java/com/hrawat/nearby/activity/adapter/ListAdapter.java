package com.hrawat.nearby.activity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hrawat.nearby.R;
import com.hrawat.nearby.activity.model.ListModel;
import com.hrawat.nearby.activity.model.SearchModel.PlaceResultModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sanjum on 9/21/2017.
 */
public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface ClickListener {

        void onListClick(ListAdapter adapter, int position);
    }

    private List<ListModel> items;
    private List<PlaceResultModel> placeResultModelList;
    private Context context;
    private static final int TYPE_LOADING = 1;
    private static final int TYPE_LIST = TYPE_LOADING + 1;
    private static final int TYPE_EMPTY = TYPE_LIST + 1;
    private boolean isLoading;
    private ClickListener clickListener;

    public ListAdapter(Context context) {
        this.items = new ArrayList<>();
        this.placeResultModelList = new ArrayList<>();
        this.context = context;
    }

    public void setClickListener(ClickListener listener) {
        this.clickListener = listener;
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
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            ListModel details = items.get(position);
//            PlaceResultModel placeResultModel = placeResultModelList.get(position);
            switch (placeResultModelList.get(position).getTypes().get(0)) {
                case "restaurant":
                case "cafe":
                    myViewHolder.relativeLayout.setBackground(context.getResources()
                            .getDrawable(R.drawable.category_image));
                    break;
                case "lodging":
                    myViewHolder.relativeLayout.setBackground(context.getResources()
                            .getDrawable(R.drawable.category_image));
                    break;
                case "night_club":
                case "bar":
                    myViewHolder.relativeLayout.setBackground(context.getResources()
                            .getDrawable(R.drawable.category_image));
                    break;
                case "grocery_or_supermarket":
                case "store":
                    myViewHolder.relativeLayout.setBackground(context.getResources()
                            .getDrawable(R.drawable.category_image));
                    break;
                case "hospital":
                    myViewHolder.relativeLayout.setBackground(context.getResources()
                            .getDrawable(R.drawable.category_image));
                    break;
                case "car_repair":
                    myViewHolder.relativeLayout.setBackground(context.getResources()
                            .getDrawable(R.drawable.category_image));
                    break;
                case "gym":
                case "health":
                    myViewHolder.relativeLayout.setBackground(context.getResources()
                            .getDrawable(R.drawable.category_image));
                    break;
                case "transit_station":
                case "bus_station":
                    myViewHolder.relativeLayout.setBackground(context.getResources()
                            .getDrawable(R.drawable.category_image));
                    break;
                case "gas_station":
                    myViewHolder.relativeLayout.setBackground(context.getResources()
                            .getDrawable(R.drawable.category_image));
                    break;
                case "police":
                    myViewHolder.relativeLayout.setBackground(context.getResources()
                            .getDrawable(R.drawable.category_image));
                    break;
                case "school":
                    myViewHolder.relativeLayout.setBackground(context.getResources()
                            .getDrawable(R.drawable.category_image));
                    break;
                case "shopping_mall":
                    myViewHolder.relativeLayout.setBackground(context.getResources()
                            .getDrawable(R.drawable.category_image));
                    break;
                default:
                    myViewHolder.relativeLayout.setBackground(context.getResources()
                            .getDrawable(R.drawable.orangebox));
                    break;
            }
            myViewHolder.tvName.setText(details.getName());
            myViewHolder.tvAddress.setText(details.getAddress());
            myViewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onListClick(ListAdapter.this, holder.getPosition());
                }
            });
        } else if (holder instanceof EmptyViewHolder) {
            EmptyViewHolder emptyViewHolder = (EmptyViewHolder) holder;
        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
        }
    }

    public void replaceAll(ArrayList<ListModel> listModels, ArrayList<PlaceResultModel> places) {
        this.items.clear();
        this.items.addAll(listModels);
        this.placeResultModelList.clear();
        this.placeResultModelList.addAll(places);
        this.isLoading = false;
//        this.notifyDataSetChanged();
    }

    public void clearAll() {
        this.isLoading = false;
        this.items.clear();
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (isLoading)
            return 1;
        else
            return items.size() == 0 ? 1 : items.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isLoading)
            return TYPE_LOADING;
        if (items.size() == 0)
            return TYPE_EMPTY;
        else
            return TYPE_LIST;
    }

    public String getPlace(int position) {
        if (placeResultModelList != null) {
            Gson gson = new Gson();
            return gson.toJson(placeResultModelList.get(position));
        }
        return null;
    }

    public void startLoading() {
        this.isLoading = true;
        notifyDataSetChanged();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName;
        private TextView tvAddress;
        private RelativeLayout relativeLayout;

        MyViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.title);
            tvAddress = itemView.findViewById(R.id.address);
            relativeLayout = itemView.findViewById(R.id.rl_item);
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
