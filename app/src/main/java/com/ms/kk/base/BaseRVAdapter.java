package com.ms.kk.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRVAdapter<D, V extends BaseRVAdapter.ViewHolder> extends RecyclerView.Adapter<V> {
    protected OnItemClickListener<D> onItemClickListener;
    protected List<D> dataList;
    protected Context mContext;

    public BaseRVAdapter() {
        setHasStableIds(true);
    }


    public void refresh(List<D> list) {
        dataList = list;
        notifyDataSetChanged();
    }

    public void loadMore(List<D> list) {
        if (dataList == null)
            dataList = new ArrayList<>();
        int start = dataList.size();
        dataList.addAll(list);
        notifyItemRangeChanged(start, list.size());
    }


    public void setOnItemClickListener(OnItemClickListener<D> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public abstract V onCreateViewHolder(@NonNull ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(@NonNull V holder, int position) {
        mContext = holder.itemView.getContext();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener == null)
                    return;
                onItemClickListener.onItemClick(dataList.get(position), position);
            }
        });

        handleData(((V) holder), dataList.get(position), position);

    }

    protected abstract void handleData(V holder, D d, int position);

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    public static class ViewHolder<B extends ViewDataBinding> extends RecyclerView.ViewHolder {
        public B viewDataBinding;

        public ViewHolder(B viewDataBinding) {
            super(viewDataBinding.getRoot());
            this.viewDataBinding = viewDataBinding;
        }

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public interface OnItemClickListener<D> {
        void onItemClick(D data, int pos);
    }

}
