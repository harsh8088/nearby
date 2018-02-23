package com.hrawat.nearby.activity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.hrawat.nearby.R;
import com.hrawat.nearby.activity.model.placeModel.ReviewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hrawat on 11/3/2017.
 **/
public class ReviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ReviewModel> reviewList;
    private Context context;
    private static final int TYPE_LOADING = 1;
    private static final int TYPE_LIST = TYPE_LOADING + 1;
    private static final int TYPE_EMPTY = TYPE_LIST + 1;
    private boolean isLoading;

    public ReviewAdapter(Context context) {
        this.reviewList = new ArrayList<>();
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_LIST:
                return new ReviewAdapter.MyViewHolder(LayoutInflater.from(context)
                        .inflate(R.layout.item_review_list, parent, false));
            case TYPE_LOADING:
                return new ReviewAdapter.LoadingViewHolder((LayoutInflater.from(context)
                        .inflate(R.layout.item_loading, parent, false)));
            case TYPE_EMPTY:
            default:
                return new ReviewAdapter.EmptyViewHolder(LayoutInflater.from(context)
                        .inflate(R.layout.item_empty_reviews, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ReviewAdapter.MyViewHolder) {
            ReviewAdapter.MyViewHolder myViewHolder = (ReviewAdapter.MyViewHolder) holder;
            ReviewModel details = reviewList.get(position);
            myViewHolder.rating.setRating(details.getRating());
            myViewHolder.tv_review.setText(details.getText());
            myViewHolder.tv_reviewer.setText(details.getAuthor_name());
            myViewHolder.tv_date.setText(details.getRelative_time_description());
        } else if (holder instanceof ReviewAdapter.EmptyViewHolder) {
            ReviewAdapter.EmptyViewHolder emptyViewHolder = (ReviewAdapter.EmptyViewHolder) holder;
        } else if (holder instanceof ReviewAdapter.LoadingViewHolder) {
            ReviewAdapter.LoadingViewHolder loadingViewHolder = (ReviewAdapter.LoadingViewHolder) holder;
        }
    }

    public void replaceAll(ArrayList<ReviewModel> reviewModels) {
        this.reviewList.clear();
        this.reviewList.addAll(reviewModels);
        this.isLoading = false;
        this.notifyDataSetChanged();
    }

    public void clearAll() {
        this.isLoading = false;
        this.reviewList.clear();
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (isLoading)
            return 1;
        else
            return reviewList.size() == 0 ? 1 : reviewList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isLoading)
            return TYPE_LOADING;
        if (reviewList.size() == 0)
            return TYPE_EMPTY;
        else
            return TYPE_LIST;
    }

    public void startLoading() {
        this.isLoading = true;
        notifyDataSetChanged();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {

        private RatingBar rating;
        private TextView tv_review;
        private TextView tv_reviewer;
        private TextView tv_date;

        MyViewHolder(View itemView) {
            super(itemView);
            rating = itemView.findViewById(R.id.rb_rating);
            tv_review = itemView.findViewById(R.id.tv_review);
            tv_reviewer = itemView.findViewById(R.id.tv_reviewer);
            tv_date = itemView.findViewById(R.id.tv_date);
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
