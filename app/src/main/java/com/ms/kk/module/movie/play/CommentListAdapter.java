package com.ms.kk.module.movie.play;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.ms.kk.base.BaseRVAdapter;
import com.ms.kk.databinding.LayoutItemCommentListBinding;
import com.ms.kk.model.net.entity.respond.Comment;

public class CommentListAdapter extends BaseRVAdapter<Comment, BaseRVAdapter.ViewHolder<LayoutItemCommentListBinding>> {
    @NonNull
    @Override
    public ViewHolder<LayoutItemCommentListBinding> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutItemCommentListBinding binding = LayoutItemCommentListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder<>(binding);
    }

    @Override
    protected void handleData(ViewHolder<LayoutItemCommentListBinding> holder, Comment comment, int position) {
        holder.viewDataBinding.setComment(comment);
    }
}
