package com.huajia.venusdesktop.service.application.music.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 环形进度条
 * @author: huajia
 * @date: 2023/10/14 23:49
 */

public class CircularProgressBar extends View {
    private static final float BAR_WIDTH = 10;

    //圆的margin
    private static final float CIRCLE_MARGIN = 30;

    private int mProgress;

    private int mMaxProgress = 100;

    private int mCircleColor = Color.parseColor("#AB9CF9");

    private int mProgressColor = Color.parseColor("#7CCD7C");

    public CircularProgressBar(Context context) {
        super(context);
    }

    public CircularProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CircularProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CircularProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setProgress(int progress){
        this.mProgress = progress;
        invalidate();
    }

    public void setMaxProgress(int maxProgress){
        this.mMaxProgress = maxProgress;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int radius = Math.min(centerX, centerY);

        Paint paint = new Paint();
        paint.setColor(mCircleColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(BAR_WIDTH);

        canvas.drawCircle(centerX, centerY, radius - CIRCLE_MARGIN, paint);

        paint.setColor(mProgressColor);
        RectF rect = new RectF(centerX - radius + CIRCLE_MARGIN, centerY - radius + CIRCLE_MARGIN, centerX + radius - CIRCLE_MARGIN, centerY + radius - CIRCLE_MARGIN);
        float sweepAngle = 360f * ((float) mProgress / mMaxProgress);
        canvas.drawArc(rect, -90, sweepAngle, false, paint);
    }
}
