package com.ms.kk.databinding;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.ms.kk.R;
import com.ms.kk.utils.SystemUtils;
import com.ms.kk.widget.ZyRecycleView;

public class DataBindingAdapter {

    @BindingAdapter("avatar")
    public static void setAvatar(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).circleCrop().placeholder(R.drawable.ic_default_avatar).error(R.drawable.ic_default_avatar).into(imageView);
    }

    @BindingAdapter("thumb")
    public static void setThumb(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).into(imageView);
    }

    @BindingAdapter(("canLoadMore"))
    public static void setCanLoadMore(ZyRecycleView zyRecycleView, boolean canLoadMore) {
        zyRecycleView.setCanLoadMore(canLoadMore);
    }

}
