package com.ms.kk.module.main.home.drama;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.ms.kk.R;
import com.ms.kk.base.BaseRVAdapter;
import com.ms.kk.databinding.LayoutItemDramaListBinding;
import com.ms.kk.model.net.entity.respond.DramaItem;

public class DramaListAdapter extends BaseRVAdapter<DramaItem, BaseRVAdapter.ViewHolder<LayoutItemDramaListBinding>> {
    @NonNull
    @Override
    public ViewHolder<LayoutItemDramaListBinding> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutItemDramaListBinding binding = LayoutItemDramaListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new BaseRVAdapter.ViewHolder<LayoutItemDramaListBinding>(binding);
    }

    @Override
    protected void handleData(ViewHolder<LayoutItemDramaListBinding> holder, DramaItem dramaItem, int position) {
        holder.viewDataBinding.setDrama(dramaItem);
    }
}
