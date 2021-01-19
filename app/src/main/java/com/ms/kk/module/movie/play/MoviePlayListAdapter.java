package com.ms.kk.module.movie.play;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.ms.kk.R;
import com.ms.kk.base.BaseRVAdapter;
import com.ms.kk.databinding.LayoutItemMoviePlayListBinding;
import com.ms.kk.model.net.entity.respond.MovieListItem;

public class MoviePlayListAdapter extends BaseRVAdapter<MovieListItem, BaseRVAdapter.ViewHolder<LayoutItemMoviePlayListBinding>> {
    private int select;

    public void setSelect(int select) {
        this.select = select;
        notifyDataSetChanged();
    }

    public int getSelect() {
        return select;
    }

    @NonNull
    @Override
    public ViewHolder<LayoutItemMoviePlayListBinding> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutItemMoviePlayListBinding binding = LayoutItemMoviePlayListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder<>(binding);
    }

    @Override
    protected void handleData(ViewHolder<LayoutItemMoviePlayListBinding> holder, MovieListItem movieListItem, int position) {
        holder.viewDataBinding.setMovie(movieListItem);
        if (position==select){
            holder.viewDataBinding.tvName.setTextColor(mContext.getResources().getColor(R.color.color_0099FF));
        }else {
            holder.viewDataBinding.tvName.setTextColor(mContext.getResources().getColor(R.color.color_777777));
        }

    }
}
