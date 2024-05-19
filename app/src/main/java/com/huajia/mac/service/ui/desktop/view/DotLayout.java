package com.huajia.mac.service.ui.desktop.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.huajia.os.mac.R;
import com.huajia.mac.utils.UIHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: HuaJ1a
 * @Emal: 821759439@qq.com
 * @Date: 2024/2/3
 * @Description:
 */
public class DotLayout extends FrameLayout {
    private Context context;

    private View rootView;

    private List<ImageView> dotsList;

    private LinearLayout dotContainer;

    private ImageView currentDot;

    public DotLayout(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public DotLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public DotLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        this.context = context;
        dotsList = new ArrayList<>();
        rootView = LayoutInflater.from(context).inflate(R.layout.dot_layout, this);
        dotContainer = rootView.findViewById(R.id.dot_container);
    }

    public void init(int size){
        for (int i = 0; i < size; i++) {
            ImageView imageView = new ImageView(context);
            imageView.setImageDrawable(context.getDrawable(R.drawable.icon_dot));
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            layoutParams.width = UIHelper.dp2px(context, 10);
            layoutParams.height = UIHelper.dp2px(context, 10);
            imageView.setLayoutParams(layoutParams);
            dotContainer.addView(imageView);
            dotsList.add(imageView);
        }
        setCurrentItem(0);
    }

    public void setCurrentItem(int position){
        if (position > dotsList.size()){
            return;
        }
        if (currentDot != null){
            ViewGroup.LayoutParams layoutParams = currentDot.getLayoutParams();
            layoutParams.width = UIHelper.dp2px(context, 12);
            layoutParams.height = UIHelper.dp2px(context, 12);
            currentDot.setLayoutParams(layoutParams);
        }
        ImageView imageView = dotsList.get(position);
        ViewGroup.LayoutParams params = imageView.getLayoutParams();
        params.width = UIHelper.dp2px(context, 18);
        params.height = UIHelper.dp2px(context, 18);
        imageView.setLayoutParams(params);
        currentDot = imageView;
    }
}
