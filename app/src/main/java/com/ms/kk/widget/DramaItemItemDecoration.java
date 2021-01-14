package com.ms.kk.widget;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ms.kk.utils.SystemUtils;

public class DramaItemItemDecoration extends RecyclerView.ItemDecoration {

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view);
        outRect.top = SystemUtils.dp2px(view.getContext(), 5f);
        outRect.bottom = SystemUtils.dp2px(view.getContext(), 5f);
        if (position % 2 == 0) {
            outRect.left = SystemUtils.dp2px(view.getContext(), 15f);
            outRect.right = SystemUtils.dp2px(view.getContext(), 5f);
        } else {
            outRect.left = SystemUtils.dp2px(view.getContext(), 5f);
            outRect.right = SystemUtils.dp2px(view.getContext(), 15f);
        }

    }
}
