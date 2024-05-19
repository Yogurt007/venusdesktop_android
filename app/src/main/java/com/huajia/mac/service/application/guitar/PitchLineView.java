package com.huajia.mac.service.application.guitar;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

import com.huajia.os.mac.R;

/**
 * @Author: HuaJ1a
 * @Emal: 821759439@qq.com
 * @Date: 2024/4/15
 * @Description:
 */
public class PitchLineView extends View {
    private static final String TAG = "IntonationLineView";

    private static final int LINE_NUMBER = 10;

    private static final int TRIANGLE_LENGTH = 15;

    private  int lineSpace;

    private Paint linePaint;

    private Paint shortPaint;

    private Paint progressPaint;

    /**
     * 三角形
     */
    private Path trianglePath;

    private int viewWidth;

    private int viewHeight;

    private int longLineStartY;

    private int longLineEndY;

    // 短线开始的坐标
    private int shortLineStartY;

    // 短线结束的坐标
    private int shorLineEndY;

    // 三角形的起点
    private int triangleStart;

    /**
     * 0 ~ 2
     * 1为中心值；0 ~ 1 为负值；1 ~ 2 为正直
     */
    private float progress = 0.1f;

    private boolean isRunning = false;

    public PitchLineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        setBackgroundColor(getResources().getColor(R.color.black));
        linePaint = new Paint();
        linePaint.setColor(getResources().getColor(R.color.color_theme_white));
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(7);
        linePaint.setStrokeCap(Paint.Cap.ROUND);

        shortPaint = new Paint();
        shortPaint.setColor(Color.parseColor("#66FFFFFF"));
        shortPaint.setStyle(Paint.Style.STROKE);
        shortPaint.setStrokeWidth(7);
        shortPaint.setStrokeCap(Paint.Cap.ROUND);

        progressPaint = new Paint();
        progressPaint.setColor(getResources().getColor(R.color.color_incorrect_red));
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeWidth(7);
        linePaint.setStrokeCap(Paint.Cap.ROUND);

        trianglePath= new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBackground(canvas);
        drawProgress(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewWidth = getMeasuredWidth();
        viewHeight = getMeasuredHeight();
        // 长线占3/4
        longLineStartY = viewHeight / 8;
        longLineEndY = viewHeight - longLineStartY;
        // 短线占长线的1/3
        shortLineStartY = viewHeight / 3;
        shorLineEndY = viewHeight - shortLineStartY;
        // 间距
        lineSpace = viewWidth / (LINE_NUMBER * 2);
        triangleStart = longLineStartY - TRIANGLE_LENGTH;
    }

    /**
     * 画出背景线
     */
    private void drawBackground(Canvas canvas) {
        int x = viewWidth / 2;
        // 中长线
        canvas.drawLine(x, longLineStartY, x, longLineEndY, linePaint);
        for (int i = 0; i < LINE_NUMBER - 1; i++) {
            x = x - lineSpace;
            canvas.drawLine(x, shortLineStartY, x, shorLineEndY, shortPaint);
        }
        x = x - lineSpace;
        // 左长线
        canvas.drawLine(x, longLineStartY, x, longLineEndY, linePaint);
        x = viewWidth / 2;
        for (int i = 0; i < LINE_NUMBER - 1; i++) {
            x = x + lineSpace;
            canvas.drawLine(x, shortLineStartY, x, shorLineEndY, shortPaint);
        }
        x = x + lineSpace;
        // 有右长线
        canvas.drawLine(x, longLineStartY, x, longLineEndY, linePaint);
    }

    private void drawProgress(Canvas canvas) {
        if (!isRunning) {
            return;
        }
        float x = 0;
        int totalX = LINE_NUMBER * lineSpace;
        if (progress > 1.0f  && progress < 2) {
             x =  viewWidth / 2 - (totalX * (1.0f - progress));
        } else if (progress < 1.0f && progress > 0) {
            x =  viewWidth / 2 + (totalX * (progress - 1.0f));
        } else if (progress >= 2) {
            x = viewWidth / 2 + totalX;
        } else if (progress <= 0) {
            x = viewWidth / 2 - totalX;
        }else {
            x = viewWidth / 2;
        }
        canvas.drawLine(x, longLineStartY, x, longLineEndY, progressPaint);
        trianglePath = new Path();
        trianglePath.moveTo(x, triangleStart);
        trianglePath.lineTo(x - TRIANGLE_LENGTH, triangleStart - TRIANGLE_LENGTH);
        trianglePath.lineTo(x + TRIANGLE_LENGTH, triangleStart - TRIANGLE_LENGTH);
        trianglePath.lineTo(x, triangleStart);
        trianglePath.close();
        canvas.drawPath(trianglePath, progressPaint);
    }

    public void setProgress(float progress) {
        isRunning = true;
        progress = Math.abs((float)(Math.round(progress * 100)) / 100);
        Log.i(TAG, "progress : " + progress);
        float start = this.progress;
        float end = progress;
        invalidate();
        ValueAnimator animator = ObjectAnimator.ofFloat(start, end);
        animator.setDuration(250);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(valueAnimator -> {
            this.progress = (float) valueAnimator.getAnimatedValue();
            if (this.progress > GuitarConstants.PITCH_CORRECT_MIN && this.progress < GuitarConstants.PITCH_CORRECT_MAX) {
                this.progressPaint.setColor(getResources().getColor(R.color.color_correct_green));
            } else {
                this.progressPaint.setColor(getResources().getColor(R.color.color_incorrect_red));
            }
            invalidate();
        });
        animator.start();
    }
}
