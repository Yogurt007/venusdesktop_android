package com.huajia.venusdesktop.service.application.camera;

import android.graphics.Outline;
import android.view.View;
import android.view.ViewOutlineProvider;

public class RoundedCornerOutlineProvider extends ViewOutlineProvider {
    private int cornerRadius;

    public RoundedCornerOutlineProvider(int cornerRadius) {
        this.cornerRadius = cornerRadius;
    }

    @Override
    public void getOutline(View view, Outline outline) {
        outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), cornerRadius);
    }
}
