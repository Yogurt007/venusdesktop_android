package com.huajia.os.mac.application.music;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.huajia.os.mac.R;
import com.huajia.os.mac.application.BaseApplication;

public class MusicApplication extends BaseApplication {
    private ImageView ivClose;

    public MusicApplication(@NonNull Context context) {
        super(context);
    }

    public MusicApplication(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected MusicApplication(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.application_music);
        initView();
    }

    private void initView() {
        ivClose = findViewById(R.id.close_button);
        ivClose.setOnClickListener(view -> {
            dismiss();
        });
    }
}
