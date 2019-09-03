package com.konarktimes.konark.Common;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class HorizontalSpacingItemDecoration extends RecyclerView.ItemDecoration {
    private final int horizontalSpacing;
    @Override

    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.left=horizontalSpacing;
    }

    public HorizontalSpacingItemDecoration(int horizontalSpacing) {
        this.horizontalSpacing = horizontalSpacing;

    }
}
