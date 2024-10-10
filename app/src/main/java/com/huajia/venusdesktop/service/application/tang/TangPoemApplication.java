package com.huajia.venusdesktop.service.application.tang;

import android.content.Context;
import com.orhanobut.logger.Logger;

import androidx.annotation.NonNull;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.huajia.annotation.Route;
import com.huajia.venusdesktop.framework.router.TRouterPath;
import com.huajia.venusdesktop.framework.storage.SharedPreferencesConstants;
import com.huajia.venusdesktop.framework.storage.SharedPreferencesManager;
import com.huajia.venusdesktop.service.application.camera.RoundedCornerOutlineProvider;
import com.huajia.venusdesktop.R;
import com.huajia.venusdesktop.base.BaseApplication;

/**
 * @Author: HuaJ1a
 * @Emal: 821759439@qq.com
 * @Date: 2024/4/7
 * @Description:
 */
@Route(path = TRouterPath.TANGPOEM, heightPercent = 1)
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
                        Logger.i(TAG, "当前浏览的页数： " + page);
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
