package com.ms.kk.module.photo.album;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.ms.kk.base.BaseRVAdapter;
import com.ms.kk.databinding.LayoutItemAlbumBinding;

public class AlbumListAdapter extends BaseRVAdapter<String, BaseRVAdapter.ViewHolder<LayoutItemAlbumBinding>> {
    @NonNull
    @Override
    public ViewHolder<LayoutItemAlbumBinding> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutItemAlbumBinding binding = LayoutItemAlbumBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder<>(binding);
    }

    @Override
    protected void handleData(ViewHolder<LayoutItemAlbumBinding> holder, String s, int position) {
        Glide.with(mContext).load(s).into(holder.viewDataBinding.igShow);
    }
}
