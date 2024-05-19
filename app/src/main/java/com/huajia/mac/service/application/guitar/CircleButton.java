package com.huajia.mac.service.application.guitar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.huajia.os.mac.R;

/**
 * @Author: HuaJ1a
 * @Email: 821759439@qq.com
 * @Date: 2024/4/21
 * @Description:
 */
public class CircleButton extends androidx.appcompat.widget.AppCompatButton {

    private static final float OVAL_RADIUS_X = 50f;

    private static final float OVAL_RADIUS_Y = 20f;

    // 是否被点击了
    private boolean isClick;

    private Paint strokePaint;

    private Paint fillPaint;

    // 是否校准完成
    private boolean isAdjustFinish;

    public CircleButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        setBackground(null);
        setTextColor(getResources().getColor(R.color.white));
        fillPaint = new Paint();
        fillPaint.setStyle(Paint.Style.FILL);
        fillPaint.setAntiAlias(true);

        strokePaint = new Paint();
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeWidth(2);
        strokePaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (isAdjustFinish) {
            fillPaint.setColor(getResources().getColor(R.color.color_correct_green));
            if (isClick) {
                // 调音完成，但是还在选择状态，此时外圆还是为点击状态颜色
                strokePaint.setColor(getResources().getColor(R.color.color_theme_pink));
            } else {
                // 否则为正确状态颜色
                strokePaint.setColor(getResources().getColor(R.color.color_correct_green));
            }
        } else if (isClick){
            fillPaint.setColor(getResources().getColor(R.color.color_theme_pink));
            strokePaint.setColor(getResources().getColor(R.color.color_theme_pink));
        }else {
            fillPaint.setColor(Color.parseColor("#66FFFFFF"));
            strokePaint.setColor(Color.parseColor("#66FFFFFF"));
        }
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2 - 3, strokePaint);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2 - 7, strokePaint);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2 - 14, fillPaint);
        super.onDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        setMeasuredDimension(width, width);
    }

    public void setClick(boolean isClick) {
        this.isClick = isClick;
        postInvalidate();
    }

    public void setAdjustFinish(boolean isAdjustFinish) {
        if (isAdjustFinish) {
            this.isAdjustFinish = true;
            postInvalidate();
        }
    }
}
