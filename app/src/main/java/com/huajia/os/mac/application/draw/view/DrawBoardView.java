package com.huajia.os.mac.application.draw.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.huajia.os.mac.R;

public class DrawBoardView extends View {
    private Paint mPaint;

    private Path mPath;

    private float startX;
    private float startY;

    public DrawBoardView(Context context) {
        super(context);
    }

    public DrawBoardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DrawBoardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //画笔
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(getResources().getColor(R.color.black));
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(10);
        //路径
        mPath = new Path();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                startY = event.getY();
                mPath.moveTo(startX,startY);
                break;
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                mPath.quadTo(startX,startY,event.getX(),event.getY());
                startX = event.getX();
                startY = event.getY();
                invalidate();
                break;

        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mPath != null && mPaint != null){
            canvas.drawPath(mPath,mPaint);
        }

    }
}
