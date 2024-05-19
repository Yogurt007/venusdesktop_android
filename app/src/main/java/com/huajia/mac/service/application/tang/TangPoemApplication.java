package com.huajia.mac.service.application.tang;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.huajia.mac.framework.storage.SharedPreferencesConstants;
import com.huajia.mac.framework.storage.SharedPreferencesManager;
import com.huajia.mac.service.application.camera.RoundedCornerOutlineProvider;
import com.huajia.os.mac.R;
import com.huajia.mac.base.BaseApplication;

/**
 * @Author: HuaJ1a
 * @Emal: 821759439@qq.com
 * @Date: 2024/4/7
 * @Description:
 */
public class TangPoemApplication extends BaseApplication {
    private static final String TAG = "TangPoemApplication";

    private PDFView pdfView;

    /**
     * 当前浏览的页数
     */
    private int currentPage;

    public TangPoemApplication(@NonNull Context context) {
        super(context);
        setContentView(R.layout.application_tang_poem);
        initView();
    }

    private void initView() {
        findViewById(R.id.close_button).setOnClickListener( view -> {
            dismiss();
        });
        currentPage = SharedPreferencesManager.getInstance().getInt(SharedPreferencesConstants.Application.TANG_POEM_PAGE, 0);
        pdfView = findViewById(R.id.pdf_view);
        pdfView.setOutlineProvider(new RoundedCornerOutlineProvider(30));
        pdfView.setClipToOutline(true);
        pdfView.fromAsset("tangshi.pdf")
                .onPageChange(new OnPageChangeListener() {
                    @Override
                    public void onPageChanged(int page, int pageCount) {
                        Log.i(TAG, "当前浏览的页数： " + page);
                        currentPage = page;
                    }
                })
                .defaultPage(currentPage)
                .load();
    }

    @Override
    protected void onStop() {
        SharedPreferencesManager.getInstance().putInt(SharedPreferencesConstants.Application.TANG_POEM_PAGE, currentPage);
        super.onStop();
    }
}
