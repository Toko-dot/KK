package com.ms.kk.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DownLoadSeekBar extends androidx.appcompat.widget.AppCompatSeekBar {
    public DownLoadSeekBar(@NonNull Context context) {
        super(context);
    }

    public DownLoadSeekBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
