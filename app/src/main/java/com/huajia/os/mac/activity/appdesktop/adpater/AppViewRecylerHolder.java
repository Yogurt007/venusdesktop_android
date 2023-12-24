package com.huajia.os.mac.activity.appdesktop.adpater;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.huajia.os.mac.R;
import com.huajia.os.mac.activity.appdesktop.bean.App;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class AppViewRecylerHolder extends RecyclerView.ViewHolder {
    public ImageView appIcon;

    public TextView appName;

    public View mContainer;

    public AppViewRecylerHolder(@NonNull View itemView) {
        super(itemView);
        appIcon = itemView.findViewById(R.id.app_icon);
        appName = itemView.findViewById(R.id.app_name);
        mContainer = itemView.findViewById(R.id.container_layout);
    }
}
