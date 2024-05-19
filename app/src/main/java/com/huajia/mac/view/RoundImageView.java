package com.huajia.mac.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Outline;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.huajia.os.mac.R;

/**
 * @Author: HuaJ1a
 * @Emal: 821759439@qq.com
 * @Date: 2024/3/6
 * @Description:
 */
public class RoundImageView extends androidx.appcompat.widget.AppCompatImageView {

    /**
     * 圆角
     */
    private float radius;

    /**
     * 是否为正方形
     */
    private boolean isSquare;

    public RoundImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView);
        radius = typedArray.getDimension(R.styleable.RoundImageView_radius, 0);
        isSquare = typedArray.getBoolean(R.styleable.RoundImageView_square, false);
        typedArray.recycle();

        if (radius != 0) {
            ViewOutlineProvider viewOutlineProvider = new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    outline.setRoundRect(0, 0, getWidth(), getHeight(), radius);
                }
            };
            setOutlineProvider(viewOutlineProvider);
            setClipToOutline(true);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (isSquare) {
            setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
        }
    }
}
