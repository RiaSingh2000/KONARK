package com.konarktimes.konark.Common;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class VerticalSpacingItemDecoration extends RecyclerView.ItemDecoration {
    private final int verticalSpacing;
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.bottom=verticalSpacing;
    }

    public VerticalSpacingItemDecoration(int verticalSpacing) {
        this.verticalSpacing = verticalSpacing;

    }
}
