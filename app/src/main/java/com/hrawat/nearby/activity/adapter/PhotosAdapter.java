package com.hrawat.nearby.activity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hrawat.nearby.R;
import com.hrawat.nearby.activity.model.searchModel.PhotosModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hrawat on 11/3/2017.
 */
public class PhotosAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<PhotosModel> photosList;
    private Context context;
    private static final String IMAGE_URL =
            "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=";
    private static final int TYPE_LOADING = 1;
    private static final int TYPE_LIST = TYPE_LOADING + 1;
    private static final int TYPE_EMPTY = TYPE_LIST + 1;
    private boolean isLoading;
    private ClickListener clickListener;

    public interface ClickListener {

        void onPhotoClick(String url);
    }

    public PhotosAdapter(Context context) {
        this.photosList = new ArrayList<>();
        this.context = context;
    }

    public void setClickListener(PhotosAdapter.ClickListener listener) {
        this.clickListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_LIST:
                return new PhotosAdapter.MyViewHolder(LayoutInflater.from(context)
                        .inflate(R.layout.item_photos, parent, false));
            case TYPE_LOADING:
                return new PhotosAdapter.LoadingViewHolder((LayoutInflater.from(context)
                        .inflate(R.layout.item_loading, parent, false)));
            case TYPE_EMPTY:
            default:
                return new PhotosAdapter.EmptyViewHolder(LayoutInflater.from(context)
                        .inflate(R.layout.item_empty_photos, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof PhotosAdapter.MyViewHolder) {
            PhotosAdapter.MyViewHolder myViewHolder = (PhotosAdapter.MyViewHolder) holder;
            PhotosModel photosModel = photosList.get(position);
            final String photoUrl = IMAGE_URL + photosModel.getPhotoReference() + "&key=AIzaSyChQ0n-vud41n-_pz-nXBiDJTQrG7F0CJs";
            Glide.with(context).load(photoUrl).into(myViewHolder.ivPhoto);
            myViewHolder.ivPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onPhotoClick(photoUrl);
                }
            });
        } else if (holder instanceof PhotosAdapter.EmptyViewHolder) {
            PhotosAdapter.EmptyViewHolder emptyViewHolder = (PhotosAdapter.EmptyViewHolder) holder;
        } else if (holder instanceof PhotosAdapter.LoadingViewHolder) {
            PhotosAdapter.LoadingViewHolder loadingViewHolder = (PhotosAdapter.LoadingViewHolder) holder;
        }
    }

    public void replaceAll(ArrayList<PhotosModel> photosModels) {
        this.photosList.clear();
        this.photosList.addAll(photosModels);
        this.isLoading = false;
        this.notifyDataSetChanged();
    }

    public void clearAll() {
        this.isLoading = false;
        this.photosList.clear();
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (isLoading)
            return 1;
        else
            return photosList.size() == 0 ? 1 : photosList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isLoading)
            return TYPE_LOADING;
        if (photosList.size() == 0)
            return TYPE_EMPTY;
        else
            return TYPE_LIST;
    }

    public void startLoading() {
        this.isLoading = true;
        notifyDataSetChanged();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivPhoto;

        MyViewHolder(View itemView) {
            super(itemView);
            ivPhoto = itemView.findViewById(R.id.iv_photo);
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

