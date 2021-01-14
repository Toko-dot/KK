package com.ms.kk.databinding;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.ms.kk.R;

public class DataBindingAdapter {

    @BindingAdapter("avatar")
    public static void setAvatar(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).circleCrop().placeholder(R.drawable.ic_default_avatar).error(R.drawable.ic_default_avatar).into(imageView);
    }

    @BindingAdapter("thumb")
    public static void setThumb(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).into(imageView);
    }
}
