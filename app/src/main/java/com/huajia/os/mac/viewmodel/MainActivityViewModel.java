package com.huajia.os.mac.viewmodel;

import androidx.lifecycle.ViewModel;

public class MainActivityViewModel extends ViewModel {

    private int maxHeightApplciation;

    private int maxWidthApplication;

    private int topHeight;

    public int getMaxHeightApplciation() {
        return maxHeightApplciation;
    }

    public void setMaxHeightApplciation(int maxHeightApplciation) {
        this.maxHeightApplciation = maxHeightApplciation;
    }

    public int getMaxWidthApplication() {
        return maxWidthApplication;
    }

    public void setMaxWidthApplication(int maxWidthApplication) {
        this.maxWidthApplication = maxWidthApplication;
    }

    public int getTopHeight() {
        return topHeight;
    }

    public void setTopHeight(int topHeight) {
        this.topHeight = topHeight;
    }
}
